parser grammar execscriptParser ;

options {
    tokenVocab=execscript;
}

prog:   statement+ EOF;

statement
    :   expr NL                 # printExpr
    |   ID EQ expr NL           # assign
    |   concmd                  # cmd
    |   NL                      # blank
    ;

expr
    :   expr op=(MUL|DIV) expr      # MulDiv
    |   expr op=(ADD|SUB) expr      # AddSub
    |   value                       # val
    |   ID                          # id
    |   LPAR expr RPAR              # parens
    ;

value
    : INT   # int
    | FLOAT # float
    | LBR (value
    ;
concmd : (CONCMD | CONCMD_ID) ;
comment : LINE_COMMENT ;


ws : WS | NL ;

