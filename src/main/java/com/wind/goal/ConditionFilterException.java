package com.wind.goal;

/**
 * 条件过滤异常
 * 
 * @author zhouyanjun
 * @version 1.0 2015-1-13
 */
public class ConditionFilterException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -174701367882665809L;

	public ConditionFilterException() {
		super();
	}

	public ConditionFilterException(String message) {
		super(message);
	}

	public ConditionFilterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConditionFilterException(Throwable cause) {
		super(cause);
	}
}
