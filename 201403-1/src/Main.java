import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int[] n = new int[N];
        for (int i = 0;i < n.length;i++){
            n[i] = scanner.nextInt();
        }
        int count = 0;
        for (int i = 0;i < n.length;i++){
            for (int j = i+1;j < n.length;j++){
                if (n[i] + n[j] == 0) count++;
            }
        }
        System.out.println(count);
    }
}
