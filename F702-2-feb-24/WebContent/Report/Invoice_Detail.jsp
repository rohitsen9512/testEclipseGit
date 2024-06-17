<script type="text/javascript">

$(function (){
	
	//DropdownResultForFinanceYear('financialYearDataForinvoiceReport');
});

function CheckDate(datess)
{
var dates=datess.value;
	
	if(datess.value == undefined)
		{
		dates = datess;
		}
	var fin_years=$('select[name="fin_years"]').val();
	/* var startdate=$('input[name="startdate"]').val();
	alert(startdate);
	var enddate=$('input[name="enddate"]').val(); */
	if(fin_years !='' && startdate !='' && enddate !='')
		{
	$.post('M_Financial_Year',{action : 'checkInvoiceDate',fin_years : fin_years,dates : dates},function (r){
		
		if(r.data ==0)
			{
				alert('You have already \'Budget\' of this financial year. Please choose another one.');
			}
	
	},'json');
		}
	
}
$(function(){
var currentDate = new Date();
	
	$('.datepicker').each(function () {
        if ($(this).hasClass('hasDatepicker')) {
            $(this).removeClass('hasDatepicker');
        } 
         $(this).datepicker({
        	 yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	    });
    });
	
	$('input[name="enddate"]').datepicker("setDate", currentDate);
	currentDate.setMonth(currentDate.getMonth() - 1);
	$('input[name="startdate"]').datepicker("setDate", currentDate);
	
});

</script>


<!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
					<!-- 	Report -->
						<!-- <button type="button" name="create btn"
							class="btn btn-primary city"
							onclick="ControlDiv('Create','displayLocation','createLocation','submitLocation','M_Loc')">Create
							Location</button> -->
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Reports</a></li>
						<li class="breadcrumb-item">Invoice Master</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
		
<div class="commonDiv" id="displayGRNClosedReport">

				</div>
<form action="ReportView/Invoice_Detail_Report.jsp" method="post" target="_new">
<div class="card-body">
	<div class="card-small">
	<div class="card-header new">
	<h3 class="card-title1">Select Invoice Date</h3>
	</div>	
		<table class="table table-bordered ">
			<!-- <tr>
				<td colspan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Select Invoice Date</p></td>
			</tr> -->
			<!-- <tr>
				<td><strong>Select Financial Year</strong></td>
				<td >
					<select name="fin_years" class="form-control" id="financialYearDataForinvoiceReport" required>
						<option value="select">Select</option>
					</select>
				</td>
			</tr> -->
			<tr>
				<td><strong>Start Date</strong></td>
				<td><input type="text" name="startdate" class="form-control datepicker" readonly></td>
			</tr>
			<tr>
				<td><strong>End Date</strong></td>
				<td ><input type="text" name="enddate" class="form-control datepicker" readonly></td>
			</tr>
		
			<tr>
				<td colspan="4" align="center">
					<p style="margin-left: 250px;">
						<button type="submit"   class="btn btn-primary" >Preview</button>
					</p>
				</td>
		  </tr>
	</table>
	</div>
	</div>
</form>
</div>

