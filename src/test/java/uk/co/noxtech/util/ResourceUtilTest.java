package uk.co.noxtech.util;


import static java.lang.ClassLoader.getSystemResource;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;

public class ResourceUtilTest {

    @Test
    public void getSystemResourceAsString() {
        assertThat(ResourceUtil.getSystemResourceAsString("test.txt"))
            .isEqualTo("Hi there\nLine 2\noœ");
    }

    @Test
    public void getSystemResourceAsFile() {
        assertThat(ResourceUtil.getSystemResourceAsFile("test.txt"))
            .isEqualTo(new File(getSystemResource("test.txt").getFile()));
    }
}