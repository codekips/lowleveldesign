package com.abworks.structures.elevatorsystem.manager;

import com.abworks.structures.elevatorsystem.domain.Elevator;

public class ElevatorManager {

    public boolean isMoving(Elevator e){
        return e.isIdle();
    }

    public void moveToNextFloor(Elevator elevator) {
    }
}
