$(function (){
	
	//Add To Store through Excel
	$("#upload").change(function () {
			 var formData = new FormData();	
				formData.append('file', $("#upload").get(0).files[0]);
					$.ajax({
					  url: 'Upload_Delete_File',
					    type: 'POST',
					    data: formData,
					    async: false,
					    cache: false,
					    contentType: false,
					    dataType: "json",
					    processData: false,
					    mimeType: "multipart/form-data",
					    success: function (r) {
					    		$.post('ReadExcel',{filePath : r.upload_inv},function (r)
										{
					    			if(r.data != 2)
					    				{
											if(r.data.length > 0)
												{
												for(var i = 0; i < r.data.length ; i++)
													{
													for (var key in r.data[i])
														{
															$('input[name='+key+']').val(r.data[i][key]);
														}
													}
												for(var i = 0; i < r.sapNo.length ; i++)
												{
												for (var key in r.sapNo[i])
													{
														$('input[name='+key+']').val(r.sapNo[i][key]);
													}
												}
												}
											else
												{
													alert('Please check the uploaded file it was empty.');
												}
					    				}
					    			else
					    				{
					    					alert('Please check the uploaded file row should not be empty.');
					    				}
										},'json');
					    }
					},'json'); 
		}); 
		
		//Upload Serial No through Excel
		 $("#upload1").change(function () {
			 var formData = new FormData();	 
				formData.append('file', $("#upload1").get(0).files[0]);
					$.ajax({
					  url: 'Upload_Delete_File',
					    type: 'POST',
					    data: formData,
					    async: false,
					    cache: false,
					    contentType: false,
					    dataType: "json",
					    processData: false,
					    mimeType: "multipart/form-data",
					    success: function (r) {
					    		$.post('ReadExcel',{filePath : r.upload_inv},function (r)
										{
					    			if(r.data != 2)
					    				{
											if(r.data.length > 0)
												{
												for(var i = 0; i < r.data.length ; i++)
													{
													for (var key in r.data[i])
														{
															$('input[name='+key+']').val(r.data[i][key]);
														}
													}
												for(var i = 0; i < r.sapNo.length ; i++)
												{
												for (var key in r.sapNo[i])
													{
														$('input[name='+key+']').val(r.sapNo[i][key]);
													}
												}
												}
											else
												{
													alert('Please check the uploaded file it was empty.');
												}
					    				}
					    			else
					    				{
					    					alert('Please check the uploaded file row should not be empty.');
					    				}
										},'json');
					    }
					},'json'); 
		}); 
	$('#serialNo').hide();
	$('#AddToStore').hide();
	$('.leaseAddToStoreCommonClass').hide();
	$('.amcAddToStoreCommonClass').hide();
	$('.insAddToStoreCommonClass').hide();
	$('.calAddToStoreCommonClass').hide();
	
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
     			$('#amcYeardatepicker').removeClass('error');
        	 $('input[name="dt_amc_start"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
             date.setDate(date.getDate() - 1);
              date.setMonth(date.getMonth() + 12);
              $('input[name="dt_amc_exp"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
     			}
        }
    });

//Check Lease start date with Invoice date
	$("#leasecYeardatepicker").datepicker({
    	yearRange: '1985:2025',
    	 changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
        onSelect: function(dateText, instance) {
        	 date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
        	 var dt_inv=$('input[name="dt_inv"]').val();
        		var std_lease=$('input[name="std_lease"]').val();
        		if(std_lease < dt_inv){
        		alert("'LEASE DATE MUST BE GREATER OR EQUAL TO INVOICE DATE. '");
        		$('#leasecYeardatepicker').focus();
        		$('#leasecYeardatepicker').val('');
        		$('#leasecYeardatepicker').addClass('error');
        		}
        		else
        			{
        			$('#leasecYeardatepicker').removeClass('error');
        	 $('input[name="std_lease"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
             date.setDate(date.getDate() - 1);
              date.setMonth(date.getMonth() + 12);
              $('input[name="endt_lease"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
        			}
        }
    }); 
var currentDate = new Date();
	$('.grndatepicker').each(function () {
            $(this).removeClass('hasDatepicker');
    });

	$('.grndatepicker').datepicker({
		yearRange: '1985:2025',  
		changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	    });
	$('input[name="dt_to"]').datepicker("setDate", currentDate);
	currentDate.setMonth(currentDate.getMonth() - 1);
	$('input[name="dt_frm"]').datepicker("setDate", currentDate);
	
	//Check Lease/AMC end date with Lease/AMC start date	 	
	 $( ".leasedatepicker" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      onSelect: function(selected,evnt) {
		    	  $(this).removeClass('error');
		    	  $('input[name="std_lease"]').removeClass('error');
		    	  var std_lease=$('input[name="std_lease"]').val();
		    	  var std_lease1=$('input[name="std_lease"]').val();
		    	  var dt_end = $(this).val();
		    	  if(std_lease == '')
		    		  {
		    		  	alert('First filled start  date.');
		    		  	$('input[name="std_lease"]').focus();
		    		  	$('input[name="std_lease"]').addClass('error');
		    		  	$('input[name="std_lease"]').val('');
		    		  	$(this).val('');
		    		  	exit(0);
		    		  }
		    	  else
		    		  {
		    		  var temp_strt = std_lease.split("/");
					  var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
						var temp_end = dt_end.split("/");
						var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
						std_lease = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
						dt_end = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
		    		  if(std_lease > dt_end)
		    			  {
		    			  	alert('End date should be greater or equal to start date : '+std_lease1);
		    			  	$(this).focus();
		    			  	$(this).val('');
		    			  	$(this).addClass('error');
		    			  	exit(0);
		    			  }
		    		  }
		      }
		    });
	
});

//Ref. Excel Download For Add to Store
$('#downloadexc').click(function(e) {
    e.preventDefault();  //stop the browser from following
    window.location.href = 'InvoiceScanFile/AddStore.xlsx';
});
////Upload Serial Number..............
var ExcelToJSON1 = function() {
	
	    this.parseExcel = function(file) {
	      var reader = new FileReader();
	      reader.onload = function(e) {
	        var data = e.target.result;
	        var workbook = XLSX.read(data, {
	          type: 'binary'
	        });
	        workbook.SheetNames.forEach(function(sheetName) {
	        	if(sheetName=='Sheet1')
	        		{
	        	 console.log(sheetName);
	        	 console.log(workbook.Sheets[sheetName]);
	          var XL_row_object = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
	          var json_object = JSON.stringify(XL_row_object);
	var x=JSON.parse(json_object);
	var serialnumbercount =parseInt($('input[name="serialnumbercount"]').val());
	for(var i = 0; i <= serialnumbercount ; i++)
	{
		
		$('input[name="sno'+i+'"]').val((x[i].Serial_Number).trim());
		$('input[name="sapNo'+i+'"]').val((x[i].Asset_Ref_Number).trim());
	}
	        		}
	        	});
	      };
	      reader.onerror = function(ex) {
	        console.log(ex);
	      };
	      reader.readAsBinaryString(file);
	    };
	};
////file upload............
	   document.getElementById('upload1').addEventListener('change', handleFileSelect, false);
	   function handleFileSelect(evt) {
		      var files = evt.target.files; // FileList object
		       var xl2json = new ExcelToJSON1();
		       xl2json.parseExcel(files[0]);
		     }
	   $('#downloadexc1').click(function(e) {
		    e.preventDefault();  //stop the browser from following
		    window.location.href = 'InvoiceScanFile/Serial_Number.xlsx';
		});	

function CheckSerialNoAndSapNo(SerialVal, sapNo, callback) {
	var t = true;
	$('.removeErr').removeClass('error');
	$.post('A_Add_To_Store1', { action: 'CheckSlAndSpNo', SerialVal: SerialVal, sapNo: sapNo }, function(r) {
		if (r.data == 1) {
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
function AddToStore(id_inv, qty, id_inv_m, id_grn) {
	SessionCheck(function(ses) {
		if (ses) {
			$('#serialNo').show();
			$('#browseFile').show();
			$('#displayAddToStore').hide();
			var temp = '<center><table class="table table-bordered" style="width : 65%">' +
				'<tr class="tableHeader tableHeaderContent"><td colspan="4"><center><strong><h3>Add Serial No.</h3></strong></center></td></tr>';
			for (var i = 0; i < qty; i++) {
				j = i + 1;
				temp += '<tr><td><strong>Serial No. ' + j + '<font color="red"> * </font></strong></td>' +
					'<td><input type="text" name="sno' + i + '" value="" class="form-control removeErr" data-valid="mand"></td>' +
					'<td><strong>Oracle Apps Num  ' + j + '<font color="red"> * </font></strong></td>' +
					'<td><input type="text" name="sapNo' + i + '" value="" class="form-control removeErr" data-valid="mand"></td>' +
					'</tr>';
			}
			temp += '<tr><td colspan="4"><button type="button" style="margin-left: 260px;" class="btn btn-primary" ' +
				'onclick="ControlStore(\'Back\',\'displayAddToStore\',\'serialNo\',\'AddToStore\')"> Back </button>&nbsp;&nbsp;&nbsp;' +
				'<button type="button"  class="btn btn-primary" ' +
				'onclick="ControlStore(\'Next\',\'displayAddToStore\',\'serialNo\',\'AddToStore\',' + id_inv + ',' + id_inv_m + ',' + qty + ',' + id_grn + ')">Next </button></td></tr>' +
				'</table></center>';
			$('#serialNo').html(temp);
		}
	});
}
function DuplicateChecking(ConstantVal, ConstantSapVal, countLoop) {
	$('.removeErr').removeClass('error');
	var t = true;
	for (var i = 0; i < countLoop; i++) {
		t = true;
		var val = $('input[name="sno' + i + '"]').val();
		var sapNo = $('input[name="sapNo' + i + '"]').val();
		if (val != "") {
			if (val == ConstantVal) {
				t = false;
				alert('Duplicate value is not allowed.');
				$('input[name="sno' + i + '"]').val('');
				$('input[name="sno' + i + '"]').addClass('error');
				$('input[name="sno' + i + '"]').focus();
			}
		}
	}
	return t;
}
function DisplayAccessoryForAddToStore(servletName) {
	SessionCheck(function(ses) {
		if (ses) {
			var dt_frm = $('input[name="dt_frm"]').val();
			var dt_to = $('input[name="dt_to"]').val();
			$.post(servletName, { action: 'Display', dt_frm: dt_frm, dt_to: dt_to }, function(r) {
				var list = '<tr class="tableHeader info">' +
					'<td><strong>Invoice No</strong></td>' +
					'<td><strong>PO No.</strong></td>' +
					'<td><strong>DC No.</strong></td>' +
					'<td><strong>GRN Number</strong></td>' +
					'<td><strong>GRN Date</strong></td>' +
					'<td><strong>Quantity</strong></td>' +
					'<td style="width: 155px;"><strong><a href="#">Add To Store </a></strong></td>' +
					'</tr>';
				if (r.data.length > 0) {
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						list = list + '<tr class="success">' +
							'<td><center>' + params.no_inv + '</center></td>' +
							'<td><center>' + params.no_po + '</center></td>' +
							'<td><center>' + params.no_dc + '</center></td>' +
							'<td><center>' + params.no_grn + '</center></td>' +
							'<td><center>' + params.dtGrn + '</center></td>' +
							'<td><center>' + params.qty_recv + '</center></td>' +
							'<td><strong><a class="alert" href="#" onclick="AddToStore(' + params.id_inv + ',' + params.qty_recv + ',' + params.id_inv_m + ',' + params.id_grn + ')"> <i class="fas fa-store"></i></a></strong></td>' +
							'</tr>';
					}
					$('.addToStoreForDisplay').html(list);
				}
				else {
					list += '<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
					$('.addToStoreForDisplay').html(list);
				}
			}, 'json');
		}
	});
}
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
function DisplayInvoiceForAddToStore(servletName, ClassNameForDisplayData) {
	SessionCheck(function(ses) {
		if (ses) {
			var searchWord = $('input[name="search"]').val();
			$('#serialNo').hide();
			$('#AddToStore').hide();
			$.post(servletName, { action: 'Display', searchWord: searchWord }, function(r) {
				var list = '<thead><tr class="new">' +
					'<th><strong><center>Invoice Number</center></strong></th>' +
					'<th><strong><center>Invoice Date</center></strong></th>' +
					'<th><strong><center>PO Number</center></strong></th>' +
					'<th><strong><center>Asset Name/Model</center></strong></th>' +
					'<th><strong><center>Vendor</center></strong></th>' +
					'<th><strong><center>Total Qty</center></strong></th>' +
					'<th style="width:150px;"><strong><center>Add To Store</center></strong></th>' +
					'</tr></thead><tbody>';
				if (r.data.length > 0) {
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						list = list + '<tr>' +
							'<td><center>' + params.no_inv + '</center></td>' +
							'<td><center>' + params.dtInv + '</center></td>' +
							'<td><center>' + params.no_po + '</center></td>' +
							'<td><center>' + params.nm_model + '</center></td>' +
							'<td><center>' + params.nm_ven + '</center></td>' +
							'<td><center>' + params.qty + '</center></td>' +
							'<td><strong><center><a class="alert" href="#" onclick="AddToStore1(' + params.id_inv + ',' + params.qty + ',' + params.id_grn + ',' + params.id_sgrp + ',\'' + params.typ_asst + '\',\'hard\')"> <i class="fas fa-store"></i> </a></center></strong></td>' +
							'</tr>';
					}
					$('.' + ClassNameForDisplayData).html(list);
				}
				else {
					list += '<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
					$('.' + ClassNameForDisplayData).html(list);
				}
				getButtonsForListView('displayInvoiceForStoretd', 'InvoiceforStore');
			}, 'json');
		}
	});
}
function NoSerialNum() {
	$(".putNA").each(function(index) {
		$(this).val("NA" + index);
		console.log(index + ": " + $(this).val("NA" + index));
	});
}
function AddToStore1(id_grn_asset, qty, id_grn, id_sgrp, typAsst, type) {
	SessionCheck(function(ses) {
		if (ses) {
			$('#serialNo').show();
			$('#browseFile').show();
			$('#displayInvoiceForStore').hide();
			$('#uploaddisplay').show();
			//alert(type);
			var temp = '<center><table class="table table-bordered" style="width : 90%">' +
				'<tr class="tableHeader tableHeaderContent">' +
				'<td colspan="10"><center><strong><h3>Add Serial No.</h3></strong></center></td>' +
				'</tr>';
			temp += '<tr><td colspan="10"><button type="button" style="float:right;" class="btn btn-primary" ' +
				'onclick="GenerateDynamicSerialNumber('+id_grn+','+qty+')">Do Not Have Serial No </button></td></tr>';
			if (typAsst == 'NIT') {
				temp += '<tr><td colspan="10"><button type="button" style="float:right;" class="btn btn-primary" ' +
					'onclick="GenerateDynamicSerialNumber(' + id_grn + ',' + qty + ')">Generate Serial No </button></td></tr>';
			}
			for (var i = 0; i < qty; i++) {
				j = i + 1;
				temp += '<tr><td><strong>Serial No ' + j + '<font color="red"> * </font></strong></td>' +
					'<td><input type="text" style="max-width: 250px;" class="putNA form-control" name="sno' + i + '" value="" class="form-control removeErr" data-valid="mand"></td>' +
					'<td ><strong>Asset Ref. Num  ' + j + '</strong></td>' +
					'<td ><input type="text" name="sapNo' + i + '" value="NA" class="form-control removeErr"></td>' +
					'<td style="display:none"><strong>Oracle Apps Num  ' + j + '<font color="red">  </font></strong></td>' +
					'<td style="display:none"><input type="text" name="appNo' + i + '" value="NA" class="form-control removeErr" data-valid=""></td>' +
					'<td style="display:none"><strong>Model No ' + j + '<font color="red"> * </font></strong></td>' +
					'<td style="display:none"><input type="text" style="max-width: 100px;" name="mdl_num' + i + '" value="NA" class="form-control removeErr" data-valid="mand"></td>' +
					'<td style="display:none"><strong>Process type  ' + j + '<font color="red"> * </font></strong></td>' +
					'<td style="display:none"><input type="text" style="max-width: 100px;" name="process_typ' + i + '" value="NA" class="form-control removeErr" data-valid="mand"></td>' +
					'<td style="display:none"><strong>Storage type ' + j + '<font color="red"> * </font></strong></td>' +
					'<td style="display:none"><input type="text" style="max-width: 100px;" name="storeage_typ' + i + '" value="NA" class="form-control removeErr" data-valid="mand"></td>' +
					'<td style="display:none"><strong>RAM type ' + j + '<font color="red"> * </font></strong></td>' +
					'<td style="display:none"><input type="text" style="max-width: 100px;" name="ram_typ' + i + '" value="NA" class="form-control removeErr" data-valid="mand"></td>' +
					'</tr>';
				$('input[name="serialnumbercount"]').val(i);
			}
			temp += '<tr><td colspan="6"><button type="button" style="margin-left: 300px;" class="btn btn-primary" ' +
				'onclick="ControlStore(\'Back\',\'displayInvoiceForStore\',\'serialNo\',\'AddToStore\')"> Back </button>&nbsp;' +
				'<button type="button"  class="btn btn-primary" ' +
				'onclick="ControlStore(\'Next\',\'displayInvoiceForStore\',\'serialNo\',\'AddToStore\',' + id_grn_asset + ',' + id_grn + ',' + qty + ',\'\')">Next </button></td></tr>' +
				'</table></center>';
			$('#serialNo').html(temp);
		}
	});
}
function ControlStore(Action, DisplayDiv, HideDiv, HideDiv2, id_grn_asset, id_grn, qty, id_grn1) {
	SessionCheck(function(ses) {
		if (ses) {
			if (Action == 'Back') {
				$('#' + HideDiv).hide();
				$('#' + HideDiv2).hide();
				$('#' + DisplayDiv).show();
				$('#browseFile').hide();
			}
			if (Action == 'Next') {
				$('#uploaddisplay').hide();
				var t = 1, SerialVal = '', sapNo = '', appVal = '', mdl_numtop = '', process_typtop = '', storeage_typtop = '', ram_typtop = '';
				$('input[name="SerialVal"]').val('');
				for (var i = 0; i < qty; i++)
					var t = 1, SerialVal = '', sapNo = '', appVal = '', mdl_numtop = '', process_typtop = '', storeage_typtop = '', ram_typtop = '';
				$('input[name="SerialVal"]').val('');
				for (var i = 0; i < qty; i++) {
					$('input[name="sno' + i + '"]').removeClass('error');
					$('input[name="sapNo' + i + '"]').removeClass('error');
					val = $('input[name="sno' + i + '"]').val();
					var sapVal = $('input[name="sapNo' + i + '"]').val();
					var appNo = $('input[name="appNo' + i + '"]').val();
					var mdl_num = $('input[name="mdl_num' + i + '"]').val();
					var process_typ = $('input[name="process_typ' + i + '"]').val();
					var storeage_typ = $('input[name="storeage_typ' + i + '"]').val();
					var ram_typ = $('input[name="ram_typ' + i + '"]').val();
					if (val == "") {
						t = 0;
						alert("Mandatory Field");
						$('input[name="sno' + i + '"]').focus();
						$('input[name="sno' + i + '"]').addClass('error');
						exit();
					}
					else if (sapVal == "") {
						t = 0;
						alert("Mandatory Field");
						$('input[name="sapNo' + i + '"]').focus();
						$('input[name="sapNo' + i + '"]').addClass('error');
						exit();
					}
					else {
						var st = DuplicateChecking(val, sapVal, i);
						if (!st) {
							t = 0;
							exit();
						}
					}
					if (SerialVal == "") {
						SerialVal += val;
						if (sapVal == '') {
							sapVal = 'NA';
						}
						if (appNo == '') {
							appNo = 'NA';
						}
						if (mdl_num == '') {
							mdl_num = 'NA';
						}
						if (process_typ == '') {
							process_typ = 'NA';
						}
						if (storeage_typ == '') {
							storeage_typ = 'NA';
						}
						if (ram_typ == '') {
							ram_typ = 'NA';
						}
						sapNo += sapVal;
						appVal += appNo;
						mdl_numtop += mdl_num;
						process_typtop += process_typ;
						storeage_typtop += storeage_typ;
						ram_typtop += ram_typ;
					}
					else {
						SerialVal += ',,' + val;
						if (sapVal == '') {
							sapVal = 'NA';
						}
						if (appNo == '') {
							appNo = 'NA';
						}
						if (mdl_num == '') {
							mdl_num = 'NA';
						}
						if (process_typ == '') {
							process_typ = 'NA';
						}
						if (storeage_typ == '') {
							storeage_typ = 'NA';
						}
						if (ram_typ == '') {
							ram_typ = 'NA';
						}
						sapNo += ',,' + sapVal;
						appVal += ',,' + appNo;
						mdl_numtop += ',,' + mdl_num;
						process_typtop += ',,' + process_typ;
						storeage_typtop += ',,' + storeage_typ;
						ram_typtop += ',,' + ram_typ;
					}
				}
				if (t == 1) {
					CheckSerialNoAndSapNo(SerialVal, sapNo, function(status) {
						if (status) {
							$('input[name="SerialVal"]').val(SerialVal);
							$('input[name="sapNo"]').val(sapNo);
							$('input[name="appNo"]').val(appVal);
							$('input[name="mdl_num11"]').val(mdl_numtop);
							$('input[name="process_typ11"]').val(process_typtop);
							$('input[name="storeage_typ11"]').val(storeage_typtop);
							$('input[name="ram_typ11"]').val(ram_typtop);
							AddInputValueForAddToStore(id_grn_asset, id_grn, qty, id_grn1);
							$('#browseFile').hide();
							$('#' + HideDiv).hide();
							$('#' + DisplayDiv).hide();
							$('#' + HideDiv2).show();
						}
					});
				}
			}
			if (Action == 'Save') {
				var t = false;
				t = ValidationForm("AddToWH");
				var val = $('#st_lease1').val();
				var warr_amc = $('#warr_amc1').val();
				var ins = $('#ins1').val();
				var cal = $('#cal1').val();
				if (cal == 'Yes') {
					t = ShowRowColumnValidation('calAddToStoreCommonClass1');
				}
				else {
					t = true;
				}
				if (ins == 'Yes') {
					t = ShowRowColumnValidation('insAddToStoreCommonClass1');
				}
				else {
					t = true;
				}
				if (warr_amc == 'A' || warr_amc == 'W') {
					t = ShowRowColumnValidation('amcAddToStoreCommonClass1');
				}
				else {
					t = true;
					$('input[name="dt_amc_start"]').val('');
					$('input[name="dt_amc_exp"]').val('');
				}
				if (val == 'UL') {
					t = ShowRowColumnValidation('leaseAddToStoreCommonClass1');
				}
				else {
					t = true;
					$('input[name="std_lease"]').val('');
					$('input[name="endt_lease"]').val('');
				}
				var x = $('#AddToWH').serialize();
				if (t) {
					$('.store').attr('disabled', 'disabled');
					$.post('A_Add_To_Store1', x, function(r) {
						if (r.data) {
							t = true;
						}
						else {
							t = false;
							bootbox.alert("Data is not inserted in database please try again.");
						}
						if (t) {
							bootbox.alert("Successfully added.");
							window.location = $('.addToStore1_event').attr('href');
						}
						$('.store').removeAttr('disabled');
					}, 'json');
				}
			}
		}
	});
}
function AddInputValueForAddToStore(id_grn_asset, id_grn, qty, id_grn1) {
	SessionCheck(function(ses) {
		if (ses) {
			$.post('A_Add_To_Store1', { action: 'Edit', id_grn_asset: id_grn_asset, id_grn: id_grn }, function(r) {
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
					
					if (r.data[0].typ_asst == 'IT') {
						$('#process1').show();
						$('#process2').show();
						$('#storage_ram').show();
					}
					if (r.data[0].dtgrn == '01/01/1900') {
						$('input[name="dt_grn_dummy"]').val('NA');
					}
					else {
						$('input[name="dt_grn_dummy"]').val(r.data[0].dtgrn);
					}
					if (r.data[0].dtdc == '01/01/1900') {
						$('input[name="dt_dc_dummy"]').val('NA');
					}
					else {
						$('input[name="dt_dc_dummy"]').val(r.data[0].dtdc);
					}
					
					$('input[name="id_ven_proc"]').val(r.data[0].id_ven);
					$('input[name="no_dc"]').val(r.data[0].no_dc);
					$('input[name="dt_dc"]').val(r.data[0].dt_dc);
					$('input[name="qty_recv"]').val(qty);
					$('input[name="tt_un_prc"]').val(r.data[0].cst_asst);
					$('input[name="nm_acc_asset"]').val(r.data[0].nm_acc_asset);
					$('input[name="st_config"]').val(r.data[0].st_config);
					$('input[name="id_dept"]').val(r.data[0].id_dept);
					$('input[name="id_grp"]').val(r.data[0].id_grp);
					$('input[name="id_model"]').val(r.data[0].id_model);
					$('input[name="id_sgrp"]').val(r.data[0].id_sgrp);
					$('input[name="id_loc"]').val(r.data[0].id_loc);
					$('input[name="id_subl"]').val(r.data[0].id_subl);
					$('input[name="id_building"]').val(r.data[0].id_building);
					$('input[name="id_grn_asset"]').val(r.data[0].id_inv);
					$('input[name="id_grn"]').val(r.data[0].id_inv_m);
					$('input[name="qty"]').val(qty);
					$('input[name="id_curr"]').val(r.data[0].id_curr);
					$('input[name="typ_asst"]').val(r.data[0].typ_asst);
					$('input[name="nm_acc_asset"]').val(r.data[0].nm_acc_asset);
					$('input[name="total_prc"]').val(qty * (r.data[0].cst_asst));
					$('input[name="val_asst"]').val((r.data[0].val_in_inr) * (1.00));
					$('input[name="dt_po"]').val(r.data[0].dtPo);
					$('input[name="dt_inv"]').val(r.data[0].dtInv);
					$('input[name="dt_grn"]').val(r.data[0].dtgrn);
					$('input[name="dt_dc"]').val(r.data[0].dtdc);
					$('input[name="bd"]').val(r.data[0].bd);
				}
				else
					bootbox.alert("Try again.");
			}, 'json');
		}
	});
}
function GenerateDynamicSerialNumber(id_sgrp , qty){
	
	
	$.post('A_Add_To_Store1',{action : 'SerialNoGeneration' , id_sgrp :id_sgrp}, function(r){
		if(r.data){
			var slNo = r.data[0].slNo;
			var value='';
			for(var i=0 ; i < qty ; i++){
				value = 'NA' +slNo;
				$('input[name="sno'+i+'"]').val(value);
				$('input[name="sapNo'+i+'"]').val(value);
				
				slNo++;
			}
		}
		
		
	},'json');
	
	
	
}

function TypeProc(){
	
	var status_lease = $('select[name="st_lease"]').val();
	if(status_lease=='UL'){
		$('select[name="typ_proc"]').val('LEASE');
	}
}
function CalcTotPriceForAddToStore()
{
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	$('input[name="cst_asst_add"]').removeClass('error');
	var str = $('input[name="cst_asst_add"]').val();
	
	if(str != '' || str != undefined)
		{
			if(intRegex.test(str) || floatRegex.test(str)) {
				
				var addPrice = $('input[name="cst_asst_add"]').val();
				var tt_un_prc = $('input[name="cst_asst"]').val();
				//var ex_rate = $('input[name="ex_rate"]').val();
				
				var totPrice = parseFloat(addPrice) + parseFloat(tt_un_prc);
				
				$('input[name="val_asst"]').val(totPrice.toFixed(2));
			}
			else
			{
				alert('Invalid number.');
				$('input[name="cst_asst_add"]').addClass('error');
				$('input[name="cst_asst_add"]').focus();
				exit(0);
			}
		}
	
	
}