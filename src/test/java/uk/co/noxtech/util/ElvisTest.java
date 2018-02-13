package uk.co.noxtech.util;


import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static uk.co.noxtech.util.Elvis.nullSafe;

import java.util.Optional;

import org.junit.Test;

import lombok.Data;

public class ElvisTest {

    @Data
    private static class ElvisTestSubject {

        private Address address;
    }

    @Data
    private static class Address {

        private Street street;
    }

    private static class Street {

        private String throwNPE() {
            throw new NullPointerException("Woops");
        }
    }

    @Test
    public void nullSafe_should_return_OptionalEmpty() throws Exception {
        final ElvisTestSubject elvisTestSubject = new ElvisTestSubject();

        final Optional<String> result = nullSafe(() -> elvisTestSubject.getAddress().getStreet().throwNPE());

        assertThat(result, is(empty()));
    }
}
