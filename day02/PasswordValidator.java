
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Velmurugan Muthu
 */
public class PasswordValidator {


  public static void main(final String[] args) throws IOException {

    File file = new File(
        "day2.txt");
    HashMap<Integer, List<String>> passwordMap = new HashMap<>();
    int index = 0;

    String line;

    try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {

      while ((line = reader.readLine()) != null) {
        List<String> passwordList = new ArrayList<>();
        String[] split = line.split(" ");
        passwordList.add(split[0]);
        passwordList.add(split[1].replace(":", ""));
        passwordList.add(split[2]);
        passwordMap.put(index, passwordList);
        index++;
      }
    }

    long count = passwordMap.entrySet().parallelStream().filter(e -> findCorrectPasswordCount1(e.getValue())).count();
    System.out.println("Count1:" + count);

    long count2 = passwordMap.entrySet().parallelStream().filter(e -> findCorrectPasswordCount2(e.getValue())).count();
    System.out.println("Count2:" + count2);

  }

  /**
   * @param value
   * @return
   */
  private static boolean findCorrectPasswordCount1(final List<String> values) {

    int lowCount = Integer.parseInt(values.get(0).split("-")[0]);
    int highCount = Integer.parseInt(values.get(0).split("-")[1]);
    char key = values.get(1).charAt(0);
    String passwordString = values.get(2);

    long count = passwordString.chars().filter(ch -> ch == key).count();

    return ((count >= lowCount) && (count <= highCount)) ? true : false;
  }

  /**
   * @param value
   * @return
   */
  private static boolean findCorrectPasswordCount2(final List<String> values) {

    int firstPos = Integer.parseInt(values.get(0).split("-")[0]);
    int secondPos = Integer.parseInt(values.get(0).split("-")[1]);
    char key = values.get(1).charAt(0);
    String passwordString = values.get(2);

    if ((passwordString.charAt(firstPos - 1) == key) && (passwordString.charAt(secondPos - 1) == key)) {
      return false;
    }
    else if ((passwordString.charAt(firstPos - 1) == key) || (passwordString.charAt(secondPos - 1) == key)) {
      return true;
    }

    return false;
  }

}
