package com.android.informationdemo;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.android.informationdemo.view.chart.SleepChartView;

public class SleepChartActivity extends Activity{
	int i=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
//		setContentView(new SleepChartView(this));
        setContentView(R.layout.activity_sleep);
        
        final SleepChartView sleepChartView = (SleepChartView) findViewById(R.id.sleepchartview);
        
        sleepChartView.showWeekBarChart(null);
        
        
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				i++;
				if (i%2==1) {
					sleepChartView.showDayBarChart(null);
				} else{
					sleepChartView.showWeekBarChart(null);
				}
			}
		});
	}
}
