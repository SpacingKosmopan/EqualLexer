// localization
// C:\Users\Komputer\IdeaProjects\interpretator
void main() throws IOException {
    SchemasFactory schema = new SchemasFactory();
    SchemasFactory.Schema localSchema = SchemasFactory.Schema.builder().eol().build();
    System.out.println(schema.variable);
    System.out.println(localSchema);
    System.out.println(SchemasFactory.variableDeclarationAssignment);

    ///

    Sregex sregex = Sregex.raw("$L$l$d$l*3");
    System.out.println("Sregex test: __" + sregex.test("Hl5pp") + "__");

    Path srcPath = Path.of("src.eql");
    if (!Files.exists(srcPath, LinkOption.NOFOLLOW_LINKS)) {
        System.out.println("[!] Source file does not exist!");
        return;
    }
    String source = Files.readString(Path.of("src.eql"));
    if (source.isEmpty()) {
        System.out.println("[!] Source file is empty");
        //  return;
    }
    char[] chars = source.toCharArray();

    int pos = 0;

    ArrayList<ArrayList<CommandElement>> commands = new ArrayList<>();
    commands.add(new ArrayList<CommandElement>());
    int commandCount = 0;
    Reads lastReadToken = null;
    String readContains = "";

    while (pos < chars.length) {
        char c = chars[pos];
        System.out.println("Reading char '" + c + "'");
        if (c == ' ' && lastReadToken != null) {
            commands.get(commandCount).add(new CommandElement(lastReadToken, readContains));
            readContains = "";
            lastReadToken = null;
        }
        else if (c == ';' && lastReadToken != null){
            System.out.println("End of Line");
            commands.get(commandCount).add(new CommandElement(lastReadToken, readContains));
            readContains = "";
            commandCount++;
            commands.add(new ArrayList<CommandElement>());
            lastReadToken = null;
        }
        else {
            readContains += c + "";
            Reads nowReadingToken = identToken(c);
            if (lastReadToken == null)
                lastReadToken = nowReadingToken;
            else if (nowReadingToken != lastReadToken) 
            {
                System.out.println("Reading token has changed");
                commands.get(commandCount).add(new CommandElement(lastReadToken, readContains));
                readContains = "";
                lastReadToken = nowReadingToken;
            }
        }
        
        pos++;
    }

enum Reads {
    Value, // digit|word
    ArithmeticSign, // +-/*
    EOL, // ;
    White, // space or sth
}

Reads identToken(char c){
    return c == ' ' ? Reads.White : Character.isDigit(c) || Character.isLetter(c) ? Reads.Value : c == ';' ? Reads.EOL : Reads.White;
}

class CommandElement {
    Reads elementType;
    String contents;

    public CommandElement(Reads type, String content){
        this.elementType=type;
        this.contents=content;
    }

    public void setElement(Reads type){
        this.elementType=type;
    }

    public void setElement(String content){
        this.contents=content;
    }
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
