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
