
function ControlAccepRejectTransferRequestDiv(action , DisplayDiv , HideDiv , FormName , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Cancel')
	{
		DisplayAssetForTransferRequestAcceptReject();
		$('#'+DisplayDiv).hide();
		$('#'+HideDiv).show();
	}
	
	else if(action =='Preview')
	{
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
		DisplayRequestedAssets(id);
		
	}
	else if(action =='Accepted' || action =='Rejected')
	{
		$('.TrvlIUTBTN').attr('disabled','disabled');
			var status_approv = 2;
				if(action =='Accepted')
					{
						status_approv = 1;
					}
				
				$.post('T_Accept_Reject',{action : 'Update' , status_approv : status_approv , id : id },function (r){
					
					if(r.data > 0)
						{
							DisplayAssetForTransferRequestAcceptReject();
							bootbox.alert(action +" successfully.");
							$('#'+DisplayDiv).hide();
							$('#'+HideDiv).show();
						}
					else
					{
						bootbox.alert("Try again.");
					}
					$('.TrvlIUTBTN').removeAttr('disabled');	
				},'json');
	}
			}});
}

function DisplayRequestedAssets(req_no)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	
	$.post('T_Accept_Reject',{action : 'Preview' ,req_no : req_no} , function (r){
		
		var list= '<tr><td colspan="4" class="tableHeader"><h3 style="margin-left: 300px;">Accept/Reject Intra Unit Transfer Asset Details</h3></td></tr>'+
			'<tr class="tableHeader info">'+
		'<td><strong><center>Asset ID</center></strong></td>'+
		'<td style="width: 390px;"><strong><center>Asset Name</center></strong></td>'+
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
				'<td colspan="4">'+
				'<p style="margin-left: 302px;">'+
					'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button name="save" type="button"  class="btn btn-primary TrvlIUTBTN" onclick="ControlAccepRejectTransferRequestDiv(\'Accepted\',\'AssetForIntraUnitTransferRequestApprovalView\',\'displayAssetForIntraUnitTransferRequestApproval\',\'submitAssetForIntraUnitTransferRequestApproval\','+req_no+')">Accept</button>'+
					'&nbsp;&nbsp;&nbsp;&nbsp;<button name="save" type="button"  class="btn btn-primary TrvlIUTBTN" onclick="ControlAccepRejectTransferRequestDiv(\'Rejected\',\'AssetForIntraUnitTransferRequestApprovalView\',\'displayAssetForIntraUnitTransferRequestApproval\',\'submitAssetForIntraUnitTransferRequestApproval\','+req_no+')">Reject</button>'+
					'&nbsp;&nbsp;&nbsp;&nbsp;<button name="cancel" type="button"  class="btn btn-primary TrvlIUTBTN" onclick="ControlAccepRejectTransferRequestDiv(\'Cancel\',\'AssetForIntraUnitTransferRequestApprovalView\',\'displayAssetForIntraUnitTransferRequestApproval\',\'submitAssetForIntraUnitTransferRequestApproval\','+req_no+')">Cancel</button>'+
				'</p>'+
			'</td>'+
		'</tr>';
				$('#displayAssetForRequestedAssets').html(list);
			}
		
		else
			{
				/*list +='<tr><td colspan="4"><strong><center>No Transfer Request Available</center></strong></td></tr>';*/
				$('#displayAssetForRequestedAssets').html(list);
			}
		
		
	},'json');

			}});
}
	
	
function EditAcceptRejectTransferRequest(action , DisplayDiv , HideDiv , FormName , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{		
		$.post('T_Accept_Reject',{action : 'Edit' , id : id},function (r){
				
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




function DisplayAssetForTransferRequestAcceptReject()
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
	$.post('T_Accept_Reject',{action : 'Display' ,searchWord : searchWord} , function (r){
		
			var list= '<thead><tr class="new" >'+
			'<td><strong><center>Request Number</center></strong></td>'+
			'<td><strong><center>Request Date</center></strong></td>'+
			'<td><strong><center>Requested By</center></strong></td>'+
			'<td style="width: 150px;"><strong><center>Preview</center></strong></td>'+
		'</tr></thead><tbody>';
			
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				list = list + '<tr class="success">'+
									'<td><center>Request No-'+params.req_no+'</center></td>'+
									'<td><center>'+params.dt_req+'</center></td>'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><strong><center><a class="alert" href="#" onclick="ControlAccepRejectTransferRequestDiv(\'Preview\',\'AssetForIntraUnitTransferRequestApprovalView\',\'displayAssetForIntraUnitTransferRequestApproval\',\'submitAssetForIntraUnitTransferRequestApproval\','+params.req_no+')"> Preview </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.displayAssetForIntraUnitTransferRequestApprovalClass').html('</tbody>'+list);
			}
		
		else
			{
			/*	list +='<tr><td colspan="6"><strong><center>No Transfer Request Available</center></strong></td></tr>';*/
				$('.displayAssetForIntraUnitTransferRequestApprovalClass').html('<tbody>'+list);
			}
		getButtonsForListView('displayAssetForIntraUnitTransferRequestApprovalClass','AssetForIntraUnitTransferRequestApproval');
		
	},'json');

			}});
}



