const fileInput = document.querySelector("#fileInput");

const uploadResult = document.querySelector(".uploadResult ul");

function checkExtension(fileName) {
  const regex = /(.*?).(png|gif|jpg)$/;

  console.log(regex.test(fileName));
  return regex.test(fileName);
}

function showUploadImages(arr) {
  console.log("showUploadImages ", arr);
}
