//获取评论列表的请求
$(document).on("click", "#btn-comment", function () {/*如果直接用click的话会导致绑定不了事件，所以可以在按钮创建出来的时候就绑定事件*/
    var parentId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(parentId, 1, content);
});

//插入二次评论数据
$(document).on("click", "#btn-twice-comment", function () {
    var commentId = this.getAttribute("data");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
});

//评论函数的封装
function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容哦~~~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",/*从前端传递给服务器的数据格式*/
        data: JSON.stringify({ /*JSON.stringify 将js的对象序列化为json字符串*/
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (data) {
            if (data.code == 200) {
                window.location.reload();
                $("#comment_section").hide();
            } else {
                if (data.code == 2003) {
                    var isAsscept = confirm(data.message); //将错误信息打印到确认框，点击确定重新登录
                    if (isAsscept) {
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.22aa485d1f920b8d&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(data.message)
                }
            }
        },
        dataType: "json" /*从服务端返回给浏览器的数据格式*/
    });
}

//折叠 展开二级评论
$(document).on("click", ".comment_icon", function () {
    var id = this.getAttribute("data");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = this.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        this.removeAttribute("data-collapse");
        this.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            this.setAttribute("data-collapse", "in");
            this.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>",{
                        "class":"pull-right",
                        "html" : moment(comment.gmtCreate).format("YYYY-MM-DD")
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                this.setAttribute("data-collapse", "in");
                this.classList.add("active");
            });
        }
    }
});