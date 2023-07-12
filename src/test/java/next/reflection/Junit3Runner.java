package next.reflection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Junit3Runner {

    @ElapsedTime
    @Test
    @DisplayName("test로 시작하는 메소드만 실행한다")
    public void runner() throws Exception {
        Class clazz = Junit3Test.class;
        // 모든 메소드 이름 가져오기
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            runMethodStartWithTest(clazz.getConstructor().newInstance(), method);
        }
    }

    private void runMethodStartWithTest(Object obj, Method method) {
        String methodName = method.getName();
        // test로 시작하면 실행하기
        if (methodName.startsWith("test")) {
            try {
                method.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
