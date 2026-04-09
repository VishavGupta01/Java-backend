
# Environment Variables Setup (`dotenv-java`)

This project uses the `dotenv-java` library to manage sensitive configuration data, such as database connection credentials, outside of the main source code.

## ⚠️ Security Warning
**NEVER** commit your `.env` file to version control (Git). Doing so will expose your passwords and secrets. 

Before proceeding, ensure you have a `.gitignore` file in your project's root directory that includes the following line:
```text
.env
```
---

## 1. Installation

Depending on your project structure, add the library using one of the following methods:

### Option A: Plain Java Project (Manual JAR)
1. Download the latest `dotenv-java` `.jar` file from the [Maven Central Repository](https://mvnrepository.com/artifact/io.github.cdimascio/dotenv-java).
2. In IntelliJ IDEA: Go to **File** > **Project Structure** > **Modules** > **Dependencies**.
3. Click the **`+`** icon, select **JARs or Directories**, and locate the downloaded `.jar` file.
4. Click **Apply** and **OK**.

### Option B: Maven (`pom.xml`)
```xml
<dependency>
    <groupId>io.github.cdimascio</groupId>
    <artifactId>dotenv-java</artifactId>
    <version>3.0.0</version>
</dependency>
```

### Option C: Gradle (`build.gradle`)
```groovy
implementation 'io.github.cdimascio:dotenv-java:3.0.0'
```

---

## 2. Configuration

1. Create a new file named exactly `.env` in the root directory of this project.
2. Add your environment variables in a `KEY=VALUE` format. Do not use quotes around the values unless the value itself contains a space.

**Example `.env` structure:**
```env
DB_URL=jdbc:mysql://localhost:3306/your_database_name
DB_USER=root
DB_PASSWORD=your_super_secret_password
```

---

## 3. Usage

Import the `Dotenv` class and use it to load your variables into your application securely.

```java
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        
        // 1. Initialize and load the .env file
        Dotenv dotenv = Dotenv.load();

        // 2. Retrieve variables by their exact key name
        String dbUrl = dotenv.get("DB_URL");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");

        // 3. Use the variables in your application
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            System.out.println("Secure connection established!");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## Troubleshooting
* **`NullPointerException` or Variable Not Found:** Ensure your `.env` file is in the absolute root folder of the project, not inside the `src` directory.
* **Class Not Found:** Ensure the `.jar` dependency is properly linked in your IDE's project structure.
```