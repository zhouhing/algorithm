import java.util.HashSet;

/**
 * 给定一个非负数组arr 和一个整数m
 * 返回arr的所有子序列中的累加和，求对m取模之后的最大值，%m之后的最大值
 * 1> 如果arr中的每个数字不大，怎么做这道题？
 * 2> 如果arr中的m很小，怎么做这道题？
 * 3> 如果arr中的长度很短，但是arr中的每个数字比较大并且m比较大，怎么做这道题？
 */

public class MaxValue {

    public static int max1(int[] array, int m) {
        HashSet<Integer> set = new HashSet<>();
        process(array, 0, 0, set);
        int max = 0;
        for (Integer value : set) {
            max = Math.max(max, value % m);
        }
        return max;
    }

    public static void process(int[] arr, int index, int sum, HashSet<Integer> set) {
        //递归的出口
        if (index == arr.length) {
            set.add(sum);
        } else {
            process(arr, index + 1, sum, set);//不选择当前值
            process(arr, index + 1, sum + arr[index], set);//选择当前值
        }
    }

    /**
     * 动态规划 O(n) = N * sum
     * 所有累加不是很大的时候比较适用
     *
     * @param arr
     * @param m
     * @return
     */
    public static int max2(int[] arr, int m) {
        int sum = 0;
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
        }
        /**
         * 创建一个表，，arr[i][j]其中i为行：代表arr数组中的值
         *                          j为列：代表 0~sum 的值，
         * 表中的值为布尔量，意思为，随意使用 0~i 行的值能不能得到值 j 得布尔量
         * 原始 arr 数组 【5,1,2】
         * arr数组  0 1 2 3 4 5 6 7 8
         *      5【T F F F F T F F F】   arr[2][0]可以全都不取 【5,1,2】中的任意数而得到所以为 T
         *      1【T T F F F T F F F】   arr[1][5] 可以单独取一个 5 而得到，所以也为 T
         *      2【T F T T F T T T T】   arr[2][7] 可以取【5,1,2】中的 5 和 2 而得到，所以也为 T
         */
        boolean[][] dp = new boolean[N][sum + 1];//列数 对应 0~sum 的每一个值
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }//第一列全能取到
        dp[0][arr[0]] = true;//第一行只有一个true

        //0 行不用管
        //0 列不用管
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                //dp[i][j]      不使用 arr[i]的数，意思是 0....i-1 的数就可以得出 j
                dp[i][j] = dp[i - 1][j];
                // 使用 arr[i]的数，意思是 只要 0....i-1的数能得到，j-arr[i]的值，就可以 加上 arr[i]，得到 j
                if (j - arr[i] >= 0)//越界就不存在这种可能
                {
                    dp[i][j] = dp[i][j] | dp[i - 1][j - arr[i]];
                }
            }
        }
        // 整张表填写完毕后，可以由最后一行的值得出各种组合的最大值
        int ans = 0;
        for (int i = 0; i <= sum; i++) {
            if (dp[N - 1][i]) {
                ans = Math.max(ans, i % m);
            }
        }
        return ans;
    }

    public static int max3(int[] array, int m) {
        /* 创建一张表 bp ，其中 bp[i][j]代表，随意使用 0~i 中的值，能否得到一个值对 m 取模，等于 j
        1> dp[2][4]: 不使用 arr[2],就是使用0~1（20,17）能否得到一个对m取模之后的值等于4
        2> dp[2][4]: 使用 arr[2]，17,n :17 % 5 =2,就是使用0~1（20,17）能否得到一个对m取模之后的值等于 4-2=2 (m-n)
        2> dp[2][3]: 使用 假设arr[2]=24,24 % 5 =4，(n=4,m=5),就是使用0~1行的值，能否得到一个对m取模之后的值等于 4 （m+3）
        arr[20, 17, 19] m=5
            0 1 2 3 4 5
        20 [T F F F F F]
        17 [T F T      ]
        19 [T F        ]
         */
        int N = array.length;
        boolean[][] dp = new boolean[N][m];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        dp[0][array[0] % m] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = dp[i - 1][j];
                int cur = array[i] % m;
                if (j - cur >= 0) {
                    dp[i][j] = dp[i][j] | dp[i - 1][j - cur];
                }
                // 假如 m=9 ，对 m 取模之后的值为 8 ，而想得到的值为 3, 则需要 m+j-cur (9+3-8) 才能得到
                if (m + j - cur < m) {//转一圈回来
                    dp[i][j] = dp[i][j] | dp[i - 1][m + j - cur];
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < m; i++) {
            if (dp[N-1][i])
            {
                ans = i;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] array = {5, 1, 2};
        System.out.println(max1(array, 5));
        System.out.println(max2(array, 5));
        System.out.println(max3(array, 5));
    }

}
