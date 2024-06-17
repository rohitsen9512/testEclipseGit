<!--Print_mail_purchase_order.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#"> Report ></a><a href="#">MIS Report ></a><a href="#"> Purchase Order Report</a>-->
</div>
<style type="text/css">
@media print
{
body * { visibility: hidden; }
#createPrintorMailPurchaseOrder * { visibility: visible; }

}
</style>
<script type ="text/javascript" src="All_Js_File/Report/PO_Report.js"></script>
<script type="text/javascript">
function printPOReport(elem)
{
	 w=window.open();
		w.document.write('<html><title>Purchase Order </title>');
		//w.document.write('<body onload="window.print()">' + $(elem).html() + '</body>');
		w.document.write('<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">');
		w.document.write('<link href="CSS/CommonPrint.css" rel="stylesheet" type="text/css">');
		w.document.write($(elem).html());
		w.document.write('</html>');
	//w.print();
}

$(function() {
	$('#createPrintorMailPurchaseOrder').hide();
	$('#buttonForPoReport').hide();
	
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
	
	DisplayPurchaseOrderForPrintMail();
	DisplayDropDownData("M_Vendor","venDataForPrintMailPO",function (status){
		if(status)
			{
			
			}});
});
</script>

<div class="commonDiv" id="displayPrintorMailPurchaseOrder">
	<table class="commonTable" width="100%" height="100%">
			<tr>
				<td>
					<strong>From Date</strong>&nbsp;
					<input type="text" name="dt_frm" class="common-validation datepicker" readonly>
					&nbsp;<strong>To Date</strong>&nbsp;
					<input type="text" name="dt_to" class="common-validation datepicker" readonly>&nbsp;
					<!-- <strong>Vendor</strong>&nbsp;
					<select name="id_ven" id="venDataForPrintMailPO">
						<option value="">Select</option>
					</select> -->
					<button type="button" style="margin-top: -10px;margin-left: 25px;" class="btn btn-primary" onclick="DisplayPurchaseOrderForPrintMail()">Search </button>
							
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered  PrintMailPODisplay">
				
					</table>
				</td>
			</tr>
		</table>	
</div>

<div class="commonDiv" id="createPrintorMailPurchaseOrder">
<center>
<table class="table table-bordered DisplayAmendPurchaseOrder" style="width:92%;">
	
		
</table>
</center>
</div>
<div id="buttonForPoReport">
<button type="button" style="margin-top: -10px;margin-left: 50%;" class="btn btn-primary" onclick="printPOReport('#createPrintorMailPurchaseOrder')">Print Preview</button>
					
</div>
