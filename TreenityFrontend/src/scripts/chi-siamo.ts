import 'bulma/css/bulma.css';
import '/src/styles/about.scss';


const burger = document.querySelector('.navbar-burger');
  const nav=document.querySelector('.navbar');
  const menu = document.querySelector('.navbar-menu');
  burger?.addEventListener('click', () => {
    console.log('n');
    burger.classList.toggle('is-active');
      menu?.prepend(burger);
      // burger.classList='navbar-burger is-active';
      // (burger as HTMLElement).style = ' position: relative; z-index:100;';
    menu?.classList.toggle('is-active'); 
    nav?.appendChild(burger)
    // if (menu?.classList.contains('is-active')) {
    //   console.log('n');
    // }
  });
