package com.abworks.structures.elevatorsystem.manager;

import com.abworks.structures.elevatorsystem.domain.Elevator;
import com.abworks.structures.elevatorsystem.domain.ElevatorGroup;
import com.abworks.structures.elevatorsystem.domain.Request;
import com.abworks.structures.elevatorsystem.worker.ElevatorWorker;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorGroupManager {

    private ElevatorGroup elevatorGroup;
    private static ElevatorGroupManager instance;
    private ExecutorService executorService;

    private ElevatorGroupManager() {
        executorService = Executors.newFixedThreadPool(10);
    }

    public static ElevatorGroupManager initialize() {
        if (instance == null) {
            instance = new ElevatorGroupManager();
            instance.start();
        }
        return instance;
    }

    private void start() {
        for (Elevator elevator : elevatorGroup.getElevators()) {
            executorService.submit(new ElevatorWorker(elevator));
        }
    }

    public ElevatorGroupManager(ElevatorGroup elevatorGroup) {
        this.elevatorGroup = elevatorGroup;
    }

    public void submitRequest(Request request) {

    }
}
