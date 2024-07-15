function showEndTime() {
  /* sale_date도 date로 입력해서 날짜만 비교 */
  // 현재 시간과 제품 등록 시간 가져오기
  const startDates = document.querySelectorAll("#startDate");
  const bidDate = document.querySelectorAll("#bidDate");
  const bid_end_date = document.querySelectorAll(".bid_end_date");

  console.log(startDates.value)
  console.log(bidDate.value)

  today = new Date();

  // 각 제품별 등록시간 + 경매기간 해서 마감 기간 입력
  startDates.forEach((dates, index) => {
    let bidEndDate = new Date(dates.value);

    // (제품 등록시간 + 응찰 기간) 후 각 시간을 ms 단위로 가져오기
    let t_day = today.getTime();
    let be_date = bidEndDate.getTime() + parseInt(bidDate[index].value) * 1000 * 60 * 60 * 24;
    // console.log("t_day : ", t_day);
    // console.log("be_date : ", be_date);

    // 경매 종료 일자 표시
    let endBidding = new Date(bidEndDate);
    endBidding.setDate(endBidding.getDate() + parseInt(bidDate[index].value));

    const end_year = endBidding.getFullYear();
    const end_month = endBidding.getMonth() + 1;
    const end_day = endBidding.getDate();
    const end_hour = endBidding.getHours();
    const end_min = endBidding.getMinutes();
    const end_date = end_year + "년 " + end_month + "월 " + end_day + "일 " + end_hour + "시 " + end_min + "분 ";

    if (t_day > be_date) {
      bid_end_date[index].innerText = "경매 마감";
    } else {
      bid_end_date[index].innerText = "진행 중";
    }
  });
}

showEndTime();


// 제품 최고가, 응찰가 가져와서 비교
const bestPrices = document.querySelectorAll(".best_price");
const myBidPrices = document.querySelectorAll(".my_bid_price");
const endBid = document.querySelectorAll(".bid_end_date");
const paybtns = document.querySelectorAll(".pay-btn")

// console.log("btns",paybtns);
// console.log("bestPrice : ",bestPrices[0].value);
// console.log("bidPrice : ",myBidPrices[0].value);

paybtns.forEach((btn, index) => {
    // console.log("bestPrice : ",bestPrices[index].innerText);
    // console.log("bidPrice : ",myBidPrices[index].innerText);

    // console.log("btn", btn);

    if ((bestPrices[index].innerText == myBidPrices[index].innerText) && (endBid[index].innerText == "경매 마감")) {
        btn.style.removeProperty("display");
    }  
});



// 구매자 정보
const user_email = document.querySelector(".user_email").value;
const username = document.querySelector(".username").value;
const mid = document.querySelector(".mid").value;


paybtns.forEach((btn, index) => {
    btn.addEventListener("click", (e) => {
        e.preventDefault()

        const ptitle = document.querySelectorAll(".bid__list__detail a p")[index].innerText; // 상품명 가져오기
        let priceString = document.querySelectorAll(".my_bid_price")[index].innerText.trim(); 
        priceString = priceString.replace(/,/g, '');
        priceString = priceString.replace(' 원', '');

        const price = parseInt(priceString);

        // bno 가져오기
        const bno = document.querySelectorAll(".bno")[index].value;
        // console.log("bno : ", bno)
        // console.log("Product Title:", ptitle);
        // console.log("Product Price:", price);
        // console.log("email : ", user_email);

     Swal.fire({
      title: "구매하시겠습니까?",
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '예',
      cancelButtonText: '아니오',
      timer: 10000,
    }
    ).then(result => {
        if(result.isConfirmed) {
           kakaoPay(user_email, username, ptitle, price, bno, mid);
        }
    })
        }
    )
})

// 결제창 함수 넣어주기
var IMP = window.IMP;

var today = new Date();
var hours = today.getHours(); // 시
var minutes = today.getMinutes();  // 분
var seconds = today.getSeconds();  // 초
var milliseconds = today.getMilliseconds();
var makeMerchantUid = `${hours}` + `${minutes}` + `${seconds}` + `${milliseconds}`;

function kakaoPay(useremail, username, ptitle, price, bno, mid) {
            IMP.init("imp63664483"); // 고객사 식별코드
            IMP.request_pay({
                pg: 'kakaopay.TC0ONETIME', // PG사 코드표에서 선택
                pay_method: 'card', // 결제 방식
                merchant_uid: "IMP" + makeMerchantUid, // 결제 고유 번호
                name: ptitle, // 제품명
                amount: price, // 가격
                //구매자 정보 ↓
                buyer_email: `${useremail}`,
                buyer_name: `${username}`,
            }, function (rsp) { // callback
                    const paymentInfo = {
                            "status": true,
                            "mno":mid,
                            "bno":bno,
                            "impUid": rsp.imp_uid,
                            "merchantUid": rsp.merchant_uid
                            // 필요한 경우 추가 정보도 포함할 수 있음
                        };
                if (rsp.success) { //결제 성공시
                    // console.log("rsp: ", rsp);
                   
					fetch(`/payments`, {
                        method: "post",
                        headers: {
                            "content-type": "application/json",
                            "X-CSRF-TOKEN": csrfValue,
                        },
                        body: JSON.stringify(paymentInfo)
                    })
                    .then(response => {
                        // console.log("response ",response)
                        
                        return response.text()

                    }
                    )
                    .then(data => {
                        // 서버 결제 API 성공시 로직
                            // alert
                            Swal.fire({
                            title: "결제가 완료되었습니다.",
                            showConfirmButton: false,
                            timer: 1000,
                            });
                    })
                    .catch(error => {
                        console.error("결제 정보 서버 전송 실패 : ", error);
                    });

                } else if (rsp.success == false) { // 결제 실패시
                    Swal.fire({
                            title: "결제가 취소되었습니다.",
                            showConfirmButton: false,
                            timer: 1000,
                            });
                    console.log(rsp.error_msg)
                }
            });
        }

    // 결제 성공 시 payment 가져와서 결제하기 버튼 대신 결제 완료 띄우기
