package com.abworks.structures.notifications;

public interface INotifier {

    void createTopic(String topicName);
    void createUser(String userName);
    void subscribe(String userName, String topicName);
    void send(String topicName, String message);

}
