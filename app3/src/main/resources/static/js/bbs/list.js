'use strict';

const $bbs = document.querySelector('.bbs-wrap');
const category = ($bbs?.dataset.code)? $bbs.dataset.code : '';

    writeBtn?.addEventListener('click', e => {
        const url = `/bbs/add?category=${category}`
        location.href= url;   // get /bbs/add
    });
