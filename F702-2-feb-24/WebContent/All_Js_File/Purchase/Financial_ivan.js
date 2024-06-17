
function ControlFinalizeVendorFinanciaIvan(action , DisplayDiv , HideDiv , no_ind,id_quot,no_quot)
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
			EditFinalizeVendorFun(action , DisplayDiv , HideDiv , no_ind,id_quot,no_quot);
			
		}
	else if(action =='split')
	{
		EditFinalizeVendorFunsplit(action , DisplayDiv , HideDiv , no_ind,id_quot,no_quot);
		
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
	else if(action =='Approvefinance' || action =='Rejectfinance' || action =='Approveivan' || action =='Rejectivan' )
	{
		 
	/* bootbox.confirm("Are you sure ?", function(result) {
		if(result)
		{*/
			$('input[name="typeApprovalandreject"]').val(action);
 			var x = $('#submitApproveQuotation').serialize();
 			$.post('Finalize_Vendor',x,function (r){
 						if(r.data == 1)
 							{
	//alert('Approved.');
 							//	DisplayFinalizeVendor();
 								//bootbox.alert("Quotation has been sent for financial approval.");
 								$( ".financialivan " ).trigger( "click" );
 							}
 						else
 						{
 							bootbox.alert("Something went wrong Please try again.");
 						}
 						$('.appq').removeAttr('disabled');
 					},'json');
		 
 
	
	
	}
	
			}});
}



 


function DisplayFinalizeVendorivan()
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Finalize_Vendor',{action : 'DisplayIvanApproval',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 400px;">Waiting for Approval </p></td>'+
			'</tr>'+
				'<tr class="tableHeader info">'+
			'<td><strong>RFQ Number<a href=#></a></strong></td>'+
			'<td><strong>RFQ Date</strong></td>'+
			'<td><strong>RFQ By</strong></td>'+
			'<td style="width: 300px;"><center><strong><a href="#">Preview</a></strong> </center></td>'+
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
									'<a class="alert" href="#" onclick="ControlFinalizeVendorFinanciaIvan(\'split\',\'createApproveQuotation\',\'displayApproveQuotation\',\''+params.no_ind+'\',\''+params.id_quot+'\',\''+params.no_quot+'\')">Preview</a></strong></center>'+
									
									
									'</td>'+
									'</tr>';
				}
			
				$('.approvequotatioForDisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="4"><strong>Data Not Found.</strong></td></tr>';
				$('.approvequotatioForDisplay').html(list);
			}
		
		
	},'json');

			}});
}

function EditFinalizeVendorFunsplit(action , DisplayDiv , HideDiv , no_ind,id_quot,no_quot)
{

	SessionCheck(function (ses){		
		if(ses)
			{
		$.post('Vendor_Selection',{action : 'Edit' , no_quot : no_quot,no_ind:no_ind,id_quot:id_quot},function (r){
			var list='', temp='',rejectedVen='';
		var splitquot= '';
				if(r.data.length > 0)
					{
						
						var arr1=[], arr2=[];
	for(var x=0;x< r.bidlw.length;x++)
		{
			arr1.push(parseFloat(r.bidlw[x].tot));
			arr2.push(parseFloat(r.bidlw[x].score));
		
		
	}
	console.log(x);
	
	var valtech= arr2.sort(function(a,b) { return b-a; });
	var val= arr1.sort(function(a,b) { return a-b; });
	console.log(val);
	console.log(valtech);
						
						
						temp='<tr style="background-color: cadetblue;color: white;">'+
						'<td colspan="9"><strong style="font-size: large;margin-left: 300px;">Purchase Request Cum Vendor Selection Summary</strong></td>'+
							
						'</tr>'+
						'<input type="hidden" name="no_ind" value="'+no_ind+'">'+
								
						'<tr class="tableHeader info">'+
							'<td><strong><center>Model Name</center></strong></td>'+
							'<td><strong><center>Model Code</center></strong></td>'+
							//'<td><strong><center>Description</center></strong></td>'+
							'<td><strong><center>Quantity</center></strong></td>'+
							'<td><strong><center>Unit Price</center></strong></td>'+
							'<td><strong><center>Tax1 Name</center></strong></td>'+
							'<td><strong><center>Tax1 Value</center></strong></td>'+
							'<td><strong><center>Tax2 Name</center></strong></td>'+
							'<td><strong><center>Tax2 Value</center></strong></td>'+
							'<td style="width: 200px;"><strong><center>Total Price</center></strong></td>'+
						'</tr>';
							
							for(var i = 0; i < r.data.length ; i++)
							{
								
								params = r.data[i];
							
								temp += '<tr class="success">'+
													'<td><center>'+params.nm_model+'</center></td>'+
													'<td><center>'+params.cd_model+'</center></td>'+
													//'<td><center>'+params.description+'</center></td>'+
													'<td><center>'+params.qty+'</center></td>'+
													'<td><center>'+params.un_prc+'</center></td>'+
													'<td><center>'+params.nm_tax1+'</center></td>'+
													'<td><center>'+params.tax_val1+'</center></td>'+
													'<td><center>'+params.nm_tax2+'</center></td>'+
													'<td><center>'+params.tax_val2+'</center></td>'+
													'<td><center>'+params.tot_prc+'</center></td>'+
													'</tr>';
							}
							
							for(var j=0;j < r.RejectedVen.length;j++)
								{
								rejectedVen += '<tr class="success"><td><b>'+r.RejectedVen[j].nm_ven+'</b></td>'+
								'<td><b>'+r.RejectedVen[j].tot+' '+r.RejectedVen[j].nm_curr+'</b></td>'+
								'<input type="hidden" name="reject_id_ven" value="'+r.RejectedVen[j].id_ven+'">'+
								'<input type="hidden" name="final_bid" value="'+r.RejectedVen[j].tot+'">'+
								'<input type="hidden" name="reject_no_quot" value="'+r.RejectedVen[j].no_quot+'">'+
								
								'<td><b>Not Short List</b></td></tr>';
								
								}
							checked1='';
							checked='checked';
							lowest_bid='checked';
							lowest_bid1='';
							recurring_order='';
							compt_bid='';
							other='-';
							other_specify='';
							if(r.data[0].bidding==1) checked='checked';else if(r.data[0].bidding==0) checked1='checked';
							if(r.data[0].lowest_bid==1) lowest_bid='checked';else if(r.data[0].lowest_bid==0) lowest_bid1='checked';
							if(r.data[0].recurring_order==1) recurring_order='checked';
							if(r.data[0].compt_bid==1) compt_bid='checked';
							if(r.data[0].other==1) other='checked';
							if(r.data[0].other_specify != undefined) other_specify=r.data[0].other_specify;
							
							 list = temp+
							 '<tr>'+
							 '<input type="hidden" name="id_ven_select1" value="'+r.id_ven_select+'">'+
								'<td colspan="8" style="background-color: cadetblue;color: white;"><strong style="float:right;">Approximate Value of order </strong></td>'+
								'<td style="background-color: cadetblue;color: white;"><strong >'+params.tot+'</strong></td>'+
							'</tr>'+
								
								 
							 '<tr>'+
							 '<td colspan="8" style="border-top-style: hidden;">'+
								 '<table align="center" class="table table-bordered" style="width:70%">'+
									 '<tr style="background-color: cadetblue;color: white;">'+
											'<td><b>Vendor Name</b></td>'+
											'<td><b>Final bid</b></td>'+
											'<td style="width: 130px;"><b>Vendor Selected</b></td>'+
											'<td style="width: 130px;"><b>Technicals</b></td>'+
										'</tr>';
										var tot=0.0;
										var lwb=0;
										var cnt=0;
										var score=0;
										var lwbtech=0;
										var cnttech=0;
										for(var t=0;t< r.bidlw.length;t++)
								{
									//
									console.log("hello");
									 var index=val.indexOf(parseFloat(r.bidlw[t].tot));
								if(tot !=parseFloat(r.bidlw[t].tot)){
									lwb=parseInt(index)+1-cnt
									console.log(parseInt(index)+1-cnt);
								}
								else{
									
									cnt++;
								}
								
								 var indextecdh=valtech.indexOf(parseFloat(r.bidlw[t].score));
								if(score !=parseFloat(r.bidlw[t].score)){
									lwbtech=parseInt(indextecdh)+1-cnttech
									console.log(parseInt(indextecdh)+1-cnttech);
								}
								else{
									
									cnttech++;
								}
								
								score=parseFloat(r.bidlw[t].score);
								
								tot=parseFloat(r.bidlw[t].tot);
								if(splitquot==''){
									splitquot=splitquot+r.bidlw[t].id_quot;
									
								}
								else{
									
									splitquot=splitquot+','+r.bidlw[t].id_quot;
								}
								
									 
										list +='<tr class="info">'+
											'<td><b>'+r.bidlw[t].nm_ven+'</b></td>'+
											'<td><b>'+r.bidlw[t].tot+'</b></td>'+
											'<td><b>L'+lwb+'</b></td>'+
											'<td><b>T'+lwbtech+'  --- '+score+'</b></td>'+
											
										'</tr>';
										
									}	
										
								list +='</td>'+
								'<input type="hidden" name="tot" value="'+r.data[0].id_ven+'">'+
											'<input type="hidden" name="id_ven" value="'+r.data[0].id_ven+'">'+
											'<input type="hidden" name="id_quot" value="'+r.data[0].id_quot+'">'+
											'<input type="hidden" name="no_quot" value="'+r.data[0].no_quot+'">'+
									'</table>'+
							
					'<tr>'+
							'<td colspan="11" id="FINANCE">'+
								'<center><button type="button"   style="margin-left: 20px;" class="btn btn-primary appq" onclick="ControlFinalizeVendorFinanciaIvan(\'Approvefinance\',\'displayApproveQuotation\',\'Approve\')">Approve</button>'+
								'<button type="button" class="btn btn-primary "   style="margin-left: 10px;" onclick="ControlFinalizeVendorFinanciaIvan(\'Rejectfinance\',\'displayApproveQuotation\',\'createApproveQuotation\')">Reject</button></td></center>'+
							   
								
								
								'<td colspan="11" id="FINANCECONTROLER">'+
							    '<center><button type="button" style="margin-left: 60px;"   class="btn btn-primary appq" onclick="ControlFinalizeVendorFinanciaIvan(\'Approveivan\',\'displayApproveQuotation\',\'Approve\')">Approve</button>'+
								'<button type="button" class="btn btn-primary "   style="margin-left: 10px;" onclick="ControlFinalizeVendorFinanciaIvan(\'Rejectivan\',\'displayApproveQuotation\',\'createApproveQuotation\')">Reject</button></td></center>'+
							
							
							'</td>'+
						'</tr>';
						 $('#myBtn').show();
						$('.QuotationDetailsForApproval').html(list);	
						$('input[name="action"]').val('approvalFinancialIvan');
						$('input[name="datformodel"]').val(splitquot);
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();
					var usertype=	$('input[name="usertype"]').val();
					
					if(usertype=='FINANCE'){
						$('#FINANCE').show();
						$('#FINANCECONTROLER').hide();
					}
					
					if(usertype=='FINANCECONTROLER'){
						$('#FINANCE').hide();
						$('#FINANCECONTROLER').show();
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
/*
function EditFinalizeVendorFunsplit(action , DisplayDiv , HideDiv , no_ind)
{

	SessionCheck(function (ses){		
		if(ses)
			{
		$.post('Finalize_Vendor',{action : 'EditIvan' , no_ind : no_ind},function (r){
			var list='', temp='',temp_quot="";
			var negodisable = new Array();
			var idquotarr = new Array();
			
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
					
					var splitquot='';
						for(var j = 0; j < id_quotArr.length ; j++)
						{
							
							if(splitquot==''){
								splitquot=splitquot+id_quotArr[j];
							}
							else{
								splitquot=splitquot+','+id_quotArr[j];
							}
							console.log(splitquot);
							
							params=r.data[(t)];
							
							var ivan_approv_reject='',finance_approv_reject='';
							 
							if(params.st_negotiation=='1'){
								negodisable.push(params.id_quot)
							}
							
							if(params.ivan_approv_reject=='Accept'|| params.ivan_approv_reject=='Reject') {
								ivan_approv_reject='disabled';
							}
							
							if(params.finance_approv_reject=='Accept'  || params.finance_approv_reject=='Reject' ){
								finance_approv_reject='disabled';
							}
							var tempQuot = params.id_quot;
							//tempOptionVal +='<option value="'+tempQuot+'">'+params.nm_ven+'-'+params.no_quot+'</option>';
						
							 temp='<tr style="background-color: cadetblue;color: white;border-bottom-style: hidden;">'+
							 '<input type="hidden" name="total_ven" class="total_ven" value="'+params.id_ven+'">'+
								'<input type="hidden" name="total_amount" class="total_amount" value="'+params.tot+'">'+
								
							 '<td colspan="6" style="border-right-style: hidden;"><strong style="border-bottom-style: hidden;">Vendor Name :</strong> <b>'+params.nm_ven+'</b></td>'+
							'<td colspan="5" style="border-right-style: hidden;"><strong style="border-bottom-style: hidden;">Vendor Code :</strong> <b>'+params.cd_ven+'</b></td>'+
							
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
						 
							'</td>'+
						'</tr>'+
							
					'<tr>'+
					'<td id="blankpo" colspan="11" style="display:none;margin-left:10px"><b style="margin-left:10px">You have selected rate contract. Do you want to raise po?</b>'+
					'<select name="st_rate_cont_for_po" id="st_rate_cont_for_po" class="common-validation" data-valid="mand">'+
					
					'<option value="Yes"> Yes </option><option value="No"> No </option></select>'+
					'</td>'+
			
					'</tr>'+
					
					'<tr>'+
							'<td colspan="11" id="FINANCE">'+
								'<center><button type="button" '+finance_approv_reject+' style="margin-left: 20px;" class="btn btn-primary appq" onclick="ControlFinalizeVendorFinanciaIvan(\'Approvefinance\',\'displayApproveQuotation\',\'Approve\')">Approve</button>'+
								'<button type="button" class="btn btn-primary " '+finance_approv_reject+' style="margin-left: 10px;" onclick="ControlFinalizeVendorFinanciaIvan(\'Rejectfinance\',\'displayApproveQuotation\',\'createApproveQuotation\')">Reject</button></td></center>'+
							   
								
								
								'<td colspan="11" id="FINANCECONTROLER">'+
							    '<center><button type="button" style="margin-left: 60px;" '+ivan_approv_reject+'  class="btn btn-primary appq" onclick="ControlFinalizeVendorFinanciaIvan(\'Approveivan\',\'displayApproveQuotation\',\'Approve\')">Approve</button>'+
								'<button type="button" class="btn btn-primary " '+ivan_approv_reject+' style="margin-left: 10px;" onclick="ControlFinalizeVendorFinanciaIvan(\'Rejectivan\',\'displayApproveQuotation\',\'createApproveQuotation\')">Reject</button></td></center>'+
							
							
							'</td>'+
						'</tr>';
						 $('#myBtn').show();
						$('.QuotationDetailsForApproval').html(list);	
						$('input[name="action"]').val('approvalFinancialIvan');
						$('input[name="datformodel"]').val(splitquot);
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();
					var usertype=	$('input[name="usertype"]').val();
					
					if(usertype=='FINANCE'){
						$('#FINANCE').show();
						$('#FINANCECONTROLER').hide();
					}
					
					if(usertype=='FINANCECONTROLER'){
						$('#FINANCE').hide();
						$('#FINANCECONTROLER').show();
					}
						
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
}*/

function GetNegotiationDetails(){
	
	var id_quot_arr=$('input[name="datformodel"]').val();
	console.log(id_quot_arr);
	 $.post('Finalize_Vendor',{action : 'GetNegotiationDetails', idquotarr:id_quot_arr},function (r){
			
				if(r.data.length > 0)
					{
						console.log(r.data);
						var temp='';
						var id_quot='';
						for(var j = 0; j < r.data.length ; j++)
						{
							params1=r.data[j];
							if(id_quot!=r.data[j].no_quot){
							 temp=temp+'<tr style="background-color: cadetblue;color: white;border-bottom-style: hidden;">'+
							 	
							 '<td colspan="6" style="border-right-style: hidden;"><strong style="border-bottom-style: hidden;">Vendor Name :</strong> <b>'+params1.nm_ven+'</b></td>'+
							'<td colspan="7" style="border-right-style: hidden;"><strong style="border-bottom-style: hidden;">Vendor Code:</strong> <b>'+params1.cd_ven+'</b></td>'+
							
						'</tr>'+
						
						'<tr style="background-color: cadetblue;color: white;">'+
							'<td colspan="6" style="border-right-style: hidden;"><strong style="">Quotation No  :</strong><b>'+params1.no_quot+'</b></td>'+
							'<td colspan="7" style="border-right-style: hidden;"><strong style="">Quotation Date :</strong><b>'+params1.dt_quot+'</b></td>'+
						'</tr>'+
						'<tr class="tableHeader info">'+
						'<td style="min-width:100px;"><strong><center>Negotiation BY </strong></center></td>'+
						'<td style="min-width:100px;"><strong><center>Negotiation No  </strong></center></td>'+
						'<td style="min-width:80px;"><strong><center>Negotiation Date  </strong></center></td>'+
					
						'<td style="min-width:200px;"><strong><center>Model Name </strong></center></td>'+
						'<td ><strong><center>Quantity<a href=#></a></center></strong></td>'+
						'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
						/*'<td ><strong><center>Others<a href=#></a></center></strong></td>'+
						'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
						'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
						'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
						'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
						'<td ><strong><center>Discount<a href=#></a></center></strong></td>'+*/
						'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
						
						'</tr>';	
								
							}
							 
									
								
								temp = temp + '<tr class="success">'+
								'<td>'+params1.nm_emp+'</td>'+
								'<td>'+params1.no_nego_ven+'</td>'+
								'<td>'+params1.nego_date+'</td>'+
								
								'<td>'+params1.nm_model+'</td>'+
								'<td>'+params1.qty+'</td>'+
								'<td>'+params1.un_prc+'</td>'+
								/*'<td>'+params1.others+'</td>'+
								'<td>'+params1.t1taxname+'</td>'+
								'<td>'+params1.t2taxname+'</td>'+
								'<td>'+params1.tax_val1+'</td>'+
								'<td>'+params1.tax_val2+'</td>'+
								'<td>'+params1.buyback+'</td>'+*/
								'<td style="font-size: initial;" >'+params1.tot_prc+'</td>'+
													
													'</tr>';
							 
						
							
							
							
						id_quot=r.data[j].no_quot;	
							
					    
	
	                   }
						
							$('.displaynegotiationdetails').html(temp);	
						
						}
					
				
		 },'json'); 
	

	
	
	
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