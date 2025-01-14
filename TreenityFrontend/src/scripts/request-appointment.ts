interface AppointmentRequest {
  nome: string;
  email: string;
  phone: string;
  nomeComitiva: string;
  tipoComitiva: string;
  data: string;
  orario: string;
  intento: string;
  richiesteAggiuntive: string;
  acceptTerms: boolean;
  newsletter: boolean;
}

function validateEmail(email: string): boolean {
  return /\S+@\S+\.\S+/.test(email);
}

function validatePhone(phone: string): boolean {
  // Italian phone number format
  const phoneRegex = /^\+39[0-9]{9,10}$/;
  return phoneRegex.test(phone);
}

function validateDate(date: string): boolean {
  const selectedDate = new Date(date);
  const today = new Date();
  return selectedDate >= today;
}

async function handleSubmit(event: Event) {
  event.preventDefault();
  const form = event.target as HTMLFormElement;
  
  // Get form elements
  const formData: AppointmentRequest = {
      nome: (form.querySelector('#nome') as HTMLInputElement).value,
      email: (form.querySelector('#email') as HTMLInputElement).value,
      phone: (form.querySelector('#phone') as HTMLInputElement).value,
      nomeComitiva: (form.querySelector('#nome-comitiva') as HTMLInputElement).value,
      tipoComitiva: (form.querySelector('#dropdown') as HTMLSelectElement).value,
      data: (form.querySelector('#data') as HTMLInputElement).value,
      orario: (form.querySelector('#orario') as HTMLSelectElement).value,
      intento: (form.querySelector('#informazioni') as HTMLTextAreaElement).value,
      richiesteAggiuntive: (form.querySelectorAll('#informazioni')[1] as HTMLTextAreaElement).value,
      acceptTerms: (form.querySelector('input[type="checkbox"]') as HTMLInputElement).checked,
      newsletter: (form.querySelectorAll('input[type="checkbox"]')[1] as HTMLInputElement).checked
  };

  // Validation
  const errors: string[] = [];

  if (!formData.nome.trim()) errors.push("Il nome è obbligatorio");
  if (!validateEmail(formData.email)) errors.push("Email non valida");
  if (!validatePhone(formData.phone)) errors.push("Numero di telefono non valido");
  if (!formData.nomeComitiva.trim()) errors.push("Il nome della comitiva è obbligatorio");
  if (!formData.tipoComitiva) errors.push("Seleziona il tipo di comitiva");
  if (!validateDate(formData.data)) errors.push("La data deve essere futura");
  if (!formData.orario) errors.push("Seleziona un orario");
  if (!formData.intento.trim()) errors.push("L'intento dell'intervento è obbligatorio");
  if (!formData.acceptTerms) errors.push("Devi accettare i termini e le condizioni");

  if (errors.length > 0) {
      alert(errors.join("\n"));
      return;
  }

  try {
      const response = await fetch('/api/appointments', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json',
          },
          body: JSON.stringify(formData)
      });

      if (!response.ok) {
          throw new Error('Errore nell\'invio della richiesta');
      }

      alert('Richiesta inviata con successo!');
      form.reset();
  } catch (error) {
      console.error('Error:', error);
      alert('Si è verificato un errore durante l\'invio della richiesta. Riprova più tardi.');
  }
}

// Add event listener when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
  const form = document.querySelector('form');
  if (form) {
      form.addEventListener('submit', handleSubmit);
  }
});