package cashman.enums;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class DenominationTypeTest {

    @Test
    public void testType() {
        Assert.assertEquals(0,DenominationType.ONE_HUNDRED_DOLLAR.valuedouble().compareTo(new Double(100)));
        Assert.assertEquals(0,DenominationType.FIFETY_DOLLAR.valuedouble().compareTo(new Double(50)));
        Assert.assertEquals(0,DenominationType.TWENTY_DOLLAR.valuedouble().compareTo(new Double(20)));
        Assert.assertEquals(0,DenominationType.TEN_DOLLAR.valuedouble().compareTo(new Double(10)));
        Assert.assertEquals(0,DenominationType.FIVE_DOLLAR.valuedouble().compareTo(new Double(5)));
        Assert.assertEquals(0,DenominationType.TWO_DOLLAR.valuedouble().compareTo(new Double(2)));
        Assert.assertEquals(0,DenominationType.ONE_DOLLAR.valuedouble().compareTo(new Double(1)));
        Assert.assertEquals(0,DenominationType.FIFTY_CENT.valuedouble().compareTo(new Double(0.5)));
        Assert.assertEquals(0,DenominationType.TWENTY_CENT.valuedouble().compareTo(new Double(0.2)));
        Assert.assertEquals(0, DenominationType.TEN_CENT.valuedouble().compareTo(new Double(0.1)) );
        Assert.assertEquals(0, DenominationType.FIVE_CENT.valuedouble().compareTo(new Double(0.05)) );
        Assert.assertEquals(11, DenominationType.values().length);
    }

    @Test
    public void testfindByValue() {
        Assert.assertEquals(DenominationType.ONE_HUNDRED_DOLLAR, DenominationType.findByValue(new Double(100)));
        Assert.assertEquals(DenominationType.FIFETY_DOLLAR, DenominationType.findByValue(new Double(50)));
        Assert.assertEquals(DenominationType.TWENTY_DOLLAR, DenominationType.findByValue(new Double(20)));
        Assert.assertEquals(DenominationType.TEN_DOLLAR, DenominationType.findByValue(new Double(10)));
        Assert.assertEquals(DenominationType.FIVE_DOLLAR, DenominationType.findByValue(new Double(5)));
        Assert.assertEquals(DenominationType.TWO_DOLLAR, DenominationType.findByValue(new Double(2)));
        Assert.assertEquals(DenominationType.ONE_DOLLAR, DenominationType.findByValue(new Double(1)));
        Assert.assertEquals(DenominationType.FIFTY_CENT, DenominationType.findByValue(new Double(0.5)));
        Assert.assertEquals(DenominationType.TWENTY_CENT, DenominationType.findByValue(new Double(0.2)));
        Assert.assertEquals(DenominationType.TEN_CENT, DenominationType.findByValue(new Double(0.1)));
        Assert.assertEquals(DenominationType.FIVE_CENT, DenominationType.findByValue(new Double(0.05)));
    }

    @Test
    public void testvaluesBigDecimal() {

        Assert.assertEquals(0,DenominationType.ONE_HUNDRED_DOLLAR.value().compareTo(new BigDecimal(100)));
        Assert.assertEquals(0,DenominationType.FIFETY_DOLLAR.value().compareTo(new BigDecimal(50)));
        Assert.assertEquals(0,DenominationType.FIFTY_CENT.value().compareTo(new BigDecimal(0.5)));
    }
}
