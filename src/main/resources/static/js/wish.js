// 찜목록 활성화 비활성화
const wishBtn = document.querySelector(".follow_btn");
const wishForm = document.querySelector(".wishForm");
// const mid = wishForm.querySelector("#mid");

if ((user != "anonymousUser") & (user == user2)) {
  wishBtn.addEventListener("click", (e) => {
    e.preventDefault();
    const btn = e.target.querySelector("i");

    if (btn.classList.contains("fa-heart")) {
      if (btn.classList.contains("fa-regular")) {
        btn.classList.remove("fa-regular");
        btn.classList.add("fa-solid");

        const body = {
          pno: pno,
          mid: mid,
        };

        console.log("body : ", body);

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
            console.log("찜 목록 추가: ", data);
          });
      } else {
        btn.classList.remove("fa-solid");
        btn.classList.add("fa-regular");

        const body = {
          pno: pno,
          mid: mid,
        };

        console.log("remove body : ", body)

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

    showWish();
  });
}


const showWish =  () => {
  const btn = wishBtn.querySelector(".fa-heart");
  console.log("찜목록 wish : ", user);

  console.log("type of pno, mid : ", typeof pno, typeof mid);
  
  // if (user != 'anonymousUser') {
  fetch(`/wish/${pno}/${mid}`)
    .then((response) => response.json())
    .then((data) => {
      if(data) {
        console.log("data.mid: ", data.mid);
        console.log("mid: ", mid);

        if(data.mid == mid) {
          btn.classList.remove("fa-regular");
          btn.classList.add("fa-solid");
        } else {
          btn.classList.remove("fa-solid");
          btn.classList.add("fa-regular");
        }
      } else {
        console.log("no data : ", data);
        return;
      }
    });
  // } 
};

// if ((user != "anonymousUser") & (user == user2)) {
  showWish();
// }