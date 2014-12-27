package com.gyh.digou.bean;

import net.tsz.afinal.annotation.sqlite.Table;
@Table(name="shopOwner")
public class ShopOwner {
	private String id;
	private String shopOwnerId;
	private String shopOwner;

	public ShopOwner()
	{
		
	}
	
	
	public ShopOwner(String shopOwnerId, String shopOwner) {
		super();
		this.shopOwnerId = shopOwnerId;
		this.shopOwner = shopOwner;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopOwnerId() {
		return shopOwnerId;
	}

	public void setShopOwnerId(String shopOwnerId) {
		this.shopOwnerId = shopOwnerId;
	}

	public String getShopOwner() {
		return shopOwner;
	}

	public void setShopOwner(String shopOwner) {
		this.shopOwner = shopOwner;
	}
}
