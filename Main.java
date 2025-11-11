
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RC_Student_Lab
 */
public class Main {
    public static void main(String[] args) {
       String firstName = "";
        String surname = "";
        String username = "";
        String password = "";
        String cellNumber;

        // NAME AND SURNAME
        boolean validName = false;
        while (!validName) {
            firstName = JOptionPane.showInputDialog(null, "Enter your First Name:");
            surname = JOptionPane.showInputDialog(null, "Enter your Surname:");

            if (firstName != null && !firstName.trim().isEmpty()
                    && surname != null && !surname.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name and Surname successfully captured.");
                validName = true;
            } else {
                JOptionPane.showMessageDialog(null, "Both First Name and Surname must be entered.");
            }
        }

        // USERNAME
        boolean validUsername = false;
        while (!validUsername) {
            username = JOptionPane.showInputDialog(null,
                    "Enter username (must contain '_' and be no more than 5 characters):");
            if (username != null && username.contains("_") && username.length() <= 5) {
                JOptionPane.showMessageDialog(null, "Username successfully captured.");
                validUsername = true;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Username is not correctly formatted. Ensure it contains '_' and is no more than 5 characters.");
            }
        }

        // PASSWORD
        boolean validPassword = false;
        while (!validPassword) {
            password = JOptionPane.showInputDialog(null,
                    "Enter password (min 8 chars, 1 uppercase, 1 number, 1 special character):");
            if (password != null && password.length() >= 8
                    && password.matches(".*[A-Z].*")
                    && password.matches(".*[0-9].*")
                    && password.matches(".*[!@#$%^&(),.?\":{}|<>].*")) {
                JOptionPane.showMessageDialog(null, "Password successfully captured.");
                validPassword = true;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Password is not correctly formatted. It needs 8+ chars, 1 uppercase, 1 number, and 1 special character.");
            }
        }

        // CELL NUMBER
        boolean validCell = false;
        while (!validCell) {
            cellNumber = JOptionPane.showInputDialog(null,
                    "Enter South African cell number (include +27 and 9 digits):");
            if (cellNumber != null && cellNumber.matches("^\\+27\\d{9}$")) {
                JOptionPane.showMessageDialog(null, "Cell phone number successfully added.");
                validCell = true;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Cell phone number incorrectly formatted or does not contain international code.");
            }
        }

        // LOGIN
        boolean loggedIn = false;
        while (!loggedIn) {
            String loginUsername = JOptionPane.showInputDialog(null, "LOGIN\nEnter username:");
            String loginPassword = JOptionPane.showInputDialog(null, "Enter password:");
            if (loginUsername != null && loginPassword != null
                    && loginUsername.equals(username)
                    && loginPassword.equals(password)) {
                JOptionPane.showMessageDialog(null, "Login successful! \nWelcome back, "
                        + firstName + " " + surname + ".\nIt’s great to see you again!");
                loggedIn = true;
            } else {
                JOptionPane.showMessageDialog(null, "Login failed. Incorrect username or password. Try again.");
            }
        }

        // Launch QuickChat
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");
        int maxMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages do you want to send?"));
        QuickChat chat = new QuickChat();
        chat.startChat(maxMessages);
    }

    
}

        
    
        

