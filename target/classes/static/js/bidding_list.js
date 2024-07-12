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



paybtns.forEach((btn, index) => {
    btn.addEventListener("click", (e) => {
        e.preventDefault()

        const ptitle = document.querySelectorAll(".bid__list__detail a p")[index].innerText; // 상품명 가져오기
        let priceString = document.querySelectorAll(".my_bid_price")[index].innerText.trim(); 
        priceString = priceString.replace(/,/g, '');
        priceString = priceString.replace(' 원', '');

        const price = parseInt(priceString);

        // console.log("Product Title:", ptitle);
        // console.log("Product Price:", price);
        // console.log("email : ", user_email);

        kakaoPay(user_email, username, ptitle, price);
    })
})

// 결제창 함수 넣어주기
var IMP = window.IMP;

var today = new Date();
var hours = today.getHours(); // 시
var minutes = today.getMinutes();  // 분
var seconds = today.getSeconds();  // 초
var milliseconds = today.getMilliseconds();
var makeMerchantUid = `${hours}` + `${minutes}` + `${seconds}` + `${milliseconds}`;

function kakaoPay(useremail, username, ptitle, price) {
    if (confirm("구매 하시겠습니까?")) { // 구매 클릭시 한번 더 확인하기
            IMP.init("imp63664483"); // 가맹점 식별코드
            IMP.request_pay({
                pg: 'kakaopay.TC0ONETIME', // PG사 코드표에서 선택
                pay_method: 'card', // 결제 방식
                merchant_uid: "IMP" + makeMerchantUid, // 결제 고유 번호
                name: ptitle, // 제품명
                amount: price, // 가격
                //구매자 정보 ↓
                buyer_email: `${useremail}`,
                buyer_name: `${username}`,
            }, async function (rsp) { // callback
                if (rsp.success) { //결제 성공시
                    console.log(rsp);
					
                    //결제 성공시 프로젝트 DB저장 요청
                    if (response.status == 200) { // DB저장 성공시
                        alert('결제 완료!')
                        window.location.reload();
                    } else { // 결제완료 후 DB저장 실패시
                        alert(`error:[${response.status}]\n결제요청이 승인된 경우 관리자에게 문의바랍니다.`);
                        // DB저장 실패시 status에 따라 추가적인 작업 가능성
                    }
                } else if (rsp.success == false) { // 결제 실패시
                    alert(rsp.error_msg)
                }
            });
        }
    }

    // 결제 성공 시 payment 가져와서 결제하기 버튼 대신 결제 완료 띄우기