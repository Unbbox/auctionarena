// 비밀번호 유효성 검사
const errMsg2 = {
  email: "이메일을 입력해주세요",
  password: "비밀번호를 입력해주세요",
};

const email = document.querySelector("#email");
const emailError = document.querySelector("#emailError");
const password = document.querySelector("#password");
const passwordError = document.querySelector("#passwordError");
const chkForm = document.querySelector("#chkForm");

// 버튼 클릭 시 유효성 검사 (빈 칸)
document.querySelector(".site-btn").addEventListener("click", (e) => {
  e.preventDefault();
  console.log("폼 요청");

  if (email.value == "") {
    emailError.textContent = errMsg2.email;
    email.focus();
    exit;
  } else if (password.value == "") {
    passwordError.textContent = errMsg2.password;
    password.focus();
    exit;
  } else {
    chkForm.submit();
  }
});
