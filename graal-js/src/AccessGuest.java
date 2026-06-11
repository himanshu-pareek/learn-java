import java.util.Objects;
import org.graalvm.polyglot.*;

public class AccessGuest {
  public static void main(String[] args) {
    try (Context context = Context.create("js")) {
      String scriptCode = """
      ({
          id: 1,
          name: "Himanshu Pareek",
          skills: ["Do computer things", "Sleep", "Eat", 1, 2, 3],
          address: {
            lineOne: "D 23, Hill Street",
            lineTwo: "River Town",
            pin: 123456,
          },
        })
      """;
      Value person = context.eval("js", scriptCode);
      assert person.hasMembers();
      System.out.println("Members: " + person.getMemberKeys());

      int id = person.getMember("id").asInt();
      assert id == 1;

      String name = person.getMember("name").asString();
      assert Objects.equals(name, "Himanshu Pareek");

      Value address = person.getMember("address");
      assert address.hasMembers();
      System.out.println("Address members: " + address.getMemberKeys());

      String addressLineOne = address.getMember("lineOne").asString();
      assert Objects.equals(addressLineOne, "D 23, Hill Street");

      String addressLineTwo = address.getMember("lineTwo").asString();
      assert Objects.equals(addressLineTwo, "River Town");

      int addressPin = address.getMember("pin").asInt();
      assert addressPin == 123456;
    }
  }
}

