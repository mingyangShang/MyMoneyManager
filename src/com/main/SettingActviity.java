package com.main;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.main.TitleFragment.OnClickMenuItem;
import com.shang.moneymanager.MainActivity;
import com.shang.moneymanager.R;

public class SettingActviity extends Activity implements OnClickMenuItem{
	private int flag;
	private SettingInFragment frag_in_item;
	private SettingOutFragment frag_out_item;
	private SettingWayFragment frag_way;
	private TitleFragment frag_title=null;
	private final int FLAG_IN_ITEM=1,FLAG_OUT_ITEM=2,FLAG_WAY=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent=getIntent();
		flag=intent.getIntExtra("flag", -1);
		frag_title=new TitleFragment();
		getFragmentManager().beginTransaction()
		.add(R.id.frame_title,frag_title, "title")
		.commit();
		init();
		/*if(frag_title==null){
			frag_title=new TitleFragment();
		}*/
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		frag_title.setVisi(View.VISIBLE);//设置返回键可见
		switch(flag){
			case FLAG_IN_ITEM:
				frag_title.setTitle("收入项");
				break;
			case FLAG_OUT_ITEM:
				frag_title.setTitle("支出项");
				break;
			case FLAG_WAY:
				frag_title.setTitle("我的储蓄");
				break;
		}
	}
	public void onClickIn() {
		// TODO Auto-generated method stub
//		super.onClickIn();
		Intent intent=new Intent();
		intent.putExtra("flag",FLAG_IN_ITEM);
		setResult(1, intent);
		finish();
	}

	public void onClickOut() {
		// TODO Auto-generated method stub
		
	}

	public void onClickDiary() {
		// TODO Auto-generated method stub
	}

	public void onClickSetting() {
		// TODO Auto-generated method stub
	}

	//初始化，选择要加入的fargment
	private void init(){
		switch(flag){
		case FLAG_IN_ITEM:
			onInItem();
			break;
		case FLAG_OUT_ITEM:
			onOutItem();
			break;
		case FLAG_WAY:
			onWay();
			break;
		}
	}
	//将进钱的fragment加载到activity中去
	private void onInItem(){
		if(frag_in_item==null){
			frag_in_item=new SettingInFragment();
		}
		FragmentTransaction ft = getFragmentManager().beginTransaction();//得到一个能操作fragment的事务
	/*	if(frag_title==null){
			frag_title=new TitleFragment();
			ft.add(frag_title, "title");
		}*/
    	ft.replace(R.id.frame_content, frag_in_item);//,"in_item");
    	ft.commit();
    	
	}
	//将出钱defragment加载到activity中
	private void onOutItem(){
		if(frag_out_item==null){
			frag_out_item=new SettingOutFragment();
		}
		FragmentTransaction ft = getFragmentManager().beginTransaction();//得到一个能操作fragment的事务
		/*if(frag_title==null){
			frag_title=new TitleFragment();
			ft.add(frag_title, "title");
		}*/
    	ft.replace(R.id.frame_content, frag_out_item);//,"out_item");
    	ft.commit();
	}
	//将各项存储方式的fragment加载到actiity中
	private void onWay(){
		if(frag_way==null){
			frag_way=new SettingWayFragment();
		}
		
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();//得到一个能操作fragment的事务
		/*if(frag_title==null){
			frag_title=new TitleFragment();
			ft.add(frag_title, "title");
		}*/
    	ft.replace(R.id.frame_content, frag_way,"way");
    	ft.commit();
    	
	}
	//当点击返回健时触发
	public void onClickBack(View v){
		this.finish();
	}
	//当点击自定义的menu键的时候
	@Override
	public void doClick(int posi) {
		// TODO Auto-generated method stub
		if(posi==0){
			if(frag_title.img_back.getVisibility()==View.GONE){
				frag_title.img_back.setVisibility(View.VISIBLE);
			}else{
				frag_title.img_back.setVisibility(View.GONE);
			}
		}else{
			onClickMenuPosi(posi);
		}
	}
	private void onClickMenuPosi(int posi){
		Intent intent=new Intent();
		intent.putExtra("flag",posi);
		setResult(1, intent);
//		this.frag_title=null;//必须加上
		finish();
	}
	
	

}
