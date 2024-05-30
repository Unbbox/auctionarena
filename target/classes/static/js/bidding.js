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

const formatPrice = (data) => {
  // 가격 포매팅
};

// bidding 내역 get
const getBiddingList = () => {
  fetch(`/biddings/${pno}/all`)
    .then((response) => response.json())
    .then((data) => {
      console.log("bid =>", data);

      // 입찰 내역이 없을 시 출력
      if (data.length <= 0) biddingList.innerText = "입찰 기록이 없습니다.";

      let result = "";
      data.forEach((bidding) => {
        // <tr class="text-gray-700 dark:text-white-400 biddingList" th:each="bidding : ${dto.biddingDtos}">
        // <td class="px-4 py-3" th:text="${bidding.mNickName}">입찰자1</td>
        // <td class="px-4 py-3">[[${#numbers.formatInteger(bidding.biddingPrice, 3, 'COMMA')}]]원</td>
        // <td class="px-4 py-3" th:text="${#temporals.format(bidding.biddingTime,'yyyy-MM-dd HH:mm:ss')}"></td>
        // </tr>

        // 입찰가 format
        bidPrice = `${bidding.biddingPrice}`.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        result += `<tr class="text-gray-700 dark:text-white-400">`;
        result += `<td class="px-4 py-3">${bidding.mnickName}</td>`;
        result += `<td class="px-4 py-3" name="bidPrice" id="bidPrice">` + bidPrice + `원</td>`;
        result += `<td class="px-4 py-3">` + format_Date(`${bidding.biddingTime}`) + `</td>`;
        result += `</tr>`;
      });

      // 현재 경매가 업데이트
      console.log("현재 최고가 : ", data[0].biddingPrice);
      document.querySelector(".currPrice").innerText = data[0].biddingPrice.toLocaleString("ko-KR") + "원";

      biddingList.innerHTML = result;
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

  if (biddingPrice.value < currPrice) {
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

          if (data) alert("입찰 등록이 완료되었습니다.");

          getBiddingList();
        });
  }
});
