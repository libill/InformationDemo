package com.android.informationdemo.view.chart;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author libill 2014-11-12
 */
public class SleepChartView extends View {
	private String TAG = "SleepChartView";

	private Context mContext;
	private Paint mPaint = new Paint();
	private float width;
	private float height;

	private int xSpaceNum = 20;
	private int ySpaceNum = 4;
	private float xSpace;
	private float ySpace;

	/** 四周边距 **/
	private float left, right, top, bottom;

	private List<Float> xValues = new ArrayList<Float>();
	private List<Float> yValues = new ArrayList<Float>();
	private Float[] yFloats = { 1f, 2f, 3f };

	private String[] weekXText = { "一", "二", "三", "四", "五", "六", "日" };

	ChartType chartType = ChartType.DayBarChart;

	private enum ChartType {
		DayBarChart, WeekBarChart
	}

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

		drawBackground(canvas);
		
		drawBarChart(canvas);
	}

	public void initData() {
		width = this.getWidth();
		height = this.getHeight();

		left = right = 60;
		top = bottom = 60;

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
	
	public void drawBarChart(Canvas canvas){
		if (chartType == ChartType.DayBarChart) {
			drawDayBarChart(canvas);
		} else if (chartType == ChartType.WeekBarChart) {
			drawWeekBarChart(canvas);
		}
	}

	public void drawDayGridChart(Canvas canvas) {
		// 创建画笔
		mPaint.setColor(Color.GRAY);

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

	public void drawDayBarChart(Canvas canvas) {

		// 网格，用于测试
		drawDayGridChart(canvas);

		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.FILL);
		float temp = 0;

		float locationMark = 0; // 判断画虚线
		for (int i = 0; i < xValues.size(); i++) {
			if (yValues.get(i) <= yFloats[0]) {
				mPaint.setColor(Color.RED);
			} else if (yValues.get(i) <= yFloats[1]) {
				mPaint.setColor(Color.BLUE);
			} else if (yValues.get(i) <= yFloats[2]) {
				mPaint.setColor(Color.GREEN);
			}
			if (left + xSpace * (xValues.get(i) + temp) <= left + width) {
				// 画柱状图
				canvas.drawRect(left + xSpace * temp, height + top - (ySpace * yValues.get(i)), left + xSpace * (xValues.get(i) + temp), bottom + height, mPaint);

				if (locationMark == 0 && yValues.get(i) > yFloats[0] && yValues.get(i) <= yFloats[1]) {
					PathEffect effects = new DashPathEffect(new float[] { 8, 8, 8, 8 }, 8);
					mPaint.setPathEffect(effects);
					mPaint.setStrokeWidth(2);
					mPaint.setColor(Color.GRAY);
					// 画虚线
					canvas.drawLine(left + xSpace * temp, right, top + xSpace * temp, height + bottom, mPaint);
					mPaint.setStrokeWidth(1);
					mPaint.setPathEffect(null);
					locationMark = left + xSpace * temp;
				}
			} else {
				// if (height + top - (ySpace * yValues.get(i)) <= left +
				// width+10) {
				canvas.drawRect(left + xSpace * temp, height + top - (ySpace * yValues.get(i)), left + width, bottom + height, mPaint);
				// Log.i(TAG, "error:容错，超出范围了");
				// }
				Log.i(TAG, "error:容错，超出范围");
			}

			temp += xValues.get(i);
		}

		Paint textPaint = new Paint();
		textPaint.setColor(Color.GRAY);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(30);
		textPaint.setTextAlign(Paint.Align.LEFT);
		canvas.drawText("00:09", locationMark + 10, top + 40, textPaint);

		// 底部文字
		textPaint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText("11:09", left, height + top + bottom / 2, textPaint);

		canvas.drawText("09:00", left + width, height + top + bottom / 2, textPaint);
	}

	public void drawWeekGridChart(Canvas canvas) {
		// 创建画笔
		mPaint.setColor(Color.GRAY);

		mPaint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除

		float startX = left;
		float startY = top;
		float stopX = width + right;
		float stopY = bottom;
		
		Paint textYPaint = new Paint();
		textYPaint.setColor(Color.GRAY);
		textYPaint.setStyle(Paint.Style.FILL);
		textYPaint.setAntiAlias(true);
		textYPaint.setTextSize(30);
		textYPaint.setTextAlign(Paint.Align.LEFT);
		// 底部文字
		textYPaint.setTextAlign(Paint.Align.CENTER);
		FontMetrics fm = textYPaint.getFontMetrics();
		float textHeight = fm.descent - fm.ascent;

		// X横坐标
		for (int i = 0; i <= ySpaceNum; i++) {
			if (i%4==1) {
				canvas.drawLine(startX, startY, stopX, stopY, mPaint);
				canvas.drawText(ySpaceNum-i+"", startX/2, startY+textHeight/3, textYPaint);
			}
			startY += ySpace;
			stopY += ySpace;
		}

//		// Y横坐标
//		startX = left;
//		startY = right;
//		stopX = top;
//		stopY = height + bottom;
//		for (int i = 0; i <= xSpaceNum; i++) {
//			canvas.drawLine(startX, startY, stopX, stopY, mPaint);
//			startX += xSpace;
//			stopX += xSpace;
//		}
	}

	public void drawWeekBarChart(Canvas canvas) {
		// 网格，用于测试
		drawWeekGridChart(canvas);

		Paint textPaint = new Paint();
		textPaint.setColor(Color.GRAY);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(30);
		textPaint.setTextAlign(Paint.Align.LEFT);
		// 底部文字
		textPaint.setTextAlign(Paint.Align.CENTER);
		FontMetrics fm = textPaint.getFontMetrics();
		float textHeight = fm.descent - fm.ascent;

		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.FILL);
		float temp = 0;
		float spaceTemp = xSpace / 3;
		for (int i = 0; i < xValues.size(); i++) {
			if (yValues.get(i) <= yFloats[0]) {
				mPaint.setColor(Color.RED);
			} else if (yValues.get(i) <= yFloats[1]) {
				mPaint.setColor(Color.BLUE);
			} else if (yValues.get(i) <= yFloats[2]) {
				mPaint.setColor(Color.GREEN);
			}
			// 画柱状图
			canvas.drawRect(left + xSpace * temp + spaceTemp, height + top - (ySpace * yValues.get(i)), left + xSpace * (xValues.get(i) + temp) - spaceTemp, bottom + height, mPaint);

			// 底部文字
			canvas.drawText(weekXText[i], left + xSpace * (xValues.get(i) + temp) - xSpace / 2, height + top + bottom / 2 + textHeight / 3, textPaint);

			temp += xValues.get(i);
		}
	}

	public void showDayBarChart(ArrayList<Float> dataList) {
		chartType = ChartType.DayBarChart;
		xSpaceNum = 20;
		ySpaceNum = 4;
		xValues.clear();
		yValues.clear();
		for (int i = 0; i <= 6; i++) {
			xValues.add(1f * i);
			yValues.add(1f * (1 + i % 3));
		}

		invalidate();
	}

	public void showWeekBarChart(ArrayList<Float> dataList) {
		chartType = ChartType.WeekBarChart;
		xSpaceNum = 7;
		ySpaceNum = 13;
		xValues.clear();
		yValues.clear();
		for (int i = 0; i < xSpaceNum; i++) {
			xValues.add(1f);
		}

		for (int i = 0; i < ySpaceNum; i++) {
			yValues.add(1f * 2*i);
		}

		invalidate();
	}

}
