// ========================================== //
// 회원탈퇴
document.querySelector(".remove").addEventListener("click", () => {
  const form = document.querySelector("#removeForm");

  if (!confirm("정말로 탈퇴하시겠습니까?")) {
    return;
  }

  form.submit();
});



// ========================================== //
// 성별 선택
document.querySelectorAll('input[name="gender-radio"]').forEach((e) => {
  e.addEventListener("change", function(event){
    const genderInput = document.getElementById('gender');
    genderInput.value = event.target.value;
  })
});

// ========================================== //
// 유효성 검사
editBtn = document.querySelector(".edit-btn");
form = document.querySelector("#editForm");

// 에러 메세지
const errMsg = {
  phoneNumber: {
    notPhone: "'-'를 제외한 숫자 11자리를 입력해주세요.",
    empty: "전화번호를 입력해주세요.",
  },
  name: "이름을 입력해주세요.",
  nickname: "닉네임을 입력해주세요.",
  zonecode: "우편번호를 입력해주세요.",
  addr: "주소를 입력해주세요.",
  age: "세자리의 숫자로 입력해주세요"
};

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
editBtn.addEventListener("click", (e) => {
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

  const age = document.querySelector("#age");
  const ageError = document.querySelector("#ageError");
  const ageRegExp = /^[0-9]{0,3}$/;

  if (name.value == "") {
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
  } else if (!ageRegExp.test(age.value)){
    ageError.textContent = errMsg.age;
  } else {
    form.submit();
  }
});
