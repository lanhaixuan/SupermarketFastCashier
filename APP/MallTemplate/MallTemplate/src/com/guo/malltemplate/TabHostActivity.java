package com.guo.malltemplate;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TabHost;

import com.guo.homemall.HomeMallActivity;
import com.guo.homemall.HomePageActivity;
import com.guo.homemall.MyActivity;
import com.guo.homemall.ScanCodeActicity;

public class TabHostActivity extends TabActivity implements
		OnCheckedChangeListener {
	private Intent homeintent, Rankingintent, ScanCode, registration,
			PersonalCenter;
	private RadioButton rBtn_tab1;
	private RadioButton rBtn_tab2;
	private RadioButton rBtn_tab3;
	private RadioButton rBtn_tab4;
	private RadioButton rBtn_tab5;
	// 定义标签名
	private static final String TAB_TAG_1 = "11";
	private static final String TAB_TAG_2 = "22";
	private static final String TAB_TAG_3 = "33";
	private static final String TAB_TAG_4 = "44";
	private static final String TAB_TAG_5 = "55";
	private TabHost mHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost);
		setContent();
		setTabs();
	}

	/**
	 * 设置标签对应内容
	 **/
	private void setContent() {
		homeintent = new Intent(TabHostActivity.this, HomePageActivity.class);
		Rankingintent = new Intent(TabHostActivity.this, HomeMallActivity.class);
		ScanCode = new Intent(TabHostActivity.this, ScanCodeActicity.class);
		registration = new Intent(TabHostActivity.this, HomeMallActivity.class);
		PersonalCenter = new Intent(TabHostActivity.this, MyActivity.class);

		rBtn_tab1 = (RadioButton) findViewById(R.id.tab_item_1);
		rBtn_tab2 = (RadioButton) findViewById(R.id.tab_item_2);
		rBtn_tab3 = (RadioButton) findViewById(R.id.tab_item_3);
		rBtn_tab4 = (RadioButton) findViewById(R.id.tab_item_4);
		rBtn_tab5 = (RadioButton) findViewById(R.id.tab_item_5);

		rBtn_tab1.setOnCheckedChangeListener(this);
		rBtn_tab2.setOnCheckedChangeListener(this);
		rBtn_tab3.setOnCheckedChangeListener(this);
		rBtn_tab4.setOnCheckedChangeListener(this);
		rBtn_tab5.setOnCheckedChangeListener(this);

	}

	private void setTabs() {
		// 获取TabHost对象
		mHost = getTabHost();
		// 添加TabSpec
		mHost.addTab(mHost.newTabSpec(TAB_TAG_1).setContent(homeintent)
				.setIndicator(""));
		mHost.addTab(mHost.newTabSpec(TAB_TAG_2).setContent(Rankingintent)
				.setIndicator(""));
		mHost.addTab(mHost.newTabSpec(TAB_TAG_3).setContent(ScanCode)
				.setIndicator(""));
		mHost.addTab(mHost.newTabSpec(TAB_TAG_4).setContent(registration)
				.setIndicator(""));
		mHost.addTab(mHost.newTabSpec(TAB_TAG_5).setContent(PersonalCenter)
				.setIndicator(""));
		// 设置默认显示标签
		mHost.setCurrentTabByTag(TAB_TAG_1);
		// 设置默认选中按钮
		rBtn_tab1.setChecked(true);

	}

	public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {

		switch (arg0.getId()) {
		case R.id.tab_item_1:
			if (isChecked) {
				// 显示当前点击项对应的标签内容
				mHost.setCurrentTabByTag(TAB_TAG_1);
				// 其余的RadioButton选中状态清除
				rBtn_tab2.setChecked(false);
				rBtn_tab3.setChecked(false);
				rBtn_tab4.setChecked(false);
				rBtn_tab5.setChecked(false);
			}
			break;
		case R.id.tab_item_2:
			if (isChecked) {
				mHost.setCurrentTabByTag(TAB_TAG_2);
				rBtn_tab1.setChecked(false);
				rBtn_tab3.setChecked(false);
				rBtn_tab4.setChecked(false);
				rBtn_tab5.setChecked(false);
			}
			break;
		case R.id.tab_item_3:
			if (isChecked) {
				mHost.setCurrentTabByTag(TAB_TAG_3);
				rBtn_tab2.setChecked(false);
				rBtn_tab1.setChecked(false);
				rBtn_tab4.setChecked(false);
				rBtn_tab5.setChecked(false);
			}
			break;
		case R.id.tab_item_4:
			if (isChecked) {
				mHost.setCurrentTabByTag(TAB_TAG_4);
				rBtn_tab2.setChecked(false);
				rBtn_tab1.setChecked(false);
				rBtn_tab3.setChecked(false);
				rBtn_tab5.setChecked(false);
			}
			break;
		case R.id.tab_item_5:
			if (isChecked) {
				mHost.setCurrentTabByTag(TAB_TAG_5);
				rBtn_tab2.setChecked(false);
				rBtn_tab3.setChecked(false);
				rBtn_tab1.setChecked(false);
				rBtn_tab4.setChecked(false);
			}
			break;
		}
	}

}
