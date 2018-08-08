package uk.co.noxtech.util;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Based on Groovy's Elvis operator. For lambda backed safe null checking.
 */
public final class Elvis {

    private Elvis() {
    }

    /**
     * Will return an {@link Optional}&lt;T&gt; from the given {@link Supplier}.
     *
     * @param resolver a method chain e.g. car.getManufacturer().getName()
     * @param <T> the return type
     * @return the type wrapped in {@link Optional}
     */
    public static <T> Optional<T> nullSafe(final Supplier<T> resolver) {
        try {
            final T result = resolver.get();
            return Optional.ofNullable(result);
        } catch (final NullPointerException e) {
            return Optional.empty();
        }
    }

    /**
     * Shortcut for {@link Elvis#nullSafe}(() -&lt; methodchain).orElse(null)
     *
     * @param resolver a method chain e.g. car.getManufacturer().getName()
     * @param <T> the return type
     * @return the value from resolver or null
     */
    public static <T> T nullable(final Supplier<T> resolver) {
        return nullSafe(resolver).orElse(null);
    }

}
