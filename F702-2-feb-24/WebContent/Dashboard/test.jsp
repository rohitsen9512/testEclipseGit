<!-- Installed_asset.jsp -->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">MIS Data > </a><a href="#">Asset Report > </a><a href="#">Installed Asset</a> -->
</div>
<script src="https://code.highcharts.com"></script>
<script type="text/javascript">

$(function (){
	
	DisplayDropDownData('M_Subloc','assetDivForsubLocAsset',function (status){
		if(status)
		{
			
		}});
});

</script>


<form id="formSelect">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Installed Report - Assets</p></td>
			</tr>
		<!-- 	<tr>
				<td><b>Select Location :</b></td>
				<td>
					<select id="assetDivForLocAssetReport" name="id_loc"  style="width:140" onChange="DisplaySubDropDownDataForReport(this,'assetDivForsubLocAssetReport','M_Subloc')">
			
						
					</select>
				</td>
			</tr> -->
			<tr>
				<td><b>Select Sub Location </b></td>
				<td>
					<select id="assetDivForsubLocAsset"  name="id_sloc"  style="width:140" required>
						<option value="">Select</option>
						
					</select>
					</tr>
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 500px;float: center;">
				<button style="float:center;"  class="btn btn-primary" id="btnClick" >Report</button>
			</p>
			</form>
			<div id="container" style="min-width: 310px; height: 400px; max-width: 900px; margin: 0 auto"></div>
			<script>
			$(document).ready(function(){
				//var submit="";
				$("#formSelect").submit(function(e){
					e.preventDefault();
				    return false;
				});
				/*  $("#assetDivForsubLocAsset").on('change',function (event) {
					 console.log("sub"+$(this).val());
			            var submit=$(this).val();
			           // myChart(submit);
			           
			        }); */
				$('#btnClick').on('click',function(){
					//var abc="";
					//$('form#formSelect').preventDefault();
					 var abc=$("#assetDivForsubLocAsset option:selected").val();
					 console.log(abc+"btnClick");
					 myChart(abc);
				});
				
			});
			function myChart(submit){
				var mainAr = Array();
				var key1 = new Array();
				var value1 = new Array();
				var keyF=new Array();
				var valueF=new Array();
				console.log(key1[0]);
				console.log(value1[0]);
			   // alert("ina maim");
			   // $(document).ready(function(){
					console.log("home.jsp");
					$.ajax({
						//alert($("#assetDivForsubLocAsset").val());
						method:"GET",
						url:"Sub_Location_Data1",
						data: { id_sloc: submit}
					}).done(function(data1){
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
						});
						
						
					//});
					//var datad=[];
					 
							
						/* for(var i=0;i<key.length;i++){
							
							alert(key[i]+value[i]);
							datad=[key[i],value[i]];  
						
						
						} */
						
						   // Build the chart
				         
				        	  function chart(mainAr){
				      			//for(var i=0;i<key1.length;i++)
				      				//{
				      				      				
				      					/* console.log("["+key1[i]+","+value1[i]+"],");
				      					var chart1 = $('#container').highcharts();
				      			        chart1.series[0].setData(data1); */
			        // Build the chart
			      //  alert("in side chart function");
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
			                                              text: 'Sub_Location Wise Asset Details'
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
			              												      		showInLegend: true
			            											   }
			            									},
			           						 series: [{
			                                 			name: 'Total Count',
			                                			colorByPoint: true,
			                                            data:mainAr
			                                                  
				   									}]
			        				});
			        //.log("value of i"+i);
					//}//end for loop
			    };//end chart function
				        	 
				};//mychart close

			//}

</script>