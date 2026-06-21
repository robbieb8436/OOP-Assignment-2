package org.uob.a2.parser;

import org.uob.a2.engine.*;
import org.uob.a2.model.*; 
import org.uob.a2.parser.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class HelpCommand extends Command {

    public HelpCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
		return "Available commands: build (b) <building name> | graph (g) <resource> | info (i) <resources (r)> <entities (e)> <prices (p)> < | tick (t) | save (s) <filename> | load (l) <filename> | launch <HyperSpace Drive Name> | quit (q)";
    }
}