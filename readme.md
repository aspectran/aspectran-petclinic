# Aspectran PetClinic Demo

This is a port of the popular Spring PetClinic sample application, demonstrating how to build the same application using the Aspectran framework. It showcases how to leverage Aspectran's features to create a database-driven web application with a clean and modular architecture.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

## About this Demo

This version of PetClinic is built on Aspectran and demonstrates the use of:

- **Aspectran Core**: For Inversion of Control (IoC) and Aspect-Oriented Programming (AOP).
- **Aspectran Web**: For building web application features.
- **JPA/Hibernate**: For data persistence.
- **Thymeleaf**: For the view layer.
- **Undertow**: As the embedded web server.
- **H2**: As the embedded, in-memory database.

## Requirements

- Java 21 or later
- Maven 3.6.3 or later

## Building from Source

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/aspectran/petclinic.git
    ```

2.  **Navigate to the project directory:**
    ```sh
    cd petclinic
    ```

3.  **Build the project with Maven:**
    This will compile the source code, package the application, and copy all necessary dependencies.
    ```sh
    mvn clean package
    ```

## Running the Application

Once the project is built, you can start the application using the Aspectran Shell.

1.  **Navigate to the `bin` directory:**
    ```sh
    cd app/bin
    ```

2.  **Start the Aspectran Shell:**
    ```sh
    ./shell.sh
    ```

3.  **Access the application:**
    Open your web browser and navigate to [http://localhost:8081](http://localhost:8081).

## Database Configuration

This application uses an in-memory H2 database by default. The database is initialized at startup with schema and data. You can access the H2 console to inspect the database at [http://localhost:8081/h2-console](http://localhost:8081/h2-console).

## License

This project is licensed under the [Apache License 2.0](LICENSE.txt).
