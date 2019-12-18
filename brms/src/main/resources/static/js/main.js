var select = document.querySelector('select');
select.onchange = function(){
	window.location=this.value;
}

$("#search_btn").click(function () {
    var str=$("#input_str").val();//记得取值
    if(str!="") {
        window.sessionStorage.setItem("searchInput", str)
        location.href = 'book-show.html'
    }
    else
        alert("请输入要查找的书名")
})