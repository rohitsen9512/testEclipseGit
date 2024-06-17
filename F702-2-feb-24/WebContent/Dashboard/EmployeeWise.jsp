<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chart For IT Asset</title>
</head>
<body>


<div id="container" style="min-width: 310px; height: 600px; max-width: 800px; margin: 0 auto"></div>
<!-- <button id="button" class="autocompare">Get selected points</button> -->
</body>
<script type="text/javascript">

$(function (){
	
	DisplayDropDownData('M_Emp_user','departmentAsset',function (status){
		if(status)
		{
			
		}});
});

</script>
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
			url:"EmployeeWise",
			datatype:"json",
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
                text: 'Sub Group Wise IT Software Asset Details'
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
                    showInLegend: true
                }
            },
            series: [{
                name: 'Total Count',
               
                colorByPoint: true,
                data:mainAr
            }]
        });
    //});//end container
		}
	});
</script>
	


</html>
