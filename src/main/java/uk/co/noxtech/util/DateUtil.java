package uk.co.noxtech.util;

import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DateUtil {

    private DateUtil() {
    }

    /**
     * Convert {@link java.time.LocalDate} to {@link org.joda.time.DateTime}
     */
    public static DateTime toDateTime(LocalDate localDate) {
        if (nonNull(localDate)) {
            return new DateTime(DateTimeZone.UTC).withDate(
                localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()
            ).withTime(0, 0, 0, 0);
        }
        return null;
    }

    /**
     * Convert {@link java.time.LocalDateTime} to {@link org.joda.time.DateTime}
     */
    public static DateTime toDateTime(LocalDateTime localDateTime) {
        if (nonNull(localDateTime)) {
            return new DateTime(DateTimeZone.UTC).withDate(
                localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth()
            ).withTime(localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond(), localDateTime.getNano() / 1000);
        }
        return null;
    }

    /**
     * Convert {@link java.time.ZonedDateTime} to {@link org.joda.time.DateTime}
     */
    public static DateTime toDateTime(ZonedDateTime zonedDateTime) {
        if (nonNull(zonedDateTime)) {
            return new DateTime(
                zonedDateTime.getYear(), zonedDateTime.getMonthValue(), zonedDateTime.getDayOfMonth(),
                zonedDateTime.getHour(), zonedDateTime.getMinute(), zonedDateTime.getSecond(),
                zonedDateTime.getNano() / 1000,
                toDateTimeZone(zonedDateTime.getZone()));
        }
        return null;
    }

    /**
     * Convert {@link org.joda.time.DateTime} to {@link java.time.LocalDate}
     */
    public static LocalDate toLocalDate(DateTime dateTime) {
        if (nonNull(dateTime)) {
            DateTime dateTimeUtc = dateTime.withZone(DateTimeZone.UTC);
            return LocalDate.of(dateTimeUtc.getYear(), dateTimeUtc.getMonthOfYear(), dateTimeUtc.getDayOfMonth());
        }
        return null;
    }

    /**
     * Convert {@link org.joda.time.DateTime} to {@link java.time.LocalDateTime}
     */
    public static LocalDateTime toLocalDateTime(DateTime dateTime) {
        if (nonNull(dateTime)) {
            return LocalDateTime.of(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(),
                dateTime.getHourOfDay(), dateTime.minuteOfHour().get(), dateTime.getSecondOfMinute(),
                dateTime.getMillisOfSecond() * 1000);
        }
        return null;
    }

    /**
     * Convert {@link org.joda.time.DateTime} to {@link java.time.ZonedDateTime}
     */
    public static ZonedDateTime toZonedDateTime(DateTime dateTime) {
        if (nonNull(dateTime)) {
            return ZonedDateTime.of(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(),
                dateTime.getHourOfDay(), dateTime.minuteOfHour().get(), dateTime.getSecondOfMinute(),
                dateTime.getMillisOfSecond() * 1000, toZoneId(dateTime.getZone()));
        }
        return null;
    }

    /**
     * Convert {@link org.joda.time.DateTimeZone} to {@link java.time.ZoneId}
     */
    public static ZoneId toZoneId(DateTimeZone dateTimeZone) {
        if (nonNull(dateTimeZone)) {
            return ZoneId.of(dateTimeZone.getID());
        }
        return null;
    }

    /**
     * Convert {@link java.time.ZoneId} to {@link org.joda.time.DateTimeZone}
     */
    public static DateTimeZone toDateTimeZone(ZoneId zoneId) {
        if (nonNull(zoneId)) {
            return DateTimeZone.forID(zoneId.getId());
        }
        return null;
    }

}
