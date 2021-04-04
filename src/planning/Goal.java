package planning;

import representation.*;
import java.util.Map;

public interface Goal {

	/**
	 * vérifie si un etat satisfait le goal 
	 * @param etat
	 * @return
	 */
	public boolean isSatisfiedBy(Map<Variable, Object> etat);
	
}
