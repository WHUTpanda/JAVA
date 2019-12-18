queryRent()
var select = document.querySelector('select');
select.onchange = function(){
	window.location=this.value;
}
function returnBook(Rent_ID) {
    $.ajax({
        url:"/book/returnBook",
        type:"POST",
        data:"Rent_ID="+Rent_ID,
        success:function (data) {
            if(data==true)
            {
                alert("归还成功")

                queryRent()
            }
            else
                alert("归还失败")
        }
    })
}
function showRentRecord(data) {
    $("#rentRecord").empty("");//清空除标题外的表格
    if(data[0]!=null) {
        for (var i = 0; i < data.length; i++) {
            var tr
            if(data[i].return_Date!=null) {
                tr = $("<tr><td>" + data[i].book_ID +
                    "</td><td>" + data[i].book_name +
                    "</td><td>" + data[i].rent_Date +
                    "</td><td>" + data[i].return_Date+"</td></tr>")
            }
            else{
                tr = $("<tr><td>" + data[i].book_ID +
                    "</td><td>" + data[i].book_name +
                    "</td><td>" +data[i].rent_Date +
                    "</td><td>" +
                    "<button class='returnbutton' id="+"returnBtn"+i+">归 还</button></td></tr>");
            }
            $("#rentRecord").append(tr)
        }
        for (var i = 0; i < data.length; i++) {
            (function (i) {
                $("#returnBtn" + i).click(function () {
                    returnBook(data[i].rent_ID)
                })
            })(i);
        }
    }else
    {
        var tr = $("<tr><td colspan=4 >"+"无"+"</td></tr>");
        $("#rentRecord").append(tr);
    }

}
function  queryRent() {
    var username = JSON.parse(window.sessionStorage.getItem("curUser")).user_ID
    $.ajax({
        url: "/record/rent",
        type: "POST",
        data: "User_ID=" + username,
        success: function (data) {
            showRentRecord(data)
        }
    })
}