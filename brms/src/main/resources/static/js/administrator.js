showAll()

function define(id,type)
{
    $.ajax({
        url:"/manager/define",
        type:"post",
        data:"type="+type+"&id="+id,
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
            if(data[i].type==1)
                str="租借请求"
            else if(data[i].type==2)
                str="购买请求"
            else
                str="归还请求"
            var tr = $("<tr><th>" + data[i].id +
                "</th><th>" + data[i].userId +
                "</th><th>" + data[i].bookName +
                "</th><th>" +str+
                "</th><th>" +data[i].num+
                "</th><th>" +
                "<button class='searchbutton' id=" + "defBtn" + i + ">完 成</button></th>" +
                "</tr>");
            $("#OrderList").append(tr)
        }
        //给可租借的书籍设置响应按钮
        for (var i = 0; i < data.length; i++) {
            (function (i) {
                $("#defBtn" + i).click(function () {
                    var rt=define(data[i].id,data[i].type)
                    if(rt) {
                        $("#defBtn" + i).css({'background-color': 'gray'});
                        $("#defBtn" + i).attr("disabled", true);
                    }
                    showAll()
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
