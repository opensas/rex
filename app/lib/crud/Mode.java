package lib.crud;

public class Mode {

	public String name = "new mode";
	public String label = "new mode";
	
	public Mode(String name, String label) {
		super();
		this.name = name;
		this.label = label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Mode other = (Mode) obj;
		if (label == null) {
			if (other.label != null) return false;
		} else if (!label.equals(other.label)) return false;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mode [name=" + name + ", label=" + label + "]";
	}
	
	
	
	
	
}
