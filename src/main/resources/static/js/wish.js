// 찜목록 활성화 비활성화
const wishBtn = document.querySelector(".follow_btn");
const wishForm = document.querySelector(".wishForm");
const mid = wishForm.querySelector("#mid");

wishBtn.addEventListener("click", (e) => {
  e.preventDefault();
  const btn = e.target.querySelector("i");

  if (btn.classList.contains("fa-heart")) {
    if (btn.classList.contains("fa-regular")) {
      btn.classList.remove("fa-regular");
      btn.classList.add("fa-solid");

      const body = {
        pno: pno,
        mid: mid.value,
      };

      console.log("mid : ", mid);

      fetch(`/wish/add/${pno}`, {
        headers: {
          "content-type": "application/json",
          "X-CSRF-TOKEN": csrfValue,
        },
        body: JSON.stringify(body),
        method: "post",
      })
        .then((response) => response.text())
        .then((data) => {
          console.log(data + "번 제품 찜 목록 추가");
        });
    } else {
      btn.classList.remove("fa-solid");
      btn.classList.add("fa-regular");

      const body = {
        pno: pno,
        mid: mid.value,
      };

      fetch(`/wish/remove/${pno}`, {
        headers: {
          "content-type": "application/json",
          "X-CSRF-TOKEN": csrfValue,
        },
        body: JSON.stringify(body),
        method: "post",
      })
        .then((response) => response.text())
        .then((data) => {
          console.log(data + "번 제품 찜 목록 제거");
        });
    }
  }
});
