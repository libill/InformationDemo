package com.android.informationdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.informationdemo.adapter.JazzAdapter;
import com.android.informationdemo.view.DragSortListView;
import com.android.informationdemo.view.chart.RainAnimotion;

public class ArbItemSizeDSLVDemo extends Activity {

	private JazzAdapter adapter;

	private ArrayList<JazzArtist> mArtists;

	private String[] mArtistNames;
	private String[] mArtistAlbums;

	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			JazzArtist item = adapter.getItem(from);

			adapter.remove(item);
			adapter.insert(item, to);
		}
	};

	private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
		@Override
		public void remove(int which) {
			adapter.remove(adapter.getItem(which));
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hetero_main);

		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width;
		int height;
		width = dm.widthPixels;
		height = dm.heightPixels;
		Log.i("系统信息", "该设备的分辨是：" + width + "*" + height);
		RainAnimotion.right = width - 42;// 17*24=408
		RainAnimotion.gapX = (width - 45) / 25;
		findViewById(R.id.geomark_view3).setVisibility(View.VISIBLE);
		
		
		DragSortListView lv = (DragSortListView) findViewById(R.id.darg_list);

		lv.setDropListener(onDrop);
		lv.setRemoveListener(onRemove);

		mArtistNames = getResources().getStringArray(R.array.jazz_artist_names);
		mArtistAlbums = getResources().getStringArray(R.array.jazz_artist_albums);

		mArtists = new ArrayList<JazzArtist>();
		JazzArtist ja;
		for (int i = 0; i < mArtistNames.length; ++i) {
			ja = new JazzArtist();
			ja.id = i+"";
			ja.sortId = i;
			ja.name = mArtistNames[i];
			if (i < mArtistAlbums.length) {
				ja.albums = mArtistAlbums[i];
				ja.show = true;
			} else {
				ja.albums = "No albums listed";
				ja.show = false;
			}
			mArtists.add(ja);
		}
		
		adapter = new JazzAdapter(this, mArtists);

		lv.setAdapter(adapter);

		final Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				for(int i=0;i<mArtists.size();i++){
					//排序
					mArtists.get(i).sortId = i;
				}
				Log.i("mArtists", mArtists.toString());
				b.setText(mArtists.toString());
			}
		});
	}

	public class JazzArtist {
		public String id;
		public int sortId;
		public String name;
		public String albums;
		public boolean show;
		@Override
		public String toString() {
			return "JazzArtist [id:"+id+",sortId:"+sortId+",name:"+name+",albums:"+albums+",show:"+show + "]";
		}
	}
}
