package com.gyh.digou.progerss;

import com.gyh.digou.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomProgressDialog extends Dialog {
    private Context context = null;
    private static CustomProgressDialog customProgressDialog = null;
     
    public CustomProgressDialog(Context context){
        super(context);
        this.context = context;
    }
     
    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }
     
    
    
    public static CustomProgressDialog createDialog(Context context,int theme,int layout){
        customProgressDialog = new CustomProgressDialog(context,theme);
        customProgressDialog.setContentView(layout);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
         
        return customProgressDialog;
    }
  
    public void onWindowFocusChanged(boolean hasFocus){
         
        if (customProgressDialog == null){
            return;
        }
         
        /*ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();*/
    }
  
    /**
     *
     * [Summary]
     *       setTitile 标题
     * @param strTitle
     * @return
     *
     */
    public CustomProgressDialog setTitile(String strTitle){
        return customProgressDialog;
    }
     
    /**
     *
     * [Summary]
     *       setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public CustomProgressDialog setMessage(String strMessage,int id){
        TextView tvMsg = (TextView)customProgressDialog.findViewById(id);
         
        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }
         
        return customProgressDialog;
    }
    
    
    
   public CustomProgressDialog setResultMessage(String strMessage,int id,boolean successed){
       TextView tvMsg = (TextView)customProgressDialog.findViewById(id);
        
       if (tvMsg != null){
           tvMsg.setText(strMessage);
       }
       ProgressBar pb=(ProgressBar) customProgressDialog.findViewById(R.id.mytoast_progerssbar);
       pb.setVisibility(View.GONE);
       return customProgressDialog;
   }
   Handler mHandler=new Handler();
   /*public boolean showProgressDialog()
   {
	   if(customProgressDialog!=null)
	   {
		   if(!customProgressDialog.isShowing())
			   customProgressDialog.show();
		   return true;
	   }
	   return false;
   }*/
   public void hideProgressDialog()
   {
	   mHandler.post(run);
   }
   
   Runnable run=new Runnable()
   {

		@Override
		public void run() {
		
			if(customProgressDialog!=null)
			{
				if(customProgressDialog.isShowing())
					customProgressDialog.dismiss();
			}
		}
	   
   };
   public void hideProgressDialog(long delay)
   {
	   mHandler.postDelayed(run, delay);
   }
}
