import java.io.*;
import java.util.StringTokenizer;

/**
 * ccf 202104-2 领域均值
 */
public class Main {
    public static void neighborhoodAverage() throws IOException {
        //BufferedReader br = new BufferedReader(new FileReader("C:/Users/17865/Desktop/data.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        int[][] A = new int[n][n];

        for (int i = 0;i < n;i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 0;j < n;j++){
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        //test,存储每列的范围阈值，计算时相加。
        int[][] B = new int[n][n];
        for (int i = 0;i < n;i++){
            for (int j = 0;j < n;j++){
                for (int k = j-r;k <= j+r;k++){
                    if (k < 0 || k >= A.length){
                        continue;
                    }
                    B[j][i] += A[k][i];
                }
            }
        }

        int temp = 0, x = 0;
        for (int i = 0;i < n;i++){
            for (int j = 0;j < n;j++){
                x = averagePlus(i,j,r,B);
                if (x <= t){
                    temp++;
                }
            }
        }

        out.println(temp);
        br.close();
        out.close();
    }

    public static int average(int n,int m,int r,int[][] A){
        double sum = 0, count = 0;
        for (int i = n-r;i <= n+r;i++){
            for (int j = m-r;j <= m+r;j++){
                if (i < 0 || j < 0 || i >= A.length || j >= A[0].length){
                    continue;
                }
                sum += A[i][j];
                count++;
            }
        }
        return (int)(Math.ceil(sum / count));  //Math.ceil()向上取整，Math.floor()向下取整
    }

    //change
    public static int averagePlus(int n,int m,int r,int[][] B){
        double sum = 0, count = 0;
        for (int i = m-r;i <= m+r;i++){
            if (i < 0 || i >= B[n].length){
                continue;
            }
            sum += B[n][i];
            count++;
        }
        if (n-r < 0 && n+r >= B.length){
            count *= B.length;
        }else if (n-r < 0 && n+r < B.length){
            count *= n+r+1;
        }else if (n-r > 0 && n+r >= B.length){
            count *= B.length - (n-r);
        }else{
            count *= 2*r+1;
        }
        return (int)(Math.ceil(sum / count));
    }

    public static void main(String[] args) throws IOException{
        neighborhoodAverage();
    }
}