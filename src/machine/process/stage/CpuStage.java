package machine.process.stage;

public class CpuStage extends AbstractStage {

    public CpuStage(int ticks) {
        super(ticks);
    }

    @Override
    public int getId() {
        throw new UnsupportedOperationException("CPU operation hasn't ID");
    }
}
