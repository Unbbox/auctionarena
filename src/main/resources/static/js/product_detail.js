const sub_images = document.querySelectorAll(".item__details__pic__sub div");
const imageBtn = document.querySelector(".anime__details__pic");
const wishBtn = document.querySelector(".anime__details__btn .wish_list");

sub_images.forEach((iamge) => {
  iamge.addEventListener("click", (e) => {
    imageBtn.style.backgroundImage = iamge.style.backgroundImage;
  });
});

// 찜목록 활성화 비활성화
wishBtn.addEventListener("click", (e) => {
  const btn = e.target.querySelector("i");

  if (btn.classList.contains("fa-heart")) {
    if (btn.classList.contains("fa-regular")) {
      btn.classList.remove("fa-regular");
      btn.classList.add("fa-solid");
    } else {
      btn.classList.remove("fa-solid");
      btn.classList.add("fa-regular");
    }
  }
});

// 탭별 활성화 표시 및 해당 내용 출력
const items_nav = document.querySelectorAll(".items_nav li");
const items = document.querySelectorAll(".item_nav_sub");

const detail = document.querySelector(".item_detail");
const bidding = document.querySelector(".item-bidding-record");
const review = document.querySelector(".item_review");

items_nav.forEach((nav) => {
  nav.addEventListener("click", (e) => {
    e.preventDefault();

    //탭 활성화
    items_nav.forEach((e) => {
      e.classList.remove("active");
    });
    nav.classList.add("active");

    // 해당 내용 출력
    items.forEach((item) => {
      item.style.display = "none";
      if (nav.classList.contains("detail")) {
        detail.style.display = "block";
      } else if (nav.classList.contains("bidding")) {
        bidding.style.display = "block";
      } else if (nav.classList.contains("review")) {
        review.style.display = "block";
      }
    });
  });
});

// 이미지 변경 => images로 대체
console.log(img_arr);
// const img_obj = [
//   "/img/anime/item1.jpg",
//   "/img/anime/item2.jpg",
//   "/img/anime/item3.jpg",
//   "/img/anime/item4.jpg",
//   "/img/anime/item5.jpg",
//   "/img/anime/item6.jpg",
//   "/img/anime/item7.jpg",
//   "/img/anime/item8.jpg",
//   "/img/anime/item9.jpg",
//   "/img/anime/item10.jpg",
// ];
// const img_obj = [];
// images.forEach((image) => {
//   img_obj.add(image);
// });
// console.log(img_obj);

// 현재 이미지 위치 저장용
num = 0;
// 작은 이미지 클릭 시 메인 이미지 변경
function imgChange(cnt) {
  // 현재 이미지 주소 가져오기
  const idx = img_arr.findIndex((obj) => `url("${obj}")` == imageBtn.style.backgroundImage);

  num = idx + cnt;
  if (num < 0) {
    num = img_arr.length + num;
  } else if (num >= img_arr.length) {
    num = num - img_arr.length;
  }
  imageBtn.style.backgroundImage = `url(${img_arr[num]})`;
}

function chgImage(cnt) {}

// 현재 시간 표시
function getTime() {
  /* sale_date도 date로 입력해서 날짜만 비교 */
  // 현재 시간과 제품 등록 시간 가져오기
  new_tody = new Date();
  bid_end_date = new Date(sale_date);

  // (제품 등록시간 + 응찰 기간) 후 각 시간을 ms 단위로 가져오기
  nt = new_tody.getTime();
  bed = bid_end_date.getTime() + parseInt(biddingDate) * 1000 * 60 * 60 * 24;

  // 경매 종료 일자 표시
  let endBidding = new Date(sale_date);
  endBidding.setDate(endBidding.getDate() + parseInt(biddingDate));

  end_year = endBidding.getFullYear();
  end_month = endBidding.getMonth() + 1;
  end_day = endBidding.getDate();
  end_hour = endBidding.getHours();
  end_min = endBidding.getMinutes();
  end_date = end_year + "년 " + end_month + "월 " + end_day + "일 " + end_hour + "시 " + end_min + "분 ";
  document.querySelector(".end_date").innerText = end_date;

  // 응찰 기간이 현재 시간보다 클 경우 일, 시, 분, 초 저장
  if (nt < bed) {
    sec = parseInt(bed - nt) / 1000;
    days = parseInt(sec / 60 / 60 / 24);
    sec = sec - days * 60 * 60 * 24;
    hour = parseInt(sec / 60 / 60);
    sec = sec - hour * 60 * 60;
    min = parseInt(sec / 60);
    sec = parseInt(sec - min * 60);

    // if (hour < 10) {
    //   hour = "0" + hour;
    // }
    if (min < 10) {
      min = "0" + min;
    }
    if (sec < 10) {
      sec = "0" + sec;
    }

    date = days + "일 " + hour + "시간 " + min + "분 " + sec + "초";
  } else {
    date = "마감 되었습니다.";
  }
  /* 남은 시간 코드 끝 */

  document.querySelector(".date_now").innerText = date;

  setTimeout(getTime, 1000);
}

getTime();
