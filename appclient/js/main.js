let lista = document.getElementById('lista');
    let alumnos = [{
                    "id": 1,
                    "nombre": "Ana",
                    "imagen": "avatar1"
                },
                {
                    "id": 2,
                    "nombre": "Veronica",
                    "imagen": "avatar2"
                },
                {
                    "id": 3,
                    "nombre": "Pepe",
                    "imagen": "avatar3"
                },
                {
                    "id": 4,
                    "nombre": "Julio",
                    "imagen": "avatar4"
                },
                {
                    "id": 5,
                    "nombre": "Marta",
                    "imagen": "avatar5"
                }
            ];
lista.innerHTML = '';
for(let i = 0; i < 5; i++){
    lista.innerHTML += `<li class="list-group-item"><img src="img/${alumnos[i].imagen}.png" alt="avatar">${alumnos[i].nombre}</li>`;
}