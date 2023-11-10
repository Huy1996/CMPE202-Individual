package cmpe202;

import cmpe202.credit.CreditCard;
import cmpe202.credit.CreditCardFactory;
import cmpe202.parser.Parser;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileProcessor {
    private final CreditCardFactory factory = new CreditCardFactory();
    private DateTimeFormatter dtf;
    private Parser engine;

    public void setEngine(Parser engine) {
        this.engine = engine;
    }
    public void setDtf(DateTimeFormatter dtf) { this.dtf = dtf; }

    public void processFile() {
        Map<String, String> entry;
        List<Map<String, String>> records = new ArrayList<>();
        while ((entry = engine.readRecord()) != null) {
            CreditCard creditCard = factory.createCredit(
                    entry.get("cardNumber"),
                    YearMonth.parse(entry.get("expirationDate"), dtf),
                    entry.get("cardHolderName"));
            records.add(creditCard.toFile());
        }
        engine.write(records);
    }
}
