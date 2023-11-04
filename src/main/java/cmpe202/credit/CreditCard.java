package cmpe202.credit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public abstract class CreditCard {
    private String cardNumber;
    private YearMonth expirationDate;
    private String cardHolderName;

    public abstract String getCardType();
    public Map<String, String> toFile() {
        Map<String, String> result = new HashMap<>();
        result.put("cardNumber", this.cardNumber);
        result.put("cardType", this.getCardType());
        return result;
    }
}

