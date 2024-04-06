// 알림 아이콘 클릭 시 알림 창 표시/숨김 처리
const notificationIcon = document.querySelector('.notification-icon');
const alertBox = document.getElementById('alert-box');

notificationIcon.addEventListener('click', () => {
  alertBox.style.display = alertBox.style.display === 'none' ? 'block' : 'none';
});

//갤러리 기능
