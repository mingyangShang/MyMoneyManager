package com.shang.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.shang.moneymanager.R;
/**
 * @author Administrator
 *这是新建方式的时候每个狗日的view对应的适配器
 *里面有两个edittext和一个删除按钮,增加按钮需要监听
 */
public class NewWayAdapter extends BaseAdapter{
	private Context context=null;
	private ArrayList<Map<String,String>> data=null;
	private ViewHolder viewholder=null;
	
	//构造函数
	public NewWayAdapter(Context con,ArrayList<Map<String,String>> ways){
		this.context=con;
		this.data=ways;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=LayoutInflater.from(this.context).inflate(R.layout.item_new_way,null);
			viewholder = new ViewHolder();
			viewholder.edit_way=(EditText)convertView.findViewById(R.id.edit_way);
			viewholder.edit_number=(EditText)convertView.findViewById(R.id.edit_number);
			viewholder.iv_delete=(ImageButton)convertView.findViewById(R.id.iv_delete_item);
			viewholder.iv_add=(ImageButton)convertView.findViewById(R.id.iv_add_way);
			convertView.setTag(viewholder);
		}else{
			viewholder = (ViewHolder)convertView.getTag();
		}
		setData(position);
		return convertView;
	}
	private void setData(int posi){
		//首先设置监听器
		viewholder.iv_delete.setClickable(false);
		viewholder.iv_delete.setOnClickListener(new DeleteListener(posi));		
		viewholder.iv_add.setOnClickListener(new AddListener());
		viewholder.edit_way.setText(data.get(posi).get("way"));
		viewholder.edit_way.addTextChangedListener(new EditWatcher(posi, true));
		viewholder.edit_number.setText(data.get(posi).get("number"));
		viewholder.edit_number.addTextChangedListener(new EditWatcher(posi, false));
	}
	private class ViewHolder{
		EditText edit_way,edit_number;
		ImageButton iv_delete,iv_add;
	}
	
	//按下添加按钮
	private void onClickAdd(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("way", "");
		map.put("number", "");
		data.add(map);
		this.notifyDataSetChanged();
		System.out.println("点击添加");
	}
	//按下删除按钮,删除数据，更新界面
	private void onClickDelete(int posi){
		data.remove(posi);
		if(data.size()==0){
			Map<String,String> map=new HashMap<String,String>();
			map.put("way", "");
			map.put("number", "");
			data.add(map);
		}
		this.notifyDataSetChanged();
	}
	
	//自定义的删除按钮的监听器，参数是第几条的位置
	private class DeleteListener implements OnClickListener{
		int posi=-1;
		public DeleteListener(int position){
			this.posi=position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onClickDelete(posi);
		}
	}
	private class AddListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onClickAdd();
		}
	}
	//编辑框的监视器
	private class EditWatcher implements TextWatcher{
		int posi=-1;
		boolean way=true;
		public EditWatcher(int position,boolean flag) {
			this.posi=position;
			this.way=flag;
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			{
				data.get(posi).put(way==true?"way":"number", s.toString());
			}
		}
	}
	//判断现在的存储方式是不是已经填了
	private boolean judgeHasWay(String key){
		if(data.size()==1){
			return false;
		}
		for(Map<String,String> map:data){
			if(map.get("way").equals(key)){
				return true;
			}
		}
		return false;
	}
}
