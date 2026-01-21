import java.util.ArrayList;

public class Sregex {
    String combination;
    ArrayList<Element> decryptedTokens;

    public static Sregex raw(String combination) {
        return new Sregex(combination);
    }

    public Sregex(String combination) {
        this.combination = combination;
        try {
            decryptedTokens = decrypt(combination);
            decryptedTokens.forEach(element -> {
                System.out.println("\t[+]" + element.token.toString() + "*" + element.amount);
            });
        } catch (Exception e) {
            decryptedTokens = new ArrayList<>();
            System.out.println("[!] Failed to load Sregex: " + e.getMessage());
            e.printStackTrace();
        }
    }

    ArrayList<Element> decrypt(String combination) throws Exception {
        char[] charArray = combination.toCharArray();
        ArrayList<Element> tokens = new ArrayList<>();

        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            System.out.println("Checking " + c);
            if (c == '$') {
                // predefinied token expected
                i++;
                c = charArray[i];
                switch (c) {
                    case 'd':
                        tokens.add(new Element(Tokens.digit));

                        break;
                    default: {
                        throw new Exception("[c#01] Unidentified predefinied token exception - '" + c + "'");
                    }
                }

            } else if (c == '*') {

                if (tokens.size() != 0) {

                    if (tokens.get(tokens.size() - 1).token == Tokens.digit && tokens.get(tokens.size() - 1).token != Tokens.amount) {
                        //System.out.println("[i] Checking amount");
                        String amount = "";

                        if (charArray.length <= i + 1)
                            throw new Exception("[*#01] Invalid token length");
                        if (!Character.isDigit(charArray[i + 1]))
                            throw new Exception("[*#02] Unidentified predefinied token exception - '" + amount + "'");

                        i++;
                        c = charArray[i];
                        while (Character.isDigit(c) && i < charArray.length) {

                            //System.out.println(c+"->"+Character.isDigit(c)+" digit");
                            amount += c;
                            i++;
                            c = charArray[i];
                        }
                        i--;
                        System.out.println("Found amount: " + amount);

                        for (char singleC : amount.toCharArray()) {
                            if (!Character.isDigit(singleC))
                                throw new Exception("[*#03] At least one amount token is invalid");
                        }

                        tokens.get(tokens.size() - 1).setAmount(Integer.parseInt(amount));
                    } else System.out.println("[E] #002");
                } else System.out.println("[E] #001");

            }
        }
        return tokens;
    }

    public boolean test(String target) {
        char[] charArray = target.toCharArray();
        return false;
    }

    enum Tokens {
        digit, // $d
        amount // *n
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