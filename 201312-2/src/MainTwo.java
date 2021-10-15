import java.util.Scanner;

public class MainTwo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        String str1 = str.replace("-","");
        char[] ch = str1.toCharArray();
        int sum = 0;
        char c = '0';
        for (int i = 0;i < 9;i++){
            sum += (ch[i] - 48)*(i + 1);
        }
        sum = sum % 11;
        if (sum == 10){
            c = 'X';
        }else {
            c = (char) (sum + 48);
        }
        if (c == ch[ch.length-1]){
            System.out.println("Right");
        }else {
            System.out.println(str.substring(0,12) + c);
        }
    }
}
