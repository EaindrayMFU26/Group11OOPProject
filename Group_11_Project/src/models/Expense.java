package models;

/**
 * Class Expense:
 * **Compilation Command:** (Ensure models directory is included)
 * javac -d out -cp src src/models/Expense.java
 */

public class Expense extends Transaction // **Inheritance**: Extends Transaction class
 {
    public Expense(String description, double amount, String category) {
        super(description, amount, category);
    }

    @Override
    public String getType() // - **Polymorphism**: Overrides `getType()` method
    {
        return "Expense";
    }
}
