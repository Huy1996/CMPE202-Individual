package cmpe202.credit;

//class CardTypeDetector {
//    public static CardType detectCardType(String cardNumber) {
//        if (cardNumber.charAt(0) == '4' && (cardNumber.length() == 13 || cardNumber.length() == 16)) {
//            return CardType.Visa;
//        } else if (cardNumber.charAt(0) == '5' && cardNumber.charAt(1) >= '1' && cardNumber.charAt(1) <= '5' && cardNumber.length() == 16) {
//            return CardType.Master;
//        } else if (cardNumber.startsWith("6011") && cardNumber.length() == 16) {
//            return CardType.Discover;
//        } else if (cardNumber.charAt(0) == '3' && (cardNumber.charAt(1) == '4' || cardNumber.charAt(1) == '7') && cardNumber.length() == 15) {
//            return CardType.AmEx;
//        } else {
//            return CardType.Unknown;
//        }
//    }
//}

class CardTypeDetector {
    public static CardType detectCardType(String cardNumber) {
        if (cardNumber.matches("^4[0-9]{12}(?:[0-9]{3})?$")) {
            return CardType.Visa;
        } else if (cardNumber.matches("^5[1-5][0-9]{14}$")) {
            return CardType.Master;
        } else if (cardNumber.matches("^6011[0-9]{12}$")) {
            return CardType.Discover;
        } else if (cardNumber.matches("^3[47][0-9]{13}$")) {
            return CardType.AmEx;
        } else {
            return CardType.Unknown;
        }
    }
}


enum CardType {
    Visa,
    Master,
    Discover,
    AmEx,
    Unknown
}
