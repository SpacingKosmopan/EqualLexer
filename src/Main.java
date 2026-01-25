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
                SchemasFactory.Schema temp = SchemasFactory.identSchema(commandSet);
                if (temp != null) {
                    System.out.println("[:)] Success! - " + temp.toString());
                    PerformSchema(temp, commandSet);
                } else System.out.println("[!] Error with schema identification!");
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

void PerformSchema(SchemasFactory.Schema schema, ArrayList<Decommander.CommandElement> commandSet) {
    if (schema.equals(SchemasFactory.tempIncrement)) {
        int a = Integer.parseInt(commandSet.get(0).contents);
        String op = commandSet.get(1).contents;
        int b = Integer.parseInt(commandSet.get(2).contents);

        int result = switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            default -> throw new RuntimeException("Unknown operator");
        };

        System.out.println(result);
    }

}

Number parseNumber(String contents) throws NumberFormatException {
    try {
        return Integer.parseInt(contents);
    } catch (NumberFormatException e) {
        return Double.parseDouble(contents);
    }
}
