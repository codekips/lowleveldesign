package com.abworks.structures.pubsubservice;


import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

interface MessageSubscriber {
    void onMessage(Message message);
    String getId();
}

@AllArgsConstructor
class SubscribingUser implements MessageSubscriber {
    User u;

    @Override
    public void onMessage(Message message) {
        System.out.printf("User %s received message=%s \n", u, message);
    }

    @Override
    public String getId() {
        return u.name;
    }
}

class Topic {
    String name;
    UUID id;
    public Topic(String name){
        this.name = name;
        this.id = UUID.randomUUID();
    }
}
class User {
    String name;
    UUID id;
    public User(String name){
        this.name = name;
        this.id = UUID.randomUUID();
    }
    public String toString(){
        return "[User]: "+ this.name;
    }
}
class Message {
    String text;
    UUID id;
    public Message(String text){
        this.text = text;
        this.id = UUID.randomUUID();
    }
    public String toString(){
        return "[Message]: "+ this.text;
    }

}

class TopicService {
    private final Map<String, Topic> topicMap = new ConcurrentHashMap<>();
    private final SubscriptionService subscriptionService = new SubscriptionService();

    Topic add(String name) {
        Topic topic = topicMap.getOrDefault(name, new Topic(name));
        topicMap.putIfAbsent(name, topic);
        return topic;
    }

    public Topic getByName(String name) {
        return topicMap.get(name);
    }

    public void publish(Topic topic, Message message) {
        subscriptionService.notifySubscribers(topic, message);
    }

    public void createSubscription(Topic t, User user) {
        subscriptionService.createSubscription(t, user);
    }

}
class SubscriptionService {
    private final Map<Topic, Set<MessageSubscriber>> subscriberMap = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void createSubscription(Topic t, User user) {
        SubscribingUser subscribingUser = new SubscribingUser(user);
        subscriberMap.computeIfAbsent(t, k -> new HashSet<>()).add(subscribingUser);
    }

    public void removeSubscription(Topic t, User user) {
        SubscribingUser subscribingUser = new SubscribingUser(user);
        subscriberMap.get(t).remove(subscribingUser);
    }

    public void notifySubscribers(Topic t, Message message) {

        var subscribers = subscriberMap.getOrDefault(t, new HashSet<>());
        for (MessageSubscriber subscriber : subscribers) {
            executorService.submit(() -> {
                try {
                    subscriber.onMessage(message);
                } catch (Exception e) {
                    System.err.println("Error delivering message to subscriber " + subscriber.getId() + ": " + e.getMessage());
                }
            });

        }
    }
}


public class PubsubSystem {
    TopicService topicService = new TopicService();
    SubscriptionService subscriptionService = new SubscriptionService();
    Topic createTopic(String tName){
        return topicService.add(tName);

    }

    void createSubscription (String name, User user){
        Topic t = topicService.getByName(name);
        if (t == null)
            throw new IllegalArgumentException("Invalid topic "+ name);
        topicService.createSubscription(t, user);
    }

    void removeSubscription (String name, User user){

    }

    void publish(String topicName, Message message){
        Topic t = topicService.getByName(topicName);
        if (t == null)
            throw new IllegalArgumentException("Invalid topic "+ topicName);
        topicService.publish(t, message);
    }

    void shutdown(){

    }
}
