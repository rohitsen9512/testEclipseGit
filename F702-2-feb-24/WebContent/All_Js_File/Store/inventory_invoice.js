$(function() {
	$("#menu").menu();
});

//Main Function For Save,Update,Cancel Invoice
function ControlInv(action, displayContent, createDetails, formName, servletName) {
	SessionCheck(function(ses) {
		if (ses) {
			 debugger;
			var no_inv =$('input[name="no_inv"]').val();
			var temp_no_inv =$('input[name="temp_no_inv"]').val();
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			$('input[name="invoiceCheck"]').val('0');
			if (action == 'Create') {
				
				$('.readbaledata').each(function() {
					$(this).removeAttr('readonly', 'readonly');
					if ($(this).is("select")) {
						$(this).attr("disabled", false);
					}
				});
				$('button[name="save"]').removeClass('hideButton');
				$('button[name="update"]').addClass('hideButton');
				//$('button[name="AddStock"]').addClass('hideButton');
				$('button[name="create btn"]').addClass('hideButton');
				$('button[name="delete"]').addClass('hideButton');
				$('input[name="action"]').val('Save');
				$('input[name="id"]').val("0");
				$('#' + formName)[0].reset();
				$('#' + displayContent).hide();
				$('#' + createDetails).show();
				$('#createAccessory').show();
				$('#displayAccessory').hide();
				$.post('M_Emp_User', { action: 'LoginUserDetails' }, function(r) {
					if (r.data) {
						for (var i = 0; i < r.data.length; i++) {
													
								/*DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForInvoice', 'M_Subloc', function(status) {
								if (status) {					
						$('select[name=id_subl] option[value=' + r.data[0].id_subl + ']').attr('selected', true);
						}
						 });		*/				
								
						}
					}
					
					$(".datepicker").datepicker({
			yearRange : '1985:2025',
			changeMonth : true,
			changeYear : true,
			dateFormat : "dd/mm/yy",
			autoSize : true,
			altFormat : "dd/mm/yy",
			 minDate: 0
		});
	currentDate = new Date();
		$('input[name="dt_inv"]').datepicker("setDate", currentDate);
		$('input[name="dt_temp_inv"]').datepicker("setDate", currentDate);
				}, 'json');
			}
			if (action == 'Cancel') {
				$('#displayAccessory').hide();
				$('#createAccessory').hide();
				$('#' + createDetails).hide();
				$('#' + displayContent).show();
				DisplayInvoice(servletName, displayContent, createDetails, '', 'invoiceForDisplay');
				window.location = $('.invoice_event').attr('href');
				}
				debugger;
			
				if (action == 'Save') {
					$('button[name="save1"]').removeClass('hideButton');
					$('input[name="action"]').val('Save');
					DataInsertUpdateForAsset("Save", displayContent, createDetails, formName, servletName);
		
				/*if(no_inv=='NA'&&temp_no_inv=='NA'){
				alert("Please Provide any One Invoice Number");
			      
			    }
			else if(no_inv!='NA'&&temp_no_inv!='NA'&&temp_no_inv!=''){
				alert("Can't Provide Both Invoice Number");
				  t=false;
			}
			if(no_inv!='NA'||no_inv!=''||temp_no_inv=='NA'||temp_no_inv==''){
				
				  t=true;
			}*/
					
				}
				 else if(action == 'Update')  {
				
					$('input[name="action"]').val('Update');
					DataInsertUpdateForAsset("Update", displayContent, createDetails, formName, servletName);
		
				}
			
		}
	});
}

//For Save & Update Invoice AddtoStock
function DataInsertUpdateForAsset(action, displayContent, createDetails, formName, servletName) {
	SessionCheck(function(ses) {
		if (ses) {
			debugger;
			
			t = false;
			 
			t = ValidationForm(formName);
	var no_inv=$('input[name="no_inv"]').val();
	var temp_no_inv=$('input[name="temp_no_inv"]').val();
	
	//if(t){
		/*if(no_inv=='NA'&&temp_no_inv=='NA'){
				alert("Please Provide any One Invoice Number");
			  t=false;
	          $('#InvoiceNum').addClass('error');
                $('#InvoiceNum1').addClass('error');
			}
	     if(no_inv!='NA'&&temp_no_inv!='NA'){
				alert("Can't Provide Both Invoice Number");
				  t=false;
		 $('#InvoiceNum').addClass('error');
                $('#InvoiceNum1').addClass('error');
			}*/
			/*if(no_inv!='NA'||no_inv!=''&&temp_no_inv=='NA'||temp_no_inv==''){
				
				  t=true;
			}*/
		//}	
				
			if (t) {
				$('.inv').attr('disabled', 'disabled');
			/*	$('button[name="save"]').removeClass('hideButton');
				$('button[name="update"]').addClass('hideButton');*/
				
					 $('.form-control').removeAttr('disabled');
				$('#InvoiceNum1').removeAttr('disabled');
			    $('#dt_temp_inv').removeAttr('disabled');
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
						//$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
					}
					if(qty_fill == '' ||  qty_fill == undefined )	
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
					}
					else if(id_tax1 == '' ||  id_tax1 == undefined )
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.inv').removeAttr('disabled');
						$('.patelTax2').attr('disabled', 'disabled');
						$('select[name="id_tax1'+i+'"]').addClass('error');
						//$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
					}
					else
					{
						$('input[name="qty_fill'+i+'"]').removeClass('error');
						$('input[name="qty_empty'+i+'"]').removeClass('error');
						$('select[name="id_tax1'+i+'"]').removeClass('error');
						$('.patelTax2').attr('disabled', 'disabled');
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
						$('.patelTax2').attr('disabled', 'disabled');
					}
			if(t==1)
			{
				debugger;
				$.post(servletName, x, function(r) {
					if (r.data == 1) {
						$('#createAccessory').hide();
						$('.readbaledata').each(function() {
							$(this).attr('readonly', 'readonly');
							if ($(this).is("select")) {
								$(this).attr("disabled", true);
							}
							$('.readbaledata').each(function() {
								$(this).attr("disabled", true);
							});
						});
						debugger;
						if(action == 'Save'){
						alert("Invoice has created successfully.");
						window.location = $('.invoice_event').attr('href');
						}
						if(action == 'Update'){
						alert("Invoice has updated successfully.");
						window.location = $('.invoice_event').attr('href');
						}
					}
					else if (r.data == 2) {
						bootbox.alert("Invoice number is already exists.");
						$('#InvoiceNum').addClass('error');
					}
					else {
						
						bootbox.alert("Try again.");
					}
					$('.inv').removeAttr('disabled');
					$('.patelTax2').attr('disabled', 'disabled');
				}, 'json');
			}
			}
		}
	});
}





//For Modify Invoice
function EditInvoice(servletName, displayContent, createDetails, id,butType) {
	SessionCheck(function(ses) {
	debugger;
		if (ses) {
			$('input[name="invoiceCheck"]').val('1');
			
	      if(butType=='modi'){
				
			//$('button[name="AddStock"]').addClass('hideButton');
			$('button[name="update"]').removeClass('hideButton');
			$('#InvoiceNum1').attr('disabled', 'disabled');
			$('#dt_temp_inv').attr('disabled', 'disabled');
			}
			/*else{
				//
				$('.form-control').attr('disabled', 'disabled');
				
				
				$('button[name="save"]').addClass('hideButton');
				$('button[name="update"]').addClass('hideButton');
				$('button[name="create btn"]').addClass('hideButton');
				$('button[name="delete"]').addClass('hideButton');	
		

			 
		
			} this is for add to stock */
			

			$('button[name="save"]').addClass('hideButton');
			$('button[name="save1"]').addClass('hideButton');
			
			
			$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createAccessory').show();
			$('.hideinv').hide();
			var t = 0;
			var key1;
			$.post(servletName, { action: 'Edit', id: id }, function(r) {
				if (r.data) {
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							//
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
							
						
						}
					}
					
						$('input[name="temp_no_inv"]').val(r.data[0].temp_invNO);
			if(butType=='modi'){
						
					$('input[name="action"]').val("Update");
						
						}

					$('input[name="id"]').val(id);
						if (r.data[0].dtInv == '01/01/1900') {
						$('input[name="dt_inv"]').val('NA');
					}
					else {
						$('input[name="dt_inv"]').val(r.data[0].dtInv);
					}
					if (r.data[0].dttempInv == '01/01/1900') {
						$('input[name="dt_temp_inv"]').val('NA');
					}
					else {
						$('input[name="dt_temp_inv"]').val(r.data[0].dttempInv);
					}
					
				
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForInvoice', 'M_Subloc', function(status) {
						if (status) {
							
											$('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
											$('select[name=id_subl] option[value=' + r.data[0].id_subl + ']').attr('selected', true);
										
						}
					});
							
					dispalyLineItemInvoiceformodify(r);
				}
				else {
					bootbox.alert("Try again.");
				}
			
				
			}, 'json');
		}
	});
}

//Line items While Creating Invoice
function dispalyLineItemInvoice(action, callback) {
	if (action == 'New') {
		var list = '<tr>' +
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' +
			'<tr class="tableHeader info">' +
			'<td ><strong><center>Product<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Product Code<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Product Description<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Manufature<a href=#></a></center></strong></td>' +
			'<td ><strong><center>HSN/SAC-Code<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Asset Prefix<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Fill Quantity<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Empty Quantity<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Total Quantity<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>' +
		     '<td ><strong><center>Others<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Discount<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Total<a href=#></a></center></strong></td>' +
			'</tr>';
		for (var i = 0; i < 10; i++) {
			list += '<tr>' +
				/*' <td style="display:none;">' +
				'<select name="asset_consumable' + i + '"   style="width:107px" patelUnPrc="' + i + '" class="form-control" data-valid="mand">' +
				'<option value="ConG">Material Goods</option>' +
				'<option value="CapG">Capital Goods</option>' +
				'</select>' +
				'</td>' +*/
				'<input type="hidden" name="ConfigurableId' + i + '" value="No">' +
				'<td><center><input style="width: 120px !important;" type="text" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprod(event,\'qty_fill\',\'qty_empty\');"></center></td>' +
                 '<td><center><input style="width: 120px !important;" type="text"  name="ds_pro' + i + '" patelUnPrc="' + i + '"  class="form-control " readonly></center></td>' +
                 // '<td><center><input style="width: 120px !important;" type="text" name="prod_cd' + i + '" patelUnPrc="' + i + '"  class="form-control " ></center></td>' +
                  '<td><center><input style="width: 120px !important;" type="text" name="mfr' + i + '" patelUnPrc="' + i + '"  class="form-control " readonly></center></td>' +
                   '<td><center><input style="width: 120px !important;" type="text"  name="prod_hsncd' + i + '" patelUnPrc="' + i + '"  class="form-control " readonly></center></td>' +
                '<td><center><input style="width: 120px !important;" type="text"  name="asset_prfx' + i + '" patelUnPrc="' + i + '"  class="form-control " readonly></center></td>' + 			     
            '<td><center><input type="text"  id="qty_fill' + i + '" name="qty_fill' + i + '" style="width:60px;text-align: right;"  value="0" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTot(event,\'qty_fill\')" class="patelUnPrc data-valid=""></center></td>' +
			'<td><center><input type="text" id="qty_empty' + i + '"  name="qty_empty' + i + '" style="width:60px;text-align: right;"  value="0" class="form-control" patelUnPrc="' + i + '" onChange="calculateTot(event,\'qty_empty\')" class="patelUnPrc data-valid=""></center></td>' +
		   
 			'<td><center><input type="text"  name="qty' + i + '" style="width:80px; text-align: right;"  value="0" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>' +
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '"  value="0" style="width: 60px;text-align: right;" onChange="calculateTotfordecimal(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
				'<td><input type="text" name="others' + i + '" value="0" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotfordecimal(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '" class="form-control patelTax" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2"  disabled onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTotfordecimal(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
				'<td><center><input type="text"  name="gr_tot' + i + '" value="0" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				
				'<input type="hidden" name="count" value="' + i + '">' +
				'</tr>';
		}
		$('input[name="itemCount"]').val('10');
		$('input[name="grand_tot1"]').val('0.00');
		$('#accessoryDetails').html(list);
		DropDownDataDisplay("M_Tax", "taxDataForDropDown11", function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "patelTax2", function(status) {
					if (status) {
						/*DisplayDropDownDataForGroup('M_Asset_Div', 'groupDrop', 'CapG', function(status) {
						});*/
					}
				});
			}
		});
	} 
	//On clicking Add Button for Line Item
	else {
		var i = $('input[name="itemCount"]').val();
		var list = '<tr>' +
			'<input type="hidden" name="ConfigurableId' + i + '" value="No">' +
			'<td><center><input style="width: 120px !important;" type="text" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc"  onchange="changeEventHandlerprod(event,\'qty_fill\',\'qty_empty\');"></center></td>' +
		/*	'<td><center><select style="width: 120px !important;" id="assettypeDivForInvoice' + i + '" name="typ_asst' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80" >' +
			'<option value="">--Select--</option>' +
			'<option value="IT">IT</option>' +
			'<option value="NON-IT">NON IT</option>' +
				'<option value="Software">Software</option>' +
			'<option value="accessories">Accessories</option></select>' +
			'</center></td>' +
			'<td><center><select style="width: 120px !important;" id="assetDivForInvoice' + i + '" name="id_grp' + i + '" patelUnPrc="' + i + '"  class="form-control  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice' + i + '\',\'M_Subasset_Div\')">' +
			'<option value="">--Select--</option></select>' +
			'</center></td>' +
			'<td><center><select style="width: 120px !important;" id="subGroupDataForInvoice' + i + '" name="id_sgrp' + i + '" patelUnPrc="' + i + '"  class="form-control " data-valid=""  style="width:80" >' +
			'<option value="">--Select--</option></select>' +
			'</center></td>' +*/
			
             '<td><center><input style="width: 120px !important;" type="text"  name="ds_pro' + i + '" patelUnPrc="' + i + '"  class="form-control " readonly></center></td>' +
              
              '<td><center><input style="width: 120px !important;" type="text" name="mfr' + i + '" patelUnPrc="' + i + '"  class="form-control " readonly></center></td>' +
               '<td><center><input style="width: 120px !important;" type="text"  name="prod_hsncd' + i + '" patelUnPrc="' + i + '"  class="form-control " readonly></center></td>' +
               '<td><center><input style="width: 120px !important;" type="text"  name="asset_prfx' + i + '" patelUnPrc="' + i + '"  class="form-control "readonly></center></td>' + 
			  '<td><center><input type="text"  id="qty_fill' + i + '" name="qty_fill' + i + '" style="width:60px;text-align: right;"  value="0" class="form-control" patelUnPrc="' + i + '" onChange="calculateTot(event,\'qty_fill\')"  class="patelUnPrc" data-valid=""></center></td>' +  
			'<td><center><input type="text"   id="qty_empty' + i + '" name="qty_empty' + i + '" style="width:60px;text-align: right;"  value="0" class="form-control" patelUnPrc="' + i + '" onChange="calculateTot(event,\'qty_empty\')" class="patelUnPrc" data-valid=""></center></td>' +
			'<td><center><input type="text"  name="qty' + i + '" style="width:60px;text-align: right;"  value="0" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>' +
		    '<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '"   value="0" style="width: 60px;text-align: right;" onChange="calculateTotfordecimal(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
			'<td><input type="text" name="others' + i + '" value="0" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotfordecimal(event,\'others\')" class="form-control" ></td>' +
			'<td><center>' +
			'<select style="width: 120px;" name="id_tax1' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '" class="form-control patelTax" onChange="getTax2(event);">' +
			'</select>' +
			'</center></td>' +
			'<td><center>' +
			'<select style="width: 120px;" name="id_tax2' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2"  readonly onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')" >' +
			'</select>' +
			'</center></td>' +
			'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="0" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
			'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="0" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
			'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="0" onChange="calculateTotfordecimal(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
			'<td><center><input type="text"  name="gr_tot' + i + '" value="0" style="width:60px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
			'<input type="hidden" name="id_inventory' + i + '" value="">' +
			'<input type="hidden" name="count" value="' + i + '">' +
			'</tr>';
		$('input[name="itemCount"]').val(parseInt(i) + 1);
		$('#accessoryDetails').append(list);
		var dropDownId = "id_prod" + i + "";
		DropDownDataDisplay("M_Tax", "id_tax1" + i, function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "id_tax2" + i, function(status) {
					if (status) {
						/*DisplayDropDownDataForGroup("M_Asset_Div", "assetDivForInvoice" + i, "CapG", function(status) {
							if (status) {
							}
						});*/
					}
				});
			}
		});
	}
	//Auto Completion of Item Name
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
/*	var currentDate = new Date();
	$(".datepoinvoice").datepicker({
		yearRange: '1985:2025',
		changeMonth: true,
		changeYear: true,
		dateFormat: "dd/mm/yy",
		autoSize: true,
		altFormat: "dd/mm/yy",
	});
	console.log(currentDate);*/
}

//Line Item While modifying Invoice
function dispalyLineItemInvoiceformodify(r) {
	if (r.data) {
		
		var list = '<tr>' +
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' + '<tr class="tableHeader info">' +
			'<td ><strong><center>Product<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Product Code<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Product Description<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Manufature<a href=#></a></center></strong></td>' +
			'<td ><strong><center>HSN/SAC-Code<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Asset Prefix<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Fill Quantity<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Empty Quantity<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Quantity<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>' +
		     '<td ><strong><center>Others<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Discount<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Total<a href=#></a></center></strong></td>' +

			'</tr>';
		for (var i = 0; i < r.data.length; i++) {
			console.log('ss');
			//
			list += '<tr>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].product + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc"  onchange="changeEventHandlerprod(event,\'qty_fill\',\'qty_empty\');"></td>' +
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
				'<td><center><input style="width: 120px !important;" type="text"  name="ds_pro' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].ds_pro + '" class="form-control " readonly ></center></td>' +
              
              '<td><center><input style="width: 120px !important;" type="text" name="mfr' + i + '" patelUnPrc="' + i + '"  value= "' + r.data[i].mfr + '"  class="form-control "readonly ></center></td>' +
               '<td><center><input style="width: 120px !important;" type="text"  name="prod_hsncd' + i + '" patelUnPrc="' + i + '"  value="' + r.data[i].prod_hsncd + '" class="form-control " readonly></center></td>' +
               '<td><center><input style="width: 120px !important;" type="text"  name="asset_prfx' + i + '" patelUnPrc="' + i + '"  value="' + r.data[i].asset_prfx + '" class="form-control " readonly></center></td>' + 			     
				  '<td><center><input type="text"  id="qty_fill' + i + '" name="qty_fill' + i + '" style="width:60px;text-align: right;"  value="' + r.data[i].qty_fill + '"  class="form-control" patelUnPrc="' + i + '" value="0" onChange="calculateTot(event,\'qty_fill\')"  class="patelUnPrc data-valid=""></center></td>' +  
			'<td><center><input type="text"   id="qty_empty' + i + '" name="qty_empty' + i + '" style="width:60px;text-align: right;"  value="' + r.data[i].qty_empty + '"  class="form-control" patelUnPrc="' + i + '" value="0" onChange="calculateTot(event,\'qty_empty\')" class="patelUnPrc data-valid=""></center></td>' +
				'<td><center><input type="text"  name="qty' + i + '" style="width:60px;text-align: right;"  value="' + r.data[i].qty + '" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>' +
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotfordecimal(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
				'<td><input type="text" name="others' + i + '" value="' + r.data[i].others + '" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotfordecimal(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="' + r.data[i].id_tax1 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '" class="form-control patelTax" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="' + r.data[i].id_tax2 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2"  disabled onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')" >' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val1 + '" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val2 + '" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].buyback + '" onChange="calculateTotfordecimal(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
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
									for (var i = 0; i < r.data.length; i++) {
										params = r.data[i];
										for (var key in r.data[i]) {
											if (key == 'id_tax1') {
												$('select[name=id_tax1' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
											if (key == 'id_tax2') {
												$('select[name=id_tax2' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
										}
									}
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

//Deletion of Invoice
function DeleteInvoice(servletName, displayContent, createDetails, id) {
	SessionCheck(function(ses) {
		if (ses) {
			bootbox.confirm("Are you sure?", function(result) {
				if (result) {
					//
					$.post(servletName, { action: 'Delete', id: id }, function(r) {
						if (r.data == 1) {
							//
							window.location = $('.invoice_event').attr('href');
							DisplayInvoice(servletName, displayContent, createDetails, '', 'invoiceForDisplay');
						}
						/*if (r.data == 2) {
							bootbox.confirm("If you delete this , then all aseet will be deleted related to this invoice.", function(result1) {
								if (result1) {
									$.post(servletName, { action: 'Delete', id: id, id_inv_m: '0' }, function(r) {
										if (r.data == 1) {
											window.location = $('.invoice_event').attr('href');
											DisplayInvoice(servletName, displayContent, createDetails, '', 'invoiceForDisplay');
										}
									}, 'json');
								}
							});
						}*/
					}, 'json');
				}
			});
		}
	});
}

//Display List View of Invoice
function DisplayInvoice(servletName, displayContent, createDetails, no_inv, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			 var dtinvoice='';
		 var dtTEMPinv='';
			/*var button1='Modify';
			var button2='AddToSTock';*/
	
				var searchWord = $('input[name="searchWord"]').val();
			let count=0;
			
			//
			$.post(servletName, { action: 'Display', no_inv: no_inv,searchWord:searchWord }, function(r) {
				var list = '<thead><tr class="new">' +
				'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Invoice Number</center></strong></td>' +
					'<td><strong><center>Invoice Date</center></strong></td>' +
					'<td><strong><center>Temporary Invoice Number</center></strong></td>' +
					'<td><strong><center>Temporary Invoice Date</center></strong></td>' +
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Modify</center></strong></td>' +
					//'<td><strong><center>Add To Stock</center></strong></td>' +
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
						
						
						
						list = list + '<tr>' +
						'<td><center>' + ++count + '</center></td>' +
							//'<td><center><a class="alertlink" href="#" onclick="EditInvoice(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',' + params.id_inventory_m + ')">' + params.no_inv + '</a></center></td>' +
							'<td><center>' + params.no_inv + '</center></td>' +
							'<td><center>' + dtinvoice + '</center></td>' +
							'<td><center>' + params.temp_no_inv + '</center></td>' +
							'<td><center>' + dtTEMPinv + '</center></td>' +
								'<td><center>' + params.nm_loc + '</center></td>'+
							'<td><center>' + params.nm_subl + '</center></td>'+
                             '<td><center><a class="alertlink" href="#" onclick="EditInvoice(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',\'' + params.id_inventory_m + '\',\'modi\');">' +'Modify'+ '</a></center></td>' +
						    // '<td><center><a class="alertlink" href="#" onclick="EditInvoice(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',\'' + params.id_inventory_m + '\',\'addstock\');">' +'Add To Stock'+ '</a></center></td>' +
	
	
						'</tr>';
					    
					}
				}
				else {
					list += '<tr><td colspan="5"><strong><center>No data found.</center></strong></td></tr>';
					$('#invoiceForDisplaytd').html('</tbody>' + list);
				}
				$('#invoiceForDisplaytd').html('</tbody>' + list);
				getButtonsForListView('invoiceForDisplaytd','inv_List');
			}, 'json');
		} 
	});
}











//Getting Tax2 on selection Tax1
function getTax2(event) {
	debugger;
	var domElement = $(event.target || event.srcElement);
	var id_tax1 = $('select[name="id_tax1' + domElement.attr('patelUnPrc') + '"]').val();
	if(id_tax1==""){
		$('select[name="id_tax2' + domElement.attr('patelUnPrc') + '"]').val('');
		$('input[name="tax_val1' + domElement.attr('patelUnPrc') + '"]').val('0');
		$('input[name="tax_val2' + domElement.attr('patelUnPrc') + '"]').val('0');
		$('input[name="gr_tot' + domElement.attr('patelUnPrc') + '"]').val('0');
		TaxCalculation(event);
		
		
		
	}
	else{
		$.post('M_Tax', { action: 'getTax2', id_tax1: id_tax1 }, function(r) {
		if (r.data) {
			$('select[name="id_tax2' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].id_tax);
		}
		TaxCalculation(event, 'id_tax1', 'tax_val1');
	}, 'json');
}
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








//Check duplicate invoice Number
function checkInvoiceNo() {
	var t = 1;
	t = $('input[name="invoiceCheck"]').val();
	if (t == 0) { 
	}
}

//Auto Select Group,Sub-Group,Asset Type
function changeEventHandlerprod(event,Id,Id1) {
	var domElement =$(event.target || event.srcElement);
	     var IdName= Id + domElement.attr('patelUnPrc');
         var IdName1= Id1 + domElement.attr('patelUnPrc');
    var nm_prod = $('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val();
	console.log(nm_prod);
	 $.post('M_Asset_Div',{action : 'GetProductdetails', id:nm_prod},function (r){
			if(r.data)
				{
				  console.log(r.data);
		console.log(r.data[0].ds_assetdiv);
		
				//$('select[name=id_prod'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_assetdiv + ']').attr('selected',true);
				
		     $('input[name="ds_pro' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].ds_assetdiv);
			   $('input[name="mfr' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].mfr_assetdiv);
	         $('input[name="prod_hsncd' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].hsn_cd_assetdiv);
			   $('input[name="asset_prfx' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].asset_prod_prefix);
        
            debugger;
           
          if(r.data[0].type_grp=='TYPE-CYLINDER') 
	        {     
                   
                  $('input[name="qty_fill' + domElement.attr('patelUnPrc') + '"]').val('0');
             
                   $('input[name="qty_empty' + domElement.attr('patelUnPrc') + '"]').val('0');

                	$('#'+IdName).show();
			       $('#'+IdName1).show();
               


			 } 
		    // if(r.data[0].nm_assetdiv!='Oxygen Cylinder B-Type'||r.data[0].nm_assetdiv!='Oxygen Cylinder B-Type (Aluminium)'||r.data[0].nm_assetdiv!='Oxygen Cylinder D-Type' ) 
	        else{     
                   
                 /* $('input[name="qty_fill' + domElement.attr('patelUnPrc') + '"]').attr("readonly");
             
                   $('input[name="qty_empty' + domElement.attr('patelUnPrc') + '"]').attr("readonly");*/

               
                 	$('#'+IdName).hide();
			       $('#'+IdName1).hide();
                 


			 } 
                 
	             }
	 },'json');
}
$(function() {
	
		var currentDate = new Date();
		$(".datepicker").datepicker({
			yearRange : '1985:2025',
			changeMonth : true,
			changeYear : true,
			dateFormat : "dd/mm/yy",
			autoSize : true,
			altFormat : "dd/mm/yy",
			 minDate: 0
		});
	
	
		$('input[name="dt_to"]').datepicker("setDate", currentDate);
		 currentDate.setMonth(currentDate.getMonth() - 1);
		$('input[name="dt_frm"]').datepicker("setDate", currentDate);
	
		
		
	});

	


function AutogenerateInv(){
	   $.post('S_inventory_invoice',{action : 'InvNoGeneration'}, function(r){
		if(r.data){
		var inv_n = r.data[0].InvNO;
        var value='';
         value='TEMP'+'-'+'INV/'+inv_n;
       $('input[name="temp_no_inv"]').val(value);
       $('input[name="no_inv"]').val('NA');
		//$('input[name="dt_inv"]').val('NA');
	     $('input[name="dt_inv"]').removeAttr("data-valid");      
   }
		},'json');
	
			}

function AutoNA(){
			
       $('input[name="temp_no_inv"]').val('NA');
	           
	
			}
