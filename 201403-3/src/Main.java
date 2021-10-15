import java.util.HashMap;
import java.util.Scanner;

/**
 * 题目思路：
 * 用HashMap<String,Boolean>存储格式字符串的信息，当作字典使用，使用HashMap<String,String>存储结果集.
 * 对于每一个命令行，我们先用map判断选项是否在字典中，如果不在，则说明选项不合法，结束该命令行。
 * 接着判断是否需要参数，如果需要参数且有给合法的参数，则填入结果集，否则结束。
 * 如果不需要参数，则直接填入。
 * 输出时，使用for循环从‘a'到’z'进行遍历(保证升序输出),如果存在于结果集中，则输出。
 */

public class Main{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        /**Map其实就是保存了两个对象之间的映射关系的一种集合，HashMap的主干是一个Entry数组，
         *每个Entry数组包含一个key-value键值对。
         *HashMap由数组+链表组成的。数组是HashMap的主体，链表则是主要为了解决哈希冲突而存在.
         */
        HashMap<String,Boolean> map = new HashMap<String,Boolean>();  //字典
        HashMap<String,String> ans = new HashMap<String,String>();  //结果
        String temp = in.nextLine();

        //输入字典,如果有参数则为true；如果无参数则为false。
        for (int i = 0;i < temp.length();i++){
            if (i+1 < temp.length() && temp.charAt(i+1) == ':'){
                map.put("-"+ temp.charAt(i),true);
                i++;
            }else{
                map.put("-"+ temp.charAt(i),false);
            }
        }
        int n = in.nextInt();
        in.nextLine();   //如果没有这一句，这nextInt后留下一个回车符在缓冲区中，会被for循环中nextline读取，就会少一次循环。所以要多一句读取缓冲区中的字符。
        String[] strings = new String[n];
        for (int i = 0;i < n;i++){
            strings[i] = in.nextLine();
        }

        //开始判断
        for (int i = 0;i < n;i++){
            String[] arrs = strings[i].split(" ");
            for (int j = 1;j < arrs.length;j++){
                if (map.containsKey(arrs[j])){
                    if (j+1 < arrs.length && map.get(arrs[j]) && arrs[j+1].matches("[a-z0-9-]+")){  //有参数。
                        ans.put(arrs[j],arrs[j+1]);
                    }else if(!map.get(arrs[j])){  //没有参数，直接填入。
                        ans.put(arrs[j],"");
                    }
                    else break;  //需要参数，没给参数。
                }else break;
            }


            System.out.print("Case" + (i+1) + ":");
            //按升序对结果集进行输出
            for (char c = 'a' ;c <= 'z';c++){
                if (ans.containsKey("-" + c)){
                    System.out.print("-" + c + " ");
                    //如果有参数
                    if (!ans.get("-" + c).equals("")){
                        System.out.print(ans.get("-" + c) + " ");
                    }
                }
            }

            System.out.println();
            ans.clear();
        }
    }
}