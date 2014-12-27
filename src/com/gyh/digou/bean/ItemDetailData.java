package com.gyh.digou.bean;

import java.io.Serializable;

public class ItemDetailData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1625336332709525816L;
	private String id;
	private Goods goods;
	private Store_Data store_data;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Store_Data getStore_data() {
		return store_data;
	}
	public void setStore_data(Store_Data store_data) {
		this.store_data = store_data;
	}
}
