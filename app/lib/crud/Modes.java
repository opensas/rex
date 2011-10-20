package lib.crud;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Modes implements Iterable<Mode>, Map<String, Mode> {

	private Map<String, Mode> modes;

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return modes.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return modes.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return modes.containsValue(value);
	}

	@Override
	public Mode get(Object key) {
		return modes.get(key);
	}

	@Override
	public Mode put(String key, Mode value) {
		return modes.put(key, value);
	}

	@Override
	public Mode remove(Object key) {
		return modes.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Mode> m) {
		modes.putAll(m);
		
	}

	@Override
	public void clear() {
		modes.clear();
	}

	@Override
	public Set<String> keySet() {
		return modes.keySet();
	}

	@Override
	public Collection<Mode> values() {
		return modes.values();
	}

	@Override
	public Set<Entry<String, Mode>> entrySet() {
		return modes.entrySet();
	}

	@Override
	public Iterator<Mode> iterator() {
		return modes.values().iterator();
	}
	
}
