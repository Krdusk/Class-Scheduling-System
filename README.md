# Class Scheduling System

---

## Project Overview:
The **Class Scheduling System** is a console-based educational program developed as a **Final Project** for the course **Data Structures and Algorithms (DSA)**.  
It demonstrates the use of **HashMap** and **Object-Oriented Programming (OOP)** principles in organizing, storing, and managing class schedules efficiently.

Although the **NU Information System (NUIS)** already provides an automated scheduling platform, this project was created to simulate how such systems operate internally through **data structures and algorithms**.  
It aims to help students understand how efficient data access, modification, and organization work in real-world applications.

---

## Industry Application:
**Industry:** Education  
**Application:** Class and Course Scheduling for Students and Professors  

The system allows users to:
- Add new class schedules  
- Edit existing schedules  
- Search for schedules using the course code  
- Delete schedules when no longer needed  
- View all schedules in an organized, tabular format  

---

## Data Structure Used:
- **HashMap** – Stores schedules efficiently using the course code as a unique key.  
  - Enables constant-time complexity (O(1)) for insertion, search, and deletion.  
- **ArrayList** – Used to sort course codes alphabetically for display.  
- **OOP Concepts** – A `Schedule` class encapsulates all data (days, times, rooms, instructors) for modular and reusable design.  

---

## Technologies Used:
- **Programming Language:** Java  
- **Development Environment:** Visual Studio Code  
- **Java Version:** 17 (recommended)  
- **Program Type:** Console-based application using Scanner for input/output  

---

## Repository Contents:
| File/Folder | Description |
|--------------|-------------|
| `ClassSchedulingSystem.java` | Main Java source file containing the full program implementation. |
| `README.md` | Project overview, setup guide, and documentation summary. |
| `Documentation.pdf` | Official NU-format documentation containing introduction, explanation of data structure, sample input/output, limitations, and future enhancements. |
| `Screenshots/` | Folder containing sample outputs and menu screenshots from the console. |

---

## How to Run the Program:
1. Open **Visual Studio Code** or any preferred Java IDE.  
2. Create or open a folder named `ClassSchedulingSystem`.  
3. Place the file `ClassSchedulingSystem.java` inside the folder.  
4. Open the integrated terminal and compile the program:  
