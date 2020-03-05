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
// function collapseComments(e) {
//     var id = e.getAttribute("data-id");
//     var comments = $("#comment-" + id);
//
//     var status = e.getAttribute("data-collapse");
//     //获取二级评论展开状态
//     if (status){
//         //折叠二级评论
//        comments.removeClass("in");
//         e.removeAttribute("data-collapse");
//         e.classList.remove("active");
//     }
//     else {
//         $.getJSON( "/comment/"+id, function( data ) {
//             console.log(data);
//             var commentBody = $("comment-body-id"+id);
//             var items = [];
//
//             $.each( data.data, function(comment) {
//
//                 $("<div/>", {
//                     "class":  "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
//                     html: items.join( "" )
//                 })
//                 items.push( "<li id='" + key + "'>" + val + "</li>" );
//             });
//
//             $( "<div/>", {
//                 "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comments",
//                 "id": "comment-"+id,
//                 html: items.join( "" )
//             }).appendTo(commentBody);
//
//             //展开二级评论
//             comments.addClass("in");
//             //标记二级评论展开状态
//             e.setAttribute("data-collapse", "in");
//             e.classList.add("active");
//         });
//     }
// }

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
        //展开二级评论
        comments.addClass("in");
        //标记二级评论展开状态
        e.setAttribute("data-collapse", "in");
        e.classList.add("active");
    }
}