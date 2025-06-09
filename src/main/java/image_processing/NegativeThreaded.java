package image_processing;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class NegativeThreaded extends Thread {
	private Image before;
	private WritableImage after;
	private int id;

	public NegativeThreaded(int id, Image before, WritableImage after) {
		this.before = before;
		this.after = after;
		this.id = id;
	}

	public void run() {
		PixelWriter pw = after.getPixelWriter();
		PixelReader pr = before.getPixelReader();
		for (int x = id % 4; x < before.getWidth(); x += 4) {
			for (int y = 0; y < before.getHeight(); y++) {
				pw.setColor(x, y, pr.getColor(x, y).invert());
			}
		}
	}
}
