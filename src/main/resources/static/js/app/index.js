//api 호출 js
var main = {    //다른 js랑 겹치지 않게
    init : function () {
        var _this = this;
        var comment=this;
        $('#btn-save').on('click', function () {    //btn-save: id 엘리먼트 클릭시 save 함수 실행
            _this.save();
        });

        $('#btn-update').on('click', function () {  //수정 함수 실행
            _this.update();
        });

        $('#btn-delete').on('click', function () {  //삭제 함수 실행
            _this.delete();
        });

        $('#btn-saveComment').on('click', function () {
             _this.saveComment();
        });

        $('#btn-deleteComment').on('click', function () {
            _this.deleteComment();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),   //jquery 객체 title 값 불러오기
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',       //등록(PostsApiController에서 정의함)
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/'; //작업 끝나면 메인페이지로 이동
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',        //수정
            url: '/api/v1/posts/'+id,   //id로 게시물 구분
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {      //삭제
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert('댓글이 있는 게시물은 삭제할 수 없습니다.');
        });
    },

    saveComment:function(){
         var data = {
               author:$('#comment_author').val(),
               content:$('#comment_content').val()
                        };
         var postId = $('#id').val();

             $.ajax({
                        type: 'POST',
                        url: '/comments/'+postId,
                        dataType: 'json',
                        contentType:'application/json; charset=utf-8',
                        data: JSON.stringify(data)
                    }).done(function() {
                        alert('댓글이 등록되었습니다.');
                        window.location.href = '/posts/'+postId;
                    }).fail(function (error) {
                        alert(JSON.stringify(error));
                    });
        }
};
main.init();

