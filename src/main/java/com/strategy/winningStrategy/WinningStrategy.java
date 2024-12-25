package com.strategy.winningStrategy;

import com.enums.WinningStrategyEnum;
import com.Interfaces.IWinningStrategy;

public class WinningStrategy {
    public static IWinningStrategy getWinningStrategy(WinningStrategyEnum winningStrategyEnum, int size) {
        if(winningStrategyEnum == WinningStrategyEnum.ORDER_ONE_STRATEGY){
            return new OrderOneWinningStrategy(size);
        }
        return null;
    }
}
