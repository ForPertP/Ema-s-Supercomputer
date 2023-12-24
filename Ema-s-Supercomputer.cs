using System.CodeDom.Compiler;
using System.Collections.Generic;
using System.Collections;
using System.ComponentModel;
using System.Diagnostics.CodeAnalysis;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Runtime.Serialization;
using System.Text.RegularExpressions;
using System.Text;
using System;

class Result
{

    /*
     * Complete the 'twoPluses' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING_ARRAY grid as parameter.
     */

    public static int twoPluses(List<string> grid)
    {
        int rows = grid.Count;
        int cols = grid[0].Length;

        List<List<int>> plusSizes = new List<List<int>>(rows);
        for (int i = 0; i < rows; ++i)
        {
            plusSizes.Add(Enumerable.Repeat(0, cols).ToList());
        }

        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < cols; ++j)
            {
                int plusSize = -1;
                int minDist = Math.Min(Math.Min(i, rows - 1 - i), Math.Min(j, cols - 1 - j));

                for (int k = 0; k <= minDist; ++k)
                {
                    if (grid[i + k][j] != 'B' && grid[i - k][j] != 'B'
                        && grid[i][j + k] != 'B' && grid[i][j - k] != 'B')
                    {
                        plusSize = k;
                    }
                    else
                    {
                        break;
                    }
                }

                plusSizes[i][j] = plusSize;
            }
        }

        int totalPluses = 0;

        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < cols; ++j)
            {
                if (plusSizes[i][j] >= 0)
                {
                    totalPluses += 1;
                }
            }
        }

        List<List<int>> plusCoordinates = new List<List<int>>();
        plusCoordinates.Capacity = totalPluses;

        List<int> result = new List<int>(totalPluses * (totalPluses - 1));

        int plusIndex = 0;

        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < cols; ++j)
            {
                if (plusSizes[i][j] >= 0)
                {
                    plusCoordinates.Add(new List<int> { i, j, plusSizes[i][j] });
                }
            }
        }

        plusIndex = 0;
        int row1 = 0, row2 = 0, col1 = 0, col2 = 0, size1 = 0, size2 = 0;

        for (int i = 0; i < plusCoordinates.Count; ++i)
        {
            for (int j = 0; j < plusCoordinates.Count; ++j)
            {
                if (i != j)
                {
                    row1 = plusCoordinates[i][0];
                    row2 = plusCoordinates[j][0];
                    col1 = plusCoordinates[i][1];
                    col2 = plusCoordinates[j][1];

                    int absRowDiff = Math.Abs(row1 - row2);
                    int absColDiff = Math.Abs(col1 - col2);

                    for (size1 = 0; size1 <= plusCoordinates[i][2]; ++size1)
                    {
                        for (size2 = 0; size2 <= plusCoordinates[j][2]; ++size2)
                        {
                            if (absRowDiff > size1 + size2 || absColDiff > size1 + size2
                                || (absRowDiff > Math.Max(size1, size2) && col1 != col2)
                                || (absColDiff > Math.Max(size1, size2) && row1 != row2)
                                || (absRowDiff > Math.Min(size1, size2) && absColDiff > Math.Min(size1, size2)))
                            {
                                int factor1 = 4 * size1 + 1;
                                int factor2 = 4 * size2 + 1;

                                result.Add(factor1 * factor2);
                            }
                        }
                    }

                    plusIndex++;
                }
            }
        }

        return result.Max();
    }

}

class Solution
{
    public static void Main(string[] args)
    {
        TextWriter textWriter = new StreamWriter(@System.Environment.GetEnvironmentVariable("OUTPUT_PATH"), true);

        string[] firstMultipleInput = Console.ReadLine().TrimEnd().Split(' ');

        int n = Convert.ToInt32(firstMultipleInput[0]);

        int m = Convert.ToInt32(firstMultipleInput[1]);

        List<string> grid = new List<string>();

        for (int i = 0; i < n; i++)
        {
            string gridItem = Console.ReadLine();
            grid.Add(gridItem);
        }

        int result = Result.twoPluses(grid);

        textWriter.WriteLine(result);

        textWriter.Flush();
        textWriter.Close();
    }
}
