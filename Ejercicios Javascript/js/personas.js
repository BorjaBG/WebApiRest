window.addEventListener('load', init() );
            const endpoint = 'https://randomuser.me/api/?results=6';
            let personas = [];

            function init(){
                console.debug('Document Load and Ready');    
                //listener();

                const promesa = ajax("GET", endpoint, undefined);
                promesa
                .then( data => {
                        console.trace('promesa resolve'); 
                        personas = data;
                        pintarLista( personas );

                }).catch( error => {
                        console.warn('promesa rejectada');
                        alert(error);
                });

                console.debug('continua la ejecuion del script de forma sincrona');
                // CUIDADO!!!, es asincrono aqui personas estaria sin datos
                // pintarLista( personas );

            }//init


            /*function init(){

                const url = 'https://randomuser.me/api/?results=6';
                //creamos un objeto para realizar la REQUEST con ajax
                var xhttp = new XMLHttpRequest();

                //PONER CODIGO DENTRO
                xhttp.onreadystatechange = function() {
                    // recibimos la RESPONSE
                    if (this.readyState == 4 && this.status == 200) {
                        console.debug( this.responseText );

                        // parsear texto a Json
                        const jsonData = JSON.parse(this.responseText);    
                        console.debug( jsonData );

                        // array con personas
                        const personas = jsonData.results;

                        let lista = document.getElementById('lista');
                        lista.innerHTML = '';

                        for(let i=0; i < personas.length; i++ ){
                            const persona = personas[i];
                            
                            lista.innerHTML += `<li class="list-group-item">
                                                    <img src="${persona.picture.medium}" alt="avatar">
                                                    <b>${persona.name.first}  ${persona.name.last}</b>
                                                    <p>Usuario: ${persona.login.username}</p>
                                                    <p>Edad: ${persona.dob.age}</p>
                                                    <p>Sexo: ${persona.gender}</p>
                                                    <p>Ubicacion: ${persona.location.city}, ${persona.location.state}, ${persona.location.country}</p>
                                                    <p>E-mail: ${persona.email}</p>
                                                </li>`;

                        }



                    }// his.readyState == 4 && this.status == 200

                };// onreadystatechange

                // preparamos la petición GET
                xhttp.open("GET", url , true);
                // enviar la peticion asincrona, meter el codigo en onreadystatechange
                xhttp.send();




            } */