window.onload = function()
{

//    $('.hardware').click(function() {
//        $(this).next().slideToggle(500);
//    });

    $('#show-orders').click(function() {
        var div = $('#show-orders-div');

        if (div.hasClass('visible')) {
            hideNextPanel(div);
            div.removeClass('visible')
        }
        else {
            showNextPanel(div);
            div.addClass('visible');
        }
    });
    $('#drop-order').click(function() {
        var div = $('#drop-order-div');

        if (div.hasClass('visible')) {
            hideNextPanel(div);
            div.removeClass('visible')
        }
        else {
            showNextPanel(div);
            div.addClass('visible');
        }
    });
    $('#update-order').click(function() {
        var div = $('#update-order-div');

        if (div.hasClass('visible')) {
            hideNextPanel(div);
            div.removeClass('visible')
        }
        else {
            showNextPanel(div);
            div.addClass('visible');
        }
    });
    var showNextPanel = function(div) {
        $('.panelNext').css('z-index', '198');
        hideNextPanel('all');
        div.addClass('visible');
        div.css('z-index', '199');
        div.animate({
            left: '+=' + ($('#panel').width() + 410)
        }, 200, function() {
            $(this).addClass('visible');
        });
    };
    var hideNextPanel = function(div) {
        if (div === 'all') {
            $('.panelNext').animate({'left': '-410px'}, 200);
            $('.panelNext').removeClass('visible');
//                $(this).animate({
//                left: '-=' + ($('#panel').width() + 410)
//            }, 200, function() {
//                $(this).removeClass('visible');
//            });
        } else {
            div.animate({
                left: '-=' + ($('#panel').width() + 410)
            }, 200, function() {
                $(this).removeClass('visible');
            });
        }
    };
    $('#add-product').click(function() {
        var div = $('#add-produc-div');

        if (div.hasClass('visible')) {
            hideNextPanel(div);
            div.removeClass('visible')
        }
        else {
            showNextPanel(div);
            div.addClass('visible');
        }
    });

    $('#update-role').click(function() {
        var div = $('#update-user-role-div');

        if (div.hasClass('visible')) {
            hideNextPanel(div);
            div.removeClass('visible')
        }
        else {
            showNextPanel(div);
            div.addClass('visible');
        }
    });
    $('#drop-user').click(function() {
        var div = $('#drop-user-role-div');

        if (div.hasClass('visible')) {
            hideNextPanel(div);
            div.removeClass('visible')
        }
        else {
            showNextPanel(div);
            div.addClass('visible');
        }
    });
    $('#show-list-user').click(function() {
        var div = $('#list-user-role-div');

        if (div.hasClass('visible')) {
            hideNextPanel(div);
            div.removeClass('visible')
        }
        else {
            showNextPanel(div);
            div.addClass('visible');
        }
    });
    $('.button-redag').click(function() {
        $('#redag-modal').arcticmodal();
    });

    $('#user-control').click(function() {
        $('#user-control-bar').slideToggle(100);
    });

    $('#order-control').click(function() {
        $('#order-control-bar').slideToggle(100);
    });

    $('#product-control').click(function() {
        $('#product-control-bar').slideToggle(100);

    });

    $('.redag-window').click(function() {
        $('#redag-product-window').css("display", "block");
    });

    $('.update-link').click(function() {
        $('#update-product-window').css("display", "block");
        $('#update-product-window').css("pointer-events", "auto");
    });

    $('.update-close').click(function() {
        $('#update-product-window').css("display", "none");
    });





    $(".level0").hover(
            function() {
                var param = $(this).attr("param");
//          $(this).css("background-color","white");
                $(this).css("color", "eee");
                $('.' + param).css("display", "block");

            },
            function() {
                var param = $(this).attr("param");
                $('.' + param).css("display", "none");
//          $(this).css("background","red");
                $(this).css("color", "white");
            }
    );

    $(".subcategories").hover(
            function() {
                $(this).css("display", "block");
            },
            function() {
                $(this).css("display", "none");
            }
    );


    var panel = $('#panel');
    if (panel.length) {
        var sticker = panel.children('#panel-sticker');
        var showPanel = function() {
            panel.animate({
                left: '+=290'
            }, 200, function() {
                $(this).addClass('visible');
            });
        };
        var hidePanel = function() {
            panel.animate({
                left: '-=290'
            }, 200, function() {
                $(this).removeClass('visible');
            });
        };
        sticker
                .click(function() {
                    if (panel.hasClass('visible')) {
                        hidePanel();
                        hideNextPanel('all');
                    }
                    else {
                        showPanel();
                    }
                }).andSelf()
                .children('.close').click(function() {
            panel.remove();
        });
    }

    var panel2 = $('#update-product-panel');
    if (panel2.length) {
        var sticker2 = $('.updateClicker');
        var showPanel2 = function() {
            panel2.animate({
                right: '+=450'
            }, 200, function() {
                $(this).addClass('visible');
            });
        };
        var hidePanel2 = function() {
            panel2.animate({
                right: '-=450'
            }, 200, function() {
                $(this).removeClass('visible');
            });

        };
        sticker2.click(function() {
            if ($(this).hasClass('clicked')) {
                if (panel2.hasClass('visible')) {
                    hidePanel2();
                }
                else {
                    showPanel2();
                }
                $(this).removeClass('clicked');
            } else {
                $(sticker2).removeClass('clicked');
                $(this).addClass('clicked');
                if (panel2.hasClass('visible')) {
                    hidePanel2();
                }
                showPanel2();
            }

        }).andSelf()
                .children('.close').click(function() {
            panel2.remove();
        });
        $('.panel-sticker-update-product').click(function(){
            hidePanel2();
            $('.updateClicker').removeClass('clicked');
        });
    }


};