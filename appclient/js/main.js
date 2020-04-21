/*let alumnos = [{
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
];*/

const endpoint = 'http://localhost:8080/apprest/api/personas/';
const endpoint2 = 'http://localhost:8080/apprest/api/cursos/';
let alumnos = [];
let cursos = [];

window.addEventListener('load', init());
/*let filtro = document.getElementById('selectorSexo');
filtro.addEventListener("change", filtrado());*/

//http://localhost:8080/apprest/api/personas
function init(){
    console.debug('Document Load and Ready');    
    //listener();

    const promesa = ajax("GET", endpoint, undefined);
    promesa
    .then( data => {
            console.trace('promesa resolve'); 
            alumnos = data;
            listar( alumnos );

    }).catch( error => {
            console.warn('promesa rejectada');
            alert(error);
    });

    /*const promesa2 = ajax("GET", endpoint2, undefined);
    promesa2
    .then( data => {
            console.trace('promesa resolve'); 
            cursos = data;
            listarCursos( cursos );

    }).catch( error => {
            console.warn('promesa rejectada');
            alert(error);
    });*/

    console.debug('continua la ejecuion del script de forma sincrona');
}

function listar(arrayAlumnos){
    let lista = document.getElementById('lista');

    lista.innerHTML = '';
    console.log(arrayAlumnos);
    arrayAlumnos.forEach( (alumnos,i) => lista.innerHTML += 
                        `<li class="list-group-item"><img src = "img/${alumnos.imagen}" alt="avatar">${alumnos.nombre}
                        <button type="button" class="btn btn-warning" onclick="seleccionar(${i})"><i class="fas fa-edit"></i></button>
                        <button type="button" class="btn btn-danger" onclick="eliminar(${i})"><i class="fas fa-trash"></i></button></li>`
        
    );
}

/*function listarCursos(arrayCursos){
    let lista = document.getElementById('listaCursos');

    lista.innerHTML = '';
    console.log(arrayCursos);
    arrayCursos.forEach( (cursos,i) => lista.innerHTML += 
                        `<li class="list-group-item"><img src = "${cursos.imagen}" alt="avatar">${cursos.nombre}, ${cursos.precio}€
                        </li>`
        
    );
}*/

function buscar(){
    let busqueda = document.getElementById('buscar');
    const palabra = busqueda.value.toLowerCase();

    console.log(alumnos);

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

function seleccionar(indice){

    let  alumnoSeleccionado = { "id":-1, "nombre": "sin nombre" , "avatar" : "img/avatar7.png", "sexo": "h" };

    if ( indice > -1 ){
        alumnoSeleccionado = alumnos[indice];
    }
    
    console.debug('click persona seleccionada %o', alumnoSeleccionado);
   
    //rellernar formulario
    document.getElementById('formID').value = alumnoSeleccionado.id;
    document.getElementById('formNombre').value = alumnoSeleccionado.nombre;    
    document.getElementById('formSelectAvatar').value = alumnoSeleccionado.avatar;
    document.getElementById('formSexo').value = alumnoSeleccionado.sexo;

} // seleccionar

function eliminar(indice){
    let alumnoSeleccionado = alumnos[indice];
    console.debug('click eliminar persona %o', alumnoSeleccionado);
    const mensaje = `¿Estas seguro que quieres eliminar  a ${alumnoSeleccionado.nombre} ?`;
    if ( confirm(mensaje) ){

        const url = endpoint + alumnoSeleccionado.id;
        ajax('DELETE', url, undefined)
            .then( data =>  pintarLista() )
            .catch( error => {
                console.warn('promesa rejectada');
                alert(error);
            });

        const promesa = ajax("GET", endpoint, undefined);
        promesa
        .then( data => {
                console.trace('promesa resolve'); 
                alumnos = data;
                listar( alumnos );
        
        }).catch( error => {
            console.warn('promesa rejectada');
                lert(error);
        });

    }
} // eliminar

function guardar(){

    console.trace('click guardar');
    let id = document.getElementById('formID').value;
    let nombre = document.getElementById('formNombre').value;
    let avatar = document.getElementById('formSelectAvatar').value;
    let sexo = document.getElementById('formSexo').value;
    
    let alumno = {
        "id" : id,
        "nombre" : nombre,
        "avatar" : avatar,
        "sexo" : sexo
    };

    console.debug('persona a guardar %o', alumno);

    //CREAR
    if ( id == -1 ){ 
        console.trace('Crear nueva persona');
       
        ajax('POST',endpoint, alumno)
            .then( data => {                
                alert( alumno.nombre + ' ya esta con nosotros ');
                //limpiar formulario
                document.getElementById('formID').value = 0;
                document.getElementById('formNombre').value = '';               
                document.getElementById('formSelectAvatar').value = 'avatar1.png';
                document.getElementById('formSexo').value = '';

                pintarLista();
            })
            .catch( error => {
                console.warn('promesa rejectada %o', error);
                alert(error.informacion);
            });
        

    // MODIFICAR
    }/*else{
        console.trace('Modificar persona');

        const url = endpoint + persona.id;
        ajax('PUT', url , persona)
            .then( data => {
                alert( persona.nombre + ' modificado con exito ');
                pintarLista();
            })
            .catch( error => {
                console.warn('No se pudo actualizar %o', error);
                alert(error.informacion);
            });
        
    }*/

}// guardar