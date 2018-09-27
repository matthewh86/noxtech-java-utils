package uk.co.noxtech.util;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.Objects.nonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import uk.co.noxtech.exception.ResourceException;

public class ResourceUtil {

    /**
     * Makes use of {@link java.lang.ClassLoader#getSystemResourceAsStream(String name)} to get the resource as a String.
     *
     * @param resource the resource to load
     * @return the resource as a String
     */
    public static String getSystemResourceAsString(final String resource) {
        final InputStreamReader isr = new InputStreamReader(getSystemResourceAsStream(resource));
        final BufferedReader br = new BufferedReader(isr);

        final String result = br.lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            isr.close();
            br.close();
        } catch (final IOException e) {
            throw new ResourceException("Error closing reader.", e);
        }
        return result;
    }

    /**
     * Makes use of {@link java.lang.ClassLoader#getResource(String)} to get the resource name and return a new {@link java.io.File}
     *
     * @param resource the resource to load
     * @return the resource as a File
     */
    public static File getSystemResourceAsFile(final String resource) {
        URL url = ResourceUtil.class.getClassLoader().getResource(resource);

        if (nonNull(url)) {
            return new File(url.getFile());
        }
        throw new ResourceException("Error finding resource: " + resource);
    }
}
