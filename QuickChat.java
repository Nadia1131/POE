
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 * @author RC_Student_Lab
 */
public class QuickChat {
    private ArrayList<Message> messages;
    private int totalMessages;
    private static final String FILE_NAME ="messages.json";
    private Gson gson = new Gson();

    public QuickChat() {
        messages = new ArrayList<>();
        totalMessages = 0;
         
      messages = loadMessages();
     
    //if loadMessages() returns null , we default to an empty list
    if (messages == null) {
        messages = new ArrayList<>();
    }
       
     
     
      //If no messages exist , load test data manually
      if (messages.isEmpty()) {
          String [][] testData = {
              {"+27834557896", "Did you get the cake?", "Sent"},
              {"+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored"},
              {"+27838884567", "Yohoooo, I am at your gate", "Disregard"},
              {"+0838884567", "It is dinner time", "Sent"},
              {"+27888884567", "Ok, I am leaving without you.", "Stored"}
          };
         
          int messageId = 1;
         
          for (String[] data : testData) {
              String recipient = data[0];
              String message = data[1];
              String flag = data[2];
             
             Message msg = new Message(messageId, recipient, message);
             messages.add(msg);
             
             messageId++;
          }
         
          saveMessages(); //save data immediately
          JOptionPane.showMessageDialog(null, "Test messages loaded successfully");
      }
     
       totalMessages = messages.size();
    }

    public void startChat(int maxMessages) {
        int messagesSent = 0;
        boolean running = true;
        while (running) {
            String choiceStr = JOptionPane.showInputDialog("""
                    MENU:
                    1) Send Message
                    2) Show Recently Sent Messages
                    3) View Longest Message
                    4) Search by Message Id
                    5) Search by Recipient Cell
                    6) Delete message by hash
                    7) Display full report                                                                                                              
                    8) Quit""");

            if (choiceStr == null) return; // exit if canceled
            int choice = Integer.parseInt(choiceStr);

            switch (choice) {
                case 1 -> {
                    if (messagesSent >= maxMessages) {
                        JOptionPane.showMessageDialog(null, "You have reached the maximum number of messages.");
                        break;
                    }
                    sendMessage(messagesSent + 1);
                    messagesSent++;
                    totalMessages++;
                }
                case 2 -> showMessages();
                case 3 -> displayLongestMessage();
                case 4 -> searchByMessageId();
                case 5 -> searchByRecipientCell();
                case 6 -> deleteByHash();
                case 7 -> displayReport();
                case 8 -> {
                    saveMessages();
                    JOptionPane.showMessageDialog(null, "Goodbye.");
                    running = false;
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid choice.");
            }
        }
    }
        private void sendMessage(int messageNumber) {
    String recipientCell = JOptionPane.showInputDialog("Enter the recipient's cellphone number. (include international code)");
    String messageText = JOptionPane.showInputDialog("Enter your message (max 250 characters)");
   
    if (messageText == null || messageText.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Message cannot be empty!");
        return;
    }

    // Create the message object
    Message msg = new Message(messageNumber, recipientCell, messageText);

    // Call the SentMessages() method and pass the message
    String response = Message.SentMessages(msg, messages);

    if (response.contains("Disregarded") || response.contains("No valid")) {
    JOptionPane.showMessageDialog(null, response);
    }
}
       
        //Display recently sent messages
    private void showMessages() {
        if (messages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages sent yet");
            return;
        }

        StringBuilder sb = new StringBuilder("Recently Sent Messages:\n\n");
        for (Message msg : messages) {
            sb.append("To: ").append(msg.getRecipientCell())
              .append("\nMessage: ").append(msg.getMessage())
              .append("\nMessage ID: ").append(msg.getMessageId())
              .append("\nHash: ").append(msg.getMessageHash())
              .append("\n-----------------------------\n");
        }

         JOptionPane.showMessageDialog(null, sb.toString());
    }
   
    //Display the longest message
    private void displayLongestMessage() {
        if (messages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages yet");
            return;
        }
       
         Message longest = messages.get(0);
        for (Message msg : messages) {
            if (msg.getMessage().length() > longest.getMessage().length()) {
                longest = msg;
            }
        }

       JOptionPane.showMessageDialog(null,
                "Longest Message:\nTo: " + longest.getRecipientCell() +
                        "\nMessage: " + longest.getMessage() +
                        "\nLength: " + longest.getMessage().length() + " characters");
    }
   
    //Search by Message ID
    private void searchByMessageId() {
        if (messages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages to search");
            return;
        }
       
         String id = JOptionPane.showInputDialog("Enter Message ID:");
        if (id == null) return;

        for (Message msg : messages) {
            if (msg.getMessageId().equalsIgnoreCase(id)) {
                JOptionPane.showMessageDialog(null,
                        "Message Found!\nRecipient: " + msg.getRecipientCell() +
                                "\nMessage: " + msg.getMessage() +
                                "\nHash: " + msg.getMessageHash());
                return;
            }
        }
       
        JOptionPane.showMessageDialog(null, "Message ID not found");
    }
   
     //Search by recipient
    private void searchByRecipientCell() {
        if (messages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages to search");
            return;
        }
       
        String name = JOptionPane.showInputDialog("Enter recipient cellphone number:");
        if (name == null) return;

        StringBuilder sb = new StringBuilder("Messages sent to " + name + ":\n\n");
        boolean found = false;
       
        for (Message msg : messages) {
            if (msg.getRecipientCell().equalsIgnoreCase(name)) {
                sb.append("Message ID: ").append(msg.getMessageId())
                        .append("\nMessage: ").append(msg.getMessage())
                        .append("\nHash: ").append(msg.getMessageHash())
                        .append("\n-----------------------------\n");
                found = true;
            }
        }
       
        if (found)
            JOptionPane.showMessageDialog(null, sb.toString());
        else
            JOptionPane.showMessageDialog(null, "No messages found for that recipient");
    }
   
    //Delete a message by hash
    private void deleteByHash() {
        if (messages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages to delete");
            return;
        }
       
         String hash = JOptionPane.showInputDialog("Enter Message Hash to delete:");
        if (hash == null) return;

        for (Message msg : messages) {
            if (msg.getMessageHash().equalsIgnoreCase(hash)) {
                messages.remove(msg);
                saveMessages();
                JOptionPane.showMessageDialog(null, "Message deleted successfully!");
                return;
            }
        }
       
        JOptionPane.showMessageDialog(null, "No message found with that hash");
    }
   
    //Display full report
    private void displayReport() {
        if (messages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages to display");
            return;
        }

       
        StringBuilder sb = new StringBuilder("====== MESSAGE REPORT ======\n\n");
        for (Message msg : messages) {
            sb.append("To: ").append(msg.getRecipientCell())
                    .append("\nMessage ID: ").append(msg.getMessageId())
                    .append("\nHash: ").append(msg.getMessageHash())
                    .append("\nMessage: ").append(msg.getMessage())
                    .append("\n-----------------------------\n");
        }
       
        sb.append("\n Total Messages: ").append(messages.size());
        JOptionPane.showMessageDialog(null, sb.toString());
    }
   
    //Save messages to JSON file
    private void saveMessages() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(messages, writer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving messages: " + e.getMessage());
        }
    }
   
    //Load messages from JSON file
    private ArrayList<Message> loadMessages() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }
       
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<ArrayList<Message>>() {
                private Type getType() {
                    return null;
                }
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading messages: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static class TypeToken<T> {

        public TypeToken() {
        }
    }

    private static class Gson {

        public Gson() {
        }

        private void toJson(ArrayList<Message> messages, FileWriter writer) {
        } 

        private ArrayList<Message> fromJson(FileReader reader, Type listType) {
                
            return null;
                
    }
    
}
}