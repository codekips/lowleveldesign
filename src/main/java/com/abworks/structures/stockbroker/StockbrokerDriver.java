package com.abworks.structures.stockbroker;

public class StockbrokerDriver {
    public static void main(String[] args) {
        BrokerSystem brokerSystem = new BrokerSystem();
        Stock s = new Stock("AAPL", 100.05);
        User u1 = new User("user1");
        User u2 = new User("user2");
        brokerSystem.addStockToFavorites(s, u2);
        brokerSystem.addStockToFavorites(s, u1);

        // add buy order

    }

}
