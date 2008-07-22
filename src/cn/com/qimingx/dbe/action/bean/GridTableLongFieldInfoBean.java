package cn.com.qimingx.dbe.action.bean;

import cn.com.qimingx.spring.UploadFile;

/**
 * @author inc062805
 *
 * 更新BLOB字段
 */
public class GridTableLongFieldInfoBean extends UploadFile {
	private GridTableFieldInfoBean bean = new GridTableFieldInfoBean();

	public Object getPkObject(){
		return bean.getPkObject();
	}
	
	public int getPkType() {
		return bean.getPkType();
	}

	public void setPkType(int pkType) {
		bean.setPkType(pkType);
	}

	public String getField() {
		return bean.getField();
	}

	public String getPk() {
		return bean.getPk();
	}

	public String getPkValue() {
		return bean.getPkValue();
	}

	public String getTablename() {
		return bean.getTablename();
	}

	public void setField(String field) {
		bean.setField(field);
	}

	public void setPk(String pk) {
		bean.setPk(pk);
	}

	public void setPkValue(String pkValue) {
		bean.setPkValue(pkValue);
	}

	public void setTablename(String tablename) {
		bean.setTablename(tablename);
	}
}
