// localization
// C:\Users\Komputer\IdeaProjects\interpretator

void main() throws IOException {
    /*SchemasFactory schema = new SchemasFactory();
    SchemasFactory.Schema localSchema = SchemasFactory.Schema.builder().eol().build();
    System.out.println(schema.variable);
    System.out.println(localSchema);
    System.out.println(SchemasFactory.variableDeclarationAssignment);*/

    ///

    //Sregex sregex = Sregex.raw("$L$l$d$l*3");
    //System.out.println("Sregex test: __" + sregex.test("Hl5pp") + "__");

    Decommander decommander = new Decommander();
    decommander.decommand();

    /*Map<String, Integer> map = new HashMap<>();
    map.put("A", 1);
    map.put("B", 2);

    for (Map.Entry<String, Integer> e : map.entrySet()) {
        System.out.println(e.getKey());
        System.out.println(e.getValue());
    }*/

}