$(function() {
	$("#menu").menu();
});
//edit invoice no after stock	
function ControlInventoryafterstock(action, displayContent, editDetails, formName, servletName,id) {
		
	
    SessionCheck(function(ses) {
		if (ses) {
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			$('input[name="invoiceCheck"]').val('0');
			
			
			if (action == 'Cancel') {
			
				$('#createAccessory1').hide();
				$('#' + editDetails).hide();
				$('#' + displayContent).show();
				DisplayInventoryafterStock(servletName, displayContent, editDetails, '', 'AddtoStckForDisplay');
				window.location = $('.invEdithistory_event').attr('href');
				}
			else if(action =='Edit')
	{
		//
		EditInventoryafterStock(action , displayContent , editDetails , formName , servletName,id);
		$('#'+displayContent).hide();
		$('#'+editDetails).show();
	}
			 
			else if (action == 'Update') {
					
					$('input[name="action"]').val('Update');
				
				t = false;
			    t = ValidationForm(formName);
				
				if (t) {
				
				
				 $('.form-control').removeAttr('disabled');
				 $('.reopenState ').removeAttr('disabled');
				var x = $('#' + formName).serialize();
			    
				var itemCount=$('input[name="itemCount"]').val();
			
			
			var t=1;
				for(var i=0;i<itemCount;i++)
			{debugger;
				var id_prod=$('input[name="id_prod'+i+'"]').val();
					var qty_fill=$('input[name="qty_fill'+i+'"]').val();
					var qty_empty=$('input[name="qty_empty'+i+'"]').val();
					var id_tax1=$('select[name="id_tax1'+i+'"]').val();
					var invoice_no=$('input[name="no_inv"]').val();
					var temp_invoice_no=$('input[name="temp_no_inv"]').val();
				if(id_prod != '' )	
				{
					if(invoice_no == '' ||  invoice_no == undefined )	
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.inv').removeAttr('disabled');
						$('.patelTax2').attr('disabled', 'disabled');
						$('input[name="no_inv"]').addClass('error');
						 $('.form-control').attr('disabled', 'disabled');
							$('.invoiceno').removeAttr('disabled');
						 $('.invoicedate').attr('disabled', 'disabled');
					
						//$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
					}
					if(temp_invoice_no == '' ||  temp_invoice_no == undefined )	
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.inv').removeAttr('disabled');
						$('.patelTax2').attr('disabled', 'disabled');
						$('input[name="temp_no_inv"]').addClass('error');
						 $('.form-control').attr('disabled', 'disabled');
							$('.invoiceno').removeAttr('disabled');
						 $('.invoicedate').attr('disabled', 'disabled');
					
						//$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
					}
					/*if(qty_fill == '' ||  qty_fill == undefined )	
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.inv').removeAttr('disabled');
						$('.patelTax2').attr('disabled', 'disabled');
						$('input[name="qty_fill'+i+'"]').addClass('error');
						//$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
					}
					else if(qty_empty == '' ||  qty_empty == undefined )
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.inv').removeAttr('disabled');
						$('.patelTax2').attr('disabled', 'disabled');
						$('input[name="qty_empty'+i+'"]').addClass('error');
						//$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
					}*/
					else if(id_tax1 == '' ||  id_tax1 == undefined )
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.inv').removeAttr('disabled');
						$('.patelTax2').attr('disabled', 'disabled');
						$('select[name="id_tax1'+i+'"]').addClass('error');
						//$('select[name="id_sgrp'+i+'"]').addClass('error');
						 $('.form-control').attr('disabled', 'disabled');
							$('.invoiceno').removeAttr('disabled');
						 $('.invoicedate').attr('disabled', 'disabled');
					
						exit(0);
					}
					else
					{
						$('input[name="qty_fill'+i+'"]').removeClass('error');
						$('input[name="qty_empty'+i+'"]').removeClass('error');
						$('select[name="id_tax1'+i+'"]').removeClass('error');
						$('.patelTax2').attr('disabled', 'disabled');
						 $('.form-control').attr('disabled', 'disabled');
							$('.invoiceno').removeAttr('disabled');
						 $('.invoicedate').attr('disabled', 'disabled');
					
					}
					}
					debugger;
					/*if(qty_fill!='' || qty_empty!='' || qty!='')
					{ 
						if(id_prod  == '' || id_prod == undefined)
						{
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.inv').removeAttr('disabled');
						$('.patelTax2').attr('disabled', 'disabled');
						$('select[name="id_prod'+i+'"]').addClass('error');
						//$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
						}
					}*/
					
				
			}
				
				if($('input[name="id_prod0"]').val() == '' )	
				{
					t=0;
						bootbox.alert("Make sure you have filled all the mandatory fields !!");
						$('.inv').removeAttr('disabled');
					}
					if(t==1)
			{
				$.post(servletName, x, function(r) {
					if (r.data == 1) {
						$('#createAccessory1').hide();
						$('.readbaledata').each(function() {
							$(this).attr('readonly', 'readonly');
							if ($(this).is("select")) {
								$(this).attr("disabled", true);
							}
							$('.readbaledata').each(function() {
								$(this).attr("disabled", true);
							});
						});
						alert("Invoice has created successfully.");
						window.location = $('.invEdithistory_event').attr('href');
					}
					else if (r.data == 2) {
						bootbox.alert("Invoice number is already exists.");
						$('#LeadNum').addClass('error');
					}
					else {
						bootbox.alert("Try again.");
					}
					$('.inv').removeAttr('disabled');
				}, 'json');
			}
				
				}
				}
			}
			});
		}
        





//For Modify Invoice
function EditInventoryafterStock(action, displayContent, editDetails,formName, servletName,id) {
	SessionCheck(function(ses) {
		//
		debugger;
		if (ses) {
			$('input[name="invoiceCheck"]').val('1');
			
			$('#' + displayContent).hide();
			$('#' + editDetails).show();
			$('#createAccessory').show();
			$('#updatebutton').hide();
			

			$('.hideinv').hide();
			var t = 0;
			var key1;
			$.post(servletName, { action: 'Edit', id: id }, function(r) {
				if (r.data) {
					//
					console.log(r.data);
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
					
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								//
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
							if (key == 'bd' && r.data[i][key] == 'Yes') {
								t = 1;
							}
						//	//
							if (key == 'warranty_amc' && r.data[i][key] == 'A'||r.data[i][key] == 'W'||r.data[i][key] == 'O') {
								t = 1;
								key1= r.data[i][key];
								
							}
							
						}
					}
					//
					 $('input[name="no_inv"]').val(r.data[0].invNO);
					 $('input[name="temp_no_inv"]').val(r.data[0].temp_invNO);
		
			    if(r.data[0].invNO=='NA'){
				debugger;
			
					 $('button[name="UpdateInvoiceNo"]').removeClass('hideButton');
				
			}
				
			
			 if(r.data[0].invNO!='NA'){
				     $('button[name="UpdateInvoiceNo"]').addClass('hideButton');
               $('button[name="cancel"]').attr('style','margin-left: 500px');
                  
				 
			}	   
				
	        	  $('input[name="action"]').val("Update");
             
					$('input[name="id"]').val(id);
					$('input[name="dt_inv"]').val(r.data[0].dtInv);
					$('input[name="dt_temp_inv"]').val(r.data[0].dttempInv);
					$('input[name="dt_amc_start"]').val(r.data[0].dt_amc_start);
					$('input[name="dt_amc_exp"]').val(r.data[0].dt_amc_exp);
					
				DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForInvoice', 'M_Subloc', function(status) {
						if (status) {
			     $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
		         $('select[name=id_subl] option[value=' + r.data[0].id_subl + ']').attr('selected', true);
					}
					});						
					
					
			
					
							
					dispalyLineItemInvoiceformodify(r);
				    $('.form-control ').attr('disabled', 'disabled');
		           
	              if(r.data[0].invNO=='NA'||r.data[0].invNO==''){
					 $('.reopenState ').removeAttr('disabled');
				     $('button[Name="UpdateInvoiceNo"]').removeAttr('disabled');
					
				  }	
				else{	
					$('.reopenState ').attr('disabled', 'disabled');
					$('button[Name="UpdateInvoiceNo"]').attr('disabled', 'disabled');
					 
					}

                   }//if end
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					//
					//ShowRowColumn('Yes', 'hideRowCol');
					ShowRowColumn(key1, 'amcAddToStockCommonClass');
				}
				
			}, 'json');
		}
	});
}

//display invoice history
function DisplayInventoryafterStock(servletName, displayContent, editDetails, no_inv,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			//
			
		var searchWord = $('input[name="searchWord"]').val();
			let count=0;
			//
			$.post(servletName, { action: 'Display', no_inv: no_inv, searchWord }, function(r) {
				var list = '<thead><tr class="new">' +
				'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Invoice Number</center></strong></td>' +
					'<td><strong><center>Invoice Date</center></strong></td>' +
					'<td><strong><center>Temporary Invoice Number</center></strong></td>' +
					'<td><strong><center>Temporary Invoice Date</center></strong></td>' +
					 '<td><strong><center>Entity</center></strong></td>' +
				     '<td><strong><center>City</center></strong></td>' +
					  '<td><strong><center>View</center></strong></td>' +
					'</tr></thead><tbody>';
					//
				if (r.data.length > 0) {
					for (var i = 0; i < r.data.length; i++) {
						
						params = r.data[i];
							 if (r.data[i].dtInv == '01/01/1900') {
						    
		                  dtinvoice= 'NA';  
					}
					else {
						  dtinvoice= r.data[i].dtInv; 
					}
					if (r.data[i].dttempInv == '01/01/1900') {
						
						dtTEMPinv='NA';
						
					}
					else {
						dtTEMPinv= r.data[i].dttempInv; 
					} 
					
						//
						list = list + '<tr>' +
						'<td><center>' + ++count + '</center></td>' +
							//'<td><center><a class="alertlink" href="#" onclick="EditInventory(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',' + params.id_inventory_m + ')">' + params.no_inv + '</a></center></td>' +
							'<td><center>' + params.no_inv + '</center></td>' +
							'<td><center>' + dtinvoice + '</center></td>' +
							'<td><center>' + params.temp_no_inv + '</center></td>' +
							'<td><center>' + dtTEMPinv + '</center></td>' +
							'<td><center>' + params.nm_loc + '</center></td>'+

							'<td><center>' + params.nm_subl + '</center></td>'+

						     '<td><center><a class="alertlink" href="#" onclick="ControlInventoryafterstock(\'Edit\',\''+displayContent+'\',\''+editDetails+'\',\''+formName+'\',\'S_Add_To_Stock \','+params.id_inventory_m+')">' + 'View' + '</a></center></td>' +
	
	
						'</tr>';
					    
					}
				}
				else {
					list += '<tr><td colspan="5"><strong><center>No data found.</center></strong></td></tr>';
					$('#inventoryAfterstockForDisplaytd').html('</tbody>' + list);
				}
				$('#inventoryAfterstockForDisplaytd').html('</tbody>' + list);
				getButtonsForListView('inventoryAfterstockForDisplaytd','inv_List');
				
			}, 'json');
		} 
		
	});
}

	
//Line Item While modifying Invoice
function dispalyLineItemInvoiceformodify(r) {
	if (r.data) {
		
		var list = '<tr>' +
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' + '<tr class="tableHeader info">' +
			'<td ><strong><center>Product<a href=#></a></center></strong></td>' +
		/*	'<td ><strong><center>Assets/Model Type<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Catagory<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Sub Catagory<a href=#></a></center></strong></td>' +*/
			'<td ><strong><center>Quantity<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Others<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Discount<a href=#></a></center></strong></td>' +
			'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>' +
			'</tr>';
		for (var i = 0; i < r.data.length; i++) {
			console.log('ss');
			//
			list += '<tr>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_pro + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc " onchange="changeEventHandlerprod(event);"></td>' +
				/*'<td><center><select style="width: 120px !important;" id="assettypeDivForInvoice' + i + '" name="typ_asst' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80" >' +
				'<option value="">--Select--</option>' +
				'<option value="IT">IT</option>' +
				'<option value="NON-IT">NON IT</option>' +
				'<option value="Software">Software</option>' +
				'<option value="accessories">Accessories</option></select>' +
				'</center></td>' +
				'<td><center><select style="width: 120px; !important" id="assetDivForInvoice' + i + '" name="id_grp' + i + '" patelUnPrc="' + i + '"  class="form-control  groupdrop" data-valid=""  onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice' + i + '\',\'M_Subasset_Div\')">' +
				'<option value="">--Select--</option></select>' +
				'</center></td>' +
				'<td><center><select style="width: 120px; !important" id="subGroupDataForInvoice' + i + '" name="id_sgrp' + i + '" patelUnPrc="' + i + '"  class="form-control dropsgrp" data-valid=""  >' +
				'<option value="">--Select--</option></select>' +
				'</center></td>' +*/
				'<td><center><input type="text"  name="qty' + i + '" style="width:60px;;text-align: right;"  value="' + r.data[i].qty + '" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>' +
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
				'<td><input type="text" name="others' + i + '" value="' + r.data[i].others + '" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTot(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 100px;" name="id_tax1' + i + '" value="' + r.data[i].id_tax1 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '" class="form-control patelTax" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 100px;" name="id_tax2' + i + '" value="' + r.data[i].id_tax2 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')" readonly>' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val1 + '" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val2 + '" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].buyback + '" onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
				'<td><center><input type="text"  name="gr_tot' + i + '" value="' + r.data[i].gr_tot + '" style="width:60px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="id_inventory' + i + '" value="' + r.data[i].id_inventory + '">' +
				'<input type="hidden" name="count" value="' + i + '">' +
				'</tr>';
		}
		$('input[name="itemCount"]').val(i);
		$('input[name="grand_tot1"]').val('0.00');
		$('#accessoryDetails').html(list);
		
		//Auto select Dropdown of lineitems
		DropDownDataDisplay("M_Tax", "taxDataForDropDown11", function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "patelTax2", function(status) {
					if (status) {
						/*DisplayDropDownDataForGroup('M_Asset_Div', 'groupDrop', 'CapG', function(status) {
							if (status) {
								SubDropDownDataDisplay('0', 'dropsgrp', 'M_Subasset_Div', function(status) {*/
									for (var i = 0; i < r.data.length; i++) {
										params = r.data[i];
										for (var key in r.data[i]) {
											if (key == 'id_tax1') {
												$('select[name=id_tax1' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
											if (key == 'id_tax2') {
												$('select[name=id_tax2' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
										/*	if (key == 'id_grp') {
												$('select[name=id_grp' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
											if (key == 'id_sgrp') {
												$('select[name=id_sgrp' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
											if (key == 'typ_asst') {
												$('select[name=typ_asst' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}*/
										}
									}
							/*	});
							}
						});*/
					}
				});
			}
		});
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
}

//dispaly for addtock inventory
function DisplayInventoryforstock(servletName, displayContent, editDetails, no_inv,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			$('#serialNo').hide();
			var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();
			//
			let count=0;
			$.post(servletName, { action: 'DisplayInvforaddtostock', no_inv: no_inv, dt_frm: dt_frm, dt_to: dt_to }, function(r) {
				var list = '<thead><tr class="new">' +
					'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Invoice Number</center></strong></td>' +
					'<td><strong><center>Invoice Date</center></strong></td>' +
					'<td><strong><center>Temporary Invoice Number</center></strong></td>' +
					'<td><strong><center>Temporary Invoice Date</center></strong></td>' +
					'<td><strong><center>Product Name</center></strong></td>' +
					'<td><strong><center>Product Prefix</center></strong></td>' +
					  '<td><strong><center>Fill Quantity</center></strong></td>' +
			         '<td><strong><center>Empty Quantity</center></strong></td>' +
					  '<td><strong><center>Total Quantity</center></strong></td>' +
			 '<td><strong><center>Entity</center></strong></td>' +
				    '<td><strong><center>City</center></strong></td>' +
					
					'<td><strong><center>Add To Stock</center></strong></td>' +
					'</tr></thead><tbody>';
					//
				if (r.data.length > 0) {
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						//
						
						
						
				 if (r.data[i].dtInv == '01/01/1900') {
						    
		                  dtinvoice= 'NA';  
					}
					else {
						  dtinvoice= r.data[i].dtInv; 
					}
					if (r.data[i].dttempInv == '01/01/1900') {
						
						dtTEMPinv='NA';
						
					}
					else {
						dtTEMPinv= r.data[i].dttempInv; 
					}
					if(r.data[i].nm_acc_asset=='TYPE-PRODUCT'){
						if (r.data[i].qty_empty == ''||r.data[i].qty_empty == undefined||r.data[i].qty_empty == '0'||r.data[i].fill_qty == ''||r.data[i].fill_qty == undefined ||r.data[i].fill_qty == '0') {
						
						qtyEmpty='-';
						qtyFill='-';
					}
					
						
			}else{
						
					if (r.data[i].qty_empty == ''||r.data[i].qty_empty == undefined) {
						
						qtyEmpty='-';
						
					}
					else {
						qtyEmpty= r.data[i].qty_empty; 
					}
					if (r.data[i].qty_fill == ''||r.data[i].qty_fill == undefined) {
						qtyFill='-';
					}else{
						qtyFill=r.data[i].qty_fill;
					}  
			}
				
					console.log(params.qty );
						list = list + '<tr>' +
							'<td><center>' + ++count + '</center></td>' +
							//'<td><center><a class="alertlink" href="#" onclick="EditInventory(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',' + params.id_inventory_m + ')">' + params.no_inv + '</a></center></td>' +
							'<td><center>' + params.no_inv + '</center></td>' +
							'<td><center>' + dtinvoice + '</center></td>' +
							'<td><center>' + params.temp_no_inv + '</center></td>' +
							'<td><center>' +dtTEMPinv + '</center></td>' +
							'<td><center>' + params.product + '</center></td>' +
							'<td><center>' + params.asset_prfx + '</center></td>' +
							'<td><center>' + qtyFill+ '</center></td>' +
						    '<td><center>' + qtyEmpty + '</center></td>' +
							'<td><center>' + params.qty + '</center></td>' +
							'<td><center>' + params.nm_loc + '</center></td>'+
							'<td><center>' + params.nm_subl + '</center></td>'+
							
                           // '<td><center><a class="alertlink" href="#" onclick="ControlInventory(\'Edit\',\''+displayContent+'\',\''+editDetails+'\',\''+formName+'\',\'S_Add_To_Stock \','+params.id_inventory_m+')">' + 'AddToStock' + '</a></center></td>' +
                             '<td><center><a class="alertlink" href="#" onclick="AddToStock1(' + params.id_inventory + ',' + params.id_inventory_m + ',' + params.qty + ',\''+ params.nm_loc + '\',\''+ params.asset_prfx + '\')">' + 'Add To Stock' + '</a></center></td>' +
	               	'</tr>';
					    
					}
				}
				else {
					list += '<tr><td colspan="5"><strong><center>No data found.</center></strong></td></tr>';
					$('#inventoryForDisplaytd').html('</tbody>' + list);
				}
				$('#inventoryForDisplaytd').html('</tbody>' + list);
				getButtonsForListView('inventoryForDisplaytd','inv_List');
			}, 'json');
		
	}
	});
}

//add to stock1
function AddToStock1(id_inventory,id_inventory_m ,qty,nm_loc,asset_prfx) {
	SessionCheck(function(ses) {
		if (ses) {
			debugger;
			
			$('#serialNo').show();
		    $('#displayInventory').hide();
			
			//alert(type);
			var temp = '<center><table class="table table-bordered" style="width : 70%">' +
				'<tr class="new">' +
				'<td colspan="10"><center><strong><h3>Add Serial No</h3></strong></center></td>' +
				'</tr>';
			temp += '<tr><td colspan="10"><button type="button" style="float:right;" class="btn btn-primary" ' +
				'onclick="GenerateDynamicSerialNumber('+id_inventory+','+qty+',\''+nm_loc+'\',\''+asset_prfx+'\')">Generate Serial No </button></td></tr>';
			
			for (var i = 0; i < qty; i++) {
				j = i + 1;
				temp += '<tr><td ><strong>Serial No ' + j + '<font color="red">*</font></strong></td>' +
					'<td ><input type="text"  class="putNA form-control" name="sno' + i + '" value="" class="form-control removeErr" data-valid="mand"></td>' +
					'</tr>';
				$('input[name="serialnumbercount"]').val(i);
			}
			temp += '<tr><td colspan="6"><button type="button" style="margin-left: 300px;" class="btn btn-primary" ' +
				'onclick="Controlinventorystock(\'Back\',\'displayInventory\',\'serialNo\',\'EditInventory\','+id_inventory+','+id_inventory_m+','+qty+')"> Back </button>&nbsp;' +
				'<button type="button"  class="btn btn-primary" ' +
				'onclick="Controlinventorystock(\'Next\',\'displayInventory\',\'serialNo\',\'EditInventory\','+id_inventory+','+id_inventory_m+','+qty+')">Next </button></td></tr>' +
				'</table></center>';
			$('#serialNo').html(temp);
		}
	});
}
//
function GenerateDynamicSerialNumber(id_inventory , qty,nm_loc,assetprfx){
	
	var prifix=assetprfx;
	$.post('S_Add_To_Stock',{action : 'SerialNoGeneration' , id_inventory :id_inventory}, function(r){
		if(r.data){
			var slNo = r.data[0].slNo;
			var value='';
			var nmloc='';
			if(nm_loc=='F7 HEALTHCARE PVT LTD'){
				nmloc='F7HC';
			}
			if(nm_loc=='F7 MEDICAL EQUIPMENT'){
				nmloc='F7ME';
			}
			for(var i=0 ; i < qty ; i++){
				slNo=(parseInt(slNo)).toLocaleString('en-US', {minimumIntegerDigits: 4, useGrouping:false});
				
				value = nmloc.trim()+'-'+prifix.trim()+'-'+slNo;
				
				$('input[name="sno'+i+'"]').val(value);
				
				
				slNo++;
			}
		}
		
		
	},'json');
	
	
	
}
function DuplicateChecking1(ConstantVal, countLoop) {
	debugger;
    $('.removeErr').removeClass('error');
    var t = true;
	var pattern = /^[A-Za-z0-9-]+$/;
	debugger;
	if(countLoop==0){
		
		 var val = $('input[name="sno' + countLoop + '"]').val();
       
		if(pattern.test(val)!=true){
			t=false;
			//alert("Please provide valid characters i.e. alphabets, digits, dash (-).");
			 alert("space not allowed.");
			
			$('input[name="sno' + i + '"]').val('');
            $('input[name="sno' + i + '"]').addClass('error');
            $('input[name="sno' + i + '"]').focus();
		}
	}
    for (var i = 0; i < countLoop; i++) {
        t = true;
        var val = $('input[name="sno' + i + '"]').val();
        //var sapNo = $('input[name="sapNo' + i + '"]').val();
		if(pattern.test(val)!=true){
			t=false;
			//alert("Please provide valid characters i.e. alphabets, digits, dash (-).");
			 alert("space not allowed.");
			
			$('input[name="sno' + i + '"]').val('');
            $('input[name="sno' + i + '"]').addClass('error');
            $('input[name="sno' + i + '"]').focus();
		}
        else if (val != "") {
            if (val == ConstantVal) {
                t = false;
              alert("Please enter the unique number in the field.");
	           
                
                $('input[name="sno' + i + '"]').val('');
                $('input[name="sno' + i + '"]').addClass('error');
                $('input[name="sno' + i + '"]').focus();
            }
        }
    }
    return t;
}
function CheckSerialNoAndSapNo(SerialVal,callback) {
	var t = true;
	debugger;
	$('.removeErr').removeClass('error');
	$.post('S_Add_To_Stock', { action: 'CheckSlAndSpNo', SerialVal: SerialVal}, function(r) {
		if (r.data == 2) {
			t = false;
			var count = (r.Count) - 1;
			alert('Value already exist.');
			$('input[name="sno' + count + '"]').val('');
			$('input[name="sno' + count + '"]').addClass('error');
			$('input[name="sno' + count + '"]').focus();
		}
		callback(t);
	}, 'json');
}


function Controlinventorystock(Action, DisplayDiv, HideDiv, HideDiv2, id_inventory, id_inventory_m, qty) {
	SessionCheck(function(ses) {
		if (ses) {
			
			if (Action == 'Back') {
				$('#' + HideDiv).hide();
				$('#' + HideDiv2).hide();
				$('#' + DisplayDiv).show();
				
				
			}
			if (Action == 'Cancel') {
				
				$('select[name="warr_amc"]').val('O').attr('selected', true);
				$('input[name="warr_amc_day"]').val('');
				$('input[name="dt_amc_start"]').val('');
				$('input[name="dt_amc_exp"]').val('');
				$("#amcyearwarr").hide();	
				$("#waramcday").hide();
				$(".amcAddToStockCommonClass").hide();
				$('input[name="nm_ven"]').val('NA');
				$('input[name="ven_gst_no"]').val('NA');
				$('input[name="remarks"]').val('NA');
				
/*				$("#amcyearwarr").hide();
				$("#waramcday").hide();
				$("#startdateamc").hide();
				$("#amcYeardatepicker").hide();
				$("#endatefield").hide();
				$("#endateamc").hide();
				$(".amcAddToStockCommonClass").hide();	*/
				$('#' + HideDiv).show();
				$('#' + DisplayDiv).hide();
				$('#' + HideDiv2).hide();
				


				
			}
			if (Action == 'Next') {
				//debugger;
				var t = 1, SerialVal = '';
				$('input[name="SerialVal"]').val('');
				for (var i = 0; i < qty; i++)
					var t = 1, SerialVal = '';
				$('input[name="SerialVal"]').val('');
				for (var i = 0; i < qty; i++) {
					$('input[name="sno' + i + '"]').removeClass('error');
					
					
					val= $('input[name="sno' + i + '"]').val().trim();
				
					if (val == "") {
						t = 0;
						alert("Mandatory Field");
						$('input[name="sno' + i + '"]').focus();
						$('input[name="sno' + i + '"]').addClass('error');
						exit();
					}
					if (val == "") {
						t = 0;
						alert("Mandatory Field");
						$('input[name="sno' + i + '"]').focus();
						$('input[name="sno' + i + '"]').addClass('error');
						exit();
					}
					else {
                        var st = DuplicateChecking1(val, i);
                        if (!st) {
                            t = 0;
                            exit();
                        }
                    }
					if (SerialVal == "") {
						SerialVal += val;
						}
					else {
						SerialVal += ',,' + val
						}
				}

				if (t == 1) {
					CheckSerialNoAndSapNo(SerialVal,function(status) {
						if (status) {
							$('input[name="SerialVal"]').val(SerialVal);
							debugger;
							AddInputValueForAddToStore(id_inventory_m, id_inventory, qty);
							//debugger;
							$('#' + HideDiv).hide();
							$('#' + DisplayDiv).hide();
							$('#' + HideDiv2).show();
						}
					});
				}
			}
			if (Action == 'Save') {
				var t = false;
				t = ValidationForm("submitInventory");
				debugger;
				var warr_amc = $('select[name="warr_amc"]').val();
				debugger;
					console.log($('input[name="dt_amc_start"]').val());
					console.log($('input[name="dt_amc_exp"]').val());
					console.log($('input[name="warr_amc_day"]').val());
				if (warr_amc == 'A' || warr_amc == 'W') {
					//t = ShowRowColumnValidation('amcAddToStockCommonClass1');
				}
				else {
				
					t = true;
					//$('#warr_amc_day').removeAttr('data-valid')
					$('input[name="dt_amc_start"]').val('');
					$('input[name="dt_amc_exp"]').val('');
					$('input[name="warr_amc_day"]').val('0');
				}
			
				var x = $('#submitInventory').serialize();
				
				if (t) {
					$('.store').attr('disabled', 'disabled');
					console.log(x);
					$.post('S_Add_To_Stock', x, function(r) {
						if (r.data) {
							t = true;
						}
						else {
							t = false;
							bootbox.alert("Data is not inserted in database please try again.");
						}
						if (t) {
							bootbox.alert("Successfully added.");
							window.location = $('.addtostock_event').attr('href');
						}
						$('.store').removeAttr('disabled');
					}, 'json');
				}
			}
		}
	});
}





function AddInputValueForAddToStore(id, id_inventory, qty) {
	SessionCheck(function(ses) {
		if (ses) {
			debugger;
			$.post('S_Add_To_Stock', { action: 'Editforaddtostock', id: id, id_inventory: id_inventory }, function(r) {
				if (r.data) {
					for (var i = 0; i < r.data.length; i++)
						for (var key in r.data[i]) {
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
						}
					
					
					if (r.data[0].dtInv == '01/01/1900') {
						$('input[name="dt_inv_dummy"]').val('NA');
					}
					else {
						$('input[name="dt_inv_dummy"]').val(r.data[0].dtInv);
					}
					if (r.data[0].dttempInv == '01/01/1900') {
						$('input[name="dttemp_no_inv"]').val('NA');
					}
					else {
						$('input[name="dttemp_no_inv"]').val(r.data[0].dttempInv);
					}
					
					console.log(r.data[0].nm_acc_asset);
							
					console.log(r.data[0].qty_fill);
					console.log(r.data[0].qty_empty);
				    $('input[name="id_product"]').val(r.data[0].id_product);
	                $('input[name="id_loc"]').val(r.data[0].id_loc);
					$('input[name="id_subl"]').val(r.data[0].id_subl);
				    $('input[name="typeprod"]').val(r.data[0].nm_acc_asset);
			        $('input[name="qty_fill"]').val(r.data[0].qty_fill);
				    $('input[name="qty_empty"]').val(r.data[0].qty_empty);
                   	$('input[name="id_inventory_m"]').val(r.data[0].id_inventory_m);
					$('input[name="id_inventory"]').val(r.data[0].id_inventory);
					$('input[name="qty_recv"]').val(qty);
					$('input[name="tt_un_prc"]').val(qty * (r.data[0].un_prc));
					$('input[name="un_prc"]').val(r.data[0].un_prc);
					$('input[name="total_prc"]').val(r.data[0].gr_tot);
					$('input[name="cst_prod"]').val((r.data[0].un_prc) * (1.00));
					$('input[name="cst_prod_add"]').val(r.data[0].others_cst);
					$('input[name="dt_inv"]').val(r.data[0].dtInv);
					$('input[name="dt_temp_inv"]').val(r.data[0].dttempInv);
					$('input[name="no_inv"]').val(r.data[0].no_inv);
					$('input[name="temp_no_inv"]').val(r.data[0].temp_no_inv);
					$('input[name="nm_product"]').val(r.data[0].product);
					$('input[name="ds_prod"]').val(r.data[0].ds_assetdiv);
					$('input[name="mfr"]').val(r.data[0].mfr_assetdiv);
					
				}
				else
					bootbox.alert("Try again.");
			}, 'json');
		}
	});
}


//display instock
function DisplayInStock(servletName, displayContent) {
	SessionCheck(function(ses) {
		if (ses) {
			 
		let count=0;
		 
			
			$.post(servletName, { action: 'DisplayInStock' }, function(r) {
				//var list = '<thead><tr class="new">' +
				var list
				//
				
					
				
					
				
					list = '<thead><tr class="new">' +
					'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Product Name</center></strong></td>' +
					'<td><strong><center>Product Prefix</center></strong></td>' +
					'<td><strong><center>Available Quantity/Fill Quantity</center></strong></td>' +
					'<td><strong><center>Empty Quantity</center></strong></td>' +
					'<td><strong><center>Rental On Quantity</center></strong></td>' +
					'<td><strong><center>Product On Service Quantity</center></strong></td>' +
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'</tr></thead><tbody>';
			
					 
				if (r.data.length > 0) {
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						//
						//list = list + '<tr>' +
					
					    debugger;
                          if(params.tot_rent_aval_qty=='Null'||params.tot_rent_aval_qty==undefined){
	                               
                                     tot_rentavalqty='0';
                                 } else{tot_rentavalqty=params.tot_rent_aval_qty;
                                 }
                       if(params.tot_servc_aval_qty=='Null'||params.tot_servc_aval_qty==undefined){
	                               
                                     tot_servcavalqty='0';
                                 }else{
	                         tot_servcavalqty=params.tot_servc_aval_qty;
                                 }
                       if(params.tot_aval_empty=='Null'||params.tot_aval_empty==undefined){
	                               
                                     tot_avalempty='0';
                                 }else{
	                         tot_avalempty=params.tot_aval_empty;
                                 }
		                     list = list + '<tr>' +
		                     '<td><center>' + ++count + '</center></td>' +
						 	'<td><center>' + params.nm_assetdiv + '</center></td>' +
							'<td><center>' + params.asset_prod_prefix + '</center></td>' +
							'<td><center>' + params.tot_aval_qty + '</center></td>' +
							'<td><center>' + tot_avalempty + '</center></td>' +
							'<td><center>' + tot_rentavalqty + '</center></td>' +
							'<td><center>' + tot_servcavalqty + '</center></td>' +
							'<td><center>' + params.nm_loc + '</center></td>'+
							'<td><center>' + params.nm_subl + '</center></td>'+
							
						'</tr>';
                      
	
					    
					}
				}
				else {
					list += '<tr><td colspan="3"><strong><center>No data found.</center></strong></td></tr>';
					$('#invstockForDisplaytd').html('</tbody>' + list);
				}
				$('#invstockForDisplaytd').html('</tbody>' + list);
				
				getButtonsForListView('invstockForDisplaytd','store_list');

			}, 'json');
			/*getButtonsForListView('invstockForDisplaytd','store_list');
*/		} 
	});
}





//Getting Tax2 on selection Tax1
function getTax2(event) {
	console.log("hello");
	var domElement = $(event.target || event.srcElement);
	var id_tax1 = $('select[name="id_tax1' + domElement.attr('patelUnPrc') + '"]').val();
	$.post('M_Tax', { action: 'getTax2', id_tax1: id_tax1 }, function(r) {
		if (r.data) {
			$('select[name="id_tax2' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].id_tax);
		}
		TaxCalculation(event, 'id_tax1', 'tax_val1');
	}, 'json');
}

//Getting Model Name from JSON
var options = {
	url: "InvoiceScanFile/Model.json",
	getValue: "nm_assetdiv",
	template: {
		type: "custom",
		method: function(value, item) {
			return value;
		}
	},
	list: {
		maxNumberOfElements: 50,
		match: {
			enabled: true
		}
	},
};

//amc date
$(".amctDatepicker").datepicker({
	yearRange: '1985:2025',
	changeMonth: true,
	changeYear: true,
	dateFormat: "dd/mm/yy",
	autoSize: true,
	altFormat: "dd/mm/yy",
	onSelect: function(selected, evnt) {
		$(this).removeClass('error');
		var dt_amc_start = $('input[name="dt_amc_start"]').val();
		var dt_amc_start1 = $('input[name="dt_amc_start"]').val();
		var dt_end = $(this).val();
		if (dt_amc_start == '') {
			alert('First filled start  date.');
			$('input[name="dt_amc_start"]').focus();
			$('input[name="dt_amc_start"]').addClass('error');
			$('input[name="dt_amc_start"]').val('');
			$(this).val('');
			exit(0);
		}
		else {
			var temp_strt = dt_amc_start.split("/");
			var temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
			var temp_end = dt_end.split("/");
			var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
			dt_amc_start = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
			dt_end = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
			if (dt_amc_start > dt_end) {
				alert('End date should be greater or equal to start date : ' + dt_amc_start1);
				$(this).focus();
				$(this).val('');
				$(this).addClass('error');
				exit(0);
			}
		}
	}
});


//function calculateAmcDate(){
	/*var days=parseInt($('input[name="warr_amc_day"]').val());*/

	//Check AMC start date with Invoice date
	$("#amcYeardatepicker").datepicker({
    	yearRange: '1985:2025',
    	 changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
        onSelect: function(dateText, instance) {
        	 date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
        	 var dt_inv=$('input[name="dt_inv"]').val();

     			var dt_amc_start=$('input[name="dt_amc_start"]').val();
		
     			if(dt_amc_start < dt_inv){
     		alert("'DATE MUST BE GREATER OR EQUAL TO INVOICE DATE. '");
     		$('#amcYeardatepicker').focus();
     		$('#amcYeardatepicker').val('');
     		$('#amcYeardatepicker').addClass('error');
     		}
     		else
     			{
	 var day=$('input[name="warr_amc_day"]').val();
           if(day==''){
	alert("First enter no of years . ");
     		$('#warr_amc_day').focus();
     		$('#amcYeardatepicker').val('');
     		$('#warr_amc_day').addClass('error');
}
          else{
	    var days=parseInt($('input[name="warr_amc_day"]').val());
     			$('#amcYeardatepicker').removeClass('error');
        	 $('input[name="dt_amc_start"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
             date.setDate(date.getDate() );
            date.setMonth(date.getMonth() + days*12);
              $('input[name="dt_amc_exp"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
}
	     
	        
          
     			}
        }
    });
	
//}

function calculateAmcDate(){
	 $('input[name="dt_amc_start"]').val('');
	 $('input[name="dt_amc_exp"]').val('');
	var days=parseInt($('input[name="warr_amc_day"]').val());
	
	}
//Check AMC start date with Invoice date
	/*$("#amcYeardatepicker").datepicker({
    	yearRange: '1985:2025',
    	 changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
        onSelect: function(dateText, instance) {
        	 date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
        	 var dt_inv=$('input[name="dt_inv"]').val();
     			var dt_amc_start=$('input[name="dt_amc_start"]').val();
     			if(dt_amc_start < dt_inv){
     		alert("'DATE MUST BE GREATER OR EQUAL TO INVOICE DATE. '");
     		$('#amcYeardatepicker').focus();
     		$('#amcYeardatepicker').val('');
     		$('#amcYeardatepicker').addClass('error');
     		}
     		else
     			{
     			$('#amcYeardatepicker').removeClass('error');
        	 $('input[name="dt_amc_start"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
             date.setDate(date.getDate() - 1);
              date.setMonth(date.getMonth() + 2*12);
              $('input[name="dt_amc_exp"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
     			}
        }
    });*/

//Checking Invoice Date Based on PO date
/*function CheckContraDate() {
	var dt_inv = $('input[name="dt_inv"]').val();
	var dt_inv1 = $('input[name="dt_inv"]').val();
	var dt_po = $('input[name="dt_po"]').val();
	var temp_strt = dt_inv.split("/");
	var temp_dt_strt = new Date(temp_strt[2],
		temp_strt[1] - 1, temp_strt[0]);
	var temp_end = dt_po.split("/");
	var temp_dt_end = new Date(temp_end[2],
		temp_end[1] - 1, temp_end[0]);
	dt_inv = $.datepicker.formatDate(
		'yy-mm-dd', temp_dt_strt);
	dt_po = $.datepicker.formatDate('yy-mm-dd',
		temp_dt_end);
	if (dt_inv < dt_po) {
		alert("'INVOICE DATE MUST BE GREATER OR EQUAL TO PO DATE. '");
		$('#dt_inv').focus();
		$('#dt_inv').val('');
		$('#dt_inv').addClass('error');
	}
	else {
		$('#dt_inv').removeClass('error');
	}
}*/

//Checking GRN Date Based on Invoice date
/*function CheckgrnaDate() {
	var dt_inv = $('input[name="dt_inv"]').val();
	var dt_po = $('input[name="dt_grn"]').val();
	var temp_strt = dt_inv.split("/");
	var temp_dt_strt = new Date(temp_strt[2],
		temp_strt[1] - 1, temp_strt[0]);
	var temp_end = dt_po.split("/");
	var temp_dt_end = new Date(temp_end[2],
		temp_end[1] - 1, temp_end[0]);
	dt_inv = $.datepicker.formatDate(
		'yy-mm-dd', temp_dt_strt);
	dt_po = $.datepicker.formatDate('yy-mm-dd',
		temp_dt_end);
	if (dt_inv < dt_po) {
		alert("'GRN DATE MUST BE GREATER OR EQUAL TO INVOICE DATE. '");
		$('#dt_inv').focus();
		$('#dt_inv').val('');
		$('#dt_inv').addClass('error');
	}
	else {
		$('#dt_inv').removeClass('error');
	}
}*/

//Check duplicate invoice Number
function checkInvoiceNo() {
	var t = 1;
	t = $('input[name="invoiceCheck"]').val();
	if (t == 0) { 
	}
}

//Auto Select Group,Sub-Group,Asset Type
function changeEventHandlerprod(event) {
	var domElement =$(event.target || event.srcElement);
    var nm_prod = $('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val();
	console.log(nm_prod);
	 $.post('M_Asset_Div',{action : 'GetProductdetails', id:nm_prod},function (r){
			if(r.data)
				{
					//
				$('select[name=id_grp'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_assetdiv + ']').attr('selected',true);
				/*SubDropDownDataDisplay(r.data[0].id_assetdiv,'subGroupDataForInvoice'+domElement.attr('patelUnPrc')+'','M_Subasset_Div',function (status){
					$('select[name=id_sgrp'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_s_assetdiv + ']').attr('selected',true);
					$('select[name=typ_asst'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].typ_asst + ']').attr('selected',true);
					});*/
				}
	 },'json');
}
$(function() {
		/*$('.hideRowCol').hide();
		$(".invoiceDatepicker")
				.datepicker(
						{
							yearRange : '1985:2025',
							changeMonth : true,
							changeYear : true,
							dateFormat : "dd/mm/yy",
							autoSize : true,
							altFormat : "dd/mm/yy",
							onSelect : function(selected, evnt) {
								$(this).removeClass('error');
								var dt_inv = $('input[name="dt_inv"]').val();
								var dt_inv1 = $('input[name="dt_inv"]').val();
								var dt_po = $(this).val();
								if (dt_inv == '') {
									alert('First filled invoice date.');
									$('input[name="dt_inv"]').focus();
									$('input[name="dt_inv"]').addClass('error');
									$('input[name="dt_inv"]').val('');
									$(this).val('');
									exit(0);
								} else {
									var temp_strt = dt_inv.split("/");
									var temp_dt_strt = new Date(temp_strt[2],
											temp_strt[1] - 1, temp_strt[0]);
									/*var temp_end = dt_po.split("/");
									var temp_dt_end = new Date(temp_end[2],
											temp_end[1] - 1, temp_end[0]);*/
									//dt_inv = $.datepicker.formatDate(
										//	'yy-mm-dd', temp_dt_strt);
									/*dt_po = $.datepicker.formatDate('yy-mm-dd',
											temp_dt_end);*/
									/*if (dt_inv < dt_po) {
										alert('P.O date should be less or equal to invoice date : '
												+ dt_inv1);
										$(this).focus();
										$(this).val('');
										$(this).addClass('error');
										exit(0);
									}
								}
							}
						});*/
		var currentDate = new Date();
		$(".datepicker").datepicker({
			yearRange : '1985:2025',
			changeMonth : true,
			changeYear : true,
			dateFormat : "dd/mm/yy",
			autoSize : true,
			altFormat : "dd/mm/yy",
		});
	//$('input[name="dt_inv"]').datepicker("setDate", currentDate);
		$('input[name="dt_to"]').datepicker("setDate", currentDate);
		 currentDate.setMonth(currentDate.getMonth() - 1);
		$('input[name="dt_frm"]').datepicker("setDate", currentDate);
	
		
		
	});
	/*jQuery("input#fileID").change(function() {
		var formData = new FormData();
		formData.append('file', $('#fileID').get(0).files[0]);
		$.ajax({
			url : 'Upload_File',
			type : 'POST',
			data : formData,
			async : false,
			cache : false,
			contentType : false,
			dataType : "json",
			processData : false,
			mimeType : "multipart/form-data",
			success : function(r) {
				$('input[name="upload_inv"]').val(r.upload_inv);
			}
		}, 'json');
	});*/
	




