document.addEventListener('DOMContentLoaded', () => {
  const form = document.querySelector('form');
  
  form?.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const formData = new FormData(e.target as HTMLFormElement);
    
    // Debug: Log all form values
    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });

    const intentText = formData.get('informazioni') as string;

    const data = {
      contactPerson: formData.get('nome'),
      email: formData.get('email'),
      phone: formData.get('phone'),
      groupName: formData.get('nome-comitiva') || 'Individual',
      groupType: formData.get('dropdown'),
      eventIntent: intentText,
      message: intentText,
      additionalRequests: '',
      consentForm: formData.get('terms') === 'on',
      newsletter: formData.get('newsletter') === 'on',
      status: {
        id: 1,
        name: 'received'
      }
    };

    console.log('Raw form data:', Object.fromEntries(formData));
    console.log('Processed data:', data);

    try {
      const response = await fetch('/api/info-requests', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
      });

      if (!response.ok) {
        const errorText = await response.text();
        console.error('Server response:', errorText);
        throw new Error(`Server responded with ${response.status}: ${errorText}`);
      }

      const result = await response.json();
      console.log('Success:', result);
      window.location.href = '/pagina-successo.html';
      
    } catch (error: unknown) {
      console.error('Detailed error:', error);
      const errorMessage = error instanceof Error ? error.message : 'An unknown error occurred';
      alert(`Errore nell'invio della richiesta: ${errorMessage}`);
    }
  });
});
