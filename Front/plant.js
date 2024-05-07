document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const plantId = urlParams.get('plantId');
    fetchPlantData(plantId);
    setupHomeButtonListener();
});

function setupHomeButtonListener() {
    const homeBtn = document.getElementById('homeBtn');
    homeBtn.addEventListener('click', function() {
        window.location.href = 'index.html';
    });
}

function fetchPlantData(plantId) {
    const apiUrl = `http://115.86.165.101:9090/plant/all`;
    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            const plantData = data.find(plant => plant.plantId.toString() === plantId);
            if (plantData) {
                displayPlantDetails(plantData);
            } else {
                console.error('No data found for plant ID:', plantId);
            }
        })
        .catch(error => {
            console.error('Error fetching plants data:', error);
        });
}

function displayPlantDetails(data) {
    document.querySelector('h1').textContent = data.plantName;
    //document.querySelector('.plant img').src = data.image;
    document.querySelector('.plant p').textContent = `${data.date}일 차`;
}

function setupEditModalListeners() {
    const settingsBtn = document.getElementById('settingsBtn');
    const modal = document.getElementById('editModal');
    const closeBtn = document.querySelector('.close');
    const form = document.getElementById('editPlantForm');

    settingsBtn.addEventListener('click', () => {
        const plantId = new URLSearchParams(window.location.search).get('plantId');
        fetchPlantDataForEditing(plantId);
    });

    closeBtn.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    window.onclick = event => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    };

    form.addEventListener('submit', event => {
        event.preventDefault();
        submitPlantInfo();
    });
}

function fetchPlantDataForEditing(plantId) {
    const apiUrl = `http://115.86.165.101:9090/plant/all`;
    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            const plantData = data.find(p => p.plantId.toString() === plantId);
            if (plantData) {
                document.getElementById('plantName').value = plantData.plantName;
                const dateForInput = new Date();
                dateForInput.setDate(dateForInput.getDate() - plantData.date);
                document.getElementById('plantDate').value = dateForInput.toISOString().substring(0, 10);
                document.getElementById('editModal').style.display = 'block';
            }
        })
        .catch(error => console.error('Error fetching plant data:', error));
}

function submitPlantInfo() {
    const plantId = new URLSearchParams(window.location.search).get('plantId');
    const updatedName = document.getElementById('plantName').value;
    const updatedDate = document.getElementById('plantDate').value;
    updatePlantInfo(plantId, updatedName, updatedDate);
}

function updatePlantInfo(plantId, plantName, plantDate) {
    const apiUrl = `http://115.86.165.101:9090/plant/information-patch`;
    const data = { id: plantId, name: plantName, date: plantDate };

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
        location.reload(); // 성공 후 페이지를 새로고침
    })
    .catch(error => {
        console.error('Failed to update plant info:', error);
    });
}

setupEditModalListeners();
