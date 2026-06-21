package org.example.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BuilderTest {

    @Mock
    BoardBuilder mockBuilder;

    @Test
    void testDirectorBuildSequence() {
        Board dummyBoard = new Board();
        when(mockBuilder.getResult()).thenReturn(dummyBoard);

        SimulationDirector director = new SimulationDirector(mockBuilder);
        Board result = director.constructStandardBoard();

        assertSame(dummyBoard, result);

        InOrder inOrder = inOrder(mockBuilder);
        inOrder.verify(mockBuilder).buildTerrain();
        inOrder.verify(mockBuilder).buildPopulation();
        inOrder.verify(mockBuilder).buildIslandsAndAirports();
        inOrder.verify(mockBuilder).buildPlanes();
        inOrder.verify(mockBuilder).getResult();
        verifyNoMoreInteractions(mockBuilder);
    }
}