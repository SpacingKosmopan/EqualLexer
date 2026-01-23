// localization
// C:\Users\Komputer\IdeaProjects\interpretator

void main() throws IOException {
    //SchemasFactory schema = new SchemasFactory();
    /*SchemasFactory.Schema localSchema = SchemasFactory.Schema.builder().eol().build();
    System.out.println(schema.variable);
    System.out.println(localSchema);
    System.out.println(SchemasFactory.variableDeclarationAssignment);*/

    ///

    //Sregex sregex = Sregex.raw("$L$l$d$l*3");
    //System.out.println("Sregex test: __" + sregex.test("Hl5pp") + "__");

    Decommander decommander = new Decommander();
    ArrayList<ArrayList<Decommander.CommandElement>> commandsList = decommander.decommand();
    System.out.println("Found " + commandsList.size() + " non-empty commands: ");
    if (commandsList.isEmpty()) {
        System.out.println("[!] Commands list is empty!");
    } else {
        commandsList.stream().forEach((commandSet) -> {
            if (!commandSet.isEmpty()) {
                System.out.print("[+]");
                commandSet.stream().forEach((command) -> {
                    System.out.print("-> " + command.elementType);
                });
                System.out.println();

                SchemasFactory.Schema temp = SchemasFactory.identSchema(commandSet);
                if (temp != null)
                    System.out.println("[:)] Success!");
                else System.out.println("[!] Error with schema identification!");
            }
        });
    }

    /*Map<String, Integer> map = new HashMap<>();
    map.put("A", 1);
    map.put("B", 2);

    for (Map.Entry<String, Integer> e : map.entrySet()) {
        System.out.println(e.getKey());
        System.out.println(e.getValue());
    }*/
}