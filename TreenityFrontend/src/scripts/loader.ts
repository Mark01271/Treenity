// Add this to your <head> before any other scripts
document.documentElement.style.visibility = 'hidden';

document.addEventListener('DOMContentLoaded', () => {
  const loader = document.querySelector('.page-loader');
  
  window.addEventListener('load', () => {
    document.documentElement.style.visibility = 'visible';
    
    setTimeout(() => {
      loader?.classList.add('fade-out');
      // Clean up loader after fade
      setTimeout(() => {
        loader?.remove();
      }, 500);
    }, 500);
  });
}); 