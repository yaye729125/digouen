package com.gyh.digou.fenlei;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.gyh.digou.R;
import com.gyh.digou.bean.GoodsCategory;
import com.gyh.digou.bean.GoodsCategoryInfo;

public class FenLei_ShangPinXiangXiBaseExpandableListAdapter extends BaseExpandableListAdapter{
	private Context mContext;
	private GoodsCategoryInfo info;
	List<GoodsCategory> list;
	
	public FenLei_ShangPinXiangXiBaseExpandableListAdapter() {}
	LayoutInflater inflater;
	
	public FenLei_ShangPinXiangXiBaseExpandableListAdapter(Context mContext,List<GoodsCategory> list) {
		this.mContext = mContext;
		this.list=list;
		System.out.println("----------======"+list);
		inflater=LayoutInflater.from(mContext);
				
		for (GoodsCategory category:list) {
			System.out.println("------"+category.getCate_name());
			for(GoodsCategory category2:category.getChildren())
			{
				System.out.println("##############"+category2.getCate_name());
			}
		}
	}

	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		convertView=inflater.inflate(R.layout.fenlei_nianlingjieduan_expanded_childview, null);
		TextView tView=(TextView) convertView.findViewById(R.id.nianlingjieduan_expanedchild_tv);
		tView.setText(list.get(groupPosition).getChildren().get(childPosition).getCate_name());
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		List<GoodsCategory> child=list.get(groupPosition).getChildren();
		System.out.println("child==null?0:child.size()"+(child==null?0:child.size()));
		return child==null?0:child.size();
	}




	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		convertView=inflater.inflate(R.layout.fenlei_nianlingjieduan_expanded_parentview, null);
		/*TextView tView=(TextView) convertView.findViewById(R.id.nianlingjieduan_expanedparent_tv);
		tView.setText(list.get(groupPosition).getCate_name());*/
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
