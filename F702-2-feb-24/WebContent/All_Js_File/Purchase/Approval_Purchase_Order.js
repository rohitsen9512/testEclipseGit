function ControlApprovalPurchaseOrder(action,DisplayDiv,HideDiv,id_po)
{
	
	if(action == 'Cancel')
	{
		$('#'+DisplayDiv).show();
		$('#'+HideDiv).hide();
	
	}
	if(action == 'Modify')
	{
		PurchaseOrderForApprove(action,DisplayDiv,HideDiv,id_po);
	}
	if(action =='Accepted' || action =='Rejected')
	{
		var dt_po = $('input[name="dt_po"]').val();
		var dt_approv = $('input[name="dt_approv"]').val();
		var dt_approv1 = $('input[name="dt_approv"]').val();
		var dt_po1 = $('input[name="dt_po"]').val();
		$('input[name="dt_approv"]').removeClass('error');
		
		var temp_strt = dt_approv.split("/");
		 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
			
		var temp_end = dt_po.split("/");
		var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
			
		dt_approv = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
		dt_po = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
		
		if(dt_approv != '')
		{
			console.log("DTAPPROV:",dt_approv,",DTPO:",dt_po);
		if(dt_approv >= dt_po)
		{
			 bootbox.confirm("Are you sure ?", function(result) {
					if(result)
					{
			$('.poApprv1').attr('disabled','disabled');
			$.post('Approval_Purchase_Order',{action:'Update',status_approv:action,id_po:id_po,dt_approv:dt_approv1},function (r){
				if(r.data == 1)
					{
						bootbox.alert(action +" Successfully.");
						$( ".approvePurchaseOrder" ).trigger( "click" );
					}
				else
				{
					bootbox.alert("Somthing went wrong Please try again.");
				}
				$('.poApprv1').removeAttr('disabled');
			},'json');
					}});}
		else
			{
			alert('Approval date should be greater or equal to P.O date : '+dt_po1);
			$('input[name="dt_approv"]').focus();
			$('input[name="dt_approv"]').val('');
			$('input[name="dt_approv"]').addClass('error');
		  	exit(0);
			
			}
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
function ModifyApprovePO(action,DisplayDiv,HideDiv,id_po,id_dept,id_cc,id_section,do_direct){
	
		PurchaseOrderForApprove(action,DisplayDiv,HideDiv,id_po);	
	
}

function PurchaseOrderForApprove(action,DisplayDiv,HideDiv,id_po)
{
	$.post('Modify_Purchase_Order',{action : 'Edit',id_po:id_po},function (r){
		if(r.data.length > 0)
			{
			var totPrice=0;
			var list ='<tr class="tableHeader info">'+
			'<td ><strong><center>Model<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Group<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Sub Group<a href=#></a></center></strong></td>'+
			'<td><strong><center>Remarks</center></strong></td>'+
		    '<td><strong><center>Delivery Date <font color="red">*</font></center></strong></td>'+
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
				if(r.data[0].remarks!=undefined || r.data[0].mode_pur!='')
				{
					$('#remarks').val(r.data[0].remarks);
					}
				//$('input[name="dt_po"]').val(r.data[i].dtpo);
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
				
				var remarks='',dt_scheduled_dlvry2='';
				if(params.item_remarks !=undefined)
					remarks = params.item_remarks;
				if(params.dt_scheduled_dlvry2 !=undefined)
					dt_scheduled_dlvry2 = params.dt_scheduled_dlvry2;
				console.log(params.item_remarks);
				console.log(remarks);
				
				list += '<tr class="success">'+
				 '<td><input style="width: 120px !important;" type="text" readonly value="'+params.nm_model+'" id="dynItemData'+i+'" name="id_prod'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod(event);"></td>'+
					'<td><center><select style="width: 120px !important;" disabled="disabled" id="assetDivForInvoice'+i+'" name="id_grp'+i+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')">'+
							'<option value="">Select</option></select>'+
					'</center></td>'+
					'<td><center><select style="width: 120px !important;" disabled="disabled" id="subGroupDataForInvoice'+i+'" name="id_sgrp'+i+'" patelUnPrc="'+i+'"  class="common-validation dropsgrp" data-valid=""  style="width:80" >'+
							'<option value="">Select</option></select>'+
							'</center></td>'+
				 	'<td><center><input type="text" style="width: 150px;"  readonly name="item_remarks'+i+'" remarks="'+i+'" value="'+remarks+'"  style="width: 100px;" maxlength="500"></center></td>'+
					'<td><center><input type="text" name="dt_scheduled_dlvry'+i+'" readonly  value="'+dt_scheduled_dlvry2+'" patelUnPrc="'+i+'"  class="common-validation modifyPODatepicker2"  style="width: 120px;"  ></center></td>'+
				
				'<input type="hidden" name="count" value="'+i+'">'+
				'<input type="hidden" name="id_po_asst" value="'+params.id_po_asst+'">'+
				
				'<td><center><input type="text"  name="qty'+i+'" readonly value="'+params.qty+'" style="width:60px;;text-align: right;" class="common-validation" patelUnPrc="'+i+'" onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+
				'<td><center><input type="text" name="un_prc'+i+'" readonly  value="'+params.un_prc+'" patelUnPrc="'+i+'"  style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" ></center></td>'+
				'<td><input type="text" name="others'+i+'" readonly value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:60px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax1'+i+'" disabled="disabled" value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax2'+i+'" disabled="disabled" value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_va2\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center><input type="text"  name="tax_val1'+i+'"  readonly value="'+params.tax_val1+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
				'<td><center><input type="text"  name="tax_val2'+i+'" readonly  value="'+params.tax_val2+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
				'<td><center><input type="text"  name="buyback'+i+'"  readonly value="'+params.buyback+'" patelUnPrc="'+i+'"  onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="gr_tot'+i+'" readonly  value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
				
				
				
				'</tr>';
				totPrice += parseFloat(params.gr_tot);
				}
			var upload_po='';
			if(r.data[0].upload_po != undefined)
				{upload_po=r.data[0].upload_po;}
			var tc_name='';
			if(r.tc_name != undefined)
				{tc_name=r.tc_name;}
			
			
			list += '<input type="hidden" name="id_po" value="'+id_po+'">'+
			'<input type="hidden" name="no_ind" value="'+r.data[0].no_ind+'">'+
			'<input type="hidden" name="no_quot" value="'+r.data[0].no_quot+'">'+
			/*'<input type="hidden" name="do_direct" value="'+r.data[0].do_direct+'">'+*/
			'<input type="hidden" name="actionType'+i+'" value="Approve">'+
			'<tr class="success">'+
			'<td colspan="13"><input type="text" name="oter_text" readonly value="'+params.oter_text+'" style="float:right;"  class="common-validation" ></td>'+
			 '<td><center><input type="text" name="frt_text" readonly value="'+params.frt_text+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'frt_text\')" class="common-validation" ></center></td>'+
		        '</tr>'+
		
		        '<tr class="success">'+
		      '<td colspan="13"><b style="float:right;">Discount</b></td>'+
		        '<td><center><input type="text" name="discount" readonly value="'+params.discount+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'discount\')" class="common-validation" ></center></td>'+
	             '</tr>'+
	        	'<tr class="success">'+
		     	'<td colspan="13"><b style="float:right;">Grand Total</b></td>'+
		      	'<td><center><input type="text" name="tot" readonly value="'+params.tot+'" style="width: 60px;text-align: right" class="common-validation" readonly></center></td>'+
	        
		
		'<tr>'+
		'<td colspan="2"><b style="float:right;">Click for download Term & Conditions :</b></td>'+
			'<td colspan="4">'+
			'<a href="download.jsp?fileName1='+r.uploadedFile+'" target="_blank"> '+tc_name+'</a><br>'+
			'<a href="download.jsp?fileName1='+r.data[0].upload_po+'" target="_blank"> '+upload_po+'</a>'+
			
				'</td>'+
	    '</tr>'+

		 '<tr>'+
         ' </td></tr><tr><td colspan="14">'+
         ' <button type="button" style="margin-left: 400px;" class="btn btn-primary poApprv1" onclick="ControlApprovalPurchaseOrder(\'Accepted\',\'displayApprovePurchaseOrder\',\'createApprovePurchaseOrder\','+id_po+')">Approve </button>&nbsp;&nbsp;'+
         '	<button type="button" class="btn btn-primary poApprv1" onclick="ControlApprovalPurchaseOrder(\'Rejected\',\'displayApprovePurchaseOrder\',\'createApprovePurchaseOrder\','+id_po+')">Reject </button>&nbsp;&nbsp;'+
         '	<button type="button" class="btn btn-primary poApprv1" onclick="ControlApprovalPurchaseOrder(\'Cancel\',\'displayApprovePurchaseOrder\',\'createApprovePurchaseOrder\',\'\')">Cancel </button>'+
		 '	</td>'+
		'</tr>';
			
				
			$('.PurchaseOrderDetailsForApproval').html(list);
			
			$('#'+DisplayDiv).show();
			$('#'+HideDiv).hide();
			$('input[name="po_total"]').val(totPrice);
				}
		
		DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
			if(status)
			{
				DisplayDropDownDataForGroup('M_Asset_Div','groupDrop','CapG',function (status){
					if(status)
					{
					SubDropDownDataDisplay('0','dropsgrp','M_Subasset_Div',function (status){
					
						for(var i=0;i<r.data.length;i++)
						{
							params=r.data[i];
							for (var key in r.data[i])
					        {
								
									if(key == 'id_tax1')
										$('select[name=id_tax1'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									if(key=='id_tax2')
										$('select[name=id_tax2'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									if(key=='id_grp')
									{
										$('select[name=id_grp'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}
									if(key=='id_sgrp')
									{
										$('select[name=id_sgrp'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}		
							}
						}
                          });
					
				
					}});	
				
				
				
				
			}});
		
	},'json');


}
function PurchaseOrderForApprovejp(action,DisplayDiv,HideDiv,id_po)
{
	$.post('Modify_Purchase_Order',{action : 'Edit',id_po:id_po},function (r){
		if(r.data.length > 0)
			{
			var totPrice=0;
			var list ='<tr class="tableHeader info">'+
			'<td><strong><center>Item Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
			'<td><strong><center>Part Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
			/*'<td ><strong><center>Description<a href=#></a></center></strong></td>'+*/
			'<td ><strong><center>UOM<a href=#></a></center></strong></td>'+
			//'<td><strong><center>Remarks</center></strong></td>'+
			//'<td><strong><center>Delivery Date <font color="red">*</font></center></strong></td>'+
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
				if(r.data[0].remarks!=undefined || r.data[0].mode_pur!='')
				{
					$('#remarks').val(r.data[0].remarks);
					}
				//$('input[name="dt_po"]').val(r.data[i].dtpo);
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
				
				remarks='',dt_scheduled_dlvry2='';
				if(params.item_remarks !=undefined)
					remarks = params.item_remarks;
				if(params.dt_scheduled_dlvry2 !=undefined)
					dt_scheduled_dlvry2 = params.dt_scheduled_dlvry2;
				
				list += '<tr class="success">'+
				'<td><center>'+params.nm_prod+'</center></td>'+
				'<td><center>'+params.cd_prod+'</center></td>'+
				
				'<td><center>'+params.nm_uom+'</center></td>'+
			//	'<td><center><input type="text" style="width: 150px;" name="item_remarks'+i+'" remarks="'+i+'" value="'+remarks+'" readonly  style="width: 100px;" maxlength="500"></center></td>'+
				//'<td><center><input type="text" name="dt_scheduled_dlvry'+i+'"  value="'+dt_scheduled_dlvry2+'" patelUnPrc="'+i+'" readonly class="common-validation modifyPODatepicker2"  style="width: 120px;"  ></center></td>'+
				
				'<input type="hidden" name="count" value="'+i+'">'+
				'<input type="hidden" name="id_po_asst" value="'+params.id_po_asst+'">'+
				
				'<td><center><input type="text"  name="qty'+i+'"  value="'+params.qty+'" style="width:60px;;text-align: right;" class="common-validation" readonly patelUnPrc="'+i+'" onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+
				'<td><center><input type="text" name="un_prc'+i+'"  value="'+params.un_prc+'" patelUnPrc="'+i+'"  style="width: 60px;text-align: right;" readonly onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" ></center></td>'+
				'<td><input type="text" name="others'+i+'"  value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:60px"" readonly  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax1'+i+'"  value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" readonly onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax2'+i+'"  value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" readonly onChange="TaxCalculation(event,\'id_tax2\',\'tax_va2\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center><input type="text"  name="tax_val1'+i+'"  value="'+params.tax_val1+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax1\')" readonly style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
				'<td><center><input type="text"  name="tax_val2'+i+'"  value="'+params.tax_val2+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax2\')" readonly style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
				'<td><center><input type="text"  name="discount'+i+'"  value="'+params.discount+'" patelUnPrc="'+i+'"  onChange="calculateTot(event,\'discount\')" readonly style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="gr_tot'+i+'"  value="'+params.gr_tot1+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
				
				
				
				'</tr>';
				totPrice += parseFloat(params.gr_tot);
				}
			var upload_po='';
			if(r.data[0].upload_po != undefined)
				{upload_po=r.data[0].upload_po;}
			var tc_name='';
			if(r.tc_name != undefined)
				{tc_name=r.tc_name;}
			
			
			list += '<input type="hidden" name="id_po" value="'+id_po+'">'+
			'<input type="hidden" name="no_ind" value="'+r.data[0].no_ind+'">'+
			'<input type="hidden" name="no_quot" value="'+r.data[0].no_quot+'">'+
			/*'<input type="hidden" name="do_direct" value="'+r.data[0].do_direct+'">'+*/
			'<input type="hidden" name="actionType'+i+'" value="Approve">'+
			
		
		'<tr>'+
		'<td colspan="2"><b style="float:right;">Click for download Term & Conditions :</b></td>'+
			'<td colspan="4">'+
			'<a href="download.jsp?fileName1='+r.uploadedFile+'" target="_blank"> '+tc_name+'</a><br>'+
			'<a href="download.jsp?fileName1='+r.data[0].upload_po+'" target="_blank"> '+upload_po+'</a>'+
			/*'<textarea style="margin-left: 0px;margin-right: 0px;width: 500px; height: 73px;"  name="po_t_c" readonly>'+
			r.data[0].po_t_c+
					'</textarea>'+*/
				'</td>'+
	    '</tr>'+

		 '<tr>'+
		 '<td colspan="14"> <input type="text" name="po_total" value="0.0" id="" class="common-validatio resetAcc1 FieldResize" readonly style="float:right;";>'+
	        
         '	</td></tr><tr><td colspan="14">'+
     '	<button type="button" style="margin-left: 400px;" class="btn btn-primary poApprv1" onclick="ControlApprovalPurchaseOrder(\'Accepted\',\'displayApprovePurchaseOrder\',\'createApprovePurchaseOrder\','+id_po+')">Approve </button>&nbsp;&nbsp;'+
         '	<button type="button" class="btn btn-primary poApprv1" onclick="ControlApprovalPurchaseOrder(\'Rejected\',\'displayApprovePurchaseOrder\',\'createApprovePurchaseOrder\','+id_po+')">Reject </button>&nbsp;&nbsp;'+
         '	<button type="button" class="btn btn-primary poApprv1" onclick="ControlApprovalPurchaseOrder(\'Cancel\',\'displayApprovePurchaseOrder\',\'createApprovePurchaseOrder\',\'\')">Cancel </button>'+
		 '	</td>'+
		'</tr>';
			
				
			$('.PurchaseOrderDetailsForApproval').html(list);
			
			$('#'+DisplayDiv).show();
			$('#'+HideDiv).hide();
			$('input[name="po_total"]').val(totPrice);
			}
		
		DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
			if(status)
			{
				DropDownDataDisplay("M_UOM","uomforSales",function (status){
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
					}});	
				
				
				
				
			}});
		
	},'json');


}



function DisplayPurchaseOrderForApproval()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var typ_po =$('select[name="typ_po"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Approval_Purchase_Order',{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,typ_po:typ_po,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="10" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 325px;">Purchase Order For Approval </p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
			'<td><center><strong>PO Number</strong></center></td>'+
			'<td><center><strong>PO Date</strong></center></td>'+
			'<td><center><strong>Vendor Name </strong></center></td>'+
			
			'<td><center><strong>Quotation No</strong></center></td>'+
			'<td style="width: 70px;"><strong><center><a href="#">Preview</a></center></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				list = list + '<tr class="success">'+
				'<td><center>'+params.no_po+'</center></td>'+
				'<td><center>'+params.dt_po+'</center></td>'+
				'<td><center>'+params.nm_ven+'</center></td>'+
				
				'<td><center>'+params.no_quot+'</center></td>'+
				'<td><strong><center><a href="#" onclick="ModifyApprovePO(\'Modify\',\'createApprovePurchaseOrder\',\'displayApprovePurchaseOrder\',\''+params.id_po+'\',\''+params.id_dept+'\',\''+params.id_cc+'\',\''+params.id_section+'\',\''+params.do_direct+'\')"> Preview </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.DisplayPurchaseOrderForApproval').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="7"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.DisplayPurchaseOrderForApproval').html(list);
			}
		
		
	},'json');

			}});

}

function DisplayJWPurchaseOrderForApproval()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var typ_po =$('select[name="typ_po"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Approval_Purchase_Order',{action : 'DisplayJW',dt_frm:dt_frm,dt_to:dt_to,typ_po:typ_po,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="10" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 325px;">Purchase Order For Approval </p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
			'<td><center><strong>PO Number</strong></center></td>'+
			'<td><center><strong>PO Date</strong></center></td>'+
			'<td><center><strong>Vendor Name </strong></center></td>'+
			
			'<td><center><strong>Quotation No</strong></center></td>'+
			//'<td><center><strong>PI No</strong></center></td>'+
			'<td style="width: 70px;"><strong><center><a href="#">Preview</a></center></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				tender_num ='';
				if(params.tender_num !=undefined)
					tender_num=params.tender_num;
				
				list = list + '<tr class="success">'+
				'<td><center>'+params.no_po+'</center></td>'+
				'<td><center>'+params.dt_po+'</center></td>'+
				'<td><center>'+params.nm_ven+'</center></td>'+
				
				'<td><center>'+params.no_quot+'</center></td>'+
				//'<td><center>'+params.no_ind+'</center></td>'+
				'<td><strong><center><a href="#" onclick="ModifyApprovePO(\'Modify\',\'createApprovePurchaseOrder\',\'displayApprovePurchaseOrder\',\''+params.id_po+'\',\''+params.id_dept+'\',\''+params.id_cc+'\',\''+params.id_section+'\',\''+params.do_direct+'\')"> Preview </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.DisplayPurchaseOrderForApproval').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="7"><strong><center>Data Not Found.</center></strong></td></tr>';
				$('.DisplayPurchaseOrderForApproval').html(list);
			}
		
		
	},'json');

			}});

}