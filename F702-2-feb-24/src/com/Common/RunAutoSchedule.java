package com.Common;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

public class RunAutoSchedule implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Running Auto");
	try {
			
			
			RunMultipleSceduleTask.runmultipleTask();
			
			
		} catch (InterruptedException e) {
        	e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	/*	new ScheduleLDAPTask();
		ScheduleLDAPTask.startTask();
		*/
	}

}
