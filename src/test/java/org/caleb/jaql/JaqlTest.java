package org.caleb.jaql;

import static org.junit.Assert.*;
import org.junit.Test;

public class JaqlTest {

    @Test
    public void testNothing() throws Throwable {
        Jaql jaql = new Jaql(JaqlTest.class.getResourceAsStream("testFile.sql"));

        assertEquals(12, jaql.getQueryNames().size());
        assertNull(jaql.getQueryByName("doesntExist"));
        assertNull(jaql.getQueryByName("badNameLocation"));
        assertEquals("select * from something;", jaql.getQueryByName("firstQuery"));
        assertEquals("select * from nothing;", jaql.getQueryByName("secondQuery"));
        assertEquals("select * from caleb", jaql.getQueryByName("calebQuery"));
        assertEquals("select * from jacob", jaql.getQueryByName("jacobQuery"));
        assertEquals("select * from justice", jaql.getQueryByName("justiceQuery"));
        assertEquals("select * from nospace", jaql.getQueryByName("noSpaceQuery"));
        assertEquals("select * from manyspaces", jaql.getQueryByName("manySpaceQuery"));
        assertEquals("select * from allspaces", jaql.getQueryByName("allSpaceQuery"));
        assertEquals("", jaql.getQueryByName("blankQuery"));
        assertEquals("select * from queryWithComment\n" +
                "select * from inlineComment\n" +
                "select * from multilineComment", jaql.getQueryByName("queryWithComment"));
        assertNull(jaql.getQueryByName(""));
        assertEquals("select * from spacedname", jaql.getQueryByName("spaced Name Query"));
        assertEquals("select * from endquery", jaql.getQueryByName("endQuery"));
    }
}
