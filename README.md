# Smart Expense & Budget Manager

A modular, multi-threaded Java console application designed to record transactions, manage budgets, and calculate spending analytics using standard Java features which can prove to be usefull in real world.

## Key Features
- **User Management**: Is Simple user session initialization.
- **Transaction Engine**: Does Record income and expenses with validated numeric and categorical inputs.
- **Financial Analytics**: Real-time balance calculations and categorical expense breakdown.
- **Asynchronous Persistence**: Background thread (`AutoSaveThread`) automatically writes state changes to disk every 15 seconds.
- **Robust Exception Handling**: Custom business rules prevent zero/negative values or corrupted files from halting execution.

## Technologies Used
- **Language**: Java 17+
- **Architecture**: Model-View-Controller (MVC) Pattern
- **Threading**: Java Threads & Concurrency (`Daemon Threads`)
- **Storage**: Standard Java File I/O (`BufferedReader`/`BufferedWriter`)

## How to Compile & Run

### Prerequisites
- JDK 17 or higher installed.

### Execution Steps
1. Clone or download the repository:
   ```bash
   git clone [https://github.com/your-username/smart-expense-manager.git](https://github.com/RamanK717/smart-expense-manager.git)
   cd smart-expense-manager
