function ouvrirCours() {
    const niveau = document.getElementById("niveauSelect").value;
    const matiere = document.getElementById("matiereSelect").value;

    if (!niveau || !matiere) {
        alert("⚠️ Choisis niveau et matière !");
        return;
    }

    // Générer la page cours automatiquement (ex : cours_math_4s.html)
    const page = `cours_${matiere}_${niveau}.html`;

    window.location.href = page;
}
