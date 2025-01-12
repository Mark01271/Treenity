//Import home.scss file
import "/src/styles/home.scss";

import bulmaCarousel from "bulma-carousel/dist/js/bulma-carousel.min.js";

const carouselOptions = {
  slidesToScroll: 1,
  slidesToShow: 1,
  pagination: true,
  navigationKeys: true,
  loop: true,
  breakpoints: [
    { changePoint: 480, slidesToShow: 1, slidesToScroll: 1 },
    { changePoint: 768, slidesToShow: 1, slidesToScroll: 1 },
    { changePoint: 1024, slidesToShow: 1, slidesToScroll: 1 },
    { changePoint: 1280, slidesToShow: 1, slidesToScroll: 1 }
  ]
};

document.addEventListener("DOMContentLoaded", () => {
  // Initialize carousel
  const carousels = bulmaCarousel.attach("#carousel-demo", carouselOptions);

  // Optional: For debugging
  console.log("Carosello funziona:", carousels);
});

document.addEventListener("DOMContentLoaded", () => {

  const carousels = bulmaCarousel.attach("#carousel-demo", carouselOptions);
});