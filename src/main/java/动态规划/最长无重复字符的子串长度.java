package 动态规划;

/**
 * 给定一个只由小写字母（a~z）组成的字符串str，返回其中最长无重复字符的子串长度
 */
public class 最长无重复字符的子串长度 {
    public static int maxlength(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        //将字符转换为数组 charArray
        char[] str = s.toCharArray();
        int N = s.length();
        // last[0] -> a 上次出现的位置
        // last[1] -> b 上次出现的位置
        // last[2] -> c 上次出现的位置
        // last[3] -> d 上次出现的位置
        int[] last = new int[26];//记录字母上次出现的位置
        for (int i = 0; i < N; i++) {
            last[i] = -1;
        }
        last[str[0] - 'a'] = 0;//dp[0]=1 子串必须以0位置结尾，最长无重复字符的子串长度
        int max = 1;//记录当前的最大子串长度
        //dp[i - 1]的值，i位置的最长长度可以由 i-1 位置的已经求得的最大子串长度决定
        int preMaxLen = 1;//代表dp[i-1]的值
        for (int i = 1; i < N; i++) {//第一个位置已经设置了，所以从第二个位置开始 i=1 开始
            preMaxLen = Math.min(i - last[str[i] - 'a'], preMaxLen + 1);
            last[str[i] - 'a'] = i;
            max = Math.max(max, preMaxLen);
        }
        return max;
    }

    public static void main(String[] args) {
        String s = "abcaefaghi";
        System.out.println(s.length());
        System.out.println(maxlength(s));
    }
}
