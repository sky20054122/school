package com.brxy.school.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.brxy.school.common.CommonConstants;

/**
 * 用户卡
 * @author brxy
 *
 */

@Entity
@Table(name=CommonConstants.TABLE_PREFIX+"card")
public class Card implements Serializable{

	private static final long serialVersionUID = 5832037604438760234L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	@Column(name="CARD_ID",nullable=false,unique=true)
	private String cardId;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name="RECORD_DATE")
	private Date recordDate;
	
	@ManyToMany(fetch=FetchType.EAGER,targetEntity=Device.class)
	@JoinTable(name=CommonConstants.TABLE_PREFIX+"device_card",joinColumns={@JoinColumn(name="FK_CARD_ID")},inverseJoinColumns={@JoinColumn(name="FK_DEVICE_ID")})
	private Set<Device> devices = new HashSet<Device>();

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	public Card() {
		super();
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", cardId=" + cardId + ", username=" + username + ", phone=" + phone + ", recordDate="
				+ recordDate + "]";
	}
	
	
}
