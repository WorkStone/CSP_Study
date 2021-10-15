import java.util.Scanner;

public class SafeIndex {
    private int safeIndex(){
        int n; //输入行数
        int sum = 0,y = 0;  //sum表示成绩与重要程度的乘积和，y表示最终影响因子
        int w,score; //w表示该成绩的重要程度，score代表成绩
        Scanner scan = new Scanner(System.in);
        System.out.print("请输入成绩的个数：");
        n = scan.nextInt();
        for (int i = 0; i < n; i++) {
            w = scan.nextInt();
            score = scan.nextInt();
            sum = sum + w*score;
        }
        if (sum >= 0)
            y = sum;
        else
            y = 0;
        return y;
    }

    public static void main(String[] args) {
        SafeIndex safe = new SafeIndex();
        System.out.println(safe.safeIndex());
    }
}
