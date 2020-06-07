import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author bipy (github.com/bipy)
 */


public class Main {
    // 线程数设置
    private static final int THREAD_COUNT = 4;

    private static Runtime runtime = Runtime.getRuntime();
    private static int sum = 1;
    private static AtomicInteger count = new AtomicInteger(0);
    private static ArrayList<File> ligandList = new ArrayList<>();

    private static String[] command = new String[]{
            Variables.vinaPath,
            "--receptor", Variables.receptorPath,
            "--ligand", "",
            "--log", "",
            "--center_x", Variables.center_x,
            "--center_y", Variables.center_y,
            "--center_z", Variables.center_z,
            "--size_x", Variables.size_x,
            "--size_y", Variables.size_y,
            "--size_z", Variables.size_z,
            "--num_modes", Variables.num_modes,
            "--energy_range", Variables.energy_range,
            "--cpu", "1"
    };


    public static void main(String[] args) {
        File inputFile = new File(Variables.ligandPath);
        if (!inputFile.exists() || !inputFile.isDirectory()) {
            System.out.println("CONFIG ERROR");
            System.exit(1);
        }
        for (File ligandFile : Objects.requireNonNull(inputFile.listFiles())) {
            if (ligandFile.getName().matches(".*[.](pdbqt)$")) {
                ligandList.add(ligandFile);
            }
        }
        sum = ligandList.size();
        File[] ligandFiles = ligandList.toArray(new File[sum]);
        if (THREAD_COUNT >= 1) {
            System.out.println("======= Multi Thread Mode =======");
            ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);
            int batch = ligandFiles.length / THREAD_COUNT;
            for (int i = 0; i < THREAD_COUNT - 1; i++) {
                int startPos = i * batch;
                int endPos = (i + 1) * batch;
                service.submit(new VinaTask(Arrays.copyOfRange(ligandFiles, startPos, endPos), command));
            }
            // 最后一组处理剩下的所有
            service.submit(new VinaTask(Arrays.copyOfRange(ligandFiles,
                    (THREAD_COUNT - 1) * batch, ligandFiles.length), command));
            service.shutdown();
            try {
                service.awaitTermination(72, TimeUnit.HOURS);
            } catch (InterruptedException e) {
                System.out.println("运行超时");
                System.exit(2);
            }
        } else {
            System.out.println("THREAD_COUNT CANNOT BE LESS THAN 1");
            System.exit(3);
        }
        outputCSV(Variables.ligandPath);
    }


    public static void process(String[] args) {
        String curLigand = args[4];
        try {
            Process p = runtime.exec(args);
            while (p.isAlive()) ;
            System.out.println(String.format("(%d/%d) %s",
                    count.addAndGet(1), sum, curLigand));

        } catch (Exception e) {
            System.out.println(curLigand + " fail!");
        }
    }

    public static void outputCSV(String outputPath) {
        ArrayList<File> logList = new ArrayList<>();
        for (File curFile : Objects.requireNonNull(new File(Variables.ligandPath).listFiles())) {
            if (curFile.getName().matches(".*\\.log$")) {
                logList.add(curFile);
            }
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath + "/output.csv"));
            bw.write("Ligand,affinity(kcal/mol),mode,RMSD lower bound,RMSD upper bound\n");
            for (File logFile : logList) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile)));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] lineData = line.split("[ ]+");
                    if (lineData.length == 5 && lineData[1].matches("^[\\d]+$")) {
                        bw.write(String.format("%s,%s,%s,%s,%s\n",
                                logFile.getName().replaceAll("[.][^.]+$", ""),
                                lineData[2], lineData[1], lineData[3], lineData[4]));
                    }
                }
                br.close();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(4);
        }
    }
}




