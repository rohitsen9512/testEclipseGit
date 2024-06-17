package com.Log;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

public class LogGenerate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	public void ReadWritesLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		JsonFactory jfactory3 = new JsonFactory();
        ServletContext servletContext3 = getServletConfig().getServletContext();
	    String relativeWebPath3 = "InvoiceScanFile/LogFile.json";
		String strdirName3 = servletContext3.getRealPath(relativeWebPath3);
		JsonGenerator jGenerator3 = jfactory3.createJsonGenerator(new File(strdirName3), JsonEncoding.UTF8);
		jGenerator3.writeStartArray(); // [
			System.out.println(jGenerator3);
	    jGenerator3.writeStartObject(); // {
	    jGenerator3.writeStringField("Name","Pritesh");
	    jGenerator3.writeEndObject(); 
	    jGenerator3.writeEndArray(); // ]
		jGenerator3.close();
	}

}
