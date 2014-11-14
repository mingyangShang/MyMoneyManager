package com.main;

import help.MoneyNumberComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.maxwin.view.XListView;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shang.adapter.ItemAdapter;
import com.shang.manager.PreManager;
import com.shang.moneymanager.R;

public class SettingWayFragment extends ListFragment{
	private View view;
	private List<Map<String,String>> item_money=null;
	private ItemAdapter ada_item;
	private int total_money=0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_set_in, container, false);
		view.findViewById(R.id.img_add_way).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showAddWayDialog();
			}
		});
		return view;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		RelativeLayout layout_add=(RelativeLayout) view.findViewById(R.id.layout_add_way);
		layout_add.setVisibility(View.VISIBLE);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(item_money==null){
			item_money=new ArrayList<Map<String,String>>();
			initListView();
			new GetDataTask().execute();
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}
	//初始化listview
	private void initListView(){
		ada_item=new ItemAdapter(getActivity(), item_money);
		this.setListAdapter(ada_item);
		((XListView)this.getListView()).setPullLoadEnable(false);
		((XListView)this.getListView()).setPullRefreshEnable(false);
	}
	/*//点击增加方式后的函数,弹出一个菜单让用户选择增加的储蓄方式和金额
	private void showAddWayDialog(){
		//加载对话框的布局文件，并将无用的按钮删除
		 View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_new_way, null);
		 v.findViewById(R.id.iv_delete_item).setVisibility(View.GONE);
		 v.findViewById(R.id.iv_add_way).setVisibility(View.GONE);
		  final EditText ed_way = (EditText)v.findViewById(R.id.edit_way);
		  final EditText ed_number = (EditText)v.findViewById(R.id.edit_number);
		  new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT)  
		  .setTitle("新建储蓄")  
		  .setIcon(android.R.drawable.ic_dialog_info)  
		  .setView(v)  
		  .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//new AddInItemTask().execute(ed.getText().toString());
//				System.out.println("点击了确定"+ed_way.getText().toString()+"-->"+ed_number.getText().toString());
				//开始判断输入的数据是否合适，合适的话就保存起来，并更新他所在的界面，不合适的话弹出相应的提示框
				judgeNewWay(ed_way.getText().toString(),ed_number.getText().toString());
			}
		})  
		  .setNegativeButton("取消", null)  
		  .show();
	  }*/
	//弹出对话框
	private void showAddWayDialog(){
		 View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_new_way, null);
		 ((ImageButton)v.findViewById(R.id.iv_delete_item)).setVisibility(View.GONE);
		 ((ImageButton)v.findViewById(R.id.iv_add_way)).setVisibility(View.GONE);
		 final EditText ed_way = (EditText)v.findViewById(R.id.edit_way);
		  final EditText ed_number=(EditText)v.findViewById(R.id.edit_number);
		  new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT)  
		  .setTitle("新建储蓄")  
		  .setIcon(android.R.drawable.ic_dialog_info)  
		  .setView(v)  
		  .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				new AddWayTask().execute(ed_way.getText().toString(),ed_number.getText().toString());
			}
		})  
		  .setNegativeButton("取消", null)  
		  .show();
	  }
	/*//
	private void judgeNewWay(String way_name,String way_number){
		if(way_name.equals("") || way_number.equals("")){
			Toast.makeText(getActivity(), "输入不能为空值", 500).show();
			return ;
		}
		Map<String,String> map_ways=PreManager.getWayData(getActivity());
		Set<String> ways=map_ways.keySet();
		for(String way:ways){
			if(way.equals(way_name)){
				Toast.makeText(getActivity(), "该储蓄方式已经存在", 1000).show();
				return ;
			}
		}
		//输入的数据合法，将数据存储到sharedpreference中
		map_ways.put(way_name,way_number);
		PreManager.saveWayData(getActivity(), map_ways);
		Map<String,String> new_way=new HashMap<String, String>();
		new_way.put("itemName",way_name);
		new_way.put("totalMoney", way_number);
		item_money.add(new_way);
		try{
			double money_new=Double.parseDouble(way_number);
			double money_total=(TextView)view.findViewById(R.id.)
		}catch(NullPointerException e){
		}
		Toast.makeText(getActivity(), "新建储蓄成功", 500).show();
		
	}*/
	
	private class GetDataTask extends AsyncTask<Void, Void, Map<String,String>>{

		@Override
		protected Map<String,String> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Map<String,String> way_number=PreManager.getWayData(getActivity());
			if(way_number==null){
				way_number=new HashMap<String, String>();
			}
			return way_number;
		}
		@Override
		protected void onPostExecute(Map<String,String> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			int number=0;
			Set<String> item=result.keySet();
			if(item!=null){//如果键不为空的话
			for(String i:item){
				Map<String,String> map=new HashMap<String,String>();
				try{
					number=Integer.parseInt(result.get(i));
				}catch(Exception e){
					number=0;
				}
				total_money+=number;//总金额
				map.put("itemName", i);
				map.put("totalMoney", result.get(i));
				item_money.add(map);
			}
			}
			TextView tv=(TextView)view.findViewById(R.id.total_in);
			tv.setText("现有储蓄"+total_money+"元");
			Collections.sort(item_money,new MoneyNumberComparator());
			ada_item.notifyDataSetChanged();
		}
	}
	//当点击去确定的新建储蓄方式的时后执行此线程来判断新建的储蓄方式是否合理
	private class AddWayTask extends AsyncTask<String, Void, Boolean>{
		private String money_new="0";
		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(params[0].equals("") || params[1].equals("")){
				return false;
			}
			//找到之前的储蓄方式看是否有重复
			Map<String,String> way_number=PreManager.getWayData(getActivity());
			Set<String> ways=way_number.keySet();
			if(ways.contains(way_number)){//如果有重复的话
				return false;
			}else{
				way_number.put(params[0], params[1]);
				PreManager.saveWayData(getActivity(), way_number);//在文件中存储
				Map<String,String> map=new HashMap<String,String>();
				map.put("itemName", params[0]);
				map.put("totalMoney", params[1]);
				item_money.add(map);
			}
			money_new=params[1];
			return true;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				Collections.sort(item_money,new MoneyNumberComparator());
				ada_item.notifyDataSetChanged();//更新数据
				//更新总的储蓄金额
				try{
					System.out.println("money_new:"+money_new);
					TextView tv=(TextView)view.findViewById(R.id.total_in);
					total_money+=Double.parseDouble(money_new);
					System.out.println("现在:"+total_money);
					tv.setText("现有储蓄"+total_money+"元");
				}catch(Exception e){
				}
				Toast.makeText(getActivity(), "新储蓄添加成功", 1000).show();
			}else{
				Toast.makeText(getActivity(), "该储蓄方式已经存在了", 1000).show();
			}
		}
	}
	
	
}
