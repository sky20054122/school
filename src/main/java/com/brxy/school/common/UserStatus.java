package com.brxy.school.common;

public enum UserStatus {
	/** 未激活（预增加用户后的状态） */
	NOT_ACTIVATED(-1),

	/** 启用 */
	ENABLED(1),
	
	/**已删除*/
	DELETED(2), 

	/** 停用 */
	DISABLED(0);

	private int value = 0;

	private UserStatus(int value) {
		this.value = value;
	}

	public static UserStatus valueOf(int value) {
		for (UserStatus userStatus : UserStatus.values()) {
			if (value == userStatus.value) {
				return userStatus;
			}
		}
		return null;
	}

	

	public int value() {
		return this.value;
	}
}
