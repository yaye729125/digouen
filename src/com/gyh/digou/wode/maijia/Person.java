package com.gyh.digou.wode.maijia;

import net.tsz.afinal.annotation.sqlite.Table;


@Table(name = "person" )

public class Person {
	
	private String id;
     public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String xingming;
     private String dianhua;
     private String diqu;
     private String dizhi;
     private String youbian;
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	public String getDianhua() {
		return dianhua;
	}
	public void setDianhua(String dianhua) {
		this.dianhua = dianhua;
	}
	public String getDiqu() {
		return diqu;
	}
	public void setDiqu(String diqu) {
		this.diqu = diqu;
	}
	public String getDizhi() {
		return dizhi;
	}
	public void setDizhi(String dizhi) {
		this.dizhi = dizhi;
	}
	public String getYoubian() {
		return youbian;
	}
	public void setYoubian(String youbian) {
		this.youbian = youbian;
	}
	
}
