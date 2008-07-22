package cn.com.qimingx.dbe.action.bean;

import cn.com.qimingx.core.WebParamBean;
import cn.com.qimingx.utils.SQLTypeUtils;

/**
 * @author inc062805
 * 
 * 描述 表格中 某条记录的某个字段 的定位信息
 */
public class GridTableFieldInfoBean extends WebParamBean {
	// 表名称
	private String tablename;
	// 主键名称
	private String pk;
	// 主键值
	private String pkValue;
	// 主键值类型
	private int pkType;
	// 字段名称
	private String field;

	public Object getPkObject() {
		Object value = SQLTypeUtils.getSQLValueObject(pkType, pkValue, null);
		return value;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getPkValue() {
		return pkValue;
	}

	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public int getPkType() {
		return pkType;
	}

	public void setPkType(int pkType) {
		this.pkType = pkType;
	}
}
