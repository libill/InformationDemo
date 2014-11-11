package com.android.informationdemo;

import com.android.informationdemo.view.chart.SleepChartView;

import android.app.Activity;
import android.os.Bundle;

public class SleepChartActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(new SleepChartView(this));
	}
}
