


<%-- <%HttpSession session2 = request.getSession(); %>
	<script type="text/javascript">
		$(function (){
			$( "#menu" ).menu();
			var UserType = '<%= (String)session2.getAttribute("UserType") %>';
			//LoginAccess(UserType , 'financeAccess.properties' , 'commonActive');
		});
		
		
		</script> --%>
		
		
		
		<ul class="nav nav-treeview sidebar_css">
              <li class="commonActive depreciationMaster1 nav-item" onclick="">

             <a href="javascript:changeSubContent('Depreciation/Depreciation_master1.jsp','depreciationMaster1_event')" class="nav-link depreciationMaster1_event">
                <i class="fas fa-hand-point-right"></i>
                  <p>Depreciation Master</p>
                </a>
              </li>
              
		
              
				 <li class="commonActive depreciation nav-item" >
             <a href="#" class="nav-link">
                 <i class="right fas fa-angle-left"></i><span class="badge badge-info right"></span>
                 <i class="fas fa-hand-point-right"></i>
                  <p>
                  Depreciation
                  </p>
                </a>
                <ul class="nav nav-treeview">
              <li class="commonActive yearlyDepreciation nav-item"  onclick="">
             <a href="javascript:changeSubContent('Depreciation/Yearly_depreciation.jsp','yearlyDepreciation_event')" class="nav-link yearlyDepreciation_event">
                  <i class="fas fa-arrow-circle-right nav-icon"></i>
                  <p>Yearly Depreciation</p>
                </a>
              </li>
              <li class="commonActive monthlyDepreciation nav-item " onclick="">

             <a href="javascript:changeSubContent('Depreciation/Monthly_depreciation.jsp','monthlyDepreciation_event')" class="nav-link monthlyDepreciation_event">
                  <i class="fas fa-arrow-circle-right nav-icon"></i>
                  <p>Monthly Depreciation</p>
                </a>
              </li>
             </ul>
              </li>
              
               <li class="commonActive depreciationConfig Dep_config_CA Dep_config_IT nav-item ">
             <a href="#" class="nav-link">
                  <i class="right fas fa-angle-left"></i><span class="badge badge-info right"></span>
                    <i class="fas fa-hand-point-right"></i>
                  <p>
                  Depreciation Config 
                
                   </p>
                </a>
                <ul class="nav nav-treeview">
              <li class="commonActive location Dep_config_CA nav-item"  onclick="">
             <a href="javascript:changeSubContent('Depreciation/Dep_Config_CA.jsp','Dep_config_CA_event')" class="nav-link Dep_config_CA_event">
                  <i class="fas fa-arrow-circle-right nav-icon"></i>
                  <p>CA Yearly</p>
                </a>
              </li>
              <li class="commonActive Dep_config_IT monthlyDepreciation nav-item " onclick="">

             <a href="javascript:changeSubContent('Depreciation/Dep_Config_IT.jsp','Dep_config_IT_event')" class="nav-link Dep_config_IT_event">
                  <i class="fas fa-arrow-circle-right nav-icon"></i>
                  <p>IT Act</p>
                </a>
              </li>
             </ul>
              </li>
		
		<li class="commonActive addition_Deletion nav-item " onclick="">

             <a href="javascript:changeSubContent('Depreciation/Addition_deletion.jsp','addition_Deletion_event')" class="nav-link addition_Deletion_event">
               <i class="fas fa-hand-point-right"></i>
                  <p>Addition/Deletion</p>
                </a>
              </li>
              </ul>
		
		
		
		
		
	