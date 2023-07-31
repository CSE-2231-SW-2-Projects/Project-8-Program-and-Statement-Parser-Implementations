import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;
import components.utilities.Tokenizer;

/**
 * JUnit test fixture for {@code Statement}'s constructor and kernel methods.
 *
 * @author Zheyuan Gao
 * @author Cedric Fausey
 *
 */
public abstract class StatementTest {

    /**
     * The name of a file containing a sequence of BL statements.
     */
    private static final String FILE_NAME_1 = "test/statement1.bl",
            FILE_NAME_2 = "test/statement2.bl";

    /**
     * Invokes the {@code Statement} constructor for the implementation under
     * test and returns the result.
     *
     * @return the new statement
     * @ensures constructorTest = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorTest();

    /**
     * Invokes the {@code Statement} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new statement
     * @ensures constructorRef = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorRef();

    /**
     * Test of parse on syntactically valid input.
     */
    @Test
    public final void testParseValidExample() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_1);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test of parse on syntactically invalid input.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorExample() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    // More test cases for valid inputs for both parse and parseBlock

    /**
     * Test of parse on valid input.(Only call statements).
     */
    @Test
    public final void testParseValid1() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L("data/statementValid1.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L("data/statementValid1.bl");
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test of parse on valid input.(Only IF statements).
     */
    @Test
    public final void testParseValid2() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L("data/statementValid2.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L("data/statementValid2.bl");
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test of parse on valid input.(Only IF_ELSE statements).
     */
    @Test
    public final void testParseValid3() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L("data/statementValid3.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L("data/statementValid3.bl");
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test of parse on valid input.(Only WHILE statements).
     */
    @Test
    public final void testParseValid4() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L("data/statementValid4.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L("data/statementValid4.bl");
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test of parse on valid input.(Complex and nested IF, IF_ELSE, WHILE
     * statements).
     */
    @Test
    public final void testParseValid5() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L("data/statementValid5.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L("data/statementValid5.bl");
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test of parseBlock on valid input.(Only call statements).
     */
    @Test
    public final void testParseBlockValid1() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L("data/statementValid1.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parseBlock(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L("data/statementValid1.bl");
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parseBlock(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test of parseBlock on valid input.(Only IF statements).
     */
    @Test
    public final void testParseBlockValid2() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L("data/statementValid2.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parseBlock(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L("data/statementValid2.bl");
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parseBlock(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test of parseBlock on valid input.(Only IF_ELSE statements).
     */
    @Test
    public final void testParseBlockValid3() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L("data/statementValid3.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parseBlock(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L("data/statementValid3.bl");
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parseBlock(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test of parseBlock on valid input.(Only WHILE statements).
     */
    @Test
    public final void testParseBlockValid4() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L("data/statementValid4.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parseBlock(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L("data/statementValid4.bl");
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parseBlock(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test of parseBlock on valid input.(Complex and nested IF, IF_ELSE, WHILE
     * statements).
     */
    @Test
    public final void testParseBlockValid5() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L("data/statementValid5.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parseBlock(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L("data/statementValid5.bl");
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parseBlock(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    //More test cases for as many distinct syntax errors as possible for both
    //parse and parseBlock

    /**
     * Test of parse on syntactically invalid input.(Invalid IF condition).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseError1() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid1.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    /**
     * Test of parse on syntactically invalid input.(Missing "THEN" after IF
     * condition).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseError2() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid2.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    /**
     * Test of parse on syntactically invalid input.(Missing "END" at the end of
     * IF statement).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseError3() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid3.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    /**
     * Test of parse on syntactically invalid input.(Missing "END IF" at the end
     * of IF statement).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseError4() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid4.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    /**
     * Test of parse on syntactically invalid input.(Missing "END" at the end of
     * IF_ELSE statement).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseError5() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid5.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    /**
     * Test of parse on syntactically invalid input.(Invalid condition for while
     * statement).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseError6() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid6.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    /**
     * Test of parse on syntactically invalid input.(Missing "DO" after while
     * condition).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseError7() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid7.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    /**
     * Test of parse on syntactically invalid input.(Missing "END" at the end of
     * while statement).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseError8() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid8.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    /**
     * Test of parse on syntactically invalid input.(Missing "END WHILE" at the
     * end of while statement).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseError9() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid9.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    /**
     * Test of parseBlock on syntactically invalid input.(Invalid IF condition).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseBlockError1() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid1.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parseBlock(tokens);
    }

    /**
     * Test of parseBlock on syntactically invalid input.(Missing "THEN" after
     * IF condition).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseBlockError2() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid2.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parseBlock(tokens);
    }

    /**
     * Test of parseBlock on syntactically invalid input.(Missing "END" at the
     * end of IF statement).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseBlockError3() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid3.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parseBlock(tokens);
    }

    /**
     * Test of parseBlock on syntactically invalid input.(Missing "END IF" at
     * the end of IF statement).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseBlockError4() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid4.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parseBlock(tokens);
    }

    /**
     * Test of parseBlock on syntactically invalid input.(Missing "END" at the
     * end of IF_ELSE statement).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseBlockError5() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid5.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parseBlock(tokens);
    }

    /**
     * Test of parseBlock on syntactically invalid input.(Invalid condition for
     * while statement).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseBlockError6() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid6.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parseBlock(tokens);
    }

    /**
     * Test of parseBlock on syntactically invalid input.(Missing "DO" after
     * while condition).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseBlockError7() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid7.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parseBlock(tokens);
    }

    /**
     * Test of parseBlock on syntactically invalid input.(Missing "END" at the
     * end of while statement).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseBlockError8() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid8.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parseBlock(tokens);
    }

    /**
     * Test of parseBlock on syntactically invalid input.(Missing "END WHILE" at
     * the end of while statement).
     */
    @Test(expected = RuntimeException.class)
    public final void testParseBlockError9() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L("data/statementInvalid9.bl");
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parseBlock(tokens);
    }

}
