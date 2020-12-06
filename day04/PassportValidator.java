
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author V-Dev144
 */
public class PassportValidator {

  static int index = 0;

  public static void main(final String[] args) {

    try {
      HashMap<Integer, List<String>> passportMap = readFile();
      countValidPassport(passportMap);
      countValidPassport1(passportMap);


    }
    catch (IOException e) {
      Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
    }
  }

  /**
   * @param passportMap
   */
  private static void countValidPassport1(final HashMap<Integer, List<String>> passportMap) {

    long count = passportMap.entrySet().stream().filter(e -> findValidPassportCount1(e.getValue())).count();
    System.out.println("Valid Passport Count:" + count);
  }

  /**
   * @param trajectoryMap
   */
  private static void countValidPassport(final HashMap<Integer, List<String>> passportMap) {
    long count = passportMap.entrySet().stream().filter(e -> findValidPassportCount(e.getValue())).count();
    System.out.println("Valid Passport Count:" + count);
  }

  /**
   * @param value
   * @return
   */
  private static boolean findValidPassportCount(final List<String> passportValues) {
    String[] matches = { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" };
    String passportparams = "";
    for (String str : passportValues) {
      passportparams += str;
    }

    return Arrays.stream(matches).allMatch(passportparams::contains);
  }

  /**
   * @param value
   * @return
   */
  private static boolean findValidPassportCount1(final List<String> passportValues) {
    String[] matches = { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" };
    String passportparams = "";
    for (String str : passportValues) {
      passportparams += str;
    }
    String passportparams1 = passportparams.replace(",", "");

    HashMap<String, String> map = (HashMap<String, String>) Arrays.asList(passportparams1.split(" ")).stream()
        .map(s -> s.split(":")).collect(Collectors.toMap(e -> e[0], e -> e[1]));

    if (Arrays.stream(matches).allMatch(passportparams::contains)) {

      return checkValidation(map);
    }

    return false;
  }

  /**
   * @param map
   */
  private static boolean checkValidation(final HashMap<String, String> map) {


    boolean valid =
        ((Integer.parseInt(map.get("byr")) >= 1920) && (Integer.parseInt(map.get("byr")) <= 2002)) ? true : false;

    boolean valid1 =
        ((Integer.parseInt(map.get("iyr")) >= 2010) && (Integer.parseInt(map.get("iyr")) <= 2020)) ? true : false;


    boolean valid2 =
        ((Integer.parseInt(map.get("eyr")) >= 2020) && (Integer.parseInt(map.get("eyr")) <= 2030)) ? true : false;

    boolean valid3 = ((map.get("pid").length() == 9) && map.get("pid").matches("[0-9]*")) ? true : false;


    boolean valid4 = false;
    if (map.get("hgt").contains("cm")) {
      valid4 = ((Integer.parseInt(map.get("hgt").split("cm")[0]) >= 150) &&
          (Integer.parseInt(map.get("hgt").split("cm")[0]) <= 193)) ? true : false;
    }
    else {
      valid4 = ((Integer.parseInt(map.get("hgt").split("in")[0]) >= 59) &&
          (Integer.parseInt(map.get("hgt").split("in")[0]) <= 76)) ? true : false;
    }


    boolean valid5 = false;
    String hairclr = map.get("hcl");
    if (hairclr.startsWith("#") && (hairclr.length() == 7)) {

      String alphaRegex = "[a-f0-9]*";

      valid5 = hairclr.split("#")[1].matches(alphaRegex);
    }


    String[] matches = { "amb", "blu", "brn", "gry", "grn", "hzl", "oth" };

    boolean valid6 = Arrays.stream(matches).anyMatch(map.get("ecl")::contains);

    return valid && valid1 && valid2 && valid3 && valid4 && valid5 && valid6;

  }

  /**
   * @return
   * @throws IOException
   */
  private static HashMap<Integer, List<String>> readFile() throws IOException {
    File file =
        new File("day4.txt");

    HashMap<Integer, List<String>> passportMap = new HashMap<>();

    String line;

    int index = 1;

    try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
      List<String> passportList = new ArrayList<>();
      while ((line = reader.readLine()) != null) {

        if (line.trim().isEmpty()) {
          passportMap.put(index, passportList);
          index++;
          passportList = new ArrayList<>();
          continue;
        }

        passportList.add(line + " ");
      }
    }
    return passportMap;
  }

}
