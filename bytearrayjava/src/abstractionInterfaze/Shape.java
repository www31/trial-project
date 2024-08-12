package abstractionInterfaze;

// Interface representing a shape
interface Shape {
    // Abstract method for calculating area
   double calculateArea();

    // Default method providing some default behavior
    default void printInfo() {
        System.out.println("This is a shape.");
    }
}