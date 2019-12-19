showAll();
clickit(2);

function define(id,handle,num)
{
    $.ajax({
        url:"/manager/define",
        type:"post",
        data:"handle="+handle+"&id="+id+"&num="+num,
        success:function (data) {
            return data;
        }
    })
}

function show(data)
{
    $("#OrderList").empty("");
    if (data[0] != null) {
        for (var i = 0; i < data.length; i++) {
            var str;
            if(data[i].handle==1)
                str="租借请求"
            else if(data[i].handle==2)
                str="购买请求"
            else
                str="归还请求"
            var tr = $("<tr><th>" + data[i].id +
                "</th><th>" + data[i].userId +
                "</th><th>" + data[i].bookName +
                "</th><th>" +str+
                "</th><th>" +data[i].num+
                "</th><th>" +
                "<button class='booksearch' id=" + "defBtn" + i + ">完 成</button></th>" +
                "</tr>");
            $("#OrderList").append(tr)
        }
        //给可租借的书籍设置响应按钮
        for (var i = 0; i < data.length; i++) {
            (function (i) {
                $("#defBtn" + i).click(function () {
                    if(data[i].handle==1||data[i].handle==2){
                        var rt=define(data[i].id,data[i].handle,data[i].num);
                        $("#defBtn" + i).css({'background-color': 'gray'});
                        $("#defBtn" + i).attr("disabled", true);
                    }
                    else{
                        var num=prompt("请输入归还数量：")//弹出输入框
                        var rt=define(data[i].id,data[i].handle,num);
                        $("#defBtn" + i).css('background-color',gray);
                        $("#defBtn" + i).attr("disabled", true);
                    }

                })
            })(i);
        }
    }
    else {
        var tr = $("<tr><td colspan=6 class='bookname'>" + "当前无订单" + "</td></tr>");
        $("#OrderList").append(tr);
    }
}
function showAll()
{
    $.ajax({
        url: "/manager/show",
        success: function (data) {
            show(data)
        }
    })
}
function clickit(i) {
    if(i==1){//点击进入充值界面
        var a=document.getElementById("moneydeal")
        var b=document.getElementById("bookdeal")
        a.style.color="#DB1013";
        b.style.color="#fff";
        document.getElementById("charge").style.display="block";
        document.getElementById("dealplace").style.display="none";
    }
    if(i==2){//点击进入处理界面
        var a=document.getElementById("moneydeal");
        var b=document.getElementById("bookdeal");
        a.style.color="#fff";
        b.style.color="#DB1013";
        document.getElementById("charge").style.display="none";
        document.getElementById("dealplace").style.display="block";
    }
}
$("#addmoney").click(function () {
        var u=$("#name").val();
        var m=$("#money").val();
    $.ajax({
        url:"/user/recharge",
        type:"post",
        data:"User_ID="+u+"&amount="+m,
        success:function (data) {
            if(data==true)
            {
                alert("充值成功！")
                show()
            }
            else
                alert("充值失败！")
        }
    })
})
$("#new").click(function () {
    showAll();
})