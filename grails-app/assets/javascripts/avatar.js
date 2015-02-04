/**
 * Created by colmard on 04/02/2015.
 */
$(document).ready(function () {
    var avatars = $(".user-avatar");

    function randomColor(value) {
        var r = value % 256;
        var g = (value >> 8) % 256;
        var b = (value >> 16) % 256;

        return "rgba(" + r + "," + g + "," + b + ", 1.0)";
    }

    $(avatars).each(function () {
        var size = Math.min($(this).width(), $(this).height());
        var canvas = $(this).find(".avatar");
        var context = canvas[0].getContext("2d");
        var id = canvas.attr("id");
        var x = id;
        var a = 16807;
        var c = 12353654;
        var m = Math.pow(2, 31) - 1;

        canvas.attr("width", size);
        canvas.attr("height", size);

        x = (a * x + c) % m;
        context.fillStyle = randomColor(x);
        context.fillRect(0, 0, size, size);

        x = (a * x + c) % m;
        context.fillStyle = randomColor(x);

        var n = 8;
        var d = size / n | 0;
        if (size % n !== 0) {
            ++d;
        }

        for (var i = 0; i < n >> 1; ++i) {
            for (var j = 0; j < n; ++j) {
                x = (a * x + c) % m;

                if (x % 3 === 0) {
                    context.fillRect(i * d, j * d, d, d);
                    context.fillRect((n - i - 1) * d, j * d, d, d);
                }
            }
        }
    });
});
