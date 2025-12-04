package com.abworks.structures.elevatorsystem.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.PriorityQueue;
import java.util.UUID;

@Setter
@Getter
public class Elevator {
    private final UUID id;
    private int currFloor;
    private Direction movingDirection;
    private boolean isIdle;
    private PriorityQueue<Request> incomingRequests;

    public Elevator(){
        this.id = UUID.randomUUID();
    }

}
