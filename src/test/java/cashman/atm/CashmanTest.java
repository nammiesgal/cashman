package cashman.atm;

import cashman.atm.Cashman;
import cashman.enums.DenominationType;
import cashman.exception.CurrencyFulfilmentException;
import cashman.exception.InsufficentFundsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CashmanTest {

    private Cashman cashman;

    @Before
    public void setup() {
        cashman = new Cashman();
    }

    @Test
    public void testFundDenominationTypeCountCorrectandTopUp() {
        Assert.assertEquals(2, cashman.getFundDenominationTypeCount());
        // Add an existing denomination
        cashman.topUp(DenominationType.TWENTY_DOLLAR,10);
        Assert.assertEquals(2, cashman.getFundDenominationTypeCount());
        // Add a new denomination
        cashman.topUp(DenominationType.TWO_DOLLAR,10);
        Assert.assertEquals(3, cashman.getFundDenominationTypeCount());
    }

    @Test
    public void testClear() {
        cashman.clear();
        Assert.assertEquals(0, cashman.getFundDenominationTypeCount());
    }

    @Test
    public void testDenominationQuantity() throws Exception {

        Assert.assertEquals(10, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));
        Assert.assertEquals(10, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        cashman.topUp(DenominationType.TWENTY_DOLLAR,5);
        Assert.assertEquals(15, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        cashman.withdrawal(new BigDecimal(70));
        Assert.assertEquals(14, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        Assert.assertEquals(9, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));
    }

    @Test
    public void testWithdrawalWithOption() throws Exception {
        Assert.assertEquals(10, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));
        Assert.assertEquals(10, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        cashman.withdrawal(new BigDecimal(100), DenominationType.TWENTY_DOLLAR );
        Assert.assertEquals(5, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
    }

    @Test
    public void testWithdrawalWithOption50() throws Exception {
        cashman.clear();
        cashman.topUp(DenominationType.TWENTY_DOLLAR,2);
        cashman.topUp(DenominationType.FIFETY_DOLLAR,2);
        cashman.withdrawal(new BigDecimal(100), DenominationType.TWENTY_DOLLAR );
        Assert.assertEquals(2, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        Assert.assertEquals(0, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));
    } 

    @Test
    public void testWithdrawal() throws Exception {

        Assert.assertEquals(10, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));
        Assert.assertEquals(10, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        cashman.withdrawal(new BigDecimal(20));
        Assert.assertEquals(9, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        cashman.withdrawal(new BigDecimal(40));
        Assert.assertEquals(7, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        cashman.withdrawal(new BigDecimal(50));
        Assert.assertEquals(9, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));
        cashman.withdrawal(new BigDecimal(70));
        Assert.assertEquals(8, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));
        Assert.assertEquals(6, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        cashman.withdrawal(new BigDecimal(80));
        Assert.assertEquals(2, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        cashman.withdrawal(new BigDecimal(100));
        Assert.assertEquals(6, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));
        cashman.withdrawal(new BigDecimal(150));
        Assert.assertEquals(3, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));

        // top up another 10 notes of $20
        cashman.topUp(DenominationType.TWENTY_DOLLAR,10);
        cashman.withdrawal(new BigDecimal(60));
        Assert.assertEquals(9, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));

        cashman.withdrawal(new BigDecimal(110));
        Assert.assertEquals(6, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        Assert.assertEquals(2, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));

        cashman.withdrawal(new BigDecimal(130));
        Assert.assertEquals(2, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        Assert.assertEquals(1, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));

    }

    @Test
    public void testwithdrawalatSpecificCondition() throws Exception {
        // Clear the cashman
        cashman.clear();

        // top it up with $50 and 20
        cashman.topUp(DenominationType.TWENTY_DOLLAR,8);
        cashman.topUp(DenominationType.FIFETY_DOLLAR,3);
        // Withdraw the 200
        cashman.withdrawal(new BigDecimal(200));

        Assert.assertEquals(3, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        Assert.assertEquals(1, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeWithdrawAmount() throws Exception {
        cashman.withdrawal(new BigDecimal("-100"));
    }

    @Test(expected = CurrencyFulfilmentException.class)
    public void withdraw30dollar() throws Exception {
        Assert.assertEquals(10, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));
        Assert.assertEquals(10, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        cashman.withdrawal(new BigDecimal(30));
    }

    @Test
    public void testAddNewDenomination() throws Exception {

        Assert.assertEquals(10, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));
        Assert.assertEquals(10, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        cashman.topUp(DenominationType.TEN_DOLLAR,1);

        cashman.withdrawal(new BigDecimal(30));
        Assert.assertEquals(9, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));
        Assert.assertEquals(0, cashman.getDenominationQuantity(DenominationType.TEN_DOLLAR));
    }

    @Test(expected = InsufficentFundsException.class)
    public void testInsufficientFund() throws Exception {

        Assert.assertEquals(10, cashman.getDenominationQuantity(DenominationType.FIFETY_DOLLAR));
        Assert.assertEquals(10, cashman.getDenominationQuantity(DenominationType.TWENTY_DOLLAR));

        cashman.withdrawal(new BigDecimal(710));
    }

}
