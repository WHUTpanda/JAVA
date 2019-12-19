// JavaScript Document
var id=window.sessionStorage.getItem('Book_ID')
var select = document.querySelector('select');
select.onchange = function(){
	window.location=this.value;
}
var book;
show()
function show(){
$.ajax({
	url:"/book/byBookId",
	type:'POST',
	data:'Book_ID='+id,
	success:function(data){
		book=data;
		$("#Book_Name").text(data.book_Name);
		$("#Book_Writer").text(data.book_Writer);
		$("#Book_Dc").text(data.book_description);
		$("#Book_Price").text(data.book_Price+"元");
		$("#Book_RentMoney").text(data.book_Price*0.01+"/天");
		$("#Book_deposit").text(data.book_Price+"元");
		$("#Book_Num").text(data.num);
	}
})
}
//租借书籍函数
$("#rent").click(
    function rent() {
        var user=JSON.parse(window.sessionStorage.getItem("curUser"))
		var userId=user.user_ID
		var balance=user.user_Balance
		var num=$("#num").val();
		if(confirm("此次租借您将租借 "+num+" 本书\n"+"共需支付 "+num*book.book_Price+"元作为押金\n"+
			"您的余额为 "+balance+"元\n是否确认租借？")) {
			$.ajax({
				url: "/book/rent",
				type: "POST",
				data: "User_ID=" + userId + "&Book_ID=" + id + "&num=" + num,
				success: function (data) {
					if (data == "3")
						alert("余额不足，请充值")
					else if (data == "4")
						alert("库存不足，租借失败")
					else if (data == "2")
						alert("租借失败")
					else {
						alert("租借成功！\n请凭借租书号："+data+" 前往图书馆柜台取书")
						show()
					}

				}
			})
		}
})
//购买书籍函数
$("#buy").click(
	function buy() {
		var user=JSON.parse(window.sessionStorage.getItem("curUser"))
		var userId=user.user_ID
		var balance=user.user_Balance
		var num=$("#num").val();
		if(confirm("此次购买您将购买 "+num+" 本书\n"+"共需支付 "+num*book.book_Price+"元\n"+
			"您的余额为 "+balance+"元\n是否确认购买？")) {
			$.ajax({
				url: "/book/purchase",
				type: "POST",
				data: "User_ID=" + userId + "&Book_ID=" + id + "&num=" + num+"&Type="+true,
				success: function (data) {
					if (data == "3")
						alert("余额不足，请充值")
					else if (data == "4")
						alert("库存不足，购买失败")
					else if (data == "2")
						alert("购买失败")
					else {
						alert("购买成功！\n请凭借购书号："+data+" 前往图书馆柜台取书")
						show()
					}

				}
			})
		}
	})
//加入购物车函数
$("#shop").click(
	function shop() {
		var user=JSON.parse(window.sessionStorage.getItem("curUser"))
		var userId=user.user_ID
		var num=$("#num").val();
		if(confirm("是否加入购物车？")) {
			$.ajax({
				url: "/cart/add",
				type: "POST",
				data: "User_ID=" + userId + "&Book_ID=" + id + "&num=" + num,
				success: function (data) {
					if(data){
						alert("加入成功！")
						show()
					}
					else{
						alert("加入失败！")
						show()
					}
				}
			})
		}
	})
