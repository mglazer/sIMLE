package jpl.simle.domain;

import java.util.Collection;
import java.util.Iterator;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("labs")
public class Labs implements Collection<Lab>
{
	@XStreamImplicit(itemFieldName="lab")
	private Collection<Lab> labs;
	
	public Labs(Collection<Lab> delegate)
	{
		labs = delegate;
	}

	public Iterator<Lab> iterator() {
		return labs.iterator();
	}

	public boolean add(Lab o) {
		return labs.add(o);
	}

	public boolean addAll(Collection<? extends Lab> c) {
		return labs.addAll(c);
	}

	public void clear() {
		labs.clear();
	}

	public boolean contains(Object o) {
		return labs.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return labs.containsAll(c);
	}

	public boolean isEmpty() {
		return labs.isEmpty();
	}

	public boolean remove(Object o) {
		return labs.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return labs.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return labs.retainAll(c);
	}

	public int size() {
		return labs.size();
	}

	public Object[] toArray() {
		return labs.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return labs.toArray(a);
	}
}
