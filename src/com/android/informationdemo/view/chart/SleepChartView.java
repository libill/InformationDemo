package com.android.informationdemo.view.chart;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SleepChartView extends View {
	private String TAG="SleepChartView";

	private Context mContext;
	private Paint mPaint = new Paint();
	private float width;
	private float height;

	private int xSpaceNum = 20;
	private int ySpaceNum = 3;
	private float xSpace;
	private float ySpace;

	/** 四周边距 **/
	float left, right, top, bottom;

	List<Float> xValues = new ArrayList<Float>();
	List<Float> yValues = new ArrayList<Float>();
	Float[] yFloats = { 1f, 2f, 3f };

	public SleepChartView(Context context) {
		super(context);
		this.mContext = context;

		for (int i = 0; i <= 6; i++) {
			xValues.add(1f * i);
		}
		for (int i = 0; i <= 6; i++) {
			yValues.add(1f * (1 + i % 3));
		}
	}

	public SleepChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		initData();
		drawBackground(canvas);

		drawChart(canvas);

		drawBarChart(canvas);

	}

	public void initData() {
		width = this.getWidth();
		height = this.getHeight();

		left = right = 120;
		top = bottom = 120;

		width = width - left - right;
		height = height - top - bottom;

		xSpace = width / xSpaceNum;
		ySpace = height / ySpaceNum;

	}

	public void drawBackground(Canvas canvas) {
		mPaint.setColor(Color.WHITE);
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawRect(0, 0, width + left + right, height + top + bottom, mPaint);
	}

	public void drawChart(Canvas canvas) {
		// 创建画笔
		mPaint.setColor(Color.GRAY);// 设置红色

		mPaint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除

		float startX = left;
		float startY = top;
		float stopX = width + right;
		float stopY = bottom;

		// X横坐标
		for (int i = 0; i <= ySpaceNum; i++) {
			canvas.drawLine(startX, startY, stopX, stopY, mPaint);
			startY += ySpace;
			stopY += ySpace;
		}

		// Y横坐标
		startX = left;
		startY = right;
		stopX = top;
		stopY = height + bottom;
		for (int i = 0; i <= xSpaceNum; i++) {
			canvas.drawLine(startX, startY, stopX, stopY, mPaint);
			startX += xSpace;
			stopX += xSpace;
		}
	}

	public void drawBarChart(Canvas canvas) {
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.FILL);
		float temp = 0;
		for (int i = 0; i < xValues.size(); i++) {
			if (yValues.get(i) <= yFloats[0]) {
				mPaint.setColor(Color.RED);
			} else if (yValues.get(i) <= yFloats[1]) {
				mPaint.setColor(Color.BLUE);
			} else if (yValues.get(i) <= yFloats[2]) {
				mPaint.setColor(Color.GREEN);
			}
			if (left + xSpace * (xValues.get(i) + temp) <= left + width) {
				canvas.drawRect(left + xSpace * temp, height + top - (ySpace * yValues.get(i)), left + xSpace * (xValues.get(i) + temp), bottom + height, mPaint);
			} else {
				canvas.drawRect(left + xSpace * temp, height + top - (ySpace * yValues.get(i)), left + width, bottom + height, mPaint);
				Log.i(TAG, "error:容错，超出范围");
			}

			temp += xValues.get(i);
		}
	}

}
