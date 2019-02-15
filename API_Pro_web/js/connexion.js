window.onload = init;
let url;

function init() {
  url = "http://localhost:5555/receive_event";
  localStorage.clear;
  localStorage.setItem("url", url);
}

function connect() {
  var email = document.getElementById("email").value;
  var mdp = document.getElementById("mdp").value;

  var xhr = new XMLHttpRequest();
  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.onreadystatechange = function () {

    if (xhr.readyState === 4 && xhr.status === 200) {
      var json = JSON.parse(xhr.responseText);

      if (json.status) {
        if (json.body.societe != "") {
          console.log(json.body.firstname + " " + json.body.name);
          // CHANGER DE PAGE
          localStorage.setItem("id", json.body.id);
          localStorage.setItem("email", json.body.email);
          localStorage.setItem("firstname", json.body.firstname);
          localStorage.setItem("amount", json.body.amount);
          document.location.href = "./acceuil.html";
        }
        else {
          alert("Erreur: Ceci n'est pas un compte pro");
        }
      } else {
        alert("Erreur: " + json.body);
      }
    }

  };
  var data =
    "{" +
    '"action":"Connexion",' +
    '"body":{' +
    '"email":"' +
    email +
    '",' +
    '"password":"' +
    mdp +
    '"}}';
  xhr.send(data);
}