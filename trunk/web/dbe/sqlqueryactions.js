// DEB Action定义..
DBE.SQLQueryPanelActions = function(queryPanel) {
	/**
	 * 运行SQL语句的Action
	 */
	this.run = new Ext.Action({
		text : 'Run',
		iconCls : 'run',
		handler : function() {
			queryPanel.runSQL();
		}
	});

	/**
	 * 打开SQL文件
	 */
	this.open = new Ext.Action({
		text : '打开',
		iconCls : 'open',
		handler : function() {
			alert("打开sql文件");
			Ext.ux.SwfUploader.upload({
					upload_url : '../dbeSQLQueryAction/execSqlFile.do',
					file_types : {
						type : '*.sql',
						desc : '所有文件'
					},
					callback : {
						uploadComplete : function(file){
						}
					}
				});
		}
	});

	/**
	 * 保存sql内容
	 */
	this.save = new Ext.Action({
		text : '保存',
		iconCls : 'save',
		handler : function() {
			var sql = queryPanel.sqlEditor.getSQLText(true);
			window.location.href = '../dbeSQLQueryAction/saveAsSQL.do?sql=' + sql;
		}
	});

	/**
	 * 取得相关Action对象
	 */
	this.getActions = (function() {
		return [this.run, this.open, this.save];
	}).createDelegate(this);
};