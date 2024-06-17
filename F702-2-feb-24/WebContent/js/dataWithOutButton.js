function getListViewWithoutBtn(tableID,id){
	

$("#"+tableID).DataTable({
"responsive": true, "lengthChange": false, "autoWidth": false,
//"buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"]

"bDestroy": true,
});


/* $('#example2').DataTable({
"paging": true,
"lengthChange": false,
"searching": false,
"ordering": true,
"info": true,
"autoWidth": false,
"responsive": true,
}); */



}