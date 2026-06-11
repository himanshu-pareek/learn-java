import org.graalvm.polyglot.*;

public class Hello {
  public static void main(String[] args) {
    String name = args.length == 0 ? "World" : args[0];
    try (Context context = Context.create("js")) {
      Value greet = context.eval("js", """
          name => {
            console.log("Hello " + name + " from JS!");
          }
          """);
      greet.execute(name);

    }
  }
}

