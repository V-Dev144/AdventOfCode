import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author V-Dev144
 */
public class SeatFinder {


  public static void main(final String[] args) throws IOException {
    List<String> seatList = readFile();
    findHighestSeatID(seatList);
  }

  /**
   * @param seatMap
   */
  private static void findHighestSeatID(final List<String> seatList) {


    int maxSeatId = 0;
    List<Integer> seatNumbers = new ArrayList<>();

    for (String string : seatList) {

      int firstSeat = 0;
      int lastSeat = 127;
      int firstColumn = 0;
      int lastColumn = 7;
      int seatID = 0;

      for (int i = 0; i < string.length(); i++) {

        if (string.charAt(i) == 'F') {
          lastSeat = ((lastSeat - firstSeat) / 2) + firstSeat;
        }
        else if (string.charAt(i) == 'B') {
          firstSeat = ((lastSeat - firstSeat) / 2) + 1 + firstSeat;
        }
        if (string.charAt(i) == 'L') {
          lastColumn = ((lastColumn - firstColumn) / 2) + firstColumn;
        }
        else if (string.charAt(i) == 'R') {
          firstColumn = ((lastColumn - firstColumn) / 2) + 1 + firstColumn;
        }

      }
      seatID = (firstSeat * 8) + firstColumn;
      seatNumbers.add(seatID);
      maxSeatId = (seatID > maxSeatId) ? seatID : maxSeatId;
    }

    Collections.sort(seatNumbers);

    getMissingNumbers(seatNumbers);

    System.out.println("Maximum Seat ID:" + maxSeatId);
  }

  /**
   * @param seatNumbers
   */
  private static void getMissingNumbers(final List<Integer> seatNumbers) {

    for (int i = 0; i < (seatNumbers.size() - 1); i++) {

      int next = seatNumbers.get(i + 1);
      int current = seatNumbers.get(i);
      if ((next - current) > 1) {
        System.out.println("Missing Value : " + (current + 1));
      }
    }


  }

  /**
   * @return
   * @throws IOException
   */
  private static List<String> readFile() throws IOException {
    File file =
        new File("day5.txt");

    List<String> seatList = new ArrayList<>();

    String line;

    try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {

      while ((line = reader.readLine()) != null) {

        seatList.add(line);


      }
    }
    return seatList;
  }

}
