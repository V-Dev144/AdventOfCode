
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * @author V-Dev144
 */
public class TrajectoryFinder {


  int treeCounts = 0;
  long treeCountMultiplication = 1;

  /**
   * @param args - Arguments
   * @throws IOException - IO Exception
   */
  public static void main(final String[] args) throws IOException {

    TrajectoryFinder obj = new TrajectoryFinder();
    HashMap<Integer, String> trajectoryMap = obj.readFile();


    obj.treeCounts = obj.getTreeCounts(trajectoryMap, 1, 1);
    System.out.println("TreeCounts Right 1 Down 1:" + obj.treeCounts);

    obj.treeCountMultiplication = obj.treeCountMultiplication * obj.treeCounts;

    obj.treeCounts = obj.getTreeCounts(trajectoryMap, 3, 1);
    System.out.println("TreeCounts Right 3 Down 1:" + obj.treeCounts);

    obj.treeCountMultiplication = obj.treeCountMultiplication * obj.treeCounts;

    obj.treeCounts = obj.getTreeCounts(trajectoryMap, 5, 1);
    System.out.println("TreeCounts Right 5 Down 1:" + obj.treeCounts);

    obj.treeCountMultiplication = obj.treeCountMultiplication * obj.treeCounts;

    obj.treeCounts = obj.getTreeCounts(trajectoryMap, 7, 1);
    System.out.println("TreeCounts Right 7 Down 1:" + obj.treeCounts);

    obj.treeCountMultiplication = obj.treeCountMultiplication * obj.treeCounts;

    obj.treeCounts = obj.getTreeCounts(trajectoryMap, 1, 2);
    System.out.println("TreeCounts Right 1 Down 2:" + obj.treeCounts);

    obj.treeCountMultiplication = obj.treeCountMultiplication * obj.treeCounts;

    System.out.println("Multiplication Value:" + obj.treeCountMultiplication);


  }

  /**
   * @return - HashMap
   * @throws IOException
   */
  private HashMap<Integer, String> readFile() throws IOException {
    File file =
        new File("D:/01_Workplace/02_WorkingSet/workplace/LEA_Core_Latest/TestProject/src/com/bosch/aoc/day3.txt");

    HashMap<Integer, String> trajectoryMap = new HashMap<>();

    String line;

    int index = 1;

    try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {

      while ((line = reader.readLine()) != null) {
        trajectoryMap.put(index, line);
        index++;
      }
    }
    return trajectoryMap;
  }

  /**
   * @param trajectoryMap
   * @param treeCount
   * @param right
   * @param down
   */
  private int getTreeCounts(final HashMap<Integer, String> trajectoryMap, final int right, final int down) {

    int pass = 1;
    int treeCount = 0;
    String str;

    getCorrectedMap(trajectoryMap, down);

    for (Map.Entry<Integer, String> entry : trajectoryMap.entrySet()) {

      str = getCorrectedStringByLength(pass, entry);

      if ((entry.getKey() != 1) && (str.charAt(pass - 1) == '#')) {
        treeCount++;
      }
      pass = pass + right;
    }

    return treeCount;
  }

  /**
   * @param trajectoryMap
   * @param down
   */
  private void getCorrectedMap(final HashMap<Integer, String> trajectoryMap, final int down) {
    if (down == 2) {
      trajectoryMap.entrySet().removeIf(e -> (e.getKey() % 2) == 0);
    }
  }

  /**
   * @param pass
   * @param entry
   * @return
   */
  private String getCorrectedStringByLength(final int pass, final Map.Entry<Integer, String> entry) {
    StringBuilder str = new StringBuilder();
    str = str.append(entry.getValue());
    while (str.length() < pass) {
      str = str.append(entry.getValue());
    }
    return str.toString();
  }

}
