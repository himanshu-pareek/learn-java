import org.graalvm.polyglot.*;

public class HostAccessScoped {
    public static class Services {
	Value lastResult;

	@HostAccess.Export
	public void callback(Value result, Value notneeded) {
	    this.lastResult = result;
	    this.lastResult.pin();
	}

	String getResult() {
	    return this.lastResult.asString();
	}
    }

    public static void main(String[] args){
	Services s = new Services();

	try (Context context = Context.newBuilder().allowHostAccess(HostAccess.SCOPED).build()) {
	    context.getBindings("js").putMember("services", s);
	    context.eval("js", "services.callback('Hello from JS', 'feebar');");
	    System.out.println(s.getResult());
	}
    }
}
