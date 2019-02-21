package com.guo.malltemplate;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.guo.adapter.GuidePageAdapter;
import com.guo.staticm.StaticMethod;

public class MainActivity extends Activity {
	private ViewPager viewPager;
	private ArrayList<View> pageViews;
	private ImageView imageView, imageanim;
	private ImageView[] imageViews;
	private ViewGroup main;
	private ViewGroup group;
	private View one, tow, stree;
	private LinearLayout lin_guide;
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageanim = (ImageView) findViewById(R.id.iv_image);

		if (StaticMethod.Ifwifi(getApplicationContext())) {
			ifone();
		}

	}

	/**
	 * 判断用户是否第一次运行此软件 若不是延迟三秒进入主程序
	 */
	private void ifone() {
		// TODO Auto-generated method stub
		sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
		boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
		if (isFirstRun) {
			addView();
		} else {
			setImage(imageanim);
			final Timer mTimerlogin = new Timer();
			mTimerlogin.schedule(new TimerTask() {
				@Override
				public void run() {

					Intent intent = new Intent(MainActivity.this,
							TabHostActivity.class);
					startActivity(intent);
					mTimerlogin.cancel();
					// 设置切换动画，从右边进入，左边退出
					overridePendingTransition(android.R.anim.fade_in,
							android.R.anim.fade_out);
					MainActivity.this.finish();

				}
			}, 1 * 1 * 2000, 1 * 1 * 2000);

		}
	}

	/**
	 * 设置图片放大动画效果
	 * 
	 * @param imageView
	 */
	private void setImage(ImageView imageView) {
		// TODO Auto-generated method stub
		// 初始化
		Animation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// 设置动画时间
		scaleAnimation.setFillAfter(true);// 设置控件停留在动画结束后的位置
		scaleAnimation.setDuration(2000);
		imageView.startAnimation(scaleAnimation);
	}

	/**
	 * 界面添加第一次用户使用view
	 */
	private void addView() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = getLayoutInflater();
		one = inflater.inflate(R.layout.first_a, null);
		tow = inflater.inflate(R.layout.first_b, null);
		stree = inflater.inflate(R.layout.first_c, null);

		pageViews = new ArrayList<View>();
		pageViews.add(one);
		pageViews.add(tow);
		pageViews.add(stree);

		imageViews = new ImageView[pageViews.size()];
		main = (ViewGroup) inflater.inflate(R.layout.viewgroup, null);
		lin_guide = (LinearLayout) stree.findViewById(R.id.lin_guide);

		group = (ViewGroup) main.findViewById(R.id.viewGroup);
		viewPager = (ViewPager) main.findViewById(R.id.guidePages);
		for (int i = 0; i < pageViews.size(); i++) {
			imageView = new ImageView(MainActivity.this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(10, 10, 10, 10);
			imageView.setLayoutParams(lp);
			imageViews[i] = imageView;

			if (i == 0) {
				// 默认选中第一张图片
				imageViews[i]
						.setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.page_indicator);
			}
			group.addView(imageViews[i]);
		}
		setContentView(main);
		viewPager.setAdapter(new GuidePageAdapter(pageViews));
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
		lin_guide.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub

				Editor editor = sharedPreferences.edit();
				editor.putBoolean("isFirstRun", false);
				editor.commit();
				Intent intent = new Intent(MainActivity.this,
						TabHostActivity.class);
				startActivity(intent);
				// 设置切换动画，从右边进入，左边退出
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
				MainActivity.this.finish();
				return false;
			}
		});
	}

	/**
	 * 指引页面更改事件监听器
	 * 
	 * @author Administrator
	 * 
	 */
	private class GuidePageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		public void onPageSelected(int arg0) {
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.page_indicator_focused);

				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.page_indicator);
				}
			}
		}
	}
}
