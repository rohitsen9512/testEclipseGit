<div style="padding:10px;">
	<!-- <a href="#">Master ></a><a href="#">Declaration</a> -->
</div>

<div id="displayDeclaration">
		
		<table width="100%" height="100%">
			<tr>
				<td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayDeclaration','createDeclaration','submitDeclaration','M_Declaration')">Create Declaration</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover">
						<tr class="new">
							<td><strong>Declaration Type</strong></td>
							<td><strong>Declaration</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>


<div id="createDeclaration" style="display:none;">
	<form action="" name="submitDeclaration" id="submitDeclaration">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Declaration Details</p></td>
			</tr>
			<tr>
				<td><strong>Declaration Type<font color="red">*</font></strong></td>
				<td><input type="text" name="declarationType" mand="yes"></td> 
				
				
			</tr>
			<tr>
				<td><strong>Declaration<font color="red">*</font></strong></td>
				<td><input type="text" name="declaration" mand="yes"></td>
			</tr>
			<tr>
				<td colspan="2">
					
						<button type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayDeclaration','createDeclaration','submitDeclaration','M_Declaration')">save</button>
						<button type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayDeclaration','createDeclaration','submitDeclaration','M_Declaration')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>