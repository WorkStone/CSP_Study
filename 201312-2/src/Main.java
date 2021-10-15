import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader bin = new BufferedReader(new InputStreamReader(System.in));
        try {
            int sum = 0;
            char cc = '0';
            String isbn_0 = bin.readLine();
            String isbn = isbn_0.replace("-","");
            for (int i = 0;i < 9;i++){
                int ii = (int)isbn.charAt(i)-48;   //因为string是字符型的，找到ascii码
                sum += ii*(i+1);
            }
            sum = sum % 11;
            if (sum == 10){
                cc = 'X';
            }else {
                cc = (char)(sum + 48);
            }
            if (cc == isbn.charAt(9)){
                System.out.println("Right");
            }else {
                isbn_0 = isbn_0.substring(0,12) + cc;
                System.out.println(isbn_0);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
