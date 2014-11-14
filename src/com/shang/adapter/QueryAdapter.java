package com.shang.adapter;

import help.DataItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shang.moneymanager.R;

public class QueryAdapter extends BaseAdapter{
	private Context context=null;
	private List<DataItem> data=null;
	private ViewHolder viewholder=null;
		public QueryAdapter(Context con,List<DataItem> data) {
			this.context=con;
			this.data=data;
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
				convertView=LayoutInflater.from(this.context).inflate(R.layout.item_query,null);
				viewholder = new ViewHolder();
				viewholder.tv_item=(TextView)convertView.findViewById(R.id.tv_itemName);
				viewholder.tv_leave=(TextView)convertView.findViewById(R.id.tv_leave);
				viewholder.tv_number=(TextView)convertView.findViewById(R.id.tv_number);
				viewholder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
				convertView.setTag(viewholder);
			}else{
				viewholder = (ViewHolder)convertView.getTag();
			}
			setData(position);
			return convertView;
		}
		private void setData(int posi){
			viewholder.tv_item.setText(data.get(posi).item);
			viewholder.tv_leave.setText(data.get(posi).note);//data.get(posi).);
			String suffix=data.get(posi).dir.equals("in")?"+":"-";
			viewholder.tv_time.setText(data.get(posi).time);
			viewholder.tv_number.setText(suffix+data.get(posi).number);
		}
		private class ViewHolder{
			TextView tv_item,tv_leave,tv_time,tv_number;
		}
}
