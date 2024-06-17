$(function() {
	$("#menu").menu();
});

//Main Function For Save,Update,Cancel Invoice
function ControlAsset(action, displayContent, createDetails, formName, servletName) {
	SessionCheck(function(ses) {
		if (ses) {
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
							DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForInvoice', 'M_Subloc', function(status) {
								if (status) {
									SubDropDownDataDisplay(r.data[0].id_sloc, 'buildingDataForEmpUser', 'M_Building', function(status) {
										if (status) {
											SubDropDownDataDisplay(r.data[0].id_building, 'floorDataForEmpUser', 'M_Floor', function(status) {
												if (status) {
													$('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
													$('select[name=id_dept] option[value=' + r.data[0].id_dept + ']').attr('selected', true);
													$('select[name=id_subl] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
													$('select[name=id_building] option[value=' + r.data[0].id_building + ']').attr('selected', true);
													$('select[name=id_flr] option[value=' + r.data[0].id_flr + ']').attr('selected', true);
                                                 }
											});
										}
									});
								}
							});
						}
					}
				}, 'json');
			}
			if (action == 'Cancel') {
				$('#displayAccessory').hide();
				$('#createAccessory').hide();
				$('#' + createDetails).hide();
				$('#' + displayContent).show();
				DisplayInvoice(servletName, displayContent, createDetails, '', 'invoiceForDisplay');
				window.location = $('.addinvoice_event').attr('href');
				}
			if (action == 'Save' || action == 'Update') {
				
				if (action == 'Save') {
					$('button[name="save1"]').removeClass('hideButton');
					$('input[name="action"]').val('Save');
				}
				else {
					$('input[name="action"]').val('Update');
				}
				DataInsertUpdateForAsset("Cancel", displayContent, createDetails, formName, servletName);
			}
		}
	});
}

//For Save & Update Invoice
function DataInsertUpdateForAsset(action, displayContent, createDetails, formName, servletName) {
	SessionCheck(function(ses) {
		if (ses) {
			t = false;
			t = ValidationForm(formName);
			if (t) {
				t = false;
				var val = $('#bdIdForAddTostore').val();
				if (val == 'Yes') {
					t = ShowRowColumnValidation('common-hideShow');
				}
				else {
					t = true;
				}
			}
			if (t) {
				$('.inv').attr('disabled', 'disabled');
				$('button[name="save"]').removeClass('hideButton');
				$('button[name="update"]').addClass('hideButton');
				var x = $('#' + formName).serialize();
				var itemCount=$('input[name="itemCount"]').val();
			
			
			var t=1;
			for(var i=0;i<itemCount;i++)
			{
				var id_prod=$('input[name="id_prod'+i+'"]').val();
				if(id_prod != '' )	
				{
					var id_grp=$('select[name="id_grp'+i+'"]').val();
					var id_sgrp=$('select[name="id_sgrp'+i+'"]').val();
					if(id_grp == '' ||  id_grp == undefined || id_sgrp == '' ||  id_sgrp == undefined )
					{
						
						t=0;
						bootbox.alert("Make sure you have filled all the mandatory fields !!");
						$('.inv').removeAttr('disabled');
						$('select[name="id_grp'+i+'"]').addClass('error');
						$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
					}
					else
					{
						$('select[name="id_grp'+i+'"]').removeClass('error');
						$('select[name="id_sgrp'+i+'"]').removeClass('error');
					}
				}	
				
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
						alert("Invoice Added Successfully !!!!!");
						window.location = $('.addinvoice_event').attr('href');
					}
					else if (r.data == 2) {
						bootbox.alert("This Invoice Number Already Exist!!!");
						$('#InvoiceNum').addClass('error');
					}
					else {
						bootbox.alert("Try again.");
					}
					$('.inv').removeAttr('disabled');
				}, 'json');
			}
			}
		}
	});
}

//For Modify Invoice
function EditAsset(servletName, displayContent, createDetails, id) {
	SessionCheck(function(ses) {
		if (ses) {
			$('input[name="invoiceCheck"]').val('1');
			$('button[name="save"]').addClass('hideButton');
			$('button[name="save1"]').addClass('hideButton');
			$('button[name="update"]').removeClass('hideButton');
			
			$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createAccessory').show();
			$('.hideinv').hide();
			var t = 0;
			$.post(servletName, { action: 'Edit', id: id }, function(r) {
				if (r.data) {
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
							if (key == 'bd' && r.data[i][key] == 'Yes') {
								t = 1;
							}
						}
					}
					$('input[name="action"]').val("Update");
					$('input[name="id"]').val(id);
					$('input[name="dt_inv"]').val(r.data[0].dtInv);
					$('input[name="dt_grn"]').val(r.data[0].dtGRN);
					$('input[name="dt_po"]').val(r.data[0].dtPo);
					$('input[name="dt_dc"]').val(r.data[0].dtdc);
					$('input[name="dt_boe"]').val(r.data[0].dtBoe);
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForInvoice', 'M_Subloc', function(status) {
						if (status) {
							SubDropDownDataDisplay(r.data[0].id_subl, 'buildingDataForEmpUser', 'M_Building', function(status) {
								if (status) {
									SubDropDownDataDisplay(r.data[0].id_building, 'floorDataForEmpUser', 'M_Floor', function(status) {
										if (status) {
											$('select[name=id_dept] option[value=' + r.data[0].id_dept1 + ']').attr('selected',true);
											$('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
											$('select[name=id_subl] option[value=' + r.data[0].id_subl + ']').attr('selected', true);
											$('select[name=id_building] option[value=' + r.data[0].id_building + ']').attr('selected', true);
											$('select[name=id_flr] option[value=' + r.data[0].id_flr + ']').attr('selected', true);
										}
									});
								}
							});
						}
					});
					var uploadFile='';
						if(r.data[0].invoice_file != undefined)
							uploadFile = r.data[0].invoice_file;
							var download='<a href="downloadInvoiceDetails.jsp?fileName1='+uploadFile+'">'+uploadFile+'</a> ';
							
							document.getElementById("datachment").innerHTML =download;	
					dispalyLineItemInvoiceformodify(r);
				}
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					ShowRowColumn('Yes', 'hideRowCol');
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
			'<td ><strong><center>Assets/Model<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Assets/Model Type<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Catagory<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Sub Catagory<a href=#></a></center></strong></td>' +
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
		for (var i = 0; i < 10; i++) {
			list += '<tr>' +
				' <td style="display:none;">' +
				'<select name="asset_consumable' + i + '"   style="width:107px" patelUnPrc="' + i + '" class="form-control" data-valid="mand">' +
				'<option value="ConG">Material Goods</option>' +
				'<option value="CapG">Capital Goods</option>' +
				'</select>' +
				'</td>' +
				'<input type="hidden" name="ConfigurableId' + i + '" value="No">' +
				'<td><input style="width: 120px !important;" type="text" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprod(event);"></td>' +
				'<td><center><select style="width: 120px !important;" id="assettypeDivForInvoice' + i + '" name="typ_asst' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80" >' +
				'<option value="">--Select--</option>' +
				'<option value="IT">IT</option>' +
				'<option value="NON-IT">NON IT</option>' +
				'<option value="Software">Software</option>' +
				'<option value="accessories">Accessories</option></select>' +
				'</center></td>' +
				'<td><center><select style="width: 120px !important;" id="assetDivForInvoice' + i + '" name="id_grp' + i + '" patelUnPrc="' + i + '"  class="form-control  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice' + i + '\',\'M_Subasset_Div\')">' +
				'<option value="">--Select--</option></select>' +
				'</center></td>' +
				'<td><center><select style="width: 120px !important;" id="subGroupDataForInvoice' + i + '" name="id_sgrp' + i + '" patelUnPrc="' + i + '"  class="form-control dropsgrp" data-valid=""  style="width:80" >' +
				'<option value="">--Select--</option></select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="qty' + i + '" style="width:60px;;text-align: right;"  value="0" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>' +
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="0.0" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
				'<td><input type="text" name="others' + i + '" value="0.0" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTot(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 100px;" name="id_tax1' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '" class="form-control patelTax" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 100px;" name="id_tax2' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')" readonly>' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="0.0" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="0.0" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="0.0" onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
				'<td><center><input type="text"  name="gr_tot' + i + '" value="0.0" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
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
						DisplayDropDownDataForGroup('M_Asset_Div', 'groupDrop', 'CapG', function(status) {
						});
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
			'<td><input style="width: 120px !important;" type="text" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprod(event);"></td>' +
			'<td><center><select style="width: 120px !important;" id="assettypeDivForInvoice' + i + '" name="typ_asst' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80" >' +
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
			'</center></td>' +
			'<td><center><input type="text"  name="qty' + i + '" style="width:60px;;text-align: right;"  value="0" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>' +
			'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="0.0" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
			'<td><input type="text" name="others' + i + '" value="0.0" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTot(event,\'others\')" class="form-control" ></td>' +
			'<td><center>' +
			'<select style="width: 100px;" name="id_tax1' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '" class="form-control patelTax" onChange="getTax2(event);">' +
			'</select>' +
			'</center></td>' +
			'<td><center>' +
			'<select style="width: 100px;" name="id_tax2' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')" readonly>' +
			'</select>' +
			'</center></td>' +
			'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="0.0" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
			'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="0.0" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
			'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="0.0" onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
			'<td><center><input type="text"  name="gr_tot' + i + '" value="0.0" style="width:60px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
			'<input type="hidden" name="id_inv' + i + '" value="">' +
			'<input type="hidden" name="count" value="' + i + '">' +
			'</tr>';
		$('input[name="itemCount"]').val(parseInt(i) + 1);
		$('#accessoryDetails').append(list);
		var dropDownId = "id_prod" + i + "";
		DropDownDataDisplay("M_Tax", "id_tax1" + i, function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "id_tax2" + i, function(status) {
					if (status) {
						DisplayDropDownDataForGroup("M_Asset_Div", "assetDivForInvoice" + i, "CapG", function(status) {
							if (status) {
							}
						});
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
	var currentDate = new Date();
	$(".datepoinvoice").datepicker({
		yearRange: '1985:2025',
		changeMonth: true,
		changeYear: true,
		dateFormat: "dd/mm/yy",
		autoSize: true,
		altFormat: "dd/mm/yy",
	});
	console.log(currentDate);
}

//Line Item While modifying Invoice
function dispalyLineItemInvoiceformodify(r) {
	if (r.data) {
		var list = '<tr>' +
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' + '<tr class="tableHeader info">' +
			'<td ><strong><center>Model/Item<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Assets/Model Type<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Catagory<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Sub Catagory<a href=#></a></center></strong></td>' +
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
			list += '<tr>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_pro + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprod(event);"></td>' +
				'<td><center><select style="width: 120px !important;" id="assettypeDivForInvoice' + i + '" name="typ_asst' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80" >' +
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
				'</center></td>' +
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
				'<input type="hidden" name="id_inv' + i + '" value="' + r.data[i].id_inv + '">' +
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
						DisplayDropDownDataForGroup('M_Asset_Div', 'groupDrop', 'CapG', function(status) {
							if (status) {
								SubDropDownDataDisplay('0', 'dropsgrp', 'M_Subasset_Div', function(status) {
									for (var i = 0; i < r.data.length; i++) {
										params = r.data[i];
										for (var key in r.data[i]) {
											if (key == 'id_tax1') {
												$('select[name=id_tax1' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
											if (key == 'id_tax2') {
												$('select[name=id_tax2' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
											if (key == 'id_grp') {
												$('select[name=id_grp' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
											if (key == 'id_sgrp') {
												$('select[name=id_sgrp' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
											if (key == 'typ_asst') {
												$('select[name=typ_asst' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
										}
									}
								});
							}
						});
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
					$.post(servletName, { action: 'Delete', id: id }, function(r) {
						if (r.data == 1) {
							window.location = $('.addinvoice_event').attr('href');
							DisplayInvoice(servletName, displayContent, createDetails, '', 'invoiceForDisplay');
						}
						if (r.data == 2) {
							bootbox.confirm("If you delete this , then all aseet will be deleted related to this invoice.", function(result1) {
								if (result1) {
									$.post(servletName, { action: 'Delete', id: id, id_inv_m: '0' }, function(r) {
										if (r.data == 1) {
											window.location = $('.addinvoice_event').attr('href');
											DisplayInvoice(servletName, displayContent, createDetails, '', 'invoiceForDisplay');
										}
									}, 'json');
								}
							});
						}
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
			var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();
			$.post(servletName, { action: 'Display', no_inv: no_inv, dt_frm: dt_frm, dt_to: dt_to }, function(r) {
				var list = '<thead><tr>' +
					'<th><strong><center>Invoice Number</center></strong></th>' +
					'<th><strong><center>Invoice Date</center></strong></th>' +
					'<th><strong><center>Vendor</center></strong></th>' +
					'</tr></thead><tbody>';
				if (r.data.length > 0) {
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						list = list + '<tr>' +
							'<td><center><a class="alertlink" href="#" onclick="EditAsset(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',' + params.id_inv_m + ')">' + params.no_inv + '</a></center></td>' +
							'<td><center>' + params.dtInv + '</center></td>' +
							'<td><center>' + params.nm_ven + '</center></td>' +
							'</tr>';
					}
				}
				else {
					list += '<tr><td colspan="3"><strong><center>No data found.</center></strong></td></tr>';
					$('#invoiceForDisplaytd').html('</tbody>' + list);
				}
				$('#invoiceForDisplaytd').html('</tbody>' + list);
			}, 'json');
		}
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
	getValue: "nm_model",
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

//Checking Invoice Date Based on PO date
function CheckContraDate() {
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
}

//Checking GRN Date Based on Invoice date
function CheckgrnaDate() {
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
}

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
	 $.post('M_Model',{action : 'GetModeldetails', id:nm_prod},function (r){
			if(r.data)
				{
						console.log(r.data);
						console.log(r.data[0].id_assetdiv);
				$('select[name=id_grp'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_assetdiv + ']').attr('selected',true);
				SubDropDownDataDisplay(r.data[0].id_assetdiv,'subGroupDataForInvoice'+domElement.attr('patelUnPrc')+'','M_Subasset_Div',function (status){
					$('select[name=id_sgrp'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_s_assetdiv + ']').attr('selected',true);
					$('select[name=typ_asst'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].typ_asst + ']').attr('selected',true);
					});
				}
	 },'json');
}
$(function() {
		$('.hideRowCol').hide();
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
									var temp_end = dt_po.split("/");
									var temp_dt_end = new Date(temp_end[2],
											temp_end[1] - 1, temp_end[0]);
									dt_inv = $.datepicker.formatDate(
											'yy-mm-dd', temp_dt_strt);
									dt_po = $.datepicker.formatDate('yy-mm-dd',
											temp_dt_end);
									if (dt_inv < dt_po) {
										alert('P.O date should be less or equal to invoice date : '
												+ dt_inv1);
										$(this).focus();
										$(this).val('');
										$(this).addClass('error');
										exit(0);
									}
								}
							}
						});
		var currentDate = new Date();
		$(".datepicker").datepicker({
			yearRange : '1985:2025',
			changeMonth : true,
			changeYear : true,
			dateFormat : "dd/mm/yy",
			autoSize : true,
			altFormat : "dd/mm/yy",
		});
		$('input[name="dt_to"]').datepicker("setDate", currentDate);
		currentDate.setMonth(currentDate.getMonth() - 1);
		$('input[name="dt_frm"]').datepicker("setDate", currentDate);
		
	});
	jQuery("input#fileID").change(function() {
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
	});