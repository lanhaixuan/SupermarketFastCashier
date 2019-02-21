package com.guo.adapter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.guo.attribute.ImageDownloader;
import com.guo.attribute.OnImageDownload;
import com.guo.homemall.DetailsActivity;
import com.guo.malltemplate.R;

public class ImageAdapter extends BaseAdapter {

	public List<Map<String, String>> mList = null;
	private Context context;
	private GridView gridView;
	private ImageDownloader mDownloader;

	public ImageAdapter(Context context, List<Map<String, String>> mList2,
			GridView gridView) {

		this.context = context;
		this.mList = mList2;
		this.gridView = gridView;
	}

	public int getCount() {
		return mList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// 得到一个布局映射器
		ViewHolder holder = null;
		final Map<String, String> map = mList.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.horizontalscrollview, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// map.put("url", json.getString("url"));
		// map.put("name", json.getString("name"));
		// map.put("price", json.getString("price"));
		// map.put("isbn", json.getString("isbn"));
		// map.put("address", json.getString("address"));
		// map.put("image", json.getString("image"));

		String url = map.get("image").toString();

		if (mDownloader == null) {
			mDownloader = new ImageDownloader();
		}
		holder.iv_iamge.setTag(url);
		holder.iv_iamge.setId(position);
		// 异步下载图片
		mDownloader.imageDownload(url, holder.iv_iamge, "/yanbin", context,
				new OnImageDownload() {
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

		holder.tv_name.setText(map.get("name").toString());
		holder.tv_jiage.setText("￥" + map.get("price").toString());
		holder.tv_weizhi.setText(map.get("address").toString());

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.putExtra("mList", (Serializable)mList.get(arg2));
				it.putExtra("typ", "1");
				it.setClass(context, DetailsActivity.class);
				context.startActivity(it);
			}
		});

		return convertView;
	}

	public class ViewHolder {
		TextView tv_name;
		TextView tv_weizhi;
		TextView tv_jiage;
		ImageView iv_iamge;

		ViewHolder(View view) {
			tv_name = (TextView) view.findViewById(R.id.tv_name);
			tv_weizhi = (TextView) view.findViewById(R.id.tv_weizhi);
			tv_jiage = (TextView) view.findViewById(R.id.tv_jiage);
			iv_iamge = (ImageView) view.findViewById(R.id.iv_iamge);

		}
	}
}
