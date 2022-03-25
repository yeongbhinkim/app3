'use strict';


  //취소 상세
  cancelBtn?.addEventListener('click',e=>{
    const url = `/bbs/${bbsId.value}`;
    location.href= url;
  });



  //목록
  listBtn?.addEventListener('click',e=>{
    location.href="/bbs";
  });

//첨부파일삭제
const $files = document.querySelector('.bbs-attach');
$files?.addEventListener('click',evt=>{
  if(evt.target.tagName != 'I') return;
  if(!confirm('삭제하시겠습니까?')) return;

  const $i = evt.target;
  const url = `/attach/${$i.dataset.fid}`;
  fetch(url,{
    method:'DELETE'
  }).then(res=>res.json())
    .then(res=>{
      if(res.rtcd == '00'){
        //첨부파일 정보 화면에서 제거
        removeAttachFileFromView(evt);
      }else{
        console.log(res.rtmsg);
      }
    })
    .catch(err=>console.log(err));
});
function removeAttachFileFromView(evt){
    const $parent = document.getElementById('attachFiles');
    const $child = evt.target.closest('.attachFile');
    $parent.removeChild($child);
}