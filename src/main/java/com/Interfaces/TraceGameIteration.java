package com.Interfaces;

import com.Models.GameMove;

@FunctionalInterface
public interface TraceGameIteration {
    public void doAction(GameMove input, int index);
}
