/**
 * 
 */
package com.jabely.web.common;

import com.jabely.framework.util.StringUtils;

/**
 * @author fangmingxing
 *
 */
public class UserContext {

	private static final ThreadLocal<SessionUser> userThreadLocal = new ThreadLocal<SessionUser>();

	/**
	 * 是否已经注册过
	 * 
	 * @return
	 */
	public static boolean hasRegistered() {
		return userThreadLocal.get() != null && StringUtils.isNotBlank(getUserMobilePhone());
	}

	/**
	 * 已经获取到微信信息
	 * 
	 * @return
	 */
	public static boolean hasWeixinInfo() {
		return userThreadLocal.get() != null && StringUtils.isNotBlank(getWeixinNickName());
	}

	public static String getWeixinOpenId() {
		return userThreadLocal.get() != null ? userThreadLocal.get().getWeixinOpenId() : null;
	}

	public static String getWeixinUnionId() {
		return userThreadLocal.get() != null ? userThreadLocal.get().getWeixinUnionId() : null;
	}

	public static String getWeixinNickName() {
		return userThreadLocal.get() != null ? userThreadLocal.get().getWeixinNickName() : null;
	}

	public static Long getUserId() {
		return userThreadLocal.get() != null ? userThreadLocal.get().getUserId() : null;
	}

	public static String getUserMobilePhone() {
		return userThreadLocal.get() != null ? userThreadLocal.get().getUserMobilePhone() : null;
	}

	public static void putUserId(Long userId) {
		getUser().setUserId(userId);
	}

	public static void putUserMobilePhone(String userMobilePhone) {
		getUser().setUserMobilePhone(userMobilePhone);
	}

	public static void putWeixinOpenId(String weixinOpenId) {
		getUser().setWeixinOpenId(weixinOpenId);
	}

	public static void putWeixinNickName(String weixinNickName) {
		getUser().setWeixinNickName(weixinNickName);
	}

	public static void putWeixinUnionId(String weixinUnionId) {
		getUser().setWeixinUnionId(weixinUnionId);
	}

	private static SessionUser getUser() {
		SessionUser user = userThreadLocal.get();
		if (user == null) {
			user = new SessionUser(null, null, null, null, null);
			userThreadLocal.set(user);
		}
		return user;
	}

	public static void removeUserSessionInfo() {
		userThreadLocal.remove();
	}

	private static class SessionUser {

		public SessionUser(String weixinOpenId, String weixinUnionId, String weixinNickName, Long userId, String userMobilePhone) {
			this.weixinNickName = weixinNickName;
			this.weixinOpenId = weixinOpenId;
			this.weixinUnionId = weixinUnionId;
			this.userId = userId;
			this.userMobilePhone = userMobilePhone;
		}

		private String weixinOpenId;
		private String weixinUnionId;
		private String weixinNickName;
		private Long userId;
		private String userMobilePhone;

		public String getWeixinOpenId() {
			return weixinOpenId;
		}

		public void setWeixinOpenId(String weixinOpenId) {
			this.weixinOpenId = weixinOpenId;
		}

		public String getWeixinUnionId() {
			return weixinUnionId;
		}

		public void setWeixinUnionId(String weixinUnionId) {
			this.weixinUnionId = weixinUnionId;
		}

		public String getWeixinNickName() {
			return weixinNickName;
		}

		public void setWeixinNickName(String weixinNickName) {
			this.weixinNickName = weixinNickName;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getUserMobilePhone() {
			return userMobilePhone;
		}

		public void setUserMobilePhone(String userMobilePhone) {
			this.userMobilePhone = userMobilePhone;
		}

	}
}
