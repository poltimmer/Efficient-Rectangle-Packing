//MOMOTOR_MERGER_IGNORE_FILE
package nl.tue.algorithms.dbl.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import nl.tue.algorithms.dbl.common.ValidCheck;

/**
 * This class is never used in the Algorithm code, and is only used to submit
 * to Momotor. It automatically comments out package statements and
 * package-specific imports.
 * It places the resulting file(s) in res/momoter (or creates the directory if it
 * not yet exists). (If there are already files in that folder, they are replaced)
 * 
 * Files starting with MOMOTER_MERGER_IGNORE_FILE are ignored (like this file).
 * 
 * The resulting files can be zipped and submitted to Momotor.
 * 
 * 
 * @author E.M.A. Arts (1004076)
 * @since 5 MAY 2018
 */
public class MomotorMerger {
    //files starting with this string are ignored. Empty lines are ignored when
    //considering the first line
    private static final String IGNORE_FILE_HEADER = "//MOMOTOR_MERGER_IGNORE_FILE";
    
    //'upload' directory
    private static final File MOMOTOR_DIR = new File("res/momotor");
    
    //hardcode allowed file extensions. (.java only as of now)
    private static final Map<String, Boolean> ALLOWED_FILE_EXTENSIONS;
    static {
        Map<String, Boolean> map = new HashMap<>();
        map.put(".java", true);
        ALLOWED_FILE_EXTENSIONS = Collections.unmodifiableMap(map);
    }    
    
    /**
     * Recursively gets the files from the file system that should be merged.
     * @param files An array of files to be merged
     * @pre files != null
     * @post    (\forall file; validFiles.contains(file); shouldMergeFile(file)) &&
     *          \result == validFiles
     * @return The files that need to be merged
     */
    private static List<File> getFilesToMerge(File[] files) {
        List<File> validFiles = new ArrayList<>();
        
        for (File file : files) {
            //ignore directories
            if (file.isDirectory()) {
                //System.out.println("Directory: " + file.getName());
                validFiles.addAll(getFilesToMerge(file.listFiles()));
            } else if (shouldMergeFile(file)){
                //is a valid file
                //System.out.println("valid Java file: " + file.getName());
                validFiles.add(file);
            } else {
                //System.out.println("invalid file : " + file.getName());
            }
        }
        return validFiles;
    }
    
    /**
     * Copies, comments out package statements and non-java imports, and places
     * the file in MOMOTOR_DIR
     * 
     * @param file The file to copy
     * @pre file != null
     * @post \exists(File f; f is a copy of file && f does not contain
     *                  package-statements && f does not contain non-java imports;)
     */
    private static void mergeFile(File file) {
        try (
                InputStream in = new FileInputStream(file);
                BufferedReader fileReader =
                        new BufferedReader(new InputStreamReader(in, "UTF-8"));
                BufferedWriter fileWriter =
                        new BufferedWriter(new OutputStreamWriter(
                                new FileOutputStream(MOMOTOR_DIR + "/" + file.getName()), "UTF-8"));
        ) {
            //while there is something to read
            while (fileReader.ready()) {
                // start reading
                String line = fileReader.readLine();
                
                //copy the file, but without package and non-java imports
                if (line.startsWith("package") ||
                        (line.startsWith("import") && !line.startsWith("import java"))) {
                    //Line is a package statement or non-java native import statement
                    //These need to be commented out
                    fileWriter.write("//");
                }
                fileWriter.write(line);
                fileWriter.newLine();
            }
        } catch (FileNotFoundException e) {
            //hide error messages
            System.err.println("Could not find file");
        } catch (IOException e) {
            System.err.println("Could not read file");
        }
    }
    
    /**
     * Checks whether the specified file should be merged.
     * @param file The file to check
     * 
     * @pre File != null
     * @return  true if the file extension is a valid extension as specified in
     *          ALLOWED_FILE_EXTENSIONS && if the file does not start with the
     *          IGNORE_FILE_HEADER string.
     *          false otherwise
     */
    private static boolean shouldMergeFile(File file) {
        String fileName = file.getName();
        int fileExtensionPos = fileName.lastIndexOf(".");

        if (fileExtensionPos >= 0) {
            String fileExtension = fileName.substring(fileExtensionPos);
            if (!ALLOWED_FILE_EXTENSIONS.containsKey(fileExtension)
                    || !ALLOWED_FILE_EXTENSIONS.get(fileExtension)) {
                //file is not a .java file
                return false;
            }
        }
        if (fileStartsWith(file, IGNORE_FILE_HEADER)) {
            //file starts with the ignore string
            return false;
        }
        return true;
    }
    
    /**
     * Checks to see if the file starts with a specified string
     * @param file The file to check
     * @param startString The string to check for
     * 
     * @pre file != null
     * @return  Whether the first line of the file starts with the specified
     *          string. Ignores empty lines.
     */
    private static boolean fileStartsWith(File file, String startString) {
        try (
                InputStream in = new FileInputStream(file);
                BufferedReader fileReader =
                        new BufferedReader(new InputStreamReader(in, "UTF-8"));
        ) {
            //while there is something to read
            while (fileReader.ready()) {
                // start reading
                String line = fileReader.readLine();
                
                if (line.startsWith(startString)) {
                    //file started with specified string
                    return true;
                } else if (!line.isEmpty()) {
                    //line starts with something else (ignoring empty lines)
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            //hide error messages
            System.err.println("Could not find file");
        } catch (IOException e) {
            System.err.println("Could not read file");
        }
        return false;
    }
    
    /**
     * Method that waits for a "Y", "N", "YES", or "NO" user-input in System.in
     * If invalid input is given, the program will ask for input again.
     * Input is Capital Insensitive
     * The program terminates if the input is "N" or "NO"
     * 
     * @post The program is terminated if user input is "N" or "NO" (capital insensitive)
     */
    private static void awaitConfirmation() {
        Scanner sc = new Scanner(System.in);
        
        confirm: while (true) {
            if (sc.hasNext()) {
                String b = sc.nextLine().toUpperCase();
                switch (b) {
                    case "Y": 
                    case "YES": break confirm;
                    case "N":
                    case "NO":
                            System.out.println(MomotorMerger.class.getSimpleName() + " aborted");
                            System.exit(0);
                    default:
                            System.out.println("confirm (Y/N)?");
                            System.out.print("> ");
                            break;
                }
            }
        }
    }
    
    /** Basic Java main method */
    public static void main(String [ ] args) {    
        //ensure that debug mode is disabled
        if (ValidCheck.DEBUG_ENABLED) {
            System.err.println("Please disable Debug Mode first! (See DEBUG_ENABLED in ValidCheck.java)");
            System.err.println(MomotorMerger.class.getSimpleName() + " aborted");
            System.exit(0);
        }        
        
        //make 'momotor' directory in res/ (if it does not yet exist)
        MOMOTOR_DIR.mkdirs();
        
        //get valid files
        File[] files = new File("src/").listFiles();
        List<File> filesToMerge = getFilesToMerge(files);
        
        System.out.println("The following files will be merged in " + MOMOTOR_DIR.getAbsolutePath() + " (" + filesToMerge.size() + " total): ");
        for (File file : filesToMerge) {
            System.out.format("- %-25s (%s)\n", file.getName(), file);
        }
        System.out.println("confirm (Y/N)?");
        System.out.print("> ");
        
        awaitConfirmation();
        System.out.println("Files confirmed... merging has begun...");
        
        for (File file : filesToMerge) {
            mergeFile(file);
        }
        
        System.out.println("Merging has been completed!\nThe files can be found in: " + MOMOTOR_DIR.getAbsolutePath());
    }
}
