
function ControlTravelRequestAsset(action , DisplayDiv , HideDiv , HideDiv2 , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action == "Edit")
	{
		AddInputValueForTravelRequest(id);
		
		$('#'+HideDiv).hide();
		$('#'+HideDiv2).hide();
		$('#'+DisplayDiv).show();
	}
	else if(action == "Save")
	{
		
	var t = 0;
	t = ValidationForm("submitTravelRequest");
	var x = $('#submitTravelRequest').serialize();
	if(t > 0)
		{
		$('.TrvlUTBTN').attr('disabled','disabled');
			$.post('T_Travel_Request',x,function (r)
					{
				
						if(r.data)
							{
								t=1;
							}
							else
								{
									t=0;
									bootbox.alert("Data is not inserted in data base please try again.");
								}
								
						if(t == 1)
						{
							/*DisplayAssetForTravelRequest();
							$('#'+HideDiv2).hide();
							$('#'+DisplayDiv).show();
							$('#'+HideDiv).show();*/
							$( ".travelRequest" ).trigger( "click" );
						}
						$('.TrvlUTBTN').removeAttr('disabled');	
					},'json');
		}
	
	
}
	
	else if(action == "Cancel")
		{
		$('#'+HideDiv2).hide();
		$('#'+DisplayDiv).show();
		$('#'+HideDiv).show();
		
		}
			}});
}

function DisplayAssetForTravelRequest()
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
		$.post('T_Travel_Request',{action : 'Display' ,searchWord : searchWord} , function (r){
	
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Asset ID</strong></td>'+
					'<td style="width: 390px;"><strong>Asset Name</strong></td>'+
					'<td><strong>Serial Number</strong></td>'+
					'<td><strong>Asset Description</strong></td>'+
					'<td><strong>Employee Name</strong></td>'+
					'<td style="width: 110px;"><strong><a href="#">Request </a></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
						if(params.no_mfr == '') no_mfr = '-'; else no_mfr = params.no_mfr;
						if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
						if(params.nm_emp == '') nm_emp = '-'; else nm_emp = params.nm_emp;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+ds_pro+'</center></td>'+
						'<td><center>'+no_mfr+'</center></td>'+
						'<td><center>'+ds_asst+'</center></td>'+
						'<td><center>'+nm_emp+'</center></td>'+
						'<td><strong><a class="alert" href="#" onclick="ControlTravelRequestAsset(\'Edit\',\'TravelRequestDetails\',\'displayAssetForTravelRequestSearch\',\'DisplayAssetForTravelRequest\','+params.id_wh+')"> Request</a></strong></td>'+
						'</tr>';
						}
					
					
						$('.DisplayAssetForTravelRequesting').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available..</strong></center></td></tr>';
						$('.DisplayAssetForTravelRequesting').html(list);
					}
					
				
			},'json');
			}});

}


function AddInputValueForTravelRequest(id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	$.post('T_Travel_Request',{action : 'Edit', id : id},function (r)
			{
		
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						
							for (var key in r.data[i])
								{
								
										$('input[name='+key+']').val(r.data[i][key]);
									
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



