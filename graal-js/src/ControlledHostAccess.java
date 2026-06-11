import org.graalvm.polyglot.*;

public class ControlledHostAccess {
    public static void main(String[] args) {
	try (Context context = Context.create()) {
	    Services services = new Services();
	    context.getBindings("js")
		.putMember("services", services);
	    String name = context.eval("js",
		"let emp = services.createEmployee('John Doe');" +
				       "emp.getName()").asString();
	    assert name.equals("John Doe");

	    try {
	    context.eval("js", "services.exitVM()");
	    assert false;
	} catch (PolyglotException e) {
	    System.out.println("Got expected exception");
	    assert e.getMessage().endsWith("Unknown identifier: exitVM");
	    e.printStackTrace();
	}

	}

 }

    public static class Employee {
	private final String name;

	Employee(String name) { this.name = name; }

	@HostAccess.Export
	    public String getName() {
	    return this.name;
	}
    }

    public static class Services {
	@HostAccess.Export
	    public Employee createEmployee(String name) {
	    return new Employee(name);
	}

	public void exitVM() {
	    System.exit(1);
	}
    }
}

