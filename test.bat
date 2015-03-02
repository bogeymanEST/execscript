@echo off
pushd target\classes
call grun com.github.bogeymanest.execscript.antlr.execscript %1 -tokens -tree D:/Prog/Java/execscript/test/%2 %3
popd
