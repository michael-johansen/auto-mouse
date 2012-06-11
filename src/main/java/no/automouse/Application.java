package no.automouse;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.Random;

public class Application {
	public static void main(String... args) throws Exception {
		int dt = 1000;
		int dx = 2;
		int dy = 2;
		if (args.length >= 1)
			dt = Integer.parseInt(args[0]);
		if (args.length >= 2)
			dx = Integer.parseInt(args[1]);
		if (args.length >= 3)
			dy = Integer.parseInt(args[2]);

		Robot robot = new Robot();
		Random random = new Random();
		Point oldMouseLocation = MouseInfo.getPointerInfo().getLocation();
		while (true) {
			Point newMouseLocation = MouseInfo.getPointerInfo().getLocation();
			if (oldMouseLocation.equals(newMouseLocation)) {
				robot.mouseMove(dx * random.nextInt(2) - dx / 2 + oldMouseLocation.x, dy * random.nextInt(2) - dy / 2
						+ oldMouseLocation.y);
			}
			oldMouseLocation = MouseInfo.getPointerInfo().getLocation();
			Thread.sleep(dt);
		}
	}
}
