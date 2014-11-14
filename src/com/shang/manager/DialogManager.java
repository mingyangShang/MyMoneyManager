package com.shang.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shang.moneymanager.R;


public class DialogManager {

	/*public void popNewWay(Context con){
		  View v = LayoutInflater.from(con).inflate(R.layout.dialog_new_way, null);
		  final EditText ed = (EditText)v.findViewById(R.id.editname);
		  new AlertDialog.Builder(con)  
		  .setTitle((flag_in==true)?"新建收入来源":"新建消费项")  
		  .setIcon(android.R.drawable.ic_dialog_info)  
		  .setView(v)  
		  .setPositiveButton("确定", listen)
		  .setNegativeButton("取消", null)  
		  .show();
		  new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(flag==0){
				if(judgeDiscuExists(ed.getText().toString())==false){//成功
					new AddDiscuTask().execute(ed.getText().toString());
				}else{
					Toast.makeText(getActivity(), "该讨论组已经存在	", 1000).show();
				}
				}else{
					if(judgeGroupExists(ed.getText().toString())==false){//成功
						new AddDiscuTask().execute(ed.getText().toString());
					}else{
						Toast.makeText(getActivity(), "输入的名称已存在	", 1000).show();
					}
				}
			}
		})  
		  
	  }*/
}
