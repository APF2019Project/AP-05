package Chat;

import Main.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;

public class Message {
    private static final ArrayList<Message> allMessages = new ArrayList<>();
    private static int lastId;
    private String content;
    private User sender, receiver;
    private Message repliedMessage;
    private int id;

    public Message(String content, User sender, User receiver) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.id = lastId;
        lastId++;
    }

    public Message(String content, User sender, User receiver, int id) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.id = id;
        lastId = Math.max(id + 1, lastId);
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

    public static JSONArray getChatBetweenUsers(User user1, User user2) {
        ArrayList<Message> arrayList = getChatBetweenUsersArray(user1, user2);
        JSONArray jsonArray = new JSONArray();
        for(Message message : arrayList) {
            jsonArray.add(message.toJsonObject());
        }
        return jsonArray;
    }

    public Message getRepliedMessage() {
        return repliedMessage;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.getId());
        jsonObject.put("content", this.getContent());
        jsonObject.put("sender", this.getSender().getUsername());
        jsonObject.put("repliedMessageId", this.getRepliedMessage().getId());
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