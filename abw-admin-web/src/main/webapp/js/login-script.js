/**
 * Created by MicLee on 12/17/14.
 */
$(document).ready(function () {
    var options = {
        url: '/loginchk',
        type: 'post',
        timeout: 8000,
        dataType: 'json',
        error: function () {
            $('#password').val('');
            alert("操作出错，请刷新后重试");
        },
        success: function (data) {
            if(data.result == 'ok') {
                window.top.parent.location.href = '/home';
            } else {
                var error = $('.error');
                error.css('top', '96px');
                $('.error span').html(data.msg);
                error.fadeIn('fast', function () {
                    $('#password').val('');
                });
            }
        }
    };
    $('#loginForm').ajaxForm(options);

    $('.submit_button').click(function () {
        var username = $('#username').val();
        var password = $('#password').val();
        var error = $('.error');
        error.fadeOut('fast');

        if (username == '' || username.length < 6 || username.length > 16) {
            error.css('top', '27px');
            $('.error span').html("用户名错误");
            error.fadeIn('fast', function () {
                $('#username').focus();
            });
            return false;
        }

        if (password == '' || password.length < 6 || password.length > 64) {
            error.css('top', '96px');
            $('.error span').html("密码错误");
            error.fadeIn('fast', function () {
                $('#password').focus();
            });
            return false;
        }

        $('#loginForm').submit();
    });

    $('#username, #password').keyup(function (e) {
        if (e.keyCode == 13) {
            $('.submit_button').click();
        } else {
            $('.error').fadeOut('fast');
        }
    });

//    $input.keypress(function (event) {
//        var key = event.which;
//        if (key == 13) {
//            $("[id$=LoginButton]").click(); //支持firefox,IE无效
//            $("[id$=LoginButton]").focus();  //支持IE，firefox无效。
//
//            //以上两句实现既支持IE也支持 firefox
//        }
//    });
});