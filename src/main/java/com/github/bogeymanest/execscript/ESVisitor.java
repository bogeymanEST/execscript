package com.github.bogeymanest.execscript;

import com.github.bogeymanest.execscript.antlr.execscriptBaseVisitor;
import com.github.bogeymanest.execscript.antlr.execscriptParser;
import org.antlr.v4.runtime.Token;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ESVisitor extends execscriptBaseVisitor<Value> {
    private OutputStreamWriter writer;
    private boolean comments = true;
    //Holds variable information
    private Deque<Context> contexts = new ArrayDeque<>();
    private Pattern interpolPat = Pattern.compile("@\\{([a-zA-Z_0-9]+?)(?:\\[(.+)\\])?\\}");

    public ESVisitor(OutputStreamWriter writer) {
        this.writer = writer;
        contexts.push(new Context());
    }

    public ESVisitor(OutputStreamWriter writer, boolean comments) {
        this(writer);
        this.comments = comments;
    }

    public void pushContext() {
        contexts.push(new Context());
    }

    public void popContext() {
        contexts.pop();
    }

    public void setVariable(String id, Value value) {
        contexts.peek().set(id, value);
    }

    public Value getVariable(String id) {
        for (Context context : contexts) {
            Value v = context.get(id);
            if (v != null) return v;
        }
        return null;
    }

    @Override
    public Value visitAssign(execscriptParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        Value value = visit(ctx.expr());
        if (ctx.value() != null) {
            Value var = getVariable(id);
            Value idx = visit(ctx.value());
            if (var instanceof ListValue)
                var.asList().set(idx.asInt(), value);
            if (var instanceof MapValue)
                var.asMap().put(idx.asString(), value);
        } else setVariable(id, value);
        return value;
    }


    @Override
    public Value visitVarOpEq(execscriptParser.VarOpEqContext ctx) {
        Value var = getVariable(ctx.ID().getText());
        if (var == null)
            throw new RuntimeException("Unknown variable " + ctx.ID().getText());
        if (ctx.op.getText().equals("+=")) {
            var = var.add(visit(ctx.expr()));
        } else if (ctx.op.getText().equals("-=")) {
            var = var.subtract(visit(ctx.expr()));
        } else if (ctx.op.getText().equals("*=")) {
            var = var.multiply(visit(ctx.expr()));
        } else if (ctx.op.getText().equals("/=")) {
            var = var.divide(visit(ctx.expr()));
        }
        setVariable(ctx.ID().getText(), var);
        return var;
    }

    @Override
    public Value visitInt(execscriptParser.IntContext ctx) {
        return new IntegerValue(Integer.parseInt(ctx.INT().getText()));
    }

    @Override
    public Value visitFloat(execscriptParser.FloatContext ctx) {
        return new DoubleValue(Double.parseDouble(ctx.FLOAT().getText()));
    }

    @Override
    public Value visitString(execscriptParser.StringContext ctx) {
        String s = ctx.STRING().getText();
        return new StringValue(parseString(s.substring(1, s.length() - 1)));
    }

    @Override
    public Value visitCmd(execscriptParser.CmdContext ctx) {
        try {
            writer.write(parseString(ctx.getText()) + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Value visitLineComment(execscriptParser.LineCommentContext ctx) {
        try {
            writer.write(ctx.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Value visitMap(execscriptParser.MapContext ctx) {
        Map<String, Value> val = new LinkedHashMap<>();
        for (int i = 0; i < ctx.keys.size(); i++) {
            Token key = ctx.keys.get(i);
            String k = key.getText();
            if (!StringUtils.isNumeric(k))
                k = k.substring(1, k.length() - 1); //Remove quotes
            execscriptParser.ValueContext value = ctx.values.get(i);
            val.put(k, visit(value));
        }
        return new MapValue(val);
    }

    @Override
    public Value visitIndexed(execscriptParser.IndexedContext ctx) {
        Value v = getVariable(ctx.ID().getText());
        if (v instanceof ListValue) {
            return v.asList().get(visit(ctx.idx).asInt());
        }
        return v.asMap().get(visit(ctx.idx).asString());
    }

    @Override
    public Value visitId(execscriptParser.IdContext ctx) {
        return getVariable(ctx.ID().getText());
    }

    @Override
    public Value visitCompare(execscriptParser.CompareContext ctx) {
        Value left = visit(ctx.expr(0));
        Value right = visit(ctx.expr(1));
        int comp = left.compareTo(right);
        if (ctx.op.getText().equals("<"))
            return new BooleanValue(comp == -1);
        if (ctx.op.getText().equals(">"))
            return new BooleanValue(comp == 1);
        if (ctx.op.getText().equals("=="))
            return new BooleanValue(comp == 0);
        if (ctx.op.getText().equals("!="))
            return new BooleanValue(comp != 0);
        return null;
    }

    @Override
    public Value visitBool(execscriptParser.BoolContext ctx) {
        //ctx.BOOL().getText() can only be "true" or "false"
        return new BooleanValue(ctx.BOOL().getText().equals("true"));
    }

    @Override
    public Value visitIf(execscriptParser.IfContext ctx) {
        if (ctx.if_st != null && visit(ctx.if_ex).asBoolean()) {
            pushContext();
            ctx.if_st.forEach(this::visit);
            popContext();
        } else if (ctx.elif_ex != null && visit(ctx.elif_ex).asBoolean()) {
            pushContext();
            ctx.elif_st.forEach(this::visit);
            popContext();
        } else if (ctx.else_st != null) {
            pushContext();
            ctx.else_st.forEach(this::visit);
            popContext();
        }
        return null;
    }

    @Override
    public Value visitIncrement(execscriptParser.IncrementContext ctx) {
        Value v = getVariable(ctx.ID().getText());
        v.add(new IntegerValue(1));
        return v;
    }

/*    @Override
    public Value visitChildren(RuleNode node) {
        try {
            return super.visitChildren(node);
        } catch (Exception e) {
            RuleContext ctx = node.getRuleContext();
            System.err.println("Error when parsing!");
            System.err.println("Location: " + ctx.getSourceInterval());
            e.printStackTrace();
        }
        return null;
    }*/

    @Override
    public Value visitDecrement(execscriptParser.DecrementContext ctx) {
        Value v = getVariable(ctx.ID().getText());
        v.subtract(new IntegerValue(1));
        return v;
    }

    @Override
    public Value visitInc(execscriptParser.IncContext ctx) {
        Value v = getVariable(ctx.ID().getText());
        v.add(new IntegerValue(1));
        return v;
    }

    @Override
    public Value visitDec(execscriptParser.DecContext ctx) {
        Value v = getVariable(ctx.ID().getText());
        v.subtract(new IntegerValue(1));
        return v;
    }

    @Override
    public Value visitMulDiv(execscriptParser.MulDivContext ctx) {
        Value left = visit(ctx.expr(0));
        Value right = visit(ctx.expr(1));
        if (ctx.op.getText().equals("*"))
            return new DoubleValue(left.asDouble() * right.asDouble());
        if (ctx.op.getText().equals("/"))
            return new DoubleValue(left.asDouble() / right.asDouble());
        return null;
    }

    @Override
    public Value visitAddSub(execscriptParser.AddSubContext ctx) {
        Value left = visit(ctx.expr(0));
        Value right = visit(ctx.expr(1));
        if (left instanceof StringValue || right instanceof StringValue)
            return new StringValue(left.asString() + right.asString());
        if (ctx.op.getText().equals("+"))
            return new DoubleValue(left.asDouble() + right.asDouble());
        if (ctx.op.getText().equals("-"))
            return new DoubleValue(left.asDouble() - right.asDouble());
        return null;
    }

    @Override
    public Value visitList(execscriptParser.ListContext ctx) {
        List<Value> vals = ctx.elements.stream().map(this::visit).collect(Collectors.toList());
        return new ListValue(vals);
    }

    @Override
    public Value visitForeach(execscriptParser.ForeachContext ctx) {
        String varName = ctx.ID(0).getText();
        Value lst = getVariable(ctx.ID(1).getText());
        for (Value v : lst.asList()) {
            pushContext();
            setVariable(varName, v);
            ctx.statement().forEach(this::visit);
            popContext();
        }
        return null;
    }

    @Override
    public Value visitForeachMap(execscriptParser.ForeachMapContext ctx) {
        String keyVar = ctx.ID(0).getText();
        String valVar = ctx.ID(1).getText();
        Value map = getVariable(ctx.ID(2).getText());
        for (Map.Entry<String, Value> e : map.asMap().entrySet()) {
            pushContext();
            setVariable(keyVar, new StringValue(e.getKey()));
            setVariable(valVar, e.getValue());
            ctx.statement().forEach(this::visit);
            popContext();
        }
        return null;
    }

    @Override
    public Value visitParens(execscriptParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Value visitVal(execscriptParser.ValContext ctx) {
        return visit(ctx.value());
    }

    @Override
    public Value visitWhile(execscriptParser.WhileContext ctx) {
        while (visit(ctx.expr()).asBoolean()) {
            ctx.statement().forEach(this::visit);
        }
        return null;
    }

    /**
     * Parses a string replacing any interpolations with the respective variable values
     *
     * @param str The string to parse
     * @return The parsed string with interpolations replaced with respective variable values
     */
    public String parseString(String str) {
        StringBuffer sb = new StringBuffer();
        Matcher m = interpolPat.matcher(str);
        while (m.find()) {
            Value var = getVariable("@" + m.group(1));
            if (var == null)
                throw new RuntimeException("Could not find variable: @" + m.group(1));
            String val = var.asString();
            String idx = m.group(2);
            if (idx != null && !(var instanceof ListValue || var instanceof MapValue))
                throw new RuntimeException("Cannot index variable @" + m.group(1) + " - not a list or a map!");
            if (var instanceof ListValue && idx != null)
                val = var.asList().get(Integer.parseInt(idx)).asString();
            if (var instanceof MapValue && idx != null)
                val = var.asMap().get(idx).asString();

            m.appendReplacement(sb, val);
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
