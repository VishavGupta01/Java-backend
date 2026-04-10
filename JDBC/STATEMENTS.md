# JDBC Statement Execution Guide

In Java JDBC, the way you send SQL queries to your database impacts performance, security, and the overall maintainability of your code. This guide covers the two primary interfaces used for executing SQL: the standard `Statement` and the compiled `PreparedStatement`.

---

## 1. Statement Interface
The `Statement` interface is the base interface used for executing **static** SQL queries. It is best suited for one-off operations, such as Data Definition Language (DDL) queries like creating or altering tables, where the SQL string does not change.

**Initialization:**
```java
// No query is passed during creation
Statement st = connect.createStatement();
```

**Execution:**
You pass the full SQL query string directly into the execution methods.
* **Select Operations:** `ResultSet rs = st.executeQuery("SELECT * FROM users");`
* **Non-select Operations (Insert/Update/Delete):** `int rows = st.executeUpdate("DELETE FROM users WHERE id = 5");`

**Drawbacks:**
* Vulnerable to **SQL Injection** if you are concatenating user input directly into the query string.
* Slower for repeated tasks because the database must parse and compile the SQL string from scratch every single time it is executed.

---

## 2. PreparedStatement Interface
`PreparedStatement` extends the `Statement` interface. It is used for executing **parameterized** SQL queries, which helps prevent SQL Injection and drastically improves performance for repeated queries.

**Initialization:**
```java
// Query with placeholders (?) is passed during creation
String query = "INSERT INTO users (name, email) VALUES (?, ?)";
PreparedStatement pSt = connect.prepareStatement(query);
```

**Setting Parameters:**
Before executing, you must replace the `?` placeholders with actual values. The index is 1-based.
* `pSt.setString(1, "John Doe");` — Sets the first placeholder.
* `pSt.setInt(2, 25);` — Sets the second placeholder.

**Execution:**
Unlike the standard `Statement`, you **do not** pass the query string into the execution methods because it was already provided during initialization.
* **Select Operations:** `ResultSet rs = pSt.executeQuery();`
* **Non-select Operations:** `int rows = pSt.executeUpdate();`

---

## Quick Comparison: Statement vs. PreparedStatement

| Feature | `Statement` | `PreparedStatement` |
| :--- | :--- | :--- |
| **Parameters** | Static queries only (no `?`). | Dynamic queries using placeholders (`?`). |
| **Performance** | Slower for repeated tasks (re-compiled every time). | Faster for repeated tasks (compiled once, executed many times). |
| **Security** | Vulnerable to **SQL Injection**. | **Secure**; automatically escapes input values. |
| **Syntax** | `st.executeQuery(query);` | `pSt.executeQuery();` (Query is pre-defined). |
| **Readability** | Messy String concatenation. | Clean separation of query and data. |

---

## Under the Hood: Compilation & Advantages

When a database receives a query, it must parse, optimize, and compile it before execution.

### Performance Boost (The Math of Compilation)
When you create a `PreparedStatement`, the database pre-compiles and caches the execution plan immediately. If you execute this statement multiple times in a loop (just changing the parameters), the database skips the compilation step. Standard `Statement`s re-compile from scratch on every execution.

If $n$ is the number of times a query is executed, the total time cost is:

* **For standard `Statement`:**
  $$T_{total} = n \times (T_{parse} + T_{optimize} + T_{compile} + T_{execute})$$
* **For `PreparedStatement`:**
  $$T_{total} = (T_{parse} + T_{optimize} + T_{compile}) + (n \times T_{execute})$$

As $n$ increases, `PreparedStatement` saves massive amounts of processing time.

### Security (Prevention of SQL Injection)
`PreparedStatement` strictly treats parameter values as *data*, not as executable code. If a malicious user inputs `"OR 1=1"`, the database handles it as a literal string rather than modifying the core SQL logic. This makes your application immune to standard SQL injection attacks.

### Binary Data Handling
`PreparedStatement` makes it vastly easier to insert non-standard data types like BLOBs or CLOBs (e.g., images or large text files) using methods like `setBlob()` or `setBinaryStream()`, which is practically impossible with a standard `Statement`.