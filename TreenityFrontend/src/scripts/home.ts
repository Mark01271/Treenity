//Import home.scss file
import "/src/styles/home.scss";

import bulmaCarousel from "bulma-carousel/dist/js/bulma-carousel.min.js";

const carouselOptions = {
  slidesToScroll: 1,
  slidesToShow: 1,
  pagination: true,
  navigationKeys: true,
  loop: true
};

document.addEventListener("DOMContentLoaded", () => {
  // Initialize carousel
  const carousels = bulmaCarousel.attach('#carousel-demo', carouselOptions);

  // Optional: For debugging
  console.log('Carosello funziona:', carousels);
});

document.addEventListener("DOMContentLoaded", () => {
  // @ts-ignore
  const carousels = bulmaCarousel.attach("#carousel-demo", carouselOptions);
});

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
