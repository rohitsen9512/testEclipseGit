<!-- Installed_asset.jsp -->

<script type="text/javascript">
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

    
/* $(function (){
	
	/* $('#datepicker11').datepicker({
		yearRange: '1985:2025',
		     changeMonth: true,
		
		     changeYear: true,
		
		     dateFormat: 'yy-mm',
		
		     onClose: function() {
		     var iMonth = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
		     var iYear = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
		     $(this).datepicker('setDate', new Date(iYear, iMonth, 1));
		
		     },
		
		     beforeShow: function() {
		     if ((selDate = $(this).val()).length > 0)
		    {
		         iYear = selDate.substring(selDate.length - 4, selDate.length);
		       iMonth = jQuery.inArray(selDate.substring(0, selDate.length - 5),
		                 $(this).datepicker('option', 'monthNames'));
		        $(this).datepicker('option', 'defaultDate', new Date(iYear, iMonth, 1));
		        $(this).datepicker('setDate', new Date(iYear, iMonth, 1));
		    }
		  }
		     
		 });
	
	$("#datepicker11").focus(function () {
		$(".ui-datepicker-calendar").hide();
		$("#ui-datepicker-div").position({
		my: "center top",
		at: "center bottom",
		of: $(this)
		});
		}); */
	
	


</script>
<!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!--  Report-->
						<!-- <button type="button" name="create btn"
							class="btn btn-primary city"
							onclick="ControlDiv('Create','displayLocation','createLocation','submitLocation','M_Loc')">Create
							Location</button> -->
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Reports</a></li>
						<li class="breadcrumb-item">AMC Warranty</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
	
	<div class="card-body" id="AMCWReport">
<form action="ReportView/AMC_Warranty_Report.jsp" method="POST" target="_new">

	<div class="card-small">
	<div class="card-header new">
					<h3 class="card-title1">AMC / Warranty Report</h3>
				</div>
	<table class="table table-bordered ">


	
		<!-- <table class="commonTable" align="center" width="800px" height="100%" border="1">
			<tr>
				<td colspan="6" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">AMC / Warranty Report</p></td>
			</tr> -->
			<tr>
			<td><b>Select AMC/Warranty<font color="red">*</font></b></td>
				<td>
					<select  name="warr_amc" class="form-control select" required data-valid="mand"  style="width:140">
					<option value = "A" >AMC</option>
                    <option value = "W" >Warranty</option>
						
					</select>
				</td>
			
			<tr>
				<td><strong>Start Date</strong></td>
				<td><input type="text" name="startdate" class="form-control datepicker" readonly></td>
			</tr>
			<tr>
				<td><strong>End Date</strong></td>
				<td ><input type="text" name="enddate" class="form-control datepicker" readonly></td>
			</tr>
				
			
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 443px;float: center;">
				<button type="submit" style="float:center;"  class="btn btn-primary">Report</button>
			</p>
</div>

</form>
</div>

