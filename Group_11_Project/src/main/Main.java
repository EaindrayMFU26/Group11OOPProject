package main;

import services.FinanceManager;

/**
 * Main class:
 * - **Entry point** of the application.
 * - Creates an instance of `FinanceManager` and starts the program.
 *
 * **Compilation Command:**
 * javac -d out -cp src src/main/Main.java src/services/FinanceManager.java src/models/*.java
 *
 * **Execution Command:**
 * java -cp out main.Main
 */

public class Main {
    public static void main(String[] args) {
        FinanceManager manager = new FinanceManager();
        manager.run(); // Starts the finance tracker
    }
}
