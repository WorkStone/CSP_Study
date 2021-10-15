import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        int flag = scanner.nextInt();
        int Num = scanner.nextInt();
        String[] strings = new String[Num];
        for (int i = 0;i < Num;i++){
            //next()方法过滤有效字符前的无效字符，有效字符之后，next()方法才将空格键等作为结束符
            //nextLine()方法作用是扫描一整行，结束符只能是Enter键，可以读取空格。
            strings[i] = scanner.next();
        }
        if (flag == 1){
            for (int i = 0;i < Num;i++){
                if (strings[i].contains(string)){
                    System.out.println(strings[i]);
                }
            }
        }else {
            for (int i = 0;i < Num;i++){
                //为0时不区分大小写，于是将目标串和源串都转化成大写。
                if (strings[i].toUpperCase().contains(string.toUpperCase())){
                    System.out.println(strings[i]);
                }
            }
        }
    }
}
