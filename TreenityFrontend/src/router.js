import page from 'page';

function navigateTo(path) {
  page.show(path); // Change the URL
}

// Define your routes
page('/', () => {});
page('/cascina-giovane/home', () => {});
page('/cascina-giovane/about', () => {});

// Start the router
page();

export { navigateTo };