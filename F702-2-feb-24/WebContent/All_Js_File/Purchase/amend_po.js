function DisplaydirectamendOrder(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var typ_po =$('select[name="typ_po"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Approval_Purchase_Order',{action : 'DisplayAmend',dt_frm:dt_frm,dt_to:dt_to,typ_po:typ_po,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="10" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 325px;">Purchase Order For Amendment </p></td>'+
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
				tender_num ='';
				if(params.tender_num !=undefined)
					tender_num=params.tender_num;
				
				list = list + '<tr class="success">'+
				'<td><center>'+params.no_po+'</center></td>'+
				'<td><center>'+params.dt_po+'</center></td>'+
				'<td><center>'+params.nm_ven+'</center></td>'+
				'<td><center>'+params.no_quot+'</center></td>'+
				'<td><strong><center><a href="#" onclick="ModifyApprovePO(\'Modify\',\'createAmendPurchaseOrder2\',\'DisplayAmendPurchaseOrder\',\''+params.id_po+'\',\''+params.id_dept+'\',\''+params.id_cc+'\',\''+params.id_section+'\',\''+params.do_direct+'\')"> Preview </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.'+DisplayTableClass).html(list);
			}
		
		else
			{
				list +='<tr><td colspan="7"><strong><center>Data Not Found.</center></strong></td></tr>';
				$('.'+DisplayTableClass).html(list);
			}
		
		
	},'json');

			}});

}

function DisplayMyOrder(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var typ_po =$('select[name="typ_po"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Approval_Purchase_Order',{action : 'DisplayMyPO',dt_frm:dt_frm,dt_to:dt_to,typ_po:typ_po,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="10" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 500px;">My Purchase Order </p></td>'+
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
				tender_num ='';
				if(params.tender_num !=undefined)
					tender_num=params.tender_num;
				
				list = list + '<tr class="success">'+
				'<td><center>'+params.no_po+'</center></td>'+
				'<td><center>'+params.dt_po+'</center></td>'+
				'<td><center>'+params.nm_ven+'</center></td>'+
				'<td><center>'+params.no_quot+'</center></td>'+
				'<td><strong><center><a href="#" onclick="ModifyMyPO(\'Modify\',\'createAmendPurchaseOrder2\',\'DisplayAmendPurchaseOrder\',\''+params.id_po+'\',\''+params.id_dept+'\',\''+params.id_cc+'\',\''+params.id_section+'\',\''+params.do_direct+'\')"> Preview </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.'+DisplayTableClass).html(list);
			}
		
		else
			{
				list +='<tr><td colspan="7"><strong><center>Data Not Found.</center></strong></td></tr>';
				$('.'+DisplayTableClass).html(list);
			}
		
		
	},'json');

			}});

}

function ModifyApprovePO(action,DisplayDiv,HideDiv,id_po,id_dept,id_cc,id_section,do_direct){
 
		PurchaseOrderForApprove(action,DisplayDiv,HideDiv,id_po);	
	 
}
function ModifyMyPO(action,DisplayDiv,HideDiv,id_po,id_dept,id_cc,id_section,do_direct){
 
		PurchaseOrderMy(action,DisplayDiv,HideDiv,id_po);	
	 
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
			'<td ><strong><center>Remaining PO Quantity<a href=#></a></center></strong></td>'+
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
				 
				var currentDate = new Date();
				$( ".datepicker111" ).datepicker({
					yearRange: '1985:2025',
				      changeMonth: true,
				      changeYear: true,
				      dateFormat: "dd/mm/yy",
				      autoSize: true,
				      altFormat: "dd/mm/yy",
				      
				    });
				 
				params = r.data[i];
				
				remarks='',dt_scheduled_dlvry2='';
				if(params.item_remarks !=undefined)
					remarks = params.item_remarks;
				if(params.dt_scheduled_dlvry2 !=undefined)
					dt_scheduled_dlvry2 = params.dt_scheduled_dlvry2;
				
				list += '<tr class="success">'+
				'<td><input style="width: 120px !important;" type="text" readonly value="'+params.nm_model+'" id="dynItemData'+i+'" name="id_prod'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod(event);"></td>'+
				'<td><center><select style="width: 120px !important;" disabled="disabled" id="assetDivForInvoice'+i+'" name="id_grp'+i+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')">'+
				'<option value="">Select</option></select>'+
				'</center></td>'+
				'<td><center><select style="width: 120px !important;" disabled="disabled" id="subGroupDataForInvoice'+i+'" name="id_sgrp'+i+'" patelUnPrc="'+i+'"  class="common-validation dropsgrp" data-valid=""  style="width:80" >'+
				'<option value="">Select</option></select>'+
				'</center></td>'+
			 	'<td><center><input type="text" style="width: 150px;"    name="item_remarks'+i+'" remarks="'+i+'" value="'+remarks+'"  style="width: 100px;" maxlength="500"></center></td>'+
				'<td><center><input type="text" name="dt_scheduled_dlvry'+i+'"    value="'+dt_scheduled_dlvry2+'" patelUnPrc="'+i+'"  class="common-validation modifyPODatepicker2"  style="width: 120px;"  ></center></td>'+
			
			    '<input type="hidden" name="count" value="'+i+'">'+
			    '<input type="hidden" name="id_po_asst" value="'+params.id_po_asst+'">'+
			  
			    '<td><center><input type="text"  name="qty'+i+'"  value="'+params.qty+'" style="width:60px;;text-align: right;" class="common-validation"   patelUnPrc="'+i+'" onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+
			    '<td><center><input type="text"  name="rem_qty'+i+'"  value="'+params.rem_qty+'" style="width:60px;;text-align: right;" class="common-validation"   patelUnPrc="'+i+'" class="patelUnPrc data-valid="num"></center></td>'+
			    '<td><center><input type="text" name="un_prc'+i+'"    value="'+params.un_prc+'" patelUnPrc="'+i+'"  style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" ></center></td>'+
				'<td><input type="text" name="others'+i+'"   value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:60px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax1'+i+'"  value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax2'+i+'"   value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_va2\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center><input type="text"  name="tax_val1'+i+'"    value="'+params.tax_val1+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
				'<td><center><input type="text"  name="tax_val2'+i+'"    value="'+params.tax_val2+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
				'<td><center><input type="text"  name="buyback'+i+'"    value="'+params.buyback+'" patelUnPrc="'+i+'"  onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="gr_tot'+i+'"    value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
				
				
				
				
				'</tr>';
				totPrice += parseFloat(params.gr_tot);
				}
		   
			list += '<input type="hidden" name="id_po" value="'+id_po+'">'+
			'<input type="hidden" name="no_ind" value="'+r.data[0].no_ind+'">'+
			'<input type="hidden" name="no_quot" value="'+r.data[0].no_quot+'">'+
			'<tr class="success">'+
			'<td colspan="14"><input type="text" name="oter_text"   value="'+params.oter_text+'" style="float:right;"  class="common-validation" ></td>'+
			'<td><center><input type="text" name="frt_text"   value="'+params.frt_text+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'frt_text\')" class="common-validation" ></center></td>'+
		    '</tr>'+
		
		    '<tr class="success">'+
		    '<td colspan="14"><b style="float:right;">Discount</b></td>'+
		    '<td><center><input type="text" name="discount"   value="'+params.discount+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'discount\')" class="common-validation" ></center></td>'+
	        '</tr>'+
	        '<tr class="success">'+
		    '<td colspan="14"><b style="float:right;">Grand Total</b></td>'+
		    '<td><center><input type="text" name="tot"   value="'+totPrice+'" style="width: 60px;text-align: right" class="common-validation" readonly></center></td>'+
	        
		
		
		 '<tr>'+
		
		 '<tr>'+
		 '	</td></tr><tr><td colspan="14">'+
         '	<button type="button" style="margin-left:400px;" class="btn btn-primary poApprv1" onclick="ControlApprovalPurchaseOrder(\'Amend\',\'DisplayAmendPurchaseOrder\',\'createAmendPurchaseOrder2\','+id_po+')">Amend PO </button>&nbsp;&nbsp;'+
         '	<button type="button"  class="btn btn-primary poApprv1" onclick="ControlApprovalPurchaseOrder(\'Cancel\',\'displayApprovePurchaseOrder\',\'createApprovePurchaseOrder\',\'\')">Cancel </button>'+
		 '	</td>'+
		'</tr>';
			
				
			$('.accessoryDetails1').html(list);
			
			$('#'+DisplayDiv).show();
			$('#'+HideDiv).hide();
			$('input[name="po_total"]').val(totPrice);
			$('input[name="dt_amend"]').val(r.data[0].dtamend);
			
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
						 }});
					}});	
			}});
		
	},'json');


}

function PurchaseOrderMy(action,DisplayDiv,HideDiv,id_po)
{
	$.post('Modify_Purchase_Order',{action : 'EditMyPO',id_po:id_po},function (r){
		if(r.data.length > 0)
			{
				
				
				var upload_po='';
						if(r.data[0].upload_po != undefined)
							upload_po = r.data[0].upload_po;
				
			var totPrice=0;
			var list ='<tr class="tableHeader info">'+
			'<td ><strong><center>Model<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Group<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Sub Group<a href=#></a></center></strong></td>'+
			'<td><strong><center>Remarks</center></strong></td>'+
		    '<td><strong><center>Delivery Date <font color="red">*</font></center></strong></td>'+
			'<td ><strong><center>Quantity<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Remaining PO Quantity<a href=#></a></center></strong></td>'+
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
				 
				var currentDate = new Date();
				$( ".datepicker111" ).datepicker({
					yearRange: '1985:2025',
				      changeMonth: true,
				      changeYear: true,
				      dateFormat: "dd/mm/yy",
				      autoSize: true,
				      altFormat: "dd/mm/yy",
				      
				    });
				 
				params = r.data[i];
				
				remarks='',dt_scheduled_dlvry2='';
				if(params.item_remarks !=undefined)
					remarks = params.item_remarks;
				if(params.dt_scheduled_dlvry2 !=undefined)
					dt_scheduled_dlvry2 = params.dt_scheduled_dlvry2;
				
				list += '<tr class="success">'+
				'<td><input style="width: 120px !important;" type="text" readonly value="'+params.nm_model+'" id="dynItemData'+i+'" name="id_prod'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod(event);"></td>'+
				'<td><center><select style="width: 120px !important;" disabled="disabled" id="assetDivForInvoice'+i+'" name="id_grp'+i+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')">'+
				'<option value="">Select</option></select>'+
				'</center></td>'+
				'<td><center><select style="width: 120px !important;" disabled="disabled" id="subGroupDataForInvoice'+i+'" name="id_sgrp'+i+'" patelUnPrc="'+i+'"  class="common-validation dropsgrp" data-valid=""  style="width:80" >'+
				'<option value="">Select</option></select>'+
				'</center></td>'+
			 	'<td><center><input type="text" style="width: 150px;"    name="item_remarks'+i+'" remarks="'+i+'" value="'+remarks+'"  style="width: 100px;" maxlength="500" readonly></center></td>'+
				'<td><center><input type="text" name="dt_scheduled_dlvry'+i+'"    value="'+dt_scheduled_dlvry2+'" patelUnPrc="'+i+'"  class="common-validation modifyPODatepicker2"  style="width: 120px;" readonly ></center></td>'+
			
			    '<input type="hidden" name="count" value="'+i+'">'+
			    '<input type="hidden" name="id_po_asst" value="'+params.id_po_asst+'">'+
			  
			    '<td><center><input type="text"  name="qty'+i+'"  value="'+params.qty+'" style="width:60px;;text-align: right;" class="common-validation"   patelUnPrc="'+i+'" readonly class="patelUnPrc data-valid="num"></center></td>'+
			    '<td><center><input type="text"  name="rem_qty'+i+'"  value="'+params.rem_qty+'" style="width:60px;;text-align: right;" class="common-validation" readonly  patelUnPrc="'+i+'" class="patelUnPrc data-valid="num"></center></td>'+
			    '<td><center><input type="text" name="un_prc'+i+'"    value="'+params.un_prc+'" patelUnPrc="'+i+'"  style="width: 60px;text-align: right;" readonly class="patelUnPrc common-validation" data-valid="num" ></center></td>'+
				'<td><input type="text" name="others'+i+'"   value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:60px""  readonly class="common-validation" ></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax1'+i+'"  value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" readonly>'+
				'</select>'+
				'</center></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax2'+i+'"   value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" readonly>'+
				'</select>'+
				'</center></td>'+
				'<td><center><input type="text"  name="tax_val1'+i+'"    value="'+params.tax_val1+'" patelUnPrc="'+i+'" readonly style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
				'<td><center><input type="text"  name="tax_val2'+i+'"    value="'+params.tax_val2+'" patelUnPrc="'+i+'" readonly style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
				'<td><center><input type="text"  name="buyback'+i+'"    value="'+params.buyback+'" patelUnPrc="'+i+'"  readonly style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="gr_tot'+i+'"    value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
				
				
				
				
				'</tr>';
				totPrice += parseFloat(params.gr_tot);
				}
		   
			list += '<input type="hidden" name="id_po" value="'+id_po+'">'+
			'<input type="hidden" name="no_ind" value="'+r.data[0].no_ind+'">'+
			'<input type="hidden" name="no_quot" value="'+r.data[0].no_quot+'">'+
			'<tr class="success">'+
			'<td colspan="14"><input type="text" name="oter_text"   value="'+params.oter_text+'" style="float:right;"  class="common-validation" ></td>'+
			'<td><center><input type="text" name="frt_text"   value="'+params.frt_text+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'frt_text\')" class="common-validation" ></center></td>'+
		    '</tr>'+
		
		    '<tr class="success">'+
		    '<td colspan="14"><b style="float:right;">Discount</b></td>'+
		    '<td><center><input type="text" name="discount"   value="'+params.discount+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'discount\')" class="common-validation" ></center></td>'+
	        '</tr>'+
	        '<tr class="success">'+
		    '<td colspan="14"><b style="float:right;">Grand Total</b></td>'+
		    '<td><center><input type="text" name="tot"   value="'+totPrice+'" style="width: 60px;text-align: right" class="common-validation" readonly></center></td>'+
	        
		
		
		 '<tr>'+
	'<tr>'+
		'<td colspan="14"><center><Strong>Download PO</Strong>&nbsp;&nbsp;&nbsp;&nbsp;<a href="downloadInvoiceDetails.jsp?fileName1='+upload_po+'">'+upload_po+'</a></center></td>'+
									
									'</tr>'
		 /*'<tr>'+
		 '	</td></tr><tr><td colspan="14">'+
         '	<button type="button" style="margin-left:400px;" class="btn btn-primary poApprv1" onclick="ControlApprovalPurchaseOrder(\'Amend\',\'DisplayAmendPurchaseOrder\',\'createAmendPurchaseOrder2\','+id_po+')">Amend PO </button>&nbsp;&nbsp;'+
         '	<button type="button"  class="btn btn-primary poApprv1" onclick="ControlApprovalPurchaseOrder(\'Cancel\',\'displayApprovePurchaseOrder\',\'createApprovePurchaseOrder\',\'\')">Cancel </button>'+
		 '	</td>'+
		'</tr>';*/
			
				
			$('.accessoryDetails1').html(list);
			
			$('#'+DisplayDiv).show();
			$('#'+HideDiv).hide();
			$('input[name="po_total"]').val(totPrice);
			$('input[name="dt_amend"]').val(r.data[0].dtamend);
			
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
						 }});
					}});	
			}});
		
	},'json');


}

function ControlApprovalPurchaseOrder(action,DisplayDiv,HideDiv,id_po)
{
	
	if(action == 'Cancel')
	{
		$( ".amendPurchaseOrder" ).trigger( "click" );
	
	}
	
	if(action =='Amend')
	{var t=false;
	t=ValidationForm('submitAmendPurchaseOrder2');
	if(t)
	{
		
			 bootbox.confirm("Are you sure ?", function(result) {
					if(result)
					{
						
						var x = $('#submitAmendPurchaseOrder2').serialize();
						$.post('Approval_Purchase_Order',x,function (r){
							if(r.data == 1)
								{
								bootbox.alert("Update successfully.");
								$( ".amendPurchaseOrder" ).trigger( "click" );
								}
							else
							{
								bootbox.alert("Something went wrong Please try again.");
							}
							$('.upd').removeAttr('disabled');
						},'json');
						
			
					}});}
		
		
	}

}