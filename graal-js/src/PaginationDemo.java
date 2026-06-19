import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.graalvm.polyglot.*;

public class PaginationDemo {
    public static class Wallet {
	private static final int BATCH_SIZE_TRANSACTION = 5;
	
	private final String name;
	private final int numTransactions;
	public Wallet(String name, int numTransactions) {
	    this.name = name;
	    this.numTransactions = numTransactions;
 	}

	public String getName() {
	    return this.name;
	}

	public Iterable<String> getTransactions() {
	    List<String> result = new ArrayList<>();
	    for (int i = 0; i < this.numTransactions; i++) {
		result.add ("Transaction " + (i + 1));
	    }
	    return result;
	}

	@Override
	public String toString() {
	    return "Wallet { name: " + this.name + " }";
	}

    }

    public class WalletIterator implements Iterator<String> {
	private static final BATCH_SIZE = 5;
	private final Wallet wallet;
	private final List<String> batch;
	private int nextIndex;
	private IteratorState state;

	public WalletIterator(Wallet wallet) {
	    this.wallet = wallet;
	    this.batch = new ArrayList<>();
	    this.nextIndex = 0;
	    this.state = IteratorState.INITIALIZED;
	}

	private 

	@Override
	public boolean hasNext() {
	    return this.batch.size() < BATCH_SIZE;
	}
    
    public static void main(String[] args) {
	try (Context context = Context.newBuilder().allowHostAccess(true).build()) {
	    context.getBindings("js").putMember("wallet", new Wallet("My Wallet", 10));
	    StringBuilder jsScript = new StringBuilder();
	    jsScript.append("console.log(`Transactions in wallet: ${wallet.getName()}`);\n");
	    jsScript.append("for (let transaction of wallet.getTransactions()) {\n");
	    jsScript.append("\tconsole.log(transaction);\n");
	    jsScript.append("}\n");
	    System.out.println("-------- Executing js script -----------");
	    System.out.println(jsScript);
	    context.eval("js", jsScript);
	    System.out.println("--------- Done -------------");
	}
    }
}
	
