package com.shang.moneymanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.main.InFragment;
import com.main.OutFragment;
import com.main.QueryFragment;
import com.main.SettingFragment;
import com.main.TitleFragment;
import com.main.TitleFragment.OnClickMenu;
import com.main.TitleFragment.OnClickMenuItem;

@SuppressLint("NewApi") public class MainActivity extends Activity implements OnClickMenuItem,OnClickMenu{
	public TitleFragment frag_title=null;
	private InFragment frag_in=null;
	private OutFragment frag_out=null;
	private QueryFragment frag_diary=null;
	private SettingFragment frag_set=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  try{
        	Intent intent=getIntent();
			int result=intent.getIntExtra("flag", -1);
			if(result==-1){//如果这是由另一个avtivity床
				
			}else{
				doClick(result);
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
        initViews();
    }



    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try{
			int result=data.getIntExtra("flag", 1);
			doClick(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	@Override
	public void doClickMenu() {
		// TODO Auto-generated method stub
//		frag_title.img_back.setVisibility(View.GONE);
//		System.out.println("main:"+frag_title.img_back.getVisibility());
	}



	/**
     * 以下是覆盖的诶activity的方法
     */
	@Override
	public void doClick(int posi) {
		// TODO Auto-generated method stub
		switch (posi) {
		case 1:
			onClickIn();
			break;
		case 2:
			onClickOut();
			break;
		case 3:
			onClickDiary();
			break;
		case 4:
			onClickSetting();
			break;
		case 5:
			onClickBack();
			break;
		case 6:
			onClickMore();
			break;
		default:
			break;
		}
	}
    /**
     * 以下是自己写的方法，在上面被调用
     */
    private void initViews(){
    	frag_title=new TitleFragment();
    	FragmentTransaction ft = getFragmentManager().beginTransaction();//得到一个能操作fragment的事务
    	ft.add(R.id.frame_title, frag_title,"title");
    	frag_in=new InFragment();
    	ft.add(R.id.frame_content, frag_in,"in");
    	ft.commit();
   }
    //点击进钱的按钮后
    @SuppressLint("NewApi") public void onClickIn(){
    	frag_title.rotateMenu(1);
    	if(frag_in==null){
    		frag_in=new InFragment();
    	}
    	FragmentTransaction ft = getFragmentManager().beginTransaction();//得到一个能操作fragment的事务
//    	ft.setCustomAnimations(R.anim.zoom_enter, 0, 0, R.anim.zoom_exit);
    	ft.replace(R.id.frame_content, frag_in,"in");
    	ft.commit();
    }
    //点击出钱的按钮后
    @SuppressLint("NewApi") public void onClickOut(){
    	frag_title.rotateMenu(2);
    	if(frag_out==null){
    		frag_out=new OutFragment();
    	}
    	FragmentTransaction ft = getFragmentManager().beginTransaction();//得到一个能操作fragment的事务
//    	ft.setCustomAnimations(R.anim.zoom_enter, 0, 0, R.anim.zoom_exit);
    	ft.replace(R.id.frame_content, frag_out,"out");
    	ft.commit();
    	
    }
    //点击日志的按钮后
    public void onClickDiary(){
    	frag_title.rotateMenu(3);
    	if(frag_diary==null){
    		frag_diary=new QueryFragment();
    	}
    	FragmentTransaction ft = getFragmentManager().beginTransaction();//得到一个能操作fragment的事务
//    	ft.setCustomAnimations(R.anim.zoom_enter, 0, 0, R.anim.zoom_exit);
    	ft.replace(R.id.frame_content, frag_diary,"diary");
    	ft.commit();
    }
    //点击设置的按钮后
    @SuppressLint("NewApi") public void onClickSetting(){
    	frag_title.rotateMenu(4);
    	Log.i("debug","onclicksetting");
    	if(frag_set==null){
    		frag_set=new SettingFragment();
    	}
    	FragmentTransaction ft = getFragmentManager().beginTransaction();//得到一个能操作fragment的事务
//    	ft.setCustomAnimations(R.anim.zoom_enter, 0, 0, R.anim.zoom_exit);
    	ft.replace(R.id.frame_content, frag_set,"setting");
    	ft.commit();
    }
    public void onClickBack(){
    	
    }
    public void onClickMore(){
    	frag_title.setVisiOfMore(View.VISIBLE);
    }
}
