package com.Common;


import java.util.Timer;
import java.util.TimerTask;

import com.Purchase.Request_Quotation;
import com.auction.StartAuction;




public class BidTask extends TimerTask {
	long x=1;
	Timer timer = new Timer();  
    @Override
    public void run() {
    	
    	  
    	  
    	  
    	  
		
		
		  
		  if( x > 1 && x < 3) { 
		    System.out.println("pritesh"+Common.cancleBid);
		    new StartAuction().cancelbid(Common.tendor_name);
	        
		  
		  }
		 
    	//System.out.println(x++); 
    	  x++;
      
    }
  
    public void startTask(){
    	System.out.println("pritesh"+Common.cancleBid);
    	BidTask task = new BidTask();
        Timer timer = new Timer();  
        timer.schedule(task,0,Common.cancleBid);
      
        
       // timer.cancel();
    }

}