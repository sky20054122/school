package com.brxy.school.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.brxy.school.common.CommonConstants;

/**
 * 一个开关面板  上面有两个开关按钮  ，后期也可能三个 四个按钮，所以设计成集合
 * @author brxy
 *
 */

@Entity
@Table(name=CommonConstants.TABLE_PREFIX+"panelswitch")
public class PanelSwitch implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	@OneToMany(mappedBy="panelSwitch",fetch=FetchType.EAGER,targetEntity=ChildSwitch.class,cascade=CascadeType.ALL)
	private Set<ChildSwitch> child = new HashSet<ChildSwitch>();
	
	@ManyToOne
	@JoinColumn(name="FK_DEIVCE_ID")
	private Device device;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<ChildSwitch> getChild() {
		return child;
	}

	public void setChild(Set<ChildSwitch> child) {
		this.child = child;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public PanelSwitch() {
		super();
	}

	@Override
	public String toString() {
		return "PanelSwitch [id=" + id + ", child=" + child + "]";
	}
	
	
	
	
}
