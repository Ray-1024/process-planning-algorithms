package algorithm.rr;

import machine.computing.unit.cpu.SimpleHistoryCpu;

public class RoundRobinCpu extends SimpleHistoryCpu {

    private int currentTick;
    private final int maximumTicks;

    public RoundRobinCpu(int id, int maximumTicks) {
        super(id);
        this.maximumTicks = maximumTicks;
    }
}
