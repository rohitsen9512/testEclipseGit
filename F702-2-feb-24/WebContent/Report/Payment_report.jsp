<!--Payment_report.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#"> Reports ></a><a href="#">MIS report ></a><a href="#">Payment report</a> -->
</div>
<script type ="text/javascript" src="All_Js_File/Report/Payment_Details_Report.js"></script>
<script type="text/javascript">

$(function() {
	
	$('.hideRowCol').hide();
	
	var currentDate = new Date();
	$( ".datepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      
	    });
	$('input[name="dt_to"]').datepicker("setDate", currentDate);
	currentDate.setMonth(currentDate.getMonth() - 1);
	$('input[name="dt_frm"]').datepicker("setDate", currentDate);
	
	DisplayInvoiceForPaymentReport();
	
});
</script>
<div class="commonDiv" id="displayPaymentReport">
	
		<table class="commonTable table" align="center" style="width:100%;">
			<tr>
				<td>
					<strong>From Date</strong>&nbsp;
					<input id="dt_frm" type="text" name="dt_frm" value="" class="common-validation datepicker" readonly>
					&nbsp;&nbsp;<strong>
					To Date</strong>&nbsp;
					<input id="dt_to" type="text" name="dt_to" value="" class="common-validation datepicker" readonly>
					<button type="button" style="margin-top: -10px;margin-left: 25px;" class="btn btn-primary" onclick="DisplayInvoiceForPaymentReport()">Search </button>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered invoiceDisplayForPaymentReport">
						<tr class="new">
							<td><strong>Invoice Number</strong></td>
							<td><strong>Invoice Date</strong></td>
							<td><strong>PO Number</strong></td>
							<td><strong>PO Date</strong></td>
							<td><strong>Quantity</strong></td>
							<td><strong>Vendor</strong></td>
							<td><strong>Modify / Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>
