import org.graalvm.polyglot.*;

public class HostAccessDisableMethodScope {
    public static class Service {
	Value result;
	Value metadata;

	@HostAccess.Export
	// @HostAccess.DisableMethodScope
	public void callback(Value result, Value metadata) {
	    this.result = result;
	    this.metadata = metadata;
	}

	String getResult() {
	    return this.result.asString() + ": " + this.metadata.asString();
	}
    }

    public static void main(String[] args) {
	Service s = new Service();
	try (Context context = Context.newBuilder().allowHostAccess(HostAccess.SCOPED).build()) {
	    context.getBindings("js").putMember("services", s);
	    context.eval("js", "services.callback('Hello from JS', 'foobar');");
	    System.out.println(s.getResult());
	}
    }
}
	
