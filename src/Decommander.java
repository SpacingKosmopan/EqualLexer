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

    public ArrayList<ArrayList<CommandElement>> decommand() {
        String source = getSource("src.eql");
        if (source.equals("")) {
            System.out.println("[!] Source file is empty!");
            return null;
        }

        return GenerateCommands(source);
    }

    Map<Character, SchemasFactory.CommandElements> signs = new HashMap<>() {{
        put('+', SchemasFactory.CommandElements.Increment);
    }};

    SchemasFactory.CommandElements identToken(char c) {
        if (c == ' ') return SchemasFactory.CommandElements.White;
        if (c == ';') return SchemasFactory.CommandElements.EOL;
        if (signs.containsKey(c)) return signs.get(c);
        if (Character.isDigit(c) || Character.isLetter(c)) return SchemasFactory.CommandElements.Value;
        return SchemasFactory.CommandElements.White;
    }

    class CommandElement {
        SchemasFactory.CommandElements elementType;
        String contents;

        public CommandElement(SchemasFactory.CommandElements type, String content) {
            this.elementType = type;
            this.contents = content;
        }

        public void setElement(SchemasFactory.CommandElements type) {
            this.elementType = type;
        }

        public void setElement(String content) {
            this.contents = content;
        }
    }

    ArrayList<ArrayList<CommandElement>> GenerateCommands(String source) {
        char[] chars = source.toCharArray();

        ArrayList<ArrayList<CommandElement>> commands = new ArrayList<>();
        commands.add(new ArrayList<CommandElement>());
        int commandCount = 0;
        SchemasFactory.CommandElements lastReadToken = null;
        String readContains = "";
        int pos = 0;
        String message = "";

        while (pos < chars.length) {
            char c = chars[pos];
            System.out.println("Reading char '" + c + "'");
            if (Character.isWhitespace(c)) {
                readContains = "";
                lastReadToken = null;
            } else if (c == ';' && lastReadToken != null) {
                System.out.println("End of Line");
                commands.get(commandCount).add(new CommandElement(lastReadToken, readContains));
                commands.get(commandCount).add(new CommandElement(SchemasFactory.CommandElements.EOL, readContains));
                readContains = "";
                commandCount++;
                commands.add(new ArrayList<CommandElement>());
                lastReadToken = null;
                message += "\t" + SchemasFactory.CommandElements.EOL + "\n";
            } else {
                SchemasFactory.CommandElements nowReadingToken = identToken(c);

                if (lastReadToken != null && nowReadingToken != lastReadToken) {
                    System.out.println("Reading token has changed");
                    commands.get(commandCount)
                            .add(new CommandElement(lastReadToken, readContains));
                    readContains = "";
                    lastReadToken = nowReadingToken;
                }

                readContains += c;

                message += "\t" + nowReadingToken;
                if (lastReadToken == null)
                    lastReadToken = nowReadingToken;
            }

            pos++;
        }
        System.out.println(message);
        return commands;
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