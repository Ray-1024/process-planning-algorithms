package machine.process.stage;

public class CPU extends AbstractStage {

    public CPU(int ticks) {
        super(ticks);
    }

    @Override
    public int getId() {
        throw new UnsupportedOperationException("CPU operation hasn't ID");
    }
}
