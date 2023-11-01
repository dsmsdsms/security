
//-----------Delete user ---------

//function deleteUser(username) {
//    var isConfirmed = confirm('Are you sure you want to delete this user?');
//    if (isConfirmed) {
//        var form = document.createElement('form');
//        form.method = 'post';
//        form.action = '/auth/delete/' + username;
//        var inputMethod = document.createElement('input');
//        inputMethod.type = 'hidden';
//        inputMethod.name = '_method';
//        inputMethod.value = 'DELETE';
//        form.appendChild(inputMethod);
//        document.body.appendChild(form);
//        form.submit();
//    }
//}

function deleteUser(username) {
    if (confirm('Are you sure you want to delete this user?')) {
        fetch('/auth/delete/' + username, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                return response.text();
            }
        })
        .then(text => {
            if (text) {
                alert(text);
            }
        })
        .catch(error => console.error('Error deleting user:', error));
    }
}



//-----------Add new user ---------

document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("registrationForm");
    const errorContainer = document.getElementById("errorContainer");

    form.addEventListener("submit", function(event) {
        event.preventDefault();

        const formData = new FormData(form);
        const jsonData = {};

        formData.forEach((value, key) => {
            jsonData[key] = value;
        });

        fetch('/auth/registration', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jsonData)
        })
        .then(response => response.json())
        .then(data => {
            if (data.errorMessage) {
                errorContainer.textContent = data.errorMessage;
            } else if (data.successMessage) {
                alert(data.successMessage);         // success message
                location.reload();                  // page update
            }
        })
        .catch(error => {
            console.error('Error!', error);
        });
    });
});
