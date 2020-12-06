
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author V-Dev144
 */
public class CustomsHandler {


  public static void main(final String[] args) throws IOException {

    HashMap<Integer, List<String>> customsAnsewerMap = readFile();

    getAnswersCount(customsAnsewerMap);
    getAnswersCount1(customsAnsewerMap);

  }

  /**
   * @param customsAnsewerMap
   */
  private static void getAnswersCount(final HashMap<Integer, List<String>> customsAnsewerMap) {
    long count = customsAnsewerMap.entrySet().stream().mapToInt(i -> findAnswersCount(i.getValue())).sum();
    System.out.println("Count1:" + count);

  }

  /**
   * @param customsAnsewerMap
   */
  private static void getAnswersCount1(final HashMap<Integer, List<String>> customsAnsewerMap) {
    long count = customsAnsewerMap.entrySet().stream().mapToInt(i -> findAnswersCount1(i.getValue())).sum();
    System.out.println("Count1:" + count);

  }

  /**
   * @param value
   * @return
   */
  private static int findAnswersCount(final List<String> value) {
    int count = 0;
    String str = "";

    for (String string : value) {
      str += string;
    }
    count += str.chars().distinct().count();
    return count;
  }

  /**
   * @param value
   * @return
   */
  private static int findAnswersCount1(final List<String> value) {

    String str = "";

    if (value.size() == 1) {
      return (int) value.get(0).chars().distinct().count();
    }

    str = value.get(0);
    String outputStr = "";

    for (int j = 1; j < value.size(); j++) {

      for (int i = 0; i < str.length(); i++) {
        if (value.get(j).indexOf(str.charAt(i)) >= 0) {
          outputStr += str.charAt(i);
        }
      }
      str = outputStr;
      outputStr = "";

    }

    return (int) str.chars().distinct().count();
  }

  /**
   * @return
   * @throws IOException
   */
  private static HashMap<Integer, List<String>> readFile() throws IOException {
    File file =
        new File("day6.txt");

    HashMap<Integer, List<String>> customsAnsewerMap = new HashMap<>();

    String line;

    int index = 1;

    try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
      List<String> list = new ArrayList<>();
      while ((line = reader.readLine()) != null) {

        if (line.trim().isEmpty()) {
          customsAnsewerMap.put(index, list);
          index++;
          list = new ArrayList<>();
          continue;
        }

        list.add(line);
      }
    }
    return customsAnsewerMap;
  }
}
