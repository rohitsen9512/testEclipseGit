<!--Intraunit_transfer.jsp-->


<script type="text/javascript">

$(function() {
	
	var currentDate = new Date();
	$( ".iutDatepicker" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd",
	      autoSize: true,
	      altFormat: "DD-MMM-YYYY",
	      defaultDate: new Date(),
	      onSelect: function(selected,evnt) {
		    	
	    	  var id = $('input[name="id"]').val();
	    	var dt_tran = $('input[name="dt_tran"]').val();
	    	$('input[name="dt_tran"]').removeClass('error');
	    	
	    $.post('T_Transfer',{action : 'checkTransferDate' , dt_tran : dt_tran ,id : id },function (r){
	    		
	    		if(r.data == 0)
	    			{
	    			
	    			alert('Transfer Date should be greater or equal to installed / last IUT date '+r.dt_tran);
	    			$('input[name="dt_tran"]').focus();
	    			$('input[name="dt_tran"]').val('');
	    			$('input[name="dt_tran"]').addClass('error');
	    				exit(0);
	    			}
	    },'json');
	    }
	});
	//$('input[name="dt_tran"]').datepicker("setDate", currentDate);
	
	
	$('#TransferDetails').hide();
	DisplayAssetForTransfer();
	
	

	
	
	
});
</script>
<div class="card">
<div id="DisplayAssetForTransfer" class="commonDiv">
	<!-- <input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayAssetForTransfer()">
 -->
 <table id="DisplayAssetForTransferring"
		class="table table-bordered table-hover DisplayAssetForTransferring">
	
		<thead><tr class=" new">
			<th><strong>Asset ID</strong></td>
			<th><strong>Asset Name</strong></td>
			<th><strong>Serial Number</strong></td>
			<th><strong>Asset Description</strong></td>
			<th><strong>Transfer</strong></td>
		</tr></thead>
	</table>

</div>
</div>
<div class="card">
<div id="TransferDetails">
<form name="Transfer" id="Transfer">
	<table class="table table-bordered table-hover" id="displayAssetForRequestedAssetsFinalTransfer">
		<!-- 	
				<tr>
				<td><b>Transfer Date <font color="red">*</font></b></td>
				<td><input type="text" name="dt_tran" value="" class="iutDatepicker"></td>
				<td colspan="2"></td>
				</tr>
			<tr>
				<td colspan="4">
					<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlTransferAsset('Update','DisplayAssetForTransfer','TransferDetails','Transfer')">Transfer</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlTransferAsset('Cancel','DisplayAssetForTransfer','TransferDetails','Transfer')">Cancel</button>
				</td>
			</tr> -->
	</table>
</form>
</div>
</div>