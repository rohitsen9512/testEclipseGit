
function ControlInvoicepayment(action,displayContent,createDetails,formName,servletName) {
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
			//DisplayWorkOrderInvoiceByPO('Invoice_Through_PO','displayInvoice','createInvoice','','invoiceForDisplay');
			//DisplayinvoiceforGRN(servletName,displayContent,createDetails,'','invoiceForDisplay');
		}
	if(action=='Save' || action=='Update')
		{
		$( ".invoiceByPOBtnForDissable" ).attr('Disable',true);
			if(action == 'Save')
				{
				$('button[name="save1"]').removeClass('hideButton');
				$('input[name="action"]').val('Save');
				}
			else
				{
					$('input[name="action"]').val('Update');
				}
			
			DataInsertUpdateForPurchaseOrderpayment("Cancel",displayContent,createDetails,formName,servletName);
			
		}
	
	
		if(action=='Accept' || action=='Reject')
		{
			var dt_accept_reject=	$('input[name="dt_accept_reject"]').val();
				var remarks_reject=	$('textarea[name="remarks_reject"]').val();
				var id=	$('input[name="id"]').val();
			$.post('G_Accept_Reject',{action:'acceptReject',Status:action,id:id,dt_accept_reject:dt_accept_reject,remarks_reject:remarks_reject},function (r){
				
				if(r.data1 == 1)
					{
					 bootbox.confirm("Are you sure ?", function(result) {
							if(result)
							{
					$('#createAccessory').hide();
					bootbox.alert('Invoice updated successfully');
					$( ".invoiceaaprove" ).trigger( "click" );
					
							}
							});}
				else
					{
						bootbox.alert('Invoice updated successfully');
						$( ".invoiceaaprove" ).trigger( "click" );
					}
				$('.invoiceByPOBtnForDissable').removeAttr('disabled');
			},'json');
		}
	
			}
});
}

 

function DataInsertUpdateForPurchaseOrderpayment(action,displayContent,createDetails,formName,servletName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var t=true;
			var id_inv="0",itemCount='0';
			
	
	
		if(t)
		{
			console.log('bbbb');
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
		 
			$('input[name="action"]').val('Payment');
		
		var x = $('#'+formName).serialize();
		$('.invoiceByPOBtnForDissable').attr('disabled','disabled');
			$.post(servletName,x,function (r){
				
				if(r.data == 1)
					{
					$('#createAccessory').hide();
					bootbox.alert('Payment Done.');
					DisplayinvoiceforInvoiceForpayment('G_Accept_Reject','displayInvoice','createInvoice','','invoiceForDisplay');
							$( ".paymentActivity " ).trigger( "click" );	
			            $('#'+createDetails).show();
						$('#displayAccessory').show();
					}
				else
					{
								$( ".paymentActivity " ).trigger( "click" );	
			        
					DisplayinvoiceforInvoiceForpayment('G_Accept_Reject','displayInvoice','createInvoice','','invoiceForDisplay');
							$( ".paymentActivity " ).trigger( "click" );	
			      
					}
				$('.invoiceByPOBtnForDissable').removeAttr('disabled');
			},'json');
	
		
		}
			}
});
}

function BeforeEditAssetPO(servletName,displayContent,createDetails,id,id_dept,id_cc,id_section,po_type){
	
	EditAssetPO(servletName,displayContent,createDetails,id,po_type);
}

function EditAssetPO(servletName,displayContent,createDetails,id,po_type)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			$('#InvoiceForPurchaseOrderHideShow').hide();
	
	$('.readbaledata').each(function (){
		
		$(this).attr('readonly', 'readonly');
		if($(this).is("select"))
		{
			$(this).attr("disabled", true);
		}
		
	});
	
	$('button[name="save"]').addClass('hideButton');
	$('button[name="save1"]').addClass('hideButton');
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
						var uploadFile='';
						if(r.data[0].invoice_file != undefined)
							uploadFile = r.data[0].invoice_file;
							var download='<a href="downloadInvoiceDetails.jsp?fileName1='+uploadFile+'">'+uploadFile+'</a> ';
							
							document.getElementById("datachment").innerHTML =download;
							
						$('input[name="no_grn"]').val(r.no_grn);
						$('input[name="dt_inv"]').val(r.data[0].dtinv);
						$('input[name="action"]').val("Save");
						$('input[name="id"]').val(id);
						$('input[name="id_ven"]').val(r.data[0].id_ven);
							
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				if(t == 1)
				{
					
					ShowRowColumn('Yes' , 'hideRowCol');
				}
					DisplayAccessoryForPO('G_Accept_Reject','displayInvoice','createInvoice',id,'accessoryForDisplay');
				
					
		},'json');
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
								'<td style="width:50px;"><strong><center><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'prRequestItemForIndentSelectAll\')"> Check All </a> /<br> <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'prRequestItemForIndentSelectAll\')"> Uncheck All</a></center></strong></td>'+
								 	'<td style="min-width:100px;"><strong><center>Model Name</center></strong></td>'+
								'<td style=""><strong><center>PO Qty</center></strong></td>'+
								'<td style=""><strong><center>Invoice Qty</center></strong></td>'+
								'<td><strong><center>Remaining Qty</center></strong></td>'+
								'<td><strong><center>Accept Qty</center></strong></td>'+
							//	'<td ><strong><center>Asset Owner </center></strong></td>'+
								'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Others<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Discount<a href=#></a></center></strong></td>'+
								'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
									
							'</tr>';
							var total_amount=0;
				for(var i = 0; i < r.data.length ; i++)
				{
				 
				params = r.data[i];
				var rem_qty = params.rem_qty;
				var qty = params.qty;
				var received = parseFloat(qty) - parseFloat(rem_qty);
				if(parseFloat(rem_qty) > 0){
					list = list + '<tr class="success">'+
					'<td><strong><center><input type="checkbox" name="createLineIndent" class="prRequestItemForIndentSelectAll" patelUnPrc="'+i+'" id_po_asst='+params.id_inv+' value="'+params.id_inv+'"/></center></strong></td>'+
					//'<td><center>'+params.no_po+'</center></td>'+
					'<td><center>'+params.no_model+'</center></td>'+
					'<td><center>'+params.qty_po+'</center></td>'+
					'<td><center>'+params.qty+'</center></td>'+
					'<td><center>'+params.rem_qty+'</center></td>'+
					'<input type="hidden" name="po_no'+i+'"  value="'+params.no_po+'" patelUnPrc="'+i+'" " >'+
					'<input type="hidden" name="po_dt'+i+'"   value="'+params.dt_po+'" patelUnPrc="'+i+'" " >'+
				
					'<input type="hidden" name="rem_qty'+i+'"  value="'+params.rem_qty+'" patelUnPrc="'+i+'" " >'+
					'<input type="hidden" name="id_inv"   value="'+params.id_inv+'" patelUnPrc="'+i+'" " >'+
					'<td><center><input type="text"  name="qty'+i+'" style="width:60px;text-align: right;"  value="'+params.rem_qty+'" class="common-validation" patelUnPrc="'+i+'" onchange="calculateTot(event,\'qty\');RemQtyChkInv(event);" class="patelUnPrc data-valid="num"></center></td>'+
				
					'<input type="hidden" name="typ_asst'+i+'"  value="IT" patelUnPrc="'+i+'" " >'+
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
					
				'</tr>';
				}else{
					
						list = list + '<tr class="success">'+
						'<td><strong><center><input type="checkbox" name="createLineIndent" disabled class="prRequestItemForIndentSelectAll11" patelUnPrc="'+i+'" id_po_asst='+params.id_po_asst+' value="'+params.id_po_asst+'"/></center></strong></td>'+
					//	'<td><center>'+params.no_po+'</center></td>'+
						'<td><center>'+params.no_model+'</center></td>'+
						'<td><center>'+params.qty_po+'</center></td>'+
						'<td><center>'+params.qty+'</center></td>'+
						'<td><center>'+params.rem_qty+'</center></td>'+
						'<input type="hidden" name="id_inv"  readOnly  value="'+params.id_inv+'" patelUnPrc="'+i+'" " >'+
						'<td><center><input type="text"  name="qty'+i+'" style="width:60px;;text-align: right;"  value="'+params.qty+'" class="common-validation" patelUnPrc="'+i+'" onchange="calculateTot(event,\'qty\');RemQtyChkInv(event);" class="patelUnPrc data-valid="num"></center></td>'+
						'<input type="hidden" name="typ_asst'+i+'"  value="IT" patelUnPrc="'+i+'" " >'+
						
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
					
					'</tr>';
						
						
					}
				}
		
				$('input[name="id"]').val(id);
				$('input[name="id_po"]').val(id);
				$('.accessoryForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
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
		
		$( ".datepicker" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      
		    });
		
		
	},'json');

			}
});
}

function RemQtyChkInv(event){
	
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	
	var domElement =$(event.target || event.srcElement);
	var rem_qty = $('input[name="rem_qty'+domElement.attr('patelUnPrc')+'"]').val();
	var qty = $('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val();
	var str =qty;
	$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').removeClass('error');
	
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		if(parseFloat(qty) > parseFloat(rem_qty)){
			alert(qty+'Remaining quntity is : '+ rem_qty);
			$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').addClass('error');
			$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').focus();
			$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val(rem_qty);
			exit(0);
		}
	}
		else
		{
			alert('Invalid number.');
				$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').addClass('error');
				$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').focus();
				$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val(rem_qty);
				exit(0);
	}
}


function DisplayinvoiceforInvoiceForpayment(servletName,displayContent,createDetails,no_inv,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var dt_frm =$('#dt_frm').val();
			var dt_to =$('#dt_to').val();
			var searchWord = $('input[name="search"').val();
				$.post(servletName,{action : 'DisplayForpayment' , no_inv : no_inv,dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><center><strong>PO Number</strong></center></td>'+
					'<td><center><strong>PO Date</strong></center></td>'+
				
					'<td><center><strong>Invoice Number</strong></center></td>'+
					'<td><center><strong>Invoice Date</strong></center></td>'+
				
					'<td><strong><center>Vendor</center></strong></td>'+
					'<td style="width: 170px;"><strong><center><a href="#">Preview</a></center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
							
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_po+'</center></td>'+
						'<td><center>'+params.dt_po+'</center></td>'+
						
						'<td><center>'+params.no_inv+'</center></td>'+
						'<td><center>'+params.dt_inv+'</center></td>'+
						
						'<td><center>'+params.nm_ven+'</center></td>'+
						'<td><strong><center><a class="alert" href="#" onclick="BeforeEditAssetPOForpayment(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_inv_m+')"> Preview </a></center></strong></td>'+
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
    	  var dt_transptr = $(this).val();
    	  
    	  var temp_strt = dt_po.split("/");
		  var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
				
			var temp_end = dt_inv.split("/");
			var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
			var temp_enddt_transptr = dt_transptr.split("/");
			var temp_dt_enddt_transptr = new Date(temp_enddt_transptr[2], temp_enddt_transptr[1] - 1, temp_enddt_transptr[0]);
			
			dt_po = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
			dt_inv = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
			dt_transptr = $.datepicker.formatDate('yy-mm-dd', temp_dt_enddt_transptr);
			/*
    		  if(dt_po > dt_inv)
    			  {
    			  
    			  	alert('Inoice date should be greater or equal to P.O date : '+dt_po1);
    			  	$(this).focus();
    			  	$(this).val('');
    			  	$(this).addClass('error');
    			  	exit(0);
    			  }
    		  if(dt_po > dt_transptr)
			  {
			  	alert('Inward date should be greater or equal to P.O date : '+dt_po1);
			  	$(this).focus();
			  	$(this).val('');
			  	$(this).addClass('error');
			  	exit(0);
			  }*/
    		  
    	  
      }
    });

/// ApproveReject  Invoice

function BeforeEditAssetPOForpayment(servletName,displayContent,createDetails,id,id_dept,id_cc,id_section,po_type){
	
	EditAssetPOApprovepayment(servletName,displayContent,createDetails,id,po_type);
}

function EditAssetPOApprovepayment(servletName,displayContent,createDetails,id,po_type)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			$('#InvoiceForPurchaseOrderHideShow').hide();
	
	$('.readbaledata').each(function (){
		
		$(this).attr('readonly', 'readonly');
		if($(this).is("select"))
		{
			$(this).attr("disabled", true);
		}
		
	});
	
	$('button[name="save"]').removeClass('hideButton');
	$('button[name="save1"]').removeClass('hideButton');
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
						var uploadFile='';
						if(r.data[0].invoice_file != undefined)
							uploadFile = r.data[0].invoice_file;
							var download='<a href="downloadInvoiceDetails.jsp?fileName1='+uploadFile+'">'+uploadFile+'</a> ';
							
							document.getElementById("datachment").innerHTML =download;
							
						$('input[name="no_grn"]').val(r.no_grn);
						$('input[name="dt_inv"]').val(r.data[0].dtinv);
						$('input[name="action"]').val("Save");
						$('input[name="id"]').val(id);
						$('input[name="id_ven"]').val(r.data[0].id_ven);
						$('input[name="payment_amount"]').val('0');
						$('input[name="dt_payment"]').val('');
						$('input[name="no_utr"]').val('');
						
						$('input[name="previouspayment"]').val(r.data[0].payment_amount);
							
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				if(t == 1)
				{
					
					ShowRowColumn('Yes' , 'hideRowCol');
				}
					DisplayAccessoryForPOpayment('G_Accept_Reject','displayInvoice','createInvoice',id,'accessoryForDisplay');
				
					
		},'json');
			}
	
});
}


function DisplayAccessoryForPOpayment(servletName,displayContent,createDetails,id,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	$.post(servletName,{action : 'DisplayAsset' , id : id},function (r){
		
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
							//	'<td style="width:50px;"><strong><center><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'prRequestItemForIndentSelectAll\')"> Check All </a> /<br> <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'prRequestItemForIndentSelectAll\')"> Uncheck All</a></center></strong></td>'+
								 	'<td style="min-width:100px;"><strong><center>Model Name</center></strong></td>'+
								'<td style=""><strong><center>PO Qty</center></strong></td>'+
								'<td style=""><strong><center>Invoice Qty</center></strong></td>'+
								'<td><strong><center>Remaining Qty</center></strong></td>'+
								'<td><strong><center>Accept Qty</center></strong></td>'+
							//	'<td ><strong><center>Asset Owner </center></strong></td>'+
								'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Others<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Discount<a href=#></a></center></strong></td>'+
								'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
									
							'</tr>';
							var total_amount=0;
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				var rem_qty = params.rem_qty;
				var qty = params.qty;
				var received = parseFloat(qty) - parseFloat(rem_qty);
					total_amount=total_amount+parseFloat(params.gr_tot);
			
				if(parseFloat(rem_qty) > 0){
					list = list + '<tr class="success">'+
					//'<td><strong><center><input type="checkbox" name="createLineIndent" class="prRequestItemForIndentSelectAll" patelUnPrc="'+i+'" id_po_asst='+params.id_inv+' value="'+params.id_inv+'"/></center></strong></td>'+
					//'<td><center>'+params.no_po+'</center></td>'+
					'<td><center>'+params.no_model+'</center></td>'+
					'<td><center>'+params.qty_po+'</center></td>'+
					'<td><center>'+params.qty+'</center></td>'+
					'<td><center>'+params.rem_qty+'</center></td>'+
					'<input type="hidden" name="po_no'+i+'" readonly value="'+params.no_po+'" patelUnPrc="'+i+'" " >'+
					'<input type="hidden" name="po_dt'+i+'" readonly  value="'+params.dt_po+'" patelUnPrc="'+i+'" " >'+
				
					'<input type="hidden" name="rem_qty'+i+'"  value="'+params.rem_qty+'" patelUnPrc="'+i+'" " >'+
					'<input type="hidden" name="id_inv"   value="'+params.id_inv+'" patelUnPrc="'+i+'" " >'+
					'<td><center><input type="text"readonly  name="qty'+i+'" style="width:60px;text-align: right;"  value="'+params.rem_qty+'" class="common-validation" patelUnPrc="'+i+'" onchange="calculateTot(event,\'qty\');RemQtyChkInv(event);" class="patelUnPrc data-valid="num"></center></td>'+
				
					'<input type="hidden" name="typ_asst'+i+'"  value="IT" patelUnPrc="'+i+'" " >'+
					'<td><center><input type="text" name="un_prc'+i+'" readonly patelUnPrc="'+i+'" value="'+params.un_prc+'" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" readonly ></center></td>'+
					'<td><input type="text" name="others'+i+'" readonly value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
					'<td><center>'+
					'<select style="width: 100px;" name="id_tax1'+i+'" readonly value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
					'</select>'+
					'</center></td>'+
					'<td><center>'+
					'<select style="width: 100px;" name="id_tax2'+i+'" readonly value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')">'+
					'</select>'+
					'</center></td>'+
					'<td><center><input type="text"  name="tax_val1'+i+'" readonly patelUnPrc="'+i+'" value="'+params.tax_val1+'" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
					'<td><center><input type="text"  name="tax_val2'+i+'" readonly patelUnPrc="'+i+'" value="'+params.tax_val2+'" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
					'<td><center><input type="text"  name="buyback'+i+'" readonly patelUnPrc="'+i+'" value="'+params.buyback+'" onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
					'<td><center><input type="text"  name="gr_tot'+i+'" readonly value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
					
				'</tr>';
				}else{
					
						list = list + '<tr class="success">'+
						'<td><strong><center><input type="checkbox" name="createLineIndent" disabled class="prRequestItemForIndentSelectAll11" patelUnPrc="'+i+'" id_po_asst='+params.id_po_asst+' value="'+params.id_po_asst+'"/></center></strong></td>'+
					//	'<td><center>'+params.no_po+'</center></td>'+
						'<td><center>'+params.no_model+'</center></td>'+
						'<td><center>'+params.qty_po+'</center></td>'+
						'<td><center>'+params.qty+'</center></td>'+
						'<td><center>'+params.rem_qty+'</center></td>'+
						'<input type="hidden" name="id_inv"  readOnly  value="'+params.id_inv+'" patelUnPrc="'+i+'" " >'+
						'<td><center><input type="text" readonly  name="qty'+i+'" style="width:60px;;text-align: right;"  value="'+params.qty+'" class="common-validation" patelUnPrc="'+i+'" onchange="calculateTot(event,\'qty\');RemQtyChkInv(event);" class="patelUnPrc data-valid="num"></center></td>'+
						'<input type="hidden" name="typ_asst'+i+'"  value="IT" patelUnPrc="'+i+'" " >'+
						
						'<td><center><input type="text" name="un_prc'+i+'" readonly patelUnPrc="'+i+'" value="'+params.un_prc+'" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" readonly ></center></td>'+
						'<td><input type="text" name="others'+i+'" readonly value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
						'<td><center>'+
						'<select style="width: 100px;" name="id_tax1'+i+'" readonly value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
						'</select>'+
						'</center></td>'+
						'<td><center>'+
						'<select style="width: 100px;" name="id_tax2'+i+'" readonly value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')">'+
						'</select>'+
						'</center></td>'+
						'<td><center><input type="text"  name="tax_val1'+i+'" readonly patelUnPrc="'+i+'" value="'+params.tax_val1+'" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
						'<td><center><input type="text"  name="tax_val2'+i+'" readonly patelUnPrc="'+i+'" value="'+params.tax_val2+'" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
						'<td><center><input type="text"  name="buyback'+i+'" readonly patelUnPrc="'+i+'" value="'+params.buyback+'" onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
						'<td><center><input type="text"  name="gr_tot'+i+'"readonly  value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
					
					'</tr>';
						
						
					}
				}
			 	$('input[name="tot_pay"]').val(total_amount);
					$('input[name="acc_pay"]').val($('input[name="previouspayment"]').val());
				$('input[name="rem_pay"]').val(total_amount-parseFloat($('input[name="previouspayment"]').val()));
					
				$('input[name="id"]').val(id);
				$('input[name="id_po"]').val(id);
				$('.accessoryForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
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
		
		$( ".datepicker" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      
		    });
		
		
	},'json');

			}
});
}

