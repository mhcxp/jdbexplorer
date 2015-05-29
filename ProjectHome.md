# 欢迎使用DBExplorer #

DBExplorer是一个B/S架构的Web程序，基于JDBC连接数据库服务。它采用Extjs构建GUI，因此也具有和C/S程序一样良好的使用体验。
DBExplorer提供以下功能：
  * 支持多种数据库：Oracle10g、MS SqlServer   、MySQL、PostgreSQL、HSQLDB、Apache Derby。
  * 支持多种浏览器：Internet Explorer 6+、FireFox 1.5+(PC, Mac)、Safari 3+、Opera 9+ (PC, Mac)。
  * 支持SQL语句查询，后续还将推出可视化SQL语句生成以及SQL语法高亮。
  * 支持数据查看并即时修改，修改是可自动读取外键参考、默认值、列约束等。
  * 支持LOB数据的查看、下载以及修改。
  * 支持数据导出成多种格式：CSV、PDF、HTML、SQL。

**下载/安装**
  * 从[这儿](http://code.google.com/p/jdbexplorer/downloads/list)选择下载安装包或源码。
  * 准备运行环境
    1. [JavaSE 1.5+](http://java.sun.com/javase/downloads/index.jsp)；
    1. 支持servlet/jsp的Web服务器，推荐使用[Apache Tomcat6](http://tomcat.apache.org/download-60.cgi)；
    1. JDBC驱动程序，将相关数据库的JDBC驱动copy到classpath（例如：Tomcat的lib、或DBExplorer的WEB-INF\lib目录）。下载常用数据库的JDBC驱动[Oracle10g](http://www.oracle.com/technology/software/tech/java/sqlj_jdbc/index.html)、[MS SqlServer](http://www.microsoft.com/sql/technologies/jdbc/default.mspx)、[MySQL](http://dev.mysql.com/downloads/connector/j)、[PostgreSQL](http://jdbc.postgresql.org/download.html)、[HSQLDB](http://hsqldb.org/)、[Apache Derby](http://db.apache.org/derby/derby_downloads.html)；
  * 解压缩下载包到Web服务器的部署目录（例如Tomcat的webapps下），然后重新启动WebServer。
  * 通过浏览器访问http://webservername:port/DBExplorer，请注意将\*webservername**、**port**替换成实际的机器名称（或IP地址）和端口。**

**相关文章和资源**
  * [DBExplorer使用常见问题列表](http://code.google.com/p/jdbexplorer/wiki/FQA)。
  * [在Tomcat安装运行DBExplorer的详细过程](http://code.google.com/p/jdbexplorer/wiki/Install_DBE_To_Tomcat)。
  * [DBExplorer操作手册下载](http://groups.google.com/group/dbexplorer/web/mytest)


**问题/讨论/建议**
  * 使用中出现问题，请参考[FAQ](http://code.google.com/p/jdbexplorer/wiki/FQA)。
  * 你可以在[DBExplorerBBS](http://groups.google.com/group/DBExplorer)发起关于DBExplorer的讨论和建议。

**联系开发人员**
  * [cnetwei@gmail.com](mailto:cnetwei@gmail.com)
  * [lxl.li2008@gmail.com](mailto:cnetwei@gmail.com)
  * [chenanxin1018@gmail.com](mailto:ChenAnxin1018@gmail.com)