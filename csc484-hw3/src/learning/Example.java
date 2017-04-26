package learning;

import java.io.Serializable;
import java.util.HashMap;

public class Example implements Serializable {

	private static final long serialVersionUID = -7506939979322304314L;

	public String action;
	public HashMap<String, Boolean> attributes;

	public Example(String action) {
		this.action = action;
		attributes = new HashMap<String, Boolean>();
	}

}
