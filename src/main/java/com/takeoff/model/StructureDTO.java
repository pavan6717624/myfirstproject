package com.takeoff.model;

public class StructureDTO {
	
	String structure;
	Integer type;
	
	
	public StructureDTO(String structure, Integer type) {
		
		this.structure = structure;
		this.type = type;
	}
	
	public StructureDTO() {
	
	}
	
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	

}
