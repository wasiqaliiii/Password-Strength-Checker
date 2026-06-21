# 🔒 SecurePass Pro

## Password Strength Analyzer

SecurePass Pro is a cybersecurity-focused desktop application developed in Java Swing. It helps users evaluate the strength and security of their passwords by analyzing various security parameters and providing recommendations for improvement.

The application also includes a secure password generator and a visual password strength meter to enhance user awareness regarding password security.

---

## 👨‍💻 Student Information

**Name:** Wasiq Ali  
**Registration Number:** L1F23BSSE0102

---

## 📖 Project Overview

Many users create weak passwords that are easy to guess or crack, making their accounts vulnerable to cyberattacks. SecurePass Pro addresses this issue by analyzing passwords and providing immediate feedback and recommendations to help users create stronger and more secure passwords.

The system evaluates passwords based on:

- Password Length
- Uppercase Letters
- Lowercase Letters
- Numbers
- Special Characters

The application also detects common weak passwords and warns users about potential security risks.

---

## 🎯 Objectives

- Analyze password strength using security rules.
- Detect weak and commonly used passwords.
- Generate strong random passwords.
- Provide security recommendations.
- Visualize password strength using a progress meter.
- Demonstrate Object-Oriented Programming concepts.
- Develop a modern GUI using Java Swing.
- Follow clean code and optimization principles.

---

## ✨ Features

### 1. Password Strength Analysis
Evaluates passwords using:

- Minimum length requirement
- Uppercase letters
- Lowercase letters
- Numeric digits
- Special symbols

---

### 2. Password Security Score

Each password receives a security score from:



based on its complexity.

---

### 3. Strength Classification

| Score Range | Strength |
|------------|----------|
| 0 - 39 | Weak |
| 40 - 79 | Medium |
| 80 - 100 | Strong |

---

### 4. Common Password Detection

The system detects commonly used passwords such as:

- password
- 123456
- admin
- qwerty
- welcome

and displays a warning message.

---

### 5. Password Generator

Generates secure passwords containing:

- Uppercase Letters
- Lowercase Letters
- Numbers
- Special Characters

---

### 6. Password Strength Meter

Visual feedback using color indicators:

| Color | Strength |
|---------|-----------|
| 🔴 Red | Weak |
| 🟠 Orange | Medium |
| 🟢 Green | Strong |

---

### 7. Theme Switching

Supports:

- 🌙 Dark Theme
- ☀️ Light Theme

for improved user experience.

---

## 🛠 Technologies Used

| Technology | Purpose |
|------------|----------|
| Java | Programming Language |
| Java Swing | GUI Development |
| OOP Concepts | Software Design |
| HashSet | Fast Password Lookup |
| StringBuilder | Efficient String Handling |

---

## 🧩 OOP Concepts Implemented

### Encapsulation
Used in:

- Password Class
- SecurityReport Class

Data members are kept private and accessed through methods.

---

### Inheritance

PasswordAnalyzer inherits functionality from SecurityTool.

---

### Polymorphism

Method overriding is implemented using:

```java
getToolName()
