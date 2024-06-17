package com.Asset;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class ReadExcel
 */
public class ReadExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//String fileName = "d:/hello.xls";
		ServletContext servletContext = request.getSession().getServletContext();
		String relativeWebPath = "InvoiceScanFile";
		String strdirName = servletContext.getRealPath(relativeWebPath);
		String strfilename = request.getParameter("filePath");
	    String fileName     =   strdirName+"/"+strfilename  ;
	//	System.out.println(fileName);
		//String fileName = request.getParameter("filePath");
        Vector cellVectorHolder = new Vector();
String value1="",value2="",value3="",value4="";
short a=0;
short b=1;

int i=0;
       try {
	    	   JSONObject jobj = new JSONObject();
	   		   JSONArray jarr = new JSONArray();
	   		 JSONArray jarr3 = new JSONArray();
	   		 JSONArray jarr4 = new JSONArray();
	   		 JSONObject jobj3 = new JSONObject();
               FileInputStream myInput = new FileInputStream(fileName);

               POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

               //HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
               HSSFWorkbook wb = new HSSFWorkbook(myFileSystem);
              /* for (int k = 0; k < wb.getNumberOfSheets(); k++)
               {
                   int j=i+1;*/

               HSSFSheet sheet = wb.getSheetAt(0);
               int rows  = sheet.getPhysicalNumberOfRows();
               JSONObject jobj2 = new JSONObject();
               boolean t=true;
               for (int r = 0; r < rows; r++)
	            {
	               HSSFRow row   = sheet.getRow(r);
	             //  int     cells = row.getPhysicalNumberOfCells(); 
	               if(row != null)
	               {
		               HSSFCell cell1  = row.getCell(a);
		               HSSFCell cell2  = row.getCell(b);
		               if(cell1 == null)
		            	   value1 = "NA";
		               else
		                 value1 = cell1.getStringCellValue();
		               if(cell2 == null)
		            	   value2 = "NA";
		               else
		                   value2 = cell2.getStringCellValue();
		                   
		                   jobj2.put("sno"+r+"",value1);
		                   jobj3.put("sapNo"+r+"",value2);
	               }
	               else
	               {
	            	   t=false;
	               }
	                   
	              }
               
              
               if(t)
               {
	               jarr.put(jobj2);
	               jarr4.put(jobj3);
	               jobj.put("data", jarr);
	               jobj.put("sapNo", jarr4);
               }
               else
               {
            	   jobj.put("data", 2);
               }
              request.setAttribute("data", jobj.toString());
   			response.getWriter().write(jobj.toString());
               
              /* i++;
               }*/
        } catch (Exception e) {
               e.printStackTrace();
       }
	}

}
