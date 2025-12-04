package com.abworks.structures.notifications;

public class UserApp implements Runnable{

    private User user;
    public UserApp(User user){
        this.user = user;
    }
    @Override
    public void run() {
        System.out.println("\n\n -- Started user app: "+ user);
    }
}
