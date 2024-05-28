// ========================================== //
// 우편번호 찾기
function execDaumPostcode() {
  new daum.Postcode({
    oncomplete: function (data) {
      // 팝업을 통한 검색 결과 항목 클릭 시 실행
      var addr = ""; // 주소_결과값이 없을 경우 공백
      var extraAddr = ""; // 참고항목

      //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다
      if (data.userSelectedType === "R") {
        // 도로명 주소를 선택
        addr = data.roadAddress;
      } else {
        // 지번 주소를 선택
        addr = data.jibunAddress;
      }

      if (data.userSelectedType === "R") {
        if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
          extraAddr += data.bname;
        }
        if (data.buildingName !== "" && data.apartment === "Y") {
          extraAddr += extraAddr !== "" ? ", " + data.buildingName : data.buildingName;
        }
        if (extraAddr !== "") {
          extraAddr = " (" + extraAddr + ")";
        }
      } else {
        // 사용자가 도로명 주소를 선택하지 않은 경우 입력창 초기화
        document.getElementById("addr").value = "";
      }

      // 선택된 우편번호와 주소 정보를 input 박스에 넣는다
      document.getElementById("zonecode").value = data.zonecode;
      document.getElementById("addr").value = addr;
      // document.getElementById("UserAdd1").value += extraAddr;
      document.getElementById("phoneNumber").focus(); // 우편번호 + 주소 입력이 완료 → 상세주소로 포커스 이동
    },
  }).open();
}

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
email.addEventListener("change", () => {
  const emailRegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  if (emailRegExp.test(email.value)) {
    emailError.textContent = "";
  } else {
    emailError.textContent = errMsg.email.notEmail;
  }
});

// 비밀번호
const password = document.querySelector("#password");
const passwordError = document.querySelector("#passwordError");
password.addEventListener("change", () => {
  const pwRegExp = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/;
  if (pwRegExp.test(password.value)) {
    passwordError.textContent = "";
  } else {
    passwordError.textContent = errMsg.password.notPw;
  }
});

// 전화번호
const phoneNumber = document.querySelector("#phoneNumber");
const phoneError = document.querySelector("#phoneError");
phoneNumber.addEventListener("change", () => {
  const phoneRegExp = /^\d{9,11}$/;
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
  } else if (password.value == "") {
    passwordError.textContent = errMsg.password.empty;
    password.focus();
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
  } else {
    form.submit();
  }
});
