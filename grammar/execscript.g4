grammar execscript ;

@lexer::members {
    //Channel identifiers
    public static final int WHITESPACE = 1;
    public static final int COMMENTS = 2;
}

//A program consists of a number of statements
prog:   (statement NL?)+ EOF;

//A stement
statement
    //Assignment
    :   ID('['value']')? '=' expr           # assign
    //Shorthand aritmetic operations
    |   ID op=('+='|'-='|'*='|'/=') expr    # varOpEq
    //Consoe command
    |   (CONCMD | CONCMD_ID)                # cmd
    //For-each loop
    |   '@for ' ID ' in ' ID NL
        statement*
    '@endfor'                               # foreach
    //Map for-each loop
    |   '@for ' ID ': ' ID ' in ' ID NL
        statement*
    '@endfor'                               # foreachMap
    //While loop
    |   '@while ' expr NL
        statement*
        '@endwhile'                         # while
    //If statement
    |   '@if ' if_ex=expr NL
        if_st+=statement*
        (
        '@elseif ' elif_ex=expr NL
        elif_st+=statement*
        )*
        (
        '@else' NL
        else_st+=statement*
        )?
        '@endif'                            # if
    | LINE_COMMENT                          # lineComment
    //A variable incremented
    |   ID '++'                             # increment
    //A variable decremented
    |   ID '--'                             # decrement
    //Blank line
    |   NL                                  # blank
    ;

//An expression
expr
    //Multiplication or division
    :   expr op=('*'|'/') expr          # mulDiv
    //Addition or subtraction
    |   expr op=('+'|'-') expr          # addSub
    //Boolean statement
    |   expr op=('<'|'>'|'=='|'!=') expr # compare
    //A value
    |   value                           # val
    //A variable
    |   ID                              # id
    //A variable incremented
    |   ID '++'                         # inc
    //A variable decremented
    |   ID '--'                         # dec
    //An expresion in parentheses
    |   '(' expr ')'                    # parens
    ;

//A value
value
    //Integer value
    : INT                               # int
    //Floating point
    | FLOAT                             # float
    //String
    | STRING                            # string
    //Boolean
    | BOOL                              # bool
    //Variable with an index e.g. @var[0]
    | ID '['idx=value']'         # indexed
    //List
    | '[' NL?
        (elements+=value
        (NL?','NL? elements+=value)*)?
        NL?
      ']'                               # list
    //Map
    | '{' NL?
        (keys+=(STRING|INT) NL? ': '  NL? values+=value
        (NL? ',' NL? keys+=(STRING|INT) NL? ': ' NL? values+=value)*)?
        NL?
      '}'                               # map
    ;

//Console command with a value (convar)
CONCMD : CONCMD_ID ' ' ~([\n\r])*  ;

//Console command name
CONCMD_ID : [a-zA-Z_]+ ;

//A single-line comment
LINE_COMMENT : '//' .*? ('\n' | EOF);

//Variable identifier
ID  :   '@' [a-zA-Z][a-zA-Z0-9_]* ;

//Integer
INT :   [0-9]+ ;
//Floating point number
FLOAT:  [0-9]+ '.' [0-9]+ ;

BOOL : 'true' | 'false' ;

NL  :   '\r'? '\n' ;
WS  :   [ \t]+ -> channel(WHITESPACE) ;
STRING : '"' ('\\"'|~["\r\n])* '"' ;