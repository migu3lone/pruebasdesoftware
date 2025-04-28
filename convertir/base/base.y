%{
    #include <stdio.h>
    #include <math.h>
    int yylex(void);
    void yyerror(char *s);

    void convertToBase(int num, int base) {
        int numx = num;
        int remainder;
        char converted[100];
        int index = 0;

        while (num > 0) {
            remainder = num % base;
            if (remainder < 10)
                converted[index++] = remainder + '0';
            else
                converted[index++] = remainder - 10 + 'A';
            num /= base;
        }

        printf("Decimal: %d -> Base: %d = ", numx, base);
        for (int i = index - 1; i >= 0; i--) {
            printf("%c", converted[i]);
        }
        printf("\n");
    }

%}

%token NUMBER
%token ESPACIO
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
    NUMBER ESPACIO NUMBER EOL {
        int num = $1, base = $3;
        //printf("Ingrese un numero: ");
        //scanf("%d", &num);
        //printf("Ingrese la base a la que desea convertir: ");
        //scanf("%d", &base);

        //EVALUAR '(' Expr ')' ';'

        if (base < 2 || base > 36) {
            printf("La base debe estar entre 2 y 36.\n");
            return 1;
        }

        convertToBase(num, base);
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
