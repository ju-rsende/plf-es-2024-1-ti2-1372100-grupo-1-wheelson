document.addEventListener("DOMContentLoaded", function() {
  const locadorId = getLocadorId();
  if (!locadorId) {
    alert("ID do locador não encontrado!");
    return;
  }

  fetchVehicles();

  function fetchVehicles() {
    fetch(`http://localhost:8080/carro/locador/${locadorId}`)
      .then(response => response.json())
      .then(data => createVehicleCards(data))
      .catch(error => console.error('Error fetching vehicles:', error));
  }

  function createVehicleCards(vehicles) {
    const container = document.getElementById("vehicles-container");
    vehicles.forEach(vehicle => {
      const vehicleCard = document.createElement('div');
      vehicleCard.className = 'col-xs-12 col-sm-6 col-md-6 col-lg-3';
      vehicleCard.innerHTML = `
        <div class="profile-container">
          <div class="info-user">
            <img class="profile-photo" src="${vehicle.image || '/src/front/images/car.png'}" alt="Vehicle Image">
            <div class="name-user">
              <span class="name">${vehicle.model}</span>
              <span class="descrip">${vehicle.location}</span>
            </div>
          </div>
          <div class="bio-user">
            <p class="bio-info">
              Marca: ${vehicle.brand}
              <br>
              Valor diário: R$ ${vehicle.dailyRate}
            </p>
            <div class="buttons-options">
              <div class="row">
                <div class="col-12">
                  <a class="btn" onclick="deleteVehicle(${vehicle.id})">Apagar</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      `;
      container.appendChild(vehicleCard);
    });
  }
});

function getLocadorId() {
  // Suponha que o ID do locador seja armazenado em algum lugar, como no localStorage
  return localStorage.getItem('locadorId');
}

function deleteVehicle(vehicleId) {
  fetch(`http://localhost:8080/carro/${vehicleId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json'
    }
  })
  .then(response => {
    if (response.ok) {
      alert('Veículo apagado com sucesso!');
      location.reload(); // Recarrega a página para atualizar a lista de veículos
    } else {
      alert('Falha ao apagar o veículo.');
    }
  })
  .catch(error => console.error('Error:', error));
}
