package lib.crud;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Actions implements Iterable<Action>, Map<String, Action> {

	private Map<String, Action> actions;
	
	public Actions() {
		this.actions = new LinkedHashMap<String,Action>();
	}
	
	public Actions add(Action action) {
		this.actions.put(action.name, action);
		return this;
	}
	
	public Actions add(String action, String name, String label, String icon, Boolean isVisible, Boolean isEnabled) {
		this.actions.put(name, new Action(action, name, label, icon, isVisible, isEnabled));
		return this;
	}

	public Actions add(String action, String name, String label) {
		this.actions.put(name, new Action(action, name, label));
		return this;
	}
	
	public Action get(String name) {
		return actions.get(name);
	}
	
	public Actions getVisibles() {
		Actions visibles = new Actions();
		
		for(Action item : actions.values()) {
			if (item.isVisible) visibles.add(item);
		}
		
		return visibles;
	}
	
	public Actions getEnabled() {
		Actions enabled = new Actions();
		
		for(Action item : actions.values()) {
			if (item.isEnabled) enabled.add(item);
		}
		
		return enabled;
	}
	
	public Collection<Action> values() {
		return actions.values();
	}
	
	@Override
	public Iterator<Action> iterator() {
		return actions.values().iterator();
	}

	@Override
	public int size() {
		return actions.size();
	}

	@Override
	public boolean isEmpty() {
		return actions.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return actions.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return actions.containsValue(value);
	}

	@Override
	public Action get(Object key) {
		return actions.get(key);
	}

	@Override
	public Action put(String key, Action value) {
		return actions.put(key, value);
	}

	@Override
	public Action remove(Object key) {
		return actions.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Action> m) {
		actions.putAll(m);
	}

	@Override
	public void clear() {
		actions.clear();
	}

	@Override
	public Set<String> keySet() {
		return actions.keySet();
	}

	@Override
	public Set<java.util.Map.Entry<String, Action>> entrySet() {
		return actions.entrySet();
	}
	
}
