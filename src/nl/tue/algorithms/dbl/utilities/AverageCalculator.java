//MOMOTOR_MERGER_IGNORE_FILE
package nl.tue.algorithms.dbl.utilities;

import nl.tue.algorithms.dbl.common.Pack;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.tue.algorithms.dbl.algorithm.*;
import nl.tue.algorithms.dbl.common.ValidCheck;

/**
 * Capable of running many tests after each other (files to be provided in a 
 * specific folder), and outputs things such as runtime, coverage, etc. in a
 * separate file.
 * Also provides an R-file for the same data.
 * 
 * @author T.M. Verberk (1016472)
 * @author E.M.A. Arts (1004076)
 */
public class AverageCalculator{
    //directory in which to look for input files (I.e. the ones to execute)
    private static final File TESTCASES_DIR = new File("res/averageCalculator/input");
    //Send results to this directory
    private static final File RESULTS_DIR = new File("res/averageCalculator/output");
    //filename of the dataset (as a Comma-separated-value (csv) file). Can as such, amongst other Software, be opened in Microsoft Excel)
    private static final String RESULTS_FILENAME = "results.csv";
    //filename of the R-file to create
    private static final String R_FILENAME = "results.r";
    
    //Algorithm to test
    private static final Class<? extends Algorithm> TEST_ALGORITHM = Algorithm.class;
    
    //hardcode allowed testFile extensions
    private static final Map<String, Boolean> ALLOWED_FILE_EXTENSIONS;
    static {
        Map<String, Boolean> map = new HashMap<>();
        map.put(".txt", true);
        ALLOWED_FILE_EXTENSIONS = Collections.unmodifiableMap(map);
    } 
    
    /**
     * Gets the valid testcase files, and compiles them into a single list
     * @return A list of valid testcase Files.
     */
    private static List<File> getValidFiles() {
        List<File> validFiles = new ArrayList<>();
        //make the directories (if they do not exist yet)
        TESTCASES_DIR.mkdirs();
        
        System.out.println("Files that will be tested: ");
        
        File[] listOfFiles = TESTCASES_DIR.listFiles();
        
        //only look in the direct directory (ignoring sub-directories in this
        //directory)
        for (File file : listOfFiles) {
            if (isValidFile(file)) {
                System.out.println("- " + file.getName());
                validFiles.add(file);
            }
        }
        return validFiles;
    }
    
    /**
     * Checks whether a given file is a 'valid' file. A 'valid' file in this case
     * means that it is NOT a directory and has an allowed file extension
     * @param file The file to check
     * @pre file != null
     * @return true <==> the file is NOT a directory && the file has an allowed
     *                   file extension
     */
    private static boolean isValidFile(File file) {
        //directories are invalid
        if (file.isDirectory()) {
            return false;
        }
        
        String fileName = file.getName();
        int fileExtensionPos = fileName.lastIndexOf(".");

        if (fileExtensionPos >= 0) {
            String fileExtension = fileName.substring(fileExtensionPos);
            //exclude non-allowed file extensions
            if (!ALLOWED_FILE_EXTENSIONS.containsKey(fileExtension)
                    || !ALLOWED_FILE_EXTENSIONS.get(fileExtension)) {
                //file is not a .txt file
                return false;
            }
        }
        //accept all other files
        return true;
    }

    /**
     * Executes testcases from a given list of files, and outputs the results 
     * from those to a new file.
     * @param files List of testcases
     * @pre files != null
     * @return The output file containing the results of the input files.
     */
    public static File executeTestCases(List<File> files){     
        //make the directories first (if they do not exist yet)
        RESULTS_DIR.mkdirs();
        
        try (
            BufferedWriter fileWriter =
                new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(RESULTS_DIR + "/" + RESULTS_FILENAME), "UTF-8"));
        ) {
            //Print general info:
            fileWriter.write("Results for " + TEST_ALGORITHM.getSimpleName() + " (" + files.size() + " tests) :");
            fileWriter.newLine();
            fileWriter.write("ReadingRuntime (ns); SolverRuntime (ns); TotalRuntime (ns); NumberOfRectangles; ContainerWidth (int); ContainerHeight (int); ContainerArea (int); Coverage (%);");
            fileWriter.newLine();
            
            System.out.println("Starting to read files (this may take a while if there are many files)...");
            int n = files.size();
            int i = 0;
            
            //read each testcase
            for (File file : files)
            {
                System.out.println("- " + file.getName() + " (file " + (i+1) + " out of " + n + ")");
                try (InputStream fileIn = new FileInputStream(file))
                {           
                    //keep track of runtime (in ns)
                    long readerStartTime = System.nanoTime();
                    //create a new packingSolver to solve the testcase
                    PackingSolver solver = new PackingSolver(fileIn, TEST_ALGORITHM);
                    
                    solver.readRectangles();
                    
                    long solverStartTime = System.nanoTime();
                    solver.solve();
                    
                    long endTime = System.nanoTime();
                    
                    //Determine runtimes (in seconds)
                    long readingRuntime = solverStartTime - readerStartTime;
                    long solverRuntime = endTime - solverStartTime;
                    long totalRuntime = endTime - readerStartTime;
                    
                    fileWriter.write(readingRuntime + ";" + solverRuntime + ";" + totalRuntime + ";");
                    
                    //Determine Container area, coverage, etc.
                    Algorithm algo = solver.getAlgorithm();
                    Pack pack = algo.getPack();
                    int containerWidth = algo.getContainerWidth();
                    int containerHeight = algo.getContainerHeight();
                    int containerArea = algo.getContainerArea();
                    long coverage = pack.getCoveragePercentage();
                    
                    fileWriter.write(pack.getNumberOfRectangles() + ";" + containerWidth + ";" + containerHeight + ";" + containerArea + ";" + coverage + ";");
                    fileWriter.newLine();     
                }
                i++;
            }   
            
            System.out.println("Results for " + files.size() + " tests were outputed in " + RESULTS_DIR.getAbsolutePath());
            
            return new File(RESULTS_DIR + "/" + RESULTS_FILENAME);
        } catch (IOException e){
            System.err.println("Could not write file");
        }
        return null;
    }
    
    /**
     * Creates a new R-file with outputFile as a dataset.
     * @param outputFile file to create the dataset from in R
     * @pre outputFile != null
     */
    private static void setupRFile(File outputFile) throws IOException {
        //safety check
        if (outputFile == null) {
            return;
        }
        
        //create a new R-file
        try (
            BufferedWriter fileWriter =
                new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(RESULTS_DIR + "/" + R_FILENAME), "UTF-8"));
        ) {
            String outputFilename = outputFile.getAbsolutePath().replace("\\", "/");
            
            //and print it all
            fileWriter.write("data <-read.table(\"" + outputFilename + "\",sep=\";\",skip=\"2\")");
            fileWriter.newLine();
            fileWriter.write("readingRuntime <- data$V1");
            fileWriter.newLine();
            fileWriter.write("solverRuntime <- data$V2");
            fileWriter.newLine();
            fileWriter.write("totalRuntime <- data$V3");
            fileWriter.newLine();
            fileWriter.write("n <- data$V4");
            fileWriter.newLine();
            fileWriter.write("containerWidth <- data$V5");
            fileWriter.newLine();
            fileWriter.write("containerHeight <- data$V6");
            fileWriter.newLine();
            fileWriter.write("containerArea <- data$V7");
            fileWriter.newLine();
            fileWriter.write("coverage <- data$V8");
            fileWriter.newLine();
            fileWriter.newLine();
            
            //output mean of the runtime (as an example)
            fileWriter.write("mean(totalRuntime)/1000000000");
            fileWriter.newLine();
            
            //other stuff can be calculated on the spot in R itself
        }
    }

    /** Classic main method */
    public static void main(String [ ] args) throws IOException {
        //ensure that debug mode is disabled (Runtime (measured in ns) depends on this value!)
        if (ValidCheck.DEBUG_ENABLED) {
            System.err.println("Please disable Debug Mode first! (See DEBUG_ENABLED in ValidCheck.java)");
            System.err.println(AverageCalculator.class.getSimpleName() + " aborted");
            System.exit(0);
        } 
        //get the input files
        List<File> testCases = getValidFiles();
        
        //execute each of the files, and write their outputs to another file
        File outputFile = executeTestCases(testCases);
        
        //also setup an R file for easy calculations
        setupRFile(outputFile);
    }
}

