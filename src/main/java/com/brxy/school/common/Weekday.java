package com.brxy.school.common;

public enum Weekday {

	MONDAY(1),
	
	TUESDAY(2),
	
	WEDNESDAY(3),
	
	THURSDAY(4),
	
	FRIDAY(5),
	
	SATURDAY(6),
	
	SUNDAY(7);
	
	private int value=0;
	
	private Weekday(int value) {
		this.value = value;
	}

	public static Weekday valueOf(int value) {
		for (Weekday weekday : Weekday.values()) {
			if (value == weekday.value) {
				return weekday;
			}
		}
		return null;
	}


	public int value() {
		return this.value;
	}
}
