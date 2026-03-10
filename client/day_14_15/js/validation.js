function login() {
    const usernameInput = document.getElementById("loginUsername");
    const passwordInput = document.getElementById("loginPassword");
   
    const username = usernameInput ? usernameInput.value.trim() : "";
    const password = passwordInput ? passwordInput.value.trim() : "";
   
    if (username === "" || password === "") {
      console.log("Both Username and Password are required!");
      return;
    }
   
    console.log(`Login clicked. Username: ${username}, Password: ${password}`);
  }
   
 
  function register() {
    const nameInput = document.getElementById("registerName");
    const emailInput = document.getElementById("registerEmail");
    const usernameInput = document.getElementById("registerUsername");
    const passwordInput = document.getElementById("registerPassword");
   
    const name = nameInput ? nameInput.value.trim() : "";
    const email = emailInput ? emailInput.value.trim() : "";
    const username = usernameInput ? usernameInput.value.trim() : "";
    const password = passwordInput ? passwordInput.value.trim() : "";
   
    if (!name || !email || !username || !password) {
      console.log("All fields are mandatory.");
      return;
    }
   
    console.log(
      `Register clicked. Name: ${name}, Email: ${email}, Username: ${username}, Password: ${password}`
    );
  }
   
 
  if (typeof module !== "undefined" && module.exports) {
    module.exports = { login, register };
  }