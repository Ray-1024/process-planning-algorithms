package algorithm.rr;

import machine.computing.SimpleHistoryMachine;
import machine.computing.unit.HistoryUnit;
import machine.computing.unit.io.SimpleHistoryIo;

import java.util.stream.IntStream;

public class RoundRobinMachine extends SimpleHistoryMachine {
    protected RoundRobinMachine(int maximumTicks) {
        super(IntStream.range(0, 4).mapToObj(id -> (HistoryUnit) new RoundRobinCpu(id, maximumTicks)).toList(),
                IntStream.range(0, 2).mapToObj(id -> (HistoryUnit) new SimpleHistoryIo(id)).toList());
    }
}
