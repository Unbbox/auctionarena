document.querySelector(".notice-remove-btn").addEventListener("click", () => {
  const form = document.querySelector("#actionForm");

  if (!confirm("삭제하시겠습니까?")) {
    return;
  }

  form.submit();
});
