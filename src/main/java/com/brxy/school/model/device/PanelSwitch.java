package com.brxy.school.model.device;

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
import com.brxy.school.model.Device;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 一个开关面板  上面有两个开关按钮  ，后期也可能三个 四个按钮，所以设计成集合
 * @author brxy
 *
 */

@Entity
@Table(name=CommonConstants.TABLE_PREFIX+"panelswitch")
public class PanelSwitch implements Serializable{
	
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
	
	/**
	 * 开关面板的名称
	 */
	@Column(name="NAME")
	private String name;
	
	/**
	 * 每个开关面板的mac地址
	 */
	@Column(name="MAC")
	private String mac;

	

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

	@JsonIgnore
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public PanelSwitch() {
		super();
	}

	@Override
	public String toString() {
		return "PanelSwitch [child=" + child + ", name=" + name + ", mac=" + mac + "]";
	}
	
	

	public PanelSwitch(Set<ChildSwitch> child, Device device) {
		super();
		this.child = child;
		this.device = device;
	}

	public PanelSwitch(Set<ChildSwitch> child, Device device, String name, String mac) {
		super();
		this.child = child;
		this.device = device;
		this.name = name;
		this.mac = mac;
	}
	
	
	
	
	
	
}
