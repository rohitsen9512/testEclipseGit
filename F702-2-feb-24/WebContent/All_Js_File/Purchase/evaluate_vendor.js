
function ControlFinalizeVendorDiveval(action , DisplayDiv , HideDiv , no_ind)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Cancel')
	{
		DisplayFinalizeVendor();
		$('#'+DisplayDiv).hide();
		$('#'+HideDiv).show();
	}
	
	else if(action =='Modify')
		{
			EditFinalizeVendorFun(action , DisplayDiv , HideDiv , no_ind);
			
		}
	else if(action =='split')
	{
		EditFinalizeVendorFunsplit(action , DisplayDiv , HideDiv , no_ind);
		
	}
	else if( action =='approvalrazorpay')
	{
		var t=false;
		t=ValidationForm('submitApproveQuotation');

 if(t)
 	{
	 bootbox.confirm("Are you sure ?", function(result) {
		if(result)
		{
 			var x = $('#submitApproveQuotation').serialize();
 			$.post('Finalize_Vendor',x,function (r){
 						if(r.data == 1)
 							{
 							
 								bootbox.alert("Vendor has been sent for approval.");
 								$( ".evaluate" ).trigger( "click" );
 							}
 						else
 						{
 							bootbox.alert("Something went wrong Please try again.");
 						}
 						$('.appq').removeAttr('disabled');
 					},'json');
		}});
 		}
 
	
	
	}
	else if(action =='Accepted')
	{
		var t=false;
		t=ValidationForm('submitApproveQuotation');

 if(t)
 	{
	 bootbox.confirm("Are you sure ?", function(result) {
		if(result)
		{
 			var x = $('#submitApproveQuotation').serialize();
 			$.post('Finalize_Vendor',x,function (r){
 						if(r.data == 1)
 							{
 								DisplayFinalizeVendor();
 								bootbox.alert("Quotation has been sent for financial approval.");
 								$( ".FinalizeVendor" ).trigger( "click" );
 							}
 						else
 						{
 							bootbox.alert("Something went wrong Please try again.");
 						}
 						$('.appq').removeAttr('disabled');
 					},'json');
		}});
 		}
 
	
	
	}
	
			}});
}



function EditFinalizeVendorFun(action , DisplayDiv , HideDiv , no_ind)
{

	SessionCheck(function (ses){		
		if(ses)
			{
		$.post('Finalize_Vendor',{action : 'Edit' , no_ind : no_ind},function (r){
			var list='', temp='';
			 var place=0;   var pr = 0;zx=1,qt=0; var no_ven=0;
				if(r.data.length > 0)
					{
					
					var id_quotArr = new Array();
					for(var i = 0; i < r.data.length ; i++)
					{
					if(id_quotArr.length === 0)
						{
						id_quotArr.push(r.data[i].id_quot);
						}
					else
						{
						if($.inArray(r.data[i].id_quot, id_quotArr) > -1){
							
						}
						else
							{
							id_quotArr.push(r.data[i].id_quot);
							}
						}
					}
					var t=0,m=0;
					var tempOptionVal='',venSelectionList='',quotationId='';
					
					for(var j = 0; j < id_quotArr.length ; j++)
					{
						
						params=r.data[(m)];
						var tempQuot = params.id_quot;
						quotationId +='<input type="hidden" name="quotationId" value="'+tempQuot+'">';
						
						tempOptionVal +='<option value="'+params.id_ven+'">'+params.nm_ven+'-'+params.no_quot+'</option>';
						for(var i = 0; i < r.data.length ; i++)
						{
							
							 
							params1 = r.data[i];
							if(params1.id_quot == tempQuot)
							{
								m=i+1;
							}
						}
					}
					
						for(var j = 0; j < id_quotArr.length ; j++)
						{
							
							params=r.data[(t)];
							var tempQuot = params.id_quot;
							//tempOptionVal +='<option value="'+tempQuot+'">'+params.nm_ven+'-'+params.no_quot+'</option>';
							
							 temp='<tr style="background-color: cadetblue;color: white;border-bottom-style: hidden;">'+
							'<td colspan="7" style="border-right-style: hidden;"><strong style="border-bottom-style: hidden;">Vendor Name :</strong> <b>'+params.nm_ven+'</b></td>'+
							'<td colspan="4" style="border-right-style: hidden;"><strong style="border-bottom-style: hidden;">Vendor Code :</strong> <b>'+params.cd_ven+'</b></td>'+
							
						'</tr>'+
						'<tr style="background-color: cadetblue;color: white;">'+
							'<td colspan="6" style="border-right-style: hidden;"><strong style="">Quotation No  :</strong><b>'+params.no_quot+'</b></td>'+
							'<td colspan="6" style="border-right-style: hidden;"><strong style="">Quotation Date :</strong><b>'+params.dt_reqqt+'</b></td>'+
						'</tr>'+
						'<tr class="tableHeader info">'+
						'<td style="min-width:200px;"><strong><center>Model Name </strong></center></td>'+
						'<td><center><strong>Model Code</strong></center></td>'+
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
							
							 if(j==0){
				 venSelectionList='<tr ><td colspan="4"></td></tr>'+
					 '<tr class="tableHeader info">'+
									'<td><strong>Model Name</strong></td>'+
									'<td style="width: 70px"><strong>Model Code</strong></td>'+
								    '<td style="width: 70px"><strong>Quantity</strong></td>';
	                          
	    			
				 var venshow='';
				
					for(var k = 0; k < r.data.length ; k++)
					{
					
						var params3=r.data[k];
						console.log(params3.nm_ven);
						if(params3.nm_ven != venshow)
						{
							venSelectionList=venSelectionList+'<td><table class="commonTable " align="center" width="100%" ><tr ><td colspan="4"><strong style=" font-size:12px;"><center>'+params3.nm_ven+'</center></strong></td></tr>'+
							'<tr><td ><strong ><center><font color=red style="margin-left:10px;">Percent  Qty</font></center></strong></td>'+
							'<td><strong><center><font color=red style="margin-left:10px;">Split Quantity</font></center></strong></td>'+
							'<td><strong><center><font color=red style="margin-left:10px;">Unit Rate</font></center></strong></td>'+
							
							'<td><strong><center><font color=red style="margin-left:10px;">Negotiated Rate</font></center></strong></td></tr></table></td>'+
							'<input type="hidden" name="no_ven"  value="'+no_ven+'">'+
							'<input type="hidden" name="noind'+no_ven+'"  value="'+params3.no_ind+'">'+
							'<input type="hidden" name="idquot'+no_ven+'"  value="'+params3.id_quot+'">';
							no_ven++;
						}	
						venshow=params3.nm_ven;
					}
						venSelectionList=venSelectionList+'<td><strong><center>Selection Remarks<center></strong></td>'+
					
						
									//'<td style="width:120px;"><strong><center><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'prRequestItemForVendorSelectAll\')"> Check All </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'prRequestItemForVendorSelectAll\')"> Uncheck All</a></center></strong></td>'+
									
								'</tr>';
							 }
							
							for(var i = 0; i < r.data.length ; i++)
							{
								
								 
								params1 = r.data[i];
								if(params1.id_quot == tempQuot)
								{
									if(j==0){
										 venSelectionList += 
											 '<tr class="success">'+
										 				'<input type="hidden" name="count" value="'+i+'">'+
										 				//'<input type="hidden" name="id_prod'+i+'" value="'+params1.id_prod+'">'+
														 
														//'<input type="hidden" name="id_quot_asst'+i+'" value="'+params1.id_quot_asst+'">'+
										 				
										 				'<td>'+params1.nm_model+'</td>'+
														 '<td>'+params1.cd_model+'</td>'+
															 '<td><center>'+params1.qty+'</center></td>';
										
										 var venshowlist='';		 
										     
										
											//console.log(pr);
											//console.log(zx);
											var tx=r.data.length/no_ven;
										        	while ( pr < r.data.length){
										        	var params4=r.data[pr];
										        	var qty=0,per=0;		
										        	if(pr==zx-1){
										        		qty=params1.qty;
										        		per=100;
															}
										        	if(params4.nm_ven != venshowlist)
													{
																
																console.log(params4.id_quot_asst + params1.nm_prod + params4.nm_ven);
															//console.log(params4.nm_ven);
																
																	venSelectionList=venSelectionList+'<td><table class="commonTable " align="center" width="100%"><tr><td><center>	<input style="margin-left:20px; width:80px;" type="text" name="per_qty'+place+'" patelUnPrc="'+place+'" class=" common-validation" onblur="PercentQty(event,\'per_qty\',\''+place+'\');"  value="'+per+'" data-valid="mand"></center></td>'+
																	'<td><center>	<input style="margin-left:10px; width:80px;" type="text" name="split_qty'+place+'" patelUnPrc="'+place+'" class=" common-validation" onblur="calculateTotqty(\'split_qty\',\''+place+'\');"  value="'+qty+'" data-valid="mand"></center></td>'+
																	
																	 '<td><center><input style=" margin-left:10px;width:80px;" type="text" name="nevitem_prc_prc'+place+'"  readonly   value="'+parseFloat(params4.un_prc).toFixed(2)+'" ></center>'+
																	'<td><center><input style=" margin-left:10px;width:80px;" type="text" name="nev_prc'+place+'" onblur="addInTotalerror(\'nev_prc\',\''+place+'\');" patelUnPrc="'+place+'" class=" common-validation"  value="0" data-valid="mand"></center>'+
																	'</td> </tr></table></td>'+
																	'<input type="hidden" name="id_ven_selected'+place+'" value="'+params4.id_ven+'">'+
																'<input type="hidden" name="id_quot_asset'+place+'"  value="'+params4.id_quot_asst+'">'+
																'<input type="hidden" name="id_prod'+place+'"  value="'+params4.id_prod+'">'+
																'<input type="hidden" name="qtysp'+place+'"  value="'+params4.qty+'">'+
																
																	//'<input type="hidden" name="count" value="'+place+'">';
																	'<input type="hidden" name="place"  value="'+place+'">';
															
																	place+=1;
															
													}
										        	venshowlist=params4.nm_ven;
										        	pr+=tx;
										        //	console.log(tx);
										        //	console.log(pr);
										        	}
										        	qt++;
										        	pr=zx;
												zx++;
													
															/*'<td>'+
																'<select style="margin-left:10px" name="id_ven_selected'+i+'" patelUnPrc="'+i+'" id="acceptQuotNoID" class="removeErrorClass common-validation" data-valid="mand">'+
																	''+tempOptionVal+''+
																'</select>'+
															'</td>'+*/
															venSelectionList=venSelectionList+	'<td>'+
															'<input type="hidden" name="countcheck'+i+'" patelUnPrc="'+i+'" value="'+m+'">'+
																'<input style="margin-left:10px" type="text" name="ven_select_remrk'+i+'" patelUnPrc="'+i+'" class="removeErrorClass common-validation" id="dtApprovQuotRemarks" value="">'+
															'</td>'+
															//'<td><strong><center><input type="checkbox" name="createLineIndent" class="prRequestItemForVendorSelectAll" patelUnPrc="'+i+'" id_quot='+params.id_quot+' value="'+params.id_quot_asst+'"/></center></strong></td>'+

														'</tr>';
													 }
									t=i+1;
								temp = temp + '<tr class="success">'+
								'<td>'+params1.nm_model+'</td>'+
								'<td>'+params1.cd_model+'</td>'+
								'<td>'+params1.qty+'</td>'+
								'<td>'+params1.un_prc+'</td>'+
								'<td>'+params1.others+'</td>'+
								'<td>'+params1.nm_tax1+'</td>'+
								'<td>'+params1.nm_tax2+'</td>'+
								'<td>'+params1.tax_val1+'</td>'+
								'<td>'+params1.tax_val2+'</td>'+
								'<td>'+params1.buyback+'</td>'+
								'<td style="font-size: initial;" >'+params1.tot_prc+'</td>'+
															
													'</tr>';
									}
							}
							 list += temp+'<tr  style="background-color: cadetblue;color: white;"><td colspan="10"></td>'+
								'<td ><strong style="font-size: initial;">'+params.tot+'</strong></td>'+
								'</tr>';
							
						}

						/* list += '<tr>'+
						 '<input type="hidden" name="no_ind" value="'+no_ind+'">'+
						 '<input type="hidden" name="no_ven_cont"  value="'+no_ven+'">'+
							'<td colspan="11" style="background-color: cadetblue;color: white;"><p style="margin-left: 285px;font-size: 20px;">Finalize your vendor per item</p></td>'+
							'</tr>'+
							'<tr>'+
							'<td colspan="11"><table style="width: 100%;">'+
							    venSelectionList+
							'</table></td>'+
						'</tr>'+
						'<tr>'+
						'<td colspan="11"><b style="margin-left:10px">Vendor Finalization Date <font color=red>*</font> :</b>'+
							'<input style="margin-left:10px" id="dtApprovQuot" type="text" name="dt_approv" class="datepicker removeErrorClass">'+
							quotationId+
							/*'<input type="hidden" name="status_approv" value="Accepted">'+
							'<select style="display:none;" name="acceptQuotNo" id="" class="">'+
								'<option value="">Select</option>'+
								''+quotationId+''+
							'</select>'+*/
							/*'<strong style="margin-left:10px" >Rate Contract<font color="red"> * </font>:</strong>'+
							'<select name="rate_cont" id="rate_cont" class="common-validation" data-valid="mand" onchange="RateContract();">'+
							'<option value="">Select</option><option value="No"> No </option><option value="Yes"> Yes </option></select>'+
							'<span id="blank" style="display:none;margin-left:10px"><strong>Rate Contract Validity <font color="red"> * </font>:</strong>'+
							'<input type="text" name="dt_rate_cont_valid"  id="dt_rate_cont_valid"  value="" class="common-validation datepicker" ></span>'+
					'</td>'+
						
					'</tr>'+*/
					list +='<tr>'+
					'<td id="blankpo" colspan="11" style="display:none;margin-left:10px"><b style="margin-left:10px">You have selected rate contract. Do you want to raise po?</b>'+
					'<select name="st_rate_cont_for_po" id="st_rate_cont_for_po" class="common-validation" data-valid="mand">'+
					
					'<option value="Yes"> Yes </option><option value="No"> No </option></select>'+
					'</td>'+
			
					'</tr>'+	
							'<tr>'+
							'<td colspan="11">'+
								'<center><button type="button" style="margin-left: 200px;" class="btn btn-primary appq" onclick="ControlFinalizeVendorDiveval(\'Accepted\',\'displayApproveQuotation\',\'createApproveQuotation\')">Send For Financial Approval</button></center>'+
//								'<button type="button" class="btn btn-primary appq" style="margin-left: 400px;" onclick="ControlAccepRejectQuotationDiv(\'Rejected\',\'displayApproveQuotation\',\'createApproveQuotation\')">Reject</button></td>'+
							
								'</td>'+
							
						'</tr>';
						 
						$('.QuotationDetailsForApproval').html(list);	
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();
						
					}
				else
					{
						bootbox.alert("Something went wrong please try again.");
					}
				
				$( ".datepicker" ).datepicker({
					yearRange: '1985:2025',
				      changeMonth: true,
				      changeYear: true,
				      dateFormat: "dd/mm/yy",
				      autoSize: true,
				      altFormat: "dd/mm/yy",
				      
				    });
				
				var currentDate = new Date();
				$('input[name="dt_approv"]').datepicker("setDate", currentDate);
				
		},'json');
			}});
}


function DisplayFinalizeVendoreval()
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Finalize_Vendor',{action : 'Displayevaluation',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 400px;">Negotiate Vendor</p></td>'+
			'</tr>'+
				'<tr class="tableHeader info">'+
			'<td><strong>RFQ Number<a href=#></a></strong></td>'+
			'<td><strong>RFQ Date</strong></td>'+
			'<td><strong>RFQ By</strong></td>'+
			'<td style="width: 300px;"><center><strong><a href="#">Negotiate Vendor</a></strong> </center></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td>'+params.no_ind+'</td>'+
									'<td>'+params.dt_ind+'</td>'+
									'<td>'+params.nm_emp+'</td>'+
									'<td style="width:300px;"><center><strong>'+
									'<a class="alert" href="#" onclick="ControlFinalizeVendorDiveval(\'split\',\'createApproveQuotation\',\'displayApproveQuotation\',\''+params.no_ind+'\')"> Negotiate Vendor </a></strong></center>'+
									
									
									'</td>'+
									'</tr>';
				}
			
				$('.approvequotatioForDisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="4"><strong>No record(s) is available.</strong></td></tr>';
				$('.approvequotatioForDisplay').html(list);
			}
		
		
	},'json');

			}});
}
function EditFinalizeVendorFunsplit(action , DisplayDiv , HideDiv , no_ind)
{

	SessionCheck(function (ses){		
		if(ses)
			{
		$.post('Finalize_Vendor',{action : 'Edit' , no_ind : no_ind},function (r){
			var list='', temp='',temp_quot="";
			var negodisable = new Array();
			
			 var prit=0;
				if(r.data.length > 0)
					{
					
					var id_quotArr = new Array();
					for(var i = 0; i < r.data.length ; i++)
					{
					if(id_quotArr.length === 0)
						{
						id_quotArr.push(r.data[i].id_quot);
						}
					else
						{
						if($.inArray(r.data[i].id_quot, id_quotArr) > -1){
							
						}
						else
							{
							id_quotArr.push(r.data[i].id_quot);
							}
						}
					}
					var t=0,m=0;
					var tempOptionVal='',venSelectionList='',quotationId='';
					
					for(var j = 0; j < id_quotArr.length ; j++)
					{
						
						params=r.data[(m)];
						var tempQuot = params.id_quot;
						
						quotationId +='<input type="hidden" name="quotationId" value="'+tempQuot+'">';
						
						tempOptionVal +='<option value="'+params.id_ven+'">'+params.nm_ven+'-'+params.no_quot+'</option>';
						for(var i = 0; i < r.data.length ; i++)
						{
							
							 
							params1 = r.data[i];
							if(params1.id_quot == tempQuot)
							{
								m=i+1;
							}
						}
					}
					
						for(var j = 0; j < id_quotArr.length ; j++)
						{
							params=r.data[(t)];
							
							
							 
							if(params.st_negotiation=='1'){
								negodisable.push(params.id_quot)
							}
							
							var tempQuot = params.id_quot;
							//tempOptionVal +='<option value="'+tempQuot+'">'+params.nm_ven+'-'+params.no_quot+'</option>';
							
							 temp='<tr style="background-color: cadetblue;color: white;border-bottom-style: hidden;">'+
							 '<input type="hidden" name="total_ven" class="total_ven" value="'+params.id_ven+'">'+
								'<input type="hidden" name="total_amount" class="total_amount" value="'+params.tot+'">'+
								
							 '<td colspan="6" style="border-right-style: hidden;"><strong style="border-bottom-style: hidden;">Vendor Name :</strong> <b>'+params.nm_ven+'</b></td>'+
							'<td colspan="4" style="border-right-style: hidden;"><strong style="border-bottom-style: hidden;">Vendor Code :</strong> <b>'+params.cd_ven+'</b></td>'+
							'<td >'+
								'<button type="button" style="float:right;" class="btn btn-primary appq" id="nego'+params.id_quot+'" onclick="Negotiation(\'Negotiation\','+params.id_ven+',\''+params.id_quot+'\',\''+params.no_quot+'\')">Negotiate</button></center>'+
							
								'</td>'+
						'</tr>'+
						'<tr style="background-color: cadetblue;color: white;">'+
							'<td colspan="6" style="border-right-style: hidden;"><strong style="">Quotation No  :</strong><b>'+params.no_quot+'</b></td>'+
							'<td colspan="5" style="border-right-style: hidden;"><strong style="">Quotation Date :</strong><b>'+params.dt_reqqt+'</b></td>'+
						'</tr>'+
						'<tr class="tableHeader info">'+
						'<td style="min-width:200px;"><strong><center>Model Name </strong></center></td>'+
						'<td><center><strong>Model Code</strong></center></td>'+
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
							
							 if(j==0){
				 venSelectionList='<tr class="tableHeader info" style="display:none">'+
				 '<input type="hidden" name="addven" id="addven" value="">'+
				 '<td style="min-width:200px;"><strong><center>Model Name </strong></center></td>'+
					'<td><center><strong>Model Code</strong></center></td>'+
				 		'<td><strong>Finalize Vendor<font color=red>*</font> </strong><a href="#"><span style="margin-left:20px;font-size:15px;display:none" id="all_ven"onclick="changevenforall()">Select Vendor For All</span></a></td>'+
									'<td><strong>Selection Remarks</strong></td>'+
									//'<td style="width:120px;"><strong><center><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'prRequestItemForVendorSelectAll\')"> Check All </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'prRequestItemForVendorSelectAll\')"> Uncheck All</a></center></strong></td>'+
									
								'</tr>';
							 }
							
							for(var i = 0; i < r.data.length ; i++)
							{
								
								 
								params1 = r.data[i];
								if(params1.id_quot == tempQuot)
								{
									if(j==0){
										 venSelectionList +='<tr class="success">'+
										 				'<input type="hidden" name="count" value="'+i+'">'+
										 				'<input type="hidden" name="id_prod'+i+'" value="'+params1.id_prod+'">'+
														 
														'<input type="hidden" name="id_quot_asst'+i+'" value="'+params1.id_quot_asst+'">'+
														 '<td>'+params1.nm_model+'</td>'+
														 '<td>'+params1.cd_model+'</td>'+
															'<td>'+
																'<select style="margin-left:10px" name="id_ven_selected'+i+'" patelUnPrc="'+i+'" id="acceptQuotNoID"   onchange="selectallvendor(this);" class="removeErrorClass common-validation" data-valid="mand">'+
																	''+tempOptionVal+''+
																'</select>'+
															'</td>'+
															'<td>'+
																'<input style="margin-left:10px" type="text" name="ven_select_remrk'+i+'" patelUnPrc="'+i+'" class="removeErrorClass common-validation" id="dtApprovQuotRemarks" value="">'+
															'</td>'+
															//'<td><strong><center><input type="checkbox" name="createLineIndent" class="prRequestItemForVendorSelectAll" patelUnPrc="'+i+'" id_quot='+params.id_quot+' value="'+params.id_quot_asst+'"/></center></strong></td>'+

														'</tr>';
										 prit=prit+1; }
									t=i+1;
								temp = temp + '<tr class="success">'+
								'<input type="hidden" name="total_bid" class="total_bid"value="'+params1.tot_prc+'">'+
								
								'<input type="hidden" name="countj" value="'+j+'">'+
								'<td>'+params1.nm_model+'</td>'+
								'<td>'+params1.cd_model+'</td>'+
								'<td>'+params1.qty+'</td>'+
								'<td>'+params1.un_prc+'</td>'+
								'<td>'+params1.others+'</td>'+
								'<td>'+params1.nm_tax1+'</td>'+
								'<td>'+params1.nm_tax2+'</td>'+
								'<td>'+params1.tax_val1+'</td>'+
								'<td>'+params1.tax_val2+'</td>'+
								'<td>'+params1.buyback+'</td>'+
								'<td style="font-size: initial;" >'+params1.tot_prc+'</td>'+
													
													'</tr>';
									}
							}
							 list += temp+'<tr  style="background-color: cadetblue;color: white;">'+
								
								'<td colspan="10"><strong style="float:right;font-size: initial;">Grand Total</strong></td>'+
								'<td><strong style="font-size: initial;">'+params.tot+'</strong></td>'+
								'</tr>'+
								'<tr><td colspan="11" style="border-left-style: hidden;border-right-style: hidden;"></td></tr>';
						}

						 list += '<tr style="display:none">'+
						 '<input type="hidden" name="no_ind" value="'+no_ind+'">'+
							'<td colspan="11" style="background-color: cadetblue;color: white;"><p style="margin-left: 285px;font-size: 20px;">Finalize your vendor per item'+
							
							'<strong style="margin-left:100px;color:blue" >Finalize Vendor<font color="red">  </font></strong>'+
							'<select name="fin_ven" id="fin_ven" class="common-validation" onchange="FinalizeVendor();">'+
							'<option value=""> Select</option><option value="Item_wise"> Model Wise </option><option value="tot_wise"> Total Wise </option></select>'+
							
							'</p></td>'+
							'</tr>'+
							'<tr style="display:none">'+
							'<td colspan="11"><table style="width: 100%;">'+
							    venSelectionList+
							'</table></td>'+
						'</tr>'+
						'<tr style="display:none">'+
						'<td colspan="11"><b style="margin-left:10px">Vendor Finalization Date <font color=red>*</font> :</b>'+
							'<input style="margin-left:10px" id="dtApprovQuot" type="text" name="dt_approv" class="datepicker removeErrorClass">'+
							'<input type="hidden" name="countven" value="'+prit+'">'+
				 			
							quotationId+
						/*	'<strong style="margin-left:10px" >Rate Contract<font color="red"> * </font>:</strong>'+
							'<select name="rate_cont" id="rate_cont" class="common-validation" data-valid="mand" onchange="RateContract();">'+
							'<option value="">Select</option><option value="No"> No </option><option value="Yes"> Yes </option></select>'+
							'<span id="blank" style="display:none;margin-left:10px"><strong>Rate Contract Validity <font color="red"> * </font>:</strong>'+
							'<input type="text" name="dt_rate_cont_valid"  id="dt_rate_cont_valid"  value="" class="common-validation datepicker" ></span>'+
						*/
							'</td>'+
						'</tr>'+
							
					'<tr>'+
					'<td id="blankpo" colspan="11" style="display:none;margin-left:10px"><b style="margin-left:10px">You have selected rate contract. Do you want to raise po?</b>'+
					'<select name="st_rate_cont_for_po" id="st_rate_cont_for_po" class="common-validation" data-valid="mand">'+
					
					'<option value="Yes"> Yes </option><option value="No"> No </option></select>'+
					'</td>'+
			
					'</tr>'+
					'<tr>'+
							'<td colspan="10">'+
								'<center><button type="button" style="margin-left: 200px;" class="btn btn-primary appq" onclick="ControlFinalizeVendorDiveval(\'approvalrazorpay\',\'displayApproveQuotation\',\'createApproveQuotation\')">Send For Approval</button></center>'+
//								'<button type="button" class="btn btn-primary appq" style="margin-left: 400px;" onclick="ControlAccepRejectQuotationDiv(\'Rejected\',\'displayApproveQuotation\',\'createApproveQuotation\')">Reject</button></td>'+
							
								'</td>'+
							
						'</tr>';
						 
						$('.QuotationDetailsForApproval').html(list);	
						$('input[name="action"]').val('approvalrazorpay');
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();
						
						
						for(var l = 0; l < negodisable.length ; l++)
						{
							$('#nego'+negodisable[l]+'').attr('disabled','disabled');
							
							}
					}
				else
					{
						bootbox.alert("Something went wrong please try again.");
					}
				
				$( ".datepicker" ).datepicker({
					yearRange: '1985:2025',
				      changeMonth: true,
				      changeYear: true,
				      dateFormat: "dd/mm/yy",
				      autoSize: true,
				      altFormat: "dd/mm/yy",
				      
				    });
				
				var currentDate = new Date();
				$('input[name="dt_approv"]').datepicker("setDate", currentDate);
				
		},'json');
			}});
}
    
function Negotiation(action,id_ven,id_quot,no_quot){
		$('#nego'+id_quot+'').attr('disabled','disabled');
		
		 $.post('Finalize_Vendor',{action : 'Negotiation', id_ven:id_ven, id_quot:id_quot,no_quot:no_quot},function (r){
			
				
					if(r.data=='1'){
						bootbox.alert("Email Has been sent to vendor");
						$('#nego'+id_quot+'').attr('disabled','disabled');
		
					}
					else{
						bootbox.alert("Try Again");
						 
					}
					
				
		 },'json'); 
		 
		 
		 
		
	}

function selectallvendor(venvalue){
	
	var val=venvalue.value;
	var count =$('input[name="count"]').val();
	$('#all_ven').show();
	$('#addven').val(val);
	
	
}
function changevenforall(){
	
	var count =$('input[name="countven"]').val();
	var addven =$('input[name="addven"]').val();
	for(var i=0;i<parseFloat(count);i++){
		$('select[name=id_ven_selected'+i+'] option[value=' + addven + ']').attr('selected',true);
		
	}	
	
}
function FinalizeVendor(){
	var val_fin= $('#fin_ven').val();
	

	var count =$('input[name="countj"]').val();
	var i="0";
	var total_bid="0";
	var total_ven="0";
	var total_bidsplit="";
	var total_vensplit="";
	var total_amount="0";
	var total_vensplitamount="";
	
	$('.total_bid').each(function(){
		
		if(total_bid == '0')
			total_bid = $(this).val();
			else
				total_bid +='pks'+ $(this).val();
	});
$('.total_ven').each(function(){
		
		if(total_ven == '0')
			total_ven = $(this).val();
			else
				total_ven +='pks'+ $(this).val();
	});
$('.total_amount').each(function(){
		
		if(total_amount == '0')
			total_amount = $(this).val();
			else
				total_amount +='pks'+ $(this).val();
	});
	
total_bidsplit=total_bid.split('pks');
total_vensplit=total_ven.split('pks');
total_vensplitamount=total_amount.split('pks');
var no_ven_item=total_bidsplit.length/total_vensplit.length;
var k=0;
	if(val_fin=='Item_wise'){
	
	
	
	for(var j=0 ;j<no_ven_item;j++){
		 k=j;
		 var arr1=[],arr2=[];
		for(var t=0 ;t<total_vensplit.length;t++){
			arr1.push(parseFloat(total_bidsplit[k]));
			arr2.push(parseFloat(total_bidsplit[k]));
			k=k+no_ven_item;
		}
		//console.log(arr1);
		var val= arr1.sort(function(a,b) { return a-b; });
		
	
		console.log(val);
		//var val=Math.min.apply(Math, arr1);
		var c=0;
		    for (var w = 0; w < arr2.length; w++) {
		    	if(val[c] != 0){
		        if (arr2[w] == val[c]   ) {
		        	//console.log(w);
		        	$('select[name=id_ven_selected'+j+']:eq(0)').val(total_vensplit[w]);
		    		//$('select[name=id_ven_selected'+j+'] option[value=' + total_vensplit[w] + ']').attr('selected',true);
		    		
		        }
		    	}
		    	else{
		    		c=c+1;
		    		w=0;
		    	}
		    }
	}
	
	}
	if(val_fin=='tot_wise'){
		for(var j=0 ;j<no_ven_item;j++){
			var val=Math.min.apply(Math, total_vensplitamount);
			console.log(total_amount);
			console.log(total_vensplitamount);
			
			 for (var w = 0; w < total_vensplitamount.length; w++) {
			        if (total_vensplitamount[w] == val) {
			        	console.log(w);
			        	$('select[name=id_ven_selected'+j+']:eq(0)').val(total_vensplit[w]);
			    		//$('select[name=id_ven_selected'+j+'] option[value=' + total_vensplit[w] + ']').attr('selected',true);
			    		
			        }
			    }
			
		}
	}
}
/*
$( ".approvQuotDatepicker" ).datepicker({
	yearRange: '1985:2025',
      changeMonth: true,
      changeYear: true,
      dateFormat: "yy-mm-dd",
      autoSize: true,
      altFormat: "DD-MMM-YYYY",
      onSelect: function(selected,evnt) {
    	  $(this).removeClass('error');
    	  var dt_recv=$(this).val();
    	  var id_quot=$('input[name="id_quot"]').val();
    	  
    	  $.post('Finalize_Vendor',{action : 'CheckDate' ,dt_recv:dt_recv,id_quot:id_quot},function (r){
	    		if(r.data == 0)
	    			{
	    			alert('Approval/Reject date should be greater or equal to receive quotation date : '+r.dt_rec_quot);
	    			$(".approvQuotDatepicker").focus();
	    			$(".approvQuotDatepicker").val('');
	    			$(".approvQuotDatepicker").addClass('error');
	    				exit(0);
	    			}
	    		
	    },'json');
    	  
    	  
      }
    });

*/