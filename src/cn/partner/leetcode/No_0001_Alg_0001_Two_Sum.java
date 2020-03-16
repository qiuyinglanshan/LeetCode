package cn.partner.leetcode;

/**
 * 两数之和
 *
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

// LeetCode begin
class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return null;
    }
}

/**
 * ↓参照大神用HashMap的写法，快很多
 */
//class Solution {
//    static int r[] = new int[2];
//    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//    public int[] twoSum(int[] nums, int target) {
//        for (int i = 0; i < nums.length; i++) {
//            Integer v = map.get(nums[i]);
//            if (v != null) {
//                r[0] = v;
//                r[1] = i;
//                return r;
//            }
//            map.put(target - nums[i], i);
//        }
//        return null;
//    }
//}
// LeetCode end

//Test
public class No_0001_Alg_0001_Two_Sum {
    public static void main(String[] args) {
        int[] result = new Solution().twoSum(new int[]{2, 7, 11, 15}, 9);
        if (result != null)
            System.out.println(String.format("[%d, %d]", result[0], result[1]));
    }
}

/**
 * Output:
 * [0, 1]
 */
