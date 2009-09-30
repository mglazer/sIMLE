package jpl.simle.domain;

import java.util.Collection;
import java.util.Iterator;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("applications")
public class Applications implements Collection<Application> 
{
	@XStreamImplicit(itemFieldName="application")
	private Collection<Application> applications;
	
	public Applications(Collection<Application> applications)
	{
		this.applications = applications;
	}
	
	public Iterator<Application> iterator() 
	{
		return applications.iterator();
	}

	public boolean add(Application o) {
		return applications.add(o);
	}

	public boolean addAll(Collection<? extends Application> c) {
		return applications.addAll(c);
	}

	public void clear() {
		applications.clear();
	}

	public boolean contains(Object o) {
		return applications.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return applications.containsAll(c);
	}

	public boolean isEmpty() {
		return applications.isEmpty();
	}

	public boolean remove(Object o) {
		return applications.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return applications.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return applications.retainAll(c);
	}

	public int size() {
		return applications.size();
	}

	public Object[] toArray() {
		return applications.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return applications.toArray(a);
	}
	
}
