package cn.com.qimingx.dbe;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import cn.com.qimingx.core.ProcessResult;
import cn.com.qimingx.dbe.action.bean.DataExportBean;
import cn.com.qimingx.dbe.action.bean.GridTableFieldInfoBean;
import cn.com.qimingx.dbe.action.bean.GridTableLoadBean;
import cn.com.qimingx.dbe.action.bean.GridTableLongFieldInfoBean;
import cn.com.qimingx.dbe.action.bean.GridTableUpdateBean;
import cn.com.qimingx.dbe.action.bean.GridTableUpdateInfoBean;
import cn.com.qimingx.dbe.action.bean.TreeNodeBean;
import cn.com.qimingx.dbe.action.bean.GridTableUpdateInfoBean.UpdateValue;
import cn.com.qimingx.dbe.service.DBInfoService;
import cn.com.qimingx.dbe.service.WorkDirectory;
import cn.com.qimingx.utils.SQLTypeUtils;

/**
 * @author inc062805
 * 
 * 实现在DataGrid上的各种操作
 */
@Service("gridOperator")
public class GridOperator {
	// logger
	private static final Log log = LogFactory.getLog(GridOperator.class);

	/**
	 * 在grid中显示 指定元素的数据
	 * 
	 * @param service
	 * @param param
	 * @return
	 */
	public ProcessResult<JSON> load(DBInfoService service,
			GridTableLoadBean param) {
		String type = param.getType();
		if (type.equalsIgnoreCase("table") || type.equalsIgnoreCase("view")) {
			return getTableData(service, param);
		}
		// otehrs...

		// 无处理分支，返回空数据
		ProcessResult<JSON> pr = new ProcessResult<JSON>(true);
		pr.setData(new JSONArray());
		return pr;
	}

	// 透过服务对象，取得 Table的数据....
	private ProcessResult<JSON> getTableData(DBInfoService service,
			GridTableLoadBean param) {
		ProcessResult<JSON> pr = new ProcessResult<JSON>();

		// 取得数据信息
		String schema = param.getSchema();
		String name = param.getNodeName();
		int start = param.getStart();
		int limit = param.getLimit();
		String cond = param.getSearchCondition();
		log.debug("Search Condition:" + cond);

		ProcessResult<TableDataInfo> prData;
		prData = service.getTableData(schema, name, start, limit, cond);
		if (prData.isFailing()) {
			pr.setMessage(prData.getMessage());
			return pr;
		}
		TableDataInfo data = prData.getData();

		// 生成JSON
		JSONArray rows = new JSONArray();
		for (Map<String, Object> row : data.getRows()) {
			JSONObject jsonData = new JSONObject();
			for (Iterator<String> it = row.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				jsonData.element(key, row.get(key));
			}
			rows.add(jsonData);
		}

		JSONObject json = new JSONObject();
		json.element("total", data.getTotal());
		json.element("rows", rows);
		pr.setSuccess(true);
		pr.setData(json);
		return pr;
	}

	// 更新Blob类型的字段
	public ProcessResult<String> updateBlob(DBInfoService service,
			GridTableLongFieldInfoBean param, File file) {
		String t = param.getTablename();// 表名称
		String f = param.getField();// 字段名称
		String pk = param.getPk();// 主键名称
		Object pkv = param.getPkObject();// 主键值
		ProcessResult<String> pr = service.updateBLob(t, pk, pkv, f, file);
		return pr;
	}

	// 更新CLOB类型的字段
	public ProcessResult<String> updateClob(DBInfoService service,
			GridTableFieldInfoBean param, String clob) {
		String t = param.getTablename();// 表名称
		String f = param.getField();// 字段名称
		String pk = param.getPk();// 主键名称
		Object pkv = param.getPkObject();// 主键值
		ProcessResult<String> pr = service.updateCLob(t, pk, pkv, f, clob);
		return pr;
	}

	// 更新记录
	public ProcessResult<String> update(DBInfoService service,
			GridTableUpdateBean param) {
		ProcessResult<String> pr = new ProcessResult<String>(false);
		try {
			GridTableUpdateInfoBean update = param.getTableUpdate();
			// 生成sql语句
			String sql = "update " + param.getElementName();
			sql += " SET " + update.getField() + "=:" + update.getField();
			sql += " where " + update.getPk();
			sql += "=:" + update.getPk();
			log.debug("table.update.sql:" + sql);

			// 生成参数
			UpdateValue value = update.getUpdateValue();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(update.getField(), value.getValue());
			params.put(update.getPk(), update.getPkObject());

			return service.executeUpdate(sql, params);
		} catch (Exception e) {
			String msg = e.getMessage();
			log.error("执行Table.update出错：" + msg);
			pr.setMessage(msg);
			return pr;
		}
	}

	// 删除表记录
	public ProcessResult<String> remove(DBInfoService service,
			GridTableUpdateBean params) {
		// make sql
		String sql = "delete from " + params.getElementName();
		sql += " where " + params.getTableUpdate().getPk();
		sql += " in (" + params.getTableUpdate().getValue() + ")";
		log.debug("table.remove.sql:" + sql);
		return service.executeUpdate(sql, null);
	}

	// 读取LOB类型的字段
	public ProcessResult<JSON> readLob(DBInfoService service,
			GridTableFieldInfoBean param, WorkDirectory work) {
		// read
		String t = param.getTablename();// 表名称
		String f = param.getField();// 字段名称
		String pk = param.getPk();// 主键名称
		Object pkv = param.getPkObject();// 主键值
		ProcessResult<LobObject> lobPr = service.readLob(t, pk, pkv, f, work);

		// return
		ProcessResult<JSON> pr = new ProcessResult<JSON>(false);
		if (lobPr.isSuccess()) {
			String type = lobPr.getData().isBLOB() ? "BLOB" : "CLOB";
			JSONObject json = new JSONObject();
			json.element("success", true);
			json.element("type", type);
			json.element("isNull", lobPr.getData().isNull());
			if (lobPr.getData().isNull()) {
				json.element("name", "null");
				json.element("isImage", false);
			} else {
				json.element("name", lobPr.getData().getValue().getName());
				json.element("isImage", lobPr.getData().isImage());
			}
			pr.setSuccess(true);
			pr.setData(json);
		} else {
			pr.setMessage(lobPr.getMessage());
		}
		return pr;
	}

	// 导出表格数据
	public ProcessResult<JSON> export(DBInfoService service,
			DataExportBean param, WorkDirectory wd) {
		ProcessResult<JSON> pr = new ProcessResult<JSON>();

		// 生成 查询导出数据的SQL
		String sql = param.getSql();
		if (sql == null || sql.length() < 1) {
			// 自己生成sql
			sql = "";
			for (String field : param.getFields()) {
				if (sql.length() > 0) {
					sql += ",";
				}
				sql += field;
			}
			sql = "select " + sql + " from " + param.getElementName();
			// TODO:可能还要处理其它条件的问题
			log.debug("Table Export,sql:" + sql);
		} else {
			log.debug("Query Export,sql:" + sql);
		}
		// 生成导出数据的目标范围
		int startNo = param.getStartPageNo();// 开始页码
		int endNo = param.getEndPageNo();// 结束页码;
		int pageRange = endNo - startNo + 1;// 页码区间
		int start = startNo == 1 ? 1 : (startNo - 1) * param.getLimit() + 1;// 开始索引
		int limit = pageRange * param.getLimit();
		log.debug("start：" + start + ",limit:" + limit);

		// 取得查询结果
		ProcessResult<TableInfo> tpr;
		tpr = service.executeQuery(sql, start, limit, null);
		if (tpr.isFailing()) {
			pr.setFailing(true);
			pr.setMessage(tpr.getMessage());
			return pr;
		}

		// 读取内容生成导出文件..
		TableInfo table = tpr.getData();
		if (param.getElementName() != null) {
			table.setTableName(param.getElementName());
		} else {
			table.setTableName("NotTableName");
		}
		ProcessResult<File> fpr = table.makeDataFile(param.getFormatType(), wd);
		if (fpr.isFailing()) {
			pr.setSuccess(false);
			pr.setMessage(fpr.getMessage());
			return pr;
		}
		String fileName = fpr.getData().getName();
		log.debug("export to:" + fpr.getData().getAbsolutePath());

		// 构建返回对象
		JSONObject json = new JSONObject();
		json.element("success", true);
		json.element("file", fileName);
		pr.setData(json);
		pr.setSuccess(true);
		return pr;
	}

	// 取得外键可选值...
	public ProcessResult<JSON> getFKValues(DBInfoService service, String table,
			String field) {
		// query
		String sql = "select " + field + " from " + table;
		log.debug("getFKValues.sql:" + sql);
		ProcessResult<TableInfo> prTableInfo;
		prTableInfo = service.executeQuery(sql, -1, -1, null);

		// make json
		JSONArray jsonArray = new JSONArray();
		if (prTableInfo.isSuccess()) {
			TableDataInfo data = prTableInfo.getData().getData();
			List<Map<String, Object>> list = data.getRows();
			for (Map<String, Object> row : list) {
				JSONObject jsonRow = new JSONObject();
				jsonRow.element("key", row.get(field));
				jsonRow.element("value", row.get(field));
				jsonArray.add(jsonRow);
			}
		}
		JSONObject json = new JSONObject();
		json.element("fkvalues", jsonArray);

		// return
		ProcessResult<JSON> pr = new ProcessResult<JSON>(true);
		pr.setData(json);
		return pr;
	}
	
	
	/**
	 * 在grid中显示 指定元素的数据
	 * 
	 * @param service
	 * @param param
	 * @return
	 */
	public ProcessResult<JSON> loadColumn(DBInfoService service,
			TreeNodeBean param) {
		String type = param.getType();
		if (type.equalsIgnoreCase("table") || type.equalsIgnoreCase("view")) {
			return getTableColumns(service, param);
		}
		// otehrs...

		// 无处理分支，返回空数据
		ProcessResult<JSON> pr = new ProcessResult<JSON>(true);
		pr.setData(new JSONArray());
		return pr;
	}
	
	private ProcessResult<JSON> getTableColumns(DBInfoService service,
			TreeNodeBean param){
		ProcessResult<TableInfo> prData;
		prData = service.getTableInfo(param.getSchema(), param.getText());
		List<TableColumnInfo> columns = prData.getData().getColumns();
		JSONArray jsonArray = new JSONArray();
		for(TableColumnInfo column : columns){
			JSONObject jsonData = new JSONObject();
//			SQLTypeUtils.getJdbcTypeName(column.getType());
			jsonData.put("columName", column.getName());
			jsonData.put("dataType", SQLTypeUtils.getJdbcTypeName(column.getType()));
			jsonData.put("maxlength", column.getSize());
			jsonData.put("isNull", column.isNullable());
			jsonArray.add(jsonData);
		}
		JSONObject json = new JSONObject();
		json.element("columns", jsonArray);

		// return
		ProcessResult<JSON> pr = new ProcessResult<JSON>(true);
		pr.setData(json);
		return pr;
	}
}
