// DEB Action定义..
DBE.SQLQueryPanel = function(config) {
	// actions
	var actions = new DBE.SQLQueryPanelActions(this);

	// sql editor
	var sqlEditor = new DBE.SQLEditor({
		region : 'north',
		split : true,
		collapsible : true,
		collapseMode : 'mini',
		minSize : 80,
		actions : actions.getActions(),
		keys : [{
			key : [Ext.EventObject.ENTER],
			scope : this,
			ctrl : false,
			shift : false,
			alt : true,
			fn : function(key, eventObj) {
				// alt + Enter 时运行sql
				this.runSQL();
			}
		}]
	});

	// 创建grid（使用一个默认的TableInfo模型）
	var grid = new DBE.DynamicQueryGrid({
		tableinfo : {
			columns : [{
				name : 'Result',
				extType : {
					booleanType : false,
					dateFormat : "",
					dateType : false,
					directShow : true,
					format : "",
					longType : false,
					numberType : false,
					sortable : true,
					type : "auto"
				}
			}]
		}
	});

	var queryGridPanel = new Ext.Panel({
		region : 'center',
		minSize : 80,
		layout : 'fit',
		plain : true,
		frame : false,
		border : false,
		items : grid
	});

	// 准备配置参数
	var cfg = {
		layout : 'border',
		plain : true,
		frame : false,
		border : false,
		autoScroll : true,
		autoSize : true,
		split : true,
		items : [sqlEditor, queryGridPanel]
	};
	config = Ext.applyIf(config || {}, cfg);

	// call 父类构建器
	DBE.SQLQueryPanel.superclass.constructor.call(this, config);

	// 公布属性
	this.grid = grid;
	this.sqlEditor = sqlEditor;
	this.actions = actions;
};
Ext.extend(DBE.SQLQueryPanel, Ext.Panel, {
	/**
	 * 初始化 查询面板，包括设置sql，以及执行查询..
	 */
	init : function(sql) {
		if (sql && sql.length > 0) {
			this.runSQL(sql);
		} else {
			// load初始空数据
			this.grid.store.loadData({
				total : 1,
				rows : [{
					Result : 'no records.'
				}]
			});
		}

		// 构建工具栏按钮
		this.grid.buildTBarItems();
	},
	/**
	 * 执行指定的sql语句
	 */
	runSQL : function(sql, append) {
		if (!sql) {
			// 从编辑器中取得默认sql
			sql = this.sqlEditor.getSQLText();
		} else {
			if (append) {
				var value = this.sqlEditor.getSQLText(true);
				this.sqlEditor.setSQLText(value + "\n" + sql);
			} else {
				this.sqlEditor.setSQLText(sql);
			}
		}

		if (sql && sql.length > 0) {
			// alert("run.sql:" + sql);
			this.grid.reload(sql);
		} else {
			alert("请输入SQL语句~~");
		}
	}
});