package aop.Javassist;

import javassist.*;

/**
 * <p>Title:Javassist</p>
 * <p>Description: TODO</p>
 *
 * @author tianxiao.liu
 * @date 2019-10-16 14:24
 */
public class Javassist {

    public static void main(String[] args) throws Throwable {

        //获取存放CtClass的容器ClassPool
        ClassPool cp = ClassPool.getDefault();
        //创建一个类加载器
        Loader cl = new Loader();
        //增加一个转换器
        cl.addTranslator(cp, new JavassistTranslator());
        //启动MyTranslator的main函数
        cl.run("aop.Javassist.Javassist$JavassistTranslator", args);
    }


    public static class JavassistTranslator implements Translator {


        public void start(ClassPool classPool) throws NotFoundException, CannotCompileException {

            System.out.println("__start");

        }
        /* *
         * 类装载到JVM前进行代码织入
         */
        public void onLoad(ClassPool classPool, String s) throws NotFoundException, CannotCompileException {
            System.out.println("__onLoad");
            System.out.println(s);
            if (!"aop.Javassist.Buessine".equals(s)) {
                return;
            }
            //通过获取类文件
            try {
//                CtClass是一个class文件的抽象描述
                CtClass  cc = classPool.get(s);
                //获得指定方法名的方法
                CtMethod m = cc.getDeclaredMethod("doSomething");
                //在方法执行前插入代码
                m.insertBefore("{ System.out.println(\"记录日志\"); }");
//                m.insertAfter("{ System.out.println(\"记录日志\"); }");

            } catch (NotFoundException e) {
            } catch (CannotCompileException e) {
            }

        }
        public static void main(String[] args) {
            Buessine b = new Buessine();
            b.doSomething2("myin2");
            b.doSomething();
        }
    }
}
