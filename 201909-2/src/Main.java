import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("C:/Users/17865/Desktop/data.txt"));
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int[] sum = new int[n];
        int count = 0;
        int[] e = new int[n];      //用数组存每颗树的掉落情况，看索引值是否相邻
        for (int i = 0;i < n;i++){
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            sum[i] += k;
            for (int j = 1;j < m;j++){
                int l = Integer.parseInt(st.nextToken());
                if (l <= 0){
                    sum[i] += l;
                }else {
                    if (l < sum[i]){
                        //count++;                  //逻辑有误，如果一行值多次查果且落下，count++会出问题。
                        e[i] = 1;
                        sum[i] = l;
                    }
                }
            }
        }
        int num = 0;
        for (int i : sum){
            num += i;
        }
        int E = 0;
        if (e[0] == 1 && e[1] == 1 && e[n-1] == 1){
            E++;
        }
        if (e[n-1] == 1 && e[0] == 1 && e[n-2] == 1){
            E++;
        }
        for (int i = 1;i < n-1;i++){
            if (e[i-1] == 1 && e[i] == 1 && e[i+1] == 1){
                E++;
            }
        }
        for (int j : e){
            if (j == 1){
                count++;
            }
        }
        System.out.println(num + " " + count + " " + E);
    }
}
