package no.automouse;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Application {
    private final Robot robot;
    private final Random random = new Random(42L);
    private final Map<String, Integer> properties;
    private final Clock clock;
    private Point oldMouseLocation = MouseInfo.getPointerInfo().getLocation();
    private ScheduledExecutorService scheduledExecutorService;
    private Runnable command = new Runnable() {
        @Override
        public void run() {
            System.out.println("executing task.");

            Point newMouseLocation = getMouseLocation();
            boolean atSameLocation = oldMouseLocation.equals(newMouseLocation);
            if (atSameLocation && isActive()) {
                int x = randomStep(properties.get("dx"), oldMouseLocation.x);
                int y = randomStep(properties.get("dy"), oldMouseLocation.y);
                robot.mouseMove(x, y);
            }
            oldMouseLocation = getMouseLocation();
        }
    };


    public Application(Map<String, Integer> properties, Clock clock, ScheduledExecutorService executorService, Robot robot) throws AWTException {
        this.properties = properties;
        this.clock = clock;
        this.robot = robot;
        System.out.println("Parameters can be changed with key:value arguments.");
        System.out.println("Current parameters:");
        System.out.println(properties);
        scheduledExecutorService = executorService;
    }

    static Map<String, Integer> defaultArgs() {
        HashMap<String, Integer> arguments = new HashMap<String, Integer>();
        arguments.put("dt", 1000);
        arguments.put("dx", 2);
        arguments.put("dy", 2);
        arguments.put("start", 0);
        arguments.put("stop", 24);
        return arguments;
    }

    public static void main(String... args) throws Exception {
        final Map<String, Integer> prop = argumentsToMap(args);
        Application application = new Application(prop, new CalenderClock(), Executors.newScheduledThreadPool(2), new Robot());
        application.start();
    }

    public static Map<String, Integer> argumentsToMap(String... arguments) {
        Map<String, Integer> map = defaultArgs();
        for (String string : arguments) {
            String[] split = string.split(":");
            map.put(split[0], Integer.parseInt(split[1]));
        }
        return map;
    }

    void start() {
        System.out.printf("Scheduling fixed rate task @%d ms%n", properties.get("dt"));
        scheduledExecutorService.scheduleAtFixedRate(command, 0, properties.get("dt"), TimeUnit.MILLISECONDS);
    }

    private boolean isActive() {
        int currentHour = clock.getHour();
        return properties.get("start") <= currentHour && currentHour < properties.get("stop");
    }

    private Point getMouseLocation() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    private int randomStep(Integer delta, int base) {
        return delta * random.nextInt(2) - delta / 2 + base;
    }
}
