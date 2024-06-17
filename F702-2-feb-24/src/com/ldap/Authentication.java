package com.ldap;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Authentication extends HttpServlet {
	private static final long serialVersionUID = 1L;
HttpSession session;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session = request.getSession(true);
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//String username = "super";
		//String password = "Patel@301291";
		String base = "OU=MCHP-MAIN,DC=mchp-main,DC=com";
		//String base = "CN=Users,dc=ptplqa,dc=local";
		//String dn = "uid=" + username + "," + base;
		String dn = "cn=" + username + ",OU=India," + base;
		String ldapURL = "ldap://idc-sv-ad03:389";
		//String ldapURL = "ldap://14.141.234.49:389";

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
			//DirContext authContext = new InitialDirContext(environment);
			LdapContext ctx = new InitialLdapContext(environment, null);
			 
		    // Activate paged results
		    int pageSize = 500; 
		    byte[] cookie = null;
		        ctx.setRequestControls(new Control[]{
		        new PagedResultsControl(pageSize, Control.NONCRITICAL) });
			
			do{
			
			//String FILTER = "(&(objectClass=person) ((cn=" + username + ")))"; 
			String FILTER = "(&(objectClass=person) )"; 
			SearchControls ctls = new SearchControls(); 
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE); 
			String[] attrIDs = { "sAMAccountName",
                    "displayName",
                   // "userPrincipalName",
                    "telephoneNumber",
                    "title",
                    "department",
                    "l",
                    "manager",
                    "mail"
                    };
			
			ctls.setReturningAttributes(attrIDs);
            
			NamingEnumeration<?> answer = ctx.search(base, FILTER, ctls);
			//LDAPAuthentication auth = new LDAPAuthentication();
			LDAPAuthentication.formatResults(answer,  request);
			
			Control[] controls = ctx.getResponseControls();
		    if (controls != null) {
		        for (int i = 0; i < controls.length; i++) {
		            if (controls[i] instanceof
		                PagedResultsResponseControl) {
		                PagedResultsResponseControl prrc =
		                    (PagedResultsResponseControl) controls[i];
		                cookie = prrc.getCookie();
		            }
		        }
		    }
		    System.out.println("hhhh");
		    ctx.setRequestControls(new Control[] {
		            new PagedResultsControl(pageSize,
		                                    cookie,
		                                    Control.CRITICAL) });
		    
			 } while (cookie != null);
			
			//SearchResult sr = (SearchResult) answer.next();
			//String userDN = sr.getName();
			
			//System.out.println("Authenticated successfully."+userDN);
			//System.out.println("sr.getAttributes() : "+sr.getNameInNamespace());
			
			//session.setAttribute("answer", userDN);
			response.sendRedirect("successful.jsp");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception while authentication : "+ex);
			// Authentication failed

		}
		
	}

}
