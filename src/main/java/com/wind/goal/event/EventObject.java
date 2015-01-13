package com.wind.goal.event;

/**
 * 事件源
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-10
 */
public class EventObject implements java.io.Serializable {

	private static final long serialVersionUID = -3373876335992408660L;
	/**
	 * 事件源
	 */
	protected transient Object source;

	public EventObject() {}

	public EventObject(Object source) {
		if (source == null) throw new IllegalArgumentException("null source");

		this.source = source;
	}

	public Object getSource() {
		return source;
	}

	public String toString() {
		return getClass().getName() + "[source=" + source + "]";
	}
}
