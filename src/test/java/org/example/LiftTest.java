package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LiftTest {
    Lift lift = new Lift();

    @Test
    public void testInitLift() {
        Assertions.assertEquals(0, lift.getCurrentFloor());
    }
}
