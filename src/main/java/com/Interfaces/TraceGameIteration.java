package com.Interfaces;

import java.util.List;

@FunctionalInterface
public interface TraceGameIteration {
    public void doAction(List<Object> input, int index);
}
