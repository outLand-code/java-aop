package aop.Instrumentation;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * <p>Title:MyClassFileTransformer</p>
 * <p>Description: 需要告诉JVM在启动main函数之前，需要先执行premain函数。首先需要将premain函数所在的类打成jar包。并修改该jar包里的META-INF\MANIFEST.MF 文件。 </p>
 *
 * @author tianxiao.liu
 * @date 2019-10-16 14:49
 */
public class MyClassFileTransformer implements ClassFileTransformer {



    public static void premain(String options, Instrumentation ins) {
        //注册我自己的字节码转换器
        ins.addTransformer(new MyClassFileTransformer());
    }

    /**
     * 字节码加载到虚拟机前会进入这个方法
     */
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        System.out.println(className);
        //Buessine
        if (!"aop/Instrumentation/Buessine".equals(className)) {
            return null;
        }


        //javassist的包名是用点分割的，需要转换下
        if (className.indexOf("/") != -1) {
            className = className.replaceAll("/", ".");
        }

        try {
            //通过包名获取类文件
            CtClass cc = ClassPool.getDefault().get(className);
            //获得指定方法名的方法
            CtMethod m = cc.getDeclaredMethod("doSomething");
            //在方法执行前插入代码
            m.insertBefore("{ System.out.println(\"记录日志\"); }");
            return cc.toBytecode();
        } catch (NotFoundException e) {
        } catch (CannotCompileException e) {
        } catch (IOException e) {
            //忽略异常处理
        }
        return null;
    }
}
