// 날짜 포맷 변경
const formatDate = (data) => {
  const date = new Date(data);
  return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
};

// 댓글 목록 가져오기
const commentList = document.querySelector(".comments");

const commentLoaded = () => {
  fetch(`/comments/product/${pno}`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      document.querySelector(".commentCnt").innerHTML = data.length;

      let result = "";
      data.forEach((comment) => {
        result += `<div class="product_review_item">`;
        result += `<div class="product_review_item_pic">`;
        result += `<img src="/img/anime/review-1.jpg" alt="" /></div>`;
        result += `<div class="product_review_item_text">`;
        result += `<h6>${comment.nickname} - <span>${formatDate(comment.createdDate)}</span></h6>`;
        result += `<p>${comment.text}</p>`;

        // 추후 추가

        // 로그인 user(email) == 작성자(reply.writerEmail)
        // if (`${email}` == `${reply.writerEmail}`) {
        //   result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
        //   result += `<div><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        // }
        result += `</div></div>`;
      });
      commentList.innerHTML = result;
    });
};

// 리뷰 등록
const commentForm = document.querySelector(".comment-form");
commentForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const text = commentForm.querySelector("#comments");
  //   const nickname = commentForm.querySelector("#nickname");
  const commentNo = commentForm.querySelector("#commentNo"); // 수정일 경우

  const body = {
    // nickname: nickname,
    text: text.value,
    commentNo: commentNo.value,
  };

  if (!commentNo.value) {
    fetch(`/comments/product/${pno}`, {
      headers: {
        "content-type": "application/json",
        // "X-CSRF-TOKEN": csrfValue,
      },
      body: JSON.stringify(body),
      method: "post",
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);

        text.value = ""; // 작성한 댓글 내용 지우기

        if (data) alert(data + "번 댓글 등록 완료");
        commentLoaded();
      });
  }
});

commentLoaded();
