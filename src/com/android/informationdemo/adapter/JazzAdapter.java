package com.android.informationdemo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.informationdemo.ArbItemSizeDSLVDemo.JazzArtist;
import com.android.informationdemo.R;
import com.android.informationdemo.view.button.FlatToggleButton;

public class JazzAdapter extends ArrayAdapter<JazzArtist> {
	
	private class ViewHolder {
		public TextView albumsView;
		public FlatToggleButton flat_toggle_button;
		public ViewHolder(View view){
			albumsView = (TextView) view.findViewById(R.id.artist_albums_textview);
			flat_toggle_button = (FlatToggleButton) view.findViewById(R.id.flat_toogle_button);
		}
	}

	public JazzAdapter(Context context, List<JazzArtist> artists) {
		super(context, R.layout.jazz_artist_list_item, R.id.artist_name_textview, artists);
	}
	
	@Override
	public void insert(JazzArtist object, int index) {
		// TODO Auto-generated method stub
		super.insert(object, index);
	}
	
	@Override
	public void remove(JazzArtist object) {
		// TODO Auto-generated method stub
		super.remove(object);
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, convertView, parent);

		if (v != convertView && v != null) {
			ViewHolder holder = new ViewHolder(v);
			v.setTag(holder);
		}

		ViewHolder holder = (ViewHolder) v.getTag();
		String albums = getItem(position).albums;

		holder.albumsView.setText(albums);
		holder.flat_toggle_button.setChecked(getItem(position).show);
		holder.flat_toggle_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				getItem(position).show = !getItem(position).show;
			}
		});
		
		return v;
	}
}