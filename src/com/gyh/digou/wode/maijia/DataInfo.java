package com.gyh.digou.wode.maijia;

public class DataInfo {
	
	private String total;
	private String list;
	
	
	
	public DataInfo(){
		super();
	}
	
	public DataInfo(String total,String list){
		super();
		this.total=total;
		this.list=list;
	}
	
	
	
	
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getList() {
		return list;
	}
	public void setList(String list) {
		this.list = list;
	}
public String toString() {
		
		return total+","+list;
	}

}
