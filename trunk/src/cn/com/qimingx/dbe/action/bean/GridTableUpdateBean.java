package cn.com.qimingx.dbe.action.bean;

import net.sf.json.JSONObject;

/**
 * @author inc062805
 * 
 * Ext Tree Loader 的参数Bean
 */
public class GridTableUpdateBean extends TreeNodeBean {
	// json Data
	private String data;
	private GridTableUpdateInfoBean tableUpdate;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public GridTableUpdateInfoBean getTableUpdate() {
		if (tableUpdate == null) {
			JSONObject json = JSONObject.fromObject(data);
			Object obj = JSONObject.toBean(json, GridTableUpdateInfoBean.class);
			return (GridTableUpdateInfoBean) obj;
		}
		return tableUpdate;
	}

	public void setTableUpdate(GridTableUpdateInfoBean tableUpdate) {
		this.tableUpdate = tableUpdate;
	}
}
