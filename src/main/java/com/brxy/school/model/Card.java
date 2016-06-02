package com.brxy.school.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.brxy.school.common.CardRange;
import com.brxy.school.common.CommonConstants;

/**
 * 用户一卡通
 * 
 * @author brxy
 *
 */

@Entity
@Table(name = CommonConstants.TABLE_PREFIX + "card")
public class Card implements Serializable {

	private static final long serialVersionUID = 5832037604438760234L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CARD_ID", nullable = false, unique = true)
	private String cardId;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "RECORD_DATE")
	private Date recordDate;

	@Column(name = "UPDATE_DATE")
	private Date updateDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "CARD_RANGE")
	private CardRange cardRange;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Device.class)
	@JoinTable(name = CommonConstants.TABLE_PREFIX + "device_card", joinColumns = {
			@JoinColumn(name = "FK_CARD_ID") }, inverseJoinColumns = { @JoinColumn(name = "FK_DEVICE_ID") })
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	public CardRange getCardRange() {
		return cardRange;
	}

	public void setCardRange(CardRange cardRange) {
		this.cardRange = cardRange;
	}

	public Card() {
		super();
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", cardId=" + cardId + ", username=" + username + ", phone=" + phone + ", recordDate="
				+ recordDate + ", updateDate=" + updateDate + ", cardRange=" + cardRange + "]";
	}

	public Card(Long id, String cardId, String username, String phone, Date recordDate, Date updateDate,
			CardRange cardRange, Set<Device> devices) {
		super();
		this.id = id;
		this.cardId = cardId;
		this.username = username;
		this.phone = phone;
		this.recordDate = recordDate;
		this.updateDate = updateDate;
		this.cardRange = cardRange;
		this.devices = devices;
	}

	
	
}
