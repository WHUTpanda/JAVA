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
                else if(response==3)
                    alert("密码错误");
                else
                {
                    window.sessionStorage.setItem('curUser',JSON.stringify(response))
                    alert("登录成功");
                    if(response.user_Status!=3)
                        location.href='main.html'
                    else
                        location.href='administrator.html'

                }
            }
        });
    })