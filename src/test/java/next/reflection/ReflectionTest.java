package next.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import next.optional.Student;
import next.optional.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("테스트1: 리플렉션을 이용해서 클래스와 메소드의 정보를 정확하게 출력해야 한다.")
    public void showClass() {
        SoftAssertions s = new SoftAssertions();
        Class<Question> clazz = Question.class;
        logger.debug("Classs Name {}", clazz.getName());
    }

    @Test
    public void constructor() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }
        }
    }

    @Test
    @DisplayName("Question 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다")
    void showQuestion() {
        logger.debug("-----------------Class-------------------");
        Class<Question> clazz = Question.class;

        logger.debug("Class Name {}", clazz.getName());
        logger.debug("Class Modifiers {}", clazz.getModifiers());

        logger.debug("------------------Field-------------------");

        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            logger.debug("Field Name {}", f.getName());
            logger.debug("Field Modifiers {}", f.getModifiers());
        }

        logger.debug("-----------------Constructor---------------");

        Constructor[] constructors = clazz.getConstructors();
        for (Constructor c : constructors) {
            logger.debug("Constructor Name {}", c.getName());
            logger.debug("Constructor Modifiers {}", c.getModifiers());
            Class[] parameterTypes = c.getParameterTypes();
            logger.debug("Param Length {}", parameterTypes.length);
            for (Class p : parameterTypes) {
                logger.debug("Param Type {}", p);
            }
        }
    }

    @Test
    @DisplayName("private field에 값을 할당한다")
    void setPrivateField() {
        Class<Student> clazz = Student.class;

        Student student = new Student();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            setField(student, field);
        }

        logger.debug("Student Name : {}", student.getName());
        logger.debug("Student Age : {}", student.getAge());
    }

    private void setField(Student obj, Field field) {
        try {
            Class type = field.getType();

            if (type.equals(int.class)) {
                field.set(obj, 10);
            } else if (type.equals(String.class)) {
                field.set(obj, "학생");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스를 생성한다.")
    void createConstructorInstance() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Class<User> clazz = User.class;

        Constructor constructor = clazz.getDeclaredConstructor(String.class, Integer.class);
        User user = (User) constructor.newInstance("재성", 25);

        logger.debug("User Name {}", user.getName());
        logger.debug("User Age {}", user.getAge());
    }

    @Test
    @DisplayName("@ElapsedTime 애노테이션이 있는 메소드의 수행시간을 측정한다.")
    void measureElapsedTime() {
        Class<Junit3Runner> clazz = Junit3Runner.class;

        Method[] methods = clazz.getDeclaredMethods();

        for (Method m : methods) {
            if (m.isAnnotationPresent(ElapsedTime.class)) {
                long startTime = System.currentTimeMillis();
                try {
                    m.invoke(clazz.getConstructor().newInstance());
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                long endTime = System.currentTimeMillis();
                logger.debug("수행 시간 : {}sec", (endTime - startTime) / 1000.0);
            }
        }
    }
}
