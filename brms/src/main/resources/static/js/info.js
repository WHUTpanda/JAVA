var User=JSON.parse(window.sessionStorage.getItem("curUser"))
var userId=User.user_ID
var select = document.querySelector('select');
select.onchange = function(){
	window.location=this.value;
}
show()
function show() {
	$.ajax({
		url: "/user/getUser",
		type:"post",
		data:"User_ID="+userId,
		success: function (user) {
			window.sessionStorage.setItem("curUser",JSON.stringify(user))
			$("#User_ID").text(user.user_ID);
			$("#userName").text(user.userName);
			if (user.user_Status == 1) {
				$("#User_Status").text("普通用户（不可租书）");
			}
			if (user.user_Status == 2) {
				$("#User_Status").text("会员");
			}
			if (user.user_Status == 3) {
				$("#User_Status").text("管理员");
			}
			$("#phoneNum").text(user.phoneNum);
			$("#User_Balance").text(user.user_Balance+"元");
		}
	})
}
$("#recharge").click(function () {
	var money=prompt("请输入要充值的金额：")//弹出输入框
	$.ajax({
		url:"/user/recharge",
		type:"post",
		data:"User_ID="+User.user_ID+"&amount="+money,
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
$("#vip").click(function () {
	var userStatus=JSON.parse(window.sessionStorage.getItem("curUser")).user_Status//获取用户当前身份
	if(userStatus==1) {
		if (confirm("是否确定升级会员？这将花费您100元\n点击确定将直接扣费并且升级")) {
			$.ajax({
				url: "/user/pay",
				type: "post",
				data: "User_ID=" + userId + "&price=100",
				success: function (data) {
					if (data == 1) {//支付成功后才能升级
						$.ajax({
							url: "user/Upgrade",
							type: "post",
							data: "User_ID=" + userId,
							success: function (data) {
								if (data == true) {
									alert("升级成功！");
									show()
								} else
									alert("升级失败！")
							}
						})

					} else if (data == 2) {
						alert("余额不足！请充值")
					} else
						alert("支付失败！")
				}
			})
		}
	}
	else{
		alert("您已经是会员")
	}
})