package org.oscim.android.test;

import org.oscim.android.MapActivity;
import org.oscim.android.MapView;
import org.oscim.layers.TileGridLayer;
import org.oscim.layers.tile.bitmap.BitmapTileLayer;
import org.oscim.tiling.source.OkHttpEngine.OkHttpFactory;
import org.oscim.tiling.source.lnglat.LngLatBitmapTileSource;

import android.os.Bundle;

/**
 * Created by maven on 2014/4/30.
 */
public class LngLatMapActivity extends MapActivity {

	private static final String TILE_URL = "http://worldmapkit2.thinkgeo.com/CachedWmsServer/WmsServer.axd?SERVICE=WMS&REQUEST=GetMap&VERSION=1.1"
	        + ".1&LAYERS=WorldMapKitLayer&STYLES=WorldMapKitDefaultStyles&FORMAT=image%2Fjpeg&TRANSPARENT=false&HEIGHT=256&WIDTH=256&SRS=EPSG:4326&BBOX={b}";

	MapView mMapView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_map);
		mMapView = (MapView) findViewById(R.id.mapView);

		LngLatBitmapTileSource tileSource = new LngLatBitmapTileSource(TILE_URL);
		tileSource.setHttpEngine(new OkHttpFactory());

		BitmapTileLayer lnglat = new BitmapTileLayer(mMap, tileSource);

		mMap.setBaseMap(lnglat);

		mMap.layers().add(new TileGridLayer(mMap));

		mMap.setMapPosition(0, 0, Math.pow(2, 2));
	}

}
