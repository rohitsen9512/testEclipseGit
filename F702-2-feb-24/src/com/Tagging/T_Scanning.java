package com.Tagging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;



public class T_Scanning extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	Statement stmt1=null;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		response.setContentType("text/html");  
		HttpSession session2 = request.getSession();  
		String UserName = (String) session2.getAttribute("UserName");
		int id_emp_user = (int)session2.getAttribute("id_emp_user");
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String action = "",id="0";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		//String strdirName =   "d:/new";
		//String strdirName =   "D:/WorkSpace/Hical/WebContent/ScanFile";
		//MultipartRequest multi=new MultipartRequest(request,"d:/new"); 
		ServletContext servletContext = request.getSession().getServletContext();
		String relativeWebPath = "ScanFile";
		String strdirName = servletContext.getRealPath(relativeWebPath);
		System.out.println(strdirName);
		MultipartRequest multi  =   new MultipartRequest(request, strdirName, 10*1024*1024,"ISO-8859-1", new DefaultFileRenamePolicy()); // 10MB
		   
		String strfilename      =   multi.getFilesystemName("file");
		    String strfilename1     =   strdirName+"/"+strfilename  ;
		    String strnewName       =   "-";
		   String strDateofscan    =   "";
		    String stryears         =   "";
		    stryears                =   multi.getParameter("year");
		    strDateofscan           =   sdf.format(currDate);
		    String period =multi.getParameter("period");
		    String id_loc =multi.getParameter("id_loc");
		    String id_sloc =multi.getParameter("id_sloc");
		    String id_flr =multi.getParameter("id_flr");
		    
		try
		{
			jobj.put("data", 0);
			con=Common.GetConnection(request);
			stmt = con.createStatement();
			stmt1 = con.createStatement();
			
	            	if(!(strfilename==null || strfilename.equals("null") || strfilename.equals("NULL") || strfilename.equals("-"))) {
	            		
	            		File cv=new File(strfilename1);
	                    int k=strfilename.lastIndexOf(".");
	                    if(k!=-1) {
	                        String strtempa=strfilename.substring(0,strfilename.lastIndexOf("."));
	                        String strtempb=strfilename.substring(strfilename.lastIndexOf("."));
	                        if(strtempb.equals(".txt")) {
	                            long time=System.currentTimeMillis();
	                            strnewName=strtempa+"_"+time+strtempb;
	                            File renameCV=new File(strdirName+""+strnewName);
	                           // String strusertype = (String) session.getValue("usertype");

	                            try{
	                            	
	                            	stmt.executeUpdate("insert into T_Scan_Assets(id_loc,id_sloc,id_flr,period,yr_scan,nm_file) values('"+multi.getParameter("id_loc")+"','"+multi.getParameter("id_sloc")+"','"+multi.getParameter("id_flr")+"','"+multi.getParameter("period")+"','"+stryears+"','"+strnewName+"')");
	                            	
	                            	stmt.executeUpdate("delete T_Asset_Verification where id_loc='"+id_loc+"' and id_sloc = '"+id_sloc+"' and id_flr = '"+id_flr+"' and yr = '"+stryears+"' and period = '"+period+"'");
	                            	
	                            	if(cv.isFile()) {
	                                    cv.renameTo(renameCV);
	                                    strfilename=strdirName+""+strnewName;
	                                    
	                                    BufferedReader br           =    new BufferedReader(new FileReader(strfilename));
	                                    String straid="";
	                                    while ((straid = br.readLine()) != null) {
	                                    rs1=stmt1.executeQuery("select ds_pro,no_mfr,ds_asst,dt_alloc, to_assign,id_grp,id_sgrp,allocate from A_Ware_House  where id_wh_dyn ='"+straid+"'");
	                                   
	                                    
	                                    rs=stmt.executeQuery("select * from T_Asset_Verification   where id_wh_dyn='"+straid+"' and period='"+period+"' and yr='"+stryears+"'");
	                    				
	                                    if(rs.next()){
	                    					stmt.executeUpdate("Update T_Asset_Verification set id_loc='"+id_loc+"',id_sloc = '"+id_sloc+"', cubicle='0',id_flr = '"+id_flr+"' where id_wh_dyn='"+straid+"'");
	                    				}else{
	                    						while(rs1.next())
	                    						{
	                    					stmt.executeUpdate("insert into  T_Asset_Verification "+
	                    						"(id_wh_dyn,ds_pro,"+
	                    						"no_mfr,ds_asst,dt_alloc"+
	                    						",to_assign, id_loc,id_sloc,id_flr,"+
	                    						"cubicle,verified_by,verified_on,id_grp,id_sgrp, "+
	                    						"comments,yr,period,allocate )"+
	                    						"values('"+straid+"','"+rs1.getString(1)+"',"+
	                    						"'"+rs1.getString(2)+"','"+rs1.getString(3)+"',"+
	                    						"'"+rs1.getString(4)+"','"+rs1.getString(5)+"',"+
	                    						"'"+id_loc+"','"+id_sloc+"','"+id_flr+"','0',"+
	                    						"'"+id_emp_user+"','"+sdf.format(currDate)+"',"+
	                    						"'"+rs1.getString(6)+"', '"+rs1.getString(7)+"',"+
	                    						"'-','"+stryears+"','"+period+"','"+rs1.getString(8)+"')");
	                    						}
	                    				}
	                                    
	                                    }
	                            	}
	                            	
	                            	jobj.put("data", 1);
	                            }catch(Exception e){
	                            	
	                                System.out.println("querry insertion error"+e);
	                            }
	                        }
	                    }       }
	            	
	            	request.setAttribute("data", jobj.toString());
	    			response.getWriter().write(jobj.toString());
			
		}catch(Exception e)
		{
			System.out.println("Error in T Scanning.");
		}
		finally
		{
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		
	}

}

	
	
	
	


