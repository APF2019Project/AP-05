package Chat;

import Main.GameData;
import Main.JSONHandler;
import Main.Menu;
import Main.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class Message {
    private static final ArrayList<Message> allMessages = new ArrayList<>();
    private static int lastId;
    private String content;
    private User sender, receiver;
    private Message repliedMessage;
    private int id;

    public void setRepliedMessage(Message repliedMessage) {
        this.repliedMessage = repliedMessage;
        try {
            if (repliedMessage != null && repliedMessage.getSender() != this.getSender() &&
                    repliedMessage.getSender().getConnection() != null) {
                repliedMessage.getSender().getConnection().send("notification", this.toJsonObject());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Message(String content, User sender, User receiver) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.id = lastId;
        lastId++;
        allMessages.add(this);
        try {
            saveAllMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Message(String content, User sender, User receiver, int id) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.id = id;
        lastId = Math.max(id + 1, lastId);
        allMessages.add(this);
    }

    public static Message getMessageById(int id) {
        for (Message message : allMessages) {
            if (message.getId() == id)
                return message;
        }
        return null;
    }

    private static ArrayList<Message> getChatBetweenUsersArray(User user1, User user2) {
        ArrayList<Message> chat = new ArrayList<>();
        for (Message message : allMessages) {
            if ((message.getSender() == user1 && message.getReceiver() == user2) ||
                    (message.getSender() == user2 && message.getReceiver() == user1)) {
                chat.add(message);
            }
        }
        chat.sort(Comparator.comparing(Message::getId));
        return chat;
    }
    private static ArrayList<Message> getChatOfServerArray() {
        ArrayList<Message> chat = new ArrayList<>();
        for (Message message : allMessages) {
            if( message.getReceiver() == null) {
                chat.add(message);
            }
        }
        chat.sort(Comparator.comparing(Message::getId));
        return chat;
    }
    public static JSONArray getChatBetweenUsers(User user1, User user2) {
        ArrayList<Message> arrayList = getChatBetweenUsersArray(user1, user2);
        JSONArray jsonArray = new JSONArray();
        for(Message message : arrayList) {
            jsonArray.add(message.toJsonObject());
        }
        return jsonArray;
    }
    public static JSONArray getChatOfServer() {
        ArrayList<Message> arrayList = getChatOfServerArray();
        JSONArray jsonArray = new JSONArray();
        for(Message message : arrayList) {
            jsonArray.add(message.toJsonObject());
        }
        return jsonArray;
    }
    public synchronized static void saveAllMessages() throws Exception {
        JSONArray messageJsonArray = new JSONArray();
        for(Message message : allMessages) {
            JSONObject messageJsonObject = new JSONObject();
            messageJsonObject.put(FieldNames.id.name() , message.getId());
            messageJsonObject.put(FieldNames.content.name(), message.getContent());
            messageJsonObject.put(FieldNames.senderUsername.name(), message.getSender().getUsername());
            if(message.getReceiver()==null){
                messageJsonObject.put(FieldNames.receiverUsername.name(), "GlobalChat");
            }else {
                messageJsonObject.put(FieldNames.receiverUsername.name(), message.getReceiver().getUsername());
            }
            if(message.getRepliedMessage() != null) {
                messageJsonObject.put(FieldNames.repliedId.name(), message.getRepliedMessage().getId());
            }
            messageJsonArray.add(messageJsonObject);
        }
        new JSONHandler(new File(GameData.messagesJSONFilePath)).set(Main.FieldNames.messages, messageJsonArray);
    }

    public Message getRepliedMessage() {
        return repliedMessage;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.getId());
        jsonObject.put("content", this.getContent());
        jsonObject.put("senderImage", this.getSender().getImageAddress());
        jsonObject.put("senderUsername", this.getSender().getUsername());

        if(getReceiver() != null)
            jsonObject.put("receiverUsername", getReceiver().getUsername());

        if(repliedMessage != null) {
            jsonObject.put("repliedUsername", this.getRepliedMessage().getSender().getUsername());
            jsonObject.put("repliedMessage", this.getRepliedMessage().getContent());
        }
        return jsonObject;
    }

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public int getId() {
        return id;
    }
}
