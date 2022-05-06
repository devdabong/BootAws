// var main = {...} 선언 이유 : index.js만의 scope(유효범위)를 만들어 사용하기 위함.
// 브라우저의 scope는 공용 공간으로 쓰이기 때문에 나중에 로딩된 js의 init, save 함수가 있다면 덮어쓰게 되므로.
var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function (result) {
                console.log('id : ' + result);
            }
        }).done(function (result) {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

main.init();