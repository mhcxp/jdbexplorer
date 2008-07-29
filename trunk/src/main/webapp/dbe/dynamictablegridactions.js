// Table Grid 功能 Action定义
DBE.DynamicTableGridActions = function(dtgrid, tableinfo) {
	/*
	 * Grid操作请求方法..
	 */
	var optRequest = function(url, data, completeCall) {
		Ext.Ajax.request({
			url : url,
			method : 'POST',
			params : {
				node : dtgrid.nodeInfo.id,
				path : dtgrid.nodeInfo.getPath('text'),
				text : dtgrid.nodeInfo.text,
				data : Ext.encode(data)
			},
			callback : function(options, success, response) {
				var rst = eval("(" + response.responseText + ")");
				if (!rst.msg) {
					rst.msg = "未知消息";
				}
				Ext.Msg.info({
					message : rst.msg,
					alignRef : dtgrid.getId(),
					alignType : 'tl-tl?'
				});
				if (completeCall) {
					completeCall(success);
				}
			}
		});
	}
	/**
	 * 删除表格记录..
	 */
	this.remove = new Ext.Action({
		text : '删除',
		tooltip : '删除选择记录',
		tooltipType : 'title',
		iconCls : 'remove',
		handler : function() {
			var selects = dtgrid.getSelectionModel().getSelections();
			if (selects && selects.length > 0) {
				if (confirm('您确认要删除选择的记录~?')) {
					var ids = "";
					for (var i = 0; i < selects.length; i++) {
						if (ids.length > 0) {
							ids += ",";
						}
						ids += selects[i].data[tableinfo.pkColumnName];
					}
					// alert("remove ids:" + ids);
					var pkColumn = tableinfo
							.getColumnInfoByName(tableinfo.pkColumnName);
					var data = {
						pk : tableinfo.pkColumnName,
						type : pkColumn.type,
						value : ids
					};
					// 删除成功后，刷新数据源
					var refreshStore = function(success) {
						if (success) {
							dtgrid.store.reload();
						}
					};
					optRequest('../dbeGridAction/remove.do', data, refreshStore);
				}
			} else {
				alert('请选择要删除的记录~!');
			}
		}
	});
	/**
	 * 表格数据修改后 提交保存
	 */
	this.modifyUpdatePost = function(object) {
		// 取得基本参数
		var cm = object.grid.getColumnModel();// 列模型
		var colName = cm.getDataIndex(object.column);// 列名称
		var column = tableinfo.getColumnInfoByName(colName);// 列信息
		var value = object.value;// 当前值
		var oldValue = object.originalValue;// 原值
		var record = object.record;// 记录对象
		// 生成保存修改的数据对象
		var pk = tableinfo.pkColumnName;
		var pkColumn = tableinfo.getColumnInfoByName(pk);// 列信息
		var pkType = pkColumn.type;
		var data = {
			pk : pk,
			pkValue : record.data[tableinfo.pkColumnName],
			pkType : pkType,
			type : column.type,
			field : colName,
			value : value
		};
		// 排除日期类型（格式原因） 引起的误差
		if (column.extType.type == 'date') {
			if (value && value.format) {
				value = value.format(column.extType.dateFormat);
			}
			if (oldValue && oldValue.format) {
				oldValue = oldValue.format(column.extType.dateFormat);
			}
			// alert(oldValue + " ---> " + value);
			if (value == oldValue) {
				// 放弃本次修改，（因为日期型 format的不同 而引起的误差）
				record.reject();
				return;
			}

			data.value = value;
			data.format = column.extType.format;
		}
		// 提交修改数据
		optRequest('../dbeGridAction/update.do', data, function(success) {
			if (success) {
				record.commit();
			}
		});
	}
	/**
	 * 处理长类型内容的修改与查看..
	 */
	this.processLongTypeContent = function(grid, rowIdx, columnIdx, eventObj,
			table) {
		var cm = grid.getColumnModel();// 列模型
		var colName = cm.getDataIndex(columnIdx);// 列名称
		var record = grid.store.getAt(rowIdx);// 记录
		// 检查是否 <HTML>内容..
		if (record.data[colName] == "[HTML]") {
			alert("Sorry，[HTML]的查看与修改正在实现中....");
			return false;
		}

		// 检查是否长类型（长类型未设置editor...）
		var column = cm.getColumnById(columnIdx);
		if (column.editor == undefined) {
			// alert("Sorry，[LOB]的查看与修改正在实现中....");
			// 准备提交参数
			var pkName = tableinfo.pkColumnName;
			if (!pkName || pkName == '') {
				alert("程序错误：pkColumnName 无效~~!");
				return false;
			}
			var pkValue = record.data[tableinfo.pkColumnName];
			if (!pkValue || pkValue == '') {
				alert("程序错误：主键值无效~~!");
				return false;
			}
			var pkType = tableinfo.getColumnInfoByName(pkName).type;
			var params = {
				tablename : table,
				pk : pkName,
				pkValue : pkValue,
				pkType : pkType,
				field : colName
			}

			// 读取长字段内容..
			Ext.Ajax.request({
				url : '../dbeGridAction/readlob.do',
				params : params,
				success : function(response) {
					var json = response.responseText;
					// alert("读取长字段成功..:[" + json + "]");
					var rst = eval("(" + json + ")");
					// alert("type:" + rst.type + ";name:" + rst.name);
					// 附件参数，用于更新该字段
					rst.param = params;
					var showLobWin = new DBE.ShowLobWindow(rst);
					showLobWin.show(Ext.getBody(), function() {
						showLobWin.syncSize();
					});
				},
				failure : function(response) {
					var json = response.responseText;
					alert("读取长字段失败:[" + json + "]");
				}
			});
			return false;
		}
		return true;
	};

}