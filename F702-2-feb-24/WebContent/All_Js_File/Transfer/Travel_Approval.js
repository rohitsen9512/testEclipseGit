
function ControlAccepRejectTravelDiv(action , DisplayDiv , HideDiv , FormName , id)
{
	
	 if(action =='Modify')
	{
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
		EditAcceptRejectTravel(action , DisplayDiv , HideDiv , FormName , id);
		
	}
	 SessionCheck(function (ses){		
			if(ses)
				{
	  if(action =='Cancel')
		{
			DisplayAssetForTravelAcceptReject();
			$('#'+DisplayDiv).hide();
			$('#'+HideDiv).show();
		}
		
	else if(action =='Accepted' || action =='Rejected')
	{
		$('.TrvlApprUTBTN').attr('disabled','disabled');
			var status_approv = 2;
				if(action =='Accepted')
					{
						status_approv = 1;
					}
				var id = $('input[name="id"]').val();
					var id_wh = $('input[name="id_wh"]').val();
					var aprvl_remarks = $('textarea[name="aprvl_remarks"]').val();
					
				$.post('T_Travel_Accept_Reject',{action : 'Update' , status_approv : status_approv , id : id,id_wh:id_wh,aprvl_remarks:aprvl_remarks },function (r){
					
					if(r.data > 0)
						{
						bootbox.alert(action +" Successfully.");
							/*DisplayAssetForTravelAcceptReject();
							
							$('#'+DisplayDiv).hide();
							$('#'+HideDiv).show();*/
							$( ".TravelApproval" ).trigger( "click" );
						}
					else
					{
						bootbox.alert("Try again.");
					}
					
					$('.TrvlApprUTBTN').removeAttr('disabled');	
				},'json');
	}
			}});	
}


function EditAcceptRejectTravel(action , DisplayDiv , HideDiv , FormName , id)
{

	SessionCheck(function (ses){		
		if(ses)
			{		
		$.post('T_Travel_Accept_Reject',{action : 'Edit' , id : id},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
							for (var key in r.data[i])
					        {
								if(key == 'remarks')
								{
									$('textarea[name='+key+']').val(r.data[i][key]);
									
								}	
								$('input[name='+key+']').val(r.data[i][key]);
					        }
							 
						}
						$('input[name="id"]').val(id);
						$('input[name="id_wh"]').val(r.data[0].id_wh);
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
		},'json');
			}});
}




function DisplayAssetForTravelAcceptReject()
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
	$.post('T_Travel_Accept_Reject',{action : 'Display' ,searchWord : searchWord} , function (r){
		
			var list= '<tr class="tableHeader info">'+
			'<td><strong>Asset ID</strong></td>'+
			'<td style="width: 390px;"><strong>Asset Name</strong></td>'+
			'<td><strong>Serial Number</strong></td>'+
			'<td><strong>Asset Description</strong></td>'+
			'<td style="width: 150px;"><strong><a href="#">Accept/Reject</a></strong></td>'+
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
									'<td><strong><a class="alert" href="#" onclick="ControlAccepRejectTravelDiv(\'Modify\',\'AssetForIntraUnitTravelApprovalView\',\'displayAssetForTravelApproval\',\'submitAssetForTravelApproval\','+params.id_trvl+')"> Accept/Reject </a></strong></td>'+
									'</tr>';
				}
			
				$('.displayAssetForTravelApprovalClass').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="6"><strong><center>No Transfer Request Available</center></strong></td></tr>';
				$('.displayAssetForTravelApprovalClass').html(list);
			}
		
		
	},'json');

			}});
}


