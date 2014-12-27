package com.gyh.digou.wode.shangjia;

import java.io.Serializable;

public class AddFormatBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4182669346975961038L;
	private String spec1;
	private String spec2;
	private String price;
	private String mk_price;
	private String mim_price;
	private String stock;
	
	private boolean isEmpty;
	
	
	public boolean isEmpty() {
		return isEmpty;
	}
	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	public String getSpec1() {
		return spec1;
	}
	public void setSpec1(String spec1) {
		this.spec1 = spec1;
	}
	public String getSpec2() {
		return spec2;
	}
	public void setSpec2(String spec2) {
		this.spec2 = spec2;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMk_price() {
		return mk_price;
	}
	public void setMk_price(String mk_price) {
		this.mk_price = mk_price;
	}
	public String getMim_price() {
		return mim_price;
	}
	public void setMim_price(String mim_price) {
		this.mim_price = mim_price;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "stock="+stock+"mim_price="+mim_price+"mk_price="+mk_price+"price="+price+"spec1="+spec1+"spec2="+spec2;
	}
	
	
	
	
}
