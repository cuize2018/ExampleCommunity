function post() {
    var questionId = $("#question_id").val();
    var comment_content = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parent_id":questionId,
            "content":comment_content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200){
                $("#comment_section").hide();
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