package org.oscim.android.test;

import android.os.Bundle;
import org.oscim.android.MapActivity;
import org.oscim.layers.TileGridLayer;
import org.oscim.layers.tile.bitmap.BitmapTileLayer;
import org.oscim.layers.tile.vector.BuildingLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Layers;
import org.oscim.map.Map;
import org.oscim.theme.VtmThemes;
import org.oscim.tiling.source.bitmap.DefaultSources;
import org.oscim.tiling.source.lnglat.LngLatBitmapTileSource;

/**
 * Created by maven on 2014/4/30.
 */
public class LngLatMapActivity extends BaseMapActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BitmapTileLayer google = new BitmapTileLayer(mMap, new DefaultSources.GoogleMaps("https://mts0.google.com"));

		LngLatBitmapTileSource tileSource = new LngLatBitmapTileSource("http://worldmapkit2.thinkgeo.com/CachedWmsServer/WmsServer.axd?SERVICE=WMS&REQUEST=GetMap&VERSION=1.1" +
				".1&LAYERS=WorldMapKitLayer&STYLES=WorldMapKitDefaultStyles&FORMAT=image%2Fjpeg&TRANSPARENT=false&HEIGHT=256&WIDTH=256&SRS=EPSG:4326&BBOX={b}");
		BitmapTileLayer lnglat = new BitmapTileLayer(mMap, tileSource);

		//mMap.setBackgroundMap(google);
		mMap.layers().add(lnglat);
		mMap.layers().add(new TileGridLayer(mMap));
		mMap.setMapPosition(0,0, Math.pow(2, 2));
	}

}
