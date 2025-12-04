package com.abworks.structures.elevatorsystem.worker;

import com.abworks.structures.elevatorsystem.domain.Elevator;
import com.abworks.structures.elevatorsystem.manager.ElevatorManager;

public class ElevatorWorker implements Runnable{
    Elevator elevator;
    public ElevatorWorker(Elevator elevator){
        this.elevator = elevator;

    }
    @Override
    public void run() {
        ElevatorManager elevatorManager = new ElevatorManager();
        if (elevatorManager.isMoving(elevator)){
            elevatorManager.moveToNextFloor(elevator);
        }
        try {
            elevatorManager.moveToNextFloor(elevator);
            Thread.sleep(500);
        } catch (InterruptedException e){
            System.out.println("Elevator got interrupted");
            throw new RuntimeException(e);
        }

    }
}
