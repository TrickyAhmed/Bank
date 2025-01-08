function deleteClient(id, e) {
    e.preventDefault(); // Prevent default action


    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: "btn btn-success",
            cancelButton: "btn btn-danger"
        },
        buttonsStyling: false
    });

    swalWithBootstrapButtons.fire({
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
                url: "/clients/" + id,
                type: "DELETE",
                success: function () {
                    $("#row-" + id).remove();
                    swalWithBootstrapButtons.fire({
                        title: "Supprimé !",
                        text: "Le Client a été supprimé.",
                        icon: "success"
                    });
                },
                error: function () {
                    swalWithBootstrapButtons.fire({
                        title: "Erreur !",
                        text: "Une erreur est survenue. Veuillez réessayer.",
                        icon: "error"
                    });
                }
            });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            swalWithBootstrapButtons.fire({
                title: "Annulé",
                text: "Client non supprimé",
                icon: "error"
            });
        }
    });
}

function saveClient() {

    const notyf = new Notyf({
        duration: 5000,
        position: {
            x: 'right',
            y: 'top',
        }
    });

    const Name = $("#clientName").val();
    const Email = $("#clientEmail").val();
    const Phone = $("#clientPhone").val();

    if (!Name || !Email || !Phone) {
        notyf.error("Les champs doivent être remplis!");
        return;
    }

    const phoneRegex = /^[0-9]{8}$/;
    if (!phoneRegex.test(Phone)) {
        notyf.error("Le numéro de téléphone doit être composé de 8 chiffres uniquement!");
        return;
    }

    $.ajax({
        url: "/clients/save",
        type: "POST",
        data: {
            Name: Name,
            Email: Email,
            Phone: Phone
        },
        success: function () {
                Swal.fire({
                    title: "Sauvegardé !",
                    text: "Client enregistré avec succés",
                    icon: "success",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "btn btn-success"
                    }
                }).then(() => {
                    window.location.href = "/clients/"; // Redirect to clients page
                });

        },
        error: function () {
                Swal.fire({
                    title: "Erreur !",
                    text: "Adresse email existe déja !!",
                    icon: "error",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "btn btn-danger"
                    }
                });
            }
    });
}


function showEditAlert(id) {
    Swal.fire({
        title: 'Êtes-vous sûr ?',
        text: "Modifier le client avec l'ID : " + id + " ?" ,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Oui'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = '/clients/edit?id=' + id;
        }
    });
}


function Edited(e) {
    e.preventDefault();

    const notyf = new Notyf({
        duration: 3000,
        position: {
            x: 'right',
            y: 'top',
        }
    });
    const ID = $("#id").val();
    const Name = $("#name").val();
    const Email = $("#email").val();
    const Phone = $("#phoneNumber").val();

    if (!ID ||!Name || !Email || !Phone) {
        notyf.error("Les champs doivent être remplis!");
        return;
    }

    const phoneRegex = /^[0-9]{8}$/;
    if (!phoneRegex.test(Phone)) {
        notyf.error("Le numéro de téléphone doit être composé de 8 chiffres uniquement!");
        return;
    }

    $.ajax({
        url: "/clients/update",
        type: "POST",
        data: {
            id : ID,
            name: Name,
            email: Email,
            phoneNumber: Phone
        },
        success: function () {
            Swal.fire({
                title: "Modifié !",
                text: "Client Modifié avec succés",
                icon: "success",
                confirmButtonText: "OK",
                customClass: {
                    confirmButton: "btn btn-success"
                }
            });

        },

        error: function () {
            Swal.fire({
                title: "Erreur !",
                text: "Adresse email existe déja !!",
                icon: "error",
                confirmButtonText: "OK",
                customClass: {
                    confirmButton: "btn btn-danger"
                }
            });
        }

    });

}


