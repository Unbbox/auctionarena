document.querySelector(".remove").addEventListener("click", () => {
  const form = document.querySelector("#removeForm");

  if (!confirm("정말로 탈퇴하시겠습니까?")) {
    return;
  }

  form.submit();
});
