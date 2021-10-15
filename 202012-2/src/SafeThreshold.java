/*
* 简介：该程序得分70分，时间使用运行超时，题目限时1s内完成。
* 思路：程序采取的思路就是对数据去重，然后比较大小获取正确的次数，保存正确次数最多的y值。
* 改进想法：不能使用两层循环暴力破解，想办法对数据进行处理，节约时间。
* */

import java.util.*;
//方案一，得分70，运行超时。因为测试数据最大为10^5，如果用两层循环暴力破解，运行时间
/*public class SafeThreshold {

    public static void safeThreshold(){
        int m;     //输入同学个数
        Scanner scan = new Scanner(System.in);
        System.out.print("请输入学生个数：");
        m = scan.nextInt();

        int[] y,result;  //y是安全指数，result表示挂科结果
        y = new int[m];
        result = new int[m];   //java数组声明就要初始化。此处为动态初始化

        for (int i = 0; i < m; i++) {
            y[i] = scan.nextInt();
            result[i] = scan.nextInt();
        }

        //Arrays.sort(y);     //排序
        List<Integer> list = new ArrayList<Integer>();         //数组长度不能动态增长，但ArrayList的长度可以动态增长
        //数组元素去重。注：有些集合不允许出现重复元素，比如set
        for (int i = 0; i < m; i++) {
            if (!list.contains(y[i])){
                list.add(y[i]);
            }
        }
        System.out.println(list);
        //迭代器，foreach等价于Iterator<int> iterator = list.iterator();
        int right;
        int max = 0,max_i = 0;  //记录最大值以及最大值对应的阈值
        for (int i:list) {
            right = 0;
            for (int j = 0; j < m; j++) {
                if ((y[j] >= i && result[j] == 1) || (y[j] < i && result[j] == 0)){
                    right++;
                }
            }
            if (right > max){
                max = right;
                max_i = i;
            }else if(right == max){
                if (i >= max_i)
                    max_i = i;
            }
        }
        System.out.println("最佳阈值："+max_i);
    }

    public static void main(String[] args) {
        safeThreshold();
    }
}*/


//方案二，不能采用两层循环。处理思路和手法值得学习。
public class SafeThreshold {

    public static void safeThreshold() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int max = -1,result = -1,count = 0;

        Set<Integer> set = new TreeSet<>();        //Hashset不保证有序（注：不保证有序和保证无序不等价）；而Treeset是有序集合。这个集合对安全指数排序
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {      //该集合是一个优先级排序的队列，但排序是一个对象，所以要实现新的排序方法
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0]-o2[0];     //根据排序数组的第一个元素进行排序。
            }
        });

        while(n-- != 0){
            int[] temp = new int[]{scan.nextInt(),scan.nextInt()};           //安全指数和对应的挂科结果有一个对应的二元素数组表示
            set.add(temp[0]);                      //去重同时排序,因为set集合中元素不重复且Treeset是有序集合
            pq.add(temp);                          //对安全指数排序的同时，对数组对象依据安全指数排序。（这样就能将排序后的安全指数与整个对象数组一一对应，这也是遇到的难点之一）
            if(temp[1] == 1) count++;              //记录没有挂科的人数
        }

        //set(安全指数）升序遍历，同时判断正确预测的次数
        /*
        * 这段的含义：
        * 1、首先已将安全指数和对应的数组对象按从小到大的顺序排序；
        * 2、count记录了所有未挂科的人数，即temp[1]=1的个数；
        * 3、对set集合中值依次取值作为阈值，顺序是从小到大取值，则当安全指数大于阈值时，此时记录记录的未挂科人数则全部预测正确，所以只需要判断安全指数小于阈值的数组对象，
        *    对于安全指数小于阈值的数组对象，此时temp[1]=0才为预测正确，所以pq.peek()[1]==1时，表示已经记录在count中，需要count--，否则，count++，因为此时temp[1]==0,
        *    没有记录在count中，而等于0时，表示预测正确，所以count++；
        * 4、为什么一次循环后count值没有初始化为第二步的值，原因时这句代码：pq.poll();它将已经判定过的值移除了，不会再取下一个阈值进行重复的判定。因为所取下一个阈值一定比之前
        *    大，所以之前判定的结果对现在判定的依然有效（即是，i > pq.peek()[0] >= i_pre,i_pre是上一个所取的阈值）。
        * 本段代码参考：https://blog.csdn.net/weixin_43732798/article/details/111991065
        * */
        for (int i:set) {
            while(!pq.isEmpty() && pq.peek()[0] < i){     //peek()是获取对头元素，为空时不会返回异常。poll()是移除对头元素，为空时不会返回异常。
                //对于p.peek()[1]==1来说，之前预测对的人数-1，反之==0时，预测对的人数+1.
                if(pq.peek()[1] == 1) count--;
                else count++;
                pq.poll();
            }
            if (count >= max){
                result = i;
                max = count;
            }
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        safeThreshold();
    }
}
