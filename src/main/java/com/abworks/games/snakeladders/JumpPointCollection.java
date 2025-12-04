package com.abworks.games.snakeladders;

import lombok.Getter;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class JumpPointCollection {
    private final Map<Integer, Integer> jumpPointMapping;
    @Getter
    private final Set<Integer> startPoints;
    @Getter
    private final Set<Integer> endPoints;


    public JumpPointCollection(Map<Integer, Integer> points){
        jumpPointMapping = new HashMap<>();
        for (Map.Entry<Integer, Integer> jumpEntry: points.entrySet()){
            if (!isValid(jumpEntry.getKey(), jumpEntry.getValue())){
                System.err.println("Invalid entry. Ignoring");
            }
            else this.jumpPointMapping.put(jumpEntry.getKey(), jumpEntry.getValue());
        }
        this.startPoints = jumpPointMapping.keySet();
        this.endPoints = new HashSet<>(jumpPointMapping.values());
    }

    protected abstract boolean isValid(Integer start, Integer end);


}
