    $('#btn_login').click(function (){
        var u = $('#name').val();
        var p = $('#password').val();
        $.ajax({
            url: "/user/login",
            type: "POST",
            data:  "User_ID="+u+"&User_Password="+p,
            success:function (response)
            {
                if(response==1)
                    alert("没有该用户");
                else if(response==2) {
                    location.href='main.html'
                    window.sessionStorage.setItem('username',u)
                    alert("登录成功");
                }
                else
                    alert("密码错误");
            }
        });
    })
