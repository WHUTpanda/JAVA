var select = document.querySelector('select');
select.onchange = function(){
	window.location=this.value;
}

if(window.sessionStorage.getItem("searchInput")!=null)
{
    var input=window.sessionStorage.getItem("searchInput")
    $.ajax({
        url:"/book/searchBookInput",
        type:"POST",
        data:"input="+input,
        success:function (data) {
            show(data)

        }
    });
    window.sessionStorage.removeItem("searchInput")
}
else {
    showAll()
}

//显示全部书籍
function showAll(){
$.ajax({
    url:"book/show",
    success:function (data) {
        show(data)
    }
})
}
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

//显示书籍
function show(data) {
    $("#bookTable").empty("");
            if (data[0] != null) {
                for (var i = 0; i < data.length; i++) {
                    var tr = $("<tr  id="+"row"+i+"><th class=\"thbooknametitle\" >" + data[i].book_Name +
                        "</th><th class=\"wttitle\">" + data[i].book_Writer +
                        "</th><th class=\"pctitle\">" + data[i].book_Price +
                        "</th><th class=\"dp\">" + data[i].book_description +
                        "</th><tr>");
                    $("#bookTable").append(tr)
                }
                for(var i=0;i<data.length;i++){(function (i) {
                    $("#row"+i).click(function () {
                        window.sessionStorage.setItem("Book_ID",data[i].book_ID);
                        location.href="selected.html"
                    })
                })(i)
                }
            }
            else {
                var tr = $("<tr><td colspan=6 style='text-align: center'>" + "当前无书籍" + "</td></tr>");
                $("#bookTable").append(tr);
            }
}

//点击搜索按钮
$("#searchBtn").click(function rentx() {
    $("#bookTable").empty("");//清空除标题外的表格
    var input=$("#inputText").val();
        $.ajax({
            url:"/book/searchBookInput",
            type:"POST",
            data:"input="+input,
            success:function (data) {
               show(data)
            }
        });
})