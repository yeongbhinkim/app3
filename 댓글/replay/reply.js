'use strict';
import {ajaxCall, makeEle, debounce} from '/common.mjs';

const $rcontent   = document.querySelector('.comment.write .rcontent');
const $cancelBtn  = document.querySelector('.comment.write .cancelBtn');
const $writeBtn   = document.querySelector('.comment.write .writeBtn');

const $comments   = document.querySelector('.wrapper-comments .comments');

const $commentParents = document.querySelectorAll('.wrapper-comments .comments .comment-parent');
const $commentchilds = document.querySelectorAll('.wrapper-comments .comments .comment-child');

const $hiddenItemIcons = document.querySelectorAll('.hiddenItem .icon');
const buttonNameMap= new Map([
	['cancel',   'cancelFn'],  //취소
	['write',    'writeFn'],   //댓글등록
	['modify',   'modifyFn'],  //댓글수정
	['delete',   'deleteFn'],  //댓글삭제
	['rgood',    'rgoodFn'],   //선호
	['rbad',     'rbadFn'],    //비선호
	['rereply',  'rereplyFn'], //대댓글
	['charge',   'chargeFn']   //신고

]);

//댓글목록영역에 클릭 이벤트 감지
$comments.addEventListener('click',evt=>{
	let l_target = evt.target;

	 if (l_target.tagName === 'I') l_target = l_target.parentElement;
	 if (l_target.tagName !== 'BUTTON') return; 

	 const findedFn = buttonNameMap.get(l_target.dataset.buttonName);
	 if(!findedFn) {
		 console.log('찾고자 하는 함수 없음');  return;
	 }
	 //클릭 버튼 처리 함수호출
	 eval(`${findedFn}(evt)`);	 
});

//숨김메뉴 마우스오버시 표시
[...$commentParents, ...$commentchilds].forEach(ele=>ele.addEventListener('mouseover',evt=>{
	evt.currentTarget.querySelector('.hiddenItem').classList.remove('hidden');
}));
//숨김메뉴 마우스아웃시 숨김
[...$commentParents, ...$commentchilds].forEach(ele=>ele.addEventListener('mouseout',evt=>{
	evt.currentTarget.querySelector('.hiddenItem').classList.add('hidden');
}));

//숨김아이콘 클릭시 수정, 삭제메뉴 제어
[...$hiddenItemIcons].forEach(ele=>ele.addEventListener('click',evt=>{
	//로긴 id와 댁글작성자 id가 같으면 보여줌.
	// debugger;
	const rid = ele.closest('div[data-rnum]').dataset.rid
	if(1 == rid){
		const $menus = [...document.querySelectorAll('.hiddenItem .menu')];
		$menus.filter(ele=>ele === evt.currentTarget.nextElementSibling)
					.forEach(ele=>ele.classList.toggle('hidden'));
		$menus.filter(ele=>ele !== evt.currentTarget.nextElementSibling)
					.filter(ele=>!ele.classList.contains('hidden'))
					.forEach(ele=>ele.classList.add('hidden'));
	}else{
		const $menus = [...document.querySelectorAll('.hiddenItem .menu2')];
		$menus.filter(ele=>ele === evt.currentTarget.nextElementSibling.nextElementSibling)
					.forEach(ele=>ele.classList.toggle('hidden'));
		$menus.filter(ele=>ele !== evt.currentTarget.nextElementSibling.nextElementSibling)
					.filter(ele=>!ele.classList.contains('hidden'))
					.forEach(ele=>ele.classList.add('hidden'));		
	}
}));

$rcontent.addEventListener('focus',evt=>{
		evt.target.closest('.comment.write').querySelector('.btngrp').style.display='block';
});

$rcontent.addEventListener('keyup',evt=>{
		//입력된 글자가 있으면
		if(evt.target.textContent.trim().length > 0){
			$writeBtn.style.backgroundColor = '#00f';
		}else{
			$writeBtn.style.backgroundColor = '#eee';
		}
  });

$cancelBtn.addEventListener('click',evt=>{
	evt.target.closest('.btngrp').style.display = 'none';
	$rcontent.textContent = '';
});

$writeBtn.addEventListener('click', evt=>{
	if($rcontent.textContent.trim().length === 0) return;
	replyList();
	
});

//목록가져오기
function replyList(){
	console.log('replyList!');
	//목록가져오기 로직 추가  <===============================
}
//취소
function cancelFn(evt){
	console.log('cancelFn!');
	const $parent = evt.target.closest('.comments');
	const $child = evt.target.closest('.comment.rewrite');
	$parent.removeChild($child);
}
//등록
function writeFn(evt){
	console.log('writeFn!');
	//등록로직 추가  <===============================
	replyList();
}
//수정
function modifyFn(evt){
	console.log('modifyFn!');

	let $innerHtml = '';
	$innerHtml += `<div class="content-right">`;
	$innerHtml += `	<div class="rcontent modify" contenteditable></div>`;
	$innerHtml += `	<div class="btngrp modify">`;
	$innerHtml += `		<button type="button" class="cancelBtn" data-button-name="cancel">취소</button>`;
	$innerHtml += `		<button type="button" class="modifyBtn"  data-button-name="modify">수정</button>`;
	$innerHtml += `	</div>`;
	$innerHtml += `</div>`;

	const $parent = evt.target.closest('div[data-rnum] .content')
	const $child = $parent.querySelector('.content-right');

	//현재 댓글내용을 숨긴다
	$child.classList.add('hidden');
	const $rcontentRead = $child.querySelector('.rcontent.read');

	//수정 모드로 변경
	$child.insertAdjacentHTML('afterEnd',$innerHtml);	
	const $contnetRight = $parent.querySelector('.content-right:not(.hidden)');
	const $rcontentModify = $contnetRight.querySelector('.rcontent.modify');
	$rcontentModify.focus();
	$rcontentModify.innerHTML = $rcontentRead.innerHTML;

	$rcontentModify.addEventListener('keyup',evt=>{
		//입력된 글자가 있으면
		if(evt.target.textContent.trim().length > 0){
			$modifyBtn.style.backgroundColor = '#00f';
		}else{
			$modifyBtn.style.backgroundColor = '#eee';
		}
  });

	// 취소,수정 버튼 클릭처리
	const $cancelBtn = $contnetRight.querySelector('.cancelBtn');
	const $modifyBtn = $contnetRight.querySelector('.modifyBtn');
	$cancelBtn.addEventListener('click',evt=>{
		evt.stopPropagation();
		$child.classList.remove('hidden');
		$parent.removeChild($contnetRight);
	});
	$modifyBtn.addEventListener('click',evt=>{
		evt.stopPropagation();
		//수정로직 추가  <===============================
		console.log('댓글수정후');
		replyList();
	});
}

//삭제
function deleteFn(evt){
	console.log('deleteFn!');
	document.body.style.overflow='hidden';
	$modal.classList.remove('hidden');
	window.myCurrEvent = ()=>{
		evt.target.closest('.comments').removeChild(evt.target.closest('div[data-rnum]'));
		delete window.myCurrEvent;
	}
	//삭제로직 추가  <===============================
	console.log('댓글삭제후');
	replyList();	
};

//선호
function rgoodFn(evt){
	console.log('rgoodFn!');
}
//비선호
function rbadFn(evt){
	console.log('rbadFn!');
}
//대댓글창
function rereplyFn(evt){
	console.log('rereplyFn!');
	let $innerHtml = '';
	$innerHtml += `<div class="comment rewrite">`;
	$innerHtml += `  <div class="profileImg">`;
	$innerHtml += `    <img src="https://picsum.photos/50" alt="프로파일">`;
	$innerHtml += `  </div>`;
	$innerHtml += `  <div class="rcontent" contenteditable data-placeholder="댓글 추가..."></div>`;
	$innerHtml += `  <div class="btngrp">`;
	$innerHtml += `    <button type="button" class="cancelBtn" data-button-name="cancel">취소</button>`;
	$innerHtml += `    <button type="button" class="writeBtn"  data-button-name="write">댓글</button>`;
	$innerHtml += `  </div>`;
	$innerHtml += `</div>`;
	
	const $comment = evt.target.closest('div[data-rnum]');
	const $commentRewrite = $comment.nextElementSibling;
	
	if( !$commentRewrite || !$commentRewrite.classList.contains('rewrite')) {
		$comment.insertAdjacentHTML('afterend',$innerHtml);		
		const $rcontent = $comment.nextElementSibling.querySelector('.rcontent');
		const $btngrp = $comment.nextElementSibling.querySelector('.btngrp');
		$rcontent.addEventListener('focus',evt=>{
			$btngrp.style.display='block';
		});

		$rcontent.addEventListener('keyup',evt=>{
				//입력된 글자가 있으면
				if(evt.target.textContent.trim().length > 0){
					$btngrp.querySelector('.writeBtn').style.backgroundColor = '#00f';
				}else{
					$btngrp.querySelector('.writeBtn').style.backgroundColor = '#eee';
				}
			});
			
			// 취소 버튼 클릭처리
			const $cancelBtn = $btngrp.querySelector('.cancelBtn');
			$cancelBtn.addEventListener('click',evt=>{
				evt.stopPropagation();
		
				const $parent = evt.target.closest('.comments');
				const $child = evt.target.closest('.comment.rewrite');
				$parent.removeChild($child);
			});

			//대댓댓글 버튼 클릭처리
			const $writeBtn = $btngrp.querySelector('.writeBtn');
			$writeBtn.addEventListener('click',evt=>{
				evt.stopPropagation();
				
				if($rcontent.textContent.trim().length === 0) return;

				const $parent = evt.target.closest('.comments');
				const $child = evt.target.closest('.comment.rewrite');
				$parent.removeChild($child);

				//대댓글 작성로직 추가<=============================== 

				//저장후
				console.log('대댓글 작성후');
				replyList();
			});
	}else if($commentRewrite.classList.contains('rewrite')) {
		$commentRewrite.click();
	}

}

//신고
function chargeFn(evt){
	console.log('신고기능 미구현!!');
}