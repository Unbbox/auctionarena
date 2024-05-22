const upload_div = document.querySelector(".upload_image");
const upload_image = document.querySelector(".upload_product_image");

// div 클릭 시 파일 등록
upload_div.addEventListener("click", (e) => {
  upload_image.click();
});

upload_image.addEventListener("change", (e) => {
  const uploadFiles = [];
  const files = e.currentTarget.files;
  const imagePreview = document.querySelector(".upload_image_preview");
  const docFrag = new DocumentFragment();

  // 이미지 개수가 10개 초과일 시 => Java로 옮길 생각
  if ([...files].length >= 11) {
    alert("이미지는 최대 10개까지 업로드가 가능합니다.");
    return;
  }

  // 이미지가 아닌 파일 업로드 시 예외처리
  [...files].forEach((file) => {
    if (!file.type.match("image/.*")) {
      alert("이미지만 업로드 가능합니다.");
      return;
    }

    if ([...files].length < 11) {
      uploadFiles.push(file);
      const reader = new FileReader();
      reader.onload = (e) => {
        const preview = createElement(e, file);
        imagePreview.appendChild(preview);
      };
      reader.readAsDataURL(file);
    }
  });
});

function createElement(e, file) {
  const li = document.createElement("li");
  const img = document.createElement("img");
  img.setAttribute("src", e.target.result);
  img.setAttribute("data-file", file.name);
  li.appendChild(img);

  return li;
}
