import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int M = in.nextInt();
        ArrayList<window> list = new ArrayList<window>();
        for (int i = 0;i < N;i++){
            list.add(new window(in.nextInt(),in.nextInt(),in.nextInt(),in.nextInt(),i+1));
        }
        int x0 = 0,y0 = 0;
        String[] out = new String[M];
        int k;
        for (int j = 0;j < M;j++){
            x0 = in.nextInt();
            y0 = in.nextInt();

            k = N - 1;
            for (;k >= 0;k--){
                if (list.get(k).check(x0,y0) != 0){
                    out[j] = list.get(k).check(x0,y0) + "";

                    //这两句的作用,一个点可能存在于多个面，只显示最上面的面。
                    //所以每点击一个面之后就调整该面在集合中的位置
                    list.add(list.get(k));
                    list.remove(k);
                    break;
                }
            }

            if (k < 0) out[j] = "IGNORED";
        }

        for (int l = 0;l < M;l++){
            System.out.println(out[l]);
        }
    }

    //对窗口的封装
    static class window{
        int x1,x2,y1,y2;
        int order;

        public window(int x1,int y1,int x2,int y2,int order){
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.order = order;
        }

        //判定点击点是否在窗口范围
        public int check(int x0,int y0){
            if (x0 >= x1 && x0 <= x2 && y0 >= y1 && y0 <= y2){
                return order;
            }else{
                return 0;
            }
        }
    }
}
