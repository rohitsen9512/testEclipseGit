
function ControlEditPurchaseOrder(action,DisplayDiv,HideDiv,id_po)
{
	
	if(action == 'Cancel')
	{
		$('#'+DisplayDiv).show();
		$('#'+HideDiv).hide();
	
	}
	if(action == 'Modify')
	{
		
		PurchaseOrderForEdit(action,DisplayDiv,HideDiv,id_po);
		
	}
	if(action == 'Update')
	{
		var t=false;
		t=ValidationForm('submitModifyPurchaseOrder');
		if(t)
		{
			$('.upd').attr('disabled','disabled');
			
			$('.grpdisab').removeAttr('disabled');
			var x = $('#submitModifyPurchaseOrder').serialize();
			$.post('Modify_Purchase_Order',x,function (r){
				if(r.data == 1)
					{
								bootbox.alert(action +"d Successfully.");
							$( ".modifyPurchaseOrder" ).trigger( "click" );
						
						
					}
				else
				{
					bootbox.alert("Something went wrong Please try again.");
				}
				$('.upd').removeAttr('disabled');
			},'json');
		
		}
	}

}


function ModifyPO(action,DisplayDiv,HideDiv,id_po,id_dept,id_cc,id_section){
	
	PurchaseOrderForEdit(action,DisplayDiv,HideDiv,id_po);

}


function TaxCalculation111(event)
{

	var gr_tot =0.00;
	var domElement =$(event.target || event.srcElement);
	var un_prc = $('input[name="un_prc'+domElement.attr('patelTax')+'"]').val();
	var qty = $('input[name="qty'+domElement.attr('patelTax')+'"]').val();
	var id_tax = $('select[name="taxId'+domElement.attr('patelTax')+'"]').val();
var t=true;
console.log(id_tax);
	if(id_tax != undefined && id_tax != '')
		{
			if(id_tax == '0')
				{
				t=false;
				$('input[name="taxId'+domElement.attr('patelTax')+'"]').val('0.00');
				$('input[name="taxVal'+domElement.attr('patelTax')+'"]').val('0.00');
				$('input[name="taxPercent'+domElement.attr('patelTax')+'"]').val('0.00');
				$('input[name="tot_prc'+domElement.attr('patelTax')+'"]').val(parseFloat(un_prc)*parseFloat(qty));
				$('.commonTotal').each(function (){
						if($(this).val() != undefined)
							gr_tot += +$(this).val();
						
					});
				 var bottomtotal = parseFloat($('input[name="frt_rate"]').val())+parseFloat($('input[name="freight_amount"]').val()) +parseFloat($('input[name="frt_text"]').val()) +parseFloat($('input[name="add_chrg"]').val())-parseFloat($('input[name="buy_back"]').val())-parseFloat($('input[name="discount"]').val());
					
					// var others= $('input[name="others"]').val();
					 gr_tot = gr_tot + parseFloat(bottomtotal);
				 	 console.log(gr_tot);
				
				 $('input[name="tot"]').val(gr_tot.toFixed(2));
				}
		}
		
	if(t)
		{
	$.post('M_Tax',{action : 'DropDownResult',id:id_tax},function (r){
		
		if(r.data.length > 0 )
			{
			 var percent = r.data[0].per_tax;
			 
			 var taxVal = (parseFloat(qty)*parseFloat(un_prc)*parseFloat((percent)))/100;
			 var tot= +parseFloat(qty)*parseFloat(un_prc) + taxVal;
			 
					 $('input[name="taxVal'+domElement.attr('patelTax')+'"]').val(taxVal.toFixed(2));
					 $('input[name="tot_prc'+domElement.attr('patelTax')+'"]').val(tot.toFixed(2));
					 $('input[name="taxPercent'+domElement.attr('patelTax')+'"]').val(percent);
					 $('.commonTotal').each(function (){
							if($(this).val() != undefined)
								gr_tot += +$(this).val();
							
						});
					 var bottomtotal = parseFloat($('input[name="frt_rate"]').val())+parseFloat($('input[name="freight_amount"]').val()) +parseFloat($('input[name="frt_text"]').val()) +parseFloat($('input[name="add_chrg"]').val())-parseFloat($('input[name="buy_back"]').val())-parseFloat($('input[name="discount"]').val());
						
						// var others= $('input[name="others"]').val();
						 gr_tot = gr_tot + parseFloat(bottomtotal);
					 	 console.log(gr_tot);
					
					 $('input[name="tot"]').val(gr_tot.toFixed(2));
				
			}
		
		
	},'json');
	
		}
	var insurance = $('input[name="insurance"]').val();
	var frieght = $('input[name="frieght"]').val();
	 var tot = $('input[name="tot"]').val();
		
		 var gr_tot= + parseFloat(insurance) + parseFloat(frieght) + parseFloat(tot);
		 $('input[name="gr_tot"]').val(gr_tot);
	
}


function PurchaseOrderForEdit(action,DisplayDiv,HideDiv,id_po,id_dept,id_cc,id_section)
{
	
	$.post('Modify_Purchase_Order',{action : 'Edit',id_po:id_po},function (r){
		if(r.data.length > 0)
			{
				for(var i=0;i<r.data.length;i++)
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
					
			        }
					if(r.data[0].remarks!=undefined || r.data[0].mode_pur!='')
					{
						$('#remarks').val(r.data[0].remarks);
						}
						
				}
				
				
				var totPrice=0;	var params='';
			var list ='<tr class="tableHeader info">'+
			'<tr class="tableHeader info">'+
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
					var remarks='',dt_scheduled_dlvry2='';
					if(params.item_remarks !=undefined)
						remarks = params.item_remarks;
					if(params.dt_scheduled_dlvry2 !=undefined)
						dt_scheduled_dlvry2 = params.dt_scheduled_dlvry2;
					console.log(params.item_remarks);
					console.log(remarks);
					document.getElementById("fileID").src="InvoiceScanFile/"+r.data[0].upload_po;
					
				list += '<tr class="success">'+
				 '<td><input style="width: 120px !important;" readonly type="text" value="'+params.nm_model+'" id="dynItemData'+i+'" name="id_prod'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod(event);"></td>'+
					'<td><center><select style="width: 120px !important;" disabled id="assetDivForInvoice'+i+'" name="id_grp'+i+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop grpdisab" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')">'+
							'<option value="">Select</option></select>'+
					'</center></td>'+
					'<td><center><select style="width: 120px !important;" id="subGroupDataForInvoice'+i+'" disabled name="id_sgrp'+i+'" patelUnPrc="'+i+'"  class="common-validation dropsgrp grpdisab" data-valid=""  style="width:80" >'+
							'<option value="">Select</option></select>'+
							'</center></td>'+
				 	'<td><center><input type="text" style="width: 150px;" name="item_remarks'+i+'" remarks="'+i+'" value="'+remarks+'"  style="width: 100px;" maxlength="500"></center></td>'+
					'<td><center><input type="text" name="dt_scheduled_dlvry'+i+'"  value="'+dt_scheduled_dlvry2+'" patelUnPrc="'+i+'"  class="common-validation modifyPODatepicker2"  style="width: 120px;"  ></center></td>'+
				
				'<input type="hidden" name="count" value="'+i+'">'+
				'<input type="hidden" name="id_po_asst" value="'+params.id_po_asst+'">'+
				
				'<td><center><input type="text"  name="qty'+i+'"  value="'+params.qty+'" style="width:60px;;text-align: right;" class="common-validation" patelUnPrc="'+i+'" onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+
				'<td><center><input type="text" name="un_prc'+i+'"  value="'+params.un_prc+'" patelUnPrc="'+i+'"  style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" ></center></td>'+
				'<td><input type="text" name="others'+i+'"  value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:60px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax1'+i+'"  value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax2'+i+'"  value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_va2\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center><input type="text"  name="tax_val1'+i+'"  value="'+params.tax_val1+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
				'<td><center><input type="text"  name="tax_val2'+i+'"  value="'+params.tax_val2+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
				'<td><center><input type="text"  name="buyback'+i+'"  value="'+params.buyback+'" patelUnPrc="'+i+'"  onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="gr_tot'+i+'"  value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
				
				//'
			
		
				'<input type="hidden" name="actionType'+i+'" value="Update">'+
				
				'</tr>';
				totPrice += parseFloat(params.gr_tot);
				}
			totPrice += (parseFloat(params.frt_text) - parseFloat(params.discount));	
			
			list += 
			'<input type="hidden" name="id_po" value="'+id_po+'">'+
			'<input type="hidden" name="no_ind" value="'+r.data[0].no_ind+'">'+
			'<input type="hidden" name="no_quot" value="'+r.data[0].no_quot+'">'+
			/*'<input type="hidden" name="do_direct" value="'+r.data[0].do_direct+'">'+*/
			
			'<input type="hidden" name="reclassification" value="No">'+
			'<tr class="success">'+
			'<td colspan="13"><input type="text" name="oter_text" value="'+params.oter_text+'" style="float:right;"  class="common-validation" ></td>'+
			 '<td><center><input type="text" name="frt_text" value="'+params.frt_text+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'frt_text\')" class="common-validation" ></center></td>'+
		        '</tr>'+
		
		        '<tr class="success">'+
		      '<td colspan="13"><b style="float:right;">Discount</b></td>'+
		        '<td><center><input type="text" name="discount" value="'+params.discount+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'discount\')" class="common-validation" ></center></td>'+
	             '</tr>'+
	        	'<tr class="success">'+
		     	'<td colspan="13"><b style="float:right;">Grand Total</b></td>'+
		      	'<td><center><input type="text" name="tot" value="'+totPrice+'" style="width: 60px;text-align: right" class="common-validation" readonly></center></td>'+
	         '</tr>'+
		 '<tr>'+
		      
         '	</td></tr><tr><td colspan="14">'+
         '	<button type="button" style="margin-left: 400px;" class="btn btn-primary upd" onclick="ControlEditPurchaseOrder(\'Update\',\'displayModifyPurchaseOrder\',\'createModifyPurchaseOrder\',\'\')">Update </button>&nbsp;&nbsp;'+
		 '	<button type="button" class="btn btn-primary upd" onclick="ControlEditPurchaseOrder(\'Cancel\',\'displayModifyPurchaseOrder\',\'createModifyPurchaseOrder\',\'\')">cancel </button>'+
		 '	</td>'+
		'</tr>';
			
			$('.EditPurchaseOrder').html(list);
			
			$('#'+DisplayDiv).show();
			$('#'+HideDiv).hide();
			$('input[name="po_total"]').val(totPrice);
			
			}
		
		$( ".modifyPODatepicker2" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      onSelect: function(selected,evnt) {
		    	  $(this).removeClass('error');
		    	  var dt_po=$('.modifyPODatepicker').val();
		    	  var dt_po1=$('.modifyPODatepicker').val();
		    	  var dt_dlvr = $(this).val();
		    	  
		    	  if(dt_po == '')
		    		  {
		    		  	alert('First filled the P.O date.');
		    		  	$('.modifyPODatepicker2').focus();
		    		  	$('.modifyPODatepicker2').addClass('error');
		    		  	$('.modifyPODatepicker2').val('');
		    		  	$(this).val('');
		    		  	exit(0);
		    		  }
		    	  else
		    		  {
		    		  var temp_strt = dt_po.split("/");
						 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
							
						var temp_end = dt_dlvr.split("/");
						var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
							
						dt_po = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
			    		  dt_dlvr = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
		    		  
		    		  if(dt_po > dt_dlvr)
		    			  {
		    			  	alert('Delivery date should be greater or equal to P.O date : '+dt_po1);
		    			  	$(this).focus();
		    			  	$(this).val('');
		    			  	$(this).addClass('error');
		    			  	exit(0);
		    			  }
		    		  
		    		  }
		    	  
		      }
		    });
		
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
		
		
	},'json')
	.fail(function(jqXHR,status)
		    {
		alert('hi');
		    });

}


function DisplayPurchaseOrderForEdit12()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var typ_po =$('select[name="typ_po"]').val();
	var id_ven_search =$('select[name="id_ven_search"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Modify_Purchase_Order',{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,typ_po:typ_po,id_ven:id_ven_search,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="10" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 325px;">Purchase Order For Modification </p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
				'<td><center><strong>PO Number</strong></center></td>'+
				'<td><center><strong>PO Date</strong></center></td>'+
				'<td><center><strong>Vendor Name </strong></center></td>'+
				'<td><center><strong>Quotation No</strong></center></td>'+
				'<td><center><strong>PI No</strong></center></td>'+
				
				'<td style="width: 70px;"><strong><center><a href="#">Modify</a></center></strong></td>'+
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
									'<td><center>'+params.no_ind+'</center></td>'+
									'<td><strong><center><a href="#" onclick="ModifyPO(\'Modify\',\'createModifyPurchaseOrder\',\'displayModifyPurchaseOrder\',\''+params.id_po+'\',\''+params.id_dept+'\',\''+params.id_cc+'\',\''+params.id_section+'\')"> Modify </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.editNewPurchaseForDisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="7"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.editNewPurchaseForDisplay').html(list);
			}
		
		
	},'json');

			}});

}


$( ".modifyPODatepicker" ).datepicker({
	yearRange: '1985:2025',
      changeMonth: true,
      changeYear: true,
      dateFormat: "dd/mm/yy",
      autoSize: true,
      altFormat: "dd/mm/yy",
      onSelect: function(selected,evnt) {
    	  $(this).removeClass('error');
    	  var dt_po=$(this).val();
    	  var no_quot=$('input[name="no_quot"]').val();
    	  var no_ind=$('input[name="no_ind"]').val();
    	  var do_direct=$('input[name="do_direct"]').val();
    	  $(".modifyPODatepicker2").val('');
    	  
    	  $.post('Modify_Purchase_Order',{action : 'CheckDate' ,dt_po:dt_po,no_ind:no_ind,no_quot:no_quot,do_direct:do_direct},function (r){
	    		if(r.data == 0)
	    			{
	    			alert('P.O date should be greater or equal to approve quotation date : '+r.dt_approv);
	    			$(".modifyPODatepicker").focus();
	    			$(".modifyPODatepicker").val('');
	    			$(".modifyPODatepicker").addClass('error');
	    				exit(0);
	    			}
	    		
	    },'json');
    	  
    	  
      }
    });

/// Reclassification...
function ReclassificationModifyPO(action,DisplayDiv,HideDiv,id_po,id_dept,id_cc,id_section){
	/*SubDropDownDataDisplay(id_dept,'requestedPrCC','M_Cost_Center',function (status){
		if(status)
		{
			SubDropDownDataDisplay(id_cc,'sectionDataForRequest','M_Section',function (status){
				if(status)
				{
					DisplaySubDropDownData(id_section,'orgLocDataForPO','M_ORG_LOCATION',function (status){
						if(status)
						{
							ReclassificationPurchaseOrderForEdit(action,DisplayDiv,HideDiv,id_po);
						}});
				}});
		}});*/
	ReclassificationPurchaseOrderForEdit(action,DisplayDiv,HideDiv,id_po);
}

function ReclassificationDisplayPurchaseOrderForEdit()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var typ_po =$('select[name="typ_po"]').val();
	var id_ven_search =$('select[name="id_ven_search"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Modify_Purchase_Order',{action : 'Displayvo',dt_frm:dt_frm,dt_to:dt_to,typ_po:typ_po,id_ven:id_ven_search,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="10" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 400px;">Modify Verbal Order</p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
				'<td><center><strong>PO Number</strong></center></td>'+
				'<td><center><strong>PO Date</strong></center></td>'+
				'<td><center><strong>Vendor Name </strong></center></td>'+
				'<td><center><strong>Tender No</strong></center></td>'+
				'<td><center><strong>PO Amount</strong></center></td>'+
				
				'<td><center><strong>Raised By</strong></center></td>'+
				'<td><center><strong>PI No</strong></center></td>'+
				'<td><center><strong>Indent From Section</strong></center></td>'+
				'<td><center><strong>PO type</strong></center></td>'+
				'<td style="width: 70px;"><strong><center><a href="#">Modify</a></center></strong></td>'+
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
									'<td><center>'+tender_num+'</center></td>'+
									'<td><center>'+params.tot+'</center></td>'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><center>'+params.no_ind+'</center></td>'+
									'<td><center>'+params.nm_section+'</center></td>'+
									'<td><center>'+params.typ_of_po+'</center></td>'+
									'<td><strong><center><a href="#" onclick="ReclassificationModifyPO(\'Modify\',\'createModifyPurchaseOrder\',\'displayModifyPurchaseOrder\',\''+params.id_po+'\',\''+params.id_dept+'\',\''+params.id_cc+'\',\''+params.id_section+'\')"> Modify </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.editNewPurchaseForDisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="7"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.editNewPurchaseForDisplay').html(list);
			}
		
		
	},'json');

			}});

}


function ReclassificationPurchaseOrderForEdit(action,DisplayDiv,HideDiv,id_po,id_dept,id_cc,id_section)
{
	
	$.post('Modify_Purchase_Order',{action : 'Edit',id_po:id_po},function (r){
		if(r.data.length > 0)
			{
			
				for(var i=0;i<r.data.length;i++)
				{
					
					for (var key in r.data[i])
			        {
						if($('select[name='+key+']').is("select") && r.data[i][key] != '')
						{
								$('select[name='+key+']').val('' + r.data[i][key] + '').trigger('change');
						}
						else
						{
							$('input[name='+key+']').val(r.data[i][key]);
						}
					}
					var remarks=r.data[0].remarks;
					document.getElementById("myTextarea").value =remarks;
				
				}
				
				
				var params='',totPrice=0;
			var list ='<tr class="tableHeader info">'+
			'<td style="min-width:200px;"><strong><center>Item Name<a href=#></a></center></strong></td>'+
			'<td style="width:60px;"><strong><center>Item Code</center></strong></td>'+
			
			'<td><strong><center>UOM</center></strong></td>'+
		//	'<td><strong><center>Remarks</center></strong></td>'+
			/*'<td><strong><center>Delivery Date <font color="red">*</font></center></strong></td>'+*/
			'<td style="width: 75px;"><strong><center>Item Price <font color="red"> * </font></center></strong></td>'+
			'<td style="width: 75px;"><strong><center>P&F/Freight/EDC<font color="red"> * </font></center></strong></td>'+
			'<td style="width: 75px;display:none;"><strong><center>Others<font color="red"> * </font></center></strong></td>'+
			
			'<td style="width: 120px;"><strong><center>Tax Name</center></strong></td>'+
			'<td style="width: 75px;"><strong><center>Tax Value</center></strong></td>'+
			'<td style="width: 75px;"><strong><center>Installation(Incl. Taxes)</center></strong></td>'+
			
			'<td><strong><center>Unit Price<font color="red">*</font></center></strong></td>'+
			
			'<td style="width:70px;"><strong><center>Quantity <font color="red">*</font></center></strong></td>'+
			'<td><strong><center>Total Price</center></strong></td>'+
			
			'<td ><center><b>Installation/<br>Escort</b></center></td>'+
			'<td ><center><b>Additional/<br>Freight </b></center></td>'+
			'<td ><center><b >Insurance</b></center></td>'+
			'<td style="width: 120px;"><strong><center>Tax Name</center></strong></td>'+
			
			'<td ><center><b >IGST</b></center></td>'+
			'<td ><center><b >Others</b></center></td>'+
			'<td ><center><b >Buy Back</b></center></td>'+
			'<td ><center><b>Grand Total</b></center></td>'+
			'<td><strong>Available Budget</strong></td>'+
			
		'</tr>';
			var i=0;
			for(i=0;i<r.data.length;i++)
				{
				 params=r.data[i];
				var remarks='',dt_scheduled_dlvry='';
				if(params.item_remarks !=undefined)
					remarks = params.item_remarks;
				if(params.dt_scheduled_dlvry1 !=undefined)
					dt_scheduled_dlvry = params.dt_scheduled_dlvry1;
				var ind_bidge=parseFloat(params.budget)+parseFloat(params.grnd_tot);
				console.log(params.id_bud);
				console.log(ind_bidge);
				list += '<tr class="success">'+
				/*'<td><center>'+params.nm_prod+'</center></td>'+
				'<td><center>'+params.cd_prod+'</center></td>'+
				
				'<td><center>'+params.uomval+'</center></td>'+*/
				/*'<td><input type="text" list="itemData" name="nm_prod'+i+'" value="'+params.nm_prod+'" patelUnPrc="'+i+'" class="common-validation resetAcc" id="item" onchange="changeEventHandler(event);">'+
					'<datalist id="itemData" >'+
					'</datalist>'+
				'</td>'+*/
				'<td><input type="text" id="dynItemData'+i+'" name="nm_prod'+i+'" value="'+params.nm_prod+'" patelUnPrc="'+i+'" style="padding: 0px;width: 248px;" class="common-validation resetAcc" onchange="changeEventHandlerprod(event);">'+
				'</td>'+
				'<td><input type="text" name="cd_prod'+i+'" id="dynCdProd'+i+'" value="'+params.cd_prod+'" class="common-validation resetAcc" style="padding: 0px;width:130px" patelUnPrc="'+i+'" onchange="changeEventHandlercode(event);"></td>'+
		
				/*'<td><input type="text" list="dynItemData'+i+'" name="nm_prod'+i+'" value="'+params.nm_prod+'" patelUnPrc="'+i+'" class="common-validation resetAcc" id="item" onkeyup="onKeyUpBringPCData(event);" onchange="changeEventHandler(event);">'+
					'<datalist id="dynItemData'+i+'" >'+
					'</datalist>'+
				'</td>'+
				'<td><input type="text" name="cd_prod'+i+'" value="'+params.cd_prod+'" style="width:80px" class="common-validation resetAcc " patelUnPrc="'+i+'"  readonly></td>'+
				*/
				'<input type="hidden" name="id_bud'+i+'" value="'+params.id_bud+'"  patelUnPrc="'+i+'" >'+
				
				'<input type="hidden" name="ind_bidge'+i+'" value="'+ind_bidge+'" patelUnPrc="'+i+'" >'+

				
				'<td><input type="text" name="uom'+i+'" value="'+params.uomval+'" style="width:80px" class="resetAcc " patelUnPrc="'+i+'" readonly></td>'+
				
				'<input type="hidden" style="width: 150px;" maxlength="500" name="item_remarks'+i+'" remarks="'+i+'" value="'+remarks+'"  style="width: 100px;" >'+
				
				'<input type="hidden" name="count" value="'+i+'">'+
				'<input type="hidden" name="id_po_asst" value="'+params.id_po_asst+'">'+
				'<input type="hidden" name="dt_scheduled_dlvry'+i+'"  value="'+dt_scheduled_dlvry+'" patelUnPrc="'+i+'"  class="common-validation modifyPODatepicker2"  style="width: 120px;"  >'+
				
				//'<td><center><input type="text" name="dt_rec_quot'+i+'" value="'+params.dtrecv+'" style="width: 100px;" class="common-validation modifyPODatepicker2" data-valid="mand"></center></td>'+
				'<td><center><input type="text" name="item_prc'+i+'" patelUnPrc="'+i+'" value="'+params.item_prc+'" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'item_prc\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
				'<td><center><input type="text" name="freight_rate'+i+'" patelUnPrc="'+i+'" value="'+params.freight_rate+'" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'freight_rate\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
				'<td style="display:none;"><center><input type="text" name="others_chrg'+i+'" patelUnPrc="'+i+'" value="'+params.others_chrg+'" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'others_chrg\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
				
				'<td><center>'+
				'<select style="width: 100px;" name="taxId'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="'+params.id_tax+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'taxId\')">'+
				'</select>'+
				'</center></td>'+
				'<input type="hidden" name="taxPercent'+i+'" value="0">'+
				'<td><center><input type="text"  name="taxVal'+i+'" taxVal="'+i+'" value="'+params.tax_val+'" readonly style="width: 60px;text-align: right;" class="patelTax"></center></td>'+
				'<td><center><input type="text"  name="itemins_chrg'+i+'" patelUnPrc="'+i+'" value="'+params.itemins_chrg+'" style="width: 60px;text-align: right;" class="patelUnPrc common-validation" data-valid="num" onChange="calculateTot(event,\'itemins_chrg\')"></center></td>'+
				
				'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="'+params.un_prc+'" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" readOnly></center></td>'+
				'<td><center><input type="text" name="qty'+i+'" patelqty'+params.id_prod+'="'+params.qty+'" patelUnPrc="'+i+'" value="'+params.qty+'" onChange="calculateTot(event,\'qty\')" style="width: 50px;text-align: right;" class="patelQty" data-valid="num"></center></td>'+
				
				'<input type="hidden" name="old_qty'+i+'" patelqty'+params.id_prod+'="'+params.qty+'" patelUnPrc="'+i+'" value="'+params.qty+'" style="width: 50px;text-align: right;" class="patelQty" data-valid="num">'+
				'<input type="hidden" name="old_rem_qty'+i+'" patelqty'+params.id_prod+'="'+params.rem_qty+'" patelUnPrc="'+i+'" value="'+params.rem_qty+'" style="width: 50px;text-align: right;" class="patelQty" data-valid="num">'+
				
				'<td><center><input type="text" name="tot_prc'+i+'" value="'+params.tot_prc+'" style="width: 80px;text-align: right;"  readonly></center></td>'+
				
				
				'<td><center><input type="text"  name="frt_rate'+i+'" value="'+params.frt_rate+'" style="width: 60px;text-align: right;" patelTaxadd="'+i+'" data-valid="num" onChange="TaxCalculationadd(event,\'frt_rate\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="add_chrg'+i+'" value="'+params.add_chrg+'" style="width: 60px;text-align: right;" patelTaxadd="'+i+'" data-valid="num" onChange="TaxCalculationadd(event,\'add_chrg\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="frt_text'+i+'" value="'+params.frt_text+'" style="width: 60px;text-align: right;" patelTaxadd="'+i+'" data-valid="num" onChange="TaxCalculationadd(event,\'frt_text\')" class="common-validation" ></center></td>'+
				
				'<td><center>'+
				'<select style="width:70px;text-align: right;" name="taxIdadd'+i+'" patelTaxadd="'+i+'" taxName="'+params.taxid_add+'" class="common-validation patelTax" onChange="TaxCalculationadd(event,\'taxIdadd\')">'+
				'</select>'+
				'</center></td>'+
				'<input type="hidden" name="taxPercentadd'+i+'" value="0">'+
			
				'<td><center><input type="text"  name="add_chrg1'+i+'" value="'+params.add_chrg1+'" readonly style="width: 60px;text-align: right;" patelUnPrc="'+i+'" data-valid="num" onChange="calculateTot(event,\'add_chrg1\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="add_chrg2'+i+'" value="'+params.add_chrg2+'" style="width: 60px;text-align: right;" patelUnPrc="'+i+'" data-valid="num" onChange="calculateTot(event,\'add_chrg2\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="buy_back'+i+'" value="'+params.buy_back+'" style="width: 60px;text-align: right;" patelUnPrc="'+i+'" data-valid="num" onChange="calculateTot(event,\'buy_back\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="grnd_tot'+i+'" value="'+params.grnd_tot+'" style="width: 60px;text-align: right;" patelUnPrc="'+i+'" class="commonTotal"  readonly></center></td>'+
				'<td><input type="text" style="text-align: right;" name="budget'+i+'" value="'+params.budget+'" id="assetTotalPrice " patelUnPrc="'+i+'" class="resetAcc1 FieldResize" readonly></td>'+
				
				'<input type="hidden" name="actionType'+i+'" value="Update">'+
				
				'</tr>';
				totPrice += parseFloat(params.grnd_tot);
				}
			totPrice += (parseFloat(params.frtrate)+ parseFloat(params.freight_amount) + parseFloat(params.addchrg) + parseFloat(params.frttext)) -parseFloat(params.buyback) -parseFloat(params.discount);
			
			$('input[name="itemCount"]').val(parseInt(i));
			
		list2 = 
			'<input type="hidden" name="id_po" value="'+id_po+'">'+
			'<input type="hidden" name="no_ind" value="'+r.data[0].no_ind+'">'+
			'<input type="hidden" name="no_quot" value="'+r.data[0].no_quot+'">'+
			/*'<input type="hidden" name="do_direct" value="'+r.data[0].do_direct+'">'+*/
			
			'<input type="hidden" name="reclassification" value="Yes">'+
		
			
		'<tr>'+
		'<td><b style="float:right;">Freight Charge :<input type="text" name="freight_amount" value="'+params.freight_amount+'" style="width: 80px;text-align: right;" data-valid="num" onChange="addInTotal(\'freight_amount\')" class="common-validation" > </b></td>'+
	'</tr>'+
		'<tr>'+
			'<td><b style="float:right;">Installation Charge :<input type="text" name="frt_rate" value="'+params.frtrate+'" style="width: 80px;text-align: right;" data-valid="num" onChange="addInTotal(\'frt_rate\')" class="common-validation" > </b></td>'+
		'</tr>'+
		'<tr>'+
			'<td><b style="float:right;">Additional charges: <input type="text" name="add_chrg" value="'+params.addchrg+'" style="width: 80px;text-align: right;" data-valid="num" onChange="addInTotal(\'add_chrg\')" class="common-validation" ></b></td>'+
		'</tr>'+
		'<tr>'+
			'<td><b style="float:right;">Others Charge: <input type="text" name="frt_text" value="'+params.frttext+'" style="width: 80px;text-align: right;" data-valid="num" onChange="addInTotal(\'frt_text\')" class="common-validation" ></b></td>'+
		'</tr>'+
		'<tr>'+
			'<td><b style="float:right;">Buy Back: <input type="text" name="buy_back" value="'+params.buyback+'" style="width: 80px;text-align: right;" data-valid="num" onChange="addInTotal(\'buy_back\')" class="common-validation" ></b></td>'+
		'</tr>'+
		'<tr>'+
		'<td><b style="float:right;">Discount: <input type="text" name="discount" value="'+params.discount+'" style="width: 80px;text-align: right;" data-valid="num" onChange="addInTotal(\'discount\')" class="common-validation" ></b></td>'+
	'</tr>'+
		'<tr>'+
		'<td><b style="float:right;"><center><input type="text" name="tot" value="'+totPrice.toFixed(2)+'" style="width: 80px;text-align: right;" class="common-validation" readonly></center></b></td>'+
		'<input type="hidden" name="gr_tot" value="'+totPrice.toFixed(2)+'" style="width: 80px;text-align: right;" class="common-validation" readonly></center>'+

			'</tr>'+
		'</tr>'+
		'<tr>'+

		 '<tr>'+
         '	<td>'+
         '	<button type="button" style="margin-left: 400px;" class="btn btn-primary upd" onclick="ControlEditPurchaseOrder(\'Update\',\'displayModifyPurchaseOrder\',\'createModifyPurchaseOrder\',\'\')">Update </button>&nbsp;&nbsp;'+
		 '	<button type="button" class="btn btn-primary upd" onclick="ControlEditPurchaseOrder(\'Cancel\',\'displayModifyPurchaseOrder\',\'createModifyPurchaseOrder\',\'\')">cancel </button>'+
		 '	</td>'+
		'</tr>';
			
			$('.EditPurchaseOrder').html(list);
			$('.EditPurchaseOrder2').html(list2);
			
			$('#'+DisplayDiv).show();
			$('#'+HideDiv).hide();
			}
		
		$( ".modifyPODatepicker2" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      onSelect: function(selected,evnt) {
		    	  $(this).removeClass('error');
		    	  var dt_po=$('.modifyPODatepicker').val();
		    	  var dt_po1=$('.modifyPODatepicker').val();
		    	  var dt_dlvr = $(this).val();
		    	  
		    	  if(dt_po == '')
		    		  {
		    		  	alert('First filled the P.O date.');
		    		  	$('.modifyPODatepicker2').focus();
		    		  	$('.modifyPODatepicker2').addClass('error');
		    		  	$('.modifyPODatepicker2').val('');
		    		  	$(this).val('');
		    		  	exit(0);
		    		  }
		    	  else
		    		  {
		    		  var temp_strt = dt_po.split("/");
						 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
							
						var temp_end = dt_dlvr.split("/");
						var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
							
						dt_po = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
			    		  dt_dlvr = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
		    		  
		    		  if(dt_po > dt_dlvr)
		    			  {
		    			  	alert('Delivery date should be greater or equal to P.O date : '+dt_po1);
		    			  	$(this).focus();
		    			  	$(this).val('');
		    			  	$(this).addClass('error');
		    			  	exit(0);
		    			  }
		    		  
		    		  }
		    	  
		      }
		    });
		
		DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
			if(status)
			{
				for(var i=0;i<r.data.length;i++)
				{
					params=r.data[i];
					for (var key in r.data[i])
			        {
						
							if(key == 'id_tax'){
								$('select[name=taxId'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
			       
							}
							if(key=='taxid_add')
							{
								$('select[name=taxIdadd'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
								
							}
							
							}
				}
			}});
		/*DropDownDataDisplayForItem("M_Prod_Cart","itemData",function (status){
			if(status)
			{
				
			}});*/
		
		$("#dynCdProd0").easyAutocomplete(options2);
		$("#dynCdProd1").easyAutocomplete(options2);
		$("#dynCdProd2").easyAutocomplete(options2);
		$("#dynCdProd3").easyAutocomplete(options2);
		$("#dynCdProd4").easyAutocomplete(options2);
		$("#dynCdProd5").easyAutocomplete(options2);
		$("#dynCdProd6").easyAutocomplete(options2);
		$("#dynCdProd7").easyAutocomplete(options2);
		$("#dynCdProd8").easyAutocomplete(options2);
		$("#dynCdProd9").easyAutocomplete(options2);
		$("#dynCdProd10").easyAutocomplete(options2);
		$("#dynCdProd11").easyAutocomplete(options2);
		$("#dynCdProd12").easyAutocomplete(options2);
		$("#dynCdProd13").easyAutocomplete(options2);
		$("#dynCdProd14").easyAutocomplete(options2);
		$("#dynCdProd15").easyAutocomplete(options2);
		$("#dynCdProd16").easyAutocomplete(options2);
		$("#dynCdProd17").easyAutocomplete(options2);
		$("#dynCdProd18").easyAutocomplete(options2);
		$("#dynCdProd19").easyAutocomplete(options2);
		$("#dynCdProd20").easyAutocomplete(options2);
		$("#dynCdProd21").easyAutocomplete(options2);
		$("#dynCdProd22").easyAutocomplete(options2);
		$("#dynCdProd23").easyAutocomplete(options2);
		$("#dynCdProd24").easyAutocomplete(options2);
		$("#dynCdProd25").easyAutocomplete(options2);
		$("#dynCdProd26").easyAutocomplete(options2);
		$("#dynCdProd27").easyAutocomplete(options2);
		$("#dynCdProd28").easyAutocomplete(options2);
		$("#dynCdProd29").easyAutocomplete(options2);
		$("#dynCdProd30").easyAutocomplete(options2);
		
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
	},'json')
	.fail(function(jqXHR,status)
		    {
		
		    });

	
	
}


function dispalyLineItemDynamically(action,callback){
	
	
		var i=$('input[name="itemCount"]').val();
			
		var list = '<tr class="success">'+
				/*'<td style="display:none;"><input type="text" list="itemData" name="nm_prod'+i+'" value="" patelUnPrc="'+i+'" class="common-validation resetAcc" id="item" onchange="changeEventHandler(event);">'+
					'<datalist id="itemData" >'+
					'</datalist>'+
				'</td>'+*/
				/*'<td><input type="text" list="dynItemData'+i+'" name="nm_prod'+i+'" value="" patelUnPrc="'+i+'" class="common-validation resetAcc" id="item" onkeyup="onKeyUpBringPCData(event);" onchange="changeEventHandler(event);">'+
					'<datalist id="dynItemData'+i+'" >'+
					'</datalist>'+
				'</td>'+
				'<td><input type="text" name="cd_prod'+i+'" value="" style="width:80px" class="common-validation resetAcc " patelUnPrc="'+i+'"  readonly></td>'+
				*/
		 '<input type="hidden" name="id_bud'+i+'" value="abc"  patelUnPrc="'+i+'" >'+
			
			'<input type="hidden" name="ind_bidge'+i+'" value="0.0" patelUnPrc="'+i+'" >'+
	
			'<td><input type="text" id="dynItemData'+i+'" name="nm_prod'+i+'"  patelUnPrc="'+i+'" style="padding: 0px;width: 248px;" class="common-validation resetAcc" onchange="changeEventHandlerprod(event);">'+
			'</td>'+
			'<td><input type="text" name="cd_prod'+i+'" id="dynCdProd'+i+'"  class="common-validation resetAcc" style="padding: 0px;width:130px" patelUnPrc="'+i+'" onchange="changeEventHandlercode(event);"></td>'+
	
			
				/*'<td><input type="text" id="dynItemData'+i+'" name="nm_prod'+i+'" patelUnPrc="'+i+'" style="padding: 0px;width: 248px;" class="common-validation resetAcc" onchange="changeEventHandlerprod(event);">'+
				'</td>'+
				'<td><input type="text" name="cd_prod'+i+'" id="dynCdProd'+i+'"  class="common-validation resetAcc" style="padding: 0px;width:130px" patelUnPrc="'+i+'" onchange="changeEventHandlercode(event);"></td>'+
		*/
				'<td><input type="text" name="uom'+i+'" value="" style="width:80px" class="resetAcc " patelUnPrc="'+i+'" readonly></td>'+
				
				//'<td><center><input type="text" style="width: 150px;" name="item_remarks'+i+'" remarks="'+i+'" value="" maxlength="500"  style="width: 100px;"></center></td>'+
				
				'<input type="hidden" name="count" value="'+i+'">'+
				'<input type="hidden" name="id_po_asst" value="'+params.id_po_asst+'">'+
				
				//'<td><center><input type="text" name="dt_rec_quot'+i+'" value="'+params.dtrecv+'" style="width: 100px;" class="common-validation modifyPODatepicker2" data-valid="mand"></center></td>'+
				'<td><center><input type="text" name="item_prc'+i+'" patelUnPrc="'+i+'" value="0" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'item_prc\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
				'<td><center><input type="text" name="freight_rate'+i+'" patelUnPrc="'+i+'" value="0" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'freight_rate\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
				'<td style="display:none;"><center><input type="text" name="others_chrg'+i+'" patelUnPrc="'+i+'" value="0" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'others_chrg\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
				
				'<td><center>'+
				'<select style="width: 100px;" id="taxDataForDropDown'+i+'" name="taxId'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="'+params.id_tax+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'taxId\')">'+
				'</select>'+
				'</center></td>'+
				'<input type="hidden" name="taxPercent'+i+'" value="0">'+
				'<td><center><input type="text"  name="taxVal'+i+'" taxVal="'+i+'" value="0" readonly style="width: 60px;text-align: right;" class="patelTax"></center></td>'+
				'<td><center><input type="text"  name="itemins_chrg'+i+'" patelUnPrc="'+i+'" value="0" style="width: 60px;text-align: right;" class="patelUnPrc common-validation" data-valid="num" onChange="calculateTot(event,\'itemins_chrg\')"></center></td>'+
				
				'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="0" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" readOnly></center></td>'+
				//'<td><center><input type="text" name="qty'+i+'" patelqty'+params.id_prod+'="'+params.qty+'" patelUnPrc="'+i+'" value="0" onChange="calculateTot(event,\'qty\')" style="width: 50px;" class="patelQty" data-valid="num"></center></td>'+
				'<td><center><input type="text" name="qty'+i+'"  patelUnPrc="'+i+'" value="0" onChange="calculateTot(event,\'qty\')" style="width: 50px;text-align: right;" class="patelQty" data-valid="num"></center></td>'+
				
				'<td><center><input type="text" name="tot_prc'+i+'" value="0" style="width: 80px;text-align: right;"  readonly></center></td>'+
				'<td><center><input type="text"  name="frt_rate'+i+'" value="0.0" style="width: 60px;text-align: right;" patelTaxadd="'+i+'" data-valid="num" onChange="TaxCalculationadd(event,\'frt_rate\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="add_chrg'+i+'" value="0.0" style="width: 60px;text-align: right;" patelTaxadd="'+i+'" data-valid="num" onChange="TaxCalculationadd(event,\'add_chrg\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="frt_text'+i+'" value="0.0" style="width: 60px;text-align: right;" patelTaxadd="'+i+'" data-valid="num" onChange="TaxCalculationadd(event,\'frt_text\')" class="common-validation" ></center></td>'+
				'<td><center>'+
				'<select style="width:70px;" id="taxDataForDropDownadd'+i+'" name="taxIdadd'+i+'" patelTaxadd="'+i+'" class="common-validation patelTax" onChange="TaxCalculationadd(event,\'taxIdadd\')">'+
				'</select>'+
				'</center></td>'+
				'<input type="hidden" name="taxPercentadd'+i+'" value="0">'+
				'<td><center><input type="text"  name="add_chrg1'+i+'" value="0.0" readonly style="width: 60px;text-align: right;" patelUnPrc="'+i+'" data-valid="num" onChange="calculateTot(event,\'add_chrg1\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="add_chrg2'+i+'" value="0.0" style="width: 60px;text-align: right;" patelUnPrc="'+i+'" data-valid="num" onChange="calculateTot(event,\'add_chrg2\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="buy_back'+i+'" value="0.0" style="width: 60px;text-align: right;" patelUnPrc="'+i+'" data-valid="num" onChange="calculateTot(event,\'buy_back\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="grnd_tot'+i+'" value="0.0" style="width: 60px;text-align: right;" patelUnPrc="'+i+'" class="commonTotal"  readonly></center></td>'+
				'<td><input type="text" style="text-align: right;" name="budget'+i+'" value="0.0" id="assetTotalPrice " patelUnPrc="'+i+'" class="resetAcc1 FieldResize" readonly></td>'+
				
				'<input type="hidden" name="actionType'+i+'" value="Insert">'+
				
			'</tr>';
				
		$('input[name="itemCount"]').val(parseInt(i) +1);
	$('.EditPurchaseOrder').append(list);
	
	DropDownDataDisplay("M_Tax","taxDataForDropDown"+i+"",function (status){
		if(status)
		{
			
		}});
	DropDownDataDisplay("M_Tax","taxDataForDropDownadd"+i+"",function (status){
		if(status)
		{
			
		}});
	var dropDownId= "dynItemData"+i+"";
	/*DropDownDataDisplayForItem("M_Prod_Cart",dropDownId,function (status){
		if(status)
		{
			
		}});*/
	//callback(true);	
	

	$("#dynCdProd0").easyAutocomplete(options2);
	$("#dynCdProd1").easyAutocomplete(options2);
	$("#dynCdProd2").easyAutocomplete(options2);
	$("#dynCdProd3").easyAutocomplete(options2);
	$("#dynCdProd4").easyAutocomplete(options2);
	$("#dynCdProd5").easyAutocomplete(options2);
	$("#dynCdProd6").easyAutocomplete(options2);
	$("#dynCdProd7").easyAutocomplete(options2);
	$("#dynCdProd8").easyAutocomplete(options2);
	$("#dynCdProd9").easyAutocomplete(options2);
	$("#dynCdProd10").easyAutocomplete(options2);
	$("#dynCdProd11").easyAutocomplete(options2);
	$("#dynCdProd12").easyAutocomplete(options2);
	$("#dynCdProd13").easyAutocomplete(options2);
	$("#dynCdProd14").easyAutocomplete(options2);
	$("#dynCdProd15").easyAutocomplete(options2);
	$("#dynCdProd16").easyAutocomplete(options2);
	$("#dynCdProd17").easyAutocomplete(options2);
	$("#dynCdProd18").easyAutocomplete(options2);
	$("#dynCdProd19").easyAutocomplete(options2);
	$("#dynCdProd20").easyAutocomplete(options2);
	$("#dynCdProd21").easyAutocomplete(options2);
	$("#dynCdProd22").easyAutocomplete(options2);
	$("#dynCdProd23").easyAutocomplete(options2);
	$("#dynCdProd24").easyAutocomplete(options2);
	$("#dynCdProd25").easyAutocomplete(options2);
	$("#dynCdProd26").easyAutocomplete(options2);
	$("#dynCdProd27").easyAutocomplete(options2);
	$("#dynCdProd28").easyAutocomplete(options2);
	$("#dynCdProd29").easyAutocomplete(options2);
	$("#dynCdProd30").easyAutocomplete(options2);
	
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
