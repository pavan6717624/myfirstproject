package com.takeoff.model;

import lombok.Data;

@Data
public class ImageDTO {
	
String src, publicId;

public ImageDTO(String src, String publicId)
{
	this.src=src;
	this.publicId=publicId;
}
}
