package tk.valoeghese.biomeoverhaul.test;

import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tk.valoeghese.biomeoverhaul.shape.NormalTypeHeightmap;

public final class Simulator extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Pane pane = new Pane();
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		pane.getChildren().add(canvas);
		stage.setScene(new Scene(pane));
		stage.setWidth(WIDTH);
		stage.setHeight(HEIGHT);

		new NormalTypeHeightmap(new Random().nextLong());
		drawTo(canvas.getGraphicsContext2D().getPixelWriter(), WIDTH, HEIGHT);
		stage.show();
	}

	private void drawTo(PixelWriter writer, int width, int height) {
		for (int x = 0; x < width; ++x) {
			for (int z = 0; z < height; ++z) {
				writer.setColor(x, z, getColour(NormalTypeHeightmap.INSTANCE.getHeight(x * SCALE, z * SCALE)));
			}
		}
	}

	private Color getColour(double height) {
		int h = (int) height + 63;

		if (h > 204) {
			return Color.WHITE;
		} else if (h < 53) {
			return Color.BLUE;
		} else if (h < 63) {
			return Color.CORNFLOWERBLUE;
		} else if (h < 65) {
			return Color.BEIGE;
		} else if (h < 135) {
			return Color.LIMEGREEN;
		} else if (h < 153) {
			return Color.SADDLEBROWN;
		} else {
			return Color.DARKGREY;
		}
	}

	private static final int SCALE = 4;
	private static final int WIDTH = 800, HEIGHT = 600;
}
