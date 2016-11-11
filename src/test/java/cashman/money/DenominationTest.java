package cashman.money;

import cashman.enums.DenominationType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class DenominationTest {

    Denomination denomination;

    @Before
    public void setup() {
        denomination = new Denomination(DenominationType.FIFETY_DOLLAR, 10);
    }

    @Test
    public void testgetDenominationValue() {
        Assert.assertEquals(0, denomination.getDenominationValue().compareTo(new BigDecimal(500)));
    }

        @Test
    public void testCompareTo() {
        Denomination compareDem = new Denomination(DenominationType.TWENTY_DOLLAR,10);
        Assert.assertEquals(-1, denomination.compareTo(compareDem));

        compareDem.setSortordex(0);
        Assert.assertEquals(-1, denomination.compareTo(compareDem));

        compareDem.setSortordex(999);
        Assert.assertEquals(1, denomination.compareTo(compareDem));

    }
}
