package com.wind.goal;

import com.wind.goal.comparator.ParamComparator.CompareParamVO;

/**
 * 事件参数
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-16
 */
public class ParamterVO {

	static class DataType {
		public static final String INT = "int";
		public static final String STRING = "String";
		public static final String BOOLEAN = "boolean";
		public static final String OBJECT = "Object";
	}

	static class DataOperate {
		public static final String GREAT = ">";
		public static final String EQUAL = "=";
		public static final String LESS = "<";
		public static final String NOT_LESS = ">=";
		public static final String NOT_GREAT = "<=";
	}

	static class DataChangeType {
		public static final String INCREASE = "1";
		public static final String REPLACE = "2";
	}

	private String CName; // 参数英文名
	private String SName; // 参数中文名
	private String CType; // 参数类型(1:int, 2:string,3:boolean,4:Object)
	private String COperate; // 操作类型（>,=,<,>=,<=）
	private String CChange; // 1:递增 2:替换
	private String[] relativeParams; // 关联参数 （需要一起比较的参数）

	public ParamterVO() {}

	public ParamterVO(String cName) {
		this.CName = cName;
	}

	// private Map<String, DisposeParamComparator> queryParamComparators;

	public ParamterVO(String cName, String sName, String cType, String cOperate, String[] cRelative) {
		super();
		this.CName = cName;
		this.SName = sName;
		this.CType = cType;
		this.COperate = cOperate;
		this.relativeParams = cRelative;
	}

	public boolean compare(CompareParamVO compareParamVO) {
		String cOperate = this.getCOperate();
		String cType = this.getCType();
		String cChange = this.getCChange();
		boolean result = false;
		Object lastCurUserValue = compareParamVO.getLastCurUserValue();
		Object changeValue = compareParamVO.getEventValue();
		Object conditionValue = compareParamVO.getConditionValue();
		Object newCurValue = compareParamVO.getNowCurUserValue();
		boolean isSetNewCurValue = false;
		if (newCurValue == null) {
			isSetNewCurValue = true;
		}
		if (cType.equalsIgnoreCase(DataType.INT)) {
			if (cChange.equalsIgnoreCase(DataChangeType.INCREASE)) { // int类型参数比较
				if (lastCurUserValue != null) {
					newCurValue = (Integer) lastCurUserValue + (Integer) (changeValue == null ? 0 : changeValue);
				} else {
					newCurValue = changeValue == null ? 0 : changeValue;
				}
			} else {
				newCurValue = changeValue == null ? 0 : changeValue;
			}
			result = intCompare((Integer) newCurValue, (Integer) conditionValue, cOperate);
		} else if (cType.equalsIgnoreCase(DataType.STRING)) { // String类型参数比较
			if (cChange.equalsIgnoreCase(DataChangeType.INCREASE)) {
				if (lastCurUserValue != null) {
					newCurValue = (String) lastCurUserValue + (String) (changeValue == null ? "" : changeValue);
				} else {
					newCurValue = changeValue == null ? "" : changeValue;
				}
			} else {
				newCurValue = changeValue == null ? "" : changeValue;
			}
			result = stringCompare((String) newCurValue, (String) conditionValue, cOperate);
		} else if (cType.equalsIgnoreCase(DataType.BOOLEAN)) {
			newCurValue = changeValue == null ? false : changeValue;
			result = newCurValue.equals(conditionValue);
		} else if (cType.equalsIgnoreCase(DataType.OBJECT)) {
			newCurValue = changeValue;
			result = newCurValue.equals(conditionValue);
		}
		if (isSetNewCurValue == true) {
			compareParamVO.setNowCurUserValue(newCurValue);
		}
		if (!newCurValue.equals(lastCurUserValue)) {
			compareParamVO.setUpdateCurValue(true);
		}
		return result;
	}

	/**
	 * int类型值比较（检查参数是否符合条件）
	 * 
	 * @param eventValue
	 *            事件参数
	 * @param conditionValue
	 *            条件参数
	 * @param dataOperate
	 *            操作类型
	 * @return 参数是否符合条件
	 */
	private boolean intCompare(Integer eventValue, Integer conditionValue, String dataOperate) {
		if (dataOperate.equals(DataOperate.GREAT)) {
			return eventValue.compareTo(conditionValue) > 0 ? true : false;
		} else if (dataOperate.equals(DataOperate.EQUAL)) {
			return eventValue.compareTo(conditionValue) == 0 ? true : false;
		} else if (dataOperate.equals(DataOperate.LESS)) {
			return eventValue.compareTo(conditionValue) < 0 ? true : false;
		} else if (dataOperate.equals(DataOperate.NOT_LESS)) {
			return eventValue.compareTo(conditionValue) >= 0 ? true : false;
		} else if (dataOperate.equals(DataOperate.NOT_GREAT)) {
			return eventValue.compareTo(conditionValue) <= 0 ? true : false;
		} else {
			return false;
		}
	}

	/**
	 * String类型值比较（检查参数是否符合条件）
	 * 
	 * @param eventValue
	 *            事件参数
	 * @param conditionValue
	 *            条件参数
	 * @param dataOperate
	 *            操作类型
	 * @return 参数是否符合条件
	 */
	private boolean stringCompare(String eventValue, String conditionValue, String dataOperate) {
		if (dataOperate.equals(DataOperate.GREAT)) {
			return eventValue.compareTo(conditionValue) > 0 ? true : false;
		} else if (dataOperate.equals(DataOperate.EQUAL)) {
			return eventValue.compareTo(conditionValue) == 0 ? true : false;
		} else if (dataOperate.equals(DataOperate.LESS)) {
			return eventValue.compareTo(conditionValue) < 0 ? true : false;
		} else if (dataOperate.equals(DataOperate.NOT_LESS)) {
			return eventValue.compareTo(conditionValue) >= 0 ? true : false;
		} else if (dataOperate.equals(DataOperate.NOT_GREAT)) {
			return eventValue.compareTo(conditionValue) <= 0 ? true : false;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CName == null) ? 0 : CName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ParamterVO other = (ParamterVO) obj;
		if (CName == null) {
			if (other.CName != null) return false;
		} else if (!CName.equals(other.CName)) return false;
		return true;
	}

	public String getCName() {
		return CName;
	}

	public void setCName(String cName) {
		CName = cName;
	}

	public String getSName() {
		return SName;
	}

	public void setSName(String sName) {
		SName = sName;
	}

	public String getCType() {
		return CType;
	}

	public void setCType(String cType) {
		CType = cType;
	}

	public String getCOperate() {
		return COperate;
	}

	public void setCOperate(String cOperate) {
		COperate = cOperate;
	}

	public String getCChange() {
		return CChange;
	}

	public void setCChange(String cChange) {
		CChange = cChange;
	}

	public String[] getRelativeParams() {
		return relativeParams;
	}

	public void setRelativeParams(String[] relativeParams) {
		this.relativeParams = relativeParams;
	}
}
