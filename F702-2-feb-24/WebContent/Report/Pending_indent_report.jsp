<!--Edit_indent.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#"> Report > MIS Report></a><a href="#">PR Status</a>-->
</div>
<script type ="text/javascript" src="All_Js_File/Report/Pending_Indent_Report.js"></script>
<script type="text/javascript">

$(function() {
	$('#PreviewIndentForDelete').hide();
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
    
DisplayIndentForPreview('Pending_Indent_Report','displayDeleteIndent','PreviewIndentForDelete');
    
});

</script>

<div class="commonDiv" id="displayDeleteIndent">
	
		<table width="100%" height="100%">
			<tr>
				<td>
					<strong>From Date</strong>&nbsp;
					<input type="text" name="dt_frm" class="common-validation datepicker"readonly>
					&nbsp;&nbsp;<strong>
					To Date</strong>&nbsp;
					<input type="text" name="dt_to" class="common-validation datepicker" readonly >
					<button type="button" style="margin-top: -10px;margin-left: 25px;" class="btn btn-primary" onclick="DisplayIndentForPreview('Pending_Indent_Report','displayDeleteIndent','PreviewIndentForDelete')">Search </button>
							
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered  DisplayDeleteIndentForPreview">
				<tr class="info new">
					<td><strong>S No.</strong></td>
					<td><strong>Indent Code<a href=#></a></strong></td>
					<td><strong>Indent Date</strong></td>
					<td><strong>Created By</strong></td>
					<td><strong>Select</strong></td>
			   </tr>
			   <tr>
				    <td  colspan="5" ><font color="red"><center>Records not available</center></font></td>
			   </tr>
					</table>
				</td>
			</tr>
		</table>
</div>



<div class="commonDiv" id="PreviewIndentForDelete">
	<form action="" name="submitEditIndent">	
		<br>
		<table class="commonTable DisplayIndentForDelete" align="center" width="100%" height="100%" border="1">
			<tr>
				<td colspan="12" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 300px;">Select Request For Requesting Quotation</p></td>
			</tr>
			<tr style="background-color: blue;color: white; style="font-size: 24px;">
					<td><strong>S No.</strong></td>
					<td><strong>Request No.</strong></td>
					<td><strong>Requestor</strong></td>
					<td><strong>Asset Type</strong></td>
				    <td><strong>Group</strong></td>
					<td><strong>Subgroup</strong></td>
					<td><strong>Manufacturer</strong></td>
					<td><strong>Model No.</strong></td>
					<td><strong>Qty</strong></td>
					<td><strong>Unit Price</strong></td>
					<td><strong>Total Price</strong></td>
					<td><strong>Select</strong></td>
				</tr>
			<tr>
				<td  colspan="12">
				<button name="delete" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlAsset('Delete','displayInvoice','createInvoice','submitInvoice','A_Invoice')">Delete</button>
				<button name="addnewreq" type="button"  class="btn btn-primary" onclick="ControlAsset('Cancel','displayInvoice','createInvoice','submitInvoice','A_Invoice')">Add New Request</button>
			 </td>
			</tr>
			
		</table>
	</form>
</div>
	
