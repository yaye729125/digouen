package com.gyh.digou.wode.shangjia;

import java.util.ArrayList;
import java.util.List;

import com.gyh.digou.R;
import com.gyh.digou.bean.Specs;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditShangpinAddFormatActivity extends Activity {

	
	
	LayoutInflater inflater;
	ListView list;
	boolean flag=false;
	List<Specs> specs=new ArrayList<Specs>();
	List<Specs> orinSpecs=new ArrayList<Specs>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.tianjiashangpin_addformat);
		
		
		list=(ListView) findViewById(R.id.tianjiashangpin_addformat_list);
		
		
		inflater=getLayoutInflater();
		View footerView=inflater.inflate(R.layout.tianjiashangpin_addformat_footer, null);
		
		
		RadioButton radiobtn_add=(RadioButton) footerView.findViewById(R.id.tianjiashangpin_addformat_footer_tianjia_shuxing);
		
		
		findViewById(R.id.base_title_option).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(check())
				{
					Intent data=new Intent();
					data.putExtra("format",getSpecsBySpecs());
					setResult(0x19, data);
					EditShangpinAddFormatActivity.this.finish();
				}else
				{
					Toast.makeText(EditShangpinAddFormatActivity.this,"您必须填写所有必填项目", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		
		
		radiobtn_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//flag=true;
				if(specs.size()<20)
				{
					addFormat();
					adapter.notifyDataSetChanged();
				}else
				{
					Toast.makeText(EditShangpinAddFormatActivity.this,"您最多添加二十组属性", Toast.LENGTH_SHORT).show();
				}
				//expandList();
				
				
			}
		});
		RadioButton radiobtn_shanchu=(RadioButton) footerView.findViewById(R.id.tianjiashangpin_addformat_footer_shanchu_shuxing);
		radiobtn_shanchu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				flag=true;
				if(specs.size()>1)
				{
					DelCurFormat();
					adapter.notifyDataSetChanged();
				}else
				{
					
					Toast.makeText(EditShangpinAddFormatActivity.this,"您必须添加至少一组属性", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		Intent intent=getIntent();
		if(null==intent.getSerializableExtra("specs"))
		{
			flag=true;
		}else
		{
			specs.clear();
			Object[] objs=(Object[]) intent.getSerializableExtra("specs");
			for(Object obj:objs)
			{
				specs.add((Specs) obj);
				orinSpecs.add((Specs) obj);
				flags.add(false);
			}
			
			resetListData();
		}
		
		list.addFooterView(footerView, null, false);
		adapter=new EditShangpinFormatAdapter(this);
		list.setAdapter(adapter);
		
	}
	
	
	public Specs getSpecsEn()
	{
		Specs specs=new Specs();
		String dijia_en="",spec1="",spec2="",price="",mk_price="",stock="";
		for(int j=0;j<list.getAdapter().getCount();j++)
		{
			LinearLayout arg3=(LinearLayout) list.getChildAt(j);
			EditText dijia=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_minim_price);
			//TextView tv=(TextView) arg3.findViewById(R.id.tianjiashangpin_parent_item_tv);
			EditText guigezhi1=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_spec1);
			EditText guigezhi2=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_spec2);
			EditText kucun=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_stock);
			EditText lingshoujia=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_price);
			EditText pifajia=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_mk_price);
			
				dijia_en+=dijia.getText().toString()+",";
				spec1+=guigezhi1.getText().toString()+",";
				spec2+=guigezhi2.getText().toString()+",";
				price+=lingshoujia.getText().toString()+",";
				mk_price+=pifajia.getText().toString()+",";
				stock+=kucun.getText().toString()+",";
			
			
		}
		
		specs.setMinimum_price(dijia_en);
		specs.setMk_price(mk_price);
		specs.setPrice(price);
		specs.setSpec_1(spec1);
		specs.setSpec_2(spec2);
		specs.setStock(stock);
		
		return specs;
		
	}
	
	
	


	EditShangpinFormatAdapter adapter;
	List<ChildViewHolder> list_data=new ArrayList<ChildViewHolder>();
	
	protected boolean check() {
		
		
		boolean flag=true;
		
		
		for(int j=0;j<list_views.size();j++)
		{
			View arg3=list_views.get(j);
			EditText dijia=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_minim_price);
		
			EditText guigezhi1=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_spec1);
		
			EditText kucun=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_stock);
			EditText lingshoujia=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_price);
			EditText pifajia=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_mk_price);
			if(TextUtils.isEmpty(dijia.getText().toString())||TextUtils.isEmpty(guigezhi1.getText().toString())
					||TextUtils.isEmpty(kucun.getText().toString())||TextUtils.isEmpty(lingshoujia.getText().toString())||
					TextUtils.isEmpty(pifajia.getText().toString()))
			{
				return false;
			}
		}
		
		
		
		/*for(ChildViewHolder holder:list_data)
		{
			
			if(TextUtils.isEmpty(holder.dijia.getText().toString())||TextUtils.isEmpty(holder.guigezhi1.getText().toString())
					||TextUtils.isEmpty(holder.kucun.getText().toString())||TextUtils.isEmpty(holder.lingshoujia.getText().toString())||
					TextUtils.isEmpty(holder.pifajia.getText().toString()))
			{
				return false;
			}
			
			
		}*/
		
		
		return flag;
	}
	protected Specs getSpecs() {
		//AddFormatBean addfb=new AddFormatBean();
		Specs specs=new Specs();
		String dijia="",spec1="",spec2="",price="",mk_price="",stock="";
		
		for(ChildViewHolder holder:list_data)
		{
			
			dijia+=holder.dijia.getText().toString()+",";
			spec1+=holder.guigezhi1.getText().toString()+",";
			spec2+=holder.guigezhi2.getText().toString()+",";
			price+=holder.lingshoujia.getText().toString()+",";
			mk_price+=holder.pifajia.getText().toString()+",";
			stock+=holder.kucun.getText().toString()+",";
		}
		specs.setMinimum_price(dijia);
		specs.setMk_price(mk_price);
		specs.setPrice(price);
		specs.setSpec_1(spec1);
		specs.setSpec_2(spec2);
		specs.setStock(stock);
		
		return specs;
	}
	
	
	private Specs getSpecsBySpecs()
	{
		
		Specs specsen=new Specs();
		String dijia="",spec1="",spec2="",price="",mk_price="",stock="";
		for(Specs spec:specs)
		{
			dijia+=spec.getMinimum_price()+",";
			spec1+=spec.getSpec_1()+",";
			spec2+=spec.getSpec_2()+",";
			price+=spec.getPrice()+",";
			mk_price+=spec.getMk_price()+",";
			stock+=spec.getStock()+",";
		}
		
		specsen.setMinimum_price(dijia);
		specsen.setMk_price(mk_price);
		specsen.setPrice(price);
		specsen.setSpec_1(spec1);
		specsen.setSpec_2(spec2);
		specsen.setStock(stock);
		
		return specsen;
	}
	protected void DelCurFormat() {
		
		
		list_data.remove(list_data.size()-1);
		//flags.remove(flags.size()-1);
		list_views.remove(list_views.size()-1);
		specs.remove(specs.size()-1);
		//flag=false;
	}

	
	
	public void addFormat()
	{
		
		list_data.add(new ChildViewHolder());
		list_views.add(inflater.inflate(R.layout.tianjiashangpin_child_item,null));
		specs.add(new Specs());
		//flags.add(true);
	}
	
	
	private void resetListData() {
		list_data.clear();
		
		
		for(Specs spec:specs)
		{
			list_views.add(inflater.inflate(R.layout.tianjiashangpin_child_item,null));
			list_data.add(new ChildViewHolder() );
		}
		
	}
	
	
	
	
	
	List<View> list_views=new ArrayList<View>();
	
	static class ChildViewHolder
	{
		
	     EditText guigezhi1;
	     EditText guigezhi2;
	     EditText lingshoujia;
	     EditText dijia;
	     EditText pifajia;
	     EditText kucun;
	     TextView tv;
	}
	
	
	
	
	List<Boolean> flags=new ArrayList<Boolean>();
	
	
	 class EditShangpinFormatAdapter extends BaseAdapter {

			
			LayoutInflater inflater;
			
			EditShangpinFormatAdapter(Context mContext)
			{
			
				inflater=LayoutInflater.from(mContext);
			}
			
			private Integer index = -1;  
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return specs.size();
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
			public View getView(final int arg0, View arg3, ViewGroup arg2) {
				
				
				
				//ChildViewHolder holder=null;
				//if(arg3==null)
				//{
					//holder=list_data.get(arg0);
				
				
					arg3=list_views.get(arg0);
					EditText dijia=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_minim_price);
					TextView tv=(TextView) arg3.findViewById(R.id.tianjiashangpin_parent_item_tv);
					final EditText guigezhi1=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_spec1);
					final EditText guigezhi2=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_spec2);
					final EditText kucun=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_stock);
					final EditText lingshoujia=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_price);
					final EditText pifajia=(EditText) arg3.findViewById(R.id.tianjiashangpin_child_item_edit_mk_price);
					
					
					
					
					
					
					class dijiaWatcher implements TextWatcher {

						
						@Override
						public void afterTextChanged(Editable s) {
							 
								specs.get(arg0).setMinimum_price(s.toString());
						}

						@Override
						public void beforeTextChanged(CharSequence arg0,
								int arg1, int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onTextChanged(CharSequence arg0, int arg1,
								int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						} 
						
					}
					class pfjWatcher implements TextWatcher {

						/*ChildViewHolder holder;
						pfjWatcher(ChildViewHolder holder)
						{
							
							this.holder=holder;
						}*/
						@Override
						public void afterTextChanged(Editable s) {
							int position = (Integer)pifajia.getTag(); 
							specs.get(position).setMk_price(pifajia.getText().toString());
							
							
						}

						@Override
						public void beforeTextChanged(CharSequence arg0,
								int arg1, int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onTextChanged(CharSequence arg0, int arg1,
								int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						} 
						
					}
					class gg1Watcher implements TextWatcher {

						
						/*ChildViewHolder holder;
						gg1Watcher(ChildViewHolder holder)
						{
							
							this.holder=holder;
						}*/
						@Override
						public void afterTextChanged(Editable s) {
							
							int position = (Integer) guigezhi1.getTag(); 
							specs.get(position).setSpec_1( guigezhi1.getText().toString());
							
						}

						@Override
						public void beforeTextChanged(CharSequence arg0,
								int arg1, int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onTextChanged(CharSequence arg0, int arg1,
								int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						} 
						
					}
					class gg2Watcher implements TextWatcher {

						
						
						/*ChildViewHolder holder;
						gg2Watcher(ChildViewHolder holder)
						{
							
							this.holder=holder;
						}*/
						@Override
						public void afterTextChanged(Editable s) {
							int position = (Integer)guigezhi2.getTag(); 
							specs.get(position).setSpec_2(guigezhi2.getText().toString());
							
							
						}

						@Override
						public void beforeTextChanged(CharSequence arg0,
								int arg1, int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onTextChanged(CharSequence arg0, int arg1,
								int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						} 
						
					}
					class kcWatcher implements TextWatcher {

						
						/*ChildViewHolder holder;
						kcWatcher(ChildViewHolder holder)
						{
							
							this.holder=holder;
						}*/
						@Override
						public void afterTextChanged(Editable s) {
							int position = (Integer) kucun.getTag(); 
							specs.get(position).setStock(kucun.getText().toString());
							
							
							
						}

						@Override
						public void beforeTextChanged(CharSequence arg0,
								int arg1, int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onTextChanged(CharSequence arg0, int arg1,
								int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						} 
						
					}
					
					class lsjWatcher implements TextWatcher {

						
						
						
						/*ChildViewHolder holder;
						lsjWatcher(ChildViewHolder holder)
						{
							
							this.holder=holder;
						}*/
						@Override
						public void afterTextChanged(Editable s) {
							int position = (Integer)lingshoujia.getTag(); 
							specs.get(position).setPrice(lingshoujia.getText().toString());
							
							
						}

						@Override
						public void beforeTextChanged(CharSequence arg0,
								int arg1, int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onTextChanged(CharSequence arg0, int arg1,
								int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						} 
						
					}
					
					dijia.setTag(arg0);
					guigezhi1.setTag(arg0);
					guigezhi2.setTag(arg0);
					kucun.setTag(arg0);
					lingshoujia.setTag(arg0);
					pifajia.setTag(arg0);
					dijia.addTextChangedListener(new dijiaWatcher());
					guigezhi1.addTextChangedListener(new gg1Watcher());
					guigezhi2.addTextChangedListener(new gg2Watcher());
					kucun.addTextChangedListener(new kcWatcher());
					lingshoujia.addTextChangedListener(new lsjWatcher());
					pifajia.addTextChangedListener(new pfjWatcher());
					//arg3.setTag(holder);			
				/*}else
				{
					
					holder=(ChildViewHolder) arg3.getTag();
					
					
				}*/
					/*Log.d("arg0....specs.size",arg0+"...."+orinSpecs.size());
					if(arg0<=orinSpecs.size()-1)
					{
						
						Log.d("arg0----specs.size",arg0+"----"+orinSpecs.size());
						Specs spec=orinSpecs.get(arg0);
						
						//flags.set(arg0, true);
						dijia.setText(spec.getMinimum_price());
						guigezhi1.setText(spec.getSpec_1());
						guigezhi2.setText(spec.getSpec_2());
						//holder.kucun
						kucun.setText(spec.getStock());
						lingshoujia.setText(spec.getPrice());
						pifajia.setText(spec.getMk_price());
					}*/
					tv.setText("第"+(arg0+1)+"组属性");
					Specs spec=specs.get(arg0);
					Log.d("arg0---spec", arg0+"---"+spec.toString());
					//flags.set(arg0, true);
					dijia.setText(spec.getMinimum_price());
					guigezhi1.setText(spec.getSpec_1());
					guigezhi2.setText(spec.getSpec_2());
					//holder.kucun
					kucun.setText(spec.getStock());
					lingshoujia.setText(spec.getPrice());
					pifajia.setText(spec.getMk_price());
					
				   //Log.d("tianjiashangpiinadapter....flags===",printFlags());
					/*if(arg0<=orinSpecs.size()-1){
							
						
							Specs spec=specs.get(arg0);
							
							flags.set(arg0, true);
							holder.dijia.setText(spec.getMinimum_price());
							holder.guigezhi1.setText(spec.getSpec_1());
							holder.guigezhi2.setText(spec.getSpec_2());
							//holder.kucun
							holder.kucun.setText(spec.getStock());
							holder.lingshoujia.setText(spec.getPrice());
							holder.pifajia.setText(spec.getMk_price());
							
							
						}*/
					//	Log.d("arg0----specs.size",arg0+"----"+specs.size());	
					
				
				return arg3;
			}
			
			
		}
	
	
	 
	 
	/* public String printFlags()
	 {
		 
		 StringBuffer sb=new StringBuffer();
		 for(Boolean b:flags)
		 {
			 sb.append(b);
			 sb.append(",");
			 
		 }
		 return sb.toString();
		// System.out.println(sb.toString());
	 }*/
}
