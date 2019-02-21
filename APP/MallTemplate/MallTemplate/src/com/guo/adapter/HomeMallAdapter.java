package com.guo.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.guo.malltemplate.R;

public class HomeMallAdapter extends BaseAdapter {

	public List<Map<String, String>> mList = null;
	private Context context;

	public HomeMallAdapter(Context context, List<Map<String, String>> mList) {

		this.context = context;
		this.mList = mList;
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

//		final Map<String, String> map = mList.get(position);
		// 得到一个布局映射器
		LayoutInflater mInflater = (LayoutInflater) LayoutInflater
				.from(context);
		View view = mInflater.inflate(R.layout.simple_list_item, null);

		// final ImageView image = (ImageView) view
		// .findViewById(R.id.griditem_pic);
		// final ImageView iv_remove = (ImageView) view
		// .findViewById(R.id.iv_lremove);

		// image.setTag(map.get("imagename").toString().trim());
		// image.setId(position);
		// image.setImageBitmap((Bitmap) map.get("PIC"));
		// // 删除按钮
		// image.setOnLongClickListener(new OnLongClickListener() {
		//
		// @Override
		// public boolean onLongClick(View v) {
		// // TODO Auto-generated method stub
		// iv_remove.setVisibility(View.VISIBLE);
		// return true;
		// }
		// });
		// // 删除图片
		// iv_remove.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// for (int i = 0; i < mList.size(); i++) {
		//
		// String imagetag = image.getTag().toString().trim();
		// String imagename = mList.get(i).get("imagename").toString()
		// .trim();
		// if (imagetag.equals(imagename)) {
		// mList.remove(i);
		// notifyDataSetInvalidated();
		// break;
		// }
		//
		// }
		//
		// }
		//
		// });
		// // 放大图片/播放
		// image.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// Intent it = new Intent();
		// it.putExtra("DataList", (Serializable) mList);
		// it.setClass(context, TouchImageActivity.class);
		// it.putExtra("position", String.valueOf(image.getId()));
		// context.startActivity(it);
		// }
		//
		// });
		return view;
	}
}
