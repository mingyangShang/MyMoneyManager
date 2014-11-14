package com.main;

import help.ToggleListener;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.shang.manager.PreManager;
import com.shang.moneymanager.R;

public class SettingFragment extends Fragment implements OnClickListener{
	private View view;
	private ToggleButton toggle_start,toggle_pass;
	private ImageButton image_start,image_pass;
	final int FLAG_IN_ITEM=1,FLAG_OUT_ITEM=2,FLAG_WAY=0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_set, container, false);
		initViews();
		return view;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.query_leave:
			Intent inte=new Intent(getActivity(),SettingActviity.class);
			inte.putExtra("flag",FLAG_WAY);
			getActivity().startActivityForResult(inte,1);//(intent);
			break;
		case R.id.query_in_item://进入到进钱的项目的界面中去
			Intent intent=new Intent(getActivity(),SettingActviity.class);
			intent.putExtra("flag",FLAG_IN_ITEM);
			getActivity().startActivityForResult(intent,1);//(intent);
			break;
		case R.id.query_out_item:
			Intent intent2=new Intent(getActivity(),SettingActviity.class);
			intent2.putExtra("flag",FLAG_OUT_ITEM);
			getActivity().startActivityForResult(intent2, 2);//(intent2);
			break;
		case R.id.toggle_start:case R.id.toggleButton_start:
			toggle_start.toggle();
			break;
		case R.id.toggle_pass:case R.id.toggleButton_pass:
			if(toggle_pass.isChecked()){//如果现在有密码的话，点击相当于关闭密码
				toggle_pass.toggle();
			}else{//如果现在没有密码的话，点击相当于设置密码，
				//当返回到这个activity的时候，如果他设置成功了密码，那么改变toggle_pass的状态
				Intent inten=new Intent(getActivity(),CreateGesturePasswordActivity.class);
				startActivityForResult(inten, 0);
				break;
			}
			/*toggle_pass.toggle();
			if(toggle_pass.isChecked()){
				  LinearLayout layout_setPass=(LinearLayout)view.findViewById(R.id.layout_set_pass);
				  layout_setPass.setVisibility(View.VISIBLE);
			}else{
				 LinearLayout layout_setPass=(LinearLayout)view.findViewById(R.id.layout_set_pass);
				  layout_setPass.setVisibility(View.GONE);
			}
			break;*/
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try{
			boolean pass_seted=data.getBooleanExtra("flag", false);
			if(pass_seted){
				toggle_pass.toggle();
			}
		}catch(Exception e){
			
		}
	}

	private void initViews(){
		RelativeLayout layout_leave=(RelativeLayout)view.findViewById(R.id.query_leave);
		layout_leave.setOnClickListener(this);
		RelativeLayout layout_in=(RelativeLayout)view.findViewById(R.id.query_in_item);
		layout_in.setOnClickListener(this);
		RelativeLayout layout_out=(RelativeLayout)view.findViewById(R.id.query_out_item);
		layout_out.setOnClickListener(this);
		
		image_start=(ImageButton)view.findViewById(R.id.toggleButton_start);
		image_start.setOnClickListener(this);
		toggle_start=(ToggleButton)view.findViewById(R.id.toggle_start);
		toggle_start.setOnCheckedChangeListener(new ToggleListener(getActivity(),"启动界面",toggle_start,image_start));  
		
		image_pass=(ImageButton)view.findViewById(R.id.toggleButton_pass);
		image_pass.setOnClickListener(this);
		toggle_pass=(ToggleButton)view.findViewById(R.id.toggle_pass);
		toggle_pass.setOnCheckedChangeListener(new ToggleListener(getActivity(),"手势解锁",toggle_pass,image_pass));
		
		//根据配置检查togglebutton的状态
		boolean start=PreManager.get(getActivity(), PreManager.START, false);
		boolean pass=PreManager.get(getActivity(), PreManager.PASS, false);
		initToggleButton(start, R.id.toggleButton_start);
		initToggleButton(pass, R.id.toggleButton_pass);
	}
	//根据设置初始化togglebutton的状态
	private void initToggleButton(boolean yes,int id){
		switch(id){
		case R.id.toggleButton_start:
			toggleStart(yes);
			break;
		case R.id.toggleButton_pass:
			togglePass(yes);
			break;
		}
	}
	//转换start的状态
	private void toggleStart(boolean yes){
		toggle_start.setChecked(yes);  
	        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) image_start 
	                .getLayoutParams();
	        if (yes) { // 如果是自动播放  
	            // 调整位置  
	            params1.addRule(RelativeLayout.ALIGN_RIGHT, -1);  
	            params1.addRule(RelativeLayout.ALIGN_LEFT,  
	                    R.id.toggleButton_start);  
	            image_start.setLayoutParams(params1);  
	            image_start.setImageResource(R.drawable.progress_thumb_selector);  
	            toggle_start.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);  
	        } else {  
	            // 调整位置  
	            params1.addRule(RelativeLayout.ALIGN_RIGHT, R.id.toggle_start);  
	            params1.addRule(RelativeLayout.ALIGN_LEFT, -1);  
	            image_start.setLayoutParams(params1);  
	            image_start.setImageResource(R.drawable.progress_thumb_off_selector);  
	            toggle_start.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);  
	        }  
	}
	//转换手势解锁的toggle状态
	private void togglePass(boolean yes){
		toggle_pass.setChecked(yes);  
	        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) image_pass 
	                .getLayoutParams(); 
	        if (yes) { // 如果是自动播放  
	            // 调整位置  
	            params1.addRule(RelativeLayout.ALIGN_RIGHT, -1);  
	            params1.addRule(RelativeLayout.ALIGN_LEFT,  
	                    R.id.toggleButton_pass);  
	            image_pass.setLayoutParams(params1);  
	            image_pass.setImageResource(R.drawable.progress_thumb_selector);  
	            toggle_pass.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);  
	        } else {  
	            // 调整位置  
	            params1.addRule(RelativeLayout.ALIGN_RIGHT, R.id.toggle_pass);  
	            params1.addRule(RelativeLayout.ALIGN_LEFT, -1);  
	            image_pass.setLayoutParams(params1);  
	            image_pass.setImageResource(R.drawable.progress_thumb_off_selector);  
	            toggle_pass.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);  
	        }  
	}
	
}
