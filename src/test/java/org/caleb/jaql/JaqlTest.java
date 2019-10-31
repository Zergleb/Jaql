package org.caleb.jaql;

import static org.junit.Assert.*;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class JaqlTest {

    @Test
    public void testRetrieveTemplates() throws Throwable {
        Jaql jaql = new Jaql(JaqlTest.class.getResourceAsStream("testFile.sql"), "${", "}");

        assertEquals(14, jaql.getQueryNames().size());
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
        assertEquals("select * from templateQuery where name = 'nameValue'", jaql.getQueryByNameUnsafeNoEscapeVals("templateQuery", ImmutableMap.of("name", "nameValue")));
        String hl7Result = "MSH|^~\\&|EPIC|EPICADT|SMS|SMSADT|199912271408|CHARRIS|ADT^A04|1817457|D|2.5|\n" + 
        "PID||0493575^^^2^ID 1|454721||DOE^JOHN^^^^|DOE^JOHN^^^^|19480203|M||B|254 MYSTREET AVE^^MyTown^OH^44123^USA||(216)123-4567|||M|NON|400003403~1129086|\n" + 
        "NK1||MyLastName^MyFirstName^^^^|SPO||(216)123-4567||EC|||||||||||||||||||||||||||\n" + 
        "PV1||O|168 ~219~C~PMA^^^^^^^^^||||277^ALLEN MYLASTNAME^BONNIE^^^^|||||||||| ||2688684|||||||||||||||||||||||||199912271408||||||002376853";
        String hl7ExtractedValue = jaql.getQueryByNameUnsafeNoEscapeVals("notAQueryButHl7ExtraUseCaseMultiline", ImmutableMap.of("LastName", "MyLastName", "FirstName", "MyFirstName", "Town", "MyTown", "ADTTYPE", "A04"));
        assertEquals(hl7Result, hl7ExtractedValue);
    }
}
