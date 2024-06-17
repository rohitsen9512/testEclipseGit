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
 
 DisplayFinanceYear(function (status){
		if(status)
		{
			DropdownResultForFinanceYear('financialYear');
		 
		}});
 
 
 
 
 function DisplayFinanceYear(callback)
 {
 	
 $.post('M_Financial_Year',{action : 'DropDownResult'},function (r){
 		
 		var list= '<option value=""> Select</option>';
 		var t=false;	
 		if(r.data)
 			{
 			t=true;
 				for(var i = 0; i < r.data.length ; i++)
 				{
 				if(r.data[i].active_year == 1)
 					{
 					list = list + '<option value="'+r.data[i].id_fincance+'" selected="true"> '+r.data[i].std_finance+' - '+r.data[i].end_finance+'</option>';
 					}
 				else
 					{
 						list = list + '<option value="'+r.data[i].id_fincance+'"> '+r.data[i].std_finance+' - '+r.data[i].end_finance+'</option>';
 					}
 				
 				}
 			
 				$('#financialYearForSearch').html(list);
 			
 			}
 		else
 			{
 				bootbox.alert("Try again.");
 			}
 		callback(t);
 		
 	},'json');
 	
 	
 }
 
 </script>
 <body>
 <b> &nbsp;&nbsp;&nbsp;</b>
				<select name="type_repo" id="type_repo" >
						<option value="all">ALL PO</option>
						<option value="Direct">Direct PO</option>
						<option value="Cancel">Cancel PO</option>
						<option value="Open">Open PO</option>
						<option value="Short">Short Close PO</option>
					</select>
 <b>Search According To Financial Year &nbsp;&nbsp;&nbsp;</b>
				<select name="id_finance" id="financialYearForSearch" >
						<option value="">Select</option>
					</select>
					<button style="float:center;margin-left:20px" class="button" id="btnClick"  style="vertical-align:middle" ><span>Generate </span></button>
	
 <div id="container"
		style="min-width: 310px; height: 400px; max-width: 900px; margin-top:20px "></div>
 
 </body>
	

<script type="text/javascript">
 

$('#btnClick').click(function(){
	var mainAr = Array();
	var key1 = new Array();
	var value1 = new Array();
	var keyF=new Array();
	var valueF=new Array();
	
	var type_repo=$('select[name="type_repo"]').val();
	var heading_name="",jsppage='';
	
	if(type_repo=='all'){
		heading_name='All Purchase Order';
		jsppage='AllPO_Reportview.jsp';
	}
	if(type_repo=='Direct'){
		heading_name='Direct Purchase Order';
		jsppage='DirectPO_Reportview.jsp';
	}
	if(type_repo=='Cancel'){
		heading_name='Cancel Purchase Order';
		jsppage='Cancelled_PO_Report.jsp';
	}
	if(type_repo=='Open'){
		heading_name='Open Purchase Order';
		jsppage='OpenPO_ReportView.jsp';
	}
	if(type_repo=='Short'){
		heading_name='Short Purchase Order';
		jsppage='ShortClosePO_ReportView.jsp';
	}
	
	var id_finance=$('select[name="id_finance"]').val();
var	Datefinance=$('#financialYearForSearch').children(':selected').text();

var splittext =Datefinance.split('-');
	
	var currentLocation = window.location.href;
	 
	var url=currentLocation.slice(0, -9)+"ReportView/"+jsppage+"?startdate="+splittext[0]+"&enddate="+splittext[1]+"";
    
			  $.post("AllDashboard",{action:"poDashboard",id_finance:id_finance,type_repo:type_repo},function (r){
				  
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
                                    text: heading_name
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
});	   

</script>
