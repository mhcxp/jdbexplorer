<%@page import="java.io.InputStream"%>
<%@page pageEncoding="GBK"%>
<%
	//Enumeration<String> names = request.getParameterNames();
	//String paramList = "";
	//while(names.hasMoreElements()){
	//	String name = (String)names.nextElement();
	//	if(paramList.length()>0){
	//		paramList += "<br/>\n";
	//	}
	//	paramList += name + "=" + request.getParameter(name);
	//}
	//out.println("<h1>Params:</h1>:" + paramList);
	//System.out.println("<h1>Params:</h1>:\n" + paramList);

	InputStream stream = request.getInputStream();
	int value = -1;
	do{
		value = stream.read();
		System.out.println(value + ",");
	}while(value != -1);
%>