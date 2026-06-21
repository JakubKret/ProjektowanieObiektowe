package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationConfigTest {

    @BeforeEach
    void setUp() {
        SimulationConfig.initialize(10, 50, 20, 30, 100, 15, 25, 5, 2, 10);
    }

    @Test
    void testSingletonInstanceIsUnique() {
        SimulationConfig instance1 = SimulationConfig.getInstance();
        SimulationConfig instance2 = SimulationConfig.getInstance();
        assertSame(instance1, instance2, "Singleton powinien zwracać tę samą instancję.");
    }
}