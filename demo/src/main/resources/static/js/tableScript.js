function selectAll(selectAll)  {
  const checkboxes
     = document.querySelectorAll("input[name='reportChkRow']");

  checkboxes.forEach((checkbox) => {
    checkbox.checked = selectAll.checked
  })
}

// 삭제버튼 구현 해야함.
//function deleteReport() {
//    var cnt = $("input[name='reportChkRow']:checked").length;
//    var arr = new Array();
//    $("input[name='reportChkRow']:checked").each(function() {
//        arr.push($(this).attr('id'));
//    });
//    if(cnt == 0){
//        alert("선택된 글이 없습니다.");
//    }
//    else{
//        $.ajax = {
//            type: "POST"
//            url: "OOOO.do"
//            data: "RPRT_ODR=" + arr + "&CNT=" + cnt,
//            dataType:"json",
//            success: function(jdata){
//                if(jdata != 1) {
//                    alert("삭제 오류");
//                }
//                else{
//                    alert("삭제 성공");
//                }
//            },
//            error: function(){alert("서버통신 오류");}
//        };
//    }
//}
