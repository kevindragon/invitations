
function alignPos(s, n, c) {
  s = ""+s || "";
  if (s.length < n) {
    var gapLen = n - s.length;
    for (var i = 0; i < gapLen; i++) {
      s = c+s;
    }
  }
  return s;
}


$(function () {
  $.material.init();
});