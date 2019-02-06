window.onload = init;
let url;
let firstname;
let amount;
let email;

function init() {
    url = localStorage.getItem("url");
    firstname = localStorage.getItem("firstname");
    amount = localStorage.getItem("amount");
    email = localStorage.getItem("email");
    if (firstname == null | amount == null) {
        document.location.href = "./connexion.html";
    }
    document.getElementById("welcome").innerHTML = "Bonjour " + firstname + ", votre solde est de " + amount + " points.";
    majTableau();
}

function disconnect() {
    localStorage.removeItem("email");
    localStorage.removeItem("firstname");
    localStorage.removeItem("amount");
    document.location.href = "./connexion.html";
}

function majTableau() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {

        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);

            if (json.status) {
                var table = document.getElementById("historique");
                annonces = [];
                table.innerHTML = "";

                for (var i = 0; i < json.body.length; i++) {      
                    var row = table.insertRow(i);
                    var id = row.insertCell(0);
                    var date = row.insertCell(1);
                    var payment = row.insertCell(2);
                    var departure = row.insertCell(3);
                    var arrival = row.insertCell(4);

                    id.innerHTML = json.body[i].id;
                    date.innerHTML = json.body[i].proposed_date;
                    payment.innerHTML = json.body[i].payment;
                    departure.innerHTML = json.body[i].departure_address;
                    arrival.innerHTML = json.body[i].arrival_address;
                }
            }

            else {
                alert("Erreur: " + json.body);
            }
        }
    };

    var data =
        "{" +
        '"action":"HistoriqueContrats",' +
        '"body":{' +
        '"user_email":"' +
        email +
        '"}}';
    xhr.send(data);
}