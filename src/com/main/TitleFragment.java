package com.main;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shang.manager.DisplayUtils;
import com.shang.moneymanager.R;

public class TitleFragment extends Fragment implements OnClickListener{
	private View view=null;
	private Button mMenuButton;
	private Button mItemButton1;
	private Button mItemButton2;
	private Button mItemButton3;
	private Button mItemButton4;
//	private Button mItemButton5;
	private boolean mIsMenuOpen = false;
	private OnClickMenuItem onClickMenuItem=null;
	private OnClickMenu onClickMenu=null;
	public TextView tv_title=null;
	public ImageView img_back=null;
	public ImageView img_more=null;
	private int number_img=4;
	public static final String title_in="收入",title_out="支出",title_diary="日志",title_set="设置";
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		//将activity转为接口
		onClickMenuItem=(OnClickMenuItem)activity;
//		onClickMenu=(OnClickMenu)activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.menu_layout, container, false);
		initViews();
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	/**
	 * 以下是覆盖其他费fragment的方法
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.menu:
			onClickMenu();
			onClickMenuItem.doClick(0);//进行返回键的开关动作
			break;
		case R.id.item1:
			doButtonsClosed();
			tv_title.setText(title_set);
			onClickMenuItem.doClick(4);
			break;
		case R.id.item2:
			doButtonsClosed();
			tv_title.setText(title_diary);
			onClickMenuItem.doClick(3);
			break;
		case R.id.item3:
			doButtonsClosed();
			tv_title.setText(title_out);
			onClickMenuItem.doClick(2);
			break;
		case R.id.item4:
			doButtonsClosed();
			tv_title.setText(title_in);
			onClickMenuItem.doClick(1);
			break;
		case R.id.image_back:
			onClickMenuItem.doClick(5);
			break;
		case R.id.image_more:
			onClickMenuItem.doClick(6);
			break;
			
		/*case R.id.item5:
			doButtonsClosed();
			tv_title.setText("");
			onClickMenuItem.doClick(5);
			break;*/
		}
	}
	//以下是自己写的方法，在上面被调用
	private void initViews(){
		 mMenuButton = (Button) view.findViewById(R.id.menu);
	        mMenuButton.setOnClickListener(this);

	        mItemButton1 = (Button) view.findViewById(R.id.item1);
	        mItemButton1.setOnClickListener(this);

	        mItemButton2 = (Button) view.findViewById(R.id.item2);
	        mItemButton2.setOnClickListener(this);

	        mItemButton3 = (Button) view.findViewById(R.id.item3);
	        mItemButton3.setOnClickListener(this);

	        mItemButton4 = (Button) view.findViewById(R.id.item4);
	        mItemButton4.setOnClickListener(this);

	        /*mItemButton5 = (Button) view.findViewById(R.id.item5);
	        mItemButton5.setOnClickListener(this);*/
	        
	        tv_title=(TextView)view.findViewById(R.id.tv_title);
	       /* img_back=(ImageView)view.findViewById(R.id.image_back);
	        img_back.setOnClickListener(this);*/
	        
	        img_more=(ImageView)view.findViewById(R.id.image_more);
	        img_more.setOnClickListener(this);
	        
	        
	}
	//设置标题
	public void setTitle(String title){
		if(tv_title==null){
			tv_title=(TextView)view.findViewById(R.id.tv_title);
			tv_title.setText(title);
		}
		tv_title.setText(title);
//		((TextView)view.findViewById(R.id.tv_title)).setText(title);
	}
	//设置返回键的可见性
	public void setVisi(int i){
		if(img_back==null){
			img_back=(ImageView)view.findViewById(R.id.image_back);
			img_back.setVisibility(i);
		}else{
			img_back.setVisibility(i);
		}
	}
	//获得返回键的可见性
	public int getVisi(){
		if(img_back==null){
			img_back=(ImageView)view.findViewById(R.id.image_back);
		}
		return img_back.getVisibility();
	}
	//设置下拉键的可见性
	public void setVisiOfMore(int i){
		if(img_more==null){
			img_more=(ImageView)view.findViewById(R.id.image_more);
		}
		img_back.setVisibility(i);
	}
	//当点击了自己的menu时触发
	private void onClickMenu(){
		 if (!mIsMenuOpen) {
//			 tv_title.setVisibility(View.GONE);
			 doButtonsOpen();
         } else {
             doButtonsClosed();
//             tv_title.setVisibility(View.VISIBLE);
         }
		 
	}
	//打开所有的button
	private void doButtonsOpen(){
		 mIsMenuOpen = true;
         doAnimateOpen(mItemButton1, 0, 5, 300);
         doAnimateOpen(mItemButton2, 1, 5, 300);
         doAnimateOpen(mItemButton3, 2, 5, 300);
         doAnimateOpen(mItemButton4, 3, 5, 300);
         tv_title.setVisibility(View.GONE);
	}
	//关闭所有的button
	private void doButtonsClosed(){
		 mIsMenuOpen = false;
         doAnimateClose(mItemButton1, 0, 5, 300);
         doAnimateClose(mItemButton2, 1, 5, 300);
         doAnimateClose(mItemButton3, 2, 5, 300);
         doAnimateClose(mItemButton4, 3, 5, 300);
         tv_title.setVisibility(View.VISIBLE);
	}
	//打开5个键
	 private void doAnimateOpen(View view, int index, int total, int radius) {
	        view.setVisibility(View.VISIBLE);
	        double wid_screen=DisplayUtils.getScreenWidth(getActivity())-6;
	        double wid_img=DisplayUtils.dip2px(getActivity(), 50);
	        double len=(wid_screen-wid_img)/number_img;
	        int translationX=-(int) (len*index+len);
	        AnimatorSet set = new AnimatorSet();
	        set.playTogether(
	                ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
	                ObjectAnimator.ofFloat(view, "translationY", 0, 0),
	                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
	                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
	                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
	        set.setDuration(index * 100).start();
	    }
	 //关闭5个键
	 private void doAnimateClose(final View view, int index, int total,
	            int radius) {
	        if (view.getVisibility() == View.VISIBLE) {
	        	view.setVisibility(View.GONE);
	        } 
	        double wid_screen=DisplayUtils.getScreenWidth(getActivity());
	        double wid_img=DisplayUtils.dip2px(getActivity(), 50);
	        double len=(wid_screen-wid_img)/number_img;
	        int translationX=(int) (len*index+len);
	        AnimatorSet set = new AnimatorSet();
	        set.playTogether(
	                ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
	                ObjectAnimator.ofFloat(view, "translationY", 0, 0),
	                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
	                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
	                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));
	        set.setDuration(100*index).start();
	    }
	 //menu的风车转动
	 private void menuRotate(float angle){
		 Animation anim_rotate=new RotateAnimation(0, angle,mMenuButton.getWidth()/2,mMenuButton.getHeight()/2);
		 anim_rotate.setFillAfter(true);// True:图片停在动画结束位置  
         anim_rotate.setDuration(300);
		 mMenuButton.startAnimation(anim_rotate);
	 }
	 public void rotateMenu(int flag){
		 switch(flag){
		 case 1:
			 menuRotate(.0f);
			 break;
		 case 2:
			 menuRotate(90.0f);
			 break;
		 case 3:
			 menuRotate(180.0f);
			 break;
		 case 4:
			 menuRotate(-90.0f);
			 break;
		 }
	 }
	 //一个用来控制点击自己的menu的各项的接口
	 public  interface OnClickMenuItem{
		 void doClick(int posi);
	 }
	 //一个用来控制电机menu的接口
	 public interface OnClickMenu{
		 void doClickMenu();
	 }

}
