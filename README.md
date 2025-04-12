Delta Lake Implementation Workshop

This workshop teaches the core concepts of Delta Lake by implementing key components step by step. The workshop consists of three assignments that build upon each other.

## Assignment 1: Implementing Delta Log Write Operation
**File:** `src/main/java/deltajava/objectstore/delta/storage/DeltaLog.java`  
**Method to Implement:** `write(long version, List<Action> actions)`

Implement the write method in DeltaLog class that handles writing transaction logs. This method is fundamental to Delta Lake's operation as it persists all changes to the transaction log.

Your implementation should:
1. Validate that the version number is not negative
2. Create the log file name using `LogFileName.fromVersion(version).getPathIn(logPath)`
3. Convert actions to JSON using `JsonUtil.toJson(actions)`
4. Write the JSON to storage using `storage.writeObject(logFileName, jsonBytes)`

Run `DeltaLogTests` to verify your implementation.

## Assignment 2: Implementing Delta Table Insert Operation
**File:** `src/main/java/deltajava/objectstore/delta/storage/DeltaTable.java`  
**Method to Implement:** `insert(List<Map<String, String>> records)`

Implement the insert method in DeltaTable class that handles writing data files in Parquet format. This method demonstrates how Delta Lake manages data files.

Your implementation should:
1. Generate a unique file name using UUID
2. Create a temporary file using `Files.createTempFile`
3. Write records to Parquet format using `ParquetUtil.writeRecords`
4. Read the temporary file into memory
5. Write to object store using `storage.writeObject`
6. Create and return an AddFile action
7. Clean up the temporary file

Run `DeltaTableTest` to verify your implementation.

## Assignment 3: Implementing Optimistic Transaction Commit
**File:** `src/main/java/deltajava/objectstore/delta/storage/OptimisticTransaction.java`  
**Method to Implement:** `commit(String operation)`

Implement the commit method in OptimisticTransaction class that handles atomic commits with optimistic concurrency control. This method shows how Delta Lake manages concurrent operations.

Your implementation should:
1. Acquire the DeltaLog lock using `deltaLog.lock()`
2. Check for conflicts with concurrent transactions
3. Create a CommitInfo action with operation details
4. Calculate the next version number (readVersion + 1)
5. Write actions to the log using `deltaLog.write()`
6. Update DeltaLog using `deltaLog.update()`
7. Release the lock using `deltaLog.releaseLock()`

Run `OptimisticTransactionTest` to verify your implementation.

## Running Tests
Each assignment includes comprehensive test cases. To run the tests:
```bash
./gradlew test
```

## Tips
- Review the test cases before starting implementation
- Look at the existing code and interfaces to understand the expected behavior
- Pay attention to error handling and edge cases
- Make sure to clean up resources (close files, release locks) properly

## Learning Objectives
After completing these assignments, you will understand:
- How Delta Lake manages transaction logs
- How data is stored and organized in Delta Lake
- How optimistic concurrency control works in Delta Lake
- How atomic commits are implemented in a distributed setting