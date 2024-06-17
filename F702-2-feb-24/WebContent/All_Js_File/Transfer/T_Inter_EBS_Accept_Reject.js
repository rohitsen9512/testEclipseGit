
function ControlEBSAccepRejectInterTransferRequestDiv(action , DisplayDiv , HideDiv , FormName , id , tranType)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Cancel')
	{
		/*DisplayAssetForInterTransferRequestEBSAcceptReject();
		$('#'+DisplayDiv).hide();
		$('#'+HideDiv).show();*/
		$( ".interunittransferapproval_event" ).trigger( "click" );
	}
	
	else if(action =='Preview')
	{
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
		DisplayRequestedAssetsForInterEBSTransfer(id);
		
	}
	else if(action =='Accepted' || action =='Rejected')
	{
		$('.IUTABTN').attr('disabled','disabled');
			var status_approv = 2;
				if(action =='Accepted')
					{
						status_approv = 1;
					}
				$.post('T_Inter_EBS_Accept_Reject',{action : 'Update' , status_approv : status_approv , req_no : id },function (r){
					
					if(r.data > 0)
						{
							bootbox.alert(action +" Successfully.");
							$( ".interunittransferapprovalEbs" ).trigger( "click" );
						}
					else
					{
						bootbox.alert("Try again.");
					}
					$('.IUTABTN').removeAttr('disabled');
				},'json');
	}
			}});
}

function DisplayRequestedAssetsForInterEBSTransfer(req_no)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	
	$.post('T_Inter_EBS_Accept_Reject',{action : 'Preview' ,req_no : req_no} , function (r){
		
		var list= '<tr><td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 300px;">Accept/Reject Inter Unit Transfer Asset Details</p></td></tr>'+
			'<tr class="tableHeader info">'+
		'<td><strong><center>Ware House ID</center></strong></td>'+
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
				'<td><center>'+params.no_mfr+'</center></td>'+
				'<td><center>'+params.ds_asst+'</center></td>'+
				'</tr>';
				}
			
				list = list +'<tr>'+
				'<td colspan="4">'+
				'<p style="margin-left: 302px;">'+
				'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button name="save" type="button"  class="btn btn-primary IUTABTN" onclick="ControlEBSAccepRejectInterTransferRequestDiv(\'Accepted\',\'AssetForIntraUnitTransferRequestApprovalView\',\'displayAssetForIntraUnitTransferRequestApproval\',\'submitAssetForIntraUnitTransferRequestApproval\','+req_no+')">Accept</button>'+
				'&nbsp;&nbsp;&nbsp;&nbsp;<button name="save" type="button"  class="btn btn-primary IUTABTN" onclick="ControlEBSAccepRejectInterTransferRequestDiv(\'Rejected\',\'AssetForIntraUnitTransferRequestApprovalView\',\'displayAssetForIntraUnitTransferRequestApproval\',\'submitAssetForIntraUnitTransferRequestApproval\','+req_no+')">Reject</button>'+
				'&nbsp;&nbsp;&nbsp;&nbsp;<button name="cancel" type="button"  class="btn btn-primary IUTABTN" onclick="ControlEBSAccepRejectInterTransferRequestDiv(\'Cancel\',\'AssetForIntraUnitTransferRequestApprovalView\',\'displayAssetForIntraUnitTransferRequestApproval\',\'submitAssetForIntraUnitTransferRequestApproval\','+req_no+')">Cancel</button>'+
				'</p>'+
			'</td>'+
		'</tr>';
				$('#displayAssetForInterRequestedAssets').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="4"><strong><center>No Transfer Request Available</center></strong></td></tr>';
				$('#displayAssetForInterRequestedAssets').html(list);
			}
		
		
	},'json');

			}});
}

function EditAcceptRejectInterTransferRequest(action , DisplayDiv , HideDiv , FormName , id , tranType)
{
	SessionCheck(function (ses){		
		if(ses)
			{	
		$.post('T_Inter_EBS_Accept_Reject',{action : 'Edit' , id : id , tranType : tranType},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
							for (var key in r.data[i])
					        {
									$('input[name='+key+']').val(r.data[i][key]);
					        }
							 
						}
						if(tranType == 'Temporary')
							{
							$('.RowColHideIUTApprov').hide();
							$('.HideShowVenForIUTApprov').show();
							
							}
						else
							{
							$('.hideLocAdnSubLocForIUTApprov').show();
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




function DisplayAssetForInterTransferRequestEBSAcceptReject()
{

	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
	$.post('T_Inter_EBS_Accept_Reject',{action : 'Display' ,searchWord : searchWord} , function (r){
		
			var list= '<tr class="tableHeader info">'+
			'<td><strong><center>Request Number</center></strong></td>'+
			'<td><strong><center>Request Date</center></strong></td>'+
			'<td><strong><center>Requested By</center></strong></td>'+
			'<td style="width: 150px;"><strong><center><a href="#">Preview</a></center></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
					params = r.data[i];
					list = list + '<tr class="success">'+
										'<td><center>Request No-'+params.req_no+'</center></td>'+
										'<td><center>'+params.dt_req+'</center></td>'+
										'<td><center>'+params.nm_emp+'</center></td>'+
										'<td><strong><center><a class="alert" href="#" onclick="ControlEBSAccepRejectInterTransferRequestDiv(\'Preview\',\'AssetForIntraUnitTransferRequestApprovalView\',\'displayAssetForIntraUnitTransferRequestApproval\',\'submitAssetForIntraUnitTransferRequestApproval\','+params.req_no+')"> Preview </a></center></strong></td>'+
										'</tr>';
				}
			
				$('.displayAssetForIntraUnitTransferRequestEBSApprovalClass').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="6"><strong><center>No Transfer Request Available</center></strong></td></tr>';
				$('.displayAssetForIntraUnitTransferRequestEBSApprovalClass').html(list);
			}
		
		
	},'json');

			}});
}


