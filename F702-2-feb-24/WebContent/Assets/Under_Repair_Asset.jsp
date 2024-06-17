<!--Install_asset.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Assets > </a><a href="#">Under Repair Assets</a>-->
</div>

<script type="text/javascript">

$(function() {
	
	
	var currentDate = new Date();
	$( ".datepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	    });
	
	
	$('button[name="update"]').addClass('hideButton');
	$('#InstallDetails').addClass('hideButton');
	
	$('.datepickerForInstall').datepicker({
		yearRange: '1985:2025',  
		changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      onSelect: function(selected,evnt) {
	    	
	    	  var dt_grn = $('#dt_allocId').val();
	    	  var id = $('input[name="id"]').val();
	    	var id_grn = $('input[name="id_grn"]').val();
	    	$('#dt_allocId').removeClass('error');
	    $.post('A_Install',{action : 'CheckDate' , dt_grn : dt_grn ,id_grn : id_grn,id:id},function (r){
	    		
	    		if(r.data == 2)
	    			{
	    			
	    			alert('Allocation Date should be greater or equal to GRN date '+r.dt_grn);
	    				$('#dt_allocId').focus();
	    				$('#dt_allocId').val('');
	    				$('#dt_allocId').addClass('error');
	    				exit(0);
	    			}
	    		if(r.data == 3)
    			{
    			
    			alert('Allocation Date should be greater or equal to Un Installed date '+r.dt_grn);
    				$('#dt_allocId').focus();
    				$('#dt_allocId').val('');
    				$('#dt_allocId').addClass('error');
    				exit(0);
    			}
	    		if(r.data == 0)
    			{
    			
    			alert('Allocation Date should be greater or equal to travel receive date '+r.dt_grn);
    				$('#dt_allocId').focus();
    				$('#dt_allocId').val('');
    				$('#dt_allocId').addClass('error');
    				exit(0);
    			}
	    		
	    },'json');
	      }
	});	
	
	
						DisplayAssetForRepair("A_Repair");
				
	
});
</script>


<div class="commonDiv" id="displayAssetForInstallSearch">
	<input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayAssetForRepair('A_Repair')">
</div>
<div id="DisplayAssetForInstall">
	<table class="table table-bordered DisplayAssetForInstallation">
		<tr class="info new">
			<td><strong>Asset ID</strong></td>
			<td><strong>Asset Name</strong></td>
			<td><strong>Model Number</strong></td>
			<td><strong>Asset Description</strong></td>
			<td><strong>Install It</strong></td>
		</tr>
	</table>
</div>


