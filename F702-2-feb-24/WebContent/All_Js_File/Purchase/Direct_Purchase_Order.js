
function ControlDirectPurchaseRequest(action,displayContent,createDetails,formName,servletName) {
SessionCheck(function (ses){		
		if(ses)
			{
	
		$(".common-validation").each(function(){
		
			$(".common-validation").removeClass('error');
		});
		$('.datepicForDissable').each(function (){
			$(this).attr("disabled", false);
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
		$('#requestPrAssetSubGroup').html('<option value="">Select</option>');
		$('#proNameForReqPR').html('<option value="">Select</option>');
		$('#requestPrAssetGroup').html('<option value="">Select</option>');
		
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
		$('input[name="action"]').val('Save');
			$('input[name="id"]').val("0");
			$('input[name="id_po"]').val('new');
			
			$('#'+formName)[0].reset();
			$('#'+displayContent).hide();
			$('#'+createDetails).show();
			$('#createDirectAssetPO').show();
			$('#DisplayAssetDPORequest').hide();
			$.post('Direct_Purchase_Order',{action : 'DynGenPOId'},function (r){
				
				if(r.data)
					{
						$('input[name="no_po1"]').val(r.data);
					}
			
			},'json');
			
			
		}
	if(action=='Cancel')
		{
			$('#createDirectAssetPO').hide();
			
			$('#'+createDetails).hide();
			
			$('#'+displayContent).show();
			DisplayDirectPO(servletName,displayContent,createDetails,'','PRForDisplay');
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
			
			DataInsertUpdateForDPO(action,displayContent,createDetails,formName,servletName);
			
		}
	
			}
});
}

function TaxCalculationForDPO(id_tax)
{
var t=true;
	if(id_tax.value != undefined)
		{
			id_tax =id_tax.value;
			if(id_tax == '0')
				{
				t=false;
				$('input[name="tax_prc"]').val('0');
				}
		}
		
	if(t)
		{
	$.post('M_Tax',{action : 'DropDownResult',id:id_tax},function (r){
		
		if(r.data.length > 0 )
			{
			 var un_prc = $('input[name="un_prc"]').val();
			 var qty = $('input[name="qty"]').val();
			 var percent = r.data[0].per_tax;
			 var tot_prc = parseFloat(un_prc)*parseFloat((qty));
			 
			 var gr_tot= +tot_prc + (parseFloat(tot_prc)*parseFloat((percent)))/100;

			 if(gr_tot == undefined || gr_tot == '')
				 {
					 $('input[name="gr_tot1"]').val('0');
					 $('input[name="tax_percent"]').val(percent);
					 $('input[name="tax_val"]').val('0');
				 }
			 else
				 {
					 $('input[name="tax_percent"]').val(percent);
					 $('input[name="gr_tot1"]').val(gr_tot.toFixed(2));
					$('input[name="tax_val').val(((parseFloat(tot_prc)*parseFloat((percent)))/100).toFixed(2));
				 }
				
			}
		
		
	},'json');
	
		}
	else
		{
		var un_prc = $('input[name="un_prc"]').val();
		 var qty = $('input[name="qty"]').val();
		 
		 var tot_prc = parseFloat(un_prc)*parseFloat((qty));
			 $('input[name="tot_prc"]').val(tot_prc);
			 $('input[name="tax_per"]').val('0');
		}
}

function onChangeProductName(id)
{
	$.post('M_Prod_Cart',{action : 'Edit',id : id.value},function (r){
		
		if(r.data)
			{
			
				for(var i = 0; i < r.data.length ; i++)
				{
					for (var key in r.data[i])
			        {
							$('input[name='+key+']').val(r.data[i][key]);
			         
			        }
				}
				
				}
		else
			{
				bootbox.alert("Try again.");
			}
	},'json');


}


function onChangeAssetConsumable(name,IdNameForHtml)
{
	if(name.value == 'Asset')
		{
		DisplayDropDownDataForGroup('M_Asset_Div',IdNameForHtml,name.value,function (status){
			if(status)
			{
				
			}});
		}
	else if(name.value == 'Consumable')
		{
		DisplayDropDownDataForGroup('M_Asset_Div',IdNameForHtml,name.value,function (status){
			if(status)
			{
				
			}});
		}
	else
		{
			$('#'+IdNameForHtml).html('<option value=""> Select</option>');
			$('#'+dropDownId).focus();
		}
}



function DataInsertUpdateForDPO(action,displayContent,createDetails,formName,servletName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	t=false;
	t = ValidationForm(formName);
		if(t)
		{
			$('.dpo').attr('disabled','disabled');
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
			var x = $('#'+formName).serialize();
			var id_cc = $('select[name="id_cc"]').val();
			$.post(servletName,x,function (r){
				
				if(r.data == 1)
					{
					$('#createDirectAssetPO').hide();
					$('input[name="id_po"]').val(r.id_po);
					$('input[name="id_cc"]').val(id_cc);
					$('input[name="no_po1"]').val(r.no_po);
					bootbox.alert(action +' successfully with po no : ' +r.no_po);
			            DisplayAssetDPORequest(servletName,displayContent,createDetails,r.id_po,'assetPRForDisplay');
			            $('#'+createDetails).show();
						$('#DisplayAssetDPORequest').show();
						
						$('.readbaledata').each(function (){
							
							$(this).attr('readonly', 'readonly');
							if($(this).is("select"))
							{
								$(this).attr("disabled", true);
							}
							
						});
					}
				else
					{
						bootbox.alert("Try again.");
					}
				$('.dpo').removeAttr('disabled');
				
			},'json');
		
		}
			}
});
}

function ModifyDirectPO(servletName,displayContent,createDetails,id_po,id_dept,id_cc,id_section){
	SubDropDownDataDisplay(id_dept,'requestedPrCC','M_Cost_Center',function (status){
		if(status)
		{
			SubDropDownDataDisplay(id_cc,'sectionDataForRequest','M_Section',function (status){
				if(status)
				{
					DisplaySubDropDownData(id_section,'orgLocDataForPO','M_ORG_LOCATION',function (status){
						if(status)
						{
							EditDPORequest(servletName,displayContent,createDetails,id_po);
						}});
				}});
		}});
}

function EditDPORequest(servletName,displayContent,createDetails,id)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	
	
	
	$('.readbaledata').each(function (){
		
		$(this).attr('readonly', 'readonly');
		if($(this).is("select"))
		{
			$(this).attr("disabled", true);
		}
		
	});
$('.datepicForDissable').each(function (){
			$(this).attr("disabled", true);
	});
	
	$('button[name="save"]').addClass('hideButton');
	$('button[name="save1"]').addClass('hideButton');
	$('button[name="update"]').removeClass('hideButton');
	$('#'+displayContent).hide();
	$('#'+createDetails).show();
	$('#DisplayAssetDPORequest').show();
	var t=0;
	DisplayDropDownData("M_Floor","FloorDataForDPO",function (status){
		if(status)
			{
			DropDownDataDisplay("M_Cost_Center","ccDataForDPO",function (status){
				if(status)
					{
		$.post('Direct_Purchase_Order',{action : 'Edit',id : id},function (r){
				
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
								if(key == 'po_t_c')
								{
									$('textarea[name='+key+']').val(r.data[i][key]);
								}
					        }
							 
						}
						list='<td colspan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 150px;">PO Details<span style="margin-left: 150px;">P.O NO : &nbsp;&nbsp;'+r.data[0].no_po+'</span></p></td>'+
						'<input type="hidden" name="no_po" value="'+r.data[0].no_po+'">';
					$('#DynPurchaseOrderId').html	(list);
						$('input[name="action"]').val("Update");
						$('input[name="id"]').val(id);
						$('input[name="id_po"]').val(r.data[0].id_po);
						$('input[name="id_cc"]').val(r.data[0].id_cc);
						$('input[name="no_po1"]').val(r.data[0].no_po);
						
						$('input[name="dt_po"]').val(r.data[0].dtpo);
						$('input[name="dt_quot"]').val(r.data[0].dtquot);
						$('input[name="dt_sd_amt"]').val(r.data[0].dtsdamt);
						$('input[name="dt_val_po"]').val(r.data[0].dtvalpo);
						
						}
				else
					{
						bootbox.alert("Try again.");
					}
				
				
				DisplayAssetDPORequest('Direct_Purchase_Order','displayPR','createPR',id,'assetPRForDisplay');	
		},'json');
			}});
			}});
		
		}
	
});
}

function DeleteDPO(servletName,displayContent,createDetails,id)
{
	
	SessionCheck(function (ses){
		
		if(ses)
			{
	
				bootbox.confirm("Are you sure?", function(result) {
				if(result)
				{
				
					$.post(servletName,{action : 'Delete',id : id},function (r){
						
						if(r.data == 1)
						{
							$( ".directPurchaseOrder" ).trigger( "click" );	
						}
						
						
					},'json');
				}
			}); 
			}
		
	});
}


function DeleteDirectAssetPO(servletName,displayContent,createDetails,id)
{
SessionCheck(function (ses){
		
		if(ses)
			{	
	bootbox.confirm("Are you sure?", function(result) {
		if(result)
		{
			id_po=$('#id_po').val();
			$.post(servletName,{action : 'DeleteDirectAssetPO',id : id ,id_po:id_po},function (r){
				
				if(r.data == 1)
				{
					id_po=$('#id_po').val();
					DisplayAssetDPORequest(servletName,displayContent,createDetails,id_po,'assetPRForDisplay');
				}
			if(r.data == 2)
				{
					bootbox.confirm("This is last record of this PO if you delete this then PO also will be deleted.", function(result1) {
						if(result1)
						{
							$.post(servletName,{action : 'DeleteDirectAssetPO',id : id , id_po : '0'},function (r){
								
								if(r.data == 1)
									{
										$( ".directPurchaseOrder" ).trigger( "click" );									
									}
								
							},'json');
						}
						
					});
				}
				
				
			},'json');
		}
	}); 
			}
});
}

function CreateAssetDPORequest(action,displayContent,createDetails,no_asset,id_grp)
{
SessionCheck(function (ses){
		
		if(ses)
			{
		if(action=='Create')
		{
			$('button[name="update"]').addClass('hideButton');
			$('button[name="save"]').removeClass('hideButton');
			$('#requestPrAssetSubGroup').html('<option value="">Select</option>');
			$('#proNameForReqPR').html('<option value="">Select</option>');
			$('#requestPrAssetGroup').html('<option value="">Select</option>');
			
			$('.resetAcc').each (function(){
				$(this).val('');
				});
			$('.resetAcc1').each (function(){
				$(this).val('0.0');
				});
			$('#'+displayContent).hide();
			$('#'+createDetails).show();
			
		}
		else if(action=='Cancel')
		{
			$('#'+createDetails).hide();
			$('#'+displayContent).show();
		}
		if(action=='Modify')
		{
SubDropDownDataDisplay(id_grp,'subGroupDataForInvoice','M_Subasset_Div',function (status){
	if(status)
	{
	
$.post('Direct_Purchase_Order',{action : 'Edit',invoiceId : no_asset},function (r){
				
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
	}});
		}
	
			}
		
});
}

function DisplayAssetDPORequest(servletName,displayContent,createDetails,id,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	$.post(servletName,{action : 'DisplayAssetForPO' , id : id},function (r){
		
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Asset Name</strong></td>'+
								'<td><strong>Quantity</strong></td>'+
								'<td><strong>Unit Price</strong></td>'+
								'<td><strong>Total  Price</strong></td>'+
								'<td style="width: 105px;"><strong><a href="#">Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td>'+params.nm_prod+'</td>'+
									'<td>'+params.qty+'</td>'+
									'<td>'+params.un_prc+'</td>'+
									'<td>'+params.tot_prc+'</td>'+
									'<td><a class="alert" href="#" onclick="DeleteDirectAssetPO(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_po_asst+')"> Delete</a></strong></td>'+
									'</tr>';
				}
			
				$('input[name="id"]').val(r.data[0].id);
				$('.'+DisplayTableClass).html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

			}
});
}

function DisplayDirectPO(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var dt_frm =$('#dt_frm').val();
			var dt_to =$('#dt_to').val();
			var searchWord = $('input[name="search"').val();
				$.post(servletName,{action : 'Display' , no_req_val : no_req_val,dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><center><strong>PO Number</strong></center></td>'+
					'<td><center><strong>Quotation Number</strong></center></td>'+
					'<td><center><strong>Vendor Name </strong></center></td>'+
					'<td><center><strong>Raised By</strong></center></td>'+
					'<td><center><strong>Raised Date</strong></center></td>'+
					'<td style="width: 230px;"><strong><a href="#">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Add Asset &nbsp;&nbsp;&nbsp;&nbsp;</a><a href="#">/ &nbsp;&nbsp;&nbsp;&nbsp;Delete</a></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_po+'</center></td>'+
											'<td><center>'+params.no_quot+'</center></td>'+
											'<td><center>'+params.nm_ven+'</center></td>'+
											'<td><center>'+params.nm_emp+'</center></td>'+
											'<td><center>'+params.dt_po+'</center></td>'+
											'<td style="width:200px;"><strong><a class="alert" href="#" onclick="ModifyDirectPO(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_po+','+params.id_dept+','+params.id_cc+','+params.id_section+')"> Add Asset </a><a class="alert" href="#" onclick="DeleteDPO(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_po+')"> Delete</a></strong></td>'+
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
