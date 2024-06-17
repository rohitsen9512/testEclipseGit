
$(function(){
	
	$( "#menu" ).menu();
	
});
var options = {
		url: "InvoiceScanFile/Model.json",
		getValue: "nm_model",
		template: {
			type: "custom",
			method: function(value, item) {
				return  value ;
			}
		},
		list: {
			maxNumberOfElements: 50,
			match: {
				enabled: true
			}
		},

	};
function dispalyLineItemDynamicallyauction(action,callback){
	
	if(action == 'New')
	{
		var list ='<tr>'+
		'<td colspan="21" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 350px;">Line Item Details</p></td></tr>'+ 
	'<tr class="tableHeader info">'+
		'<td ><strong><center>Auction Category<a href=#></a></center></strong></td>'+
		
		'<td ><strong><center>Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Total Price<a href=#></a></center></strong></td>'+
		'</tr>';
		
		for(var i = 0; i < 10 ; i++)
		{
			list+='<tr>'+
			' <td style="display:none;">'+
				'<select name="asset_consumable'+i+'"  patelUnPrc="'+i+'" class="common-validation" data-valid="mand">'+
						'<option value="ConG">Material Goods</option>'+
						'<option value="CapG">Capital Goods</option>'+
				'</select>'+
			'</td>'+
			'<input type="hidden" name="ConfigurableId'+i+'" value="No">'+	
			
				'<td><input style="width: 120px !important;" type="text" id="dynItemData'+i+'" name="id_prod'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod(event);"></td>'+
			
			'<td style="display:none;"><center><select style="width: 120px !important;" id="assetDivForInvoice'+i+'" name="id_grp'+i+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')">'+
					'<option value="">Select</option></select>'+
			'</center></td>'+
			'<td style="display:none;"><center><select style="width: 120px !important;" id="subGroupDataForInvoice'+i+'" name="id_sgrp'+i+'" patelUnPrc="'+i+'"  class="common-validation dropsgrp" data-valid=""  style="width:80" >'+
					'<option value="">Select</option></select>'+
					'</center></td>'+
				
		 	'<td ><center><input type="text"  name="qty'+i+'" style="text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  onBlur="return CalcTotal(\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+

			'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="text-align: right;" onBlur="return CalcTotal(\'un_prc\')" class=" common-validation"    ></center></td>'+
		     '<td><center><input type="text"  name="tot_prc'+i+'" value="0.0" patelUnPrc="'+i+'" style="text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
				//'
			
			'<input type="hidden" name="count" value="'+i+'">'+		
		'</tr>';
			
		}
		
		
		$('input[name="itemCount"]').val('10');
		$('input[name="grand_tot1"]').val('0.00');
		
		$('#accessoryDetails').html(list);
		DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
			if(status)
			{
				
				DisplayDropDownDataForGroup('M_Asset_Div','groupDrop','CapG',function (status){
					});
			}});
	
	}else{
		var i=$('input[name="itemCount"]').val();
			
		var list='<tr>'+
		'<input type="hidden" name="ConfigurableId'+i+'" value="No">'+	
		
		 	'<td><input  type="text" id="dynItemData'+i+'" name="id_prod'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod(event);"></td>'+
		'<td style="display:none;"><center><select style="width: 120px !important;" id="assetDivForInvoice'+i+'" name="id_grp'+i+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')">'+
		'<option value="">Select</option></select>'+
		'</center></td>'+
		'<td style="display:none;"><center><select style="width: 120px !important;" id="subGroupDataForInvoice'+i+'" name="id_sgrp'+i+'" patelUnPrc="'+i+'"  class="common-validation " data-valid=""  style="width:80" >'+
		'<option value="">Select</option></select>'+
		'</center></td>'+
		
		
	 	'<td><center><input type="text"  name="qty'+i+'" style="text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  onBlur="return CalcTotal(\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+

		'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="text-align: right;" onBlur="return CalcTotal(\'un_prc\')" class=" common-validation"    ></center></td>'+
	
	       '<td><center><input type="text"  name="tot_prc'+i+'" value="0.0" patelUnPrc="'+i+'" style="text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
			//'
	 	'<input type="hidden" name="count" value="'+i+'">'+		
			'</tr>';
		$('input[name="itemCount"]').val(parseInt(i) +1);
	$('#accessoryDetails').append(list);
	DropDownDataDisplay("M_Tax","id_tax"+i,function (status){
		if(status)
		{
			
				
			
		}});
	DisplayDropDownDataForGroup("M_Asset_Div","assetDivForInvoice"+i,"CapG",function (status){
		if(status)
		{
			
		}});
	}
	$("#dynItemData0").easyAutocomplete(options);
	$("#dynItemData1").easyAutocomplete(options);
	$("#dynItemData2").easyAutocomplete(options);
	$("#dynItemData3").easyAutocomplete(options);
	$("#dynItemData4").easyAutocomplete(options);
	$("#dynItemData5").easyAutocomplete(options);
	$("#dynItemData6").easyAutocomplete(options);
	$("#dynItemData7").easyAutocomplete(options);
	$("#dynItemData8").easyAutocomplete(options);
	$("#dynItemData9").easyAutocomplete(options);
	$("#dynItemData10").easyAutocomplete(options);
	$("#dynItemData11").easyAutocomplete(options);
	$("#dynItemData12").easyAutocomplete(options);
	$("#dynItemData13").easyAutocomplete(options);
	$("#dynItemData14").easyAutocomplete(options);
	$("#dynItemData15").easyAutocomplete(options);
	$("#dynItemData16").easyAutocomplete(options);
	$("#dynItemData17").easyAutocomplete(options);
	$("#dynItemData18").easyAutocomplete(options);
	$("#dynItemData19").easyAutocomplete(options);
	$("#dynItemData20").easyAutocomplete(options);
	$("#dynItemData21").easyAutocomplete(options);
	$("#dynItemData22").easyAutocomplete(options);
	$("#dynItemData23").easyAutocomplete(options);
	$("#dynItemData24").easyAutocomplete(options);
	$("#dynItemData25").easyAutocomplete(options);
	$("#dynItemData26").easyAutocomplete(options);
	$("#dynItemData27").easyAutocomplete(options);
	$("#dynItemData28").easyAutocomplete(options);
	$("#dynItemData29").easyAutocomplete(options);
	$("#dynItemData30").easyAutocomplete(options);
	
	
	callback(true);	
	
}



function ControlPurchaseRequest(action,displayContent,createDetails,formName,servletName) {
SessionCheck(function (ses){		
		if(ses)
			{
	
		$(".common-validation").each(function(){
		
			$(".common-validation").removeClass('error');
		});
		$('input[name="dt_req"]').attr("disabled", false);
	
	
	if(action=='Create')
		{
		dispalyLineItemDynamicallyauction("New",function (status){
			if(status)
			{
			}});
			$.post('Auction',{action : 'auto_jwno'},function (r){
				
				$('input[name="auc_code"]').val(r.wo_no);
					
				
			
	},'json');
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
			$('input[name="no_req_val"]').val('new');
			
			$('#requestPrAssetSubGroup').html('<option value="">Select</option>');
			$('#proNameForReqPR').html('<option value="">Select</option>');
			$('#requestPrAssetGroup').html('<option value="">Select</option>');
			
			
			//$('#'+formName)[0].reset();
			$('#'+displayContent).hide();
			$('#'+createDetails).show();
			$('#createAssetPR').show();
			$('#DisplayAssetRP').hide();
			
$.post('P_Request_PR',{action : 'LoginUserDetails'},function (r){
				
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
											if(key == 'remarks')
											{
												$('textarea[name='+key+']').val(r.data[i][key]);
											}
								        }
										 
									}
									$('select[name=req_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected',true);
								
							
					
					}
},'json');
			
		}
	if(action=='Cancel')
		{
			$('#DisplayAssetRP').hide();
			$('#createAssetPR').hide();
			$('#'+createDetails).hide();
			
			$('#'+displayContent).show();
			DisplayPR(servletName,displayContent,createDetails,'','PRForDisplay');
		}
	
	if(action == 'SendForApproval'){
		var id = $('input[name="id"]').val();
		$.post('P_Request_PR',{action : 'SendForApproval',id:id},function (r){
			if(r.data ==1)
			{
				$('#DisplayAssetRP').hide();
				$('#createAssetPR').hide();
				$('#'+createDetails).hide();
				
				$('#'+displayContent).show();
				DisplayPRPreview('P_Request_PR','displayPR','createPR','','PRForDisplay');
				bootbox.alert("Purchase request has been sent for approval.");
			}
		else
			{
				bootbox.alert("Try again.");
			}
			},'json');				
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
			
			DataInsertUpdateForPR("Cancel",displayContent,createDetails,formName,servletName);
			
		}
	
			}
});
}

function DataInsertUpdateForPR(action,displayContent,createDetails,formName,servletName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	t=false;
	t = ValidationForm(formName);
		if(t)
		{
			//$('.req').attr('disabled','disabled');
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
			var x = $('#'+formName).serialize();
			
			$.post(servletName,x,function (r){
				
				if(r.data)
					{
					//$('#createAssetPR').hide();
					$('input[name="no_req_val"]').val(r.no_req);
						bootbox.alert("Auction Successfully added.");
			         
						$( ".createauction" ).trigger( "click" );
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
			//	$('.req').removeAttr('disabled');
				
			},'json');
		
		}
			}
});
}

function DisplayPR(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var dt_frm =$('#dt_frm').val();
			var dt_to =$('#dt_to').val();
			var searchWord = $('input[name="search"').val();
				$.post(servletName,{action : 'Display'   ,searchWord:searchWord},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><center><strong>Auction Date</strong></center></td>'+
					'<td><center><strong>Auction Name</strong></center></td>'+
					'<td><center><strong>Auction Price </strong></center></td>'+
					'<td><center><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></center></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.dt_auc+'</center></td>'+
											'<td><center>'+params.auc_name+'</center></td>'+
											'<td><center>'+params.grand_tot+'</center></td>'+
											'<td style="width:200px;"><strong><center><a class="alert" href="#" onclick="EditPR(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_auc+')"> Modify </a><a class="alert" href="#" onclick="DeletePR(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_auc+')"> Delete</a></center></strong></td>'+
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
});
}

