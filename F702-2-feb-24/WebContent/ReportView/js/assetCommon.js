$(function(){
	
	$( "#menu" ).menu();
	
});
 

function ControlAsset(action,displayContent,createDetails,formName,servletName) {
	
	
		$(".form-control").each(function(){
		
			$(".form-control").removeClass('error');
		});
		
	
	
	if(action=='Create')
		{
		$('.readbaledata').each(function (){
			
			$(this).removeAttr('readonly', 'readonly');
			if($(this).is("select"))
				{
					$(this).attr("disabled", false);
				}
			
		});
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
		$('input[name="action"]').val('Save');
			$('input[name="id"]').val("0");
			$('#'+formName)[0].reset();
			$('#'+displayContent).hide();
			$('#'+createDetails).show();
			$('#createAccessory').show();
			$('#displayAccessory').hide();
			
		}
	if(action=='Cancel')
		{
			$('#displayAccessory').hide();
			$('#createAccessory').hide();
			$('#'+createDetails).hide();
			
			$('#'+displayContent).show();
			DisplayInvoice(servletName,displayContent,createDetails,'','invoiceForDisplay');
		}
	if(action=='Save' || action=='Update')
		{
			if(action == 'Save')
				{
				$('button[name="save1"]').removeClass('hideButton');
				$('input[name="action"]').val('Save');
				}
			else
				{
					$('input[name="action"]').val('Update');
				}
			
			DataInsertUpdateForAsset("Cancel",displayContent,createDetails,formName,servletName);
			
		}
	
	
}


function DataInsertUpdateForAsset(action,displayContent,createDetails,formName,servletName)
{
	t=false;
	
	t = ValidationForm(formName);
	
	if(t)
		{
			t=false;
			var val = $('#bdId').val();
			
			if(val == 'Yes')
				{
					t = ShowRowColumnValidation('Yes','common-hideShow');
				}
			else
				{
				t=true;
				}
			
		}
	
		if(t)
		{
		
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
			var x = $('#'+formName).serialize();
			
			$.post(servletName,x,function (r){
				
				if(r.data > 0)
					{
					$('#createAccessory').hide();
					
						$(".message").show();
			            $(".message").fadeOut(2500);
			            
			            //DisplayInvoice(servletName,displayContent,createDetails,r.data,'invoiceForDisplay');
			            DisplayAccessory(servletName,displayContent,createDetails,r.data,'accessoryForDisplay');
						
			            $('#'+createDetails).show();
						$('#displayAccessory').show();
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
			},'json');
		
		}
}


function EditAsset(servletName,displayContent,createDetails,id)
{
	
	DisplayAccessory('A_Invoice','displayInvoice','createInvoice',id,'accessoryForDisplay');
	
	$('.readbaledata').each(function (){
		
		$(this).attr('readonly', 'readonly');
		if($(this).is("select"))
		{
			$(this).attr("disabled", true);
		}
		
	});
	
	$('button[name="save"]').addClass('hideButton');
	$('button[name="save1"]').addClass('hideButton');
	$('button[name="update"]').removeClass('hideButton');
	$('#'+displayContent).hide();
	$('#'+createDetails).show();
	$('#displayAccessory').show();
	var t=0;
		$.post(servletName,{action : 'Edit',id : id},function (r){
				
				if(r.data)
					{
					
						for(var i = 0; i < r.data.length ; i++)
						{
							
							
							for (var key in r.data[i])
					        {
								
								if($('select[name='+key+']').is("select") && r.data[i][key] != '')
								{
									
									$('option[value=' + r.data[i][key] + ']').attr('selected',true);
									
								}
								else
								{
									$('input[name='+key+']').val(r.data[i][key]);
								}
								if(key == 'bd' && r.data[i][key] =='Yes')
								{
									t=1;
									
								}
								
					         
					        }
							 
						}
						
						$('input[name="action"]').val("Update");
						$('input[name="id"]').val(id);
						
						}
				else
					{
						bootbox.alert("Try again.");
					}
				if(t == 1)
				{
					
					ShowRowColumn('Yes' , 'hideRowCol');
				}	
				
		},'json');
		
}

function DeleteInvoice(servletName,displayContent,createDetails,id)
{
		bootbox.confirm("Are you sure?", function(result) {
		if(result)
		{
		
			$.post(servletName,{action : 'Delete',id : id},function (r){
				
				if(r.data == 1)
				{
					DisplayInvoice(servletName,displayContent,createDetails,'','invoiceForDisplay');
				}
				if(r.data == 2)
				{
					bootbox.confirm("If you delete this , then all aseet will be deleted related to this invoice.", function(result1) {
						if(result1)
						{
							$.post(servletName,{action : 'Delete',id : id , id_inv_m : '0'},function (r){
								
								if(r.data == 1)
									{
										DisplayInvoice(servletName,displayContent,createDetails,'','invoiceForDisplay');
									}
							},'json');
						}
						
					});
				}
				
			},'json');
		}
	}); 
}


function DeleteAsset(servletName,displayContent,createDetails,id)
{
		bootbox.confirm("Are you sure?", function(result) {
		if(result)
		{
			id_inv_m = $('input[name="id"]').val();
			$.post(servletName,{action : 'DeleteAsset',id : id , id_inv_m : id_inv_m},function (r){
				
				if(r.data == 1)
				{
					id_m=$('#id').val();
					DisplayAccessory(servletName,displayContent,createDetails,id_m,'accessoryForDisplay');
				}
			if(r.data == 2)
				{
					bootbox.confirm("This is last record of this Invoice if delete this then invoice also deleted.", function(result1) {
						if(result1)
						{
							$.post(servletName,{action : 'DeleteAsset',id : id , id_inv_m : '0'},function (r){
								
								if(r.data == 1)
									{
										ControlAsset('Cancel',displayContent,createDetails,'',servletName);
									}
							},'json');
						}
						
					});
				}
				
				
			},'json');
		}
	}); 
}

function CreateAccessory(action,displayContent,createDetails,no_asset)
{
	
		if(action=='Create')
		{
			$('button[name="update"]').addClass('hideButton');
			$('button[name="save"]').removeClass('hideButton');
			
			$('.resetAcc').each (function(){
				$(this).val('');
				});
			$('#'+createDetails).show();
		}
		else if(action=='Cancel')
		{
			$('#'+createDetails).hide();
			$('#'+displayContent).show();
		}
		if(action=='Modify')
		{
$.post('A_Invoice',{action : 'Edit',invoiceId : no_asset},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
							
							
							for (var key in r.data[i])
					        {
								if($('select[name='+key+']').is("select"))
								{
									$('option[value=' + r.data[i][key] + ']').attr('selected',true);
									
								}
							else
								{
									$('input[name='+key+']').val(r.data[i][key]);
								}
					        }
							 
						}
						
						$('input[name="invoiceId"]').val(no_asset);
						$('input[name="id"]').val(r.data[0].id_inv_m);
						
						}
				else
					{
						bootbox.alert("Try again.");
					}
				
				
		},'json');
			$('button[name="update"]').removeClass('hideButton');
			$('button[name="save"]').addClass('hideButton');
			$('#'+createDetails).show();
		}
}

function DisplayAccessory(servletName,displayContent,createDetails,no_inv,DisplayTableClass)
{
	
	$.post(servletName,{action : 'Display' , no_inv : no_inv},function (r){
		
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Asset/Accessory</strong></td>'+
								'<<td><strong>Quantity</strong></td>'+
								'<<td><strong>Manufacturer</strong></td>'+
								'<td><strong>Unit Price</strong></td>'+
								'<td><strong>Total Unit Price</strong></td>'+
								'<<td><strong>Total  Price</strong></td>'+
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_acc_asset+'</center></td>'+
									'<td><center>'+params.qty+'</center></td>'+
									'<td><center>'+params.mfr+'</center></td>'+
									'<td><center>'+params.un_prc+'</center></td>'+
									'<td><center>'+params.tt_un_prc+'</center></td>'+
									'<td><center>'+params.tt+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="CreateAccessory(\'Modify\',\'displayAccessory\',\'createAccessory\','+params.id_inv+')"> Modify </a><a class="alert" href="#" onclick="DeleteAsset(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_inv+')"> Delete</a></strong></td>'+
									'</tr>';
				}
			
				$('input[name="id"]').val(r.data[0].id_inv_m);
				$('.accessoryForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');


}

function DisplayInvoice(servletName,displayContent,createDetails,no_inv,DisplayTableClass)
{

			var dt_frm =$('#dt_frm').val();
			var dt_to =$('#dt_to').val();
			
				$.post(servletName,{action : 'Display' , no_inv : no_inv,dt_frm:dt_frm,dt_to:dt_to},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Invoice Number</strong></td>'+
					'<<td><strong>Invoice Date</strong></td>'+
					'<td><strong>PO Number</strong></td>'+
					'<<td><strong>PO Date</strong></td>'+
					
					'<<td><strong>Vendor</strong></td>'+
					'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_inv+'</center></td>'+
											'<td><center>'+params.dt_inv+'</center></td>'+
											'<td><center>'+params.no_po+'</center></td>'+
											'<td><center>'+params.dt_po+'</center></td>'+
											
											'<td><center>'+params.nm_ven+'</center></td>'+
											'<td><strong><a class="alert" href="#" onclick="EditAsset(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_inv_m+')"> Modify </a><a class="alert" href="#" onclick="DeleteInvoice(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_inv_m+')"> Delete</a></strong></td>'+
											'</tr>';
						}
					
					
						$('.'+DisplayTableClass).html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="6"><strong><center>No record(s) is available..</center></strong></td></tr>';
						$('.'+DisplayTableClass).html(list);
					}
				
			},'json');
		

}

