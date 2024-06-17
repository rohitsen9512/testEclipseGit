function exportExcel(){
	alert("You are not Authorized to Export to Excel");
}

function CheckSpace(FieldName){
	
	var Field =$('input[name="'+FieldName+'"]').val();
	console.log(Field);
	if(Field.indexOf(' ') >= 0){
		$('input[name="'+FieldName+'"]').val('');
		alert('Space Not Allowed');
		}
		
}


function getDATAFromEMPuser(ids){
	//
	var id=ids.value;
	
	$.post('M_Emp_User', {action : "Display",id:id},function(r){
			
			for(var i = 0; i < r.data.length ; i++)
			{	
					
					
				    DisplaySubDropDownData(r.data[0].id_loc,'SubLocDataForBulkInstallAsset','M_Subloc',function (status){
					if(status)
						{
							
							
						 SubDropDownDataDisplay(r.data[0].id_sloc,'buildingDataForBulkInstallAsset','M_Building',function (status){
					if(status)
						{	
						 SubDropDownDataDisplay(r.data[0].id_building,'FloorForBulkInstallAsset','M_Floor',function (status){
					if(status)
						{	
							/*DisplaySubDropDownData(r.data[0].id_dept,'nmfunction','M_Cost_Center',function (status){
							if(status)
							{*/
									/*DisplaySubDropDownData(r.data[0].id_cc,'subfunction','M_S_Function',function (status){
										if(status)
											{
												SubDropDownDataDisplay(r.data[0].id_s_function,'subbu','M_BU',function (status){
												if(status)
													{	*/
					    $('select[name=id_dept] option[value=' + r.data[0].id_dept + ']').attr('selected',true);
	 					/*$('select[name=id_cc] option[value=' + r.data[0].id_cc + ']').attr('selected',true);
					    $('select[name=id_s_function] option[value=' + r.data[0].id_s_function + ']').attr('selected',true);*/

		               $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected',true);
					   $('select[name=id_subl] option[value=' + r.data[0].id_sloc + ']').attr('selected',true);
                       $('select[name=id_building] option[value=' + r.data[0].id_building + ']').attr('selected',true);
                       $('select[name=id_flr] option[value=' + r.data[0].id_flr + ']').attr('selected',true);
						/*$('select[name=id_bu] option[value=' + r.data[0].id_bu + ']').attr('selected',true);*/
	               $('select[name=repo_mngr] option[value=' + r.data[0].repo_mngr + ']').attr('selected',true);
				$('select[name=firstla] option[value=' + r.data[0].repo_mngr + ']').attr('selected',true);
				   	    }});
						 }});	
						// }});
					}});
						/* }});	
						 }});*/
				
			}
		},'json'); 
	
}

function getDATA(ids){
	
	var id=ids.value;
	$.post('M_Subloc', {action : "Display",id:id},function(r){
			
			for(var i = 0; i < r.data.length ; i++)
			{	
					
					console.log(r.data[0]);
				 	
					$('input[name="gstin_no"]').val(r.data[0].gstin_no_city);	
					$('input[name="dl_no"]').val(r.data[0].dl_no_city);		
					   
		            }
		},'json'); 
		
	
	dispalyLineItemLead('New');
}


function getsrcCode(ids){
	
	var id=ids.value;
	$.post('M_Source', {action : "Display",id:id},function(r){
			
			for(var i = 0; i < r.data.length ; i++)
			{	
					
					console.log(r.data[0]);
				 	
					$('input[name="cd_src"]').val(r.data[0].cd_src);	
						
					   
		            }
		},'json'); 
		
	

}










function SendEmail(){
	var t=false;
	//debugger;
	t = ValidationForm("submitSendEmail");
	if(t){
		$('.sendMailBtn').attr('disabled','disabled');
		var x = $('#submitSendEmail').serialize();
		$.post('UserAccessControle',x,function (r){
			
			if(r.data == 1){
				alert("Mail has been sent successfully.");
				$( ".sendEmail" ).trigger( "click" );
			}
			else
				alert("Somthing went wrong. Please try again.");
				$('.sendMailBtn').removeAttr('disabled');
		},'json');
	}
	
}

function calculateTot(event, name){
debugger;
	var domElement =$(event.target || event.srcElement);
	var totParticular=0.0,total=0.0;
	/*var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;*/
	var intRegex = /^\d+$/;
	var floatRegex = /^\d+$/;
	var str ='';
	console.log(floatRegex);
	var fieldName= name + domElement.attr('patelUnPrc');
	
	$('input[name="'+fieldName+'"]').removeClass('error');
	
	str = $('input[name="'+fieldName+'"]').val();
	
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		
	 var id_tax1 = $('select[name="id_tax1'+domElement.attr('patelUnPrc')+'"]').val();
	if(id_tax1 != '0')
	TaxCalculation(event,"id_tax1","tax_val1",function (status){
		if(status)
			{
				var id_tax2 = $('select[name="id_tax2'+domElement.attr('patelUnPrc')+'"]').val();
				if(id_tax2 != '0')
				TaxCalculation(event,"id_tax2","tax_val2",function (status){
					if(status)
						{
						
						}});
			}});
	
		
	}
	else
		{
			alert('Please enter valid number in the field.');
			$('input[name="'+fieldName+'"]').addClass('error');
			$('input[name="'+fieldName+'"]').focus();
			$('input[name="'+fieldName+'"]').val(0);
			exit(0);
			
		}
	
		}
	 else
		{
		alert('Mandatory Field.');
		$('input[name="'+fieldName+'"]').addClass('error');
		$('input[name="'+fieldName+'"]').focus();
		$('input[name="'+fieldName+'"]').val(0);
		exit(0);
	} 
	
	
}
function calculateTotfordecimal(event, name){
debugger;
	var domElement =$(event.target || event.srcElement);
	var totParticular=0.0,total=0.0;
	/*var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;*/
	var intRegex = /^\d+$/;
    var floatRegex = /^\d+(\.\d{1,2})?$/;


	var str ='';
	console.log(floatRegex);
	var fieldName= name + domElement.attr('patelUnPrc');
	
	$('input[name="'+fieldName+'"]').removeClass('error');
	
	str = $('input[name="'+fieldName+'"]').val();
	
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		
	 var id_tax1 = $('select[name="id_tax1'+domElement.attr('patelUnPrc')+'"]').val();
	if(id_tax1 != '0')
	TaxCalculation(event,"id_tax1","tax_val1",function (status){
		if(status)
			{
				var id_tax2 = $('select[name="id_tax2'+domElement.attr('patelUnPrc')+'"]').val();
				if(id_tax2 != '0')
				TaxCalculation(event,"id_tax2","tax_val2",function (status){
					if(status)
						{
						
						}});
			}});
	
		
	}
	else
		{
			alert('Please enter valid number in the field.');
			$('input[name="'+fieldName+'"]').addClass('error');
			$('input[name="'+fieldName+'"]').focus();
			$('input[name="'+fieldName+'"]').val(0);
			exit(0);
			
		}
	
		}
	 else
		{
		alert('Mandatory Field.');
		$('input[name="'+fieldName+'"]').addClass('error');
		$('input[name="'+fieldName+'"]').focus();
		$('input[name="'+fieldName+'"]').val(0);
		exit(0);
	} 
	
	
}
function addInTotal(name){
	//alert('Warning : You suppose to not change the value.');
	var domElement =$(event.target || event.srcElement);
	var totParticular=0.0,total=0.0;
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	
	$('input[name="'+name+'"]').removeClass('error');
	var gr_tot=0.0;
	var str = $('input[name="'+name+'"]').val();
	debugger;
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		 $('.commonTotal').each(function (){
				if($(this).val() != undefined)
					gr_tot += +$(this).val();
			});
		console.log(gr_tot);
			var tot=(parseFloat($('input[name="frt_text"').val())+gr_tot)-parseFloat($('input[name="discount"').val());
			$('input[name="tot"').val(tot.toFixed(2));	

		
	}else{
		alert('Invalid number.');
		//$('input[name="'+name+'"]').addClass('error');
		$('input[name="'+name+'"]').val(0);
		exit(0);
	
	}
	}
	
}
function TaxCalculation(event,fieldName,taxValColName,callback){
	console.log("hiii");
	var t=false;
	var domElement =$(event.target || event.srcElement);
	var totParticular=0.0,total=0.0;
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	
	$('select[name="'+fieldName+domElement.attr('patelUnPrc')+'"]').removeClass('error');
	
	str = $('select[name="'+fieldName+domElement.attr('patelUnPrc')+'"]').val();
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		var id_tax = $('select[name="'+fieldName+domElement.attr('patelUnPrc')+'"]').val();
		//alert(id_tax + fieldName+domElement.attr('patelUnPrc'));
		if(id_tax != '0')
		{
				
			
			//if(parseFloat(buybackche)<parseFloat(gr_tot_check) ){
		$.post('M_Tax',{action : 'DropDownResult',id:id_tax},function (r){
			
			if(r.data.length > 0 )
				{
					
					 
				 var percent1 = r.data[0].per_tax1;
			     var percent2 = r.data[0].per_tax2;
				 var gr_tot=0.00;
				 
				 var qty = $('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val();
				 var un_prc = $('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val();
				 var other = $('input[name="others'+domElement.attr('patelUnPrc')+'"]').val();
				 var taxVal=0.0;
				 var tot_un_prc= +(parseFloat(qty)*parseFloat(un_prc))+parseFloat(other);
				 taxVal1 = (tot_un_prc*percent1)/100;
				 taxVal2 = (tot_un_prc*percent2)/100;
			//
				 $('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val(taxVal1.toFixed(2));
				 $('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val(taxVal2.toFixed(2));
				 var tax_val1 = $('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val();
				 
				 var tax_val2 = $('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val();
				 var buyback = $('input[name="buyback'+domElement.attr('patelUnPrc')+'"]').val();
				 var buybackche = $('input[name="buyback'+domElement.attr('patelUnPrc')+'"]').val();
				var gr_tot_check = parseFloat(tot_un_prc)+parseFloat(tax_val1)+parseFloat(tax_val2);
			 
			  
				
			if(parseFloat(buyback)<parseFloat(gr_tot_check) ){
				
				 grnd_tot=parseFloat(tot_un_prc)+parseFloat(tax_val1)+parseFloat(tax_val2)-parseFloat(buyback);
				$('input[name="gr_tot'+domElement.attr('patelUnPrc')+'"]').val(grnd_tot.toFixed(2));
				
			}
			else{
				$('input[name="buyback'+domElement.attr('patelUnPrc')+'"]').val(0);
				alert('Please enter the discount equal to or less than Grand Total amount.');
			}	
				
				
				 debugger;
console.log(grnd_tot);
				 t=true;
				}
			$('.commonTotal').each(function (){
				if($(this).val() != undefined)
					total += +$(this).val();
				
			});
			var tot=(parseFloat(total));
			$('input[name="tot"').val(tot.toFixed(2));
			$('#grandtotinword').text(number2text(parseFloat(total)));
			
			callback(t);
		},'json');
		//}if
		/*else{
			alert('Discount must be less then total amount.');	
			
		}*/
		}
		//calculateTot(event);
		
	}else{
		alert('Invalid number.');
		//$('input[name="'+name+'"]').addClass('error');
		$('input[name="'+name+'"]').val(0);
		exit(0);
	
	}
	
	
	}
	
}

//for extend
function calculateTotextendld(event, name){
debugger;
	var domElement =$(event.target || event.srcElement);
	var totParticular=0.0,total=0.0;
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	var str ='';
	console.log(floatRegex);
	var fieldName= name + domElement.attr('patelUnPrc');
	
	$('input[name="'+fieldName+'"]').removeClass('error');
	
	str = $('input[name="'+fieldName+'"]').val();
	
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		
	 var id_tax1 = $('select[name="id_tax1'+domElement.attr('patelUnPrc')+'"]').val();
	if(id_tax1 != '0')
	TaxCalculationextendld(event,"id_tax1","tax_val1",function (status){
		if(status)
			{
				var id_tax2 = $('select[name="id_tax2'+domElement.attr('patelUnPrc')+'"]').val();
				if(id_tax2 != '0')
				TaxCalculationextendld(event,"id_tax2","tax_val2",function (status){
					if(status)
						{
						
						}});
			}});
	
		
	}
	else
		{
			alert('Invalid number.');
			$('input[name="'+fieldName+'"]').addClass('error');
			$('input[name="'+fieldName+'"]').focus();
			$('input[name="'+fieldName+'"]').val(0);
			exit(0);
			
		}
	
		}
	 else
		{
		alert('Mandatory Field.');
		$('input[name="'+fieldName+'"]').addClass('error');
		$('input[name="'+fieldName+'"]').focus();
		$('input[name="'+fieldName+'"]').val(0);
		exit(0);
	} 
	
	
}



function TaxCalculationextendld(event,fieldName,taxValColName,callback){
	console.log("hiii");
	var t=false;
	var domElement =$(event.target || event.srcElement);
	var totParticular=0.0,total=0.0;
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	
	$('select[name="'+fieldName+domElement.attr('patelUnPrc')+'"]').removeClass('error');
	
	str = $('select[name="'+fieldName+domElement.attr('patelUnPrc')+'"]').val();
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		var id_tax = $('select[name="'+fieldName+domElement.attr('patelUnPrc')+'"]').val();
		//alert(id_tax + fieldName+domElement.attr('patelUnPrc'));
		if(id_tax != '0')
		{
				
			
			//if(parseFloat(buybackche)<parseFloat(gr_tot_check) ){
		$.post('M_Tax',{action : 'DropDownResult',id:id_tax},function (r){
			
			if(r.data.length > 0 )
				{
					
				 
				 var percent1 = r.data[0].per_tax1;
			     var percent2 = r.data[0].per_tax2;
				 var gr_tot=0.00;
				 
				 var qty = $('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val();
				 var un_prc = $('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val();
				  var cylndr_fill_mt = $('input[name="cylndr_fill_mt'+domElement.attr('patelUnPrc')+'"]').val();
			      var other = $('input[name="others'+domElement.attr('patelUnPrc')+'"]').val();
				 var taxVal=0.0;
				 var tot_un_prc= +(parseFloat(1)*parseFloat(un_prc))+parseFloat(other)+parseFloat(cylndr_fill_mt);
				 taxVal1 = (tot_un_prc*percent1)/100;
				 taxVal2 = (tot_un_prc*percent2)/100;
			debugger;	
				 $('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val(taxVal1.toFixed(2));
				 $('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val(taxVal2.toFixed(2));
				 var tax_val1 = $('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val();
				 
				 var tax_val2 = $('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val();
				 var buyback = $('input[name="buyback'+domElement.attr('patelUnPrc')+'"]').val();
				 var buybackche = $('input[name="buyback'+domElement.attr('patelUnPrc')+'"]').val();
				var gr_tot_check = parseFloat(tot_un_prc)+parseFloat(tax_val1)+parseFloat(tax_val2);
			 
			  
				
			if(parseFloat(buyback)<parseFloat(gr_tot_check) ){
				
				 grnd_tot=parseFloat(tot_un_prc)+parseFloat(tax_val1)+parseFloat(tax_val2)-parseFloat(buyback);
				$('input[name="gr_tot'+domElement.attr('patelUnPrc')+'"]').val(grnd_tot.toFixed(2));
				
			}
			else{
				$('input[name="buyback'+domElement.attr('patelUnPrc')+'"]').val(0);
				alert('Please enter the discount equal to or less than Grand Total amount.');
			}	
				
				
				 
console.log(grnd_tot);
				 t=true;
				}
		/*	$('.commonTotal').each(function (){
				if($(this).val() != undefined)
					total += +$(this).val();
				
			});*/
			/*var tot=(parseFloat(total));
			$('input[name="tot"').val(tot.toFixed(2));
			$('#grandtotinword').text(number2text(parseFloat(total)));*/
			
			callback(t);
		},'json');
	
		}
		
		
	}else{
		alert('Invalid number.');
		//$('input[name="'+name+'"]').addClass('error');
		$('input[name="'+name+'"]').val(0);
		exit(0);
	
	}
	
	
	}
	
}

function calculateTotlead(event, name){
 
	var domElement =$(event.target || event.srcElement);
	var totParticular=0.0,total=0.0;
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	var str ='';
	console.log(floatRegex);
	var fieldName= name + domElement.attr('patelUnPrc');
	
	$('input[name="'+fieldName+'"]').removeClass('error');
	
	str = $('input[name="'+fieldName+'"]').val();
	
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		
	 var id_tax1 = $('select[name="id_tax1'+domElement.attr('patelUnPrc')+'"]').val();
	if(id_tax1 != '0')
	TaxCalculationlead(event,"id_tax1","tax_val1",function (status){
		if(status)
			{
				var id_tax2 = $('select[name="id_tax2'+domElement.attr('patelUnPrc')+'"]').val();
				if(id_tax2 != '0')
				TaxCalculationlead(event,"id_tax2","tax_val2",function (status){
					if(status)
						{
						
						}});
			}});
	
		
	}
	else
		{
			alert('Invalid number.');
			$('input[name="'+fieldName+'"]').addClass('error');
			$('input[name="'+fieldName+'"]').focus();
			$('input[name="'+fieldName+'"]').val(0);
			exit(0);
			
		}
	
		}
	 else
		{
		alert('Mandatory Field.');
		$('input[name="'+fieldName+'"]').addClass('error');
		$('input[name="'+fieldName+'"]').focus();
		$('input[name="'+fieldName+'"]').val(0);
		exit(0);
	} 
	
	
}


function TaxCalculationlead(event,fieldName,taxValColName,callback){
	var t=false;
	var domElement =$(event.target || event.srcElement);
	debugger;
	var totParticular=0.0,total=0.0;
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	
	$('select[name="'+fieldName+domElement.attr('patelUnPrc')+'"]').removeClass('error');
	
	str = $('select[name="'+fieldName+domElement.attr('patelUnPrc')+'"]').val();
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		var id_tax = $('select[name="'+fieldName+domElement.attr('patelUnPrc')+'"]').val();
		//alert(id_tax + fieldName+domElement.attr('patelUnPrc'));
		if(id_tax != '0')
		{
		$.post('M_Tax',{action : 'DropDownResult',id:id_tax},function (r){
			
			if(r.data.length > 0 )
				{
				 var percent1 = r.data[0].per_tax1;
			     var percent2 = r.data[0].per_tax2;
				 var gr_tot=0.00;
				 
				 //var qty = $('input[name="quantity'+domElement.attr('patelUnPrc')+'"]').val();
				 var un_prc = $('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val();
				 var cylndr_fill_mt = $('input[name="cylndr_fill_mt'+domElement.attr('patelUnPrc')+'"]').val();
				 var other = $('input[name="others'+domElement.attr('patelUnPrc')+'"]').val();
				 var taxVal=0.0;
				 var tot_un_prc= +(parseFloat(1)*parseFloat(un_prc))+parseFloat(other)+parseFloat(cylndr_fill_mt);
				 taxVal1 = (tot_un_prc*percent1)/100;
				 taxVal2 = (tot_un_prc*percent2)/100;
			//
				 $('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val(taxVal1.toFixed(2));
				 $('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val(taxVal2.toFixed(2));
				 var tax_val1 = $('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val();
				 
				 var tax_val2 = $('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val();
				// var rfnd_charges = $('input[name="refnd'+domElement.attr('patelUnPrc')+'"]').val();
			     //var tr_charges = $('input[name="tr_chrg'+domElement.attr('patelUnPrc')+'"]').val();
				 var buyback = $('input[name="buyback'+domElement.attr('patelUnPrc')+'"]').val();
				 grnd_tot=parseFloat(tot_un_prc)+parseFloat(tax_val1)+parseFloat(tax_val2)-parseFloat(buyback);;
				 $('input[name="gr_tot'+domElement.attr('patelUnPrc')+'"]').val(grnd_tot.toFixed(2));
                  //console.log(grnd_tot);
				 t=true;
				}
			debugger;
			$('.commonTotal').each(function (){
				if($(this).val() != undefined)
					total += +$(this).val();
				
			});
			var tot=(parseFloat(total));
					$('input[name="tot"').val(tot.toFixed(2));
			 $('input[name="tot_tot"').val(tot.toFixed(2));
			 var refund_amt= $('input[name="refund_amt"]').val();
        var  trnsprt_amt=$('input[name="trnsprt_amt"]').val();      
	
    
     var  refund=parseFloat(trnsprt_amt)+parseFloat(refund_amt)+parseFloat(tot);
      
				var tt=	$('input[name="tot"').val(parseFloat(refund).toFixed(2));
			$('#grandtotinword').text(number2text(tot.toFixed(2)));
			
			
			callback(t);
		},'json');
		}
		//calculateTot(event);
		
	}else{
		alert('Invalid number.');
		//$('input[name="'+name+'"]').addClass('error');
		$('input[name="'+name+'"]').val(0);
		exit(0);
	
	}
	
	
	}
	
}


function calculateTotRefillead(event, name){
 
	var domElement =$(event.target || event.srcElement);
	var totParticular=0.0,total=0.0;
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	var str ='';
	console.log(floatRegex);
	var fieldName= name + domElement.attr('patelUnPrc');
	
	$('input[name="'+fieldName+'"]').removeClass('error');
	         debugger;
	str = $('input[name="'+fieldName+'"]').val();
	
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		
	 var id_tax1 = $('select[name="id_tax1'+domElement.attr('patelUnPrc')+'"]').val();

	if(id_tax1 != '0')
	TaxCalculationRefilllead(event,"id_tax1","tax_val1",function (status){
		if(status)
			{
				//debugger;
				var id_tax2 = $('select[name="id_tax2'+domElement.attr('patelUnPrc')+'"]').val();
				if(id_tax2 != '0')
				TaxCalculationRefilllead(event,"id_tax2","tax_val2",function (status){
					if(status)
						{
						
						}});
			}});
	
		
	}
	else
		{
			alert('Invalid number.');
			$('input[name="'+fieldName+'"]').addClass('error');
			$('input[name="'+fieldName+'"]').focus();
			$('input[name="'+fieldName+'"]').val(0);
			exit(0);
			
		}
	
		}
	 else
		{
		alert('Mandatory Field.');
		$('input[name="'+fieldName+'"]').addClass('error');
		$('input[name="'+fieldName+'"]').focus();
		$('input[name="'+fieldName+'"]').val(0);
		exit(0);
	} 
	
	
}


function TaxCalculationRefilllead(event,fieldName,taxValColName,callback){
	var t=false;
	var domElement =$(event.target || event.srcElement);
	//debugger;
	var totParticular=0.0,total=0.0;
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	
	$('select[name="'+fieldName+domElement.attr('patelUnPrc')+'"]').removeClass('error');
	
	str = $('select[name="'+fieldName+domElement.attr('patelUnPrc')+'"]').val();
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		var id_tax = $('select[name="'+fieldName+domElement.attr('patelUnPrc')+'"]').val();
		//alert(id_tax + fieldName+domElement.attr('patelUnPrc'));
		if(id_tax != '0')
		{
		$.post('M_Tax',{action : 'DropDownResult',id:id_tax},function (r){
			
			if(r.data.length > 0 )
				{
				 var percent1 = r.data[0].per_tax1;
			     var percent2 = r.data[0].per_tax2;
				 var gr_tot=0.00;
				 
				 //var qty = $('input[name="quantity'+domElement.attr('patelUnPrc')+'"]').val();
				// var un_prc = $('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val();
				 var cylndr_fill_mt = $('input[name="cylndr_fill_mt'+domElement.attr('patelUnPrc')+'"]').val();
				 var other = $('input[name="others'+domElement.attr('patelUnPrc')+'"]').val();
				 var taxVal=0.0;
				 var tot_un_prc= +(parseFloat(1)*parseFloat(cylndr_fill_mt))+parseFloat(other);
				 taxVal1 = (tot_un_prc*percent1)/100;
				 taxVal2 = (tot_un_prc*percent2)/100;
			//
				 $('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val(taxVal1.toFixed(2));
				 $('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val(taxVal2.toFixed(2));
				 var tax_val1 = $('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val();
				 
				 var tax_val2 = $('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val();
				
				 var buyback = $('input[name="buyback'+domElement.attr('patelUnPrc')+'"]').val();
				 grnd_tot=parseFloat(tot_un_prc)+parseFloat(tax_val1)+parseFloat(tax_val2)-parseFloat(buyback);;
				 $('input[name="gr_tot'+domElement.attr('patelUnPrc')+'"]').val(grnd_tot.toFixed(2));
                  //console.log(grnd_tot);
				 t=true;
				}
			debugger;
			$('.commonTotal').each(function (){
				if($(this).val() != undefined)
					total += +$(this).val();
				
			});
			var tot=(parseFloat(total));
					$('input[name="tot"').val(tot.toFixed(2));
			 $('input[name="tot_tot"').val(tot.toFixed(2));
			 //var refund_amt= $('input[name="refund_amt"]').val();
        var  trnsprt_amt=$('input[name="trnsprt_amt"]').val();      
	
    
     var  refund=parseFloat(trnsprt_amt)+parseFloat(tot);
      
				var tt=	$('input[name="tot"').val(parseFloat(refund).toFixed(2));
			$('#grandtotinword').text(number2text(tot.toFixed(2)));
			
			
			callback(t);
		},'json');
		}
		//calculateTot(event);
		
	}else{
		alert('Invalid number.');
		//$('input[name="'+name+'"]').addClass('error');
		$('input[name="'+name+'"]').val(0);
		exit(0);
	
	}
	
	
	}
	
}



/*function addinTotld(name){
debugger;
		  var refund_amtadjst= $('input[name="refund_amt_adjust"]').val();
        var  tot_refamt=$('input[name="tot_ref_amt"]').val();      
	
    var   tot1=$('input[name="refund_amount"]').val();
     var  refund=parseFloat(refund_amtadjst)+parseFloat(tot_refamt)-parseFloat(tot1);
      
					$('input[name="refund_amt"').val(parseFloat(refund).toFixed(2));
       //$('#grandtotinword').text(number2text(parseFloat(refund).toFixed(2)));
		 
}*/

function addinTotLead(name){

		  var total= $('input[name="refund_amt"]').val();
        var  transport=$('input[name="trnsprt_amt"]').val();      
	
    var   tot1=$('input[name="tot_tot"]').val();
     var  refund=parseFloat(transport)+parseFloat(total)+parseFloat(tot1);
      
					$('input[name="tot"').val(parseFloat(refund).toFixed(2));
       $('#grandtotinword').text(number2text(parseFloat(refund).toFixed(2)));
		 
}
function CheckInvoiceQty(event) {
	var domElement =$(event.target || event.srcElement);
	var store_qty = $('input[name="in_stoc_qty'+domElement.attr('patelUnPrc')+'"]').val();
	 var qty = $('input[name="quantity'+domElement.attr('patelUnPrc')+'"]').val();
	
		if(parseFloat(qty)>parseFloat(store_qty))
			{
			bootbox.alert("Quantity should be less that Store Quantity");
			$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val(0);
			}
			
  }

function CheckInvoice(event) {
	var domElement =$(event.target || event.srcElement);
	var store_qty = $('input[name="in_stoc_qty'+domElement.attr('patelUnPrc')+'"]').val();
	 var qty = $('input[name="quantity'+domElement.attr('patelUnPrc')+'"]').val();
	
	
		var stocQ = parseFloat(store_qty)-parseFloat(qty);	
        
        	 $('input[name="in_stoc_qty'+domElement.attr('patelUnPrc')+'"]').val(stocQ.toFixed(2));

 }




function DropDownDataDisplayForTax2(servletName,dropDownId,callback)
{
	var t =false;
	var list= '';
	$.post(servletName,{action : 'DropDownResultForTax2'},function (r){
		
		if(r.data)
			{
			t=true;
			if(servletName == 'M_Tax')
				 list= '<option value=""> Select</option>';
			else 
				list= '<option value=""> Select</option>';
			
				for(var i = 0; i < r.data.length ; i++)
				{
				
				
				for (var key in r.data[i])
		        {
					id=r.data[i][key];
					break;
		        }
				for (var key in r.data[i])
		        {
					val=r.data[i][key];
					
		        }
				list = list + '<option value="'+val+'"> '+id+'</option>';
				//list = list + '<option value="'+id+'"> '+ val+'</option>';
				
				}
				if(dropDownId == 'patelTax2')		
					$('.patelTax2').html(list);	
			
			
				$('#'+dropDownId).html(list);
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		if(callback) callback(t);
	},'json');

}


function HideShow(val)
{
	var value =val.value;
	if(value == undefined)
		value = val;
	
	if(value == 'SUPER' || value == 'FINANCE' || value == 'FDIRECTOR' || value == 'IAUDIT' || value == 'PAPPRV' || value == 'PRPOCODNTR' || value == 'SRMGRHEAD' || value == 'PHEAD')
		$('.hideShow').hide();
	else
		$('.hideShow').show();		
}


function GetDropDownDataForAssetOwner(dropDownId , callback)
{
	var t=false;
	$.post("UserAccessControle",{action : 'AssetOwnerDropDownData'},function (r){
			
			if(r.data.length > 0)
				{
					var list= '<option value=""> Select</option>';
					var id='',val='';
					for(var i = 0; i < r.data.length ; i++)
					{
						
						for (var key in r.data[i])
				        {
							val=r.data[i][key];
							id = key;
				        }
						list = list + '<option value="'+id+'"> '+val+'</option>';
					}
				
				
					$('#'+dropDownId).html(list);
					t=true;
				}
			callback(t);
	},'json');
}


function LoginAccess(UserType , fileName , classNameForHide)
{
	
	if(UserType!='Super'){
		
		$('.'+classNameForHide).hide();
		$.post('UserAccessControle', {fileName : fileName , action : "LoginAccess"},function(r){
			debugger;
			console.log(r.data);
			for(var i = 0; i < r.data.length ; i++)
			{	
				console.log(r.data[i]);
				for (var key in r.data[i])
					{
						$('.'+r.data[i][key]).show();
						console.log(r.data[i][key]);
					}
			}
		},'json'); 
	}
}


function InsuranceAndFrightCheck(InputFieldName)
{
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	var str =$('input[name="'+InputFieldName+'"]').val();
	$('input[name="'+InputFieldName+'"]').removeClass('error');
	if(str != '' || str != undefined)
	{
if(intRegex.test(str) || floatRegex.test(str)) {
	
	 var insurance = $('input[name="insurance"]').val();
		var frieght = $('input[name="frieght"]').val();
		 var tot = $('input[name="tot"]').val();
			
			 var gr_tot= + parseFloat(insurance) + parseFloat(frieght) + parseFloat(tot);
			 $('input[name="gr_tot"]').val(gr_tot);
}
else
	{
	if(str != '')
		{
			alert('Invalid number.');
			$('input[name="'+InputFieldName+'"]').addClass('error');
			$('input[name="'+InputFieldName+'"]').focus();
			exit(0);
		}
	}
	}
	
}

function DisplayDropDownDataForGroup(servletName,dropDownId,type,callback)
{
	var t =false;
	var list= '';
	var type1 = type.value;
	if(type.value == undefined)
		{
		type1 = type;
		}
	
	$.post(servletName,{action : 'DropDownResult',type:type1},function (r){
		//alert();
		if(r.data)
			{
			if(dropDownId == ('assetDivForStoreAssetProcReport') || dropDownId == ('assetDivForMaintenanceReport') ||  dropDownId == ('assetGrpForTransferReport') || dropDownId == ('assetDivForAssetHReport') || dropDownId == ('assetDivForStoreAssetReport') || (dropDownId == 'assetDivForMonthlyFaReport') || (dropDownId == 'assetDivForFaYearlyReport') || (dropDownId == 'typeIdForFaYearlyReport') || (dropDownId == 'assetDivForStoreAssetLReport') || (dropDownId == 'assetDivFordeptReport') || (dropDownId == 'assetGrpForTransferReport11'))
				list= '<option value="All"> All</option>';
			else
			 list= '<option value=""> Select</option>';
			if(dropDownId ==('assetDivForYearlyDprn') || dropDownId ==('assetDivForMonthlyDprn'))
				list ='';
				t=true;
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				for (var key in r.data[i])
		        {
					id=r.data[i][key];
					break;
		        }
				for (var key in r.data[i])
		        {
					val=r.data[i][key];
					
		        }
				list = list + '<option value="'+id+'"> '+val+'</option>';
				}
			if(dropDownId=='groupDrop'){
				$('.groupdrop').html(list);
			}
			else if(dropDownId == 'group')
				$('.group').html(list);
			else{
				$('#'+dropDownId).html(list);
			}
			
				$('#'+dropDownId).focus();
			}
		/*else
			{
				bootbox.alert("Try again.");
			}*/
		callback(t);
	},'json');

}



function DisplayDropDownDataForLGroup(servletName,dropDownId,type,callback)
{
	var t =false;
	var list= '';
	var type1 = type.value;
	if(type.value == undefined)
		{
		type1 = type;
		}
	
	$.post(servletName,{action : 'DropDownResult',type:type1},function (r){
		
		if(r.data)
			{
			if(dropDownId == ('assetDivForStoreAssetProcReport') || dropDownId == ('assetDivForMaintenanceReport') || dropDownId == ('assetDivForAssetHReport') || dropDownId == ('assetDivForStoreAssetReport') || (dropDownId == 'assetDivForMonthlyFaReport') || (dropDownId == 'assetDivForFaYearlyReport') || (dropDownId == 'typeIdForFaYearlyReport') || (dropDownId == 'assetDivForStoreAssetLReport') || (dropDownId == 'assetDivFordeptReport'))
				list= '<option value="All"> All</option>';
			else
			 list= '<option value=""> Select</option>';
			if(dropDownId ==('assetDivForYearlyDprn') || dropDownId ==('assetDivForMonthlyDprn'))
				list ='';
				t=true;
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				for (var key in r.data[i])
		        {
					id=r.data[i][key];
					break;
		        }
				for (var key in r.data[i])
		        {
					val=r.data[i][key];
					
		        }
				list = list + '<option value="'+id+'"> '+val+'</option>';
				}
			
			
				$('#'+dropDownId).html(list);
				$('#'+dropDownId).focus();
			}
		/*else
			{
				bootbox.alert("Try again.");
			}*/
		callback(t);
	},'json');

}




function DisplayShortedVendorDataFor(servletName,dropDownId,type_ven,searchWord)
{
var t =false;
var list='';
$.post(servletName,{action : 'MultiDropDownResult',type_ven:type_ven,searchWord:searchWord},function (r){

if(r.data)
{
t=true;
if(dropDownId != 'assetDivForRequestQuotation')
list= '<option value="0"> Select</option>';

for(var i = 0; i < r.data.length ; i++)
{


for (var key in r.data[i])
       {
id=r.data[i][key];
break;
       }
for (var key in r.data[i])
       {
val=r.data[i][key];

       }
list = list + '<option value="'+id+'"> '+val+'</option>';
}


$('#'+dropDownId).html(list);

}
else
{
bootbox.alert("Try again.");
}

},'json');

}

function changeContent(pageName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	$('.mainMenuContent').removeClass('active');
	$('.'+pageName).addClass('active');
	if(pageName =="Biding")
		{	
			$('#mainBody').load('Auction/AuctionMaster.jsp');
			
		}
	
	if(pageName =="Master")
		{	
			$('#mainBody').load('Master/master.jsp');
			
		}
		
		if(pageName =="Partner")
		{	
			//alert("hello");
			$('#mainBody').load('Master/Partner_Profile.jsp');
			
		}
	if(pageName =="Purchase")
		{	
			$('#mainBody').load('Purchase/purchase_master.jsp');
		}
	if(pageName =="GRN")
		{	
			$('#mainBody').load('GRN/grn_master.jsp');
		}
	if(pageName =="Asset")
		{	
		
			$('#mainBody').load('Assets/asset_master.jsp');
		}
	if(pageName =="Tagging")
		{	
			$('#mainBody').load('Tagging/tagging_master.jsp');
		}
	if(pageName =="Depreciation")
		{	
			$('#mainBody').load('Depreciation/depreciation_master.jsp');
		}
	if(pageName =="Report")
		{	
			$('#mainBody').load('Report/report_master.jsp');
		}
	if(pageName =="Inventory")
	{	
		$('#mainBody').load('Inventory/inventory_master.jsp');
	}
	if(pageName =="Transfer")
		{	
			$('#mainBody').load('Transfer/transfer_master.jsp');
		}
	if(pageName =="SEZ")
	{	
		$('#mainBody').load('SEZ/sez_master.jsp');
	}
	if(pageName =="ChangePassword")
	{	
		$('#mainBody').load('modal.jsp');
	}
	if(pageName =="Dashboard")
	{	
		$('#mainBody').load('Dashboard/dashboard_master.jsp');
	}
	if(pageName =="HelpDesk")
	{	
		$('#mainBody').load('HelpDesk/help_desk.jsp');
	}
	if(pageName =="Store")
	{	
		$('#mainBody').load('Store/Store.jsp');
	}
			}
});
}

function SessionCheck(callback)
{
	var t =true;
	debugger;
	$.post('Logout',function (r){
	    debugger;
	    if(r.ses == false){
	    Swal.fire({
								position: 'center',
								icon: 'error',
								title: 'Use session is expired. Please re-login in the site.',
								//showConfirmButton: false,
								timer: 3000
							})
	    }
		
		if(r.ses == false)
			{
				debugger;
								
									
										//alert("Session Expired. Please Login");
										       t =true;
										    	//parent.location.href = "./index.html";
										    	parent.location.href = "./index.html";
										    	
						    				}
		callback(t);
	},'json');
}

function MakeAllCheckCommonFun(Action , ClassName)
{
	
	if(Action == 'DeselectAll')
	{
		$('.'+ClassName).each(function (){
			
			$(this).prop('checked', false);
			
			});
	}	
	else
		{
		$('.'+ClassName).each(function (){
			
			$(this).prop('checked', true);
			
			});
		
		}
}


function ShowRowColumn(action , ClassName)
{
  
	if(action.value =='Yes' || action.value =='CAL' || action.value =='PM' || action =='CAL' || action =='PM' || action =='Yes' || action =='A' || action =='W' || action.value =='A' || action.value =='W'  || action.value =='UL')
		{
		debugger;
			$('.'+ClassName).show();
				$("#amcyearwarr").show();	
				$("#waramcday").show();
				$(".amcAddToStockCommonClass").show();
			$('input[name="dt_amc_start"]').attr('data-valid','mand');
	    $('input[name="dt_amc_exp"]').attr('data-valid','mand');
	    $('input[name="warr_amc_day"]').attr('data-valid','mand');
				
		}
	else if(action.value == 'No' || action.value =='O' || action.value =='NUL')
		{
		
		$('input[name="dt_amc_start"]').val('');
			
	    $('input[name="dt_amc_exp"]').val('');
	    $('input[name="warr_amc_day"]').val('');
		$("#waramcday").hide();
		$("#startdateamc").hide();
		$("#amcYeardatepicker").hide();
		$("#endatefield").hide();
		$("#endateamc").hide();
		$("#amcyearwarr").hide();	
		
			$('.'+ClassName).hide();
	$('input[name="dt_amc_start"]').removeAttr('data-valid','mand');
			
	    $('input[name="dt_amc_exp"]').removeAttr('data-valid','mand');
	    $('input[name="warr_amc_day"]').removeAttr('data-valid','mand');
		}
	

}














function ShowRowColLead(event , Id,Id1,Id2,Id3,Id4,Id5,Id6,Id7,Id8)
{
	debugger;
	
	  var domElement =$(event.target || event.srcElement);
      var IdName= Id + domElement.attr('patelUnPrc');
      var IdName1= Id1 + domElement.attr('patelUnPrc');
        var IdName2= Id2 + domElement.attr('patelUnPrc');
      var IdName3= Id3 + domElement.attr('patelUnPrc');
 var IdName4 =Id4 + domElement.attr('patelUnPrc');
 var IdName5 =Id5 + domElement.attr('patelUnPrc');
 var IdName6 =Id6 + domElement.attr('patelUnPrc');
 var IdName7 =Id7 + domElement.attr('patelUnPrc');
 var IdName8 =Id8 + domElement.attr('patelUnPrc');
	 var value = $('select[name="typ_service'+domElement.attr('patelUnPrc')+'"]').val();
       var id_prod=$('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val();
 
	if(value =='Rental')
		{
		/*	$('input[name="rental_day'+domElement.attr('patelUnPrc')+'"]').val('');
			$('input[name="dt_exp_rent'+domElement.attr('patelUnPrc')+'"]').val('');*/
			$('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val('0.00');
			     $('select[name=id_tax1'+domElement.attr('patelUnPrc')+'] option[value=' +''+ ']').attr('selected',true);
              $('select[name=id_tax2'+domElement.attr('patelUnPrc')+'] option[value=' +''+ ']').attr('selected',true);  
                $('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val('0.00');
				 $('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val('0.00');
				
				//$('input[name="refnd'+domElement.attr('patelUnPrc')+'"]').val();
			     //$('input[name="tr_chrg'+domElement.attr('patelUnPrc')+'"]').val();
				$('input[name="buyback'+domElement.attr('patelUnPrc')+'"]').val('0.00');
			
				 $('input[name="gr_tot'+domElement.attr('patelUnPrc')+'"]').val('0.00');
		
			$('#'+IdName).show();
			$('#'+IdName1).hide();
		}
	else if(value == 'Sale')
		{
     
           $('input[name="rental_day'+domElement.attr('patelUnPrc')+'"]').removeClass('error');
           $('input[name="rental_day'+domElement.attr('patelUnPrc')+'"]').val('');
         	if(id_prod==''){
	       
			//$('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val('');
           $('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val('0.00');
           $('#'+IdName).hide();
			$('#'+IdName1).hide();
			
			 
			$('#'+IdName2).show();
			
		   $('#'+IdName3).show();
			$('#'+IdName4).show();
			$('#'+IdName5).hide();
			$('#'+IdName6).show();
				$('#'+IdName7).hide();
				$('#'+IdName8).show();
	            
          }	
          else{
	        $('input[name="rental_day'+domElement.attr('patelUnPrc')+'"]').val('');
			$('input[name="dt_exp_rent'+domElement.attr('patelUnPrc')+'"]').val('');
	        $('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val('');
           $('input[name="mfr' + domElement.attr('patelUnPrc') + '"]').val('');			 
		    $('input[name="ds_product' + domElement.attr('patelUnPrc') + '"]').val('');
		    $('input[name="un_prc' + domElement.attr('patelUnPrc') + '"]').val('0.00');
		    $('input[name="in_stoc_qty' + domElement.attr('patelUnPrc') + '"]').val('0');	       
            $('select[name=sr_no'+domElement.attr('patelUnPrc')+'] option[value=' +''+ ']').attr('selected',true);
      $('select[name=id_tax1'+domElement.attr('patelUnPrc')+'] option[value=' +''+ ']').attr('selected',true);
$('select[name=id_tax2'+domElement.attr('patelUnPrc')+'] option[value=' +''+ ']').attr('selected',true);            
$('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val('0.00');
				 $('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val('0.00');
				
				//$('input[name="refnd'+domElement.attr('patelUnPrc')+'"]').val();
			   //  $('input[name="tr_chrg'+domElement.attr('patelUnPrc')+'"]').val();
				$('input[name="buyback'+domElement.attr('patelUnPrc')+'"]').val('0.00');
			
				 $('input[name="gr_tot'+domElement.attr('patelUnPrc')+'"]').val('0.00');
          
			
			 $('#'+IdName).hide();
			$('#'+IdName1).hide();
			$('#'+IdName2).show();
			
		   $('#'+IdName3).show();
			$('#'+IdName4).show();
			$('#'+IdName5).hide();
			$('#'+IdName6).show();
				$('#'+IdName7).hide();
					$('#'+IdName8).show();
			
			ShowunitPrice(id_prod,idloc,idsloc)
	         //changeEventHandlerprodd('',id_prod,idloc,idsloc)
           }


			
		}else if(value == 'Refill'){
			
				        $('input[name="rental_day'+domElement.attr('patelUnPrc')+'"]').val('');
			$('input[name="dt_exp_rent'+domElement.attr('patelUnPrc')+'"]').val('');
	        $('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val('');
           $('input[name="mfr' + domElement.attr('patelUnPrc') + '"]').val('');			 
		    $('input[name="ds_product' + domElement.attr('patelUnPrc') + '"]').val('');
		    $('input[name="un_prc' + domElement.attr('patelUnPrc') + '"]').val('0.00');
		    $('input[name="in_stoc_qty' + domElement.attr('patelUnPrc') + '"]').val('0');	       
            $('select[name=sr_no'+domElement.attr('patelUnPrc')+'] option[value=' +''+ ']').attr('selected',true);
      $('select[name=id_tax1'+domElement.attr('patelUnPrc')+'] option[value=' +''+ ']').attr('selected',true);
$('select[name=id_tax2'+domElement.attr('patelUnPrc')+'] option[value=' +''+ ']').attr('selected',true);            
$('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val('0.0');
				 $('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val('0.00');
				
			
				$('input[name="buyback'+domElement.attr('patelUnPrc')+'"]').val('0.00');
			
				 $('input[name="gr_tot'+domElement.attr('patelUnPrc')+'"]').val('0.00');
			
			 
		
		  $('#'+IdName).hide();
			$('#'+IdName1).hide();
			$('#'+IdName2).hide();
			
		   $('#'+IdName3).hide();
			$('#'+IdName4).hide();
			$('#'+IdName5).show();
			$('#'+IdName6).hide();
				$('#'+IdName7).show();
			$('#'+IdName8).hide();
		  //$('#patelUnPrc').remove(data-valid,'mand');
			
		
		
		
		
		}//
	

}



//for unprice
function ShowunitPrice(Id, Id1,Id2){
	 $.post('M_Asset_Div',{action : 'GetProductdetails', id:Id,idsloc:Id1,idloc:Id2},function (r){
	 
			if(r.data.length!=0)
				{
				
							console.log(r.data);
					console.log(r.data[0].mfr_assetdiv);
			//$('input[name="mfr' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].mfr_assetdiv);			 
		    //$('input[name="ds_product' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].ds_assetdiv);
		    $('input[name="un_prc' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].un_prc_assetdiv);
		    //$('input[name="in_stoc_qty' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].tot_aval_qty);
				
			/*	SubDropDownDataDisplayitemSrNo(r.data[0].id_assetdiv,idsloc,idloc,'serialnoDivForlead'+domElement.attr('patelUnPrc')+'','S_Add_To_Stock',function (status){
					//$('select[name=sr_no'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_s_assetdiv + ']').attr('selected',true);
					});*/
				}
				
				
				
	 },'json');
	
}

function showRowExtension(event ,Id,Id1,Id2,Id3,Id4){
	debugger;
	 var domElement =$(event.target || event.srcElement);
  
      var IdName= Id + domElement.attr('patelUnPrc');
      var IdName1= Id1 + domElement.attr('patelUnPrc');
       var IdName2= Id2 + domElement.attr('patelUnPrc');
       var IdName3= Id3 + domElement.attr('patelUnPrc');
  var IdName4= Id4 + domElement.attr('patelUnPrc');
      var value = $('select[name="extension_status'+domElement.attr('patelUnPrc')+'"]').val();
       if(value=='Yes'){
	       $('#'+IdName).show();
              $('#'+IdName1).show();
             $('#'+IdName2).show();
             $('#'+IdName3).hide();
               $('#'+IdName4).show();
              $('input[name="dt_return_str'+domElement.attr('patelUnPrc')+'"]').val('');
        }
       else{
	         $('#'+IdName).hide();
             $('#'+IdName1).hide();
             $('#'+IdName2).hide();
             $('#'+IdName4).hide();
             $('#'+IdName3).show();
            $('input[name="dt_extend_str'+domElement.attr('patelUnPrc')+'"]').val('');
            $('input[name="extend_un_prc'+domElement.attr('patelUnPrc')+'"]').val('0.00');
            $('input[name="extend_days'+domElement.attr('patelUnPrc')+'"]').val('');
             $('input[name="current_exp_dt'+domElement.attr('patelUnPrc')+'"]').val('');
            
         }

 
			
	
}


function ShowRowColchequeExtend(event ,Id){
	debugger;
	 var domElement =$(event.target || event.srcElement);
  
      var IdName= Id + domElement.attr('patelUnPrc');
   
      var value = $('select[name="cr_pay_mode'+domElement.attr('patelUnPrc')+'"]').val();
       if(value=='Cheque'){
	       $('#'+IdName).show();
             
        }
       else{
	         $('#'+IdName).hide();
            
             $('input[name="cr_cheque_no'+domElement.attr('patelUnPrc')+'"]').val('');
            
         }

 
			
	
}

/*function ChangePassword()
{
	alert();
	var t=0;
	t=ValidationForm('changePassword');
	
	if(t>0)
		{	
			var pass = $('#pass').val();
			var cpass = $('#cpass').val();
			
			if(pass == cpass)
				{
					var x = $('#changePassword').serialize();
					$.post('Logout',x,function (r){
						
						if(r.data == 2)
							{
								alert("Password has changed successfully.");
								 $('#ChangePassword').modal('hide');

							}
						else if(r.data == 1)
							{
								alert(" Wrong Password.");
							}
					
					},'json');
				
				}
			else
				{
					alert("Password should be same.");
				  	
				  	$('#cpass').focus();
				}
		
		}
	
}
*/

function changeSubContent(pageName,className) {
SessionCheck(function (ses){



if(ses)
{
//
var str = className.substring(0, className.length -6 );
$('.content-wrapper').load(pageName);
$('.nav-link').removeClass('active');
//alert(className);
//$('.'+className).parent().addClass('active');
$('.'+className).addClass('active');
$('.nav-link').css("margin-left", "0px");
//$('.'+className+'_event').css("margin-left", "50px");

}
});
}

function ControlDiv(action,displayContent,createDetails,formName,servletName,Message,ColName,Message2,id,id_loc) {
	
SessionCheck(function (ses){
		
		if(ses)
			{
				
		if(action == 'Edit')
			{
				
				if(id_loc)
				{
					//alert(servletName);
					
					if(servletName == 'M_Prod_Cart')
						{
						DisplayDropDownDataForGroup('M_Asset_Div','groupDataForItem',ColName,function (status){
							if(status)
							{
								/*SubDropDownDataDisplay(id_loc,'subGroupDataForItem','M_Subasset_Div',function (status){
									if(status)
									{*/
										EditFun(servletName,displayContent,createDetails,id,id_loc);
									//}});
							}});
						
						
						
						}
					else if(servletName == 'M_Model')
					{
						SubDropDownDataDisplay(id_loc,'subgroupDataForModel','M_Subasset_Div',function (status){
							if(status)
							{
								EditFun(servletName,displayContent,createDetails,id,id_loc);
							}});
						}
							
					else if(servletName == 'M_Prod_Consumable')
						{
						SubDropDownDataDisplay(id_loc,'subGroupDataForCItem','M_Subasset_Div',function (status){
							if(status)
							{
								EditFun(servletName,displayContent,createDetails,id,id_loc);
							}});
						}
					else if(servletName == 'M_Budget')
					{
						SubDropDownDataDisplay(Message,'subAssetDivForBudget','M_Subasset_Div',function (status){
						if(status)
						{
							DisplaySubDropDownData(ColName,'ModelDivForAssetBudget','M_Model',function (status){
						if(status)
						{
							EditFun(servletName,displayContent,createDetails,id,id_loc);
							}});
						}});
					}
			
					else if(servletName == 'M_Building')
					{
						
						
								DropDownDataDisplay("M_Loc","locDataForBuilding",function (status){
									if(status)
										{
										DisplaySubDropDownData(id_loc,'subLocDataForBuilding','M_Subloc',function (status){
											if(status)
											{
										EditFun(servletName,displayContent,createDetails,id,id_loc);
											}});
									}});
							
					
					}
					else if(servletName == 'M_Floor')
						{

					DropDownDataDisplay("M_Loc","locDataForFloor",function (status){
							if(status)
							{
						DisplaySubDropDownData(ColName,'subLocDataForFloor','M_Subloc',function (status){
							if(status)
							{
						SubDropDownDataDisplay(Message,'buildingDataForFloor','M_Building',function (status){
							if(status)
							{
						
										  EditFun(servletName,displayContent,createDetails,id,id_loc);
										
								
							}});
							}});
							}});
						}
						else if(servletName == 'M_BU')
						{
                      
					DropDownDataDisplay("M_Dept","DeptDataForCostCenter",function (status){
							if(status)
							{
						DisplaySubDropDownData(ColName,'nmfunction','M_Cost_Center',function (status){
							if(status)
							{
						DisplaySubDropDownData(Message,'subfunction','M_S_Function',function (status){
							if(status)
							{
						
										  EditFun(servletName,displayContent,createDetails,id,id_loc);
										
								
							}});
							}});
							}});
						}
						
						
						else if(servletName == 'M_Team')
						{

					DropDownDataDisplay("M_Dept","deptForEmp",function (status){
							if(status)
							{
						DisplaySubDropDownData(ColName,'nmfunction','M_Cost_Center',function (status){
							if(status)
							{
						DisplaySubDropDownData(Message,'subfunction','M_S_Function',function (status){
							if(status)
							{
								SubDropDownDataDisplay(formName,'subbu','M_BU',function (status){
							if(status)
							{
						
										  EditFun(servletName,displayContent,createDetails,id,id_loc);
										
								}});
							}});
							}});
							}});
						}
						
						
							else if(servletName == 'M_S_Function')
					{
						
						
								DropDownDataDisplay("M_Dept","DeptDataForCostCenter",function (status){
									if(status)
										{
										DisplaySubDropDownData(ColName,'id_cc_drop','M_Cost_Center',function (status){
											if(status)
											{
										EditFun(servletName,displayContent,createDetails,id,id_loc);
											}});
									}});
							
					
					}
						
						else if(servletName == 'M_Emp_User')
					{
						//
					DropDownDataDisplay("M_Loc","locDataForEmpUser",function (status){
							if(status)
							{
					DisplaySubDropDownData(id_loc,'slocDataForEmpUser','M_Subloc',function (status){
						if(status)
						{
					/*SubDropDownDataDisplay(Message,'buildingDataForEmpUser','M_Building',function (status){
						if(status)
						{
							SubDropDownDataDisplay(ColName,'floorDataForEmpUser','M_Floor',function (status){
						if(status)
						{*/
									  EditFun(servletName,displayContent,createDetails,id,id_loc);
									
					/*		
						}});
						}});*/
						}});
						}});
					}
					
						
					else
						{
						DisplayDropDownData("M_Loc","locDataForBuilding",function (status){
							if(status)
								{
						DisplaySubDropDownData(id_loc,'slocDataForEmpUser','M_Subloc',function (status){
							if(status)
							{
								
								SubDropDownDataDisplay(id_loc,'buildingDataForEmpUser','M_Building',function (status){
									if(status)
									{
													EditFun(servletName,displayContent,createDetails,id,id_loc);
									}});
										
							}});		
								
							}});
							
						}
				}
			}
		
	
	if(action=='Create')
		{
		
		$(".form-control").each(function(){
			$(".form-control").removeClass('error');
			$('button[name="create btn"]').addClass('hideButton');
			if($(this).is("select"))
			{
				$(this).find('option:selected').attr('selected',false);
				//$(this).val('');
			}
			if ($(this).attr("readonly") == "readonly") 
		    { 
		        $(this).removeAttr("readonly"); 
		    } 
				$(this).attr("disabled", false);
			
		});
		
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="delete"]').addClass('hideButton');
		
		if(servletName == 'M_User_Login')
			{
				//DataForCreatingUser();
				$('#userEmail').attr('disabled','disabled');

				$('#Disabled').attr('checked',false);
				$('#Enabled').attr('checked',true);
			}
		if(servletName == 'M_Emp_User')
		{
			DisplayDropDownData('M_Emp_User','repoManagerDataForEmpUser',function (status){
				if(status)
				{
					
				}});
		}
		if(servletName == 'M_Vendor')
		{
			$.post('M_Vendor',{action : 'auto_jwno'},function (r){
			
			$('input[name="cd_ven"]').val(r.wo_no);
				
},'json');
		}
/*		if(servletName == 'M_Floor')
		{
			DropDownDataDisplay("M_Country","countryDataForFloor",function (status){
				if(status)
					{
					
					}});
		}
		if(servletName == 'M_Subloc')
		{
			DropDownDataDisplay("M_Country","countryDataForSubLoc",function (status){
				if(status)
					{
					
					}});
		}
		if(servletName == 'M_Building')
		{
			DisplayDropDownData("M_Country","countryDataForBuilding",function (status){
				if(status)
					{
					
					}});
		}*/
		//alert(displayContent);
			$('input[name="action"]').val("Save");
			$('input[name="id"]').val("0");
			$('#'+displayContent).hide();
			$('#'+formName)[0].reset();
			
			$('#'+createDetails).show();
			$('#createAccessory').show();
			
		}
	if(action =='Cancel')
		{
			/*$('#createAccessory').hide();
			$('#'+createDetails).hide();
			
			$('#'+displayContent).show();*/
			
		RefreshData(servletName);
		}
	if(action=='Save')
		{
			debugger;
			DataInsertUpdate(action,displayContent,createDetails,formName,servletName,Message,ColName,Message2);
			
		}
		if(action=='Update')
		{
			debugger;
			DataInsertUpdate(action,displayContent,createDetails,formName,servletName,Message,ColName,Message2);
			
		}
	
			}
});
}

function ControlDivAdHoc(action) {
	
SessionCheck(function (ses){
		
		if(ses)
			{
	
	if(action=='Create')
		{
			DropdownResultForFinanceYear('financialYearAdhoc');
			$('input[name="actionAdhoc"]').val("Save");
			$('#displayBudget').hide();
			$('#createBudget').hide();
			$('#createAdHocBudget').show();
			
		}
	if(action =='Cancel')
		{
			$('#displayBudget').show();
			$('#createBudget').hide();
			$('#createAdHocBudget').hide();
		}
		if(action == 'SaveAdHoc'){
		var id = $('select[name="id_finance_adhoc"]').val();
		$.post('M_Budget',{action : 'SaveAdHoc',id:id},function (r){
			if(r.data ==1)
			{
			$('#displayBudget').show();
			$('#createBudget').hide();
			$('#createAdHocBudget').hide();
				bootbox.alert("Ad-Hoc Budget Succesfully Created.");
			}
			else if(r.data ==2)
			{
			
				bootbox.alert("Ad-Hoc Budget Already Existed For Particular Finance Year.");
			}
		else
			{
				bootbox.alert("Try again.");
			}
			
			},'json');				
}
	
			}
});
}
function DisplayDropDownDataForVendor(servletName,dropDownId,type_ven,callback)
{
	//alert("hello");
	var t =false;
	var list='';
	$.post(servletName,{action : 'DropDownResult',type_ven:type_ven},function (r){
		
		if(r.data)
			{
			t=true;
			if(dropDownId != 'assetDivForRequestQuotation')
				 list= '<option value=""> Select</option>';
			
				for(var i = 0; i < r.data.length ; i++)
				{
				
				
				for (var key in r.data[i])
		        {
					id=r.data[i][key];
					break;
		        }
				for (var key in r.data[i])
		        {
					val=r.data[i][key];
					
		        }
				list = list + '<option value="'+id+'"> '+val+'</option>';
				}
			
			
				$('#'+dropDownId).html(list);
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		callback(t);
	},'json');

}


function DisplayDropDownData(servletName,dropDownId,callback)
{
	var t =false;
	var list= '';
	$.post(servletName,{action : 'DropDownResult'},function (r){
		if((dropDownId == 'assetDivForMonthlyFaReport') || (dropDownId == 'assetDivForFaYearlyReport') || (dropDownId == 'typeIdForFaYearlyReport'))
			list= '<option value="All"> All</option>';
		else
		 list= '<option value=""> Select</option>';
		
		if((dropDownId == 'assetDivForYearlyDprn') || (dropDownId == 'assetDivForMonthlyDprn'))
			{
			 list = '';
			}
		if(r.data)
			{
			
				t=true;
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				
				for (var key in r.data[i])
		        {
					id=r.data[i][key];
					break;
		        }
				for (var key in r.data[i])
		        {
					val=r.data[i][key];
					
		        }
				list = list + '<option value="'+id+'"> '+val+'</option>';
				}
			
			 	if(dropDownId == 'subGroup')
				$('.subGroup').html(list);
				else
				$('#'+dropDownId).html(list);
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		callback(t);
	},'json');

}


function DropDownResultLoginProvision(servletName,dropDownId,callback)
{
	var t =false;
	var list= '';
	$.post(servletName,{action : 'DropDownResultLoginProvision'},function (r){
		if((dropDownId == 'assetDivForMonthlyFaReport') || (dropDownId == 'assetDivForFaYearlyReport') || (dropDownId == 'typeIdForFaYearlyReport'))
			list= '<option value="All"> All</option>';
		else
		 list= '<option value=""> Select</option>';
		
		if((dropDownId == 'assetDivForYearlyDprn') || (dropDownId == 'assetDivForMonthlyDprn'))
			{
			 list = '';
			}
		if(r.data)
			{
			
				t=true;
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				
				for (var key in r.data[i])
		        {
					id=r.data[i][key];
					break;
		        }
				for (var key in r.data[i])
		        {
					val=r.data[i][key];
					
		        }
				list = list + '<option value="'+id+'"> '+val+'</option>';
				}
			
			 	if(dropDownId == 'subGroup')
				$('.subGroup').html(list);
				else
				$('#'+dropDownId).html(list);
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		callback(t);
	},'json');

}

function DataforEmployee(servletName,Id)
{
	var t =false;
	var list= '';
	$.post(servletName,{action : 'find', id:Id},function (r){
		
		
		if(r.data)
			{
			
				t=true;
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
					
				for (var key in r.data[i])
		        {
					$('#'+dropDownId).val(r.data[i][key]);
					
					break;
		        }
				
				
				}
			
			
				$('#'+dropDownId).html(list);
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		callback(t);
	},'json');

}

//This is for opposite display..........
function DropDownDataDisplay(servletName,dropDownId,callback)
{
	var t =false;
	var list= '';
	
	$.post(servletName,{action : 'DropDownResult'},function (r){
		
		if(r.data)
			{
			t=true;
			if(servletName == 'M_Tax')
				 list= '<option value=""> Select</option>';
			else 
				list= '<option value=""> Select</option>';
			
			if(dropDownId == 'assetDivisionForTransferReport11')
				list= '<option value="All"> All</option>';
			
				for(var i = 0; i < r.data.length ; i++)
				{
				
				
				for (var key in r.data[i])
		        {
					id=r.data[i][key];
					break;
		        }
				for (var key in r.data[i])
		        {
					val=r.data[i][key];
					
		        }
				list = list + '<option value="'+val+'"> '+id+'</option>';
				}
			
			if(dropDownId == 'taxDataForDropDown11')
				$('.patelTax').html(list);
			else if(dropDownId == 'SlnoorDropDown11')
				$('.patelSlno').html(list);	
			else if(dropDownId == 'group')
				$('.group').html(list);
			else if(dropDownId == 'subGroup')
				$('.subGroup').html(list);
			else
				$('#'+dropDownId).html(list);
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		callback(t);
	},'json');

}


function DisplaySubDropDownData(ids,dropDownId,servletName,callback)
{
	var t = false;
	var id=ids.value;
	if(ids.value == undefined)
		{
		id = ids;
		}
	
	
	if(ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
		
			$.post(servletName,{action:'DropDownResult' , id : id},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value=""> Select</option>';
					if(dropDownId == 'floorDataForUserPermission')
						list= '';
						
						if(r.data.length == 0)
							{
								if(dropDownId == 'slocDataForInvoice'){
									$('#'+dropDownId).html(list);
									$('#'+dropDownId).focus();
								}else{
									bootbox.alert("No sub value is there. Please select other.");
									$('#'+dropDownId).html(list);
									$('#'+dropDownId).focus();
								}
								
								
								
							}
						else
							{
									for(var i = 0; i < r.data.length ; i++)
									{
									
									
									for (var key in r.data[i])
							        {
										id=r.data[i][key];
										break;
							        }
									for (var key in r.data[i])
							        {
										val=r.data[i][key];
										
							        }
								//	list = list + '<option value="'+id+'"> '+val+'</option>';
									
							list = list + '<option value="'+val+'"> '+id+'</option>';
									console.log(list);
									}
							}
						
						
						$('#'+dropDownId).html(list);
						$('#'+dropDownId).focus();
					}
				else
					{
						bootbox.alert(" Please select the value.");
					}
				if(callback) callback(t);
			},'json');
		}
}

// This is for opposite display..........

function SubDropDownDataDisplay(ids,dropDownId,servletName,callback)
{
	var t=false;
	var id=ids.value;
	if(ids.value == undefined)
		{
		id = ids;
		}
	if(dropDownId == 'subGroupDataForMaintenance')
		$('#assetIdForAssetMaintenance').html('<option value=""> Select </option>');
	
	if(ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
			$.post(servletName,{action:'DropDownResult' , id : id},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
									for(var i = 0; i < r.data.length ; i++)
									{
									
									
									for (var key in r.data[i])
							        {
										id=r.data[i][key];
										break;
							        }
									for (var key in r.data[i])
							        {
										val=r.data[i][key];
										
							        }
								//	list = list + '<option value="'+val+'"> '+id+'</option>';
									list = list + '<option value="'+id+'"> '+val+'</option>';
										
									}
							}
					if(dropDownId=='dropsgrp'){
						$('.dropsgrp').html(list);
					}
					else{
						$('#'+dropDownId).html(list);
					}
						
						$('#'+dropDownId).focus();
						
					}
				else
					{
						bootbox.alert("Try again1111.");
					}
				if(callback) callback(t);
			},'json');
		}
	
}



function SubDropDownDataDisplayitemSrNo(ids,ids1,ids2,dropDownId,type_grp,servletName,callback)
{
  console.log(ids2);	 
	var t=false;
	
	var id=ids.value;
	var id1=ids1.value;
	if(ids.value == undefined)
		{
		id = ids;
		}
	if(ids1.value == undefined)
		{
		id1 = ids1;
		}
	if(ids2.value == undefined)
		{
		id2 = ids2;
		}
	if(dropDownId == 'subGroupDataForMaintenance')
		$('#assetIdForAssetMaintenance').html('<option value=""> Select </option>');
	
	if(ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
			//
			$.post(servletName,{action:'DropDownResult' , id : id,id1:id1,id2:id2,type_grp:type_grp},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value="">Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No  value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
									for(var i = 0; i < r.data.length ; i++)
									{
									
									
									for (var key in r.data[i])
							        {
										id=r.data[i][key];
										break;
							        }
									for (var key in r.data[i])
							        {
										val=r.data[i][key];
										
							        }
								//	list = list + '<option value="'+val+'"> '+id+'</option>';
									list = list + '<option value="'+id+'"> '+val+'</option>';
										
									}
							}
					if(dropDownId=='dropsgrp'){
						$('.dropsgrp').html(list);
					}
					else{
						$('#'+dropDownId).html(list);
					}
						
						$('#'+dropDownId).focus();
						
					}
				else
					{
						bootbox.alert("Try again1111.");
					}
				if(callback) callback(t);
			},'json');
		}
	
}

function SubDropDownDataDisplayitemwithAllSrNo(ids,ids1,ids2,ids3,type_grp ,dropDownId,servletName,callback)
{
  console.log(ids2);	 
	var t=false;
	
	var id=ids.value;
	var id1=ids1.value;
	if(ids.value == undefined)
		{
		id = ids;
		}
	if(ids1.value == undefined)
		{
		id1 = ids1;
		}
	if(ids2.value == undefined)
		{
		id2 = ids2;
		}
		if(ids3.value == undefined)
		{
		id3= ids3;
		}
	if(dropDownId == 'subGroupDataForMaintenance')
		$('#assetIdForAssetMaintenance').html('<option value=""> Select </option>');
	
	if(ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
			//
			$.post(servletName,{action:'DropDownResultwithAlldata' , id : id,id1:id1,id2:id2,SerialVal:id3,type_grp:type_grp},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value="">Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No  value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
									for(var i = 0; i < r.data.length ; i++)
									{
									
									
									for (var key in r.data[i])
							        {
										id=r.data[i][key];
										break;
							        }
									for (var key in r.data[i])
							        {
										val=r.data[i][key];
										
							        }
								//	list = list + '<option value="'+val+'"> '+id+'</option>';
									list = list + '<option value="'+id+'"> '+val+'</option>';
										
									}
							}
					if(dropDownId=='dropsgrp'){
						$('.dropsgrp').html(list);
					}
					else{
						$('#'+dropDownId).html(list);
					}
						
						$('#'+dropDownId).focus();
						
					}
				else
					{
						bootbox.alert("Try again1111.");
					}
				if(callback) callback(t);
			},'json');
		}
	
}


function SubDropDownDataDisplay(ids,dropDownId,servletName,callback)
{
	 
	var t=false;
	var id=ids.value;
	if(ids.value == undefined)
		{
		id = ids;
		}
	if(dropDownId == 'subGroupDataForMaintenance')
		$('#assetIdForAssetMaintenance').html('<option value=""> Select </option>');
	
	if(ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
			//
			$.post(servletName,{action:'DropDownResult' , id : id},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
									for(var i = 0; i < r.data.length ; i++)
									{
									
									
									for (var key in r.data[i])
							        {
										id=r.data[i][key];
										break;
							        }
									for (var key in r.data[i])
							        {
										val=r.data[i][key];
										
							        }
								//	list = list + '<option value="'+val+'"> '+id+'</option>';
									list = list + '<option value="'+id+'"> '+val+'</option>';
										
									}
							}
					if(dropDownId=='dropsgrp'){
						$('.dropsgrp').html(list);
					}
					else{
						$('#'+dropDownId).html(list);
					}
						
						$('#'+dropDownId).focus();
						
					}
				else
					{
						bootbox.alert("Try again1111.");
					}
				if(callback) callback(t);
			},'json');
		}
	
}

function SubDropDownDataDisplayforemp(ids1,dropDownId,servletName,idsloc, callback)
{
	
	var t=false;
	
	var id1=ids1.value;
     var id=idsloc.value;
	
	if(idsloc.value == undefined)
		{
		id = idsloc;
		
		}
		
	if(ids1.value == undefined)
		{
		id1 = ids1;
			
		}
	
	if(dropDownId == 'subGroupDataForMaintenance')
		$('#assetIdForAssetMaintenance').html('<option value=""> Select </option>');
	
	if(idsloc.value == ''||ids1.value=='')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
			
			$.post(servletName,{action:'DropDownResult' , id :id,id1:id1},function (r){
				
				if(r.data)
					{
						
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
									for(var i = 0; i < r.data.length ; i++)
									{
									
									
									for (var key in r.data[i])
							        {
										id=r.data[i][key];
										break;
							        }
									for (var key in r.data[i])
							        {
										val=r.data[i][key];
										
							        }
								//	list = list + '<option value="'+val+'"> '+id+'</option>';
									list = list + '<option value="'+id+'"> '+val+'</option>';
										
									}
							}
					if(dropDownId=='dropsgrp'){
						$('.dropsgrp').html(list);
					}
					else{
						$('#'+dropDownId).html(list);
					}
						
						$('#'+dropDownId).focus();
						
					}
				else
					{
						bootbox.alert("Try again1111.");
					}
				if(callback) callback(t);
			},'json');
		}
	
}



function SubDropDownDataDisplaybydesingEmp(ids1,dropDownId,servletName,callback)
{
	
	var t=false;
	
	var id1=ids1.value;
    
	
	if(ids1.value == undefined)
		{
		id1 = ids1;
			
		}
	
	if(dropDownId == 'subGroupDataForMaintenance')
		$('#assetIdForAssetMaintenance').html('<option value=""> Select </option>');
	
	
	else
		{
			
			$.post(servletName,{action:'DropDownResult' , id1:id1},function (r){
				
				if(r.data)
					{
						
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
									for(var i = 0; i < r.data.length ; i++)
									{
									
									
									for (var key in r.data[i])
							        {
										id=r.data[i][key];
										break;
							        }
									for (var key in r.data[i])
							        {
										val=r.data[i][key];
										
							        }
								//	list = list + '<option value="'+val+'"> '+id+'</option>';
									list = list + '<option value="'+id+'"> '+val+'</option>';
										
									}
							}
					if(dropDownId=='dropsgrp'){
						$('.dropsgrp').html(list);
					}
					else{
						$('#'+dropDownId).html(list);
					}
						
						$('#'+dropDownId).focus();
						
					}
				else
					{
						bootbox.alert("Try again1111.");
					}
				if(callback) callback(t);
			},'json');
		}
	
}





function EditFunEmp(action,displayContent,createDetails,id_flr,servletName,id_sloc,id_building,id,id_loc,id_dept,id_cc,id_s_function)
{
	
SessionCheck(function (ses){
		
		if(ses)
			{
	$('button[name="save"]').addClass('hideButton');
	$('button[name="update"]').removeClass('hideButton');
		
		
		DropDownDataDisplay("M_Subloc","slocDataForEmpUser",function (status){
							if(status)
							{
				/*		DisplaySubDropDownData(id_loc,'slocDataForEmpUser','M_Subloc',function (status){
						if(status)
						{
				SubDropDownDataDisplay(id_sloc,'buildingDataForEmpUser','M_Building',function (status){
						if(status)
						{
							SubDropDownDataDisplay(id_building,'floorDataForEmpUser','M_Floor',function (status){
						if(status)
						{
							*/	
							
			 $.post(servletName,{action : 'Edit',id : id},function (r){
				if(r.data)
					{
					$('#'+displayContent).hide();
					$('#'+createDetails).show();
					
					
						for(var i = 0; i < r.data.length ; i++)
						{
							
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
									
								if(key == 'nm_emp'  || key == '')
									{
										$('input[name='+key+']').attr('readonly', 'readonly');
									}
					         
					        }
							 
						}
						document.getElementById("image").src="InvoiceScanFile/"+r.data[0].emp_image;
						$('input[name="action"]').val("Update");
						$('input[name="id"]').val(id);
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
		},'json')
								
													
							
						/*}});
						}});
						}});*/
						}});
						
						
						
	
		
		
			}
		
		
	
});
}

function EditFunBudget(action,displayContent,createDetails,servletName,id_assetdiv,id_s_assetdiv,id,id_dept,id_cc,id_s_function,id_bu)
{
	
SessionCheck(function (ses){
		
		if(ses)
			{
	$('button[name="save"]').addClass('hideButton');
	$('button[name="update"]').removeClass('hideButton');
	
		
		
					SubDropDownDataDisplay(id_assetdiv,'subAssetDivForBudget','M_Subasset_Div',function (status){
						if(status)
						{
					DisplaySubDropDownData(id_s_assetdiv,'ModelDivForAssetBudget','M_Model',function (status){
						if(status)
						{
							DisplaySubDropDownData(id_dept,'nmfunction','M_Cost_Center',function (status){
						if(status)
						{
								DisplaySubDropDownData(id_cc,'subfunction','M_S_Function',function (status){
							if(status)
							{
									SubDropDownDataDisplay(id_s_function,'subbu','M_BU',function (status){
										if(status)
											{
										$.post(servletName,{action : 'Edit',id : id},function (r){
				if(r.data)
					{
					$('#'+displayContent).hide();
					$('#'+createDetails).show();
					
					
						for(var i = 0; i < r.data.length ; i++)
						{
							
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
									
								if(key == 'nm_emp'  || key == '')
									{
										$('input[name='+key+']').attr('readonly', 'readonly');
									}
					         
					        }
							 
						}
						
						$('input[name="action"]').val("Update");
						$('input[name="id"]').val(id);
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
		},'json')
								
													}});	
								
											}});	
								
							}});	
							
						}});
						}});
						
						
						
	
		
		
			}
		
		
	
});
}
function EditFun(servletName,displayContent,createDetails,id,id_loc)
{
	
SessionCheck(function (ses){
		
		if(ses)
			{
	$('button[name="save"]').addClass('hideButton');
	$('button[name="update"]').removeClass('hideButton');
	
	
		$.post(servletName,{action : 'Edit',id : id},function (r){
				if(r.data)
					{
					$('#'+displayContent).hide();
					$('#'+createDetails).show();
					$('button[name="create btn"]').addClass('hideButton');
					if(servletName == 'M_Vendor')
					{
						$('input[name="procured"]').prop('checked', false);
						$('input[name="service"]').prop('checked', false);
					}
					
						for(var i = 0; i < r.data.length ; i++)
						{
							
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
									
									if(key == 't_and_c')
										{
											$('textarea[name='+key+']').val(r.data[i][key]);
										}
									
									if(r.data[i][key] == 'Procured' || r.data[i][key] == 'Service' || r.data[i][key] == 'yes')
										{
										
											$('input[name='+key+']').prop('checked', true);
										}
									if(r.data[i][key] == 'No')
										{
											$('input[name='+key+']').val('yes');
										}
									
								if(key == 'nm_emp'  || key == '')
									{
										$('input[name='+key+']').attr('readonly', 'readonly');
									}
								
					         
					        }
							 
						}
						
						if(servletName == 'M_Company')
						{
							 
							document.getElementById("image").src="InvoiceScanFile/"+r.data[0].file_name;
						}
						if(servletName == 'M_Emp_User')
						{
							 
							document.getElementById("image").src="InvoiceScanFile/"+r.data[0].emp_image;
						}
						
						if(servletName == 'M_Vendor')
						{
							$('input[name="procured"]').val('Procured');
							$('input[name="service"]').val('Service');
							document.getElementById("file4ID1").src="InvoiceScanFile/"+r.data[0].file_name1;
						}
						
						if(servletName == 'M_User_Login')
						{
							var id_mngr = r.data[0].id_mngr;
							
							if(id_mngr != '')
								{
									HideShow(r.data[0].type_user);
								}
							$('#cpwdConfirm').removeClass('error');
							$('#Enabled').val('1');
							$('#Disabled').val('0');
							if(r.data[0].status_user == 1)
								{
									$('#Enabled').val('1');
									$('#Enabled').attr('checked',true);
								}
							else
								{
									$('#Disabled').val('0');
									$('#Disabled').attr('checked',true);
								}
							
						}
						
						$('input[name="action"]').val("Update");
						$('input[name="id"]').val(id);
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
		},'json')
		
		
		
		.fail(function(jqXHR,status)
			    {
			    });
		
	
			}
		
		
	
});
}
function EditFunPartner(servletName,displayContent,createDetails,id)
{
	
SessionCheck(function (ses){
		
		if(ses)
			{
	$('button[name="save"]').addClass('hideButton');
	$('button[name="update"]').removeClass('hideButton');
		/*DropDownDataDisplay("M_Loc","locDataForSubLoc",function (status){
			if(status)
				{*/
				
	$.post('M_Vendor',{action : 'auto_jwno'},function (r){
			
			$('input[name="cd_ven"]').val(r.wo_no);
				
},'json');
		$.post(servletName,{action : 'EditPartner'},function (r){
				if(r.data)
					{
					$('#'+displayContent).hide();
					$('#'+createDetails).show();
					
						for(var i = 0; i < r.data.length ; i++)
						{
							
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
									
									if(key == 't_and_c')
										{
											$('textarea[name='+key+']').val(r.data[i][key]);
										}
									
									if(r.data[i][key] == 'Procured' || r.data[i][key] == 'Service' || r.data[i][key] == 'yes')
										{
										
											$('input[name='+key+']').prop('checked', true);
										}
									if(r.data[i][key] == 'No')
										{
											$('input[name='+key+']').val('yes');
										}
									
									
								if(key.match('cd_') || key == 'pre_asset')
									{
										$('input[name='+key+']').attr('readonly', 'readonly');
									}
								if(key == 'nm_emp'  || key == '')
									{
										$('input[name='+key+']').attr('readonly', 'readonly');
									}
								if(servletName != 'M_Emp_User' && servletName != 'M_User_Login')
								{
									if(key == 'id_assetdiv' || key == 'id_div' || key == 'id_loc' || key == 'id_sloc' || key == 'id_country' || key == 'id_building' || key == 'id_flr' || key == 'id_s_assetdiv')
										{
											$('select[name='+key+']').attr("disabled", true);
										}
								}
								
					         
					        }
							 
						}
						
						
						
						if(servletName == 'M_User_Login')
						{
							var id_mngr = r.data[0].id_mngr;
							
							if(id_mngr != '')
								{
									HideShow(r.data[0].type_user);
								}
							$('#cpwdConfirm').removeClass('error');
							$('#Enabled').val('1');
							$('#Disabled').val('0');
							if(r.data[0].status_user == 1)
								{
									$('#Enabled').val('1');
									$('#Enabled').attr('checked',true);
								}
							else
								{
									$('#Disabled').val('0');
									$('#Disabled').attr('checked',true);
								}
							
						}
						
						$('input[name="action"]').val("UpdatePartner");
						$('input[name="id"]').val(id);
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
		},'json')
		
		
		
		.fail(function(jqXHR,status)
			    {
			    });
		
				//}});
	
			}
		
		
	
});
}
function MakeItCurrent(servletName,displayContent,createDetails,id)
{
SessionCheck(function (ses){
		
		if(ses)
			{
		
			$.post(servletName,{action : 'Current',id : id},function (r){
				
				if(r.data == 1)
					{
						DisplayData(servletName,displayContent,createDetails);
					}
				
			},'json');
		}
	}); 
}

function DeleteFun(servletName,displayContent,createDetails,id,Message,Message2)
{debugger;
SessionCheck(function (ses){
		debugger;
		if(ses)
			{
				var ids=id;
				if(ids=="Super" || ids=="EndUser"){
					bootbox.alert("This User Type Can't be deleted.");
				}
				else
				{
	bootbox.confirm("Are you sure? You want to delete the record?", function(result) {
		
		if(result)
		{
			
			
			$.post(servletName,{action : 'Delete',id : id},function (r){
				debugger;
				if(r.data == 1)
					{
						var message=Message;
			
					  Swal.fire({
								position: 'center',
								icon: 'success',
								title: message,
								showConfirmButton: false,
								timer: 3000
							})
						RefreshData(servletName);
						//DisplayData(servletName,displayContent,createDetails);
					}
				else if(r.data == 2)
					{
						//bootbox.alert('Can not delete.Assets exists in this Category.');
						var message=Message2;
			
					  Swal.fire({
								position: 'center',
								icon: 'error',
								title: message,
								showConfirmButton: false,
								timer: 3000
							})
					}
				else
					{
					//	bootbox.alert('Can not delete.Assets exists in this Category.');
					var message=Message2;
			
					  Swal.fire({
								position: 'center',
								icon: 'error',
								title: message,
								showConfirmButton: false,
								timer: 3000
							})
			
					
					}
				
			},'json');
		}
		
	}); 
	}
	}		
			
});
}

function selectAll(actionClass , classNameForCheck)
{
	
	if($('.'+actionClass).prop('checked')) {
		$('.'+classNameForCheck +" option").attr('selected',true);
	} else {
		$('.'+classNameForCheck +" option").attr('selected',false);
	}
	
	
}

function ShowRowColumnValidation(className)
{
	
	var t=true;
	$('.'+className).each(function (){
		$('.'+className).removeClass('error');
			console.log($(this).val());
				if($(this).val() == '')
				{
					console.log($(this).val());
					t=false;
					bootbox.alert('Mandatory field.');
					$(this).focus();
					$(this).addClass('error');
					exit(0);
				}
			
	});
	return t;
}


function CheckValWhichAllReadyExit(ServletName , Message , ColName ,callback)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
			// 	
	$('input[name="'+ColName+'"]').removeClass('error');
	var val = $('input[name="'+ColName+'"]').val();
	var id_loc='',id_sloc='',id_building='',id_country='' ,id_assetdiv='',id_s_assetdiv='';
	console.log(val);
	/*alert(val);
	alert(ColName);*/
/*	if(ServletName == 'M_Subloc')
	{
	id_country=$('#countryDataForSubLoc').val();
	 id_loc = $('#locDataForSubLoc').val();
	 
	}
	if(ServletName == 'M_Building')
	{
	id_country=$('#countryDataForBuilding').val();
	 id_loc = $('#locDataForBuilding').val();
	 id_sloc = $('#subLocDataForBuilding').val();
	}
	if(ServletName == 'M_Floor')
	{
	id_country=$('#countryDataForFloor').val();
	 id_loc = $('#locDataForFloor').val();
	 id_sloc = $('#subLocDataForFloor').val();
	 id_building = $('#buildingDataForFloor').val();
	}*/
	
	if(ServletName == 'M_Model')
	{
		id_assetdiv = $('#groupDataForModel').val();
		id_s_assetdiv = $('#subgroupDataForModel').val();
	}
	
	var t=true;
	$.post(ServletName , {action : 'CheckExitsVal' , value : val , ColName : ColName,id_country : id_country,id_loc : id_loc,id_sloc : id_sloc,id_building : id_building,id_s_assetdiv:id_s_assetdiv,id_assetdiv:id_assetdiv},function (r){
		
		if(r.data == 1)
			{
				t=false;
				$('input[name="'+ColName+'"]').addClass('error');
				$('input[name="'+ColName+'"]').focus();
				//alert(Message);
				var msg = Message;
                                 	Swal.fire({
     											 position: 'center',
 												 icon: 'error',
 												 title: msg,
  													showConfirmButton: false,
 													 timer: 1500
})
				exit(0);
			}
		callback(t);
	},'json')
	.fail(function(jqXHR,status)
		    {
		    });
	
			}});
}
function EditFunApp(servletName,displayContent,createDetails,id)
{
	
SessionCheck(function (ses){
		
		if(ses)
			{
	/*$('button[name="save"]').removeClass('hideButton');
	$('button[name="update"]').removeClass('hideButton');*/
		/*DropDownDataDisplay("M_Loc","locDataForSubLoc",function (status){
			if(status)
				{*/
				
	
		$.post(servletName,{action : 'Edit',id : id},function (r){
				if(r.data)
					{
					$('#'+displayContent).hide();
					$('#'+createDetails).show();
					if(servletName == 'M_Vendor')
					{
						$('input[name="procured"]').prop('checked', false);
						$('input[name="service"]').prop('checked', false);
					}
						for(var i = 0; i < r.data.length ; i++)
						{
							
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
									
									if(key == 't_and_c')
										{
											$('textarea[name='+key+']').val(r.data[i][key]);
										}
									
									if(r.data[i][key] == 'Procured' || r.data[i][key] == 'Service' || r.data[i][key] == 'yes')
										{
										
											$('input[name='+key+']').prop('checked', true);
										}
									if(r.data[i][key] == 'No')
										{
											$('input[name='+key+']').val('yes');
										}
									
									
								if(key.match('cd_') || key == 'pre_asset')
									{
										$('input[name='+key+']').attr('readonly', 'readonly');
									}
								if(key == 'nm_emp'  || key == '')
									{
										$('input[name='+key+']').attr('readonly', 'readonly');
									}
								if(servletName != 'M_Emp_User' && servletName != 'M_User_Login')
								{
									if(key == 'id_assetdiv' || key == 'id_div' || key == 'id_loc' || key == 'id_sloc' || key == 'id_country' || key == 'id_building' || key == 'id_flr' || key == 'id_s_assetdiv')
										{
											$('select[name='+key+']').attr("disabled", true);
										}
								}
								
					         
					        }
							 
						}
						if(servletName == 'M_Company')
						{
							document.getElementById("image").src="InvoiceScanFile/"+r.data[0].file_name;
						}
						if(servletName == 'M_Vendor')
						{
							$('input[name="procured"]').val('Procured');
							$('input[name="service"]').val('Service');
						}
						
						if(servletName == 'M_User_Login')
						{
							var id_mngr = r.data[0].id_mngr;
							
							if(id_mngr != '')
								{
									HideShow(r.data[0].type_user);
								}
							$('#cpwdConfirm').removeClass('error');
							$('#Enabled').val('1');
							$('#Disabled').val('0');
							if(r.data[0].status_user == 1)
								{
									$('#Enabled').val('1');
									$('#Enabled').attr('checked',true);
								}
							else
								{
									$('#Disabled').val('0');
									$('#Disabled').attr('checked',true);
								}
							
						}
						
						//$('input[name="action"]').val("Approve");
						$('input[name="id"]').val(id);
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
		},'json')
		
		
		
		.fail(function(jqXHR,status)
			    {
			    });
		
				//}});
	
			}
		
		
	
});
}

function DataInsertUpdate(action,displayContent,createDetails,formName,servletName,Message,ColName,Message2)
{
	debugger;
SessionCheck(function (ses){
		if(ses)
			{
	var t = false;
	var j=0;
	var arr = ColName.split(',,');
	var msg = Message.split(',,');
	for(var i=0;i<arr.length;i++)
	{
		 
		CheckValWhichAllReadyExit(servletName , msg[i] , arr[i] ,function (t1){
			j += 1;
			//
			if(!t1)
				{
					exit(0);
				}
			else
			{
				t=t1;
				if(j == arr.length)
				{
				
					if(t)
					{
						t=false;
						debugger;
						t = ValidationForm(formName);
						if(servletName == 'M_User_Login')
							{
								if(t)
								{
									
									/*var value = $('select[name="type_user"]').val();
									if(value == 'SUPER' || value == 'FINANCE' || value == 'FDIRECTOR' || value == 'IAUDIT' || value == 'PAPPRV' || value == 'PRPOCODNTR' || value == 'SRMGRHEAD' || value == 'PHEAD')
									{t=true;}else{
											t=false;
											var id_mngr = $('select[name="id_mngr"]').val();
											if(id_mngr == '')
													alert('Please select Manager Name.');
											else
												t=true;
										}*/
									
											t=false;
											t= ConfirmPasswordCheck();
								}
							}
						/*if(servletName == 'M_Vendor')
						{
							$('input[name="procured"]').removeClass('error');
							
							if(!$('input[name="procured"]').prop('checked') && !$('input[name="service"]').prop('checked'))
								{
									t=false;	
									$('input[name="procured"]').addClass('error');
									$('input[name="procured"]').focus();
									alert('Mandatory Field.');
								}
								
						}*/
						
					}
				
				if(t)
					{
						
						
					
						var x = $('#'+formName).serialize();
						console.log(x);
						console.log("hi");
						console.log(action);
						//$('.store').removeAttr('disabled');
						if(servletName == 'M_User_Login')
						{
							$('.btn').attr('disabled','disabled');
						}
						//debugger;
						$.post(servletName,x,function (r){
							
							if(r.data==1)
								{
								$('button[name="save"]').removeClass('hideButton');
								$('button[name="update"]').addClass('hideButton');
									$(".message").show();
						            $(".message").fadeOut(2500);
                              debugger;
                                 if(action=='Update'){
	                           		var message=Message2;
                                 	Swal.fire({
     											 position: 'center',
 												 icon: 'success',
 												 title: message,
  													showConfirmButton: false,
 													 timer: 1500
})
                            
                             }
                                 if(action=='Save'){
	                               var message=Message2;
                                 	Swal.fire({
     											 position: 'center',
 												 icon: 'success',
 												 title: message,
  													showConfirmButton: false,
 													 timer: 1500
})
                                 
                                
                                }


                           
                                    RefreshData(servletName);
									/*DisplayData(servletName,displayContent,createDetails);
									ControlDiv(action,displayContent,createDetails,formName,servletName);*/
								}
							else if(r.data == 2)
							{
								bootbox.alert("First take all assets from this user.");
								}
								else if(r.data == 3)
							{
								bootbox.alert("Budget is already allocated for particular Cost-Center");
								}
							else
								{
									bootbox.alert("Try again.");
								}
							$('.loginBtn').removeAttr('disabled');
						},'json');
					
					}
					
				}
			}
			
			
			
		});
		
    }
    
	
			}
});

}

function DataInsertUpdateTax(action, displayContent, createDetails, formName, servletName) {
	SessionCheck(function(ses) {

		if (ses) {
			var j=1;
			
			var nm_tax1=$('input[name="nm_tax1"]').val();
			var nm_tax2=$('input[name="nm_tax2"]').val();
			var per_tax1=$('input[name="per_tax1"]').val();
			var per_tax2=$('input[name="per_tax2"]').val();
			$.post('M_Tax', { action: 'CheckExitsValues', nm_tax1: nm_tax1, nm_tax2: nm_tax2, per_tax1: per_tax1, per_tax2: per_tax2}, function(r) {

				if (r.data == 2) {
					alert("Tax details are already exist.");
				}
				else
				{
					t = ValidationForm(formName);
 
							if (t) {
								var x = $('#' + formName).serialize();
								$.post(servletName, x, function(r) {

									if (r.data == 1) {
										$('button[name="save"]').removeClass('hideButton');
										$('button[name="update"]').addClass('hideButton');
										$(".message").show();
										$(".message").fadeOut(2500);
										//alert("Tax Create Successfully.");
										 Swal.fire({
								position: 'center',
								icon: 'success',
								title: 'Tax has created successfully.',
								//showConfirmButton: false,
								timer: 3000
							   })
										RefreshData(servletName);
										/*DisplayData(servletName,displayContent,createDetails);
										ControlDiv(action,displayContent,createDetails,formName,servletName);*/
									}
									else if (r.data == 2) {
										bootbox.alert("First take all assets from this user.");
									}
									else if (r.data == 3) {
										bootbox.alert("Budget is already allocated for particular Department");
									}
									else {
										bootbox.alert("Try again.");
									}
									$('.loginBtn').removeAttr('disabled');
								}, 'json');

							}
				}
			}, 'json')
				 





		}
	});

}
function ControlDivPartner(action,displayContent,createDetails,formName,servletName,Message,ColName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	var t = false;
	var j=0;
	var arr = ColName.split(',,');
	var msg = Message.split(',,');
	
	var x = $('#'+formName).serialize();
						
						if(servletName == 'M_User_Login')
						{
							$('.loginBtn').attr('disabled','disabled');
						}
						
						$.post(servletName,x,function (r){
							
							if(r.data==1)
								{
								$('button[name="save"]').addClass('hideButton');
								$('button[name="update"]').removeClass('hideButton');
								alert("Your Profile Successfully Updated");
									$(".message").show();
						            $(".message").fadeOut(2500);
						            RefreshData(servletName);
									/*DisplayData(servletName,displayContent,createDetails);
									ControlDiv(action,displayContent,createDetails,formName,servletName);*/
								}
							
							else
								{
									bootbox.alert("Try again.");
								}
							$('.loginBtn').removeAttr('disabled');
						},'json');
			}
});

}
function RefreshData(servletName)
{
	console.log(servletName);
	
	switch(servletName)
	{
		
			case  "M_Vendor_view" :
			
			$( ".viewPartner" ).trigger( "click" );
			break;
			case  "M_Group" :
			
			window.location = $('.EditAssignEmployee_event').attr('href');
			break;
			case  "M_User_Type" :
			
			window.location = $('.usertype_event').attr('href');
			break;
		case  "M_Team" :
			$( ".team" ).trigger( "click" );
			break;
		case  "M_BU" :
			$( ".bu" ).trigger( "click" );
			break;
		case  "M_S_Function" :
			$( ".subfunction" ).trigger( "click" );
			break;
		case  "M_Country" :
			$( ".country" ).trigger( "click" );
			break;
		case  "M_Division" :
			$( ".division" ).trigger( "click" );
			break;
		case "M_Tax" :
		window.location = $('.Tax_event').attr('href');
			//$( ".Tax" ).trigger( "click" );
			break;
			case "M_Group" :
		window.location = $('.GroupAssignment_event').attr('href');
			//$( ".Tax" ).trigger( "click" );
			break;
		case  "M_Designation" :
		window.location = $('.designation_event').attr('href');
			//$( ".designation" ).trigger( "click" );
			break;
		case  "M_Dept" :
		window.location = $('.department_event').attr('href');
			//$( ".department" ).trigger( "click" );
			break;
		case  "M_StorageDetail" :
			//$( ".Storage" ).trigger( "click" );
			break;
		case  "M_Loc" :
		window.location = $('.location_event').attr('href');
			
			break;
		case  "M_Subloc" :
		window.location = $('.sublocation_event').attr('href');
			
			break;
		case  "M_Floor" :
		window.location = $('.floor_event').attr('href');
			
			break;
		case  "M_Building" :
		window.location = $('.building_event').attr('href');
			
			break;
		case  "M_User_Login" :
		window.location = $('.user_event').attr('href');
			
			break;
		case  "M_Delivery" :
			$( ".Delivery" ).trigger( "click" );
			break;
		case  "M_Warranty" :
			$( ".Warranty" ).trigger( "click" );
			break;
		case  "M_Asset_Div" :
		window.location = $('.CapitalGrp_event').attr('href');
			//$( ".CapitalGrp" ).trigger( "click" );
			break;
		case  "M_Subasset_Div" :
		window.location = $('.CapitalSubGrp_event').attr('href');
			//$( ".CapitalSubGrp" ).trigger( "click" );
			break;
		case  "M_Consumable_Div" :
			$( ".ConsumableGrp" ).trigger( "click" );
			break;
		case  "M_Consumable_Sub_Div" :
			$( ".ConsumableSubGrp" ).trigger( "click" );
			break;
		case  "M_Emp_User" :
		window.location = $('.employeeUser_event').attr('href');
			//$( ".employeeUser" ).trigger( "click" );
			break;
		//
		case  "M_Financial_Year" :
		window.location = $('.finance_event').attr('href');
			//$( ".finance" ).trigger( "click" );
			break;
		case  "M_Vendor" :
		window.location = $('.vendorMaster_event').attr('href');
			//$( ".vendorMaster" ).trigger( "click" );
			break;
		case  "M_Currency" :
		//window.location = $('.user_event').attr('href');
			$( ".Currency" ).trigger( "click" );
			break;
		case  "M_Cost_Center" :
		window.location = $('.costCenter_event').attr('href');
			//$( ".costCenter" ).trigger( "click" );
			break;
		case  "M_CG" :
			$( ".CG" ).trigger( "click" );
			break;
		case  "M_Unit" :
		//window.location = $('.user_event').attr('href');
			$( ".Unit" ).trigger( "click" );
			break;
		case  "M_Prod_Cart" :
			$( ".ItemCart" ).trigger( "click" );
			break;
		case  "M_Budget" :
		//window.location = $('.user_event').attr('href');
			$( ".budget" ).trigger( "click" );
			break;
		case  "M_Terms_Conditions" :
			$( ".tac" ).trigger( "click" );
			break;
		case  "M_Exchange_Rate" :
			$( ".ExchangeRate" ).trigger( "click" );
			break;
		case  "M_Model" :
		window.location = $('.Model_event').attr('href');
			//$( ".Model" ).trigger( "click" );
			break;
		case  "M_User_Access" :
			window.location = $('.useracces_event').attr('href');
			//$( ".useracces" ).trigger( "click" );
			break;
		case  "M_Source" :
			window.location = $('.source_event').attr('href');
			//$( ".useracces" ).trigger( "click" );
			break;
			
	}
}

function ValidationForm(formName) {
	//debugger;
	var t=false;
	
	$("#"+formName+" .form-control").each(function(){
		
		$("#"+formName+" .form-control").removeClass('error');
		//console.log(this);
		var validation = $(this).attr("data-valid");

		if (validation != undefined && validation != '') {
			
			console.log(validation);
			err = 'valid';

			if (validation.search("num") != "-1") {
				debugger;
				if ($(this).val() != '')
				{
					err = validateNumber($(this).val());
					if(err !='')
						{
							alert(err);
							$(this).focus();
							$(this).addClass('error');
							exit();
						}
				}
				else
				{
					Swal.fire({
  position: 'top',
  title: 'Mandatory field'
});	
					$(this).focus();
					$(this).addClass('error');
					exit();
				}
				

			}

			if (validation.search("email") != "-1") {

				if ($(this).val() != '')
				{
					err = validateEmail($(this).val());
					if(err !='')
					{
						alert(err);
						$(this).focus();
						$(this).addClass('error');
						exit();
					}
				}
				else
					{
						Swal.fire({
  position: 'top',
  title: 'Mandatory field'
});	
						$(this).focus();
						$(this).addClass('error');
						exit();
					}
				
			}

             // debugger;
			if (validation.search("mand") != "-1") {
                    console.log(this);
				if ($(this).val() == '')
				{
					Swal.fire({
  position: 'top',
  title: 'Mandatory field'
});	
					$(this).focus();
					$(this).addClass('error');
					exit();
				}
				
				
				
				
			}
			if (err == 'valid')
			{ 
				err = ''; 
				t=true;
				
			}


		}
		
	});
	return t;
}



//validate Number...
function validateNumber(x) {
    var str = x;

    var str = '1' + str;
    var str = str.replace(/[\(\)\.\-\/\ ]/g, '');
    if (str == '1') {
        return '';
    }
    else {
        if (parseInt(str) != str) {
            return 'Invalid number';
        }
        else {
        	x=str.replace('.', '0');
            var limit = $('#' + x).attr('limit');
            var xxxx = $('#' + x).val();

            if (limit) {
                if (xxxx.length == limit) {
                    return '';
                } else {
                    return limit + 'Characters Required';
                }

            } else {
                return '';
            }
        }
    }
}
// validate number end here...........

// validate email
function validateEmail(xx) {

    var emailID = xx;
    var error = '';
    if (xx) {

        atpos = emailID.indexOf("@");
        dotpos = emailID.lastIndexOf(".");
        if (atpos < 1 || (dotpos - atpos < 2)) {
            error = "Please enter correct email ID";

        }
    }
    return (error);
}
// validate email end here...
function DisplayDropDownDataForStorage(servletName,dropDownId,callback)
{
	var t =false;
	var list='';
	$.post(servletName,{action : 'DropDownResult'},function (r){
		
		if(r.data)
			{
			t=true;
			if(dropDownId != 'assetDivForRequestQuotation')
				 list= '<option value=""> Select</option>';
			
				for(var i = 0; i < r.data.length ; i++)
				{
				
				
				for (var key in r.data[i])
		        {
					id=r.data[i][key];
					break;
		        }
				for (var key in r.data[i])
		        {
					val=r.data[i][key];
					
		        }
				list = list + '<option value="'+id+'"> '+val+'</option>';
				}
			
			
				$('#'+dropDownId).html(list);
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		callback(t);
	},'json');

}

function ControlDivApp(action,displayContent,createDetails,formName,servletName,Message,ColName,id,id_loc) {
	
SessionCheck(function (ses){
		
		if(ses)
			{
		
	
	
	if(action =='Cancel')
		{
			$( ".vendorMasterApp" ).trigger( "click" );
		}
	if(action=='Approve')
		{
		//alert("action");
		$('input[name="action"]').val("Approve");
			DataInsertUpdateApp("Cancel",displayContent,createDetails,formName,servletName,Message,ColName);
			
		}
	if(action=='Reject')
		{
			$('input[name="action"]').val("Reject");
		DataInsertUpdateRej("Cancel",displayContent,createDetails,formName,servletName,Message,ColName);

		
		}
			}
});
}
function DataInsertUpdateApp(action,displayContent,createDetails,formName,servletName,Message,ColName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	var t = false;
	var j=0;
						//alert("action");
				
						var x = $('#'+formName).serialize();
						
						if(servletName == 'M_User_Login')
						{
							$('.loginBtn').attr('disabled','disabled');
						}
						
						$.post(servletName,x,function (r){
							
							if(r.data==1)
								{
								$('button[name="save"]').removeClass('hideButton');
								$('button[name="update"]').addClass('hideButton');
									alert("Successfully Approved!!!");
						           $( ".vendorMasterApp" ).trigger( "click" );
									/*DisplayData(servletName,displayContent,createDetails);
									ControlDiv(action,displayContent,createDetails,formName,servletName);*/
								}
							else if(r.data == 2)
							{
								bootbox.alert("First take all assets from this user.");
								}
							else
								{
									bootbox.alert("Try again.");
								}
							$('.loginBtn').removeAttr('disabled');
						},'json');
					
				
			}
});

}


function DataInsertUpdateRej(action,displayContent,createDetails,formName,servletName,Message,ColName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	var t = false;
	var j=0;
						//alert("action");
				
						var x = $('#'+formName).serialize();
						
						if(servletName == 'M_User_Login')
						{
							$('.loginBtn').attr('disabled','disabled');
						}
						
						$.post(servletName,x,function (r){
							
							if(r.data==1)
								{
								$('button[name="save"]').removeClass('hideButton');
								$('button[name="update"]').addClass('hideButton');
									alert("Partner Rejected....");
									$( ".vendorMasterApp" ).trigger( "click" );
									
								}
							else if(r.data == 2)
							{
								bootbox.alert("First take all assets from this user.");
								}
							else
								{
									bootbox.alert("Try again.");
								}
							$('.loginBtn').removeAttr('disabled');
						},'json');
					
				
			}
});

}


function getSubMenu(ids,dropDownId,servletName)
{
	debugger;
	var t=false;
	var id=ids.value;
	if(ids.value == undefined)
		{
		id = ids;
		}
		
	
	if(ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	
	else
		{
			$.post(servletName,{action:'SubMenuDropDownResult' , menu : id},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
									for(var i = 0; i < r.data.length ; i++)
									{
									
									
									for (var key in r.data[i])
							        {
										id=r.data[i][key];
										break;
							        }
									for (var key in r.data[i])
							        {
										val=r.data[i][key];
										
							        }
									list = list + '<option value="'+id+'"> '+val+'</option>';
									}
							}
					
						$('#'+dropDownId).html(list);
						$('#'+dropDownId).focus();
					}
				else
					{
						bootbox.alert("Try again.");
					}
				if(callback) callback(t);
			},'json');
			}
	
}
/*function uservalid(id){
	
 		
			x=$('#'+id).val();
			if(x==null){
				alert("Please select any one option.")
			}
	
}*/
function getcheck(id){
	
 		
			$('#'+id).html('');
	
}
function getSubMenu1(ids,user,dropDownId,servletName)
{
	
	var t=false;
	var id=ids.value;
	if(ids.value == undefined)
		{
		id = ids;
		}
		
	//
	if(ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	
	else
		{
			$.post(servletName,{action:'SubMenuDropDownResult1' , menu1 : id, user : user},function (r){
				
				if(r.data)
					{
						//
						/*const result = r.data.filter((thing, index, self) =>
  index === self.findIndex((t) => (
    t.userclass === thing.userclass && t.username === thing.username
  ))
)*/
var result = r.data.filter(value => JSON.stringify(value) !== '{}');

console.log(result);
/*var uniqueArray = removeDuplicates(result, "userclass");
console.log("uniqueArray is: " + JSON.stringify(uniqueArray));*/
					t=true;
					var list= '';
						if(result.length == 0)
							{
								//bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
									for(var i = 0; i < result.length ; i++)
									{
									
									
									for (var key in result[i])
							        {
										id=result[i][key];
										break;
							        }
									for (var key in result[i])
							        {
										val=result[i][key];
										
							        }
									list = list + '<option value="'+id+'"> '+val+'</option>';
									}
							}
					
						$('#'+dropDownId).html(list);
						$('#'+dropDownId).focus();
					}
				else
					{
						bootbox.alert("Try again.");
					}
				if(callback) callback(t);
			},'json');
			}
	
}

//Move Right Left For Master
$("#MoveRight").click(function(){
	
	
	
	var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("User Permission is already exists.");
				}
				else
				{
	var selectedList='';
	$.each($("#subgroupDataForMaster option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForMasterSelected") != null){
		
			$.each($("#subgroupDataForMasterSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForMasterSelected').append(selectedList);
	}//
	
	}, 'json' );
	 
});
$("#MoveLeft").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForMasterSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForMaster').append(selectedList);
	
	 
});
//move right left for stock
$("#MoveRightstock").click(function(){
	
	
	
		var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("User Permission is already exists.");
				}
				else
				{
	var selectedList='';
	$.each($("#subgroupDataForStock option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForStockSelected") != null){
		
			$.each($("#subgroupDataForStockSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForStockSelected').append(selectedList);
	
	}//
	
	}, 'json' ); 
});
$("#MoveLeftstock").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForStockSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForStock').append(selectedList);
	
	 
});
//Move Right Left For Direct Po
/*$("#MoveRightDirectpo").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForDirectPo option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForDirectPoSelected") != null){
		
			$.each($("#subgroupDataForDirectPoSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForDirectPoSelected').append(selectedList);
	
	 
});*/
/*$("#MoveLeftDirectpo").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForDirectPoSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForDirectPo').append(selectedList);
	
	 
});*/
//Move Right Left For order
$("#MoveRightOrder").click(function(){
	
	var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("User Permission is already exists.");
				}
				else
				{
	var selectedList='';
	$.each($("#subgroupDataForOrder option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForOrderSelected") != null){
		
			$.each($("#subgroupDataForOrderSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForOrderSelected').append(selectedList);
	}//
	
	}, 'json' );
	 
});
$("#MoveLeftOrder").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForOrderSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForOrder').append(selectedList);
	
	 
});
//Move Right Left For jobwork
$("#MoveRightJobWork").click(function(){
	
	
	var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("User Permission is already exists.");
				}
				else
				{
	var selectedList='';
	$.each($("#subgroupDataForJobWork option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForJobWorkSelected") != null){
		
			$.each($("#subgroupDataForJobWorkSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForJobWorkSelected').append(selectedList);
	}//
	
	}, 'json' );
	 
});
$("#MoveLeftJobWork").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForJobWorkSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForJobWork').append(selectedList);
	
	 
});
//Move Right Left For stocktransfer
$("#MoveRightStocktransfer").click(function(){
	
	var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("User Permission is already exists.");
				}
				else
				{
	var selectedList='';
	$.each($("#subgroupDataForStockTransfer option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForStockTransferSelected") != null){
		
			$.each($("#subgroupDataForStockTransferSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForStockTransferSelected').append(selectedList);
	
	 }//
	
	}, 'json' );
});
$("#MoveLeftStocktransfer").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForAssetsSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForStockTransfer').append(selectedList);
	
	 
});
//Move Right Left For Report
$("#MoveRightReport").click(function(){
	
	var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("User Permission is already exists.");
				}
				else
				{
	var selectedList='';
	$.each($("#subgroupDataForReport option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForReportSelected") != null){
		
			$.each($("#subgroupDataForReportSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForReportSelected').append(selectedList);
	
	 }//
	
	}, 'json' );
});
$("#MoveLeftReport").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForReportSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForReport').append(selectedList);
	
	 
});
//Move Right Left For tagging
$("#MoveRightTagging1").click(function(){
	var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("User Permission is already exists.");
				}
				else
				{
	var selectedList='';
	$.each($("#subgroupDataForTagging1 option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForTagging1Selected1") != null){
		
			$.each($("#subgroupDataForTagging1Selected1 option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForTagging1Selected1').append(selectedList);
	}//
	
	}, 'json' );
	 
});
$("#MoveLeftTagging1").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForTagging1Selected1 option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForTagging1').append(selectedList);
	
	 
});
//Move Right Left For Assets
$("#MoveRightAssets").click(function(){
	
	var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("User Permission is already exists.");
				}
				else
				{
	var selectedList='';
	$.each($("#subgroupDataForAssets option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForAssetsSelected") != null){
		
			$.each($("#subgroupDataForAssetsSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForAssetsSelected').append(selectedList);
	}//
	
	}, 'json' );
	 
});
$("#MoveLeftAssets").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForAssetsSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForAssets').append(selectedList);
	
	 
});
//Move Right Left For Transfer
$("#MoveRightTransfer").click(function(){
	//
	var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("User Permission is already exists.");
				}
				else
				{
	var selectedList='';
	$.each($("#subgroupDataForTransfer option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForTransferSelected") != null){
		
			$.each($("#subgroupDataForTransferSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForTransferSelected').append(selectedList);
	}//
	
	}, 'json' );
	 
});
$("#MoveLeftTransfer").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForTransferSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForTransfer').append(selectedList);
	
	 
});
//Move Right Left For Tagging
$("#MoveRightTagging").click(function(){
	
	var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("User Permission is already exists.");
				}
				else
				{
	var selectedList='';
	$.each($("#subgroupDataForTagging option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForTaggingSelected") != null){
		
			$.each($("#subgroupDataForTaggingSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForTaggingSelected').append(selectedList);
	
	 }//
	
	}, 'json' );
});
$("#MoveLeftTagging").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForTaggingSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForTagging').append(selectedList);
	
	 
});
//Move Right Left For Depreciation
$("#MoveRightDepreciation").click(function(){
	
	var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("User Permission is already exists.");
				}
				else
				{
	var selectedList='';
	$.each($("#subgroupDataForDepreciation option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForDepreciationSelected") != null){
		
			$.each($("#subgroupDataForDepreciationSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForDepreciationSelected').append(selectedList);
	}//
	
	}, 'json' );
	 
});
$("#MoveLeftDepreciation").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForDepreciationSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForDepreciation').append(selectedList);
	
	 
});
//Move Right Left For Helpdesk
$("#MoveRightHelpdesk").click(function(){
	
	var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("User Permission is already exists.");
				}
				else
				{
	var selectedList='';
	$.each($("#subgroupDataForHelpdesk option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForHelpdeskSelected") != null){
		
			$.each($("#subgroupDataForHelpdeskSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForHelpdeskSelected').append(selectedList);
	}//
	
	}, 'json' );
	 
});
$("#MoveLeftHelpdesk").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForHelpdeskSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForHelpdesk').append(selectedList);
	
	 
});
/*Move Right Left For Report
$("#MoveRightReport").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForReport option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForReportSelected") != null){
		
			$.each($("#subgroupDataForReportSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForReportSelected').append(selectedList);
	
	 
});
$("#MoveLeftReport").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForReportSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataFor').append(selectedList);
	
	 
});*/
//Move Right Left For Dashboard
$("#MoveRightDashboard").click(function(){
	
	var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("User Permission is already exists.");
				}
				else
				{
	var selectedList='';
	$.each($("#subgroupDataForDashboard option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#subgroupDataForDashboardSelected") != null){
		
			$.each($("#subgroupDataForDashboardSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#subgroupDataForDashboardSelected').append(selectedList);
	}//
	
	}, 'json' );
	 
});
$("#MoveLeftDashboard").click(function(){
	
	var selectedList='';
	$.each($("#subgroupDataForDashboardSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#subgroupDataForDashboard').append(selectedList);
	
	 
});
function ControlDivAccess(action,displayContent,createDetails,formName,servletName,Message,ColName,id,id_design,id_loc,id_subl) {
	
SessionCheck(function (ses){
	
		if(ses)
			{
			
		if(action == 'Edit')
			{
				if(id_loc != undefined)
			{
				GetAllSubloc(id_loc);
			}
				
				
				$.post(servletName,{action : 'Edit',id:id},function (r){
					if(r.data.length > 0){
						console.log(r.data);
							console.log('kajol');
			/*			var arr = typ_asst.split(',');
										
										for(var i=0; i<arr.length; i++)
											{
												$('select[name="id_asst_ownr1"] option[value=' + arr[i] + ']').attr('selected',true);
											}
											*/
											
										//var arr1 = id_dept.split(',');
										
								/*		for(var i=0; i<arr1.length; i++)
											{
												$('select[name="id_dept1"] option[value=' + arr1[i] + ']').attr('selected',true);
											}*/
									var arr1 = id_design.split(',');
										
									for(var i=0; i<arr1.length; i++)
											{
												$('select[name="id_design1"] option[value=' + arr1[i] + ']').attr('selected',true);
											}
										var arr2 = id_loc.split(',');
										
										for(var i=0; i<arr2.length; i++)
											{
												$('select[name="id_loc1"] option[value=' + arr2[i] + ']').attr('selected',true);
											}
											//
										var arr3 = id_subl.split(',');
										//
										for(var i=0; i<arr3.length; i++)
											{
												//
												$('select[name="id_subl1"] option[value=' + arr3[i] + ']').attr('selected',true);
											}	
						var i=0;
						var selectedList='',selectedList1='',selectedList2='',selectedList3='',selectedList4='',selectedList5='',selectedList6='',selectedList7='';
			debugger;
			var result = r.data.filter(value => JSON.stringify(value) !== '{}');
			var result1 = r.stock.filter(value => JSON.stringify(value) !== '{}');
			//var result2 = r.directpo.filter(value => JSON.stringify(value) !== '{}');
			var result3 = r.order.filter(value => JSON.stringify(value) !== '{}');
			var result4 = r.jobwork.filter(value => JSON.stringify(value) !== '{}');
			var result5 = r.stocktransfer.filter(value => JSON.stringify(value) !== '{}');
			var result6 = r.report.filter(value => JSON.stringify(value) !== '{}');
			var result7 = r.tagging1.filter(value => JSON.stringify(value) !== '{}');
				//
for(i=0;i<result.length;i++)
				{
					
				params=result[i];
		        selectedList += '<option value="'+params.userclass+'">'+params.username+'</option>';
}
//
for(i=0;i<result1.length;i++)
				{
					
				params=result1[i];
		        selectedList1 += '<option value="'+params.userclass+'">'+params.username+'</option>';
}
/*for(i=0;i<result2.length;i++)
				{
				params=result2[i];
		        selectedList2 += '<option value="'+params.userclass+'">'+params.username+'</option>';
}*/
for(i=0;i<result3.length;i++)
				{
				params=result3[i];
		        selectedList3 += '<option value="'+params.userclass+'">'+params.username+'</option>';
}
for(i=0;i<result4.length;i++)
				{
				params=result4[i];
		        selectedList4 += '<option value="'+params.userclass+'">'+params.username+'</option>';
}
for(i=0;i<result5.length;i++)
				{
				params=result5[i];
		        selectedList5 += '<option value="'+params.userclass+'">'+params.username+'</option>';
}
for(i=0;i<result6.length;i++)
				{
				params=result6[i];
		        selectedList6 += '<option value="'+params.userclass+'">'+params.username+'</option>';
}
for(i=0;i<result7.length;i++)
				{
				params=result7[i];
		        selectedList7 += '<option value="'+params.userclass+'">'+params.username+'</option>';
}

$('#subgroupDataForMasterSelected').append(selectedList);
$('#subgroupDataForMasterSelected').focus();
$('#subgroupDataForStockSelected').append(selectedList1);
$('#subgroupDataForStockSelected').focus();
/*$('#subgroupDataForDirectPoSelected').append(selectedList2);
$('#subgroupDataForDirectPoSelected').focus();*/
$('#subgroupDataForOrderSelected').append(selectedList3);
$('#subgroupDataForOrderSelected').focus();
$('#subgroupDataForJobWorkSelected').append(selectedList4);
$('#subgroupDataForJobWorkSelected').focus();
$('#subgroupDataForStockTransferSelected').append(selectedList5);
$('#subgroupDataForStockTransferSelected').focus();
$('#subgroupDataForReportSelected').append(selectedList6);
$('#subgroupDataForReportSelected').focus();
$('#subgroupDataForTagging1Selected1').append(selectedList7);
$('#subgroupDataForTagging1Selected1').focus();

/*$('#subgroupDataForAssetsSelected').append(selectedList1);
$('#subgroupDataForAssetsSelected').focus();
$('#subgroupDataForTransferSelected').append(selectedList2);
$('#subgroupDataForTransferSelected').focus();
$('#subgroupDataForTaggingSelected').append(selectedList3);
$('#subgroupDataForTaggingSelected').focus();
$('#subgroupDataForDepreciationSelected').append(selectedList4);
$('#subgroupDataForDepreciationSelected').focus();
$('#subgroupDataForHelpdeskSelected').append(selectedList5);
$('#subgroupDataForHelpdeskSelected').focus();
$('#subgroupDataForReportSelected').append(selectedList6);
$('#subgroupDataForReportSelected').focus();
$('#subgroupDataForDashboardSelected').append(selectedList7);
$('#subgroupDataForDashboardSelected').focus();*/
}

if(r.data1.length > 0){
						var j=0;
                for(j=0;j<r.data1.length;j++)
				{
					console.log("Hello");
				params1=r.data1[j];
		        if(params1.usermenu =='master'){
			    $('#master').prop('checked', true);
getSubMenu1(master,id,'subgroupDataForMaster','M_User_Type');
			
		}
		else if(params1.usermenu =='stock'){
			    $('#stock').prop('checked', true);
getSubMenu1(stock,id,'subgroupDataForStock','M_User_Type');
			
		}
	/*	else if(params1.usermenu =='directpo'){
			    $('#directpo').prop('checked', true);
getSubMenu1(directpo,id,'subgroupDataForDirectPo','M_User_Type');
			
		}*/
		else if(params1.usermenu =='order'){
			    $('#order').prop('checked', true);
getSubMenu1(order,id,'subgroupDataForOrder','M_User_Type');
			
		}
		else if(params1.usermenu =='jobwork'){
			    $('#jobwork').prop('checked', true);
getSubMenu1(jobwork,id,'subgroupDataForJobWork','M_User_Type');
			
		}
		else if(params1.usermenu =='stocktransfer'){
			    $('#stocktransfer').prop('checked', true);
getSubMenu1(stocktransfer,id,'subgroupDataForStockTransfer','M_User_Type');
			
		}
		else if(params1.usermenu =='report'){
			    $('#report').prop('checked', true);
getSubMenu1(report,id,'subgroupDataForReport','M_User_Type');
			
		}
		else if(params1.usermenu =='tagging1'){
			    $('#tagging1').prop('checked', true);
getSubMenu1(tagging1,id,'subgroupDataForTagging1','M_User_Type');
			
		}
		else{
			
		}
		
/*		else if(params1.usermenu =='assets'){
			    $('#assets').prop('checked', true);
getSubMenu1(assets,id,'subgroupDataForAssets','M_User_Type');
			
		}
		else if(params1.usermenu =='transfer'){
			    $('#transfer').prop('checked', true);
getSubMenu1(transfer,id,'subgroupDataForTransfer','M_User_Type');
			
		}
		else if(params1.usermenu =='tagging'){
			    $('#tagging').prop('checked', true);
getSubMenu1(tagging,id,'subgroupDataForTagging','M_User_Type');
			
		}
		else if(params1.usermenu =='depreciation'){
			    $('#depreciation').prop('checked', true);
getSubMenu1(depreciation,id,'subgroupDataForDepreciation','M_User_Type');
			
		}
		else if(params1.usermenu =='helpdesk'){
			    $('#helpdesk').prop('checked', true);
getSubMenu1(helpdesk,id,'subgroupDataForHelpdesk','M_User_Type');
			
		}
		else if(params1.usermenu =='report'){
			    $('#report').prop('checked', true);
getSubMenu1(report,id,'subgroupDataForReport','M_User_Type');
			
		}
		else if(params1.usermenu =='dashboard'){
			    $('#dashboard').prop('checked', true);
getSubMenu1(dashboard,id,'subgroupDataForDashboard','M_User_Type');
			
		}
		else{
			
		}*/
}
}

				
				$('#createUserAccess').show();
				$('#displayUserAccess').hide();
				$('#createAction').show();
				$('input[name="action"]').val('Update');
				$('button[name="update"]').removeClass('hideButton');
				$('button[name="save"]').addClass('hideButton');
				 $('#usertypeDataForuser').val(r.user[0].id_usertype);
			     $('#type_user').val(r.user[0].id_design);
                 $('input[name="type_user"]').val(r.user[0].nm_usertype);

			
		
		//DisplayLineItemDynamicallyForEditOpprtunity1(r.data[0].id_customer);
	},'json');
				
			}
		
	
	if(action=='Create')
		{
		if(!(servletName =='C_ManPowerDetails' || servletName == 'C_Contract')){
		$(".form-control").each(function(){
			$(".form-control").removeClass('error');
			
			if($(this).is("select"))
			{
				$(this).find('option:selected').attr('selected',false);
				//$(this).val('');
			}
			if ($(this).attr("readonly") == "readonly") 
		    { 
		        $(this).removeAttr("readonly"); 
		    } 
				$(this).attr("disabled", false);
			
		});
		}
		console.log("");
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
		
		if(servletName == 'M_User_Login')
			{
				//DataForCreatingUser();
				$('#Disabled').attr('checked',false);
				$('#Enabled').attr('checked',true);
			}
		
		
			$('input[name="action"]').val("Save");
			$('input[name="id"]').val("0");
			$('#'+formName)[0].reset();
			$('#'+displayContent).hide();
			$('#'+createDetails).show();
			/*$('#createAccessory').show();*/
			
		}
	if(action=='Cancel')
		{
		
			window.location = $('.useracces_event').attr('href');
		}
	if(action=='Save' || action=='Update')
		{
		
			DataInsertUpdateForAccess("Cancel",displayContent,createDetails,formName,servletName,Message,ColName);
			
		}
	
			}
});
}

function DataInsertUpdateForAccess(action,displayContent,createDetails,formName,servletName,Message,ColName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	t=false;
//	t = ValidationForm(formName);
		/*var nm_usertype=$('input[name="type_user"]').val();
			
	$.post('M_User_Access', { action: 'CheckExitsValues', value: nm_usertype,ColName: 'type_user'}, function(r) {
           debugger;
				if (r.data == 2) {
					bootbox.alert("Permission already given to this usertype...");
				}
				else
				{*/
					debugger;
					
	t = ValidationForm(formName);
	
	$('#subgroupDataForMasterSelected').removeClass('error');
	    /*if(t)
			{*/
			//
			var gg = $('#subgroupDataForMasterSelected');
			$.each($("#subgroupDataForMasterSelected option"), function(){ 
				$(this).attr('selected', 'selected');
			    });
            var stock = $('#subgroupDataForStockSelected');
			$.each($("#subgroupDataForStockSelected option"), function(){ 
				$(this).attr('selected', 'selected');
			    });
           /* var directpo = $('#subgroupDataForDirectPoSelected');
			$.each($("#subgroupDataForDirectPoSelected option"), function(){ 
				$(this).attr('selected', 'selected');
			    });*/
            var order = $('#subgroupDataForOrderSelected');
			$.each($("#subgroupDataForOrderSelected option"), function(){ 
				$(this).attr('selected', 'selected');
			    });
            var jobwork = $('#subgroupDataForJobWorkSelected');
			$.each($("#subgroupDataForJobWorkSelected option"), function(){ 
				$(this).attr('selected', 'selected');
			    });
            var transfer = $('#subgroupDataForStockTransferSelected');
			$.each($("#subgroupDataForStockTransferSelected option"), function(){ 
				$(this).attr('selected', 'selected');
			    });
            var report = $('#subgroupDataForReportSelected');
			$.each($("#subgroupDataForReportSelected option"), function(){ 
				$(this).attr('selected', 'selected');
			    });
           var tagging = $('#subgroupDataForTagging1Selected1');
			$.each($("#subgroupDataForTagging1Selected1 option"), function(){ 
				$(this).attr('selected', 'selected');
			    });
			
		/*	if(gg.val() == null)
					{
						bootbox.alert("Mandatory Field.");
						$('#subgroupDataForMasterSelected').addClass('error');
						$('#subgroupDataForMasterSelected').focus();
						t=false;
						exit(0);
					}*/
				
		var dept=$('#designDataForUserPermission').val();

		var loc=$('#locDataForUser').val();

		var sloc=$('#sublocDataForUser').val();

		
	

		 if(dept==null || dept ==''){

			Swal.fire({

  position: 'top',

  title: 'Please fill-up all the mandatory field'

});	

$('#designDataForUserPermission').addClass('error');

		}

		else if(loc==null || loc ==''){

			Swal.fire({

  position: 'top',

  title: 'Please fill-up all the mandatory field'

});	

$('#locDataForUser').addClass('error');

		}

		else if(sloc==null){

			Swal.fire({

  position: 'top',

  title: 'Please fill-up all the mandatory field'

});	

$('#sublocDataForUser').addClass('error');

		}	
			
			else{		
			bootbox.alert("User Permission is created successfully.");
			window.location = $('.useracces_event').attr('href');
			//}
		/*if(t)
		{*/
		
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
			var x = $('#'+formName).serialize();
			debugger;
			$.post(servletName,x,function (r){
				
				if(r.data)
					{
					debugger;
					bootbox.alert("User Permission is created successfully.");
						window.location = $('.useracces_event').attr('href');
					}
				else
				
					/*{
						bootbox.alert("Try again.");
					}*/
				$('.req').removeAttr('disabled');
				
			},'json');
			
			
			}
		
		/*}*/
	
			/*}*/
					
					//}//
	
	
	
	

//}, 'json')
				 





		}
	});

}



function removeDuplicates(originalArray, prop) {
     var newArray = [];
     var lookupObject  = {};

     for(var i in originalArray) {
        lookupObject[originalArray[i][prop]] = originalArray[i];
     }

     for(i in lookupObject) {
         newArray.push(lookupObject[i]);
     }
      return newArray;
 }


function checkEmail(str)
	{
	    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    if(!re.test(str))
	    alert("Please enter a valid email address");
	    $('input[name="mailid"]').val("");
	}

function GetAllSubloc(locIds)
{
	  $.post('M_Loc',{action : 'GetAllSubloc' , locIds : locIds} , function (r){
			 if(r.data.length > 0)
				 {
				 	var list = '';
				 	for(var i = 0; i < r.data.length ; i++)
					{
					for (var key in r.data[i])
			        {
						id=r.data[i][key];
						break;
			        }
					for (var key in r.data[i])
			        {
						val=r.data[i][key];
			        }
					list = list + '<option value="'+id+'"> '+val+'</option>';
					}
					$('#sublocDataForUser').html(list);
				 }
			 else
				 {
				 $('#sublocDataForUser').html('');
				 }
		  },'json');	

}

function EditFunVen(servletName,displayContent,createDetails,id)
{
	//
SessionCheck(function (ses){
	if(ses)
	{
		$('button[name="save"]').addClass('hideButton');
		$('button[name="update"]').removeClass('hideButton');
		
		
			
					
						$.post(servletName,{action : 'Edit',id : id},function (r){
							if(r.data)
								{
									$('#'+displayContent).hide();
									$('#'+createDetails).show();
									
									for(var i = 0; i < r.data.length ; i++)
									{
										for (var key in r.data[i])
								        {
												if($('select[name='+key+']').is("select"))
												{
													$('select[name='+key+'] option[value=' + r.data[i][key] + ']').prop('selected',true);
												}
											else
												{
													$('input[name='+key+']').val(r.data[i][key]);
												}
								        }
									}
									
									//HideShowUserPermission(userType);
									
									$('#cpwdConfirm').removeClass('error');
									$('#Enabled').val('1');
									$('#Disabled').val('0');
									if(r.data[0].status_user == 1)
										{
											$('#Enabled').val('1');
											$('#Enabled').attr('checked',true);
											$('.disableDate').hide();
											$('.enableDate').show();
										}
									else
										{
											$('#Disabled').val('0');
											$('#Disabled').attr('checked',true);
											$('.disableDate').show();
											$('.enableDate').hide();
											
										}
									
									if(r.data[0].dtexpdlvr != "01/01/1900"){
								$('input[name="dt_exp_dlvr"]').val(r.data[0].dtexpdlvr);
								}
								else{
									$('input[name="dt_exp_dlvr"]').val("");
								}
								if(r.data[0].dtexpdlvrend != "01/01/1900"){
								$('input[name="dt_exp_dlvr_end"]').val(r.data[0].dtexpdlvrend);
								}
								else{
									$('input[name="dt_exp_dlvr_end"]').val("");
								}
								if(r.data[0].dtexpdlvr1 != "01/01/1900"){
								$('input[name="dt_exp_dlvr1"]').val(r.data[0].dtexpdlvr1);
								}
								else{
									$('input[name="dt_exp_dlvr1"]').val("");
								}
								if(r.data[0].dtexpdlvrend1 != "01/01/1900"){
								$('input[name="dt_exp_dlvr1_end"]').val(r.data[0].dtexpdlvrend1);
								}
								else{
									$('input[name="dt_exp_dlvr1_end"]').val("");
								}
								
								if(r.data[0].dtexpdlvr2 != "01/01/1900"){
								$('input[name="dt_exp_dlvr2"]').val(r.data[0].dtexpdlvr2);
								}
								else{
									$('input[name="dt_exp_dlvr2"]').val("");
								}
								
								if(r.data[0].dtexpdlvrend2 != "01/01/1900"){
								$('input[name="dt_exp_dlvr2_end"]').val(r.data[0].dtexpdlvrend2);
								}
								else{
									$('input[name="dt_exp_dlvr2_end"]').val("");
								}
								if(r.data[0].dtexpdlvr3 != "01/01/1900"){
								$('input[name="dt_exp_dlvr3"]').val(r.data[0].dtexpdlvr3);
								}
								else{
									$('input[name="dt_exp_dlvr3"]').val("");
								}
								if(r.data[0].dtexpdlvrend3 != "01/01/1900"){
								$('input[name="dt_exp_dlvr3_end"]').val(r.data[0].dtexpdlvrend3);
								}
								else{
									$('input[name="dt_exp_dlvr3_end"]').val("");
								}
								if(r.data[0].dtexpdlvr4 != "01/01/1900"){
								$('input[name="dt_exp_dlvr4"]').val(r.data[0].dtexpdlvr4);
								}
								else{
									$('input[name="dt_exp_dlvr4"]').val("");
								}
								if(r.data[0].dtexpdlvrend4 != "01/01/1900"){
								$('input[name="dt_exp_dlvr4_end"]').val(r.data[0].dtexpdlvrend4);
								}
								else{
									$('input[name="dt_exp_dlvr4_end"]').val("");
								}
								$('input[name="action"]').val("Update");
									$('input[name="id"]').val(id);
										//
										
											
										/*var arr3 = r.data[0].id_grp.split(',');
										
										for(var i=0; i<arr3.length; i++)
											{
												$('select[name="id_grp1"] option[value=' + arr3[i] + ']').attr('selected',true);
											}
											*/
										
									
									
									
									
								}
							
							
							
						},'json');
						
					
			}});
}
	
function number2text(value) {
    var fraction = Math.round(frac(value)*100);
    var f_text  = "";

    if(fraction > 0) {
        f_text = "and "+convert_number(fraction)+" ";
    }

    return convert_number(value)+"  "+f_text+" Only";
}
function frac(f) {
    return f % 1;
}

function convert_number(number)
{
    if ((number < 0) || (number > 999999999)) 
    { 
        return "NUMBER OUT OF RANGE!";
    }
    var Gn = Math.floor(number / 10000000);  /* Crore */ 
    number -= Gn * 10000000; 
    var kn = Math.floor(number / 100000);     /* lakhs */ 
    number -= kn * 100000; 
    var Hn = Math.floor(number / 1000);      /* thousand */ 
    number -= Hn * 1000; 
    var Dn = Math.floor(number / 100);       /* Tens (deca) */ 
    number = number % 100;               /* Ones */ 
    var tn= Math.floor(number / 10); 
    var one=Math.floor(number % 10); 
    var res = ""; 

    if (Gn>0) 
    { 
        res += (convert_number(Gn) + " Crore"); 
    } 
    if (kn>0) 
    { 
            res += (((res=="") ? "" : " ") + 
            convert_number(kn) + " Lakh"); 
    } 
    if (Hn>0) 
    { 
        res += (((res=="") ? "" : " ") +
            convert_number(Hn) + " Thousand"); 
    } 

    if (Dn) 
    { 
        res += (((res=="") ? "" : " ") + 
            convert_number(Dn) + " Hundred"); 
    } 


    var ones = Array("", "One", "Two", "Three", "Four", "Five", "Six","Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen","Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen","Nineteen"); 
var tens = Array("", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty","Seventy", "Eighty", "Ninety"); 

    if (tn>0 || one>0) 
    { 
        if (!(res=="")) 
        { 
            res += "  "; 
        } 
        if (tn < 2) 
        { 
            res += ones[tn * 10 + one]; 
        } 
        else 
        { 

            res += tens[tn];
            if (one>0) 
            { 
                res += (" " + ones[one]); 
            } 
        } 
    }

    if (res=="")
    { 
        res = "zero"; 
    } 
    return res;
}

