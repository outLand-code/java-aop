package aop.dynamic;

import sun.misc.ProxyGenerator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**

 */
public class Dynamic {

    public static void main(String[] args) {
        Class[] proxyInterface =new Class[]{IBuessine.class,IBuessine2.class};
        DynamicInvocationHandler handler =new DynamicInvocationHandler(new Buessine());
        ClassLoader classLoader = ProxyDemo.class.getClassLoader();
        IBuessine2  proxyIBuessine=(IBuessine2) Proxy.newProxyInstance(classLoader,proxyInterface,handler);
        proxyIBuessine.doSomething2("myin");
        ((IBuessine) proxyIBuessine).doSomething();

    }


    public static class DynamicInvocationHandler implements InvocationHandler {

        private Object target; //目标对象

        DynamicInvocationHandler(Object target) {
            this.target = target;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


//            System.out.println(proxy.toString());
            System.out.println(Arrays.toString(args));
            //执行原有逻辑
            Object rev = method.invoke(target, args);
            //执行织入的日志，你可以控制哪些方法执行切入逻辑
            if (method.getName().equals("doSomething")) {
                System.out.println("记录日志");
            }
            return rev;
        }
    }
}
