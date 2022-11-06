function turnEditOff() {
    $('.form_field').attr('readonly', 'readonly');
    $('.form_field').removeClass('w3-input');
    $('#submit').css('display', 'none');
    $('#cancel').css('display', 'none');
    $('#edit').css('display', 'inline-block');
}

function turnEditOn() {
    $('.form_field').removeAttr('readonly');
    $('.form_field').addClass('w3-input');
    $('#submit').css('display', 'inline-block');
    $('#cancel').css('display', 'inline-block');
    $('#edit').css('display', 'none');
}

document.addEventListener('DOMContentLoaded', function () {
    turnEditOff();
});