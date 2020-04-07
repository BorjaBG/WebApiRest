let alumnos = [{
        "id": 1,
        "nombre": "Ana",
        "sexo": "m",
        "imagen": "avatar1"
    },
    {
        "id": 2,
        "nombre": "Veronica",
        "sexo": "m",
        "imagen": "avatar2"
    },
    {
        "id": 3,
        "nombre": "Pepe",
        "sexo": "h",
        "imagen": "avatar3"
    },
    {
        "id": 4,
        "nombre": "Julio",
        "sexo": "h",
        "imagen": "avatar4"
    },
    {
        "id": 5,
        "nombre": "Marta",
        "sexo": "m",
        "imagen": "avatar5"
    }
];

window.addEventListener('load', init());
/*let filtro = document.getElementById('selectorSexo');
filtro.addEventListener("change", filtrado());*/

function init(){
    console.debug('Archivo cargado');
    listar(alumnos);
}

function listar(arrayAlumnos){
    let lista = document.getElementById('lista');

    lista.innerHTML = '';
    arrayAlumnos.forEach(alumnos => lista.innerHTML += 
                        `<li class="list-group-item"><img src="img/${alumnos.imagen}.png" alt="avatar">${alumnos.nombre}</li>`
        
    );
}

function buscar(){
    let busqueda = document.getElementById('buscar');
    const palabra = busqueda.value.toLowerCase();

    if(palabra) {
        const alumnosFiltradosNombre = alumnos.filter( el => el.nombre.toLowerCase().includes(palabra));
        filtrado(alumnosFiltradosNombre);
    }else {
        filtrado(alumnos);
    }
}

function filtrado(alumnosFiltrar){
    let filtro = document.getElementById('selectorSexo');
    console.log(filtro.value);
    switch (filtro.value) {
        case "t":
            listar(alumnosFiltrar);
            break;
        case "h":
            const alumnosFiltrados = alumnosFiltrar.filter( el => el.sexo == "h");
            listar(alumnosFiltrados);
            break;
        case "m":
            const alumnosFiltrados2 = alumnosFiltrar.filter( el => el.sexo == "m");
            listar(alumnosFiltrados2);
            break;
    }
}