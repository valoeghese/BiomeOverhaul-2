package tk.valoeghese.biomeoverhaul.test;

import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tk.valoeghese.biomeoverhaul.shape.NormalTypeHeightmap;
import tk.valoeghese.worldcomet.api.noise.Noise;
import tk.valoeghese.worldcomet.api.noise.OctaveOpenSimplexNoise;

// Simulator of "normal terrain" heightmap. Produces a javafx image thereof.
public final class Simulator extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	private static Noise beachNoise;

	@Override
	public void start(Stage stage) throws Exception {
		Pane pane = new Pane();
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		pane.getChildren().add(canvas);
		stage.setScene(new Scene(pane));
		stage.setWidth(WIDTH);
		stage.setHeight(HEIGHT);

		long seed = new Random().nextLong();
		new NormalTypeHeightmap(seed);
		beachNoise = new OctaveOpenSimplexNoise(new Random(seed), 3, 300.0);

		stage.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			System.out.println((e.getScreenX() * SCALE) + ", " + (e.getScreenY() * SCALE));
		});

		drawTo(canvas.getGraphicsContext2D().getPixelWriter(), WIDTH, HEIGHT);
		stage.show();
	}

	private static void drawTo(PixelWriter writer, int width, int height) {
		for (int x = 0; x < width; ++x) {
			for (int z = 0; z < height; ++z) {
				int beach = (int) (1.5 + 3 * beachNoise.sample(x * SCALE, z * SCALE));
				writer.setColor(x, z, getColour(NormalTypeHeightmap.INSTANCE.getHeight(x * SCALE, z * SCALE), beach, x * SCALE, z * SCALE));
			}
		}
	}

	private static Color getColour(double height, int beachHeightOffset, int x, int z) {
		int h = (int) height + 63;

		if (h > 204) {
			return Color.WHITE;
		} else if (h < 63) {
			return Color.rgb(10, 20, (int) map(h, 0, 63, 0, 0xED));
		} else if (h <= 63 + beachHeightOffset) {
			int c = (int) map(h, 63, 67, 0xc5, 0xf5);
			return Color.rgb(c, c, 0);
		} else if (h < 135) {
			return Color.rgb(0x32, (int) map(h, 63, 135, 0x50, 0xb0), 0x32);
		} else if (h < 153) {
			return Color.rgb((int) map(h, 135, 153, 0x6b, 0xab), (int) map(h, 135, 153, 0x25, 0x65), 0x13);
		} else {
			return Color.gray(map(h, 153, 204, 0.4, 1.0));
		}
	}

	private static double map(double value, double min, double max, double newmin, double newmax) {
		value -= min;
		value /= (max - min);
		return newmin + value * (newmax - newmin);
	}

	@SuppressWarnings("unused")
	private Color getColourOld(double height, int beachHeightOffset, int x, int z) {
		int h = (int) height + 63;

//		if ((h & 10) == 0) return Color.BLACK;

		if (h > 204) {
			return Color.WHITE;
		} else if (h < 53) {
			return Color.BLUE;
		} else if (h < 63) {
			return Color.CORNFLOWERBLUE;
		} else if (h <= 63 + beachHeightOffset) {
			return Color.BEIGE;
		} else if (h < 135) {
			if (NormalTypeHeightmap.INSTANCE.isMountain(x, z)) {
				return Color.GREEN;
			}
			return Color.LIMEGREEN;
		} else if (h < 153) {
			return Color.SADDLEBROWN;
		} else {
			return Color.DARKGREY;
		}
	}

	private static final int SCALE = 4;
	private static final int WIDTH = 1000, HEIGHT = 800;
}
