package image_processing;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ContourThreaded extends Thread {
	private Image before;
	private WritableImage after;
	private int id;

	public ContourThreaded(int id, Image before, WritableImage after) {
		this.before = before;
		this.after = after;
		this.id = id;
	}

	public void run() {
		int[][] convolutionMatrix = { { -1, -1, -1 }, { -1, 8, -1 }, { -1, -1, -1 } };
		PixelWriter pw = after.getPixelWriter();
		PixelReader pr = before.getPixelReader();
		for (int x = 0; x < before.getWidth(); x++) {
			pw.setColor(x, 0, pr.getColor(x, 0).grayscale());
			pw.setColor(x, (int) before.getHeight() - 1,
					pr.getColor(x, (int) before.getHeight() - 1).grayscale());
		}
		for (int y = 0; y < before.getHeight(); y++) {
			pw.setColor(0, y, pr.getColor(0, y).grayscale());
			pw.setColor((int) before.getWidth() - 1, y,
					pr.getColor((int) before.getWidth() - 1, y).grayscale());
		}
		for (int x = 1 + id % 4; x < before.getWidth() - 1; x += 4) {
			for (int y = 1; y < before.getHeight() - 1; y++) {
				Color[][] colors = new Color[3][3];
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						colors[i][j] = pr.getColor(x + i - 1, y + j - 1).grayscale();
					}
				}
				int[][] grays = new int[3][3];
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						grays[i][j] = (int) (colors[i][j].getRed() * 255 * convolutionMatrix[i][j]);
					}
				}
				int Gray = 0;
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						Gray += grays[i][j];
					}
				}
				Gray = Math.max(Gray, 0);
				Gray = Math.min(Gray, 255);
				pw.setColor(x, y, Color.grayRgb(Gray));
			}
		}
	}
}
