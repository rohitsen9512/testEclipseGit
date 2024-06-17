package com.ldap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import com.Common.Common;

public class DataModel {
	static ResultSet rs=null;
	static ResultSet rs1=null;
	static PreparedStatement ps=null;
	static Connection con=null;
	static Statement stmt=null;

	public static int GetReferenceIdForDeptAndCC(String colName, String Value , String TableName,int refId,HttpServletRequest request){
		int id=0;
		con=Common.GetConnection(request);
		String[] parts = Value.split("-");
		if(colName.equals("nm_dept"))
			id = GetID(colName, Value , TableName,refId);
		else
			id = GetID(colName, Value , TableName,refId);
		return id;
	}
	
	
	private static int GetID(String colName, String Value , String TableName,int refId){
		String sql="";
		int id=0;
		if(colName.equals("nm_dept"))
			sql="select id_dept from "+TableName+" where "+colName+"='"+Value+"' ";
		else
			sql="select id_cc from "+TableName+" where "+colName+"='"+Value+"' ";
			
		try{
			
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				id=rs.getInt(1);
			}else{
				if(colName.equals("nm_dept"))
					sql = "insert into "+TableName+" (nm_dept,cd_dept) values('"+Value+"','"+Value+"')";
				else
					sql = "insert into "+TableName+" (id_dept,nm_cc,cd_cc) values("+refId+", '"+Value+"','"+Value+"')";
				
				stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs = stmt.getGeneratedKeys();
		            if (rs.next()) {
		            	id = rs.getInt(1);
		            } 
			}
		}catch(Exception e){
			System.out.println("Error While DB Connectivity.");
		}
		
		
		return id;
	}
	
	
	//////
	
	public static int GetReferenceIdForLocAndSLoc(String colName, String Value , String TableName,int refId,HttpServletRequest request){
		int id=0;
		con=Common.GetConnection(request);
		if(colName.equals("nm_loc"))
			id = GetIDLocAndSloc(colName, Value , TableName,refId);
		else
			id = GetIDLocAndSloc(colName, Value , TableName,refId);
		return id;
	}
	
	
	private static int GetIDLocAndSloc(String colName, String Value , String TableName,int refId){
		String sql="";
		int id=0;
		if(colName.equals("nm_loc"))
			sql="select id_loc from "+TableName+" where "+colName+"='"+Value+"' ";
		else
			sql="select id_sloc from "+TableName+" where "+colName+"='"+Value+"' ";
			
		try{
			
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				id=rs.getInt(1);
			}else{
				if(colName.equals("nm_loc"))
					sql = "insert into "+TableName+" (nm_loc,cd_loc,id_country) values('"+Value+"','"+Value+"',1)";
				else
					sql = "insert into "+TableName+" (id_loc,nm_subl,cd_subl,id_country) values("+refId+", '"+Value+"','"+Value+"',1)";
				
				stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs = stmt.getGeneratedKeys();
		            if (rs.next()) {
		            	id = rs.getInt(1);
		            } 
			}
		}catch(Exception e){
			System.out.println("Error While DB Connectivity.");
		}
		
		
		return id;
	}
	///////
	
	public static int GetIDDesignation(String colName, String Value , String TableName,HttpServletRequest request){
		String sql="";
		int id=0;
			sql="select id_design from "+TableName+" where "+colName+"='"+Value+"' ";
			
		try{
			con=Common.GetConnection(request);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				id=rs.getInt(1);
			}else{
					sql = "insert into "+TableName+" (nm_design,cd_design) values('"+Value+"','"+Value+"')";
				
					stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs = stmt.getGeneratedKeys();
		            if (rs.next()) {
		            	id = rs.getInt(1);
		            } 
			}
		}catch(Exception e){
			System.out.println("Error While DB Connectivity.");
		}
		
		
		return id;
	}
	
	
	///////
	public static String GetIDEmployee(String colName, String Value , String TableName,HttpServletRequest request){
		String sql="";
		
		
		String id="";
			sql="select id_emp_user from "+TableName+" where "+colName+"='"+Value+"' ";
			System.out.println(sql);
		try{
			con=Common.GetConnection(request);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				id=rs.getString(1);
			}else{
					/*sql = "insert into "+TableName+" (nm_design,cd_design) values('"+Value+"','"+Value+"',1)";
				
					stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs = stmt.getGeneratedKeys();
		            if (rs.next()) {
		            	id = rs.getInt(1);
		            } */
				id="";
			}
		}catch(Exception e){
			System.out.println("Error While DB Connectivity.");
		}
		
		
		return id;
	}
	
	
	
	
	
	
	///code For RazorPay
	
	
	static int GetIDdept(String colName, String Value , String TableName,int refId,HttpServletRequest request){
		String sql="";
		int id=0;
		con=Common.GetConnection(request);
		if(colName.equals("nm_dept"))
			sql="select id_dept from "+TableName+" where "+colName+"='"+Value+"' ";
	
		else
			
			sql="select id_loc from "+TableName+" where "+colName+"='"+Value+"' ";
			
		try{
			
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				id=rs.getInt(1);
			}else{
				if(colName.equals("nm_dept"))
					sql = "insert into "+TableName+" (nm_dept,cd_dept) values('"+Value+"','"+Value+"')";
				else
					sql = "insert into "+TableName+" (nm_loc,cd_loc) values('"+Value+"','"+Value+"')";
				
					
				
				stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs = stmt.getGeneratedKeys();
		            if (rs.next()) {
		            	id = rs.getInt(1);
		            } 
			}
		}catch(Exception e){
			System.out.println("Error While DB Connectivity.");
		}
		
		
		return id;
	}
	
	static int GetIdcostCenter(String colName, String Value , String TableName,int dept,HttpServletRequest request){
		String sql="";
		int id=0;
		con=Common.GetConnection(request);
		if(colName.equals("nm_cc"))
			sql="select id_cc from "+TableName+" where "+colName+"='"+Value+"' and id_dept="+dept+"";
		else
			sql="select id_sloc from "+TableName+" where "+colName+"='"+Value+"' and id_loc="+dept+"";
			
			
		try{
			
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				id=rs.getInt(1);
			}else{
				
				if(colName.equals("nm_cc"))
					sql = "insert into "+TableName+" (id_dept,nm_cc,cd_cc) values("+dept+", '"+Value+"','"+Value+"')";
				else
					sql = "insert into "+TableName+" (id_loc,nm_subl,cd_subl) values("+dept+", '"+Value+"','"+Value+"')";
				
				stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs = stmt.getGeneratedKeys();
		            if (rs.next()) {
		            	id = rs.getInt(1);
		            } 
			}
		}catch(Exception e){
			System.out.println("Error While DB Connectivity.");
		}
		
		
		return id;
	}
	
	static int GetIdsubfunction(String colName, String Value , String TableName,int dept,int id_cc,HttpServletRequest request){
		String sql="";
		int id=0;
		con=Common.GetConnection(request);
		if(colName.equals("nm_s_function"))
			sql="select id_s_function from "+TableName+" where "+colName+"='"+Value+"' and  id_dept="+dept+" and  id_cc="+id_cc+"  ";
		else
			sql="select id_building from "+TableName+" where "+colName+"='"+Value+"' and  id_loc="+dept+" and  id_sloc="+id_cc+"  ";
		
			

		try{
			
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				id=rs.getInt(1);
			}else{
				if(colName.equals("nm_s_function"))
					sql = "insert into "+TableName+" (id_dept,id_cc,nm_s_function) values("+dept+","+id_cc+" ,'"+Value+"')";
				else
					sql = "insert into "+TableName+" (id_loc,id_sloc,nm_building,cd_building,id_country) values("+dept+","+id_cc+" ,'"+Value+"','"+Value+"','0')";
				
				
				stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs = stmt.getGeneratedKeys();
		            if (rs.next()) {
		            	id = rs.getInt(1);
		            } 
			}
		}catch(Exception e){
			System.out.println("Error While DB Connectivity.");
		}
		
		
		return id;
	}
	static int GetIdbu(String colName, String Value , String TableName,int dept,int id_cc,int id_s_function,HttpServletRequest request){
		String sql="";
		int id=0;
		con=Common.GetConnection(request);
		if(colName.equals("nm_bu"))
			sql="select id_bu from "+TableName+" where "+colName+"='"+Value+"' and  id_dept="+dept+" and  id_cc="+id_cc+" and  id_s_function="+id_s_function+" ";
		else
			sql="select id_flr from "+TableName+" where "+colName+"='"+Value+"' and  id_loc="+dept+" and  id_sloc="+id_cc+" and  id_building="+id_s_function+" ";
		
			
		try{
			
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				id=rs.getInt(1);
			}else{
				if(colName.equals("nm_bu"))
					sql = "insert into "+TableName+" (id_dept,id_cc,id_s_function,nm_bu) values("+dept+","+id_cc+","+id_s_function+", '"+Value+"')";
				else
					sql = "insert into "+TableName+" (id_loc,id_sloc,id_building,nm_flr,cd_flr) values("+dept+","+id_cc+","+id_s_function+", '"+Value+"','"+Value+"')";
				
				stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs = stmt.getGeneratedKeys();
		            if (rs.next()) {
		            	id = rs.getInt(1);
		            } 
			}
		}catch(Exception e){
			System.out.println("Error While DB Connectivity.");
		}
		
		
		return id;
	}
	
	static int GetIdnull(String colName, String Value , String TableName,int dept,int id_cc,int id_s_function,HttpServletRequest request){
		String sql="";
		int id=0;
		con=Common.GetConnection(request);
		if(colName.equals("nm_subl"))
		{
			sql="select id_sloc from "+TableName+" where    id_loc="+dept+" order by id_sloc asc ";
		}
		if(colName.equals("nm_building"))
		{
			sql="select id_building from "+TableName+" where    id_loc="+dept+" and  id_sloc="+id_cc+" order by id_sloc asc";
		}
		if(colName.equals("nm_flr"))
		{
			sql="select id_flr from "+TableName+" where   id_loc="+dept+" and  id_sloc="+id_cc+" and  id_building="+id_s_function+" order by id_sloc asc";
				}
		try{
			
			
			
		
			System.out.println(sql);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				id=rs.getInt(1);
			}
			else {
				
				if(colName.equals("nm_subl"))
				{
					sql = "insert into "+TableName+" (id_loc,nm_subl,cd_subl) values("+dept+", 'NA','NA')";
				}		
				if(colName.equals("nm_building"))
				{
					sql = "insert into "+TableName+" (id_loc,id_sloc,nm_building,cd_building,id_country) values("+dept+","+id_cc+" ,'NA','NA','0')";
				}
				if(colName.equals("nm_flr"))
				{
					sql = "insert into "+TableName+" (id_loc,id_sloc,id_building,nm_flr,cd_flr) values("+dept+","+id_cc+","+id_s_function+", 'NA','NA')";
						}
				
				stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs = stmt.getGeneratedKeys();
		            if (rs.next()) {
		            	id = rs.getInt(1);
		            } 
			}
		}catch(Exception e){
			System.out.println("Error While DB Connectivity.");
		}
		
		
		return id;
	}
	
	
	
}
