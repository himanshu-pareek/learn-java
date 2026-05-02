import java.util.*;
import java.util.function.*;

public interface MyStream<T> {
    static <T> MyStream<T> of(Collection<T> source) {
	return new MyCollectionStream<>(source);
    }

    public void forEach(Consumer<T> consumer);

    public <U> MyStream<U> map(Function<T, U> mapper);

    public MyStream<T> filter(Predicate<T> tester);

    public List<T> toList();	
}

abstract class MyAbstractStream<T> implements MyStream<T> {
	private boolean operated = false;
	@Override
	public final void forEach(Consumer<T> consumer) {
		validateNotOperated();
		internalForEach(consumer);
	}
	
	@Override
	public final <U> MyStream<U> map(Function<T, U> mapper) {
		validateNotOperated();
		return new MyMapStream<>(this, mapper);
	}

	@Override
	public final MyStream<T> filter(Predicate<T> tester) {
		validateNotOperated();
		return new MyFilterStream<>(this, tester);
	}

	@Override
	public final List<T> toList() {
		List<T> result = new ArrayList<>();
		this.forEach(result::add);
		return result;
	}

	private void validateNotOperated() {
		if (this.operated) {
			throw new IllegalStateException("Stream is already operated or closed");
		}
		this.operated = true;
	}

	abstract protected void internalForEach(Consumer<T> consumer);
}


class MyFilterStream<T> extends MyAbstractStream<T> {
	private final MyAbstractStream<T> parent;
	private final Predicate<T> tester;

	public MyFilterStream(MyAbstractStream<T> parent, Predicate<T> tester) {
		this.parent = parent;
		this.tester = tester;
	}

	@Override
	protected void internalForEach(Consumer<T> consumer) {
		this.parent.internalForEach(t -> {
			if (this.tester.test(t)) {
				consumer.accept(t);
			}
		});
	}
}

class MyMapStream<T, P> extends MyAbstractStream<T> {
    private final MyAbstractStream<P> parent;
    private final Function<P, T> mapper;

    public MyMapStream(MyAbstractStream<P> parent, Function<P, T> mapper) {
	this.parent = parent;
	this.mapper = mapper;
    }

    @Override
    protected void internalForEach(Consumer<T> consumer) {
	this.parent.internalForEach(p -> {
		consumer.accept(this.mapper.apply(p));
	});
    }
}

class MyCollectionStream<T> extends MyAbstractStream<T> {
    private final Collection<T> source;

    public MyCollectionStream(Collection<T> source) {
	this.source = source;
    }

    @Override
    protected void internalForEach(Consumer<T> consumer) {
	this.source.forEach(consumer);
    }
}

