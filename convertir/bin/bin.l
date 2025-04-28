%{
    #include <stdio.h>
    #include "y.tab.h"
%}

%%

[0-9]+                  { yylval = atoi(yytext); return NUMBER; }
\n                      { return EOL; }
[ \t]                   ; // Ignorar espacios en blanco y tabulaciones
.                       ;

%%

int yywrap() {
    return 1;
}
