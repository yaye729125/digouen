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

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.bean.Goods;
import com.gyh.digou.bean.IImages;
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
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView.ScaleType;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @see ShangjiaEditCommerActivity
 * @author Administrator
 *
 */
public class TianJiaShangPinActivity extends Activity {

	ImageView button1;
	
	
	LinearLayout layout;
	
	
	
	/*LinearLayout layout2;
	LinearLayout layout3;
	LinearLayout layout4;*/
	RadioButton tianjia_shangping;
	Button fenlei_button;
	RadioButton tianjia_shuxing;
	int a = 0;
	String b;//if_show
	
	String c;//recommend
	
	String dayta;//cate_name
	String cate_id = null;
	EditText tianjia_title;
	EditText tianjia_miaoshu;
	EditText tianjia_pingpai;
	EditText tianjia_tiaoxingma;
	EditText tianjia_guigeming1;
	EditText tianjia_guigeming2;

	CheckBox shangjia_check;
	CheckBox tuijian_check;
	//String id1 = "";
	// LayoutInflater inflater;
	String[] ss = new String[] { "相册", "相机", "取消" };
	Bitmap myBitmap;
	private byte[] mContent;
	ListView list;
	TianjiaListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tianjiashangpin_en);
		list=(ListView) findViewById(R.id.tianjiashangpin_list);
		inflater=getLayoutInflater();
		tianjia_title = (EditText) findViewById(R.id.tianjia_title);
		tianjia_miaoshu = (EditText) findViewById(R.id.tianjia_miaoshu);
		tianjia_pingpai = (EditText) findViewById(R.id.tianjia_pingpai);
		tianjia_tiaoxingma = (EditText) findViewById(R.id.tianjia_tiaoxingma);
		tianjia_guigeming1 = (EditText) findViewById(R.id.tianjia_guigeming1);
		tianjia_guigeming2 = (EditText) findViewById(R.id.tianjia_guigeming2);

		shangjia_check = (CheckBox) findViewById(R.id.shangjia_check);
		tuijian_check = (CheckBox) findViewById(R.id.tuijian_check);

		shangjia_check
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {

						if (shangjia_check.isChecked()) {
							b ="1";
						} else {
							b = "0";
						}

						
					}
				});

		tuijian_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

				if (tuijian_check.isChecked()) {
					c = "1";
				} else {
					c = "0";
				}

				
			}
		});
		
		adapter=new TianjiaListAdapter();
		list.setAdapter(adapter);
		
		
		layout = (LinearLayout) findViewById(R.id.photo_image);
		
		fenlei_button = (Button) findViewById(R.id.fenlei_button);

		button1 = (ImageView) findViewById(R.id.tupian_button);

		fenlei_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(TianJiaShangPinActivity.this,
						Xuanzefenlei.class);
				startActivityForResult(intent, 3);

			}
		});

		button1.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				AlertDialog.Builder builder = new Builder(
						TianJiaShangPinActivity.this);
				builder.setTitle("选择图片");
				//
				View log = TianJiaShangPinActivity.this.getLayoutInflater()
						.inflate(R.layout.de_02, null);
				builder.setView(log);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				ListView vi = (ListView) log.findViewById(R.id.de_01_listview);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						TianJiaShangPinActivity.this,
						android.R.layout.simple_list_item_1, ss);
				vi.setAdapter(adapter);
				vi.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (arg2 == 0) {
							Intent intent = new Intent();
							intent.setType("image/*");

							intent.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intent, 1);
							alertDialog.dismiss();

						}
						if (arg2 == 1) {
							Intent intent = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							startActivityForResult(intent, 0);
							alertDialog.dismiss();

						}
						if (arg2 == 2) {
							alertDialog.dismiss();

						}

					}
				});

			}
		});

		findViewById(R.id.tianjia_shuxing).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						startActivityForResult(new Intent(
								TianJiaShangPinActivity.this,
								TianJiaShangpinAddFormatActivity.class), 0x91);

					}
				});

		findViewById(R.id.base_title_option).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						if (TextUtils.isEmpty(tianjia_guigeming1.getText()
								.toString())
								|| TextUtils.isEmpty(tianjia_tiaoxingma
										.getText().toString())
								|| TextUtils.isEmpty(tianjia_title.getText()
										.toString())) {

							Toast.makeText(TianJiaShangPinActivity.this,
									"您还未填写完所有必填选项", Toast.LENGTH_SHORT).show();

						} else {

							if (goods_file_ids.size() >= 1) {
								if (!(null == specs)) {

									if (!((null) == cate_id)) {

										EditCommit();

									} else {
										Toast.makeText(
												TianJiaShangPinActivity.this,
												"您还未选择分类", Toast.LENGTH_SHORT)
												.show();

									}

								} else {
									Toast.makeText(
											TianJiaShangPinActivity.this,
											"您至少需要添加一组属性", Toast.LENGTH_SHORT)
											.show();
								}

							} else {
								Toast.makeText(TianJiaShangPinActivity.this,
										"您至少需要添加一张商品图片", Toast.LENGTH_SHORT)
										.show();
							}

						}

					}
				});

	}

	
	List<Specs> specList=new ArrayList<Specs>();
	LayoutInflater inflater;
	class TianjiaListAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return specList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder holder=null;
			if(arg1==null)
			{
				holder=new ViewHolder();
				arg1=inflater.inflate(R.layout.tianjiashangpin_child_item_en, null);
				holder.dijia=(TextView) arg1.findViewById(R.id.tianjiashangpin_child_item_en_edit_mini_price);
				holder.guigezhi1=(TextView) arg1.findViewById(R.id.tianjiashangpin_child_item_en_edit_spec1);
				holder.guigezhi2=(TextView) arg1.findViewById(R.id.tianjiashangpin_child_item_en_edit_spec2);
				holder.kucun=(TextView) arg1.findViewById(R.id.tianjiashangpin_child_item_en_edit_stock);
				holder.lingshoujia=(TextView) arg1.findViewById(R.id.tianjiashangpin_child_item_en_edit_price);
				holder.pifajia=(TextView) arg1.findViewById(R.id.tianjiashangpin_child_item_en_edit_mk_price);
				holder.tv=(TextView) arg1.findViewById(R.id.tianjiashangpin_parent_item_en_tv);
				arg1.setTag(holder);
			}else
			{
				holder=(ViewHolder) arg1.getTag();
			}
			
			
			
			Specs spec=specList.get(arg0);
			holder.tv.setText("第"+(arg0+1)+"组属性");
			
			holder.dijia.setText(spec.getMinimum_price());
			holder.guigezhi1.setText(spec.getSpec_1());
			holder.guigezhi2.setText(spec.getSpec_2());
			//holder.kucun
			holder.kucun.setText(spec.getStock());
			holder.lingshoujia.setText(spec.getPrice());
			holder.pifajia.setText(spec.getMk_price());
			
			return arg1;
		}
		
		
		
		
	}
	
	static class ViewHolder
	{
		
		TextView guigezhi1;
		TextView guigezhi2;
		TextView lingshoujia;
		TextView dijia;
		TextView pifajia;
		TextView kucun;
	    TextView tv;
		
		
	}
	
	
	
	
	public void EditCommit() {

		String tianjia_titlec = tianjia_title.getText().toString();
		String tianjia_miaoshuc = tianjia_miaoshu.getText().toString();
		String tianjia_pingpaic = tianjia_pingpai.getText().toString();

		String tianjia_tiaoxingmac = tianjia_tiaoxingma.getText().toString();

		String tianjia_guigeming1c = tianjia_guigeming1.getText().toString();

		String tianjia_guigeming2c = tianjia_guigeming2.getText().toString();

		

		final List<NameValuePair> param_list = new ArrayList<NameValuePair>();

		param_list.add(new BasicNameValuePair("token", Data.getInfo().getData().getToken()));
		param_list.add(new BasicNameValuePair("sku", tianjia_tiaoxingmac));
		param_list.add(new BasicNameValuePair("brand", tianjia_pingpaic));
		param_list.add(new BasicNameValuePair("cate_id", cate_id));
		param_list.add(new BasicNameValuePair("cate_name", dayta));
		param_list.add(new BasicNameValuePair("description", tianjia_miaoshuc));
		param_list.add(new BasicNameValuePair("goods_name", tianjia_titlec));
		param_list.add(new BasicNameValuePair("if_show", b + ""));
		param_list.add(new BasicNameValuePair("recommended", c + ""));
		param_list.add(new BasicNameValuePair("spec_name_1",
				tianjia_guigeming1c));
		param_list.add(new BasicNameValuePair("spec_name_2",
				tianjia_guigeming2c));
		
		param_list.add(new BasicNameValuePair("minimum_price", specs.getMinimum_price()
				.split(",")[0]));
		param_list.add(new BasicNameValuePair("mk_price", specs.getMk_price()
				.split(",")[0]));

		param_list.add(new BasicNameValuePair("price",specs.getPrice().split(
				",")[0]));

		param_list.add(new BasicNameValuePair("stock", specs.getStock().split(
				",")[0]));
		param_list.add(new BasicNameValuePair("goods_file_id[]", getGoodsFileId()));

		
		param_list.add(new BasicNameValuePair("stock[]", specs.getStock()));

		param_list.add(new BasicNameValuePair("minimum_price[]", specs.getMinimum_price()));
		param_list.add(new BasicNameValuePair("price[]", specs.getStock()));
		param_list
				.add(new BasicNameValuePair("mk_price[]",  specs.getMk_price()));
		param_list.add(new BasicNameValuePair("spec_1[]",specs.getSpec_1()));
		param_list.add(new BasicNameValuePair("spec_2[]", specs.getSpec_2()));

		for (NameValuePair param : param_list) {

			System.out.println(param.toString());

		}
		showDialog();
		new Thread(new Runnable() {

			@Override
			public void run() {
				final String result = NetworkUtil.post(Tools.getBaseUrl()
						+ "?app=my_goods&act=api_add", param_list);

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						hideDialog();
						try {
							JSONObject json_result = new JSONObject(result);

							Toast.makeText(TianJiaShangPinActivity.this,
									json_result.getString("ErrMsg"),
									Toast.LENGTH_SHORT).show();
							TianJiaShangPinActivity.this.finish();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

			}

		}).start();

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
	
	
	private String getGoodsFileId() {
	StringBuilder sb=new StringBuilder();
	for(String s:goods_file_ids)
	{
		sb.append(s+",");
	}
	return sb.toString();
}
	
	List<String> goods_file_ids=new ArrayList<String>();
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	// AddFormatBean addFb=null;

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
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;

	}

	Specs specs=new Specs();
	//AddFormatBean addFb;

	
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != RESULT_CANCELED) {

			if (requestCode == 0x91) {

				specs = (Specs) data.getSerializableExtra("format");
				getSpecsBySpec();
				// Log.d("onActivityResult", addFb.toString());

			}

			/**
			 * 如果不拍照 或者不选择图片返回 不执行任何操作
			 */

			if (requestCode == 3) {
				dayta = data.getStringExtra("cate_name");
				cate_id = data.getStringExtra("cate_id");
				Toast.makeText(TianJiaShangPinActivity.this, dayta + cate_id, 0)
						.show();

				fenlei_button.setText(dayta);
			}

			if (data != null) {
				/**
				 * 因为两种方式都用到了startActivityForResult方法，
				 * 这个方法执行完后都会执行onActivityResult方法 ， 所以为了区别到底选择了那个方式获取图片要进行判断
				 * ，这里的requestCode跟startActivityForResult里面第二个参数对应 1== 相册 0 ==相机
				 */
				if (requestCode == 1) {

					handleThePicture(data);

				} else if (requestCode == 0) {

					handleThePicture(data);

				}
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		refreshList();
		
	}
	private void refreshList() {
		adapter.notifyDataSetChanged();
		Tools.setListViewHeightBasedOnChildren(list);
		
	}
	private void getSpecsBySpec() {
	
		String[] stocks=specs.getStock().split(",");
		String[] mini_price=specs.getMinimum_price().split(",");
		String[] mk_price=specs.getMk_price().split(",");
		String[] price=specs.getPrice().split(",");
		String[] spec1=specs.getSpec_1().split(",");
		String[] spec2=specs.getSpec_2().split(",");
		
		for(int i=0;i<stocks.length;i++)
		{
			
			
			Specs spec=new Specs();
			
			spec.setStock(stocks[i]);
			spec.setMinimum_price(mini_price[i]);
			spec.setMk_price(mk_price[i]);
			spec.setPrice(price[i]);
			//if(null==spec1)
			if(!(null==spec1))
			{
				if(i<spec1.length)
				{
					if(!(null==spec1[i]))
					{
						spec.setSpec_1(spec1[i]);
					}
				}
			}
			
			if(!(null==spec2))
			{
				if(i<spec2.length)
				{
					if(!(null==spec2[i]))
					{
						spec.setSpec_2(spec2[i]);
					}
				}
			}
			specList.add(spec);
			
			
		}
		
		
	}


	// public void get
	private static String app_upload_comm_image = "tmp_upload_comm.png";

	private void uploadImage(File imageFile) {

		showDialog();
		AjaxParams params = new AjaxParams();

		params.put("token",Data.info.getData().getToken());
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
						Toast.makeText(TianJiaShangPinActivity.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						System.out.println("---80-------" + t
								+ "-----80-----80------------");
						hideDialog();
						try {
							JSONObject jsonObject = new JSONObject(t);

							Toast.makeText(TianJiaShangPinActivity.this,
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

	protected void addImageViews(final String file_id) {

		final RelativeLayout layout_rela = new RelativeLayout(this);

		RelativeLayout.LayoutParams rela_param = new RelativeLayout.LayoutParams(
				Tools.dip2px(this, 80), Tools.dip2px(this, 80));
		// rela_param.
		layout_rela.setLayoutParams(rela_param);

		ImageView imageView = new ImageView(this);

		imageView.setImageBitmap(headImage);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		imageView.setPadding(5, 5, 5, 5);

		layout_rela.addView(imageView);
		ImageView imv_del = new ImageView(this);
		imv_del.setImageResource(R.drawable.cart_del);

		imv_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				layout.removeView(layout_rela);
				goods_file_ids.remove(file_id);
			}
		});

		RelativeLayout.LayoutParams imv_del_layout_param = new RelativeLayout.LayoutParams(
				Tools.dip2px(this, 20), Tools.dip2px(this, 20));

		imv_del_layout_param.addRule(RelativeLayout.ALIGN_PARENT_TOP,
				RelativeLayout.TRUE);
		imv_del_layout_param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
				RelativeLayout.TRUE);
		layout_rela.addView(imv_del, imv_del_layout_param);
		// imv_
		layout.addView(layout_rela);

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

		/*
		 * try {
		 * 
		 * 
		 * // 获得图片的uri Uri originalUri = data.getData(); ContentResolver
		 * resolver = getContentResolver(); // 将图片内容解析成字节数组 mContent =
		 * readStream(resolver.openInputStream(Uri
		 * .parse(originalUri.toString()))); // 将字节数组转换为ImageView可调用的Bitmap对象
		 * myBitmap = getPicFromBytes(mContent, null); //把得到的图片绑定在控件上显示
		 * 
		 * 
		 * ImageView imageView=new ImageView(this);
		 * 
		 * 
		 * // imageView.setLayoutParams(new LayoutParams(80, 80));
		 * imageView.setScaleType(ScaleType.FIT_CENTER);
		 * imageView.setImageBitmap(myBitmap); layout.addView(imageView);
		 * 
		 * 
		 * 
		 * String [] proj={MediaStore.Images.Media.DATA}; String imagePath="";
		 * 
		 * @SuppressWarnings("deprecation") Cursor
		 * cursor=managedQuery(originalUri, proj, null,null,null);
		 * if(cursor!=null) { int column_index =
		 * cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); if(
		 * cursor.getCount()>0 && cursor.moveToFirst() ) { imagePath =
		 * cursor.getString(column_index); } }
		 */

	}

}