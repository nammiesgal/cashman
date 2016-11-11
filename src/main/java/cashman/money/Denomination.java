package cashman.money;

import cashman.enums.DenominationType;
import cashman.util.Constant;

import java.math.BigDecimal;

public class Denomination implements Comparable<Denomination> {

    private DenominationType value;
    private int quantity;
    private BigDecimal sortordex;

    public Denomination(DenominationType faceValue, int quantity) {
        this.value = faceValue;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal value() {
        return value.value();
    }

    public DenominationType getValue() {
        return value;
    }

    public BigDecimal getDenominationValue() {
        return value.value().multiply(BigDecimal.valueOf(quantity));
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void minusQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public void setSortordex( int sortordex) {
        this.sortordex = new BigDecimal(sortordex);
    }

    public BigDecimal getSortorder() {
        return sortordex;
    }

    public void show() {
        System.out.printf("$%6.2f AUD : %4s\n" , value.value(), quantity);
    }

    public int compareTo(Denomination denomination) {
        BigDecimal currentSortorder = (this.sortordex != null
                                            && BigDecimal.ZERO.compareTo(this.sortordex) != Constant.ZERO )
                                                 ? this.sortordex : this.value();
        BigDecimal prevSortOrder = (denomination.getSortorder() != null
                                        &&  BigDecimal.ZERO.compareTo(denomination.getSortorder()) != Constant.ZERO)
                                                 ?  denomination.getSortorder() : denomination.value();
        return prevSortOrder.compareTo(currentSortorder);
    }
}