package no.automouse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * User: Michael Johansen
 * Date: 18.04.13
 * Time: 23:34
 */
public class ApplicationTest {

    private Application application;
    private IncrementalClock clock;
    private ScheduledExecutorService executorService;
    private Map<String, Integer> properties;
    @Mock
    private Robot robot;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        executorService = Executors.newSingleThreadScheduledExecutor();
        clock = new IncrementalClock();
        properties = Application.defaultArgs();

        properties.put("dx", 2);
        properties.put("dy", 2);
        properties.put("dt", 100);
        properties.put("start", 5);
        properties.put("stop", 7);

        application = new Application(properties, clock, executorService, robot);
    }

    @Test
    public void testMouseIsMoved() throws Exception {
        application.start();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        verify(robot, atLeastOnce()).mouseMove(anyInt(), anyInt());
    }

    private static class IncrementalClock implements Clock {

        private int hour;

        @Override
        public int getHour() {

            return hour++;
        }
    }
}
