
function ControlInventoryStoreForAvailableItem(Action)
{	
	if(Action == 'Next')
	{
		
		var t = true;
		var id_grp = $('#assetDivForAvailableItem').val();
		var id_sgrp = $('#subassetDivDataForAvailableItem').val();
		$('#subassetDivDataForAvailableItem').removeClass('error');
		$('#assetDivForAvailableItem').removeClass('error');
		
		if(id_grp == '')
			{
				bootbox.alert(" Mandatory field.");
				$('#assetDivForAvailableItem').focus();
				$('#assetDivForAvailableItem').addClass('error');
				t=false;
			}
		else if(id_sgrp == '')
		{
			bootbox.alert(" Mandatory field.");
			$('#subassetDivDataForAvailableItem').focus();
			$('#subassetDivDataForAvailableItem').addClass('error');
			t=false;
		}
		if(t)
			{
				DisplayDropDownData("M_Emp_User","empIdForAvailableItem");
				EditConsumableInventoryForAvailableItem('I_Available_Item',id_grp,id_sgrp);
				
			}
			
		
		
	}
	if(Action == 'Take')
	{
		var t = false;
		t = ValidationForm("SubmitAvailableItems");
	if(t)
	{
		t=CheckTakenQty();
	}
		if(t)
			{
			var x = $('#SubmitAvailableItems').serialize();
			var id_grp = $('#assetDivForAvailableItem').val();
			var id_sgrp = $('#subassetDivDataForAvailableItem').val();
			
				$.post('I_Available_Item',x,function (r)
								{
							
									if(r.data)
										{
											bootbox.alert("You have successfully taken.");
											EditConsumableInventoryForAvailableItem('I_Available_Item',id_grp,id_sgrp);
											
										}
										else
											{
												t=0;
												bootbox.alert(" Please try again.");
											}
									
								},'json');
						
						}
			}
}

function CheckTakenQty()
{
	var t=true;
	$('input[name="qty_taken"]').removeClass('error');
	var avilableQty = parseInt($('input[name="qty_recv"]').val());
	var takenqty = parseInt($('input[name="qty_taken"]').val());
	
	if(takenqty > avilableQty)
		{
		t=false;
			$('input[name="qty_taken"]').focus();
			$('input[name="qty_taken"]').addClass('error');
			alert("You can not take more than avilable items.");
			
		}
	return t;
}

function EditConsumableInventoryForAvailableItem(servletName,id_grp,id_sgrp)
{
	
	
		$.post(servletName,{action : 'Edit',id_grp : id_grp , id_sgrp : id_sgrp},function (r){
			if(r.data == 'data')
			{
				bootbox.alert("No items are available. Please select different group and sub group.");
			}
			else if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
							for (var key in r.data[i])
					        {
								$('input[name='+key+']').val(r.data[i][key]);
							
					        }
							 
						}
						$('input[name="qty_taken"]').val('');
						$('input[name="id_grp"]').val(r.data[0].id_grp);
						$('input[name="id_sgrp"]').val(r.data[0].id_sgrp);
						$('input[name="id_inventory_store"]').val(r.data[0].id_inventory_store);
						
						$('#selectGroupSubGroupForAvailableItem').hide();
						$('#AvailableItemsForTakeItem').show();
						}
				
				
				
		},'json');
		
}

function AddInputValueForInventoryAddToStore(id_inventory ,id_inventory_m ,qty)
{
	$.post('I_Available_Items',{action : 'Edit',id_inventory : id_inventory , id_inventory_m : id_inventory_m},function (r)
			{
		
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						
							for (var key in r.data[i])
								{
									$('input[name='+key+']').val(r.data[i][key]);
								}
					       	
						
						$('input[name="id_sgrp"]').val(r.data[0].id_sgrp);
						$('input[name="id_inventory"]').val(id_inventory);
						$('input[name="id_inventory_m"]').val(id_inventory_m);
						$('input[name="qty"]').val(qty);
					}
					else
							bootbox.alert("Try again.");
				
			},'json');
		
	}
