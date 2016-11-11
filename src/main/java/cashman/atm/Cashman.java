package cashman.atm;


import cashman.exception.CurrencyFulfilmentException;
import cashman.exception.InsufficentFundsException;

import cashman.enums.DenominationType;
import cashman.util.Constant;
import cashman.money.Denomination;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

public class Cashman {

    private HashMap<DenominationType, Denomination> denominationMap = new HashMap<DenominationType, Denomination>();

    public Cashman() {
        denominationMap.put(DenominationType.TWENTY_DOLLAR,
                            new Denomination(DenominationType.TWENTY_DOLLAR, Constant.TEN));
        denominationMap.put(DenominationType.FIFETY_DOLLAR,
                new Denomination(DenominationType.FIFETY_DOLLAR, Constant.TEN));
    }

    public void withdrawal(BigDecimal amount) throws CurrencyFulfilmentException, InsufficentFundsException,
            IllegalArgumentException {
        withdrawal(amount,null);
    }

    public void withdrawal(BigDecimal amount, DenominationType denominationType)
                throws CurrencyFulfilmentException, InsufficentFundsException,
                                IllegalArgumentException {

        List<Denomination> atmFundDenominations = new ArrayList<>(denominationMap.values());
        if (BigDecimal.ZERO.compareTo(amount) == Constant.ONE) {
            throw new IllegalArgumentException("Cannot withdraw a negative amount");
        } else if (getTotalAmount(atmFundDenominations).compareTo(amount) == Constant.MINUS_ONE) {
            throw new InsufficentFundsException("Not enough funnd in the ATM to fulfil the request");
        }
        List<Denomination> withdrawals = new ArrayList<Denomination>();
        boolean fulfilled = false;
        if (denominationType != null) {
            atmFundDenominations.forEach(denomination -> {
                if (denomination.getValue() == denominationType) {
                    denomination.setSortordex(Constant.HIGHEST);
                }
            });
            Collections.sort(atmFundDenominations);
            fulfilled = performFulfillment(amount, atmFundDenominations, withdrawals);
            // reset the sort order again
            atmFundDenominations.forEach(denomination -> { denomination.setSortordex(Constant.ZERO); } );
            // clear the withdrawal list
            withdrawals.clear();
        }
        if (!fulfilled) {
            Collections.sort(atmFundDenominations);
            fulfilled = performFulfillment(amount, atmFundDenominations, withdrawals);
        }
        if (!fulfilled) {
            throw new CurrencyFulfilmentException("Can not fulfil with availaible denomiation");
        }
    }

    private boolean performFulfillment(BigDecimal amount, List<Denomination> atmFundDenominations,
                                                    List<Denomination> withdrawals) {
        boolean fulfilled = false;
        for (int idx = Constant.ZERO; idx < atmFundDenominations.size(); idx++) {
            Denomination currentFundDenomination = atmFundDenominations.get(idx);
            if (currentFundDenomination.value().compareTo(amount) <= Constant.ZERO
                    && currentFundDenomination.getQuantity() > Constant.ZERO) {
                fulfilled = isFulfilled(atmFundDenominations.size(), withdrawals,
                        currentFundDenomination, amount, idx);
                if (fulfilled) {
                    break;
                }
            }
        }
        return fulfilled;
    }

    public void reportAtmBalance() {

        System.out.printf("\n");
        System.out.printf("################# ATM  FUND ####################\n");
        List<Denomination> atmFundDenominations = new ArrayList<>(denominationMap.values());
        Collections.sort(atmFundDenominations);
        atmFundDenominations.forEach(denomination -> {
                    denomination.show();
                }
        );
    }

    private boolean isFulfilled(int atmFundDenominationSize, List<Denomination> withdrawals,
                                Denomination denomination, BigDecimal amount, int denominationidx) {
        boolean isFulfilled = false;
        BigDecimal fulfilledTotal = BigDecimal.ZERO;
        Denomination currentwithdrawal = new Denomination(denomination.getValue(), Constant.ZERO);
        withdrawals.add(currentwithdrawal);
        for (int i = Constant.ZERO; i < denomination.getQuantity(); i++) {
            if (denomination.getQuantity() - Constant.ONE >= Constant.ZERO) {
                fulfilledTotal = getTotalAmount(withdrawals).add(currentwithdrawal.value());
                if (fulfilledTotal.compareTo(amount) == Constant.ONE) {
                    if (denominationidx == atmFundDenominationSize - Constant.ONE) {
                        if (denominationidx > Constant.ZERO) {
                            Denomination previousDenWithdrawal = withdrawals.get(denominationidx - Constant.ONE);
                            previousDenWithdrawal.minusQuantity(Constant.ONE);
                            currentwithdrawal.addQuantity(Constant.ONE);
                        }
                    }
                } else {
                    currentwithdrawal.addQuantity(Constant.ONE);
                    if (fulfilledTotal.compareTo(amount) == Constant.ZERO) {
                        isFulfilled = true;
                        setFundLevel(withdrawals);
                        break;
                    }
                }
            }
        }
        return isFulfilled;
    }

    private void setFundLevel(List<Denomination> withdrawals) {
        withdrawals.forEach(withdrawal -> {
            Denomination atmFundDenomination = denominationMap.get(withdrawal.getValue());
            int newQuantityLevel = atmFundDenomination.getQuantity() - withdrawal.getQuantity();
            atmFundDenomination.setQuantity(newQuantityLevel);
        });
    }

    private BigDecimal getTotalAmount(List<Denomination> denominations) {

        BiFunction<Denomination, BigDecimal, BigDecimal> func = (denomination, amount) -> {
            return amount.add(denomination.getDenominationValue());
        };
        BigDecimal amount = BigDecimal.ZERO;
        for (Denomination denomination : denominations) {
            amount = func.apply(denomination, amount);
        }
        return amount;
    }

    public void topUp(DenominationType denominationtype, int quantity) {

        if (denominationMap.containsKey(denominationtype)) {
            Denomination denomination = denominationMap.get(denominationtype);
            denomination.addQuantity(quantity);
        } else {
            Denomination denomination = new Denomination(denominationtype, quantity);
            denominationMap.put(denominationtype, denomination);
        }
    }

    public int getDenominationQuantity(DenominationType denominationType) {
        return denominationMap.containsKey(denominationType)
                ? denominationMap.get(denominationType).getQuantity() : Constant.ZERO;

    }

    public int getFundDenominationTypeCount() {
        return denominationMap.size();
    }

    public void clear() {
        denominationMap.clear();
    }

}
