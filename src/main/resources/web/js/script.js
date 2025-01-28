// Função de cadastro (apenas lógica visual, dados são salvos em Java)
document.querySelector('#registerForm').addEventListener('submit', (event) => {
    event.preventDefault(); // Previne o comportamento padrão do formulário

    const username = document.querySelector('#registerUsername').value;
    const email = document.querySelector('#registerEmail').value;
    const password = document.querySelector('#registerPassword').value;

    // Verificar se os campos não estão vazios
    if (!username || !email || !password) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    // Salvar dados via Java (JavaBridge)
    if (window.javaBridge) {
        window.javaBridge.saveData(username, email, password); // Chama o método Java para salvar os dados
    } else {
        console.error("Erro ao acessar o objeto Java.");
    }

    // Redirecionar para a tela de login (apenas visual, não precisa de JavaScript aqui)
    document.querySelector('.wrapper').classList.remove('active');
});

// Função de login (apenas lógica visual, dados são validados em Java)
document.querySelector('#loginForm').addEventListener('submit', (event) => {
    event.preventDefault(); // Previne o comportamento padrão do formulário

    const username = document.querySelector('#loginUsername').value;
    const password = document.querySelector('#loginPassword').value;

    // Verificar se os campos não estão vazios
    if (!username || !password) {
        alert("Por favor, preencha ambos os campos.");
        return;
    }
    console.log("Clicou no botão Login!");  // Verifique se isso aparece no console

    // Realizar login via Java (JavaBridge)
    if (window.javaBridge) {
        window.javaBridge.login(username, password); // Chama o método Java para verificar o login
    } else {
        console.error("Erro ao acessar o objeto Java.");
    }
});
