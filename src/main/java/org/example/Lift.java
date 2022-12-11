package org.example;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

public class Lift {
    private Integer currentFloor;
    private final PriorityQueue<Request> queue = new PriorityQueue<>(
            Comparator.comparingInt(Request::getDestinationFloor));

    private Direction currentDirection = Direction.UP;

    private State state = State.IDLE;

    private String name = "Lift";

    public Lift() {
        currentFloor = 0;
    }

    public Lift(Integer currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setCurrentFloor(Integer floor) {
        currentFloor = floor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public State getState() {
        return state;
    }

    public PriorityQueue<Request> getQueue() {
        return queue;
    }

    private void process() {
        Request servingRequest = queue.poll();
        int timeTaken = 0;

        currentDirection = Objects.requireNonNull(servingRequest).getDirection();
        state = State.RUNNING;

        while (!Objects.equals(currentFloor, Objects.requireNonNull(servingRequest).getSourceFloor())) {
            if (servingRequest.getSourceFloor() > currentFloor) {
                currentFloor++;
            } else if (servingRequest.getSourceFloor() < currentFloor) {
                currentFloor--;
            }
            timeTaken++;
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            System.out.println(this);
        }

        timeTaken++;
        state = State.OPEN;
//        System.out.println(this);
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        state = State.CLOSE;
//        System.out.println(this);
        state = State.RUNNING;

        while (!Objects.equals(currentFloor, Objects.requireNonNull(servingRequest).getDestinationFloor())) {
            if (Objects.equals(servingRequest.getDirection(), Direction.UP)) currentFloor++;
            else if (Objects.equals(servingRequest.getDirection(), Direction.DOWN)) currentFloor--;
            timeTaken++;
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            System.out.println(this);
        }

        timeTaken++;
        state = State.OPEN;
//        System.out.println(this);
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        state = State.CLOSE;
//        System.out.println(this);
        state = State.IDLE;

        System.out.println(name + " took " + timeTaken + " units of time");
    }

    public void run(BlockingQueue<Request> requests) throws InterruptedException {
        Request request;

        while (true) {
            if (!requests.isEmpty()) {
                if (Objects.equals(requests.peek().getDirection(), currentDirection) || Objects.equals(state, State.IDLE)) {
                    request = requests.take();
                    queue.add(request);
                    CompletableFuture.runAsync(this::process);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Lift{" +
                "currentFloor=" + currentFloor +
                ", currentDirection=" + currentDirection +
                ", state=" + state +
                ", name='" + name + '\'' +
                '}';
    }
}
