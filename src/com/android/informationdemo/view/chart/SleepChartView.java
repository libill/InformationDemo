package com.android.informationdemo.view.chart;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SleepChartView extends View {

	private Context mContext;
	private Paint mPaint = new Paint();
	private float width;
	private float height;
	
	private int xSpaceNum = 10;
	private int ySpaceNum = 10;
	private float xSpace;
	private float ySpace;

	public SleepChartView(Context context) {
		super(context);
		this.mContext = context;
	}

	public SleepChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		initData();

		drawChart(canvas);

	}
	
	public void initData(){
		width = this.getWidth();
		height = this.getHeight();
		xSpace = width/xSpaceNum;
		ySpace = height/ySpaceNum;
	}
	
	public void drawChart(Canvas canvas){
		// 创建画笔
		mPaint.setColor(Color.RED);// 设置红色

		mPaint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了

		float startX = 0;
		float startY = 0;
		float stopX = width;
		float stopY = 0;
		
		// X横坐标
		for (int i = 0; i <= 10; i++) {
			canvas.drawLine(startX, startY, stopX, stopY, mPaint);
			startY += ySpace;
			stopY += ySpace;
		}
		
		// Y横坐标
		startX = 0;
		startY = 0;
		stopX = 0;
		stopY = height;
		for (int i = 0; i <= 10; i++) {
			canvas.drawLine(startX, startY, stopX, stopY, mPaint);
			startX += xSpace;
			stopX += xSpace;
		}
	}

}
