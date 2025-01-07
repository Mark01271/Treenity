import 'bulma/css/bulma.css'

import '/src/styles/main.scss'

document.addEventListener('DOMContentLoaded', () => {
  const burger = document.querySelector('.navbar-burger');
  const menu = document.querySelector('.navbar-menu');

  if (burger) {
    burger.addEventListener('click', () => {
      burger.classList.toggle('is-active');
      if (menu) {
        menu.classList.toggle('is-active');
      }
    });
  }
});