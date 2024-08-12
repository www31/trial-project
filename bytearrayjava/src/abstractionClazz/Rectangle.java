package abstractionClazz;

// Concrete subclass of Shape representing a rectangle
class Rectangle extends Shape{
    private double length;
    private double width;
    
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    
    // Implementing the abstract method to calculate area
    @Override
    public double calculateArea() {
        return length * width;
    }
}