%{
    #include <stdio.h>
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
        printf("Decimal: %d -> Hexadecimal: %X\n", $1, $1);
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