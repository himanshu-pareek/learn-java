import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.lang.reflect.Field;
import java.lang.NoSuchFieldException;

public class ClassObjectDemo {
  public static void main(String[] args) throws ClassNotFoundException {
    System.out.println ("Object.getClass()");
    System.out.println ("foo".getClass());
    System.out.println ("foo".getClass().getClass());
    System.out.println ("foo".getClass().getClass().getClass());
    enum E {A, B}
    E.A.getClass();
    byte[] bytes = new byte[1024];
    System.out.println (bytes.getClass());
    Set<String> set = new HashSet<>();
    System.out.println (set.getClass());

    System.out.println ("\n\nThe .class syntax");
    System.out.println (boolean.class);
    System.out.println (java.io.PrintStream.class);
    System.out.println (int[][][].class);

    System.out.printf ("\n\nClass.forName and Class.getName\n");
    System.out.println (Class.forName("java.util.Locale"));
    System.out.println (Class.forName("[D"));
    System.out.println (Class.forName("[[Ljava.lang.String;"));

    System.out.println("\n\nTYPE Field for Primitive Type Wrappers");
    System.out.println (Double.TYPE);
    System.out.println (Void.TYPE);

    System.out.println ("\n\nMethods that Return Classes");
    System.out.println (javax.swing.JButton.class.getSuperclass());
    System.out.println (Arrays.asList(Character.class.getClasses()));
    class B {

      public class C {}

      public interface I {}

      public enum F {}

      private class D {}
    }

    System.out.println(Arrays.asList(B.class.getClasses()));
    System.out.println(Arrays.asList(Character.class.getDeclaredClasses()));
    System.out.println(Arrays.asList(B.class.getDeclaredClasses()));

    class MyClass {
      static Object o = new Object() {
        public void m() {}
      };
    }

    try {
      Field f = System.class.getField ("out");
      System.out.println ("Declaring class of out: " + f.getDeclaringClass());
      System.out.println ("Declaring class of MyClass.o: " + MyClass.o.getClass().getDeclaringClass());
    } catch (NoSuchFieldException e) {
      System.out.println ("ERROR: " + e.getMessage());
    }

    System.out.println ("Enclosing class of Thread.State: " + Thread.State.class.getEnclosingClass());
    System.out.println ("Enclosing class of MyClass.o: " + MyClass.o.getClass().getEnclosingClass());
  }
}

