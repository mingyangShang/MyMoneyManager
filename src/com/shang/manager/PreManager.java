package com.shang.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreManager {
	public static final String START = "START";	//手势密码
	public static final String PASS = "PASS";//手势密码
	public static final String START_FIRST="START_FIRST";//是否是第一次进入程序
	/**
	 * 获取配置
	 * @param context
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static boolean get(Context context, String name, boolean defaultValue) {
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		boolean value = prefs.getBoolean(name, defaultValue);
		return value;
	}
	public static String get(Context context, String name, String defaultValue) {
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String value = prefs.getString(name, defaultValue);
		return value;
	} 
	
	/**
	 * 保存用户配置
	 * @param context
	 * @param name
	 * @param value
	 * @return
	 */
	public static boolean set(Context context, String name, boolean value) {
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean(name, value);
		return editor.commit();	//提交
	}

	public static boolean set(Context context, String name, String value) {
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putString(name, value);
		return editor.commit();	//提交
	}
	//存储数据
	public static boolean saveData(Context context,String name,List<String> data){
		SharedPreferences sp=context.getSharedPreferences(name,Context.MODE_PRIVATE);
		Editor editor=sp.edit();
		int size=data.size();
		editor.putInt("size",size);//先放一个变量，知道这次将放多少个数据
		for(int i=0;i<size;++i){
			editor.putString(String.valueOf(i), data.get(i));//一个一个地数据
		}
		editor.commit();
		return true;
	}
	//获得在shared中存数的数据，也是根据shared的名字也就是参数中的那么来判断
	public static ArrayList<String> getData(Context context,String name){
		ArrayList<String> list=new ArrayList<String>();
		SharedPreferences sp=context.getSharedPreferences(name,Context.MODE_PRIVATE);
		int size=sp.getInt("size",0);
		for(int i=0;i<size;++i){
			list.add(sp.getString(String.valueOf(i),""));
		}
		return list;
	}
	//存储钱的存储方式和金额
	public static void saveWayData(Context con,Map<String,String> data){
		SharedPreferences sp=con.getSharedPreferences("shared_way",Context.MODE_PRIVATE);
		Editor editor=sp.edit();
		Set<String> data_set=data.keySet();
		for(String s:data_set){
			editor.putString(s,data.get(s));
		}
		editor.commit();
	}
	//获取钱的所有的存储方式
	public static Map<String,String> getWayData(Context con){
		SharedPreferences sp=con.getSharedPreferences("shared_way",Context.MODE_PRIVATE);
		return (Map<String, String>) sp.getAll();
	}
	//存储进钱或者出钱的项目
	private static void saveItemData(Context con,String item_name,Set<String> data){
		SharedPreferences sp=con.getSharedPreferences("shared_item",Context.MODE_PRIVATE);
		Editor editor=sp.edit();
		editor.putStringSet(item_name, data);
		editor.commit();
	}
	//存储进钱的项目
	public static void saveInItem(Context con,Set<String> data){
		saveItemData(con, "shared_in", data);
	}
	//存储出钱的项目
	public static void saveOutItem(Context con,Set<String> data){
		saveItemData(con, "shared_out", data);
	}
	//获得进钱或者出钱的项目
	public static Set<String> getItemData(Context con,String key){
		SharedPreferences sp=con.getSharedPreferences("shared_item",Context.MODE_PRIVATE);
		return sp.getStringSet(key,new HashSet<String>());
	}
	//获得进钱的项目
	public static Set<String> getInItem(Context con){
		SharedPreferences sp=con.getSharedPreferences("shared_item",Context.MODE_PRIVATE);
		return sp.getStringSet("shared_in",new HashSet<String>());
	}
	//获得出钱的项目
	public static Set<String> getOutItem(Context con){
		SharedPreferences sp=con.getSharedPreferences("shared_item",Context.MODE_PRIVATE);
		return sp.getStringSet("shared_out",new HashSet<String>());
	}
}
