window.onload = init;
let url;
let firstname;
let amount;
let data;

function init() {
    url = localStorage.getItem("url");
    firstname = localStorage.getItem("firstname");
    amount = localStorage.getItem("amount");
    document.getElementById("welcome").innerHTML = "Bonjour " + firstname + ", votre solde est de " + amount + " points.";
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

function disconnect() {
    localStorage.removeItem("email");
    localStorage.removeItem("firstname");
    localStorage.removeItem("amount");
    document.location.href = "./connexion.html";
}

function b_search() {
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
}