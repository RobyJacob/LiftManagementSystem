package org.example;

public class Request {
    private final Integer sourceFloor;
    private final Integer destinationFloor;
    private final Direction direction;

    public Request(Integer sourceFloor, Integer destinationFloor, Direction direction) {
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.direction = direction;
    }

    public Integer getSourceFloor() {
        return sourceFloor;
    }

    public Integer getDestinationFloor() {
        return destinationFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Request{" +
                "sourceFloor=" + sourceFloor +
                ", destinationFloor=" + destinationFloor +
                ", direction=" + direction +
                '}';
    }
}
