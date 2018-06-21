package nl.tue.algorithms.dbl.utilities;

import nl.tue.algorithms.dbl.common.Pack;
import nl.tue.algorithms.dbl.common.ValidCheck;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class AverageCalculater{

    private static List<File> getTestCases() {
    List<File> validFiles = new ArrayList<>();
    File folder = new File("/res/allTestCases/testCases/folder/");
    File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getName());
                validFiles.add(file);
            }
    }
        return validFiles;
}


    float addedUpCoverage = 0;
    float Coverage = 0;
    int n=0;
    public void ReadTestCases(File file){
            try(
                InputStream in=new FileInputStream(file);
                BufferedReader fileReader=
                new BufferedReader(new InputStreamReader(in,"UTF-8"));
                BufferedWriter fileWriter=
                    new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("res/allTestCases/result/summary.txt")));
            ) {
                //while there is something to read
                while(fileReader.ready()){
                    PackingSolver solver=new PackingSolver(in);
                    solver.readRectangles();

                    Pack p = solver.getAlgorithm().getPack();
                    // calculate total result
                    //write the result in the file
                    fileWriter.write((int) p.getCoveragePercentage());
                    fileWriter.newLine();
                    n++;
                }
                Coverage = addedUpCoverage/n;
                fileWriter.write((int) Coverage);
                fileWriter.newLine();

            } catch (FileNotFoundException e){
                //hide error messages
                System.err.println("Could not find file");
            } catch (IOException e){
                System.err.println("Could not read file");
            }
        }


    public void main(String args[]) throws IOException {

        List<File> testCases = getTestCases();
        for (File file : testCases) {
            ReadTestCases(file);
        }


    }
}

