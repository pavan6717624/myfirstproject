package com.takeoff.model;



public class DisplayItemsDTO {
	


	@Override
	public String toString() {
		return "DisplayItemsDTO [id=" + id + ", name=" + name + ", price=" + price + ", cookingTime=" + cookingTime
				+ ", availability=" + availability + ", count=" + count + "]";
	}
	Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getCookingTime() {
		return cookingTime;
	}
	public void setCookingTime(Integer cookingTime) {
		this.cookingTime = cookingTime;
	}
	public Boolean getAvailability() {
		return availability;
	}
	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	String name;
	Double price;
	Integer cookingTime;
	Boolean availability;
	Integer count;

}