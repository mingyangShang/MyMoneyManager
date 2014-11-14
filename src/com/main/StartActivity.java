package com.main;

import help.DataItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.shang.adapter.NewWayAdapter;
import com.shang.manager.DatabaseManager;
import com.shang.manager.PreManager;
import com.shang.moneymanager.MainActivity;
import com.shang.moneymanager.R;
/**
 * 这是用户第一次进入程序所要进入的界面
 * 在这里面有一个gridview来让用户输入自己现在有钱的种类和对应的金额
 * 有一个button键开始管理，进入主界面
 * 在点击button后如果没有其他的意外情况，就要建立数据库和shared文件
 * @author Administrator
 *
 */
public class StartActivity extends Activity{
	private GridView gv_way=null;
	private Button bt_start=null;
	private NewWayAdapter adapter_newWay=null;
	private ArrayList<Map<String,String>> data_ways=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		boolean isFirst=PreManager.get(getApplicationContext(), PreManager.START_FIRST, true);
		if(isFirst==false){//如果不是第一次进入程序，跳转到验证手势密码的界面中去
			Intent intent=new Intent(this,UnlockGesturePasswordActivity.class);
			startActivity(intent);
			finish();
		}
		setContentView(R.layout.activity_start);
		//首先初始化和数据
		init();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PreManager.set(getApplicationContext(), PreManager.START_FIRST, false);
	}
	/**
	 * 以下是覆盖的非activity的方法
	 */
	public void onStartClick(View v){
		//开始进入主界面，并存储数据
		if(judgeHasEmpty()==false){//如果没有空值的话，建立数据库，存储数据
			if(judgeHasSame()==false){
				onClickYes();
			}else{
//				showMoneyErrorDialog();
				Toast.makeText(getApplicationContext(), "填写的支出方式有重复", 1000).show();
			}
		}else{//如果有空值的话，显示提示框
			onShowDialogClick();
		}
	}
	
	/**
	 * 以下是自己定义的方法，在上面被调用
	 */
	private void initData(){
		data_ways=new ArrayList<Map<String,String>>();
		Map<String,String> map=new HashMap<String,String>();
		map.put("way", "现金");
		map.put("number", "");
		data_ways.add(map);
	}
	private void initViews(){
		gv_way=(GridView)findViewById(R.id.gv_way);
		bt_start=(Button)findViewById(R.id.bt_start);
		adapter_newWay=new NewWayAdapter(getApplicationContext(), data_ways);
		gv_way.setAdapter(adapter_newWay);
	}
	private void init(){
		initData();
		initViews();
	}
	//判断新方式中是否有空的值
	private boolean judgeHasEmpty(){
		for(Map<String,String> map:data_ways){
			if(map.get("way").length()==0 || map.get("number").length()==0){
				return true;
			}
		}
		return false;
	}	
	//判断是否有重复的存储方式
	private boolean judgeHasSame(){
		List<String> item_name=new ArrayList<String>();
		for(Map<String,String> map:data_ways){
			if(item_name.contains(map.get("way"))==false){
				item_name.add(map.get("way"));
			}else{
				return true;
			}
		}
		return false;
	}
	//判断数字是否合适
	private boolean judgeNumberIllegal(){
		int number=0;
		for(Map<String,String> map:data_ways){
			try{
				number=Integer.parseInt(map.get("number"));
			}catch(NumberFormatException e){
				return false;
			}
			if(number<0){
				return false;
			}
		}
		return true;
	}
	//存储way和对应的金额
	private void savePreData(){
		Map<String,String> way_number=new HashMap<String, String>();
		for(Map<String,String> map:data_ways){
			way_number.put(map.get("way"), map.get("number"));
		}
		PreManager.saveWayData(getApplicationContext(), way_number);
	}
	//当现在的有一条中是空的时候，显示一个提醒框
	 public void onShowDialogClick(){  
		   
        AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT);  
        builder.setTitle("上述内容存在空值，是否自动忽略");  
        builder.setPositiveButton("是", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				onClickYes();
			}
        });
        builder.setNegativeButton("否，我要填上",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
        });
        AlertDialog ad = builder.create();  
        ad.show();
    }  
/*	private void showMoneyErrorDialog(){
		new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT)  
		  .setTitle("提示") 
		  .setMessage("输入的金额不合适")
		  .setIcon(android.R.drawable.ic_dialog_info)  
		  .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		})  
		  .show();
	}*/
	//当点击确定的按钮的时候触发此函数
	private void onClickYes(){
		new CreateDataBaseTask().execute();
	}
	//一个新的线程用来建立数据库
	private class CreateDataBaseTask extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//建立文件存储
			savePreData();
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//启动主activity，加上动画
			startActivity(new Intent(StartActivity.this,MainActivity.class));
			finish();
		}
	}
	private void getData(){
		for(Map<String,String> map:data_ways){
			if(map.get("way").length()==0 || map.get("number").length()==0){
				continue;
			}
		}
	}
}
