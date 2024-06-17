
function ControlAssetVerificationAsset(action , DisplayDiv , HideDiv ,HideDiv2 , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Cancel')
	{
		DisplayAssetForAssetVerification("T_Asset_Verification");
		$('#'+HideDiv2).hide();
		$('#'+DisplayDiv).show();
		$('#'+HideDiv).show();
		
		
	}
	
	else if(action =='Modify')
		{
			EditAssetVerificationFun(id);
			$('#'+HideDiv2).show();
			$('#'+DisplayDiv).hide();
			$('#'+HideDiv).hide();
			
			
		}
	else if(action == "Save")
	{
		
	var t = 0;
	
	var x = $('#AssetVerify').serialize();
			$.post('T_Asset_Verification',x,function (r)
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
							DisplayAssetForAssetVerification("T_Asset_Verification");
							$('#'+HideDiv2).hide();
							$('#'+DisplayDiv).show();
							$('#'+HideDiv).show();
						}
						
					},'json');
		}
	
			}});

}



function EditAssetVerificationFun(id)
{
	SessionCheck(function (ses){		
		if(ses)
			{		
		$.post('T_Asset_Verification',{action : 'Edit' , id : id},function (r){
				
			if(r.data)
			{
				for(var i = 0; i < r.data.length ; i++)
				
					for (var key in r.data[i])
						{
							if($('select[name='+key+']').is("select"))
							{
								$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
								
							}
						else if($('textarea[name='+key+']').is("textarea"))
							{
								$('textarea[name='+key+']').val(r.data[i][key]);
							}
						else
							{
								$('input[name='+key+']').val(r.data[i][key]);
							}
						}
				
				$('input[name="id"]').val(id);	
				$('input[name="id_loc"]').val(id_loc);
				$('input[name="id_subl"]').val(id_subl);
				$('input[name="id_wh_dyn"]').val(id_wh_dyn);
				$('input[name="id_flr"]').val(id_flr);
				$('input[name="comments"]').val(comments);
				$('input[name="verified_by"]').val(verified_by);
				$('input[name="verified_on"]').val(verified_on);
				
			}
			else
				{
					bootbox.alert("Try again.");
				}
					
		
	},'json');
			}});
}


function DisplayAssetForAssetVerification()
{
	SessionCheck(function (ses){		
		if(ses)
			{	
		$.post('T_Asset_Verification',{action : 'Display'},function (r){
			
			var list= '<tr class="tableHeader info">'+
			'<td><strong>Asset Name</strong></td>'+
			'<td><strong>Serial number</strong></td>'+
			'<td><strong>Group</strong></td>'+
			'<td><strong><a href="#">Verify </a></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.ds_pro+'</center></td>'+
									'<td><center>'+params.no_mfr+'</center></td>'+
									'<td><center>'+params.nm_assetdiv+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="ControlAssetVerificationAsset(\'Modify\',\'displayAssetForAssetVerificationSearch\',\'DisplayAssetForAssetVerification\',\'AssetVerificationDetails\','+params.id_wh+')"> Verify </a></strong></td>'+
									'</tr>';
				}
			
				$('.displayAssetForAssetVerification').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="6"><strong><center>No Asset Found To Verify.</center></strong></td></tr>';
				$('.displayAssetForAssetVerification').html(list);
			}
		
		
	},'json');

			}});
}


