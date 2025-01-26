import page from 'page';

function navigateTo(path) {
  page.show(path); // Change the URL
}

// Define your routes
page('/', () => {});
page('/cascina-giovane.it/home', () => {});
page('/cascina-giovane.it/chi-siamo', () => {});
// page('chi-siamo', () => {});
// Start the router
page();

export { navigateTo };