package com.chanjet.ccs.ccp.base.util;

public class CcpUtil {

    // num：返回的数量
    public static int[] getRandomIndex(int num, int max){
        //int num = 6; // TODO 应可配置
        if(max<=num){
            int[] arr = new int[max];
            for(int i=0;i<max;i++){
                arr[i]=i;
            }
            return arr;
        }else{
            int[] array = new int[num];
            for(int i=0;i<num;){
                int curr_num = (int)(Math.random()*max);
                boolean flag = true;
                for(int j=0;j<i;j++){
                    if(array[j]==curr_num){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    array[i] = curr_num;
                    i++;
                }
            }
            return array;
        }
    }
    
    
    public static void main(String[] args) {
        for (int i : getRandomIndex(2, 2000))
            System.out.println(i);
    }
}
