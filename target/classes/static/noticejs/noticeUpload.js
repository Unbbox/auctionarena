const fileInput = document.querySelector("#fileInput");

const uploadResult = document.querySelector(".uploadResult ul");

function checkExtension(fileName) {
  const regex = /(.*?).(png|gif|jpg)$/;

  console.log(regex.test(fileName));
  return regex.test(fileName);
}

// 업로드이미지 보여주기
function showUploadImages(arr) {
  console.log("showUploadImages ", arr);

  let tags = "";

  arr.forEach((obj, idx) => {
    tags += `<li data-name="${obj.fileName}" data-path="${obj.folderPath}" data-uuid="${obj.uuid}">`;
    tags += `<a href=""><img src="/upload/display?fileName=${obj.imageURL}" class="block"></a>`;
    tags += `<div>`;
    tags += `<span class="text-sm d-inline-block mx-1">${obj.fileName}</span>`;
    tags += `<a href="#" data-file="${obj.imageURL}">`;
    tags += `<i class="fa-solid fa-xmark"></i>`;
    tags += `</a></div></li>`;
  });
  uploadResult.insertAdjacentHTML("beforeend", tags);
}

// fileInput change 이벤트
fileInput.addEventListener("change", (e) => {
  const files = e.target.files;

  const formData = new FormData();
  for (let index = 0; index < files.length; index++) {
    if (checkExtension(files[index].name)) {
      formData.append("folderType", "notice");
      formData.append("uploadFiles", files[index]);
    }
  }

  for (const value of formData.values()) {
    console.log(value);
  }

  fetch("/upload/uploadAjax", {
    method: "post",
    headers: {
      "X-CSRF-TOKEN": csrfValue,
    },
    body: formData,
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      showUploadImages(data);
    });
});

// register, modify 중복 사용
document.querySelector("#create-form").addEventListener("submit", (e) => {
  e.preventDefault();

  const form = e.target;

  const attachInfos = document.querySelectorAll(".uploadResult ul li");
  console.log(attachInfos);

  let result = "";
  attachInfos.forEach((obj, idx) => {
    result += `<input type='hidden' value='${obj.dataset.path}' name='noticeImageDtos[${idx}].path'>`;
    result += `<input type='hidden' value='${obj.dataset.uuid}' name='noticeImageDtos[${idx}].uuid'>`;
    result += `<input type='hidden' value='${obj.dataset.name}' name='noticeImageDtos[${idx}].imgName'>`;
  });
  form.insertAdjacentHTML("beforeend", result);

  console.log(form.innerHTML);

  form.submit();
});
