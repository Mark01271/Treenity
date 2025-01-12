//Import home.scss file
import "/src/styles/home.scss";

//Import home.scss file
import "/src/styles/home.scss";

import "bulma-carousel/dist/js/bulma-carousel.min.js";

// Initialize all elements with carousel class.
const carousels = bulmaCarousel.attach(".carousel", options);

// To access to bulmaCarousel instance of an element
const element = document.querySelector("#my-element");
if (element && element.bulmaCarousel) {
  // bulmaCarousel instance is available as element.bulmaCarousel
}

document.addEventListener("DOMContentLoaded", () => {
  // Example: Fetching data from the backend API
  fetch("http://localhost:8080/api/your-endpoint")
    .then((response) => response.json())
    .then((data) => {
      console.log("Data from backend:", data);
      // Update the DOM with the fetched data
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });

  // Example: Adding an event listener to a button
  const button = document.getElementById("exampleButton");
  if (button) {
    button.addEventListener("click", () => {
      alert("Button clicked!");
    });
  }
});
