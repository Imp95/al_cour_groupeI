window.onload = init;

function init() {
    var url = localStorage.getItem("url");
    var firstname = localStorage.getItem("firstname");
    var amount = localStorage.getItem("amount");
    document.getElementById("name").innerHTML= "Bonjour " + firstname +" !";
}