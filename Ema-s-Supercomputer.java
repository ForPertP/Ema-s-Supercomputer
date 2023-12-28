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
