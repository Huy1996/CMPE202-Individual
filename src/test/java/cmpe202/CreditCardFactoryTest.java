package cmpe202;

import cmpe202.credit.*;
import org.junit.Test;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CreditCardFactoryTest {

    @Test
    public void testCreateCreditVisa() {
        CreditCardFactory factory = new CreditCardFactory();
        CreditCard creditCard = factory.createCredit("4111111111111111", YearMonth.of(2023, 12), "John Doe");
        Map<String, String> output = creditCard.toFile();
        Map<String, String> expect = Map.of(
                "cardNumber", "4111111111111111",
                "cardType", "Visa");


        assertTrue(creditCard instanceof VisaCC);
        assertEquals("Visa", creditCard.getCardType());
        assertEquals("4111111111111111", creditCard.getCardNumber());
        assertEquals(YearMonth.of(2023, 12), creditCard.getExpirationDate());
        assertEquals("John Doe", creditCard.getCardHolderName());
        assertEquals(expect, output);
    }

    @Test
    public void testCreateCreditMaster() {
        CreditCardFactory factory = new CreditCardFactory();
        CreditCard creditCard = factory.createCredit("5111111111111111", YearMonth.of(2023, 12), "Jane Doe");
        Map<String, String> output = creditCard.toFile();
        Map<String, String> expect = Map.of(
                "cardNumber", "5111111111111111",
                "cardType", "Master");

        assertTrue(creditCard instanceof MasterCC);
        assertEquals("Master", creditCard.getCardType());
        assertEquals("5111111111111111", creditCard.getCardNumber());
        assertEquals(YearMonth.of(2023, 12), creditCard.getExpirationDate());
        assertEquals("Jane Doe", creditCard.getCardHolderName());
        assertEquals(expect, output);
    }

    @Test
    public void testCreateCreditDiscover() {
        CreditCardFactory factory = new CreditCardFactory();
        CreditCard creditCard = factory.createCredit("6011123456789123", YearMonth.of(2023, 12), "Discover Holder");
        Map<String, String> output = creditCard.toFile();
        Map<String, String> expect = Map.of(
                "cardNumber", "6011123456789123",
                "cardType", "Discover");

        assertTrue(creditCard instanceof DiscoverCC);
        assertEquals("Discover", creditCard.getCardType());
        assertEquals("6011123456789123", creditCard.getCardNumber());
        assertEquals(YearMonth.of(2023, 12), creditCard.getExpirationDate());
        assertEquals("Discover Holder", creditCard.getCardHolderName());
        assertEquals(expect, output);
    }

    @Test
    public void testCreateCreditAmEx() {
        CreditCardFactory factory = new CreditCardFactory();
        CreditCard creditCard = factory.createCredit("341111111111111", YearMonth.of(2023, 12), "AmEx Holder");
        Map<String, String> output = creditCard.toFile();
        Map<String, String> expect = Map.of(
                "cardNumber", "341111111111111",
                "cardType", "American Express");

        assertTrue(creditCard instanceof AmExCC);
        assertEquals("American Express", creditCard.getCardType());
        assertEquals("341111111111111", creditCard.getCardNumber());
        assertEquals(YearMonth.of(2023, 12), creditCard.getExpirationDate());
        assertEquals("AmEx Holder", creditCard.getCardHolderName());
        assertEquals(expect, output);
    }

    @Test
    public void testWrongCardNumber() {
        CreditCardFactory factory = new CreditCardFactory();
        CreditCard creditCard = factory.createCredit("9111111111111111", YearMonth.of(2023, 12), "Unknown Holder");
        Map<String, String> output = creditCard.toFile();
        Map<String, String> expect = Map.of(
                "cardNumber", "9111111111111111",
                "cardType", "invalid: not a possible card number");

        assertTrue(creditCard instanceof ErrorCC);
        assertEquals("invalid: not a possible card number", creditCard.getCardType());
        assertEquals("9111111111111111", creditCard.getCardNumber());
        assertEquals(YearMonth.of(2023, 12), creditCard.getExpirationDate());
        assertEquals("Unknown Holder", creditCard.getCardHolderName());
        assertEquals(expect, output);
    }

    @Test
    public void testNonNumericCardNumber() {
        CreditCardFactory factory = new CreditCardFactory();
        CreditCard creditCard = factory.createCredit("91s1t11a11111111", YearMonth.of(2023, 12), "Unknown Holder");
        Map<String, String> output = creditCard.toFile();
        Map<String, String> expect = Map.of(
                "cardNumber", "91s1t11a11111111",
                "cardType", "invalid: non numeric characters");

        assertTrue(creditCard instanceof ErrorCC);
        assertEquals("invalid: non numeric characters", creditCard.getCardType());
        assertEquals("91s1t11a11111111", creditCard.getCardNumber());
        assertEquals(YearMonth.of(2023, 12), creditCard.getExpirationDate());
        assertEquals("Unknown Holder", creditCard.getCardHolderName());
        assertEquals(expect, output);
    }

    @Test
    public void testLongCardNumber() {
        CreditCardFactory factory = new CreditCardFactory();
        CreditCard creditCard = factory.createCredit("42424242424242424242", YearMonth.of(2023, 12), "Unknown Holder");
        Map<String, String> output = creditCard.toFile();
        Map<String, String> expect = Map.of(
                "cardNumber", "42424242424242424242",
                "cardType", "invalid: more than 19 digits");

        assertTrue(creditCard instanceof ErrorCC);
        assertEquals("invalid: more than 19 digits", creditCard.getCardType());
        assertEquals("42424242424242424242", creditCard.getCardNumber());
        assertEquals(YearMonth.of(2023, 12), creditCard.getExpirationDate());
        assertEquals("Unknown Holder", creditCard.getCardHolderName());
        assertEquals(expect, output);
    }

    @Test
    public void testEmptyCardNumber() {
        CreditCardFactory factory = new CreditCardFactory();
        CreditCard creditCard = factory.createCredit("", YearMonth.of(2023, 12), "Unknown Holder");
        Map<String, String> output = creditCard.toFile();
        Map<String, String> expect = Map.of(
                "cardNumber", "",
                "cardType", "invalid: empty/null card number");

        assertTrue(creditCard instanceof ErrorCC);
        assertEquals("invalid: empty/null card number", creditCard.getCardType());
        assertEquals("", creditCard.getCardNumber());
        assertEquals(YearMonth.of(2023, 12), creditCard.getExpirationDate());
        assertEquals("Unknown Holder", creditCard.getCardHolderName());
        assertEquals(expect, output);
    }

    @Test
    public void testNullCardNumber() {
        CreditCardFactory factory = new CreditCardFactory();
        CreditCard creditCard = factory.createCredit(null, YearMonth.of(2023, 12), "Unknown Holder");
        Map<String, String> output = creditCard.toFile();
        Map<String, String> expect = new HashMap<>();
        expect.put("cardNumber", null);
        expect.put("cardType", "invalid: empty/null card number");

        assertTrue(creditCard instanceof ErrorCC);
        assertEquals("invalid: empty/null card number", creditCard.getCardType());
        assertNull(creditCard.getCardNumber());
        assertEquals(YearMonth.of(2023, 12), creditCard.getExpirationDate());
        assertEquals("Unknown Holder", creditCard.getCardHolderName());
        assertEquals(expect, output);
    }
}
