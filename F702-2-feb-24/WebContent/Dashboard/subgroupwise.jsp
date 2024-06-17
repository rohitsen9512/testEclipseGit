<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Chart For IT Asset</title>
</head>
<style>
.a {
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
 
 <b style="color:green">Category</b>
		<select id="groupDataForModel"  name="group" style="width:140" onChange="SubDropDownDataDisplay(this,'subgroupDataForModel','M_Subasset_Div')">
						<option value="">Select</option>
	   </select>
	   <b style="color:green">Sub Category</b>
		<select id="subgroupDataForModel"  name="subgrp"  style="width:140" >
						<option value="">Select</option>
	   </select>
	  
	   					<button style="float:center;margin-left:20px" class="button" id="btnClick"  style="vertical-align:middle" ><span>Generate </span></button>
	   
 
 <div id="container"
		style="min-width: 1000px; height: 400px; max-width: 2000px; margin-top:20px "></div>
 
 </body>
<script type="text/javascript">

$('#btnClick').click(function(){
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
	var group=$('select[name="group"]').val();
	var subgrp=$('select[name="subgrp"]').val();

		$(document).ready(function(){
			console.log("home.jsp");
			$.ajax({
				
				method:"GET",
				url:"Dashboard",
				datatype:"json",
				data: { group: group,subgrp: subgrp} ,
				success:function(data1){
					console.log("data:"+data1);
					var jsonData=JSON.stringify(data1);
					//console.log("json data:"+jsonData);
					$.each(data1, function(key, value) {               
		                  console.log(key +"============================="+value);
		                  key1.push(key);
		                  value1.push(value);
		//                  key2.push(key);
		//                  value2.push(value);
		                  
					});
					
					for(j=0;j<key1.length;j++) { 
						   var temp = new Array(key1[j],value1[j]); 
						   mainAr[j] = temp;     
						}
					//mainAr.push(key1,value1);
					console.log("Main AR ="+mainAr);
					chart(mainAr);
				}
				
				
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
	                text: 'Sub Group Wise Asset Details'
	            },
	          tooltip: {
	              //pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	            },
	            legend: {
	                layout: 'vertical',
	               // width: '500px',
	                //backgroundColor: '#FFFFFF',
	                align: 'left',
	                //verticalAlign: 'top',
	               //floating: true,
	                
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
		});
});	
</script>
	


</html>
