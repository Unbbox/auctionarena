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
today = new Date();
year = today.getFullYear();
month = today.getMonth() + 1;
day = today.getDate();
hour = today.getHours();
minute = today.getMinutes();
second = today.getSeconds();

date = year + "년 " + month + "월 " + day + "일 " + hour + ":" + minute + ":" + second;

document.querySelector(".date_now").innerText = date;
// console.log(year + "년" + month + "월" + day + "일 " + hour + ":" + minute + ":" + second);
