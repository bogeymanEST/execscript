package com.github.bogeymanest.execscript;

import com.beust.jcommander.Parameter;
import com.github.bogeymanest.execscript.antlr.execscriptLexer;
import com.github.bogeymanest.execscript.antlr.execscriptParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.*;

public class Execscript {
    @Parameter(names = {"-no-comments"}, description = "Disable")
    private boolean noComments = false;

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("No input file specified");
            return;
        }
        if (args.length == 1) {
            System.err.println("No output file specified");
            return;
        }
        compile(args[0], args[1]);
    }

    public static void compile(String inputFile, String outputFile) throws IOException {
        compile(new FileInputStream(inputFile), new FileOutputStream(outputFile));
    }

    /**
     * Compiles everything from the input stream into the output stream.
     *
     * @param is The input stream
     * @param os The output stream
     */
    public static void compile(InputStream is, OutputStream os) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(is);
        execscriptLexer lexer = new execscriptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        execscriptParser parser = new execscriptParser(tokens);
        lexer.removeErrorListeners();
        lexer.addErrorListener(DescriptiveErrorListener.INSTANCE);
        parser.removeErrorListeners();
        parser.addErrorListener(DescriptiveErrorListener.INSTANCE);
        ParseTree tree = parser.prog();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        ESVisitor visitor = new ESVisitor(writer);
        visitor.visit(tree);
        writer.flush();
        writer.close();
    }
}
