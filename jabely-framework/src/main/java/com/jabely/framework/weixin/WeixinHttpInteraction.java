/**
 * 
 */
package com.jabely.framework.weixin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jabely.framework.util.JsonUtil;

/**
 * 与微信的交互对象
 * 
 * @author mingxing.fmx
 * 
 */
public class WeixinHttpInteraction implements DisposableBean, InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(WeixinHttpInteraction.class);

	public static final String PAGE_AUTH_URL_BASE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect";
	public static final String PAGE_AUTH_URL_USER = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect";

	private static final String WEIXIN_HTTP_HOST = "https://api.weixin.qq.com";
	// private static final String WEIXIN_MP_HTTP_HOST =
	// "https://mp.weixin.qq.com";

	private static final String ACCESS_TOKEN_PARAM_KEY = "access_token";

	private static final String URI_PREX = "/cgi-bin";

	private CloseableHttpClient httpClient;
	private Timer timer;
	private String accessToken;
	private String appid;
	private String appsecret;

	public String getAppid() {
		return this.appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getAppsecret() {
		return this.appsecret;
	}

	/**
	 * 获取不需要用户点击授权的请求微信的url
	 * 
	 * @param redirectUrl
	 * @param state
	 * @return
	 */
	public String queryBasePageAuthUrl(String redirectUrl, String state) {
		return String.format(PAGE_AUTH_URL_BASE, getAppid(), redirectUrl, state);
	}

	/**
	 * 获取需要用户点击授权的请求微信的url
	 * 
	 * @param redirectUrl
	 * @param state
	 * @return
	 */
	public String queryUserPageAuthUrl(String redirectUrl, String state) {
		return String.format(PAGE_AUTH_URL_USER, getAppid(), redirectUrl, state);
	}

	/**
	 * 根据openId获取用户信息
	 * 
	 * @param openid
	 * @param lang
	 * @return
	 * @throws WeixinBizExcepton
	 */
	public WeixinUser queryWeixinUser(String openid) throws WeixinBizExcepton {
		HttpGet httpget = new HttpGet(WEIXIN_HTTP_HOST + URI_PREX + "/user/info?" + ACCESS_TOKEN_PARAM_KEY + "=" + accessToken + "&openid=" + openid + "&lang=zh_CN");
		return invoke(httpget, WeixinUser.class, "根据OpenId获取微信用户基础信息");
	}

	/**
	 * 网页授权后，获取微信用户信息
	 * 
	 * @param openid
	 * @param authAccessToken
	 * @return
	 * @throws WeixinBizExcepton
	 */
	public WeixinUser queryWeixinUser(String openid, String authAccessToken) throws WeixinBizExcepton {
		HttpGet httpget = new HttpGet(WEIXIN_HTTP_HOST + "/sns/userinfo?" + ACCESS_TOKEN_PARAM_KEY + "=" + authAccessToken + "&openid=" + openid + "&lang=zh_CN");
		return invoke(httpget, WeixinUser.class, "网页授权后获取微信用户基础信息");
	}

	/**
	 * 
	 * @param code
	 * @return
	 * @throws WeixinBizExcepton
	 */
	public PageAuthAccessToken queryPageAuthAccessToken(String code) throws WeixinBizExcepton {
		HttpGet httpget = new HttpGet(WEIXIN_HTTP_HOST + "/sns/oauth2/access_token?appid=" + getAppid() + "&secret=" + getAppsecret() + "&code=" + code + "&grant_type=authorization_code");
		PageAuthAccessToken pageAuthAccessToken = invoke(httpget, PageAuthAccessToken.class, "网页授权后获取openId");

		return pageAuthAccessToken;
	}

	private <T> T invoke(HttpGet httpget, Class<T> clazz, String methodDes) throws WeixinBizExcepton {
		T t = null;
		try {
			HttpResponse response = null;
			response = httpClient.execute(httpget);
			if (response != null) {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status <= 300) {
					HttpEntity entity = response.getEntity();
					t = JsonUtil.parseObject(entity != null ? EntityUtils.toString(entity, "utf-8") : "", clazz);
				}
			}
		} catch (Exception e) {
			logger.error(methodDes + "失败", e);
			throw new WeixinHttpException(methodDes + "失败", e);
		}
		if (t != null && clazz.isAssignableFrom(Weixin.class)) {
			Weixin weixin = (Weixin) t;
			if (weixin.isSuccess()) {
				return t;
			} else {
				logger.error(methodDes + "失败:" + weixin.getErrcode() + ":" + weixin.getErrmsg());
				throw new WeixinBizExcepton(weixin.getErrcode(), weixin.getErrmsg());
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(String.format(PAGE_AUTH_URL_BASE, "1", "2", "3"));
		// boolean isForever = true;
		// String param = "{" + (!isForever ? "\"expire_seconds\": 1800, " : "")
		// + "\"action_name\": \"" + (!isForever ? "QR_SCENE" :
		// "QR_LIMIT_SCENE")
		// + "\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
		// System.out.println(param);
		// String josnstr =
		// "{\"ticket\":\"gQG28DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL0FuWC1DNmZuVEhvMVp4NDNMRnNRAAIEesLvUQMECAcAAA==\",\"expire_seconds\":1800}";
		// QRCode qrcode = JSON.parseObject(josnstr, QRCode.class);
		// System.out.println(qrcode.getTicket());
		// System.out.println(qrcode.getExpire_seconds());
		// System.out.println("qrscene_124".lastIndexOf("_"));
		// System.out.println("qrscene_124".substring("qrscene_".length()));
		// int time = 7200-30*60;
		// Calendar c = Calendar.getInstance();
		// c.setTime(new Date());
		// c.add(Calendar.SECOND, time);
		// System.out.println(c.getTime());
	}

	/**
	 * 重置access_token
	 * 
	 * @return 返回多少秒之后再来重置access_token
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private int resetTokenFromWeixin() throws ClientProtocolException, IOException {
		int time = 0;
		HttpGet httpget = new HttpGet(WEIXIN_HTTP_HOST + URI_PREX + "/token?grant_type=client_credential&appid=" + getAppid() + "&secret=" + getAppsecret());
		HttpResponse response = httpClient.execute(httpget);
		if (response != null) {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status <= 300) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					JSONObject object = JSON.parseObject(EntityUtils.toString(entity, "utf-8"));
					accessToken = object.getString("access_token");
					time = object.getIntValue("expires_in");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					logger.warn("获取到的access_token为：" + accessToken + "，当前时间为：" + sdf.format(new Date()));
					// token过期前半个小时再去取一次token
					return time > 0 ? time - 30 * 60 : time;
				}
			}
		}
		return time;
	}

	private class GetTokenTask extends TimerTask {
		private Logger logger = LoggerFactory.getLogger(GetTokenTask.class);

		private Timer timer;
		private WeixinHttpInteraction interaction;

		public GetTokenTask(Timer timer, WeixinHttpInteraction interaction) {
			this.timer = timer;
			this.interaction = interaction;
		}

		@Override
		public void run() {
			try {
				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
				this.cancel();// 取消调度任务
				int time = interaction.resetTokenFromWeixin();
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.SECOND, time);
				this.timer.schedule(new GetTokenTask(this.timer, this.interaction), c.getTime());
			} catch (Exception e) {
				logger.error("定时调度任务获取微信的token失败!!!", e);
			}
		}
	}

	@Override
	public void destroy() throws Exception {
		if (httpClient != null) {
			httpClient.close();
			httpClient = null;
		}
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (httpClient == null) {
			httpClient = HttpClients.createDefault();
		}

		if (timer == null) {
			timer = new Timer("微信Token定时器");
			int time = resetTokenFromWeixin();
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.SECOND, time);
			timer.schedule(new GetTokenTask(timer, this), c.getTime());
		}
	}
}