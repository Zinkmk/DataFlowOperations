package dataEngineering;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) throws IOException, CsvValidationException {
    CsvParser csvP = new CsvParser("src/Data/bookstore_report2.csv");
    csvP.printCsv();


    // JSON for later use in our project
    /*load the JSON
    1. Create instance of GSON
    2. Create a JsonReader object using FileReader
    3. Array of class instances of AuthorParser, assign data from our JsonReader
    4. foreach loop to check data
     */
    Gson gson = new Gson();
    JsonReader jsread = new JsonReader(new FileReader("src/Data/authors.json"));
        AuthorParser[] authors = gson.fromJson(jsread, AuthorParser[].class);

    for (var element : authors) {
      System.out.println(element.getName());
    }
  }
}
