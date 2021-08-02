package com.takeoff.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ImageDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1137373385198496953L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String image;
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "userId")
	UserDetails user;
	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
