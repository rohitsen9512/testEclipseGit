
function ControlFinalizeVendorPrintDiv(action , DisplayDiv , HideDiv , no_ind)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Cancel')
	{
		DisplayQuotaionForPreview();
		$('#'+DisplayDiv).hide();
		$('#'+HideDiv).show();
	}
	
	else if(action =='Modify')
		{
			EditViewQuotaionFun(action , DisplayDiv , HideDiv , no_ind);
			
		}
	}});
}



function EditViewQuotaionFun(action , DisplayDiv , HideDiv , no_ind)
{
	SessionCheck(function (ses){		
		if(ses)
			{
		$.post('Finalize_Vendor',{action : 'ViewEdit' , no_ind : no_ind},function (r){
			var list='', temp='';
			
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
					var t=0;
					var tempOptionVal='';
					
					header='<tr style="background-color: cadetblue;color: white;">'+
					'<td colspan="12" style="border-bottom-style: hidden;"><center><strong style="font-size: large;">'+r.data[0].quot_header_nm+' </strong></center></td>'+
				'</tr>'+
				'<tr style="background-color: cadetblue;color: white;">'+
					'<td colspan="12" style="background-color: cadetblue;color: white;border-top: 0px solid #ddd;"><table><tr>'+
						'<table style=" width:100%">'+
							'<tr style="border-style: hidden;">'+
								'<td><strong >Purchase Indent No  : <b>'+no_ind+'</b></strong></td>'+
								'<td><strong >Indenting Department  : <b>'+r.data[0].indent_dept+'</b></strong></td>'+
								'<td><strong >No Of Quotation Invited  : <b>'+r.data[0].invt_quot_num+'</b></strong></td>'+
								'<td><strong >No Of Offers Received  : <b>'+r.data[0].recvd_quot_num+'</b></strong></td>'+
							'</tr>'+
							'<tr style="border-style: hidden;">'+
								'<td><strong >Price bid opened on   : <b>'+r.data[0].dt_bid_open1+'</b></strong></td>'+
								'<td><strong >No of offers accepted from price bid  : <b>'+r.data[0].accepted_quot_num+'</b></strong></td>'+
								'<td colspan="2"></td>'+
							'</tr>'+
						'</table>'+
					'</td>'+
					
				'</tr>';
				
					
						for(var j = 0; j < id_quotArr.length ; j++)
						{
							
							params=r.data[(t)];
							
								
							
							var tempQuot = params.id_quot;
							
							 temp='<tr style="background-color: cadetblue;color: white;">'+
								'<td colspan="12" style="background-color: cadetblue;color: white;border-top: 0px solid #ddd;"><table><tr>'+
								'<table style=" width:100%">'+
									'<tr style="background-color: cadetblue;color: white;border-bottom-style: hidden;">'+
										'<td  style="border-right-style: hidden;"><strong style="float:right;border-bottom-style: hidden;">Vendor Name :</strong></td>'+
										'<td  style=""><b>'+params.nm_ven+'</b></td>'+
										'<td  style="border-right-style: hidden;"><center><strong style="float:right;">Quotation No  :</strong></center></td>'+
										'<td  style="border-right-style: hidden;"><b>'+params.no_quot+'</b></td>'+
										'<td  style="border-right-style: hidden;"><center><strong style="float:right;">Quotation Date :</strong></center></td>'+
										'<td  style=""><b>'+params.dtrecquot+'</b></td>'+
									'</tr>'+	
								'</table>'+
							'</td>'+
							
						'</tr>'+
								 
					/*	'<tr style="background-color: cadetblue;color: white;border-bottom-style: hidden;">'+
							'<td colspan="3" style="border-right-style: hidden;"><strong style="float:right;border-bottom-style: hidden;">Vendor Name :</strong></td>'+
							'<td colspan="6" style=""><b>'+params.nm_ven+'</b></td>'+
						'</tr>'+
						'<tr style="background-color: cadetblue;color: white;">'+
							'<td colspan="2" style="border-right-style: hidden;"><center><strong style="float:right;">Quotation No  :</strong></center></td>'+
							'<td colspan="2" style="border-right-style: hidden;"><b>'+params.no_quot+'</b></td>'+
							'<td colspan="2" style="border-right-style: hidden;"><center><strong style="float:right;">Quotation Date :</strong></center></td>'+
							'<td colspan="3" style=""><b>'+params.dtrecquot+'</b></td>'+
						'</tr>'+*/
						'<tr class="tableHeader info">'+
							'<td><strong><center>Item Name<a href=#></a></center></strong></td>'+
							'<td><strong><center>Item Code</center></strong></td>'+
							'<td><strong><center>Specification</center></strong></td>'+
							'<td><strong><center>UOM</center></strong></td>'+
							
							'<td><strong><center>Base Price</center></strong></td>'+
							'<td><strong><center>Freight rate</center></strong></td>'+
							'<td><strong><center>Others </center></strong></td>'+
							
							'<td><strong><center>Tax Name</center></strong></td>'+
							'<td><strong><center>Tax Value</center></strong></td>'+
							'<td><strong><center>Unit Price</center></strong></td>'+
							'<td style="width: 70px;"><strong><center>Quantity</center></strong></td>'+
							'<td><strong><center>Total</center></strong></td>'+
							
						'</tr>';
							 var tot=0;
							for(var i = 0; i < r.data.length ; i++)
							{
								
								params1 = r.data[i];
								if(params1.id_quot == tempQuot)
								{
									t=i+1;
									tot += parseFloat(params1.tot_prc);
								temp = temp + '<tr class="success">'+
													'<td><center>'+params1.nm_prod+'</center></td>'+
													'<td><center>'+params1.cd_prod+'</center></td>'+
													'<td><center>'+params1.description+'</center></td>'+
													'<td><center>'+params1.uom+'</center></td>'+
													
													'<td><center>'+params1.item_prc+'</center></td>'+
													'<td><center>'+params1.freight_rate+'</center></td>'+
													'<td><center>'+params1.others_chrg+'</center></td>'+
													
													'<td><center>'+params1.nm_tax+'</center></td>'+
													'<td><center>'+params1.tax_val+'</center></td>'+
													'<td><center>'+params1.un_prc+'</center></td>'+
													'<td><center>'+params1.qty+'</center></td>'+
													'<td><center>'+params1.tot_prc+'</center></td>'+
													'</tr>';
									}
							}
							
							 list += temp+'<tr  style="background-color: cadetblue;color: white;">'+
								'<td colspan="11"><strong style="float:right;">Grand Total ( '+params.nm_curr+' )</strong></td>'+
								'<td><strong>'+tot.toFixed(2)+'</strong></td>'+
								'</tr>';
									
						}
				list +='<tr style="background-color: cadetblue;color: white;">'+
							'<td colspan="12" style="border-bottom-style: hidden;"><strong>'+
							
							'<br><b style="margin-left: 10%;">Note: '+r.data[0].quot_note+'</b></strong>'+
							'</td>'+
						'</tr>'+
						'<tr style="background-color: cadetblue;color: white;">'+
							'<td colspan="12" style="border-bottom-style: hidden;"><strong>'+
							
							'<br><b style="margin-left: 10%;">'+r.data[0].agm_print+'</b> <b style="margin-left: 20%;">'+r.data[0].agm_mmd+'</b> <b style="margin-left: 20%;">'+r.data[0].mgr_f_a+'</b></strong>'+
							'</td>'+
						'</tr>'+
						'<tr style="background-color: cadetblue;color: white;">'+
							'<td colspan="12" style="height:100px;"><strong style="border-bottom-style: hidden;">'+
							
							'<br><b style="margin-left: 10%;">AGM(Printing)</b> <b style="margin-left: 20%;">AGM(MMD)</b> <b style="margin-left: 20%;">MGR(F&A)</b></strong>'+
							'</td>'+
						'</tr>';
						
						var	tempButton = '<button type="button" style="margin-left: 400px;" class="btn btn-primary" onClick="printPg(\'#createPreviewQuotation\')">Print Preview </button>&nbsp;&nbsp;';
						
					
						 
						$('.QuotationDetailsForApproval').html(header+list);
						$('#buttonForPrintMail').html(tempButton);	
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
				
		},'json');
			}});
}


function PrintFinalizeVendor()
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Finalize_Vendor',{action : 'ViewDisplay',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 400px;">Finalized Vendor Print Privew</p></td>'+
			'</tr>'+
				'<tr class="tableHeader info">'+
			'<td><center><strong>Indent Code<a href=#></a></strong></center></td>'+
			'<td><center><strong>Indent Date</strong></center></td>'+
			'<td><center><strong>Indented By</strong></center></td>'+
			'<td style="width: 150px;"><strong><center><a href="#">Preview</a></center></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.no_ind+'</center></td>'+
									'<td><center>'+params.dt_ind+'</center></td>'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><strong><center><a class="alert" href="#" onclick="ControlFinalizeVendorPrintDiv(\'Modify\',\'createPreviewQuotation\',\'displayPreviewQuotation\',\''+params.no_ind+'\')"> Preview </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.viewQuotatioForDisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.viewQuotatioForDisplay').html(list);
			}
		
		
	},'json');

			}});
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