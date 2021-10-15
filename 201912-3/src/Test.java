import java.util.LinkedList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> list = new LinkedList<String>();
        list.add("H");
        list.set(0,"H"+"2");
        System.out.println(list);
    }
}
