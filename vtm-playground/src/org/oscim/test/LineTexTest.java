package org.oscim.test;

import org.oscim.backend.CanvasAdapter;
import org.oscim.backend.canvas.Color;
import org.oscim.core.GeometryBuffer;
import org.oscim.gdx.GdxMap;
import org.oscim.gdx.GdxMapApp;
import org.oscim.layers.GenericLayer;
import org.oscim.renderer.BucketRenderer;
import org.oscim.renderer.GLViewport;
import org.oscim.renderer.MapRenderer;
import org.oscim.renderer.bucket.LineTexBucket;
import org.oscim.renderer.bucket.TextureItem;
import org.oscim.theme.styles.LineStyle;

import com.badlogic.gdx.Gdx;
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

			//	LineBucket ll = buckets.addLineBucket(0,
			//	                                      new LineStyle(Color.fade(Color.CYAN, 0.5f), 1.5f));

			LineTexBucket ll;

			@Override
			public boolean setup() {

				ll = buckets.getLineTexBucket(0);

				TextureItem tex = new TextureItem(CanvasAdapter.getBitmapAsset("patterns/arrow.png"));
				tex.mipmap = true;

				ll.line = LineStyle.builder()
				    .stippleColor(Color.BLACK)
				    .stipple(64)
				    .stippleWidth(1)
				    .strokeWidth(2)
				    .strokeColor(Color.RED)
				    .fixed(true)
				    .texture(tex)
				    .build();

				ll.width = 8;

				return super.setup();
			}

			@Override
			public void update(GLViewport v) {
				if (!init) {
					mMapPosition.copy(v.pos);
					init = true;
				}

				buckets.clear();
				buckets.set(ll);
				GeometryBuffer.makeCircle(g, 0, 0, 400, 20);

				//	g.clear();
				//	g.startLine();
				//g.addPoint(-1, 0);
				//	g.addPoint(0, 0);
				//g.scale(100, 100);

				ll.addLine(g);

				compile();

				angle = Gdx.input.getX() / 2f % 360;

				MapRenderer.animate();
			}
		}));
	}

	public static void main(String[] args) {
		GdxMapApp.init();
		GdxMapApp.run(new LineTexTest(), null, 256);
	}
}
