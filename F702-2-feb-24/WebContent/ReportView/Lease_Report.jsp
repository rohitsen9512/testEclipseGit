<!-- Installed_asset.jsp -->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Reports ></a><a href="#">MIS Report ></a><a href="#">AMC / Warranty</a>-->
</div>
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
<form action="ReportView/Lease_Reportview.jsp" method="POST" target="_new">

<div class="commonDiv" id="AMCWReport">

	
		<table class="commonTable" align="center" width="800px" height="100%" border="1">
			<tr>
				<td colspan="6" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">AMC / Warranty Report</p></td>
			</tr>
			
			
			<tr>
				<td><strong>Start Date</strong></td>
				<td><input type="text" name="startdate" class="form-control datepicker" readonly></td>
			</tr>
			<tr>
				<td><strong>End Date</strong></td>
				<td ><input type="text" name="enddate" class="form-control datepicker" readonly></td>
			</tr>
				
			</tr>
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 443px;float: center;">
				<button type="submit" style="float:center;"  class="btn btn-primary">Report</button>
			</p>
</div>
