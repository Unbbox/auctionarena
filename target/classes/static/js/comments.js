// 날짜 포맷 변경
const formatDate = (data) => {
  const date = new Date(data);
  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    String(date.getHours()).padStart(2, "0") +
    ":" +
    String(date.getMinutes()).padStart(2, "0")
  );
};

// 댓글 목록 가져오기
const commentList = document.querySelector(".comments");

const commentLoaded = () => {
  fetch(`/comments/${pno}/all`)
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
        //   result += `<div><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        // }
        result += `</div></div>`;
      });
      commentList.innerHTML = result;
    });
};

// 리뷰 등록, 수정
const commentForm = document.querySelector(".comment-form");
commentForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const text = commentForm.querySelector("#text");
  const mid = commentForm.querySelector("#mid");
  const nickname = commentForm.querySelector("#nickname");
  const email = commentForm.querySelector("#email");
  const commentNo = commentForm.querySelector("#commentNo"); // 수정일 경우

  const body = {
    pno: pno,
    text: text.value,
    nickname: nickname.value,
    email: email.value,
    mid: mid.value,
    commentNo: commentNo.value,
  };

  // 리뷰 등록
  if (!commentNo.value) {
    fetch(`/comments/${pno}`, {
      headers: {
        "content-type": "application/json",
        "X-CSRF-TOKEN": csrfValue,
      },
      body: JSON.stringify(body),
      method: "post",
    })
      .then((response) => response.text())
      .then((data) => {
        console.log("comment >> " + data);

        text.value = ""; // 작성한 댓글 내용 지우기

        if (data) console.log(data + "번 댓글 등록 완료");
        commentLoaded();
      });
  } else {
    // 리뷰 수정
    fetch(`/comments/${pno}/${reviewNo.value}`, {
      headers: {
        "content-type": "application/json",
        "X-CSRF-TOKEN": csrfValue,
      },
      body: JSON.stringify(body),
      method: "put",
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);

        text.value = "";
        reviewNo.value = "";
        reviewForm.querySelector(".starrr a:nth-child(" + grade + ")").click();

        if (data) alert(data + " 번 리뷰가 수정되었습니다.");
        commentLoaded(); // 댓글 리스트 다시 가져오기
      });
  }
});

commentLoaded();
