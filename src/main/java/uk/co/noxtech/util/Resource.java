package uk.co.noxtech.util;

import java.net.URL;

public class Resource {

    public static URL getResource(String name) {
        return Resource.class.getClassLoader().getResource(name);
    }

}
