const form = document.querySelector("#add-student");

form.addEventListener('submit', function (e) {
    e.preventDefault();
    const data = `name=${this.name.value}&surname=${this.surname.value}&email=${this.email.value}&password=${this.password.value}&modules=${getModuleValue(form)}&coins=${this.coins.value}&checkbox=${getCheckValue(form)}`;
    console.log(data);
    setReward(data);
});


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
    fetch("http://localhost:8000/mentor/add_student",
        {
            mode: 'no-cors',
            method: "POST",
            body: data
        })
        .then(function (response) {
            console.log(response);
        });
}