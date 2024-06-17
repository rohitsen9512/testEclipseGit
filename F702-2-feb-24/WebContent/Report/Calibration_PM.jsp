<!-- Installed_asset.jsp -->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Reports ></a><a href="#">Search ></a><a href="#">Stores Asset</a>-->
</div>
<script type="text/javascript">
    
$(function (){
	
	$('#datepicker12').datepicker({
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
	
	$("#datepicker12").focus(function () {
		$(".ui-datepicker-calendar").hide();
		$("#ui-datepicker-div").position({
		my: "center top",
		at: "center bottom",
		of: $(this)
		});
		});
		
	
	
	
});

</script>
<form action="ReportView/Calibration_PM_Report.jsp" method="POST" target="_new">

<div class="commonDiv" id="AMCWReport">

	
		<table class="commonTable" align="center" width="800px" height="100%" border="1">
			<tr>
				<td colspan="6" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Calibration / PM  Report</p></td>
			</tr>
			<tr>
			<td><b>Calibration / PM<font color="red">*</font></b></td>
				<td>
					<select  name="cal_pm" class="common-validation select" data-valid="mand"  style="width:140">
					<option value = "CAL" >CAL</option>
                    <option value = "PM" >PM</option>
						
					</select>
				</td>
			<td  class="lefttd" nowrap><b>Month / Year<font color = 'Red'>*</font></b></td>
              <td  class="centertd">
              <input type="text" id="datepicker12" name="calDateMonth" value="" class="common-validation select" data-valid="mand"  style="width:140">
                        
              </td>
				
			</tr>
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 443px;float: center;">
				<button type="submit" style="float:center;"  class="btn btn-primary" onClick=" return ControleAMCAndCAL('datepicker12')">Report</button>
			</p>
</div>
</form>