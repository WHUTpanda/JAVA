$("#btn_register").click(function () {
    var u=$("#name").val();
    var p1=$("#password1").val();
    var p2=$("#password2").val();
    if(p1!=p2)
    {
        alert("前后两次密码输入不一致")
    }
    else
    {
        $.ajax({
            url:"/user/Register",
            type:"POST",
            data:"User_ID="+u+"&User_Password="+p1,
            success:function (response) {
                if(response)
                    alert("注册成功");
                else
                    alert("注册失败，可能是用户名重复");
            }
        });
    }
})