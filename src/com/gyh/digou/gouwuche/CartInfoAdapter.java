package com.gyh.digou.gouwuche;

import java.util.List;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyh.digou.R;
import com.gyh.digou.itemdetail.ItemDetailActivity;

public class CartInfoAdapter extends BaseExpandableListAdapter {

	
	//private Context mContext;
	private JSONObject jsonObject;
	private LayoutInflater inflater;
	private List<JSONObject> orderList;
	List<JSONObject> cartInfo;
	private Context mContext;
	private Handler mHandler;
	FinalBitmap imageLoader;
	//private int childCount;
	
	boolean flag=false;
	public void setFlag(boolean flag)
	{
		this.flag=flag;
	}
	public void editCartInfo(boolean flag)
	{
		this.flag=flag;
		
		//cl
		notifyDataSetChanged();
		//notifyDataSetInvalidated();
	}
	public CartInfoAdapter(Context mContext)
	{
		
		this.mContext=mContext;
		inflater=LayoutInflater.from(mContext);
		imageLoader=FinalBitmap.create(mContext);
		imageLoader.configLoadingImage(R.drawable.ic_launcher);
		
	}
	
	
	
	public CartInfoAdapter(Context mContext,List<JSONObject> cartInfo)
	{
		
		
		this(mContext);
		if(cartInfo!=null)
		{
			this.cartInfo=cartInfo;
			try {
				initCartCheck(true);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			notifyDataSetChanged();
		}
		
		
	}
	/*public void setData(List<JSONObject> cartInfo)
	{
		if(cartInfo!=null)
		{
			this.cartInfo=cartInfo;
			try {
				initCartCheck(true);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			notifyDataSetChanged();
		}
	}*/
	
	public void clear()
	{
		if(cartInfo!=null)
		{
			cartInfo.clear();
			notifyDataSetChanged();
		}
		
	}
	public void initCartCheck(boolean check) throws JSONException
	{
		for(int i=0;i<cartInfo.size();i++)
		{
			JSONObject jsonObj=cartInfo.get(i);
			jsonObj.put("check",check);
		}
		
		
	}
	
	public void cancleOrAll(boolean check) throws JSONException
	{
		
		initCartCheck(check);
		
		notifyDataSetChanged();
	}
	
	public void deleteCart(int groupPosition,int childPosition)
	{
		
		
		
	}
	public void setCartData(List<JSONObject> cartInfo)
	{
		if(cartInfo!=null)
		{
			this.cartInfo=cartInfo;
			try {
				initCartCheck(true);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			notifyDataSetChanged();
			//notifyDataSetInvalidated();
			Log.d("cartInfoAdapter setcartData",cartInfo.toString());
			mHandler.sendEmptyMessage(0x1);
		}
	}
	
	public List<JSONObject> getData()
	{
		return cartInfo;
	}
	@Override
	public int getGroupCount() {
		
		return cartInfo==null?0:cartInfo.size();
	}
	@Override
	public int getChildrenCount(int groupPosition){
		int childCount=0;
		try {
			
			childCount = cartInfo.get(groupPosition).getJSONArray("goods").length();
			
		} catch (Exception e) {
			return childCount;
			//e.printStackTrace();
		}
		return childCount;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
	
	public void setHandler(Handler handler)
	{
		this.mHandler=handler;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		
		
		convertView=inflater.inflate(R.layout.cart_shopowner, null);
		
		TextView tv=(TextView) convertView.findViewById(R.id.cart_shopowner_tv);
		final CheckBox checkbox=(CheckBox) convertView.findViewById(R.id.cart_shopowner_checkbox);
		
		final int gp=groupPosition;
		
		String store_name="";
		final JSONObject json_w=cartInfo.get(groupPosition);
		
		try {
			
			checkbox.setChecked(json_w.getBoolean("check"));
		
			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				
				try {
					json_w.put("check",arg1);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkbox.setChecked(arg1);
					//notifyDataSetChanged();
				mHandler.sendEmptyMessage(0x1);
					//refreshData();
			}
		});
		
		store_name = json_w.getString("store_name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv.setText(store_name);
		
		return convertView;
	}
	
	
	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		ViewHolderChild holder=null;
		if(convertView==null)
		{
			holder=new ViewHolderChild();
			//SlideView
			convertView=inflater.inflate(R.layout.cart_item, null);
			holder.tv_cart_item_name=(TextView) convertView.findViewById(R.id.cart_item_name);
			holder.tv_cart_item_num=(TextView) convertView.findViewById(R.id.cart_item_num);
			//holder.checkbox_cart_item=(CheckBox) convertView.findViewById(R.id.cart_item_checkbox);
			
			holder.tv_cart_item_edit_num=(TextView) convertView.findViewById(R.id.pop_num);
			holder.btn_cart_item_num_add=(ImageView) convertView.findViewById(R.id.pop_add);
			holder.btn_cart_item_num_del=(ImageView) convertView.findViewById(R.id.pop_reduce);
			holder.imv_cart_item=(ImageView) convertView.findViewById(R.id.cart_item_imv);	
			holder.tv_cart_item_price=(TextView) convertView.findViewById(R.id.cart_item_price);
			holder.tv_cart_item_format=(TextView) convertView.findViewById(R.id.cart_item_format);
			holder.layout_cart_item_editnum=(LinearLayout) convertView.findViewById(R.id.cart_item_layout_editnum);
			holder.btn_cart_item_del=(ImageView) convertView.findViewById(R.id.cart_item_btn_del);
			convertView.setTag(holder);
		}else
		{
			holder=(ViewHolderChild) convertView.getTag();
		}
		
		if(!flag)
		{
			holder.layout_cart_item_editnum.setVisibility(View.GONE);
			holder.btn_cart_item_del.setVisibility(View.GONE);
			holder.tv_cart_item_name.setVisibility(View.VISIBLE);
			holder.tv_cart_item_price.setVisibility(View.VISIBLE);
		}else
		{
			holder.layout_cart_item_editnum.setVisibility(View.VISIBLE);
			holder.btn_cart_item_del.setVisibility(View.VISIBLE);
			holder.tv_cart_item_name.setVisibility(View.GONE);
			holder.tv_cart_item_price.setVisibility(View.GONE);
		}
		
		
		
		final JSONObject json_e=cartInfo.get(groupPosition);
		
		try {
		
		final JSONArray arry=json_e.getJSONArray("goods");
			
		
		final JSONObject json_w=arry.getJSONObject(childPosition);
		
			imageLoader.display(holder.imv_cart_item,json_w.getString("goods_image"));
		
			final String goods_id=json_w.getString("goods_id");
			final String goods_num=json_w.getString("quantity");
			holder.tv_cart_item_format.setText(json_w.getString("specification"));
			holder.tv_cart_item_num.setText("x"+goods_num);	
			holder.tv_cart_item_edit_num.setText(goods_num);	
			holder.tv_cart_item_name.setText(json_w.getString("goods_name"));
			holder.tv_cart_item_price.setText(json_w.getString("price"));
			
			holder.imv_cart_item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					Intent intent=new Intent(mContext,ItemDetailActivity.class);
					intent.putExtra("goods_id",goods_id);
					mContext.startActivity(intent);
					
				}
			});
			
			holder.btn_cart_item_num_add.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					try {
						int num=Integer.parseInt(goods_num);
						Message msg=new Message();
						msg.what=0x0011;
						msg.obj=json_w.getString("spec_id");
						msg.arg1=num+1;
						mHandler.handleMessage(msg);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			//holder.tv_cart_item_edit_num.
			holder.tv_cart_item_edit_num.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					
					final Dialog dlg= new Dialog(mContext, R.style.basedialog);
					
					
					LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.cart_change_cartnum, null);
					TextView tv_reduce=(TextView) layout.findViewById(R.id.cart_change_cartnum_tv_reduce);
					TextView tv_add=(TextView) layout.findViewById(R.id.cart_change_cartnum_tv_add);
					
					final EditText edit_num=(EditText) layout.findViewById(R.id.cart_change_cartnum_edit_num);
					Button btn_cancel=(Button) layout.findViewById(R.id.cart_change_cartnum_btn_cancle);
					Button btn_ok=(Button) layout.findViewById(R.id.cart_change_cartnum_btn_ok);
					
					btn_cancel.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							
							dlg.cancel();
						}
					});
					btn_ok.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							dlg.dismiss();
							int num=0;
							try{
								
								Message msg=new Message();
								
								String numStr=edit_num.getText().toString();
								num=Integer.parseInt(numStr);
								msg.what=0x0011;
								msg.obj=json_w.getString("spec_id");
								msg.arg1=num;
								mHandler.handleMessage(msg);
							
							}catch(NumberFormatException e)
							{
								
							} catch (JSONException e) {
								
								e.printStackTrace();
							}
							
						}
					});
					
					dlg.setContentView(layout);
					dlg.show();
					
					
				}
			});
			holder.btn_cart_item_num_del.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					int num=Integer.parseInt(goods_num);
					if(num>1)
					{
						try {
							
							Message msg=new Message();
							msg.what=0x0011;
							msg.obj=json_w.getString("spec_id");
							msg.arg1=num-1;
							mHandler.handleMessage(msg);
							//json_w.put("quantity",(num-1)+"");
							
						} catch (JSONException e) {
						
							e.printStackTrace();
						}
						
						
						
					}else
					{
						Toast.makeText(mContext,"数量不能少于一件",Toast.LENGTH_SHORT).show();
					}
					
				}
			});
			
			
			holder.btn_cart_item_del.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					try {
					Message msg=new Message();
					msg.what=0x0010;
					
						msg.obj=json_w.getString("rec_id");
						msg.arg1=groupPosition;
						msg.arg2=childPosition;
					
						mHandler.handleMessage(msg);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
			});
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return convertView;
	}

	
	 class ViewHolderChild
	{
		TextView tv_cart_item_name;
		TextView tv_cart_item_num;
		TextView tv_cart_item_price;
		TextView tv_cart_item_edit_num;
		
		TextView tv_cart_item_format;
		ImageView btn_cart_item_del;
		LinearLayout layout_cart_item_editnum;
		
		//CheckBox checkbox_cart_item;
		ImageView btn_cart_item_num_add;
		ImageView btn_cart_item_num_del;
		ImageView imv_cart_item;
		
		
	}
	 class ViewHolderParent
	{
		TextView tv_cart_store_name;
		CheckBox checkbox_cart_store;
		
		
	}
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		
		return true;
	}

	
}
