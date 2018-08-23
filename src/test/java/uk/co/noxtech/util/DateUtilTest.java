package uk.co.noxtech.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

public class DateUtilTest {

    @Test
    public void toDateTime_shouldCreateDateTime_fromLocalDate() {
        assertThat(DateUtil.toDateTime(
            LocalDate.of(2000, 1, 1)))
            .isEqualByComparingTo(new DateTime(2000, 1, 1, 0, 0, 0, 0));
    }

    @Test
    public void toDateTime_shouldCreateDateTime_fromLocalDateTime() {
        assertThat(DateUtil.toDateTime(
            LocalDateTime.of(2000, 1, 1, 12, 30, 30, 500000)))
            .isEqualByComparingTo(new DateTime(2000, 1, 1, 12, 30, 30, 500));
    }

    @Test
    public void toDateTime_shouldCreateDateTime_fromZonedDateTime() {
        assertThat(DateUtil.toDateTime(
            ZonedDateTime.of(2000, 1, 1, 12, 30, 30, 500000, ZoneId.of("UTC"))))
            .isEqualByComparingTo(new DateTime(2000, 1, 1, 12, 30, 30, 500, DateTimeZone.UTC));
    }

    @Test
    public void toLocalDate_shouldCreateLocalDate_fromDateTime() {
        assertThat(DateUtil.toLocalDate(new DateTime(2000, 1, 1, 0, 0, 0, 0)))
            .isEqualTo(LocalDate.of(2000, 1, 1));
    }

    @Test
    public void toLocalDateTime_shouldCreateLocalDateTime_fromDateTime() {
        assertThat(DateUtil.toLocalDateTime(new DateTime(2000, 1, 1, 12, 30, 30, 500)))
            .isEqualTo(LocalDateTime.of(2000, 1, 1, 12, 30, 30, 500000));
    }

    @Test
    public void toZonedDateTime_shouldCreateZoneDateTime_fromDateTime() {
        assertThat(DateUtil.toZonedDateTime(new DateTime(2000, 1, 1, 12, 30, 30, 500, DateTimeZone.UTC)))
            .isEqualTo(ZonedDateTime.of(2000, 1, 1, 12, 30, 30, 500000, ZoneId.of("UTC")));
    }

}
