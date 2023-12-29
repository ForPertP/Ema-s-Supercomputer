import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'twoPluses' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING_ARRAY grid as parameter.
     */

    public static int twoPluses(List<String> grid) {
    // Write your code here
        int rows = grid.size();
        int cols = grid.get(0).length();

        List<List<Integer>> plusSizes = new ArrayList<>(rows);
        for (int i = 0; i < rows; ++i) {
            plusSizes.add(new ArrayList<>(cols));
            for (int j = 0; j < cols; ++j) {
                plusSizes.get(i).add(0);
            }
        }

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                int plusSize = -1;
                int minDist = Math.min(Math.min(i, rows - 1 - i), Math.min(j, cols - 1 - j));

                for (int k = 0; k <= minDist; ++k) {
                    if (grid.get(i + k).charAt(j) != 'B' && grid.get(i - k).charAt(j) != 'B'
                            && grid.get(i).charAt(j + k) != 'B' && grid.get(i).charAt(j - k) != 'B') {
                        plusSize = k;
                    } else {
                        break;
                    }
                }

                plusSizes.get(i).set(j, plusSize);
            }
        }

        int totalPluses = 0;

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (plusSizes.get(i).get(j) >= 0) {
                    totalPluses += 1;
                }
            }
        }

        List<List<Integer>> plusCoordinates = new ArrayList<>();

        List<Integer> result = new ArrayList<>(totalPluses * (totalPluses - 1));

        int plusIndex = 0;

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (plusSizes.get(i).get(j) >= 0) {
                    plusCoordinates.add(List.of(i, j, plusSizes.get(i).get(j)));
                }
            }
        }

        plusIndex = 0;
        int row1 = 0, row2 = 0, col1 = 0, col2 = 0, size1 = 0, size2 = 0;

        for (int i = 0; i < plusCoordinates.size(); ++i) {
            for (int j = 0; j < plusCoordinates.size(); ++j) {
                if (i != j) {
                    row1 = plusCoordinates.get(i).get(0);
                    row2 = plusCoordinates.get(j).get(0);
                    col1 = plusCoordinates.get(i).get(1);
                    col2 = plusCoordinates.get(j).get(1);

                    int absRowDiff = Math.abs(row1 - row2);
                    int absColDiff = Math.abs(col1 - col2);

                    for (size1 = 0; size1 <= plusCoordinates.get(i).get(2); ++size1) {
                        for (size2 = 0; size2 <= plusCoordinates.get(j).get(2); ++size2) {
                            if (absRowDiff > size1 + size2 || absColDiff > size1 + size2
                                    || (absRowDiff > Math.max(size1, size2) && col1 != col2)
                                    || (absColDiff > Math.max(size1, size2) && row1 != row2)
                                    || (absRowDiff > Math.min(size1, size2) && absColDiff > Math.min(size1, size2))) {
                                int factor1 = 4 * size1 + 1;
                                int factor2 = 4 * size2 + 1;

                                result.add(factor1 * factor2);
                            }
                        }
                    }

                    plusIndex++;
                }
            }
        }

        return result.stream().mapToInt(Integer::intValue).max().orElse(0);
    }   

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        List<String> grid = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        int result = Result.twoPluses(grid);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
