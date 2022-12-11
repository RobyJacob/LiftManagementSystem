package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);

        Integer numFloors = Integer.parseInt(scanner.next());
        Integer numLifts = Integer.parseInt(scanner.next());

        start(numLifts);

        scanner.close();
    }

    private static void start(Integer numLifts) throws InterruptedException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BlockingQueue<Request> requests;
        try (ExecutorService liftService = Executors.newFixedThreadPool(numLifts)) {
            requests = new LinkedBlockingQueue<>();

            for (int i = 0; i < numLifts; i++) {
                int finalI = i;
                liftService.execute(() -> {
                    Lift lift = new Lift();
                    lift.setName("Lift-" + (finalI + 1));
                    try {
                        lift.run(requests);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        while (true) {
            System.out.println("Any requests? ");
            String resp = reader.readLine().toLowerCase();

            if (Objects.equals(resp, "yes")) {
                System.out.println("Source floor: ");
                Integer sourceFloor = Integer.parseInt(reader.readLine());
                System.out.println("Destination floor: ");
                Integer destinationFloor = Integer.parseInt(reader.readLine());
                System.out.println("Direction: ");
                String direction = reader.readLine().toLowerCase();
                Request request = null;
                if (Objects.equals(direction, "up")) {
                    request = new Request(sourceFloor, destinationFloor, Direction.UP);
                } else if (Objects.equals(direction, "down")) {
                    request = new Request(sourceFloor, destinationFloor, Direction.DOWN);
                }
                requests.put(Objects.requireNonNull(request));
            }
        }
    }
}