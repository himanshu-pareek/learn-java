import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;

import static java.lang.System.out;

enum ClassMember {
  CONSTRUCTOR,
  FIELD,
  METHOD,
  CLASS,
  ALL
}

public class ClassSpy {
  public static void main (String[] args) {
    try {

      Class<?> c = Class.forName(args[0]);
      out.printf("Class:\n\t%s\n\n", c.getCanonicalName());

      Package p = c.getPackage();
      out.printf("Package:\n\t%s\n\n", (p == null ? "-- No Package --" : p.getName()));

      for (int i = 1; i < args.length; i++) {
        switch (ClassMember.valueOf(args[i])) {
          case CONSTRUCTOR:
            printMembers (c.getConstructors(), "Constructor");
            break;
          case FIELD:
            printMembers (c.getFields(), "Fields");
            break;
          case METHOD:
            printMembers (c.getMethods(), "Methods");
            break;
          case CLASS:
            printClasses(c);
            break;
          case ALL:
            printMembers (c.getConstructors(), "Constructors");
            printMembers (c.getFields(), "Fields");
            printMembers (c.getMethods(), "Methods");
            printClasses (c);
            break;
          default:
            assert false;
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void printMembers (Member[] members, String label) {
    out.printf ("%s:\n", label);

    for (Member member: members) {
      if (member instanceof Constructor constructor) {
        out.printf ("\t%s\n", constructor.toGenericString());
      } else if (member instanceof Field field) {
        out.printf ("\t%s\n", field.toGenericString());
      } else if (member instanceof Method method) {
        out.printf ("\t%s\n", method.toGenericString());
      }
    }

    if (members.length == 0) {
      out.printf (" -- No %s --\n", label);
    }

    out.printf ("\n");
  }

  private static void printClasses (Class<?> clazz) {
    out.printf ("Classes:\n");
    Class<?>[] classes = clazz.getClasses();
    for (Class<?> cls: classes) {
      out.printf ("\t%s\n", cls.getCanonicalName());
    }

    if (classes.length == 0) {
      out.printf (" -- No member interfaces, classes, or enums --\n");
    }

    out.printf("\n");
  }
}

