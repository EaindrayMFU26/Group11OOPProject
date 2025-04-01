package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Abstract class Transaction:
 * - Implements **Polymorphism** (Different transactions behave differently).
 *
 * **Compilation Command:**
 * javac -d out -cp src src/models/Transaction.java
 */
  

public abstract class Transaction implements Serializable// -Implements **Serializable** (Allows saving to file).
 {
    private static final long serialVersionUID = 1L;
    protected String description;
    protected double amount;
    protected String category;
    protected LocalDateTime timestamp;

     // Constructor with validation
    public Transaction(String description, double amount, String category) {
        if (description.matches("\\d+")) {
            throw new IllegalArgumentException("Description cannot be only numbers!");
        }
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.timestamp = LocalDateTime.now();  // Automatically stores current timestamp
    }

    public abstract String getType();  // Must be implemented by child classes

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return getType() + ": " + description + " | Amount: $" + amount + " | Category: " + category + " | Date: " + getTimestamp();
    }
}
