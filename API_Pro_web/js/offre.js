window.onload = init;
let url;
let firstname;
let amount;
let data;
let email;
let annonces;

function init() {
    url = localStorage.getItem("url");
    firstname = localStorage.getItem("firstname");
    amount = localStorage.getItem("amount");
    email = localStorage.getItem("email");
    document.getElementById("welcome").innerHTML = "Bonjour " + firstname + ", votre solde est de " + amount + " points.";
    majTableau();
}

function b_csvfile_listener() {
    var csvfile = document.getElementById("csvfile").files[0];
    var reader = new FileReader();
    console.log(csvfile.name + "\n\n" + reader.readAsText(csvfile));
    reader.onload = function (event) {
        data = event.target.result;
        //TODO
        doStuff();
    }
}

function doStuff() {
    dataSplit = data.split('\n');
    console.log(dataSplit);
}

function majTableau() {
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

                    var row = table.insertRow(i);
                    var id = row.insertCell(0);
                    var annonce = row.insertCell(1);
                    var date = row.insertCell(2);

                    id.innerHTML = json.body[i].id;
                    annonce.innerHTML = json.body[i].ad_id;
                    date.innerHTML = json.body[i].proposed_date;
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
}