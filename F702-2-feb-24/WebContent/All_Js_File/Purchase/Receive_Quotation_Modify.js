function ControlReceiveQuotation(action ,DispDiv , HideDiv , no_ind)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action == 'Cancel')
	{
		$('#'+DispDiv).show();
		$('#'+HideDiv).hide();
	}
	if(action == 'Save')
	{
		var t=false;
		t=ValidationForm('submitReceiveQuotation');
		
		if(t)
			{bootbox.confirm("Are you sure ?", function(result) {
				if(result)
				{	
				var x = $('#submitReceiveQuotation').serialize();
				$('.rcvQuotBtnForDissable').attr('disabled','disabled');
				$.post('Receive_Quotation_Modify',x,function (r)
						{
					
							if(r.data)
								{
								bootbox.alert("Successfully Added.");
								$( ".receiveQuotation" ).trigger( "click" );
								}
								else
									{
										bootbox.alert("Something went wrong. Please try again.");
									}
									
							$('.rcvQuotBtnForDissable').removeAttr('disabled');	
						},'json');
				}});
			}
		
		//$( ".receiveQuotation" ).trigger( "click" );
		
		}
	
	if(action == 'Update')
	{
		
		var t=false;
		t=ValidationForm('submitReceiveQuotation');
		if(t)
		{
			$('.upd').attr('disabled','disabled');

			
			var x = $('#submitReceiveQuotation').serialize();
			console.log(x);
			$.post('Receive_Quotation_Modify',x,function (r){
				if(r.data == 1)
					{
					/*$('#'+DispDiv).show();
					$('#'+HideDiv).hide();
				DisplayModifyReceiveQuotation();*/	
							bootbox.alert("Update Successfully.");
						$( ".receiveQuotationEdit" ).trigger( "click" );
							
						
					}
				else
				{
					bootbox.alert("Something went wrong Please try again.");
				}
				$('.upd').removeAttr('disabled');
			},'json');
		
		}
	}
	
	
			}});
}

jQuery("input#uploadFileForRecQuot").change(function () {
	var formData = new FormData();
    formData.append('file', $('#uploadFileForRecQuot').get(0).files[0]);
	$.ajax({
	  url: 'Upload_File',
	    type: 'POST',
	    data: formData,
	    async: false,
	    cache: false,
	    contentType: false,
	    dataType: "json",
	    processData: false,
	    success: function (r) {
	    	
	    		$('input[name="nm_upload_file"]').val(r.upload_inv);
	    	//	bootbox.alert("File Uploaded successfully");
	    		
	    	
	    }
	},'json');
});



jQuery("input#uploadFileForRecQuot").change(function () {
	var formData = new FormData();
    formData.append('file', $('#uploadFileForRecQuot').get(0).files[0]);
	$.ajax({
	  url: 'Upload_File',
	    type: 'POST',
	    data: formData,
	    async: false,
	    cache: false,
	    contentType: false,
	    dataType: "json",
	    processData: false,
	    success: function (r) {
	    	
	    		$('input[name="nm_upload_file"]').val(r.upload_inv);
	    	//	bootbox.alert("File Uploaded successfully");
	    		
	    	
	    }
	},'json');
});


function DisplayQuotationDetails(DispDiv , HideDiv ,id_quot)
{
	
	
	$.post('Receive_Quotation_Modify',{action : 'DisplayQuotationDetails',id:id_quot},function (r){
		var list=' <tr class="info">'+
		'<td style="min-width:200px;"><strong><center>Model Name </strong></center></td>'+
		'<td><center><strong>Model Code</strong></center></td>'+
		'<td><strong><center>Remarks <br> (100 Character Only.)  </center></strong></td>'+
		
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
		if(r.data.length > 0)
			{
			var grnd_tot=0.0;
					
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];	
				 grnd_tot=parseFloat(params.qty)*parseFloat(params.qty);
				list += '<tr class="success">'+
					'<td style="width:500px;"><center>'+params.nm_model+'</center></td>'+
					'<td style="width:80px;"><center>'+params.cd_model+'</center></td>'+
					'<td><center><input style="width: 135px;" type="text" name="remarks'+i+'" value="'+params.remarks+'" class="common-validation" ></center></td>'+
					'<input type="hidden" name="count" value="'+i+'">'+
					'<td><center><input type="text"  name="qty'+i+'"  readonly value="'+params.qty+'" style="width:60px;;text-align: right;" class="common-validation" patelUnPrc="'+i+'" onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+
					'<td><center><input type="text" name="un_prc'+i+'"  value="'+params.un_prc+'" patelUnPrc="'+i+'"  style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" ></center></td>'+
					'<td><input type="text" name="others'+i+'"  value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:60px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
					'<td><center>'+
					'<select style="width: 80px;" name="id_tax1'+i+'"  value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
					'</select>'+
					'</center></td>'+
					'<td><center>'+
					'<select style="width: 80px;" name="id_tax2'+i+'"  value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')">'+
					'</select>'+
					'</center></td>'+
					'<td><center><input type="text"  name="tax_val1'+i+'"  value="'+params.tax_val1+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
					'<td><center><input type="text"  name="tax_val2'+i+'"  value="'+params.tax_val2+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
					'<td><center><input type="text"  name="buyback'+i+'"  value="'+params.buyback+'" patelUnPrc="'+i+'"  onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
					
					'<td><center><input type="text"  name="gr_tot'+i+'"  value="'+params.tot_prc+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
						'<input type="hidden" name="id_quot_asst'+i+'" value="'+params.id_quot_asst+'">'+
				'</tr>';
				}
				list +='<tr>'+
						'<td colspan="11"><input type="text" name="oter_text" value="Others Charge" style="float:right;"  class="common-validation" ></td>'+
						
							//'<td colspan="19"><b style="float:right;"></b></td>'+
							'<td><center><input type="text" name="frt_text" value="'+params.frt_text+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'frt_text\')" class="common-validation" ></center></td>'+
						'</tr>'+
						
						'<tr>'+
						'<td colspan="11"><b style="float:right;">Discount</b></td>'+
						'<td><center><input type="text" name="discount" value="'+params.discount+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'discount\')" class="common-validation" ></center></td>'+
					'</tr>'+
						'<tr>'+
							'<td colspan="11"><b style="float:right;">Grand Total</b></td>'+
							'<td><center><input type="text" name="tot" value="'+params.tot+'" style="width: 60px;text-align: right" class="common-validation" readonly></center></td>'+
					'</tr>'+
				
				
			 '<tr>'+
	         '	<td colspan="12">'+
	         '	<button type="button" style="margin-left: 400px;" class="btn btn-primary rcvQuotBtnForDissable" onclick="ControlReceiveQuotation(\'Update\',\'displayReceiveQuotation\',\'createReceiveQuotation\',\'\')">Update </button>&nbsp;&nbsp;'+
			 '	<button type="button" class="btn btn-primary rcvQuotBtnForDissable" onclick="ControlReceiveQuotation(\'Cancel\',\'displayReceiveQuotation\',\'createReceiveQuotation\',\'\')">cancel </button>'+
			 '	</td>'+
			'</tr>';
				if(r.data[0].remarks !=undefined)
					remarks = r.data[0].remarks;
				$('input[name="id_quot"]').val(id_quot);
				$('input[name="id"]').val(id_quot);
				$('input[name="dt_rec_quot"]').val(r.data[0].dtrecquot);
				$('input[name="no_quot"]').val(r.data[0].no_quot);
				$('input[name="no_ind"]').val(r.data[0].no_ind);
				 
					 $('input[name="nego_no"]').val(r.data[0].nego_no);
					
				$('.QuotationDetailsForReceiveVendor').html(list);
				
				DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
					if(status)
					{
						
						
					}});
			}
		else
			{
			
			list +='<tr><td colspan="7"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.QuotationDetailsForReceiveVendor').html(list);
			}
		
		$( ".recvQuotDatepicker" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      onSelect: function(selected,evnt) {
		    	  $(this).removeClass('error');
		    	  var dt_req_quot=$('input[name="dt_rec_quot"]').val();
		    	  var dt_req_quot1=$('input[name="dt_rec_quot"]').val();
		    	  var dt_end = $(this).val();
		    	  if(dt_req_quot == '')
		    		  {
		    		  	alert('First filled quotation receive date.');
		    		  	$('input[name="dt_rec_quot"]').focus();
		    		  	$('input[name="dt_rec_quot"]').addClass('error');
		    		  	$('input[name="dt_rec_quot"]').val('');
		    		  	$(this).val('');
		    		  	exit(0);
		    		  }
		    	  else
		    		  {
		    		  var temp_strt = dt_req_quot.split("/");
						 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
							
						var temp_end = dt_end.split("/");
						var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
							
						dt_req_quot = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
			    		  dt_end = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
		    		  
		    		  if(dt_req_quot > dt_end)
		    			  {
		    			  	alert('Delivery date should be greater or equal to quotation receive date : '+dt_req_quot1);
		    			  	$(this).focus();
		    			  	$(this).val('');
		    			  	$(this).addClass('error');
		    			  	exit(0);
		    			  }
		    		  
		    		  }
		    	  
		      }
		    });
	
		
	},'json');
	
	$('#'+DispDiv).show();
	$('#'+HideDiv).hide();

}


$( ".recvQuotDatepicker2" ).datepicker({
	yearRange: '1985:2025',
      changeMonth: true,
      changeYear: true,
      dateFormat: "dd/mm/yy",
      autoSize: true,
      altFormat: "dd/mm/yy",
      onSelect: function(selected,evnt) {
    	  $(this).removeClass('error');
    	  var dt_recv=$(this).val();
    	  var id_req_quot=$('input[name="id_req_quot"]').val();
    	  $(".recvQuotDatepicker").val('');
    	  
    	  $.post('Receive_Quotation_Modify',{action : 'CheckDate' ,dt_recv:dt_recv,id_req_quot:id_req_quot},function (r){
	    		if(r.data == 0)
	    			{
	    			alert('Quotation receive date should be greater or equal to requested quotation date : '+r.dt_req_quot);
	    			$(".recvQuotDatepicker2").focus();
	    			$(".recvQuotDatepicker2").val('');
	    			$(".recvQuotDatepicker2").addClass('error');
	    				exit(0);
	    			}
	    		
	    },'json');
    	  
    	  
      }
    });



function DisplayReceiveQuotation(servletName,displayContent,createDetails)
{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
	$.post(servletName,{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){
		
		var list= '<tr>'+
		'<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 400px;">List Of Submit RFQ</p></td>'+
		'</tr>'+
			'<tr class="tableHeader info">'+
			'<td><center><strong>Quotation Rrf Number</strong></center></td>'+
			'<td><center><strong>Quotation Rrf Date</strong></center></td>'+
			'<td><center><strong>RFQ No.</strong></center></td>'+
		'<td style="width:200px;"><center><a href="#"><strong>Modify</strong></a></center></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.no_quot+'</center></td>'+
									'<td><center>'+params.dtrecquot+'</center></td>'+
									'<td><center>'+params.no_ind+'</center></td>'+
									'<td><center><a class="alert" href="#" onclick="DisplayQuotationDetails(\'createReceiveQuotation\',\'displayReceiveQuotation\','+params.id_quot+')">Modify</a></center></td>'+
							  '</tr>';
				}
			
				$('.receiveQuotatioForDisplay').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.receiveQuotatioForDisplay').html(list);
			}
		
	},'json');
}


function calculateTot111111(event, fieldName){

	var domElement =$(event.target || event.srcElement);
	var totParticular=0.0,total=0.0;
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	var str ='';
	$('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').removeClass('error');
	$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').removeClass('error');
	
	if(fieldName =='qty')
	 str = $('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val();
	else
		str = $('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val();

	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		if($('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val() != '')
		{
			totParticular = parseFloat($('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val())*parseFloat($('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val());
			$('input[name="tot_prc'+domElement.attr('patelUnPrc')+'"]').val(totParticular.toFixed(2));
		}
	else
		{
			$('input[name="tot_prc'+domElement.attr('patelUnPrc')+'"]').val('');
		}
	$('.commonTotal').each(function (){
		if($(this).val() != undefined)
		total += +$(this).val();
		
	});
	if(total != 0.0)
		{
			$('input[name="tot"]').val(total.toFixed(2));
			if($('input[name="tax_per"]').val() == 0)
				$('input[name="gr_tot"]').val(total.toFixed(2));
		}
	else
		$('input[name="tot"]').val('');
	
	var id_tax = $('select[name="taxId'+domElement.attr('patelUnPrc')+'"]').val();
	if(id_tax != '0')
		{
		$.post('M_Tax',{action : 'DropDownResult',id:id_tax},function (r){
			
			if(r.data.length > 0 )
				{
				 var percent = r.data[0].per_tax;
				 var gr_tot=0.00;
				 var qty = $('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val();
				 var un_prc = $('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val();
				 var taxVal = (parseFloat(qty)*parseFloat(un_prc)*parseFloat((percent)))/100;
				 var tot= +parseFloat(qty)*parseFloat(un_prc) + taxVal;

						 $('input[name="taxVal'+domElement.attr('patelUnPrc')+'"]').val(taxVal.toFixed(2));
						 $('input[name="tot_prc'+domElement.attr('patelUnPrc')+'"]').val(tot.toFixed(2));
						 $('input[name="taxPercent'+domElement.attr('patelUnPrc')+'"]').val(percent);
						 $('.commonTotal').each(function (){
								if($(this).val() != undefined)
									gr_tot += +$(this).val();
								
							});
						 var others= $('input[name="others"]').val();
						 gr_tot = gr_tot + parseFloat(others);
						 
						 $('input[name="tot"]').val(gr_tot.toFixed(2));
					
				}
			
			
		},'json');
		}
		
	}
	else
		{
			alert('Invalid number.');
			if(fieldName =='qty'){
				$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').addClass('error');
				$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').focus();
				$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val(0);
				exit(0);
			}else{
				$('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').addClass('error');
				$('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').focus();
				$('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val(0);
				exit(0);
			}
			
		}
	
		}
	 else
		{
		alert('Mandatory Field.');
		if(fieldName =='qty'){
			$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').addClass('error');
			$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').focus();
			$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val(0);
			exit(0);
		}else{
			$('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').addClass('error');
			$('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').focus();
			$('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val(0);
			exit(0);
		}
		} 
	
	
}
//////////////////////////// modify receive Quotation....................
function DisplayModifyReceiveQuotation()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Receive_Quotation_Modify',{action : 'Modifyrecvquotation',searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="6" class="tableHeader"><center><p class="tableHeaderContent" ">Modify Receive Quotation</p></center></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
				'<td><center><strong>Quotation Number<a href=#></a></strong></center></td>'+
				'<td><center><strong>Indent Number</strong></center></td>'+
				'<td><center><strong>Vendor Name </strong></center></td>'+
				'<td style="width: 135px;"><strong><center><a href="#">Modify Receive Quotation</a></center></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.no_quot+'</center></td>'+
									'<td><center>'+params.no_ind+'</center></td>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
										'<td><strong><center><a class="alert" href="#" onclick="ControlmodifyReceiveQuotation(\'modify\',\'createReceiveQuotation\',\'displayReceiveQuotation\',\''+params.no_quot+'\',\''+params.no_ind+'\',\''+params.id_quot+'\')"> Modify </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.modireceiveQuotatioForDisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.modireceiveQuotatioForDisplay').html(list);
			}
		
		
	},'json');

			}});

}

function ControlmodifyReceiveQuotation(action,DisplayDiv,HideDiv,no_quot,no_ind,id_quot)
{
	
	
	$.post('Receive_Quotation_Modify',{action : 'Modifyrecvquotation',no_ind:no_ind,id:id_quot},function (r){
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
						if(key == 'remarks')
						{
							$('textarea[name='+key+']').val(r.data[i][key]);
						}
					
			        }
					
					/*if(r.data[0].rate_cont=='Yes'){
						 $('#rate').show();
						 $('#blank').hide();
						 $('#ratedt').show();
					}
					else{
						 $('#rate').hide();
						 $('#ratedt').hide();
						 $('#blank').show();
					}*/
					 $('input[name="no_quot"]').val(r.data[0].no_quot);
						
				}
				var totPrice=0;	var params='';
			var list ='<tr class="tableHeader info">'+
			'<td style="min-width:200px;"><strong><center>Model Name<a href=#></a></center></strong></td>'+
			'<td style="width:60px;"><strong><center>Model Code</center></strong></td>'+
			
			'<td><strong><center>UOM</center></strong></td>'+
			'<td><strong><center>HSN Code</center></strong></td>'+
			
			'<td><strong><center>Remarks</center></strong></td>'+
			'<td style="width: 75px;"><strong><center>Model Price <font color="red"> * </font></center></strong></td>'+
			'<td style="width: 75px;"><strong><center>Freight Percent<font color="red"> * </font></center></strong></td>'+
			
			'<td style="width: 75px;"><strong><center>P&F/Freight/EDC<font color="red"> * </font></center></strong></td>'+
			'<td style="width: 75px;display:none;"><strong><center>Others<font color="red"> * </font></center></strong></td>'+
			
			'<td style="width: 120px;"><strong><center>Tax Name</center></strong></td>'+
			'<td style="width: 75px;"><strong><center>Tax Value</center></strong></td>'+
			'<td style="width: 75px;"><strong><center>Installation(Incl Taxes)</center></strong></td>'+
			
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
			
		'</tr>';
			for(var i=0;i<r.data.length;i++)
				{
				 params=r.data[i];
				remarks='',dt_scheduled_dlvry='';
				if(params.remk !=undefined)
					remarks = params.remk;
				if(params.dt_scheduled_dlvry1 !=undefined)
					dt_scheduled_dlvry = params.dt_scheduled_dlvry1;
					
				list += '<tr class="success">'+
				'<td><center>'+params.nm_prod+'</center></td>'+
				'<td><center>'+params.cd_prod+'</center></td>'+
				
				'<td><center>'+params.uomval+'</center></td>'+
				'<td><center>'+params.hsn_code+'</center></td>'+
				
				'<td><center><input type="text" style="width: 150px;" name="remarks'+i+'" remarks="'+i+'" value="'+remarks+'"  style="width: 100px;" ></center></td>'+
					
				
				'<input type="hidden" name="id_quot_asst'+i+'" value="'+params.id_quot_asst+'">'+
				
				//'<td><center><input type="text" name="dt_rec_quot'+i+'" value="'+params.dtrecv+'" style="width: 100px;" class="common-validation modifyPODatepicker2" data-valid="mand"></center></td>'+
				'<td><center><input type="text" name="item_prc'+i+'" patelUnPrc="'+i+'" value="'+params.item_prc+'" style="width: 60px;" onChange="calculateTot(event,\'item_prc\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
				'<td><center><input type="text" name="freight_percent'+i+'" patelUnPrc="'+i+'" value="'+params.freight_percent+'" style="width: 60px;text-align: right" onChange="calculateTot(event,\'freight_percent\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
				
				'<td><center><input type="text" name="freight_rate'+i+'" patelUnPrc="'+i+'" value="'+params.freight_rate+'" style="width: 60px;" onChange="calculateTot(event,\'freight_rate\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
					
				'<td><center>'+
				'<select style="width: 100px;" name="taxId'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="'+params.id_tax+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'taxId\')">'+
				'</select>'+
				'</center></td>'+
				'<input type="hidden" name="taxPercent'+i+'" value="0">'+
				'<td><center><input type="text" style="width: 60px;" name="taxVal'+i+'" taxVal="'+i+'" value="'+params.tax_val+'" readonly style="width: 60px;" class="patelTax"></center></td>'+
				'<td><center><input type="text" style="width: 60px;" name="itemIns_chrg'+i+'" patelUnPrc="'+i+'" value="'+params.itemins_chrg+'" style="width: 60px;" class="patelUnPrc common-validation" data-valid="num" onChange="calculateTot(event,\'itemIns_chrg\')"></center></td>'+
				
				'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="'+params.un_prc+'" style="width: 60px;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" readOnly></center></td>'+
				'<td><center><input type="text" name="qty'+i+'" patelqty'+params.id_prod+'="'+params.qty+'" patelUnPrc="'+i+'" value="'+params.qty+'" onChange="calculateTot(event,\'qty\')" style="width: 50px;" class="patelQty" data-valid="num"></center></td>'+
				'<td style="display:none;"><center><input type="text" name="others_chrg'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;" onChange="calculateTot(event,\'others_chrg\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
				
				'<td><center><input type="text" name="tot_prc'+i+'" value="'+params.tot_prc+'" style="width: 80px;"  readonly></center></td>'+
				
				'<td><center><input type="text"  name="frt_rate'+i+'" value="'+params.frt_rate+'" style="width: 60px;" patelTaxadd="'+i+'" data-valid="num" onBlur="TaxCalculationadd(event,\'frt_rate\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="add_chrg'+i+'" value="'+params.add_chrg+'" style="width: 60px;" patelTaxadd="'+i+'" data-valid="num" onBlur="TaxCalculationadd(event,\'add_chrg\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="frt_text'+i+'" value="'+params.frt_text+'" style="width: 60px;" patelTaxadd="'+i+'" data-valid="num" onBlur="TaxCalculationadd(event,\'frt_text\')" class="common-validation" ></center></td>'+
				
				'<td><center>'+
				'<select style="width:70px;" name="taxIdadd'+i+'" patelTaxadd="'+i+'" taxName="'+params.taxid_add+'" class="common-validation patelTax" onChange="TaxCalculationadd(event,\'taxIdadd\')">'+
				'</select>'+
				'</center></td>'+
				'<input type="hidden" name="taxPercentadd'+i+'" value="0">'+
				
				
				'<td><center><input type="text"  name="add_chrg1'+i+'" value="'+params.add_chrg1+'" style="width: 60px;" readonly patelUnPrc="'+i+'" data-valid="num" onBlur="calculateTot(event,\'add_chrg1\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="add_chrg2'+i+'" value="'+params.add_chrg2+'" style="width: 60px;" patelUnPrc="'+i+'" data-valid="num" onBlur="calculateTot(event,\'add_chrg2\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="buy_back'+i+'" value="'+params.buy_back+'" style="width: 60px;" patelUnPrc="'+i+'" data-valid="num" onBlur="calculateTot(event,\'buy_back\')" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="grnd_tot'+i+'" value="'+params.grnd_tot+'" style="width: 60px;" patelUnPrc="'+i+'" class="commonTotal"  readonly></center></td>'+
		
				
				
				'<input type="hidden" name="actionType'+i+'" value="Update">'+
				
				'</tr>';
				totPrice += parseFloat(params.grnd_tot);
				}
			
			totPrice += (parseFloat(params.frtrate) + parseFloat(params.addchrg) + parseFloat(params.frttext)) -parseFloat(params.buyback) +parseFloat(params.freight_amount) -parseFloat(params.discount);
			
			list += 
			//'<input type="hidden" name="id_po" value="'+id_po+'">'+
				'<input type="hidden" name="count" value="'+i+'">'+
			'<input type="hidden" name="no_ind" value="'+r.data[0].no_ind+'">'+
			'<input type="hidden" name="no_quot" value="'+r.data[0].no_quot+'">'+
			/*'<input type="hidden" name="do_direct" value="'+r.data[0].do_direct+'">'+*/
			
			'<input type="hidden" name="reclassification" value="No">'+
			'<tr>'+
			'<td colspan="20"><b style="float:right;">Freight Charge</b></td>'+
			'<td><center><input type="text" name="freight_amount" value="'+params.freight_amount+'" style="width: 80px;" data-valid="num" onChange="addInTotal(\'freight_amount\')" class="common-validation" ></center></td>'+
		'</tr>'+
			'<tr>'+
			'<td colspan="20"><b style="float:right;">Installation Charge</b></td>'+
			'<td><center><input type="text" name="frt_rate" value="'+params.frtrate+'" style="width: 80px;" data-valid="num" onChange="addInTotal(\'frt_rate\')" class="common-validation" ></center></td>'+
		'</tr>'+
		'<tr>'+
			'<td colspan="20"><b style="float:right;">Additional charges</b></td>'+
			'<td><center><input type="text" name="add_chrg" value="'+params.addchrg+'" style="width: 80px;" data-valid="num" onChange="addInTotal(\'add_chrg\')" class="common-validation" ></center></td>'+
		'</tr>'+
		'<tr>'+
		'<td colspan="20"><input type="text" name="oter_text" value="'+params.oter_text+'" style="float:right;"  class="common-validation" ></td>'+
		
			//'<td colspan="19"><b style="float:right;">Others Charge</b></td>'+
			'<td><center><input type="text" name="frt_text" value="'+params.frttext+'" style="width: 80px;" data-valid="num" onChange="addInTotal(\'frt_text\')" class="common-validation" ></center></td>'+
		'</tr>'+
		'<tr>'+
			'<td colspan="20"><b style="float:right;">Buy Back</b></td>'+
			'<td><center><input type="text" name="buy_back" value="'+params.buyback+'" style="width: 80px;" data-valid="num" onChange="addInTotal(\'buy_back\')" class="common-validation" ></center></td>'+
		'</tr>'+
		'<tr>'+
		'<td colspan="20"><b style="float:right;">Discount</b></td>'+
		'<td><center><input type="text" name="discount" value="'+params.discount+'" style="width: 80px;" data-valid="num" onChange="addInTotal(\'discount\')" class="common-validation" ></center></td>'+
	'</tr>'+

		'<tr>'+
			'<td colspan="20"><b style="float:right;">Total</b></td>'+
			'<td><center><input type="text" name="tot" value="'+totPrice.toFixed(2)+'" style="width: 80px;" class="common-validation" readonly></center></td>'+
			'<input type="hidden" name="gr_tot" value="'+totPrice.toFixed(2)+'" style="width: 80px;" class="common-validation" readonly></center>'+

			'</tr>'+
		'</tr>'+
		'<tr>'+
		/*'<td colspan="2"><b style="float:right;">Term & Conditions :</b></td>'+
			'<td colspan="6">'+
			'<textarea style="margin-left: 0px;margin-right: 0px;width: 600px; height: 73px;"  name="po_t_c">'+
			r.data[0].po_t_c+
					'</textarea>'+
				'</td>'+
	    '</tr>'+*/

		 '<tr>'+
         '	<td colspan="20">'+
         '	<button type="button" style="margin-left: 400px;" class="btn btn-primary upd" onclick="ControlReceiveQuotation(\'Update\',\'displayReceiveQuotation\',\'createReceiveQuotation\',\'\')">Update </button>&nbsp;&nbsp;'+
		 '	<button type="button" class="btn btn-primary upd" onclick="ControlReceiveQuotation(\'Cancel\',\'displayReceiveQuotation\',\'createReceiveQuotation\',\'\')">cancel </button>'+
		 '	</td>'+
		'</tr>';
			
			$('.modifyquot').html(list);
			
			$('#'+DisplayDiv).show();
			$('#'+HideDiv).hide();
			}
		
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
		
		
		
	},'json')
	.fail(function(jqXHR,status)
		    {
		alert('hi');
		    });

}
