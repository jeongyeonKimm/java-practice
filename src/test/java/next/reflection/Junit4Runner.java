package next.reflection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import org.slf4j.Logger;

public class Junit4Runner {
    @Test
    @DisplayName("@MyTest 애노테이션으로 설정되어 있는 메소드만 실행한다.")
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        Method[] methods = clazz.getDeclaredMethods();

        for (Method m : methods) {
            if (m.isAnnotationPresent(MyTest.class)) {
                m.invoke(clazz.newInstance());
            }
        }
    }
}


