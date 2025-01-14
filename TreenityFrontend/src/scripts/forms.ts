import '../styles/forms.scss';

document.addEventListener('DOMContentLoaded', () => {
  const form = document.querySelector('form');
  
  form?.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const formData = new FormData(e.target as HTMLFormElement);
    const data = {
      contactPerson: formData.get('nome'),
      email: formData.get('email'),
      phone: formData.get('phone'),
      groupName: formData.get('nome-comitiva'),
      groupType: formData.get('dropdown'),
      date: formData.get('data'),
      timePreference: formData.get('orario'),
      additionalInfo: formData.get('informazioni')
    };

    console.log('Sending data:', data);

    try {
      const response = await fetch('/api/appointment-requests', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
      });

      if (!response.ok) {
        const errorText = await response.text();
        console.error('Server response:', errorText);
        throw new Error('Network response was not ok');
      }

      const result = await response.json();
      console.log('Success:', result);
      alert('Richiesta inviata con successo!');
      
    } catch (error) {
      console.error('Error:', error);
      alert('Errore nell\'invio della richiesta. Per favore riprova.');
    }
  });
});

