import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Test {


    /*请完成下面这个函数，实现题目要求的功能
    当然，你也可以不按照下面这个模板来作答，完全按照自己的想法来 ^-^
    ******************************开始写代码******************************/
    static int calcMinStaff(int times,String input) {
        System.out.println("hello");
        return 0;
    }
    /******************************结束写代码******************************/


    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        int res;
        String a="";
        int i = in.nextInt();
        for (int j = 0; j < i; j++) {
            a= in.next();
            System.out.println(a.endsWith(","));
        }
        res = calcMinStaff(i,a);
        System.out.println(String.valueOf(res));

    }
}