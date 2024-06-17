<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
a {
    color: green;
}
.button {
  display: inline-block;
  padding: 15px 25px;
  font-size: 24px;
  cursor: pointer;
  text-align: center;	
  text-decoration: none;
  outline: none;
  color: #fff;
  background-color: #4CAF50;
  border: none;
  border-radius: 15px;
  box-shadow: 0 9px #999;
}

.button:hover {background-color: #3e8e41}

.button:active {
  background-color: #3e8e41;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}
</style>
 <script>
 
 transferDash();
 
 function transferDash(){
	 var mainAr = Array();
		var key1 = new Array();
		var value1 = new Array();
		var keyF=new Array();
		var valueF=new Array();
		
		
		
		var id_finance=$('select[name="id_finance"]').val();
	var	Datefinance=$('#financialYearForSearch').children(':selected').text();

	var splittext =Datefinance.split('-');
		
		var currentLocation = window.location.href;
		 
		  var url=currentLocation.slice(0, -9)+"/ReportView/Transfer_Report_Dash.jsp?";  
	    
				  $.post("AllDashboard",{action:"tranferDashboard",id_finance:id_finance},function (r){
					  
					  if(r.data1.length>0){
						  
						  for(var i=0;i<r.data1.length;i++){
							  for (var key in r.data1[i])
						        {
								  key1.push(key);
					              value1.push(parseFloat(r.data1[i][key]));
								//  console.log(key+"============================="+r.data1[i][key]);
									
								  var temp = new Array(key1[i],value1[i]); 
								   mainAr[i] = temp;  
						         
						        }
							    
						  }
					 
					chart(mainAr);
					  
					  
					  }
					
				
			  },'json');
	   
				  function chart(mainAr){
					     
						$('#container').highcharts({
	 						 chart: {
	 								     plotBackgroundColor: null,
	 								     plotBorderWidth: null,
							             plotShadow: false,
								         type: 'pie'
									     },
						    credits: {
	  								    enabled: false
								     },
	 					  exporting: {
										         enabled: false
	                               },

	                        title: {
	                        	style: {
	                                color: '#000080',
	                                fontWeight: 'bold'
	                            },
	                                    text: "Transfer Dashboard"
	                               },
	                        tooltip: {
	                      	  
	                              pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	                               },
	                               legend: 
	                               {
	                                   layout: 'vertical',
										 align: 'left',
									  },
	                        plotOptions: {
	                                           pie: {
	                                                 allowPointSelect: true,
	                                                 cursor: 'pointer',
	                                                  dataLabels: {
	                                                                enabled: true,
	            									 			      format: '<b>{point.name}</b>: {y} ',
	                                                                allowDecimals: false,
	                                                                style: {
	                                             		                       color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                                                                        }
	          													},
	    												      		showInLegend: true,
	    												      		
	    												      		point: {
	    									                            events: {
	    									                                    click: function() {
	    									                           
	    	                                                              var mon=(this.name).split('('); 
	    	                                                              var getmon=mon[0];
	    	                                                                  
	    	                                                                   var strtdt=["-04-01","-05-01","-06-01","-07-01","-08-01","-09-01","-10-01","-11-01","-12-01","-01-01","-02-01","-03-01"];
	    	                                                                  var enddt=["-04-30","-05-31","-06-30","-07-31","-08-31","-09-30","-10-31","-11-30","-12-31","-01-30","-02-28","-03-30"];
	    	                                                                  var Month=["April","May","June","July","August","September","October","November","December","January","February","March"];
	    	                                                                    getmon=  Month .indexOf(getmon);
	    	                                                                     //alert(getmon);	

	    	                                                                     var currentYear ;
	    	                                                                     if(mon[0]=="January" || mon[0]=="February" || mon[0]=="March"){
	    	                                                                     	//currentYear= new Date().getFullYear() + 1;
	    	                                                                        currentYear= new Date().getFullYear() ;
	    	                                                                     }
	    	                                                                    else{
	    	                                                                    	currentYear = new Date().getFullYear();
	    	                                                                    }

	    	                                                                    

	    	                                                                     var strt= currentYear+strtdt[getmon];
	    	                                                                      var end= currentYear+enddt[getmon];

	    	                                                                         var dt='startdate='+strt+'&enddate='+end;


	    	                                                                     //alert(strt+'---'+end);	



	    									                             window.open(url+dt);

	    									                                        }
	    									                                }

	    															}
	  											   },
	  											  
	  									},
	 						 series: [{
	                       			name: 'Total Count',
	                      			colorByPoint: true,
	                                  data:mainAr,
	                                  url:'www.google.com'
	                                        
										}]
					});

	}
	}
 
 </script>
 <body>
	
 <div id="container"
		style="min-width: 310px; height: 400px; max-width: 900px; margin-top:20px "></div>
 
 </body>
	


