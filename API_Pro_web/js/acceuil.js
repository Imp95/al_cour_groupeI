window.onload = init;
let url;
let firstname;
let amount;
let data;
let lock;

function init() {
    lock = true;
    url = localStorage.getItem("url");
    firstname = localStorage.getItem("firstname");
    amount = localStorage.getItem("amount");
    if (firstname == null | amount == null) {
        document.location.href = "./index.html";
    }
    document.getElementById("welcome").innerHTML = "Bonjour " + firstname + ", votre solde est de " + amount + " points.";
    lock = false;
}

function disconnect() {
    if (!lock) {
        localStorage.removeItem("email");
        localStorage.removeItem("firstname");
        localStorage.removeItem("amount");
        document.location.href = "./index.html";
    }
}

function goToOffre() {
    if (!lock) {
        document.location.href = "./offre.html";
    }
}

function goToContrat() {
    if (!lock) {
        document.location.href = "./contrat.html";
    }
}

function goToHistorique() {
    if (!lock) {
        document.location.href = "./historique.html";
    }
}

function b_search() {
    lock = true;
    var depart = document.getElementById("depart").value;
    var arrivee = document.getElementById("arrivee").value;
    var espace = document.getElementById("espace").value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {

        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);

            if (json.status) {
                // CREER LA LISTE
                var table = document.getElementById("annonces");
                table.innerHTML = "";
                for (var i = 0; i < json.body.length; i++) {
                    var row = table.insertRow(i);

                    var id = row.insertCell(0);
                    var bagage = row.insertCell(1);
                    var paiement = row.insertCell(2);
                    var a_depart = row.insertCell(3);
                    var a_arrivee = row.insertCell(4);
                    var d_depart = row.insertCell(5);
                    var d_arrivee = row.insertCell(6);

                    id.innerHTML = json.body[i].id;
                    bagage.innerHTML = json.body[i].bagage;
                    paiement.innerHTML = json.body[i].payment;
                    a_depart.innerHTML = json.body[i].departure_address;
                    a_arrivee.innerHTML = json.body[i].arrival_address;
                    d_depart.innerHTML = json.body[i].departure_date;
                    d_arrivee.innerHTML = json.body[i].arrival_date;
                }
            } else {
                alert("Erreur: " + json.body);
            }
        }

    };
    var data =
        "{" +
        '"action":"RechercheAnnonce",' +
        '"body":{' +
        '"departure_town":"' +
        depart +
        '",' +
        '"arrival_town":"' +
        arrivee +
        '",' +
        '"bagage":"' +
        espace +
        '"}}';
    xhr.send(data);
    lock = false;
}