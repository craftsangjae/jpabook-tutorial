package learning.examples.lombok;

import org.junit.Test;

import static org.junit.Assert.*;

public class LombokOnlyGetterTest {

    @Test
    public void getValuesFromLombokOnlyGetter() {
        LombokOnlyGetter hello = new LombokOnlyGetter("Hello", 10);

        assertEquals(hello.getId(), 10); assertEquals(hello.getName(), "Hello");
    }

    @Test
    public void getValuesFromLombokGetterSetter() {
        LombokGetterSetter hello = new LombokGetterSetter("Hello", 10);
        assertEquals(hello.getId(), 10); assertEquals(hello.getName(), "Hello");

        int dstId = 13; String dstName = "hi";

        hello.setId(dstId); hello.setName(dstName);

        assertEquals(hello.getId(), dstId); assertEquals(hello.getName(), dstName);
    }

}