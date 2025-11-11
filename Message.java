import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;


/**
 *
 * @author RC_Student_Lab
 */
public class Message {
    private String messageId;
    private String recipientCell;
    private String message;
    private String messageHash;
    private static int numMessagesSent = 0;
    private int messageNumber;

    public Message(int messageNumber, String recipientCell, String message) {
        this.messageId = generateMessageId();
        this.recipientCell = recipientCell;
        this.messageNumber = messageNumber;
        this.message = message;
        this.messageHash = createMessageHash();
    }

    // Generate random message ID
    public static String generateMessageId() {
        Random rand = new Random();
        int id = 1000000000 + rand.nextInt(900000000);
        return String.valueOf(id);
    }

    // Create message hash
    public String createMessageHash() {
        String[] words = message.trim().split(" ");
        String firstWord = words.length > 0 ? words[0] : "NA";
        String lastWord = words.length > 1 ? words[words.length - 1] : "NA";
        return (messageId.substring(0, 2) + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase());
    }

    // Check message length and send
    public String sendMessage() {
        if (message.length() > 250) {
            return "Please enter a message of less than 250 characters.";
        } else {
            numMessagesSent++;
            return "Message sent.";
        }
    }

    // Choose action for message
    public static String SentMessages(Message msg, ArrayList<Message> messagesList) {
        String[] options = {"Send Message", "Store Message", "Disregard Message"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an action for this message:",
                "QuickChat Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        String response;
        switch (choice) {
            case 0 -> {
                response = "Message successfully sent.";
                messagesList.add(msg);
                numMessagesSent++;
            }
            case 1 -> {
                response = "Message successfully stored.";
                messagesList.add(msg);
                numMessagesSent++;
            }
            case 2 -> response = "Message disregarded.";
            default -> response = "No valid option selected.";
        }

        msg.displayMessageDetails(); // show details
        return response;
    }

    // Print message details
    public void displayMessageDetails() {
        String details = "Message ID: " + messageId
                + "\nMessage Hash: " + messageHash
                + "\nRecipient Cell: " + recipientCell
                + "\nMessage: " + message
                + "\nTotal Messages Sent: " + numMessagesSent;
        JOptionPane.showMessageDialog(null, details, "Message Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

   

    public static int getNumMessagesSent() {
        return numMessagesSent;
    }
    public String getMessage() {
        return message;
    }
    public String getRecipientCell() {
        return recipientCell;
    }    
    public String getMessageId()   {
        return messageId;
    }
    public String getMessageHash()   {
        return messageHash;
    }

   
}
