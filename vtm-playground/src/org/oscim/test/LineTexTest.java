package org.oscim.test;

import org.oscim.backend.CanvasAdapter;
import org.oscim.backend.canvas.Color;
import org.oscim.core.GeometryBuffer;
import org.oscim.gdx.GdxMap;
import org.oscim.gdx.GdxMapApp;
import org.oscim.layers.GenericLayer;
import org.oscim.renderer.BucketRenderer;
import org.oscim.renderer.GLViewport;
import org.oscim.renderer.bucket.LineBucket;
import org.oscim.renderer.bucket.LineTexBucket;
import org.oscim.renderer.bucket.TextureItem;
import org.oscim.theme.styles.LineStyle;

import com.badlogic.gdx.Input;

public class LineTexTest extends GdxMap {

	@Override
	protected boolean onKeyDown(int keycode) {
		if (keycode == Input.Keys.NUM_1) {
			angle++;
			mMap.render();
			return true;

		}

		if (keycode == Input.Keys.NUM_2) {
			angle--;
			mMap.render();
			return true;

		}
		return false;
	}

	float angle = 0;

	@Override
	protected void createLayers() {
		mMap.setMapPosition(0, 0, 1 << 8);
		mMap.layers().add(new GenericLayer(mMap, new BucketRenderer() {
			boolean init;

			GeometryBuffer g = new GeometryBuffer(10, 1);

			LineBucket lb = buckets.addLineBucket(0,
			                                      new LineStyle(Color.fade(Color.CYAN, 0.5f), 2.5f));

			LineTexBucket ll;

			@Override
			public boolean setup() {

				//lb.next = ll;
				ll = buckets.getLineTexBucket(1);

				TextureItem tex = new TextureItem(CanvasAdapter.getBitmapAsset("patterns/dot.png"));
				tex.mipmap = true;

				ll.line = LineStyle.builder()
				    .stippleColor(Color.BLACK)
				    .stipple(16)
				    .stippleWidth(1)
				    .strokeWidth(8)
				    .strokeColor(Color.RED)
				    .fixed(true)
				    .texture(tex)
				    .build();

				//ll.width = 8;

				return super.setup();
			}

			@Override
			public void update(GLViewport v) {
				if (!init) {
					mMapPosition.copy(v.pos);
					init = true;
				}

				buckets.clear();
				buckets.set(lb);
				GeometryBuffer.makeCircle(g, 0, 0, 600, 40);

				//	g.clear();
				//	g.startLine();
				//	g.addPoint(-1, 0);
				//	g.addPoint(1, 0);
				//	g.addPoint(1, -1);
				//	g.addPoint(-1, -1);
				//	g.scale(100, 100);

				for (int i = 0; i < 15; i++) {
					lb.addLine(g);
					ll.addLine(g);
					g.scale(0.8f, 0.8f);
				}
				compile();

			}
		}));
	}

	public static void main(String[] args) {
		GdxMapApp.init();
		GdxMapApp.run(new LineTexTest(), null, 256);
	}
}
