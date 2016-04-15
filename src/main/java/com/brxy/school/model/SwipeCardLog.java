package com.brxy.school.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.brxy.school.common.CommonConstants;
import com.brxy.school.common.OperationType;

@Entity
@Table(name=CommonConstants.TABLE_PREFIX+"swipecardlog")
public class SwipeCardLog implements Serializable {

	private static final long serialVersionUID = -6864314801483814485L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	@Column(name="DEVICE_ID")
	private Long deviceId;
	
	@Column(name="DEVICE_NAME")
	private String deviceName;
	
	@Column(name="CARD_ID")
	private String cardId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="OPERATION_TYPE")
	private OperationType operationType;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public SwipeCardLog() {
		super();
	}

	@Override
	public String toString() {
		return "SwipeCardLog [id=" + id + ", deviceId=" + deviceId + ", deviceName=" + deviceName + ", cardId=" + cardId
				+ ", operationType=" + operationType + "]";
	}
	
	
	
	
}
