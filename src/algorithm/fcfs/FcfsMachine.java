package algorithm.fcfs;

import machine.computing.SimpleHistoryMachine;
import machine.computing.unit.HistoryUnit;
import machine.computing.unit.cpu.SimpleHistoryCpu;
import machine.computing.unit.io.SimpleHistoryIo;
import machine.computing.unit.Unit;

import java.util.stream.IntStream;

public class FcfsMachine extends SimpleHistoryMachine {
    public FcfsMachine() {
        super(IntStream.range(0, 4).mapToObj(id -> (HistoryUnit) new SimpleHistoryCpu(id)).toList(),
                IntStream.range(0, 2).mapToObj(id -> (HistoryUnit) new SimpleHistoryIo(id)).toList());
    }
}
