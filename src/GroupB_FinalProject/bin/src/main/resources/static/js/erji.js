// JavaScript Document

$(document).ready(function(){
 jQuery.jq51menu = function(menuboxid,submenu){
  var menuboxli = $(menuboxid+" li");
  menuboxli.eq(0).find(submenu).show();
  menuboxli.find("a:first").attr("href","javascript:;");
  menuboxli.click( function () {
   $(this).addClass("thismenu").find(submenu).show().end().siblings("li").removeClass("thismenu").find(submenu).hide();
  });
 };
 //调用方法，可重用
 $.jq51menu("#menubox","div.submenu");
});