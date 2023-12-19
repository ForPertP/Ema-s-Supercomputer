#include <bits/stdc++.h>

using namespace std;

string ltrim(const string &);
string rtrim(const string &);
vector<string> split(const string &);

/*
 * Complete the 'twoPluses' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts STRING_ARRAY grid as parameter.
 */

int twoPluses(vector<string> grid) {
    int rows = grid.size();
    int cols = grid[0].size();

    vector<vector<int>> plusSizes(rows, vector<int>(cols, 0));

    for (int i = 0; i < rows; ++i) {
        for (int j = 0; j < cols; ++j) {
            int plusSize = -1;
            int minDist = min(min(i, rows - 1 - i), min(j, cols - 1 - j));

            for (int k = 0; k <= minDist; ++k) {
                if (grid[i + k][j] != 'B' && grid[i - k][j] != 'B'
                    && grid[i][j + k] != 'B' && grid[i][j - k] != 'B') {
                    plusSize = k;
                } else {
                    break;
                }
            }

            plusSizes[i][j] = plusSize;
        }
    }

    int totalPluses = 0;

    for (int i = 0; i < rows; ++i) {
        for (int j = 0; j < cols; ++j) {
            if (plusSizes[i][j] >= 0) {
                totalPluses += 1;
            }
        }
    }

    vector<vector<int>> plusCoordinates;
    plusCoordinates.reserve(totalPluses);

    vector<int> result(totalPluses * (totalPluses - 1), 0);

    int plusIndex = 0;

    for (int i = 0; i < rows; ++i) {
        for (int j = 0; j < cols; ++j) {
            if (plusSizes[i][j] >= 0) {
                plusCoordinates.push_back({i, j, plusSizes[i][j]});
            }
        }
    }

    plusIndex = 0;
    int row1 = 0, row2 = 0, col1 = 0, col2 = 0, size1 = 0, size2 = 0;

    for (size_t i = 0; i < plusCoordinates.size(); ++i) {
        for (size_t j = 0; j < plusCoordinates.size(); ++j) {
            if (i != j) {
                row1 = plusCoordinates[i][0];
                row2 = plusCoordinates[j][0];
                col1 = plusCoordinates[i][1];
                col2 = plusCoordinates[j][1];

                int absRowDiff = abs(row1 - row2);
                int absColDiff = abs(col1 - col2);

                for (size1 = 0; size1 <= plusCoordinates[i][2]; ++size1) {
                    for (size2 = 0; size2 <= plusCoordinates[j][2]; ++size2) {
                        if (absRowDiff > size1 + size2 || absColDiff > size1 + size2
                            || (absRowDiff > max(size1, size2) && col1 != col2)
                            || (absColDiff > max(size1, size2) && row1 != row2)
                            || (absRowDiff > min(size1, size2) && absColDiff > min(size1, size2))) {
                            int factor1 = 4 * size1 + 1;
                            int factor2 = 4 * size2 + 1;

                            if (result[plusIndex] < factor1 * factor2) {
                                result[plusIndex] = factor1 * factor2;
                            }
                        }
                    }
                }

                plusIndex++;
            }
        }
    }

    return *max_element(result.begin(), result.end());
}

int main()
{
    ofstream fout(getenv("OUTPUT_PATH"));

    string first_multiple_input_temp;
    getline(cin, first_multiple_input_temp);

    vector<string> first_multiple_input = split(rtrim(first_multiple_input_temp));

    int n = stoi(first_multiple_input[0]);

    int m = stoi(first_multiple_input[1]);

    vector<string> grid(n);

    for (int i = 0; i < n; i++) {
        string grid_item;
        getline(cin, grid_item);

        grid[i] = grid_item;
    }

    int result = twoPluses(grid);

    fout << result << "\n";

    fout.close();

    return 0;
}

