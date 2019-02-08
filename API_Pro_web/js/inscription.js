window.onload = init;
let url;

function init() {
    url = localStorage.getItem("url");
}

function inscription() {
    var email = document.getElementById("email").value;
    var lastname = document.getElementById("lastname").value;
    var firstname = document.getElementById("fisrtname").value;
    var birthday = document.getElementById("birthday").value;
    var phone = document.getElementById("phone").value;
    var mdp = document.getElementById("mdp").value;
    var mdp2 = document.getElementById("mdp2").value;
    var societe = document.getElementById("societe").value;


    if (mdp == mdp2) {
        if (email != "" & lastname != "" & firstname != "" & phone != "" & societe != "") {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", url, true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var json = JSON.parse(xhr.responseText);

                    if (json.status) {
                        console.log(json.body.firstname + " " + json.body.name);
                        // CHANGER DE PAGE
                        localStorage.setItem("email", json.body.email);
                        localStorage.setItem("firstname", json.body.firstname);
                        localStorage.setItem("amount", json.body.amount);
                        document.location.href = "./acceuil.html";
                    } else {
                        alert("Erreur: " + json.body);
                    }
                }
            };
        }
        else {
            alert("Erreur: Un ou plusieurs champs manquants");
        }
    }
    else {
        alert("Erreur: Les mots de passe sont différents");
    }
    var data =
        '{' +
        '"action":"Inscription",' +
        '"body":{' +
        '"email":"' +
        email +
        '",' +
        '"password":"' +
        mdp +
        '",' +
        '"name":"' +
        lastname +
        '",' +
        '"firstname":"' +
        firstname +
        '",' +
        '"birthday":"' +
        birthday +
        '",' +
        '"phone_number":"' +
        phone +
        '",' +
        '"societe":"' +
        societe +
        '"}}';
    xhr.send(data);
}