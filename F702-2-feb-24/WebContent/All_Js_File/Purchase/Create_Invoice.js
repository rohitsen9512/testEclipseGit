function SendMailOfInvoice()
{
SessionCheck(function (ses){
		
		if(ses)
			{
		var t=false;
		t=ValidationForm('submitInvoice');	
			if(t)
		{		
			var x = $('#submitInvoice').serialize();
					$.post('Create_Invoice',x,function (r){
				
				if(r.data){
					bootbox.alert("Mail has been Sent.");
							$( ".createinvoice" ).trigger( "click" );
				}
				else{
					bootbox.alert("Something Went Wrong");
									
					
				}
				
					
					
				},'json');
		
		}
			}
});
}
function poDropdown(servletName,dropDownId,callback)
{
	var t =false;
	var list='';
	$.post(servletName,{action : 'DropDownResult'},function (r){
		
		if(r.data)
			{
			t=true;
			if(dropDownId != 'assetDivForRequestQuotation')
				 list= '<option value=""> Select</option>';
			
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
		callback(t);
	},'json');

}
function DisplayInvoicePO(servletName,displayContent,createDetails,no_inv,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var dt_frm =$('#dt_frm').val();
			var dt_to =$('#dt_to').val();
			
				$.post(servletName,{action : 'Display' , no_inv : no_inv,dt_frm:dt_frm,dt_to:dt_to},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong><center>Invoice Number</center></strong></td>'+
					'<td><strong><center>Invoice Date</center></strong></td>'+
				
					'<td><strong><center>Vendor</center></strong></td>'+
			    '<td><center><strong><a href="#">Modify </a>  </strong></center></td>'+	
                 '</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_inv+'</center></td>'+
											'<td><center>'+params.dtInv+'</center></td>'+
											
											'<td><center>'+params.nm_ven+'</center></td>'+
									    	'<td style="width:200px;"><strong><center><a class="alert" href="#" onclick="EditInvoicePO(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\',\''+params.id_inv_m+'\')"> Modify </a></center></strong></td>'+
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
function EditInvoicePO(servletName,displayContent,createDetails,id_inv_m){
	var id_ven=0;
	$.post(servletName,{action : 'Edit',id : id_inv_m},function (r){
		
		if(r.data.length > 0)
			{
			id_ven=r.data[0].id_ven;
			
			for(var i = 0; i < r.data.length ; i++)
				
				for (var key in r.data[i])
					{
					
						if($('select[name='+key+']').is("select") && r.data[i][key] != '')
							
							$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
						else
							$('input[name='+key+']').val(r.data[i][key]);
						
					}
			
				
				$('button[name="save"]').addClass('hideButton');
				$('button[name="update"]').removeClass('hideButton');
				$('#'+displayContent).hide();
				$('#'+createDetails).show();
				//$('#DisplayAssetRP').show();
			
				$('#createAssetPR').show();
				//$('#DisplayAssetRP').hide();
				$('input[name="dt_inv"]').val(r.data[0].dtinv);
				$('input[name="id"]').val(id_inv_m);
			

			}
		/*else
			bootbox.alert("Try again.");*/
		DisplayLineItemDynamicallyForEditLineItempo(servletName,displayContent,createDetails,id_inv_m,id_ven);
	},'json');
	
	

}
function DisplayLineItemDynamicallyForEditLineItempo(servletName,displayContent,createDetails,id_inv_m,id_ven)
{
$.post(servletName,{action : 'DisplayLineItem',id:id_inv_m},function (r){
	
	if(r.data.length > 0)
		{
			for(var i=0;i<r.data.length;i++)
			{
				for (var key in r.data[i])
		        {
					if($('select[name='+key+']').is("select") && r.data[i][key] != '')
					{
						
						$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
					//	$('select[name='+key+']').val('' + r.data[i][key] + '').trigger('change');
						
					}
					else
					{
						$('input[name='+key+']').val(r.data[i][key]);
					}
				
		        }
			}
			
			var params='';
		var list ='<tr class="tableHeader info">'+
		'<td><strong><center>Item Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>Item Code<font color="red"> * </font><a href=#></a></center></strong></td>'+
		
		'<td ><strong><center>PO Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Remaining Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>GRN Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Others<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Discount<a href=#></a></center></strong></td>'+
		'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
		
		'</tr>';
		var i=0;
		for(i=0;i<r.data.length;i++)
			{
			params=r.data[i];
		
			list += '<tr class="success">'+
			'<td><center>'+params.nm_model+'</center></td>'+
					'<td><center>'+params.cd_model+'</center></td>'+
			'<td><center><input type="text"  name="po_qty'+i+'"  style="width:60px;;text-align: right;"  value="'+params.po_qty+'" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
			'<td><center><input type="text"  name="rem_qty'+i+'" style="width:60px;;text-align: right;"  value="'+params.po_rem_qty+'" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
			'<td><center><input type="text"  name="qty'+i+'" style="width:60px;;text-align: right;"  value="'+params.qty+'" class="common-validation" patelUnPrc="'+i+'" onchange="calculateTot(event,\'qty\');checkRemQty1(event);" class="patelUnPrc data-valid="num"></center></td>'+
			'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="'+params.un_prc+'" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" readonly ></center></td>'+
			'<td><input type="text" name="others'+i+'" value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_tax1'+i+'" value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
			'</select>'+
			'</center></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_tax2'+i+'" value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')">'+
			'</select>'+
			'</center></td>'+
			'<td><center><input type="text"  name="tax_val1'+i+'" patelUnPrc="'+i+'" value="'+params.tax_val1+'" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
			'<td><center><input type="text"  name="tax_val2'+i+'" patelUnPrc="'+i+'" value="'+params.tax_val2+'" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
			'<td><center><input type="text"  name="buyback'+i+'" patelUnPrc="'+i+'" value="'+params.buyback+'" onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
			'<td><center><input type="text"  name="gr_tot'+i+'" value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
			
			'<input type="hidden" name="id_po_asset'+i+'" value="'+params.id_po_asst+'">'+
			'<input type="hidden" name="id_prod'+i+'" value="'+params.id_model+'">'+
			'<input type="hidden" name="count" value="'+i+'">'+	
			
			'</tr>';
	
			}
		
			
		$('input[name="itemCount"]').val(parseInt(i));
	
	$('#createAccessory').show();
		$('#accessoryDetails').html(list);
		/*$('.EditSalesOrder2').html(list2);*/
		
		
		
		
		}
	
			
			
			
					DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
						if(status)
						{
							DropDownDataDisplay("M_Tax","taxDataForDropDown12",function (status){
								if(status)
								{
									for(var i=0;i<r.data.length;i++)
									{
										params=r.data[i];
										for (var key in r.data[i])
										{
							
											
											if(key == 'id_tax1'){
												$('select[name=id_tax1'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
							       
											}
											if(key=='id_tax2')
											{
												$('select[name=id_tax2'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
												
											}
								
										}
									}
									
								
							
						}});
							
						}});
		
	
	$( ".modifyPODatepicker2" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      onSelect: function(selected,evnt) {
	    	  $(this).removeClass('error');
	    	  var dt_po=$('.poDatepicker').val();
	    	  var dt_po1=$('.poDatepicker').val();
	    	  var dt_dlvr = $(this).val();
	    	  
	    	  if(dt_po == '')
	    		  {
	    		  	alert('First filled the P.O date.');
	    		  	$('.modifyPODatepicker2').focus();
	    		  	$('.modifyPODatepicker2').addClass('error');
	    		  	$('.modifyPODatepicker2').val('');
	    		  	$(this).val('');
	    		  	exit(0);
	    		  }
	    	  else
	    		  {
	    		  var temp_strt = dt_po.split("/");
					 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
						
					var temp_end = dt_dlvr.split("/");
					var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
						
					dt_po = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
		    		  dt_dlvr = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
	    		  
	    		  if(dt_po > dt_dlvr)
	    			  {
	    			  	alert('Delivery date should be greater or equal to P.O date : '+dt_po1);
	    			  	$(this).focus();
	    			  	$(this).val('');
	    			  	$(this).addClass('error');
	    			  	exit(0);
	    			  }
	    		  
	    		  }
	    	  
	      }
	    });
	
	
	
	
},'json');

}
function ControlAsset(action,displayContent,createDetails,formName,servletName) {
SessionCheck(function (ses){		
		if(ses)
			{
	
		$(".common-validation").each(function(){
		
			$(".common-validation").removeClass('error');
		});
		
		$('.readbaledata').each(function (){
			$(this).attr("disabled", false);
		
	});
		$('input[name="invoiceCheck"]').val('0');
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
				$('#accessoryDetails').html('');
			
		}
	if(action=='Cancel')
		{
			$('#displayAccessory').hide();
			$('#createAccessory').hide();
			$('#'+createDetails).hide();
			
			$('#'+displayContent).show();
			DisplayInvoice(servletName,displayContent,createDetails,'','invoiceForDisplay');
			$( ".addinvoice" ).trigger( "click" );
		/*if(formName == 'submitDC')
			$( ".addDC" ).trigger( "click" );
		else
		$( ".addinvoice" ).trigger( "click" );*/
		}
	if(action=='Save' || action=='Update')
		{
			if(action == 'Save')
				{
				$('button[name="save1"]').removeClass('hideButton');
				$('input[name="action"]').val('Save');
				
					var t=false;
			var id_po_asst="0",itemCount='0';
			$('.prRequestItemForIndentSelectAll').each(function(){
				if(this.checked)
				{
					t=true;
					if(id_po_asst == '0')
						id_po_asst = $(this).val();
						else
							id_po_asst +='Patel'+ $(this).val();
					if(itemCount == '0')
						itemCount = $(this).val();
						else
							itemCount +='Patel'+ $(this).val();
				}
				
			});
			if(!t)
				{
					bootbox.alert("Please select at least one request then proceed.");
				}		
			else{
				
				$('input[name="id_po_asst"]').val(id_po_asst);
				t=false;
				t = ValidationForm(formName);
			}
					
	if(t){
				DataInsertUpdateForAsset("Cancel",displayContent,createDetails,formName,servletName);
	
	}
				
				
				}
			else
				{
					$('input[name="action"]').val('Update');
								DataInsertUpdateForAsset("Cancel",displayContent,createDetails,formName,servletName);
	
				}
			
			
		}
	
			}
});
}
function DataInsertUpdateForAsset(action,displayContent,createDetails,formName,servletName)
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
			$('.inv').attr('disabled','disabled');
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
		
		$('#vendorDataForInvoice').removeAttr("disabled");
		var x = $('#'+formName).serialize();
		
			$.post(servletName,x,function (r){
				
				if(r.data > 0)
					{
					$('#createAccessory').hide();
					
					$('.readbaledata').each(function (){
						
						$(this).attr('readonly', 'readonly');
						if($(this).is("select"))
						{
							$(this).attr("disabled", true);
						}
						$('.readbaledata').each(function (){
							$(this).attr("disabled", true);
						});
					});
			            
			           	$( ".createinvoice " ).trigger( "click" );
					}
				else
					{
						bootbox.alert("Try again.");
					}
				$('.inv').removeAttr('disabled');
			},'json');
	
		
		}
		
			}
});
}

function DropDownDataDisplayPONumber(servletName,dropDownId,callback)
{
	var t =false;
	var list= '';
	$.post(servletName,{action : 'DropDownResultPO'},function (r){
		
		if(r.data)
			{
			t=true;
			
				list= '<option value=""> Select</option>';
			
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
		callback(t);
	},'json');

}
function DisplayInvoiceof()
{
SessionCheck(function (ses){
		
		if(ses)
			{
				var id_po=$('#no_po').val();
				var id_ven=$('#id_ven').val();
				
	$.post('Create_Invoice',{action : 'Displayinvoice' , id_ven : id_ven,id_po : id_po},function (r){
		
	var list= '<tr>'+
			'<td colspan="6" class="tableHeader"><center><p class="tableHeaderContent" ">Invoice Details</p></center></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
				'<td><center><strong>Invoice Number<a href=#></a></strong></center></td>'+
				'<td><center><strong>Invoice Date</strong></center></td>'+
				'<td><center><strong>Download Invoice </strong></center></td>'+
				
		'</tr>';
	if(r.data.length > 0)
			{
				var invoice_file='';
						if(r.data[0].invoice_file != undefined)
							invoice_file = r.data[0].invoice_file;
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.no_inv+'</center></td>'+
									'<td><center>'+params.dt_inv+'</center></td>'+
									'<td><center><a href="downloadInvoiceDetails.jsp?fileName1='+invoice_file+'">'+invoice_file+'</a></center></td>'+
									
									'</tr>';
				}
			
				$('#detailsofinvoice').html(list);
			}
	
	
	
		else
			{
				alert("Try again.");
			}
		
		
		
		
		
		
	},'json');

			}
});
}
function DisplayAccessoryForPO(id)
{
	
SessionCheck(function (ses){
		
		if(ses)
			{
				
				var ids=id.value;
	$.post('Create_Invoice',{action : 'DisplayAssetPO' , id : ids},function (r){
		
		if(r.data.length>0)
			{
				var list= '<tr class="tableHeader info">'+
			'<td style="width:50px;"><strong><center><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'prRequestItemForIndentSelectAll\')"> Check All </a> /<br> <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'prRequestItemForIndentSelectAll\')"> Uncheck All</a></center></strong></td>'+
			'<td ><strong><center>Item/Model Name<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Item/Model Code<a href=#></a></center></strong></td>'+
			'<td ><strong><center>PO Quantity<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Remaining QTY<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Accept QTY<a href=#></a></center></strong></td>'+
			
		 	'<td ><strong><center>Quantity<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Others<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Discount<a href=#></a></center></strong></td>'+
			'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
					
							'</tr>';
				for(var i = 0; i < r.data.length ; i++)
				{
				
				
				
				$('select[name=id_ven] option[value=' + r.data[0].id_ven+']').attr('selected',true);
				$('input[name="dt_po"]').val(r.data[0].dt_po);
				$('input[name="no_po"]').val(r.data[0].no_po);
				
				
				params = r.data[i];
				var dt_scheduled_dlvry='';
				var rem_qty = params.rem_qty;
				var qty = params.qty;
				var received = parseFloat(qty) - parseFloat(rem_qty);
				if(parseFloat(rem_qty) > 0){
					list = list + '<tr class="success">'+
					'<td><strong><center><input type="checkbox" name="createLineIndent" class="prRequestItemForIndentSelectAll" patelUnPrc="'+i+'" id_po_asst='+params.id_po_asst+' value="'+params.id_po_asst+'"/></center></strong></td>'+
					'<td><center>'+params.nm_model+'</center></td>'+
					'<td><center>'+params.cd_model+'</center></td>'+
					'<td><center>'+params.qty+'</center></td>'+
					'<td><center>'+params.rem_qty+'</center></td>'+
					'<td><center>'+received+'</center></td>'+
					'<input type="hidden" name="id_prod'+i+'"  value="'+params.id_prod+'" patelUnPrc="'+i+'" " >'+
					'<input type="hidden" name="id_grp'+i+'"  value="'+params.id_grp+'" patelUnPrc="'+i+'" " >'+
					'<input type="hidden" name="id_sgrp'+i+'"  value="'+params.id_sgrp+'" patelUnPrc="'+i+'" " >'+
					
					'<input type="hidden" name="po_qty'+i+'"  value="'+params.qty+'" patelUnPrc="'+i+'" " >'+
					
					'<input type="hidden" name="rem_qty'+i+'"  value="'+params.rem_qty+'" patelUnPrc="'+i+'" " >'+
					'<input type="hidden" name="po_asstets_id"   value="'+params.id_po_asst+'" patelUnPrc="'+i+'" " >'+
					
				'<td><center><input type="text"  name="qty'+i+'"  value="'+params.rem_qty+'" style="width:60px;;text-align: right;" class="common-validation" patelUnPrc="'+i+'" onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+
				'<td><center><input type="text" name="un_prc'+i+'"  value="'+params.un_prc+'" patelUnPrc="'+i+'"  style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" ></center></td>'+
				'<td><input type="text" name="others'+i+'"  value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:60px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
				'<td><center>'+
				'<select style="width: 100px;" name="id_tax1'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center>'+
				'<select style="width: 100px;" name="id_tax2'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center><input type="text"  name="tax_val1'+i+'" patelUnPrc="'+i+'" value="'+params.tax_val1+'" onChange="calculateTot(event,\'val_tax1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
				'<td><center><input type="text"  name="tax_val2'+i+'" patelUnPrc="'+i+'" value="'+params.tax_val2+'" onChange="calculateTot(event,\'val_tax2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
				'<td><center><input type="text"  name="buyback'+i+'"  value="'+params.buyback+'" patelUnPrc="'+i+'"  onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="gr_tot'+i+'"  value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
				'<input type="hidden" name="count" value="'+i+'">'+	
				
				'</tr>';
				}else{
					
						list = list + '<tr class="success">'+
						'<td><strong><center><input type="checkbox" name="createLineIndent" disabled class="prRequestItemForIndentSelectAll11" patelUnPrc="'+i+'" id_po_asst='+params.id_po_asst+' value="'+params.id_po_asst+'"/></center></strong></td>'+
					'<td><center>'+params.nm_model+'</center></td>'+
					'<td><center>'+params.cd_model+'</center></td>'+
					'<td><center>'+params.qty+'</center></td>'+
					'<td><center>'+params.rem_qty+'</center></td>'+
					'<td><center>'+received+'</center></td>'+
					'<input type="hidden" name="id_prod'+i+'"  value="'+params.id_prod+'" patelUnPrc="'+i+'" " >'+
					'<input type="hidden" name="id_grp'+i+'"  value="'+params.id_grp+'" patelUnPrc="'+i+'" " >'+
					'<input type="hidden" name="id_sgrp'+i+'"  value="'+params.id_sgrp+'" patelUnPrc="'+i+'" " >'+
				
					
						'<input type="hidden" name="rem_qty'+i+'"  readOnly value="'+params.rem_qty+'" patelUnPrc="'+i+'" " >'+
						'<input type="hidden" name="po_asstets_id"  readOnly  value="'+params.id_po_asst+'" patelUnPrc="'+i+'" " >'+
						'<input type="hidden" name="po_qty'+i+'"  value="'+params.qty+'" patelUnPrc="'+i+'" " >'+
						
					'<td><center><input type="text"  name="qty'+i+'"  readOnly value="'+params.rem_qty+'" style="width:60px;;text-align: right;" class="common-validation" patelUnPrc="'+i+'" onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+
				'<td><center><input type="text" name="un_prc'+i+'"  readOnly value="'+params.un_prc+'" patelUnPrc="'+i+'"  style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" ></center></td>'+
				'<td><input type="text" name="others'+i+'" readOnly  value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:60px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
				'<td><center>'+
				'<select style="width: 100px;" name="id_tax1'+i+'" disabled="disabled" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center>'+
				'<select style="width: 100px;" name="id_tax2'+i+'" disabled="disabled" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center><input type="text"  name="tax_val1'+i+'" readOnly patelUnPrc="'+i+'" value="'+params.tax_val1+'" onChange="calculateTot(event,\'val_tax1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
				'<td><center><input type="text"  name="tax_val2'+i+'"readOnly  patelUnPrc="'+i+'" value="'+params.tax_val2+'" onChange="calculateTot(event,\'val_tax2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
				'<td><center><input type="text"  name="buyback'+i+'" readOnly  value="'+params.buyback+'" patelUnPrc="'+i+'"  onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="gr_tot'+i+'" readOnly  value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
				'<input type="hidden" name="count" value="'+i+'">'+	
				
					'</tr>';
						
						
					}
				}
			
				$('input[name="id"]').val(id);
				$('input[name="id_po"]').val(id);
				$('#accessoryDetails').html(list);
			DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
			if(status)
			{
				DropDownDataDisplay("M_Tax","taxDataForDropDown12",function (status){
					if(status)
					{
						
					}});
				
			}});
			}
		else
			{
				alert("No Remaining PO Qty is there related to this PO!!!");
			}
		
			/*DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
			if(status)
			{
				
						for(var i=0;i<r.data.length;i++)
						{
							params=r.data[i];
							for (var key in r.data[i])
					        {
								
									if(key == 'id_tax1')
										$('select[name=id_tax1'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									if(key=='id_tax2')
										$('select[name=id_tax2'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									
							}
						}
					
				
				
				
				
			}});*/
		
	},'json');

			}
});
}





