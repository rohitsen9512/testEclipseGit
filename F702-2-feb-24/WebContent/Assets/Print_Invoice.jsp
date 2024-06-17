<!--Print_mail_purchase_order.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#"> Assets > </a><a href="#">Print Invoice</a>-->
</div>
<style>
/* .table th, .table td {
line-height: 21px;
} */
</style>
<script type ="text/javascript" src="All_Js_File/Asset/Print_Invoice.js"></script>



<script type="text/javascript">

$(function() {
	$('#createPrintorMailPurchaseOrder').hide();
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
	
	DisplayExportInvoiceForPrint();
	/* DisplayDropDownData("M_Vendor","venDataForPrintMailPO",function (status){
		if(status)
			{
			
			}}); */
});

function printPg(elem)
{
	 w=window.open();
		w.document.write('<html><title>Export Invoice Print</title>');
		//w.document.write('<body onload="window.print()">' + $(elem).html() + '</body>');
		w.document.write('<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">');
		w.document.write('<link href="CSS/CommonPrint.css" rel="stylesheet" type="text/css">');
		
		w.document.write('<style>@page :last {display:none;}</style>');
		w.document.write($(elem).html());
		w.document.write('</html>');
		w.document.close();
}
function printPgForExcel(elem){
	window.open('data:application/vnd.ms-excel,' + encodeURIComponent( $('div[id$=venDetails]').html()));
	e.preventDefault();
}
function printlatterhead(elem)
{ w=window.open();
	w.document.write('<html><title>Purchase Order</title>');
	//w.document.write('<body onload="window.print()">' + $(elem).html() + '</body>');
	w.document.write('<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">');
	w.document.write('<link href="CSS/CommonPrint.css" rel="stylesheet" type="text/css">');
	w.document.write($(elem).html());
		w.document.write('<style>@page :first {margin-top:6cm;margin-bottom:2cm;scale:60%;}</style><html><head><title></title>');
	
	w.document.write('</html>');
	
	
	w.document.close();
	
	
	
	
	
	/* 
	 w=window.open();
		w.document.write('<html><title>Purchase Order</title>');
		//w.document.write('<body onload="window.print()">' + $(elem).html() + '</body>');
		w.document.write('<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">');
		w.document.write('<link href="CSS/CommonPrint.css" rel="stylesheet" type="text/css">');
		w.document.write($(elem).html());
		w.document.write('</html>');
		w.document.close(); */
}
 function Popup(data) 
{
    var mywindow = window.open('', 'my div', '');
    /*optional stylesheet*/ //mywindow.document.write('<link rel="stylesheet" href="main.css" type="text/css" />');
    mywindow.document.write('');
    mywindow.document.write(data);
   // w.disableExternalCapture();
    mywindow.print();
    //window.onfocus=function(){ window.close();};
 } 
 

</script>
<div class="card">
<div class="commonDiv" id="displayPrintorMailPurchaseOrder">
	<table class="commonTable" width="100%" height="100%">
			<tr>
				<td>
				<input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayExportInvoiceForPrint();">
				
					<!-- <strong>From Date</strong>&nbsp;
					<input type="text" name="dt_frm" class="common-validation datepicker">
					&nbsp;<strong>To Date</strong>&nbsp;
					<input type="text" name="dt_to" class="common-validation datepicker">&nbsp;
					<strong>Vendor</strong>&nbsp;
					<select name="id_ven" id="venDataForPrintMailPO">
						<option value="">Select</option>
					</select>
					<button type="button" style="margin-top: -10px;margin-left: 25px;" class="btn btn-primary" onclick="DisplayPurchaseOrderForPrintMail()">Search </button>
						 -->	
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered  PrintInvoice">
				
					</table>
				</td>
			</tr>
		</table>	
</div>
</div>
<div class="card">
<div class="commonDiv DisplayAmendPurchaseOrder" id="createPrintorMailPurchaseOrder">


<div id="venDetails" style="width: 250%;">

</div>
</div>
</div>
<div id="buttonForPoPrint">

</div>












