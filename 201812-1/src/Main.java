import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        //BufferedReader bf = new BufferedReader(new FileReader("C:/Users/17865/Desktop/data.txt"));
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int r = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int g = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());//一共的路程
        int sum = 0;

        System.out.println(sum);
    }
}