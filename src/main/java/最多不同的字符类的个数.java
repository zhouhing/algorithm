import java.util.HashSet;

/**
 * 如果两个字符串，所含的字符种类完全一样，就算一类，（abcdaedasg 这个字符串属于 类abcdesg， baacba 和 abc属于同一类）
 * 只由小写字母（a~z）组成的一批字符串，都放在了字符类型的数组类型String[] arr中，返回 arr中有多少类？
 */
public class 最多不同的字符类的个数 {
    public static int type1(String[] arr) {
        HashSet<String> types = new HashSet<>();
        for (String str : arr) {
            char[] chs = str.toCharArray();
            boolean[] map = new boolean[26];//记录每个字符是否出现了
            for (int i = 0; i < chs.length; i++) {
                map[chs[i] - 'a'] = true;
            }
            StringBuilder keys = new StringBuilder();
            for (int i = 0; i < map.length; i++) {
                if (map[i]) {
                    keys.append(String.valueOf((char) (i + 'a')));
                }
            }
            types.add(keys.toString());
        }
        return types.size();
    }
    public static int type2(String[] arr){
        //int 类型有32位，每一位使用0或1代表字符有没有出现过，故可以使用一个整数代替字符的出现结果
        // a~z 26个字符，完全可以使用 一个 int类型的整数记录位置，a~Z 52个字符用 long类型 64位大小记录
        HashSet<Integer> types = new HashSet<>();
        for (String str: arr)
        {
            char [] chs = str.toCharArray();
            int key = 0;//00000000 00000000 00000000 00000000
            for (int i = 0; i < chs.length; i++) {
                key |= (1 << (chs[i] - 'a'));
            }
            types.add(key);
        }
        return types.size();
    }

    public static void main(String[] args) {

    }
}
