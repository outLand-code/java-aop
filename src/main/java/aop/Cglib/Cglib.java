package aop.Cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * <p>Title:Cglib</p>
 * <p>Description: TODO</p>
 *
 * @author tianxiao.liu
 * @date 2019-10-16 13:54
 */
public class Cglib {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Buessine.class);
        //设置需要织入的逻辑
        enhancer.setCallback(new CglibInterceptor());
        Buessine newBusiness = (Buessine) enhancer.create();
        //使用织入器创建子类,可以不需要接口
        newBusiness.doSomething2("myin");
    }


    public static class CglibInterceptor implements MethodInterceptor {



        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            //执行原有逻辑，注意这里是invokeSuper
            Object rev = methodProxy.invokeSuper(o, objects);
            //执行织入的日志
            if (method.getName().equals("doSomething2")) {
                System.out.println("记录日志");
            }
            return rev;
        }
    }
}
