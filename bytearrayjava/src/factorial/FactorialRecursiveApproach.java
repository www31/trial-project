package factorial;

public class FactorialRecursiveApproach {

    // Method to calculate factorial recursively
    public static long factorialRecursive(int n) {
        if (n < 2) {
            return 1;
        } else {
            return n * factorialRecursive(n - 1);
        }
    }
    
    public static void main(String[] args) {
        int number = 5; // Example: Calculate factorial of 5
        long factorial = factorialRecursive(number);
        System.out.println("Factorial of " + number + " = " + factorial);
    }
}
