// localization
// C:\Users\Komputer\IdeaProjects\interpretator
void main() throws IOException {
    Sregex sregex = Sregex.raw("$d*3$d");

    Path srcPath = Path.of("src.eql");
    if (!Files.exists(srcPath, LinkOption.NOFOLLOW_LINKS)) {
        System.out.println("[!] Source file does not exist!");
        return;
    }
    String source = Files.readString(Path.of("src.eql"));
    if (source.equals("")) {
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