function collapseSectionOfChildEl(el) {
    let section = el.closest('.menu-section');
    section.find('.menu-collapse-button').toggleClass('collapsed');
    section.find(".collapse").slideToggle('fast');
}

document.addEventListener('DOMContentLoaded', function () {
    $('.menu-collapse-button').each(
        function () {
            collapseSectionOfChildEl($(this));
        }
    );
    $('sidebar li a').each(
        function () {
            var rLoc = document.location.pathname;
            var lLoc = $(this).attr('href');
            if (rLoc.startsWith(lLoc)) {
                $(this).parent('li').addClass('active');
                collapseSectionOfChildEl($(this));
            }
        }
    );

    $('.menu-collapse-button').on('click', function (e) {
        collapseSectionOfChildEl($(this));
    });

    $('.select-editable').each(function () {
        let thus = $(this);
        let list = thus.parent().find('.es-list')
        let lli = list.find('li[selected="selected"]')
        if (lli.length > 0)
            thus.css('background-color', 'lightgreen');
    });

});