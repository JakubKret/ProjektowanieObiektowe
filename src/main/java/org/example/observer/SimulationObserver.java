package org.example.observer;

import org.example.model.Board;

public interface SimulationObserver {
    void onSimulationTick(Board board);
}