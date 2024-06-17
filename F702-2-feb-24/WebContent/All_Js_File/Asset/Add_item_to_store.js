function getModel() {
		

				$.post('M_Model', {
					action : 'getModel'
				}, function(r) {
					if (r.data.length>0) {
						var availableTags2=[];
						
						for(var i=0;i<r.data.length;i++){
							availableTags2.push(r.data[i].nm_model);
						}
						    $( "#modelDataForAddItem" ).autocomplete({
						      source: availableTags2
						    });
						
					}
					
				}, 'json');
				 
			}
	
	function getDetails() {

		var id_model = $('input[name="nm_model"]').val();

		$.post('M_Model', {
			action : 'GetModeldetails',
			id : id_model
		}, function(r) {
			if (r.data.length>0) {
				$('input[name="id_model"]').val(r.data[0].id_model);
				
				$('select[name=id_grp] option[value=' + r.data[0].id_assetdiv + ']').attr('selected',true);
				SubDropDownDataDisplay(r.data[0].id_assetdiv,'subgroupDataForAddAsset','M_Subasset_Div',function (status){
					
					$('select[name=id_sgrp] option[value=' + r.data[0].id_s_assetdiv + ']').attr('selected',true);
					$('select[name=typ_asst] option[value=' + r.data[0].typ_asst + ']').attr('selected',true);
					});
				
				$('.hidecat').hide();
				$('input[name="ds_pro"]').val(id_model);
				$('input[name="ds_asst"]').val(id_model);
				$('input[name="no_model"]').val(id_model);
			}
			else{
				$('input[name="id_model"]').val("0");
				$('#groupDataForAddAsset').removeAttr('selected').find('option:first').attr('selected', 'selected');
				$('#subgroupDataForAddAsset').removeAttr('selected').find('option:first').attr('selected', 'selected');
				$('.hidecat').show();
				$('input[name="ds_pro"]').val(id_model);
				$('input[name="ds_asst"]').val(id_model);
				$('input[name="no_model"]').val(id_model);
			}
		}, 'json');
		 
	}
function getLocDetails() {

		var id = $('select[name="id_flr"]').val();

		$.post('M_Floor', {
			action : 'Edit',
			id : id
		}, function(r) {
			if (r.data.length>0) {
				$('input[name="id_loc"]').val(r.data[0].id_loc);
				$('input[name="id_subl"]').val(r.data[0].id_sloc);
				$('input[name="id_building"]').val(r.data[0].id_building);
			}
		}, 'json');
		 
	}	

function getTax2Details(){
	var id_tax1 = $('select[name="id_tax1"]').val();
	var qty = $('input[name="qty"]').val();
	var un_prc = $('input[name="un_prc"]').val();
	var others = $('input[name="others"]').val();
	if(qty==''){
		
		//Swal.fire('Quantity can not be blank');
		Swal.fire({
  position: 'top',
  title: 'Mandatory field'
});	
		$('input[name="qty"]').val('1');
		qty=1;
	}
	if(qty=='0'){
		Swal.fire('Quantity can not be 0');
		$('input[name="qty"]').val('1');
		qty=1;
	}
	if(un_prc==''){
		$('input[name="un_prc"]').val('0');
		un_prc=0
	}
	if(others==''){
		$('input[name="others"]').val('0');
		others=0;
	}
	var total=(parseFloat(qty)*parseFloat(un_prc))+parseFloat(others);
	var taxVal1=0.0;
	var taxVal2=0.0;
	
	$.post('M_Tax', { action: 'getTax2Details', id_tax1: id_tax1 }, function(r) {
		if (r.data) {
			$('select[name="id_tax2"]').val(r.data[0].id_tax);
			var percent1 = r.data[0].per_tax1;
			var percent2 = r.data[0].per_tax2;
			taxVal1 = (parseFloat(total)*parseFloat(percent1))/100;
			taxVal2 = (parseFloat(total)*parseFloat(percent2))/100;
			$('input[name="tax_val1"]').val(taxVal1.toFixed(2));
				 $('input[name="tax_val2"]').val(taxVal2.toFixed(2));
				 var tax_val1 = $('input[name="tax_val1"]').val();
				 
				 var tax_val2 = $('input[name="tax_val2"]').val();
				 var discount = $('input[name="discount"]').val();
				if(discount==''){
		$('input[name="discount"]').val('0');
		discount=0;
	}
				 grnd_tot=parseFloat(total)+parseFloat(tax_val1)+parseFloat(tax_val2)-parseFloat(discount);
				
				 $('input[name="gr_tot"]').val(grnd_tot.toFixed(2));
			peritem_price=parseFloat(grnd_tot)/parseFloat(qty);
			$('input[name="val_asst"]').val(peritem_price.toFixed(2));
		}
	}, 'json');
}	

function CalculateTotalPriceItem1(){
	
	var qty = $('input[name="qty_asst"]').val();
	var un_prc = $('input[name="val_asst"]').val();
	if(qty==''){
		
		Swal.fire('Quantity can not be blank');
		$('input[name="qty"]').val('1');
		qty=1;
	}
	if(qty=='0'){
		Swal.fire('Quantity can not be 0');
		$('input[name="qty"]').val('1');
		qty=1;
	}
	if(un_prc==''){
		$('input[name="un_prc"]').val('0');
		un_prc=0
	}
	
		$('input[name="cst_asst"]').val(un_prc);
		$('input[name="tt_un_prc"]').val(un_prc);
	
	
}

function CalculateTotalPriceItem(){
	
	var qty = $('input[name="qty"]').val();
	var un_prc = $('input[name="un_prc"]').val();
	var others = $('input[name="others"]').val();
	if(qty==''){
		
		Swal.fire('Quantity can not be blank');
		$('input[name="qty"]').val('1');
		qty=1;
	}
	if(qty=='0'){
		Swal.fire('Quantity can not be 0');
		$('input[name="qty"]').val('1');
		qty=1;
	}
	if(un_prc==''){
		$('input[name="un_prc"]').val('0');
		un_prc=0
	}
	if(others==''){
		$('input[name="others"]').val('0');
		others=0;
	}
	var total=(parseFloat(qty)*parseFloat(un_prc))+parseFloat(others);
	 var tax_val1 = $('input[name="tax_val1"]').val();
	 var tax_val2 = $('input[name="tax_val2"]').val();
	var discount = $('input[name="discount"]').val();
	if(discount==''){
		$('input[name="discount"]').val('0');
		discount=0;
	}
				
				 grnd_tot=parseFloat(total)+parseFloat(tax_val1)+parseFloat(tax_val2)-parseFloat(discount);
				
				 $('input[name="gr_tot"]').val(grnd_tot.toFixed(2));
			peritem_price=parseFloat(grnd_tot)/parseFloat(qty);
			$('input[name="val_asst"]').val(peritem_price.toFixed(2));
}


function ControlNextSerial(qty) {
	SessionCheck(function(ses) {
		if (ses) {
			
			t = false;
			t = ValidationForm('submitInvoice');
			if (t) {
			$('#serialNo').show();
			$('#browseFile').show();
			$('#displayInvoiceForStore').hide();
			$('#uploaddisplay').show();
			//alert(type);
			var temp = '<center><table class="table table-bordered" style="width : 90%">' +
				'<tr class="tableHeader tableHeaderContent">' +
				'<td colspan="10"><center><strong><h3>Add Serial No</h3></strong></center></td>' +
				'</tr>';
			temp += '<tr><td colspan="10"><button type="button" style="float:right;" class="btn btn-primary" ' +
				'onclick="GenerateDynamicSerialNumber1('+qty+')">Do Not Have Serial No </button></td></tr>';
			
			for (var i = 0; i < qty; i++) {
				j = i + 1;
				temp += '<tr><td><strong>Serial No ' + j + '<font color="red"> * </font></strong></td>' +
					'<td><input type="text" style="max-width: 250px;" class="putNA form-control" name="sno' + i + '" value="" class="form-control removeErr" data-valid="mand"></td>' +
					'<td ><strong>Asset Ref. Num  ' + j + '</strong></td>' +
					'<td ><input type="text" name="sapNo' + i + '" value="NA" class="form-control removeErr"></td>' +
					'</tr>';
				$('input[name="serialnumbercount"]').val(i);
			}
			temp += '<tr><td colspan="6" style="text-align: center;"><button type="button"  class="btn btn-primary" ' +
				'onclick="ControlStore(\'Back\',\'displayInvoiceForStore\',\'serialNo\',\'AddToStore\')"> Back </button>&nbsp;' +
				'<button type="button"  class="btn btn-primary" ' +
				'onclick="ControlAddItem(\'Save\','+qty+')">Save </button></td></tr>' +
				'</table></center>';
			$('#serialNo').html(temp);
			}
		}
	});
}


function GenerateDynamicSerialNumber1(qty){
	
	
	$.post('A_Add_To_Store1',{action : 'SerialNoGeneration' , id_sgrp :"1"}, function(r){
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
	   $('#downloadexc12').click(function(e) {
		    e.preventDefault();  //stop the browser from following
		    window.location.href = 'InvoiceScanFile/Serial_Number.xlsx';
		});	
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
		
		
function ControlAddItem(Action,qty){
	
	if (Action == 'Save') {
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
						var st = DuplicateChecking1(val, sapVal, i);
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
						
						sapNo += sapVal;
						
					}
					else {
						SerialVal += ',,' + val;
						if (sapVal == '') {
							sapVal = 'NA';
						}
						
						sapNo += ',,' + sapVal;
						
					}
				}
				if (t == 1) {
					CheckSerialNoAndSapNo1(SerialVal, sapNo, function(status) {
						if (status) {
							$('input[name="SerialVal"]').val(SerialVal);
							$('input[name="sapNo"]').val(sapNo);
							 t = true;
							var x = $('#submitInvoice').serialize();
							$.post('A_Add_Item_To_Store', x, function(r) {
						if (r.data) {
							t = true;
						}
						else {
							t = false;
							bootbox.alert("Data is not inserted in database please try again.");
						}
						if (t) {
							Swal.fire({
				    			  position: 'top',
				    			  icon: 'success',
				    			  title: 'Successfully added.',
				    			  showConfirmButton: false,
				    			  timer: 2000
				    			});
							//bootbox.alert("");
							window.location = $('.additemstore_event').attr('href');
						}
						$('.store').removeAttr('disabled');
					}, 'json');
						}
					});
				}
			}
}		

function DuplicateChecking1(ConstantVal, ConstantSapVal, countLoop) {
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

function CheckSerialNoAndSapNo1(SerialVal, sapNo, callback) {
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