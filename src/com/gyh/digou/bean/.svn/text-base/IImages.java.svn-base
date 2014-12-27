package com.gyh.digou.bean;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class IImages implements Parcelable {

	
	private String image_id;
	private String goods_id;
	private String image_url;
	private String thumbnail;
	private String sort_order;
	private String file_id;
	public String getImage_id() {
		return image_id;
	}
	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getSort_order() {
		return sort_order;
	}
	public void setSort_order(String sort_order) {
		this.sort_order = sort_order;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
	
	
	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(image_url);
	}
	
	
	public static final Parcelable.Creator<IImages> CREATOR=new Parcelable.Creator<IImages>() {

		@Override
		public IImages createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new IImages(source);
		}

		@Override
		public IImages[] newArray(int size) {
			// TODO Auto-generated method stub
			return new IImages[size];
		}
		
		
		
	};
	
	
	
	
	private IImages(Parcel in)
	{
		image_url=in.readString();
	}
	
	
	
	
	
	
}
