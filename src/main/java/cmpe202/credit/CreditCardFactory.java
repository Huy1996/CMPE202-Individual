package cmpe202.credit;

import java.time.YearMonth;

public class CreditCardFactory {
    public CreditCard createCredit(String cardNumber, YearMonth expirationDate, String cardHolderName) {
        CardType type = CardTypeDetector.detectCardType(cardNumber);
        switch (type) {
            case Visa: return new VisaCC(cardNumber, expirationDate, cardHolderName);
            case Master: return new MasterCC(cardNumber, expirationDate, cardHolderName);
            case Discover: return new Discover(cardNumber, expirationDate, cardHolderName);
            case AmEx: return new AmExCC(cardNumber, expirationDate, cardHolderName);
            default: return new ErrorCC(cardNumber, expirationDate, cardHolderName);
        }
    }
}
