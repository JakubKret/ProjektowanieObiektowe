package org.example.observer;

import org.example.model.Board;
import org.example.model.SimulationConfig;
import org.example.model.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObserverTest {

    @Mock SimulationObserver mockObserver1;
    @Mock SimulationObserver mockObserver2;

    @BeforeAll
    static void init() {
        SimulationConfig.initialize(10, 50, 20, 30, 100, 15, 25, 5, 2, 10);
    }

    @Test
    void testBoardNotifiesObservers() {
        Board board = new Board();

        Tile[][] dummyTable = new Tile[board.width][board.height];
        for(int x=0; x<board.width; x++) {
            for(int y=0; y<board.height; y++) {
                dummyTable[x][y] = new Tile(0, x, y);
            }
        }
        board.setBoardTable(dummyTable);

        board.addObserver(mockObserver1);
        board.addObserver(mockObserver2);

        board.tick();

        verify(mockObserver1, times(1)).onSimulationTick(board);
        verify(mockObserver2, times(1)).onSimulationTick(board);
    }
}