

function UploadFileItem(module){
	$('.loading').show();
	$('#dialog').dialog('close')
		$('input[name="action"]').val(module);
		var x = $('#upload_prod').serialize();
		//alert(x);
		$.post('M_Upload_Master_Data',x,function (r){
		
	$('.loading').hide();
        bootbox.alert(" successfully Uploaded");
	
				
				window.location = $('.upload_excel_event').attr('href');
			
			
		});
	
		window.location = $('.upload_excel_event').attr('href');
	}	
	var ExcelToJSON = function(module) {
		
	    this.parseExcel = function(file) {
	      var reader = new FileReader();

	      reader.onload = function(e) {
	        var data = e.target.result;
	        var workbook = XLSX.read(data, {
	          type: 'binary'
	        });
	        workbook.SheetNames.forEach(function(sheetName) {
	          // Here is your object
	        	if(sheetName=='Sheet1')
	        		{
	        	 console.log(sheetName);
	        	 console.log(workbook.Sheets[sheetName]);
	          var XL_row_object = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
	          var json_object = JSON.stringify(XL_row_object);
	         // console.log(JSON.parse(json_object));
	var x=JSON.parse(json_object);
	//console.log(json_object);
	var list ='<form action="" name="upload_prod" id="upload_prod">	'+
		//	'<td colspan="10" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 350px;">Line Item Details</p></td></tr>'+ 
		'<table><tr>'+
		'<td colspan="18">	<button name="save" type="button" style="margin-left:150px;"   class="btn btn-primary" onclick="UploadFileItem(\''+module+'\');">Upload</button></td>'+
		'</tr>';
		var list3=include(x,list,module);
		
	list3+='</table></form>'+

	$('#dialog').html(list3);
	
	 // $( "#dialog" ).dialog();
	  
	  
	  $( "#dialog" ).dialog({
		  
          width: 1000,
          height: 500,
          modal: true,
		  close: function( event, ui ) {
				$( ".ItemCart" ).trigger( "click" );
			 // window.location.reload(true); 
			  
		  }
		});
	        
	        		}
	        	});
	      };

	      reader.onerror = function(ex) {
	        console.log(ex);
	      };

	      reader.readAsBinaryString(file);
	    };
	};
	
////file upload............
	   document.getElementById('upload').addEventListener('change', handleFileSelect, false);

	   function handleFileSelect(evt) {
		var module= $('select[name="module"]').val();
		      var files = evt.target.files; // FileList object
		       var xl2json = new ExcelToJSON(module);
		       xl2json.parseExcel(files[0]);
		     }
	   
function include(x,list,module)	{
	
	if(module=="location"){
	var list2="";
	list2=list+'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
			'<td><strong>Location Name</strong></td>'+
			'<td><strong>Location Code</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list2+='<tr>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="nm_loc'+i+'" value="'+(x[i].Location_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="cd_loc'+i+'" value="'+(x[i].Location_Code).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	}
	
	else if(module=="sub_location"){
	var list2="";
	list2=list+'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
			'<td><strong>Location Name</strong></td>'+
			'<td><strong>Sub-Location Name</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list2+='<tr>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="nm_loc'+i+'" value="'+(x[i].Location_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_subl'+i+'" value="'+(x[i].Sub_Location_Name).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	}
	
	else if(module=="building"){
	var list2="";
	list2=list+'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
			'<td><strong>Location Name</strong></td>'+
			'<td><strong>Sub-Location Name</strong></td>'+
			'<td><strong>Building Name</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list2+='<tr>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="nm_loc'+i+'" value="'+(x[i].Location_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_subl'+i+'" value="'+(x[i].Sub_Location_Name).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_building'+i+'" value="'+(x[i].Building_Name).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	}
	
	else if(module=="floor"){
	var list2="";
	list2=list+'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
			'<td><strong>Location Name</strong></td>'+
			'<td><strong>Sub-Location Name</strong></td>'+
			'<td><strong>Building Name</strong></td>'+
			'<td><strong>Floor Name</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list2+='<tr>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="nm_loc'+i+'" value="'+(x[i].Location_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_subl'+i+'" value="'+(x[i].Sub_Location_Name).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_building'+i+'" value="'+(x[i].Building_Name).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_flr'+i+'" value="'+(x[i].Floor_Name).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	}
	
	else if(module=="category"){
	var list2="";
	list2=list+'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
			'<td><strong>Category Name</strong></td>'+
			'<td><strong>Category Code</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list2+='<tr>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="nm_assetdiv'+i+'" value="'+(x[i].Category_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="cd_assetdiv'+i+'" value="'+(x[i].Category_Code).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	}
	
	else if(module=="sub_category"){
	var list2="";
	list2=list+'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
			'<td><strong>Category Name</strong></td>'+
			'<td><strong>Sub-Category Name</strong></td>'+
			'<td><strong>Sub-Category Code</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list2+='<tr>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="nm_assetdiv'+i+'" value="'+(x[i].Category_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="nm_s_assetdiv'+i+'" value="'+(x[i].Sub_Category_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="cd_s_assetdiv'+i+'" value="'+(x[i].Sub_Category_Code).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	}
	
	else if(module=="model"){
	var list2="";
	list2=list+'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
			'<td><strong>Category Name</strong></td>'+
			'<td><strong>Sub-Category Name</strong></td>'+
			'<td><strong>Model/Item Name</strong></td>'+
			'<td><strong>Asset Type</strong></td>'+
			'<td><strong>Manufacturer</strong></td>'+
			'<td><strong>Description</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list2+='<tr>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="nm_assetdiv'+i+'" value="'+(x[i].Category_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="nm_s_assetdiv'+i+'" value="'+(x[i].Sub_Category_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_model'+i+'" value="'+(x[i].Model_Name).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_model'+i+'" value="'+(x[i].typ_asst).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="mfr'+i+'" value="'+(x[i].Manufacturer).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="ds_asst'+i+'" value="'+(x[i].Description).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	}
	
	else if(module=="department"){
	var list2="";
	list2=list+'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
			'<td><strong>Department Name</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list2+='<tr>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="nm_dept'+i+'" value="'+(x[i].Department_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	}
	
	else if(module=="cost_center"){
	var list2="";
	list2=list+'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
			'<td><strong>Cost Center/Project Name</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list2+='<tr>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="nm_cc'+i+'" value="'+(x[i].Cost_Center_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	}
	
	else if(module=="user_type"){
	var list2="";
	list2=list+'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
			'<td><strong>User Type Name</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list2+='<tr>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="nm_usertype'+i+'" value="'+(x[i].User_Type_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	}
	
	else if(module=="designation"){
	var list2="";
	list2=list+'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
			'<td><strong>Designation Name</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list2+='<tr>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="nm_design'+i+'" value="'+(x[i].Designation_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	}
	
	else if(module=="employee"){
	var list2="";
	list2=list+'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
		'<td><strong>Employee Name<font color="red">  </font></strong></td>'+
			'<td><strong>Employee Code</strong></td>'+
			'<td><strong>Official Mail ID</strong></td>'+
			'<td><strong>Contact No.</strong></td>'+
			'<td><strong>Designation</strong></td>'+
			'<td><strong>Reporting Manager</strong></td>'+
			'<td><strong>Department</strong></td>'+
			'<td><strong>Cost Center</strong></td>'+
			'<td><strong>Location</strong></td>'+
			'<td><strong>Sub-Location</strong></td>'+
			'<td><strong>Building</strong></td>'+
			'<td><strong>Floor</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list2+='<tr>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Employee_Name'+i+'" value="'+(x[i].Employee_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Employee_Code'+i+'" value="'+(x[i].Employee_Code).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Mail_id'+i+'" value="'+(x[i].Mail_id).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Contact_No'+i+'" value="'+(x[i].Contact_No).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
        '<td><input type="text"style="padding: 0px;width: 248px;"  name="Designation'+i+'" value="'+(x[i].Designation).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Repoting_Manager'+i+'" value="'+(x[i].Repoting_Manager).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Department'+i+'" value="'+(x[i].Department).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Cost_Center'+i+'" value="'+(x[i].Cost_Center).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_loc'+i+'" value="'+(x[i].Location).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_subl'+i+'" value="'+(x[i].Sub_Location).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		 '<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_building'+i+'" value="'+(x[i].Building).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_flr'+i+'" value="'+(x[i].Floor).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	}
	else if(module=="tax"){
	var list2="";
	list2=list+'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
		'<td><strong>Tax1 Name<font color="red">  </font></strong></td>'+
			'<td><strong>Tax1 %</strong></td>'+
			'<td><strong>Tax2 Name<font color="red">  </font></strong></td>'+
			'<td><strong>Tax2 %</strong></td>'+
			'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list2+='<tr>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_tax1'+i+'" value="'+(x[i].Tax1_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="per_tax1'+i+'" value="'+(x[i].Tax1_Percentage).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="nm_tax2'+i+'" value="'+(x[i].Tax2_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="per_tax2'+i+'" value="'+(x[i].Tax2_Percentage).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
      '<input type="hidden" name="count" value="'+i+'">'+	
 '</tr>';
		
	}
	}
	
	return list2;
}


$("#module").change(function () {
	console.log(this.value);
	if(this.value==''){
		$("#download_master").attr('href', '#');
	}
	else{
	 
	  var file_location="AllExcel/";
      file_location+=this.value;
$("#download_master").attr('href', file_location+'.xlsx');
	 // $("#download_master").text($("#module option:selected").text());
}
	});
	


function testif(e) {
e.preventDefault();
  target = e.target || e.srcElement; // Support for IE6-8
var hreflink=target.getAttribute("href");
  if (hreflink == "#") {
	bootbox.alert("Please select a module first.")
}
else{
	window.location.href = hreflink;
}
}