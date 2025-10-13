/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author RC_Student_Lab
 */
public class QuickChat {
    private ArrayList <Message> sentMessages = new ArrayList<>();
    private int totalSent = 0;

    public static void main(String[] args) {
        QuickChat app = new QuickChat();
        app.run();
    }

    public void run() {
        boolean running = true;

        while (running) {
            String[] options = {"Send Message", "Coming Soon", "Quit"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Welcome to QuickChat!\nChoose an option:",
                    "QuickChat Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            switch (choice) {
                case 0 -> // Send message
                    handleSendMessage();
                case 1 -> // Coming soon
                    JOptionPane.showMessageDialog(null, "Feature coming soon!");
                case 2, JOptionPane.CLOSED_OPTION -> // Quit
                    running = false;
                default -> JOptionPane.showMessageDialog(null, "Invalid choice, try again.");
            }
            // Quit
                    }

        JOptionPane.showMessageDialog(null, "Session ended.\nTotal messages sent: " + totalSent);
        printSentMessages();
    }

    private void handleSendMessage() {
        String recipient = JOptionPane.showInputDialog("Enter recipient phone number (+ followed by 11–13 digits):");
        if (recipient == null) return; // user cancelled

        String content = JOptionPane.showInputDialog("Enter your message (max 250 chars):");
        if (content == null) return; // user cancelled
        
        Message msg = new message(recipient, content);

        if (!msg.isValidmessageRecipient()) {
            JOptionPane.showMessageDialog(null, "Invalid recipient format!");
            return;
        }

        if (!msg.isValidMessageLength()) {
            JOptionPane.showMessageDialog(null, "Message too long! Limit 250 characters.");
            return;
        }

        String[] actions = {"Send", "Store", "Disregard"};
        int action = JOptionPane.showOptionDialog(null,
                "Choose what to do with the message:\n\n" + msg.toString(),
                "Message Action",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                actions,
                actions[0]);

        switch (action) {
            case 0 -> {
                // Send
                msg.setStatus("Sent");
                sentMessages.add(msg);
                totalSent++;
                JOptionPane.showMessageDialog(null, "Message sent successfully!");
            }

            case 1 -> {
                // Store
                msg.setStatus("Stored");
                storeToJson(msg);
                JOptionPane.showMessageDialog(null, "Message stored to JSON file.");
            }

            case 2, JOptionPane.CLOSED_OPTION -> // Disregard
                JOptionPane.showMessageDialog(null, "Message disregarded.");
            default -> JOptionPane.showMessageDialog(null, "Invalid choice.");
        }
            }


    private void printSentMessages() {
        if (sentMessages.isEmpty()) {
            System.out.println("No messages sent.");
        } else {
            System.out.println("Messages sent during this session:");
            for (Message msg : sentMessages) {
                System.out.println(msg);
            }
        }
    }
} 

