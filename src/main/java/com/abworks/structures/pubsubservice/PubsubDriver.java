package com.abworks.structures.pubsubservice;

public class PubsubDriver {
    public static void main(String[] args) {
        PubsubSystem pubsubSystem = new PubsubSystem();

        User basicUser1 = new User("basic1");
        User basicUser2 = new User("basic2");


        pubsubSystem.createTopic("sports");
        pubsubSystem.createTopic("news");
        pubsubSystem.createSubscription("sports", basicUser1);
        pubsubSystem.createSubscription("news", basicUser1);
        pubsubSystem.createSubscription("sports", basicUser2);

        pubsubSystem.publish("news", new Message("news 1"));
        pubsubSystem.publish("sports", new Message("sports 1"));
        pubsubSystem.publish("news", new Message("news 2"));



    }
}
