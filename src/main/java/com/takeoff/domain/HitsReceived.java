package com.takeoff.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HitsReceived  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6201318277070973871L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long mappingId;
	
	public Long getMappingId() {
		return mappingId;
	}

	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
	}

	public Timestamp getHitOn() {
		return hitOn;
	}

	public void setHitOn(Timestamp hitOn) {
		this.hitOn = hitOn;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	@Column(columnDefinition="datetime")
	 Timestamp  hitOn;
	
	String referer;
}
