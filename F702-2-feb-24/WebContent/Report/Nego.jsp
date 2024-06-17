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
DropDownDataDisplayForReport("M_Vendor","vendordata",function (status){if(status){

}});
</script>

<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#"> Reports ></a><a href="#">Negotiation Report</a> -->
</div>

<div class="commonDiv" id="displayGRNRejectReport">
<form action="ReportView/nego.jsp" method="post" target="_new">	
		<table align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Select Vendor</p></td>
			</tr>
			<!-- <tr>
				<td><strong>Quotation Start Date</strong></td>
				<td><input type="text" name="startdate" class="datepicker"></td>
			</tr>
			<tr>
				<td><strong>QuotationEnd Date</strong></td>
				<td ><input type="text" name="enddate" class="datepicker"></td>
			</tr> -->
		<tr>
				<td><b>Vendor</b></td>
				<td><input type="text" list="vendordata" name="nm_vendor_sales"
					 placeholder="All" class="common-validation resetAcc" > <datalist
						id="vendordata">
					</datalist></td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<p style="margin-left: 250px;">
						<button type="submit"   class="btn btn-primary" onclick="return GRNDate()">Preview</button>
					</p>
				</td>
		  </tr>
	</table>
</form>
</div>
