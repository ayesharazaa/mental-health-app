# Mind Track

Mind Track is a full-stack mental health tracking web application built using **Spring Boot, MySQL, JWT Authentication, JavaScript, HTML, and CSS**.

## Project Overview

The purpose of this system is to help users monitor their everyday activities through multiple self-tracking tools.

Users can:

- Record moods
- Write journal entries with emotion tags
- Add and track habits
- Set and monitor goals
- Track stress levels
- Communicate through a support board
- Access mental health resources curated by the admin

The system also includes a **crisis detection mechanism** that automatically analyzes journal entries to detect signs of deteriorating mental health.

If potential risk is identified:

- The Admin can review alerts to verify crisis indications
- The Admin can flag inappropriate content that violates the code of conduct

The application ensures:

- Secure authentication using JWT
- Data privacy protection
- Export option for users to download their complete mental health dataset

---

# How to Set Up and Deploy the Project

## Prerequisites

Make sure the following are installed on your machine:

- Java Development Kit (JDK) 17+
- Maven
- MySQL Database Server

---

## Step 1: Open the Project Folder

- Extract the downloaded ZIP file (if applicable).
- Open the project folder in your IDE (e.g., IntelliJ IDEA or Eclipse) as a Maven project.

---

## Step 2: Configure the Database

Create a database in MySQL:

```sql
CREATE DATABASE mindtrack_db;
```

Update your Spring Boot configuration file:

src/main/resources/application.properties

Replace the placeholders with your MySQL credentials:

```
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```

---

## Step 3: Build and Run the Backend (Spring Boot)

Using your IDE:

- Open the project as a Maven project.
- Allow the IDE to automatically import dependencies.
- Run the main Spring Boot application class (e.g., DemoApplication.java with @SpringBootApplication).

---

## Step 4: Test the Application

Open your browser and navigate to:

http://localhost:8080/login

Test credentials:

**Admin**
- Username: admin
- Password: admin123

**User**
- Register a new account
