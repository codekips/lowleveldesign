package com.abworks.games.snakeladders;

import lombok.Getter;

@Getter
public class Player {



    private String name;
    private int position;

    public Player(String name) {
        this.name = name;
        this.position = 0;
    }

    public void moveTo(int position){
        this.position = position;
    }
}
