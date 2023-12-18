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

int twoPluses(vector<string> grid)
{
    int r = grid.size();
    int c = grid[0].size();

    std::vector<std::vector<int>> size;
    size.assign(r, vector<int>(c, 0));

    for (int i = 0; i < r; ++i)
    {
        for (int j = 0; j < c; ++j)
        {
            int count = -1;
            int min = std::min(std::min(i, r-1-i), std::min(j, c-1-j));

            for (int k = 0; k <= min; ++k)
            {
                if (grid[i+k][j] != 'B' && grid[i-k][j] != 'B'
                    && grid[i][j+k] != 'B' && grid[i][j-k] != 'B')
                {
                    count = k;
                }
                else
                {
                    break;
                }
            }

            size[i][j] = count;
        }
    }

    int count = 0;

    for (int i = 0; i < r; ++i)
    {
        for (int j = 0; j < c; ++j)
        {
            if (size[i][j] >= 0)
            {
                count += 1;
            }
        }
    }

    std::vector<std::vector<int>> plus;
    plus.assign(count, vector<int>(3, 0));

    std::vector<int> result;
    result.resize(count * (count - 1));

    int k = 0;

    for (int i = 0; i < r; ++i)
    {
        for (int j = 0; j < c; ++j)
        {
            if (size[i][j] >= 0)
            {
                plus[k][0] = i;
                plus[k][1] = j;
                plus[k][2] = size[i][j];
                k++;
            }
        }
    }

    k = 0;
    int r1 = 0, r2 = 0, c1 = 0, c2 = 0, s1 = 0, s2 = 0;

    for (int i = 0; i < plus.size(); ++i)
    {
        for (int j = 0; j < plus.size(); ++j)
        {
            if (i != j)
            {
                r1 = plus[i][0];
                r2 = plus[j][0];
                c1 = plus[i][1];
                c2 = plus[j][1];

                for (s1 = 0; s1 <= plus[i][2]; s1++)
                {
                    for (s2 = 0; s2 <= plus[j][2]; s2++)
                    {
                        if (std::abs(r1 - r2) > s1 + s2
                            || std::abs(c1 - c2) > s1 + s2
                            || (std::abs(r1 - r2) > std::max(s1, s2) && c1 != c2)
                            || (std::abs(c1 - c2) > std::max(s1, s2) && r1 != r2)
                            || (std::abs(r1 - r2) > std::min(s1, s2)
                                && std::abs(c1 - c2) > std::min(s1, s2)))
                        {
                            if (result[k] < (4 * s1 + 1) * (4 * s2 + 1))
                            {
                                result[k] = (4 * s1 + 1) * (4 * s2 + 1);
                            }
                        }
                    }
                }

                k++;
            }
        }
    }

    return *max_element(result.begin(), result.end());
}

