package com.dystu.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ViewPager viewPager;

	private View view1, view2, view3;

	private List<View> viewlist;

	private ImageView cursor;

	private int bmpw = 0;

	private int offset = 0;

	private int currIndex = 0;

	// private PagerTabStrip tabStrip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		// tabStrip = (PagerTabStrip) findViewById(R.id.pagerTab);
		// tabStrip.setTabIndicatorColor(Color.GREEN);
		LayoutInflater inflater = getLayoutInflater();
		view1 = inflater.inflate(R.layout.layout1, null);
		view2 = inflater.inflate(R.layout.layout2, null);
		view3 = inflater.inflate(R.layout.layout3, null);
		viewlist = new ArrayList<View>();
		viewlist.add(view1);
		viewlist.add(view2);
		viewlist.add(view3);

		/*
		 * titleList = new ArrayList<String>();
		 * 
		 * titleList.add("test1"); titleList.add("test2");
		 * titleList.add("test3");
		 */

		initCursorPos();

		viewPager.setAdapter(new MyPagerAdapter(viewlist));

		viewPager.setOnPageChangeListener(new MyPageChangeListener());

	}

	private void initCursorPos() {

		cursor = (ImageView) findViewById(R.id.cursor);
		bmpw = BitmapFactory.decodeResource(getResources(),
				R.drawable.id_category_selector).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;

		offset = (screenW / viewlist.size() - bmpw) / 2;

		Matrix matrix = new Matrix();

		matrix.postTranslate(offset, 0);

		cursor.setImageMatrix(matrix);

	}

	private class MyPagerAdapter extends PagerAdapter {

		private List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListViews.get(position));
			return mListViews.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	private class MyPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpw;
		int two = one * 2;

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			Animation animation = null;
			switch (position) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;

			}

			currIndex = position;
			animation.setFillAfter(true);
			animation.setDuration(300);
			cursor.startAnimation(animation);

		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}

	}
}
