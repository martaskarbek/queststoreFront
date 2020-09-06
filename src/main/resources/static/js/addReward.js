const form = document.querySelector("#add-reward");

form.addEventListener('submit', function (e) {
    e.preventDefault();
    //name=Agnieszka&surname=Kowalska&email=a.kowalska%40codecool.com
    const data = `name=${this.name.value}&price=${this.price.value}&description=${this.description.value}&radio=${getRadioVal(form, "radio")}`;
    console.log(data);
    setReward(data);
});

function getRadioVal(form, name) {
    var val;
    // get list of radio buttons with specified name
    var radios = form.elements[name];

    // loop through list of radio buttons
    for (var i=0, len=radios.length; i<len; i++) {
        if ( radios[i].checked ) { // radio checked?
            val = radios[i].value; // if so, hold its value in val
            break; // and break out of for loop
        }
    }
    return val; // return value of checked radio or undefined if none checked
}

function setReward(data) {
    fetch("http://localhost:8000/mentor/add_artifact",
        {
            mode: 'no-cors',
            method: "POST",
            body: data
        })
        .then(function (response) {
            console.log(response);
        });
}