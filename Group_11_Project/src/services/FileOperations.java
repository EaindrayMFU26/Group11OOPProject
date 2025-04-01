package services;

import java.io.IOException;


/**
 * Interface FileOperations:
 * - **Interface Concept**: Defines common file operations for FinanceManager.
 * - Provides abstraction (FinanceManager implements these methods).
 *
 * **No Compilation Needed:** This is an interface and will be compiled when used in a class.
 */

 
public interface FileOperations {
    void saveToFile(String filename) throws IOException;
    void loadFromFile(String filename) throws IOException, ClassNotFoundException;
}
