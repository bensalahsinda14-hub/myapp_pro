// ==============================
// Formulaire contact
// ==============================
const contactForm = document.querySelector('#contactForm');

if (contactForm) {
    contactForm.addEventListener('submit', (e) => {
        e.preventDefault();
        alert('Merci pour votre message !');
        contactForm.reset();
    });
}
