package com.gyh.digou.wode.shangjia;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.bean.Goods;
import com.gyh.digou.bean.IImages;
import com.gyh.digou.bean.ItemDetailInfo;
import com.gyh.digou.bean.Specs;
import com.gyh.digou.progerss.CustomProgressDialog;
import com.gyh.digou.util.NetworkUtil;
import com.gyh.digou.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView.ScaleType;

public class ShangjiaEditCommerActivity extends Activity {
	
	
	
	
	//商品id由商品管理页面传递过来,作为全局变量
	String goods_id="";
	
	
	ImageView button1;
	
	//添加商品图片根布局
	LinearLayout layout;
	/*LinearLayout layout2;
	LinearLayout layout3;
	LinearLayout layout4;*/
	RadioButton tianjia_shangping;
	Button fenlei_button;
	RadioButton tianjia_shuxing;
     int a=0;
     String b;
     String c;
     String dayta;
     String cate_id;
     
     
     
     EditText tianjia_title;
     EditText tianjia_miaoshu;
     EditText tianjia_pingpai;
     EditText tianjia_tiaoxingma;
     EditText tianjia_guigeming1;
     EditText tianjia_guigeming2;
     
     
     CheckBox shangjia_check;
     CheckBox tuijian_check;
    // String id1="";
	//LayoutInflater inflater;
     String[] ss=new String[]{"相册","相机","取消"};	
	 Bitmap myBitmap;
     private byte[] mContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);//why?
		
		Intent intent=getIntent();
		
		goods_id=intent.getStringExtra("goods_id");
		
		setContentView(R.layout.tianjiashangpin_en);
		
		
		findViewById(R.id.base_title_option).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				
				if (TextUtils.isEmpty(tianjia_guigeming1.getText()
						.toString())
						|| TextUtils.isEmpty(tianjia_tiaoxingma
								.getText().toString())
						|| TextUtils.isEmpty(tianjia_title.getText()
								.toString())) {

					Toast.makeText(ShangjiaEditCommerActivity.this,
							"您还未填写完所有必填选项", Toast.LENGTH_SHORT).show();

				} else {

					if (goods_file_ids.size() >= 1) {
						editCommer2();
						
					}else
					{
						Toast.makeText(ShangjiaEditCommerActivity.this,
								"您至少需要添加一张商品图片", Toast.LENGTH_SHORT)
								.show();
					}
				}
				
	
			}
		});
		
		
		
		
		
		
		
		tianjia_title=(EditText) findViewById(R.id.tianjia_title);
		tianjia_miaoshu=(EditText) findViewById(R.id.tianjia_miaoshu);
		tianjia_pingpai=(EditText) findViewById(R.id.tianjia_pingpai);
		tianjia_tiaoxingma=(EditText) findViewById(R.id.tianjia_tiaoxingma);
		tianjia_guigeming1=(EditText) findViewById(R.id.tianjia_guigeming1);
		tianjia_guigeming2=(EditText) findViewById(R.id.tianjia_guigeming2);
		
		
		
		
		shangjia_check=(CheckBox) findViewById(R.id.shangjia_check);
		tuijian_check=(CheckBox) findViewById(R.id.tuijian_check);
		
		shangjia_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {		
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				
				if(shangjia_check.isChecked()){
					b="1";
				} 
				else
				{
					b="0";
				}
				
				
			}
		});
		
		tuijian_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				
				if(tuijian_check.isChecked()){
					c="1";
				}
				else
				{
					c="0";
				}
				
				
			}
		});
		
		layout=(LinearLayout) findViewById(R.id.photo_image);
		fenlei_button=(Button) findViewById(R.id.fenlei_button);
		tianjia_shuxing=(RadioButton) findViewById(R.id.tianjia_shuxing);
		
		
		
		 button1=(ImageView) findViewById(R.id.tupian_button);
		
		 
		 
		 fenlei_button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					
					Intent intent=new Intent(ShangjiaEditCommerActivity.this, Xuanzefenlei.class);
					startActivityForResult(intent, 3);
					
				
				}
			});
			 
			
			 
			 button1.setOnClickListener(new OnClickListener() {
				
				public void onClick(View arg0) {
					
					
					 AlertDialog.Builder builder = new Builder(ShangjiaEditCommerActivity.this);
						builder.setTitle("选择图片");
				//
							View log = ShangjiaEditCommerActivity.this.getLayoutInflater().inflate(R.layout.de_02, null);
							builder.setView(log);
							final AlertDialog alertDialog = builder.create();
							alertDialog.show();
					ListView vi=	(ListView) log.findViewById(R.id.de_01_listview);				
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(ShangjiaEditCommerActivity.this, android.R.layout.simple_list_item_1, ss);	
					vi.setAdapter(adapter);
					vi.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
						if(arg2==0){
							
							
							// Intent intent = new Intent("com.android.camera.action.CROP");
							  Intent intent = new Intent();
					            intent.setType("image/*");

					            intent.setAction(Intent.ACTION_GET_CONTENT);
					            startActivityForResult(intent, 1);
					            alertDialog.dismiss();
						
							
							
							
						}
						if(arg2==1){
							 Intent intent = new Intent(
	                                 "android.media.action.IMAGE_CAPTURE");
	                 startActivityForResult(intent, 0);
	                      alertDialog.dismiss();
							
						}
						if(arg2==2){
							alertDialog.dismiss();
							
						}
							
							
							
						}
					});	
					
					
				}
			});
		 
		findViewById(R.id.tianjia_shuxing).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(ShangjiaEditCommerActivity.this,EditShangpinAddFormatActivity.class);
				intent.putExtra("specs",goods.get_specs().toArray());
				startActivityForResult(
						
						intent,0x91);
				
				
			}
		});
		
		
		imageLoader=FinalBitmap.create(this);
		
		editCommer();
		
		
		/*edit_name=(EditText) findViewById(R.id.shangjia_edit_comm_name);
		
		EditText edit_price=(EditText) findViewById(R.id.shangjia_edit_comm_price);
		EditText edit_des=(EditText) findViewById(R.id.shangjia_edit_comm_des);
		findViewById(R.id.shangjia_edit_comm_del).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				delCommer();
			}
		});*/
	}
	
	
	
	FinalBitmap imageLoader;
	
	
	
	//AddFormatBean addFb=null;
	Specs specs=null;
	
	
	
	
	public static Bitmap getPicFromBytes(byte[] bytes,
            BitmapFactory.Options opts) {
		
		
    if (bytes != null)
            if (opts != null)
                    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                                    opts);
            else
                    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    return null;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		
		
		    byte[] buffer = new byte[1024];
		    int len = -1;
		    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		    while ((len = inStream.read(buffer)) != -1) 
		    {
           
		    	outStream.write(buffer, 0, len);
		    }
		    byte[] data = outStream.toByteArray();
		    outStream.close();
		    inStream.close();
   
		    
		    return data;

    
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		
		if(resultCode!=RESULT_CANCELED)
		{
			
			if(requestCode==0x91)
			{
				
				specs=(Specs) data.getSerializableExtra("format");
				//Log.d("onActivityResult", addFb.toString());
				
				
			}
			
		    if(requestCode==3){
		    		dayta=	 data.getStringExtra("cate_name");
		    		 cate_id=	 data.getStringExtra("cate_id");
		    		Toast.makeText(ShangjiaEditCommerActivity.this, dayta+cate_id, 0).show();
		    
		    		fenlei_button.setText(dayta);
		        }
		        
		        
		        
		        
		        

		        if (data != null) {
		        	
		        	
		        	
		        	if (requestCode == 1) {

						handleThePicture(data);

					} else if (requestCode == 0) {

						handleThePicture(data);

					}
		        	
		        }
	
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	CustomProgressDialog dialog;
	public void showDialog()
    {
    	
    	if(dialog==null)
    	{
    		dialog=CustomProgressDialog.createDialog(this, R.style.CustomProgressDialog,R.layout.mytoast);
    		dialog.setMessage("正在加载",R.id.mytoast_tv);
    		dialog.show();
    		/*dialog=new CustomProgressDialog(mContex,R.style.basedialog);
    		dialog.*/
    	}else
    	{
    		if(!dialog.isShowing())
    			dialog.show();
    	}
    	
    }
    public void hideDialog()
    {
    	if(dialog!=null)
    	{
    		dialog.dismiss();
    	}
    	
    }
	// public void get
		private static String app_upload_comm_image = "tmp_upload_comm.png";

		private void uploadImage(File imageFile) {

			showDialog();
			AjaxParams params = new AjaxParams();

			params.put("token", MyApp.info.getData().getToken());
			try {

				params.put("file", imageFile);

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// System.out.println("-=-=-=-=---------"+path);
			FinalHttp fh = new FinalHttp();
			fh.post(Tools.getBaseUrl() + "?app=comupload&act=api_uploadedfile",
					params, new AjaxCallBack<String>() {

						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							hideDialog();
							Toast.makeText(ShangjiaEditCommerActivity.this, strMsg,
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(String t) {
							System.out.println("---80-------" + t
									+ "-----80-----80------------");
							hideDialog();
							try {
								JSONObject jsonObject = new JSONObject(t);

								Toast.makeText(ShangjiaEditCommerActivity.this,
										jsonObject.getString("ErrMsg"),
										Toast.LENGTH_SHORT).show();

								JSONObject jsonData = jsonObject
										.getJSONObject("data");
								//id1 += jsonData.getString("id") + ",";
								goods_file_ids.add(jsonData.getString("id"));
								addImageViews(jsonData.getString("id"));

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					});

		}
	
	Bitmap headImage;
	
		private void handleThePicture(Intent data) {

			Bundle extras = data.getExtras();
			if (extras != null) {
				headImage = extras.getParcelable("data");

				try {
					// exception to do
					if (Tools.hasSdcard()) {
						File imageFile = Tools.makeImageDir(app_upload_comm_image);
						FileOutputStream fos;

						fos = new FileOutputStream(imageFile);

						headImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

						fos.close();
						uploadImage(imageFile);

					} else {
						if (null != headImage && !headImage.isRecycled()) {
							headImage.recycle();
							headImage = null;

						}

						Toast.makeText(this, "未找到sd卡,上传头像失败", Toast.LENGTH_SHORT)
								.show();
					}

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}

			}
		}
	
	
	
	
	
	
	
	protected void delCommer() {
		final List<NameValuePair> params=new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("token",Data.getInfo().getData().getToken()));
		
		params.add(new BasicNameValuePair("id",goods.getGoods_id()));
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				
				StringBuffer sb=new StringBuffer();
				for(NameValuePair param:params)
				{
					sb.append(param.toString()+"&");
				}
				String result=NetworkUtil.post(Tools.getBaseUrl() + "?app=my_goods&act=api_drop", params);
				System.out.println("params==="+sb.toString()+"   result==="+result);
			}
		}).start();
		
	}
	
	
	
	List<String> goods_file_ids=new ArrayList<String>();
	//EditText edit_name;
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	Goods goods;
	
	
	
	public void editCommer()
	{
		
		
		
			AjaxParams params3=new AjaxParams();
			
			params3.put("goods_id",goods_id);
			Log.d("editCommer   ", goods_id);
			//params.p
			
			FinalHttp fh3=new FinalHttp();
			fh3.post(Data.getGoodsDetailUrl(),params3,new AjaxCallBack<String>()
					{
						@Override
						public void onLoading(long count, long current) {
							super.onLoading(count, current);
						}
						@Override
						public void onSuccess(String t) {
								System.out.println(t);
							try
							{
								Gson gson=new Gson();
								ItemDetailInfo itemDetailInfo=gson.fromJson(t,ItemDetailInfo.class);
								goods=itemDetailInfo.getData().getGoods();
								
								//goods.get_
			
								//edit_name.setText(goods.getGoods_name());
								
								showText();
								initImageViews();
							}catch(JsonSyntaxException e)
							{
								e.printStackTrace();
							}
					
						}
						
					});
		
	}
	
	
	public void initImageViews()
	{
		
		
		
		for(final IImages image:goods.get_images())
		{
			
			
			final RelativeLayout layout_rela=new RelativeLayout(this);
			
			RelativeLayout.LayoutParams rela_param=new RelativeLayout.LayoutParams(Tools.dip2px(this,80), Tools.dip2px(this,80));
			//rela_param.
			layout_rela.setLayoutParams(rela_param);
			
			
			ImageView imageView=new ImageView(this);
			imageView.setScaleType(ScaleType.CENTER_CROP);
	        imageView.setPadding(5, 5, 5, 5);
	        layout_rela.addView(imageView);
	        //image.getThumbnail()
	        Log.d("init imageview", image.getImage_url());
			imageLoader.display(imageView, image.getImage_url());
			
			  ImageView imv_del=new ImageView(this);
		        imv_del.setImageResource(R.drawable.cart_del);
		        
		        
		       // imv_del.setTag(image.getFile_id());
		        imv_del.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						
						
						Log.d("1111111111111111112sasdfasd", "");
						
						System.out.println("ttsstss");
						
						goods_file_ids.remove(image.getFile_id());
						System.out.println("ttsstss==="+goods_file_ids.size());
						
						layout.removeView(layout_rela);
						
						
						
						
					}
				});
		        
		        RelativeLayout.LayoutParams imv_del_layout_param=new RelativeLayout.LayoutParams(Tools.dip2px(this,20), Tools.dip2px(this,20));
		        
		        imv_del_layout_param.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
		        imv_del_layout_param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
		        layout_rela.addView(imv_del, imv_del_layout_param);
		        //imv_
		        layout.addView(layout_rela);
		}
		
		
		
	}
	
	
	
	Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			
			switch(msg.what)
			{
			case 0x1132:
				
				Log.d("handler...", "");
				goods_file_ids.remove(msg.obj);
				break;
			}
			
		}
		
	};
	
	protected void addImageViews(String goods_file_id) {
		
		
	
		final RelativeLayout layout_rela=new RelativeLayout(this);
		
		RelativeLayout.LayoutParams rela_param=new RelativeLayout.LayoutParams(Tools.dip2px(this,80), Tools.dip2px(this,80));
		//rela_param.
		layout_rela.setLayoutParams(rela_param);
		
		
		ImageView imageView=new ImageView(this);
        
        //imageView.setImageBitmap(headImage);
        imageView.setScaleType(ScaleType.CENTER_CROP);
        imageView.setPadding(5, 5, 5, 5);
        imageView.setImageBitmap(myBitmap);
        //imageLoader.display(imageView, goods.get_images())
        layout_rela.addView(imageView);
        final ImageView imv_del=new ImageView(this);
        imv_del.setImageResource(R.drawable.cart_del);
        
        imv_del.setTag(goods_file_id);
        imv_del.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				Log.d("sasdfasd", "1111111111111111112");
				
				
				goods_file_ids.remove((String)imv_del.getTag());
				layout.removeView(layout_rela);
				
				
				
				
			}
		});
        
        RelativeLayout.LayoutParams imv_del_layout_param=new RelativeLayout.LayoutParams(Tools.dip2px(this,20), Tools.dip2px(this,20));
        
        imv_del_layout_param.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
        imv_del_layout_param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        layout_rela.addView(imv_del, imv_del_layout_param);
        //imv_
        layout.addView(layout_rela);
		
	}
	
	protected void showText() {
		
		
		
			for(IImages image:goods.get_images())
			{
				//id1+=image.getFile_id()+",";
				
				goods_file_ids.add(image.getFile_id());
				
			}
		
		
			tianjia_title.setText(goods.getGoods_name());
			tianjia_miaoshu.setText(goods.getDescription());
		    tianjia_pingpai.setText(goods.getBrand());
		    tianjia_tiaoxingma.setText(goods.getSku());
		    tianjia_guigeming1.setText(goods.getSpec_name_1());
		    tianjia_guigeming2.setText(goods.getSpec_name_2());
			fenlei_button.setText(goods.getCate_name());
			cate_id=goods.getCate_id();
			dayta=goods.getCate_name();
			
			if(goods.getIf_show().equals("1"))
			{
				shangjia_check.setChecked(true);
			}else
			{
				shangjia_check.setChecked(false);
			}
			
			if(goods.getRecommended().equals("1"))
			{
				tuijian_check.setChecked(true);
			}else
			{
				tuijian_check.setChecked(false);
			}
			
			//c=goods.getRecommended();
		
			getSpecData();
		
	}

	/*public String getToken()
	{
		
		return MyApp.info.getData().getToken();
	}*/
	
	protected void editCommer2() {
		showDialog();
		//getSpecData(goods);
		final List<NameValuePair> param_list=new ArrayList<NameValuePair>();
		
		
		String	tianjia_tiaoxingmac=tianjia_tiaoxingma.getText().toString();

		String	tianjia_guigeming1c=tianjia_guigeming1.getText().toString();

		String	tianjia_guigeming2c=tianjia_guigeming2.getText().toString();
		String tianjia_titlec=	tianjia_title.getText().toString();
		String tianjia_miaoshuc=tianjia_miaoshu.getText().toString();
		String tianjia_pingpaic=tianjia_pingpai.getText().toString();	
		
		param_list.add(new BasicNameValuePair("token",Data.getInfo().getData().getToken()));
		param_list.add(new BasicNameValuePair("id",goods_id));
		param_list.add(new BasicNameValuePair("sku", tianjia_tiaoxingmac));
		param_list.add(new BasicNameValuePair("brand", tianjia_pingpaic));
		param_list.add(new BasicNameValuePair("spec_name_1",
				tianjia_guigeming1c));
		param_list.add(new BasicNameValuePair("spec_name_2",
				tianjia_guigeming2c));
		param_list.add(new BasicNameValuePair("cate_id", cate_id));
		param_list.add(new BasicNameValuePair("cate_name",dayta));
		param_list.add(new BasicNameValuePair("description", tianjia_miaoshuc));
		param_list.add(new BasicNameValuePair("goods_name", tianjia_titlec));
		param_list.add(new BasicNameValuePair("if_show", b+""));
		param_list.add(new BasicNameValuePair("recommended", c + ""));
		param_list.add(new BasicNameValuePair("minimum_price", specs.getMinimum_price()
				.split(",")[0]));
		param_list.add(new BasicNameValuePair("mk_price", specs.getMk_price()
				.split(",")[0]));

		param_list.add(new BasicNameValuePair("price",specs.getPrice().split(
				",")[0]));

		param_list.add(new BasicNameValuePair("stock", specs.getStock().split(
				",")[0]));
		param_list.add(new BasicNameValuePair("stock[]", specs.getStock()));
		param_list.add(new BasicNameValuePair("goods_file_id[]",getFileIdString()));
		param_list.add(new BasicNameValuePair("minimum_price[]", specs.getMinimum_price()));
		param_list.add(new BasicNameValuePair("price[]", specs.getStock()));
		param_list.add(new BasicNameValuePair("mk_price[]",  specs.getMk_price()));
		param_list.add(new BasicNameValuePair("spec_1[]",specs.getSpec_1()));
		param_list.add(new BasicNameValuePair("spec_2[]", specs.getSpec_2()));
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				
				StringBuffer sb=new StringBuffer();
				for(NameValuePair param:param_list)
				{
					sb.append(param.toString()+"&");
				}
				final String result=NetworkUtil.post(Tools.getBaseUrl() + "?app=my_goods&act=api_edit",param_list);
				
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						try {
							hideDialog();
							Toast.makeText(ShangjiaEditCommerActivity.this,new JSONObject(result).getString("ErrMsg"), Toast.LENGTH_SHORT).show();
						
						
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
				System.out.println("params==="+sb.toString()+"   result==="+result);
			}
		}).start();
		
	}



	private String getFileIdString() {
		StringBuilder sb=new StringBuilder();
		
		for(String s:goods_file_ids)
		{
			
			
			sb.append(s+",");
		}
		
		return sb.toString();
	}



	String spec_1="",spec_2="",price="",mini_price="",mk_price="",stock="",sku="";
	
	
	private void getSpecData() {
		
		for(Specs specs:goods.get_specs())
		{
			spec_1+=specs.getSpec_1()+",";
			spec_2+=specs.getSpec_2()+",";
			price+=specs.getPrice()+",";
			mini_price+=specs.getMinimum_price()+",";
			mk_price+=specs.getMk_price()+",";
			stock+=specs.getStock()+",";
			sku+=specs.getSku()+",";
		}
		specs=new Specs();
		specs.setSpec_1(spec_1);
		specs.setSpec_2(spec_2);
		specs.setPrice(price);
		specs.setMinimum_price(mini_price);
		specs.setMk_price(mk_price);
		specs.setStock(stock);
		//specs.setSku(sku)
	}
	
	
	/*private String getGoodsFileId() {
		StringBuilder sb=new StringBuilder();
		for(IImages image:goods.get_images())
		{
			sb.append(image.getFile_id()+",");
		}
		return sb.toString();
	}*/
	
	
}
