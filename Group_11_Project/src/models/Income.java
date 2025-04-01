package models;


/**
 * Class Income:
 * **Compilation Command:**
 * javac -d out -cp src src/models/Income.java
 */


public class Income extends Transaction // **Inheritance**: Extends `Transaction` class.
 {
    public Income(String description, double amount, String category) {
        super(description, amount, category);
    }

    @Override
    public String getType() //- **Polymorphism**: Overrides `getType()` method.
    {
        return "Income";
    }
}
