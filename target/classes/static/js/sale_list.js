// 날짜 포맷 변경
const format_Date = (data) => {
  const date = new Date(data);
  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    String(date.getHours()).padStart(2, "0") +
    ":" +
    String(date.getMinutes()).padStart(2, "0")
  );
};

console.log("ㅗㅑ: ", bidEndDate);

function showEndBidDate() {
  const Dates = document.querySelectorAll("#bidDate");
  const bid_end_date = document.querySelectorAll(".bid_end_date");
  Dates.forEach((dates, index) => {
    console.log("before date : ", dates.value);
    let bid_date = format_Date(dates.value);
    console.log("date : ", bid_date);

    bid_end_date[index].innerText = bid_date;
  });
}

showEndBidDate();
