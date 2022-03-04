let $appendModalHTML = '';
$appendModalHTML += `<div class="modal hidden">`;
$appendModalHTML += `  <div class="modal__overlay"></div>`;
$appendModalHTML += `  <div class="modal__content">`;
$appendModalHTML += `    <div class="modal__header">`;
$appendModalHTML += `      <div class="modal__title">`;
$appendModalHTML += `        <i class="fas fa-trash-alt"></i>`;
$appendModalHTML += `        <span>댓글 삭제</span>`;
$appendModalHTML += `      </div>`;
$appendModalHTML += `    </div>`;
$appendModalHTML += `    <div class="modal__body">댓글을 완전히 삭제할까요?</div>`;
$appendModalHTML += `    <div class="modal__footer">`;
$appendModalHTML += `      <button class="mybtn modal__cancel">취소</button>`;
$appendModalHTML += `      <button class="mybtn modal__delete">삭제</button>`;
$appendModalHTML += `    </div>`;
$appendModalHTML += `  </div>`;
$appendModalHTML += `</div>  `;

//현재문서 하단에 modal요소 추가  
document.body.insertAdjacentHTML('beforeend',$appendModalHTML)
//모달
const $modal = document.querySelector('.modal');
const $modalCancelBtn = $modal.querySelector('.modal__cancel');
const $modalDeletBtn = $modal.querySelector('.modal__delete');
const $modalOveray = $modal.querySelector('.modal__overlay');
//-- 모달 이벤트 등록 시작
$modalOveray.addEventListener("click",(e)=>{
  // $modal.classList.add('hidden');
});
//취소
$modalCancelBtn.addEventListener("click",(e)=>{
  document.body.style.overflow='initial';
  $modal.classList.add('hidden');
});
//삭제
$modalDeletBtn.addEventListener("click",(e)=>{
  window.myCurrEvent(); //모달을 사용하고자하는 페이지에서 window.myCurrEvent함수구현
  document.body.style.overflow='initial';
  $modal.classList.add('hidden');	
}) ;    
	//-- 모달 이벤트 등록 종료