package aop.Instrumentation;

/**

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
