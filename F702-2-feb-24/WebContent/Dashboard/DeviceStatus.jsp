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
 
 DisplayDropDownDataForGroup('M_Asset_Div','groupDataForModel','CapG',function (status){
		if(status)
		{
		 
		}});
 </script>
 <body>
<div class="dashbpardheader">
<div class="item1">
 <b style="color:green">Category</b>
		<select id="groupDataForModel"  name="group"  style="width:140" onChange="SubDropDownDataDisplay(this,'subgroupDataForModel','M_Subasset_Div')">
						<option value="">Select</option>
	   </select>
	   
 <b style="color:green">Sub Category</b>
		<select id="subgroupDataForModel"  name="subgrp"  style="width:140" onChange="DisplaySubDropDownData(this,'Model','M_Model')">
						<option value="">Select</option>
	   </select>
	   
 <b style="color:green">Model</b>
		<select id="Model"  name="model"  style="width:140" >
						<option value="">Select</option>
	   </select></div>
					<button style="float:center;margin-left:20px" class="button" id="btnClick"  style="vertical-align:middle" ><span>Generate </span></button>
	</div>
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
    												      		showInLegend: true,
    												      		
    												      		point: {
    									                            events: {
    									                                           click: function(event) {
    if(currentLocation.slice(-1)=="#"){

    url=currentLocation.slice(0, -9)+"ReportView/Location_Asset_report2.jsp?type=all&id_grp="+group+"&id_sgrp="+subgrp+"&id_model="+model+"&label="+this.name+"";

    }
    else{
    url=currentLocation.slice(0, -8)+"ReportView/Location_Asset_report2.jsp?type=all&id_grp="+group+"&id_sgrp="+subgrp+"&id_model="+model+"&label="+this.name+"";
    	
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

}
});	   

</script>
