package cmpe202;

import cmpe202.credit.CardType;
import cmpe202.credit.CardTypeDetector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTypeDetectorTest {

    @Test
    public void testDetectCardTypeVisa() {
        assertEquals(CardType.Visa, CardTypeDetector.detectCardType("4111111111111111"));
        assertEquals(CardType.Visa, CardTypeDetector.detectCardType("4111111111111"));
        assertEquals(CardType.Visa, CardTypeDetector.detectCardType("4012888888881881"));
    }

    @Test
    public void testDetectCardTypeMaster() {
        assertEquals(CardType.Master, CardTypeDetector.detectCardType("5111111111111111"));
        assertEquals(CardType.Master, CardTypeDetector.detectCardType("5211111111111111"));
        assertEquals(CardType.Master, CardTypeDetector.detectCardType("5311111111111111"));
        assertEquals(CardType.Master, CardTypeDetector.detectCardType("5411111111111111"));
        assertEquals(CardType.Master, CardTypeDetector.detectCardType("5511111111111111"));
    }

    @Test
    public void testDetectCardTypeDiscover() {
        assertEquals(CardType.Discover, CardTypeDetector.detectCardType("6011123456789123"));
        assertEquals(CardType.Discover, CardTypeDetector.detectCardType("6011000990139424"));
    }

    @Test
    public void testDetectCardTypeAmEx() {
        assertEquals(CardType.AmEx, CardTypeDetector.detectCardType("341111111111111"));
        assertEquals(CardType.AmEx, CardTypeDetector.detectCardType("371111111111111"));
    }

    @Test
    public void testDetectCardTypeUnknown() {
        assertEquals(CardType.Unknown, CardTypeDetector.detectCardType("9111111111111111"));
        assertEquals(CardType.Unknown, CardTypeDetector.detectCardType("1234567890123456"));
    }

    @Test
    public void testDetectCardTypeNull() {
        assertEquals(CardType.Unknown, CardTypeDetector.detectCardType(null));
    }
}
