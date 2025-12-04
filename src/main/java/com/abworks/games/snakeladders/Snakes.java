package com.abworks.games.snakeladders;

import java.util.Map;

public class Snakes extends JumpPointCollection{

    public Snakes(Map<Integer, Integer> snakes){
        super(snakes);
    }
    @Override
    protected boolean isValid(Integer start, Integer end) {
        if (start == 0) return false;
        return start > end;
    }
}
