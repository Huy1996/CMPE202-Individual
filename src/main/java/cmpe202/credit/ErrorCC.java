package cmpe202.credit;

import java.time.YearMonth;

public class ErrorCC extends CreditCard{
    public ErrorCC(String cardNumber, YearMonth expirationDate, String name) {
        super(cardNumber, expirationDate, name);
    }

    @Override
    public String getCardType() { return "invalid: " + checkError(); }

    private String checkError() {
        if (this.getCardNumber().isEmpty()) {
            return "empty/null card number";
        }
        if (this.getCardNumber().matches(".*\\D.*")) {
            return "non numeric characters";
        }
        if (this.getCardNumber().length() >= 19) {
            return "more than 19 digits";
        }
        return "not a possible card number";
    }
}
