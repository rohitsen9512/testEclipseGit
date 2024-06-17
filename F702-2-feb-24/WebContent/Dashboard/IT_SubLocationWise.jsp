<!-- Installed_asset.jsp -->

<script src="https://code.highcharts.com"></script>
<script type="text/javascript">

$(function (){
	
	DropDownDataDisplayForReport('M_Loc','assetDivForLocAsset');
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
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:150px">Location IT Asset Details</p></td>
			</tr>
		
			<tr>-->
			<b style="color:green">Asset Type</b>
		<select class="form-control" id="typeDivForLocAsset" name="typ_asst" style="width:140px; display:inline;" data-valid="mand">
		<option value="All">ALL</option>
						<option value="IT">IT</option>
						<option value="soft">Software</option>
						<option value="nonit">NON IT</option>
						<option value="accessories">Accessories</option>
						
					</select>
				<td> <b style="color:green">Select Location</b></td>
				<td>
					<select id="assetDivForLocAsset" class="form-control" name="id_loc"  style="width:140px; display:inline;" required>
						<option value="">Select</option>
						
					</select>
		<!-- 			</tr>
		</table> -->
		
	
		
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
						var ddlFruits = $("#assetDivForLocAsset");
					 if (ddlFruits.val() == "") {
			                //If the "Please Select" option is selected display error.
			                alert("Please select an option!");
			                return false;
			            }
					 var abc=$("#assetDivForLocAsset option:selected").val();
					 var typ=$("#typeDivForLocAsset option:selected").val();
					 console.log(abc+"btnClick");
					 myChart(abc,typ);
				});
				
			});
			function myChart(submit,typ_asst){
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
						data: { id_loc: submit,typ_asst :typ_asst}
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
						var currentLocation = window.location.href;
						
						
						var url='';
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
			                                              text: 'Location Wise IT Asset Details'
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
											
											url=currentLocation.slice(0, -9)+"ReportView/LocWiseAsset.jsp?label=abc&id_sgrp="+this.name+"&id_loc="+submit+"&type="+typ_asst+"";
										    
										}
										else{
											url=currentLocation.slice(0, -8)+"ReportView/LocWiseAsset.jsp?label=abc&id_sgrp="+this.name+"&id_loc="+submit+"&type="+typ_asst+"";
												
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
			                                            data:mainAr
			                                                  
				   									}]
			        				});
			        //.log("value of i"+i);
					//}//end for loop
			    };//end chart function
				        	 
				};//mychart close

			//}

</script>