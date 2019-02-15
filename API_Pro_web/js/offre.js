window.onload = init;
let url;
let firstname;
let amount;
let data;
let email;
let annonces;
let ids;
let lock;

function init() {
    lock = true;
    url = localStorage.getItem("url");
    firstname = localStorage.getItem("firstname");
    amount = localStorage.getItem("amount");
    email = localStorage.getItem("email");
    if (firstname == null | amount == null) {
        document.location.href = "./index.html";
    }
    document.getElementById("welcome").innerHTML = "Bonjour " + firstname + ", votre solde est de " + amount + " points.";
    ids = [];
    majTableau();
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

function goToAcceuil() {
    if (!lock) {
        document.location.href = "./acceuil.html";
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

function b_csvfile_listener() {
    lock = true;
    var csvfile = document.getElementById("csvfile").files[0];
    var reader = new FileReader();
    console.log(csvfile.name + "\n\n" + reader.readAsText(csvfile));
    reader.onload = function (event) {
        data = event.target.result;
        createOffers();
    }
}

function createOffers() {
    //parsing
    dataSplit = data.split('\n');
    if (dataSplit.length > 0) {
        for (var i = 1; i < dataSplit.length; i++) {
            var elements = dataSplit[i].split(',');
            var id = elements[0];
            var date = elements[1];

            if (id != null && date != null && !ids.includes(id)) {
                console.log("id : " + id + ", date : " + date);
                var re_id = new RegExp('^[0-9]+$');
                var re_date = new RegExp('([0-2]?[0-9]|30|31)\/((10|11|12)|0?[1-9])\/[0-9]{2} ([0|1]?[0-9]|2[0-4])\:[0-5][0-9]');
                console.log("id check : " + re_id.test(id));
                console.log("date check : " + re_date.test(date));

                if (re_id.test(id) && re_date.test(date)) {
                    var num = date.match(/\d+/g).map(Number);
                    if (num[4] == 0) {
                        sendOffer(id, num[0] +"/" + num[1] + "/" + num[2] + " " + num[3] + ":00");
                    }
                    else {
                        sendOffer(id, num[0] +"/" + num[1] + "/" + num[2] + " " + num[3] + ":" + num[4]);
                    }
                }
            }
        }
    }
    lock = false;
}

function sendOffer(id, date) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {

        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);

            if (json.status) {
                ids.push(id);
                console.log("Offre créer : id " + id + " | date " + date);
                majTableau();
            }

            else {
                alert("Erreur: " + json.body);
            }
        }
    };

    var data =
        "{" +
        '"action":"CreationOffre",' +
        '"body":{' +
        '"carrier_email":"' +
        email +
        '",' +
        '"ad_id":"' +
        id +
        '",' +
        '"proposed_date":"' +
        date +
        '"}}';
    xhr.send(data);
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
                var table = document.getElementById("offres");
                annonces = [];
                table.innerHTML = "";

                for (var i = 0; i < json.body.length; i++) {
                    annonces.push(json.body[i].ad_id);
                    
                    if (!ids.includes(json.body[i].ad_id)) {
                        ids.push(json.body[i].ad_id);
                    }
                    
                    var row = table.insertRow(i);
                    var id = row.insertCell(0);
                    var annonce = row.insertCell(1);
                    var date = row.insertCell(2);
                    var bagage = row.insertCell(3);
                    var payment = row.insertCell(4);

                    id.innerHTML = json.body[i].id;
                    annonce.innerHTML = json.body[i].ad_id;
                    date.innerHTML = json.body[i].proposed_date;
                    bagage.innerHTML = json.body[i].bagage;
                    payment.innerHTML = json.body[i].payment;
                }
            }

            else {
                alert("Erreur: " + json.body);
            }
        }
    };

    var data =
        "{" +
        '"action":"VoirOffres",' +
        '"body":{' +
        '"user_email":"' +
        email +
        '"}}';
    xhr.send(data);
    lock = false;
}