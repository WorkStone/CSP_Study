import java.util.*;

public class Main {
    //点的坐标
    static class P{
        int x;
        int y;
    }

    public static List<P> p = new ArrayList<P>();  //存放顶点
    public static void main(String[] args) {
        boolean[][] map = new boolean[205][205];
        long[][] d = new long[205][205];
        boolean[][] vis = new boolean[205][205];
        Queue<P> q = new LinkedList<P>();   //用于广度优先遍历

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        long r = in.nextLong();
        long[][] arr = new long[n+m][2];

        for (int i = 0;i < n+m;++i){
            long a = in.nextLong();
            long b = in.nextLong();
            arr[i][0] = a;
            arr[i][1] = b;
        }
        in.close();

        for (int i = 0;i < n+m;++i){
            for (int j = i+1;j < n+m;++j){
                if ((arr[i][0]-arr[j][0])*(arr[i][0]-arr[j][0])+(arr[i][1]-arr[j][1])*(arr[i][1]-arr[j][1]) <= r*r){
                    //勾股定理判定距离
                    map[i][j] = true;
                    map[j][i] = true;
                }
            }
        }

        for (int i = 0;i < 205;i++)
            for (int j = 0;j < 205;j++)
                d[i][j] = 0x3f3f3f3f;

        d[0][0] = 0;
        vis[0][0] = true;
        P p = new P();

        p.x = 0;
        p.y = 0;

        q.add(p);
        while (!q.isEmpty()){
            P s = new P();
            s = q.remove();
            vis[s.x][s.y] = false;
            for (int i = 0;i < n+m;++i){
                P tem = new P();
                if (map[s.x][i]){
                    tem.x = i;
                    tem.y = s.y;
                    if (i >= n) ++tem.y;
                    if (tem.y<=k&&d[tem.x][tem.y]>d[s.x][s.y]+1){
                        d[tem.x][tem.y] = d[s.x][s.y] + 1;
                        if (!vis[tem.x][tem.y]){
                            vis[tem.x][tem.y] = true;
                            q.add(tem);
                        }
                    }
                }
            }
        }
        long ans = 0x3f3f3f3f;
        for (int i = 0;i <= k;i++) ans = Math.min(ans,d[1][i]);
        System.out.printf("%d/n",ans-1);
    }
}
