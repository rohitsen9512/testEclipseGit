
function ControlAcceptRejectGrnDivInventory(action , DisplayDiv , HideDiv , FormName , id)
{
	if(action =='Cancel')
	{
		DisplayInventoryForAcceptReject();
		$('#'+DisplayDiv).hide();
		$('#'+HideDiv).show();
	}
	
	else if(action =='Modify')
		{
			EditAcceptRejectInventoryGrnFun(action , DisplayDiv , HideDiv , FormName , id);
			
		}
	else if(action =='Accepted' || action =='Rejected')
	{

			var status_approv = 2;
				if(action =='Accepted')
					{
						status_approv = 1;
					}
				var id_inventory_grn = $('input[name="id"]').val();
				var remarks = $('textarea[name="remarks"]').val();
				$.post('I_Accept_Reject',{action : 'Update' , status_approv : status_approv , id : id_inventory_grn , remarks : remarks},function (r){
					
					if(r.data > 0)
						{
							DisplayInventoryForAcceptReject();
							bootbox.alert(action +" Successfully.");
							$('#'+DisplayDiv).hide();
							$('#'+HideDiv).show();
						}
					else
					{
						bootbox.alert("Try again.");
					}
					
				},'json');
	}
	
}



function EditAcceptRejectInventoryGrnFun(action , DisplayDiv , HideDiv , FormName , id)
{
		
		$.post('I_Create_Grn',{action : 'Edit' , id : id},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
							for (var key in r.data[i])
					        {
								
									$('input[name='+key+']').val(r.data[i][key]);
								
					        }
							 
						}
						$('textarea[name="remarks"]').val(r.data[0].remarks);
						$('input[name="id"]').val(id);
						
						if(r.data[0].dtDc != '01/01/1900')
							$('input[name="dt_dc"]').val(r.data[0].dtDc);
						else
							$('input[name="dt_dc"]').val('');
						
							if(r.data[0].dtGrn != '01/01/1900')
							$('input[name="dt_grn"]').val(r.data[0].dtGrn);
							else
								$('input[name="dt_grn"]').val('');
								
							$('input[name="dt_inv"]').val(r.data[0].dtInv);
							$('input[name="dt_po"]').val(r.data[0].dtPo);
							
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();
						
					}
				else
					{
						bootbox.alert("Somthing went wrong.");
					}
				
		},'json');
}


function DisplayInventoryForAcceptReject()
{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	
		$.post('I_Accept_Reject',{action : 'Display' ,dt_frm:dt_frm,dt_to:dt_to},function (r){
			
			var list= '<tr class="tableHeader info">'+
			'<td><strong>Invoice Number</strong></td>'+
			'<<td><strong>Invoice Date</strong></td>'+
			'<td><strong>GRN Number</strong></td>'+
			'<<td><strong>GRN Date</strong></td>'+
			'<td><strong>Quantity</strong></td>'+
			'<td><strong><a href="#">Accept/Reject</a></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.no_inv+'</center></td>'+
									'<td><center>'+params.dtInv+'</center></td>'+
									'<td><center>'+params.no_grn+'</center></td>'+
									'<td><center>'+params.dtGrn+'</center></td>'+
									'<td><center>'+params.qty_recv+'</center></td>'+
									'<td style="width: 160px;"><strong><a class="alert" href="#" onclick="ControlAcceptRejectGrnDivInventory(\'Modify\',\'GrnInventoryDtaForView\',\'displayGrnInventoryForAccetReject\',\'submitGrnInventoryDtaForAcceptReject\','+params.id_inventory_grn+')"> Accept/Reject </a></strong></td>'+
									'</tr>';
				}
			
				$('.displayGrnInventoryForAccetRejectClass').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="6"><strong><center>Grn is not created yet.</center></strong></td></tr>';
				$('.displayGrnInventoryForAccetRejectClass').html(list);
			}
		
		
	},'json');


}


