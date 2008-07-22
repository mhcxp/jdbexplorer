{
	// 系统支持的数据库类型定义
	dbtypes : [{
		name : 'Derby',
		url : 'jdbc:derby://<server>/<dbname>;create=true',
		driver : 'org.apache.derby.jdbc.ClientDriver',
		service : 'cn.com.qimingx.dbe.service.DefaultDBInfoService'	
	}, {
		name : 'ORACLE10g',
		url : 'jdbc:oracle:thin:@<server>:<dbname>',
		driver : 'oracle.jdbc.driver.OracleDriver',
		service : 'cn.com.qimingx.dbe.service.impl.OracleDBInfoService'
	}, {
		name : 'MS_SQLSERVER',
		url : 'jdbc:sqlserver://<server>;databaseName=<dbname>',
		driver : 'com.microsoft.sqlserver.jdbc.SQLServerDriver',
		service : 'cn.com.qimingx.dbe.service.DefaultDBInfoService'
	}, {
		name : 'MySQL',
		url : 'jdbc:mysql://<server>/<dbname>',
		driver : 'com.mysql.jdbc.Driver',
		service : 'cn.com.qimingx.dbe.service.DefaultDBInfoService'
	}, {
		name : 'HSQL_Server',
		url : 'jdbc:hsqldb:hsql://<server>/<dbname>',
		driver : 'org.hsqldb.jdbcDriver',
		service : 'cn.com.qimingx.dbe.service.DefaultDBInfoService'
	}, {
		name : 'PostgreSQL',
		url : 'jdbc:postgresql://<server>/<dbname>',
		driver : 'org.postgresql.Driver',
		service : 'cn.com.qimingx.dbe.service.DefaultDBInfoService'
	}]
}
