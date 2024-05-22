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

// 이미지 변경
const img_obj = [
  "/img/anime/item1.jpg",
  "/img/anime/item2.jpg",
  "/img/anime/item3.jpg",
  "/img/anime/item4.jpg",
  "/img/anime/item5.jpg",
  "/img/anime/item6.jpg",
  "/img/anime/item7.jpg",
  "/img/anime/item8.jpg",
  "/img/anime/item9.jpg",
  "/img/anime/item10.jpg",
];
num = 0;
function imgChange(cnt) {
  // 현재 이미지 주소 가져오기
  const idx = img_obj.findIndex((obj) => `url("${obj}")` == imageBtn.style.backgroundImage);

  num = idx + cnt;
  if (num < 0) {
    num = img_obj.length + num;
  } else if (num >= img_obj.length) {
    num = num - img_obj.length;
  }
  imageBtn.style.backgroundImage = `url(${img_obj[num]})`;
}

// 현재 시간 표시
function getTime() {
  today = new Date();
  year = String(today.getFullYear()).padStart(4, "0");
  month = String(today.getMonth() + 1).padStart(2, "0");
  day = String(today.getDate()).padStart(2, "0");
  hour = String(today.getHours()).padStart(2, "0");
  minute = String(today.getMinutes()).padStart(2, "0");
  second = String(today.getSeconds()).padStart(2, "0");

  // 마감 기한 저장
  // 마감 일자, 시간 분리
  sale_days = sale_date.split("T");
  sale_time = sale_date.split("T")[1];
  // console.log(sale_time);

  // 마감 일자 년월일 분리
  end_date = sale_days[0].split("-");
  end_year = parseInt(end_date[0]);
  end_month = parseInt(end_date[1]);
  end_day = Number(end_date[2]) + Number(biddingDate);

  // 마감 시간 시분초 분리
  end_hour = sale_time.split(":")[0];
  end_minute = sale_time.split(":")[1];
  end_second = sale_time.split(":")[2];

  // 마감 시간과 현재 시간 비교 후 남은 시간 계산
  // 날짜 비교()
  days = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
  bidding_year = end_year - year;
  bidding_month = end_month - month;
  bidding_day = end_day - day;
  if (bidding_month < 0) {
    bidding_year -= 1;
    bidding_month += 12;
  }
  if (bidding_day < 0) {
    bidding_month -= 1;
    bidding_day += days[month - 1];
    // 윤년 계산 => 기간이 2월달이고, 해당 연도가 윤년일 경우
    if (month == 2) {
      if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
        bidding_day += 1;
      }
    }
  }

  // 시간 비교
  bidding_hour = end_hour - hour;
  bidding_minute = end_minute - minute;
  bidding_second = end_second - second;
  if (bidding_hour < 0) {
    bidding_day -= 1;
    bidding_hour += 24;
  }
  if (bidding_minute < 0) {
    bidding_hour -= 1;
    bidding_minute += 60;
  }
  if (bidding_second < 0) {
    bidding_minute -= 1;
    bidding_second += 60;
  }

  date = bidding_day + "일 " + bidding_hour + "시간 " + bidding_minute + "분 " + Math.floor(bidding_second) + "초";
  document.querySelector(".date_now").innerText = date;

  setTimeout(getTime, 1000);
}

getTime();
