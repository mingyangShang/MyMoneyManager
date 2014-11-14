package com.shang.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shang.moneymanager.R;

public class ItemAdapter extends BaseAdapter{
private Context context=null;
private List<Map<String,String>> data=null;
private ViewHolder viewholder=null;
//���캯��
public ItemAdapter(Context con,List<Map<String,String>> ways){
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
		convertView=LayoutInflater.from(this.context).inflate(R.layout.item_item,null);
		viewholder = new ViewHolder();
		viewholder.tv_item=(TextView) convertView.findViewById(R.id.tv_itemName);
		viewholder.tv_total=(TextView)convertView.findViewById(R.id.tv_total);
		viewholder.img_enter=(ImageView)convertView.findViewById(R.id.ib_enter);
		convertView.setTag(viewholder);
	}else{
		viewholder = (ViewHolder)convertView.getTag();
	}
	setData(position);
	return convertView;
}
private void setData(int posi){
	viewholder.tv_item.setText(data.get(posi).get("itemName"));
	viewholder.tv_total.setText(data.get(posi).get("totalMoney"));
	viewholder.img_enter.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		}
	});
}
private class ViewHolder{
	TextView tv_item,tv_total;
	ImageView img_enter;
}
}
