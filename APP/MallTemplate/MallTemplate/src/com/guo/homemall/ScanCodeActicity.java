package com.guo.homemall;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.guo.malltemplate.R;
import com.sitech.oncon.barcode.camera.CaptureActivity;

public class ScanCodeActicity extends Activity {
	private String ScanCodeID;
	private List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
	private ImageView iv_iamge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scancode_activity);
		init();
		iv_iamge = (ImageView) findViewById(R.id.iv_iamge);
		iv_iamge.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				init();
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		Intent it = new Intent();
		it.setClass(ScanCodeActicity.this, CaptureActivity.class);
		startActivityForResult(it, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && data != null) {
			ScanCodeID = data.getStringExtra("NewStringInIntent");
			getServerGoods();
		} else {
			Toast.makeText(ScanCodeActicity.this, "未识别到数据！", Toast.LENGTH_SHORT)
					.show();
		}

	}

	private void setGoods(JSONArray response) {
		// TODO Auto-generated method stub
		for (int i = 0; i < response.length(); i++) {
			try {
				JSONObject json = response.getJSONObject(i);

				if (json.getString("isbn").equals(ScanCodeID)) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("url", json.getString("url"));
					map.put("name", json.getString("name"));
					map.put("price", json.getString("price"));
					map.put("isbn", json.getString("isbn"));
					map.put("address", json.getString("address"));
					map.put("image", json.getString("image"));
					mList.add(map);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (mList.size() > 0) {
			Intent it = new Intent();
			it.putExtra("mList", (Serializable) mList.get(0));
			it.putExtra("typ", "2");
			it.setClass(ScanCodeActicity.this, DetailsActivity.class);
			startActivity(it);
		} else {
			Toast.makeText(ScanCodeActicity.this, "未识别到数据！", Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * 获取商品数据
	 * 
	 * @return
	 */
	public void getServerGoods() {

		RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

		JsonArrayRequest request = new JsonArrayRequest(
				"http://47.94.80.89/goods/", new Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						System.out.println(response.toString());
						setGoods(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.toString(), error);
					}
				}) {
			protected Response<JSONArray> parseNetworkResponse(
					NetworkResponse response) {
				JSONArray JSONArray;
				try {
					JSONArray = new JSONArray(
							new String(response.data, "UTF-8"));
					return Response.success(JSONArray,
							HttpHeaderParser.parseCacheHeaders(response));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return Response.error(new ParseError(e));
				} catch (JSONException e) {
					e.printStackTrace();
					return Response.error(new ParseError(e));
				}
			}
		};

		queue.add(request);
	}
}
