document.querySelector(".link").addEventListener("click", (e) => {
  e.preventDefault();

  const heartdiv = e.target.querySelector(".wish");

  heartdiv.addEventListener("click", (e) => {
    // const heart = heartbtn.querySelector(".fa-heart");
    console.log(heartbtn);
    const heartbtn = heartdiv.querySelector("i");

    if (heartbtn.classList.contains("fa-regular")) {
      heartbtn.classList.remove("fa-regular");
      heartbtn.classList.add("fa-solid");
    } else if (heartbtn.classList.contains("fa-solid")) {
      heartbtn.classList.remove("fa-solid");
      heartbtn.classList.add("fa-regular");
    }
  });
  // window.location.href = "https://www.naver.com";
  // heartbtn = e.target;
  // console.log(heartbtn);
  // console.log(heartbtn.querySelector(".fa-heart"));
  window.location.href = "anime-details";
});
