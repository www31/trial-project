#include <stdio.h>

// parameters are what a function expects when it's invoked
void birthday(char x[], int y) {
    printf("\nHappy birthday dear %s!", x);
    printf("\nYou are %d years old!", y);
}

int main() {

    char name[] = "Bro";
    int age = 21;
    // arguments are what you're sending a function
    birthday(name, age);

    return 0;
}