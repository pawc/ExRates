package pawc.exrates.datafeed.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DateTest 
    extends TestCase
{
    public DateTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( DateTest.class );
    }

    public void testDate()
    {
       String input = "Sat, 8 Aug 2015 12:00:01 GMT";
       Date date = new Date(input);
       assertTrue(date.getYear().equals("2015"));
       assertTrue(date.getMonth().equals("Aug"));
       assertTrue(date.getDay().equals("8"));
       assertTrue(date.getHour().equals("12"));
       assertTrue(date.getMinutes().equals("00"));
       assertTrue(date.getSeconds().equals("01"));
    }
}
