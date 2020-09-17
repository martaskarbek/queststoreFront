const form = document.querySelector("#add-reward");
const studentId = document.getElementById('studentId').textContent;
const questId = document.getElementById('questId').textContent;
const submission = document.getElementById("submission");


form.addEventListener('submit', function (e) {
    e.preventDefault();
    const data = `studentId=${studentId}&questId=${questId}&submission=${submission.value}&status=${getStatusValue(form)}&value=0`;
    console.log(data);
    setReward(data);
});



function getStatusValue(form) {
    var e = document.getElementById("status");
    var userChoice = e.options[e.selectedIndex].value;
    return userChoice;
}



function setReward(data) {
    fetch("http://localhost:8000/student/student_menu",
        {
            mode: 'no-cors',
            method: "POST",
            body: data
        })
        .then(function (response) {
            console.log(response);
        });
}