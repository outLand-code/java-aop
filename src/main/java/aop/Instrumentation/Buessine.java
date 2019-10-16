package aop.Instrumentation;

/**
 * <p>Title:aop.dynamic.Buessine</p>
 * <p>Description: TODO</p>
 *
 * @author tianxiao.liu
 * @date 2019-10-16 11:57
 */
public class Buessine  {
    public boolean doSomething() {
        System.out.println("doSomething");
        return true;
    }

    public void doSomething2(String in){
        System.out.println("doSomething2");
        System.out.println(in);

    }
}
