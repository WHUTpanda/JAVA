var select = document.querySelector('select');
select.onchange = function(){
    window.location=this.value;
}
var User=JSON.parse(window.sessionStorage.getItem("curUser"));
var userId=User.user_ID;
var selectednum=0;
var selectedsum=0;
var datafirst;
showshopping()
function showshopping() {
    $.ajax({
        url:"/cart/show",
        type:"post",
        data:"User_ID="+userId,
        success:function (data) {
            datafirst=data;
            show(data)
        }
    })
}
function show(data){
    $("#shopping").empty("");
    if (data[0] != null) {
        for (var i = 0; i < data.length; i++) {
            var sum=data[i].price*data[i].num;
            var tr = $(
                "<tr><th class=\"bookid\" id=book"+i+">" + data[i].bookId +
                "</th><th class=\"bookname\"><a onclick=clickselected("+data[i].bookId+") id='selectedbook'"+i+">"+data[i].bookName +
                "</a></th><th class=\"bookwriter\">" + data[i].bookWriter +
                "</th><th class=\"addtime\">" + data[i].addDate +
                "</th><th class=\"bookprice\">" + data[i].price +
                "</th><th class=\"bookprice\"><input type=\"number\" min="+0+ "max="+100+
                " step="+1+"value="+data[i].num+
                "class=\"numinput\"></th><th class=\"bookprice\">"+sum+
                "</th><th class=\"buybutton\">" +
                "<input type=\"checkbox\" class=\"checktype\" id=" + "checkbox" + i + "></th></tr>");
            $("#shopping").append(tr)
        }
        for(var i=0;i<data.length;i++){
            (function(i){
                $("#number"+i).click(function (i) {
                    var num=$("#number"+i).value();
                    $.ajax({
                        url: "/cart/change",
                        type:"post",
                        data:"cart="+data[i].cartId+"&num="+num,
                        success:function (data) {
                            showshopping();
                        }
                    })
                })
            })(i);
        }
        for(var i=0;i<data.length;i++){
            (function (i) {
                $("#checkbox"+i).onclick(function (i) {
                    var judge=$("#checkbox"+i).checked();
                    if(judge){
                        $("#selectednum").val("总数："+(selectednum+data[i].num));
                        $("#selectedsum").val("总计："+(selectedsum+data[i].sum*data[i].price));
                    }
                    else{
                        $("#selectednum").val("总数："+(selectednum-data[i].num));
                        $("#selectedsum").val("总计："+(selectedsum-data[i].sum*data[i].price));
                    }
                    showshopping();
                })
            })(i);
        }
    }
    else {
        var tr = $("<tr><th colspan=8 style='text-align: center'>" + "购物车为空！" + "</th></tr>");
        $("#shopping").append(tr);
    }
}
function clickselected(book_Id) {
    window.sessionStorage.setItem("Book_ID",book_id)
    location.href="selected.html"
}
function rentbook(data){
    $.ajax({
        url:"/book/rentbook",
        type:"POST",
        data:data,
        success:function () {
            showshopping()
        }
    })
}
function purchasebook(data){
    $.ajax({
        url:"/book/purchasebook",
        type:"POST",
        data:data,
        success:function () {
            showshopping()
        }
    })
}
function deletebook(data){
    $.ajax({
        url:"/book/deletebook",
        type:"POST",
        data:data,
        success:function () {
            showshopping()
        }
    })
}
$("#rent").click(function () {
    for(var i=0;i<datafirst.length;i++){
        if($("checkbox"+i).is(':checked')){
            data.append(datafirst[i])
        }
    }
    if(confirm("押金为"+selectedsum+",是否全部租借?")){
        rentbook(data)
    }
})
$("#buy").click(function () {
    for(var i=0;i<datafirst.length;i++){
        if($("checkbox"+i).is(':checked')){
            data.append(datafirst[i])
        }
    }
    if(confirm("总价格为"+selectedsum+",是否全部购买?")){
        purchasebook(data)
    }
})
$("#delete").click(function () {
    for(var i=0;i<datafirst.length;i++){
        if($("checkbox"+i).is(':checked')){
            data.append(datafirst[i])
        }
    }
    if(confirm("是否删除?")){
        deletebook(data)
    }
})