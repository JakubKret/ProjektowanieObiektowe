package org.example.model;

import org.example.state.HealthState;
import org.example.state.InfectedState;
import org.example.strategy.MovementStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BehavioralPatternsTest {

    @Mock MovementStrategy mockMovement;
    @Mock HealthState mockState;

    @BeforeAll
    static void init() {
        SimulationConfig.initialize(10, 50, 20, 30, 100, 15, 25, 5, 2, 10);
    }

    @Test
    void testEntityDelegatesToStrategyAndState() {
        Entity testEntity = new Entity(2, 2, mockMovement) {};
        testEntity.changeState(mockState);
        Tile[][] dummyBoard = new Tile[10][10];

        testEntity.performTurn(dummyBoard, 10, 10);

        verify(mockMovement, times(1)).move(testEntity, dummyBoard, 10, 10);
        verify(mockState, times(1)).passTurn(eq(testEntity), anyDouble());
    }

    @Test
    void testStateTransitionOnInfection() {
        Human human = EntityFactory.createHuman(0, 0);
        human.changeState(new InfectedState());
        assertTrue(human.getHealthState().canInfectOthers(), "Chora jednostka musi zakażać.");
    }
}