package com.gyh.digou.wode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gyh.digou.Data;
import com.gyh.digou.MainActivity;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.bean.LoginData;
import com.gyh.digou.bean.LoginInfo;
import com.gyh.digou.gouwuche.GouWuCheFragement;
import com.gyh.digou.util.NetworkUtil;
import com.gyh.digou.util.Tools;
import com.gyh.digou.util.UploadUtil;
import com.gyh.digou.wode.maijia.ZhangHuGuanliAcitivity;
import com.gyh.digou.wode.maijia.Shenqingshanjia;
import com.gyh.digou.wode.maijia.WodeGouLiangActivity;
import com.gyh.digou.wode.maijia.Wodeshoucang;
import com.gyh.digou.wode.maijia.Wodexiaoxi;
import com.gyh.digou.wode.maijia.Yijianfankui;
import com.gyh.digou.wode.shangjia.ShangjiaShouyeActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;




public class WoDeFragement extends Fragment{
	
	
	Intent dataIntent;
	public ImageView imv_headImage;
	
	MainActivity activity;
	Context mContext;
	
	
	TextView tv_username;
	TextView tv_level;
	TextView tv_dian;
	FinalBitmap imageLoader;
	PullToRefreshScrollView scrollView;
	
	public static WoDeFragement wf;
	public static WoDeFragement getInstance()
	{
		if(wf==null)
		{
			wf=new WoDeFragement();
		}
		return wf;
	}
	
	
	/*static Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			getImageToView((Intent)msg.obj);
		}
	};*/
	//Handler hand
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		mContext=activity=(MainActivity) getActivity();
		imageLoader=FinalBitmap.create(mContext);
		
		//activity.setHandler(handler);
	}

	
	
	
	@Override
	public void onResume() {
		
		super.onResume();
		mContext=activity=(MainActivity) getActivity();
		/*activity.setHandler(new Handler(){

			@Override
			public void handleMessage(Message msg) {
				//getImageToView((Intent)msg.obj);
			}
			
		});*/
	}



	@Override
	public void onDestroy() {
	
		super.onDestroy();
	}

	
	boolean is_refresh=false;
	
	
	//todo ...implements onclick  not new onclick
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		
		
		View wodeFragmentView = inflater.inflate(R.layout.wode_fragment, container, false);
		
		scrollView=(PullToRefreshScrollView) wodeFragmentView.findViewById(R.id.wode_fragment_srollview);
		scrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				
				is_refresh=true;
				refreshWodeInfo();
				
			}
		});
		RelativeLayout layout_upload_image=(RelativeLayout) wodeFragmentView.findViewById(R.id.wode_layout_upload_image);
		imv_headImage=(ImageView) wodeFragmentView.findViewById(R.id.wode_fragment_imv_image);
		tv_username=(TextView) wodeFragmentView.findViewById(R.id.wode_fragment_username);
		
		tv_level=(TextView) wodeFragmentView.findViewById(R.id.wode_fragment_level);
		TableRow	shenqingchengweishangjia=(TableRow) wodeFragmentView.findViewById(R.id.shenqingchengweishangjia);
		shenqingchengweishangjia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(mContext, Shenqingshanjia.class);
				startActivity(intent);
				
			}
		});
		tv_dian=(TextView) wodeFragmentView.findViewById(R.id.wode_fragment_shuangdian);
		layout_upload_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				activity.showGetImageDialog();
				
			}
		});
		TableRow dingDanChaXun = (TableRow)wodeFragmentView.findViewById(R.id.dingdanchaxun);
		dingDanChaXun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, DingDanGuanLiActivity.class);
				startActivity(intent);
			}
		});
		if(!flag_getdata)
		{
			refreshData();
		}else
		{
			
			try {
				tv_dian.setText(json_data.getString("point"));
				//tv_level.setText(json_data.getString("ugrade"));
				tv_level.setText(json_data.getString("ugrade"));
				tv_username.setText(json_data.getString("user_name"));
				imageLoader.display(imv_headImage,json_data.getString("portrait"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
			TableRow tabr;
			TableRow wodexiaoxi;
			TableRow zhanghuguanli;
			TableRow yijian;
			TableRow tuichu;
			TableRow wodeshoucang;
			wodeshoucang=(TableRow) wodeFragmentView.findViewById(R.id.wodeshoucang);
			
			
			
			
		
			
			
			
			wodeshoucang.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(mContext,Wodeshoucang.class);
					startActivity(intent);
				}
			});
			
			
			
			
			
			
			zhanghuguanli=(TableRow) wodeFragmentView.findViewById(R.id.zhanghuguanli);
			
			zhanghuguanli.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent=new Intent(mContext, ZhangHuGuanliAcitivity.class);
					startActivity(intent);
					
				}
			});
			
		
			
			tuichu=(TableRow) wodeFragmentView.findViewById(R.id.zhuxiao);
			tuichu.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				        AlertDialog.Builder builder = new Builder(mContext);  
				        builder.setMessage("是否注销?");  
				        builder.setTitle("提示");  
				        builder.setPositiveButton("确认",  
				        new android.content.DialogInterface.OnClickListener() {  
				            @Override  
				            public void onClick(DialogInterface dialog, int which) {  
				            
				            	
				            	
				            	 File userInfo=  mContext.getFileStreamPath("login"); 	
				            	 userInfo.delete();
				            	 Data.info=null;
				        /*    	//MyApp app=get MyApp();	
 			          MyApp  app=(MyApp)activity.getApplication();
				       File name= app.getFileStreamPath("login");
				       name.delete();	*/
				       
				     
				       
				    
		            }
							
				        });  
				        builder.setNegativeButton("取消",  
				        new android.content.DialogInterface.OnClickListener() {  
				            @Override  
				            public void onClick(DialogInterface dialog, int which) {  
				               
				            }  
				        });  
				        builder.create().show();  
				    }  
				
			
			});
			wodexiaoxi=(TableRow) wodeFragmentView.findViewById(R.id.wodexiaoxi);
			wodexiaoxi.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent=new Intent(mContext, Wodexiaoxi.class);
					startActivity(intent);
					
				}
			});
			
			
			tv_dian=(TextView) wodeFragmentView.findViewById(R.id.wode_fragment_shuangdian);
			ImageView moshi_button=(ImageView)wodeFragmentView.findViewById(R.id.moshi_button);
			
			moshi_button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent=new Intent();
					intent.setClass(mContext,ShangjiaShouyeActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				}
			});
			yijian=(TableRow) wodeFragmentView.findViewById(R.id.yijianfankui);
			yijian.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent=new Intent(mContext, Yijianfankui.class);
					startActivity(intent);
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
					
				}
			});
			tabr=(TableRow) wodeFragmentView.findViewById(R.id.shuangdian);
			tabr.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent=new Intent(mContext, WodeGouLiangActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
					
				}
			});
			
			
			
			
			layout_upload_image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					activity.showGetImageDialog();
					
				}
			});
			/*TableRow dingDanChaXun = (TableRow)wodeFragmentView.findViewById(R.id.dingdanchaxun);
			dingDanChaXun.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(mContext, DingDanGuanLiActivity.class);
					startActivity(intent);
				}
			});
			
			if(!flag_getdata)
			{
				refreshData();
			}else
			{
				
				try {
					tv_dian.setText(json_data.getString("point"));
					tv_level.setText(json_data.getString("ugrade"));
					tv_username.setText(json_data.getString("user_name"));
					imageLoader.display(imv_headImage,json_data.getString("portrait"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			
			
			return wodeFragmentView;
		}
	protected void overridePendingTransition(int pushLeftIn, int pushLeftOut) {
		// TODO Auto-generated method stub
		
	}

	protected void refreshWodeInfo() {
		refreshData();
		
	}
	boolean flag_getdata=false;
	JSONObject json_data;
	public void refreshData()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run() {
				
				List<NameValuePair> params2 = new ArrayList<NameValuePair>();
				
				
				/*MyApp myApp=(MyApp) activity.getApplication();
				String token=myApp.getInfo().getData().getToken();*/
				
				
				params2.add(new BasicNameValuePair("token",Data.info.getData().getToken()));
				String result=NetworkUtil.post(Tools.getBaseUrl()+"?app=member&act=api_user_info",params2);
				System.out.println(result);
				try {
					JSONObject json_result=new JSONObject(result);
					json_data=json_result.getJSONObject("data");
					flag_getdata=true;
					
					activity.runOnUiThread(new Runnable(){

						@Override
						public void run() {
							if(is_refresh)
							{
								//scrollView.setMode(Mode.DISABLED);
								scrollView.onRefreshComplete();
								is_refresh=false;
							}
							try {
								tv_dian.setText(json_data.getString("point"));
								tv_level.setText(json_data.getString("ugrade"));
								tv_username.setText(json_data.getString("user_name"));
								imageLoader.display(imv_headImage,json_data.getString("portrait"));
							} catch (JSONException e) {
								e.printStackTrace();
							}
							
							
						}
						
					});
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		}).start();
		
		
	}
	
	

	private  void  uploadPic() {
		
		  new Thread(new Runnable(){

				@Override
				public void run() {
		
				 final Map<String, String> params = new HashMap<String, String>();
				/* MyApp myApp=(MyApp) activity.getApplication();
				 String token=myApp.getInfo().getData().getToken();*/
		         params.put("token",Data.info.getData().getToken());
		      
		         final Map<String, File> files = new HashMap<String, File>();
				 try {
			        	
			        	
						final String request = UploadUtil.post(Tools.getBaseUrl()+"?app=apply&act=api_upload_image", params, files);
						//System.out.println
						
						activity.runOnUiThread(new Runnable()
						{

							@Override
							public void run() {
								Toast.makeText(mContext,request, Toast.LENGTH_SHORT).show();
								tv_username.setText(request);
							}
							
						});
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
        	 
         }).start();
       
		
		
		/*AjaxParams params=new AjaxParams();
		
		MyApp myApp=(MyApp) activity.getApplication();
		String token=myApp.getInfo().getData().getToken();
		params.put("token",token);
		try {
			
		params.put("image",activity.getImageFile());
		
		
		FinalHttp fh=new FinalHttp();
		fh.post(Tools.getBaseUrl()+"?app=apply&act=api_upload_image",new AjaxCallBack<String>()
				{

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						
					}
					@Override
					public void onSuccess(String t) {
						Toast.makeText(getActivity(),t, Toast.LENGTH_SHORT).show();
					}
			
				});
		} catch (FileNotFoundException e) {
			Toast.makeText(getActivity(),"上传头像失败",Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}*/
	}
}
