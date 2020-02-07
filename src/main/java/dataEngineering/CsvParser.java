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
  /*  we can make List a string but it's common to have to handle more
  than just strings so we either use a Class or Blank Object type.
   */

  /**
   * CsvParser: reads csv files using @readCsv and OpenCSV
   * @param infile the file to be opened
   * @throws IOException
   * @throws CsvValidationException
   */
  public CsvParser(String infile) throws IOException, CsvValidationException {
    if (checkFile(infile)) {
      readCsv(infile); // next function
    }
  }


  /**
   * readCsv: read a file and load fileRows list
   * @param csvinfile CSV file with the path for loading
   * @throws IOException
   * @throws CsvValidationException
   */
  protected void readCsv(String csvinfile) throws IOException, CsvValidationException {
    //open a file & input stream for use with CSVReader (to create a reader object)
    FileInputStream csvStream = new FileInputStream(csvinfile);
    InputStreamReader inputStream = new InputStreamReader(csvStream, StandardCharsets.UTF_8);
    CSVReader reader = new CSVReader(inputStream);

    // Object type,
    // Making it a String[] will allow it to cast later
    //Read the file and load each line into our List
    String[] nextLine;
    while ((nextLine = reader.readNext()) != null) {
      fileRows.add(nextLine);
    }
    //close reader
    reader.close();
  }

  /**
   * prints the CSV
   */
  protected void printCsv() {
    for (Object row : fileRows) {
      /*
      fileRows will be an object type
      after getting each row, we will need to "cast" row to be a string array (String[])
       */
      for (String fields : (String[]) row) {
        System.out.println(fields + ", ");
      }
      System.out.println("\b\b\n--------");
    }
  }

  /**
   * checkFile: checks to see if there's actually a file
   * @param csvfile CSV file with the path for loading
   * @return false - not found // true - found
   */
  private boolean checkFile(String csvfile) {
    if (!Files.exists(Paths.get(csvfile))) {
      System.out.println("file doesn't exist");
      return false;
    }
    return true;
  }
}
