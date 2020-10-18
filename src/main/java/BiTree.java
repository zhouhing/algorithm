import lombok.Data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BiTree {

    @Data
    static class TreeNode{
        Integer data;
        TreeNode leftNode;
        TreeNode rightNode;

        public TreeNode(Integer data) {
            this.data = data;
        }

    }
    private static TreeNode createTree(LinkedList<Integer> inputList){
        TreeNode node = null;
        if (inputList== null || inputList.isEmpty()){
            return null;
        }
        Integer data = inputList.removeFirst();
        if (data != null)
        {
            node = new TreeNode(data);
            node.leftNode = createTree(inputList);
            node.rightNode = createTree(inputList);
        }
        return node;
    }
    public static void  preVisitNode(TreeNode node){
        if (node == null)
        {
            return;
        }
        System.out.println(node.data);
        preVisitNode(node.leftNode);
        preVisitNode(node.rightNode);
    }
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<Integer>(Arrays.asList(new Integer[]{3, 2, 4, null, null, 10, null}));
        TreeNode tree = createTree(list);
        System.out.println(tree);
        System.out.println("优美的分割线~~~~~~~~~~~~~~~~~~");
        preVisitNode(tree);
    }
}
