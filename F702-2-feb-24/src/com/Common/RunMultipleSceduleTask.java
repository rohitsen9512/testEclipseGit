package com.Common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.Mail.MailInbox;

import dto.Common.UserAccessData;


public class RunMultipleSceduleTask  {
	 public static Connection con=null;
    private static long START_TIME;

    public static void runmultipleTask() throws InterruptedException, ServletException, IOException {

    	 Properties prop = new Properties();	
 		InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/mailConfiguration.properties");
 		
 		if (inputStream == null) {
 			System.out.println("property file 'mailConfiguration.properties' not found in the classpath");
 		}
 		String DealyTime ="",repetetimemilisec="",followUPrepeatTimesec="";
 		try
 		{
 			prop.load(inputStream);
 			 DealyTime = prop.getProperty("DealyTime");
 			 repetetimemilisec = prop.getProperty("InboxMailrepetetimesec");
 			followUPrepeatTimesec = prop.getProperty("followUPrepeatTimesec");
 				
 			}
 				catch(Exception e)
 		{
 			System.out.println("Connection Error... "+e.toString());
 		}
 		System.out.println("testing done");
 		 long delay = Long.valueOf(DealyTime);
 		 long repeat = Long.valueOf(repetetimemilisec);
 		 long repeatforfollouptime = Long.valueOf(followUPrepeatTimesec);
    	
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(100000000);
        
       

        START_TIME = System.currentTimeMillis();
 
        Runnable taskgetmailinboxdata = getmailinboxdata();
        ////scheduledExecutorService.scheduleAtFixedRate(taskgetmailinboxdata,delay,repeat, TimeUnit.SECONDS);
   
        
            
     //   Runnable taskFollowupMail = runCloseAcountOfUser();
      
        Runnable taskCloseAcountOfUser = runFollowupMail();
       
        scheduledExecutorService.scheduleAtFixedRate(taskCloseAcountOfUser,0,24, TimeUnit.HOURS);
        //scheduledExecutorService.scheduleAtFixedRate(taskCloseAcountOfUser,2,50, TimeUnit.SECONDS);
        /*   scheduledExecutorService.scheduleAtFixedRate(taskFollowupMail,delay,repeatforfollouptime, TimeUnit.SECONDS);
        
*/        
        
    }
    
    public static   Runnable getmailinboxdata() {
    	 
        return () -> MailInbox.getMailinboxdata();
    }

    
    public static   Runnable runFollowupMail() {
    	 
        return () -> ExpiryAllTaskNotification.ExpiryRental();
    }
    /*
    
    public static   Runnable runCloseAcountOfUser() {
   	 
    	 return () ->  {
 			try {
 				new Common().CloseAcountOfUser();
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 		};
    }
 */
}