import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.IntStream;

/*
 * Copyright (c) Robert Bosch GmbH. All rights reserved.
 */

/**
 * @author VMU2COB
 */
public class SumPairFinder {

  /**
   * @param args - arguments
   * @throws IOException - File IO Exception
   */
  public static void main(final String[] args) throws IOException {

    File file = new File("test.txt");
    int[] input = new int[200];
    int sum = 2020;
    int index = 0;
    String line;

    try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {

      while ((line = reader.readLine()) != null) {
        input[index] = Integer.parseInt(line);
        index++;
      }
    }

    IntStream.range(0, input.length).parallel().forEach(i -> IntStream.range(0, input.length)
        .filter(j -> (i != j) && ((input[i] + input[j]) == sum)).forEach(j -> addPairs(input[i], input[j])));

    IntStream.range(0, input.length).parallel()
        .forEach(i -> IntStream.range(0, input.length).filter(j -> (i != j))
            .forEach(j -> IntStream.range(0, input.length)
                .filter(k -> (k != i) && (k != j) && ((input[i] + input[j] + input[k]) == sum))
                .forEach(k -> addPairs(input[i], input[j], input[k]))));

  }

  /**
   * @param i - integer
   * @param j - integer
   * @param k - integer
   */
  private static void addPairs(final int i, final int j, final int k) {
    System.out.println("Pair: {" + i + "," + j + "," + k + "}");
    System.out.println("Multiplication:" + (i * j * k));
  }

  /**
   * @param integer
   * @param integer2
   */
  private static void addPairs(final Integer integer, final Integer integer2) {
    System.out.println("Pair: {" + integer + "," + integer2 + "}");
    System.out.println("Multiplication:" + (integer * integer2));
  }
}
