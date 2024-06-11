document.addEventListener('DOMContentLoaded', function() {
  fetchPlantData();
  fetchWeatherData();
  fetchState();
  setInterval(fetchState, 1000);
  initializeNotificationModal();
});

function fetchPlantData() {
  const plantUrl = "http://15.165.56.144/plant/all";

  fetch(plantUrl)
      .then(response => response.json())
      .then(data => {
          let hasNewAlarm = false;

          data.forEach((item, index) => {
              const plantElement = document.getElementById(`plant${index + 1}`);
              if (plantElement) {
                  plantElement.querySelector('h2').textContent = item.plantName;
                  plantElement.querySelector('p').textContent = `${item.date}일 차`;

                  if (item.newAlarm) {
                      hasNewAlarm = true;
                  }
              }
          });

          // newAlarm 값이 true인 경우 빨간색 점 표시
          if (hasNewAlarm) {
              document.getElementById('notification-dot').style.display = 'block';
          } else {
              document.getElementById('notification-dot').style.display = 'none';
          }
      })
      .catch(error => console.error('Failed to fetch plant data:', error));
}


//날씨정보
function fetchWeatherData() {
  const weatherUrl = "http://15.165.56.144/weather";

  fetch(weatherUrl)
      .then(response => response.json())
      .then(data => {

        //날짜 정보 포맷
        const formattedDate = `${data.month}/${data.day} ${data.time}:00 기준`;

        // 날짜 정보 업데이트
        document.getElementById('date').textContent = formattedDate;

          //최대/최소 온도 업데이트
          document.getElementById('max-temp').textContent = `${data.maxTemp}°`;
          document.getElementById('min-temp').textContent = `${data.minTemp}°`;
          document.getElementById('rain').textContent = `${data.rain}%`;
          updateWeatherIcon(data.weatherCode);

          // 현재 온도 업데이트
          const currentTempElements = document.querySelectorAll('.current-temperature .temperature-value');
          currentTempElements.forEach(element => {
              element.textContent = `${data.currentTemp}°C`;
          });

          // 현재 습도 업데이트
          const humidity = data.humidity;
            document.querySelector('.current-humidity .humidity-value').textContent = `${humidity}%`;
          
      })
      .catch(error => console.error('Failed to fetch weather data:', error));
}
//센서 요청
function fetchState() {
  const stateUrl = "http://15.165.56.144/plant/state";
  
  fetch(stateUrl)
    .then(response => response.json())
    .then(data => {
        // 센서 온도 업데이트
        const atmosphereTempElements = document.querySelectorAll('.atmosphere-temperature .temperature-value');
        atmosphereTempElements.forEach(element => {
        element.textContent = `${data.airTemperature}°C`; //이 부분만 수정
        });

        // 센서 습도 업데이트
        const planthumidity = data.airHumidity; //이 부분만 수정
        document.querySelector('.atmosphere-humidity .plant-humidity-value').textContent = `${planthumidity}%`;

    })
    .catch(error => console.error('Failed to fetch state data:', error));
}


function updateWeatherIcon(weatherCode) {
  const weatherIcon = document.getElementById('weatherIcon');
  switch (weatherCode) {
    case 1:
      weatherIcon.src = 'icon/맑음.png'; 
      break;
    case 2:
      weatherIcon.src = 'icon/구름많음.png'; 
      break;
    case 3:
      weatherIcon.src = 'icon/흐림.png'; 
      break;
    case 4:
      weatherIcon.src = 'icon/비.png'; 
      break;
    case 5:
      weatherIcon.src = 'icon/비눈.png'; 
      break;
    case 6:
      weatherIcon.src = 'icon/눈.png';
      break;
    case 7:
      weatherIcon.src = 'icon/소나기.png';
      break;
    default:
      weatherIcon.src = 'icon/맑음.png';
  }
}


//일기예보 기능 테스트

function getWeatherIcon(weatherCode) {
  switch (weatherCode) {
      case 1: return 'icon/맑음.png';
      case 2: return 'icon/구름많음.png';
      case 3: return 'icon/흐림.png';
      case 4: return 'icon/비.png';
      case 5: return 'icon/비눈.png';
      case 6: return 'icon/눈.png';
      case 7: return 'icon/소나기.png';
      default: return 'icon/맑음.png'; // 기본 아이콘
  }
}

document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('weatherIcon').addEventListener('click', function() {
    fetch('http://15.165.56.144/weather/all')
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById('weatherForecast');
            if (!container) {
                console.error('The container element does not exist!');
                return;
            }
            container.innerHTML = ''; // Clear previous content
            data.forEach(day => {
                const iconPath = getWeatherIcon(day.weatherCode);
                const content = `
                    <div class="forecast-item">
                        <div class="forecast-date">${day.month}/${day.day}</div>
                        <div class="weather-info">
                            <img src="${iconPath}" class="weather-icon-modal" alt="Weather Icon">
                            <div class="weather-details">
                                <div class="temp-details">
                                    <div class="weather-detail">최고 기온: ${day.maxTemp}°C</div>
                                    <div class="weather-detail">최저 기온: ${day.minTemp}°C</div>
                                </div>
                                <div class="humidity-details">
                                    <div class="weather-detail">습&emsp;&emsp;도: ${day.humidity}%</div>
                                    <div class="weather-detail">강수확률: ${day.rain}%</div>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
                container.innerHTML += content;
            });
            document.getElementById('weatherModal').style.display = 'block';
        })
        .catch(error => console.error('Failed to fetch weather data:', error));
});



  document.getElementsByClassName('close-weather')[0].addEventListener('click', function() {
      document.getElementById('weatherModal').style.display = 'none';
  });

  document.querySelector('.close-notification').addEventListener('click', function() {
       document.querySelector('.notification-modal').style.display = 'none';
  });

  window.onclick = function(event) {
      if (event.target == document.getElementById('weatherModal')) {
          document.getElementById('weatherModal').style.display = 'none';
      }
  };
});

//알림

function initializeNotificationModal() {
  const notificationIcon = document.querySelector('.notification-icon');
  const notificationModal = document.getElementById('notificationModal');
  const closeBtn = notificationModal.querySelector('.close-notification');
  const deleteAllBtn = document.getElementById('deleteAllNotifications');

  notificationIcon.addEventListener('click', function() {
      fetch('http://15.165.56.144/alarm')
          .then(response => response.json())
          .then(data => {
              const notificationList = document.getElementById('notificationList');
              notificationList.innerHTML = ''; // 기존 알림 목록 초기화

              // 알림 목록을 역순으로 표시
              data.reverse().forEach(notification => {
                  const item = document.createElement('div');
                  item.className = 'notification-item'; // 클래스 추가
                  item.classList.add(notification.readFlag ? 'read' : 'unread');
                  item.innerHTML = `
                      <span class="close-notification-item" data-id="${notification.id}">&times;</span>
                      <strong>${notification.title} <span class="${notification.readFlag ? 'read' : 'unread'}">${notification.readFlag ? '읽음' : 'NEW'}</span></strong>
                      <p>${notification.contents}</p>
                      <span class="date-time">${notification.alarmDate} ${notification.alarmTime}</span>`;
                  notificationList.appendChild(item);
              });

              // 각 X 버튼에 이벤트 리스너 추가
              document.querySelectorAll('.close-notification-item').forEach(button => {
                  button.addEventListener('click', function() {
                      const alarmId = this.getAttribute('data-id');
                      deleteAlarm(alarmId, this);
                  });
              });

              notificationModal.style.display = 'block';
          })
          .catch(error => console.error('Error fetching notifications:', error));
  });

  closeBtn.addEventListener('click', function() {
      notificationModal.style.display = 'none';
      location.reload(); // 화면 새로고침
  });

  deleteAllBtn.addEventListener('click', function() {
      deleteAllNotifications();
  });

  window.onclick = function(event) {
      if (event.target === notificationModal) {
          notificationModal.style.display = 'none';
          location.reload(); // 화면 새로고침
      }
  };
}

function deleteAlarm(alarmId, element) {
  fetch(`http://15.165.56.144/alarm/${alarmId}`, {
    method: 'DELETE'
  })
  .then(response => {
    if (response.ok) {
      element.parentElement.remove();
      alert('알림이 삭제되었습니다');
    } else {
      console.error('Failed to delete alarm');
    }
  })
  .catch(error => console.error('Error:', error));
}

function deleteAllNotifications() {
  fetch('http://15.165.56.144/alarm/all', {
    method: 'DELETE'
  })
  .then(response => {
    if (response.ok) {
      const notificationList = document.getElementById('notificationList');
      notificationList.innerHTML = ''; // Clear all notifications
      alert('모든 알림이 삭제되었습니다');
    } else {
      console.error('Failed to delete all notifications');
    }
  })
  .catch(error => console.error('Error:', error));
}
//ai 추천
document.addEventListener('DOMContentLoaded', function() {
  var modal = document.getElementById('aiRecommendationModal');
  var btn = document.querySelector('.ai-recommendation');
  var span = document.getElementsByClassName('close')[0];

  btn.onclick = function() {
      modal.style.display = "block";
  }

  span.onclick = function() {
      modal.style.display = "none";
  }

  window.onclick = function(event) {
      if (event.target == modal) {
          modal.style.display = "none";
      }
  }

  document.getElementById('aiRecommendationForm').addEventListener('submit', function(e) {
      e.preventDefault();
      var plantName = document.getElementById('plantNameInput').value;
      fetch(`http://15.165.56.144/bot/chat/${encodeURIComponent(plantName)}`)
          .then(response => response.json())
          .then(data => {
              document.getElementById('aiRecommendationResult').innerHTML = formatAIResponse(data.message);
          })
          .catch(error => {
              console.error('Error:', error);
              document.getElementById('aiRecommendationResult').innerHTML = '추천을 가져오는 데 실패했습니다.';
          });
  });
});

function formatAIResponse(message) {
  return message.replace(/\n/g, '<br>');
}
