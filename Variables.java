public class Variables {
    /**
     * Input:
     * --receptor arg        rigid part of the receptor (PDBQT)
     * --flex arg            flexible side chains, if any (PDBQT)
     * --ligand arg          ligand (PDBQT)
     * <p>
     * Search space (required):
     * --center_x arg        X coordinate of the center
     * --center_y arg        Y coordinate of the center
     * --center_z arg        Z coordinate of the center
     * --size_x arg          size in the X dimension (Angstroms)
     * --size_y arg          size in the Y dimension (Angstroms)
     * --size_z arg          size in the Z dimension (Angstroms)
     * <p>
     * Output (optional):
     * --out arg             output models (PDBQT), the default is chosen based on
     * the ligand file name
     * --log arg             optionally, write log file
     * <p>
     * Misc (optional):
     * --cpu arg                 the number of CPUs to use (the default is to try to
     * detect the number of CPUs or, failing that, use 1)
     * --seed arg                explicit random seed
     * --exhaustiveness arg (=8) exhaustiveness of the global search (roughly
     * proportional to time): 1+
     * --num_modes arg (=9)      maximum number of binding modes to generate
     * --energy_range arg (=3)   maximum energy difference between the best binding
     * mode and the worst one displayed (kcal/mol)
     * <p>
     * Configuration file (optional):
     * --config arg          the above options can be put here
     * <p>
     * Information (optional):
     * --help                display usage summary
     * --help_advanced       display usage summary with advanced options
     * --version             display program version
     */

    // vina.exe 所在绝对路径
    public static String vinaPath = "Z:\\Workspace\\vina\\Vina\\vina.exe";

    // 所有 ligand 所在文件夹路径
    public static String ligandPath = "Z:\\Workspace\\vina\\PDBQT";

    // receptor 路径
    public static String receptorPath = "Z:\\Workspace\\vina\\5c1m_noligand.pdbqt";

    public static String center_x = "1.192";

    public static String center_y = "17.166";

    public static String center_z = "-59.109";

    public static String size_x = "15";

    public static String size_y = "15";

    public static String size_z = "15";

    public static String num_modes = "10";

    public static String energy_range = "3";

    private Variables() {
    }
}

