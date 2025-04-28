%{
    #include <stdio.h>
    #include <stdlib.h>
    int yylex(void);
    void yyerror(char *s);
%}

%token NUMBER
%token EOL

%%

input:
    lines
    ;

lines:
    lines line
    | line
    ;

line:
    NUMBER EOL {
        int num = $1;
        printf("Decimal: %d -> Octal: %o\n", num, num);
    }
    | EOL
    ;

%%

int main() {
    yyparse();
    return 0;
}

void yyerror(char *s) {
    printf("\n%s\n", s);
}