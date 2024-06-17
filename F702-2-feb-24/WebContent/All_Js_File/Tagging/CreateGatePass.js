
function ControlDivForGatePass(action , DisplayDiv , HideDiv , FormName , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Create')
		{	
		EditGatePassFun(action , DisplayDiv , HideDiv , FormName , id);
		
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
		}
	else if(action =='Next')
	{	
		var t =false;
t = ValidationForm(FormName);
		if(t)
			{
			$('#'+HideDiv).hide();
			$('#'+DisplayDiv).show();
			$('#DisplayAssetDataForView').show();
			DisplayAssetForCreateGatePass(FormName);
			}
		
		
	}
	else if(action =='Cancel')
	{
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
	}
	
	else if(action =='Save')
	{
	
		var t=false;
		t = ValidationForm(FormName);
		
		if(t)
			{
				var x = $('#'+FormName).serialize();
				
				$.post('T_Create_Gate_Pass',x,function (r){
					
					if(r.data == 1)
						{
							DisplayAssetForCreateGatePass('SearchForGatePass');
							bootbox.alert("Created Successfully.");
							$('#'+HideDiv).hide();
							$('#'+DisplayDiv).show();
						}
					else
						{
						bootbox.alert("Try Again.");
						}

				},'json');
			}
		
	}
			}});
}



function EditGatePassFun(action , DisplayDiv , HideDiv , FormName , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{	
	
		$.post('T_Create_Gate_Pass',{action : 'Edit' , id : id},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
							for (var key in r.data[i])
					        {
									$('input[name='+key+']').val(r.data[i][key]);
					        }
						}
						
						$('input[name="id_iut"]').val(id);
						$('input[name="type_tran"]').val(r.data[0].type_tran);
						$('input[name="id_wh"]').val(r.data[0].id_wh);
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
		},'json');
			}});
}


function DisplayAssetForCreateGatePass(FormName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var x = $('#'+FormName).serialize();
			$.post('T_Create_Gate_Pass',x,function (r){
				
				var list= '<tr>'+
					'<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:370px">Select AssetId To Relocate</p></td>'+
					'</tr>'+
					'<tr class="tableHeader info">'+
				'<td><center><strong>Asset ID</strong></center></td>'+
				'<td><center><strong>Transfer Type</strong></center></td>'+
				'<td><center><strong>Asset Name</strong></center></td>'+
				'<td><center><strong>Serial No</strong></center></td>'+
				'<td><strong><a href="#">Create Gate Pass</a></strong></td>'+
			'</tr>';
				
			if(r.data.length > 0)
				{
					
					
					for(var i = 0; i < r.data.length ; i++)
					{
					
					params = r.data[i];
					
					list = list + '<tr class="success">'+
										'<td><center>'+params.id_wh_dyn+'</center></td>'+
										'<td><center>'+params.type_tran+'</center></td>'+
										'<td><center>'+params.ds_pro+'</center></td>'+
										'<td><center>'+params.no_mfr+'</center></td>'+
										'<td><strong><a class="alert" href="#" onclick="ControlDivForGatePass(\'Create\',\'DisplayAssetDataForCreateGatePass\',\'DisplayAssetDataForView\',\'submitCreateGatepass\','+params.id_iut+')"> Create Gate Pass </a></strong></td>'+
										'</tr>';
					}
					
					
					$('.DisplayAssetDataForCreateGatePassClass').html(list);
					
					
				
				}
			
			else
				{
					list +='<tr><td colspan="3"><strong><center>Grn is not created yet.</center></strong></td></tr>';
					$('.DisplayAssetDataForCreateGatePassClass').html(list);
				}
			
		},'json');
			}});
}

