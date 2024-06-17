<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Category Report</title>
</head>
<!-- 
<script type="text/javascript">


$(function (){
	
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForStoreAssetReport','CapG',function (status){
		if(status)
		{
			
		}});
});

</script>
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


<form action="Dashboard/Group_Asset_Report.jsp" method="POST" target="_new">


	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:150px">Location IT Asset Details</p></td>
			</tr>
		
			<tr>
				<tr>
				<td><b>Select the Asset Group<font color="red"> * </font>:</b></td>
				<td>
					<select id="assetDivForStoreAssetReport" name="id_grp"  style="width:140" onChange="SubDropDownDataDisplayForReport(this,'subAssetDivForStoreAssetReport','M_Subasset_Div')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
					</tr>
		</table>
		
	
		
			<button style="float:center;" class="button" id="btnClick"  style="vertical-align:middle" ><span>Generate </span></button>
			</form> -->


<div id="container" style="min-width: 310px; height: 400px; max-width: 900px; margin: 0 auto"></div>

<!-- <button id="button" class="autocompare">Get selected points</button> -->
</body>
<script type="text/javascript">
var mainAr = Array();
var key1 = new Array();
var value1 = new Array();
var keyF=new Array();
var valueF=new Array();
console.log(key1[0]);
console.log(value1[0]);
//var key2 = new Array();
//var value2 = new Array();
//var dataArrayFianl =Array();


	$(document).ready(function(){
		console.log("home.jsp");
		$.ajax({
			
			method:"GET",
			url:"Group_Data",
			datatype:"json",
			success:function(data1){
				console.log("data:"+data1);
				//var jsonData=JSON.stringify(data1);
				//console.log("json data:"+jsonData);
				$.each(data1, function(key,value) {               
	                  console.log(key+"============================="+value);
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
				console.log("Main AR ="+mainAr);
				chart(mainAr);
			
			
			  
				//alert(data);
			}
			
			
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
                                              text: 'Category Wise Asset Details'
                                         },
                                  tooltip: {
                                	  
              //pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
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
	        	 
	});
</script>
	


</html>
