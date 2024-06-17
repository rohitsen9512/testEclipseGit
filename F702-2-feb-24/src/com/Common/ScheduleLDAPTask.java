package com.Common;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import com.ldap.LDAPAuthentication;

import dto.Common.UserAccessData;

public class ScheduleLDAPTask extends TimerTask {
	long x=1;
	Timer timer = new Timer();  
	

    public void run(HttpServletRequest request) {
    		System.out.println("Check Ldap");
    		  Date currentTime = Calendar.getInstance().getTime();
    		     System.out.println(currentTime);
    		LDAPAuthentication.LdapStart( request);
    
    		
    	
    	
    }
  
    public static void startTask(){
    	ScheduleLDAPTask task = new ScheduleLDAPTask();
        Timer timer = new Timer();  
       
       Properties prop = new Properties();	
		InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/mailConfiguration.properties");
		
		if (inputStream == null) {
			System.out.println("property file 'mailConfiguration.properties' not found in the classpath");
		}
		String DealyTime ="",repetetimemilisec="";
		try
		{
			prop.load(inputStream);
			 DealyTime = prop.getProperty("DealyTime");
			 repetetimemilisec = prop.getProperty("repetetimemilisec");
				
			}
				catch(Exception e)
		{
			System.out.println("Connection Error... "+e.toString());
		}
		 long num2 = Long.valueOf(DealyTime);
		 long num3 = Long.valueOf(repetetimemilisec);
	    
	   
        timer.schedule(task,num2,num3);
       // timer.cancel();
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}