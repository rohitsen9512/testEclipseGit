$(function (){
	$( "#menu" ).menu();
	
});


function DisplayDropDownDataForReport(servletName,dropDownId)
{
	
	$.post(servletName,{action : 'DropDownResult'},function (r){
		
		if(r.data)
			{
			
				var list= '<option value=""> Select</option><option value="all"> All</option>';
				if((dropDownId == 'assetDivForLocAssetReport') || dropDownId == ('fordept') || dropDownId == ('divDataForlocReport') || dropDownId == ('assetDivForsubLocAsset'))
					list= '<option value="all"> All</option>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				
				for (var key in r.data[i])
		        {
					id=r.data[i][key];
					break;
		        }
				for (var key in r.data[i])
		        {
					val=r.data[i][key];
					
		        }
				list = list + '<option value="'+id+'"> '+val+'</option>';
				}
			
			
				$('#'+dropDownId).html(list);
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		
	},'json');

}

//This is for opposite display..........
function DropDownDataDisplayForReport(servletName,dropDownId)
{
	
	$.post(servletName,{action : 'DropDownResult'},function (r){
		
		if(r.data)
			{
			
				var list= '<option value="all"> All</option>';
				if((dropDownId == 'assetDivForLocAssetReport') || dropDownId == ('fordept') || dropDownId == ('divDataForlocReport') || dropDownId == ('assetDivForsubLocAsset'))
					list= '<option value="all"> All</option>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				
				for (var key in r.data[i])
		        {
					id=r.data[i][key];
					break;
		        }
				for (var key in r.data[i])
		        {
					val=r.data[i][key];
					
		        }
				list = list + '<option value="'+val+'"> '+id+'</option>';
				}
			
			
				$('#'+dropDownId).html(list);
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		
	},'json');

}


function DisplaySubDropDownDataForReport(ids,dropDownId,servletName)
{
	if(ids.value == 'all' || ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
		
		
	$.post(servletName,{action:'DropDownResult' , id : ids.value},function (r){
		
		if(r.data)
			{
			var list= '<option value=""> Select</option>';
				if(r.data.length == 0)
					{
						bootbox.alert("No sub value is there. Please select other.");
						$('#'+dropDownId).html(list);
						$('#'+dropDownId).focus();
						
					}
				else
					{
							for(var i = 0; i < r.data.length ; i++)
							{
							
							
							for (var key in r.data[i])
					        {
								id=r.data[i][key];
								break;
					        }
							for (var key in r.data[i])
					        {
								val=r.data[i][key];
								
					        }
							list = list + '<option value="'+id+'"> '+val+'</option>';
							}
					}
			
				$('#'+dropDownId).html(list);
				$('#'+dropDownId).focus();
			}
		else
			{
				bootbox.alert(" Please select the value.");
			}
		
	},'json');
		}
}

// This is for opposite display..........

function SubDropDownDataDisplayForReport(ids,dropDownId,servletName)
{
	if(dropDownId == 'subGroupDataForMaintenanceReport')
		$('#assetIdForAssetMaintenanceReport').html('<option value=""> Select</option>');
		
	if(ids.value == 'all' || ids.value == '' || ids.value == 'All')
		{
		
			$('#'+dropDownId).html('<option value=""> Select</option>');
			$('#'+dropDownId).focus();
		}
	else
		{
			$.post(servletName,{action:'DropDownResult' , id : ids.value},function (r){
				
				if(r.data)
					{
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
									for(var i = 0; i < r.data.length ; i++)
									{
									
									
									for (var key in r.data[i])
							        {
										id=r.data[i][key];
										break;
							        }
									for (var key in r.data[i])
							        {
										val=r.data[i][key];
										
							        }
									list = list + '<option value="'+val+'"> '+id+'</option>';
									}
							}
					
						$('#'+dropDownId).html(list);
						$('#'+dropDownId).focus();
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
			},'json');
		}
}