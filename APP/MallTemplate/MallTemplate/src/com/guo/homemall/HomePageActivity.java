package com.guo.homemall;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.guo.adapter.ImageAdapter;
import com.guo.malltemplate.R;

public class HomePageActivity extends Activity {
	private ViewPager viewPager;
	private LinearLayout point_group;
	private TextView image_desc;
	private HorizontalScrollView horizontalScrollView;
	private LinearLayout linearLayout;
	private GridView gridview;

	// 图片资源id
	private final int[] images = { R.drawable.img_banner,
			R.drawable.img_banner, R.drawable.img_banner,
			R.drawable.img_banner, R.drawable.img_banner };
	// 图片标题集合
	private final String[] imageDescriptions = { "巩俐不低俗，我就不能低俗",
			"扑树又回来啦！再唱经典老歌引万人大合唱", "揭秘北京电影如何升级", "乐视网TV版大派送", "热血屌丝的反杀" };

	private ArrayList<ImageView> imageList;
	// 上一个页面的位置
	protected int lastPosition = 0;

	// 判断是否自动滚动viewPager
	private boolean isRunning = true;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 执行滑动到下一个页面
			viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			if (isRunning) {
				// 在发一个handler延时
				handler.sendEmptyMessageDelayed(0, 2000);
			}

		};
	};

	private List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> aList = new ArrayList<Map<String, String>>();
	private EditText edttext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage_activity);
		gridview = (GridView) findViewById(R.id.gridview);
		edttext = (EditText) findViewById(R.id.edttext);
		init();
		getServerGoods();
		edttext.addTextChangedListener(mTextWatcher);
		edttext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void init() {
		// TODO Auto-generated method stub
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		point_group = (LinearLayout) findViewById(R.id.point_group);
		image_desc = (TextView) findViewById(R.id.image_desc);
		image_desc.setText(imageDescriptions[0]);

		// 初始化图片资源
		imageList = new ArrayList<ImageView>();
		for (int i : images) {
			// 初始化图片资源
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(i);
			imageList.add(imageView);

			// 添加指示小点
			ImageView point = new ImageView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(5,
					5);
			params.rightMargin = 20;
			point.setLayoutParams(params);
			point.setBackgroundResource(R.drawable.ic_launcher);
			if (i == R.drawable.ic_launcher) {
				point.setEnabled(true);
			} else {
				point.setEnabled(false);
			}

			point_group.addView(point);
		}

		viewPager.setAdapter(new MyPageAdapter());
		// 设置当前viewPager的位置
		viewPager.setCurrentItem(Integer.MAX_VALUE / 2
				- (Integer.MAX_VALUE / 2 % imageList.size()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// 页面切换后调用， position是新的页面位置

				// 实现无限制循环播放
				position %= imageList.size();

				image_desc.setText(imageDescriptions[position]);

				// 把当前点设置为true,将上一个点设为false
				point_group.getChildAt(position).setEnabled(true);
				point_group.getChildAt(lastPosition).setEnabled(false);
				lastPosition = position;

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// 页面正在滑动时间回调

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// 当pageView 状态发生改变的时候，回调

			}
		});

		/**
		 * 自动循环： 1.定时器：Timer 2.开子线程：while true循环 3.ClockManger
		 * 4.用Handler发送延时信息，实现循环，最简单最方便
		 * 
		 */

		handler.sendEmptyMessageDelayed(0, 2000);
	}

	@Override
	protected void onDestroy() {
		// 停止滚动
		isRunning = false;
		super.onDestroy();
	}

	private class MyPageAdapter extends PagerAdapter {
		// 需要实现以下四个方法

		@Override
		public int getCount() {
			// 获得页面的总数
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// 判断view和Object对应是否有关联关系
			if (view == object) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 获得相应位置上的view； container view的容器，其实就是viewpage自身,
			// position: viewpager上的位置
			// 给container添加内容
			container.addView(imageList.get(position % imageList.size()));

			return imageList.get(position % imageList.size());
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// 销毁对应位置上的Object
			// super.destroyItem(container, position, object);
			container.removeView((View) object);
			object = null;
		}

	}

	private void setGoods(JSONArray response) {
		// TODO Auto-generated method stub
		for (int i = 0; i < response.length(); i++) {
			try {
				JSONObject json = response.getJSONObject(i);
				Map<String, String> map = new HashMap<String, String>();
				map.put("url", json.getString("url"));
				map.put("name", json.getString("name"));
				map.put("price", json.getString("price"));
				map.put("isbn", json.getString("isbn"));
				map.put("address", json.getString("address"));
				map.put("image", json.getString("image"));
				mList.add(map);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (mList.size() > 0) {
			ImageAdapter imageAdapter = new ImageAdapter(HomePageActivity.this,
					mList, gridview);
			gridview.setAdapter(imageAdapter);
		} else {
			Toast.makeText(getApplicationContext(), "获取商品数据失败！",
					Toast.LENGTH_SHORT).show();
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

	TextWatcher mTextWatcher = new TextWatcher() {
		private CharSequence temp;
		private int editStart;
		private int editEnd;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			temp = s;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			// mTextView.setText(s);//将输入的内容实时显示
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (mList.size() == 0) {
				return;
			}
			String edtt = edttext.getText().toString().trim();
			aList.clear();
			for (int i = 0; i < mList.size(); i++) {
				if (mList.get(i).get("name").contains(edtt)) {
					aList.add(mList.get(i));
				}
			}
			ImageAdapter imageAdapter = new ImageAdapter(HomePageActivity.this,
					aList, gridview);
			gridview.setAdapter(imageAdapter);
		}
	};
}
