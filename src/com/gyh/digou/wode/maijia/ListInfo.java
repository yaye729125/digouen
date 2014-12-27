package com.gyh.digou.wode.maijia;

public class ListInfo {
	private String msg_id;
	private String from_id;
	private String to_id;
	private String title;
	private String content;
	private String add_time;
	private String last_update;
	
	private String parent_id;
	
	public ListInfo(){
		super();
	}
	//public ListInfo(String msg_id,String from_id,String to_id ,String title,String content,String add_time,String last_update,String parent_id ,String status,String user_name,String user_info){
		public ListInfo(String content,String add_time){
	super();
		this.msg_id=msg_id;
		this.from_id=from_id;
		this.to_id=to_id;
		this.title=title;
		this.content=content;
		this.add_time=add_time;
		this.last_update=last_update;
		this.parent_id=parent_id;
		this.status=status;
		this.user_name=user_name;
		this.user_info=user_info;
		
	}
	
	
	
	
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getFrom_id() {
		return from_id;
	}
	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}
	public String getTo_id() {
		return to_id;
	}
	public void setTo_id(String to_id) {
		this.to_id = to_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getLast_update() {
		return last_update;
	}
	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_info() {
		return user_info;
	}
	public void setUser_info(String user_info) {
		this.user_info = user_info;
	}
	private String status;
	private String user_name;
	private String user_info;
	
public String toString() {
		
		return msg_id+","+ from_id+","+ to_id+","+ title+","+ content+","+ add_time+","+ last_update+","+ parent_id+","+ status+","+ user_name+","+ user_info;
	}
}
