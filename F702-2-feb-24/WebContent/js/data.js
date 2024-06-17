function getButtonsForListView(tableID,id){
	

$("#"+tableID).DataTable({
"responsive": true, "lengthChange": false, "autoWidth": false,
//"buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"]
"buttons":
[
{
text: '<i class="fas fa-clipboard" data-toggle="tooltip" title="Copy"></i>',
extend: 'copy',
},
{
text: '<i class="fas fa-file-csv" data-toggle="tooltip" title="CSV"></i>',
extend: 'csv',
title: id,
extension: '.csv'
},
{
text: '<i class="far fa-file-excel" data-toggle="tooltip" title="Excel"></i>',
extend: 'excel',
title: id,
extension: '.xls'
},
{
text: '<i class="fas fa-file-pdf" data-toggle="tooltip" title="PDF"></i>',
extend: 'pdf',
title: id,
extension: '.pdf'
},
{
text: '<i class="fas fa-print" data-toggle="tooltip" title="Print"></i>',
extend: 'print',

},
{
text: '<i class="fas fa-cog" data-toggle="tooltip" title="Setting"></i>',
extend: 'colvis',
// className: 'btn btn-xs btn-primary assets-export-btn export-pdf ttip',

}


],
"bDestroy": true,
}).buttons().container().appendTo('#'+tableID+'_wrapper .col-md-6:eq(0)');


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