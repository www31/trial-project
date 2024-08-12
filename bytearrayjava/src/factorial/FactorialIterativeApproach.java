package factorial;

public class FactorialIterativeApproach {
    
    // Method to calculate factorial iteratively
    public static long factorialIterative(int n) {
        long factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }
    
    public static void main(String[] args) {
        int number = 5; // Example: Calculate factorial of 5
        long factorial = factorialIterative(number);
        System.out.println("Factorial of " + number + " = " + factorial);
    }
}
