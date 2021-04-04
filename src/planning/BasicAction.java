package planning;

import java.util.HashMap;
import java.util.Map;

import representation.Variable;
/**
 * la classe BasicAction qui représente une Action
 * @author 
 *
 */
public class BasicAction implements Action {
	
	private Map<Variable, Object> precondition;
	private Map<Variable, Object> effet;
	private String name;
	private int cout;
	
	/**
	 * 
	 * @param precondition
	 * @param effet
	 * @param cout
	 */
	public BasicAction(Map<Variable, Object> precondition,Map<Variable, Object> effet, int cout) {
		this.precondition = precondition;
		this.effet = effet;
		this.cout  = cout;
	}
	/**
	 * 
	 * @param name
	 * @param precondition
	 * @param effet
	 * @param cout
	 */
	public BasicAction(String name,Map<Variable, Object> precondition,Map<Variable, Object> effet, int cout) {
		this(precondition,effet,cout);
		this.name = name;
		
	}
	
	@Override
	public boolean isApplicable(Map<Variable, Object> etat) {
		return etat.entrySet().containsAll(this.precondition.entrySet());
	}
	
	@Override
	public Map<Variable, Object> successor(Map<Variable, Object> etat) {
		if(isApplicable(etat)) {
			Map<Variable, Object> map = new HashMap<Variable,Object>(etat);
			map.putAll(this.effet);
			return map;
		}else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public int getCost() {
		return this.cout;
	}
	@Override
	public String toString() {
		return name +"[precondition=" + precondition + ", effet=" + effet + ", cout=" + cout + "]";
	}
	
}
