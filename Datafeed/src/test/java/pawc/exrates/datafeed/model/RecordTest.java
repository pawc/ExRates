package pawc.exrates.datafeed.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RecordTest 
    extends TestCase
{
    public RecordTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( RecordTest.class );
    }

    public void testRecord()
    {
       String input = "Sat, 15 Aug 2015 12:00:01 GMT";
       Date date = new Date(input);
       Record record = new Record("EUR", "Euro", date, "4.101");
       assertTrue(record.getFullName().equals("Euro"));
       assertTrue(record.getName().equals("EUR"));
       assertTrue(record.getDateToSQLFormat().equals("15-Aug-2015"));
       assertTrue(record.getTimeToSQLFormat().equals("12:00:01"));
       assertTrue(Double.compare(Double.parseDouble(record.getRate()), 4.101)==0);
    }
}
