package com.main;


import help.DataItem;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shang.adapter.QueryAdapter;
import com.shang.manager.DatabaseManager;
import com.shang.moneymanager.R;

public class QueryFragment extends Fragment implements OnItemClickListener,IXListViewListener{
	private View view=null;
	private ViewPager viewPager = null;
    private int currIndex = 0;// 当前页卡编号  
	private View view_in,view_out,view_all;//两个tab对应的view
	private XListView list_all,list_out,list_in;//每个view 的listView
	private List<View> views;// Tab页面列表  
	private QueryAdapter ada_all,ada_in,ada_out;
	private List<DataItem> data_all,data_in,data_out;
	private TextView tv_all,tv_in,tv_out;
	private ImageView imageView;// 动画图片 
	private int index=0;
	private int offset = 0;// 动画图片偏移量  
	private int bmpW;// 动画图片宽度  
	private boolean first=false;//是否是第一次进入

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		data_all=new ArrayList<DataItem>();
		data_in=new ArrayList<DataItem>();
		data_out=new ArrayList<DataItem>();
		views = new ArrayList<View>();
		 //获得数据适配器
        ada_all=new QueryAdapter(getActivity(),data_all);
		ada_in=new QueryAdapter(getActivity(),data_in);
		ada_out=new QueryAdapter(getActivity(),data_out);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_query, container, false);		
		InitTextView();
		InitImageView(); 
		initViewPager();
		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 //设置数据
		if(first==false){
			new LoadMoreTask().execute();
			first=true;
		}
	}
	 private void InitImageView() {  
	        imageView= (ImageView) view.findViewById(R.id.cursor);  
	        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度  
	        DisplayMetrics dm = new DisplayMetrics();  
	        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);  
	        int screenW = dm.widthPixels;// 获取分辨率宽度  
	        offset = (screenW / 3 - bmpW) / 2;// 计算偏移量  
	        Matrix matrix = new Matrix();  
	        matrix.postTranslate(offset, 0);  
	        imageView.setImageMatrix(matrix);// 设置动画初始位置  
	    }  
	private void InitTextView() {  
        tv_in = (TextView) view.findViewById(R.id.text_in);  
        tv_all = (TextView) view.findViewById(R.id.text_all);  
        tv_out = (TextView) view.findViewById(R.id.text_out);  
  
        tv_in.setOnClickListener(new MyOnClickListener(0));  
        tv_all.setOnClickListener(new MyOnClickListener(1));  
        tv_out.setOnClickListener(new MyOnClickListener(2));  
    }  
	/*//初始化radobutton
	 private void initRadios(){
		 	radio_in = (RadioButton)view.findViewById(R.id.title_in);
		 	radio_in.setOnCheckedChangeListener(new MyOnClickListener());
		  	
		  	radio_all = (RadioButton)view.findViewById(R.id.title_all);
		  	radio_all.setOnCheckedChangeListener(new MyOnClickListener());
		  	
		  	radio_out = (RadioButton)view.findViewById(R.id.title_out);
		  	radio_out.setOnCheckedChangeListener(new MyOnClickListener());
	 }*/
	 /**  
	     *      
	     * 头标点击监听 3 */  
	    private class MyOnClickListener implements OnClickListener{  
	        private int index=0;  
	        public MyOnClickListener(int i){  
	            index=i;  
	        }  
	        public void onClick(View v) {  
	            viewPager.setCurrentItem(index);              
	        }  
	          
	    }  
	/*//定义一个用来监听textView的Listener
	  private class MyOnClickListener implements OnCheckedChangeListener{  
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				switch(buttonView.getId()){
				case R.id.title_all:
					if(isChecked){
						viewPager.setCurrentItem(1);
					}
					break;
				case R.id.title_in:
					if(isChecked){
						viewPager.setCurrentItem(0);
					}
					break;
				case R.id.title_out:
					if(isChecked){
						viewPager.setCurrentItem(2);
					}
					break;
				}
			}				        
	    } */
	  
	  @Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	}
	  //获得数据
	  private void getData(){
		  
		  /*data_all=new ArrayList<Map<String,String>>();
		  for(int i=0;i<10;++i){
			  Map<String,String> map=new HashMap<String,String>();
			  map.put("item", "吃饭");
			  map.put("leave", "1212");
			  map.put("time", "2012-21-12");
			  map.put("number", "-121");
			  data_all.add(map);
		  }*/
		  data_in=data_out=data_all;
		  
	  }
	//初始化viewpager
	  private void initViewPager() {
			viewPager = (ViewPager) view.findViewById(R.id.pager);
			 LayoutInflater inflater=getActivity().getLayoutInflater();  
		        view_all=inflater.inflate(R.layout.list_all, null);
		        view_in=inflater.inflate(R.layout.list_in, null); 
		        view_out=inflater.inflate(R.layout.list_out,null);
		        list_all = (XListView)view_all.findViewById(R.id.list_all);
		        list_out = (XListView)view_out.findViewById(R.id.list_out);
		        list_in = (XListView)view_in.findViewById(R.id.list_in);
		        //设置滑动监听器
		      /*  list_all.setOnScrollListener(new HideBarScrollListener(onScroll));
		        list_out.setOnScrollListener(new HideBarScrollListener(onScroll));

		        list_in.setOnScrollListener(new HideBarScrollListener(onScroll));
		        list_in.setOnScrollListener(this);*/
		        //设置adapter，并传数据
		    /*    list_all.setAdapter(ada_contact);
		        list_in.setAdapter(ada_disu);
		        list_out.setAdapter(ada_group);*/
		        //设置条目的点击事件的监听器
		        list_all.setOnItemClickListener(this);
		        list_in.setOnItemClickListener(this);
		        list_out.setOnItemClickListener(this);
		       
		        //设置不可以下滑
		        list_all.setPullRefreshEnable(false);//Enable(true);
		        list_in.setPullRefreshEnable(false);//(true);
		        list_out.setPullRefreshEnable(false);
		        //设置可以上拉
		        list_all.setPullLoadEnable(true);
		        list_in.setPullLoadEnable(true);
		        list_out.setPullLoadEnable(true);
		        //设置滑动的监听器
		        list_all.setXListViewListener(this);
		        list_in.setXListViewListener(this);
		        list_out.setXListViewListener(this);
		        
		       
				
				//设置数据适配器
		        list_all.setAdapter(ada_all);
		        list_in.setAdapter(ada_in);
		        list_out.setAdapter(ada_out);

		     /*   //设置长按事件的监听器
		        list_all.setOnItemLongClickListener(this);
		        list_in.setOnItemLongClickListener(this);
		        list_out.setOnItemLongClickListener(this);*/
		        //在views添加view
		        views.add(view_in);  
		        views.add(view_all);  
		        views.add(view_out);
		        //初始化viewpager
		        viewPager.setAdapter(new MyViewPagerAdapter(views)); 
		        viewPager.setCurrentItem(0);  
		        viewPager.setOnPageChangeListener(new MyOnPageChangeListener()); 
		}
	 /* private void onLoad() {
		  switch(flag){
		  case 0:
				list_in.stopRefresh();
				list_in.stopLoadMore();
				list_in.setRefreshTime("刚刚");
			  break;
		  case 1:
				list_all.stopRefresh();
				list_all.stopLoadMore();
				list_all.setRefreshTime("刚刚");
			  break;
		  case 2:
			    list_out.stopRefresh();
				list_out.stopLoadMore();
				list_out.setRefreshTime("刚刚");
			  break;
		  }
		}*/
	  private void onLoad(){
		  list_in.stopRefresh();
		  list_in.stopLoadMore();
		  list_in.setRefreshTime("刚刚");
		  list_all.stopRefresh();
		  list_all.stopLoadMore();
		  list_all.setRefreshTime("刚刚");
		  list_out.stopRefresh();
		  list_out.stopLoadMore();
		  list_out.setRefreshTime("刚刚");
	  }
	  public class MyOnPageChangeListener implements OnPageChangeListener{  
		  
	        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量  
	        int two = one * 2;// 页卡1 -> 页卡3 偏移量  
	        public void onPageScrollStateChanged(int arg0) {  
	        }  
	  
	        public void onPageScrolled(int arg0, float arg1, int arg2) {  
	        }  
	  
	        public void onPageSelected(int arg0) {  
	            Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);//显然这个比较简洁，只有一行代码。  
	            currIndex = arg0;  
	            animation.setFillAfter(true);// True:图片停在动画结束位置  
	            animation.setDuration(300);  
	            imageView.startAnimation(animation);  
	        }  
	          
	    }  
	/*  //定义一个用来监听viewpager中view变化的listener
	  public class MyOnPageChangeListener implements OnPageChangeListener{  
	        public void onPageScrollStateChanged(int arg0) {  	              	              
	        }  
	        public void onPageScrolled(int arg0, float arg1, int arg2) {       
	        }  
	        public void onPageSelected(int arg0) {   
	        	switch(arg0){
	        	case 0:
	        		radio_in.setChecked(true);
					radio_all.setChecked(false);
					radio_out.setChecked(false);	
					currIndex=0;
	        		break;
	        	case 1:
	        		radio_in.setChecked(false);
	        		radio_all.setChecked(true);
					radio_out.setChecked(false);
					currIndex=1;
	        		break;
	        	case 2:
	        		radio_in.setChecked(false);
					radio_all.setChecked(false);
					radio_out.setChecked(true);
					currIndex=2;
	        		break;
	        	}
	        }           
	    }*/
	  //定义一个用来监听viewpager中国的view变化的adapter
	  public class MyViewPagerAdapter extends PagerAdapter{  
	        private List<View> mListViews;  
	        public MyViewPagerAdapter(List<View> mListViews) {  
	            this.mListViews = mListViews;  
	        }  
	        @Override  
	        public void destroyItem(ViewGroup container, int position, Object object)   {     
	            container.removeView(mListViews.get(position));  
	        }  
	        @Override  
	        public Object instantiateItem(ViewGroup container, int position) {            
	             container.addView(mListViews.get(position), 0);  
	             return mListViews.get(position);  
	        }  
	        @Override  
	        public int getCount() {           
	            return  mListViews.size();  
	        }  
	        @Override  
	        public boolean isViewFromObject(View arg0, Object arg1) {             
	            return arg0==arg1;  
	        }  
	    }

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new LoadMoreTask().execute();
	}  
	private class LoadMoreTask extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<DataItem> list=DatabaseManager.queryAfter30(getActivity(),index);
			index++;
			data_all.addAll(list);
			//方便调试
			for(DataItem item:list){
				if(item.dir.equals("in")){
					data_in.add(item);
				}else{
					data_out.add(item);
				}
			}
			if(list.size()<30){
				return false;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result==false){
				Toast.makeText(getActivity(), "没有数据了", 300).show();
			}
			ada_all.notifyDataSetChanged();
			ada_in.notifyDataSetChanged();
			ada_out.notifyDataSetChanged();
			onLoad();
		}
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		index=0;
	}
	
}
