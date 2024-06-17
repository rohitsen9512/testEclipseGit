package com.ldap;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;

import com.Common.Common;
import com.Login.LoginValidation;

import dto.Common.UserAccessData;

public class LDAPAuthentication
{
	
	static ResultSet rs=null;
	static ResultSet rs1=null;
	static PreparedStatement ps=null;
	static Connection con=null;
	static Statement stmt=null;
	
	
	 private static HashMap<String, String> myMap;
	 private static String id_depts;
	  private static String id_ccs;
	 private static String id_bus;
 	 private static String id_locs;
	 private static String id_s_functions;
	 private static String id_buildings;
	 private static String id_flrs;
	 private static String id_slocs;
	 private static String empname;
	 private static int skipedcount=0;
	 
	     
	     
	public static  void LdapStart( HttpServletRequest request)
	
	{
		
		String password = "";
		String base = "";
		String dn = "";
		String ldapURL = "";
		
		Properties prop = new Properties();	
		InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/configuration.properties");
		
		if (inputStream == null) {
			System.out.println("property file 'configuration.properties' not found in the classpath");
		}
		try
		{	
				
			prop.load(inputStream);
	     	 password = prop.getProperty("ldapPassword");
	     	 base = prop.getProperty("LdapBase");
	     	 ldapURL = prop.getProperty("Ldapurl");
	       	dn = prop.getProperty("Ldapdn");
		}
		catch(Exception e)
		{
			System.out.println("Ldap Error... "+e);
		}
		 
		// Setup environment for authenticating
		
		Hashtable<String, String> environment = 
			new Hashtable<String, String>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		environment.put(Context.PROVIDER_URL, ldapURL);
		environment.put(Context.SECURITY_AUTHENTICATION, "simple");
		environment.put(Context.SECURITY_PRINCIPAL, dn);
		environment.put(Context.SECURITY_CREDENTIALS, password);

		try
		{
			DirContext authContext = 
				new InitialDirContext(environment);
			//String FILTER = "(&(objectClass=person) ((sAMAccountName=" + username + ")))"; 
			String FILTER = "(&(objectClass=person))"; 
			SearchControls ctls = new SearchControls(); 
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE); 
			NamingEnumeration<?> answer = authContext.search(base, FILTER, ctls);
			System.out.println("Authenticated successfully.");
			System.out.println("Result:"+answer);
			formatResults(answer,  request);
			//SearchResult sr = (SearchResult) answer.next();
			//String userDN = sr.getNameInNamespace();
			
			
			//System.out.println("sr.getAttributes() : "+sr.getNameInNamespace());
			// user is authenticated
			
		}
		catch (Exception ex)
		{
			System.out.println("Exception : "+ex);
			// Authentication failed

		}
		
	}
	
	public static void formatResults(NamingEnumeration<?> enumer,HttpServletRequest request) throws Exception
    {
	int count = 0;
	
	try
	{
		con=Common.GetConnection(request);
		mapInitialization();
		System.out.println("Started Inserting/Updating data...");
	    while (enumer.hasMore())
	    {
	    	try {
		SearchResult sr = (SearchResult) enumer.next();
		System.out.println("SEARCH RESULT:" + sr.getName());
		empname=sr.getName();
		formatAttributes(sr.getAttributes(),  request);
		System.out.println("===================================================="+count++);
		//count++;
	    	}catch(Exception e) {
	    		System.out.println("One row skipped due to insufficient data...");
	    	}
	    }
	    System.out.println("Search returned " + count + " results");
	}
	catch (Exception e)
	{
	    System.out.println("formatResults : "+e);
	}
    }
	
	 public static void formatAttributes(Attributes attrs,HttpServletRequest request) throws Exception
	    {
		 System.out.println(myMap);
		 System.out.println(attrs);
		 String updateQuery="",inserQueryCol="",inserQueryVal="",cd_emp="",colName="",colVal="",emailID="";
		int id_dept=0,id_cc=0,id_loc=0,id_sloc=0,id_design=0,id_s_function=0,id_bu=0,id_building=0,id_flr=0;	
		String repo_mngr="";
		if (attrs == null)
		{
		    System.out.println("This result has no attributes");
		}
		else
		{
		    try
		    {
		    	 
		    	  int a=0;
				   if(a==0) {
				   for (NamingEnumeration<?> enumer1 = attrs.getAll(); enumer1.hasMore();)
					{
					 
					   Attribute attrib1 = (Attribute) enumer1.next();
					    
					    colName = myMap.get(attrib1.getID());
						   colVal = (String) attrib1.get(0);
					   if(colName!=null) {
						     if(colName.equals("id_dept") && !colVal.equals(""))
						     {
						    	 id_depts=(String) attrib1.get(0);
						      }
						     if(colName.equals("id_cc") && !colVal.equals(""))
						     {
						    	 id_ccs=(String) attrib1.get(0);
						     }
						     if(colName.equals("id_s_function") && !colVal.equals(""))
						     {
						    	 id_s_functions=(String) attrib1.get(0); 
						     }
						     if(colName.equals("id_bu") && !colVal.equals(""))
						     {
						    	 id_bus=(String) attrib1.get(0); 
								   
						     }
						     
						     if(colName.equals("id_loc") && !colVal.equals(""))
						     {
						    	 id_locs=(String) attrib1.get(0); 
									 
						      }
						     if(colName.equals("id_sloc") && !colVal.equals(""))
						     {
						    	 id_slocs=(String) attrib1.get(0); 
										 
						     }
						     if(colName.equals("id_building") && !colVal.equals(""))
						     {
						    	 id_buildings=(String) attrib1.get(0); 
									 
						     }
						     if(colName.equals("id_flr") && !colVal.equals(""))
						     {
						    	 id_flrs=(String) attrib1.get(0);
								   
						     }
						     
						    
					   }
					   a=1;
					}
				   }	
		    	
			for (NamingEnumeration<?> enumer = attrs.getAll(); enumer.hasMore();)
			{
				
			    Attribute attrib = (Attribute) enumer.next();
			    
			    colName = myMap.get(attrib.getID());
				   colVal = (String) attrib.get(0);
				   
				   if(attrib.getID().equals("userPrincipalName")){
				    	emailID=colVal;
				    	
				    	//colName="id_emp";
				    }
				   
				 
				   
				   if(colName!=null) {
			     if(colName.equals("id_dept") && !colVal.equals(""))
			     {
//			    	 String[] parts = colVal.split("-");
//			    	 
//			 			id_dept = DataModel.GetReferenceIdForDeptAndCC("nm_dept",colVal,"M_Dept",0);
//			 			if(parts.length >1) { 
//			 				
//			 			id_cc = DataModel.GetReferenceIdForDeptAndCC("nm_cc",parts[(parts.length)-1].trim(),"M_Company_Costcenter",id_dept);
//			 				
//			 				
//			 				
//			 			}else {  
//			 			id_cc = DataModel.GetReferenceIdForDeptAndCC("nm_cc",parts[0],"M_Company_Costcenter",id_dept);
//			 			
//			 			
//			 			
//			 			
//			 			}
			 			/*updateQuery ="id_dept='"+id_dept+"' , id_cc='"+id_cc+"' ";
					     inserQueryCol="id_dept,id_cc";
					     inserQueryVal="'"+id_dept+"' , '"+id_cc+"' ";*/
					     
			    	 
			    	 id_dept = DataModel.GetIDdept("nm_dept",colVal,"M_Dept",0,  request);
			    	 id_cc = DataModel.GetIdcostCenter("nm_cc",id_ccs,"M_Company_Costcenter",id_dept,  request); 		
			    	 
			    	 id_s_function= DataModel.GetIdsubfunction("nm_s_function",id_s_functions,"m_s_function",id_dept,id_cc,  request);
			    	 id_bu= DataModel.GetIdbu("nm_bu",id_bus,"m_bu",id_dept,id_cc,id_s_function,  request);
				    	 
			    	 
			      	 
			    	 
					     if(updateQuery.equals(""))
					    	 updateQuery ="id_dept='"+id_dept+"' , id_cc='"+id_cc+"', id_s_function='"+id_s_function+"', id_bu='"+id_bu+"' ";
						    else 
						    	updateQuery +=" , id_dept='"+id_dept+"' , id_cc='"+id_cc+"' , id_s_function='"+id_s_function+"', id_bu='"+id_bu+"'";
						    
						    if(inserQueryCol.equals(""))
						    	inserQueryCol="id_dept,id_cc,id_s_function,id_bu";
						    else
						    	inserQueryCol +=",id_dept,id_cc,id_s_function,id_bu";
						    
						    if(inserQueryVal.equals(""))
						    	inserQueryVal="'"+id_dept+"' , '"+id_cc+"' , '"+id_s_function+"' , '"+id_bu+"' ";
						    else
						    	inserQueryVal +=", '"+id_dept+"' , '"+id_cc+"', '"+id_s_function+"' , '"+id_bu+"' ";
					     
			     }else if(colName.equals("id_loc") && !colVal.equals(""))
			     {
			    	
			    	 
			    	 id_loc = DataModel.GetIDdept("nm_loc",colVal,"M_Loc",0,  request);
			    	 if(id_slocs!=null) {
			    		  id_sloc = DataModel.GetIdcostCenter("nm_subl",id_slocs,"M_Subloc",id_loc,  request);
			    	 }
			    	 else {
			    		 
			    		 id_sloc = DataModel.GetIdnull("nm_subl",id_slocs,"M_Subloc",id_loc,0,0,  request);
			    	 }
			    	 if(id_buildings!=null) {
			    		 id_building = DataModel.GetIdsubfunction("nm_building",id_buildings,"m_building",id_loc,id_sloc,  request);
			    		 
			    	 }
			    	 else {
			    		 id_building = DataModel.GetIdnull("nm_building",id_buildings,"m_building",id_loc,id_sloc,0,  request);
						    	  
						    
			    	 }
			    	 if(id_flrs!=null) {
			    		 id_flr= DataModel.GetIdbu("nm_flr",id_flrs,"m_floor",id_loc,id_sloc,id_building,  request);
			    	 }
			    	 else {
			    		 id_flr= DataModel.GetIdnull("nm_flr",id_flrs,"m_floor",id_loc,id_sloc,id_building,  request);
						   }
			    
			    	 
				    	
			    	 
					     
					     if(updateQuery.equals(""))
					    	 updateQuery ="id_loc='"+id_loc+"' , id_sloc='"+id_sloc+"', id_building='"+id_building+"', id_flr='"+id_flr+"' ";
						    else 
						    	updateQuery +=" , id_loc='"+id_loc+"' , id_sloc='"+id_sloc+"', id_building='"+id_building+"', id_flr='"+id_flr+"'  ";
						    
						    if(inserQueryCol.equals(""))
						    	inserQueryCol="id_loc,id_sloc,id_building,id_flr";
						    else
						    	inserQueryCol +=",id_loc,id_sloc,id_building,id_flr";
						    
						    if(inserQueryVal.equals(""))
						    	inserQueryVal="'"+id_loc+"' , '"+id_sloc+"', '"+id_sloc+"', '"+id_flr+"' ";
						    else
						    	inserQueryVal +=", '"+id_loc+"' , '"+id_sloc+"' , '"+id_sloc+"', '"+id_flr+"' ";
					     
			     }else if(colName.equals("id_design") && !colVal.equals(""))
			     {
			    	 id_design = DataModel.GetIDDesignation("nm_design",colVal,"M_Designation",  request);

			 			if(updateQuery.equals(""))
					    	 updateQuery ="id_design='"+id_design+"'";
						    else 
						    	updateQuery +=" , id_design='"+id_design+"' ";
						    
						    if(inserQueryCol.equals(""))
						    	inserQueryCol="id_design";
						    else
						    	inserQueryCol +=",id_design";
						    
						    if(inserQueryVal.equals(""))
						    	inserQueryVal="'"+id_design+"'";
						    else
						    	inserQueryVal +=", '"+id_design+"'";
					     
			     }
			     
			     else if(colName.equals("repo_mngr") && !colVal.equals(""))
			     {
			    	 String temp="";
			    	 //colVal="CN=Vinayak BG - X00208,OU=Banglore,OU=India,OU=MCHP-MAIN,DC=mchp-main,DC=com";
//			    	 String[] parts = colVal.split(",");
//			    	 for(int i=0;i<parts.length;i++){
//			    		 if(parts[i].contains("CN=")){
//			    			 temp = parts[i].split("=")[1]; 
//			    			 //colVal = temp.split("-")[1];
//			    			 colVal = temp;
//			    			 colVal = colVal.trim();
//			    			 break;
//			    		 }
//			    	 }
			    	 repo_mngr = DataModel.GetIDEmployee("adrepo_mngr",colVal,"M_Emp_User",  request);
			    	 System.out.println(colVal);
			 			if(updateQuery.equals(""))
					    	 updateQuery ="repo_mngr='"+repo_mngr+"'";
						    else 
						    	updateQuery +=" , repo_mngr='"+repo_mngr+"' ";
						    
						    if(inserQueryCol.equals(""))
						    	inserQueryCol="repo_mngr";
						    else
						    	inserQueryCol +=",repo_mngr";
						    
						    if(inserQueryVal.equals(""))
						    	inserQueryVal="'"+repo_mngr+"'";
						    else
						    	inserQueryVal +=", '"+repo_mngr+"'";
					     
			     }
			     else if(colName.equals("distinguishedName") && !colVal.equals(""))
			     {
			    	 String temp="";
			    	
			    	 System.out.println(colVal);
			 			if(updateQuery.equals(""))
					    	 updateQuery ="adrepo_mngr='"+colVal+"'";
						    else 
						    	updateQuery +=" , adrepo_mngr='"+colVal+"' ";
						    
						    if(inserQueryCol.equals(""))
						    	inserQueryCol="adrepo_mngr";
						    else
						    	inserQueryCol +=",adrepo_mngr";
						    
						    if(inserQueryVal.equals(""))
						    	inserQueryVal="'"+colVal+"'";
						    else
						    	inserQueryVal +=", '"+colVal+"'";
					     
			     }
			     else if(colName.equals("id_emp") && !colVal.equals(""))
			     {
			    	 String emp_type="Employee";
			    	 if(colVal.startsWith("X"))
			    		 emp_type = "Contract";
			    	 
			    	 cd_emp = colVal;
			 			if(updateQuery.equals(""))
					    	 updateQuery =colName+"= '"+colVal+"'"+ " , emp_type='"+emp_type+"'";
						    else 
						    	updateQuery +=" , emp_type='"+emp_type+"' ," + colName+"= '"+colVal+"'";
						    
						    if(inserQueryCol.equals(""))
						    	inserQueryCol=" emp_type ," + colName;
						    else
						    	inserQueryCol +=",emp_type , "+ colName;
						    
						    if(inserQueryVal.equals(""))
						    	inserQueryVal="'"+emp_type+"', "+"'"+colVal+"'";
						    else
						    	inserQueryVal +=", '"+emp_type+"' ," +"'"+colVal+"'";
					     
			     }
			     
			     else
			    	 if(!colName.equals("id_flr") && !colName.equals("id_sloc") && !colName.equals("id_s_function") && !colName.equals("id_bu") && !colName.equals("id_building") && !colName.equals("id_cc") )
					  {// 
			     
			     
				    if(updateQuery.equals(""))
				    	updateQuery =colName+"= '"+colVal+"'";
				    else 
				    	updateQuery +=", "+colName+"= '"+colVal+"'";
				    
				    if(inserQueryCol.equals(""))
				    	inserQueryCol = colName;
				    else
				    	inserQueryCol += ", "+colName;
				   
				    
				    if(inserQueryVal.equals(""))
				    	inserQueryVal = "'"+colVal+"'";
				    else
				    	inserQueryVal += ", '"+colVal+"'";
			   }
			   // System.out.println("inserQueryCol :" + inserQueryCol +" inserQueryVal : "+inserQueryVal);
			   // System.out.println("updateQuery :" + updateQuery);
			    /*for (NamingEnumeration<?> e = attrib.getAll(); e.hasMore();)
			    {
				Object value = e.next();
				boolean canPrint = isAsciiPrintable(value);
				if (canPrint)
				{
				    System.out.println("\t\t    Value:    = " + value);
				}
				else
				{
				    System.out.println("\t\t        = <-value is not printable->");
				}
			    }*/
			}
			}
			
			//if(!cd_emp.equals("") && !emailID.equals(""))
			System.out.println(cd_emp);
				if(!cd_emp.equals(""))
				AddUpdateData(cd_emp,updateQuery,inserQueryCol,inserQueryVal);
				else
				{
					
					System.out.println("Email Id is not persent for user "+empname+" hence we have skiped this user for insertion and updation.----------" + ++skipedcount);
				}
			
		    }
		    catch (NamingException e)
		    {
		    	System.out.println("NamingException : "+e);
		    }
		}
	    }
	
	 public static void AddUpdateData(String id_emp, String updateQuery,String inserQueryCol, String inserQueryVal){
		 try{
		 String sql="select id_emp_user from M_Emp_User where id_emp='"+id_emp+"'";
		 System.out.println(sql);
		 ps=con.prepareStatement(sql);
		 rs = ps.executeQuery();
		 if(rs.next()){
			 sql = "update M_Emp_User set "+updateQuery+",status_emp='Working' where id_emp_user="+rs.getInt(1)+"";
			
			 System.out.println(sql);
			 ps=con.prepareStatement(sql);
			 ps.executeUpdate();
		 }else{
			 sql = "insert into M_Emp_User ("+inserQueryCol+",status_emp) values("+inserQueryVal+",'Working')";
			 System.out.println(sql);
			 ps=con.prepareStatement(sql);
			 ps.executeUpdate();
		 }
		 
		 }catch(Exception e){
			 System.out.println("Data Base Error : "+e);
		 }
		 
	 }
	 

	 public static void  mapInitialization(){
		        myMap = new HashMap<String, String>();
		        
		        Properties prop = new Properties();	
				InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/configuration.properties");
				
				if (inputStream == null) {
					
					System.out.println("property file 'configuration.properties' not found in the classpath");
				}
				try
				{	
						
					prop.load(inputStream);
					
				String ldapidconfig = prop.getProperty("ldapidconfig");
				     	
				String idSplit []=ldapidconfig.split(",");
				//System.out.println("pritesh"+idSplit);
				for(int i=0;i<=idSplit.length;i++) {
					
					String spitvalid []=idSplit[i].split(":");
					
					myMap.put(spitvalid[0], spitvalid[1]);
					
				}
					
				/*
				 * myMap.put("displayName", "nm_emp"); myMap.put("mail", "id_emp");
				 * myMap.put("mobile", "cont_no"); myMap.put("title", "id_design");
				 * myMap.put("division", "id_dept"); myMap.put("manager", "repo_mngr");
				 * myMap.put("l", "id_loc"); // myMap.put("st", "id_sloc"); //
				 * myMap.put("street", "id_building"); // myMap.put("streetAddress", "id_flr");
				 * 
				 * myMap.put("department", "id_s_function"); myMap.put("bU", "id_cc");
				 * myMap.put("subFunction", "id_bu");
				 */
				}
				catch(Exception e)
				{
					System.out.println("Ldap id config Error... "+e);
				}
				 
		     	
		    	
		    
	 }
	 
	 
	 /**
	     * Check to see if this Object can be printed.
	     * 
	     * @param obj
	     * @return
	     */
	    public static boolean isAsciiPrintable(Object obj)
	    {
		String str = null;
		try
		{
		    str = (String) obj;
		}
		catch (Exception e)
		{
		    return false;
		    // TODO Auto-generated catch block e.printStackTrace();
		}
		if (str == null)
		{
		    return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++)
		{
		    if (isAsciiPrintable(str.charAt(i)) == false)
		    {
			return false;
		    }
		}
		return true;
	    }

	    /**
	     * Used by isAsciiPrintable(Object obj)
	     * 
	     * @param ch
	     * @return
	     */
	    public static boolean isAsciiPrintable(char ch)
	    {
		return ch >= 32 && ch < 127;
	    }
	 
}
