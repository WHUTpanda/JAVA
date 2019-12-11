var x=1;
a();
var u=window.sessionStorage.getItem('username');
$.ajax({
    url:"/user/getUser",
    type:'POST',
    data:'User_ID='+u,
    success:function (data) {
        $("#username").text ("用户名: "+data.user_ID);
        if(data.user_Status==1)
            $("#userstatus").text("用户身份: 普通用户");
        else if(data.user_Status==2)
            $("#userstatus").text("用户身份: 会员");
        else if(data.user_Status==3)
            $("#userstatus").text("用户身份: 管理员");
    }
});
//购买书籍函数
function purchase(modelBookId,userId) {
    $.ajax({
        url:"/book/purchase",
        type:"POST",
        data:"User_ID="+userId+"&ModelBook_ID="+modelBookId,
        success:function (data) {
            if(data)
                alert("购买成功！")
            else
                alert("购买失败")
        }
    })
}
//租借书籍函数
function rent(modelBookId,userId) {
    $.ajax({
        url:"/book/rent",
        type:"POST",
        data:"User_ID="+userId+"&ModelBook_ID="+modelBookId,
        success:function (data) {
            if(data)
                alert("租借成功！")
            else
                alert("租借失败")
        }
    })
}
//显示待租书
function a() {
    $("#RABtable tr:not(:first)").empty("");
    $.ajax({
        url: "book/showRent",
        success: function (data) {
            if (data[0] != null) {
                for (var i = 0; i < data.length; i++) {
                    var tr = $("<tr><td >" + data[i].book_Name +
                        "</td><td>" + data[i].book_Writer +
                        "</td><td>" + data[i].book_Price +
                        "</td><td>" + data[i].book_description +
                        "</td><td>" +
                        "<button class='booksearch' id=" + "rentBtn" + i + ">租 借</button></td>" +
                        "</tr>");
                    $("#RABtable").append(tr)
                }
                for (var i = 0; i < data.length; i++) {
                    (function (i) {
                        $("#rentBtn" + i).click(function () {
                            rent(data[i].modelBook_ID, u);
                            a();
                        })
                    })(i);
                }
            } else {
                var tr = $("<tr><td colspan=6 class='bookname'>" + "当前无待租书籍" + "</td></tr>");
                $("#RABtable").append(tr);
            }
        }
    })
}
$("#rentbutton").click(function rentx() {
    document.getElementById("bouchasebutton").style.color="#fff";
    document.getElementById("rentbutton").style.color="rgba(14,190,255,1.00)";
    x=1;
    $("#RABtable tr:not(:first)").empty("");
    $.ajax({
        url:"book/showRent",
        success:function (data) {
            if(data[0]!=null) {
                for (var i = 0; i < data.length; i++) {
                    var tr = $("<tr><td>" + data[i].book_Name +
                        "</td><td>" + data[i].book_Writer +
                        "</td><td>" +data[i].book_Price+
                        "</td><td>"+data[i].book_description +

                        "</td><td>" +
                        "<button class='booksearch' id="+"rentBtn"+i+">租 借</button></td></tr>");
                    $("#RABtable").append(tr)
                }
                for (var i = 0; i < data.length; i++) {
                    (function (i) {
                        $("#rentBtn" + i).click(function () {
                            rent(data[i].modelBook_ID,u);
                            rentx();
                        })
                    })(i);
                }
            }else
            {
                var tr = $("<tr><td colspan=4 class='bookname'>"+"当前无待租书籍"+"</td></tr>");
                $("#RABtable").append(tr);
            }
        }
    })
})
//显示待售书籍
$("#bouchasebutton").click(function buyx() {
    x=2;
    $("#RABtable tr:not(:first)").empty("");
    document.getElementById("rentbutton").style.color="#fff";
    document.getElementById("bouchasebutton").style.color="rgba(14,190,255,1.00)";
    var a1 =document.getElementById("rentbutton");
    $.ajax({
        url: "book/showPurchase",
        success: function (data) {
            if (data[0] != null) {
                for (var i = 0; i < data.length; i++) {
                    var tr = $("<tr>" +
                        "<td>" + data[i].book_Name +
                        "</td><td>" + data[i].book_Writer +
                        "</td><td>" + data[i].book_Price +
                        "</td><td>" + data[i].book_description +
                        "</td><td>" +
                        "<button class='booksearch' id=" + "purchaseBtn" + i + ">购 买</button></td>" +
                        "</tr>");
                    $("#RABtable").append(tr)
                }
                for (var i = 0; i < data.length; i++) {
                    (function (i) {
                        $("#purchaseBtn" + i).click(function () {
                            purchase(data[i].modelBook_ID, u);
                            buyx();
                        })
                    })(i);
                }
            } else {
                var tr = $("<tr><td colspan=5 class='bookname'>" + "没有待售书籍" + "</td></tr>");
                $("#RABtable").append(tr);
            }
        }
    })
})
//点击搜索按钮
$("#searchBtn").click(function rentx() {
    $("#RABtable tr:not(:first)").empty("");//清空除标题外的表格
    var input=$("#inputText").val();
    //if(document.getElementById("rentbutton").style.color=="rgba(14,190,255,1.00)")
    if(x==1)
    {
        $.ajax({
            url:"/book/searchRentBook",
            type:"POST",
            data:"Input="+input,
            success:function (data) {
                if(data[0]!=null) {
                    for (var i = 0; i < data.length; i++) {
                        var tr = $("<tr><td>" + data[i].book_Name +
                            "</td><td>" + data[i].book_Writer +
                            "</td><td>" + data[i].book_Price +
                            "</td><td>" + data[i].book_description +

                            "</td><td>" +
                            "<button class='booksearch' id=" + "rentBtn" + i + ">租 借</button></td></tr>");
                        $("#RABtable").append(tr)
                    }
                    for (var i = 0; i < data.length; i++) {
                        (function (i) {
                            $("#rentBtn" + i).click(function () {
                                rent(data[i].modelBook_ID, u);
                                rentx();
                            })
                        })(i);
                    }
                }
                else
                {
                    var tr = $("<tr><td colspan=5 class='bookname'>"+"没有找到相关书籍"+"</td></tr>");
                    $("#RABtable").append(tr);
                }
            }
        });
    }
    else
    {
        $.ajax({
            url:"/book/searchPurchaseBook",
            type:"POST",
            data:"Input="+input,
            success:function (data) {
                if (data[0] != null) {
                    for (var i = 0; i < data.length; i++) {
                        var tr = $("<tr>" +
                            "<td>" + data[i].book_Name +
                            "</td><td>" + data[i].book_Writer +
                            "</td><td>" + data[i].book_Price +
                            "</td><td>" + data[i].book_description +
                            "</td><td>" +
                            "<button class='booksearch' id=" + "purchaseBtn" + i + ">购 买</button></td>" +
                            "</tr>");
                        $("#RABtable").append(tr)
                    }
                    for (var i = 0; i < data.length; i++) {
                        (function (i) {
                            $("#purchaseBtn" + i).click(function () {
                                purchase(data[i].modelBook_ID, u);
                                rentx();
                            })
                        })(i);
                    }
                }
                else
                {
                    var tr = $("<tr><td colspan=5 class='bookname'>"+"没有找到相关书籍"+"</td></tr>");
                    $("#RABtable").append(tr);
                }
            }
        });
    }
})