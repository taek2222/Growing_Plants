document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const plantId = urlParams.get('plantId');
    fetchPlantData(plantId);
    fetchPlantState();
    setupHomeButtonListener();
    setupWateringButtonListener(plantId);
    setupWaterRecordButtonListener(plantId);
    updatePlantImage(plantId);
    fetchTodayWateringCount(plantId);

    document.getElementById('sunLightMax').addEventListener('input', function() {
        updateSunlightStatus(this.value);
    });
});

function updatePlantImage(plantId) {
    const imageElement = document.getElementById('plantImage');
    imageElement.src = `icon/${plantId}.jpg`;
}

function setupHomeButtonListener() {
    const homeBtn = document.getElementById('homeBtn');
    homeBtn.addEventListener('click', function() {
        window.location.href = 'index.html';
    });
}


function setupWaterRecordButtonListener(plantId) {
    // "물 공급 기록" 버튼을 고유 클래스로 선택
    const waterRecordBtn = document.querySelector('.water-record-btn');
    waterRecordBtn.addEventListener('click', function() {
        fetchWaterRecords(plantId); // 물 공급 기록 조회 함수 호출
    });
}

function setupWateringButtonListener(plantId) {
    // "물주기" 버튼을 고유 클래스로 선택
    const wateringButton = document.querySelector('.water-supply-btn');
    wateringButton.addEventListener('click', function() {
        waterPlant(plantId); // 물주기 함수 호출
    });
}


function waterPlant(plantId) {
    const waterUrl = `http://15.165.56.144/plant/water/${plantId}`;
    fetch(waterUrl, { method: 'GET' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(data => {
            alert(`${data}`);
        })
        .catch(error => {
            console.error('Error when watering plant:', error);
            alert('Error when watering plant: ' + error.message);
        });
}

function fetchPlantState() {
    const apiUrl = `http://15.165.56.144/plant/state`;
    fetch(apiUrl)
        .then(response => response.json())
        .then(state => {
            displayPlantState(state);
        })
        .catch(error => {
            console.error('Error fetching plant state:', error);
        });
}


function fetchPlantData(plantId) {
    const allApiUrl = `http://15.165.56.144/plant/all`;
    const infoApiUrl = `http://15.165.56.144/plant/information-get/${plantId}`;
    
    fetch(allApiUrl)
        .then(response => response.json())
        .then(allData => {
            const plantData = allData.find(plant => plant.plantId.toString() === plantId);
            if (plantData) {
                fetch(infoApiUrl)
                    .then(response => response.json())
                    .then(infoData => {
                        const plantSettings = infoData.plantSetting;
                        const plantInfoSettings = infoData.plantInfoSetting;
                        const plantThresholdSettings = infoData.plantThresholdSetting;

                        document.querySelector('h1').textContent = plantSettings.plantName;
                        document.querySelector('.plant p').textContent = `${plantInfoSettings.date}일 차`;

                        const sunLightMax = plantThresholdSettings.sunLightMax; // sunLightMax 값 설정

                        // 모든 데이터를 함께 displayPlantDetails로 전달
                        displayPlantDetails(plantData, sunLightMax, plantInfoSettings);
                    })
                    .catch(error => console.error('Error fetching plant information:', error));
            } else {
                console.error('No data found for plant ID:', plantId);
            }
        })
        .catch(error => console.error('Error fetching all plants data:', error));
}


function displayPlantDetails(data, sunLightMax, plantInfoSettings, plantThresholdSettings) {
    const sunlightDuration = convertMinutesToHoursMinutes(data.sunlightDuration);
    const growLightDuration = convertMinutesToHoursMinutes(data.growLightDuration);

    const sunlightPercentage = calculatePercentage(data.sunlightDuration, sunLightMax * 60); // sunLightMax 값 사용
    const growLightPercentage = calculatePercentage(data.growLightDuration, sunLightMax * 60); // sunLightMax 값 사용

    document.querySelector('.sun-light').style.width = `${sunlightPercentage}%`;
    document.querySelector('.sun-light').textContent = `${sunlightPercentage}%`;

    document.querySelector('.led-light').style.width = `${growLightPercentage}%`;
    document.querySelector('.led-light').textContent = `${growLightPercentage}%`;
    document.querySelector('.time-box').innerHTML = `
        <span>햇빛: ${sunlightDuration.hours}H ${sunlightDuration.minutes}M</span><br>
        <span>전등: ${growLightDuration.hours}H ${growLightDuration.minutes}M</span>
    `;

    // 기존 기능 유지 - 데이터 확인 후 수정
    document.querySelector('.light-sensor').textContent = `${plantThresholdSettings.lightThreshold}%`;
    document.querySelector('.soil-sensor').textContent = `${data.soilThreshold}%`;
    document.querySelector('.water-sensor').textContent = `${data.waterThreshold}%`;
    document.querySelector('.led-status span').textContent = `LED 상태: ${data.lightStatus ? "ON" : "OFF"}`;
}





function displayPlantState(state) {
    document.querySelector('.light-sensor').textContent = `${state.lightIntensity}%`;

    // Retrieve plantId from the URL query parameters
    const plantId = new URLSearchParams(window.location.search).get('plantId');

    // Choose soil moisture data based on plantId
    const soilMoisture = plantId === "1" ? state.soilMoisture1 : state.soilMoisture2;
    document.querySelector('.soil-sensor').textContent = `${soilMoisture}%`;
    document.querySelector('.water-sensor'). textContent = `${state.waterAmount}%`;
    
    // Choose LED status based on plantId
    const ledStatus1 = state.lightStatus1 ? "ON" : "OFF";
    const ledStatus2 = state.lightStatus2 ? "ON" : "OFF";
    const ledStatus = plantId === "1" ? ledStatus1 : ledStatus2;
    document.querySelector('.led-status span').textContent = `LED 상태: ${ledStatus}`;
}


function convertMinutesToHoursMinutes(minutes) {
    const hours = Math.floor(minutes / 60);
    const remainingMinutes = minutes % 60;
    return { hours, minutes: remainingMinutes };
}

function calculatePercentage(duration, total) {
    return Math.min(Math.round((duration / total) * 100), 100);
}

function setupEditModalListeners() {
    const settingsBtn = document.getElementById('settingsBtn');
    const modal = document.getElementById('editModal');
    const closeBtn = document.querySelector('.close');
    const form = document.getElementById('editPlantForm');
    const sunlightRange = document.getElementById('sunLightMax');

    settingsBtn.addEventListener('click', () => {
        const plantId = new URLSearchParams(window.location.search).get('plantId');
        fetchPlantDataForEditing(plantId);
    });

    closeBtn.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    form.addEventListener('submit', event => {
        event.preventDefault();
        submitPlantInfo();
    });

    sunlightRange.addEventListener('input', (event) => {
        updateSunlightValue(event.target.value);
    });
}

function updateSunlightValue(value) {
    document.getElementById('sunlightValue').textContent = `${value} 시간`;
}

function fetchPlantDataForEditing(plantId) {
    const apiUrl = `http://15.165.56.144/plant/information-get/${plantId}`;
    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            const plantSettings = data.plantSetting;
            const plantInfoSettings = data.plantInfoSetting;
            const plantThresholdSettings = data.plantThresholdSetting;

            document.getElementById('plantName').value = plantSettings.plantName;
            const dateForInput = new Date();
            dateForInput.setDate(dateForInput.getDate() - plantInfoSettings.date);
            document.getElementById('plantDate').value = dateForInput.toISOString().substring(0, 10);
            document.getElementById('waterThreshold').value = plantThresholdSettings.waterThreshold;
            document.getElementById('lightThreshold').value = plantThresholdSettings.lightThreshold;
            document.getElementById('soilThreshold').value = plantThresholdSettings.soilThreshold;
            document.getElementById('sunLightMax').value = plantThresholdSettings.sunLightMax;
            document.getElementById('sunlightValue').textContent = `${plantThresholdSettings.sunLightMax} 시간`;
            document.getElementById('editModal').style.display = 'block';
        })
        .catch(error => console.error('Error fetching plant data:', error));
}



function submitPlantInfo() {
    const plantId = new URLSearchParams(window.location.search).get('plantId');
    const updatedName = document.getElementById('plantName').value;
    const updatedDate = document.getElementById('plantDate').value;
    const updatedWaterThreshold = document.getElementById('waterThreshold').value;
    const updatedLightThreshold = document.getElementById('lightThreshold').value;
    const updatedSoilThreshold = document.getElementById('soilThreshold').value;
    const updatedSunLightMax = document.getElementById('sunLightMax').value;

    updatePlantInfo(plantId, updatedName, updatedDate, updatedWaterThreshold, updatedLightThreshold, updatedSoilThreshold, updatedSunLightMax);
}


function updatePlantInfo(plantId, plantName, plantDate, waterThreshold, lightThreshold, soilThreshold, sunLightMax) {
    const apiUrl = `http://15.165.56.144/plant/information-patch/${plantId}`;
    const data = {
        plantName: plantName,
        date: plantDate,
        lightThreshold: parseInt(lightThreshold),
        soilThreshold: parseInt(soilThreshold),
        waterThreshold: parseInt(waterThreshold),
        sunLightMax: parseInt(sunLightMax)
    };

    fetch(apiUrl, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        return response.json();
    })
    .then(() => {
        console.log('Plant info updated successfully');
        document.getElementById('editModal').style.display = 'none';
        location.reload(); // Reload page after successful update
    })
    .catch(error => {
        console.error('Failed to update plant info:', error);
    });
}


setupEditModalListeners();


//물 공급기록
function fetchWaterRecords(plantId) {
    const waterRecordUrl = `http://15.165.56.144/plant/water-supply/${plantId}`;
    fetch(waterRecordUrl)
        .then(response => response.json())
        .then(data => {
            displayWaterRecords(data);
        })
        .catch(error => console.error('Error fetching water records:', error));
}

function displayWaterRecords(records) {
    const modal = document.getElementById('waterRecordModal');
    const modalContent = modal.querySelector('.modal-content');
    let content = `<h2>물 공급 기록</h2>`; // 제목을 변수에 먼저 추가

    records.forEach(record => {
        content += `<p>기록 ID: ${record.listId}, 날짜: ${record.supplyDate}, 시간: ${record.supplyTime}</p>`;
    });

    modalContent.innerHTML = content; // innerHTML을 한 번에 업데이트
    modal.style.display = 'block'; // 모달창 표시

    // 모달창 외부 클릭 시 모달창 닫기
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    };

    // 모달창 닫기 버튼 이벤트
    modal.querySelector('.close').addEventListener('click', function() {
        modal.style.display = 'none';
    });
}

function fetchTodayWateringCount(plantId) {
    const waterRecordUrl = `http://15.165.56.144/plant/water-supply/today/${plantId}`;

    fetch(waterRecordUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('todayWaterCount').textContent = `오늘은 ${data}회 물공급을 했습니다.`; // 오늘의 물 공급 횟수 표시
        })
        .catch(error => {
            console.error('Error fetching today\'s watering count:', error);
            document.getElementById('todayWaterCount').textContent = "";
        });
}