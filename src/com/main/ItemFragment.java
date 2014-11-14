package com.main;

import help.DataItem;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.shang.adapter.QueryAdapter;
import com.shang.manager.DatabaseManager;
import com.shang.moneymanager.R;

public class ItemFragment extends Fragment implements OnItemClickListener,IXListViewListener{
	private View view;
	private List<DataItem> item;
	private QueryAdapter ada_item;
	private boolean in;
	private String itemName;
	private int index=0;
	private XListView listview;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle=getArguments();
		in=bundle.getBoolean("in");
		itemName=bundle.getString("itemName");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.list_in,container,false);
		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(item==null){
			item=new ArrayList<DataItem>();
			initListView();
			//开始执行线程
			new GetItemDataTask().execute();
		}
	}

	private void initListView(){
		ada_item=new QueryAdapter(getActivity(), item);
		listview=(XListView)(view.findViewById(R.id.list_in));
		listview.setAdapter(ada_item);
		listview.setPullLoadEnable(false);
		listview.setPullRefreshEnable(false);
		/*this.setListAdapter(ada_item);
		((XListView)this.getListView()).setPullLoadEnable(false);
		((XListView)this.getListView()).setPullRefreshEnable(false);*/
//		((XListView)this.getListView()).setOnItemClickListener(this);
	}
	private class GetItemDataTask extends AsyncTask<Void, Void, List<DataItem>>{

		@Override
		protected List<DataItem> doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			List<DataItem> list=DatabaseManager.queryItemsAfter30(getActivity(), itemName, in, index);
			index++;
//			addMoreItem(list);
			return list;
			/*if(list.size()<30){
				return false;
			}
			return true;*/
		}

		@Override
		protected void onPostExecute(List<DataItem> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result==null){
				Toast.makeText(getActivity(), "没有数据了", 300).show();
				return ;
			}
			item.addAll(result);
			if(result.size()<30){
				Toast.makeText(getActivity(), "没有数据了", 300).show();
			}
			ada_item.notifyDataSetChanged();
		}
		
	}
	/*//将加载的更多的item的项加入到数据item中
	private void addMoreItem(List<DataItem> list){
		for(DataItem data:list){
			Map<String,String> map=new HashMap<String,String>();
			map.put(arg0, arg1)
		}
	}*/
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new GetItemDataTask().execute();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
