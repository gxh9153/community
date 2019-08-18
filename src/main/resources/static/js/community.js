$(document).on("click", "#btn-comment", function () {/*如果直接用click的话会导致绑定不了事件，所以可以在按钮创建出来的时候就绑定事件*/
    var parentId = $("#question_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",/*从前端传递给服务器的数据格式*/
        data: JSON.stringify({ /*JSON.stringify 将js的对象序列化为json字符串*/
            "parentId": parentId,
            "content": content,
            "type": 1
        }),
        success: function (data) {
            if (data.code == 200) {
                $("#comment_section").hide();
            } else {
                if (data.code == 2003) {
                    var isAsscept = confirm(data.message); //将错误信息打印到确认框，点击确定重新登录
                    if (isAsscept) {
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.22aa485d1f920b8d&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        localStorage.setItem("closable","true");
                    }
                } else {
                    alert(data.message)
                }
            }
        },
        dataType: "json" /*从服务端返回给浏览器的数据格式*/
    });
});