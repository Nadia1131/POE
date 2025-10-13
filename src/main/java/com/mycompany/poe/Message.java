/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

/**
 *
 * @author RC_Student_Lab
 */
public class Message {
    private String messageId; 
       private String recipientNumber;
       private String message;
       private String messageHash;
       private String status;
       private static int messageSent = 0;
       private int messageNumber;
       
       
       public Message (String recipientNumber, String message) { 
           this.messageId =  generateMessageId();
           this.recipientNumber = recipientNumber;
           this.message = message;
           this.messageHash = createMessageHash;
       }
       
       //checking message ID
       public static String generateMessageId() {
           Random rand = new Random();
           int id = 1000000000 + rand.nextInt(900000000);
           return String.valueOf(id);
       }   
       
       //checking recipient cell number
       public boolean checkRecipientNumber() {
           boolean validNumber = false;
              while (!validNumber) {
               recipientNumber = JOptionPane.showInputDialog(null, "enter South African cell number(include +27 && 9 digits):");   
                 if (recipientNumber != null && recipientNumber.matches("^\\+27\\d{9}$")) {
                     JOptionPane.showMessageDialog(null, "Cell phone number successfully captured");
                validNumber = true;
                } else {
                     JOptionPane.showMessageDialog(null, "Cell phone number incorrectly formatted or does not contain international code. Please correct the number and try again.");
                }     
            }         
           return false;
    }                 
       
         //check message length
         public boolean isValidMessageLength() {
             return message.length() <= 250;
         }
         
         //prompting the user to either snd . store or disregard the message
         String[] actions = {"Send", "Store", "Disregard"};

// Show options to user
int choice = JOptionPane.showOptionDialog(
        null,
        "What would you like to do with this message?",
        "Message Options",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        actions,
        actions[0]
);

// Perform action based on user's choice
switch (choice) {
    case 0 -> { // Send
        sentMessages.add(message);
        totalSent++;
        JOptionPane.showMessageDialog(null, "Message sent successfully!");
    }
    case 1 -> { // Store
        storeToJson(message);
    }
    case 2 -> { // Disregard
        JOptionPane.showMessageDialog(null, "Message disregarded.");
    }
    default -> JOptionPane.showMessageDialog(null, "No valid action selected.");
}
         
   // create and and return the message hash
         public String createMessageHash() {
            String[] words = message.split("");
            String firstWord = words.length > 0 ? words[0] : "NA";
            String lastWord = words.length > 1 ? words[words.length - 1] : "NA";
            return (messageId.substring(0,2) + firstWord + lastWord).toUpperCase();
         }      
           
    //getters
         public String getMessageId() { return messageId; }

    public String getRecipient() { return recipient; }

    public String getContent() { return content; }

    public String getMessageHash() { return messageHash; }

    public static int getMessageCount() { return messageCount; }

    //printing the messages sent
    public void displaySentMessages() {
    if (sentMessages.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No messages have been sent yet.");
        return;
    }

    StringBuilder sb = new StringBuilder("Messages Sent:\n\n");
    for (Message m : sentMessages) {
        sb.append("To: ").append(m.getRecipient()).append("\n");
        sb.append("Message: ").append(m.getContent()).append("\n");
        sb.append("Hash: ").append(m.getMessageHash()).append("\n");
        sb.append("-----------------------------\n");
    }

    JOptionPane.showMessageDialog(null, sb.toString(), "Sent Messages", JOptionPane.INFORMATION_MESSAGE);
}
         
    //returning the total messages sent
    public static int returnTotalMessages() {
         return numMessagesSent;
    }         
}
}
