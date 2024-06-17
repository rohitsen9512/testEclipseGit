function DashCountTotal(callback){
	
	  $.post('AllDashboard',{action:'DashCount'},function (r){ 
		
		console.log(r);
		//alert(r.data[0].vendor);
		
	
		/*$(".comp").attr("data-to",r.data[0].comp)
		$(".comp").text(r.data[0].comp);
		$(".pending").attr("data-to",r.data[1].pending)
		$(".pending").text(r.data[1].pending);
		$(".approve").attr("data-to",r.data[2].approve)
		$(".approve").text(r.data[2].approve);
		$(".work").attr("data-to",r.data[3].work)
		$(".work").text(r.data[3].work);
		$(".popending").attr("data-to",r.data[4].popending)
		$(".popending").text(r.data[4].popending);
		$(".invoicepending").attr("data-to",r.data[0].invoicepending)
		$(".invoicepending").text(r.data[0].invoicepending);
		$(".tot_vendor").attr("data-to",r.data[0].vendor)
		$(".tot_vendor").text(r.data[0].vendor);
		$(".payments").attr("data-to",r.data[0].payment)
		$(".payments").text(r.data[0].payment);
		$(".In_store_Asset").attr("data-to",r.data[0].In_store_Asset)
		$(".In_store_Asset").text(r.data[0].In_store_Asset);
		$(".Allocated_Asset").attr("data-to",r.data[0].Allocated_Asset)
		$(".Allocated_Asset").text(r.data[0].Allocated_Asset);
		$(".damagedAsset").attr("data-to",r.data[0].damagedAsset)
		$(".damagedAsset").text(r.data[0].damagedAsset);
		$(".totalaccess").attr("data-to",r.data[0].totalaccess)
		$(".totalaccess").text(r.data[0].totalaccess);
		$(".availAccess").attr("data-to",r.data[0].availAccess)
		$(".availAccess").text(r.data[0].availAccess);
		$(".linkacc").attr("data-to",r.data[0].linkacc)
		$(".linkacc").text(r.data[0].linkacc);*/
		
		$(".Rental_Product").attr("data-to",r.data[0].Rental_Product);
		$(".Rental_Product").text(r.data[0].Rental_Product);
		$(".Total_sale").text(r.data[0].Total_sale);
		$(".Total_sale").attr("data-to",r.data[0].Total_sale);
		$(".In_store_Product").text(r.data[0].In_store_Product);
		$(".In_store_Product").attr("data-to",r.data[0].In_store_Product);
		$(".invoicepending").attr("data-to",r.data[0].In_store_Product);
		$(".invoicepending").text(r.data[0].invoicepending);
		$(".create_inv").attr("data-to",r.data[0].create_inv);
	    $(".create_inv").text(r.data[0].create_inv);
		
		//$(".In_store_Asset").text(r.data[0].In_store_Asset);
		
		//PI Chart js...


		
		
		
		 },'json'); 
	return callback('1');
	
}


function poststushomedash(){
	var id_finance='';
	var	Datefinance='';
	
	 $.post('M_Financial_Year',{action : 'DropDownResult'},function (r){
 		
 	
 				for(var i = 0; i < r.data.length ; i++)
 				{
 				if(r.data[i].active_year == 1)
 					{
 					
				 id_finance=r.data[i].id_fincance;
	             Datefinance=r.data[i].std_finance+' - '+r.data[i].end_finance;
                            i=r.data.length;
				
	
	var mainAr = Array();
	var key1 = new Array();
	var value1 = new Array();
	var keyF=new Array();
	var valueF=new Array();
	

var splittext =Datefinance.split('-');
	
	var currentLocation = window.location.href;
	
	
	var url='';
	
	if(currentLocation.slice(-1)=="#"){
		
		url=currentLocation.slice(0, -9)+"ReportView/AllPO_Reportview.jsp?startdate="+splittext[0]+"&enddate="+splittext[1]+"";
	    
	}
	else{
		url=currentLocation.slice(0, -8)+"ReportView/AllPO_Reportview.jsp?startdate="+splittext[0]+"&enddate="+splittext[1]+"";
			
	}
		
			  $.post("AllDashboard",{action:"poDashboard",id_finance:id_finance},function (r){
				  
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
				     
					$('#container1').highcharts({
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
            
                                    text: 'Purchase Order Status.'
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
    												      		showInLegend: false,
    												      		
    												      		 point: {
    										                            events: {
    										                                    click: function() {
    										                                    	
    										                             window.open(url);
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
 				
 				}
 			
 		
 		
 	},'json');

}

function PoDashboardCount(id_finance){
	
		
		   $.post('AllDashboard',{action:'poDashboard',id_finance:id_finance},function (r){ 

			  var listdt='<tr style="font-size:13px;border="1""><td style="width: 100px;font-size:13px;color: blue;"><strong><center>Month</center></strong></td><td style="color: blue;"><strong><center>Total Purchase Order Month Wise</center></strong></td>'+
			  '<tr><td style="width: 100px;color: #1616d6;"><strong><center>January</center></strong></td><td><strong><center>'+(r.data1[0].April)+'</center></strong></td>'+
	           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>February</center></strong></td><td><strong><center>'+(r.data1[1].May)+'</center></strong></td>'+
	           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>March</center></strong></td><td><strong><center>'+(r.data1[2].June)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>April</center></strong></td><td><strong><center>'+(r.data1[3].July)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>May</center></strong></td><td><strong><center>'+(r.data1[4].August)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>June</center></strong></td><td><strong><center>'+(r.data1[5].September)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>July</center></strong></td><td><strong><center>'+(r.data1[6].October)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>August</center></strong></td><td><strong><center>'+(r.data1[7].November)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>September</center></strong></td><td><strong><center>'+(r.data1[8].December)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>October</center></strong></td><td><strong><center>'+(r.data1[9].January)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>November</center></strong></td><td><strong><center>'+(r.data1[10].February)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>December</center></strong></td><td><strong><center>'+(r.data1[11].March)+'</center></strong></td>';
			           
				$('#listdt').html(listdt);
			
  },'json'); 
	
	
}

function deviceStatus(){
	

	var mainAr = Array();
	var key1 = new Array();
	var value1 = new Array();
	var keyF=new Array();
	var valueF=new Array();
	
	var group='';
	var subgrp='';
	var model='';
	var currentLocation = window.location.href;
	var x='';
	if(group==''){
		 x ='All';
	 }
	
	var url='';
	
	if(currentLocation.slice(-1)=="#"){
		
		url=currentLocation.slice(0, -9)+"ReportView/Location_Asset_report1.jsp?id_grp="+x+"&id_sgrp="+subgrp+"";
	    
	}
	else{
		url=currentLocation.slice(0, -8)+"ReportView/Location_Asset_report1.jsp?id_grp="+x+"&id_sgrp="+subgrp+"";
			
	}
			  $.post("AllDashboard",{action:"devicestatus",group:group,subgrp:subgrp,model:model},function (r){
				  
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
					  
				 
					//  console.log("Main AR ="+mainAr);
			//	console.log(mainAr);
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
		    textdecoration: 'underline',
            color: '#000080',
            fontWeight: 'bold'
            
        },
                                    text: 'Hardware status.'
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
    												      		showInLegend: false,
    												      		
    												      		 point: {
    										                            events: {
    										                                    click: function() {
    										                                    	
    										                             window.open(url);
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
	 

function CiculerPiChartPendingReject(callback){
	
	  $.post('AllDashboard',{action:'PendingReject'},function (r){ 
		console.log(r);
		//PI Chart js...
var chartDiv = $("#barChart");
var myChart = new Chart(chartDiv, {
    type: 'pie',
    data: {
        labels: ["Approve", "Reject", "Complete", "Cancelled","Pending"],
        datasets: [
        {
            data: [r.data[2].approve,r.data[3].reject, r.data[0].comp, r.data[1].cancel,r.data[4].pending],
            backgroundColor: [
            "#FF6384",
            "#4BC0C0",
            "#FFCE56",
            "#E7E9ED",
            "#36A2EB"
            ]
        }]
    },
    options: {
        title: {
            display: true,
            text: 'Purchase'
        },
		responsive: true,
maintainAspectRatio: false,
    }
});
		$("#comp").text(r.data[0].comp);
		$("#pending").text(r.data[4].pending);
		$("#approve").text(r.data[2].approve);
		$("#reject").text(r.data[3].reject);
		$("#cencel").text(r.data[1].cancel);
		
		 },'json'); 
	return callback('1');
	
}


function PoDashboard(){
	
		
		   $.post('AllDashboard',{action:'poDashboard'},function (r){ 
			//   alert('Requested Date should be between financial year : '+r.data[0].tot);
				  var context = document.getElementById('clients').getContext('2d');
			  var barChart = new Chart(context, {
			  	  type: 'doughnut',
			  	  data: {
			  	  //labels: ["A. "+ freq [0] +"cr." , "B. "+ freq [1] +"cr.", "C. "+ freq [2] +"cr.", "Total. " +freq [3] +"cr."],
			  	      labels: ["January", "February", "March","April", "May", "June", "July","August", "September", "October","November", "December", ],
			  	  datasets: [{
			  		label: 'Installed asset(Value)',
			  	    data: [r.data[0].tot, r.data[1].tot,r.data[2].tot,r.data[3].tot, r.data[4].tot,
			  	           r.data[5].tot,r.data[6].tot, r.data[7].tot,r.data[8].tot,r.data[9].tot, r.data[10].tot,r.data[11].tot,],
			  	    
			  	     // data: [freq [0], freq [1], freq [2], freq [3]],
			  	  borderColor: [
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)'
			  	            ],
			  	            backgroundColor: [
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)'
			  	            ],
			  	            
			  	          borderWidth: 1
			  	    }]
			  	    
			  	  },
			  	 
			        options: {
			            responsive: false,

            text: 'Purchase'
			
			        }
			  	  
			  	});
		/*	  var context = document.getElementById('clientsline').getContext('2d');
			  var barChart = new Chart(context, {
			  	  type: 'line',
			  	  data: {
			  	  //labels: ["A. "+ freq [0] +"cr." , "B. "+ freq [1] +"cr.", "C. "+ freq [2] +"cr.", "Total. " +freq [3] +"cr."],
			  	      labels: ["January", "February", "March","April", "May", "June", "July","August", "September", "October","November", "December", ],
			  	  datasets: [{
			  	      label: 'Purchase Order(No.)',
			  	    data: [r.data[0].tot, r.data[1].tot,r.data[2].tot,r.data[3].tot, r.data[4].tot,
			  	           r.data[5].tot,r.data[6].tot, r.data[7].tot,r.data[8].tot,r.data[9].tot, r.data[10].tot,r.data[11].tot,],
			  	    
			  	     // data: [freq [0], freq [1], freq [2], freq [3]],
			  	  borderColor: [
			  	               'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)'
			  	            ],

			  	            backgroundColor: [
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)'
			  	            ],
			  	          borderWidth: 1
			  	    }]
			  	    
			  	  },
			  	 
			        options: {
			            responsive: false,

            text: 'Purchase'
			           
			        }
			  	  
			  	});*/
		       	



			  var listdt='<tr style="font-size:13px;border="1""><td style="width: 100px;font-size:13px;color: blue;"><strong><center>Month</center></strong></td><td style="color: blue;"><strong><center>Total Installed asset Value</center></strong></td>'+
			  '<tr><td style="width: 100px;color: #1616d6;"><strong><center>January</center></strong></td><td><strong><center>'+parseFloat(r.data[0].count)+'</center></strong></td>'+
	           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>February</center></strong></td><td><strong><center>'+parseFloat(r.data[1].count)+'</center></strong></td>'+
	           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>March</center></strong></td><td><strong><center>'+parseFloat(r.data[2].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>April</center></strong></td><td><strong><center>'+parseFloat(r.data[3].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>May</center></strong></td><td><strong><center>'+parseFloat(r.data[4].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>June</center></strong></td><td><strong><center>'+parseFloat(r.data[5].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>July</center></strong></td><td><strong><center>'+parseFloat(r.data[6].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>August</center></strong></td><td><strong><center>'+parseFloat(r.data[7].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>September</center></strong></td><td><strong><center>'+parseFloat(r.data[8].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>October</center></strong></td><td><strong><center>'+parseFloat(r.data[9].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>November</center></strong></td><td><strong><center>'+parseFloat(r.data[10].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>December</center></strong></td><td><strong><center>'+parseFloat(r.data[11].count)+'</center></strong></td>';
			           
				$('#listdt').html(listdt);
			
  },'json'); 
	
	
}

function PoVendorDashboard(){
	
		
		   $.post('AllDashboard',{action:'poVendorDashboard'},function (r){ 
			//   alert('Requested Date should be between financial year : '+r.data[0].count);
				  var context = document.getElementById('vendorpo1').getContext('2d');
			  var barChart = new Chart(context, {
			  	  type: 'bar',
			  	  data: {
			  	  //labels: ["A. "+ freq [0] +"cr." , "B. "+ freq [1] +"cr.", "C. "+ freq [2] +"cr.", "countal. " +freq [3] +"cr."],
			  	      labels: ["January", "February", "March","April", "May", "June", "July","August", "September", "October","November", "December", ],
			  	  datasets: [{
			  		label: 'Purchase Orders',
			  	    data: [r.data[0].count, r.data[1].count,r.data[2].count,r.data[3].count, r.data[4].count,
			  	           r.data[5].count,r.data[6].count, r.data[7].count,r.data[8].count,r.data[9].count, r.data[10].count,r.data[11].count,],
			  	    
			  	     // data: [freq [0], freq [1], freq [2], freq [3]],
			  	  borderColor: [
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)'
			  	            ],
			  	            backgroundColor: [
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)'
			  	            ],
			  	            
			  	          borderWidth: 1
			  	    }]
			  	    
			  	  },
			  	 
			        options: {
			            responsive: false,

            text: 'Purchase'
			
			        }
			  	  
			  	});
			  var context = document.getElementById('vendorpo').getContext('2d');
			  var barChart = new Chart(context, {
			  	  type: 'line',
			  	  data: {
			  	  
			  	      labels: ["January", "February", "March","April", "May", "June", "July","August", "September", "October","November", "December", ],
			  	  datasets: [{
			  	      label: 'Purchase Orders',
			  	    data: [r.data[0].count, r.data[1].count,r.data[2].count,r.data[3].count, r.data[4].count,
			  	           r.data[5].count,r.data[6].count, r.data[7].count,r.data[8].count,r.data[9].count, r.data[10].count,r.data[11].count,],
			  	    
			  	     // data: [freq [0], freq [1], freq [2], freq [3]],
			  	  borderColor: [
			  	               'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)'
			  	            ],

			  	            backgroundColor: [
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)'
			  	            ],
			  	          borderWidth: 1
			  	    }]
			  	    
			  	  },
			  	 
			        options: {
			            responsive: false,

            text: 'Purchase'
			           
			        }
			  	  
			  	});
		       	
 var listdtpo='<tr style="font-size:13px;border="1""><td style="width: 100px;font-size:13px;color: blue;"><strong><center>Month</center></strong></td><td style="color: blue;"><strong><center>&nbsp;&nbsp;&nbsp;&nbsp;Total PO&nbsp;&nbsp;&nbsp;</center></strong></td>'+
			  '<tr><td style="width: 100px;color: #1616d6;"><strong><center>January</center></strong></td><td><strong><center>'+parseFloat(r.data[0].count)+'</center></strong></td>'+
	           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>February</center></strong></td><td><strong><center>'+parseFloat(r.data[1].count)+'</center></strong></td>'+
	           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>March</center></strong></td><td><strong><center>'+parseFloat(r.data[2].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>April</center></strong></td><td><strong><center>'+parseFloat(r.data[3].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>May</center></strong></td><td><strong><center>'+parseFloat(r.data[4].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>June</center></strong></td><td><strong><center>'+parseFloat(r.data[5].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>July</center></strong></td><td><strong><center>'+parseFloat(r.data[6].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>August</center></strong></td><td><strong><center>'+parseFloat(r.data[7].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>September</center></strong></td><td><strong><center>'+parseFloat(r.data[8].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>October</center></strong></td><td><strong><center>'+parseFloat(r.data[9].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>November</center></strong></td><td><strong><center>'+parseFloat(r.data[10].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>December</center></strong></td><td><strong><center>'+parseFloat(r.data[11].count)+'</center></strong></td>';
			           
				$('#listdtpo').html(listdtpo);


			  
			
  },'json'); 
	
	
}

function InvoiceVendorDashboard(){
	
		
		   $.post('AllDashboard',{action:'invoiceVendorDashboard'},function (r){ 
			//   alert('Requested Date should be between financial year : '+r.data[0].count);
				  var context = document.getElementById('vendorinvoice1').getContext('2d');
			  var barChart = new Chart(context, {
			  	  type: 'bar',
			  	  data: {
			  	  //labels: ["A. "+ freq [0] +"cr." , "B. "+ freq [1] +"cr.", "C. "+ freq [2] +"cr.", "countal. " +freq [3] +"cr."],
			  	      labels: ["January", "February", "March","April", "May", "June", "July","August", "September", "October","November", "December", ],
			  	  datasets: [{
			  		label: 'Invoices',
			  	    data: [r.data[0].count, r.data[1].count,r.data[2].count,r.data[3].count, r.data[4].count,
			  	           r.data[5].count,r.data[6].count, r.data[7].count,r.data[8].count,r.data[9].count, r.data[10].count,r.data[11].count,],
			  	    
			  	     // data: [freq [0], freq [1], freq [2], freq [3]],
			  	  borderColor: [
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)'
			  	            ],
			  	            backgroundColor: [
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)'
			  	            ],
			  	            
			  	          borderWidth: 1
			  	    }]
			  	    
			  	  },
			  	 
			        options: {
			            responsive: false,

            text: 'Purchase'
			
			        }
			  	  
			  	});
			  var context = document.getElementById('vendorinvoice').getContext('2d');
			  var barChart = new Chart(context, {
			  	  type: 'line',
			  	  data: {
			  	  
			  	      labels: ["January", "February", "March","April", "May", "June", "July","August", "September", "October","November", "December", ],
			  	  datasets: [{
			  	      label: 'Invoices',
			  	    data: [r.data[0].count, r.data[1].count,r.data[2].count,r.data[3].count, r.data[4].count,
			  	           r.data[5].count,r.data[6].count, r.data[7].count,r.data[8].count,r.data[9].count, r.data[10].count,r.data[11].count,],
			  	    
			  	     // data: [freq [0], freq [1], freq [2], freq [3]],
			  	  borderColor: [
			  	               'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)'
			  	            ],

			  	            backgroundColor: [
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(29, 233, 182, 1)',
			  	                'rgba(156, 39, 176, 1)',
			  	                'rgba(84, 110, 122, 1)'
			  	            ],
			  	          borderWidth: 1
			  	    }]
			  	    
			  	  },
			  	 
			        options: {
			            responsive: false,

            text: 'Purchase'
			           
			        }
			  	  
			  	});
		       	

 var listdtinv='<tr style="font-size:13px;border="1""><td style="width: 100px;font-size:13px;color: blue;"><strong><center>Month</center></strong></td><td style="color: blue;"><strong><center>Total Invoice</center></strong></td>'+
			  '<tr><td style="width: 100px;color: #1616d6;"><strong><center>January</center></strong></td><td><strong><center>'+parseFloat(r.data[0].count)+'</center></strong></td>'+
	           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>February</center></strong></td><td><strong><center>'+parseFloat(r.data[1].count)+'</center></strong></td>'+
	           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>March</center></strong></td><td><strong><center>'+parseFloat(r.data[2].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>April</center></strong></td><td><strong><center>'+parseFloat(r.data[3].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>May</center></strong></td><td><strong><center>'+parseFloat(r.data[4].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>June</center></strong></td><td><strong><center>'+parseFloat(r.data[5].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>July</center></strong></td><td><strong><center>'+parseFloat(r.data[6].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>August</center></strong></td><td><strong><center>'+parseFloat(r.data[7].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>September</center></strong></td><td><strong><center>'+parseFloat(r.data[8].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>October</center></strong></td><td><strong><center>'+parseFloat(r.data[9].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>November</center></strong></td><td><strong><center>'+parseFloat(r.data[10].count)+'</center></strong></td>'+
			           '<tr><td style="width: 100px;color: #1616d6;"><strong><center>December</center></strong></td><td><strong><center>'+parseFloat(r.data[11].count)+'</center></strong></td>';
			           
				$('#listdtinv').html(listdtinv);

			  
			
  },'json'); 
	
	
}

function assetStatus(){
		var mainAr = Array();
		var key1 = new Array();
		var value1 = new Array();
		var keyF=new Array();
		var valueF=new Array();
		
		var group=$('select[name="group"]').val();
		var subgrp=$('select[name="subgrp"]').val();
		var model=$('select[name="model"]').val();
		var currentLocation = window.location.href;
		var x='';
		if(group==''){
			 x ='All';
		 }
		
		var url=currentLocation.slice(0, -9)+"ReportView/Location_Asset_report1.jsp?id_grp="+x+"&id_sgrp="+subgrp+"";
	    
				  $.post("AllDashboard",{action:"devicestatus",group:group,subgrp:subgrp,model:model},function (r){
					  
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
						  
					 
						//  console.log("Main AR ="+mainAr);
				//	console.log(mainAr);
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
		    textDecoration: 'underline',
            color: '#000080',
            fontWeight: 'bold'
            
        },
	                                    text: 'Hardware status.'
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
	    												      		showInLegend: false,
	    												      		
	    												      		 point: {
	    										                            events: {
	    										                                    click: function() {
	    										                                    	
	    										                             window.open(url);
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
	
	

	function groupWise(callback){
		var mainAr = Array();
		var key1 = new Array();
		var value1 = new Array();
		var keyF=new Array();
		var valueF=new Array();
		console.log(key1[0]);
		console.log(value1[0]);
		var typ=$("#groupDataForModel option:selected").val();
		if(typ==undefined){
			typ="";
		}
		//var key2 = new Array();
		//var value2 = new Array();
		//var dataArrayFianl =Array();


			$(document).ready(function(){
				//console.log("home.jsp");
				$.ajax({
					
					method:"GET",
					url:"Group_Data",
					data: { id_grp: typ}
					}).done(function(data1){
							//console.log("data:"+data1);
							//var jsonData=JSON.stringify(data1);
							//console.log("json data:"+jsonData);
							$.each(data1, function(key,value) {               
				                  //console.log(key+"============================="+value);
				                 key1.push(key);
				                 value1.push(value);
				//                  key2.push(key);
				//                  value2.push(value);
				                  //mainAr.push(key,value);
				                  
							});
							for(j=0;j<key1.length;j++) { 
								   var temp = new Array(key1[j],value1[j]); 
								   mainAr[j] = temp;     
								}
							//mainAr.push(key1,value1);
							//console.log("Main AR ="+mainAr);
							chart(mainAr);
						
						
						  
							//alert(data);
						});
					
					
				
				//var datad=[];
				 
						
					/* for(var i=0;i<key.length;i++){
						
						alert(key[i]+value[i]);
						datad=[key[i],value[i]];  
					
					
					} */
					
					   // Build the chart
			         
			        	  function chart(mainAr){
			      			var currentLocation = window.location.href;
	
	
	var url='';
	
	if(currentLocation.slice(-1)=="#"){
		
		url=currentLocation.slice(0, -9)+"ReportView/Location_Asset_report1.jsp?id_grp=All&id_sgrp=";
	    
	}
	else{
		url=currentLocation.slice(0, -8)+"ReportView/Location_Asset_report1.jsp?id_grp=All&id_sgrp=";
			
	}
		        					$('#container2').highcharts({
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
		                                              text: null
		                                         },
		                                  tooltip: {
		                                	  
		              //pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
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
    										                                           click: function(event) {
	if(currentLocation.slice(-1)=="#"){
		
		url=currentLocation.slice(0, -9)+"ReportView/hardwareStatus.jsp?label=abc&id_grp="+this.name+"";
	    
	}
	else{
		url=currentLocation.slice(0, -8)+"ReportView/hardwareStatus.jsp?label=abc&id_grp="+this.name+"";
			
	}
	    										                               console.log(this.name);    	
    										                             window.open(url);
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
		        //.log("value of i"+i);
				//}//end for loop
		    };//end chart function
			   if(callback) callback(true);     	 
			});
			
		}
		
		
		
	function subGroup(callback){
		var mainAr = Array();
		var key1 = new Array();
		var value1 = new Array();
		var keyF=new Array();
		var valueF=new Array();
		//console.log(key1[0]);
		//console.log(value1[0]);
		//var key2 = new Array();
		//var value2 = new Array();
		//var dataArrayFianl =Array();
		var typ=$("#groupDataForModel option:selected").val();
		if(typ==undefined){
			typ="";
		}
		var group=$('select[name="group"]').val();
		var subgrp=$('select[name="subgrp"]').val();

			$(document).ready(function(){
				//console.log("home.jsp");
				$.ajax({
					
					method:"GET",
					url:"Dashboard",
					data: { id_grp: typ}
					}).done(function(data1){
							//console.log("data:"+data1);
							//var jsonData=JSON.stringify(data1);
							//console.log("json data:"+jsonData);
							$.each(data1, function(key,value) {               
				                 // console.log(key+"============================="+value);
				                 key1.push(key);
				                 value1.push(value);
				//                  key2.push(key);
				//                  value2.push(value);
				                  //mainAr.push(key,value);
				                  
							});
							for(j=0;j<key1.length;j++) { 
								   var temp = new Array(key1[j],value1[j]); 
								   mainAr[j] = temp;     
								}
							//mainAr.push(key1,value1);
							//console.log("Main AR ="+mainAr);
							chart(mainAr);
						
						
						  
							//alert(data);
						});
				//var datad=[];
			
	
	var currentLocation = window.location.href;
	
	
	var url='';
	
	
				function chart(mainAr){
					
					/* for(var i=0;i<key.length;i++){
						
						alert(key[i]+value[i]);
						datad=[key[i],value[i]];
					
					} */
					
					   // Build the chart
			         // $(document).ready(function () {

		        // Build the chart
		        $('#container3').highcharts({
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
		                text: null
		            },
		          tooltip: {
		              //pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		            },
		           /*  legend: 
	                               {
	                                   layout: 'Vertical',
										 align: 'left',
									  },*/
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
    										                                     click: function(event) {
	if(currentLocation.slice(-1)=="#"){
		
		url=currentLocation.slice(0, -9)+"ReportView/hardwareStatus.jsp?label=abc&id_sgrp="+this.name+"";
	    
	}
	else{
		url=currentLocation.slice(0, -8)+"ReportView/hardwareStatus.jsp?label=abc&id_sgrp="+this.name+"";
			
	}
	    										                               console.log(this.name);    	
    										                             window.open(url);
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
		    //});//end container
				}
				if(callback) callback(1);
			});
		}
		
		function AMCDash(callback){
		var mainAr = Array();
		var key1 = new Array();
		var value1 = new Array();
		var keyF=new Array();
		var valueF=new Array();
		var typ=$("#groupDataForModel option:selected").val();
		if(typ==undefined){
			typ="";
		}

			$(document).ready(function(){
				//console.log("home.jsp");
				$.ajax({
					
					method:"GET",
					url:"AMCData",
					data: { id_grp: typ}
					}).done(function(data1){
							//console.log("data:"+data1);
							//var jsonData=JSON.stringify(data1);
							//console.log("json data:"+jsonData);
							$.each(data1, function(key,value) {               
				                 // console.log(key+"============================="+value);
				                 key1.push(key);
				                 value1.push(value);
				//                  key2.push(key);
				//                  value2.push(value);
				                  //mainAr.push(key,value);
				                  
							});
							for(j=0;j<key1.length;j++) { 
								   var temp = new Array(key1[j],value1[j]); 
								   mainAr[j] = temp;     
								}
							//mainAr.push(key1,value1);
							//console.log("Main AR ="+mainAr);
							chart(mainAr);
						
						
						  
							//alert(data);
						});
				//var datad=[];
			
	
	var currentLocation = window.location.href;
	
	
	var url='';
	
	
				function chart(mainAr){
					
		        $('#container4').highcharts({
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
		                text: null
		            },
		          tooltip: {
		              //pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		            },
		           /*  legend: 
	                               {
	                                   layout: 'Vertical',
										 align: 'left',
									  },*/
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
    										                                     click: function(event) {
	if(currentLocation.slice(-1)=="#"){
		
		url=currentLocation.slice(0, -9)+"ReportView/hardwareStatus.jsp?label=abc&id_sgrp="+this.name+"";
	    
	}
	else{
		url=currentLocation.slice(0, -8)+"ReportView/hardwareStatus.jsp?label=abc&id_sgrp="+this.name+"";
			
	}
	    										                               console.log(this.name);    	
    										                             window.open(url);
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
		    //});//end container
				}
				if(callback) callback(1);
			});
		}
		
	function allDevice(){
		var mainAr = Array();
		var key1 = new Array();
		var value1 = new Array();
		var keyF=new Array();
		var valueF=new Array();
		var typ_asst=$('select[name="typ_asst"]').val();
		var group=$('select[name="group"]').val();
		var subgrp=$('select[name="subgrp"]').val();
		var model=$('select[name="model"]').val();
		
		var currentLocation = window.location.href;
		var x='';
		if(group==''){
			 x ='All';
		 }
		var url='';
		
		if(currentLocation.slice(-1)=="#"){
			
			url=currentLocation.slice(0, -9)+"ReportView/Location_Asset_report1.jsp?id_grp="+x+"&id_sgrp="+subgrp+"";
		    
		}
		else{
			url=currentLocation.slice(0, -8)+"ReportView/Location_Asset_report1.jsp?id_grp="+x+"&id_sgrp="+subgrp+"";
				
		}
		
		
				  $.post("AllDashboard",{action:"alldevicestatus",group:group,subgrp:subgrp,model:model,typ_asst:typ_asst},function (r){
					  console.log(r.data);
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
						  
					 
						//  console.log("Main AR ="+mainAr);
				//	console.log(mainAr);
					chart(mainAr);
					  
					  
					  }
					
				
			  },'json');
	   
				  function chart(mainAr){
					     
						$('#container1').highcharts({
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
	                                    text: 'Stock Allocation.'
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
	    												      		showInLegend: false,
	    												      		
	    												      		 point: {
	    										                            events: {
	    										                                    click: function() {
	    										                                    	
	    										                             window.open(url);
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
	function assetStatus1(){
		var fncl=$('#yr_month').val();
		var grp_name=($('#groupDataForModel option:selected').text()).substring(1);
		var typ=$("#groupDataForModel option:selected").val();
		if(typ==undefined){
			typ="";
		}
	if(fncl!=''){
	var x = $('#chartview').serialize();
	//
		
		    $.post("AllDashboard",{action:"devicestatus1",group:typ},function (r){
			//   alert('Requested Date should be between financial year : '+r.data[0].tot);
				  var context = document.getElementById('clients').getContext('2d');
			  var barChart = new Chart(context, {
				legend: 
                               {
                                   layout: 'vertical',
									 align: 'left',
								  },
				
			  	  type: 'pie',
                  
			  	  data: {
			  	  //labels: ["A. "+ freq [0] +"cr." , "B. "+ freq [1] +"cr.", "C. "+ freq [2] +"cr.", "Total. " +freq [3] +"cr."],
			  	      labels: ["Allocated to employee","Under service","Not Working","In Store","Temporary Laptop","Buy back","Physical Damage (Major)","Physical Damage (Minor)","Scrapped / disposed",  ],
			  	    


datasets: [{
			  		label: 'Total Counts',
			  	    data: [r.data[0].count, r.data[1].count,r.data[2].count,r.data[3].count, r.data[4].count,
			  	           r.data[5].count,r.data[6].count, r.data[7].count,r.data[8].count,],
			  	    
			  	     // data: [freq [0], freq [1], freq [2], freq [3]],
			  	  borderColor: [
			  	                'rgba(35, 203, 167, 1)',
			  	                'rgba(211, 84, 0, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(0,0,255,1)',
			  	                'rgba(191, 85, 236, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(207, 0, 15, 1)',
			  	                'rgba(255,192,203,1)',
			  	                'rgba(249, 105, 14, 1)',
			  	                
			  	            ],
			  	            backgroundColor: [
			  	                'rgba(0, 138, 0, 1)',
			  	                'rgba(211, 84, 0, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(0,0,255,1)',
			  	                'rgba(191, 85, 236, 1)',
			  	                'rgba(0, 0, 0, 1)',
			  	                'rgba(255, 0, 0, 1)',
			  	                'rgba(255,192,203,1)',
			  	                'rgba(249, 105, 14, 1)',
			  	                
			  	            ],
			  	            
			  	          borderWidth: 1
                          
			  	    }]
			  	    
			  	  },
			  	 
			        options: {
				
			            responsive: false,
			           
			        }
			  	  
			  	});
			  	var currentLocation = window.location.href;
		
		var x='';
		 
			 x ='All';
		  
		var url='';
		
		 
		 var canvas = document.getElementById("clients");
			   canvas.onclick = function(evt) {
      var activePoints = barChart.getElementsAtEvent(evt);
      if (activePoints[0]) {
        var chartData = activePoints[0]['_chart'].config.data;
        var idx = activePoints[0]['_index'];

        var label = chartData.labels[idx];
        var value = chartData.datasets[0].data[idx];
 if(currentLocation.slice(-1)=="#"){
		if(grp_name=="" || grp_name=="Select"){
				url=currentLocation.slice(0, -9)+"ReportView/hardwareStatus.jsp?label="+label+"";
			}
			else{
			url=currentLocation.slice(0, -9)+"ReportView/hardwareStatus.jsp?id_grp="+grp_name+"&label="+label+"";
		    }
		}
		else{
			if(grp_name=="" || grp_name=="Select"){
			url=currentLocation.slice(0, -8)+"ReportView/hardwareStatus.jsp?label="+label+"";
				}
				else{
					url=currentLocation.slice(0, -8)+"ReportView/hardwareStatus.jsp?id_grp="+grp_name+"&label="+label+"";
				}
				}
        
          window.open(url);
      }
    };
		
  },'json'); 
	}
	else{
		alert("Please Select Financial Year.");
	}
	
}
function allDevice1(){
		var fncl=$('#yr_month').val();
		var grp_name=($('#groupDataForModel option:selected').text()).substring(1);
		var typ=$("#groupDataForModel option:selected").val();
		if(typ==undefined){
			typ="";
		}
	if(fncl!=''){
	var x = $('#chartview').serialize();
	//
		
		    $.post("AllDashboard",{action:"alldevicestatus1",group:typ},function (r){
			//   alert('Requested Date should be between financial year : '+r.data[0].tot);
				  var context = document.getElementById('allDevice').getContext('2d');
			  var barChart = new Chart(context, {
				legend: 
                               {
                                   layout: 'vertical',
									 align: 'left',
								  },

			  	  type: 'pie',
                  
			  	  data: {
			  	  //labels: ["A. "+ freq [0] +"cr." , "B. "+ freq [1] +"cr.", "C. "+ freq [2] +"cr.", "Total. " +freq [3] +"cr."],
			  	     labels: ["Allocated to employee","In Store",  ],
			  	  datasets: [{
			  		label: 'Total Counts',
			  	    data: [r.data[0].count, r.data[1].count,],
			  	    
			  	     // data: [freq [0], freq [1], freq [2], freq [3]],
			  	  borderColor: [
			  	                'rgba(35, 203, 167, 1)',
			  	                'rgba(211, 84, 0, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(0,0,255,1)',
			  	                'rgba(191, 85, 236, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(207, 0, 15, 1)',
			  	                'rgba(255,192,203,1)',
			  	                'rgba(249, 105, 14, 1)',
			  	                
			  	            ],
			  	            backgroundColor: [
			  	                'rgba(0, 138, 0, 1)',
			  	                'rgba(211, 84, 0, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(0,0,255,1)',
			  	                'rgba(191, 85, 236, 1)',
			  	                'rgba(0, 0, 0, 1)',
			  	                'rgba(255, 0, 0, 1)',
			  	                'rgba(255,192,203,1)',
			  	                'rgba(249, 105, 14, 1)',
			  	                
			  	            ],
			  	            
			  	          borderWidth: 1
                          
			  	    }]
			  	    
			  	  },
			  	 
			        options: {
				
			            responsive: false,
			           
			        }
			  	  
			  	});
			  	var currentLocation = window.location.href;
		
		var x='';
		 
			 x ='All';
		  
		var url='';
		
		 
		 var canvas = document.getElementById("allDevice");
			   canvas.onclick = function(evt) {
      var activePoints = barChart.getElementsAtEvent(evt);
      if (activePoints[0]) {
        var chartData = activePoints[0]['_chart'].config.data;
        var idx = activePoints[0]['_index'];

        var label = chartData.labels[idx];
        var value = chartData.datasets[0].data[idx];
 if(currentLocation.slice(-1)=="#"){
			if(grp_name=="" || grp_name=="Select"){
				url=currentLocation.slice(0, -9)+"ReportView/hardwareStatus.jsp?label="+label+"";
			}
			else{
			url=currentLocation.slice(0, -9)+"ReportView/hardwareStatus.jsp?id_grp="+grp_name+"&label="+label+"";
		    }
		}
		else{
			if(grp_name=="" || grp_name=="Select"){
			url=currentLocation.slice(0, -8)+"ReportView/hardwareStatus.jsp?label="+label+"";
				}
				else{
					url=currentLocation.slice(0, -8)+"ReportView/hardwareStatus.jsp?id_grp="+grp_name+"&label="+label+"";
				}
		}    
        
          window.open(url);
      }
    };
		
  },'json'); 
	}
	else{
		alert("Please Select Financial Year.");
	}
	$("#div11").load(" #div11 > *");
	$("#div12").load(" #div12 > *");
}

function groupWise1(){
		var fncl=$('#yr_month').val();
	if(fncl!=''){
	var x = $('#chartview').serialize();
	//
		
		    $.post("AllDashboard",{action:"alldevicestatus"},function (r){
			//   alert('Requested Date should be between financial year : '+r.data[0].tot);
				  var context = document.getElementById('clients1').getContext('2d');
			  var barChart = new Chart(context, {
			  	  type: 'bar',
			  	  data: {
			  	  //labels: ["A. "+ freq [0] +"cr." , "B. "+ freq [1] +"cr.", "C. "+ freq [2] +"cr.", "Total. " +freq [3] +"cr."],
			  	      labels: ["Allocated to employee","In Store",  ],
			  	  datasets: [{
			  		label: 'Total Counts',
			  	    data: [r.data[0].count, r.data[1].count,],
			  	    
			  	     // data: [freq [0], freq [1], freq [2], freq [3]],
			  	  borderColor: [
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                
			  	            ],
			  	            backgroundColor: [
			  	                'rgba(216, 27, 96, 1)',
			  	                'rgba(3, 169, 244, 1)',
			  	                
			  	            ],
			  	            
			  	          borderWidth: 1
			  	    }]
			  	    
			  	  },
			  	 
			        options: {
			            responsive: false,
			           
			        }
			  	  
			  	});
			  
			
  },'json'); 
	}
	else{
		alert("Please Select Financial Year.");
	}
	
}

	/*function assetStatus1(){
		var fncl=$('#yr_month').val();
		var grp_name=($('#groupDataForModel option:selected').text()).substring(1);
		var typ=$("#groupDataForModel option:selected").val();
		if(typ==undefined){
			typ="";
		}
	if(fncl!=''){
	var x = $('#chartview').serialize();
	//
		
		    $.post("AllDashboard",{action:"devicestatus1",group:typ},function (r){
			//   alert('Requested Date should be between financial year : '+r.data[0].tot);
				  var context = document.getElementById('barChart').getContext('2d');
			  var barChart = new Chart(context, {
				legend: 
                               {
                                   layout: 'vertical',
									 align: 'left',
								  },
				
			  	  type: 'bar',
                  
			  	  data: {
			  	  //labels: ["A. "+ freq [0] +"cr." , "B. "+ freq [1] +"cr.", "C. "+ freq [2] +"cr.", "Total. " +freq [3] +"cr."],
			  	      labels: ["Allocated to employee","Under service","Not Working","In Store","Temporary Laptop","Buy back","Physical Damage (Major)","Physical Damage (Minor)","Scrapped / disposed",  ],
			  	    


datasets: [{
			  		label: 'Total Counts',
			  	    data: [r.data[0].count, r.data[1].count,r.data[2].count,r.data[3].count, r.data[4].count,
			  	           r.data[5].count,r.data[6].count, r.data[7].count,r.data[8].count,],
			  	    
			  	     // data: [freq [0], freq [1], freq [2], freq [3]],
			  	  borderColor: [
			  	                'rgba(35, 203, 167, 1)',
			  	                'rgba(211, 84, 0, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(0,0,255,1)',
			  	                'rgba(191, 85, 236, 1)',
			  	                'rgba(84, 110, 122, 1)',
			  	                'rgba(207, 0, 15, 1)',
			  	                'rgba(255,192,203,1)',
			  	                'rgba(249, 105, 14, 1)',
			  	                
			  	            ],
			  	            backgroundColor: [
			  	                'rgba(0, 138, 0, 1)',
			  	                'rgba(211, 84, 0, 1)',
			  	                'rgba(255, 152, 0, 1)',
			  	                'rgba(0,0,255,1)',
			  	                'rgba(191, 85, 236, 1)',
			  	                'rgba(0, 0, 0, 1)',
			  	                'rgba(255, 0, 0, 1)',
			  	                'rgba(255,192,203,1)',
			  	                'rgba(249, 105, 14, 1)',
			  	                
			  	            ],
			  	            
			  	          borderWidth: 1
                          
			  	    }]
			  	    
			  	  },
			  	 
			        options: {
				
			            responsive: false,
			           
			        }
			  	  
			  	});
			  	var currentLocation = window.location.href;
		
		var x='';
		 
			 x ='All';
		  
		var url='';
		
		 
		 var canvas = document.getElementById("clients");
			   canvas.onclick = function(evt) {
      var activePoints = barChart.getElementsAtEvent(evt);
      if (activePoints[0]) {
        var chartData = activePoints[0]['_chart'].config.data;
        var idx = activePoints[0]['_index'];

        var label = chartData.labels[idx];
        var value = chartData.datasets[0].data[idx];
 if(currentLocation.slice(-1)=="#"){
		if(grp_name=="" || grp_name=="Select"){
				url=currentLocation.slice(0, -9)+"ReportView/hardwareStatus.jsp?label="+label+"";
			}
			else{
			url=currentLocation.slice(0, -9)+"ReportView/hardwareStatus.jsp?id_grp="+grp_name+"&label="+label+"";
		    }
		}
		else{
			if(grp_name=="" || grp_name=="Select"){
			url=currentLocation.slice(0, -8)+"ReportView/hardwareStatus.jsp?label="+label+"";
				}
				else{
					url=currentLocation.slice(0, -8)+"ReportView/hardwareStatus.jsp?id_grp="+grp_name+"&label="+label+"";
				}
				}
        
          window.open(url);
      }
    };
		
  },'json'); 
	}
	else{
		alert("Please Select Financial Year.");
	}
	
}*/
function displayDamageDashboard(callback){
	$.post("A_Edit_From_Store",{action:"damagedash"},function (r){
		var list='<ul class="products-list product-list-in-card pl-2 pr-2">';
		
		if(r.data.length > 0)
			{
				console.log(r);
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				list= list+ '<li class="item">'+
										'<div class="product-img"><img src="dist/img/default-150x150.png" alt="Product Image"class="img-size-50"></div><div class="product-info">'+
											'<a href="#" class="product-title" <i class="fas fa-edit" onclick="GotoDamageEditPage('+params.id_wh+','+params.id_loc+')"></i>'+params.ds_pro+
											' <span class="badge badge-warning float-right">'+params.tt_un_prc+'</span></a>'+
											 '<span class="product-description">'+params.typ_asst+'&nbsp;&nbsp;'+params.nm_ven+'&nbsp;&nbsp;'+params.id_wh_dyn+'</span></div></li>';

									
		
		}
		$('#trycatch').html('</ul>'+list);
}
else
			{
			list +='<center>No record(s) is available..</center>';
			$('#trycatch').html('</ul>'+list);
			}
		return callback('1');
	},'json');



}
function GotoDamageEditPage( id , id_loc){
	//changeSubContent('Assets/Damage_asset.jsp','damageAsset');
	SessionCheck(function (ses){		
		if(ses)
			{	
					 
				
				
	changeSubContent('Assets/Damage_asset.jsp','damageAsset');
	setTimeout(function() {
								ControlEditFromStore('Edit','EditDetailsForEditFromStore','SearchFromForEditFromstore','DisplayEditFromStore',id,id_loc);
 
									 
								}, 1000);
	
	
}
});
 
}

function displayRecentAssetDashboard(){
	$.post("A_Edit_From_Store",{action:"RecentAddDash"},function (r){
		var list='<ul class="products-list product-list-in-card pl-2 pr-2">';
		
		if(r.data.length > 0)
			{
				console.log(r);
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				list= list+ '<li class="item">'+
										'<div class="product-img"><img src="dist/img/default-150x150.png" alt="Product Image"class="img-size-50"></div><div class="product-info">'+
											'<a href="javascript:void(0)" class="product-title" onclick="GotoAssetEditPage('+params.id_wh+','+params.id_loc+',\'+params.typ_asst+\')">'+params.nm_model+
											' <span class="badge badge-warning float-right">'+params.tt_un_prc+'</span></a>'+
											 '<span class="product-description">'+params.typ_asst+'&nbsp;&nbsp;'+params.ds_pro+'&nbsp;&nbsp;'+params.id_wh_dyn+'</span></div></li>';

									
		
		}
		$('#RecentAsset').html('</ul>'+list);
}
else
			{
			list +='<center>No record(s) is available.</center>';
			$('#RecentAsset').html('</ul>'+list);
			}
		
	},'json');



}
function GotoAssetEditPage( id , id_loc,type){
	//changeSubContent('Assets/Damage_asset.jsp','damageAsset');
	SessionCheck(function (ses){		
		if(ses)
			{	
					 
				
				
	changeSubContent('Assets/Edit_from_store.jsp','editFromStore');
	setTimeout(function() {
								ControlEditFromStore('Edit','EditDetailsForEditFromStore','SearchFromForEditFromstore','DisplayEditFromStore',id,id_loc,type);
 
									 
								}, 1000);
	
	
}
});
 
}

function pricechart(callback){
	var mainAr1 = Array();
	var mainAr2 = Array();
	var key1 = new Array();
	var value1 = new Array();
	var keyF=new Array();
	var valueF=new Array();
	
	 $.post("AllDashboard",{action:"poDashboard"},function (r){
				  
				  if(r.data1.length>0){
					  //console.log(r);
					  for(var i=0;i<r.data1.length;i++){
						  for (var key in r.data1[i])
					        {
							  key1.push(key);
				              value1.push(parseFloat(r.data1[i][key]));
							 //console.log(key+"============================="+r.data1[i][key]);
								
							  //var temp = new Array(key1[i],value1[i]); 
							   mainAr1[i] = +r.data1[i][key];  
					         console.log("main ar1 ki key value=======" +mainAr1[i]);

					        }
						    
					  }
				 
				//chart(mainAr1);
				  
				  
				  }
				   if(r.data2.length>0){
					  //console.log(r);
					  for(var i=0;i<r.data2.length;i++){
						  for (var key in r.data1[i])
					        {
							  key1.push(key);
				              value1.push(parseFloat(r.data2[i][key]));
							 //console.log(key+"============================="+r.data1[i][key]);
								
							  //var temp = new Array(key1[i],value1[i]); 
							   mainAr2[i] = r.data2[i][key];  
					         console.log("main ar2 ki key value=======" +mainAr2[i]);
					        }
						    
					  }
				 
				//chart(mainAr2);
				  
				  
				  }
				//chart(mainAr1,mainAr2);
			
		
		  
		  	 /* function chart(mainAr1,mainAr2){
				     
					$('#barchart').highcharts({
 						 chart: {
 								     plotBackgroundColor: null,
 								     plotBorderWidth: null,
						             plotShadow: false,
							         type: 'column'
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
            
                                    text: 'Purchase Order Status.'
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
                                           column: {
                                                 allowPointSelect: true,
                                                 cursor: 'pointer',
                                                  dataLabels: {
                                                                enabled: true,
            									 			      format: '<b>{point.name}</b>: {x} ',
                                                                allowDecimals: false,
                                                                style: {
                                             		                       color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                                                        }
          													},
    												      		showInLegend: false,
    												      		
    												      		 point: {
    										                            events: {
    										                                    click: function() {
    										                                    	
    										                             window.open(url);
    										                                        }
    										                                }

    										            }
  											   },
  											  
  									},
 						 series: [{
                       			name: 'Total Count',
                      			colorByPoint: true,
                                  data:mainAr1,mainAr2,
                                  url:'www.google.com'
                                        
									}]
				});
				

}*/
   
	
	const ctx = document.getElementById('barChart').getContext('2d');
const barChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August','September','October','November','December'],
        datasets: [{
            label: 'Previous Year Invoices',
            data: [mainAr2[9], mainAr2[10],mainAr2[11],mainAr2[0],mainAr2[1],mainAr2[2],mainAr2[3],mainAr2[4],mainAr2[5],mainAr2[6],mainAr2[7],mainAr2[8]],
            backgroundColor: [
                'rgba(128, 128, 128, 0.5)',
                'rgba(128, 128, 128, 0.5)',
                'rgba(128, 128, 128, 0.5)',
                'rgba(128, 128, 128, 0.5)',
                'rgba(128, 128, 128, 0.5)',
                'rgba(128, 128, 128, 0.5)',
                'rgba(128, 128, 128, 0.5)',
                'rgba(128, 128, 128, 0.5)',
                'rgba(128, 128, 128, 0.5)',
                'rgba(128, 128, 128, 0.5)',
                'rgba(128, 128, 128, 0.5)',
                'rgba(128, 128, 128, 0.5)'
                
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)'
            ],
            borderWidth: 1
        },{
            label: 'Invoice Created This Year',
            data: [mainAr1[9], mainAr1[10],mainAr1[11],mainAr1[0],mainAr1[1],mainAr1[2],mainAr1[3],mainAr1[4],mainAr1[5],mainAr1[6],mainAr1[7],mainAr1[8]] ,
            backgroundColor: [
                'rgba(153, 102, 255, 0.8)',
                'rgba(153, 102, 255, 0.8)',
               'rgba(153, 102, 255, 0.8)',
                'rgba(153, 102, 255, 0.8)',
                'rgba(153, 102, 255, 0.8)',
                'rgba(153, 102, 255, 0.8)',
                'rgba(153, 102, 255, 0.8)',
                'rgba(153, 102, 255, 0.8)',
                'rgba(153, 102, 255, 0.8)',
                'rgba(153, 102, 255, 0.8)',
                'rgba(153, 102, 255, 0.8)',
                'rgba(153, 102, 255, 0.8)'
            ],
            borderColor: [
                 'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)'
            ],
            borderWidth: 1
        }
        
        ]
        
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});
  },'json');
	return callback('1');
}