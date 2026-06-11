import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;

public class ComputedArrayDemo {

  static class ComputedArray implements ProxyArray {
    public Object get(long index) {
      return index * 2;
    }

    public void set(long index, Value value) {
      throw new UnsupportedOperationException();
    }

    public long getSize() {
      return 10;
    }
  }

  public static void main(String[] args) {
    try (Context context = Context.create()){
      ComputedArray arr = new ComputedArray();
      context.getBindings("js").putMember("arr", arr);
      Value value = context.eval("js", """
          let result = 0;
          for (let element of arr) {
            result += element;
          }
          result
          """);
      System.out.println("Value: " + value);
      // System.out.println("Result: " + result);
    }
  }
}

