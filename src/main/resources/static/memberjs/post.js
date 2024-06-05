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
