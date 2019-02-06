window.onload = init;
let url;
let firstname;
let amount;

function init() {
    url = localStorage.getItem("url");
    firstname = localStorage.getItem("firstname");
    amount = localStorage.getItem("amount");
    if (firstname == null | amount == null) {
        document.location.href = "./connexion.html";
    }
    document.getElementById("welcome").innerHTML = "Bonjour " + firstname + ", votre solde est de " + amount + " points.";
}

function disconnect() {
    localStorage.removeItem("email");
    localStorage.removeItem("firstname");
    localStorage.removeItem("amount");
    document.location.href = "./connexion.html";
}