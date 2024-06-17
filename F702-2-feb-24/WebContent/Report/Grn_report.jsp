<!--Print_mail_purchase_order.jsp-->
<div class="commonDiv" style="padding:10px;color:blue;">
	<!--   Report > GRN Report >-->
</div>
<style type="text/css">
@media print
{
body * { visibility: hidden; }
#createPrintorMailPurchaseOrder * { visibility: visible; }

}
</style>
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
        	    autoSize: true
	    });
    });
	
	$('input[name="enddate"]').datepicker("setDate", currentDate);
	currentDate.setMonth(currentDate.getMonth() - 1);
	$('input[name="startdate"]').datepicker("setDate", currentDate);
	
});

</script>

<div class="commonDiv" id="displayCancelledPOReport">
<form action="ReportView/Grn_ReportView.jsp" method="post" target="_blank">	
		<table align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Select GRN Date</p></td>
			</tr>
			<tr>
				<td><strong>Start Date<font color="red">*</font></strong></td>
				<td><input type="text" name="startdate" class="datepicker" readonly></td>
			</tr>
			<tr>
				<td><strong>End Date<font color="red">*</font></strong></td>
				<td ><input type="text" name="enddate" class="datepicker" readonly></td>
			</tr>
		
			<tr>
				<td colspan="4" align="center">
					<p style="margin-left: 250px;">
						<button type="submit"   class="btn btn-primary" onclick="return PODate()">Preview</button>
					</p>
				</td>
		  </tr>
	</table>
</form>
</div>
