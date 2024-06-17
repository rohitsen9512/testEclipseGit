<div style="padding:10px;">
	<!-- <a href="#">Master ></a><a href="#">Budget Master > </a><a href="#">Add Budget</a> -->
</div>
<script type ="text/javascript" src="js/depreciationCommon.js"></script>
<script type ="text/javascript" src="common.js"></script>
<script type="text/javascript">
$(function() {
	
	var currentDate = new Date();
	$( ".datepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd",
	      autoSize: true,
	      altFormat: "DD-MMM-YYYY",
	      
	    });
	
	DropDownDataDisplay("M_Dept","budForDepCost",function (status){
	if(status)
		{
		
		DisplayDropDownDataForGroup('M_Asset_Div','groupForBudget','CapG',function (status){
			if(status)
			{
				DisplayDropDownData("M_Emp_User","empforSpoc",function (status){
					if(status)
					{
					
		
			DisplayFinanceYear(function (status){
				if(status)
				{
					DropdownResultForFinanceYear('financialYear');
					DisplayData('M_Budget','displayBudget','createBudget');
				}
				});
			}});
			}});
		}});
	
	jQuery("input#file4ID").change(function () {
		
		 var formData = new FormData();	 
			formData.append('file', $('#file4ID').get(0).files[0]);
			
				$.ajax({
				  url: 'Upload_File',
				    type: 'POST',
				    data: formData,
				    async: false,
				    cache: false,
				    contentType: false,
				    dataType: "json",
				    processData: false,
				    mimeType: "multipart/form-data",
				    success: function (r) {
				    	
				    		$('input[name="file_name"]').val(r.upload_inv);
				    		//bootbox.alert("File Uploaded successfully");
				    }
				},'json'); 
				
		//$( "#submitForUploadData" ).trigger( "click" );
	}); 
	
	
	$('button[name="update"]').addClass('hideButton');

	
	
});

function DisplayFinanceYear(callback)
{
	
$.post('M_Financial_Year',{action : 'DropDownResult'},function (r){
		
		var list= '<option value=""> Select</option>';
		var t=false;	
		if(r.data)
			{
			t=true;
				for(var i = 0; i < r.data.length ; i++)
				{
				if(r.data[i].active_year == 1)
					{
					list = list + '<option value="'+r.data[i].id_fincance+'" selected="true"> '+r.data[i].std_finance+' / '+r.data[i].end_finance+'</option>';
					}
				else
					{
						list = list + '<option value="'+r.data[i].id_fincance+'"> '+r.data[i].std_finance+' / '+r.data[i].end_finance+'</option>';
					}
				
				}
			
				$('#financialYearForSearch').html(list);
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		callback(t);
		
	},'json');
	
	
}
/* $('#nm_budgetId').keyup( function(){
	CheckValWhichAllReadyExit('M_Budget' , 'Budget already exist.' , 'nm_budget');
	});
	
$('#cd_budgetId').keyup( function(){
	CheckValWhichAllReadyExit('M_Budget' , 'Budget code already exist.' , 'cd_budget');
	}); */
	
function checkExitsBudget(id_cc)
{
	var id_dept=$('select[name="id_dept"]').val();
	var id_finance=$('#financialYear').val();
	var id_bu=$('select[name="id_bu"]').val();
	var id_model=$('select[name="id_model"]').val();
	if(id_dept !='' && id_finance !='' && id_cc !='')
		{
	$.post('M_Budget',{action : 'CheckExitsVal',id_bu : id_bu,id_dept:id_dept,id_finance:id_finance,id_model:id_model},function (r){
		
		if(r.data ==0)
			{
				alert('You have already \'Budget\' of this financial year. Please choose another one.');
			}
	
	},'json');
		}
	
}

function checkSearchRessultBudget(id_finance)
{
	DisplayData('M_Budget','displayBudget','createBudget');
}
</script>

<div id="displayBudget">
		
		<table width="100%" height="100%">
			<tr>
				<td>
				<b>Search According To Financial Year &nbsp;&nbsp;&nbsp;</b>
				<select name="id_finance" id="financialYearForSearch" onChange="checkSearchRessultBudget(this)">
						<option value="">Select</option>
					</select>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayBudget','createBudget','submitBudget','M_Budget')">Create Budget</button>
					</p>
					<button type="button" style="float:right; display:none"  class="btn btn-primary" onclick="ControlDivAdHoc('Create')">Create Ad-Hoc Budget</button>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover budgetdataForDisplay">
						<tr class="success new">
							<td><strong>Department</strong></td>
							<!-- <td><strong>Cost Center</strong></td> -->
							<td><strong>Financial Year</strong></td>
							<td><strong>Total Budget Allotted</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
</div>


<div id="createBudget" style="display:none;">
	<form action="" name="submitBudget" id="submitBudget">	
		<table align="center" width="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="font-size: 24px;color: white;margin-left: 400px;">Budget Details</p></td>
			</tr>
			
			<tr>
				<td><b>Select Financial Year<font color="red">*</font></b></td>
				<td>
					<select name="id_finance" id="financialYear"  style="width:140" class="common-validation" data-valid="mand" >
						<option value="">Select</option>
					</select>
				</td>
				<td><strong>SPOC<font color="red">*</font></strong></td>
				<td>
					
					 <select id="empforSpoc" class="common-validation" name="id_spoc" data-valid="mand" onchange="getDATAFromEMPuser(this)">
					 
					 	<option value="">Select</option>
					</select>
				</td>
				
			</tr>
			
			<tr>
			<td><strong>Department<font color="red">*</font></strong></td>
				<td>
					
					 <select id="budForDepCost" class="common-validation" name="id_dept" data-valid="mand" onChange="DisplaySubDropDownData(this,'nmfunction','M_Cost_Center')">
					 
					 	<option value="">Select</option>
					</select>
				</td>
			<td><strong>BU <font color="red">*</font></strong></td>
				<td>
					<select id="nmfunction" class="common-validation" name="id_cc" data-valid="mand" onChange="DisplaySubDropDownData(this,'subfunction','M_S_Function')">
						<option value="">Select</option>
						
					</select>
				</td>
				
			</tr>
			<tr>
			<td><strong>Function<font color="red">*</font></strong></td>
				<td>
					<select id="subfunction" class="common-validation" name="id_s_function" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subbu','M_BU')">
						<option value="">Select</option>
						
					</select>
				</td>
				<td><strong>Sub-Function<font color="red">*</font></strong></td>
				<td>
					<select id=subbu class="common-validation" name="id_bu" data-valid="mand" >
						<option value="">Select</option>
					</select>
				</td> 
				
			</tr>
			<tr>
			<td><strong>Group<font color="red">*</font></strong></td>
				<td>
					
					 <select id="groupForBudget" class="common-validation" name="id_assetdiv" data-valid="mand" onChange="DisplaySubDropDownDataForReport(this,'subAssetDivForBudget','M_Subasset_Div')">
					 
					 	<option value="">Select</option>
					</select>
				</td>
				<td><strong>Sub-Group<font color="red">*</font></strong></td>
				<td>
					<select id="subAssetDivForBudget" class="common-validation" name="id_s_assetdiv" data-valid="mand" onChange="SubDropDownDataDisplayForReport(this,'ModelDivForAssetBudget','M_Model');checkExitsBudget(this)">
						<option value="">Select</option>
					</select>
				</td> 
				
				
			</tr>
			<tr>
			<!-- <td><b>Select the Asset/Model</b></td>
				<td>
					<select id="ModelDivForAssetBudget" name="id_model"  style="width:140" onChange="checkExitsBudget(this)">
						<option value="">Select</option>
						
					</select>
				</td> -->
				<td><b>Transaction<font color="red">*</font> </b> </td>
				<td>
					<select  name="trans" class="common-validation"  data-valid="mand">
						<option value="">Select</option>
						<option value="trans">Transaction</option>
						<option value="non_trans">Non-Transaction</option>
					</select>
				</td>
				<td><b>Category<font color="red">*</font></b> </td>
				<td>
					<select  name="category" class="common-validation"  data-valid="mand">
						<option value="">Select</option>
						<option value="core">Core</option>
						<option value="non_core">Non-Core</option>
						<option value="bau">BAU</option>
					</select>
				</td>
				</tr>
				<tr>
				
			<td ><strong>Total Budget<font color="red">*</font></strong></td>
				<td colspan="3"><input id="total_budAllo" class="common-validation" type="text" name="annual_bud" data-valid="num" onblur="InsuranceAndFrightCheck('annual_bud')"></td>
				
			</tr>
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
			<tr>
				<td colspan="4">
					
						<button type="button" name="save" style="margin-left:400px;" class="btn btn-primary" onclick="ControlDiv('Save','displayBudget','createBudget','submitBudget','M_Budget','','','','')">Save</button>
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayBudget','createBudget','submitBudget','M_Budget','','','','')">Update</button>
						<button type="button" name="cancel" class="btn btn-primary" onclick="ControlDiv('Cancel','displayBudget','createBudget','submitBudget','M_Budget')">Cancel</button>
				</td>
			</tr>
		</table>
	</form>
</div>

<div id="createAdHocBudget" style="display:none;">
	<form action="" name="submitAdHocBudget" id="submitAdHocBudget">	
		<table align="center" width="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="font-size: 24px;color: white;margin-left: 400px;">Ad-HocBudget Details</p></td>
			</tr>
			
			<tr>
				<td><b>Select Financial Year<font color="red">*</font></b></td>
				<td>
					<select name="id_finance_adhoc" id="financialYearAdhoc"  style="width:140" class="common-validation" data-valid="mand" >
						<option value="">Select</option>
					</select>
				</td>
				
			</tr>
			<input type="hidden" name="actionAdhoc" value="SaveAdHoc">
			<tr>
				<td colspan="4">
					
						<button type="button" name="save" style="margin-left:400px;" class="btn btn-primary" onclick="ControlDivAdHoc('SaveAdHoc')">Create</button>
						<button type="button" name="cancel" class="btn btn-primary" onclick="ControlDivAdHoc('Cancel')">Back</button>
				</td>
			</tr>
		</table>
	</form>
</div>

