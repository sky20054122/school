/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */
package com.brxy.school.exceptions;

/**
 * @author Eric
 *
 */
public class FatalException extends Exception
{
    private static final long serialVersionUID = -6811766889490960769L;

    public FatalException(String errMsg)
    {
        super(errMsg);
    }

}
