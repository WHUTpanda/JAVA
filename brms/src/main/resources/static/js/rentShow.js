queryRent()
var select = document.querySelector('select');
select.onchange = function(){
	window.location=this.value;
}
function returnBook(Rent_ID,Num) {
    var num
    if(Num==1)
        num=1
    else{
        var num=prompt("请输入归还的数量")
    }
    $.ajax({
        url:"/book/returnBook",
        type:"POST",
        data:"Rent_ID="+Rent_ID+"&num="+num,
        success:function (data) {
            if(data==true)
            {
                alert("归还请求成功！")
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
                    "</td><td>" + data[i].num +
                    "</td><td>" + data[i].rent_Date +
                    "</td><td>" + data[i].return_Date+"</td></tr>")
            }
            else if(data[i].handle==0){
                tr = $("<tr><td>" + data[i].book_ID +
                    "</td><td>" + data[i].book_name +
                    "</td><td>" + data[i].num +
                    "</td><td>" +data[i].rent_Date +
                    "</td><td>" +
                    "<button  class='returnbutton' id="+"returnBtn"+i+">归还</button></td></tr>");
            }
            else if(data[i].handle==1){
                tr = $("<tr><td>" + data[i].book_ID +
                    "</td><td>" + data[i].book_name +
                    "</td><td>" + data[i].num +
                    "</td><td>" +data[i].rent_Date +
                    "</td><td>" +
                    "<button tyle='disabled=\"disabled\"; background-color:gray;'  class='returnbutton' id="+"rent"+i+">租借中</button></td></tr>");
            }
            else if(data[i].handle==2){
                tr = $("<tr><td>" + data[i].book_ID +
                    "</td><td>" + data[i].book_name +
                    "</td><td>" + data[i].num +
                    "</td><td>" +data[i].rent_Date +
                    "</td><td>" +
                    "<button style='disabled=\"disabled\";background-color:gray;' class='returnbutton' id="+"returnin"+i+">归还中</button></td></tr>");
            }
            $("#rentRecord").append(tr)
        }
        for (var i = 0; i < data.length; i++) {
            (function (i) {
                $("#returnBtn" + i).click(function () {
                    returnBook(data[i].rent_ID,data[i].num)
                })
            })(i);
        }
    }else
    {
        var tr = $("<tr><td colspan=4 style='text-align: center'>"+"无"+"</td></tr>");
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