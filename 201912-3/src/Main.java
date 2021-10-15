import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static Map<String, Integer> lele=new TreeMap<>();
    static Map<String, Integer> rele=new TreeMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
        String[] line=bf.readLine().split(" ");

        int n=Integer.parseInt(line[0]);

        for(int i=0;i<n;i++) {

            line=bf.readLine().split("\\=");//分割方程式两边

            manage(line[0],lele);//分割方程式内部
            manage(line[1],rele);//分割方程式内部

            if(marksure(lele,rele)) {
                System.out.println("Y");
            }else {
                System.out.println("N");
            }

            lele.clear();
            rele.clear();

        }

    }

    static boolean marksure(Map<String,Integer> lele,Map<String,Integer> rele) {//判断两端是否相等
        Set<String> set=lele.keySet();
        Set<String> set1=rele.keySet();
        Iterator<String> it=set.iterator();
        Iterator<String> it1=set1.iterator();

        while(it.hasNext()) {
            String str1=(String)it.next();
            if(!rele.containsKey(str1) || (!lele.get(str1).equals(rele.get(str1)))) {
                return false;
            }
        }

        while(it1.hasNext()) {
            String str2=(String)it1.next();
            if(!lele.containsKey(str2)) {
                return false;
            }
        }

        return true;
    }

    static void manage(String line,Map<String ,Integer> tempele) {//做数据处理

        String[] str=line.split("\\+");//切割为单个化学方程式

        List<String> list=new LinkedList<>();

        for(int i=0;i<str.length;i++) {

            list=split(str[i],list);
            //System.out.println(list);
            sum(list,tempele);

            list.clear();

        }

    }

    static void sum(List<String> list,Map<String ,Integer> tempele) {//元素求和
        int factor=1,temp=0;
        int[] num=new int[1000];

        if(isNumber(list.get(0))) {//如果公式第一个是数组
            factor*=Integer.parseInt(list.get(0));
        }

        for(int i=0;i<list.size();i++) {
            if(isUpper(list.get(i))) {//处理元素
                if((list.size()-1)==i) {
                    num[temp++]=1;
                }else{
                    if(isNumber(list.get(i+1))) {
                        num[temp++]=Integer.parseInt(list.get(i+1));
                    }else {
                        num[temp++]=1;
                    }
                }

                int value=factor;

                for(int j=0;j<temp;j++) {
                    value*=num[j];
                }

                if(tempele.containsKey(list.get(i))) {
                    tempele.put(list.get(i), tempele.get(list.get(i))+value);
                }else {
                    tempele.put(list.get(i), value);
                }
                num[temp--]=0;
            }

            if(list.get(i).equals("(")) {//判断处理"("
                int index=1;

                for(int j=i+1;j<list.size()-1;j++) {
                    if(list.get(j).equals("(")) {
                        index++;
                    }else if(list.get(j).equals(")") && index!=1) {
                        index--;
                    }else if(list.get(j).equals(")") && index==1) {
                        if(j==(list.size()-1)) {
                            num[temp++]=1;
                            break;
                        }
                        if(isNumber(list.get(j+1))) {
                            num[temp++]=Integer.parseInt(list.get(j+1));
                        }else {
                            num[temp++]=1;
                        }
                        break;
                    }
                }

                if(list.get(list.size()-1).equals(")")) {
                    num[temp++]=1;
                    //内循环
                }
            }

            if(list.get(i).equals(")")) {//判断处理")"
                num[temp--]=0;
            }

        }//统计个数
        num=null;
    }

    /**
     *
     * @param str
     * @param list
     * @return
     * add有两种：boolean add（E e），在list末尾插入。
     * void add(int index,E element),可指定插入的位置，但是index不能大于此时list的容量
     *
     * set方法是替换list中某个位置的值，但是这个位置必须有元素，否则会抛出异常。
     */
    static List<String> split(String str,List<String> list) {//分割为单个元素
        int index=0,temp=0;
        String[] ele=str.split("");

        loop:for(int i=0;i<ele.length;i++) {
            if(isNumber(ele[i])&& temp==0) {
                list.add(index, ele[i]);
                temp++;
            }else if(isNumber(ele[i])&& temp!=0){
                list.set(index-1, list.get(index-1)+ele[i]);
                continue loop;
            }else {
                if(isUpper(ele[i])) {
                    list.add(index, ele[i]);
                }else if(isLower(ele[i])) {
                    list.set(index-1,ele[i-1]+ele[i]);
                    continue loop;
                }else if(ele[i].equals("(")||ele[i].equals(")")){
                    list.add(index,ele[i]);
                }
                temp=0;
            }
            index++;
        }//处理成单个元素和数字
        return list;
    }

    static boolean isNumber(String str) {//判断是否数字
        if(str.charAt(0)>='0' && str.charAt(0)<='9') {
            return true;
        }
        return false;
    }

    static boolean isLower(String str) {//判断小写
        if(str.charAt(0)>='a' && str.charAt(0)<='z') {
            return true;
        }
        return false;
    }

    static boolean isUpper(String str) {//判断大写
        if(str.charAt(0)>='A' && str.charAt(0)<='Z') {
            return true;
        }
        return false;
    }
}