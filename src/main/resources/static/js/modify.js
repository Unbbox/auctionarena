// 이미지 x 버튼 클릭시
// 이미지 li만 제거
document.querySelector(".upload_image_preview").addEventListener("click", (e) => {
  e.preventDefault();

  // li 태그 가져오기
  const currentLi = e.target.closest("li");

  if (confirm("정말로 삭제하시겠습니까?")) {
    currentLi.remove();
  }
});
