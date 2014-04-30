package org.oscim.tiling.source.lnglat;

import org.oscim.core.Tile;
import org.oscim.tiling.source.UrlTileSource;
import org.oscim.tiling.source.bitmap.BitmapTileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maven on 2014/4/30.
 */
public class LngLatBitmapTileSource extends BitmapTileSource {
	private final static Logger log = LoggerFactory.getLogger(LngLatBitmapTileSource.class);
	private TileUrlFormatter formatter = new TileUrlFormatter() {
		@Override
		public String formatTilePath(UrlTileSource tileSource, Tile tile) {
			if (!isTileVisible(tile.tileX, tile.tileY, tile.zoomLevel)) {
				return "";
			} else {
				StringBuilder sb = new StringBuilder();
				int x = tile.tileX, y = tile.tileY - getTileOffsetY(tile.zoomLevel), z = tile.zoomLevel;
				for (String b : tileSource.getTilePath()) {
					if (b.length() == 1) {
						switch (b.charAt(0)) {
							case 'b':
								String bbox = getBBOX(x, y, tile.zoomLevel);
								sb.append(bbox);
								log.debug("from x:{},y:{},z:{} to bbox:{}", x, y, z, bbox);
								continue;
							default:
								break;
						}
					}
					sb.append(b);
				}
				return sb.toString();
			}
		}
	};

	private boolean isTileVisible(int tileX, int tileY, byte zoomLevel) {
		int miny = getTileOffsetY(zoomLevel), maxy = 3 * miny - 1;
		return tileY >= miny && tileY <= maxy;
	}

	private int getTileOffsetY(int zoom) {
		return 1 << (zoom - 2);
	}

	public LngLatBitmapTileSource(String url) {
		this(parseString(url), 2, 20);
	}

	private LngLatBitmapTileSource(String[] urlAndPath, int zoomMin, int zoomMax) {
		super(urlAndPath[0], urlAndPath[1], zoomMin, zoomMax);

	}

	private static String[] parseString(String path) {
		String res[] = new String[2];
		try {

			URL u = new URL(path);
			res[0] = u.getProtocol() + "://" + u.getAuthority();
			res[1] = path.replace(res[0], "");
			return res;
		} catch (MalformedURLException e) {
		}
		return null;
	}

	public String getTileUrl(Tile tile) {
		return getUrl() + formatter.formatTilePath(this, tile);
	}

	@Override
	public TileUrlFormatter getUrlFormatter() {
		return formatter;
	}

	private String getBBOX(int x, int y, int zoom) {
		double left = x * 360 / (1 << zoom) - 180;
		double right = (x + 1) * 360 / (1 << zoom) - 180;
		double top = 90 - y * 180 / (1 << (zoom - 1));
		double bottom = 90 - (y + 1) * 180 / (1 << (zoom - 1));
		return String.format("%s,%s,%s,%s", left, bottom, right, top);
	}
}
