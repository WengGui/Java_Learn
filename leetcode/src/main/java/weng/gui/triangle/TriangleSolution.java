package weng.gui.triangle;

import java.util.List;

/*
给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
例如，给定三角形：
[
     [2],
    [3,4],
   [6,5,7],
  [4,1,8,3]
]
自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
 */
public class TriangleSolution {

    public static void main(String[] args) {

    }


    public int min(List<Integer> list){
        if(list.size()==1){
            return list.get(0);
        }
        int min = Integer.MAX_VALUE;
        for(Integer num : list){
            if(num < min)
                min = num ;
        }
        return min;
    }

    public void minPath(List<List<Integer>> triangle){
        int sum = 0 ;
        for(List<Integer> subList : triangle){
            sum+= min(subList);
        }
    }
}
