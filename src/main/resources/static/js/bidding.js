// 날짜 포맷 변경
const format_Date = (data) => {
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

// bidding 내역 get
const getBiddingList = () => {
  fetch(`/biddings/${pno}/all`)
    .then((response) => response.json())
    .then((data) => {
      console.log("bid : ", data);

      // 입찰 내역이 없을 시 출력
      if (data.length <= 0) {
        biddingList.innerText = "입찰 기록이 없습니다.";

        // 가격 3자리씩 끊어서 comma 넣기
        document.querySelector(".currPrice").innerText = startPrice.replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원";
        // 입찰 기록 없으면 바로 종료
        return;
      }

      let result = "";
      data.forEach((bidding, index) => {
        // 입찰가 format
        bidPrice = `${bidding.biddingPrice}`.replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        result += `<tr class="text-gray-700 dark:text-white-400">`;

        if (index == 0) {
          if (user == `${bidding.mnickName}`) {
            result += `<td class="px-4 py-3" style="text-decoration: underline; color: #e53637;">${bidding.mnickName}</td>`;
            result += `<td class="px-4 py-3" style="text-decoration: underline; color: #e53637;">` + bidPrice + `원</td>`;
            result += `<td class="px-4 py-3" style="text-decoration: underline; color: #e53637;">` + format_Date(`${bidding.biddingTime}`) + `</td>`;
          } else {
            result += `<td class="px-4 py-3" style="text-decoration: underline;">${bidding.mnickName}</td>`;
            result += `<td class="px-4 py-3" style="text-decoration: underline;">` + bidPrice + `원</td>`;
            result += `<td class="px-4 py-3" style="text-decoration: underline;">` + format_Date(`${bidding.biddingTime}`) + `</td>`;
          }
        } else {
          result += `<td class="px-4 py-3">${bidding.mnickName}</td>`;
          result += `<td class="px-4 py-3" name="bidPrice" id="bidPrice">` + bidPrice + `원</td>`;
          result += `<td class="px-4 py-3">` + format_Date(`${bidding.biddingTime}`) + `</td>`;
        }
        result += `</tr>`;
      });

      // 현재 경매가 업데이트
      if (data[0].biddingPrice != null) {
        document.querySelector(".currPrice").innerText = data[0].biddingPrice.toLocaleString("ko-KR") + "원";
        document.querySelector(".currPrice").value = data[0].biddingPrice;
      }
      biddingList.innerHTML = result;
      // console.log("biddingCnt : ", biddingCnt);
      document.querySelector(".biddingCnt").innerText = biddingCnt + "회";
    });
};

getBiddingList();

// bidding 등록 post
const biddingForm = document.querySelector(".biddingForm");
biddingForm.addEventListener("submit", (e) => {
  e.preventDefault();
  // biddingPrice, mid, mNickname
  const biddingPrice = biddingForm.querySelector("#biddingPrice");
  const mid = biddingForm.querySelector("#mid");
  const mNickName = biddingForm.querySelector("#mNickName");
  const currPrice = document.querySelector(".currPrice");

  // 입찰 마감되었을 때 입찰 안되게 해야함
  const bidding_date = document.querySelector(".date_now");
  if (bidding_date.classList.contains("finish_sale")) {
    alert("경매가 마감되었습니다.");
    return;
  }
  // 처음 입찰 기록 없을 때 입찰 기록 설정
  if (currPrice.value == undefined) {
    currPrice.value = startPrice;
  }
  if (biddingPrice.value == undefined) {
    biddingPrice.value = startPrice;
  }

  // console.log("currPrice value : ", currPrice.value);
  // console.log("biddingPrice : ", biddingPrice.value);
  // console.log("currPrice type : ", typeof currPrice.value);
  // console.log("biddingPrice type : ", typeof biddingPrice.value);

  if (Number(biddingPrice.value) <= Number(currPrice.value)) {
    alert("입찰 금액이 현재 경매가보다 낮습니다.");
    return;
  } else {
    const body = {
      pno: pno,
      biddingPrice: biddingPrice.value,
      mid: mid.value,
      mNickName: mNickName.value,
    };

    if (biddingPrice)
      // 입찰 등록
      fetch(`/biddings/${pno}`, {
        headers: {
          "content-type": "application/json",
          "X-CSRF-TOKEN": csrfValue,
        },
        body: JSON.stringify(body),
        method: "post",
      })
        .then((response) => response.text())
        .then((data) => {
          console.log(data);

          biddingPrice.value = "";

          getBiddingList();
          if (data) alert("입찰 등록이 완료되었습니다.");
        });
  }
});
