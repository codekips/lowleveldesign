package com.abworks.games.snakeladders;

import java.util.Map;

public class Ladders extends JumpPointCollection{

    public Ladders(Map<Integer, Integer> ladders){
        super(ladders);
    }
    @Override
    protected boolean isValid(Integer start, Integer end) {
        if (start == 0) return false;
        return start < end;
    }
}
