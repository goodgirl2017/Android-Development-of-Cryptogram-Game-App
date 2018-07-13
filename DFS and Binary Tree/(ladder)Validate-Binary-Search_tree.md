# Validate Binary Search Tree (M)

**Author**: Nancy
&copy;

## Description

[a link](https://www.lintcode.com/problem/validate-binary-search-tree/description
)
![description](https://github.com/goodgirl2017/leetcode-problems/blob/master/Images/validate-binary-search-tree.png)

## Assumption

## Key Points

## Data Structure

## How to get it?


## Method


## Complexity

### Method1: Traverse 

#### Time Complexity: O(n)


#### Space Complexity: O(n)


### Method2: 

#### Time Complexity: O(n)


#### Space Complexity: O(n)


## Other Methods

## Comparison of Methods

## Follow Up

## Code

### In Order Traverse: O(n)--Non recursion

```java class:"lineNo"
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */

public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: True if the binary tree is BST, or false
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        int prev = Integer.MIN_VALUE; //!!
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
        
        int id = 0;
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            id++;
            if (id == 1 || node.val > prev) { //!! !! !!
                prev = node.val;
            } else {
                return false;
            }
            
            if (node.right == null) {
                TreeNode ele = stack.pop();
                while (!stack.isEmpty() && stack.peek().right == ele) {
                    ele = stack.pop();
                }
            } else {
                TreeNode ele = node.right;
                while (ele != null) {
                    stack.push(ele);
                    ele = ele.left;
                }
            }
            
        }
        
        return true;
    }
}
```

#### <span style="color:blue">some *Comments* text</span>
     * <span style="color:red">some **注意第一个点的比较，因为此时没有prev这个值，所以额外定义了id line34; ** text</span>
     * <span style="color:red">some __**即使你初始赋值是MIN_VALUE line24也是没用的，因为BST是严格的大于和小于，他可能初始值就正好是MIN_VALUE line24 **__ text</span>
     <span style="color:red">some __**注Int prev line 24不要忘了初始赋值 **__ text</span>
     * <span style="color:red">some __**如果要避免额外定义id，可以考虑prev设置成TreeNode type,同时赋值为NULL，这样就没有极端情况的考虑了，因为corner case已经把root == null解决了 **__ text</span>
     * <span style="color:red">some __**序列的初始值是stack里面的初始值，并不是root line 34，千万不要为了避免用id variable从而比较node是否是root，因为root不是初始值 **__ text</span>
     * <span style="color:red">some __**! TreeNode里面有== 方法，不用用equals() **__ text</span>

### In order Traverse O(n) --recursion



### Divide and Conquer: O(n)


## Knowledge
	*<span style="color:red">some **! TreeNode里面有== 方法，不用用equals() ** text</span>




