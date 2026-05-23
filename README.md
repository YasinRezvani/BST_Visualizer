# BST Visualizer
- **Supervisor:** [Dr. Maryam Khodabakhsh](https://www.shahroodut.ac.ir/en/as/?id=S931)  
- **Teaching Assistant:** Mr. Mohammad Ramzi  
- **Course:** Data Structure  
- **Organization:** [Shahrood University of Technology](https://www.shahroodut.ac.ir/en/)  

---

## 📖 About The Project
**BST Visualizer** is a desktop application written in Java (Swing) that allows you to **insert** and **remove** nodes in a Binary Search Tree (BST) and observe the tree structure dynamically.  
The program draws nodes as circles connected by lines, and supports **zoom** (mouse wheel) and **pan** (mouse drag) for exploring large trees comfortably.

---

## ✨ Features
- 🔵 **Insert** an integer value into the BST
- 🔴 **Remove** (delete) a value with proper BST reconnection
- 🖱️ **Zoom in/out** using the mouse wheel
- ✋ **Drag & pan** the tree view with mouse
- 🎨 Color‑coded nodes (blue circles with white text) and dark edges
- ⌨️ **Keyboard shortcut**: press `Enter` in the text field to perform insert/remove
- 🧩 Well‑separated classes: `TreeNode`, `BST`, `TreePanel`, `BST_Visualizer`

---

## 📦 Code Structure

| Class | Responsibility |
|-------|---------------|
| `TreeNode` | Represents a node of the BST (value, left child, right child) |
| `BST` | Contains the BST logic: `insert`, `remove`, `minValue` |
| `TreePanel` | Custom `JPanel` for rendering the tree; handles zoom & pan |
| `BST_Visualizer` | Main class that builds the GUI, connects buttons to BST operations |

---

## 🚀 How to Run

1. **Compile** all `.java` files in the same directory:
   ```bash
   javac BST_Visualizer.java
   ```
2. **Run** the application:
   ```bash
   java BST_Visualizer
   ```

> ⚠️ Make sure you have **Java 8 or higher** installed.

---

## 🕹️ Usage

1. Launch the program. A maximized window appears with two text fields and buttons.
2. **Insert** a number:
   - Type an integer in the **Insert** field and press `Enter` or click the **Insert** button.
   - The tree will update immediately.
3. **Remove** a number:
   - Type the value in the **Remove** field and press `Enter` or click the **Remove** button.
   - The node is deleted using the standard BST removal algorithm (replacing with inorder successor if two children exist).
4. **Navigate the tree**:
   - Use the **mouse wheel** to zoom in/out.
   - **Drag** with the mouse to move the tree around the panel.

---

## 🧪 Example Tree

The commented‑out array in the source code (inside `main`) contains a large set of values (from 0 to 88) that you can use to test the visualizer.  
Uncomment the loop to populate the tree automatically on startup.

---

## 📸 Screenshot

<img width="1918" height="1136" alt="Demo" src="https://github.com/user-attachments/assets/be46084e-2698-45dc-9fe0-fd8c186aea59" />



---

## ⚙️ Controls Summary

| Action | Input |
|--------|-------|
| Insert node | Type number → `Insert` button / `Enter` |
| Remove node | Type number → `Remove` button / `Enter` |
| Zoom in/out | Mouse wheel scroll |
| Pan view | Mouse drag |

---

## 📚 Dependencies
- **Java SE** (Swing, AWT, AWT Geometry) – no external libraries required.

---

## 📄 License
This project is provided for educational purposes as part of the **Data Structure** course.
