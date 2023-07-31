import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 *
 * @author Zheyuan Gao
 * @author Cedric Fausey
 *
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     *
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires [c is a condition string]
     * @ensures parseCondition = [Condition corresponding to c]
     */
    private static Condition parseCondition(String c) {
        assert c != null : "Violation of: c is not null";
        assert Tokenizer
                .isCondition(c) : "Violation of: c is a condition string";
        return Condition.valueOf(c.replace('-', '_').toUpperCase());
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"IF"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF") : ""
                + "Violation of: <\"IF\"> is proper prefix of tokens";

        //Process if condition part.
        /*
         * First token should be "IF". (Syntax error already been checked in
         * assert part)
         */
        tokens.dequeue();
        //Next token should be condition. (Syntax error check needed).
        String condition = tokens.dequeue();
        //Check if the condition is a valid condition.
        Reporter.assertElseFatalError(Tokenizer.isCondition(condition),
                "Violation of: " + condition + " is valid condition");
        //Parse the string into condition.
        Condition c = parseCondition(condition);
        //Next token should be "THEN". (Syntax error check needed).
        String then = tokens.dequeue();
        //Check if the then is actually "THEN".
        Reporter.assertElseFatalError(then.equals("THEN"),
                "Violation of: THEN should be present after the if condition.");

        //Process if block part.
        Statement nsIf = s.newInstance();
        nsIf.parseBlock(tokens);

        //If there is no else block. we check if there is END IF at the end.
        if (!tokens.front().equals("ELSE")) {
            //Check if next tokens is "END".
            Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                    "Violation of: END should be present at the end of if block.");
            //Check if next tokens is "IF".
            Reporter.assertElseFatalError(tokens.dequeue().equals("IF"),
                    "Violation of: IF should be present at the end of if block.");
            //Assemble the if condition and block to the statement.
            s.assembleIf(c, nsIf);
        } else {
            Reporter.assertElseFatalError(tokens.dequeue().equals("ELSE"),
                    "Violation of: ELSE should be present at the start of else block.");
            //If there is a ELSE block, start to process it.
            Statement nsElse = s.newInstance();
            nsElse.parseBlock(tokens);
            //Check if next tokens is "END".
            Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                    "Violation of: END should be present at the end of if block.");
            //Check if next tokens is "IF".
            Reporter.assertElseFatalError(tokens.dequeue().equals("IF"),
                    "Violation of: IF should be present at the end of if block.");
            //Assemble the if condition and if && else block to the statement.
            s.assembleIfElse(c, nsIf, nsElse);
        }

    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"WHILE"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE") : ""
                + "Violation of: <\"WHILE\"> is proper prefix of tokens";

        //Process while condition part.

        /*
         * First tokens should be "WHILE". Already been checked in assert above.
         */
        tokens.dequeue();
        //Next token should be condition. (Syntax error check needed).
        String condition = tokens.dequeue();
        //Check if the condition is a valid condition.
        Reporter.assertElseFatalError(Tokenizer.isCondition(condition),
                "Violation of: " + condition + " is valid condition");
        //Parse the string into condition.
        Condition c = parseCondition(condition);
        //Next tokens should be "DO", check if that is the case.
        Reporter.assertElseFatalError(tokens.dequeue().equals("DO"),
                "Violation of: DO should be present after the while condition.");

        //Process while block part.
        Statement ns = s.newInstance();
        ns.parseBlock(tokens);

        //Check the "END" and "WHILE" at the end of the while statement.
        //Check if next tokens is "END".
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Violation of: END should be present at the end of while block.");
        //Check if next tokens is "IF".
        Reporter.assertElseFatalError(tokens.dequeue().equals("WHILE"),
                "Violation of: WHILE should be present at the end of while block.");

        //Assemble the while condition and block to the statement.
        s.assembleWhile(c, ns);

    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [identifier string is a proper prefix of tokens]
     * @ensures <pre>
     * s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0
                && Tokenizer.isIdentifier(tokens.front()) : ""
                        + "Violation of: identifier string is proper prefix of tokens";

        String inst = tokens.dequeue();
        //Assemble the call to the statement.
        s.assembleCall(inst);

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        //There are three situations: IF or IF_ELSE, WHILE, and call.
        if (tokens.front().equals("IF")) {
            parseIf(tokens, this);
        } else if (tokens.front().equals("WHILE")) {
            parseWhile(tokens, this);
        } else {
            //It is a call
            parseCall(tokens, this);
        }

    }

    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        /*
         * The end of a block can either be "ELSE", "END", or
         * Tokenizer.END_OF_INPUT. Use a while loop until we reach one of this.
         */
        Statement ns = this.newInstance();
        int position = 0;
        while (!(tokens.front().equals("ELSE") || tokens.front().equals("END")
                || tokens.front().equals(Tokenizer.END_OF_INPUT))) {
            ns.parse(tokens);
            this.addToBlock(position, ns);
            position++;
        }

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
        out.print("Enter valid BL statement(s) file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        s.parse(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}
