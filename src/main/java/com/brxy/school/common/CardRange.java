package com.brxy.school.common;
/**
*一卡通可以刷卡的设备范围
*@author xiaobing
*@version 2016年6月1日 下午2:14:21
*/
public enum CardRange {

	/** 全校卡  默认学校所有设备 不需要关联 */
	CARD_RANGE_ALL(1),
	

	/** 指定设备卡    关联具体设备 */
	CARD_RANGE_CUSTOM(0);

	private int value = 0;

	private CardRange(int value) {
		this.value = value;
	}

	public static CardRange valueOf(int value) {
		for (CardRange cardRange : CardRange.values()) {
			if (value == cardRange.value) {
				return cardRange;
			}
		}
		return null;
	}

	

	public int value() {
		return this.value;
	}
	
}
