package uk.co.noxtech.util;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Based on Groovy's Elvis operator. For lambda backed safe null checking.
 */
public final class Elvis {

    private Elvis() {
    }

    public static <T> Optional<T> nullSafe(final Supplier<T> resolver) {
        try {
            final T result = resolver.get();
            return Optional.ofNullable(result);
        } catch (final NullPointerException e) {
            return Optional.empty();
        }
    }

}
