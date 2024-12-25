package com.Interfaces;

import com.models.GameMove;

@FunctionalInterface
public interface TraceGameIteration {
    public void doAction(GameMove input, int index);
}
