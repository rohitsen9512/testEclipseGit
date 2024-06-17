package com.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

public class InsertData {

	public static Connection con=null;
	public static Connection con1=null;
	public static Connection con2=null;
	public static Connection con3=null;
	public static Connection con4=null;
	public static Connection con5=null;
	public static ResultSet rs = null;
	public static ResultSet rs1 = null;
	public static ResultSet rs2 = null;
	public static ResultSet rs4 = null;
	public static ResultSet rs5 = null;
	public static Statement st=null;
	public static Statement st1=null;
	public static Statement st4=null;
	public static Statement st5=null;
	public static PreparedStatement ps=null;
	public static PreparedStatement ps1=null;
	public static PreparedStatement ps2=null;
	public static PreparedStatement ps4=null;
	public static PreparedStatement ps5=null;
	static Statement stmt=null;
	
	
	public static void main(String[] args,HttpServletRequest request) {
		// TODO Auto-generated method stub
		//InserUpdateLocation();
		UpdateAsset(request);
	}
	
	private static void UpdateAsset(HttpServletRequest request){
		
		String id_ven="0",id_loc="0", id_po="0",id_wh_dyn="";
		String id_div="",id_sloc="",id_buldng="",id_store="";
		String query="select wh.id_wh_dyn,nm_flr,f.id_div,f.id_loc,f.id_sloc,f.id_building from A_Ware_House wh, M_Floor f where f.id_flr=wh.id_flr";
		
		try{
			con=Common.GetConnection(request);
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
		while(rs.next()){
			
			id_div= rs.getString("id_div");
			id_loc =rs.getString("id_loc");
			id_sloc = rs.getString("id_sloc");
			id_buldng =rs.getString("id_building");
			id_wh_dyn = rs.getString("id_wh_dyn");
			
			String sql="update A_Ware_House set id_div="+id_div+", id_loc="+id_loc+",id_subl="+id_sloc+",id_building="+id_buldng+" where id_wh_dyn='"+id_wh_dyn+"'";
			 
			con3=Common.GetConnection(request);
	        PreparedStatement ps2=con3.prepareStatement(sql);
				ps2.executeUpdate();
			

	           System.out.println("One row updated."+id_wh_dyn); 
		}
		
		}catch(Exception e)
		{
			System.out.println("Here some error." + e);
		}
		
		
		
		}
		
	
	
private static void InserUpdateLocation(HttpServletRequest request){
		
	String id_ven="0",id_loc="0", id_po="0";
	String location="", venName="", po_number="",po_creation_date="",total_price="",unit_price="",quantity="",assetName="";
	String div="",loc="",sloc="",buldng="",store="";
	String id_div="",id_sloc="",id_buldng="",id_store="";
	String query="select * from Test";
	
	try{
		con=Common.GetConnection(request);
		ps=con.prepareStatement(query);
		rs=ps.executeQuery();
	while(rs.next()){
		
		div= rs.getString("Division");
		loc =rs.getString("Region");
		sloc = rs.getString("State");
		buldng =rs.getString("City");
		store =rs.getString("Store");
		
				
		id_div = returnDivId(div,request);
		id_loc = returnLocId(id_div, loc,request);
		id_sloc = returnSLocId(id_div , id_loc , sloc,request);
		id_buldng = returnBuldngId(id_div , id_loc , id_sloc, buldng,request);
		//id_buldng = returnVenId(venName);
		
		String sql="update M_Floor set id_div="+id_div+", id_loc="+id_loc+",id_sloc="+id_sloc+",id_building="+id_buldng+" where nm_flr='"+store+"'";
		 
		con3=Common.GetConnection(request);
        PreparedStatement ps2=con3.prepareStatement(sql);
			ps2.executeUpdate();
		

           System.out.println("One row inserted."); 
	}
	
	}catch(Exception e)
	{
		System.out.println("Here some error." + e);
	}
	
	
	
	}
	
	private static String returnDivId(String div,HttpServletRequest request){
		String id="0", sql="";
		try{
			sql ="select id_div from M_Company_Division where nm_div='"+div+"' ";
			con5=Common.GetConnection(request);
			ps5=con5.prepareStatement(sql);
			rs5=ps5.executeQuery();
			if(rs5.next()){
				id = rs5.getString(1);
			}
			
		}catch(Exception e){
			
		}
		
		return id;
	}
	
	private static String returnVenId(String venName,HttpServletRequest request){
		String id="0", sql="";
		try{
			sql ="select id_ven from M_Vendor where nm_ven='"+venName+"' ";
			con5=Common.GetConnection(request);
			ps5=con5.prepareStatement(sql);
			rs5=ps5.executeQuery();
			if(rs5.next()){
				id = rs5.getString(1);
			}else{
				sql = "inser into M_Vendor(cd_ven, nm_ven, add1, add2, add3, city, state, country, phone, nm_contact, ct_phone, service, procured)"+
					  " VALUES ('NA', '"+venName+"', 'NA','NA','NA','NA','NA','NA','NA','NA','NA','service','procured')";
				stmt=con5.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs5 = stmt.getGeneratedKeys();
		            if(rs5.next()) {
		            	id = rs5.getString(1);
		            } 
				
			}
			
		}catch(Exception e){
			
		}
		
		return id;
	}
	
	private static String returnBuldngId(String id_div ,String id_loc,String id_sloc, String buldng,HttpServletRequest request){
		String id="0"; String sql="";
		try{
			
					
			sql ="select id_building from M_Building where id_div= "+id_div+" and id_loc="+id_loc+" and id_sloc="+id_sloc+" and nm_building='"+buldng+"'";
			con4=Common.GetConnection(request);
			ps4=con4.prepareStatement(sql);
			rs4=ps4.executeQuery();
			if(rs4.next()){
				id = rs4.getString(1);
			}else{
				sql = "insert into M_Building(id_div, id_loc, id_country,id_sloc,nm_building,cd_building)"+
					  " VALUES ('"+id_div+"', '"+id_loc+"', 1, '"+id_sloc+"', '"+buldng+"','"+buldng+"')";
				stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs4 = stmt.getGeneratedKeys();
		            if(rs4.next()) {
		            	id = rs4.getString(1);
		            } 
				
			}
			
		}catch(Exception e){
			
		}
		
		return id;
	}
	private static String returnLocId(String id_div , String LocName,HttpServletRequest request){
		String id="0"; String sql="";
		try{
			sql ="select id_loc from M_Loc where nm_loc='"+LocName+"' and id_div= "+id_div+"";
			con4=Common.GetConnection(request);
			ps4=con4.prepareStatement(sql);
			rs4=ps4.executeQuery();
			if(rs4.next()){
				id = rs4.getString(1);
			}else{
				sql = "inser into M_Loc(cd_loc, nm_loc, id_country,id_div)"+
					  " VALUES ('"+LocName+"', '"+LocName+"', 1, "+id_div+")";
				stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs4 = stmt.getGeneratedKeys();
		            if(rs4.next()) {
		            	id = rs4.getString(1);
		            } 
				
			}
			
		}catch(Exception e){
			
		}
		
		return id;
	}
	private static String returnSLocId(String id_div , String id_loc ,String LocName,HttpServletRequest request){
		String id="0"; String sql="";
		try{
			
			sql ="select id_sloc from M_Subloc where id_div= "+id_div+" and id_loc="+id_loc+" and nm_subl='"+LocName+"'";
			con4=Common.GetConnection(request);
			ps4=con4.prepareStatement(sql);
			rs4=ps4.executeQuery();
			if(rs4.next()){
				id = rs4.getString(1);
			}else{
				/*sql = "inser into M_Subloc(cd_loc, nm_loc, id_country,id_div)"+
					  " VALUES ('"+LocName+"', '"+LocName+"', 1, "+id_div+")";
				stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs4 = stmt.getGeneratedKeys();
		            if(rs4.next()) {
		            	id = rs4.getString(1);
		            } 
				*/
			}
			
		}catch(Exception e){
			
		}
		
		return id;
	}
}
