package com.main;

import help.MoneyNumberComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.maxwin.view.XListView;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.shang.adapter.ItemAdapter;
import com.shang.manager.DatabaseManager;
import com.shang.manager.PreManager;
import com.shang.moneymanager.MainActivity;
import com.shang.moneymanager.R;

public class SettingInFragment extends ListFragment{
	private View view;
//	private Set<String> item; 
	private List<Map<String,String>> item_money;
	private ItemAdapter ada_item;
//	private XListView list;
	
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
		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(item_money==null){
			item_money=new ArrayList<Map<String,String>>();
			initListView();
			//开始执行线程
			new GetDataTask().execute();
		}
	}
	//初始化listview
	private void initListView(){
		ada_item=new ItemAdapter(getActivity(), item_money);
		/*list=(XListView)view.findViewById(R.id.list);
		list.setPullLoadEnable(false);
		list.setPullRefreshEnable(false);
		list.setOnItemClickListener(this);
		list.setAdapter(ada_item);*/
		setListAdapter(ada_item);
		((XListView)this.getListView()).setPullLoadEnable(false);
		((XListView)this.getListView()).setPullRefreshEnable(false);
//		((XListView)this.getListView()).setOnItemClickListener(this);
	}
	private class GetDataTask extends AsyncTask<Void, Void, Set<String>>{

		@Override
		protected Set<String> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Set<String> item=PreManager.getInItem(getActivity());
			if(item==null){
				item=new HashSet<String>();
			}
			return item;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Set<String> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			int number=0,number_total=0;
			for(String i:result){
				Map<String,String> map=new HashMap<String,String>();
				map.put("itemName", i);
				number=DatabaseManager.getTotalByItem(getActivity(), i, true);
				number_total+=number;
				map.put("totalMoney", String.valueOf(number));
				item_money.add(map);
			}
			TextView tv=(TextView)view.findViewById(R.id.total_in);
			tv.setText("共收入"+number_total+"元");
			Collections.sort(item_money,new MoneyNumberComparator());
			ada_item.notifyDataSetChanged();
		}
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent intent=new Intent(getActivity(),ItemActivity.class);
		intent.putExtra("itemType", "in");
		intent.putExtra("itemName", item_money.get(position-1).get("itemName"));
		startActivity(intent);
	}
}
