import org.graalvm.polyglot.*;

public class HostAccessExplicit {
    public static class Services {
	Value lastResult;

	@HostAccess.Export
	public void callback(Value result) {
	    this.lastResult = result;
	}

	String getResult() {
	    return this.lastResult.asString();
	}
    }

    public static void main(String[] args) {
	Services s = new Services();

	try (Context context = Context.newBuilder().allowHostAccess(HostAccess.EXPLICIT).build()) {
	    context.getBindings("js").putMember("services", s);
	    context.eval("js", "services.callback('Hello from JS!');");
	    System.out.println(s.getResult());
	}
    }
}
