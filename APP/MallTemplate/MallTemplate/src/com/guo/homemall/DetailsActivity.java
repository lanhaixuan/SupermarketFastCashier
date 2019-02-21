package com.guo.homemall;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.guo.attribute.ImageDownloader;
import com.guo.attribute.OnImageDownload;
import com.guo.malltemplate.R;

public class DetailsActivity extends Activity {
	private ViewPager viewPager;
	private LinearLayout point_group;
	private TextView image_desc;
	private HorizontalScrollView horizontalScrollView;
	private LinearLayout linearLayout;
	private GridView gridview;

	// 图片资源id
	private final int[] images = { R.drawable.img_xiangqing,
			R.drawable.img_xiangqing, R.drawable.img_xiangqing,
			R.drawable.img_xiangqing, R.drawable.img_xiangqing };
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

	private TextView name, price, address;
	private Map<String, Object> DataMap;
	private ImageView iv_image, iv_saosao;
	private ImageDownloader mDownloader;
	private String typ = "1";

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_activity);
		init();
		DataMap = (Map<String, Object>) getIntent().getSerializableExtra(
				"mList");
		typ = getIntent().getExtras().getString("typ");
		name = (TextView) findViewById(R.id.tv_name);
		price = (TextView) findViewById(R.id.tv_price);
		address = (TextView) findViewById(R.id.tv_address);
		iv_image = (ImageView) findViewById(R.id.iv_image);
		iv_saosao = (ImageView) findViewById(R.id.iv_saosao);

		name.setText(DataMap.get("name").toString());
		price.setText("￥" + DataMap.get("price").toString() + "件");
		address.setText(DataMap.get("address").toString());

		String url = DataMap.get("image").toString();
		if (mDownloader == null) {
			mDownloader = new ImageDownloader();
		}
		iv_image.setTag(url);
		// 异步下载图片
		mDownloader.imageDownload(url, iv_image, "/yanbin",
				DetailsActivity.this, new OnImageDownload() {
					@Override
					public void onDownloadSucc(Bitmap bitmap, String c_url,
							ImageView mimageView) {

						ImageView imageView = (ImageView) mimageView
								.findViewWithTag(c_url);
						if (imageView != null) {
							mimageView.setImageBitmap(bitmap);
							imageView.setTag("");
						}
					}
				}, url);

		iv_saosao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (typ.equals("1")) {
					Intent it = new Intent();
					it.setClass(DetailsActivity.this, ScanCodeActicity.class);
					startActivity(it);
				} else {

					JSONObject json = new JSONObject();
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat formatter1 = new SimpleDateFormat(
							"MMddHHmm");
					try {
						json.put("orderid", formatter1.format(new Date()));
						json.put("goodsname", DataMap.get("name"));
						json.put("goodprice", DataMap.get("price"));
						json.put("quantity", "1");
						json.put("imageurl", DataMap.get("image"));
						json.put("is_degauss", "no");
						json.put("is_pay", "false");
						json.put("add_time", formatter.format(new Date()));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					volley_JsonObjectRequestPost(json);
				}

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

	/*
	 * JsonObjectRequest JsonObjectRequest — To send and receive JSON Object
	 * from the Server 这个类可以用来发送和接收JSON对象。
	 * 这个类的一个重载构造函数允许设置适当的请求方法（DELETE，GET，POST和PUT）。
	 * 
	 * GET方式请求服务器
	 */
	private void volley_JsonObjectRequestPost(JSONObject json) {
		RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				"http://47.94.80.89/orderitems/", json,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(DetailsActivity.this, "添加成功！",
								Toast.LENGTH_SHORT).show();
						finish();
					}
				});

		request.setTag("volley_JsonObjectRequestGet");
		queue.add(request);
	}
}
