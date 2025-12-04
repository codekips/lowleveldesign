package com.abworks.structures.stockbroker;

import lombok.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Builder
@Getter
class OrderDetails {
    User orderPlacedBy;
    String orderID;
    int quantity;
    double price;
    Stock stock;
}

class BuyOrder {
    OrderDetails orderDetails;
    public BuyOrder(OrderDetails orderDetails){
        this.orderDetails = orderDetails;
    }



}

class SellOrder {
    OrderDetails orderDetails;
    public SellOrder(OrderDetails orderDetails){
        this.orderDetails = orderDetails;
    }
}


@Getter
class Stock {
    final String symbol;
    @Setter
    Double amount;
    public Stock(String symbol, double amount){
        this.symbol = symbol;
        this.amount = amount;
    }
}


class User {
    UUID userId;
    String name;
    public User(String name){
        this.userId = UUID.randomUUID();
    }
}

interface StockObserver {
    void update(Stock s);
}

@AllArgsConstructor
@EqualsAndHashCode
class UserNotifier implements StockObserver{
    final User u;


    @Override
    public void update(Stock s) {
        System.out.printf("User %s, notification %s \n", u.name, s);
    }
}

// Stock Observer
class FavoriteSubscriptions {
    private final Map<Stock, Set<StockObserver>> stockToUserSubscriptions = new HashMap<>();

    public void addSubscription(Stock s, User u){
        StockObserver userNotifier = new UserNotifier(u);
        stockToUserSubscriptions.computeIfAbsent(s, k->new HashSet<>()).add(userNotifier);
    }

    public void notifyUsers(Stock s) {
        for (StockObserver observer: stockToUserSubscriptions.getOrDefault(s, new HashSet<>()))
            observer.update(s);
    }
}
class StockService {
    FavoriteSubscriptions subscriptions = new FavoriteSubscriptions();
    void updateStockPrice(Stock s, double amount){
        s.setAmount(amount);
        subscriptions.notifyUsers(s);
    }
}

class ExchangeService {
    Map<Stock, PriorityQueue<BuyOrder>> buyOrders;
    Map<Stock, PriorityQueue<SellOrder>> sellOrders;

    public void placeBuyOrder(BuyOrder order){
        Stock toBuy = order.orderDetails.stock;
        if (!buyOrders.containsKey(toBuy)){
            buyOrders.put(toBuy,
                    new PriorityQueue<>(Comparator
                            .comparingDouble((o)->((BuyOrder)o).orderDetails.price)
                            .reversed()));
        }
        buyOrders.get(toBuy).offer(order);
    }
    public void placeSellOrder(SellOrder order){
        //lowest sell wins
        Stock toSell = order.orderDetails.stock;
        if (!sellOrders.containsKey(toSell)){
            sellOrders.put(toSell,
                    new PriorityQueue<>(Comparator.comparingDouble((o)->o.orderDetails.price)));
        }
        sellOrders.get(toSell).offer(order);
    }

}

public class BrokerSystem {
    FavoriteSubscriptions subscriptionsService = new FavoriteSubscriptions();

    public void registerStock(Stock stock){

    }
    public void registerUser(User user){

    }
    void addStockToFavorites(Stock stock, User user){
        subscriptionsService.addSubscription(stock, user);
    }


}
