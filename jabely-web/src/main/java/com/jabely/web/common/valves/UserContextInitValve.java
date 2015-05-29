/**
 * 
 */
package com.jabely.web.common.valves;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.jabely.biz.user.UserUtil;
import com.jabely.biz.user.biz.IUserReadManager;
import com.jabely.biz.user.biz.IUserWriteManager;
import com.jabely.biz.user.dao.domain.UserInfoDO;
import com.jabely.biz.user.dao.domain.UserWeixinInfoDO;
import com.jabely.framework.log.Logger;
import com.jabely.framework.util.StringUtils;
import com.jabely.framework.weixin.PageAuthAccessToken;
import com.jabely.framework.weixin.WeixinHttpInteraction;
import com.jabely.framework.weixin.WeixinUser;
import com.jabely.web.common.UserContext;

/**
 * @author fangmingxing
 *
 */
public class UserContextInitValve extends AbstractValve {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private WeixinHttpInteraction weixinHttpInteraction;

	@Autowired
	private IUserReadManager userReadManager;

	@Autowired
	private IUserWriteManager userWriteManager;

	/** 无需登陆的请求目录，根目录为'/' */
	private Set<String> noNeedLoginDir;

	/** 不需要登陆的目录里，某些需要登陆的具体请求 */
	private Set<String> needLoginUrls;

	/** 需要登陆的目录里，某些不需要登陆的具体请求 */
	private Set<String> noNeedLoginUrls;

	/** 无需用户手机号的请求目录 */
	private Set<String> noNeedMobileDir;

	/** 无需用户手机号的目录中，某些需要用户手机号的请求目录 */
	private Set<String> needMobileUrls;

	/** 需要手机号的目录中，某些不需要手机号的具体请求 */
	private Set<String> noNeedMobileUrls;

	private static final String weixinOpenIdKey = "weixinOpenId";
	private static final String weixinUnionIdKey = "weixinUnionId";
	private static final String weixinNickNameKey = "weixinNickName";
	private static final String userIdKey = "userId";
	private static final String userMobilePhoneKey = "mobilePhone";

	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		TurbineRunData rundata = getTurbineRunData(request);
		String target = rundata.getTarget();

		// 是否需要登录，不需要的场景直接invoke下一步
		if (!isNeedLogin(target)) {
			pipelineContext.invokeNext();
			return;
		}

		// 设置session
		HttpSession session = rundata.getRequest().getSession();
		Object weixinOpenId = session.getAttribute(weixinOpenIdKey);
		UserContext.putWeixinOpenId(weixinOpenId == null ? null : weixinOpenId.toString());
		Object weixinUnionId = session.getAttribute(weixinUnionIdKey);
		UserContext.putWeixinUnionId(weixinUnionId == null ? null : weixinUnionId.toString());
		Object weixinNickName = session.getAttribute(weixinNickNameKey);
		UserContext.putWeixinNickName(weixinNickName == null ? null : weixinNickName.toString());
		Object userId = session.getAttribute(userIdKey);
		UserContext.putUserId(userId == null ? null : Long.parseLong(userId.toString()));
		Object userMobilePhone = session.getAttribute(userMobilePhoneKey);
		UserContext.putUserMobilePhone(userMobilePhone == null ? null : userMobilePhone.toString());

		// 微信昵称&手机号不存在则跳转到需要执行的页面去
		if (StringUtils.isNotBlank(UserContext.getWeixinNickName())) {
			// 手机不存在，请求页面也需要登陆用户手机，重定向到手机登记页面
			if (StringUtils.isBlank(UserContext.getUserMobilePhone()) && isNeedMobile(target)) {
				rundata.setRedirectTarget("/register.vm");
				return;
			}
			// 如果存在手机号，请求页面还是手机号登记页面，重定向到个人中心页面
			if (StringUtils.isNotBlank(UserContext.getUserMobilePhone()) && target.equals("/register.vm")) {
				rundata.setRedirectTarget("/personal/center.vm");
				return;
			}
			pipelineContext.invokeNext();
			return;
		}

		String state = rundata.getParameters().getString("state");
		String code = rundata.getParameters().getString("code");
		// 请求是否是从微信过来（state参数必须有，并且值为weixin1或weixin2）
		boolean isWeixinRequest = StringUtils.isNotBlank(state) && ("weixin1".equals(state) || "weixin2".equals(state));
		// 微信请求过来，把openId、unionId清空
		if (isWeixinRequest) {
			UserContext.putWeixinOpenId(null);
			UserContext.putWeixinUnionId(null);
		}

		// 微信请求过来，并且code为空，用户不给授权
		if (isWeixinRequest && StringUtils.isBlank(code)) {
			// 去首页
			rundata.setRedirectTarget("/index.vm");
			return;
		}

		String callbackUrl = request.getRequestURL() + (StringUtils.isNotBlank(request.getQueryString()) ? "" : "?" + request.getQueryString());
		// 微信openId为空，也不是从微信过来的请求,则重新定向到微信服务器去，静默授权的url
		if (StringUtils.isBlank(UserContext.getWeixinOpenId()) && !isWeixinRequest) {
			// 没有openId，跳转到微信静默授权登陆页面
			rundata.setRedirectLocation(weixinHttpInteraction.queryBasePageAuthUrl(callbackUrl, "weixin1"));
			return;
		}

		PageAuthAccessToken pageAuthAccessToken = null;
		// 微信过来的，且code不为空
		if (isWeixinRequest && StringUtils.isNotBlank(code)) {
			try {
				// 通过code获取access_toke & openId
				pageAuthAccessToken = weixinHttpInteraction.queryPageAuthAccessToken(code);
				UserContext.putWeixinOpenId(pageAuthAccessToken.getOpenid());
				UserContext.putWeixinUnionId(pageAuthAccessToken.getUnionid());
			} catch (Exception e) {
				Logger.exp("SESSION_PROCESS", "根据微信callback回来里的code参数值去获取openId、accessToken", e, code, state);
				// 跳到一个错误页面，再引导用户去刷新
				request.setAttribute("ERR_CODE", "QUERY_WEIXIN_PAGE_AUTH_ACCESS_TOKEN_EXP");// 错误码
				request.setAttribute("PRE_BIZ_DESC", "SESSION_PROCESS"); // 错误产生时正在执行的业务描述
				rundata.setRedirectTarget("/error.vm");
				return;
			}
		}

		// 如果openId还不存在，理论上不存在的情况
		if (StringUtils.isBlank(UserContext.getWeixinOpenId())) {
			// 跳到一个错误页面，再引导用户去刷新
			request.setAttribute("ERR_CODE", "WEIXIN_USER_OPEN_ID_NOT_EXISTP");// 错误码
			request.setAttribute("PRE_BIZ_DESC", "SESSION_PROCESS"); // 错误产生时正在执行的业务描述
			rundata.setRedirectTarget("/error.vm");
			return;
		}
		
		// openId不为空(查询微信用户信息异常出现了导致的微信昵称没有，但是openid却在session里)
		// 静默授权已经通过返回了code
		if (StringUtils.isNotBlank(UserContext.getWeixinOpenId())) {
			UserWeixinInfoDO userWeixinInfo = null;
			try {
				// 根据openId获取用户信息
				userWeixinInfo = userReadManager.queryUserWeixinInfo(UserContext.getWeixinOpenId());
			} catch (Exception e) {
				Logger.exp("SESSION_PROCESS", "根据微信openId获取数据库里的微信数据", e, UserContext.getWeixinOpenId());
				// 跳到一个错误页面，再引导用户去刷新
				request.setAttribute("ERR_CODE", "QUERY_USER_WEIXIN_INFO_EXP");// 错误码
				request.setAttribute("PRE_BIZ_DESC", "SESSION_PROCESS"); // 错误产生时正在执行的业务描述
				rundata.setRedirectTarget("/error.vm");
				return;
			}

			// 已经拿到过微信信息，则不在需要和微信打交道了
			if (userWeixinInfo != null) {
				UserContext.putWeixinNickName(userWeixinInfo.getNickName());
				UserInfoDO user = null;
				try {
					user = userReadManager.queryUserInfo(userWeixinInfo.getUserId());
				} catch (Exception e) {
					Logger.exp("SESSION_PROCESS", "根据用户id获取平台用户数据", e, code, state);
					// 跳到一个错误页面，再引导用户去刷新
					request.setAttribute("ERR_CODE", "QUERY_USER_INFO_EXP");// 错误码
					request.setAttribute("PRE_BIZ_DESC", "SESSION_PROCESS"); // 错误产生时正在执行的业务描述
					rundata.setRedirectTarget("/error.vm");
					return;
				}

				if (user != null) {
					UserContext.putUserMobilePhone(user.getMobilePhone());
					// 如果请求登记页面，默认跳转至个人中心
					if (target.equals("/register.vm")) {
						rundata.setRedirectTarget("/personal/center.vm");
						return;
					}
				} else {
					// 需要手机号的页面，重定向到注册页面
					if (isNeedMobile(target)) {
						rundata.setRedirectTarget("/register.vm");
						return;
					}
				}
				pipelineContext.invokeNext();
				return;
			} else {

				WeixinUser weixinUser =null;
				// 如果 先根据微信openId去获取下微信信息（已关注的用户是可以获取到得）
				if (isWeixinRequest && "weixin1".equals(state) || !isWeixinRequest) {
					try {
						weixinUser = weixinHttpInteraction.queryWeixinUser(pageAuthAccessToken.getOpenid());
					} catch (Exception e) {
						Logger.exp("SESSION_PROCESS", "根据openid获取微信用户信息", e, code, state);
						// 跳到一个错误页面，再引导用户去刷新
						request.setAttribute("ERR_CODE", "QUERY_WEIXIN_USER_INFO_EXP");// 错误码
						request.setAttribute("PRE_BIZ_DESC", "SESSION_PROCESS"); // 错误产生时正在执行的业务描述
						rundata.setRedirectTarget("/error.vm");
						return;
					}
				} else if (isWeixinRequest && "weixin2".equals(state) && pageAuthAccessToken != null) {
					try {
						// 请求来自于微信，并且是用户手动点授权过来的
						weixinUser = weixinHttpInteraction.queryWeixinUser(pageAuthAccessToken.getOpenid(), pageAuthAccessToken.getAccess_token());
					} catch (Exception e) {
						Logger.exp("SESSION_PROCESS", "根据openid+accessToken获取微信用户信息", e, code, state);
						// 跳到一个错误页面，再引导用户去刷新
						request.setAttribute("ERR_CODE", "QUERY_WEIXIN_AUTH_USER_INFO_EXP");// 错误码
						request.setAttribute("PRE_BIZ_DESC", "SESSION_PROCESS"); // 错误产生时正在执行的业务描述
						rundata.setRedirectTarget("/error.vm");
						return;
					}
				}
				// 静默授权过来可能会为null
				// 用户授权过来理论上不会为null
				if (weixinUser != null) {
					userWeixinInfo = userWriteManager.saveUserWeixinInfo(UserUtil.createUserWeixinInfo(weixinUser));
					UserContext.putWeixinNickName(userWeixinInfo.getNickName());
					UserContext.putUserId(userWeixinInfo.getUserId());
					rundata.setRedirectTarget("/register.vm");
					return;
				}
			}
		}

		// 其他情况跳转至微信用户手动授权页面
		rundata.setRedirectLocation(weixinHttpInteraction.queryBasePageAuthUrl(callbackUrl, "weixin2"));
	}

	/**
	 * 根据当前请求url判断是否需要登陆
	 * 
	 * @param target
	 * @return
	 */
	private boolean isNeedLogin(String target) {
		// 需要登陆
		if (needLoginUrls.contains(target)) {
			return true;
		}

		// 不需要登陆
		if (noNeedLoginUrls.contains(target)) {
			return false;
		}

		// 是根目录
		if (target.lastIndexOf("/") == 0 && noNeedLoginDir.contains("/")) {
			return false;
		}
		String tmp = target.substring(1);
		String dir = tmp.substring(0, tmp.indexOf("/"));
		if (noNeedLoginDir.contains(dir)) {
			return false;
		}
		return true;
	}

	/**
	 * 根据当前请求url判断是否需要登陆用户的手机号
	 * 
	 * @param target
	 * @return
	 */
	private boolean isNeedMobile(String target) {
		// 需要手机
		if (needMobileUrls.contains(target)) {
			return true;
		}

		// 不需要手机
		if (noNeedMobileUrls.contains(target)) {
			return false;
		}

		// 是根目录
		if (target.lastIndexOf("/") == 0 && noNeedMobileDir.contains("/")) {
			return false;
		}
		String tmp = target.substring(1);
		String dir = tmp.substring(0, tmp.indexOf("/"));
		if (noNeedMobileDir.contains(dir)) {
			return false;
		}
		return true;
	}

	public Set<String> getNoNeedLoginDir() {
		return noNeedLoginDir;
	}

	public void setNoNeedLoginDir(Set<String> noNeedLoginDir) {
		this.noNeedLoginDir = noNeedLoginDir;
	}

	public void setNeedLoginUrls(Set<String> needLoginUrls) {
		this.needLoginUrls = needLoginUrls;
	}

	public void setNoNeedLoginUrls(Set<String> noNeedLoginUrls) {
		this.noNeedLoginUrls = noNeedLoginUrls;
	}

	public void setNoNeedMobileDir(Set<String> noNeedMobileDir) {
		this.noNeedMobileDir = noNeedMobileDir;
	}

	public void setNeedMobileUrls(Set<String> needMobileUrls) {
		this.needMobileUrls = needMobileUrls;
	}

	public void setNoNeedMobileUrls(Set<String> noNeedMobileUrls) {
		this.noNeedMobileUrls = noNeedMobileUrls;
	}

}
