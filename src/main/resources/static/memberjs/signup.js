// ========================================== //
// 약관동의
const form = document.querySelector("#signupForm");
const checkAll = document.querySelector("#chk_all"); // 모두동의 버튼
const checkBoxs = document.querySelectorAll(".checkbox"); // 약관동의 버튼
const signupBtn = document.querySelector(".sign-btn");

// 체크박스 체크 여부 임시 저장
const agreements = {
  agree1: false,
  agree2: false,
};

checkBoxs.forEach((item) => item.addEventListener("input", toggleCheckBox));

// 모두동의에 의한 나머지 체크박스 상태
// 모두동의 체크 → 모든 체크박스 체크
//  모두동의 해제 → 모든 체크박스 해제
checkAll.addEventListener("click", (e) => {
  // const {checked} = e.target 과 같음
  const checked = e.target.checked;
  if (checked) {
    checkBoxs.forEach((item) => {
      item.checked = true;
      agreements[item.id] = true;
      item.classList.add("agreeActive");
    });
  } else {
    checkBoxs.forEach((item) => {
      item.checked = false;
      agreements[item.id] = false;
      item.classList.remove("agreeActive");
    });
  }
  toggleSubmitButton();
});

// 각각의 체크박스 활성화, 비활성화
function toggleCheckBox(e) {
  const { checked, id } = e.target;
  agreements[id] = checked;
  e.target.parentElement.classList.toggle("agreeActive");
  checkAllStatus();
  toggleSubmitButton();
}

// 체크박스 상태별 모두동의 체크박스 체크 여부
function checkAllStatus() {
  const { agree1, agree2 } = agreements;
  if (agree1 && agree2) {
    checkAll.checked = true;
    console.log(agreements);
  } else {
    checkAll.checked = false;
    console.log(agreements);
  }
}

// 필수동의 체크 여부를 확인한 후 버튼 활성화, 비활성화
function toggleSubmitButton() {
  const { agree1, agree2 } = agreements;
  if (agree1 && agree2) {
    signupBtn.disabled = false; // 버튼 활성화
    signupBtn.classList.add("signupActive");
  } else {
    signupBtn.disabled = true; // 버튼 비활성화
    signupBtn.classList.remove("signupActive");
  }
}

// ========================================== //
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

// ========================================== //
// 유효성 검사

// 에러 메세지
const errMsg = {
  email: {
    notEmail: "이메일 형식이 아닙니다.",
    empty: "이메일을 입력해주세요",
  },
  password: {
    notPw: "8~20자의 영문, 숫자, 특수문자(!@#$%^&*)를 모두 포함한 비밀번호를 입력해주세요.",
    empty: "비밀번호를 입력해주세요",
  },
  phoneNumber: {
    notPhone: "'-'를 제외한 숫자 11자리를 입력해주세요.",
    empty: "전화번호를 입력해주세요",
  },
  name: "이름을 입력해주세요.",
  nickname: "닉네임을 입력해주세요.",
  zonecode: "우편번호를 입력해주세요.",
  addr: "주소를 입력해주세요.",
};

// 이메일
const email = document.querySelector("#email");
const emailError = document.querySelector("#emailError");
const emailRegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
email.addEventListener("change", () => {
  if (emailRegExp.test(email.value)) {
    emailError.textContent = "";
  } else {
    emailError.textContent = errMsg.email.notEmail;
  }
});

// 비밀번호
const password = document.querySelector("#password");
const passwordError = document.querySelector("#passwordError");
const pwRegExp = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/;
password.addEventListener("change", () => {
  if (pwRegExp.test(password.value)) {
    passwordError.textContent = "";
  } else {
    passwordError.textContent = errMsg.password.notPw;
  }
});

// 전화번호
const phoneNumber = document.querySelector("#phoneNumber");
const phoneError = document.querySelector("#phoneError");
const phoneRegExp = /^\d{9,11}$/;
phoneNumber.addEventListener("change", () => {
  if (phoneRegExp.test(phoneNumber.value)) {
    phoneError.textContent = "";
  } else {
    phoneError.textContent = errMsg.phoneNumber.notPhone;
  }
});

// 회원가입 버튼 클릭 시 유효성 검사 (빈 칸)
signupBtn.addEventListener("click", (e) => {
  e.preventDefault();
  console.log("폼 요청");
  const name = document.querySelector("#name");
  const nameError = document.querySelector("#nameError");

  const nickname = document.querySelector("#nickname");
  const nicknameError = document.querySelector("#nicknameError");

  const zonecode = document.querySelector("#zonecode");
  const zonecodeError = document.querySelector("#zonecodeError");

  const addr = document.querySelector("#addr");
  const addrError = document.querySelector("addrError");

  if (email.value == "") {
    emailError.textContent = errMsg.email.empty;
    email.focus();
    exit;
  } else if (!emailRegExp.test(email.value)) {
    emailError.textContent = errMsg.email.notEmail;
    exit;
  } else if (password.value == "") {
    passwordError.textContent = errMsg.password.empty;
    password.focus();
    exit;
  } else if (!pwRegExp.test(password.value)) {
    passwordError.textContent = errMsg.password.notPw;
    exit;
  } else if (name.value == "") {
    nameError.textContent = errMsg.name;
    name.focus();
    exit;
  } else if (nickname.value == "") {
    nicknameError.textContent = errMsg.nickname;
    nickname.focus();
    exit;
  } else if (zonecode.value == "") {
    zonecodeError.textContent = errMsg.zonecode;
    zonecode.focus();
    exit;
  } else if (addr.value == "") {
    addrError.textContent = errMsg.addr;
    addr.focus();
    exit;
  } else if (phoneNumber.value == "") {
    phoneError.textContent = errMsg.phoneNumber.empty;
    phoneNumber.focus();
    exit;
  } else if (!phoneRegExp.test(phoneNumber.value)) {
    phoneError.textContent = errMsg.phoneNumber.notPhone;
    exit;
  } else {
    form.submit();
  }
});
