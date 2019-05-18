$(document).ready(function(){

    $("#tableUsers").on('click','#editModalShow',function(){

        console.log("button pressed");
        var currentRow=$(this).closest("tr");

        var col_id=currentRow.find("td:eq(0)").text();
        var col_user_name=currentRow.find("td:eq(1)").text();
        var col_user_email=currentRow.find("td:eq(2)").text();
        var col_user_pass=currentRow.find("td:eq(3)").text();

        $("#modal_id").val(col_id);
        $("#modal_username").val(col_user_name);
        $("#modal_email").val(col_user_email);
        $("#modal_password").val(col_user_pass);

        $('#userEdit').modal('show');
    });
});