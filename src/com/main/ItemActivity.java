package com.main;

import java.util.Map;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.main.TitleFragment.OnClickMenuItem;
import com.shang.moneymanager.MainActivity;
import com.shang.moneymanager.R;

public class ItemActivity extends Activity implements OnItemClickListener,OnClickMenuItem{
	private TitleFragment frag_title=null;
	private GridView grid_item=null;
	private BaseAdapter ada_item=null;
	private Map<String,String> data=null;//用来存放这条item对应的所有数据
	private String type,name;
	private ItemFragment frag_item;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getData();
		initViews();//初始化控件
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		frag_title.setVisi(View.VISIBLE);
		if(type.equals("in")){
			frag_title.setTitle("收入:"+name);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doClick(int posi) {
		// TODO Auto-generated method stub
		if(posi==0){
			if(frag_title.getVisi()==View.VISIBLE){
				frag_title.setVisi(View.GONE);
			}else{
				frag_title.setVisi(View.VISIBLE);
			}
		}else{
			//onClickMenuPosi(posi);
			Intent intent=new Intent(this,com.shang.moneymanager.MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("flag",posi);
			setResult(1);
			startActivity(intent);
			finish();
		}
	}
	//当点击返回健时触发
	public void onClickBack(View v){
		this.finish();
	}
	//获得数据v
	private void getData(){
		Intent intent=getIntent();
		try{
			type=intent.getStringExtra("itemType");
		    name=intent.getStringExtra("itemName");
		}catch(Exception e){
			e.printStackTrace();
			finish();
		}
	}
	//初始化控件
	private void initViews(){
		//grid_item=(GridView)findViewById(R.id.grid_item);
		//grid_item.setOnItemClickListener(this);
		addFrag();
	}
	//将titlfragment添加到界面中
	private void addFrag(){
		frag_title=new TitleFragment();
    	FragmentTransaction ft = getFragmentManager().beginTransaction();//得到一个能操作fragment的事务
    	ft.add(R.id.frame_title, frag_title,"title");
    	frag_item=new ItemFragment();
    	Bundle bundle=new Bundle();
    	bundle.putBoolean("in",type.equals("in"));
    	bundle.putString("itemName", name);
    	frag_item.setArguments(bundle);
    	ft.add(R.id.frame_content,frag_item,"item");
    	ft.commit();
	}
	//一个用来给girdview提供数据的适配器
	private class ItemTotalAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return data.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			
			return null;
		}
		
	}
	

}
