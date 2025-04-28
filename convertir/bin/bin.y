%{
    #include <stdio.h>
    #include <stdlib.h>
    #include <math.h>
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
        printf("Decimal: %d -> Binario: ", num);
        int binary[32]; // Suponemos que manejamos números de hasta 32 bits
        int i = 0;

        while (num > 0) {
            binary[i] = num % 2;
            num = num / 2;
            i++;
        }

        for (int j = i - 1; j >= 0; j--) {
            printf("%d", binary[j]);
        }
        printf("\n");
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
