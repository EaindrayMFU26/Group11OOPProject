package services;

import models.Transaction;
import models.Income;
import models.Expense;

import java.io.*;
import java.time.ZoneId;
import java.util.*;

/**
 * Interface FinanceManagerInterface:
 * - **Extends** FileOperations for file management.
 * - Defines core functionalities of FinanceManager.
 *
 * **No Compilation Needed:** This is an interface and will be compiled when used in a class.
 */
public class FinanceManager {
    private List<Transaction> transactions;
    private Scanner scanner;
    private double budget;
    private double totalIncome;
    private double totalExpenses;
    private List<String> incomeCategories;
    private List<String> expenseCategories;

    // Constructor initializes budget and predefined categories
    public FinanceManager() {
        transactions = new ArrayList<>();
        scanner = new Scanner(System.in);
        budget = 0;

        // Separate categories for Income and Expenses
        incomeCategories = new ArrayList<>(Arrays.asList("Salary", "Bonus", "Investments"));
        expenseCategories = new ArrayList<>(Arrays.asList("Food", "Rent", "Entertainment", "Bills", "Shopping"));
    }


    /**
     * **Set Monthly Budget**
     * - Prompts user for a budget and stores it.
     */
    public void setBudget() {
        System.out.print("Enter your monthly budget: ");
        budget = getValidDoubleInput();
        System.out.println("Budget set to: $" + budget);
    }

    /**
     * **Add Transaction**
     * - Allows user to enter an `Income` or `Expense`.
     * - Uses method `chooseCategory()` to assign categories.
     */
    public void addTransaction() {
        System.out.print("Enter transaction type (1-Income, 2-Expense): ");
        int type = getValidIntInput(1, 2);

        String category = chooseCategory(type);

        System.out.print("Enter amount: ");
        double amount = getValidDoubleInput();

        System.out.print("Enter description: ");
        String description = getValidDescription();

        Transaction transaction;
        if (type == 1) {
            transaction = new Income(description, amount, category);
            totalIncome += amount;
        } else {
            transaction = new Expense(description, amount, category);
            totalExpenses += amount;
        }

        transactions.add(transaction);
        System.out.println("Transaction added successfully!");

        // **Budget Warning System**
        if (totalExpenses > budget * 0.9 && totalExpenses <= budget) {
            System.out.println("⚠ Warning: Your expenses are close to exceeding your budget!");
        }
        else if(totalExpenses > budget) {
            System.out.println("⚠ Warning: You have exceeded your budget!");
        }
    }

    /**
     * **Display Transactions**
     * - Prints all transactions stored in the system.
     */
    public void displayTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
            return;
        }

        System.out.println("\n=== Transaction History ===");
        int index = 1;
        for (Transaction t : transactions) {
            System.out.println(index++ + ". " + t);
        }
    }

    // Display financial summary
    public void displaySummary() {
        double savings = totalIncome - totalExpenses;
        System.out.println("\n=== Financial Summary ===");
        System.out.println("Budget: $" + budget);
        System.out.println("Total Income: $" + totalIncome);
        System.out.println("Total Expenses: $" + totalExpenses);
        System.out.println("Net Savings: $" + savings);
    }

    
     /**
     * **Delete a Transaction**
     * - User selects a transaction to remove.
     * - Adjusts total income/expenses accordingly.
     */
    public void deleteTransaction() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions to delete.");
            return;
        }

        displayTransactions();
        System.out.print("Enter the transaction number to delete: ");
        int index = getValidIntInput(1, transactions.size());

        Transaction transaction = transactions.remove(index - 1);

        // Adjust totals
        if (transaction instanceof Income) {
            totalIncome -= transaction.getAmount();
        } else {
            totalExpenses -= transaction.getAmount();
        }

        System.out.println("Transaction deleted successfully!");
    }

    // Export transactions to CSV
    public void exportToCSV() {
        try (PrintWriter writer = new PrintWriter(new File("transactions.csv"))) {
            writer.println("Type,Description,Amount,Category,Date");
            for (Transaction t : transactions) {
                writer.println(t.getType() + "," + t.getDescription() + "," + t.getAmount() + "," + t.getCategory() + "," + t.getTimestamp());
            }
            System.out.println("Transactions exported to transactions.csv");
        } catch (IOException e) {
            System.out.println("Error exporting to CSV: " + e.getMessage());
        }
    }

    // Choose category based on transaction type
    private String chooseCategory(int type) {
        List<String> categories = (type == 1) ? incomeCategories : expenseCategories;
        System.out.println("Choose a category:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
        System.out.println((categories.size() + 1) + ". Other (Create New Category)");
        System.out.print("Enter category: ");

        int choice = getValidIntInput(1, categories.size() + 1);
        if (choice == categories.size() + 1) {
            System.out.print("Enter new category name: ");
            String newCategory = scanner.nextLine().trim();
            categories.add(newCategory);
            return newCategory;
        }
        return categories.get(choice - 1);
    }

    // Get valid integer input
    private int getValidIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Invalid input! Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    // Get valid double input
    private double getValidDoubleInput() {
        while (true) {
            try {
                double input = Double.parseDouble(scanner.nextLine().trim());
                if (input > 0) {
                    return input;
                }
                System.out.println("Amount must be greater than zero! Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid amount.");
            }
        }
    }

    private String getValidDescription() {
        while (true) {
            String description = scanner.nextLine().trim();
            if (!description.isEmpty() && !description.matches("\\d+")) {
                return description;
            }
            System.out.println("Description cannot be empty or only numbers. Please enter a valid description.");
        }
    }

    // Save transactions to file
    /**
     * **Save Data to File**
     * - Saves transactions, budget, and categories for later use.
     */

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(transactions);
            oos.writeDouble(budget);
            oos.writeDouble(totalIncome);
            oos.writeDouble(totalExpenses);
            oos.writeObject(incomeCategories);
            oos.writeObject(expenseCategories);
        }
    }

    /**
     * **Load Data from File**
     * - Retrieves previously saved data.
     */

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            transactions = (List<Transaction>) ois.readObject();
            budget = ois.readDouble();
            totalIncome = ois.readDouble();
            totalExpenses = ois.readDouble();
            incomeCategories = (List<String>) ois.readObject();
            expenseCategories = (List<String>) ois.readObject();
        }
    }

    // Run the finance tracker
    public void run() {
        try {
            loadFromFile("finance_data.ser");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        }

        while (true) {
            System.out.println("\n=== Personal Finance Tracker ===");
            System.out.println("1. Set Budget");
            System.out.println("2. Add Transaction");
            System.out.println("3. View Transactions");
            System.out.println("4. Delete Transaction");
            System.out.println("5. View Summary");
            System.out.println("6. Export to CSV");
            System.out.println("7. Save & Exit");
            System.out.print("Choose an option: ");

            int choice = getValidIntInput(1, 7);

            switch (choice) {
                case 1: setBudget(); break;
                case 2: addTransaction(); break;
                case 3: displayTransactions(); break;
                case 4: deleteTransaction(); break;
                case 5: displaySummary(); break;
                case 6: exportToCSV(); break;
                case 7: {
                    try {
                        saveToFile("finance_data.ser");
                        System.out.println("Data saved. Exiting...");
                    } catch (IOException e) {
                        System.out.println("Error saving data: " + e.getMessage());
                    }
                    return;
                }
            }
        }
    }
}
