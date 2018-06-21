//js无缝滚动代码
        function marquee(i, direction) {
            var obj = document.getElementById("marquee" + i);
            var obj1 = document.getElementById("marquee" + i + "_1");
            var obj2 = document.getElementById("marquee" + i + "_2");
            if (direction == "up") {
                if (obj2.offsetTop - obj.scrollTop <= 0) {
                    obj.scrollTop -= (obj1.offsetHeight + 20);
                } else {
                    var tmp = obj.scrollTop;
                    obj.scrollTop++;
                    if (obj.scrollTop == tmp) {
                        obj.scrollTop = 1;
                    }
                }
            } else {
                if (obj2.offsetWidth - obj.scrollLeft <= 0) {
                    obj.scrollLeft -= obj1.offsetWidth;
                } else {
                    obj.scrollLeft++;
                }
            }
        }

        function marqueeStart(i, direction) {
            var obj = document.getElementById("marquee" + i);
            var obj1 = document.getElementById("marquee" + i + "_1");
            var obj2 = document.getElementById("marquee" + i + "_2");

            obj2.innerHTML = obj1.innerHTML;
            var marqueeVar = window.setInterval("marquee(" + i + ", '" + direction + "')", 40);
            obj.onmouseover = function () {
                window.clearInterval(marqueeVar);
            }
            obj.onmouseout = function () {
                marqueeVar = window.setInterval("marquee(" + i + ", '" + direction + "')", 20);
            }
        }