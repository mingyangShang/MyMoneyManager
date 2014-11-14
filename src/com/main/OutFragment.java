package com.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shang.manager.DatabaseManager;
import com.shang.manager.PreManager;
import com.shang.moneymanager.R;

public class OutFragment extends DirBaseFragment implements Runnable{
	private Set<String> set_item;
	private List<String> list_item;
	private ArrayAdapter<String> ada_out;
	private Spinner spinner_out;
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Toast.makeText(getActivity(),"保存成功",500).show();
		}
		
	};

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String s_way=way_list.get(spinner_way.getSelectedItemPosition());
		String s_item=list_item.get(spinner_out.getSelectedItemPosition());
		String s_number=edit_number.getText().toString();
		String s_time=getDate();
		String s_note=edit_note.getText().toString();
		String s_way_number=way_data.get(s_way);
		DatabaseManager.insertNewIem(getActivity(), s_item, "out", s_way, s_number, s_time, s_note);
		PreManager.saveWayData(getActivity(), way_data);//文件中数据更新
		handler.obtainMessage().sendToTarget();
	}

	@Override
	public void onClickAddItem() {
		// TODO Auto-generated method stub
		this.showAddItemDialog();
	}

	@Override
	public void onClickOk() {
		// TODO Auto-generated method stub
		int number=0;
		String s_number=edit_number.getText().toString();
		try{
			number=Integer.valueOf(s_number);
		}catch(Exception e){//如果输入的金额的数字不合适的话给予提示，重新输入
			Toast.makeText(getActivity(), "输入的金额格式错误", 500).show();
			edit_number.requestFocus();
			return ;
		}
		String s_way=way_list.get(spinner_way.getSelectedItemPosition());
		String s_way_number=way_data.get(s_way);
		int number_has=0;
		try{
			number_has=Integer.valueOf(s_way_number);
		}catch(NumberFormatException e){
			number_has=0;
		}
		if(number>number_has){
			showMoneyErrorDialog();
			return ;
		}
		way_data.put(s_way, ""+(number_has-number));
		//开启一个新的线程来实现向数据库中插入数据,shared文件中的金额也要更新
		new Thread(this).start();
	}
	
	private void showAddItemDialog(){
		 View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_way, null);
		  final EditText ed = (EditText)v.findViewById(R.id.editname);
		  new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT)  
		  .setTitle("新建收入来源")  
		  .setIcon(android.R.drawable.ic_dialog_info)  
		  .setView(v)  
		  .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				new AddOutItemTask().execute(ed.getText().toString());
			}
		})  
		  .setNegativeButton("取消", null)  
		  .show();
	  }
	//当输入的花的钱比有的前多时，弹出这个提示框
	private void showMoneyErrorDialog(){
		new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT)  
		  .setTitle("提示") 
		  .setMessage("没有这么多钱让你花")
		  .setIcon(android.R.drawable.ic_dialog_info)  
		  .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				edit_number.requestFocus();
			}
		})  
		  .show();
	}
	@Override
	public void initViews(){
		super.initViews();
		spinner_out=(Spinner) super.view.findViewById(R.id.spinner_use);
		spinner_out.setAdapter(ada_out);
	}
	@Override
	public void getItemData(){
		set_item=PreManager.getOutItem(getActivity());
		list_item=new ArrayList<String>();
		if(set_item==null){
			set_item=new HashSet<String>();
		}
		for (String str : set_item) {  
		        list_item.add(str);
		}  
//		ada_out=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list_item);
		ada_out=new ArrayAdapter<String>(getActivity(), R.layout.item_drop, list_item);
		/*ada_out = new ArrayAdapter<String>(getActivity(),R.layout.item_drop, list_item){  
             @Override  
             public View getDropDownView(int position, View convertView, ViewGroup parent) {  
                 if(convertView==null){  
                     convertView = getActivity().getLayoutInflater().inflate(R.layout.item_drop, parent, false);  
                 }  
                 TextView label = (TextView) convertView.findViewById(android.R.id.text1);  
                 label.setText(getItem(position));  
//                 ImageView icon = (ImageView) convertView.findViewById(R.id.icon);  
//                 icon.setVisibility(spinner.getSelectedItemPosition() == position?View.VISIBLE:View.INVISIBLE);  
                   
                 return convertView;  
             }  
        };   */
		// 设置下拉列表的风格  
//		ada_out.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ada_out.setDropDownViewResource(R.layout.item_drop);
	}
	//在增加条目的对话框中点击确定后的线程
		private class AddOutItemTask extends AsyncTask<String,String,Boolean>{
			@Override
			protected Boolean doInBackground(String... params) {
				// TODO Auto-generated method stub
				if(set_item.contains(params[0])){
					return false;
				}else{
					//如果满足条件的话，重新写入文件，并将adapter的数据更新
					set_item.add(params[0]);
					PreManager.saveOutItem(getActivity(), set_item);
					list_item.add(params[0]);
				}
				return true;
			}
			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(result==true){
					ada_out.notifyDataSetChanged();
				}else{
					Toast.makeText(getActivity(), "该存储方式已经存在", 1000).show();
				}
			}
		}
		private class OnSaveOutTask extends AsyncTask<Void, Void, Boolean>{

			@Override
			protected Boolean doInBackground(Void... params) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}
		}
		
}
