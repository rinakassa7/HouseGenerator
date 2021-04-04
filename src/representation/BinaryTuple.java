package representation;

/**
 * construire un couple d'objet
 * @author 
 *
 */
public class BinaryTuple {
	private Object o1,o2;
	
	/**
	 * constructeur de BinaryTuple
	 * @param o1
	 * @param o2
	 */
	public BinaryTuple(Object o1, Object o2) {
		this.o1=o1;
		this.o2=o2;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((o1 == null) ? 0 : o1.hashCode());
		result = prime * result + ((o2 == null) ? 0 : o2.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BinaryTuple other = (BinaryTuple) obj;
		if (o1 == null) {
			if (other.o1 != null)
				return false;
		} else if (!o1.equals(other.o1))
			return false;
		if (o2 == null) {
			if (other.o2 != null)
				return false;
		} else if (!o2.equals(other.o2))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "BinaryTuple [o1=" + o1 + ", o2=" + o2 + "]";
	}

	
}
