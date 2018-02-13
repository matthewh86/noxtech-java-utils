package uk.co.noxtech.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static uk.co.noxtech.util.LegacyDateUtil.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTimeZone;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import uk.co.noxtech.util.LegacyDateUtil.TimeUnit;

public class LegacyDateUtilTest {

    private static final int YEAR = 2000;
    private static final int MONTH = 1;
    private static final int DAY_OF_MONTH = 1;

    private static final int HOUR = 0;
    private static final int MINUTE = 0;
    private static final int SECOND = 0;
    private static final int NANO_SECOND = 0;

    private static final Date JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0 = getExpectedDate();
    private static final Date JAVA_UTIL_DATE_Y2000_M1_D2_H0_m0_s0 = date(2000, 1, 2, 0, 0, 0);
    private static final Date JAVA_UTIL_DATE_Y2000_M1_D1_H23_m59_s59 = date(2000, 1, 1, 23, 59, 59);
    private static final Date JAVA_UTIL_DATE_Y2000_M2_D1_H0_m0_s0 = date(2000, 2, 1, 0, 0, 0);
    private static final Date JAVA_UTIL_DATE_Y2001_M1_D1_H0_m0_s0 = date(2001, 1, 1, 0, 0, 0);

    private static final LocalDate JAVA8_LOCAL_DATE = getExpectedLocalDate();
    private static final LocalDateTime JAVA8_LOCAL_DATE_TIME = getExpectedLocalDateTime();
    private static final ZonedDateTime JAVA8_ZONED_DATE_TIME = getExpectedZonedDateTime();

    private static final org.joda.time.DateTime JODA_DATE_TIME = getExpectedJodaDateTime();
    private static final org.joda.time.LocalDate JODA_LOCAL_DATE = getExpectedJodaLocalDate();
    private static final org.joda.time.LocalDateTime JODA_LOCAL_DATE_TIME = getExpectedJodaLocalDateTime();

    /* --- Java Util Date --- */

    private static Calendar getExpectedCalendar() {
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone(LegacyDateUtil.getZoneOffset()));
        instance.set(YEAR, Calendar.JANUARY, DAY_OF_MONTH);
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        return instance;
    }

    private static Date getExpectedDate() {
        return getExpectedCalendar().getTime();
    }

    /* --- Java 8 API --- */

    private static LocalDate getExpectedLocalDate() {
        return LocalDate.of(YEAR, MONTH, DAY_OF_MONTH);
    }

    private static LocalDateTime getExpectedLocalDateTime() {
        return LocalDateTime.of(YEAR, MONTH, DAY_OF_MONTH, HOUR, MINUTE, SECOND, NANO_SECOND);
    }

    private static ZonedDateTime getExpectedZonedDateTime() {
        return ZonedDateTime.of(YEAR, MONTH, DAY_OF_MONTH, HOUR, MINUTE, SECOND, NANO_SECOND, LegacyDateUtil.getZoneOffset());
    }

    /* --- JodaTime API --- */

    private static org.joda.time.DateTime getExpectedJodaDateTime() {
        org.joda.time.DateTime dateTime = new org.joda.time.DateTime();
        dateTime = dateTime.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone(LegacyDateUtil.getZoneOffset())));
        dateTime = dateTime.withYear(YEAR);
        dateTime = dateTime.withMonthOfYear(MONTH);
        dateTime = dateTime.withDayOfMonth(DAY_OF_MONTH);
        dateTime = dateTime.withTime(HOUR, MINUTE, SECOND, NANO_SECOND);
        return dateTime;
    }

    private static org.joda.time.LocalDate getExpectedJodaLocalDate() {
        return org.joda.time.LocalDate.fromCalendarFields(getExpectedCalendar());
    }

    private static org.joda.time.LocalDateTime getExpectedJodaLocalDateTime() {
        return org.joda.time.LocalDateTime.fromCalendarFields(getExpectedCalendar());
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void toDate_should_convertJava8APIDates_to_utilDates() throws Exception {
        Date fromLocalDate = LegacyDateUtil.toDate(JAVA8_LOCAL_DATE);
        Date fromLocalDateTime = LegacyDateUtil.toDate(JAVA8_LOCAL_DATE_TIME);
        Date fromZonedDateTime = LegacyDateUtil.toDate(JAVA8_ZONED_DATE_TIME);

        assertThat(fromLocalDate, is(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0));
        assertThat(fromLocalDateTime, is(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0));
        assertThat(fromZonedDateTime, is(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0));
    }

    @Test
    public void toDate_should_convertJodaTimeDates_to_utilDates() throws Exception {
        Date fromJodaLocalDate = LegacyDateUtil.toDate(JODA_LOCAL_DATE);
        Date fromJodaLocalDateTime = LegacyDateUtil.toDate(JODA_LOCAL_DATE_TIME);
        Date fromJodaDateTime = LegacyDateUtil.toDate(JODA_DATE_TIME);

        assertThat(fromJodaLocalDate, is(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0));
        assertThat(fromJodaLocalDateTime, is(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0));
        assertThat(fromJodaDateTime, is(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0));
    }

    @Test
    public void toJodaDateTime_should_convertUtilDate_to_jodaDateTime() {
        org.joda.time.DateTime dateTime = LegacyDateUtil.toJodaDateTime(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0);

        assertThat(dateTime, is(JODA_DATE_TIME));
    }

    @Test
    public void toJodaLocalDate_should_convertUtilDate_to_jodaLocalDate() {
        org.joda.time.LocalDate localDate = LegacyDateUtil.toJodaLocalDate(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0);

        assertThat(localDate, is(JODA_LOCAL_DATE));
    }

    @Test
    public void toJodaLocalDateTime_should_convertUtilDate_to_jodaLocalDateTime() {
        org.joda.time.LocalDateTime localDateTime = LegacyDateUtil.toJodaLocalDateTime(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0);

        assertThat(localDateTime, is(JODA_LOCAL_DATE_TIME));
    }

    @Test
    public void toLocalDate_should_convertUtilDate_to_java8LocalDate() {
        LocalDate localDate = LegacyDateUtil.toLocalDate(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0);

        assertThat(localDate, is(JAVA8_LOCAL_DATE));
    }

    @Test
    public void toLocalDateTime_should_convertUtilDate_to_java8LocalDateTime() {
        LocalDateTime localDateTime = LegacyDateUtil.toLocalDateTime(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0);

        assertThat(localDateTime, is(JAVA8_LOCAL_DATE_TIME));
    }

    @Test
    public void toZonedLocalDateTime_should_convertUtilDate_to_java8ZonedLocalDateTime() {
        ZonedDateTime zonedDateTime = LegacyDateUtil.toZonedDateTime(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0);

        assertThat(zonedDateTime, is(JAVA8_ZONED_DATE_TIME));
    }

    @Test
    public void chronoShift_should_addASecond() {
        Date expected = new Date(100, Calendar.JANUARY, 1, 0, 0, 1);

        Date date = LegacyDateUtil.chronoShift(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, TimeUnit.SECOND, 1);

        assertThat(date, is(expected));
    }

    @Test
    public void chronoShift_should_subtractASecond() {
        Date expected = new Date(99, Calendar.DECEMBER, 31, 23, 59, 59);

        Date date = LegacyDateUtil.chronoShift(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, TimeUnit.SECOND, -1);

        assertThat(date, is(expected));
    }

    @Test
    public void chronoShift_should_addAMinute() {
        Date expected = new Date(100, Calendar.JANUARY, 1, 0, 1, 0);

        Date date = LegacyDateUtil.chronoShift(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, TimeUnit.MINUTE, 1);

        assertThat(date, is(expected));
    }

    @Test
    public void chronoShift_should_subtractAMinute() {
        Date expected = new Date(99, Calendar.DECEMBER, 31, 23, 59, 0);

        Date date = LegacyDateUtil.chronoShift(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, TimeUnit.MINUTE, -1);

        assertThat(date, is(expected));
    }

    @Test
    public void chronoShift_should_addAnHour() {
        Date expected = new Date(100, Calendar.JANUARY, 1, 1, 0, 0);

        Date date = LegacyDateUtil.chronoShift(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, TimeUnit.HOUR, 1);

        assertThat(date, is(expected));
    }

    @Test
    public void chronoShift_should_subtractAnHour() {
        Date expected = new Date(99, Calendar.DECEMBER, 31, 23, 0, 0);

        Date date = LegacyDateUtil.chronoShift(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, TimeUnit.HOUR, -1);

        assertThat(date, is(expected));
    }

    @Test
    public void chronoShift_should_addADay() {
        Date expected = new Date(100, Calendar.JANUARY, 2);

        Date date = LegacyDateUtil.chronoShift(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, TimeUnit.DAY, 1);

        assertThat(date, is(expected));
    }

    @Test
    public void chronoShift_should_subtractADay() {
        Date expected = new Date(99, Calendar.DECEMBER, 31);

        Date date = LegacyDateUtil.chronoShift(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, TimeUnit.DAY, -1);

        assertThat(date, is(expected));
    }

    @Test
    public void chronoShift_should_addAMonth() throws Exception {
        Date expected = new Date(100, Calendar.FEBRUARY, 1);

        Date date = LegacyDateUtil.chronoShift(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, TimeUnit.MONTH, 1);

        assertThat(date, is(expected));
    }

    @Test
    public void chronoShift_should_subtractAMonth() throws Exception {
        Date expected = new Date(99, Calendar.DECEMBER, 1);

        Date date = LegacyDateUtil.chronoShift(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, TimeUnit.MONTH, -1);

        assertThat(date, is(expected));
    }

    @Test
    public void chronoShift_should_addAYear() throws Exception {
        Date expected = new Date(101, Calendar.JANUARY, 1);

        Date date = LegacyDateUtil.chronoShift(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, TimeUnit.YEAR, 1);

        assertThat(date, is(expected));
    }

    @Test
    public void chronoShift_should_subtractAYear() throws Exception {
        Date expected = new Date(99, Calendar.JANUARY, 1);

        Date date = LegacyDateUtil.chronoShift(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, TimeUnit.YEAR, -1);

        assertThat(date, is(expected));
    }

    @Test
    public void chronoDiff_should_returnMilliSecondsDifference() throws Exception {
        long result = LegacyDateUtil.chronoDiff(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, JAVA_UTIL_DATE_Y2000_M1_D2_H0_m0_s0, TimeUnit.MILLISECOND);

        assertThat(result, is(86400000L));
    }

    @Test
    public void chronoDiff_should_returnSecondsDifference() throws Exception {
        long result = LegacyDateUtil.chronoDiff(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, JAVA_UTIL_DATE_Y2000_M1_D2_H0_m0_s0, TimeUnit.SECOND);

        assertThat(result, is(86400L));
    }

    @Test
    public void chronoDiff_should_returnMinutesDifference() throws Exception {
        Date javaUtil_Y2000_M1_D2_H0_m0_s0 = date(2000, 1, 2, 0, 0, 0);

        long result = LegacyDateUtil.chronoDiff(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, JAVA_UTIL_DATE_Y2000_M1_D2_H0_m0_s0, TimeUnit.MINUTE);

        assertThat(result, is(1440L));
    }

    @Test
    public void chronoDiff_should_returnHoursDifference() throws Exception {
        long result = LegacyDateUtil.chronoDiff(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, JAVA_UTIL_DATE_Y2000_M1_D2_H0_m0_s0, TimeUnit.HOUR);

        assertThat(result, is(24L));
    }

    @Test
    public void chronoDiff_should_returnDaysDifference() throws Exception {
        long result = LegacyDateUtil.chronoDiff(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, JAVA_UTIL_DATE_Y2000_M1_D2_H0_m0_s0, TimeUnit.DAY);

        assertThat(result, is(1L));
    }

    @Test
    public void chronoDiff_should_returnNegativeDaysDifference_for_smallerLeftDate() throws Exception {
        long result = LegacyDateUtil.chronoDiff(JAVA_UTIL_DATE_Y2000_M1_D2_H0_m0_s0, JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, TimeUnit.DAY);

        assertThat(result, is(-1L));
    }

    @Test
    public void chronoDiff_should_returnMonthsDiffernce() throws Exception {
        Long result = LegacyDateUtil.chronoDiff(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, JAVA_UTIL_DATE_Y2000_M2_D1_H0_m0_s0, TimeUnit.MONTH);

        assertThat(result, is(1L));
    }

    @Test
    public void chronoDiff_should_returnYearDifference() throws Exception {
        Long result = LegacyDateUtil.chronoDiff(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0, JAVA_UTIL_DATE_Y2001_M1_D1_H0_m0_s0, TimeUnit.YEAR);

        assertThat(result, is(1L));
    }

    @Test
    public void date_should_createDate() throws Exception {
        Date date = date(YEAR, MONTH, DAY_OF_MONTH, HOUR, MINUTE, SECOND, 0);

        assertThat(date, is(JAVA_UTIL_DATE_Y2000_M1_D1_H0_m0_s0));
    }

    @Test
    public void truncateDiff_should_return1DayDifference_forTwoDates1SecondApart() throws Exception {
        long result = LegacyDateUtil.truncateDiff(JAVA_UTIL_DATE_Y2000_M1_D1_H23_m59_s59, JAVA_UTIL_DATE_Y2000_M1_D2_H0_m0_s0, TimeUnit.DAY);

        assertThat(result, is(1L));
    }

    @Test
    public void chronoDiff_should_return0DaysDifference_forTwoDates1SecondApart() throws Exception {
        long result = LegacyDateUtil.chronoDiff(JAVA_UTIL_DATE_Y2000_M1_D1_H23_m59_s59, JAVA_UTIL_DATE_Y2000_M1_D2_H0_m0_s0, TimeUnit.DAY);

        assertThat(result, is(0L));
    }

    @Test
    public void chronoDiff_should_return1SecondDifference_forTwoDates1SecondApart() throws Exception {
        long result = LegacyDateUtil.chronoDiff(JAVA_UTIL_DATE_Y2000_M1_D1_H23_m59_s59, JAVA_UTIL_DATE_Y2000_M1_D2_H0_m0_s0, TimeUnit.SECOND);

        assertThat(result, is(1L));
    }

    @Test
    public void truncateDiff_should_return242DayDifference_forTwoDates() throws Exception {
        long result = LegacyDateUtil.truncateDiff(date(2018, 1, 1), date(2018, 8, 31), TimeUnit.DAY);

        assertThat(result, is(242L));
    }

}
