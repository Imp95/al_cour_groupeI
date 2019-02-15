window.onload = init;
let url;
let firstname;
let amount;
let email;
let id_c;
let lock;

function init() {
    lock = true;
    url = localStorage.getItem("url");
    firstname = localStorage.getItem("firstname");
    email = localStorage.getItem("email");
    if (firstname == null) {
        document.location.href = "./index.html";
    }
    majAmount()
    majTableau()
    lock = false;
}

function disconnect() {
    if (!lock) {
        localStorage.removeItem("id");
        localStorage.removeItem("email");
        localStorage.removeItem("firstname");
        localStorage.removeItem("amount");
        document.location.href = "./index.html";
    }
}

function goToAcceuil() {
    if (!lock) {
        document.location.href = "./acceuil.html";
    }
}

function goToOffre() {
    if (!lock) {
        document.location.href = "./offre.html";
    }
}

function goToHistorique() {
    if (!lock) {
        document.location.href = "./historique.html";
    }
}

function majTableau() {
    lock = true;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {

        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);

            if (json.status) {
                var table = document.getElementById("contrats");
                annonces = [];
                table.innerHTML = "";

                for (var i = 0; i < json.body.length; i++) {
                    id_c = json.body[i].id;

                    var b_maj = document.createElement('input');
                    b_maj.setAttribute('type', 'button');
                    b_maj.onclick = function () {
                        majContrat(id_c);
                    }

                    var row = table.insertRow(i);
                    var id = row.insertCell(0);
                    var date = row.insertCell(1);
                    var payment = row.insertCell(2);
                    var departure = row.insertCell(3);
                    var arrival = row.insertCell(4);
                    var statut = row.insertCell(5);
                    var maj = row.insertCell(6);

                    id.innerHTML = json.body[i].id;
                    date.innerHTML = json.body[i].proposed_date;
                    payment.innerHTML = json.body[i].payment;
                    departure.innerHTML = json.body[i].departure_address;
                    arrival.innerHTML = json.body[i].arrival_address;
                    if (json.body[i].status == 0) {
                        statut.innerHTML = "Colis en attente";
                    }
                    else {
                        statut.innerHTML = "En cours d'acheminement";
                    }

                    maj.appendChild(b_maj);

                }
            }

            else {
                alert("Erreur: " + json.body);
            }
        }
    };

    var data =
        "{" +
        '"action":"VoirContrats",' +
        '"body":{' +
        '"user_email":"' +
        email +
        '"}}';
    xhr.send(data);
    lock = false;
}

function majContrat(id) {
    var code = prompt("Entrer votre code", "");

    if (code > 0) {
        console.log(code)
        var xhr = new XMLHttpRequest();
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {

            if (xhr.readyState === 4 && xhr.status === 200) {
                var json = JSON.parse(xhr.responseText);
                console.log(json)
                if (json.status) {
                    majTableau();
                    if (json.body.status == 2) {
                        // to replace with db call
                        document.location.href = "./contrat.html";
                    }
                }

                else {
                    alert("Erreur: " + json.body);
                }
            }
        };

        var data =
            "{" +
            '"action":"UpdateContrat",' +
            '"body":{' +
            '"id_contract":"' +
            id +
            '",' +
            '"preuve":' +
            code +
            '}}';
        xhr.send(data);
    }
}

function majAmount() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {

        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);
            localStorage.setItem("amount", json.body.amount);
            amount = localStorage.getItem("amount");
            if (amount == null) {
                document.location.href = "./index.html";
            }
            document.getElementById("welcome").innerHTML = "Bonjour " + firstname + ", votre solde est de " + amount + " points.";
        }
    };

    var data =
        "{" +
        '"action":"UpdateAmount",' +
        '"body":{' +
        '"user_email":"' +
        email+
        '"}}';
    xhr.send(data);
}
