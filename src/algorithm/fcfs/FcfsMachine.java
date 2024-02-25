package algorithm.fcfs;

import machine.computing.AbstractMachine;
import machine.computing.unit.Unit;

import java.util.stream.IntStream;

public class FcfsMachine extends AbstractMachine {
    public FcfsMachine() {
        super(IntStream.range(0, 4).mapToObj(FcfsCpu::new).map(cpu -> (Unit) cpu).toList(),
                IntStream.range(0, 2).mapToObj(FsfcIo::new).map(io -> (Unit) io).toList());
    }
}
