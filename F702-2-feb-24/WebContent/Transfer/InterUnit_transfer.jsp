<!--Intraunit_transfer.jsp-->


<script type="text/javascript">

$(function() {
	
	$('.iutDatepicker').datepicker({
		yearRange: '1985:2025',  
		changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      onSelect: function(selected,evnt) {
	    	
	    	  var dt_tran = $(this).val();
	    	  var id = $('input[name="id"]').val();
	    	  var id_wh = $('input[name="id_wh"]').val();
	    
	    	$(this).removeClass('error');
	    $.post('T_Inter_Transfer',{action : 'CheckDate' , dt_tran : dt_tran ,id:id,id_wh:id_wh},function (r){
	    		
	    		if(r.data == 2)
	    			{
	    			alert('Transfer Date  should be greater or equal to grn date '+r.dt_iut);
	    				$('#dt_tranID').focus();
	    				$('#dt_tranID').val('');
	    				$('#dt_tranID').addClass('error');
	    				exit(0);
	    			}
	    		else if(r.data == 3)
    			{
    			alert('Transfer Date  should be greater or equal to de allocated date '+r.dt_iut);
    				$('#dt_tranID').focus();
    				$('#dt_tranID').val('');
    				$('#dt_tranID').addClass('error');
    				exit(0);
    			}
	    		
	    },'json');
	      }
	});
	
	
	
	$('#TransferDetails').hide();
	DisplayAssetForInterTransfer();
	/* getButtonsForListView('DisplayAssetForTransferring','EditFrom'); */
	
	
});
</script>
<div class="card">
<div id="DisplayAssetForTransfer" class="commonDiv">
	<!-- <input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForInterTransfer()">
	 -->
	 <table id="DisplayAssetForTransferring"
		class="table table-bordered table-hover DisplayAssetForTransferring">
	
		<thead><tr class="info new">
			<th><strong>Asset ID</strong></th>
			<th><strong>Asset Name</strong></th>
			<th><strong>Serial Number</strong></th>
			<th><strong>Asset Description</strong></th>
			<th><strong>Transfer </strong></th>
		</tr></thead>
	</table>
</div>
</div>
<div class="card">
<div id="TransferDetails">
<form name="Transfer" id="Transfer">
	<table class="table table-bordered table-hover" id="displayAssetForRequestedAssetsFinalInterTransfer">
			
			<tr>
				<td colspan="4">
					<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary txx" onclick="ControlInterTransferAsset('Update','DisplayAssetForTransfer','TransferDetails','Transfer')">Transfer</button>
					<button name="cancel" type="button"  class="btn btn-primary txx" onclick="ControlInterTransferAsset('Cancel','DisplayAssetForTransfer','TransferDetails','Transfer')">Cancel</button>
				</td>
			</tr>
	</table>
</form>
</div>
</div>