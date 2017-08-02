/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */
package com.brxy.school.websocket.protocol;


/**
 * 定义响应状态码
 * 
 * @author Eric
 *
 */
public enum WSStatus
{
    SUCCESS(200, "OK"),
    
    ALREADY_EXISTED(208, "Already Existed"),

    BAD_REQUEST(400, "Bad Request"),

    UNAUTHORIZED(401, "Unauthorized"),
    
    NOT_EXIST(404, "Not Exist"),
   
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    
    RESOURCE_REMOVED(410, "Resource Removed"),
    
    FAILED(412, "Precondition Failed");
    
    private final int value;

    private final String reasonPhrase;


    private WSStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value()
    {
        return this.value;
    }

    public String getReasonPhrase()
    {
        return reasonPhrase;
    }

}
