package com.brxy.school.service.vos;

import java.util.HashSet;
import java.util.Set;

/**
*
*@author xiaobing
*@version 2016年4月25日 下午6:06:29
*/
public class PanelSwitchVo {

	private Long id;
	
	private Set<ChildSwitchVo> child = new HashSet<ChildSwitchVo>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<ChildSwitchVo> getChild() {
		return child;
	}

	public void setChild(Set<ChildSwitchVo> child) {
		this.child = child;
	}
	
	
	
}
