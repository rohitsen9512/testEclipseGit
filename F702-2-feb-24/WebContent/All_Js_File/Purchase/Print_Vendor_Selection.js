function ControlDivForPrintVendorSelection(action,DispDiv,HideDiv)
{
if(action == 'Cancel')
	{
		$('#'+HideDiv).hide();
		$('#'+DispDiv).show();
	}
else if(action == 'Save')
	{
	var x = $('#submitPrintVendorSelection').serialize();
	$.post('Print_Vendor_Selection',x,function (r)
		{
				
			if(r.data == 1)
				{
					bootbox.alert("Successfully Added.");
					$( ".printvendorSelection" ).trigger( "click" );
				}
				else
				{
					bootbox.alert("Something went wrong. Please try again.");
				}
					
		},'json');
	
	}
}

function PrintVendorSelection(DisplayDiv,HideDiv,no_quot,no_ind,id_quot)
{
	SessionCheck(function (ses){		
		if(ses)
			{
		$.post('Print_Vendor_Selection',{action : 'Edit' , no_quot : no_quot,no_ind:no_ind,id_quot:id_quot},function (r){
			var list='', temp='',rejectedVen='';
			
				if(r.data.length > 0)
					{
						temp='<tr style="background-color: cadetblue;color: white;">'+
						'<td colspan="8"><strong style="font-size: large;margin-left: 30%;">Purchase Request Cum Vendor Selection Summary</strong></td>'+
							
						'</tr>'+
						
						'<tr class="tableHeader info">'+
							'<td><strong><center>Item Name</center></strong></td>'+
							'<td><strong><center>Item Code</center></strong></td>'+
							'<td><strong><center>Description</center></strong></td>'+
							'<td><strong><center>Quantity</center></strong></td>'+
							'<td><strong><center>Unit Price</center></strong></td>'+
							'<td><strong><center>Tax Name</center></strong></td>'+
							'<td><strong><center>Tax Value</center></strong></td>'+
							'<td style="width: 200px;"><strong><center>Total Price</center></strong></td>'+
						'</tr>';
						var checked='Yes';
						var lowest_bid='Yes';
						var lowest_bid1='';
						var recurring_order='';
						var compt_bid='';
						var other='-';
						var other_specify='-';
						if(r.data[0].lowest_bid==1) lowest_bid='Yes';else if(r.data[0].lowest_bid==0) lowest_bid='No';
						if(r.data[0].recurring_order==1) recurring_order='Yes';else recurring_order='No';
						if(r.data[0].compt_bid==1) compt_bid='Yes';else compt_bid='No';
						if(r.data[0].other==1) other='Yes';else other='No';
						if(r.data[0].other_specify != undefined) other_specify=r.data[0].other_specify;
						
							for(var i = 0; i < r.data.length ; i++)
							{
								
								params = r.data[i];
								
								temp += '<tr class="success">'+
													'<td><center>'+params.nm_prod+'</center></td>'+
													'<td><center>'+params.cd_prod+'</center></td>'+
													'<td><center>'+params.description+'</center></td>'+
													'<td><center>'+params.qty+'</center></td>'+
													'<td><center>'+params.un_prc+'</center></td>'+
													'<td><center>'+params.nm_tax+'</center></td>'+
													'<td><center>'+params.tax_val+'</center></td>'+
													'<td><center>'+params.tot_prc+'</center></td>'+
													'</tr>';
							}
							
							for(var j=0;j < r.RejectedVen.length;j++)
								{
								rejectedVen += '<tr class="success"><td><b>'+r.RejectedVen[j].nm_ven+'</b></td>'+
								'<td>'+r.RejectedVen[j].tot+' '+r.RejectedVen[j].nm_curr+'</td>'+
								
								'<input type="hidden" name="reject_id_ven" value="'+r.RejectedVen[j].id_ven+'">'+
								'<input type="hidden" name="final_bid" value="'+r.RejectedVen[j].tot+'">'+
								'<input type="hidden" name="reject_no_quot" value="'+r.RejectedVen[j].no_quot+'">'+
								
								'<td><b>Not Short List</b></td></tr>';
								
								}
							
							 list = temp+
							 '<tr>'+
							 '<input type="hidden" name="id_ven_select1" value="'+r.id_ven_select+'">'+
								'<td colspan="7"><strong style="float:right;">Approximate Value of order ( '+params.nm_curr+' )</strong></td>'+
								'<td ><strong >'+params.tot+'</strong></td>'+
							'</tr>'+
							
							 '<tr>'+
							 	'<td colspan="8" style="border-top-style: hidden;">'+
								 	'<table align="center" class="tableHeader info" style="width:100%">'+
									 	'<tr style="background-color: cadetblue;">'+
											'<td><b>Vendor Name</b></td>'+
											'<td><b>Final bid</b></td>'+
											'<td style="width: 130px;"><b>Vendor Status</b></td>'+
										'</tr>'+
										'<tr class="success">'+
											'<td>'+r.data[0].nm_ven+'</td>'+
											'<td>'+r.data[0].tot+' ( '+r.data[0].nm_curr+' )</td>'+
											'<td><b>Short List</b></td>'+
											'<input type="hidden" name="tot" value="'+r.data[0].id_ven+'">'+
											'<input type="hidden" name="id_ven" value="'+r.data[0].id_ven+'">'+
											'<input type="hidden" name="id_quot" value="'+r.data[0].id_quot+'">'+
											'<input type="hidden" name="no_quot" value="'+r.data[0].no_quot+'">'+
										'</tr>'+
										''+rejectedVen+''+
										'</table>'+
								'</td>'+
							'</tr>'+
							'<tr><td colspan="8">'+
							'<table style="width:60%" class="table table-bordered">'+
							'<tr>'+
								'<td colspan="2">'+
								'<b>Vendor Selection Reason</b>'+
								'</td>'+
							'</tr>'+
							'<tr>'+
								'<td >Did you select the vendor with the lowest bid?</td>'+
								'<td>'+lowest_bid+'</td>'+
							'</tr>'+
							'<tr>'+
								'<td>Competitively bid in last 6 months</td>'+
								'<td>'+compt_bid+'</td>'+
							'</tr>'+
							'<tr>'+
								'<td>Recurring Order</td>'+
								'<td>'+recurring_order+'</td>'+
							'</tr>'+
							'<tr>'+
								'<td>Others</td>'+
								'<td>'+other_specify+'</td>'+
							'</tr>'+
						'</table>'+
					'</td></tr>';
						
							
							var temp= 	'<button type="button" style="margin-left: 400px;" class="btn btn-primary" onClick="printPg(\'#createPrintVendorSelection\')">Print Preview</button>';
							
						$('.PrintVendorSelectionDisplay').html(list);	
						$('#buttonForPrint').html(temp);	
						$('#buttonForPrint').show();
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


function DisplayQuotaionForPrintVendorSelection()
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Print_Vendor_Selection',{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="7" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 400px;">Print Vendor Selection</p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
			'<td><center><strong>Vendor Name</strong></center></td>'+
			'<td><center><strong>Indent No</strong></center></td>'+
			'<td><center><strong>Indent By</strong></center></td>'+
			'<td><center><strong>Quotation No</strong></center></td>'+
			'<td><center><strong>Approved By</strong></center></td>'+
			'<td><center><strong>Approved Date</strong></center></td>'+
			'<td style="width: 115px;"><strong><center><a href="#">Print</a></center></strong></td>'+
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
									'<td><strong><center><a class="alert" href="#" onclick="PrintVendorSelection(\'createPrintVendorSelection\',\'displayPrintVendorSelection\',\''+params.no_quot+'\',\''+params.no_ind+'\',\''+params.id_quot+'\')"> Print</a></center></strong></td>'+
									'</tr>';
				}
			
				$('.printvendorselectionForDisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="7"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.printvendorselectionForDisplay').html(list);
			}
	},'json');

			}});
}