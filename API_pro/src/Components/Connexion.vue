<template>
  <div id="connexion">
    <h2>Authentification</h2>
    <div>
      <label for="email">Email:</label>
      <input type="text" id="email" name="email">
    </div>
    <br>
    <div>
      <label for="pass">Mot de passe:</label>
      <input type="password" id="pass" name="pass" required>
    </div>
    <br>
    <button v-on:click="connect">Connexion</button>
  </div>
</template>

<script>
module.exports = {
  methods: {
    connect: function(event) {
      var mail = document.getElementById("email").value;
      var mdp = document.getElementById("pass").value;

      var xhr = new XMLHttpRequest();
      var url = "http://192.168.43.134:5555/receive_event";
      xhr.open("POST", url, true);
      xhr.setRequestHeader("Content-Type", "application/json");
      xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
          var json = JSON.parse(xhr.responseText);
          console.log(json.status);
        }
      };
      var data = '{"action" : "Connexion", "body" : { "email":"example1@email.com", "password":"azerty" }}';
      xhr.send(data);
    }
  }
};
</script>

<style scoped>
p {
  color: red;
}
</style>