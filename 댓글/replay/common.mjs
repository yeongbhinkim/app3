export const ajaxCall = {
  //조회
  get(url,handler) {
    fetch(url, {
      method: 'GET',
      headers: { 'Accept': 'application/json' }
    })
      .then(response => response.json())
      .then(json => {console.log(json);handler(json);})
      .catch(error => console.error(error));
  },

  //생성
  post(url, jsonObj, handler) {

    fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',  //전송데이터 타입
        'Accept': 'application/json'         //수신데이터 타입
      },
      body: JSON.stringify(jsonObj)          //js => json포맷 문자열로변환
    })
      .then(response => response.json())
      .then(json => {console.log(json);handler(json);})
      .catch(error => console.error(error));

  },

  //수정
  patch(url, jsonObj, handler) {

    fetch(url, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',    //전송데이터 타입
        'Accept': 'application/json'         //수신데이터 타입
      },
      body: JSON.stringify(jsonObj)          //js => json포맷 문자열로변환
    })
      .then(response => response.json())
      .then(json => {console.log(json);handler(json);})
      .catch(error => console.error(error));

  },

  //삭제
  del(url, handler) {

    fetch(url, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',    //전송데이터 타입
        'Accept': 'application/json'         //수신데이터 타입
      },
    })
      .then(response => response.json())     //json포맷 문자열 => js객체로 변환
      .then(json => {console.log(json);handler(json);})
      .catch(error => console.error(error));

  }
}     

//태그이름, 속성객체, 자식노드를 포함하는 노드만들어 반환하는 함수
export const makeEle = function (node, attributes, ...subNodes) {
  let eleNode = '';
  if (node) {
    //1) element생성
    eleNode = document.createElement(node);
    //2) attributes생성
    if (attributes) {
      for (let attr in attributes) {
        if (attributes.hasOwnProperty(attr)) {
          let attrNode = document.createAttribute(attr);
          attrNode.value = attributes[attr];
          eleNode.setAttributeNode(attrNode);
        }
      }
    }
  }
  //3) textNode생성
  for (let i = 0; i < subNodes.length; i++) {
    let child = subNodes[i];
    if (typeof child == 'string') {
      child = document.createTextNode(child);
    }
    if (node) {
      eleNode.appendChild(child);
    } else {
      eleNode = child;
    }
  }
  return eleNode;
}

export const debounce = (callback, delay) => {
  let timerId;
  return event => {
    if (timerId) clearTimeout(timerId);
    timerId = setTimeout(callback, delay, event);
  };
};

export const throttle = (callback, delay) => {
  let timerId;
  return event => {
    if (timerId) return;
    timerId = setTimeout(() => {
      callback(event);
      timerId = null;
    }, delay, event);
  };
};

