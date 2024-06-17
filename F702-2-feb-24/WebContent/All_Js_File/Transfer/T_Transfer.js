
function ControlTransferAsset(action , DisplayDiv , HideDiv , FormName , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Cancel')
	{
		DisplayAssetForTransfer();
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
	}
	
	else if(action =='Preview')
		{
			$('#'+HideDiv).hide();
			$('#'+DisplayDiv).show();
			DisplayRequestedAssetsForFinalTransfer(id);
			
		}
	
	else if(action == "Update")
	{
		$('input[name="dt_tran"]').removeClass('error');
			var dt_tran =$('input[name="dt_tran"]').val();
			
			if(dt_tran == '')
				{
					bootbox.alert('Mandatory Field');
					$('input[name="dt_tran"]').focus();
					$('input[name="dt_tran"]').addClass('error');
					exit(0);
				}
			$('.tx').attr('disabled','disabled');

			
			$.post('T_Transfer',{action : 'Update', req_no : id,dt_tran : dt_tran },function (r){
				
				if(r.data > 0)
					{
					bootbox.alert("Updated successfully.");
						/*DisplayAssetForTransfer();
						
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();*/
					//$( ".intraunittransfer_event" ).trigger( "click" );
					window.location = $('.intraunittransfer_event').attr('href');
					}
				else
				{
					bootbox.alert("Try again.");
				}
				$('.tx').removeAttr('disabled');
			},'json');
			
	}
			}});
}

function DisplayRequestedAssetsForFinalTransfer(req_no)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	
	$.post('T_Transfer',{action : 'Preview' ,req_no : req_no} , function (r){
		
		var list= '<tr><td colspan="4" class="tableHeader"><center><h3>Asset Details For Relocation</h3></center></td></tr>'+
			'<tr class="tableHeader info">'+
		'<td><strong><center>Asset ID</center></strong></td>'+
		'<td style="width: 390px;"><strong><center>Asset Name<center></strong></td>'+
		'<td><strong><center>Serial Number</center></strong></td>'+
		'<td><strong><center>Asset Description</center></strong></td>'+
	'</tr>';
			
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				list = list + '<tr class="success">'+
				'<td><center>'+params.id_wh_dyn+'</center></td>'+
				'<td><center>'+params.ds_pro+'</center></td>'+
				'<td><center>'+params.serial_no+'</center></td>'+
				'<td><center>'+params.ds_asst+'</center></td>'+
				'</tr>';
				}
			
				list = list +'<tr>'+
				'<div><td colspan="4">'+
				'<p><input type="hidden" name="req_no" value="'+req_no+'">'+
				'<b>Transfer Date <font color="red"> * </font>:</b>'+
				'<input style="width:200px;" type="text" name="dt_tran" value="" class="form-control iutDatepicker">'+
					'&nbsp;&nbsp;&nbsp;&nbsp;<button name="save" type="button" style="margin-left:40%;"   class="btn btn-primary tx" onclick="ControlTransferAsset(\'Update\',\'DisplayAssetForTransfer\',\'TransferDetails\',\'Transfer\','+req_no+')">Transfer</button>'+
					'&nbsp;&nbsp;&nbsp;&nbsp;<button name="cancel" type="button"  class="btn btn-primary tx" onclick="ControlTransferAsset(\'Cancel\',\'DisplayAssetForTransfer\',\'TransferDetails\',\'Transfer\','+req_no+')">Cancel</button>'+
				'</p>'+
			'</div></td>'+
		'</tr>';
				$('#displayAssetForRequestedAssetsFinalTransfer').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="4"><strong><center>No Transfer Request Available</center></strong></td></tr>';
				$('#displayAssetForRequestedAssetsFinalTransfer').html(list);
			}
		$( ".iutDatepicker" ).datepicker({
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "yy-mm-dd",
		      autoSize: true,
		      altFormat: "DD-MMM-YYYY",
		      defaultDate: new Date(),
		      onSelect: function(selected,evnt) {
			    	
		    	  var req_no = $('input[name="req_no"]').val();
		    	var dt_tran = $('input[name="dt_tran"]').val();
		    	$('input[name="dt_tran"]').removeClass('error');
		    	
		    $.post('T_Transfer',{action : 'checkTransferDate' , dt_tran : dt_tran ,req_no : req_no },function (r){
		    		
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
		
	},'json');

			}});
}

/*function TransferFun(action , DisplayDiv , HideDiv , FormName , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{		
		$.post('T_Transfer',{action : 'Edit' , id : id},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
							for (var key in r.data[i])
					        {
								
									$('input[name='+key+']').val(r.data[i][key]);
								
					        }
							 
						}
						$('input[name="id"]').val(id);
						
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
		},'json');
			}});
}
*/

function DisplayAssetForTransfer()
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
	$.post('T_Transfer',{action : 'Display' ,searchWord : searchWord} , function (r){
	
		
			var list= '<thead><tr class="new">'+
			'<th><strong><center>Request Number</center></strong></th>'+
			'<th><strong><center>Request Date</center></strong></th>'+
			'<th><strong><center>Approved By</center></strong></th>'+
			'<th style="width: 150px;"><strong><center>Preview</center></strong></th>'+
		'</tr></thead><tbody>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
								'<td><center>Request No-'+params.req_no+'</center></td>'+
								'<td><center>'+params.dt_req+'</center></td>'+
								'<td><center>'+params.nm_emp+'</center></td>'+
								'<td><strong><center><a class="alert" href="#" onclick="ControlTransferAsset(\'Preview\',\'TransferDetails\',\'DisplayAssetForTransfer\',\'Transfer\','+params.req_no+')"> Transfer </a></center></strong></td>'+
								'</tr>';
				
				}
			
				$('.DisplayAssetForTransferring').html('</tbody>'+list);
			}
		
		else
			{
				list +='<tr><td colspan="6"><strong><center>No Asset Available for transfer </center></strong></td></tr>';
				$('.DisplayAssetForTransferring').html('</tbody>'+list);
			}
		/*getButtonsForListView('DisplayAssetForTransferring','AssetForTransferring');*/
		
	},'json');
			}});

}



