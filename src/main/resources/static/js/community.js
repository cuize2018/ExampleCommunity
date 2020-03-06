/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var comment_content = $("#comment_content").val();
    comment2target(questionId, 1,comment_content);
}

function comment2target(targetId, type, content) {
    if (!content){
        alert("不能回复空内容");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parent_id":targetId,
            "content":content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200){
                window.location.reload();
            }
            else {
                if(response.code == 2003){
                    //如果未登录，则登录后关掉首页
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=174625521473a43a1f1b&\n" +
                            "redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true); //在浏览器中存储是否关掉页面
                    }
                }
                else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var comment_content = $("#input-"+commentId).val();
    comment2target(commentId, 2, comment_content)
}

/**
 * 展开二级回复
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    var status = e.getAttribute("data-collapse");
    //获取二级评论展开状态
    if (status){
        //折叠二级评论
       comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }
    else {
        var subCommentContainer = $("#comment-"+id);

        if (subCommentContainer.children().length !== 1){
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }
        else {
            $.getJSON( "/comment/"+id, function( data ) {

                $.each(data.data.reverse(), function(index, comment) {

                    var mediaLeftElement = $("<div/>", {
                        "class":"media-left"
                    }).append($("<img/>", {
                        "class":"media-object img-rounded",
                        "src":comment.user.avatar_url
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
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmt_create).format('YYYY-MM-DD')
                        // "html": comment.gmt_create
                    })));

                    var mediaElement = $("<div/>", {
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>",{
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
            });

            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }

    }
}

// function collapseComments(e) {
//     var id = e.getAttribute("data-id");
//     var comments = $("#comment-" + id);
//
//     var status = e.getAttribute("data-collapse");
//     //获取二级评论展开状态
//     if (status){
//         //折叠二级评论
//         comments.removeClass("in");
//         e.removeAttribute("data-collapse");
//         e.classList.remove("active");
//     }
//     else {
//         //展开二级评论
//         comments.addClass("in");
//         //标记二级评论展开状态
//         e.setAttribute("data-collapse", "in");
//         e.classList.add("active");
//     }
// }