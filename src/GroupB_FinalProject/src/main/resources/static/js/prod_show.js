<!--选项卡-->
        function nApplys(thisObj, Num) {
            if (thisObj.className == "active") return;
            var ApplyObj = thisObj.parentNode.id;
            var ApplyList = document.getElementById(ApplyObj).getElementsByTagName("li");
            for (i = 0; i < ApplyList.length; i++) {
                if (i == Num) {
                    thisObj.className = "active";
                    document.getElementById(ApplyObj + "_Content" + i).style.display = "block";
                } else {
                    ApplyList[i].className = "normal";
                    document.getElementById(ApplyObj + "_Content" + i).style.display = "none";
                }
            }
        }
