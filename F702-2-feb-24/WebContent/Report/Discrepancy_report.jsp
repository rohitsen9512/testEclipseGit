<!--Discrepancy_report.jsp-->
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>

<script type="text/javascript">
function ControleDiscrepancy()
{
	var t=true;
	$('.form-control').removeClass('error');
	$('.form-control').each(function (){
		
		if($(this).val() == '')
			{
			alert('Mandatory Field.');
			$(this).focus();
			$(this).addClass('error');
			t=false;
			return t;
			}
	});

	return t;
}
$(document).ready(function() {
    $('.select2search').select2();
});

$(function() {
	
	
	
	$('#datepicker111').datepicker({
		
	     
	     changeYear: true,
	
	     dateFormat: 'yy',
	
	     onClose: function() {
	     var iYear = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
	     $(this).datepicker('setDate', new Date(iYear, 1));
	
	     },
	
	     beforeShow: function() {
	     if ((selDate = $(this).val()).length > 0)
	    {
	         iYear = selDate.substring(selDate.length - 4, selDate.length);
	        $(this).datepicker('option', 'defaultDate', new Date(iYear, 1));
	        $(this).datepicker('setDate', new Date(iYear, 1));
	    }
	  }
	     
	 });

$("#datepicker111").focus(function () {
	$(".ui-datepicker-calendar").hide();
	$("#ui-datepicker-div").position({
	my: "center top",
	at: "center bottom",
	of: $(this)
	});
	});
	
	
DropDownDataDisplay("M_Loc","locationForDiscrepancyReport",function (status){
	if(status)
		{
		
		}});
	/* DisplayDropDownData("M_Loc","locationForDiscrepancyReport",function (status){
		if(status)
		{
			DisplayDropDownData("M_Subloc","subLocationForDiscrepancyReport",function (status){
				if(status)
				{
					DisplayDropDownData("M_Floor","floorForDiscrepancyReport",function (status){
						if(status)
						{
							
						}
							});
				}
					});
		}
			}); */
	
	
	
});


</script>

<div class="card-body" id="displayDiscrepancyReport">

<form action="ReportView/Discrepancy_ReportView.jsp" method="POST" target="_new" name="DiscrepancyReportForm" id="DiscrepancyReportForm">	
		
			
			
			<div class="card-small">
	<div class="card-header">
					<h3 class="card-title1">Discrepancy Report</h3>
				</div>
	<table class="table table-bordered ">
			<!-- <tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:191px">Discrepancy Report</p></td>
			</tr> -->
			<!-- <tr>
			<td ><b>Division Name<font color="red"> * </font>:</b></td>
				<td >
					<select id="divDataForDescrepancy" name="id_div" class="form-control readbaledata" data-valid="mand"  style="width:140" onChange="DisplaySubDropDownData(this,'locationForDiscrepancyReport','M_Loc')">
						<option value="">Select</option>
						
					</select>
				</td>
				</tr>-->
			<tr> 
				<td><b>Location<font color="red">*</font></b></td>
				<td>
					<select id="locationForDiscrepancyReport" name="id_loc" class="form-control select select2search" data-valid="mand" required style="width:140" onChange="DisplaySubDropDownData(this,'subLocationForDiscrepancyReport','M_Subloc')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Sub-Location<font color="red">*</font></b></td>
				<td>
					<select id="subLocationForDiscrepancyReport" name="id_subl" class="form-control select select2search" data-valid="mand" required style="width:140" onChange="SubDropDownDataDisplay(this,'buildingForDiscrepancyReport','M_Building')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Building<font color="red">*</font></b></td>
				<td>
					<select id="buildingForDiscrepancyReport" name="id_building" class="form-control select select2search" data-valid="mand" required style="width:140" onChange="DisplaySubDropDownDataForReport(this,'floorForDiscrepancyReport','M_Floor')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Floor<font color="red">*</font></b></td>
				<td>
					<select id="floorForDiscrepancyReport" name="id_flr" class="form-control select select2search" data-valid="mand" required style="width:140">
						<option value="">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Year<font color="red">*</font></b></td>
				<td><input name="yr" type="text" id="datepicker111" style="width:350px; display:inline" class="form-control" required data-valid="mand">
					<select name="period" class="form-control select" data-valid="mand" style="width:350px; display:inline" >
						<option value="FH">First Half</option>
						<option value="SH">Second Half</option>
					</select>
				</td>
			</tr>
	</table>
	
	<br>
	
				<p style="width:180px;margin-left: 520px;float:center;">
					<button type="submit" style="float:center;"  class="btn btn-primary" onclick="">Report</button>
				</p>
				</div>
	</form>
</div>

