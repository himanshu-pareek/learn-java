import org.graalvm.polyglot.*;

public class HostAccessDisableMethodScope {
    public static class Service {
	Value result;
	Value metadata;

	public void callback(Value result, Value metadata) {
	    this.result = result;
	    this.metadata = metadata;
	}

	String getResult() {
	    return this.result.asString() + ": " + this.metadata.asString();
	}
    }

    public static void main(String[] args) {
	
