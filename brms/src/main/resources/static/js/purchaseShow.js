queryPurchase()
var select = document.querySelector('select');
select.onchange = function(){
	window.location=this.value;
}
function showRentRecord(data) {
    $("#purchaseRecord tr:not(:first)").empty("");//清空除标题外的表格
    if(data[0]!=null) {
        for (var i = 0; i < data.length; i++) {
            var tr = $("<tr><td>" + data[i].book_ID +
                "</td><td>" + data[i].book_name +
                "</td><td>" + data[i].purchase_Date +
                "</td></tr>")
            $("#purchaseRecord").append(tr)
        }
    }else
    {
        var tr = $("<tr><td colspan=3 style='text-align: center'>"+"无"+"</td></tr>");
        $("#purchaseRecord").append(tr);
    }

}
function  queryPurchase() {
    var username = JSON.parse(window.sessionStorage.getItem("curUser")).user_ID
    $.ajax({
        url: "/record/purchase",
        type: "POST",
        data: "User_ID=" + username,
        success: function (data) {
            showRentRecord(data)
        }
    })
}