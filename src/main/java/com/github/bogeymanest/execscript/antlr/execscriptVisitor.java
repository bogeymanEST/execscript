// Generated from grammar/execscript.g4 by ANTLR 4.5
package com.github.bogeymanest.execscript.antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link execscriptParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface execscriptVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link execscriptParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(execscriptParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link execscriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(execscriptParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varOpEq}
	 * labeled alternative in {@link execscriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarOpEq(execscriptParser.VarOpEqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code cmd}
	 * labeled alternative in {@link execscriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmd(execscriptParser.CmdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code foreach}
	 * labeled alternative in {@link execscriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForeach(execscriptParser.ForeachContext ctx);
	/**
	 * Visit a parse tree produced by the {@code foreachMap}
	 * labeled alternative in {@link execscriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForeachMap(execscriptParser.ForeachMapContext ctx);
	/**
	 * Visit a parse tree produced by the {@code while}
	 * labeled alternative in {@link execscriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile(execscriptParser.WhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code if}
	 * labeled alternative in {@link execscriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(execscriptParser.IfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lineComment}
	 * labeled alternative in {@link execscriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLineComment(execscriptParser.LineCommentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code increment}
	 * labeled alternative in {@link execscriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncrement(execscriptParser.IncrementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code decrement}
	 * labeled alternative in {@link execscriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecrement(execscriptParser.DecrementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link execscriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlank(execscriptParser.BlankContext ctx);
	/**
	 * Visit a parse tree produced by the {@code val}
	 * labeled alternative in {@link execscriptParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVal(execscriptParser.ValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link execscriptParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(execscriptParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compare}
	 * labeled alternative in {@link execscriptParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompare(execscriptParser.CompareContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dec}
	 * labeled alternative in {@link execscriptParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDec(execscriptParser.DecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addSub}
	 * labeled alternative in {@link execscriptParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(execscriptParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link execscriptParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(execscriptParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDiv}
	 * labeled alternative in {@link execscriptParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDiv(execscriptParser.MulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inc}
	 * labeled alternative in {@link execscriptParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInc(execscriptParser.IncContext ctx);
	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link execscriptParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(execscriptParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code float}
	 * labeled alternative in {@link execscriptParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloat(execscriptParser.FloatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code string}
	 * labeled alternative in {@link execscriptParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(execscriptParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link execscriptParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(execscriptParser.BoolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code indexed}
	 * labeled alternative in {@link execscriptParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexed(execscriptParser.IndexedContext ctx);
	/**
	 * Visit a parse tree produced by the {@code list}
	 * labeled alternative in {@link execscriptParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(execscriptParser.ListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code map}
	 * labeled alternative in {@link execscriptParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMap(execscriptParser.MapContext ctx);
}