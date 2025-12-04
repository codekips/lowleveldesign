package com.abworks.structures.notifications;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

@EqualsAndHashCode
class Topic {
    String topicName;
    UUID id;
    public Topic(String topicName){
        this.topicName = topicName;
        this.id = UUID.randomUUID();
    }
    @Override
    public String toString(){
        return "["+topicName+"]";
    }
}

@EqualsAndHashCode
class User {
    String userName;
    UUID id;
    public User(String userName){
        this.userName = userName;
        this.id = UUID.randomUUID();
    }
    public String toString(){
        return "["+userName+"]";
    }
}



class TopicService {
    Map<String, Topic> nameTopicMap;
    Map<Topic, Set<User>> topicUserSubscriptions;
    public TopicService(){
        this.nameTopicMap = new HashMap<>();
    }
    public void createTopic(String name){
        if (nameTopicMap.containsKey(name)){
            System.err.println("Cannot create the topic. Already exists");
        }
        else {
            nameTopicMap.put(name, new Topic(name));
        }
    }


}

class UserService {
    Map<String, User> nameUserMap;

    public UserService(){
        this.nameUserMap = new HashMap<>();
    }
    public User createUser(String name){
        if (nameUserMap.containsKey(name)){
            System.err.println("Cannot create the user. Already exists");
        }
        else {
            System.out.println("Creating user "+ name);
            nameUserMap.put(name, new User(name));
        }
        return nameUserMap.get(name);
    }

    public void startUserApp(User user) {
        UserApp userApp = new UserApp(user);
        new Thread(userApp).start();
    }
}





public class NotificationsManager implements INotifier{
    private TopicService topicService = new TopicService();
    private UserService userService = new UserService();

    @Override
    public void createTopic(String topicName) {
        topicService.createTopic(topicName);

    }

    @Override
    public void createUser(String userName) {
        User user = userService.createUser(userName);
        userService.startUserApp(user);

    }

    @Override
    public void subscribe(String userName, String topicName) {

    }

    @Override
    public void send(String topicName, String message) {

    }
}
