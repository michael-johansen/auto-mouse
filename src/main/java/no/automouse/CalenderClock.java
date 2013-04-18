package no.automouse;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * User: Michael Johansen
 * Date: 18.04.13
 * Time: 23:17
 */
class CalenderClock implements Clock {
    private GregorianCalendar calendar = new GregorianCalendar();

    @Override
    public int getHour() {
        calendar.setTime(new Date());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
