// 알림 아이콘 클릭 시 알림 창 표시/숨김 처리
const notificationIcon = document.querySelector('.notification-icon');
const alertBox = document.getElementById('alert-box');

notificationIcon.addEventListener('click', (event) => {
  alertBox.style.display = alertBox.style.display === 'none' ? 'block' : 'none';
  event.stopPropagation(); // 이벤트 버블링을 막음
});

// 문서 전체에 클릭 이벤트 리스너를 추가하여 알림 창 외의 곳을 클릭 시 알림 창을 숨김
document.addEventListener('click', (event) => {
  if (event.target !== alertBox && event.target !== notificationIcon) {
    alertBox.style.display = 'none';
  }
});

//엔드포인트
const url = "http://115.86.165.101:9090/plant/all";
// fetch 요청
fetch(url)
  .then(response => {
    // 요청이 성공적으로 완료되었는지 확인
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    // JSON 형태로 응답 파싱
    return response.json();
  })
  .then(data => {
    // 데이터에서 plantID와 plantName 추출
    data.forEach(plant => {
      console.log(`Plant ID: ${plant.plantID}, Plant Name: ${plant.plantName}`);
    });
    const plantTitles = document.querySelectorAll('.plant h2');
    if(data.length >= 2) {
      plantTitles[0].textContent = data[0].plantName; // 첫 번째 식물의 이름 설정
      plantTitles[1].textContent = data[1].plantName; // 두 번째 식물의 이름 설정
    }

  })
  .catch(error => {
    // 오류 처리
    console.error('There was a problem with the fetch operation:', error);
  });
