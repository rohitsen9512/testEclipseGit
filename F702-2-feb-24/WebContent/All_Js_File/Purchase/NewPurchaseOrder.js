
function ControlPurchaseOrder(action,DisplayDiv,HideDiv,no_quot,no_ind,id_quot)
{

SessionCheck(function (ses){		
if(ses)
{
	if(action == 'Create')
		{
		
		 CreatePurchaseOrder(action,DisplayDiv,HideDiv,no_quot,no_ind,id_quot);
	
	$.post('P_Request_PR',{action : 'LoginUserDetails'},function (r){
				
				if(r.data)
					{	for(var i = 0; i < r.data.length ; i++)
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
										 
									}
									console.log(r.data[0].repo_mngr);
								
									$('select[name=add_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected',true);
									/*$('select[name=firstla] option[value=' + r.data[0].repo_mngr + ']').attr('selected',true);
								    $('select[name=secondla] option[value=' + r.data[0].secnd_app + ']').attr('selected',true);
				 					$('select[name=repo_mngr] option[value=' + r.data[0].repo_mngr + ']').attr('selected',true);
									$('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected',true);
									$('select[name=id_building] option[value=' + r.data[0].id_building + ']').attr('selected',true);
									$('select[name=id_flr] option[value=' + r.data[0].id_flr + ']').attr('selected',true);
									$('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected',true);
																	
									var id_loc=$('select[name="id_loc"]').val();
									var id_sloc=$('select[name="id_loc"]').val();
									var id_building=$('select[name="id_loc"]').val();
									
							DisplaySubDropDownData(id_loc,'subLocDataForFloor','M_Subloc',function (status){
						if(status)
								{
					SubDropDownDataDisplay(id_sloc,'buildingDataForFloor','M_Building',function (status){
						if(status)
									{
							SubDropDownDataDisplay(id_building,'subForFloor','M_Floor',function (status){
						if(status)
										{
									$('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected',true);
									$('select[name=id_building] option[value=' + r.data[0].id_building + ']').attr('selected',true);
									$('select[name=id_flr] option[value=' + r.data[0].id_flr + ']').attr('selected',true);

										}});	
									
									}});	
								
								}});*/	
					}
},'json');

		}
	if(action == 'Cancel')
	{
		$('#'+DisplayDiv).show();
		$('#'+HideDiv).hide();
	
	}
	if(action == 'Save')
	{
		var t=false;
		t=ValidationForm('submitPurchaseOrder');
		if(t)
		{
				
			$.post('NewPurchaseOrder',{action : 'CreatePOIdDynamic'},function (r){
				
				if(r.PONum)
					{
					bootbox.confirm("Are you sure you want to create this po number:"+r.PONum+" ?", function(result) {
						if(result)
						{
							$('.npo').attr('disabled','disabled');
							var x = $('#submitPurchaseOrder').serialize();
							console.log(x);
							$.post('NewPurchaseOrder',x,function (r){
								if(r.data == 1)
									{
										bootbox.alert(action +" successfully with po no : "+r.POID);
										$( ".newPurchaseOrder" ).trigger( "click" );
									}
								else
								{
									bootbox.alert("Something went wrong , Please try again.");
								}
								$('.npo').removeAttr('disabled');
							},'json');
						}
					});
					
					}
			},'json');
			
			
		
		
		}
		
		
		
		
	}
}});

}

function TaxCalculationForPO(id_tax)
{
var t=true;
	if(id_tax.value != undefined)
		{
			id_tax =id_tax.value;
			if(id_tax == '0')
				{
				t=false;
				$('input[name="tax_prc"]').val('0');
				$('input[name="tax_per"]').val('0');
				var tot = $('input[name="tot"]').val();
				var insurance = $('input[name="insurance"]').val();
				var frieght = $('input[name="frieght"]').val();
				var gr_tot = + tot + parseFloat(insurance) + parseFloat(frieght);
				$('input[name="gr_tot"]').val(gr_tot);
				}
		}
		
	if(t)
		{
	$.post('M_Tax',{action : 'DropDownResult',id:id_tax},function (r){
		
		if(r.data.length > 0 )
			{
			 var tot = $('input[name="tot"]').val();
			 var percent = r.data[0].per_tax;
			 var insurance = $('input[name="insurance"]').val();
				var frieght = $('input[name="frieght"]').val();
			 var gr_tot=  + tot + (parseFloat(tot)*parseFloat((percent)))/100;
			 gr_tot = + gr_tot  + parseFloat(insurance) + parseFloat(frieght);
			 if(tot == undefined || tot == '')
				 {
					 $('input[name="gr_tot"]').val('');
					 $('input[name="tax_per"]').val(percent);
				 }
			 else
				 {
					 $('input[name="tax_per"]').val(percent);
					 $('input[name="gr_tot"]').val(gr_tot.toFixed(2));
					 $('input[name="tax_prc').val(((parseFloat(tot)*parseFloat((percent)))/100).toFixed(2));
				 }
				
			}
		
		
		
	},'json');
	
		}
	
}

function TaxCalculationadd(event)
{
	//alert('Warning : You are not supposed to change the value.');
	var gr_tot =0.00;
	var domElement =$(event.target || event.srcElement);
	
	var id_tax = $('select[name="taxIdadd'+domElement.attr('patelTaxadd')+'"]').val();

console.log(id_tax);

		
	$.post('M_Tax',{action : 'DropDownResult',id:id_tax},function (r){
		
		if(r.data.length > 0 )
			{
			 var percent = r.data[0].per_tax;
			  
			
				 //line item extra charges......
				 var frt_rate = $('input[name="frt_rate'+domElement.attr('patelTaxadd')+'"]').val();
				 var frt_text = $('input[name="frt_text'+domElement.attr('patelTaxadd')+'"]').val();
				 var add_chrg = $('input[name="add_chrg'+domElement.attr('patelTaxadd')+'"]').val();
				 var buy_back = $('input[name="buy_back'+domElement.attr('patelTaxadd')+'"]').val();
				// var add_chrg1 = $('input[name="add_chrg1'+domElement.attr('patelTaxadd')+'"]').val();
				 var add_chrg2 = $('input[name="add_chrg2'+domElement.attr('patelTaxadd')+'"]').val();
					var add_chrg1=0.0;
					add_chrg1=((parseFloat(frt_rate)+parseFloat(frt_text)+parseFloat(add_chrg))*parseFloat(percent))/100;
					// alert(add_chrg1);
			 var tot= $('input[name="tot_prc'+domElement.attr('patelTaxadd')+'"]').val();
			 var grnd_tot=parseFloat(tot)+parseFloat(frt_rate)+parseFloat(frt_text)+parseFloat(add_chrg)+parseFloat(add_chrg1)+parseFloat(add_chrg2)-parseFloat(buy_back);
			// alert(grnd_tot);
			 
			 $('input[name="add_chrg1'+domElement.attr('patelTaxadd')+'"]').val(add_chrg1.toFixed(2));
			 $('input[name="grnd_tot'+domElement.attr('patelTaxadd')+'"]').val(grnd_tot.toFixed(2));
					 
						
					 
					 $('.commonTotal').each(function (){
							if($(this).val() != undefined)
								gr_tot += +$(this).val();
							
						});
					 var bottomtotal = parseFloat($('input[name="frt_rate"]').val()) +parseFloat($('input[name="frt_text"]').val()) +parseFloat($('input[name="add_chrg"]').val())+parseFloat($('input[name="freight_amount"]').val())-parseFloat($('input[name="buy_back"]').val())-parseFloat($('input[name="discount"]').val());
						
						// var others= $('input[name="others"]').val();
						 gr_tot = gr_tot + parseFloat(bottomtotal);
					 	 console.log(gr_tot);
					
					 $('input[name="tot"]').val(gr_tot.toFixed(2));
				
			}
		
		
	},'json');
	
		
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

function getAddPo(event) {
	//var domElement =$(event.target || event.srcElement);
	var ship_to = $('select[name="ship_to"]').val();
	
    $.post('NewPurchaseOrder',{action : 'getAddPo',id:ship_to},function (r){
		
		if(r.data)
			{
				$('textarea[name="add_po"]').val(r.data[0].add1);
				
			}
		
		
	},'json');
    
  }


function CreatePurchaseOrder(action,DisplayDiv,HideDiv,no_quot,no_ind,id_quot)
{
	
	$.post('NewPurchaseOrder',{action : 'Edit',no_quot:no_quot,no_ind:no_ind,id_quot:id_quot},function (r){

		
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
			}
			DisplayDropDownData('M_Loc','ship_to',function (status){
				if(status)
					{}});
			var venDetails = r.data[0].nm_ven+','+r.data[0].cd_ven+', '+
								r.data[0].city+','+r.data[0].state+','+r.data[0].country + '. GST: '+r.data[0].cst;
			var totPrice=0;
			
			
			
			var temp='<td colspan="4" style="width: 50%;font-size:15px;"><b>VENDOR DETAILS</b><br><p>'+venDetails+'.</p></td>'+
			
		
	
				'</td>'+
				/*'<input type="hidden" name="no_po" value="'+r.POID+'">'+*/
				'<input type="hidden" name="no_quot" value="'+no_quot+'">'+
				'<input type="hidden" name="dt_quot" value="'+r.data[0].dtrecquot+'">'+
				'<input type="hidden" name="id_ven" value="'+r.data[0].id_ven+'">'+
				'<input type="hidden" name="id_quot" value="'+r.data[0].id_quot+'">'+
				
				
				
				
				'<input type="hidden" name="no_ind" value="'+no_ind+'">';
			
			var list ='<tr class="tableHeader info">'+
			'<td  style="min-width: 150px;"><strong><center>Model Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
			'<td  style="min-width: 150px;"><strong><center>Model Code<font color="red"> * </font><a href=#></a></center></strong></td>'+
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
				var	remarks='';
				if(params.remarks !=undefined)
					remarks = params.remarks;
					
				list += '<tr class="success">'+
				'<input type="hidden" name="id_prod" value="'+params.id_prod+'">'+
				'<input type="hidden" name="id_grp" value="'+params.id_grp+'">'+
				'<input type="hidden" name="id_sgrp" value="'+params.id_sgrp+'">'+
				'<td  style="min-width: 150px;"><center>'+params.nm_model+'</center></td>'+
				'<td  style="min-width: 150px;"><center>'+params.cd_model+'</center></td>'+
				'<td><center><input type="text" style="width: 150px;" name="item_remarks'+i+'" remarks="'+i+'" value="'+remarks+'"  style="width: 100px;" maxlength="500"></center></td>'+
				'<td><center><input type="text" name="dt_scheduled_dlvry'+i+'"  value="" patelUnPrc="'+i+'"  class="common-validation poDatepickerdl"  style="width: 120px;"  ></center></td>'+
				'<td><center><input type="text"  name="qty'+i+'"  value="'+params.qty+'" style="width:60px;;text-align: right;" class="common-validation" patelUnPrc="'+i+'" onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+
				'<td><center><input type="text" name="un_prc'+i+'"  value="'+params.un_prc+'" patelUnPrc="'+i+'"  style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" ></center></td>'+
				'<td><input type="text" name="others'+i+'"  value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:60px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
				'<td><center>'+
				'<select style="width: 100px;" name="id_tax1'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center>'+
				'<select style="width: 100px;" name="id_tax2'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center><input type="text"  name="tax_val1'+i+'" patelUnPrc="'+i+'" value="'+params.tax_val1+'" onChange="calculateTot(event,\'val_tax1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
				'<td><center><input type="text"  name="tax_val2'+i+'" patelUnPrc="'+i+'" value="'+params.tax_val2+'" onChange="calculateTot(event,\'val_tax2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
				'<td><center><input type="text"  name="buyback'+i+'"  value="'+params.buyback+'" patelUnPrc="'+i+'"  onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="gr_tot'+i+'"  value="'+params.tot_prc+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
				'<input type="hidden" name="count" value="'+i+'">'+	
				
				'</tr>';
				totPrice += parseFloat(params.tot_prc);
				}
			
			totPrice += (parseFloat(params.frt_text) - parseFloat(params.discount));
		
			list += '<tr>'+
			'<td colspan="12"><input type="text" name="oter_text" value="'+params.oter_text+'" style="float:right;"  class="common-validation" ></td>'+
			
			//'<td colspan="19"><b style="float:right;"></b></td>'+
			'<td><center><input type="text" name="frt_text" value="'+params.frt_text+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'frt_text\')" class="common-validation" ></center></td>'+
		'</tr>'+
		
		'<tr>'+
		'<td colspan="12"><b style="float:right;">Discount</b></td>'+
		'<td><center><input type="text" name="discount" value="'+params.discount+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'discount\')" class="common-validation" ></center></td>'+
	'</tr>'+
		'<tr>'+
			'<td colspan="12"><b style="float:right;">Grand Total</b></td>'+
			'<td><center><input type="text" name="tot" value="'+totPrice+'" style="width: 60px;text-align: right" class="common-validation" readonly></center></td>'+
	'</tr>';
	    
					
			
			list += '<tr></tr><tr></tr><tr>'+
		        
         '	</td></tr><tr><td colspan="12">'+
         '	<button type="button" style="margin-left: 400px;" class="btn btn-primary npo" onclick="ControlPurchaseOrder(\'Save\',\'displayNewPurchaseOrder\',\'createPurchaseOrder\',\'\',\'\')">Save </button>&nbsp;&nbsp;'+
		 '	<button type="button" class="btn btn-primary npo" onclick="ControlPurchaseOrder(\'Cancel\',\'displayNewPurchaseOrder\',\'createPurchaseOrder\',\'\',\'\')">cancel </button>'+
		 '	</td>'+
		'</tr>';
			
			DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
				if(status)
				{
					DropDownDataDisplay("M_Tax","taxDataForDropDown12",function (status){
						if(status)
						{
							/*DropDownDataDisplay("M_UOM","uomforSales",function (status){
								if(status)
								{
									
								}});*/
						}});
					
				}});	
			$('#dynamicIdAndVenDetailsForPO').html(temp);
			$('.quotDetailsForPO').html(list);
			
			$('#'+DisplayDiv).show();
			$('#'+HideDiv).hide();
			}
		$( ".poDatepickerdl" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      onSelect: function(selected,evnt) {
		    	  $(this).removeClass('error');
		    	  var dt_po=$('input[name="dt_po"]').val();
		    	  var dt_po1=$('input[name="dt_po"]').val();
		    	  var dt_delv=$(this).val();
		    	  if(dt_po == '')
		    		  {
		    		  	alert('First filled the P.O date.');
		    		  	$('.poDatepicker').focus();
		    		  	$('.poDatepicker').addClass('error');
		    		  	$('.poDatepicker').val('');
		    		  	$(this).val('');
		    		  	exit(0);
		    		  }
		    	  else
		    		  {
		    		  var temp_strt = dt_po.split("/");
						 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
							
						var temp_end = dt_delv.split("/");
						var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
							
						dt_po = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
						dt_delv = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
		    		  
		    		  if(dt_po > dt_delv)
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
		$( ".poDatepicker2" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      onSelect: function(selected,evnt) {
		    	  $(this).removeClass('error');
		    	  var dt_po=$('.poDatepicker').val();
		    	  var dt_po1=$('.poDatepicker').val();
		    	  var dt_delv=$(this).val();
		    	  if(dt_po == '')
		    		  {
		    		  	alert('First filled the P.O date.');
		    		  	$('.poDatepicker').focus();
		    		  	$('.poDatepicker').addClass('error');
		    		  	$('.poDatepicker').val('');
		    		  	$(this).val('');
		    		  	exit(0);
		    		  }
		    	  else
		    		  {
		    		  var temp_strt = dt_po.split("/");
						 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
							
						var temp_end = dt_delv.split("/");
						var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
							
						dt_po = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
						dt_delv = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
		    		  
		    		  if(dt_po > dt_delv)
		    			  {
		    			  	alert('Delivery date should be greater or equal to P.O date : '+dt_po1);
		    			  	$(this).focus();
		    			  	$(this).val('');
		    			  	$(this).addClass('error');
		    			  	exit(0);
		    			  }
		    		  
		    		  }
		    	//  $('input[name="dt_scheduled_dlvry"]').datepicker("setDate", currentDate);
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
								
									if(key == 'id_tax1')
										$('select[name=id_tax1'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									if(key=='id_tax2')
										$('select[name=id_tax2'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									
							}
						}
					
				
				
				
				
			}});
		
		
		
		
		
		
	},'json');


}


function DisplayQuatationForPurchaseOrder()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{

					
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('NewPurchaseOrder',{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="6" class="tableHeader"><center><p class="tableHeaderContent" ">Purchase Order Through Tendering</p></center></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
				'<td><center><strong>Quotation Number<a href=#></a></strong></center></td>'+
				'<td><center><strong>Indent Number</strong></center></td>'+
				'<td><center><strong>Vendor Name </strong></center></td>'+
				
				'<td><center><strong>Approved By</strong></center></td>'+
				'<td><center><strong>Approved Date </strong></center></td>'+
				'<td style="width: 135px;"><strong><center><a href="#">Create PO</a></center></strong></td>'+
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
									
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><center>'+params.dt_approv+'</center></td>'+
									'<td><strong><center><a class="alert" href="#" onclick="ControlPurchaseOrder(\'Create\',\'createPurchaseOrder\',\'displayNewPurchaseOrder\',\''+params.no_quot+'\',\''+params.no_ind+'\',\''+params.id_quot+'\')"> Create PO </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.newPurchaseForDisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.newPurchaseForDisplay').html(list);
			}
		
		
	},'json');

			}});

}



$( ".poDatepicker" ).datepicker({
	yearRange: '1985:2025',
      changeMonth: true,
      changeYear: true,
      dateFormat: "dd/mm/yy",
      autoSize: true,
      altFormat: "dd/mm/yy",
      onSelect: function(selected,evnt) {
    	  $(this).removeClass('error');
    	  var dt_po=$(this).val();
    	  var id_quot=$('input[name="id_quot"]').val();
    	  $(".poDatepicker2").val('');
    	  
    	  $.post('NewPurchaseOrder',{action : 'CheckDate' ,dt_po:dt_po,id_quot:id_quot},function (r){
	    		if(r.data == 0)
	    			{
	    			alert('P.O date should be greater or equal to approve quotation date : '+r.dt_approv);
	    			$(".poDatepicker").focus();
	    			$(".poDatepicker").val('');
	    			$(".poDatepicker").addClass('error');
	    				exit(0);
	    			}
	    		
	    },'json');
    	  
    	  
      }
    });

