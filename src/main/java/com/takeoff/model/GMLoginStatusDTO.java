package com.takeoff.model;

import lombok.Data;

@Data
public class GMLoginStatusDTO {
	
	String userId="0";
	String userType="NONE";
	Boolean loginStatus=false;
	String jwt="";
	String loginId="0";
	String name="";
	String message = "";

}
