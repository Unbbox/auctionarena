// 비밀번호 가리기
const pwdInput = document.querySelectorAll(".password");
const togglePwd = document.querySelectorAll(".fa-eye");

pwdInput.forEach((pwdInput, index) => {
  pwdInput.type = "password";

  togglePwd[index].addEventListener("click", () => {
    if (pwdInput.type == "password") {
      pwdInput.type = "text";
    } else {
      pwdInput.type = "password";
    }
  });
});

// 비밀번호 유효성 검사
const errMsg = {
  notPw: "8~20자의 영문, 숫자, 특수문자(!@#$%^&*)를 모두 포함한 비밀번호를 입력해주세요.",
  empty: "비밀번호를 입력해주세요",
};

const newPassword = document.querySelector("#newPassword");
const checkPassword = document.querySelector("#checkPassword");
const newPasswordError = document.querySelector("#newPasswordError");
const chkPasswordError = document.querySelector("#chkPasswordError");
const form = document.querySelector("#editForm");

newPassword.addEventListener("change", () => {
  const pwRegExp = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/;
  if (pwRegExp.test(newPassword.value)) {
    newPasswordError.textContent = "";
  } else {
    newPasswordError.textContent = errMsg.notPw;
  }
});

// 버튼 클릭 시 유효성 검사 (빈 칸)
document.querySelector(".site-btn").addEventListener("click", (e) => {
  e.preventDefault();
  console.log("폼 요청");

  if (newPassword.value == "") {
    newPasswordError.textContent = errMsg.empty;
    newPassword.focus();
    exit;
  }
  if (checkPassword.value == "") {
    chkPasswordError.textContent = errMsg.empty;
    checkPassword.focus();
    exit;
  } else {
    form.submit();
  }
});
