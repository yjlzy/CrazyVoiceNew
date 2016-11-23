package com.crazyvoice.util;

import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.crazyvoice.db.CrazyVoiceDB;
import com.crazyvoice.model.Category;
import com.crazyvoice.model.Channel;
import com.crazyvoice.model.Program;

/**
 * 
 * 处理服务器返回数据
 */
public class Utility {
	public static List<Program> programs;

	/*
	 * 处理频道分类信息
	 */
	public synchronized static boolean handleCategoryResponse(
			CrazyVoiceDB crazyVoiceDB, String reponse) {
		try {
			JSONObject object = new JSONObject(reponse);
			Log.d("CrazyVoice_Utility_Category", object.get("error_code") + ":"
					+ object.get("reason"));
			if (object.getInt("error_code") == 0) {
				String result = object.getString("result");
				Log.d("CrazyVoice_Utility_Category", result);
				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String id = jsonObject.getString("id");
					String name = jsonObject.getString("name");
					Category category = new Category();
					category.setCategoryCode(id);
					category.setCategoryName(name);
					crazyVoiceDB.saveCategory(category);
				}
				return true;
			} else {
				Log.d("CrazyVoice_Utility_Category", object.get("error_code")
						+ ":" + object.get("reason"));
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	/*
	 * 处理频道信息
	 */
	public static boolean handleChannelResponse(CrazyVoiceDB crazyVoiceDB,
			String reponse) {
		try {
			JSONObject object = new JSONObject(reponse);
			Log.d("CrazyVoice_Utility_Channel", object.get("error_code") + ":"
					+ object.get("reason"));
			if (object.getInt("error_code") == 0) {
				String result = object.getString("result");
				Log.d("CrazyVoice_Utility_Channel", result);
				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String rel = jsonObject.getString("rel");
					String channelName = jsonObject.getString("channelName");
					String pid = jsonObject.getString("pId");
					// String url=jsonObject.getString("url");
					Channel channel = new Channel();
					channel.setChannelCode(rel);
					channel.setCategoryId(Integer.parseInt(pid));
					channel.setChannelName(channelName);
					// channel.setUrl(channelName);
					crazyVoiceDB.saveChannel(channel);
				}
				return true;
			} else {
				Log.d("CrazyVoice_Utility_Channel", object.get("error_code")
						+ ":" + object.get("reason"));
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;

	}

	/*
	 * 处理节目信息
	 */
	public static boolean handleProgramResponse(CrazyVoiceDB crazyVoiceDB,
			String reponse) {
		try {
			JSONObject object = new JSONObject(reponse);
			Log.d("CrazyVoice_Utility_Program", object.get("error_code") + ":"
					+ object.get("reason"));
			if (object.getInt("error_code") == 0) {
				String result = object.getString("result");
				Log.d("CrazyVoice_Utility_Program", result);
				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String cName = jsonObject.getString("cName");
					String pName = jsonObject.getString("pName");
					String pUrl = jsonObject.getString("pUrl");
					String time = jsonObject.getString("time");
					Program program = new Program();
					program.setcName(cName);
					program.setpName(pName);
					program.setpUrl(pUrl);
					program.setTime(time);
					programs.add(program);
				}
				return true;
			} else {
				Log.d("CrazyVoice_Utility_Channel", object.get("error_code")
						+ ":" + object.get("reason"));
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}
	
	/*
	 * 返回当前正在播放的节目
	 */
	public static String getNowPrograme(List<Program> programs){
		
		
		return null;
	}
}
