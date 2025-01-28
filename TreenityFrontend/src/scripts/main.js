//Javascript for navbar

function showMenu() {
  document.querySelector(".navigation").classList.toggle("active");
  document.querySelector(".menu").classList.toggle("hide");
  document.querySelector(".close").classList.toggle("show");
}

//javascript for carousel
bulmaCarousel.attach("#carousel-demo", {
  slidesToScroll: 1,
  slidesToShow: 2,
});
