$(document).ready(function () {
	$("#clientName").autocomplete({
		source: function (request, response) {
			$.ajax({
				url: '/comptes/search',
				data: {
					query: request.term
				},
				success: function (data) {
					response(data);
				}
			});
		},
		minLength: 1,
		select: function (event, ui) {
			console.log("Client selected: " + ui.item.value);
		}
	});
});

function deleteCompte(rib, e) {
	e.preventDefault();


	Swal.fire({
		title: "Êtes-vous sûr ?",
		text: "Vous ne pourrez pas annuler cette action !",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Oui, supprimez-le !",
		cancelButtonText: "Non, annuler !",
		reverseButtons: true
	}).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url: "/comptes/delete-ajax",
				type: "POST",
				data: { rib: rib },
				success: function () {
					$("#row-" + rib).remove();
					Swal.fire({
						title: "Supprimé !",
						text: "Le Compte a été supprimé.",
						icon: "success"
					});
				},
				error: function () {
					Swal.fire({
						title: "Erreur !",
						text: "Une erreur est survenue. Veuillez réessayer.",
						icon: "error"
					});
				}
			});
		} else if (result.dismiss === Swal.DismissReason.cancel) {
			Swal.fire({
				title: "Annulé",
				text: "Compte non supprimé",
				icon: "error"
			});
		}
	});
}

function saveCompte() {
	const notyf = new Notyf({
		duration: 5000,
		position: {
			x: 'right',
			y: 'top',
		}
	});

	const rib = $("#rib").val();
	const nomClient = $("#clientName").val();
	const solde = $("#solde").val();

	// Check that all required fields are filled
	if (!rib || !nomClient || !solde) {
		notyf.error("Les champs doivent être remplis!");
		return;
	}

	// Proceed with AJAX request to save the account
	$.ajax({
		url: "/comptes/save", // Backend endpoint
		type: "POST",
		data: {
			rib: rib,
			nomClient: nomClient,
			solde: solde
		},
		success: function () {
				Swal.fire({
					title: "Sauvegardé !",
					text: "Compte sauvgardé avec succés !!",  // Display the success message from backend
					icon: "success",
					confirmButtonText: "OK",
					customClass: {
						confirmButton: "btn btn-success"
					}
				}).then(() => {
					window.location.href = "/comptes/"; // Redirect after saving
				});
			},
		error: function (xhr) {
			// Handle specific error codes
			if (xhr.status == 404) {
				Swal.fire({
					title: "Erreur !",
					text: "Il n'y a pas de compte avec ce client !!",
					icon: "error"
				});
			} else if (xhr.status == 400) {
				Swal.fire({
					title: "Erreur !",
					text: "RIB Existe deja !!",
					icon: "error"
				});
			} else {
				// Generic error handler for unexpected cases
				Swal.fire({
					title: "Erreur !",
					text: "Une erreur est survenue. Veuillez réessayer plus tard.",
					icon: "error"
				});
			}
		}

	});
}


function showEditAlert(rib) {
	Swal.fire({
		title: 'Êtes-vous sûr ?',
		text: "Modifier le compte avec le RIB : " + rib + " ?" ,
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Yes '
	}).then((result) => {
		if (result.isConfirmed) {
			window.location.href = '/comptes/edit?rib=' + rib;
		}
	});
}

document.getElementById("generateRibBtn").addEventListener("click", function () {
	const ribInput = document.getElementById("rib");

	// Generate 8 random digits
	const rib = Math.floor(10000000 + Math.random() * 90000000); // Ensures 8 digits

	// Set the generated value in the input field
	ribInput.value = rib;
});
