package com.gyh.digou.bean;

import java.io.Serializable;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class GoodsCategory  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1218810142376970139L;
	public String cate_id;
	public String store_id;
	public String cate_name;
	public String parent_id;
	public String sort_order;
	public String if_show;
	public String image;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<GoodsCategory> children;
	public String getCate_id() {
		return cate_id;
	}
	public void setCate_id(String cate_id) {
		this.cate_id = cate_id;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getCate_name() {
		return cate_name;
	}
	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getSort_order() {
		return sort_order;
	}
	public void setSort_order(String sort_order) {
		this.sort_order = sort_order;
	}
	public String getIf_show() {
		return if_show;
	}
	public void setIf_show(String if_show) {
		this.if_show = if_show;
	}
	public List<GoodsCategory> getChildren() {
		return children;
	}
	public void setChildren(List<GoodsCategory> children) {
		this.children = children;
	}
/*	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(cate_name);
	}
	
	
	public static final Parcelable.Creator<GoodsCategory> CREATOR= new Parcelable.Creator<GoodsCategory>() {

		@Override
		public GoodsCategory createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new GoodsCategory(source);
		}

		@Override
		public GoodsCategory[] newArray(int size) {
			// TODO Auto-generated method stub
			return new GoodsCategory[size];
		}
	};  
	public GoodsCategory(Parcel in)
	{
		cate_name=in.readString();
	}*/
	/*@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		
		sb.append("{");
		sb.append(cate_id+",");
		sb.append(cate_name+",");
		
		sb.append("[");
		for(GoodsCategory category:children)
		{
			sb.append(category.toString());
			sb.append("{");
			sb.append(category.getCate_id()+":");
			sb.append(category.getCate_name()+":");
			sb.append(category.getStore_id());
			sb.append(category.getChildren());
			sb.append("}");
		}
		
		sb.append("]");
		
		sb.append(Utils.printList(children));
		sb.append("}");
		
		return sb.toString();
	}*/
}
