package image_processing;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ThresholdThreaded extends Thread {
	private Image before;
	private WritableImage after;
	private int id;
	private int threshold;

	public ThresholdThreaded(int id, Image before, WritableImage after, int threshold) {
		this.before = before;
		this.after = after;
		this.id = id;
		this.threshold = threshold;
	}

	public void run() {
		PixelWriter pw = after.getPixelWriter();
		PixelReader tpr = after.getPixelReader();
		PixelReader pr = before.getPixelReader();
		for (int x = id % 4; x < before.getWidth(); x += 4) {
			for (int y = 0; y < before.getHeight(); y++) {
				pw.setColor(x, y, pr.getColor(x, y).grayscale());
				if (tpr.getColor(x, y).getRed() * 255 < threshold) {
					pw.setColor(x, y, Color.BLACK);
				} else {
					pw.setColor(x, y, Color.WHITE);
				}
			}
		}
	}
}
