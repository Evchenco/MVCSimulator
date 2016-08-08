package com.simulator.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Model {

	private Map<String, Object> params = Collections.synchronizedMap(new HashMap<String, Object>());

	public void addParam(String paramName, Object value) {
		params.put(paramName, value);
	}

	public void remove(String paramName) {
		params.remove(paramName);
	}

	public Map<String, Object> getParams() {
		return new HashMap(params);
	}
}
