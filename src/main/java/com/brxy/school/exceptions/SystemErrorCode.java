package com.brxy.school.exceptions;

import java.io.Serializable;

public interface SystemErrorCode extends Serializable {
	String getErrorKey();

//	AppModule getAppModule();
}