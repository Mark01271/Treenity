import "../styles/forms.scss";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("#infoRequestForm") as HTMLFormElement;

  if (!form) {
    console.error("Form element not found!");
    return;
  }

  console.log("Form found:", form);

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    console.log("Form submitted");

    const formData = new FormData(e.target as HTMLFormElement);

    // Debug: Log all form values
    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });

    const timeMap: { [key: string]: string } = {
      Mattina: "Mattina",
      Pomeriggio: "Pomeriggio",
      Giornata: "Giornata",
    };

    const intentText = formData.get("informazioni") as string;

    const data = {
      contactPerson: formData.get("nome"),
      email: formData.get("email"),
      phone: formData.get("phone"),
      groupName: formData.get("nome-comitiva"),
      groupType: formData.get("dropdown") as string,
      availabilityDate: formData.get("data"),
      availabilityTime: timeMap[formData.get("orario") as string] || "",
      eventIntent: intentText,
      message: intentText,
      additionalRequests: formData.get("informazioni-aggiuntive") || "",
      consentForm: formData.get("terms") === "on",
      newsletter: formData.get("newsletter") === "on",
      status: {
        id: 1,
        name: "received",
      },
    };

    // Validation
    const errors: string[] = [];
    if (!data.contactPerson) errors.push("Il nome è obbligatorio");
    if (!/\S+@\S+\.\S+/.test(data.email as string))
      errors.push("Email non valida");
    if (!/^\+39[0-9]{9,10}$/.test(data.phone as string))
      errors.push("Numero di telefono non valido");
    if (!data.groupType) errors.push("Seleziona il tipo di gruppo");
    if (!data.message) errors.push("Il messaggio è obbligatorio");
    if (!data.consentForm)
      errors.push("Devi accettare i termini e le condizioni");

    if (errors.length > 0) {
      alert(errors.join("\n"));
      return;
    }

    console.log("Raw form data:", Object.fromEntries(formData));
    console.log("Processed data:", data);

    try {
      const response = await fetch("/api/info-requests", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (!response.ok) {
        const errorText = await response.text();
        console.error("Server response:", errorText);
        throw new Error(
          `Server responded with ${response.status}: ${errorText}`
        );
      }

      const result = await response.json();
      console.log("Success:", result);

      // Reset form and redirect on success
      form.reset();
      window.location.href = "/pagina-successo.html";
    } catch (error) {
      console.error("Detailed error:", error);
      const errorMessage =
        error instanceof Error ? error.message : "An unknown error occurred";
      alert(`Errore nell'invio della richiesta: ${errorMessage}`);
    }
  });
});
