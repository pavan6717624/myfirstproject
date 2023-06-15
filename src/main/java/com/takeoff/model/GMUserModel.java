package com.takeoff.model;

import lombok.Data;

@Data
public class GMUserModel{


	Long id;
	String password = "";
	String name = "";
	Long contact = 0l;
	String email = "";
	String city = "";
	String message = "";

	Long loginId;

	String roleName="";
}
