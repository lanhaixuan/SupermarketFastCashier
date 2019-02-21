package com.guo.staticm;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class StaticMethod {
	/**
	 * 判断网路
	 * 
	 * @param context
	 * @param runnable
	 * @return 调用此方法 context 参数需要填写 (类名.this)不然会报错 具体原因不明
	 */
	public static boolean Ifwifi(Context context) {

		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = con.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			Toast.makeText(context, "当前网络连接失败！", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 判断网路 若需要启动线程调用此方法
	 * 
	 * @param context
	 * @param runnable
	 * @return 调用此方法 context 参数需要填写 (类名.this)不然会报错 具体原因不明
	 */
	public static void Ifwifi(Context context, Runnable runnable) {

		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = con.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			Toast.makeText(context, "网络中断了哦！", Toast.LENGTH_SHORT).show();
			return;
		} else {
			new Thread(runnable).start();
		}

	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// 如果是根据条件传值 Where 先put单独JSONObject 然后再add JSONArray集合
			// 最后全部pu到json
			try {
				JSONObject whe = new JSONObject();
				JSONObject jb = new JSONObject();
				JSONArray where = new JSONArray();
				whe.put("activitiesCityId", "4201");
				where.put(whe);
				jb.put("TableName", "pre_common_City_activities");
				jb.put("Fields", "");
				jb.put("Where", where);

				// GetDataByW = as.GetDataByW(jb.toString(), "同城列表");
				// DataList = GetDataByW.getGetDataByW();
				// handlerData.sendEmptyMessage(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	//
}
