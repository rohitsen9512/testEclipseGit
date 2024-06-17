<!-- Installed_asset.jsp -->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">Dashboard > </a><a href="#">Sub Location Wise Non-IT Asset Details </a> -->
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


<form id="formSelect">
	
		<!-- <table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:100px">Sub Location Wise Non-IT Asset Details</p></td>
			</tr> -->
		
			<tr>
				<td><b style="color:green"> Select Sub Location </b></td>
				<td>
					<select id="assetDivForsubLocAsset"  name="id_sloc"  style="width:140" onChange="SubDropDownDataDisplay(this,'buildingAssetReport','M_Building')">
						<option value="">Select</option>
						
					</select>
					</tr>
					<tr>
				<td><b style="color:green">Select Building </b></td>
				<td>
					<select id="buildingAssetReport" name="id_building"  style="width:140" >
			
						
					</select>
				</td>
		<!-- 	</tr>
		</table>
		 -->
		<button style="float:center;" class="button" id="btnClick"  style="vertical-align:middle" ><span>Generate </span></button>
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
					var ddlFruits = $("#assetDivForsubLocAsset");
					 if (ddlFruits.val() == "") {
			                //If the "Please Select" option is selected display error.
			                alert("Please select an option!");
			                return false;
			            }
					 var ddlFruits = $("#buildingAssetReport");
					 if (ddlFruits.val() == "") {
			                //If the "Please Select" option is selected display error.
			                alert("Please select an option!");
			                return false;
			            }
					 var abc=$("#assetDivForsubLocAsset option:selected").val();
					 var building=$("#buildingAssetReport option:selected").val();
					 console.log(abc+"btnClick");
					 //console.log(frl+"btnClick");
					 myChart(abc,building);
				});
				
			});
			function myChart(submit,building){
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
						url:"NIT_FloorWise",
						data: {id_sloc: submit , id_building : building}
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
			                                              text: 'Sub Location Wise Non-IT Asset Details'
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