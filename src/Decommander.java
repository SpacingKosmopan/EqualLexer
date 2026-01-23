import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Decommander {
    String getSource(String path) {
        Path srcPath = Path.of(path);
        if (!Files.exists(srcPath, LinkOption.NOFOLLOW_LINKS)) {
            System.out.println("[!] Source file does not exist!");
            return "";
        }
        try {
            String src = Files.readString(srcPath);
            return src;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public void decommand() {
        String source = getSource("src.eql");
        if (source.equals("")) {
            System.out.println("[!] Source file is empty!");
            return;
        }

        GenerateCommands(source);
    }

    enum Reads {
        Value, // digit|word
        camelName,
        Increment,
        Decrement,
        Multiplication,
        Division,
        EOL, // ;
        White // space or sth
    }


    Map<Character, Reads> signs = new HashMap<>() {{
        put('+', Reads.Increment);
    }};

    Reads identToken(char c) {
        if (c == ' ') return Reads.White;
        if (c == ';') return Reads.EOL;
        if(signs.containsKey(c))return signs.get(c);
        if (Character.isDigit(c) || Character.isLetter(c)) return Reads.Value;
        return Reads.White;
    }

    class CommandElement {
        Reads elementType;
        String contents;

        public CommandElement(Reads type, String content) {
            this.elementType = type;
            this.contents = content;
        }

        public void setElement(Reads type) {
            this.elementType = type;
        }

        public void setElement(String content) {
            this.contents = content;
        }
    }




    void GenerateCommands(String source) {
        char[] chars = source.toCharArray();

        ArrayList<ArrayList<CommandElement>> commands = new ArrayList<>();
        commands.add(new ArrayList<CommandElement>());
        int commandCount = 0;
        Reads lastReadToken = null;
        String readContains = "";
        int pos = 0;
        String message = "";

        while (pos < chars.length) {
            char c = chars[pos];
            System.out.println("Reading char '" + c + "'");
            if (c == ' ' && lastReadToken != null) {
                commands.get(commandCount).add(new CommandElement(lastReadToken, readContains));
                readContains = "";
                lastReadToken = null;
            } else if (c == ';' && lastReadToken != null) {
                System.out.println("End of Line");
                commands.get(commandCount).add(new CommandElement(lastReadToken, readContains));
                readContains = "";
                commandCount++;
                commands.add(new ArrayList<CommandElement>());
                lastReadToken = null;
                message += "\t" + Reads.EOL;
            } else {
                readContains += c + "";
                Reads nowReadingToken = identToken(c);
                message += "\t" + nowReadingToken;
                if (lastReadToken == null)
                    lastReadToken = nowReadingToken;
                else if (nowReadingToken != lastReadToken) {
                    System.out.println("Reading token has changed");
                    commands.get(commandCount).add(new CommandElement(lastReadToken, readContains));
                    readContains = "";
                    lastReadToken = nowReadingToken;
                }
            }

            pos++;
        }
        System.out.println(message);
    }

    /*String[] words = source.trim().split("\\s+");

    for (String word : words) {
        System.out.println(word);
    }*/

    // lepiej czytać znak po znaku i wtedy
    // now->camelName
    // albo now->Snakename
    // while (Character.isLetter(c)) now is camelName
    // if (c == (char)Types.Equal) now is assignement czy coś tam
}