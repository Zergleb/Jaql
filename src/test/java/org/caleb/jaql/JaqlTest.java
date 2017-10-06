package org.caleb.jaql;

import static org.junit.Assert.*;
import org.junit.Test;

public class JaqlTest {

    @Test
    public void testNothing() throws Throwable {
        Jaql jaql = new Jaql(JaqlTest.class.getResourceAsStream("testFile.sql"));

        assertEquals("select * from something;", jaql.getQueryByName("firstQuery"));
        assertEquals("select * from nothing;", jaql.getQueryByName("secondQuery"));
        assertEquals("select * from jacob", jaql.getQueryByName("jacobQuery"));
        assertEquals("select * from caleb", jaql.getQueryByName("calebQuery"));
    }
}
