
function ControlTravelReceiveAsset(action , DisplayDiv , HideDiv , HideDiv2 , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action == "Edit")
	{
		AddInputValueForTravelReceive(id);
		
		$('#'+HideDiv).hide();
		$('#'+HideDiv2).hide();
		$('#'+DisplayDiv).show();
	}
	else if(action == "Save")
	{
		
	var t = 0;
	t = ValidationForm("submitTravelReceive");
	var x = $('#submitTravelReceive').serialize();
	if(t > 0)
		{
			
			$.post('Travel_Receive_Asset',x,function (r)
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
							bootbox.alert("Asset Received Successfuly..");
							/*DisplayAssetForTravelReceive();
							$('#'+HideDiv2).hide();
							$('#'+DisplayDiv).show();
							$('#'+HideDiv).show();*/
							$( ".TravelReceiveAsset_event" ).trigger( "click" );
						}
						
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

function DisplayAssetForTravelReceive()
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
				$.post('Travel_Receive_Asset',{action : 'Display' ,searchWord : searchWord} , function (r){
	
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Asset ID</strong></td>'+
					'<td style="width: 390px;"><strong>Asset Name</strong></td>'+
					'<td><strong>Serial Number</strong></td>'+
					'<td><strong>Asset Description</strong></td>'+
					'<td style="width: 110px;"><strong><a href="#">Receive </a></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
						if(params.no_mfr == '') no_mfr = '-'; else no_mfr = params.no_mfr;
						if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+ds_pro+'</center></td>'+
						'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center>'+ds_asst+'</center></td>'+
						'<td><strong><a class="alert" href="#" onclick="ControlTravelReceiveAsset(\'Edit\',\'TravelReceiveDetails\',\'displayAssetForTravelReceiveSearch\',\'DisplayAssetForTravelReceive\','+params.id_trvl+')"> Receive</a></strong></td>'+
						'</tr>';
						}
					
					
						$('.DisplayAssetForTravelReceive').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available..</strong></center></td></tr>';
						$('.DisplayAssetForTravelReceive').html(list);
					}
					
				
			},'json');
			}});

}


function AddInputValueForTravelReceive(id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	$.post('Travel_Receive_Asset',{action : 'Edit', id : id},function (r)
			{
		
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						
							for (var key in r.data[i])
								{
										$('input[name='+key+']').val(r.data[i][key]);
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



