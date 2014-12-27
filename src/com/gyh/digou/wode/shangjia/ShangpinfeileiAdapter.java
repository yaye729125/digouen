package com.gyh.digou.wode.shangjia;

import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyh.digou.R;
import com.gyh.digou.fenlei.LeftViewAdapter.ViewHolder;

public class ShangpinfeileiAdapter extends BaseAdapter {

	
	private Context mContext;
	private List<JSONObject> list;
	LayoutInflater inflater;
	
	

	public ShangpinfeileiAdapter(Context mContext, List<JSONObject> list) {

		//System.out.println(list.get(0).toString());
		this.mContext = mContext;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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

		try {
			


				arg1 = inflater.inflate(R.layout.shangpinxuanzefenlei_style, null);
			TextView	textView1 = (TextView) arg1
						.findViewById(R.id.text_fenlei_1);

			ImageView imageView=(ImageView) arg1.findViewById(R.id.image_1);
			


			textView1.setText(list.get(arg0).getString("cate_name"));
			       //   System.out.println(list.get(arg0).getString("funds")+"-0-0-0-0-0----0");
               
			     String im=list.get(arg0).getString("image");
			         
			     System.out.println(im+"-555555555555555555555");
			          FinalBitmap bitmap=FinalBitmap.create(mContext);
						
						bitmap.display(imageView, im); 
						

			

		} catch (JSONException e) {

			e.printStackTrace();
		}

		return arg1;

	}


}
