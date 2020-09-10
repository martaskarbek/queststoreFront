const form = document.querySelector("#add-reward");
const questId = document.getElementById("questId").textContent;

form.addEventListener('submit', function (e) {
    e.preventDefault();
    const data = `name=${this.name.value}&price=${this.price.value}&description=${this.description.value}&radio=${getRadioVal(form, "radio")}&checkbox=${getCheckValue(form)}&modules=${getModuleValue(form)}&questId=${questId}`;
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

function getModuleValue(form) {
    var e = document.getElementById("modules");
    var userChoice = e.options[e.selectedIndex].value;
    return userChoice;
}

function getCheckValue(form) {
    var val = false;
    var checkbox = document.getElementById('checkbox');

        if ( checkbox.checked ) { // radio checked?
            val = checkbox.value; // if so, hold its value in val

    }
    return val;
}


function setReward(data) {
    fetch("http://localhost:8000/mentor/quests_mentor",
        {
            mode: 'no-cors',
            method: "POST",
            body: data
        })
        .then(function (response) {
            console.log(response);
        });
}