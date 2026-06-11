import org.graalvm.polyglot.*;

public class LookTypes {
  public static void main(String[] args) {
    try (Context context = Context.newBuilder().allowAllAccess(true).build()) {
      java.math.BigDecimal decimal = context.eval("js", """
          var BigDecimal = Java.type("java.math.BigDecimal");
          BigDecimal.valueOf(10).pow(20);
          """).asHostObject();
      System.out.println("decimal: " + decimal);
    }
  }
}

