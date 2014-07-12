/*
 * SimpleModal Basic Modal Dialog
 * http://simplemodal.com
 *
 * Copyright (c) 2013 Eric Martin - http://ericmmartin.com
 *
 * Licensed under the MIT license:
 *   http://www.opensource.org/licenses/mit-license.php
 */

jQuery(function($) {
    var flag = true;
    function setFlag(f){ flag = f;}
    $('#add-product').click(function(e) {
        $('#add-product-content').modal();

        return false;
    });

    $('#update-role').click(function(e) {
        $('#update-users-role-content').modal();

        return false;
    });

    $('#drop-user').click(function(e) {
        $('#list-users-drop-content').modal();

        return false;
    });

    $('#show-list-user').click(function(e) {
        $('#list-users-content').modal();

        return false;
    });

    

});