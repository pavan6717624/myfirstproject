package com.takeoff.model;



public class OrderDetails {
	
	

	@Override
	public String toString() {
		return "OrderDetails [cart=" + cart + ", catId=" + catId + "]";
	}

	Cart cart;
	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

	Long catId;
	
	

}
