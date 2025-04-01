package services;

import java.io.IOException;


/**
 * `FinanceManager`:
 * - Implements Interfaces: Implements FinanceManagerInterface and FileOperations.
 * - Uses Collections: List<Transaction> stores transactions.
 * - Exception Handling: Catches invalid inputs and ensures robust execution.
 *
 * **Compilation Command:**
 * javac -d out -cp src src/services/FinanceManager.java src/models/*.java
 */

public interface FinanceManagerInterface extends FileOperations {
    void setBudget();
    void addTransaction();
    void displayTransactions();
    void displaySummary();
    void deleteTransaction();
    void exportToCSV();
}
