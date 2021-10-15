import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
    private static List<Rule> list=new ArrayList<>();
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        String line=in.nextLine();
        String[] linetemp=blankSplit(line);
        int n=Integer.parseInt(linetemp[0]);
        int m=Integer.parseInt(linetemp[1]);
        for (int i=0;i<n;i++){
            String line2=in.nextLine();
            String[] tempstr=blankSplit(line2);
            String rule=tempstr[0];
            String name=tempstr[1];
            Rule rule1=new Rule(rule,name);
            list.add(rule1);
        }
        for (int j=0;j<m;j++) {
            String temp=in.nextLine();
            boolean flag=false;
            for (int k=0;k<n;k++){
                String rule=list.get(k).rule;
                if(match(rule,temp,false)){
                    flag=true;
                    System.out.print(list.get(k).name);
                    match(rule,temp,true);
                    System.out.println();
                    break;
                }
            }
            if (!flag){
                System.out.println(404);
            }
        }
    }
    private static Boolean match(String rule, String url,boolean flag) {
        int k1 = 0, k2 = 0, len1 = rule.length(), len2 = url.length();
        while (k1 < len1 && k2 < len2) {
            if (rule.charAt(k1) == url.charAt(k2)) {
                k1++;
                k2++;
                continue;
            }
            if (rule.charAt(k1++) != '<') {
                return false;
            }
            if (flag) {
                System.out.print(" ");
            }
            if (rule.charAt(k1) == 'i') {
                boolean flag2 = false;
                while (k2 < url.length() && url.charAt(k2) != ' ' && url.charAt(k2) >= '0' && url.charAt(k2) <= '9') {
                    if (url.charAt(k2) > '0') {
                        flag2 = true;
                    }
                    if (flag && flag2) {
                        System.out.print(url.charAt(k2));
                    }
                    k2++;
                }
                if (!flag2) {
                    return false;
                }
                k1 += 4;
                continue;
            }
            if (rule.charAt(k1) == 's') {
                boolean flag2 = false;
                while (k2 < url.length() && url.charAt(k2) != '/') {
                    flag2 = true;
                    if (flag) {
                        System.out.print(url.charAt(k2));
                    }
                    k2++;
                }
                if (!flag2) {
                    return false;
                }
                k1 += 4;
                continue;
            }
            if (rule.charAt(k1) == 'p') {
                if (flag) {
                    while (k2 < url.length()) {
                        System.out.print(url.charAt(k2++));
                    }
                }
                return true;
            }

        }
        return (k1 == len1 && k2 == len2);
    }


    static class Rule{
        String rule;
        String name;

        public Rule(String rule, String name) {
            this.rule = rule;
            this.name = name;
        }
    }

    private static String[] blankSplit(String temp){
        String[] words=temp.split(" ");
        return words;
    }
}