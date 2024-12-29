import page from 'page';

function navigateTo(path) {
  page.show(path); // Change the URL
}

// Define your routes
page('/', () => {});
page('/cascina-giovane.it/home', () => {});
page('/cascina-giovane.it/about', () => {});

// Start the router
page();

export { navigateTo };