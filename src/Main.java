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
        return;
    }
    char[] chars = source.toCharArray();

    int pos = 0;

    while (pos < chars.length) {
        char c = chars[pos];

        pos++;
    }
}