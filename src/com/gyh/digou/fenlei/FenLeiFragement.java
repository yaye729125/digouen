package com.gyh.digou.fenlei;


import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gyh.digou.MainActivity;
import com.gyh.digou.R;
import com.gyh.digou.bean.GoodsCategory;
import com.gyh.digou.bean.GoodsCategoryInfo;
import com.gyh.digou.mytoast.CustomToast;


public class FenLeiFragement extends Fragment{

	ListView listViewItem ;
	ListView listViewXiangXi;
	
	//List<GoodsCategory> list;
	GoodsCategoryInfo info;
	ChildrenListViewBaseAdapter childrenListViewBaseAdapter;
	RadioGroup group;
	int cur_position;
	public static  FenLeiFragement ff;
	public static  FenLeiFragement getInstance()
	{
		if(ff==null)
		{
			ff=new FenLeiFragement();
		}
		return ff;
	}
	ListView leftList,rightList;
	MainActivity mainActivity;
	Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		mContext=mainActivity=(MainActivity) getActivity();
		
	}
	AnimationSildingLayout layout;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fenlei_fragment_en, container, false);
		
		leftList = (ListView) view.findViewById(R.id.list);
        rightList = (ListView) view.findViewById(R.id.list1);
        layout = (AnimationSildingLayout) view.findViewById(R.id.main_slayout);
        layout.initLayout(leftList, rightList);
        layout.setOnSildingFinishListener(new AnimationSildingLayout.OnSildingFinishListener() {
            @Override
            public void onSildingFinish() {
                //todo 处理rightview 移出界面的逻辑
            	leftList.setDividerHeight(1);
            }
        });
        aa = new LeftViewAdapter(mContext);
        leftList.setAdapter(aa);
        bb = new RightListAdapter(mContext);
        rightList.setAdapter(bb);
        leftList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                foodposition = position;
                aa.setSelectedPosition(position);
                aa.notifyDataSetInvalidated();
                bb.setData(info, foodposition);

                leftList.setDividerHeight(0);
               
                layout.startSildingInAnimation(position);
            }
        });
		
        
        rightList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				
				GoodsCategory category=((RightListAdapter)rightList.getAdapter()).getAdapterData().get(arg2);
				
				
				intent.setClass(getActivity(), FenLeiChooseResultActivity.class);
				intent.putExtra("fenlei_search_cate_id",category.getCate_id());
				 
				startActivity(intent);
			}
		});
       
      
        
        if(info==null)
        {
        	goodsCategory();
        }else
        {
        	aa.setData(info);
        }
		
		
		return view;
	}
	int foodposition;
	RightListAdapter bb;
	LeftViewAdapter aa;
	
	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}
	@Override
	public void onDestroyView() {
		
		super.onDestroyView();
	}





	@Override
	public void onPause() {
		
		super.onPause();
		
		mainActivity.hideDialog();
	}





	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}





	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}





	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}





	//FenLeiListItemBaseAdapter parentAdapter;	
	public void goodsCategory(){
		mainActivity.showDialog();
		AjaxParams params=new AjaxParams();
		FinalHttp fh=new FinalHttp();
		fh.post("http://www.cddego.com/api.php?app=category&act=api_goods", params,new AjaxCallBack<String>() {
			
			@Override
			public void onLoading(long count, long current) {
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(String t) {
				Gson gson=new Gson();
				
				try
				{
				    info = gson.fromJson(t, GoodsCategoryInfo.class);
					aa.setData(info);
				}catch(JsonSyntaxException e)
				{
						
				}
				
				mainActivity.hideDialog();
				
				
			}
		});
	}
}
