
function ControlDivForVendorSelection(action,DispDiv,HideDiv)
{
if(action == 'Cancel')
	{
		$('#'+HideDiv).hide();
		$('#'+DispDiv).show();
	}
else if(action == 'Approve' || action == 'Reject')
	{
	$('.ven').attr('disabled','disabled');

    var id_quot =$('input[name="id_quot"]').val();
	var financial_rmrk =$('textarea[name="financial_rmrk"]').val();
	var x = $('#submitVendorSelection').serialize();
	$.post('FinancialApproval',{action:'Save',Approve:action,financial_rmrk:financial_rmrk,id_quot:id_quot},function (r)
		{
				
			if(r.data == 1)
				{
					bootbox.alert("Successfully Added.");
					$( ".financlapprov" ).trigger("click");
				}
				else
				{
					bootbox.alert("Something went wrong. Please try again.");
				}

			
			$('.ven').removeAttr('disabled');				
		},'json');
	
		
	}



}

function PreviewFinancialApproval(DisplayDiv,HideDiv,no_quot,no_ind,id_quot)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			
		$.post('FinancialApproval',{action : 'Edit' , no_quot : no_quot,no_ind:no_ind,id_quot:id_quot},function (r){
			var list='', temp='',rejectedVen='';
		
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
						var remBUdget=(parseFloat(r.budget[0].budg_rem))-(parseFloat(params.tot));
							 list = temp+
							 '<tr>'+
							 '<input type="hidden" name="id_ven_select1" value="'+r.id_ven_select+'">'+
								'<td colspan="8" style="background-color: cadetblue;color: white;"><strong style="float:right;">Approximate Value of order </strong></td>'+
								'<td style="background-color: cadetblue;color: white;"><strong >'+params.tot+'</strong></td>'+
							'</tr>'+
								
								
							 '<td colspan="8" style="border-top-style: hidden;">'+
								 '<table align="center" class="table table-bordered" style="width:70%">'+
									 '<tr style="background-color: cadetblue;color: white;">'+
											'<td><b>Budget Allocated</b></td>'+
											'<td><b>Available Budget</b></td>'+
											'<td style="width: 130px;"><b>Budget Utilized</b></td>'+
											'<td style="width: 130px;"><b>Budget Remaining</b></td>'+
										'</tr>'+
								'<tr style="background-color: cadetblue;color: white;">'+
											'<td><b>'+r.budget[0].budg_allo+'</b></td>'+
											'<td><b>'+r.budget[0].budg_rem+'</b></td>'+
											'<td style="width: 130px;"><b>'+params.tot+'</b></td>'+
											'<td style="width: 130px;"><b>'+remBUdget+'</b></td>'+
										'</tr>'+
										
										'</table></td></tr>'+
								'<tr><td colspan="8" style="border-top-style: hidden;">'+
								'<div align="center"><b>List of bids received</b></div>'+
								'</td></tr>'+
								 '<tr>'+
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
								/*'<div align="center"><p><font size="2"><b>(ii) Did you select the vendor with the lowest bid?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
								'<input type="radio"  name="lowest_bid" value="1" '+lowest_bid+'>&nbsp;Yes&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
								'<input type="radio"  name="lowest_bid" value="0" '+lowest_bid1+'>&nbsp;No'+
							'</p></div>'+*/
							'</tr>'+
							'<tr>'+
							'<td colspan="8" style="border-top-style: hidden;"><font size="2"><b>Remarks</b></font></td>'+
							'</tr>'+
							/*'<tr>'+
								'<td colspan="8" style="border-top-style: hidden;">'+
									'<span style="margin-left: 280px;"><input type="checkbox" name="recurring_order" value="1" '+recurring_order+'>&nbsp;&nbsp;Recurring order&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
									'<input type="checkbox" name="compt_bid" value="1" '+compt_bid+'>&nbsp;&nbsp;Competitively bid in last 6 months</span>'+
								'</td>'+
								'</tr>'+*/
							'<tr>'+
								'<td colspan="8" style="border-top-style: hidden;">'+
								/*	'<span style="margin-left: 280px;"><input type="checkbox" name="other" value="1" '+other+'>other(please specify)&nbsp;&nbsp;&nbsp;&nbsp;'+
								*/	'<textarea rows="2" name="financial_rmrk" style="margin-left: 280px;width: 400px;height: 100px;:" cols="40"  class="textarea" onKeyPress="return checkRemarks()" onBlur="return checkSpace(this)">'+other_specify+'</textarea></span></td>'+
								'</td>'+
								'</tr>'+
							 '<tr>'+
								'<td colspan="8">'+
									'<button type="button" style="margin-left: 400px;" class="btn btn-primary ven" onclick="ControlDivForVendorSelection(\'Approve\',\'displayVendorSelection\',\'createVendorSelection\')">Approve</button>'+
								    '<button type="button"  style="margin-left: 5px;" class="btn btn-primary ven" onclick="ControlDivForVendorSelection(\'Reject\',\'displayVendorSelection\',\'createVendorSelection\')">Reject</button>'+
								
									'<button type="button" class="btn btn-primary ven" style="margin-left: 10px;" onclick="ControlDivForVendorSelection(\'Cancel\',\'displayVendorSelection\',\'createVendorSelection\')">Cancel</button></td>'+
								'</td>'+
								
							'</tr>';
							
						 
						 
						$('.PreviewVendorSelectionDisplay').html(list);	
						
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();
						
					}
				else
					{
						bootbox.alert("Something went wrong please try again.");
					}
				
		},'json');
			}});
}






function DisplayQuotaionForFinancialApproval()
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('FinancialApproval',{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="7" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 325px;">Quotation Number For Vendor Selection</p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
			'<td><strong><center>Vendor Name</center></strong></td>'+
			'<td><center><strong>Indent No</strong></center></td>'+
			'<td><center><strong>Indent By</strong></center></td>'+
			'<td><center><strong>Quotation No</strong></center></td>'+
			'<td><center><strong>Approved By</strong></center></td>'+
			'<td><center><strong>Approved Date</strong></center></td>'+
			'<td style="width: 115px;"><strong><center><a href="#">Preview</a></center></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.no_ind+'</center></td>'+
									'<td><center>'+params.indent_by+'</center></td>'+
									'<td><center>'+params.no_quot+'</center></td>'+
									'<td><center>'+params.approv_by+'</center></td>'+
									'<td><center>'+params.dt_approv+'</center></td>'+
									'<td><strong><center><a class="alert" href="#" onclick="PreviewFinancialApproval(\'createVendorSelection\',\'displayVendorSelection\',\''+params.no_quot+'\',\''+params.no_ind+'\',\''+params.id_quot+'\')"> Preview </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.vendorSelectionForDisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="7"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.vendorSelectionForDisplay').html(list);
			}
		
		
	},'json');

			}});
}
