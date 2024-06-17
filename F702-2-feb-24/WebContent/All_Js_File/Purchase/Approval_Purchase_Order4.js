function ControlApprovalPurchaseOrder4(action,DisplayDiv,HideDiv,id_po)
{
	
	if(action == 'Cancel')
	{
		$('#'+DisplayDiv).show();
		$('#'+HideDiv).hide();
	
	}
	if(action == 'Modify')
	{
		PurchaseOrderForApprove4(action,DisplayDiv,HideDiv,id_po);
	}
	if(action =='Accepted' || action =='Rejected')
	{
		var dt_approv = $('input[name="dt_approv"]').val();
		$('input[name="dt_approv"]').removeClass('error');
		
		if(dt_approv != '')
		{
		$.post('Approval_Purchase_Order4',{action:'CheckDate',id_po:id_po,dt_approv:dt_approv},function (r){
		if(r.data == 1)
		{
			$('.poApprv4').attr('disabled','disabled');
			$.post('Approval_Purchase_Order4',{action:'Update',status_approv:action,id_po:id_po,dt_approv:dt_approv},function (r){
				if(r.data == 1)
					{
						bootbox.alert("Updated Successfully.");
						$( ".approvePurchaseOrder4" ).trigger( "click" );
					}
				else
				{
					bootbox.alert("Something went wrong Please try again.");
				}
				$('.poApprv4').removeAttr('disabled');	
			},'json');
					
		}
		else
			{
			alert('Approval date should be greater or equal to first approval P.O date : '+r.dt_approv);
			$('input[name="dt_approv"]').focus();
			$('input[name="dt_approv"]').val('');
			$('input[name="dt_approv"]').addClass('error');
		  	exit(0);
			
			}
		},'json');
		}
		else
			{
			alert('Madatory Field.');
			$('input[name="dt_approv"]').focus();
			$('input[name="dt_approv"]').val('');
			$('input[name="dt_approv"]').addClass('error');
		  	exit(0);
			}
		
	}

}


function PurchaseOrderForApprove4(action,DisplayDiv,HideDiv,id_po)
{
	$.post('Approval_Purchase_Order4',{action : 'Edit',id_po:id_po},function (r){
		if(r.data.length > 0)
			{
			var list ='<tr class="tableHeader info">'+
			'<td style="width:150px;"><strong><center>Asset Name<a href=#></a></center></strong></td>'+
			'<td><strong><center>Description</center></strong></td>'+
			'<td><strong><center>Delivery Date <font color="red">*</font></center></strong></td>'+
			'<td style="width:70px;"><strong><center>Quantity <font color="red">*</font></center></strong></td>'+
			'<td><strong><center>Unit Price<font color="red">*</font></center></strong></td>'+
			'<td style="width: 120px;"><strong><center>Tax Name</center></strong></td>'+
			'<td style="width: 75px;"><strong><center>Tax Value</center></strong></td>'+
			'<td><strong><center>Total Price</center></strong></td>'+
		'</tr>';
			for(var i=0;i<r.data.length;i++)
				{
				params=r.data[i];
				for (var key in r.data[i])
		        {
					
						if($('select[name='+key+']').is("select"))
						{
							$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
						}
						else
						{
							$('input[name='+key+']').val(r.data[i][key]);
						}
						
		        }
				
				$('input[name="dt_po"]').val(r.data[i].dtpo);
				var currentDate = new Date();
				$( ".datepicker111" ).datepicker({
					yearRange: '1985:2025',
				      changeMonth: true,
				      changeYear: true,
				      dateFormat: "dd/mm/yy",
				      autoSize: true,
				      altFormat: "dd/mm/yy",
				      
				    });
				$('input[name="dt_approv"]').datepicker("setDate", currentDate);
				params = r.data[i];
				
				list += '<tr class="success">'+
				'<td style="width:150px;"><center>'+params.nm_prod+'</center></td>'+
				'<td><center><input type="text" name="description'+i+'" value="'+params.description+'" class="common-validation" readonly></center></td>'+
				'<td><center><input type="text" name="dt_rec_quot'+i+'" value="'+params.dtrecv+'" style="width: 100px;" readonly class="common-validation datepicker" data-valid="mand"></center></td>'+
				'<td><center><input type="text" name="qty'+i+'" patelqty'+params.id_prod+'="'+params.qty+'" value="'+params.qty+'" style="width: 80px;" class="patelQty" readonly></center></td>'+
				
				'<input type="hidden" name="count" value="'+i+'">'+
				'<input type="hidden" name="id_po_asst" value="'+params.id_po_asst+'">'+
				
				'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" readonly value="'+params.un_prc+'" style="width: 80px;" onBlur="calculateTot(event)" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
				'<td><center>'+params.nm_tax+'</center></td>'+
				'<td><center>'+params.tax_val+'</center></td>'+
				'<td><center><input type="text" name="tot_prc'+i+'" value="'+params.tot_prc+'" style="width: 80px;" class="commonTotal" readonly></center></td>'+
				'</tr>';
				}
			
			list += '<tr>'+
			'<input type="hidden" name="id_po" value="'+id_po+'">'+
				'<td style="border-top-style: hidden;"><b>Approver Name 1 </b></td>'+
				'<td colspan="5" style="border-style: hidden;">'+params.nm_emp+'</td>'+
				'<td><b style="float:right;">Total</b></td>'+
				'<td><center><input type="text" name="tot" value="'+r.data[0].tot+'" style="width: 80px;" class="common-validation" readonly></center></td>'+
			'</tr>'+
			/*'<tr>'+
			'<td colspan="3"><strong style="float:right;">Tax Percentage :</strong></td>'+
			'<td style="border-left-style: hidden;"><input type="text" name="tax_per" value="'+params.tax_per+'" style="width: 80px;" class="common-validation" readonly></td>'+
			'</td>'+
			'<td style="border-left-style: hidden;"><strong style="float:right;">Tax Price :</strong></td>'+
			'<td><input type="text" name="tax_prc" value="'+params.tax_prc+'" style="width: 80px;" class="common-validation" readonly></td>'+
		'</tr>'+*/
			'<tr>'+
			'<td style="border-top-style: hidden;"><b>Approver Name 2 </b></td>'+
			'<td colspan="5" style="border-style: hidden;">'+params.nm_emp2+'</td>'+
			'<td  style="border-top-style: hidden;border-left-style: hidden;"><b style="float:right;">Insurance </b></td>'+
			'<td><center><input type="text" name="insurance" style="width: 80px;border-left-style: hidden;" value="'+params.insurance+'" class="common-validation" data-valid="num" readonly></center></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="border-top-style: hidden;"><b>Approver Name 3 </b></td>'+
			'<td colspan="5" style="border-style: hidden;">'+params.nm_emp3+'</td>'+
			'<td style="border-top-style: hidden;border-left-style: hidden;"><b style="float:right;">Frieght </b></td>'+
			'<td><center><input type="text" style="width: 80px;" name="frieght" value="'+params.frieght+'" class="common-validation" data-valid="num" readonly></center></td>'+
		'</tr>'+
		
		'<tr>'+
			'<td colspan="7" style="border-top-style: hidden;"><strong style="float:right;">Grand Total ( '+r.data[0].nm_curr+' )</strong></td>'+
			'<td><center><input type="text" name="gr_tot" value="'+r.data[0].gr_tot+'" style="width: 80px;" class="common-validation" readonly></center></td>'+
		'</tr>'+
			
		'</tr>'+
		'<tr>'+
		'<td colspan="2"><b style="float:right;">Term & Conditions :</b></td>'+
			'<td colspan="6">'+
			'<textarea style="margin-left: 0px;margin-right: 0px;width: 500px; height: 73px;"  name="po_t_c" readonly>'+
			r.data[0].po_t_c+
					'</textarea>'+
				'</td>'+
	    '</tr>'+

		 '<tr>'+
         '	<td colspan="8">'+
         '	<button type="button" style="margin-left: 400px;" class="btn btn-primary poApprv3" onclick="ControlApprovalPurchaseOrder4(\'Accepted\',\'displayApprovePurchaseOrder4\',\'createApprovePurchaseOrder4\','+id_po+')">Approve </button>&nbsp;&nbsp;'+
         '	<button type="button" class="btn btn-primary poApprv3" onclick="ControlApprovalPurchaseOrder4(\'Rejected\',\'displayApprovePurchaseOrder4\',\'createApprovePurchaseOrder4\','+id_po+')">Reject </button>&nbsp;&nbsp;'+
         '	<button type="button" class="btn btn-primary poApprv3" onclick="ControlApprovalPurchaseOrder4(\'Cancel\',\'displayApprovePurchaseOrder4\',\'createApprovePurchaseOrder4\',\'\')">Cancel </button>'+
		 '	</td>'+
		'</tr>';
			
				
			$('.PurchaseOrderDetailsForApproval4').html(list);
			
			$('#'+DisplayDiv).show();
			$('#'+HideDiv).hide();
			}
		
		
	},'json');


}



function DisplayPurchaseOrderForApproval4()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var typ_po =$('select[name="typ_po"]').val();
	
		$.post('Approval_Purchase_Order4',{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,typ_po:typ_po},function (r){

			var list= '<tr>'+
			'<td colspan="7" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 325px;">Purchase Order For Approval </p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
				'<td><center><strong>PO Number</strong></center></td>'+
				'<td><center><strong>SAP Number</strong></center></td>'+
				'<td><center><strong>Quotation Number</strong></center></td>'+
				'<td><center><strong>Vendor Name </strong></center></td>'+
				'<td><center><strong>Raised By</strong></center></td>'+
				'<td><center><strong>Raised Date</strong></center></td>'+
				'<td style="width: 70px;"><strong><center><a href="#">Preview</a></center></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.no_po+'</center></td>'+
									'<td><center>'+params.no_sap+'</center></td>'+
									'<td><center>'+params.no_quot+'</center></td>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><center>'+params.dt_po+'</center></td>'+
									'<td><strong><center><a href="#" onclick="ControlApprovalPurchaseOrder4(\'Modify\',\'createApprovePurchaseOrder4\',\'displayApprovePurchaseOrder4\',\''+params.id_po+'\')"> Preview </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.DisplayPurchaseOrderForApproval4').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="7"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.DisplayPurchaseOrderForApproval4').html(list);
			}
		
		
	},'json');

			}});

}