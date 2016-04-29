package com.brxy.school.model.device;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.brxy.school.common.CommonConstants;
import com.brxy.school.model.Device;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name =CommonConstants.TABLE_PREFIX+ "mediaurl")
public class MediaURL implements Serializable {

	private static final long serialVersionUID = -3387072283909840699L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "MEDIA_A")
	private String mediaA;

	@Column(name = "MEDIA_B")
	private String mediaB;

	/**
	 * optional=false 表示 media必须有对应的设备 true表示可以为空
	 */
	@OneToOne(optional = false, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "FK_DEVICE_ID", unique = true)
	private Device device;

	public MediaURL() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMediaA() {
		return mediaA;
	}

	public void setMediaA(String mediaA) {
		this.mediaA = mediaA;
	}

	public String getMediaB() {
		return mediaB;
	}

	public void setMediaB(String mediaB) {
		this.mediaB = mediaB;
	}

	@JsonIgnore
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	

	public MediaURL(String mediaA, String mediaB,Device device) {
		super();
		this.mediaA = mediaA;
		this.mediaB = mediaB;
		this.device = device;
	}

	@Override
	public String toString() {
		return "MediaURL [id=" + id + ", mediaA=" + mediaA + ", mediaB=" + mediaB + "]";
	}

}
