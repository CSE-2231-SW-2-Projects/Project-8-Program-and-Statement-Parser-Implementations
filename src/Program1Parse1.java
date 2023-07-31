import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Zheyuan Gao
 * @author Cedric Fausey
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to statement string of body of
     *          instruction at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        //First entry in tokens is string "INSTRUCTION"(Already checked).
        tokens.dequeue();

        /*
         * Next entry in the tokens should be string of the identifier.(Syntax
         * error checking needed).
         */
        String identifier = tokens.dequeue();
        /*
         * Check if the identifier is a valid identifier.
         */
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(identifier),
                "Violation of: <\"User-defined instruction\"> is a proper Identifier");
        // Check if the identifier does not match primitive instructions.
        Reporter.assertElseFatalError(
                !identifier.equals("move") && !identifier.equals("turnleft")
                        && !identifier.equals("turnright")
                        && !identifier.equals("infect")
                        && !identifier.equals("skip"),
                "Violation of: <\"user-defined instruction\"> can not be the name"
                        + " of the primitive instructions.");

        //Next token should be "IS". (Syntax error check needed).
        String is = tokens.dequeue();
        //Check if the is is actually "IS".
        Reporter.assertElseFatalError(is.equals("IS"),
                "Violation of: Identifier" + " should be followed by <\"IS\">");

        //Parse the block into the body.
        body.parseBlock(tokens);

        //Next token should be "END". (Syntax error check needed).
        String end = tokens.dequeue();
        //Check if the end is actually "END".
        Reporter.assertElseFatalError(end.equals("END"),
                "Violation of: <\"END\">" + " present in front of Identifier.");

        //Next token should also be identifier.
        String endIdentifier = tokens.dequeue();
        //Check if the endIdentifier matches the beginning identifier.
        Reporter.assertElseFatalError(identifier.equals(endIdentifier),
                "Violation of: <\"Identifer\"> at the end of new instruction "
                        + "definition must be the same as the identifier at the "
                        + "beginning of the definition.");

        // Return the identifier name.
        return identifier;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        /*
         * First handle with program header.
         */
        //First token should be "PROGRAM". (Syntax error check needed).
        String program = tokens.dequeue();
        //Check if the program is actually "PROGRAM".
        Reporter.assertElseFatalError(program.equals("PROGRAM"),
                "Violation of: <\"PROGRAM\"> is proper prefix of tokens");

        //Next token should be a valid identifier. (Syntax error check needed).
        String identifier = tokens.dequeue();
        //Check if the identifier is a valid identifier.
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(identifier),
                "Violation of: <\"Program name\"> is a proper Identifier");
        //Assign the name to the program.
        this.setName(identifier);

        //Next token should be "IS". (Syntax error check needed).
        String is = tokens.dequeue();
        //Check if the is is actually "IS".
        Reporter.assertElseFatalError(is.equals("IS"),
                "Violation of: Identifier" + " should be followed by <\"IS\">");

        /*
         * Second start to process user-defined instructions.
         */
        Map<String, Statement> context = this.newContext();
        /*
         * If the tokens' front is INSTRUCTION then there is still new
         * instructions to parse.
         */
        while (tokens.front().equals("INSTRUCTION")) {
            Statement userStatement = this.newBody();
            String userInstrName = parseInstruction(tokens, userStatement);
            //Check if the user defined instructions are unique.
            Reporter.assertElseFatalError(!context.hasKey(userInstrName),
                    "Violation of: User defined instructions names should be unique.");
            context.add(userInstrName, userStatement);
        }
        this.swapContext(context);

        /*
         * Then start to process main body of the program.
         */
        //Next token should be "BEGIN". (Syntax error check needed)
        String begin = tokens.dequeue();
        //Check if the begin is actually "BEGIN".
        Reporter.assertElseFatalError(begin.equals("BEGIN"),
                "Violation of: the main program should start with BEGIN.");

        //Parse tokens of body of program.
        Statement body = this.newBody();
        body.parseBlock(tokens);
        this.swapBody(body);

        /*
         * Handle the footer of the program.
         */
        //Next token should be "END". (Syntax error check needed).
        String end = tokens.dequeue();
        //Check if the end is actually "END".
        Reporter.assertElseFatalError(end.equals("END"),
                "Violation of: <\"END\">" + " present in front of Identifier.");

        //Next token should also be identifier.
        String endIdentifier = tokens.dequeue();
        //Check if the endIdentifier matches the beginning identifier.
        Reporter.assertElseFatalError(identifier.equals(endIdentifier),
                "Violation of: <\"Identifer\"> at the end of program must be the"
                        + " same as the identifier at the beginning of the program.");

        //Next token should be Tokenizer.END_OF_INPUT.(Syntax error check needed).
        Reporter.assertElseFatalError(
                tokens.dequeue().equals(Tokenizer.END_OF_INPUT),
                "Violation of: <Tokenizer.END_OF_INPUT> is a suffix of tokens.");

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
