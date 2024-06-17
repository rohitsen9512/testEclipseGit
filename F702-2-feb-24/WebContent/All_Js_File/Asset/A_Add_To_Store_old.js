
function CheckSerialNoAndSapNo(SerialVal,sapNo,callback)
{
	var t=true;
	$('.removeErr').removeClass('error');
	$.post('A_Add_To_Store',{action : 'CheckSlAndSpNo',SerialVal : SerialVal , sapNo : sapNo},function (r)
			{
				if(r.data == 1)
					{
						t=false;
						var count = (r.Count)-1;
						alert('Value already exist.');
						$('input[name="sno'+count+'"]').val('');
						$('input[name="sno'+count+'"]').addClass('error');
						$('input[name="sno'+count+'"]').focus();
					
					}
				 /*if(r.data == 2)
					{
						t=false;
						var count = (r.Count)-1;
						alert('Value all ready exits.');
						$('input[name="sapNo'+count+'"]').val('');
						$('input[name="sapNo'+count+'"]').addClass('error');
						$('input[name="sapNo'+count+'"]').focus();
					}*/
		
				callback(t);
			},'json');
	
	

}


function AddToStore(id_inv , qty , id_inv_m,id_grn)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	$('#serialNo').show();
	$('#browseFile').show();
	$('#displayAddToStore').hide();
	
	var temp ='<center><table class="table table-bordered" style="width : 65%">' +
					'<tr class="tableHeader tableHeaderContent"><td colspan="4"><center><strong>Add Serial No</strong></center></td></tr>' ;
	
	
	for(var i = 0 ; i < qty ; i++)
		{
			j=i+1;
			temp += '<tr><td><strong>Serial No. '+j+'<font color="red">*</font></strong></td>'+
				'<td><input type="text" name="sno'+i+'" value="" class="common-validation removeErr" data-valid="mand"></td>'+
				'<td><strong>Oracle Apps Num  '+j+'<font color="red">*</font></strong></td>'+
				'<td><input type="text" name="sapNo'+i+'" value="" class="common-validation removeErr" data-valid="mand"></td>'+
				'</tr>';
		}
	
	temp += '<tr><td colspan="4"><button type="button" style="margin-left: 260px;" class="btn btn-primary" '+
			'onclick="ControlStore(\'Back\',\'displayAddToStore\',\'serialNo\',\'AddToStore\')"> Back </button>&nbsp;&nbsp;&nbsp;'+
			
			'<button type="button"  class="btn btn-primary" '+
			'onclick="ControlStore(\'Next\',\'displayAddToStore\',\'serialNo\',\'AddToStore\','+id_inv+','+id_inv_m+','+qty+','+id_grn+')">Next </button></td></tr>'+
			'</table></center>';
			
	
	
	$('#serialNo').html(temp);
			}});
}
function DuplicateChecking(ConstantVal , ConstantSapVal , countLoop)
{
	$('.removeErr').removeClass('error');
	var t=true;
	for(var i = 0 ; i < countLoop ; i++)
	{
		t=true;
		var val = $('input[name="sno'+i+'"]').val();
		var sapNo = $('input[name="sapNo'+i+'"]').val();
		if(val != "")
		{
			if(val == ConstantVal)
				{
				t=false;
					alert('Duplicate value is not allowed.');
					$('input[name="sno'+i+'"]').val('');
					$('input[name="sno'+i+'"]').addClass('error');
					$('input[name="sno'+i+'"]').focus();
					
				}
		}
	 /*if(sapNo != "")
		{
			if(sapNo == ConstantSapVal)
			{
				alert('Duplicate value is not allowed.');
				$('input[name="sapNo'+i+'"]').val('');
				$('input[name="sapNo'+i+'"]').addClass('error');
				$('input[name="sapNo'+i+'"]').focus();
				t=false;
			}
		
		}*/
		
	}
return t;

}

function ControlStore(Action , DisplayDiv , HideDiv , HideDiv2 , id_inv ,id_inv_m ,qty,id_grn)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			
	if(Action == 'Back')
		{
			$('#'+HideDiv).hide();
			$('#'+HideDiv2).hide();
			$('#'+DisplayDiv).show();
			$('#browseFile').hide();
		}
	
	if(Action == 'Next')
	{
		var t=1,SerialVal='',sapNo='',appVal='';
		$('input[name="SerialVal"]').val('');
		for(var i = 0 ; i < qty ; i++)
			{
			$('input[name="sno'+i+'"]').removeClass('error');
			$('input[name="sapNo'+i+'"]').removeClass('error');
				val = $('input[name="sno'+i+'"]').val();
				var sapVal = $('input[name="sapNo'+i+'"]').val();
				var appNo = $('input[name="appNo'+i+'"]').val();
				if(val == "")
					{
						t=0;
						alert("Mandatory Field");
						$('input[name="sno'+i+'"]').focus();
						$('input[name="sno'+i+'"]').addClass('error');
						exit();
					}
				else if(sapVal == "")
				{
					t=0;
					alert("Mandatory Field");
					$('input[name="sapNo'+i+'"]').focus();
					$('input[name="sapNo'+i+'"]').addClass('error');
					exit();
				}
				else
					{
						var st = DuplicateChecking(val , sapVal , i);
						if(!st)
						{
							t=0;
							exit();
						}
					}
				if(SerialVal == "")
				{
					SerialVal += val;
					
					if(sapVal == '')
						{
						sapVal = 'NA';
						}
					if(appNo == '')
					{
						appNo = 'NA';
					}
					
					sapNo += sapVal;
					appVal += appNo;
				}
			else
				{
					SerialVal +=',,'+val;
					if(sapVal == '')
					{
						sapVal = 'NA';
					}
					if(appNo == '')
					{
						appNo = 'NA';
					}
					
					sapNo +=',,'+sapVal;
					appVal +=',,'+appNo;
				}
			
			}
		
		if(t == 1)
			{
			
			 CheckSerialNoAndSapNo(SerialVal,sapNo,function(status){
				if(status)
					{
						$('input[name="SerialVal"]').val(SerialVal);
						$('input[name="sapNo"]').val(sapNo);
						$('input[name="appNo"]').val(appVal);
						
						//DisplayDropDownData('M_Currency','currDataForAddToStore');
						//DisplayDropDownData('M_Vendor','vendorDataForAddToStore');
						
						AddInputValueForAddToStore(id_inv ,id_inv_m ,qty,id_grn);
						$('#browseFile').hide();
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).hide();
						$('#'+HideDiv2).show();
					}
			});
			
				/*$('input[name="SerialVal"]').val(SerialVal);
				$('input[name="sapNo"]').val(sapNo);
				
				//DisplayDropDownData('M_Currency','currDataForAddToStore');
				//DisplayDropDownData('M_Vendor','vendorDataForAddToStore');
				
				AddInputValueForAddToStore(id_inv ,id_inv_m ,qty,id_grn);
				
				$('#'+HideDiv).hide();
				$('#'+DisplayDiv).hide();
				$('#'+HideDiv2).show();*/
			
			}
	}
	
	if(Action == 'Save')
	{
		
		var t = false;
		t = ValidationForm("AddToWH");
		var val = $('#st_lease1').val();
		var warr_amc =$('#warr_amc1').val();
		var ins =$('#ins1').val();
		var cal =$('#cal1').val();
		if(cal == 'Yes')
		{
			t = ShowRowColumnValidation('calAddToStoreCommonClass1');
		}
	else
		{
		t=true;
		}
		if(ins == 'Yes')
		{
			t = ShowRowColumnValidation('insAddToStoreCommonClass1');
		}
	else
		{
		t=true;
		}
		if(warr_amc == 'A' || warr_amc == 'W')
		{
			t = ShowRowColumnValidation('amcAddToStoreCommonClass1');
			
		}
	else
		{
		t=true;
		$('input[name="dt_amc_start"]').val('');
		$('input[name="dt_amc_exp"]').val('');
		}
		if(val == 'UL')
			{
				t = ShowRowColumnValidation('leaseAddToStoreCommonClass1');
				
			}
		else
			{
			t=true;
			$('input[name="std_lease"]').val('');
			$('input[name="endt_lease"]').val('');
			}
		
		var x = $('#AddToWH').serialize();
		
		if(t)
			{
			$('.store').attr('disabled','disabled');

			
				
				$.post('A_Add_To_Store1',x,function (r)
						{
					
							if(r.data)
								{
								
									t=true;
								}
								else
									{
										t=false;
										bootbox.alert("Data is not inserted in database please try again.");
									}
									
							if(t)
							{
								bootbox.alert("Successfully added.");
								//$( ".addToStore1" ).trigger( "click" );
								window.location = $('.addToStore1_event').attr('href');
								/*DisplayAccessoryForAddToStore("A_Add_To_Store");
								$('#'+HideDiv).hide();
								$('#'+HideDiv2).hide();
								$('#'+DisplayDiv).show();*/
							}
							$('.store').removeAttr('disabled');
						},'json');
				
				
			
			}
		
		
	}
			}});
}


function AddInputValueForAddToStore(id_inv ,id_inv_m ,qty,id_grn)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	$.post('A_Add_To_Store1',{action : 'Edit',id_inv : id_inv , id_inv_m : id_inv_m},function (r)
			{
		
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						
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
									
									
									
								}
						$('input[name="id_grn"]').val(id_grn);
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
						/*$('input[name="id_storage"]').val(r.data[0].id_storage);*/
						$('input[name="id_inv"]').val(id_inv);
						$('input[name="id_inv_m"]').val(r.data[0].id_inv_m);
						$('input[name="qty"]').val(qty);
						$('input[name="id_curr"]').val(r.data[0].id_curr);
						$('input[name="typ_asst"]').val(r.data[0].typ_asst);
						$('input[name="nm_acc_asset"]').val(r.data[0].nm_acc_asset);
						$('input[name="total_prc"]').val(qty*(r.data[0].cst_asst));
						$('input[name="val_asst"]').val((r.data[0].val_in_inr)*(1.00));
						$('input[name="dt_po"]').val(r.data[0].dtPo);
						$('input[name="dt_inv"]').val(r.data[0].dtInv);
						/*$('input[name="no_bd"]').val(r.data[0].no_bd);
						$('input[name="dt_bd"]').val(r.data[0].dt_bd);*/
						$('input[name="bd"]').val(r.data[0].bd);
					}
					else
							bootbox.alert("Try again.");
				
				
			
				
				
			},'json');
			}});
	}

function DisplayAccessoryForAddToStore(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	
	
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
			
				$.post(servletName,{action : 'Display' , dt_frm : dt_frm , dt_to : dt_to } , function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Invoice No</strong></td>'+
					'<td><strong>PO No.</strong></td>'+
					'<td><strong>DC No.</strong></td>'+
					'<td><strong>GRN Number</strong></td>'+
					'<td><strong>GRN Date</strong></td>'+
					'<td><strong>Quantity</strong></td>'+
					'<td style="width: 155px;"><strong>Add To Store</strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_inv+'</center></td>'+
						'<td><center>'+params.no_po+'</center></td>'+
						'<td><center>'+params.no_dc+'</center></td>'+
						'<td><center>'+params.no_grn+'</center></td>'+
						'<td><center>'+params.dtGrn+'</center></td>'+
						'<td><center>'+params.qty_recv+'</center></td>'+
						'<td><strong><a class="alert" href="#" onclick="AddToStore('+params.id_inv+','+params.qty_recv+','+params.id_inv_m+','+params.id_grn+')"> Add To Store </a></strong></td>'+
						'</tr>';
						}
					
					
						$('.addToStoreForDisplay').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
						$('.addToStoreForDisplay').html(list);
					}
					
				
			},'json');
		
			}});
}


$( ".amctDatepicker" ).datepicker({
	yearRange: '1985:2025',
      changeMonth: true,
      changeYear: true,
      dateFormat: "dd/mm/yy",
      autoSize: true,
      altFormat: "dd/mm/yy",
      onSelect: function(selected,evnt) {
    	  $(this).removeClass('error');
    	  var dt_amc_start=$('input[name="dt_amc_start"]').val();
    	  var dt_amc_start1=$('input[name="dt_amc_start"]').val();
    	  var dt_end = $(this).val();
    	  if(dt_amc_start == '')
    		  {
    		  	alert('First filled start  date.');
    		  	$('input[name="dt_amc_start"]').focus();
    		  	$('input[name="dt_amc_start"]').addClass('error');
    		  	$('input[name="dt_amc_start"]').val('');
    		  	$(this).val('');
    		  	exit(0);
    		  }
    	  else
    		  {
    		  var temp_strt = dt_amc_start.split("/");
			  var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
					
				var temp_end = dt_end.split("/");
				var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
					
				dt_amc_start = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
				dt_end = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
    		  
    		  if(dt_amc_start > dt_end)
    			  {
    			  	alert('End date should be greater or equal to start date : '+dt_amc_start1);
    			  	$(this).focus();
    			  	$(this).val('');
    			  	$(this).addClass('error');
    			  	exit(0);
    			  }
    		  
    		  }
    	  
      }
    });

function DisplayInvoiceForAdd(servletName , ClassNameForDisplayData)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var dt_frm =$('input[name="dt_frm"]').val();
			var dt_to =$('input[name="dt_to"]').val();
			//var invType =$('select[name="invType"]').val();
			
				$.post(servletName,{action : 'Display',dt_frm:dt_frm,dt_to:dt_to},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Invoice Number</strong></td>'+
					'<td><strong>Invoice Date</strong></td>'+
					'<td><strong>PO Number</strong></td>'+
					/*'<td><strong>PO Date</strong></td>'+*/
					'<td><strong>Asset Name</strong></td>'+
					'<td><strong>Vendor</strong></td>'+
					'<td><strong>Quantity</strong></td>'+
				//	'<td><strong>Remaining Qty</strong></td>'+
					'<td><strong><a href="#">Add To Store </a></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_inv+'</center></td>'+
											'<td><center>'+params.dtInv+'</center></td>'+
											'<td><center>'+params.no_po+'</center></td>'+
											/*'<td><center>'+params.dt_po+'</center></td>'+*/
											'<td><center>'+params.ds_pro+'</center></td>'+
											'<td><center>'+params.nm_ven+'</center></td>'+
											'<td><center>'+params.qty+'</center></td>'+
											//'<td><center>'+params.ReminQty+'</center></td>'+
											'<td><strong><a class="alert" href="#" onclick="AddToStore('+params.id_inv+','+params.qty+','+params.id_inv_m+','+params.id_grn+')"> Add To Store </a></strong></td>'+
											'</tr>';
						}
					
						$('.'+ClassNameForDisplayData).html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
						$('.'+ClassNameForDisplayData).html(list);
					}
				
			},'json');
			}});
}

function DisplayInvoiceForADD(servletName , ClassNameForDisplayData)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var dt_frm =$('input[name="dt_frm"]').val();
			var dt_to =$('input[name="dt_to"]').val();
			//var invType =$('select[name="invType"]').val();
			
				$.post(servletName,{action : 'Display',dt_frm:dt_frm,dt_to:dt_to},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Invoice Number</strong></td>'+
					'<td><strong>Invoice Date</strong></td>'+
					'<td><strong>PO Number</strong></td>'+
					/*'<td><strong>PO Date</strong></td>'+*/
					'<td><strong>Asset Name</strong></td>'+
					'<td><strong>Vendor</strong></td>'+
					'<td><strong>Total Qty</strong></td>'+
					'<td><strong>Remaining Qty</strong></td>'+
					'<td><strong>Create Grn </strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_inv+'</center></td>'+
											'<td><center>'+params.dtInv+'</center></td>'+
											'<td><center>'+params.no_po+'</center></td>'+
											/*'<td><center>'+params.dt_po+'</center></td>'+*/
											'<td><center>'+params.ds_pro+'</center></td>'+
											'<td><center>'+params.nm_ven+'</center></td>'+
											'<td><center>'+params.qty+'</center></td>'+
											'<td><center>'+params.ReminQty+'</center></td>'+
											'<td><strong><a class="alert" href="#" onclick="CreateGrnFun('+params.id_inv_m+' , '+params.qty+' , '+params.id_inv+')"> Create Grn </a></strong></td>'+
											'</tr>';
						}
					
						$('.'+ClassNameForDisplayData).html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
						$('.'+ClassNameForDisplayData).html(list);
					}
				
			},'json');
			}});
}

function DisplayInvoiceForAddToStore(servletName , ClassNameForDisplayData)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			$('#serialNo').hide();
			$('#AddToStore').hide();
			var dt_frm =$('input[name="dt_frm"]').val();
			var dt_to =$('input[name="dt_to"]').val();
			//var invType =$('select[name="invType"]').val();
			
				$.post(servletName,{action : 'Display',dt_frm:dt_frm,dt_to:dt_to},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong><center>Invoice Number</center></strong></td>'+
					'<td><strong><center>Invoice Date</center></strong></td>'+
					'<td><strong><center>PO Number</center></strong></td>'+
					/*'<td><strong>PO Date</strong></td>'+*/
					'<td><strong><center>Asset Name</center></strong></td>'+
					'<td><strong><center>Vendor</center></strong></td>'+
					'<td><strong><center>Total Qty</center></strong></td>'+
					'<td><strong>Department Name</strong></td>'+
					'<td><strong><center>Add To Store </center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_inv+'</center></td>'+
											'<td><center>'+params.dtInv+'</center></td>'+
											'<td><center>'+params.no_po+'</center></td>'+
											/*'<td><center>'+params.dt_po+'</center></td>'+*/
											'<td><center>'+params.ds_pro+'</center></td>'+
											'<td><center>'+params.nm_ven+'</center></td>'+
											'<td><center>'+params.qty+'</center></td>'+
											'<td><center>'+params.nm_dept+'</center></td>'+
											'<td><strong><center><a class="alert" href="#" onclick="AddToStore1('+params.id_grn_asset+','+params.qty+','+params.id_grn+','+params.id_sgrp+',\''+params.typ_asst+'\')"> Add To Store </a></center></strong></td>'+
											'</tr>';
						}
					
						$('.'+ClassNameForDisplayData).html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
						$('.'+ClassNameForDisplayData).html(list);
					}
				
			},'json');
			}});
}

function AddToStore1(id_inv , qty , id_inv_m, id_grn , typAsst)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	$('#serialNo').show();
	$('#browseFile').show();
	$('#displayInvoiceForStore').hide();
	
	var temp ='<center><table class="table table-bordered" style="width : 90%">' +
					'<tr class="tableHeader tableHeaderContent"><td colspan="6"><center><strong>Add Serial No</strong></center></td></tr>' ;
	if(typAsst == 'NIT'){
		temp += '<tr><td colspan="6"><button type="button" style="float:right;" class="btn btn-primary" '+
				'onclick="GenerateDynamicSerialNumber('+id_grn+','+qty+')">Generate Serial No </button></td></tr>';
	}
	for(var i = 0 ; i < qty ; i++)
		{
			j=i+1;
			temp +='<tr><td><strong>Serial No '+j+'<font color="red">*</font></strong></td>'+
				'<td><input type="text" name="sno'+i+'" value="" class="common-validation removeErr" data-valid="mand"></td>'+
				'<td><strong>Tag Num  '+j+'<font color="red">*</font></strong></td>'+
				'<td><input type="text" name="sapNo'+i+'" value="" class="common-validation removeErr" data-valid="mand"></td>'+
				
				'<td><strong>Oracle Apps Num  '+j+'<font color="red"> </font></strong></td>'+
				'<td><input type="text" name="appNo'+i+'" value="NA" class="common-validation removeErr" data-valid=""></td>'+
				'</tr>';
		}
	
	temp += '<tr><td colspan="6"><button type="button" style="margin-left: 300px;" class="btn btn-primary" '+
			'onclick="ControlStore(\'Back\',\'displayInvoiceForStore\',\'serialNo\',\'AddToStore\')"> Back </button>&nbsp;'+
			
			'<button type="button"  class="btn btn-primary" '+
			'onclick="ControlStore(\'Next\',\'displayInvoiceForStore\',\'serialNo\',\'AddToStore\','+id_inv+','+id_inv_m+','+qty+',\'\')">Next </button></td></tr>'+
			'</table></center>';
	
		
	$('#serialNo').html(temp);
	
	
			}});
}

function GenerateDynamicSerialNumber(id_sgrp , qty){
	
	
	$.post('A_Add_To_Store1',{action : 'SerialNoGeneration' , id_sgrp :id_sgrp}, function(r){
		if(r.data){
			var slNo = r.data[0].slNo;
			var sName = r.data[0].cd_s_assetdiv;
			var value='';
			for(var i=0 ; i < qty ; i++){
				value = sName + '-' +slNo;
				$('input[name="sno'+i+'"]').val(value);
				$('input[name="sapNo'+i+'"]').val(value);
				
				slNo++;
			}
		}
		
		
	},'json');
	
	
	
}