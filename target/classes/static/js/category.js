const cateForm = document.querySelectorAll(".category__menu > ul > li");

cateForm.forEach((menu) => {
  menu.addEventListener("click", (e) => {
    e.preventDefault();

    console.log("menu : ", e.target);
    const cateValue = Number(e.target.querySelector("input").value);
    console.log("cateValue : ", cateValue);
    console.log("cateValue type : ", typeof cateValue);

    fetch(`/category/cate/${cateValue}`)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
      });
  });
});
