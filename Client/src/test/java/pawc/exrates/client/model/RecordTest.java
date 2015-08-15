package pawc.exrates.client.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import pawc.exrates.client.model.Record;


public class RecordTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RecordTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RecordTest.class );
    }

    
    public void testRecord(){
        String symbol = "EUR";
        Double rate = 1.23456;
        String name = "Euro";
        String date = "2015-03-20";
        String time = "12:00:00";
        Record record = new Record(symbol, rate, date, time, name);
        assertNotNull(record);
        assertTrue(record instanceof Record);
        assertEquals(symbol, record.getSymbol());
        assertEquals(rate, record.getRate());
        assertEquals(name, record.getName());
        assertEquals(date, record.getDate());
        assertEquals(time, record.getTime());
        record.setSymbol("USD");
        record.setRate(2.34242);
        record.setName("US Dollar");
        record.setDate("2014-02-10");
        record.setTime("13:00:00");
        assertEquals("USD", record.getSymbol());
        assertEquals(2.34242, record.getRate());
        assertEquals("US Dollar", record.getName());
        assertEquals("2014-02-10", record.getDate());
        assertEquals("13:00:00", record.getTime());
        
    }
    
    
}
