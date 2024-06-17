<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
.tdcenter {
	align-items: center;
	justify-content: center;
	text-align: center;
}

a {
	color: green;
}

.button {
	display: inline-block;
	padding: 6px 12px;
	font-size: 20px;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
	outline: none;
	color: #fff;
	background-color: #4CAF50;
	border: none;
	border-radius: 11px;
	box-shadow: 0 9px #999;
}

.button:hover {
	background-color: #3e8e41
}

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
			 DisplayDropDownData("M_Vendor","venDataForDPO",function (status){
			      if(status)
		            	{
		            	}});
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
	<div>
	<br><br>
		<div style="margin-top: 10px;display: flex;">
			<!-- <b> &nbsp;&nbsp;&nbsp;</b> <select name="type_repo" id="type_repo"
				style="width: 120px;">
				<option value="all">ALL PO</option>
				<option value="Direct">Direct PO</option>
				<option value="Cancel">Cancel PO</option>
				<option value="Open">Open PO</option>
				<option value="Short">Short Close PO</option> -->
			<!-- </select> --> <b> Month &nbsp;&nbsp;&nbsp;</b> <select style="width:140px;" id="month" name="month" class="form-control">
    <option value="1">January</option>
    <option value="2">February</option>
    <option value="3">March</option>
    <option value="4">April</option>
    <option value="5">May</option>
    <option value="6">June</option>
    <option value="7">July</option>
    <option value="8">August</option>
    <option value="9">September</option>
    <option value="10">October</option>
    <option value="11">November</option>
    <option value="12">December</option>
</select> <b>Year &nbsp;&nbsp;&nbsp;</b> <select style="width:140px;" id="year" name="year" class="form-control">
    <option value="2015">2015</option>
    <option value="2016">2016</option>
    <option value="2017">2017</option>
    <option value="2018">2018</option>
    <option value="2019">2019</option>
    <option value="2020">2020</option>
    <option value="2021">2021</option>
    <option value="2022">2022</option>
    <option value="2023">2023</option>
    <option value="2024">2024</option>
    <option value="2025">2025</option>
    <option value="2026">2026</option>
</select>
			<button style="float: center; margin-left: 20px" class="button"
				id="btnClick" style="vertical-align:middle">
				<span>Generate </span>
			</button>
			 <select style="display:none;" name="chartview" id="" onchange="hideshowchart(this)"
				style="z-index: 1000; bottom: 0; position: fixed; width: 100px; float: right; margin: 10px 20px; background: #8085e9; color: white;">
				<option value="pie">Pie Chart</option>
				<option value="bar">Bar Chart</option>
			</select> 

		</div>

		<div style="display: flex;">
			<div style="margin-top: 30px; margin-left: 30px;">
				<table class="table table-bordered "
					style="max-width: 350px; box-shadow: 0 4px 8px 0 rgb(172 7 7/ 60%), 0 6px 20px 0 rgb(128, 133, 233);"
					id="ppodetails">

				</table>

			</div>
			<div id="container"
				style="flex-grow: 1; flex-shrink: 1; max-width: 50%; margin-top: 20px">


			</div>
			<div
				style="flex-grow: 1; flex-shrink: 1; margin-top: 20px; display: none"
				id="barchart">
				<canvas id="myChart"></canvas>


			</div>

		</div>
	</div>

	<div></div>
</body>


<script type="text/javascript">
	function hideshowchart(id) {
		var chrt = id.value;
		if (chrt == 'pie') {
			$('#container').show();
			$('#barchart').hide();
		} else {

			$('#container').hide();
			$('#barchart').show();
		}

	}

	$('#btnClick')
			.click(
					function() {
						var mainAr = Array();
						var key1 = new Array();
						var value1 = new Array();
						var listvalue = new Array();
						var keyF = new Array();
						var valueF = new Array();
						var valuepo = new Array();
						var months = new Array();

						var type_repo = $('select[name="type_repo"]').val();
						var month = $('select[name="month"]').val();
						var year = $('select[name="year"]').val();
						var heading_name = "", jsppage = '';

						if (type_repo == 'all') {
							heading_name = 'All Purchase Order';
							jsppage = 'AllPO_Reportview.jsp';
						}
						if (type_repo == 'Direct') {
							heading_name = 'Direct Purchase Order';
							jsppage = 'DirectPO_Reportview.jsp';
						}
						if (type_repo == 'Cancel') {
							heading_name = 'Cancel Purchase Order';
							jsppage = 'Cancelled_PO_Report.jsp';
						}
						if (type_repo == 'Open') {
							heading_name = 'Open Purchase Order';
							jsppage = 'OpenPO_ReportView.jsp';
						}
						if (type_repo == 'Short') {
							heading_name = 'Short Purchase Order';
							jsppage = 'ShortClosePO_ReportView.jsp';
						}

						var id_finance = $('select[name="id_finance"]').val();
						var id_vendor = $('select[name="id_vendor"]').val();
						var Datefinance = $('#financialYearForSearch')
								.children(':selected').text();

						var splittext = Datefinance.split('-');

						var currentLocation = window.location.href;

						var url = currentLocation.slice(0, -9) + "ReportView/"
								+ jsppage + "?startdate=" + splittext[0]
								+ "&enddate=" + splittext[1] + "";

						$
								.post(
										"AllDashboard",
										{
											action : "AMCDashboard",
											id_finance : id_finance,
											type_repo : type_repo,
											id : id_vendor,
											month : month,
											year : year
										},
										function(r) {

											if (r.data1.length > 0) {

												for (var i = 0; i < r.data1.length; i++) {

													for ( var key in r.data1[i]) {
														key1.push(key);
														value1
																.push(parseFloat(r.data1[i][key]));

														listvalue
																.push(parseFloat(r.data1[i][key])
																		+ ';'
																		+ key);
														//  console.log(key+"============================="+r.data1[i][key]);

														var temp = new Array(
																key1[i],
																value1[i]);
														mainAr[i] = temp;

													}

												}
												var list = '';
												list = list
														+ ' <tr align="center" style="background-color:#703db5 ; justify-content: center; align-items:center;text-align:center;flex-direction:column;color: white;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">'
														+ '<td class="tdcenter">Date</td>'
														
														+ '<td class="tdcenter">Total Items Going to Expire</td>'
														+ '</tr>';
												for (var k = 0; k < listvalue.length; k++) {

													console.log(listvalue[k]);
													var countdetails = listvalue[k]
															.split(';');
													var monthdetails = countdetails[1]
															.split('(');

													valuepo
															.push(monthdetails[1]
																	.slice(0,
																			-1));
													months
															.push(monthdetails[0]);
													list = list
															+ '<tr align="center"><td class="tdcenter" style=" background-color:#36A2EB; justify-content: center;display:flex;align-items:center;text-align:center;flex-direction:column;color: white;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);"> '
															+ monthdetails[0]
															+ '</td>'
															
															+ '<td class="tdcenter" style="background-color:#FF6384; justify-content: center; align-items:center;text-align:center;flex-direction:column;color: white;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">'
															+ countdetails[0]
															+ '</td>' +

															'</tr>';

												}
												$('#ppodetails').html(list);
												var ctx = document
														.getElementById(
																"myChart")
														.getContext("2d");

												console.log(months);
												var myBarChart = new Chart(
														ctx,
														{
															type : 'bar',
															data : {
																labels : months,
																datasets : [ {
																	label : "Total Budget",
																 
																		 backgroundColor: [
															  	                '#6a5acd',
															  	                '#ff6347',
															  	                '#9acd32',
															  	                '#000080',
															  	                '#7b68ee',
															  	                '#32cd32',
															  	                '#2f4f4f',
															  	                '#8b008b',
															  	                '#6495ed',
															  	                '#a9a9a9',
															  	                '#ff8c00',
															  	                '#daa520 ',
															  	                
															  	            ],
																	data : valuepo
																} ]
															},
															options : {
																barValueSpacing : 20,
																scales : {
																	yAxes : [ {
																		ticks : {
																			min : 0,
																		}
																	} ]
																},

																onClick : function(
																		e) {

																	var activePointLabel = this
																			.getElementsAtEvent(e)[0]._model.label;
																	getbudgetdata(activePointLabel);
																}
															}
														});

												chart(mainAr);

											}

										}, 'json');

						function chart(mainAr) {

							$('#container')
									.highcharts(
											{
												chart : {
													plotBackgroundColor : null,
													plotBorderWidth : null,
													plotShadow : false,
													type : 'pie'
												},
												credits : {
													enabled : false
												},
												exporting : {
													enabled : false
												},

												title : {
													style : {
														color : '#000080',
														fontWeight : 'bold'
													},
													text : heading_name
												},
												tooltip : {
													enabled : false,
													pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
												},
												legend : {
													layout : 'vertical',
													align : 'left',
												},
												plotOptions : {
													pie : {
														allowPointSelect : true,
														cursor : 'pointer',
														dataLabels : {
															enabled : true,
															format : '<b>{point.name}</b>: {y} ',
															allowDecimals : false,
															style : {
																color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
																		|| 'black'
															}
														},
														showInLegend : false,

														point: {
								                            events: {
								                                           click: function(event) {
if(currentLocation.slice(-1)=="#"){

url=currentLocation.slice(0, -9)+"ReportView/AMC_Warranty_Report3.jsp?end="+this.name.slice(0, 10)+"";

}
else{
url=currentLocation.slice(0, -8)+"ReportView/AMC_Warranty_Report3.jsp?end="+this.name.slice(0, 10)+"";

}
									                               console.log(this.name);    	
								                             window.open(url);
								                                        }
								                                }

								            }
													},

												},
												series : [ {
													name : 'Total Count',
													colorByPoint : true,
													data : mainAr,
													url : 'www.google.com'

												} ]
											});

						}

					});
</script>
 
 