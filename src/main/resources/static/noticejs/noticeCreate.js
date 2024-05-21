// x를 누르면 파일 삭제 요청
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  e.preventDefault();

  const currentLi = e.target.closest("li");

  const filePath = e.target.closest("a").dataset.file;
  console.log("filePath", filePath);

  const formData = new FormData();
  formData.append("filePath", filePath);

  fetch("/upload/remove", {
    method: "post",
    headers: {
      "X-CSRF-TOKEN": CSSFontFeatureValuesRule,
    },
    body: formData,
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);
      if (data) {
        currentLi.remove();
      }
    });
});
