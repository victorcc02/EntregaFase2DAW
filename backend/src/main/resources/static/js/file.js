src="https://cdn.jsdelivr.net/npm/chart.js"
document.addEventListener("DOMContentLoaded", function () {
  console.log("Página cargada, ejecutando scripts...");

  // Función para agregar eventos a botones
  function addNavigationEvent(buttonId, targetPage) {
    var button = document.getElementById(buttonId);
    if (button) {
      button.addEventListener("click", function () {
        window.location.href = targetPage;
      });
    } else {
      console.warn(`Botón '${buttonId}' no encontrado en esta página.`);
    }
  }

  // ✅ Agregamos eventos para los botones necesarios
  addNavigationEvent("newRoutine", "newRoutine.html");
  addNavigationEvent("addDiet", "newDiet.html");

  // ✅ Verificar si el modal de error existe antes de usarlo
  var errorModalElement = document.getElementById("errorModal");
  if (errorModalElement) {
    var errorModal = new bootstrap.Modal(errorModalElement);
    errorModal.show();
    console.log("Modal de error mostrado.");

    errorModalElement.addEventListener("hidden.bs.modal", function () {
      let errorContent = document.getElementById("errorContent");
      let loadingSpinner = document.getElementById("loadingSpinner");
      let errorCard = document.getElementById("errorCard");

      if (errorContent && loadingSpinner && errorCard) {
        errorContent.style.display = "block";

        setTimeout(() => {
          loadingSpinner.style.display = "none";
          errorCard.style.display = "block";
        }, 2000);
      } else {
        console.warn("Elementos de error no encontrados.");
      }
    });
  } else {
    console.warn("Modal 'errorModal' no encontrado en esta página.");
  }

  var buttonShowRoutine = document.querySelectorAll(".goToShowRoutine");
  buttonShowRoutine.forEach(function (button) {
    button.addEventListener("click", function () {
      var trainingId = button.dataset.trainingId; // We get de id from data-attribute

      if (trainingId) {
        window.location.href = "/trainings/" + trainingId; // Send the url to the controller to see the training view
      } else {
        console.warn("ID del entrenamiento no encontrado.");
      }
    });
  });

  var buttonEditRoutine = document.querySelectorAll(".goToEditRoutine");
  buttonEditRoutine.forEach(function (button) {
    button.addEventListener("click", function () {
      var trainingId = button.dataset.trainingId; // We get de id from data-attribute

      if (trainingId) {
        window.location.href = "/trainings/editTraining/" + trainingId; // Send the url to the controller to see the edit training view
      } else {
        console.warn("ID del entrenamiento no encontrado.");
      }
    });
  });
  var buttonDeleteRoutine = document.querySelectorAll(".goToDeleteRoutine");
  buttonDeleteRoutine.forEach(function (button) {
    button.addEventListener("click", function () {
      var trainingId = button.dataset.trainingId; // We get de id from data-attribute

      if (trainingId) {
        let confirmation = confirm('¿Estas seguro de querer eliminar esta rutina?');
        if(confirmation) {
          window.location.href = "/trainings/delete/" + trainingId; // Send the url to the controller to see the edit training view
        }
      } else {
        console.warn("ID del entrenamiento no encontrado.");
      }
    });
  });

  var buttonDeleteCommentRoutine = document.querySelectorAll(".goToDeleteCommentRoutine");
  buttonDeleteCommentRoutine.forEach(function (button) {
    button.addEventListener("click", function () {
      var trainingId = button.dataset.trainingId; // We get de training id from data-attribute
      var commentId = button.dataset.commentId; // We get de training comment id from data-attribute

      if (trainingId) {
        let confirmation = confirm('¿Estas seguro de querer eliminar este comentario de esta rutina?');
        if(confirmation) {
          window.location.href = "/trainingComments/" + trainingId + "/" + commentId +  "/delete"  ; // Send the url to the controller to delete comment training
        }
      } else {
        console.warn("ID del entrenamiento no encontrado.");
      }
    });
  });

  var buttonDeleteCommentDiet = document.querySelectorAll(".goToDeleteCommentDiet");
  buttonDeleteCommentDiet.forEach(function (button) {
    button.addEventListener("click", function () {
      var nutritionId = button.dataset.nutritionId; // We get de nutrition id from data-attribute
      var commentId = button.dataset.commentId; // We get de nutrition comment id from data-attribute

      if (nutritionId) {
        let confirmation = confirm('¿Estas seguro de querer eliminar este comentario de esta dieta?');
        if(confirmation) {
          window.location.href = "/nutritionComments/" + nutritionId + "/" + commentId +  "/delete"  ; // Send the url to the controller to delete comment diet
        }
      } else {
        console.warn("ID de la dieta no encontrado.");
      }
    });
  });

  var buttonSuscribeRoutine = document.querySelectorAll(".goToSubscribeRoutine");
  buttonSuscribeRoutine.forEach(function (button) {
    button.addEventListener("click", function () {
      var trainingId = button.dataset.trainingId; // We get de id from data-attribute

      if (trainingId) {
        let confirmation = confirm('¿Estas seguro de querer suscribirte esta rutina?');
        if(confirmation) {
          window.location.href = "/trainings/subscribe/" + trainingId;
        }
      } else {
        console.warn("ID del entrenamiento no encontrado.");
      }
    });
  });

  var buttonUnsubscribeRoutine = document.querySelectorAll(".goToUnsubscribeRoutine");
  buttonUnsubscribeRoutine.forEach(function (button) {
    button.addEventListener("click", function () {
      var trainingId = button.dataset.trainingId; // We get de id from data-attribute

      if (trainingId) {
        let confirmation = confirm('¿Estas seguro de querer desuscribirte de esta rutina?');
        if(confirmation) {
          window.location.href = "/trainings/unsubscribe/" + trainingId;
        }
      } else {
        console.warn("ID del entrenamiento no encontrado.");
      }
    });
  });

  var buttonDeleteRoutineFromList = document.querySelectorAll(".goToDeleteRoutineFromList");
  buttonDeleteRoutineFromList.forEach(function (button) {
    button.addEventListener("click", function () {
      var trainingId = button.dataset.trainingId; // We get de id from data-attribute

      if (trainingId) {
        let confirmation = confirm('¿Estas seguro de querer desuscribirte de esta rutina?');
        if(confirmation) {
          window.location.href = "/trainings/deleteFromList/" + trainingId;
        }
      } else {
        console.warn("ID del entrenamiento no encontrado.");
      }
    });
  });


  var buttonShowDiet = document.querySelectorAll(".goToShowDiet");
  buttonShowDiet.forEach(function (button) {
    button.addEventListener("click", function () {
      var nutritionId = button.dataset.nutritionId; // We get de id from data-attribute

      if (nutritionId) {
        window.location.href = "/nutritions/" + nutritionId; // Send the url to the controller to see the training view
      } else {
        console.warn("ID de la nutricion no encontrado.");
      }
    });
  });

  var buttonEditDiet = document.querySelectorAll(".goToEditDiet");
  buttonEditDiet.forEach(function (button) {
    button.addEventListener("click", function () {
      var nutritionId = button.dataset.nutritionId; // We get de id from data-attribute

      if (nutritionId) {
        window.location.href = "/nutritions/editNutrition/" + nutritionId; // Send the url to the controller to see the edit training view
      } else {
        console.warn("ID de la nutricion no encontrado.");
      }
    });
  });
  var buttonDeleteDiet = document.querySelectorAll(".goToDeleteDiet");
  buttonDeleteDiet.forEach(function (button) {
    button.addEventListener("click", function () {
      var nutritionId = button.dataset.nutritionId; // We get de id from data-attribute

      if (nutritionId) {
        let confirmation = confirm('¿Estas seguro de querer eliminar esta dieta?');
        if(confirmation) {
          window.location.href = "/nutritions/delete/" + nutritionId; // Send the url to the controller to see the edit training view
        }
      } else {
        console.warn("ID de la nutricion no encontrado.");
      }
    });
  });

  var buttonSuscribeDiet = document.querySelectorAll(".goToSubscribeDiet");
  buttonSuscribeDiet.forEach(function (button) {
    button.addEventListener("click", function () {
      var nutritionId = button.dataset.nutritionId; // We get de id from data-attribute

      if (nutritionId) {
        let confirmation = confirm('¿Estas seguro de querer suscribirte a esta dieta?');
        if(confirmation) {
          window.location.href = "/nutritions/subscribe/" + nutritionId;
        }
      } else {
        console.warn("ID de la nutricion no encontrado.");
      }
    });
  });

  var buttonUnsubscribeDiet = document.querySelectorAll(".goToUnsubscribeDiet");
  buttonUnsubscribeDiet.forEach(function (button) {
    button.addEventListener("click", function () {
      var nutritionId = button.dataset.nutritionId; // We get de id from data-attribute

      if (nutritionId) {
        let confirmation = confirm('¿Estas seguro de querer desuscribirte de esta dieta?');
        if(confirmation) {
          window.location.href = "/nutritions/unsubscribe/" + nutritionId;
        }
      } else {
        console.warn("ID de la nutricion no encontrado.");
      }
    });
  });

  var buttonDeleteDietFromList = document.querySelectorAll(".goToDeleteDietFromList");
  buttonDeleteDietFromList.forEach(function (button) {
    button.addEventListener("click", function () {
      var nutritionId = button.dataset.nutritionId; // We get de id from data-attribute

      if (nutritionId) {
        let confirmation = confirm('¿Estas seguro de querer desuscribirte de esta dieta?');
        if(confirmation) {
          window.location.href = "/nutritions/deleteFromList/" + nutritionId;
        }
      } else {
        console.warn("ID de la nutricion no encontrado.");
      }
    });
  });

});

document.addEventListener("DOMContentLoaded", function () {
  const params = new URLSearchParams(window.location.search);
  if (params.get("error")) {
    alert("Incorrect email or password. Please, try again.");

    // Remueve el parámetro `error` de la URL sin recargar
    window.history.replaceState({}, document.title, window.location.pathname);

    // Forzar una recarga para que el login se renderice bien
    setTimeout(() => {
      window.location.reload();
    } );
  }
});



function validateRoutineForm(){

  let name = document.forms["editForm"]["name"].value;
  console.log(name)
  if(name === ""){
    alert("Invalid routine name length (empty name).");
    return false;
  }
  if(name.length > 30){
    alert("Invalid routine name length (too long).");
    return false;
  }
  let duration = document.forms["editForm"]["duration"].value;
  if(duration < 1){
    alert("Invalid duration. Remember that duration is measured in numbers (minutes).");
    return false;
  }
  if(duration > 200){
    alert("You should not exceed 6 hours of training, as it may be harmful to your health.");
    return false;
  }
  let description = document.forms["editForm"]["description"].value;
  if(description.length < 5){
    alert("Incorrect length, too short. Please fill in this field.");
    return false;
  }
  if(description.length > 255){
    alert("Exceeded length: 255 characters maximum.");
    return false;
  }
  return true;
}

function validateNutritionForm(){

  let name = document.forms["editForm"]["name"].value;
  console.log(name)
  if(name === ""){
    alert("Invalid diet name length (empty name).");
    return false;
  }
  if(name.length > 30){
    alert("Invalid diet name length (too long).");
    return false;
  }
  let calories = document.forms["editForm"]["calories"].value;
  if(calories < 50 ){
    alert("Invalid calories. Calories must be more than 50 for health reasons.");
    return false;
  }
  if(calories > 2500){
    alert("You should not exceed 2500 calories, as it may be harmful to your health.");
    return false;
  }
  let description = document.forms["editForm"]["description"].value;
  if(description.length < 5){
    alert("Incorrect length, too short. Please fill in this field.");
    return false;
  }
  if(description.length > 255){
    alert("Exceeded length: 255 characters maximum.");
    return false;
  }
  return true;
}
//edit image
function previewSelectedImage(event) {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function(e) {
      document.getElementById("previewImage").src = e.target.result;
    };
    reader.readAsDataURL(file);
  }
}


function redirectFromComments() {
  let pathSegments = window.location.pathname.split("/"); 
  let lastSegment = pathSegments.pop(); 
  let firstSegment = pathSegments.pop().replace("Comments", "s"); 
  window.location.href = `/${firstSegment}/${lastSegment}`;
}

function redirectFromNewComments(event, preventer) {
  event.preventDefault();
  let pathSegments = window.location.pathname.split("/"); 
  pathSegments.pop();
  let newUrl = pathSegments.join("/");
  if (preventer){
    window.location.href = newUrl;
  } else {
    let form = document.getElementById("commentForm");
    form.action = newUrl;
    form.submit();
  }
  
}

function redirectFromEditComments(event, preventer) {
  event.preventDefault();
  let pathSegments = window.location.pathname.split("/"); 
  pathSegments.pop();
  if (preventer){
    pathSegments.pop();
    let newUrl = pathSegments.join("/");
    window.location.href = newUrl;
  } else {
    let newUrl = pathSegments.join("/");
    let form = document.getElementById("commentForm");
    form.action = newUrl;
    form.submit();
  }
}

document.addEventListener("DOMContentLoaded", function() {
  const chartElement = document.getElementById("reportGraphicChart");
  if (chartElement) { // Solo ejecuta si está en la página correcta
      const reportedCount = parseInt(chartElement.dataset.reported, 10) || 0;
      const nonReportedCount = parseInt(chartElement.dataset.notReported, 10) || 0;
      renderReportChart(reportedCount, nonReportedCount);
  }
});

function renderReportChart(reportedCount, nonReportedCount) {
  const ctx = document.getElementById("reportGraphicChart").getContext("2d");
  new Chart(ctx, {
      type: "pie",
      data: {
          labels: ["Reportados", "No Reportados"],
          datasets: [{
              data: [reportedCount, nonReportedCount],
              backgroundColor: ["red", "blue"]
          }]
      }
  });
}

//AJAX METHOD
let currentPage = 0;
const commentsPerPage = 10; 

document.addEventListener("DOMContentLoaded", function () {
  const chartElement = document.getElementById("ajaxPagination");
  if (chartElement) {
    let allComments = document.querySelectorAll("#resultsContainer .col-12.col-md-4.mb-4");
    allComments.forEach((comment, index) => {
      if (index >= commentsPerPage) {
        comment.style.display = "none"; // Hide extra comments at start
      }
    });
  }
});

function loadMoreComments() {
  let loadMoreButton = document.getElementById("loadMore");
  let resultsContainer = document.getElementById("resultsContainer");

  let spinner = document.createElement("div");
  spinner.className = "spinner-border text-primary d-block mx-auto my-3";
  spinner.role = "status";
  spinner.innerHTML = '<span class="visually-hidden">Loading...</span>';
  resultsContainer.appendChild(spinner);

  loadMoreButton.disabled = true;
  let requests = [];

  let xhr = new XMLHttpRequest();
  let nutritionIdElement = document.getElementById("nutritionId");
  let trainingIdElement = document.getElementById("trainingId");

  let nutritionId = nutritionIdElement ? nutritionIdElement.value : null;
  let trainingId = trainingIdElement ? trainingIdElement.value : null;

  if (nutritionId) {
    xhr.open("GET",`/nutritionComments/${nutritionId}/moreComments?page=${currentPage + 1}`,true);
  }else if (trainingId) {
    xhr.open("GET",`/trainingComments/${trainingId}/moreComments?page=${currentPage + 1}`,true);
  }

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        let responseHTML = xhr.responseText;

        if (responseHTML.trim().length > 0) {
          resultsContainer.insertAdjacentHTML("beforeend", responseHTML);
          currentPage++;
        } else {
          loadMoreButton.style.display = "none"; // Hide the button if there aren't more comments
        }
      } else {
        console.error("Error al cargar más comentarios");
      }

      // Hide the spinner and enable the button
      spinner.remove();
      loadMoreButton.disabled = false;
    }
  };

  xhr.send();

}

// LOAD MORE CARDS
let currentPageCards = 1;
const cardsPerPage = 10;

document.addEventListener("DOMContentLoaded", function () {
  const ajaxContainer = document.getElementById("ajaxCards"); // ID of the container where the cards are
  if (ajaxContainer) {
    let allCards = document.querySelectorAll("#resultsContainer .col-12.col-md-4.mb-4");
    allCards.forEach((card, index) => {
      if (index >= cardsPerPage) {
        card.style.display = "none"; // Hide extra cards at start
      }
    });
  }
});

function loadMoreCards() {
  let loadMoreButton = document.getElementById("loadMore");
  let resultsContainer = document.getElementById("resultsContainer");

  let spinner = document.createElement("div");
  spinner.className = "spinner-border text-primary d-block mx-auto my-3";
  spinner.role = "status";
  spinner.innerHTML = '<span class="visually-hidden">Loading...</span>';
  resultsContainer.appendChild(spinner);

  loadMoreButton.disabled = true;

  let xhr = new XMLHttpRequest();

  // Find out if we are in the diets or trainings page
  let isDietsPage = window.location.pathname.includes("nutrition");
  let isTrainingsPage = window.location.pathname.includes("training");

  let url = "";
  if (isDietsPage) {
    url = `/nutritions/moreDiets?page=${currentPageCards + 1}`;
  } else if (isTrainingsPage) {
    url = `/trainings/moreRoutines?page=${currentPageCards + 1}`;
  } else {
    console.error("No se pudo determinar la URL de carga.");
    spinner.remove();
    loadMoreButton.disabled = false;
    return;
  }

  xhr.open("GET", url, true);

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        let responseHTML = xhr.responseText;

        if (responseHTML.trim().length > 0) {
          resultsContainer.insertAdjacentHTML("beforeend", responseHTML);
          currentPageCards++;
        } else {
          loadMoreButton.disabled = true;
          loadMoreButton.innerText = "No more items available";
          loadMoreButton.classList.add("disabled");
        }
      } else {
        console.error("Error al cargar más elementos");
      }

      spinner.remove();
      loadMoreButton.disabled = false;
    }
  };

  xhr.send();
}


