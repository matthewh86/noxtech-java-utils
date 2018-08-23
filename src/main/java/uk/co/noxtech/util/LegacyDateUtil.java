package uk.co.noxtech.util;

import static java.util.Objects.nonNull;

import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTimeZone;

/**
 * <p>Utility class to easily convert between java.util.date and the Java 8 Date API or the JodaTime API. </p>
 * <p>This defaults to UTC for convenience.</p>
 * <p>Also includes date difference, and date shifting methods.</p>
 */
public final class LegacyDateUtil {

    private static ZoneOffset ZONE_OFFSET = ZoneOffset.of(ZoneOffset.UTC.getId());

    public static ZoneOffset getZoneOffset() {
        return ZONE_OFFSET;
    }

    public static void setZoneOffset(final ZoneOffset zoneOffset) {
        ZONE_OFFSET = zoneOffset;
    }

    private LegacyDateUtil() {
    }

    /* --- Java 8 API -> Date --- */

    public static Date toDate(final java.time.LocalDate localDate) {
        if (nonNull(localDate)) {
            return Date.from(localDate.atStartOfDay(ZONE_OFFSET).toInstant());
        }
        return null;
    }

    public static Date toDate(final java.time.LocalDateTime localDateTime) {
        if (nonNull(localDateTime)) {
            return Date.from(localDateTime.toInstant(ZONE_OFFSET));
        }
        return null;
    }

    public static Date toDate(final ZonedDateTime zonedDateTime) {
        if (nonNull(zonedDateTime)) {
            return Date.from(zonedDateTime.toInstant());
        }
        return null;
    }

    /* --- Date -> Java 8 API --- */

    public static java.time.LocalDate toLocalDate(final Date date) {
        if (nonNull(date)) {
            return toZonedDateTime(date).toLocalDate();
        }
        return null;
    }

    public static java.time.LocalDateTime toLocalDateTime(final Date date) {
        if (nonNull(date)) {
            return toZonedDateTime(date).toLocalDateTime();
        }
        return null;
    }

    public static ZonedDateTime toZonedDateTime(final Date date) {
        if (nonNull(date)) {
            return date.toInstant().atZone(ZONE_OFFSET);
        }
        return null;
    }

    /* --- JodaTime API -> Date --- */

    public static Date toDate(final org.joda.time.DateTime dateTime) {
        if (nonNull(dateTime)) {
            return dateTime.toDate();
        }
        return null;
    }

    public static Date toDate(final org.joda.time.LocalDate localDate) {
        if (nonNull(localDate)) {
            return localDate.toDate();
        }
        return null;
    }

    public static Date toDate(final org.joda.time.LocalDateTime localDateTime) {
        if (nonNull(localDateTime)) {
            return localDateTime.toDate(TimeZone.getTimeZone(ZONE_OFFSET));
        }
        return null;
    }

    /* --- Date -> JodaTime API --- */

    public static org.joda.time.DateTime toJodaDateTime(final Date date) {
        if (nonNull(date)) {
            return new org.joda.time.DateTime(date, getJodaDateTimeZone());
        }
        return null;
    }

    public static org.joda.time.LocalDate toJodaLocalDate(final Date date) {
        if (nonNull(date)) {
            return new org.joda.time.LocalDate(date, getJodaDateTimeZone());
        }
        return null;
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(final Date date) {
        if (nonNull(date)) {
            return new org.joda.time.LocalDateTime(date, getJodaDateTimeZone());
        }
        return null;
    }

    private static DateTimeZone getJodaDateTimeZone() {
        return DateTimeZone.forTimeZone(TimeZone.getTimeZone(ZONE_OFFSET));
    }

    public enum TimeUnit {
        MILLISECOND(Calendar.MILLISECOND, ChronoUnit.MILLIS),
        SECOND(Calendar.SECOND, ChronoUnit.SECONDS),
        MINUTE(Calendar.MINUTE, ChronoUnit.MINUTES),
        HOUR(Calendar.HOUR, ChronoUnit.HOURS),
        DAY(Calendar.DATE, ChronoUnit.DAYS),
        MONTH(Calendar.MONTH, ChronoUnit.MONTHS),
        YEAR(Calendar.YEAR, ChronoUnit.YEARS);

        private int calendarValue;

        private ChronoUnit chronoUnitValue;

        TimeUnit(final int calendarValue, final ChronoUnit chronoUnitValue) {
            this.calendarValue = calendarValue;
            this.chronoUnitValue = chronoUnitValue;
        }
    }

    /**
     * Returns a new Date with the time shifted by the amount of TimeUnits.
     *
     * @param date date to be shifted
     * @param timeUnit TimeUnit to use
     * @param amount amount of TimeUnits to shift
     * @return the date shifted by the amount of TimeUnits.
     */
    public static Date chronoShift(final Date date, final TimeUnit timeUnit, final int amount) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(timeUnit.calendarValue, amount);
        return cal.getTime();
    }

    /**
     * Returns the TimeUnit difference between two dates.
     * E.g. chronoDiff(2000-01-01 23:59:59, 2000-01-02 00:00:00, DAY) would return 0 days as there is only a 1 second difference.
     *
     * @param left the date to query against the right date.
     * @param right the date the left is queried against.
     * @param timeUnit the {@link TimeUnit} to return (e.g. {@link TimeUnit#DAY})
     * @return the TimeUnit difference between query and target (e.g. -1L).
     * positive if left is before the right.
     * negative if left is after the right.
     */
    public static Long chronoDiff(final Date left, final Date right, final TimeUnit timeUnit) {
        if (TimeUnit.YEAR.equals(timeUnit) || TimeUnit.MONTH.equals(timeUnit)) {
            final YearMonth mLeft = YearMonth.from(left.toInstant().atOffset(ZONE_OFFSET).toLocalDate());
            final YearMonth mRight = YearMonth.from(right.toInstant().atOffset(ZONE_OFFSET).toLocalDate());
            return mLeft.until(mRight, timeUnit.chronoUnitValue);
        }

        return timeUnit.chronoUnitValue.between(left.toInstant(), right.toInstant());
    }

    /**
     * Returns the TimeUnit difference between two dates.
     *
     * This will truncate the dates to the TimeUnit specified.
     * E.g. truncateDiff(2000-01-01 23:59:59, 2000-01-02 00:00:00, DAY) would return 1 day despite only having 1 second difference.
     *
     * @param left the date to query against the right date.
     * @param right the date the left is queried against.
     * @param timeUnit the {@link TimeUnit} to return (e.g. {@link TimeUnit#DAY})
     * @return the TimeUnit difference between query and target (e.g. -1L).
     * positive if left is before the right.
     * negative if left is after the right.
     */
    public static Long truncateDiff(final Date left, final Date right, final TimeUnit timeUnit) {
        final Date leftTruncated = toDate(toZonedDateTime(left).truncatedTo(timeUnit.chronoUnitValue));
        final Date rightTruncated = toDate(toZonedDateTime(right).truncatedTo(timeUnit.chronoUnitValue));
        return chronoDiff(leftTruncated, rightTruncated, timeUnit);
    }

    /**
     * Construct a new date. Uses ZonedDateTime at ZONE_OFFSET under the covers.
     *
     * @param year the year to represent, from MIN_YEAR to MAX_YEAR
     * @param month the month-of-year to represent, from 1 (January) to 12 (December)
     * @param day the day-of-month to represent, from 1 to 31
     * @param hour the hour-of-day to represent, from 0 to 23
     * @param minute the minute-of-hour to represent, from 0 to 59
     * @param second the second-of-minute to represent, from 0 to 59
     * @param millisecond the number of milliseconds to represent, from 0 to 999.
     * @return a new Date with the given time values.
     */
    public static Date date(final int year, final int month, final int day, final int hour, final int minute, final int second, final int millisecond) {
        return toDate(ZonedDateTime.of(year, month, day, hour, minute, second, millisecond * 1000000, ZONE_OFFSET));
    }

    /**
     * Construct a new date. Uses ZonedDateTime at ZONE_OFFSET under the covers. Sets milliseconds to 0.
     *
     * @param year the year to represent, from MIN_YEAR to MAX_YEAR
     * @param month the month-of-year to represent, from 1 (January) to 12 (December)
     * @param day the day-of-month to represent, from 1 to 31
     * @param hour the hour-of-day to represent, from 0 to 23
     * @param minute the minute-of-hour to represent, from 0 to 59
     * @param second the second-of-minute to represent, from 0 to 59
     * @return a new Date with the given time values with milliseconds set to 0.
     */
    public static Date date(final int year, final int month, final int day, final int hour, final int minute, final int second) {
        return date(year, month, day, hour, minute, second, 0);
    }

    /**
     * Construct a new date at midnight. Uses ZonedDateTime at ZONE_OFFSET under the covers. Sets hour, minute, second and millisecond to 0.
     *
     * @param year the year to represent, from MIN_YEAR to MAX_YEAR
     * @param month the month-of-year to represent, from 1 (January) to 12 (December)
     * @param day the day-of-month to represent, from 1 to 31
     * @return a new Date with the given time values with time (HH:MM:SS.sss) set to zeroes.
     */
    public static Date date(final int year, final int month, final int day) {
        return date(year, month, day, 0, 0, 0, 0);
    }

}
