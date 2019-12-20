var select = document.querySelector('select');
select.onchange = function(){
    window.location=this.value;
}
var User=JSON.parse(window.sessionStorage.getItem("curUser"));
var userId=User.user_ID;
var selectednum=0;
var selectedsum=0;
var datafirst=[];


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
                "<tr><th class=\"bookid\">" + data[i].bookId +
                "</th><th class=\"bookname\" id="+"bookname"+i+">"+data[i].bookName +
                "</th><th class=\"bookwriter\">" + data[i].bookWriter +
                "</th><th class=\"addtime\">" + data[i].addDate +
                "</th><th class=\"bookprice\" id='price'"+i+">" + data[i].price +
                "</th><th class=\"bookprice\"><input type=\"number\"  min=1 max=100 step=1 value='"+data[i].num+
                "'class=\"numinput\" id='number"+i+"'></th><th class=\"bookprice\" id='sum"+i+"'>"+sum+
                "</th><th class=\"buybutton\">" +
                "<input type=\"checkbox\" class=\"checktype\" id=" + "checkbox" + i + "></th></tr>");
            $("#shopping").append(tr)
        }
        for(var i=0;i<data.length;i++){
            (function(i){
                $("#bookname"+i).click(function () {
                    window.sessionStorage.setItem("Book_ID",data[i].bookId);
                    location.href="selected.html"
                })
            })(i)
        }
        for(var i=0;i<data.length;i++) {
            (function (i) {
                $("#number" + i).bind("input propertychange",function () {
                    var num = Number($(this).val())
                    var price=data[i].price
                    $("#sum" + i).text(num*price)
                    showallselected()
                })
            })(i)
        }

        for(var i=0;i<data.length;i++){
            (function (i) {
                $("#checkbox"+i).click(function () {
                    showallselected()
                });
            })(i)
        }
    }
    else {
        var tr = $("<tr><th colspan=8 style='text-align: center'>" + "购物车为空！" + "</th></tr>");
        $("#shopping").append(tr);
        $("#selectednum").text("总数："+0);
        $("#selectedsum").text("总计："+0);
    }
}
function showallselected() {
    selectedsum=0;
    selectednum=0;
    for(var i=0;i<datafirst.length;i++){
        if($("#checkbox"+i).prop("checked")){
            var price=datafirst[i].price
            var num=parseInt($("#number"+i).val())
            selectedsum+=price*num;
            selectednum+=num;
        }
    }
    $("#selectednum").text("总数："+(selectednum));
    $("#selectedsum").text("总计："+(selectedsum))
}
function rentbook(data) {
    $.ajax({
        url:"/cart/rent",
        type:"POST",
        data:"User_ID="+userId+data,
        success:function (response) {
            if(response==2){
                alert("租借失败！")
            }
            else if(response==3) {
                alert("余额不足！")
            }
            else if(response==false) {
                alert("未知错误！")
            }
            else if(typeof response=='string'){
                alert("取书号为："+response+"\n请前往前台领取")
                showshopping()
            }
            else{
                var data="";
                for(var i=1;i<response.length;i++){
                    data+=response[i]
                    data+='\n'
                }
                alert("书籍库存不足\n不足书籍如下：\n"+data)
            }
        }
    })
}
function purchasebook(data){
    $.ajax({
        url:"/cart/purchase",
        type:"POST",
        data:"User_ID="+userId+data,
        success:function (response) {
            if(response==2){
                alert("购买失败！")
            }
            else if(response==3) {
                alert("余额不足！")
            }
            else if(response==false) {
                alert("未知错误！")
            }
            else if(typeof response=='string'){
                alert("取书号为："+response+"\n请前往前台领取")
                showshopping()
            }
            else{
                var data="";
                for(var i=1;i<response.length;i++){
                    data+=response[i]
                    data+='\n'
                }
                alert("书籍库存不足\n不足书籍如下：\n"+data)
            }
        }
    })
}
function deletebook(data){
    $.ajax({
        url:"/cart/remove",
        data:data,
        type:"POST",
        success:function (response) {
            if(response){
                alert("删除成功！")
                showshopping()
            }
            else {
                alert("删除失败！")
            }
        }
    })
}
$("#rent").click(function () {
    var data=""
    for(var i=0;i<datafirst.length;i++){
        if($("#checkbox"+i).prop("checked")){
            var bookid=datafirst[i].bookId
            var cartid=datafirst[i].cartId
            var num=$("#number"+i).val()
            data=data+"&Cart_ID="+cartid+"&Book_ID="+bookid+"&num="+num
        }
    }
    if(selectednum==0){
        alert("请选择")
    }
    else if(confirm("押金为"+selectedsum+",是否全部租借?")){
        rentbook(data)
    }
})
$("#buy").click(function () {
    var data=""
    for(var i=0;i<datafirst.length;i++){
        if($("#checkbox"+i).prop("checked")){
            var bookid=datafirst[i].bookId
            var cartid=datafirst[i].cartId
            var num=$("#number"+i).val()
            data=data+"&Cart_ID="+cartid+"&Book_ID="+bookid+"&num="+num
        }
    }
    if(selectednum==0){
        alert("请选择")
    }
    else if(confirm("总价格为"+selectedsum+",是否全部购买?")){
        purchasebook(data)
    }
})
$("#delete").click(function () {
    var data
    var t=0
    for(var i=0;i<datafirst.length;i++){
        if($("#checkbox"+i).prop("checked")){
            var cartid=datafirst[i].cartId
            if(t==0)
                data="cartId="+cartid
            else
                data=data+"&cartId="+cartid
            t++
        }
    }
    if(selectednum==0){
        alert("请选择")
    }
    else if(confirm("是否删除?")){
        deletebook(data)
    }
})