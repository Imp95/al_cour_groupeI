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

                if (id != null && date != null && !annonces.includes(id)) {
                console.log("id : " + id + ", date : " + date);
                var re_id = new RegExp('^[0-9]+$');
                var re_date = new RegExp('([0-2]?[0-9]|30|31)\/((10|11|12)|0?[1-9])\/[0-9]{2} ([0|1]?[0-9]|2[0-4])\:[0-5][0-9]');
                console.log("id check : " + re_id.test(id));
                console.log("date check : " + re_date.test(date));
            }
        }
    }


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