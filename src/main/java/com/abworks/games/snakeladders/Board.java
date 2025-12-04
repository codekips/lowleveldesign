package com.abworks.games.snakeladders;

import java.util.Map;

public class Board {
//    board is a set of tile.
    private int maxNumber;
    private Snakes snakes;
    private Ladders ladders;

    private Board(int n, Snakes snakes, Ladders ladders){
        this.maxNumber = n;
        this.snakes = snakes;
        this.ladders = ladders;
    }
    public static Board build(int n, Snakes snakes, Ladders ladders){
        Board temp = new Board(n, snakes, ladders);
        temp.validate();
        return null;
    }

    private void validate() {
        if (maxNumber<5) throw new RuntimeException("Board too small");
        if (snakes.getStartPoints().contains(maxNumber)) throw new RuntimeException("Snake can't " +
                "be at the ending");
        if (ladders.getStartPoints().contains(maxNumber)) throw new RuntimeException("Snake can't " +
                "be at the ending");

    }


}
