package cmpe202.credit;

import java.time.LocalDate;
import java.time.YearMonth;

public class DiscoverCC extends CreditCard{
    public DiscoverCC(String cardNumber, YearMonth expirationDate, String name) {
        super(cardNumber, expirationDate, name);
    }

    @Override
    public String getCardType() {
        return "Discover";
    }
}
