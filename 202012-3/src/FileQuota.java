import java.util.*;
import java.util.regex.Pattern;

//该题要用树结构解决
public class FileQuota {

    public static void fileQuota(){
        Scanner in = new Scanner(System.in);
        System.out.print("请输入指令条数：");
        int n = Integer.parseInt(in.nextLine());
        //Set<String[]> set = new HashSet<>();                     //set是无序的，后面遍历的时候不是按顺序来的，所以用ArrayList
        ArrayList<String[]> alist = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("请输入第" + (i+1) + "条指令：");
            String s = in.nextLine();                              //问题：当n=1时，改循环并不会输出。原因：之前有一个nextInt函数，两种的处理机制不同，后面会把前面的回车符读入。
            String[] subString = s.split(" ");               //split(" ")不能判断连续个空格，而split("\s+")用处更加广泛
            //set.add(subString);
            alist.add(subString);
        }

        for (String[] str: alist) {
            /*for (int i = 0; i < str.length; i++) {
                System.out.print(str[i] + " ");
            }
            System.out.println();*/
            switch (str[0]){
                case "C":
                    System.out.println("文件路径合法？" + creatFile(str));
                    break;
                case "R":
                    System.out.println("这是一条文件删除指令");
                    break;
                case "Q":
                    System.out.println("这是一条配额分配指令");
                    break;
                default:
                    System.out.println("您输入的指令不对。");
                    break;
            }
        }
    }

    //创造普通文件操作
    public static boolean creatFile(String[] strings){
        if (strings[1].isEmpty() || !Pattern.matches("^(\\/[A-Z])(\\/[A-Z]+|\\/[0-9]+)*",strings[1])) return false;  //针对文件路径格式的正则表达式
        String s = strings[1];

        return true;
    }

    //删除文件操作
    public static boolean removeFile(String[] strings){
        return false;
    }

    //文件配额操作
    public static boolean quotaFile(String[] strings){
        return false;
    }

    public static void main(String[] args) {
        fileQuota();
    }
}
