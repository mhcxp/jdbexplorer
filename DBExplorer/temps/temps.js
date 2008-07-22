// 保存Action被取消，因为保存策略改变成，修改后即时保存
Actions var saveAction = new Ext.Action({
	text : '保存',
	tooltip : '保存修改',
	tooltipType : 'title',
	iconCls : 'accept',
	scope : this,
	handler : function() {
		alert("保存修改...");
	}
});
this.insertAction(0, "save", saveAction);
// ==========================================================
// copy全部行的功能被取消，因为可以通过选择全部行再进行copy，达到copy全部行的目的
// copyToClipboard :
new Ext.Action({
	text : '复制全部行到剪贴板',
	iconCls : 'copy',
	scope : this,
	handler : function() {
		// alert("ds" + this.store);
		funCopyToClipboard(this.store.getRange(), false);
	}
})
// , copyToClipboardWithColumnHeader:
new Ext.Action({
	text : '复制全部行到剪贴板-包括列名称',
	iconCls : 'copy',
	scope : this,
	handler : function() {
		// alert("ds" + this.store);
		funCopyToClipboard(this.store.getRange(), true);
	}
})
// ===============================================================
// 对于 没有主键的表 将不再以 sql.panel的方式处理，而是采用和打开视图相同的策略（即只读表）
// 这一变化将会是直接从后台予以处理，前台不要考虑其中问题..
// alert("查询没有主键的表，只读..");
var tabName = "tabSQL_Query_Window";
var tab = tabPanel.getItem(tabName);
var sql = 'select * from ' + node.text;
if (!tab) {
	var query = new DBE.SQLQueryPanel();
	var ctl = tabPanel.add({
		id : tabName,
		title : 'SQL Query Window',
		autoScroll : true,
		closable : true,
		plain : true,
		items : query
	})
	ctl.show();
	ctl.doLayout();
	ctl.getQueryPanel = function() {
		return query;
	}
	query.init();
	query.runSQL(sql);
} else {
	var query = tab.getQueryPanel();
	query.runSQL(sql);
	tabPanel.setActiveTab(tab);
}