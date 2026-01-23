import java.util.ArrayList;
import java.util.List;

/// file -> words set <br>
/// words set -> commands set (EOL - End of Line, ;) <br>
/// commands set -> schema, tokens set <br>
/// <pre><code>new Schema variableDeclaration
/// (Tokens.Type, Tokens.Declaration, Tokens.camelName, Tokens.Assign, Tokens.Value, Tokens.EOL);</code></pre>
public class SchemasFactory {


    public enum CommandElements {
        Type, // int
        Declaration, // ==
        camelName, // Sregex "$L$l+"
        Assign, // =
        Value, // $d+$l+$L+
        EOL, // ;
        Increment,
        Decreament,
        Division,
        Multiplication,
        White,
    }

    public static Schema identSchema(ArrayList<Decommander.CommandElement> commands) {
        final Schema variableDeclarationAssignment = new Schema(CommandElements.Type, CommandElements.Declaration, CommandElements.camelName, CommandElements.Assign, CommandElements.Value, CommandElements.EOL);
        final Schema variableDeclaration = new Schema(CommandElements.Type, CommandElements.Declaration, CommandElements.camelName, CommandElements.EOL);
        final Schema variable = Schema.builder().nameCamel().eol().build();
        final Schema tempIncrement = new Schema.Builder().value().arithmeticSign().value().eol().build();

        ArrayList<Schema> schemas = new ArrayList<>() {
            {
                add(variableDeclarationAssignment);
                add(variableDeclaration);
                add(variable);
                add(tempIncrement);
            }
        };

        for (int i = 0; i < schemas.size(); i++) {
            Schema schema = schemas.get(i);
            if (schema.commandsOrder.size() != commands.size()) {
                continue;
            }
            boolean thisIsThis = true;
            for (int j = 0; j < commands.size(); j++) {
                if (commands.get(j).elementType != schema.commandsOrder.get(j)) {
                    thisIsThis = false;
                }
            }
            if (thisIsThis) {
                return schema;
            }
        }
        return null;
    }

    static class Schema {
        final List<CommandElements> commandsOrder;

        public Schema(CommandElements... commandsOrder) {
            this.commandsOrder = List.of(commandsOrder);
        }

        public Schema(List<CommandElements> commandsOrder) {
            this.commandsOrder = commandsOrder;
        }

        @Override
        public String toString() {
            return String.join(", ", commandsOrder.stream()
                    .map(Enum::name)
                    .toArray(String[]::new));
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private final List<CommandElements> elements = new ArrayList<>();

            public Builder type() {
                elements.add(CommandElements.Type);
                return this;
            }

            public Builder declaration() {
                elements.add(CommandElements.Declaration);
                return this;
            }

            public Builder nameCamel() {
                elements.add(CommandElements.camelName);
                return this;
            }

            public Builder assign() {
                elements.add(CommandElements.Assign);
                return this;
            }

            public Builder value() {
                elements.add(CommandElements.Value);
                return this;
            }

            public Builder eol() {
                elements.add(CommandElements.EOL);
                return this;
            }

            public Builder arithmeticSign() {
                elements.add(CommandElements.Increment);
                return this;
            }

            public Schema build() {
                return new Schema(elements);
            }
        }
    }
}