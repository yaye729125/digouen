package com.gyh.digou.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import net.tsz.afinal.FinalBitmap;

import android.view.LayoutInflater;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.gyh.digou.R;
import com.gyh.digou.imageswitcher.shangpinxinxi.ShangPinInfosItem;
/**
 * ListView���Ż���
 * 		1���ظ�ʹ��convertView��onvertView���ǳ�ʼ���б�ʱ�Ķ���
 * 			Ҳ���ǿ��Ա��ظ����õĶ��󣬵�ˢ���б�ʱ�����Բ����ٴ�������
 * 			ֱ���ó�ʼ��ʱ�Ķ���Ȼ��ı�����������ݾ�OK�ˣ����ԱȽϽ�Լ�ڴ棻
 * 		2��ʹ��ViewHolder�����ͬ���в��������Ч�ʣ�
 * 				�ڴ���View�ؼ���ʱ��Ͳ��ҳ������id
 * 		3���ڲ����ļ���д��ListView�ؼ�ʱ����
 * 			���Լ�ȷ����listView�ؼ��Ŀ�ߣ�һ���������fill_parent����ֱ��д��
 * @author Administrator
 *
 */
public class MyListViewBaseAdapter extends BaseAdapter{
	private Context myContext;// ���ڽ��մ��ݹ�4��Context���� 
	//����һ�������ݵ�����
	//private ArrayList<ShangPinInfosItem> list = new ArrayList<ShangPinInfosItem>();
	ArrayList<HashMap<String, String>> list;
	private LayoutInflater inflater;
	FinalBitmap imageLoader;
	
	public MyListViewBaseAdapter(Context mContext,ArrayList<HashMap<String, String>> list)
	{
		this.list=list;
		this.myContext=mContext;
		inflater=LayoutInflater.from(myContext);
		
		
		imageLoader=FinalBitmap.create(mContext);  
	    imageLoader.configLoadingImage(R.drawable.ic_launcher);
	}
	//�÷����ķ���ֵ��һ������ٸ��б���
	@Override
	public int getCount() {
		return list.size();
	}

	

	// �÷����ķ���ֵ��position�����б������
	@Override
	public long getItemId(int position) {
		return position;
	}
	

	/**getView:���ص�����Ļ����ʾ���б���
		convertView��ת����ͼ��
		*/
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder ;
		if(null==convertView){
			viewHolder = new ViewHolder();
			//����View�ؼ�
			convertView = inflater.inflate(R.layout.list_item, null);
			
			//�ڴ���View�ؼ���ʱ��Ͱ�View�������ҳ�4�����Ѳ��ҳ�4��������浽viewHolder����Ӧ��������ȥ
			viewHolder.pictureName = (ImageView)convertView.findViewById(R.id.pictureName);
			viewHolder.shangPinMingXi = (TextView)convertView.findViewById(R.id.shangPinMingXi);
			viewHolder.shangPinNewPrice = (TextView)convertView.findViewById(R.id.shangPinNewPrice);
			viewHolder.shangPinOldPrice = (TextView)convertView.findViewById(R.id.shangPinOldPrice);
			//viewHolder.yaoYiYao = (Button)convertView.findViewById(R.id.yaoYiYao);
			
			convertView.setTag(viewHolder);//��ʱ�洢�������
		}else{
			//ֱ��ȡViewHolder����
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		
		HashMap<String,String> map=list.get(position);
		imageLoader.display(viewHolder.pictureName,map.get("default_image"));
		viewHolder.shangPinMingXi.setText(map.get("goods_name"));
		
	/*	ShangPinInfosItem shangPinInfosItem = getItem(position);
		if(null != shangPinInfosItem){
			viewHolder.pictureName.setImageResource(shangPinInfosItem.pictureName);
			viewHolder.shangPinMingXi.setText(shangPinInfosItem.shangPinMingXi);
			viewHolder.shangPinNewPrice.setText(shangPinInfosItem.shangPinNewPrice);
			viewHolder.shangPinOldPrice.setText(shangPinInfosItem.shangPinOldPrice);
			viewHolder.yaoYiYao.setText(shangPinInfosItem.yaoYiYao);
		}*/
		
        return convertView;
	}
	
	
	//��ArrayList�����ʼ��һЩ���
	private void initDate(){
		for (int i = 0; i < 10; i++) {
			ShangPinInfosItem shangPinInfosItem = new ShangPinInfosItem();
			
		}
	}
	
	
	//����ViewHolder�࣬�ڴ����ж���LIstView�б��е�������˴���item_list.xml��������
	static class ViewHolder{
		ImageView pictureName;
		TextView shangPinMingXi;
		TextView shangPinNewPrice;
		TextView shangPinOldPrice;
		Button yaoYiYao;
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
}
