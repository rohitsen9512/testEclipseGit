
function ControlInvoiceThroughPO(action,displayContent,createDetails,formName,servletName) {
SessionCheck(function (ses){		
		if(ses)
			{
	
		$(".common-validation").each(function(){
		
			$(".common-validation").removeClass('error');
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
			DisplayPurchaseOrder(servletName,displayContent,createDetails,'','invoiceForDisplay');
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
			
			DataInsertUpdateForPurchaseOrder("Cancel",displayContent,createDetails,formName,servletName);
			
		}
	
			}
});
}


function DataInsertUpdateForPurchaseOrder(action,displayContent,createDetails,formName,servletName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	t=false;
	t = ValidationForm(formName);
	if(t)
		{
			t=false;
			var val = $('#bdIdForAddTostore').val();
			
			if(val == 'Yes')
				{
				
					t = ShowRowColumnValidation('common-hideShow');
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
				
				if(r.data1 == 1)
					{
					$('#createAccessory').hide();
					bootbox.alert('Invoice updated successfully');
			            DisplayAccessoryForPO(servletName,displayContent,createDetails,r.data,'accessoryForDisplay');
						
			            $('#'+createDetails).show();
						$('#displayAccessory').show();
					}
				else
					{
						bootbox.alert('Invoice updated successfully');
						$( ".addInvoicePOForInventory" ).trigger( "click" );
					}
				
			},'json');
	
		
		}
			}
});
}


function EditAssetPO(servletName,displayContent,createDetails,id)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			$('#InvoiceForPurchaseOrderHideShow').hide();
	//DisplayAccessoryForPO('Invoice_Through_PO','displayInvoice','createInvoice',id,'accessoryForDisplay');
	
	$('.readbaledata').each(function (){
		
		$(this).attr('readonly', 'readonly');
		if($(this).is("select"))
		{
			$(this).attr("disabled", true);
		}
		
	});
	
	$('button[name="save"]').addClass('hideButton');
	$('button[name="save1"]').addClass('hideButton');
	//$('button[name="update"]').removeClass('hideButton');
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
									
									$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									
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
						
						$('input[name="action"]').val("Save");
						$('input[name="id"]').val(id);
						$('input[name="id_loc"]').val(r.data[0].id_loc);
						$('input[name="id_subl"]').val(r.data[0].id_subl);
						$('input[name="nm_acc_asset"]').val(r.data[0].nm_acc_asset);
						$('input[name="id_ven"]').val(r.data[0].id_ven);
						$('input[name="id_curr"]').val(r.data[0].id_curr);
						
						
						}
				else
					{
						bootbox.alert("Try again.");
					}
				if(t == 1)
				{
					
					ShowRowColumn('Yes' , 'hideRowCol');
				}	
				DisplayAccessoryForPO('Invoice_Through_PO_For_Inventory','displayInvoice','createInvoice',id,'accessoryForDisplay');
					
					
		},'json');
			}
	
});
}



function CreateAccessoryForPO(action,displayContent,createDetails,id_po_asst,id_grp)
{
SessionCheck(function (ses){
		
		if(ses)
			{
		 if(action=='Cancel')
		{
			$('#'+createDetails).hide();
			$('#'+displayContent).show();
		}
		if(action=='Modify')
		{
			$('#InvoiceForPurchaseOrderHideShow').show();
SubDropDownDataDisplay(id_grp,'subGroupDataForInvoice','M_Subasset_Div',function (status){
	if(status)
	{
	
$.post('Invoice_Through_PO_For_Inventory',{action : 'EditAsset',id_po_asst : id_po_asst},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
							
							
							for (var key in r.data[i])
					        {
								if($('select[name='+key+']').is("select"))
								{
									$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									
								}
							else
								{
									$('input[name='+key+']').val(r.data[i][key]);
								}
					        }
							 
						}
						
						$('input[name="id_po_asst"]').val(id_po_asst);
						$('input[name="id"]').val(r.data[0].id_po);
						$('input[name="typ_po"]').val(r.data[0].typ_po);
						$('input[name="qty"]').val('');
						$('input[name="tt_un_prc"]').val('');
						$('input[name="tt"]').val('');
						
						}
				else
					{
						bootbox.alert("Try again.");
					}
				
				
		},'json');
			//$('button[name="update"]').removeClass('hideButton');
			$('button[name="save"]').removeClass('hideButton');
			$('#'+createDetails).show();
	}});
		}
	
			}
		
});
}

function DisplayAccessoryForPO(servletName,displayContent,createDetails,id,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	$.post(servletName,{action : 'DisplayAsset' , id : id},function (r){
		
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Item Name</strong></td>'+
								'<td><strong>Total Quantity</strong></td>'+
								'<td><strong>Remaining Quantity</strong></td>'+
								'<td><strong>Manufacturer</strong></td>'+
								'<td><strong>Unit Price</strong></td>'+
								'<td><strong>Total  Price</strong></td>'+
								'<td style="width: 175px;"><strong><a href="#">Create Invoice </a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_prod+'</center></td>'+
									'<td><center>'+params.qty+'</center></td>'+
									'<td><center>'+params.rem_qty+'</center></td>'+
									'<td><center>'+params.mfr+'</center></td>'+
									'<td><center>'+params.un_prc+'</center></td>'+
									'<td><center>'+params.tot_prc+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="CreateAccessoryForPO(\'Modify\',\'displayAccessory\',\'createAccessory\','+params.id_po_asst+','+params.id_assetdiv+')"> Create Invoice </a></strong></td>'+
									'</tr>';
				}
			
				$('input[name="id"]').val(id);
				$('input[name="rem_qty"]').val(r.data[0].rem_qty);
				$('.accessoryForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

			}
});
}

function DisplayPurchaseOrder(servletName,displayContent,createDetails,no_inv,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var dt_frm =$('#dt_frm').val();
			var dt_to =$('#dt_to').val();
			
				$.post(servletName,{action : 'Display' , no_inv : no_inv,dt_frm:dt_frm,dt_to:dt_to},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Quotation Number</strong></td>'+
					'<td><strong>Quotation Date</strong></td>'+
					'<td><strong>PO Number</strong></td>'+
					'<td><strong>PO Date</strong></td>'+
					
					'<td><strong>Vendor</strong></td>'+
					'<td style="width: 170px;"><strong><a href="#">Update Invoice </a></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_quot+'</center></td>'+
											'<td><center>'+params.dtQuot+'</center></td>'+
											'<td><center>'+params.no_po+'</center></td>'+
											'<td><center>'+params.dtPo+'</center></td>'+
											
											'<td><center>'+params.nm_ven+'</center></td>'+
											'<td><strong><a class="alert" href="#" onclick="EditAssetPO(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_po+')"> Update Invoice </a></strong></td>'+
											'</tr>';
						}
					
					
						$('.'+DisplayTableClass).html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
						$('.'+DisplayTableClass).html(list);
					}
				
			},'json');
		
			}
});
}


$( ".invoiceThroughDatepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      onSelect: function(selected,evnt) {
	    	  $(this).removeClass('error');
	    	  var dt_po=$('input[name="dt_po"]').val();
	    	  var dt_po1=$('input[name="dt_po"]').val();
	    	  var dt_inv = $(this).val();
	    	  
	    	  var temp_strt = dt_po.split("/");
			  var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
					
				var temp_end = dt_inv.split("/");
				var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
					
				dt_po = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
				dt_inv = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
	    	  
	    		  if(dt_po > dt_inv)
	    			  {
	    			  	alert('Inoice date should be greater or equal to P.O date : '+dt_po1);
	    			  	$(this).focus();
	    			  	$(this).val('');
	    			  	$(this).addClass('error');
	    			  	exit(0);
	    			  }
	    		  
	    		  
	    	  
	      }
	    });

