package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RequestTest {

    @Test
    public void testInitRequest() {
        Request request = new Request(2, 5, Direction.UP);

        Assertions.assertEquals(2, request.getSourceFloor());
        Assertions.assertEquals(5, request.getDestinationFloor());
        Assertions.assertEquals(Direction.UP, request.getDirection());
    }
}
