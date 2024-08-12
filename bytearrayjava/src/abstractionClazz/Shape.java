package abstractionClazz;

// Abstract class representing a shape
abstract class Shape {
    // Abstract method for calculating area (to be implemented by subclasses)
    public abstract double calculateArea();

    // Concrete method that can be shared among subclasses
    public void printInfo() {
        System.out.println("This is a shape.");
    }
}
