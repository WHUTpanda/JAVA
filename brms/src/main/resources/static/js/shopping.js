function showshopping(data){
	$("#shopping").empty("");
            if (data[0] != null) {
                for (var i = 0; i < data.length; i++) {
                    var tr = $(
						"<tr><th class=\"bookid\" >" + data[i].bookId +
                        "</th><th class=\"bookname\">" + data[i].book_Name +
                        "</th><th class=\"bookwriter\">" + data[i].Book_Writer +
                        "</th><th class=\"addtime\">" + data[i].addDate +
					   "</th><th class=\"bookprice\">" + data[i].price +
                        "</th><th class=\"buybutton\">" +
                        "<button class=\"booksearch\" id=" + "purchaseBtn" + i + ">购 买</button></th></tr>");
                    $("#shopping").append(tr)
                }            
            }
            else {
                var tr = $("<tr><th colspan=6 class='bookname'>" + "购物车为空！" + "</th></tr>");
                $("#shopping").append(tr);
            }
}
var select = document.querySelector('select');
select.onchange = function(){
	window.location=this.value;
}
