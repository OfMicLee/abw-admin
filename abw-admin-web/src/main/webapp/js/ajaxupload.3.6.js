(function() {
    function o(a) {
        if (typeof a == "string") a = h.getElementById(a);
        return a
    }
    function j(a, b, c) {
        if (k.addEventListener) a.addEventListener(b, c, false);
        else k.attachEvent && a.attachEvent("on" + b,
            function() {
                c.call(a, k.event)
            })
    }
    function p(a, b) {
        a.className.match(RegExp("(\\s|^)" + b + "(\\s|$)")) || (a.className += " " + b)
    }
    function l(a, b) {
        var c = RegExp("(\\s|^)" + b + "(\\s|$)");
        a.className = a.className.replace(c, " ")
    }
    function q(a) {
        if (!a.pageX && a.clientX) {
            var b = 1,
                c = document.body;
            if (c.getBoundingClientRect) {
                b = c.getBoundingClientRect();
                b = (b.right - b.left) / c.clientWidth
            }
            return {
                x: a.clientX / b + h.body.scrollLeft + h.documentElement.scrollLeft,
                y: a.clientY / b + h.body.scrollTop + h.documentElement.scrollTop
            }
        }
        return {
            x: a.pageX,
            y: a.pageY
        }
    }
    var h = document,
        k = window,
        m = function() {
            var a = h.createElement("div");
            return function(b) {
                a.innerHTML = b;
                b = a.childNodes[0];
                a.removeChild(b);
                return b
            }
        } (),
        n = document.documentElement.getBoundingClientRect ?
            function(a) {
                var b = a.getBoundingClientRect(),
                    c = a.ownerDocument;
                a = c.body;
                c = c.documentElement;
                var d = c.clientTop || a.clientTop || 0,
                    f = c.clientLeft || a.clientLeft || 0,
                    e = 1;
                if (a.getBoundingClientRect) {
                    e = a.getBoundingClientRect();
                    e = (e.right - e.left) / a.clientWidth
                }
                if (e > 1) f = d = 0;
                return {
                    top: b.top / e + (window.pageYOffset || c && c.scrollTop / e || a.scrollTop / e) - d,
                    left: b.left / e + (window.pageXOffset || c && c.scrollLeft / e || a.scrollLeft / e) - f
                }
            }: function(a) {
            if (k.jQuery) return jQuery(a).offset();
            var b = 0,
                c = 0;
            do {
                b += a.offsetTop || 0;
                c += a.offsetLeft || 0
            } while ( a = a . offsetParent );
            return {
                left: c,
                top: b
            }
        },
        r = function() {
            var a = 0;
            return function() {
                return "ValumsAjaxUpload" + a++
            }
        } ();
    Ajax_upload = AjaxUpload = function(a, b) {
        if (a.jquery) a = a[0];
        else if (typeof a == "string" && /^#.*/.test(a)) a = a.slice(1);
        a = o(a);
        this._input = null;
        this._button = a;
        this._justClicked = this._submitting = this._disabled = false;
        this._parentDialog = h.body;
        if (window.jQuery && jQuery.ui && jQuery.ui.dialog) {
            var c = jQuery(this._button).parents(".ui-dialog");
            if (c.length) this._parentDialog = c[0]
        }
        this._settings = {
            action: "upload.php",
            name: "userfile",
            data: {},
            autoSubmit: true,
            responseType: false,
            onChange: function() {},
            onSubmit: function() {},
            onComplete: function() {}
        };
        for (var d in b) this._settings[d] = b[d];
        this._createInput();
        this._rerouteClicks()
    };
    AjaxUpload.prototype = {
        setData: function(a) {
            this._settings.data = a
        },
        disable: function() {
            this._disabled = true
        },
        enable: function() {
            this._disabled = false
        },
        destroy: function() {
            if (this._input) {
                this._input.parentNode && this._input.parentNode.removeChild(this._input);
                this._input = null
            }
        },
        _createInput: function() {
            var a = this,
                b = h.createElement("input");
            b.setAttribute("type", "file");
            b.setAttribute("name", this._settings.name);
            var c = {
                position: "absolute",
                margin: "-5px 0 0 -175px",
                padding: 0,
                width: "220px",
                height: "30px",
                fontSize: "14px",
                opacity: 0,
                cursor: "pointer",
                display: "none",
                zIndex: 2147483583,
                outline:"0 none"
            };
            for (var d in c) b.style[d] = c[d];
            if (b.style.opacity !== "0") b.style.filter = "alpha(opacity=0)";
            this._parentDialog.appendChild(b);
            j(b, "change",
                function() {
                    var f = this.value.replace(/.*(\/|\\)/, "");
                    if (a._settings.onChange.call(a, f, /[.]/.exec(f) ? /[^.]+$/.exec(f.toLowerCase()) : "") != false) a._settings.autoSubmit && a.submit()
                });
            j(b, "click",
                function() {
                    a.justClicked = true;
                    setTimeout(function() {
                            a.justClicked = false
                        },
                        2500)
                });
            this._input = b
        },
        _rerouteClicks: function() {
            var a = this,
                b, c = {
                    top: 0,
                    left: 0
                },
                d = false;
            j(a._button, "mouseover",
                function() {
                    if (! (!a._input || d)) {
                        d = true;
                        var f = a._button,
                            e, g;
                        g = n(f);
                        e = g.left;
                        g = g.top;
                        b = {
                            left: e,
                            right: e + f.offsetWidth,
                            top: g,
                            bottom: g + f.offsetHeight
                        };
                        if (a._parentDialog != h.body) c = n(a._parentDialog)
                    }
                });
            j(document, "mousemove",
                function(f) {
                    var e = a._input;
                    if (e && d) if (a._disabled) {
                        l(a._button, "hover");
                        e.style.display = "none"
                    } else {
                        f = q(f);
                        if (f.x >= b.left && f.x <= b.right && f.y >= b.top && f.y <= b.bottom) {
                            e.style.top = f.y - c.top + "px";
                            e.style.left = f.x - c.left + "px";
                            e.style.display = "block";
                            p(a._button, "hover")
                        } else {
                            d = false;
                            var g = setInterval(function() {
                                    if (!a.justClicked) {
                                        if (!d) e.style.display = "none";
                                        clearInterval(g);
                                    }
                                },
                                25);
                            l(a._button, "hover")
                        }
                    }
                })
        },
        _createIframe: function() {
            var a = r(),
                b = m('<iframe src="javascript:false;" name="' + a + '" />');
            b.id = a;
            b.style.display = "none";
            h.body.appendChild(b);
            return b
        },
        submit: function() {
            var a = this,
                b = this._settings;
            if (this._input.value !== "") {
                var c = this._input.value.replace(/.*(\/|\\)/, "");
                if (b.onSubmit.call(this, c, /[.]/.exec(c) ? /[^.]+$/.exec(c.toLowerCase()) : "") != false) {
                    var d = this._createIframe(),
                        f = this._createForm(d);
                    f.appendChild(this._input);
                    f.submit();
                    h.body.removeChild(f);
                    this._input = f = null;
                    this._createInput();
                    var e = false;
                    j(d, "load",
                        function() {
                            if (d.src == "javascript:'%3Chtml%3E%3C/html%3E';" || d.src == "javascript:'<html></html>';") e && setTimeout(function() {
                                    h.body.removeChild(d)
                                },
                                0);
                            else {
                                var g = d.contentDocument ? d.contentDocument: frames[d.id].document;
                                if (! (g.readyState && g.readyState != "complete")) if (! (g.body && g.body.innerHTML == "false")) {
                                    var i;
                                    if (g.XMLDocument) i = g.XMLDocument;
                                    else if (g.body) {
                                        i = g.body.innerHTML;
                                        if (b.responseType && b.responseType.toLowerCase() == "json") {
                                            if (g.body.firstChild && g.body.firstChild.nodeName.toUpperCase() == "PRE") i = g.body.firstChild.firstChild.nodeValue;
                                            i = i ? window.eval("(" + i + ")") : {}
                                        }
                                    } else i = g;
                                    b.onComplete.call(a, c, i);
                                    e = true;
                                    d.src = "javascript:'<html></html>';"
                                }
                            }
                        })
                } else {
                    h.body.removeChild(this._input);
                    this._input = null;
                    this._createInput()
                }
            }
        },
        _createForm: function(a) {
            var b = this._settings,
                c = m('<form method="post" enctype="multipart/form-data"></form>');
            c.style.display = "none";
            c.action = b.action;
            c.target = a.name;
            h.body.appendChild(c);
            for (var d in b.data) {
                a = h.createElement("input");
                a.type = "hidden";
                a.name = d;
                a.value = b.data[d];
                c.appendChild(a)
            }
            return c
        }
    }
})();