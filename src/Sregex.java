import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class Sregex {
    String combination;
    ArrayList<Element> decryptedTokens;

    public static Sregex raw(String combination) {
        return new Sregex(combination);
    }

    private Sregex(String combination) {
        this.combination = combination;
        try {
            decryptedTokens = decrypt(combination);
            System.out.println("decryptedTokens: ");
            decryptedTokens.forEach(element -> {
                System.out.println("\t[+]" + element.token.toString() + "*" + element.amount);
            });
        } catch (Exception e) {
            decryptedTokens = new ArrayList<>();
            System.out.println("[!] Failed to load Sregex: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static final EnumSet<Tokens> TOKENS_WITH_AMOUNT =
            EnumSet.of(
                    Tokens.digit,
                    Tokens.letter,
                    Tokens.bigLetter
            );

    ArrayList<Element> decrypt(String combination) throws Exception {
        char[] charArray = combination.toCharArray();
        ArrayList<Element> tokens = new ArrayList<>();

        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == '$') {
                // predefinied token expected
                i++;
                c = charArray[i];
                switch (c) {
                    case 'd':
                        tokens.add(new Element(Tokens.digit));
                        break;
                    case 'l':
                        tokens.add(new Element(Tokens.letter));
                        break;
                    case 'L':
                        tokens.add(new Element(Tokens.bigLetter));
                        break;
                    default: {
                        throw new Exception("[$#01] Unidentified predefinied token exception - '" + c + "'");
                    }
                }

            } else if (c == '*') {

                if (!tokens.isEmpty()) {

                    if (TOKENS_WITH_AMOUNT.contains(tokens.getLast().token) && tokens.getLast().token != Tokens.amount) {
                        //System.out.println("[i] Checking amount");
                        String amount = "";

                        if (charArray.length <= i + 1)
                            throw new Exception("[*#01] Invalid token length");
                        if (!Character.isDigit(charArray[i + 1]))
                            throw new Exception("[*#02] Unidentified predefinied token exception - '" + amount + "'");

                        i++;
                        c = charArray[i];
                        while (i < charArray.length && Character.isDigit(charArray[i])) {
                            amount += charArray[i];
                            i++;
                        }
                        i--; // cofnięcie, bo for-loop też zrobi i++

                        System.out.println("Found amount: " + amount);

                        for (char singleC : amount.toCharArray()) {
                            if (!Character.isDigit(singleC))
                                throw new Exception("[*#03] At least one amount token is invalid");
                        }

                        tokens.getLast().setAmount(Integer.parseInt(amount));
                    } else System.out.println("[E] #002 amount follows wrong token");
                } else System.out.println("[E] #001 tokens list is empty");

            }
        }
        return tokens;
    }

    public boolean test(String target) {
        if (decryptedTokens.isEmpty()) return false;
        char[] charArray = target.toCharArray();

        System.out.println("Read chars to test: ");
        for (int i = 0; i < charArray.length; i++) {
            System.out.println("\t[?]" + charArray[i]);
        }

        int characterNumber = 0;
        for (var token : decryptedTokens) { // e.x. two tokens: 2 digit number, 1 letter
            for (int i = 0; i < token.amount; i++) {

                if (charArray.length <= characterNumber) return false;
                boolean tested = testChar(charArray[characterNumber], token.token);
                System.out.println(
                        token.token.toString()
                                + "-"
                                + charArray[characterNumber]
                                + " " + tested
                );
                characterNumber++;
                if (!tested)
                    return false;
            }

        }
        //System.out.println("[i] " + characterNumber + "-" + charArray.length);
        return characterNumber == charArray.length;
    }

    public boolean testChar(char c, Tokens token) {
        return switch (token) {
            case digit -> Character.isDigit(c);
            case letter -> Character.isLetter(c);
            case bigLetter -> Character.isUpperCase(c) && Character.isLetter(c);
            default -> false;
        };
    }

    enum Tokens {
        digit, // $d
        amount, // *n
        letter, // $l
        bigLetter, // $L
    }

    class Element {
        Tokens token;
        int amount;

        public Element(Tokens token, int amount) {
            this.token = token;
            this.amount = amount;
        }

        public Element(Tokens token) {
            this.token = token;
            this.amount = 1;
        }

        public void setToken(Tokens token) {
            this.token = token;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
