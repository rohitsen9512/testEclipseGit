package com.Asset;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class Upload_File extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		response.setContentType("text/html"); 
		int t=0;
		try
		{
		//String strdirName =   "D:/WorkSpace/TestWorkSpace/IntSez/WebContent/InvoiceScanFile";
		//MultipartRequest multi=new MultipartRequest(request,"d:/new"); 
			ServletContext servletContext = request.getSession().getServletContext();
			String relativeWebPath = "InvoiceScanFile";
			String strdirName = servletContext.getRealPath(relativeWebPath);
		MultipartRequest multi  =   new MultipartRequest(request, strdirName, 10*1024*1024,"ISO-8859-1", new DefaultFileRenamePolicy()); // 10MB
		    String strfilename      =   multi.getFilesystemName("file");
		    String strfilename1     =   strdirName+"/"+strfilename  ;
		    String strnewName       =   "-";
System.out.println(strdirName);
	            	if(!(strfilename==null || strfilename.equals("null") || strfilename.equals("NULL") || strfilename.equals("-"))) {
	            		
	            		//File cv=new File(strfilename1);
	                    int k=strfilename.lastIndexOf(".");
	                    if(k!=-1) {
	                        String strtempa=strfilename.substring(0,strfilename.lastIndexOf("."));
	                        String strtempb=strfilename.substring(strfilename.lastIndexOf("."));
	                       
	                            long time=System.currentTimeMillis();
	                            strnewName=strtempa+"_"+time+strtempb;
	                            File renameCV=new File(strdirName+"/"+strnewName);
	                           multi.getFile("file").renameTo(renameCV);
	                            System.out.println(strnewName);
	                           // String strusertype = (String) session.getValue("usertype");

	                           
	                        t=1;
	                    }       }
	            	jobj.put("data", t);
	            	jobj.put("upload_inv", strnewName);
	            	request.setAttribute("data", jobj.toString());
	    			response.getWriter().write(jobj.toString());
			
		}catch(Exception e)
		{
			System.out.println("Error in Upload File."+e);
		}
		
		
		
	}

}

	
	
	
	


