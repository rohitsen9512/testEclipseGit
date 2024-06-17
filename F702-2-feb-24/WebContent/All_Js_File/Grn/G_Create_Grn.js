
function ControlInvoiceGRN(action,displayContent,createDetails,formName,servletName) {
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
	if(action=='Accept' || action=='Reject')
		{
		$( ".invoiceByPOBtnForDissable" ).attr('Disable',true);
			if(action == 'Accept')
				{
				$('button[name="save1"]').removeClass('hideButton');
				$('input[name="action"]').val('Accept');
				}
			else
				{
					$('input[name="action"]').val('Reject');
				}
			
			DataInsertUpdateForPurchaseOrder("Cancel",displayContent,createDetails,formName,servletName);
			
		}
	
			}
});
}

function DataInsertUpdateForWorkOrder(action,displayContent,createDetails,formName,servletName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var t=false;
			var id_inv="0",itemCount='0';
			$('.prRequestItemForIndentSelectAll').each(function(){
				if(this.checked)
				{
					t=true;
					if(id_inv == '0')
						id_inv = $(this).val();
						else
							id_inv +='Patel'+ $(this).val();
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
				
				$('input[name="id_inv_ass"]').val(id_inv);
				t=false;
				t = ValidationForm(formName);
			}		
	
	
		if(t)
		{
		
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
		
		
		var x = $('#'+formName).serialize();
		$('.invoiceByPOBtnForDissable').attr('disabled','disabled');
			$.post(servletName,x,function (r){
				
				if(r.data1 == 1)
					{
					 bootbox.confirm("Are you sure ?", function(result) {
							if(result)
							{
					$('#createAccessory').hide();
					bootbox.alert('GRN updated successfully');
					$( ".woUpdateInvoice" ).trigger( "click" );
					//DisplayWorkOrderInvoiceByPO(servletName,displayContent,createDetails,r.data,'accessoryForDisplay');
						
			            //$('#'+createDetails).show();
						//$('#displayAccessory').show();
							}});}
				else
					{
						bootbox.alert('GRN updated successfully');
						$( ".woUpdateInvoice" ).trigger( "click" );
					}
				$('.invoiceByPOBtnForDissable').removeAttr('disabled');
			},'json');
	
		
		}
			}
});
}

function DataInsertUpdateForPurchaseOrder(action,displayContent,createDetails,formName,servletName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var t=false;
			var id_inv="0",itemCount='0';
	
		
			console.log('bbbb');
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
		
		
		var x = $('#'+formName).serialize();
		//alert(servletName);
		//alert(x);
		$('.invoiceByPOBtnForDissable').attr('disabled','disabled');
			$.post(servletName,x,function (r){
				
				if(r.data1 == 1)
					{
					$('#createAccessory').hide();
					bootbox.alert('GRN Updated successfully');
						$( ".creategrn" ).trigger( "click" );	
			            $('#'+createDetails).show();
						$('#displayAccessory').show();
					}
				else
					{
						bootbox.alert('Try Again');
						//$( ".creategrn" ).trigger( "click" );
					}
				$('.invoiceByPOBtnForDissable').removeAttr('disabled');
			},'json');
	
			}
});
}

function BeforeEditAssetPOAccept(servletName,displayContent,createDetails,id){
	
	EditAssetPO(servletName,displayContent,createDetails,id);
}

function EditAssetPO(servletName,displayContent,createDetails,id)
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
	
	$('button[name="save"]').addClass('hideButton');
	$('button[name="save1"]').addClass('hideButton');
	$('button[name="update"]').addClass('hideButton');
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
						
						$('input[name="dt_inv"]').val(r.data[0].dtinv);
						$('input[name="dt_grn"]').val(r.data[0].dtgrn);
						$('input[name="action"]').val("Save");
						$('input[name="id"]').val(id);
						$('input[name="id_ven"]').val(r.data[0].id_ven);
						$('textarea[name="remarks"]').val(r.data[0].remarks);	
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
					DisplayAccessoryForPO('G_Create_Grn','displayInvoice','createInvoice',id,'accessoryForDisplay');
				
					
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
								'<td style="min-width:100px;"><strong><center>Item Name</center></strong></td>'+
								'<td style=""><strong><center>GRN Qty</center></strong></td>'+
								'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
								'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
									
							'</tr>';
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				var rem_qty = params.rem_qty;
				var qty = params.qty;
					
						list = list + '<tr class="success">'+
						'<td><center>'+params.nm_model+'</center></td>'+
						'<td><center>'+params.qty+'</center></td>'+
						'<td><center>'+params.un_prc+'</center></td>'+
						'<td><center>'+params.nm_tax1+'</center></td>'+
						'<td><center>'+params.nm_tax2+'</center></td>'+
						'<td><center>'+params.tax_val1+'</center></td>'+
						'<td><center>'+params.tax_val2+'</center></td>'+
						'<td><center>'+params.gr_tot+'</center></td>'+
						
					'</tr>';
						
						
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


function DisplayinvoiceforGRNAccept(servletName,displayContent,createDetails,no_inv,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var dt_frm =$('#dt_frm').val();
			var dt_to =$('#dt_to').val();
			var searchWord = $('input[name="search"').val();
				$.post(servletName,{action : 'Display' , no_inv : no_inv,dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><center><strong>GRN Number</strong></center></td>'+
					'<td><center><strong>GRN Date</strong></center></td>'+
					'<td><center><strong>Invoice Number</strong></center></td>'+
					'<td><center><strong>Invoice Date</strong></center></td>'+
					'<td><strong><center>Vendor</center></strong></td>'+
					'<td style="width: 170px;"><strong><center><a href="#"> Accept/Reject</a></center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
							
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_grn+'</center></td>'+
						'<td><center>'+params.dtgrn+'</center></td>'+
						'<td><center>'+params.no_inv+'</center></td>'+
						'<td><center>'+params.dtinv+'</center></td>'+
						'<td><center>'+params.nm_ven+'</center></td>'+
						'<td><strong><center><a class="alert" href="#" onclick="BeforeEditAssetPOAccept(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_grn+')"> Accept/Reject </a></center></strong></td>'+
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


