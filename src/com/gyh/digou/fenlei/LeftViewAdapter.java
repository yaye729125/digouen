package com.gyh.digou.fenlei;

import java.util.List;

import net.tsz.afinal.FinalBitmap;

import com.gyh.digou.R;
import com.gyh.digou.bean.GoodsCategoryInfo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


public class LeftViewAdapter extends BaseAdapter {
	FinalBitmap imageLoader;
	Context context;
	//private List<String> lst;
	LayoutInflater inflater;
	//String [] foods;
	//int last_item;
	//int [] image;
	GoodsCategoryInfo info;
	private int selectedPosition = -1;
    //用来标示是否需要隐藏图片和描述文字
    private boolean hideFlag;

   
    
    public LeftViewAdapter(Context mContext)
    {
    	this.context = mContext;
    	inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		imageLoader=FinalBitmap.create(mContext);
		imageLoader.configLoadingImage(R.drawable.item_loading);
    }
    
    
	public void setSelectedPosition(int position) {   
	selectedPosition = position;   
	}   
	          

	/*public myadapter(Context context,List<String > lst){
		this.context = context;
		this.lst = lst;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 

	}*/
	public void setData(GoodsCategoryInfo info){
		if(info!=null)
		{
			this.info=info;
			notifyDataSetChanged();
		}
		
		
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return info==null?0:info.getData().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return info.getData().get(position).getChildren().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder  holder = null;
	    if(convertView==null){
	    convertView = inflater.inflate(R.layout.fenlei_fragment_listitem, null);
	    holder = new ViewHolder();
        holder.textView =(TextView)convertView.findViewById(R.id.fenlei_fragment_parent_listitem_tv);
        holder.imageView =(ImageView)convertView.findViewById(R.id.fenlei_fragment_parent_listitem_imv);
        //holder.layout=(RelativeLayout)convertView.findViewById(R.id.colorlayout);
      // holder.description=(TextView)convertView.findViewById(R.id.txt_description);
        convertView.setTag(holder);
	    }
	    else{
	    	holder=(ViewHolder)convertView.getTag();
	    }
	 /*   if(last_item == position){
	    holder.textView.setBackgroundColor(R.color.bg);
	    }*/
	    // 设置选中效果  
	    holder.textView.setTextColor(Color.BLACK);   
	     
	    imageLoader.display(holder.imageView,info.getData().get(position).getImage());
	   
	    holder.textView.setText(info.getData().get(position).getCate_name());
	   // holder.textView.setTextColor(Color.BLACK);
	   //holder.imageView.setBackgroundResource(image[position%10]);
        if(hideFlag){
        	if(selectedPosition == position)   
    	    {   
    	    	 holder.textView.setTextColor(Color.BLACK);   

    	       
    	   } else {   
    		   holder.textView.setTextColor(Color.LTGRAY);   
    	     // holder.layout.setBackgroundColor(Color.TRANSPARENT);
    	   }   
            holder.imageView.setVisibility(View.INVISIBLE);
            //holder.description.setVisibility(View.INVISIBLE);
        }else{
            holder.imageView.setVisibility(View.VISIBLE);
           // holder.description.setVisibility(View.VISIBLE);
        }
	    return convertView;
	}
	
	public static class ViewHolder{
		public TextView textView;
		public ImageView  imageView;
		//public RelativeLayout layout;
		//public TextView description;
	}

    public boolean isHideFlag() {
        return hideFlag;
    }

    public void setHideFlag(boolean hideFlag) {
        this.hideFlag = hideFlag;
    }
}
