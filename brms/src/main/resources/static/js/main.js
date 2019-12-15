var u=window.sessionStorage.getItem('username');
var userStatus;
$.ajax({
    url:"/user/getUser",
    type:'POST',
    data:'User_ID='+u,
    success:function (data) {
        $("#username").text ("用户名: "+data.user_ID);
        userStatus=data.user_Status
        if(data.user_Status==1)
            $("#userstatus").text("用户身份: 普通用户");
        else if(data.user_Status==2)
            $("#userstatus").text("用户身份: 会员");
        else if(data.user_Status==3)
            $("#userstatus").text("用户身份: 管理员");
    }
});
$("#vip").click(function () {
    if(userStatus==1) {
        $.ajax({
            url: "user/Upgrade",
            type: "post",
            data: "User_ID="+u,
            success:function (data) {
                if (data == true) {
                    alert("升级成功！");
                    $("#userstatus").text("用户身份: 会员");
                }
                else
                    alert("升级失败！")
            }
        })
    }
    else{
        alert("您已经是会员")
    }
})
$("#search_btn").click(function () {
    var str=$("#input_str").val();//记得取值
    if(str!="") {
        window.sessionStorage.setItem("searchInput", str)
        location.href = 'book-show.html'
    }
    else
        alert("请输入要查找的书名")
})