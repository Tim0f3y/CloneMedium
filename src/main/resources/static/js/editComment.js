$('#comments_container').on('click', '.edit-comment', async function (e) {
    let id = $(this).attr('data-id');
    let comment = await fetch("/api/user/comment/" + id).then(function (response) {
        return response.json();
    });
    $("#editCommentModalText").val(comment.text);

    $("#editCommentConfirm").one('click', function () {
        let newText = $("#editCommentModalText").val();
        if (newText !== '') {
            comment.text = newText;
            return fetch('/api/user/comment/update', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(comment)
            }).then(function (response) {
                if (response.ok) {
                    $("#editCommentModal").modal("hide");
                    $("#commentCardText-" + id).text(newText);
                    e.preventDefault();
                }
            })
        } else {
            alert("Комментарий не должен быть пустым");
        }
    })
});


