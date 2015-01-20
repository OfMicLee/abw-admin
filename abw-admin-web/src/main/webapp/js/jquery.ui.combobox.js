(function( $ ) {
    $.widget( "ui.combobox", {
        _create: function() {
            var input;
            var that = this;
            var select = this.element.hide();
            var selected = select.children( ":selected" );
            var selecttext = selected.text();
            var value = selected.val() ? selected.text() : "";
            var wrapper = this.wrapper = $( "<span>" )
                .addClass( "ui-combobox" )
                .insertAfter( select );

            function removeIfInvalid(element) {
                var value = $( element ).val();
                var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( value ) + "$", "i" );
                var valid = false;
                select.children( "option" ).each(function() {
                    if ( $( this ).text().match( matcher ) ) {
                        this.selected = valid = true;
                        return false;
                    }
                });
                if ( !valid ) {
                    // remove invalid value, as it didn't match anything
                    $( element )
                        .val( "" )
                        .attr( "title", value + "不在当前列表范围内" )
                        .tooltip( "open" );
                    select.val( "" );
                    setTimeout(function() {
                        input.tooltip( "close" ).attr( "title", "" );
                    }, 2500 );
                    input.data( "autocomplete" ).term = "";
                    return false;
                }
            }

            input = $( "<input>" )
                .appendTo( wrapper )
                .val( value )
                .attr( "id", select.attr("id")+"input")
                .attr( "title", "" )
                .attr( "placeholder", selecttext)
                .autocomplete({
                    delay: 0,
                    minLength: 0,
                    source: function( request, response ) {
                        var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
                        response( select.children( "option" ).map(function() {
                            var text = $( this ).text();
                            if ( ( !request.term || matcher.test(text) ) ) {
                                return {
                                    label: text,
                                    value: text,
                                    option: this
                                };
                            }
                        })
                        );
                    },
                    select: function( event, ui ) {
                        ui.item.option.selected = true;
                        that._trigger( "selected", event, {
                            item: ui.item.option
                        });
                        select.trigger("change",true);
                    },
                    change: function( event, ui ) {
                        if ( !ui.item )
                            return removeIfInvalid( this );
                    }
                })
                .addClass("input-60p");

            input.data( "autocomplete" )._renderItem = function( ul, item ) {
                return $( "<li>" )
                    .data( "item.autocomplete", item )
                    .append( "<a>" + item.label + "</a>" )
                    .appendTo( ul );
            };

            $( "<a>" )
                .attr( "tabIndex", -1 )
                .appendTo( wrapper )
                .button({
                    icons: {
                        primary: "ui-icon-triangle-1-s"
                    },
                    text: false
                })
                .addClass( "ui-resizable-s" )
                .click(function() {
                    // close if already visible
                    if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
                        input.autocomplete( "close" );
                        removeIfInvalid( input );
                        return;
                    }

                    // work around a bug (likely same cause as #5265)
                    $( this ).blur();

                    // pass empty string as value to search for, displaying all results
                    input.autocomplete( "search", "" );
                    input.focus();
                });

            input
                .tooltip({
                    position: {
                        my: "center top",
                        at: "center bottom+2"
                    },
                    show: {
                        duration: "fast"
                    },
                    hide: {
                        effect: "explode"
                    }
                });
        },

        destroy: function() {
            this.wrapper.remove();
            this.element.show();
            $.Widget.prototype.destroy.call( this );
        }
    });
})( jQuery );