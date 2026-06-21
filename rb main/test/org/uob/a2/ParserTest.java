package org.uob.a2.parser;

import org.junit.jupiter.api.Test;
import org.uob.a2.*; // assuming Command + concrete commands live here

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void tickLongFormTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("tick 10");

        int mark = 0;
        mark += (cmd instanceof TickCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.tickLongFormTest: " + mark + "/1");
    }

    @Test
    void tickShortFormTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("t 5");

        int mark = 0;
        mark += (cmd instanceof TickCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.tickShortFormTest: " + mark + "/1");
    }

    @Test
    void emptyStringDefaultsToTickTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("");

        int mark = 0;
        mark += (cmd instanceof TickCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.emptyStringDefaultsToTickTest: " + mark + "/1");
    }

    @Test
    void buildLongFormTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("build mine");

        int mark = 0;
        mark += (cmd instanceof BuildCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.buildLongFormTest: " + mark + "/1");
    }

    @Test
    void buildShortFormTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("b mine");

        int mark = 0;
        mark += (cmd instanceof BuildCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.buildShortFormTest: " + mark + "/1");
    }

    @Test
    void helpTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("help");

        int mark = 0;
        mark += (cmd instanceof HelpCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.helpTest: " + mark + "/1");
    }

    @Test
    void infoLongFormTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("info");

        int mark = 0;
        mark += (cmd instanceof InfoCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.infoLongFormTest: " + mark + "/1");
    }

    @Test
    void infoShortFormTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("i");

        int mark = 0;
        mark += (cmd instanceof InfoCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.infoShortFormTest: " + mark + "/1");
    }

    @Test
    void quitTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("quit");

        int mark = 0;
        mark += (cmd instanceof QuitCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.quitTest: " + mark + "/1");
    }

    @Test
    void cheatTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("cheat lots_of_money");

        int mark = 0;
        mark += (cmd instanceof CheatCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.cheatTest: " + mark + "/1");
    }

    @Test
    void graphTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("graph");

        int mark = 0;
        mark += (cmd instanceof GraphCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.graphTest: " + mark + "/1");
    }

    @Test
    void saveTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("save myfile");

        int mark = 0;
        mark += (cmd instanceof SaveCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.saveTest: " + mark + "/1");
    }

    @Test
    void loadTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("load myfile");

        int mark = 0;
        mark += (cmd instanceof LoadCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.loadTest: " + mark + "/1");
    }

    @Test
    void invalidCommandTest() {
        Parser parser = new Parser();

        Command cmd = parser.parse("glitterbomb 42");

        int mark = 0;
        mark += (cmd instanceof InvalidCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.invalidCommandTest: " + mark + "/1");
    }

    @Test
    void sanitiseTest() {
        Parser parser = new Parser();

        Command cmd1 = parser.parse("HeLp");
        Command cmd2 = parser.parse("BuIlD mine");
        Command cmd3 = parser.parse("TiCk 3");

        int mark = 0;
        mark += (cmd1 instanceof HelpCommand) ? 1 : 0;
        mark += (cmd2 instanceof BuildCommand) ? 1 : 0;
        mark += (cmd3 instanceof TickCommand) ? 1 : 0;
        System.out.println("AUTOMARK::Parser.sanitiseTest: " + mark + "/3");
    }

    @Test
    void leadingSpacesTest() {
        Parser parser = new Parser();
    
        Command cmd = parser.parse("   tick 5");
    
        int mark = 0;
        mark += (cmd instanceof TickCommand) ? 1 : 0;
    
        System.out.println("AUTOMARK::Parser.leadingSpacesTest: " + mark + "/1");
    }
}
