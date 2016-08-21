function formValidation(oEvent) {
    oEvent = oEvent || window.event;
    var valid = true;
    var pattern = /^[^\\/?%*:\'\"|<>\.]+$/;
    if (!pattern.test(document.getElementById('name').value) || document.getElementById('name').value.length < 2) {
        valid = false;
    } else if (!pattern.test(document.getElementById('short_name').value) || document.getElementById('short_name').value.length < 1) {
        valid = false;
    }

    if (valid) {
        document.getElementById('add').disabled = false;
    } else {
        document.getElementById('add').disabled = true;
    }
}

window.onload = function () {

    var textName = document.getElementById('name');
    var textLastName = document.getElementById('short_name');
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