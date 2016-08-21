function formValidation(oEvent) {
    oEvent = oEvent || window.event;
    var valid = true;
    var pattern = /^[a-zA-Z]+$/;
    if (!pattern.test(document.getElementById('first_name').value) || document.getElementById('first_name').value.length < 2) {
        valid = false;
    }
    if (!pattern.test(document.getElementById('last_name').value) || document.getElementById('last_name').value.length < 2) {
        valid = false;
    } 
    if (valid) {
        document.getElementById('add').disabled = false;
    } else {
        document.getElementById('add').disabled = true;
    }
}

window.onload = function () {
    var textName = document.getElementById('first_name');
    var textLastName = document.getElementById('last_name');
    textName.onkeyup = formValidation;
    textLastName.onkeyup = formValidation;
};

function popupform(myform, windowname) {
    if (!window.focus) {
        return true;
    }
    window.open('', windowname, 'height=400,width=600,scrollbars=yes,left=700,top=200');
    myform.target = windowname;
    return true;
}
function enable_submit(status) {
    if (document.getElementById('checkbox').disabled === true) {
        document.getElementById('chekbox').removeAttribute('checked');
    }
    document.getElementById('edit').disabled = !status;
}
function getRowIndex(x) {
    document.getElementById('hiddenRowIndex').value = x.rowIndex;
}