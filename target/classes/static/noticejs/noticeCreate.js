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
      "X-CSRF-TOKEN": csrfValue,
    },
    body: formData,
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);
      if (data) {
            Swal.fire({
            title: "이미지 삭제",
            text: "이미지를 삭제하시겠습니까?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "네",
            cancelButtonText: "아니요",
          }).then((result) => {
            if (result.isConfirmed) {
              currentLi.remove();
            } else {
              return;
            }
          }); 
      }
    });
});
