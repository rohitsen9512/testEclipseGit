<div style="padding:10px;">
	<!--  <a href="#"> Vendor Details</a>-->
</div>
<script type="text/javascript">

$(function() {
	
	$('button[name="update"]').addClass('hideButton');
	
	DisplayDataApprove('M_Vendor','displayVendor','createVendor');
	
});

/* $('#nm_vendorId').keyup( function(){
	CheckValWhichAllReadyExit('M_Vendor' , 'Vendor name already exist.' , 'nm_ven');
	});
	
$('#cd_vendorId').keyup( function(){
	CheckValWhichAllReadyExit('M_Vendor' , 'Vendor code already exist.' , 'cd_ven');
	}); */
</script>


	<div id="displayVendor">
		
		<table width="100%" height="100%">
			<tr>
				<td>
				<div  id="displayVendor">
			<input type="text" name="searchWord" value="" placeholder="Search......" onkeyup="DisplayDataApp('M_Vendor','displayVendor','createVendor')">
</div>
					
					<!-- <button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDivApp('Create','displayVendor','createVendor','submitVendor','M_Vendor')">Create Partner</button>
					<button type="button" style="float:left;"  class="btn btn-primary" >Send Link</button>
				 --></td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered vendorForDisplay">
						<tr class="new">
							<td><strong>Vendor Name</strong></td>
							<td><strong>Vendor Code</strong></td>
							<td><strong>Type Of Vendor</strong></td>
							<td><strong>Contact Person Name</strong></td>
							<td><strong>Phone Number</strong></td>
							<td><strong>E-mail Id</strong></td>
							<td><strong>Approve/Reject</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>
	
	
	
	<div id="createVendor" style="display:none;">
	<form name="submitVendor" id="submitVendor">
		<table  class="table table-bordered">
		<tr>
 			<td colspan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 420px;">Vendor Details</p></td>
 		</tr>
		<tr>
			<td><b>Vendor Name<font color="red">*</font></b></td>
			<td ><input id="nm_vendorId" type="text"  name="nm_ven" readonly class="common-validation" data-valid="mand" autofocus="" value=""></td>
			<td ><b>Vendor Code<font color="red">*</font></b></td>
			<td ><input id="cd_vendorId" type="text"  name="cd_ven"   value=""  class="common-validation" data-valid="mand" readonly></td>
		</tr>
		<tr>
			<td ><b>Address 1<font color="red">*</font></b></td>
			<td ><input type="text"  name="add1" value=""  readonly class="common-validation" data-valid="mand" ></td>

			<td ><b>Address 2</b></td>

			<td ><input type="text"  name="add2" value="" readonly class="common-validation" ></td>

			
		</tr>
		<tr>
			<td ><b>Address3</b></td>
			<td ><input type="text"  name="add3" value=""  readonly class="common-validation" ></td>
			<!-- <td><b>Type Of Vendor<font color="red">* </font>:</b></td>
			<td>
				<input type ="checkbox" name="procured" value="Procured" ><b>Procured </b>
				<input type ="checkbox" name="service" value="Service" ><b>Service </b>
			
			</td> -->
			<td ><b>City<font color="red">*</font></b></td>
			<td ><input type="text"  name="city" value="" readonly class="common-validation" data-valid="mand" ></td>

			
		</tr>
		<tr>
			<td ><b>State<font color="red">*</font></b></td>

			<td ><input type="text"  name="state" value="" readonly class="common-validation" data-valid="mand" ></td>

			<td ><b>Postal Code <font color="red">*</font></b></td>

			<td ><input type="text"  name="pin" value="" readonly ></td>

			
		</tr>
		<tr>
			<td ><b>Country<font color="red">*</font></b></td>

			<td ><input type="text"  name="country" value="" readonly  class="common-validation" data-valid="mand" ></td>

			<td ><b>Telephone<font color="red">*</font></b></td>

			<td ><input type="text"  name="phone" value="" readonly class="common-validation" data-valid="num"></td>

		</tr>
		<tr>
       		<td ><b>Mobile No <font color="red">*</font></b></td>

			<td ><input type="text"  name="ct_mob" value="" readonly class="common-validation" data-valid="mand"></td>
			<td ><b>E-mail Id <font color="red">*</font></b></td>

    		<td ><input type="text"  name="mailid" value="" readonly class="common-validation" data-valid="mand"></td>
    		</tr>
		<tr>
		    <td ><b>GST No <font color="red">*</font></b></td>

			<td ><input type="text"  name="fax" value=""  readonly class="common-validation" data-valid="num"></td>
		    
			<td  ><b>TIN Number <font color="red">*</font> </b></td>

      		<td  ><input type="text"  name="tin"  readonly class="common-validation" data-valid="num"></td>
			
			
		</tr>
		<tr>
			<td ><b>CIN Number <font color="red">*</font></b></td>

			<td ><input type="text"  name="kst" value=""  readonly class="common-validation" data-valid="num"></td>

			
			<td ><b>MSME Number <font color="red">*</font> </b></td>

			<td ><input type="text"  name="cst"  value="" readonly class="common-validation" data-valid="num"></td>

			
		</tr>
		<tr>
			<td ><b>PAN Number <font color="red">*</font></b></td>

			<td ><input type="text"  name="pan"  readonly value=""
			 class="common-validation" ></td>

			
			<td ><b>TAN <font color="red">*</font></b></td>

			<td ><input type="text"  name="servicetax" value=""  readonly class="common-validation" data-valid="num"></td>

			
		</tr>
		
		<tr>
			<td ><b>Nature Of Business<font color="red">*</font></b></td>

			<td ><input type="text"  name="nature_bus"  value=""
			 class="common-validation" data-valid="mand" readonly></td>

			
			<td ><b>Year of Inc./ in business since <font color="red">*</font></b></td>

			<td ><input type="text"  name="bus_yr" value=""  class="common-validation" readonly data-valid="num"></td>

			
		</tr>
		<tr>
				<td><strong>Group<font color="red">*</font></strong></td>
				
				<td>	
				<select id="groupDataForVendor" class="common-validation" name="id_grp" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subgroupDataForVendor','M_Subasset_Div')">
						<option value="">Select</option>
						
					</select>
					
				</td>
			<td><strong> Sub Group<font color="red">*</font></strong></td>
				<td>
					<select multiple="multiple" id="subgroupDataForVendor" class="common-validation" name="selected_id_sgroup" data-valid="mand">
						
						
					</select>
				</td>	
				
		</tr>
		<tr>
 			<td colspan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 420px;">Each Certificate Have 25 Score</p></td>
 		</tr>
		<tr>
		<td><b>MSME Declaration </b></td>
				<td><input id="file4ID1" type="file" name="file1" class="common-validation" value="" ></td>
				
				<td><b>TDS-Letter for ITR-206AB</b></td>
				<td><input id="file4ID2" type="file" name="file2" class="common-validation" value="" ></td>
		</tr>
		<tr>
		<td><b>Start Date /</b><b>End Date</b></td>
		<td><input type="text" name="dt_exp_dlvr" value="" class="common-validation readbaledata datepicker"  >
		 <input type="text" name="dt_exp_dlvr" value="" class="common-validation readbaledata datepicker" >
		</td>
		<td><b>Start Date /</b><b>End Date</b></td>
		<td><input type="text" name="dt_exp_dlvr" value="" class="common-validation readbaledata datepicker" >
		 <input type="text" name="dt_exp_dlvr" value="" class="common-validation readbaledata datepicker"  >
		</td>
		</tr>
		
		<tr>
		<td><b>CMMI Level 5</b></td>
				<td><input id="file4ID3" type="file" name="file3" class="common-validation" value="" ></td>
				<td><b>ISO 9000 Certificate </b></td>
				<td><input id="file4ID4" type="file" name="file4" class="common-validation" value="" ></td>
		</tr>
		<tr>
		<td><b>Start Date /</b><b>End Date</b></td>
		<td><input type="text" name="dt_exp_dlvr" value="" class="common-validation readbaledata datepicker"  >
		 <input type="text" name="dt_exp_dlvr" value="" class="common-validation readbaledata datepicker"  >
		</td>
		<td><b>Start Date /</b><b>End Date</b></td>
		<td><input type="text" name="dt_exp_dlvr" value="" class="common-validation readbaledata datepicker" >
		 <input type="text" name="dt_exp_dlvr" value="" class="common-validation readbaledata datepicker"  >
		</td>
		</tr>
		<tr>
		<td>
				<h5 style="text">Download  Fromat For MSME Declaration <a id="downloadmsme" href="" >Click To Download</a></h5><br>
			</td>
			<td colspan="3">
				<h5 style="text">Download  Fromat For TDS-Letter for ITR-206AB <a id="downloadtds" href="" >Click To Download</a></h5><br>
			</td>
			</tr>
    	<!-- <tr>
      		
      		
			<td><b>Type Of Vendor<font color="red">* </font>:</b></td>
			<td>
				<input type ="checkbox" name="procured" value="Procured" ><b>Procured </b>
				<input type ="checkbox" name="service" value="Service" ><b>Service </b>
			
			</td>
			<td></td>
			<td></td>
		</tr> -->
    	<tr>
 			<td colspan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 420px;">Bank Account Details</p></td>
 		</tr>
 		<tr>
			<td  ><b>IFSC Code <font color="red">*</font></b></td>

			<td ><input type="text"  name="ifsc" value="" class="common-validation" readonly data-valid="mand"></td>
			<td  ><b>Account Number<font color="red">*</font></b></td>

			<td ><input type="text"  name="acc_no" value="" class="common-validation" readonly data-valid="mand"></td>
			
		</tr>
		<tr>
			<td  ><b>Name on Account<font color="red">*</font></b></td>

			<td ><input type="text"  name="nm_account" value="" class="common-validation" readonly  data-valid="mand"></td>
			<td  ><b>Bank Name <font color="red">*</font></b></td>

			<td ><input type="text"  name="nm_bank" value="" class="common-validation" readonly data-valid="mand"></td>
			
		</tr>
		<tr>
			<td  ><b>Bank Branch</b></td>

			<td ><input type="text"  name="nm_branch" value="" readonly class="common-validation" ></td>
			<td  ><b>MICR Code <font color="red">*</font></b></td>

			<td ><input type="text"  name="micr_code" value="" readonly class="common-validation" data-valid="mand"></td>
			
		</tr>
		<tr>
			<td  ><b>Address <font color="red">*</font></b></td>

			<td ><input type="text"  name="add_bank" value="" readonly class="common-validation" data-valid="mand"></td>
			<td  ><b>Country <font color="red">*</font> </b></td>

			<td ><input type="text"  name="country_bank" value="" readonly class="common-validation" data-valid="mand"></td>
			
		</tr>
		<tr>
			<td  ><b>City <font color="red">*</font></b></td>

			<td ><input type="text"  name="city_bank" value="" readonly class="common-validation" data-valid="mand"></td>
			<td  ><b>State <font color="red">*</font></b></td>

			<td ><input type="text"  name="state_bank" value="" readonly class="common-validation" data-valid="mand"></td>
			
		</tr>
		<tr>
			<td  ><b>Postal Code <font color="red">*</font></b></td>

			<td ><input type="text"  name="postal_code_bank" value="" readonly class="common-validation" data-valid="mand"></td>
			<td  ><b>Telephone number</b></td>

			<td ><input type="text"  name="telephone_bank" value="" readonly class="common-validation" data-valid="mand"></td>
			
		</tr>
		<tr>
			<td  ><b>Fax Number</b></td>

			<td colspan="3"><input type="text"  name="fax_bank" readonly value="" class="common-validation" data-valid="mand"></td>
			
			
		</tr>
		<tr>
			<td  colspan="4" width="50%" style="background-color: blue;"><p style="font-size: 24px; background-color: blue;color: white;margin-left:370px;">Details of Contact Person</p></td>
			
		</tr>
		<tr>
			<td  ><b>Name</b></td>

			<td ><input type="text"  name="nm_contact" value="" readonly class="common-validation" ></td>
			<td ><b>Designation</b></td>

			<td ><input type="text"  name="ct_desig" value="" readonly class="common-validation"></td>
			
		</tr>
		
		<tr>
		
			<td ><b>Phone Number</b></td>
			<td ><input type="text"  name="ct_phone" value="" readonly class="common-validation" ></td>
			<td ><b>E-mail Id <font color="red">*</font></b></td>

    		<td ><input type="text"  name="ct_mailid" value="" readonly class="common-validation" data-valid="mand"></td>
		</tr>
		
		<tr>
       		
			
    		<td ><b>Password </b></td>
			<td colspan="3"><input type="text"  name="password" readonly value="" class="common-validation"></td>
			
				
		</tr>
		
		<tr>
       		
			<td ><b>Company url </b></td>
			<td colspan="3"><input type="text"  name="url"  readonly value="" class="common-validation"></td>
			
					<input type="hidden" name="action" value="">
					<input type="hidden" name="id" value="0">
					
		</tr>
			<tr>
			
			 <td ><b>App/Rej Remarks</b></td>
			<td colspan="3"><textarea  maxlength="2000" onkeyup="charcountupdate(this.value)" style="width: 568px;height: 69px;" rows="6" cols="5"  name ="apprejremarks" id ="remarks" placeholder="2000 Character Only......"  class="common-validation "></textarea><span id=charcount></span> </td>
			
			<!-- <td colspan="2"></td> -->
			</tr>

		<tr>
			<td  colspan="4">
				<button name="Approve" type="button" style="margin-left:450px;"   class="btn btn-primary" onclick="ControlDivApp('Approve','displayVendor','createVendor','submitVendor','M_Vendor','Vendor name already exist.,,Vendor code already exist.' , 'nm_ven,,cd_ven');">Approve</button>
				<button name="Reject" type="button"   class="btn btn-primary" onclick="ControlDivApp('Reject','displayVendor','createVendor','submitVendor','M_Vendor','' , '')">Reject</button>
				<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDivApp('Cancel','displayVendor','createVendor','submitVendor','M_Vendor');">Back</button>
					
			</td>
		</tr>

		</table>
	</form>
	</div>
	