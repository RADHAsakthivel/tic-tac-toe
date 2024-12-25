package com.strategy.traceGameStrategy;

import com.enums.TraceGameStrategyEnum;
import com.Interfaces.ITraceGame;

public class TraceGameStrategy {
    public  static ITraceGame getTraceGameStrategy(TraceGameStrategyEnum traceGameStrategyEnum){
        if(TraceGameStrategyEnum.ORDER_ONE_TRACE_GAME == traceGameStrategyEnum){
            return  new OrderOneTraceGameStrategy();
        }
        return null;
    }
}
