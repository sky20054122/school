/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */

package com.brxy.school.service.domain;


import java.io.Serializable;

import com.brxy.school.common.HashCodeUtil;
import com.brxy.school.websocket.protocol.WSEntity;
import com.brxy.school.websocket.protocol.WSHeaders;




/**
 * @author xiaobing
 * 
 */
public class DistributedRequestBean<T> implements Serializable
{
    private static final long serialVersionUID = 6748222409775301466L;

    private WSEntity<T> entity;

    private String promptMsg;


    public DistributedRequestBean(WSEntity<T> entity, String promptMsg) {
        this.entity = entity;
        this.promptMsg = promptMsg;
    }

    public DistributedRequestBean() {
    }

    public WSEntity<T> getEntity()
    {
        return entity;
    }

    public void setEntity(WSEntity<T> entity)
    {
        this.entity = entity;
    }

    public String getPromptMsg()
    {
        return promptMsg;
    }

    public void setPromptMsg(String promptMsg)
    {
        this.promptMsg = promptMsg;
    }

    @Override
    public String toString()
    {
        return "DistributedRequestBean [entity=" + entity + ", promptMsg="
                + promptMsg + "]";
    }

    @Override
    public int hashCode()
    {
        return HashCodeUtil.hash(23, this.entity.getHeaders().getRequestId());
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
        {
            return false;
        }
        if(this == obj)
        {
            return true;
        }
//        if(obj instanceof Area)
//        {
//            DistributedRequestBean drb = (DistributedRequestBean) obj;
//            WSHeaders headers = drb.getEntity().getHeaders();
//            if(headers.getRequestId().equals(
//                    this.entity.getHeaders().getRequestId()))
//            {
//                return true;
//            }
//        }
        return false;
    }
}
