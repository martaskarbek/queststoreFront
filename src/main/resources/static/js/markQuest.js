const form = document.querySelector("#add-reward");
const studentId = document.getElementById('studentId').textContent;
const questId = document.getElementById('questId').textContent;
const submission = document.getElementById("submission");
const value = document.getElementById('value').textContent;

form.addEventListener('submit', function (e) {
    e.preventDefault();
    const data = `studentId=${studentId}&questId=${questId}&value=${value}&submission=${submission.value}&status=${getStatusValue(form)}`;
    console.log(data);
    setReward(data);
});


function getStatusValue(form) {
    var e = document.getElementById("status");
    var userChoice = e.options[e.selectedIndex].value;
    return userChoice;
}



function setReward(data) {
    fetch("http://localhost:8000/mentor/mark_quest",
        {
            mode: 'no-cors',
            method: "POST",
            body: data
        })
        .then(function (response) {
            console.log(response);
        });
}