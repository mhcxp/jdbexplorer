# DBExplorer使用常见问题列表 #
  * 为什么打开的Grid是只读的，数据也不能即时修改？
可能是因为你打开的是一个View（视图），或者是一个无primary key（主键）的表（Table），因此无法修改数据。
  * 如何添加对新数据库类型的支持？
向%dbe\_dir%\WEB-INF\classes\dbe\_config.js文件添加新的配置条目，并正确的设置jdbc参数信息（包括驱动名称、URL模式等），然后将数据库对应的jdbc驱动（.jar文件）copy到classpath位置即可。
  * 如何我们提出问题和建议？
你可以发送e-mail给项目成员，或者在[DBExplorerBBS](http://groups.google.com/group/DBExplorer) 中发起讨论。