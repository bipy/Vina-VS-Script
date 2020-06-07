import java.io.File;

public class VinaTask implements Runnable {
    private File[] taskList;
    private String[] args;

    public VinaTask(File[] taskList, String[] args) {
        this.taskList = taskList;
        this.args = args;
    }

    @Override
    public void run() {
        for (File ligand : taskList) {
            args[4] = ligand.getPath();
            args[6] = args[4] + ".log";
            Main.process(args);
        }
    }
}
