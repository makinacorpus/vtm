package org.oscim.android.test;

import org.oscim.layers.TileGridLayer;
import org.oscim.layers.tile.bitmap.BitmapTileLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.theme.VtmThemes;
import org.oscim.tiling.source.bitmap.BitmapTileSource;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;

import android.os.Bundle;
import android.view.View;

/**
 * Created by maven on 2014/4/30.
 * 
 * Switch the map layer through the buttons at the bottom, you may find that the
 * appliction may hung up and be no responsitive.
 * 
 * It seems that it is caused by the PausedThread
 */
public class SetThemeActivity extends BaseMapActivity {
	private VectorTileLayer vector;
	private LabelLayer label;

	private BitmapTileLayer sate;

	public static class GoogleMapSatellite extends BitmapTileSource {
		public GoogleMapSatellite(String hostName) {
			super(hostName, "/vt/lyrs=s@149&x={X}&y={Y}&z={Z}&s=Galileo&scale=2", 2, 20); //jpeg for sat
		}
	}

	public SetThemeActivity() {
		super(R.layout.activity_map_theme);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.initVectorLayer();
		this.initSatelliteLayer();
		setBackgroundMap("vec");

		findViewById(R.id.btn_vec).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setBackgroundMap("vec");
			}
		});
		findViewById(R.id.btn_sate).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setBackgroundMap("sate");
			}
		});
	}

	private void setBackgroundMap(String type) {
		//clear all the layers except the EventLayer which have an index of 0
		for (int i = mMap.layers().size() - 1; i > 0; i--) {
			mMap.layers().remove(i);
		}
		if (type.equals("vec")) {
			mMap.layers().add(1, vector);
			mMap.layers().add(2, label);
			mMap.setTheme(VtmThemes.DEFAULT);
		} else if (type.equals("sate")) {
			mMap.layers().add(1, sate);
			mMap.layers().add(2, vector);
			mMap.layers().add(3, label);
			mMap.setTheme(VtmThemes.DEFAULT);
		}
		mMap.layers().add(new TileGridLayer(mMap));
	}

	private void initVectorLayer() {
		vector = new VectorTileLayer(mMap, new OSciMap4TileSource());
		label = new LabelLayer(mMap, vector);
	}

	private void initSatelliteLayer() {
		sate = new BitmapTileLayer(mMap, new GoogleMapSatellite("http://mt1.google.com"));
	}
}
