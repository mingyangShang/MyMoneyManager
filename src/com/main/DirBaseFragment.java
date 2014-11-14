package com.main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.amo.demo.wheelview.NumericWheelAdapter;
import com.amo.demo.wheelview.OnWheelChangedListener;
import com.amo.demo.wheelview.WheelView;
//import com.shang.adapter.DropdownAdapter;
import com.shang.manager.DisplayUtils;
import com.shang.manager.PreManager;
import com.shang.moneymanager.R;

public class DirBaseFragment extends Fragment implements OnClickListener{
	public View view=null;
	public Spinner spinner_way=null;
	private ArrayAdapter<String> ada_way=null;
	public Map<String,String> way_data;
	public List<String> way_list;
	public WheelView wv_year,wv_month,wv_day;
	public EditText edit_number,edit_note;
//	public DropdownAdapter ada_way;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWayInitData();
		getItemData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragement_out, container, false);
		initViews();
		showDateTimePicker();
		return view;
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bt_ok:
			onClickOk();
			break;
		case R.id.add_use:
			onClickAddItem();
			break;
		}
	}

	/**
	 * 一下是自己写的方法，在上面被调用
	 */
	//初始化控件并未相应的控件设置监听器
	public void initViews(){
		Button button_ok=(Button)view.findViewById(R.id.bt_ok);
		button_ok.setOnClickListener(this);
		ImageView iv_add_use=(ImageView)view.findViewById(R.id.add_use);
		iv_add_use.setOnClickListener(this);
		ImageView iv_add_res=(ImageView) view.findViewById(R.id.add_res);
		iv_add_res.setOnClickListener(this);
		
		spinner_way=(Spinner)view.findViewById(R.id.spinner_res);
		spinner_way.setAdapter(ada_way);
		
		edit_note=(EditText)view.findViewById(R.id.edit_note);
		edit_number=(EditText)view.findViewById(R.id.edit_number);
		
	}
	//获得编辑的金额
	public String getNumber(){
		EditText edit_number=(EditText)view.findViewById(R.id.edit_number);
		return edit_number.getText().toString();
	}
	//获得编辑的备注
	public String getNote(){
		EditText edit_note=(EditText)view.findViewById(R.id.edit_note);
		return edit_note.getText().toString();
	}
	//获得数据
	private void getWayInitData(){
		way_data=PreManager.getWayData(getActivity());
		Set<String> set=way_data.keySet();
		way_list=new ArrayList<String>();
		for(String s:set){
			way_list.add(s);
		}
//		ada_way = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, way_list);
//		ada_way=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,way_list);
//		ada_way=new DropdownAdapter(getActivity(), way_list);
		ada_way=new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,way_list);

		// 设置下拉列表的风格  
//		ada_way.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		ada_way.setDropDownViewResource(R.layout.item_drop);
	}
	private void showDateTimePicker() {
		final int START_YEAR = 2014, END_YEAR = 2100;
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);


		// 年
		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
		wv_year.setCyclic(true);// 可循环滚动
		wv_year.setLabel("年");// 添加文字
		wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据

		// 月
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setLabel("月");
		wv_month.setCurrentItem(month);

		// 日
		wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(true);
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel("日");
		wv_day.setCurrentItem(day - 1);
		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String
						.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
							.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);
		// 根据屏幕密度来指定选择器字体的大小
		int textSize = (int)((DisplayUtils.getScreenHeight(getActivity()) / 100) * 4);
//		textSize = 12;
		wv_day.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;
	}
	public String getDate(){
		String parten = "00";
		DecimalFormat decimal = new DecimalFormat(parten);
		return ((wv_year.getCurrentItem() + 2014) + "/"
				 + decimal.format((wv_month.getCurrentItem() + 1)) + "/"
				 + decimal.format((wv_day.getCurrentItem() + 1)));
	}
	//获得条目的数据(在子类中实现)
	public void getItemData(){
		
	}
	public void onClickOk(){
		
	}
	public void onClickAddItem(){
		
	} 
}
