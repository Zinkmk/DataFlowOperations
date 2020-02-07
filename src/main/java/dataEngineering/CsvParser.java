package dataEngineering;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

  private List fileRows = new ArrayList();
  /* List holds all the fields from the file because it's a CSV of strings, we can make it
  a string but it's common to have to handle more than just strings so we either use
  a Class or Blank Object type.
   */

  public CsvParser(String infile) throws IOException, CsvValidationException {
    /** CsvParser - Reads csv Files using OpenCSV
     * On Load, check if file exists then Load it into fileRows
     * @param infile the file to be opened with path info
     */
    if (checkFile(infile)) {
      readCsv(infile);
    }
  }

  protected void readCsv(String csvinfile) throws IOException, CsvValidationException {
    /** readCSV: Read CSV file and load our fileRows list
     * @param csvinfile CSV file with path info for loading
     */

    //open a file & input stream for use with CSVReader (to creater a reader object
    FileInputStream csvStream = new FileInputStream(csvinfile);
    InputStreamReader inputStream = new InputStreamReader(csvStream, StandardCharsets.UTF_8);
    CSVReader reader = new CSVReader(inputStream);

    // Object type, it will be too (not String[])
    // Making it a String[] will allow it to cast later
    //Read the file and load each line into our List
    String[] nextLine;
    while ((nextLine = reader.readNext()) != null) {
      fileRows.add(nextLine);
    }
    //close reader
    reader.close();
  }

  protected void printCsv() {
    /** printCsv - Printout the Csv */
    for (Object row : fileRows) {
      /*
      So fileRows will be an object type
      after getting each row, we will need to "cast" row to be a string array (String[])
       */
      for (String fields : (String[]) row) {
        System.out.println(fields + ", ");
      }
      System.out.println("\b\b\n--------");
    }
  }

  private boolean checkFile(String csvfile) {
    /** checkFile - checks to ensure the file exists
     * @return false on file not found true on found
     */
    if (!Files.exists(Paths.get(csvfile))) {
      System.out.println("file doesn't exist");
      return false;
      // may change this to throw an exception
    }
    return true;
  }
}
